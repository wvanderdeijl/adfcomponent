package com.redheap.rh.faces.component;

import java.beans.PropertyDescriptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.el.MethodExpression;

import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.MetadataTarget;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;

import org.apache.myfaces.trinidad.util.ClassLoaderUtils;

public class ListenersTagRule extends MetaRule {

    public static final MetaRule INSTANCE = new ListenersTagRule();

    private ListenersTagRule() {
    }

    @Override
    public Metadata applyRule(String attrName, TagAttribute faceletsTagAttr, MetadataTarget target) {
        Class cls = target.getPropertyType(attrName);
        if (!(MethodExpression.class.isAssignableFrom(cls) && attrName.endsWith("Listener"))) {
            return null; // component property must be type MethodExpression and its name end with Listener
        }
        // get setter method on JSF component
        Method jsfSetter = target.getWriteMethod(attrName);
        if (jsfSetter == null) {
            return null;
        }
        // look for getFooListeners() property
        PropertyDescriptor listeners = target.getProperty(attrName + "s");
        if (listeners == null) {
            return null;
        }
        // it should return an array of FooListener objects
        Class<?> arrayType = listeners.getPropertyType();
        if (!arrayType.isArray()) {
            return null;
        }
        Class<?> listenerClass = arrayType.getComponentType();
        // when processing FooListener, try to load FooEvent class for listener method
        Class<?> eventClass = getEventClass(listenerClass);
        if (eventClass == null) {
            return null;
        }
        // return MetaData that can map Facelets tag attribute to JSF component
        return new ListenerMethodExpressionPropertyMetadata(jsfSetter, faceletsTagAttr, new Class[] { eventClass });
    }

    private static Class<?> getEventClass(Class<?> listenerClass) {
        String listenerName = listenerClass.getName();
        if (!listenerName.endsWith("Listener")) {
            return null;
        }
        String eventName = (listenerName.substring(0, listenerName.length() - "Listener".length()) + "Event");
        ClassLoader loader = ClassLoaderUtils.getContextClassLoader();
        try {
            return Class.forName(eventName, true, loader);
        } catch (ClassNotFoundException cnfe) {
            return null;
        }
    }

    private static class ListenerMethodExpressionPropertyMetadata extends Metadata {
        private final Method jsfSetter;
        private final TagAttribute faceletsTagAttr;
        private Class[] argTypes;

        public ListenerMethodExpressionPropertyMetadata(Method jsfSetter, TagAttribute faceletsTagAttr,
                                                        Class[] argTypes) {
            this.jsfSetter = jsfSetter;
            this.faceletsTagAttr = faceletsTagAttr;
            this.argTypes = argTypes;
        }

        @Override
        public void applyMetadata(FaceletContext ctx, Object jsfInstance) {
            // create MethodExpression from facelets tag attribute value, eg #{bean.handleEvent}
            Class<?> expectedReturnType = null;
            MethodExpression expression = faceletsTagAttr.getMethodExpression(ctx, expectedReturnType, argTypes);
            // invoke setter on JSF component class with the created MethodExpression
            try {
                jsfSetter.invoke(jsfInstance, new Object[] { expression });
            } catch (InvocationTargetException e) {
                throw new TagAttributeException(faceletsTagAttr, e.getCause());
            } catch (Exception e) {
                throw new TagAttributeException(faceletsTagAttr, e);
            }
        }
    }

}

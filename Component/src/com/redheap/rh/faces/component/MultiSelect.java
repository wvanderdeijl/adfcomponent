package com.redheap.rh.faces.component;

import com.redheap.rh.faces.event.ItemDeleteEvent;
import com.redheap.rh.faces.event.ItemSelectEvent;

import javax.el.MethodExpression;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

import oracle.adf.share.logging.ADFLogger;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.bean.PropertyKey;
import org.apache.myfaces.trinidad.component.UIXSelectOrder;

public class MultiSelect extends UIXSelectOrder {

    static public final FacesBean.Type TYPE = new FacesBean.Type(UIXSelectOrder.TYPE);

    //Required properties by the RichRenderer.    
    static public final PropertyKey INLINE_STYLE_KEY = TYPE.registerKey("inlineStyle", String.class);
    static public final PropertyKey STYLE_CLASS_KEY = TYPE.registerKey("styleClass", String.class);
    static public final PropertyKey SHORT_DESC_KEY = TYPE.registerKey("shortDesc", String.class);
    static public final PropertyKey PARTIAL_TRIGGERS_KEY = TYPE.registerKey("partialTriggers", String.class);
    static public final PropertyKey VISIBLE_KEY = TYPE.registerKey("visible", Boolean.class, Boolean.TRUE);
    static public final PropertyKey CLIENT_COMPONENT_KEY = TYPE.registerKey("clientComponent", String.class);
    static public final PropertyKey CLIENT_ATTRIBUTE_KEY = TYPE.registerKey("clientAttributesKey", String.class);
    static public final PropertyKey CLIENT_LISTENER_KEY = TYPE.registerKey("clientListenersKey", String.class);
    static public final PropertyKey UNSECURE_KEY = TYPE.registerKey("unsecureKey", String.class);

    //Define the properties on the component.
    //    static public final PropertyKey AUTO_SUBMIT_KEY = TYPE.registerKey("autoSubmit", String.class);
    //    static public final PropertyKey ATTR_CHANGE_LIST_KEY = TYPE.registerKey("attributeChangeListener ", String.class);
    //    static public final PropertyKey BINDING_KEY = TYPE.registerKey("binding", String.class);
    //    static public final PropertyKey LABEL_KEY = TYPE.registerKey("label", String.class);
    //    static public final PropertyKey LABEL_ACCESS_KEY = TYPE.registerKey("labelAndAccessKey", String.class);
    //    static public final PropertyKey LABEL_STYLE_KEY = TYPE.registerKey("labelStyle", String.class);
    //    static public final PropertyKey READ_ONLY_KEY = TYPE.registerKey("readOnly", String.class);
    //    static public final PropertyKey REQUIRED_KEY = TYPE.registerKey("required", String.class);
    //    static public final PropertyKey RENDERED_KEY = TYPE.registerKey("rendered", String.class);
    //    static public final PropertyKey CHANGED_KEY = TYPE.registerKey("changed", String.class);
    //    static public final PropertyKey CHANGED_DESC_KEY = TYPE.registerKey("changedDesc", String.class);
    //    static public final PropertyKey CONTENT_STYLE_KEY = TYPE.registerKey("contentStyle", String.class);
    //    static public final PropertyKey CONVERTER_KEY = TYPE.registerKey("converter", String.class);
    //    static public final PropertyKey DISABLED_KEY = TYPE.registerKey("disabled", String.class);
    //    static public final PropertyKey VALIDATOR_KEY = TYPE.registerKey("validator", String.class);
    //    static public final PropertyKey VAL_CHANGE_LIST_KEY = TYPE.registerKey("valueChangeListener", String.class);
    static public final PropertyKey ITEM_SELECT_KEY = TYPE.registerKey("itemSelectListener", MethodExpression.class);
    static public final PropertyKey ITEM_DELETE_KEY = TYPE.registerKey("itemDeleteListener", MethodExpression.class);

    private static final ADFLogger logger = ADFLogger.createADFLogger(MultiSelect.class);

    public MultiSelect(String rendererType) {
        super(rendererType);
        logger.fine("created MultiSelect with renderer-type {0}", rendererType);
    }

    public MultiSelect() {
        super();
        logger.fine("created MultiSelect with default renderer-type");
    }

    @Override
    protected FacesBean.Type getBeanType() {
        return TYPE;
    }

    public void setItemSelectListener(MethodExpression input) {
        setProperty(ITEM_SELECT_KEY, input);
    }

    public MethodExpression getItemSelectListener() {
        return (MethodExpression) getProperty(ITEM_SELECT_KEY);
    }

    public void setItemDeleteListener(MethodExpression input) {
        setProperty(ITEM_DELETE_KEY, input);
    }

    public MethodExpression getItemDeleteListener() {
        return (MethodExpression) getProperty(ITEM_DELETE_KEY);
    }

    /**
     * @param event faces event
     * @throws AbortProcessingException exception during processing
     */
    @Override
    public void broadcast(FacesEvent event) throws AbortProcessingException {
        logger.fine("MultiSelect broadcasting event {0}", event);
        // notify the bound TagSelectListener
        if (event instanceof ItemSelectEvent) {
            // utility method found in UIXComponentBase for invoking method event expressions
            broadcastToMethodExpression((ItemSelectEvent) event, getItemSelectListener());
        }
        if (event instanceof ItemDeleteEvent) {
            // utility method found in UIXComponentBase for invoking method event expressions
            broadcastToMethodExpression((ItemDeleteEvent) event, getItemDeleteListener());
        }
        super.broadcast(event);
    }
}

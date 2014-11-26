package com.redheap.rh.faces.component;

import com.redheap.rh.faces.event.ItemSelectEvent;
import com.redheap.rh.faces.event.ItemSelectListener;

import javax.el.MethodExpression;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.bean.PropertyKey;
import org.apache.myfaces.trinidad.component.UIXEditableValue;

// extend UIXComponentBasem, UIXValue, UIXEditableValue or UIXCommand
public class MultiSelect extends UIXEditableValue {

    public static final FacesBean.Type TYPE = new FacesBean.Type(UIXEditableValue.TYPE);

    // WARNING: Each property must have the correct setters and getters. The RedheapComponentHandler
    // and its super classes use reflection to see if the component class can receive these properties
    // If they can't find an appropriate setter they will simply store the attribute as a string in
    // the FacesBean. This leads to issues with all non-string attributes (like listeners, string arrays,
    // or other non-string fields)
    public static final PropertyKey ITEM_SELECT_KEY = TYPE.registerKey("itemSelectListener", MethodExpression.class);

    // required properties by the RichRenderer.
    static public final PropertyKey INLINE_STYLE_KEY = TYPE.registerKey("inlineStyle", String.class);
    static public final PropertyKey STYLE_CLASS_KEY = TYPE.registerKey("styleClass", String.class);
    static public final PropertyKey SHORT_DESC_KEY = TYPE.registerKey("shortDesc", String.class);
    static public final PropertyKey PARTIAL_TRIGGERS_KEY = TYPE.registerKey("partialTriggers", String[].class);
    static public final PropertyKey VISIBLE_KEY = TYPE.registerKey("visible", Boolean.class, Boolean.TRUE);
    static public final PropertyKey CLIENT_COMPONENT_KEY = TYPE.registerKey("clientComponent", String.class);
    static public final PropertyKey CLIENT_ATTRIBUTE_KEY = TYPE.registerKey("clientAttributesKey", String.class);
    static public final PropertyKey CLIENT_LISTENER_KEY = TYPE.registerKey("clientListenersKey", String.class);
    static public final PropertyKey UNSECURE_KEY = TYPE.registerKey("unsecureKey", String.class);

    private static final String DFLT_RENDERER_TYPE = "com.redheap.rh.rendertype.MultiSelect";

    public MultiSelect() {
        super(DFLT_RENDERER_TYPE);
    }

    public MultiSelect(String rendererType) {
        super(rendererType);
    }

    @Override
    protected FacesBean.Type getBeanType() {
        return TYPE;
    }

    @Override
    public String getFamily() {
        return "com.redheap.rh.componentfamily.MultiSelect";
    }

    public void setItemSelectListener(MethodExpression input) {
        setProperty(ITEM_SELECT_KEY, input);
    }

    public MethodExpression getItemSelectListener() {
        return (MethodExpression) getProperty(ITEM_SELECT_KEY);
    }

    public ItemSelectListener[] getItemSelectListeners() {
        return (ItemSelectListener[]) getFacesListeners(ItemSelectListener.class);
    }

    public void addItemSelectListener(ItemSelectListener listener) {
        addFacesListener(listener);
    }

    public void removeItemSelectListener(ItemSelectListener listener) {
        removeFacesListener(listener);
    }

    public String[] getPartialTriggers() {
        return (String[]) getProperty(PARTIAL_TRIGGERS_KEY);
    }

    public void setPartialTriggers(String[] partialTriggers) {
        setProperty(PARTIAL_TRIGGERS_KEY, (partialTriggers));
    }

    // RichComponentHandler.createMetaRuleset and its super class TrinidadComponentHandler.createMetaRuleset
    // seem to process all tag attributes from the facelets page and try to map it to setters/getters in the
    // component classes (like this one)
    // see RichListenersTagRule.applyRule for when a rich faces listener attribute is recognized:
    // - attribute name has to end with "Listener"
    // - component class has to have method setFooListener accepting a MethodExpression that will be invoked when
    //   everything is okay
    // - component class has to have method getFooListeners() that returns array of FooListener[]
    //   (other component class also have addFooListener and removeFooListener, probably for multiple listener support)
    // - full name of FooListener, has to start with oracle.adf.view.rich. (package)
    // - a class FooEvent has to exist (replace Listener with Even in class name - so also same package)
    // if all that is okay RichListenersTagRule.applyRule will return instance of private class
    // ListenerMEPropertyMetadata extends Metadata

    /**
     * Invoked by each FacesEvent that gets queued for this component. We also use this
     * to enforce the proper JSF Lifecycle Phase to execute the event.
     */
    @Override
    public void queueEvent(FacesEvent event) {
        if (event instanceof ItemSelectEvent && event.getSource() == this) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
        }
        super.queueEvent(event);
    }

    /**
     * Broadcast event to appropriate listeners. Invoked by the ViewRoot in the
     * appropriate JSF Lifecycle Phase to execute the previously queued event.
     */
    @Override
    public void broadcast(FacesEvent event) throws AbortProcessingException {
        if (event instanceof ItemSelectEvent) {
            // utility method found in UIXComponentBase for invoking method event expressions
            broadcastToMethodExpression(event, getItemSelectListener());
        }
        super.broadcast(event);
    }

}
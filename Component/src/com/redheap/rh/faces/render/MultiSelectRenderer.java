package com.redheap.rh.faces.render;

import com.redheap.rh.faces.component.MultiSelect;
import com.redheap.rh.faces.event.ItemSelectEvent;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;

import oracle.adf.view.rich.render.ClientComponent;
import oracle.adf.view.rich.render.ClientEvent;
import oracle.adf.view.rich.render.ClientMetadata;
import oracle.adf.view.rich.render.RichRenderer;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.bean.PropertyKey;
import org.apache.myfaces.trinidad.context.RenderingContext;

/**
 * Renders a MultiSelect component as an ADF Rich Client component.
 * <p>
 * You should really look at the source code and javadoc of oracle.adf.view.rich.render.RichRenderer
 * when creating a custom Rich Client renderer as there are a number of methods you most likely
 * need or want to override. For components that contain an (editable) value you should subclass
 * oracle.adfinternal.view.faces.renderkit.rich.EditableValueRenderer or create similar
 * functionality if you don't want to extend an adfinternal class.
 * <p>
 * For this example we have looked at oracle.adfinternal.view.faces.renderkit.rich.CalendarRenderer
 * for inspiration as it is a rather complex and a complex renderer which demonstrates a lot of
 * the renderer features.
 */
public class MultiSelectRenderer extends RichRenderer {

    private PropertyKey _value;

    /**
     * Default no-arg constructor.
     * Will use {@link MultiSelect#TYPE} to describe all possible properties.
     */
    public MultiSelectRenderer() {
        super(MultiSelect.TYPE);
    }

    /**
     * Constructor to use a specific Type.
     * Normally the no-arg constructor should be used
     * @param type FacesBean.Type describing all possible component properties.
     */
    public MultiSelectRenderer(FacesBean.Type type) {
        super(type);
    }

    /**
     * Perform the encoding (html generation) of the component. This should also invoke encodeChild() or
     * encodeAllChildren() to ensure any children are rendered.
     * @param facesContext FacesContext
     * @param renderCtx Context information about the rendering process such as if this a PPR request
     * @param component JSF component instance we are rendering
     * @param clientComponent represents client-side component instances and can be used to add property values to
     *                        the client side component
     * @param facesBean FacesBean with all the component state
     * @throws IOException
     */
    @Override
    protected void encodeAll(FacesContext facesContext, RenderingContext renderCtx, UIComponent component,
                             ClientComponent clientComponent, FacesBean facesBean) throws IOException {
        ResponseWriter rw = facesContext.getResponseWriter();

        // outer most html element is marked as being the primary element for visual editor in JDev
        rw.startElement("div", component); //rootElement so add to the multiSelect UIComponent.
        // have superclass render the client ID and default styling
        renderId(facesContext, component, clientComponent);
        renderAllRootAttributes(facesContext, renderCtx, clientComponent, facesBean);

        // render an unnumbered list with all the items
        rw.startElement("ul", null);
        renderStyleClass(facesContext, renderCtx, "rh|multiSelect::content");

        @SuppressWarnings("unchecked")
        List value = (List) facesBean.getProperty(_value);
        int[] clientKeys = new int[value.size()];
        for (int i = 0, n = value.size(); i < n; i++) {
            encodeItem(facesContext, renderCtx, value.get(i), i);
            clientKeys[i] = i;
        }
        rw.endElement("ul"); // close unnumbered list

        // render hidden input field with value (indices)
        rw.startElement("input", null);
        rw.writeAttribute("type", "hidden", null);
        String inputId = component.getClientId(facesContext) + ":value";
        rw.writeAttribute("id", inputId, null);
        rw.writeAttribute("name", inputId, null);
        rw.writeAttribute("value", Arrays.toString(clientKeys), null);
        rw.endElement("input");

        rw.endElement("div"); // close root div
    }

    /**
     * Render a single item in the multi select component
     * @param facesContext FacesContext
     * @param renderCtx Context information about the rendering process such as if this a PPR request
     * @param item item to render
     * @param index index of the given item in de multi select component
     * @throws IOException
     */
    protected void encodeItem(FacesContext facesContext, RenderingContext renderCtx, Object item,
                              int index) throws IOException {
        ResponseWriter rw = facesContext.getResponseWriter();
        String s = item == null ? "" : item.toString();
        rw.startElement("li", null);
        renderStyleClass(facesContext, renderCtx, "rh|multiSelect::item");
        rw.writeAttribute("title", s, null);
        rw.writeAttribute("rh-li", index, null);
        rw.startElement("button", null);
        renderStyleClass(facesContext, renderCtx, "rh|multiSelect::item-content");
        rw.writeAttribute("type", "button", null);
        rw.writeAttribute("tabindex", "-1", null);
        rw.startElement("span", null);
        rw.writeText(s, null);
        rw.endElement("span");
        rw.endElement("button");
        rw.startElement("span", null);
        rw.writeAttribute("title", "Remove", null);
        renderStyleClass(facesContext, renderCtx, "rh|multiSelect::item-delete");
        rw.endElement("span");
        rw.endElement("li");
    }

    /**
     * Overriden to return the default style class for this renderer. This is used by other methods in the
     * super class to determine our css skin selector to retrieve skin properties like -tr-enable-themes.
     * @param context FacesContext
     * @param renderCtx Context information about the rendering process such as if this a PPR request
     * @param bean FacesBean with all the component state
     * @return "rh|multiSelect"
     */
    @Override
    protected String getDefaultStyleClass(FacesContext context, RenderingContext renderCtx, FacesBean bean) {
        return "rh|multiSelect";
    }

    /**
     * Provide the name of the client-side javascript object representing this renderer.
     * HINT: if you do not have a client-side object, you should not be subclassing RichRenderer!
     * Subclass the Trinidad CoreRenderer class, or the ordinary JSF Renderer class.
     * @return "RhMultiSelect"
     */
    @Override
    protected String getClientConstructor() {
        return "RhMultiSelect";
    }

    /**
     * Decode the incoming request and check if there are any values or events in the request for our component.
     * @param context FacesContext
     * @param component JSF component instance
     * @param clientId fully qualified ID of the client component which is needed to find its specific values in the
     * http request parameters.
     */
    @Override
    public void decodeInternal(FacesContext context, UIComponent component, String clientId) {
        super.decodeInternal(context, component, clientId);
        String hiddenInputId = clientId + ":value";
        Map<String, String> request = context.getExternalContext().getRequestParameterMap();
        String submittedValue = request.get(hiddenInputId);
        if (submittedValue != null && component instanceof EditableValueHolder) {
            EditableValueHolder evh = (EditableValueHolder) component;
            evh.setSubmittedValue(submittedValue);
        }
        // see if any of our ClientEvents are in the request. If so, queue proper server side FacesEvent
        ClientEvent selectEvent = getClientEvent(context, clientId, "itemSelect");
        if (selectEvent != null) {
            new ItemSelectEvent(component, (String) selectEvent.getParameters().get("item")).queue();
        }
    }

    @Override
    public Object getConvertedValue(FacesContext facesContext, UIComponent component,
                                    Object submittedValue) throws ConverterException {
        // convert the submitted string from the client to List as understood by model
        EditableValueHolder evh = (EditableValueHolder) component;
        List<String> modelValues = new ArrayList<String>((List<String>) evh.getValue());
        int[] keepIndices = keepIndices((String) submittedValue);
        List<String> newValues = new ArrayList<String>(keepIndices.length);
        for (int i : keepIndices) {
            newValues.add(modelValues.get(i));
        }
        return newValues;
    }

    private int[] keepIndices(String submittedValue) {
        String[] strIndices = submittedValue.replace("[", "").replace("]", "").split(",");
        if (strIndices.length == 1 && strIndices[0].trim().isEmpty()) {
            return new int[0];
        }
        int[] retval = new int[strIndices.length];
        for (int i = 0, n = strIndices.length; i < n; i++) {
            retval[i] = Integer.parseInt(strIndices[i].trim());
        }
        return retval;
    }

    /**
     * Override to find all necessary PropertyKeys in the given FacesBean.Type so we can easily access
     * these during rendering and adds any properties that are needed on the client side to the metadata so
     * they are sent to the client.
     * @param type FacesBean.Type describing all the component properties
     * @param metadata ClientMetadata to register properties that should or can be sent to the client
     */
    @Override
    protected void findTypeConstants(FacesBean.Type type, ClientMetadata metadata) {
        super.findTypeConstants(type, metadata);
        // find PropertyKeys we need during rendering
        _value = type.findKey("value");
        // use following methods on metadata to register client side properties:
        // .addIfComponentProperty for properties that should be sent if there will be a client component
        // .addPersistedProperty for properties that must be persisted using change manager (session or persistent MDS)
        //                       during attribute synch from client
        // .addRequiredProperty will force instantiation of a client component as they have to be present on the client
        // .addSecureProperty for properties that cannot be changed at the client and should not be synched back to server
    }

}

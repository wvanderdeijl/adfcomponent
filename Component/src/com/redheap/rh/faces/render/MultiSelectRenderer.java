package com.redheap.rh.faces.render;

import com.redheap.rh.faces.component.MultiSelect;
import com.redheap.rh.faces.event.ItemSelectEvent;

import com.redheap.rh.faces.event.ItemSelectEvent;

import java.io.IOException;

import java.util.List;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import oracle.adf.view.rich.render.ClientComponent;
import oracle.adf.view.rich.render.ClientEvent;
import oracle.adf.view.rich.render.ClientMetadata;
import oracle.adf.view.rich.render.RichRenderer;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.bean.PropertyKey;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class MultiSelectRenderer extends RichRenderer {

    private PropertyKey _value;

    public MultiSelectRenderer() {
        this(MultiSelect.TYPE);
    }

    public MultiSelectRenderer(FacesBean.Type type) {
        super(type);
    }

    @Override
    protected void encodeAll(FacesContext facesContext, RenderingContext renderingContext, UIComponent component,
                             ClientComponent clientComponent, FacesBean facesBean) throws IOException {
        //SelectManyChoiceRenderer
        ResponseWriter rw = facesContext.getResponseWriter();
        rw.startElement("div", component); //rootElement so add to the multiSelect UIComponent.

        renderId(facesContext, component, clientComponent);
        renderAllRootAttributes(facesContext, renderingContext, clientComponent, facesBean);
        rw.startElement("ul", null);
        RichRenderer.renderStyleClass(facesContext, renderingContext, "rh|multiSelect::content");

        List<String> value = (List<String>) facesBean.getProperty(_value);
        for (int i = 0; i < value.size(); i++) {
            String title = value.get(i);

            rw.startElement("li", null);
            RichRenderer.renderStyleClass(facesContext, renderingContext, "rh|multiSelect::item");
            rw.writeAttribute("title", title, null);
            rw.writeAttribute("rh-li", i, null);
            rw.startElement("button", null);
            RichRenderer.renderStyleClass(facesContext, renderingContext, "rh|multiSelect::item-content");
            rw.writeAttribute("type", "button", null);
            rw.writeAttribute("tabindex", "-1", null);
            rw.startElement("span", null);
            rw.writeText(title, null);
            rw.endElement("span");
            rw.endElement("button");
            rw.startElement("span", null);
            rw.writeAttribute("title", "Remove", null);
            RichRenderer.renderStyleClass(facesContext, renderingContext, "rh|multiSelect::item-delete");
            rw.endElement("span");
            rw.endElement("li");
        }

        rw.endElement("ul");
        rw.endElement("div");
    }

    @Override
    protected String getDefaultStyleClass(FacesContext context, RenderingContext arc, FacesBean bean) {
        return "rh|multiSelect";
    }

    @Override
    protected String getClientConstructor() {
        return "RhMultiSelect";
    }

    @Override
    public void decodeInternal(FacesContext context, UIComponent component, String clientId) {
        super.decodeInternal(context, component, clientId);
        // see if any of our ClientEvents are in the request. If so, queue proper server side FacesEvent
        ClientEvent selectEvent = getClientEvent(context, clientId, "itemSelect");
        if (selectEvent != null) {
            new ItemSelectEvent(component, (String) selectEvent.getParameters().get("item")).queue();
        }
    }

    @Override
    protected void findTypeConstants(FacesBean.Type type, ClientMetadata metadata) {
        _value = type.findKey("value");
        super.findTypeConstants(type, metadata);
    }
}

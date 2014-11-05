package com.redheap.rh.faces.render;

import com.redheap.rh.faces.component.MultiSelect;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import javax.faces.context.ResponseWriter;

import oracle.adf.view.rich.render.ClientComponent;
import oracle.adf.view.rich.render.RichRenderer;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class MultiSelectRenderer extends RichRenderer {
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
        MultiSelect multiSelect = (MultiSelect) component;
        ResponseWriter rw = facesContext.getResponseWriter();
        rw.startElement("div", multiSelect); //rootElement so add to the multiSelect.
        rw.writeAttribute("class", renderingContext.getStyleClass("rh|multiSelect") + " " + multiSelect.getStyleClass(),
                          "styleClass");
        rw.writeAttribute("id", multiSelect.getClientId(facesContext), "id");
        rw.startElement("ul", null);
        rw.writeAttribute("class", renderingContext.getStyleClass("rh|multiSelect::content"), null);
        rw.startElement("li", null);
        rw.writeAttribute("class", renderingContext.getStyleClass("rh|multiSelect::item"), null);
        rw.writeAttribute("title", "Salary between 10 and 100", null);
        rw.startElement("button", null);
        rw.writeAttribute("class", renderingContext.getStyleClass("rh|multiSelect::item-content"), null);
        rw.writeAttribute("type", "button", null);
        rw.writeAttribute("tabindex", "-1", null);
        rw.startElement("span", null);        
        rw.writeText("Salary between 10 and 100", null);
        rw.endElement("span");
        rw.endElement("button");
        rw.startElement("span", null);
        rw.writeAttribute("title", "Remove", null);
        rw.writeAttribute("class", renderingContext.getStyleClass("rh|multiSelect::item-delete"), null);
        rw.endElement("span");
        rw.endElement("li");
        rw.endElement("ul");
        rw.endElement("div");
    }

    @Override
    protected String getClientConstructor() {
        return "RhMultiSelect";
    }
}

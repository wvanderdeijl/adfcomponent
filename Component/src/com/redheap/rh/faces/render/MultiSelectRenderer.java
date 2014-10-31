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
    protected void encodeAll(FacesContext facesContext, RenderingContext renderingContext, UIComponent multiSelect,
                             ClientComponent clientComponent, FacesBean facesBean) throws IOException {
        //SelectManyChoiceRenderer
        ResponseWriter rw = facesContext.getResponseWriter();
        rw.startElement("div", multiSelect); //rootElement so add to the multiSelect.
        rw.writeAttribute("id", multiSelect.getClientId(facesContext), "id");
        rw.startElement("div", null);
        rw.writeText("Hello World!", null);
        rw.endElement("div");
        rw.endElement("div");
    }

    @Override
    protected String getClientConstructor() {
        return "RhMultiSelect";
    }
}

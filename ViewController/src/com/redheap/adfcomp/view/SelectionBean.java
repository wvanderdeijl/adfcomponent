package com.redheap.adfcomp.view;

import com.redheap.rh.faces.event.ItemSelectEvent;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;

public class SelectionBean {
    private String selectedItem;
    private String text = "initialText";

    public SelectionBean() {
        super();
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void processItemSelect(ItemSelectEvent event) throws AbortProcessingException {
        System.out.println("**************");
        System.out.println(FacesContext.getCurrentInstance().getCurrentPhaseId());
        System.out.println(event);
        System.out.println(event.getItem());
        System.out.println(event.getComponent());
        System.out.println(event.getPhaseId());
        System.out.println(event.getSource());
        System.out.println("**************");
        setSelectedItem(event.getItem());
    }
}

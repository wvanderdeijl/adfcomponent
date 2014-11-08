package com.redheap.adfcomp.view;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;

import oracle.adf.view.rich.com.redheap.rh.faces.event.ItemSelectEvent;
import oracle.adf.view.rich.com.redheap.rh.faces.event.ItemSelectListener;

public class SelectionBean implements ItemSelectListener {

    public SelectionBean() {
        super();
    }

    @Override
    public void processItemSelect(ItemSelectEvent event) throws AbortProcessingException {
        System.out.println("**************");
        System.out.println(FacesContext.getCurrentInstance().getCurrentPhaseId());
        System.out.println(event);
        System.out.println(event.getItem());
        System.out.println(event.getComponent());
        System.out.println(event.getPhaseId());
        System.out.println(event.getSource());
    }

    public void calendarDisplayChanged(oracle.adf.view.rich.event.CalendarDisplayChangeEvent event) {
        System.out.println(event);
    }
}

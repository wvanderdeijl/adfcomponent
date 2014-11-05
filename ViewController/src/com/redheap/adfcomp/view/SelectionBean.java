package com.redheap.adfcomp.view;

import com.redheap.rh.faces.event.ItemSelectEvent;
import com.redheap.rh.faces.event.ItemSelectListener;

import javax.faces.event.AbortProcessingException;

public class SelectionBean implements ItemSelectListener {

    public SelectionBean() {
        super();
    }

    @Override
    public void processItemSelect(ItemSelectEvent event) throws AbortProcessingException {
        System.out.println("**************");
        System.out.println(event);
        System.out.println(event.getItem());
        System.out.println(event.getComponent());
        System.out.println(event.getPhaseId());
        System.out.println(event.getSource());
    }
}

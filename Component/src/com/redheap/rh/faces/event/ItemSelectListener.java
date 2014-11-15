package com.redheap.rh.faces.event;


import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesListener;


public interface ItemSelectListener extends FacesListener {

    /**
     * <p>Process the {@link TagSelectEvent}.</p>
     * @param event fired on click of a tag link
     * @throws AbortProcessingException error processing {@link TagSelectEvent}
     */
    public void processItemSelect(ItemSelectEvent event) throws AbortProcessingException;

}

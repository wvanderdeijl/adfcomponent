package com.redheap.rh.faces.event;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesListener;

@Deprecated
public interface ItemDeleteListener extends FacesListener {

    //FIXME: PlaceHolder code from the Oracle Example.

    /**
     * <p>Process the {@link TagSelectEvent}.</p>
     * @param event fired on click of a tag link
     * @throws AbortProcessingException error processing {@link TagSelectEvent}
     */
    public void processItemDelete(ItemDeleteEvent event) throws AbortProcessingException;

}

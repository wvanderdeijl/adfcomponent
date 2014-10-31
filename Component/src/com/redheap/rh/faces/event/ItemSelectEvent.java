package com.redheap.rh.faces.event;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

public class ItemSelectEvent extends FacesEvent {
//FIXME: Placeholder code from the Oracle Example.

    /**
      * <p>Tag selected on the client.</p>
      */
     private String tag = null;
     /**
      * <p>Overloade constructor passing the <code>source</code> 
      * {@link oracle.adfdemo.acme.faces.component.TagPane} component and the 
      * selected <code>tag</code>.
      * </p>
      * @param source component firing the event
      * @param tag selected tag link type
      */
     public ItemSelectEvent(UIComponent source, 
                String tag)
     {
      super(source);
      this.tag = tag;
     }

     /**
      * <p>Returns <code>true</code> if the <code>facesListener</code> is a
      * {@link TagSelectListener}.</p>
      * 
      * @param facesListener listener to be evaluated
      * @return <code>true</code> 
      * if <code>facesListener</code> instancof {@link TagSelectListener}
      */
     public boolean isAppropriateListener(FacesListener facesListener)
     {
      return (facesListener instanceof ItemSelectListener);
     }
     /**
      * <p>Delegates to the <code>processTagSelect</code>
      * method of a <code>FacesListener</code>
      * implementing the {@link TagSelectListener} interface.
      * 
      * @param facesListener target listener realizing {@link TagSelectListener}
      */
     public void processListener(FacesListener facesListener)
     {
      ((ItemSelectListener) facesListener).processItemSelect(this);
     }
     /**
      * @return the tag that was selected triggering this event
      */
     public String getTag()
     {
      return tag;
     }
}

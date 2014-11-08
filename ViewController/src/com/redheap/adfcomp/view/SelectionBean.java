package com.redheap.adfcomp.view;

<<<<<<< .merge_file_a06020
import com.redheap.rh.faces.event.ItemSelectEvent;
import com.redheap.rh.faces.event.ItemSelectListener;

import javax.faces.event.AbortProcessingException;

=======
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;

import oracle.adf.view.rich.com.redheap.rh.faces.event.ItemSelectEvent;
import oracle.adf.view.rich.com.redheap.rh.faces.event.ItemSelectListener;

>>>>>>> .merge_file_a06856
public class SelectionBean implements ItemSelectListener {

    public SelectionBean() {
        super();
    }

    @Override
    public void processItemSelect(ItemSelectEvent event) throws AbortProcessingException {
        System.out.println("**************");
<<<<<<< .merge_file_a06020
=======
        System.out.println(FacesContext.getCurrentInstance().getCurrentPhaseId());
>>>>>>> .merge_file_a06856
        System.out.println(event);
        System.out.println(event.getItem());
        System.out.println(event.getComponent());
        System.out.println(event.getPhaseId());
        System.out.println(event.getSource());
    }
<<<<<<< .merge_file_a06020
=======

    public void calendarDisplayChanged(oracle.adf.view.rich.event.CalendarDisplayChangeEvent event) {
        System.out.println(event);
    }
>>>>>>> .merge_file_a06856
}

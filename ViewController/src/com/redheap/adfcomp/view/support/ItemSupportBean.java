package com.redheap.adfcomp.view.support;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

public class ItemSupportBean {
    public ItemSupportBean() {
        itemList.add("Hello World!");
        itemList.add("Next Item!");
        itemList.add("Item Three");
    }

    private List<String> itemList = new ArrayList<String>();
    private String newItem;

    public void setNewItem(String newItem) {
        this.newItem = newItem;
    }

    public String getNewItem() {
        return newItem;
    }

    public void setItemList(List<String> itemList) {
        this.itemList = itemList;
    }

    public List<String> getItemList() {
        return itemList;
    }


    public void addItem(ActionEvent actionEvent) {
        if (newItem == null) {
            return;
        }
        itemList.add(newItem);
        newItem = null;
    }
}

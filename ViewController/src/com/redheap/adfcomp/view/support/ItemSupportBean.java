package com.redheap.adfcomp.view.support;

import java.util.ArrayList;
import java.util.List;

public class ItemSupportBean {
    public ItemSupportBean() {
        itemList.add("Hello World!");
        itemList.add("Next Item!");
        itemList.add("Item Three");
    }
  
    private List<String> itemList = new ArrayList<String>();

    public void setItemList(List<String> itemList) {
        this.itemList = itemList;
    }

    public List<String> getItemList() {
        return itemList;
    }
}

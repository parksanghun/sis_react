package com.sist.news;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

public class Channel {
    private List<Item> item = new ArrayList<Item>();

    public List<Item> getItem() {
        return item;
    }

    @XmlElement
    public void setItem(List<Item> item) {
        this.item = item;
    }
}

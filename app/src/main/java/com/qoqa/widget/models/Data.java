
package com.qoqa.widget.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Utilisation de la librerie gson
 * @version 0.1.0
 * @author Rafael Jimenez
 */
public class Data {

    @SerializedName("items")
    @Expose
    private List<Item> items = new ArrayList<Item>();
    @SerializedName("qdefis")
    @Expose
    private Qdefis qdefis;

    /**
     * 
     * @return
     *     The items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * 
     * @param items
     *     The items
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * 
     * @return
     *     The qdefis
     */
    public Qdefis getQdefis() {
        return qdefis;
    }

    /**
     * 
     * @param qdefis
     *     The qdefis
     */
    public void setQdefis(Qdefis qdefis) {
        this.qdefis = qdefis;
    }

}


package com.qoqa.widget.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Utilisation de la librerie gson
 * @version 0.1.0
 * @author Rafael Jimenez
 */
public class Item {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("deal")
    @Expose
    private Deal deal;

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The deal
     */
    public Deal getDeal() {
        return deal;
    }

    /**
     * 
     * @param deal
     *     The deal
     */
    public void setDeal(Deal deal) {
        this.deal = deal;
    }

}

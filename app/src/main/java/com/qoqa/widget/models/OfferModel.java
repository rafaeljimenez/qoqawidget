
package com.qoqa.widget.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Utilisation de la librairie gson
 * @version 0.1.0
 * @author Rafael Jimenez
 */
public class OfferModel {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("locale")
    @Expose
    private String locale;
    @SerializedName("maintenance")
    @Expose
    private Object maintenance;
    @SerializedName("shop")
    @Expose
    private String shop;
    @SerializedName("shop_id")
    @Expose
    private Integer shopId;
    @SerializedName("shop_affiliation")
    @Expose
    private Boolean shopAffiliation;
    @SerializedName("user")
    @Expose
    private Object user;

    /**
     * 
     * @return
     *     The data
     */
    public Data getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The locale
     */
    public String getLocale() {
        return locale;
    }

    /**
     * 
     * @param locale
     *     The locale
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     * 
     * @return
     *     The maintenance
     */
    public Object getMaintenance() {
        return maintenance;
    }

    /**
     * 
     * @param maintenance
     *     The maintenance
     */
    public void setMaintenance(Object maintenance) {
        this.maintenance = maintenance;
    }

    /**
     * 
     * @return
     *     The shop
     */
    public String getShop() {
        return shop;
    }

    /**
     * 
     * @param shop
     *     The shop
     */
    public void setShop(String shop) {
        this.shop = shop;
    }

    /**
     * 
     * @return
     *     The shopId
     */
    public Integer getShopId() {
        return shopId;
    }

    /**
     * 
     * @param shopId
     *     The shop_id
     */
    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    /**
     * 
     * @return
     *     The shopAffiliation
     */
    public Boolean getShopAffiliation() {
        return shopAffiliation;
    }

    /**
     * 
     * @param shopAffiliation
     *     The shop_affiliation
     */
    public void setShopAffiliation(Boolean shopAffiliation) {
        this.shopAffiliation = shopAffiliation;
    }

    /**
     * 
     * @return
     *     The user
     */
    public Object getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(Object user) {
        this.user = user;
    }

}

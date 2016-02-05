
package com.qoqa.widget.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Utilisation de la librerie gson
 * @version 0.1.0
 * @author Rafael Jimenez
 */
public class Image {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("o")
    @Expose
    private String o;
    @SerializedName("p")
    @Expose
    private String p;
    @SerializedName("t")
    @Expose
    private String t;
    @SerializedName("theme")
    @Expose
    private String theme;
    @SerializedName("various_width")
    @Expose
    private String variousWidth;

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The o
     */
    public String getO() {
        return o;
    }

    /**
     * 
     * @param o
     *     The o
     */
    public void setO(String o) {
        this.o = o;
    }

    /**
     * 
     * @return
     *     The p
     */
    public String getP() {
        return p;
    }

    /**
     * 
     * @param p
     *     The p
     */
    public void setP(String p) {
        this.p = p;
    }

    /**
     * 
     * @return
     *     The t
     */
    public String getT() {
        return t;
    }

    /**
     * 
     * @param t
     *     The t
     */
    public void setT(String t) {
        this.t = t;
    }

    /**
     * 
     * @return
     *     The theme
     */
    public String getTheme() {
        return theme;
    }

    /**
     * 
     * @param theme
     *     The theme
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * 
     * @return
     *     The variousWidth
     */
    public String getVariousWidth() {
        return variousWidth;
    }

    /**
     * 
     * @param variousWidth
     *     The various_width
     */
    public void setVariousWidth(String variousWidth) {
        this.variousWidth = variousWidth;
    }

}

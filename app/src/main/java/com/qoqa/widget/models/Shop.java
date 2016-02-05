package com.qoqa.widget.models;

/**
 * Ce modèle est utilisé pour la sauvegarde et
 * la récupération des shops dans la base de donnée
 *
 * @version 0.1.0
 * @author Rafael Jimenez
 */
public class Shop {


    private int id;
    private String name;
    private String domain;

    public Shop() {
        this.id = -1;
        this.name = "";
        this.domain = "";
    }
    public Shop(int id, String name, String domain) {
        this.id = id;
        this.name = name;
        this.domain = domain;
    }

    /**
     * @return
     *      The ID
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *     L'id du market
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * @return
     *      Le nom du market
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *      le nom du Market
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     *      Le nom de domaine
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @param domain
     *      Le nom de domaine
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }
}

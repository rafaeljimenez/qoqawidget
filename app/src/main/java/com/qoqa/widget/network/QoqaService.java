package com.qoqa.widget.network;


import com.qoqa.widget.models.OfferModel;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Cette classe va nous créer les routes vers l'API Qoqa
 *
 * @version 0.1.0
 * @author Rafael Jimenez
 */

public interface QoqaService {

    /** Méthode http GET pour récupérer les ShopsSummary */
    @GET("/api/v3/fr/shops/summary")
    Call<OfferModel> getShopsSummary();

}
 


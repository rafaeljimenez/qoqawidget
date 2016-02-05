package com.qoqa.widget.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.qoqa.widget.models.Item;
import com.qoqa.widget.models.OfferModel;
import com.qoqa.widget.utils.SharedPreferencesManager;
import com.qoqa.widget.views.widgets.WidgetProvider;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Ce cervice vas nous permettre de télécharger en arrière plan les information à afficher
 * dans le widget. Une fois ces informations reçues nous prévenons le widget pour qu'il puisse
 * réaliser l'action nécessaire
 *
 * @version 0.1.0
 * @author Rafael Jimenez
 */
public class RemoteFetchService extends Service {

    /** id du widget qui crée l'object*/
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    /** L'url du domain où l'on accédera à l'API*/
    private final String domainApiUrl = "http://qoqa.ch";




    /** Key qui sera afficher lors d'un "log" */
    private static final String KEY_LOG = "RemoteFetchService";
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    /**
     * Récupère appwidget_id de  l'"intent" il sera necesssaire pour actualiser le widget plus tard.
     * Puis récupères les shopsSumary
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Il est nécessaire qu'il existe "EXTRA_APPWIDGET_ID" si non nous n'aurons pas l'id du widget
        if (intent!=null && intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID)) {
            appWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            //récupérations des shopsSumary
            fetchDataFromShopsSumary();
            return super.onStartCommand(intent, flags, startId);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Cette méthode fait une requête à l'api qoqa pour récupérer les "ShopsSumary".
     * Une fois l'information reçues nous avertissons le widget qu'il peut réaliser l'action nécessaire
     */
    private void fetchDataFromShopsSumary() {
        // Création du service qui va nous permettre de dialoguer avec l'API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(domainApiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Préparation de l'appel
        QoqaService qoqaService = retrofit.create(QoqaService.class);

        //Nous récupérons le shopsSumary de l'api
        Call<OfferModel> call = qoqaService.getShopsSummary();
        call.enqueue(new Callback<OfferModel>() {

            @Override
            public void onResponse(Response<OfferModel> response, Retrofit retrofit) {
                OfferModel offerModel = response.body();

                //vérifie qu'on nous avons bien reçus des offres
                if (offerModel.getData().getItems().size() > 0) {
                    List<Item> listItemList = offerModel.getData().getItems();
                    //Enregistre la date actuelle qui sera la date de la dernière actualisation du widget
                    SharedPreferencesManager.setLastDateRefreshWidget(getApplicationContext());

                    //Enregistre le json contenant les offres
                    SharedPreferencesManager.setLastOffersDownloaded(getApplicationContext(), listItemList);

                    populateWidget();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //-- Dans la prochaine version il faudra gérer les erreurs en affichant un message dans notre widget--//
            }

        });

    }

    /**
     * Cette méthode envoie un broadcat au WidgetProvider
     * comme ça le widget reçois une notification pour faire l'action nécessaire
     * ici l'action est : action == WidgetProvider.DATA_FETCHED
     */
    private void populateWidget() {
        Intent widgetUpdateIntent = new Intent();
        widgetUpdateIntent.setAction(WidgetProvider.DATA_FETCHED);
        widgetUpdateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                appWidgetId);
        sendBroadcast(widgetUpdateIntent);

        //Arrête le service
        this.stopSelf();
    }
}
package com.qoqa.widget.views.widgets;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.qoqa.widget.R;
import com.qoqa.widget.network.RemoteFetchService;
import com.qoqa.widget.utils.SharedPreferencesManager;

/**
 * Cette classe nous permet de récupérer les différentes actions à gérer avec le Widget.
 * De la gestion des boutons à la réception des informations du serveur
 *
 * @version 0.1.1
 * @since 0.1.0
 * @author Rafael Jimenez
 */

public class WidgetProvider extends AppWidgetProvider {


    //------------ Les différentes actions à gérer -------------//

    // String to be sent on Broadcast as soon as Data is Fetched
    /** Le String qui sera envoyé dans le broadcast lorsque les information seront reçus du serveur*/
    public static final String DATA_FETCHED = "com.qoqa.widget.widgetqoqa.DATA_FETCHED";
    /** Action envoyée lorsque l'on clique sur une cellule de la liste*/
    public static final String CLICK_ACTION = "com.qoqa.widget.widgetqoqa.CLICK";
    /** Action envoyée lorsque l'on appuie sur le bouton refresh*/
    public static final String REFRESH_ACTION = "com.qoqa.widget.widgetqoqa.REFRESH";

    //----------- Les information complémentaires ajoutée aux actions -----------//

    /** Permet d'envoyer comme information l'url du site à afficher */
    public static final String EXTRA_SITE_URL = "com.qoqa.widget.widgetqoqa.SITE_URL";

    /**
     * Utilisation d'un boolean pour savoir si le widget est entrain d être actualisé.
     * J'ai renommé le "true" pour avoir une meilleure compréhension du code par la suite
     */
    private final boolean IS_REFRESHING = true;

    /**
     * Cette méthode est appelé chaque 30 min comme spécifié dans widgetinfo.xml.
     * Cette méthode est aussi appelée à chaque redémarrage du téléphone
     * Elle permet de télécharger les offres du jours au bon moment
     * @param context context de l'application
     * @param appWidgetIds ids de tous les widget lié à l'application
     * @param appWidgetManager le widgetManager
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        Intent serviceIntent = new Intent(context, RemoteFetchService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        context.startService(serviceIntent);
        updateAllWidgets(context,appWidgetIds,appWidgetManager,!IS_REFRESHING);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.weather_list);
        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }

    /**
     * Création graphique du widget en fonction de son ID
     * @param context contexte de l'application
     * @param appWidgetId Id du widget a actualiser
     * @param isRefreshing Est-ce que les données sont en téléchargement
     * @return
     *      Une nouvelle vue avec les bons composants
     */
    private RemoteViews updateWidgetListView(Context context, int appWidgetId, boolean isRefreshing) {

        // le layout à afficher dans le widget
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_layout);

        // Permet de créer la liste
        Intent svcIntent = new Intent(context, WidgetService.class);
        svcIntent.setData(Uri.fromParts("content", String.valueOf(appWidgetId), null));
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

        // ajout de l'adapter au listview du widget
        remoteViews.setRemoteAdapter(appWidgetId,R.id.weather_list,svcIntent);

        // la vue à afficher au cas où la liste des offres est vide
        remoteViews.setEmptyView(R.id.weather_list, R.id.empty_view);



        //Permet de gérer les click avec chaque élément de la liste des offres
        //Il faut ajouter l'intent data pour pouvoir ajouter un "Extra" sinon celui-ci sera ignoré
        final Intent onClickIntent = new Intent(context, WidgetProvider.class);
        onClickIntent.setAction(WidgetProvider.CLICK_ACTION);
        onClickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        onClickIntent.setData(Uri.parse(onClickIntent.toUri(Intent.URI_INTENT_SCHEME)));
        final PendingIntent onClickPendingIntent = PendingIntent.getBroadcast(context, 0,
                onClickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setPendingIntentTemplate(R.id.weather_list, onClickPendingIntent);

        //Affichage du la date du dernier refresh seulement si il existe
        remoteViews.setTextViewText(R.id.textViewLastRefresh, SharedPreferencesManager.getLastDateRefreshWidget(context));

        //Affichage de la bare de progression lors du rafraichissement
        if(isRefreshing){
            remoteViews.setViewVisibility(R.id.refresh, View.INVISIBLE);
            remoteViews.setViewVisibility(R.id.progressBar, View.VISIBLE);
        }
        else {
            remoteViews.setViewVisibility(R.id.refresh, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.progressBar, View.INVISIBLE);

            // Binding du click intent pour le bouton refresh du widget
            final Intent refreshIntent = new Intent(context, WidgetProvider.class);
            refreshIntent.setAction(WidgetProvider.REFRESH_ACTION);
            final PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, 0,
                    refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.refresh, refreshPendingIntent);
        }

        return remoteViews;

    }


    /**
     * Lorsque l'on reçoit un broadcast cette méthode sera applée.
     * Grâce à l'intent nous pouvons savoir quelle Action nous devons réaliser
     * @param context context de l'application
     * @param intent Description abstraite d'une operation à réaliser
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        ComponentName thisWidget = new ComponentName(context, WidgetProvider.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        final String action = intent.getAction();
        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        //--- GESTION DES ACTIONS ---//

        //Action à réaliser lorsqu'on reçois les informations téléchargées
        if (action.equals(DATA_FETCHED)) {
            updateAllWidgets(context,appWidgetIds,appWidgetManager,!IS_REFRESHING);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.weather_list);
        }
        //Action à réaliser lorsqu'on click sur une offre
        else if (action.equals(CLICK_ACTION)) {

            // en fonction de l'adresse le bon shop sera ouvert dans le navigateur
            final String url = intent.getStringExtra(EXTRA_SITE_URL);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        }
        //Action à réaliser lorsqu'on appuis sur le bouton resfresh
        else if(action.equals(REFRESH_ACTION)){
            //Téléchargement des données
            Intent serviceIntent = new Intent(context, RemoteFetchService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            context.startService(serviceIntent);

            updateAllWidgets(context, appWidgetIds, appWidgetManager, IS_REFRESHING);
        }
        //Action àréaliser lorsque le widget à été mis à jour
        else if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)){
            updateAllWidgets(context,appWidgetIds,appWidgetManager , ! IS_REFRESHING);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.weather_list);
        }

        super.onReceive(context, intent);

    }

    /**
     * Nous appliquons l'actualisation pour chaque widget
     * @param context context de l'application
     * @param appWidgetIds ids de tous les widgets liés à l'application
     * @param appWidgetManager le widgetManager
     */
    public void updateAllWidgets(Context context, int[] appWidgetIds, AppWidgetManager appWidgetManager, boolean isRefreshing){
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            RemoteViews layout = updateWidgetListView(context, appWidgetIds[i],isRefreshing);
            appWidgetManager.updateAppWidget(appWidgetIds[i], layout);
        }
    }
}
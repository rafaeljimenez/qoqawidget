package com.qoqa.widget.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qoqa.widget.models.Item;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Cette classe va nous permettre de stocker des informations simples
 * Pour les informations complexes nous utilisons sqlite dans {@link com.qoqa.widget.helpers.DatabaseHelper}
 *
 * @version 0.1.0
 * @author Rafael Jimenez
 */
public class SharedPreferencesManager {

    /** Nom du fichier où nous enregistrons les informations */
    private static final String APP_SETTINGS = "APP_SETTINGS";

    /** La "key" pour le stockage de la dernière date de rafraichissement du widget */
    private static final String LAST_DATE_REFRESH = "LAST_DATE_REFRESH";
    /** La "key" pour le stockage des dernières offres téléchargées */
    private static final String LAST_OFFERS_DOWNLOADED = "LAST_OFFERS_DOWNLOADED";
    /** Key qui sera afficher lors d'un "log" */
    private static final String KEY_LOG = "SharedPreferencesLog";
    private SharedPreferencesManager() {}

    /**
     * Récupération du sharedPreference
     * @param context état courant de l'application
     * @return le sharedPreference
     */
    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }

    /**
     * Récupère la date du dernier "refresh" du Widget
     * @param context Etat courant de l'application
     * @return La date sous la forme "MM/dd HH:mm"
     */
    public static String  getLastDateRefreshWidget(Context context) {

        //Récupère la date stockée si elle n'existe pas retourne 0
        long  milis = getSharedPreferences(context).getLong(LAST_DATE_REFRESH , 0);

        //Vérifie qu'il existe bien une date
        if(milis ==0)
            return "-";

        //Formatage personnalisé de la date
        String lastDateStr = DateManager.monthDayTimeFormat(new Date(milis));
        return lastDateStr;
    }

    /**
     * Enregistre la date de la dernière actualisation
     * @param context état courant de l'application
     */
    public static void setLastDateRefreshWidget(Context context) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong(LAST_DATE_REFRESH, (new Date()).getTime());
        editor.commit();
    }

    /**
     * Enregistre la liste des offres
     * @param context contexte de l'application
     * @param items Liste des offres
     */
    public static void setLastOffersDownloaded(Context context, List<Item> items){
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        String json = new Gson().toJson(items);
        editor.putString(LAST_OFFERS_DOWNLOADED, json);
        editor.commit();
    }

    /**
     * Récupère la liste des offres enregistré
     * @param context contexte de l'application
     * @return
     *      La liste des offres
     */
    public static List<Item> getLastOffersDownloaded(Context context){
        String jsonLastOffers = getSharedPreferences(context).getString(LAST_OFFERS_DOWNLOADED , null);
        if(jsonLastOffers==null){
            return null;
        }
        else{
            // Récupère le type dans lequel nous devons faire la conversion
            Type type = new TypeToken<List<Item>>(){}.getType();
            return new Gson().fromJson(jsonLastOffers,type);
        }
    }
}

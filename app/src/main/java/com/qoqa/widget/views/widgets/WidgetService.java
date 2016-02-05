package com.qoqa.widget.views.widgets;


import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Ce service permet de relier un adaptateur à distance pour demander un RemoteViews approprié à afficher dans le widget
 * (Listview, GridView etc...)
 *
 * @version 0.1.0
 * @author Rafael Jimenez
 */
public class WidgetService extends RemoteViewsService {

    /**
     * En fonction des données de l'Intent retourne le RemoteView approprié.
     * Pour le moment le widget a une seule vue possible à retourner.
     *
     * @param intent Description abstraite d'une opération à effectuer
     * @return
     *      Le RemoteView approprié
     */
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new ListWidgetProvider(this.getApplicationContext(), intent));
    }
}

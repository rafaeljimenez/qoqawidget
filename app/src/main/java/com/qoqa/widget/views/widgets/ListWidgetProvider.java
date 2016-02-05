package com.qoqa.widget.views.widgets;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.qoqa.widget.R;
import com.qoqa.widget.helpers.DatabaseHelper;
import com.qoqa.widget.models.Deal;
import com.qoqa.widget.models.Item;
import com.qoqa.widget.models.Shop;
import com.qoqa.widget.network.RemoteFetchService;
import com.qoqa.widget.utils.SharedPreferencesManager;

/**
 * Cette classe nous permet de remplir la liste de notre widget
 * le principe est le même que pour les "Adapter"
 *
 * @version 0.1.0
 * @author Rafael Jimenez
 */

public class ListWidgetProvider implements RemoteViewsFactory {

    /** La liste des offres du moments */
    private List<Item> listOffers = new ArrayList<Item>();

    /** context de l'application*/
    private Context context = null;

    /** La base de donnée */
    private DatabaseHelper db;

    public ListWidgetProvider(Context context, Intent intent) {
        this.context = context;
        db = new DatabaseHelper(context);
        populateListItem();
    }

    /**
     * Récupération des informations à afficher dans la liste
     */
    private void populateListItem() {
        List<Item> itemsList = SharedPreferencesManager.getLastOffersDownloaded(context);
        if(itemsList !=null ) {
            listOffers = itemsList;
        }
        else {
            listOffers = new ArrayList<Item>();
        }
    }

    /**
     * Retourne le nombre d'élément à afficher dans la liste
     * @return
     *      Le nombre d'élément a afficher dans la liste
     */
    @Override
    public int getCount() {
        return listOffers.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Similaire au getView d'un "Adapter" où à la place d'une "View"on retourne un "RemoteViews"
     * @param position
     *      position de l'offre à afficher
     * @return
     *      La vue actualisée
     */
    @Override
    public RemoteViews getViewAt(int position) {
        populateListItem();
        Deal product = listOffers.get(position).getDeal();

        String brand = product.getBrand();
        String name = product.getName();
        String price = product.getUnitPrice() / 100 +"";
        String regularPrice = product.getRegularPrice() / 100 + "";

        //récupération du layout
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.product_row);

        //affectation de chaque textView par le texte voulu
        rv.setTextViewText(R.id.tvBrand, brand);
        rv.setTextViewText(R.id.tvName, name);
        rv.setTextViewText(R.id.tvPrice, price);
        rv.setTextViewText(R.id.tvRegularPrice, regularPrice);


        // si le "RegularPrice" n'existe pas on masque le "au lieu de"
        if (product.getRegularPrice() == 0) {

            rv.setViewVisibility(R.id.LinearLayoutAuLieuDe, View.GONE);
        } else {
            rv.setViewVisibility(R.id.LinearLayoutAuLieuDe, View.VISIBLE);
        }

        //récupération du market en fonction de son id
        Shop shop = db.getShop(product.getShopId());

        // Vérifie que le market recherché par l'id existe
        if(shop !=null) {

            //resource id de l'icon du shop initialisé à -1 car pas trouvé encore
            int imageResId = -1;

            String[] splitName = shop.getName().split("\\.");

            //vérifie que le nom existe bien
            if (splitName.length > 0) {
                String resName = splitName[0].toLowerCase();
                imageResId = context.getResources().getIdentifier(resName, "drawable", context.getPackageName());
            }
            //si la resource n'as pas été trouvée on lui affecte une image par défaut
            if (imageResId == -1) {
                rv.setImageViewResource(R.id.thumbnail, R.drawable.qoqa);
            } else {
                rv.setImageViewResource(R.id.thumbnail, imageResId);
            }

            // Création de l'intent lorsque nous appuierons sur la cellule
            // pour ouvrir  à la page du bon produit qoqa
            final Intent intent = new Intent();
            final Bundle extras = new Bundle();
            extras.putString(WidgetProvider.EXTRA_SITE_URL, "http://" + shop.getDomain());
            intent.putExtras(extras);

            //ajout de l'intent pour l'event "OnClick"
            rv.setOnClickFillInIntent(R.id.LayoutRowAnnonce, intent);

        }
        return rv;
    }


    /**
     * Lors du chargement nous pouvons ajouter une vue customisée
     * @return
     *      une vue customisée pour le chargment des éléments de la liste du widget
     */
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
    }

    /**
     * Lorsque nous notifions que les données du widget ont été modifiées
     * cette méthode est appelée.
     */
    @Override
    public void onDataSetChanged() {
        populateListItem();
    }

    @Override
    public void onDestroy() {
    }

}
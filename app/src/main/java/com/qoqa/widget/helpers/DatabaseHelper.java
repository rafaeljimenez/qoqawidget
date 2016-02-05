package com.qoqa.widget.helpers;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.qoqa.widget.models.Shop;

/**
 * Cette classe va gérer notre base de données sqlite en créant les différentes tables
 * et en applicant différentes méthodes CRUD
 * @version 0.2.0
 * @author Rafael Jimenez
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /** Logcat tag */
    private static final String LOG = "DatabaseHelper";

    /** Version de la Database*/
    private static final int DATABASE_VERSION = 1;

    /** Nom de la Database */
    private static final String DATABASE_NAME = "QOQA_DATABASE";

    //---- Noms des tables ----//

    /** Nom de la table shop */
    private static final String TABLE_SHOP = "shop";

    //----- Les nom de colones qui seront dans chaque table-----//

    /** La key de l'id */
    private static final String KEY_ID = "_id";

    //----- Les nom de colones qui seront dans la table shop-----//

    /** La key name */
    private static final String KEY_NAME = "name";
    /** La key domain */
    private static final String KEY_DOMAIN = "domain";


    /** Requête pour créer la table shop */
    private static final String CREATE_TABLE_SHOP = "CREATE TABLE "
            + TABLE_SHOP + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME
            + " TEXT," + KEY_DOMAIN + " TEXT" + ")";

    /**
     * Constructeur de DatabaseHelper
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * La base de donnée sera créée et une seul fois lors de sa première utilisation
     * @param db la base de donnée
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // création des table necessaire.
        db.execSQL(CREATE_TABLE_SHOP);
        //remplissage de la table
        initShops(db);
    }

    /**
     * Lors d'un "upgrade" les anciennes tables doivent être supprimées puis
     * les nouvelles seront créées
     *
     * @param db La base de donnée
     * @param oldVersion ancienne version de la base de donnée
     * @param newVersion nouvelle version de la base de donnée
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // suppression des anciennes tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOP);

        // création des nouvelles tables
        onCreate(db);
    }

    /**
     * Ferme la base de donnée
     */
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
    //----------------SHOP METHODS------------ //

    /**
     * Permet d'initaliser notre base de donnée
     * Dans une prochaine version une base de données sqlite sera fournie,
     * il ne sera plus nécessaire de faire cela
     * @param db base de donnée
     */
    public void initShops(SQLiteDatabase db){
        Shop mQoqa = new Shop(2,"QoQa.ch","www.qoqa.ch");
        Shop mQoqaFr = new Shop(3,"QoQa.fr","www.qoqa.fr");
        Shop mQwine = new Shop(4,"Qwine.ch","www.qwine.ch");
        Shop mSport = new Shop(7,"Qsport.ch","www.qsport.ch");
        Shop mQooking = new Shop(10,"Qooking.ch","www.qooking.ch");
        Shop mQChef = new Shop(11,"Qchef.fr","www.qchef.fr");
        createShop(mQoqa, db);
        createShop(mQwine, db);
        createShop(mSport, db);
        createShop(mQooking, db);
        createShop(mQoqaFr, db);
        createShop(mQChef, db);

    }

    /**
     * Ajout d'un nouveau "shop" dans la base de donnée
     * @param shop shop à stocker
     * @param db base de donnée
     * @return l'ID du shop ajouté
     */
    public long createShop(Shop shop,SQLiteDatabase db) {
        if(db==null)
            db = this.getWritableDatabase();

        //création des valeurs à insérer dans la table
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, shop.getName());
        values.put(KEY_DOMAIN, shop.getDomain());
        values.put(KEY_ID, shop.getId());

        // insert row
        long shop_id = db.insert(TABLE_SHOP, null, values);

        return shop_id;
    }

    /**
     * Récupère un seul shop en fonction de son ID
     * @param shopId id du shop à récupérer
     * @return
     *      Le Shop recherché ou null si rien n'a été trouvé
     */
    public Shop getShop(long shopId) {
        SQLiteDatabase db = this.getReadableDatabase();

        //Requete sql pour récupérer un shop en fonction de son ID
        String selectQuery = "SELECT  * FROM " + TABLE_SHOP +" WHERE "
                + KEY_ID + " = " +shopId;

        Log.d(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        //vérifie si il a trouvé un "Shop"
        if (c != null && c.getCount()>0) {
            c.moveToFirst();
            Shop shop = new Shop();
            shop.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            shop.setName((c.getString(c.getColumnIndex(KEY_NAME))));
            shop.setDomain((c.getString(c.getColumnIndex(KEY_DOMAIN))));
            return shop;
        }
        //Aucun "Shop" n'a été trouvé
        else{
            return  null;
        }

    }

}
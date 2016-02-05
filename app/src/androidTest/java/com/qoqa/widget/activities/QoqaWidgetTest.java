package com.qoqa.widget.activities;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.TextView;

import com.qoqa.widget.R;
import com.qoqa.widget.utils.SharedPreferencesManager;

/**
 * Ces tests ont pour but de vérifier le fonctionnement des composants de la première vue
 * que se soit du disign ou du matériel.
 * Ce que nous testons :
 * 1) Les zones de textes existent bien
 * 2) L'enregistrement de la date lors de l'actualisation du widget est bien enregistrée
 *
 * @author Rafael Jimenez
 * @version 0.1.1
 */
public class QoqaWidgetTest extends ActivityInstrumentationTestCase2<QoqaWidget> {
    QoqaWidget qoqaWidgetActivity;
    TextView textViewTitle;
    TextView textViewContent;
    Context context;


    public QoqaWidgetTest() {
        super(QoqaWidget.class);
    }

    /**
     *
     * @throws Exception
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        qoqaWidgetActivity = getActivity();
        textViewTitle = (TextView)qoqaWidgetActivity.findViewById(R.id.textViewTitle);
        textViewContent = (TextView)qoqaWidgetActivity.findViewById(R.id.textViewContent);
        context = InstrumentationRegistry.getTargetContext();
        SharedPreferencesManager.clearSharedPrefs(context);
    }

    /**
     * Vérifie que les textView existent bien
     */
    @SmallTest
    public void testTextViewAreNotNull() {
        assertNotNull(textViewContent);
        assertNotNull(textViewTitle);
    }

    /**
     * Test le shared preference pour voir si il enregistre et récupère bien l'information voulue
     */
    @MediumTest
    public void testSharedPreference_SaveAndReadLastDate(){
        String date = SharedPreferencesManager.getLastDateRefreshWidget(context);
        //Vérifie qu'il n'existe pas de date dans un premier temp
        assertNull(date);

        //Enregistrement de la date
        SharedPreferencesManager.setLastDateRefreshWidget(context);

        //cette fois la date doit exister
        date = SharedPreferencesManager.getLastDateRefreshWidget(context);
        assertNotNull(date);
    }
}

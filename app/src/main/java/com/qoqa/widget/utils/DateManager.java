package com.qoqa.widget.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateManager simplifie la manipulation des dates
 *
 * @version 0.1.0
 * @author Rafael Jimenez
 */
public class DateManager {
    /**
     * Formatage de la date en "MM/dd HH:mm"
     * Exemple, si nous sommes le 7 janvier 2016 à 23h30 l'affichage sera le suivant : "7/1 23:30"
     * @param date La date à formater
     * @return La date formatée en String
     */
    public static String monthDayTimeFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
        String dateFormatStr = sdf.format(date);
        return dateFormatStr;
    }
}

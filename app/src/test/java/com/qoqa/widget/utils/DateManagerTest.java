package com.qoqa.widget.utils;

/**
 * Created by rafael on 07/02/16.
 */

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;


/**
 * Permet de tester le DateManager
 * @author Rafael Jimenez
 * @version 0.1.1
 */
@SmallTest
public class DateManagerTest {

    /**
     * Différents test pour vérifier si la date se transforme comme on le souhaite
     * Exemple 11/05/2015-12:55 -> 11/05 12:55
     * @throws ParseException
     *
     * @author Rafael Jimenez
     * @version 0.1.1
     */

    /**
     * Test si la méthode monthDayTimeFormat de {@link DateManager} fonctionne bien
     * @throws ParseException
     */
    @Test
    public void dateManager_CheckMonthDayTimeFormat() throws ParseException {
        // Permet de créer un format de date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy-HH:mm");

        assertThat("Vérifie que la date 11/05/2015-12:55 se transforme bien en 11/05 12:55",
                DateManager.monthDayTimeFormat(sdf.parse("11/05/2015-12:55")),
                is(equalTo("11/05 12:55")));

        assertThat("Vérifie que la date 01/01/2015-00:00 se transforme bien en 01/01 00:00",
                DateManager.monthDayTimeFormat(sdf.parse("01/01/2015-00:00")),
                is(equalTo("01/01 00:00")));

        assertThat("Vérifie que la date 31/12/2015-23:59 se transforme bien en 31/12 23:59",
                DateManager.monthDayTimeFormat(sdf.parse("31/12/2015-23:59")),
                is(equalTo("31/12 23:59")));
    }

}
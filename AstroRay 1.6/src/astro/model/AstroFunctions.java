/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astro.model;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Rayan Ivaturi
 */
public class AstroFunctions {
    //Planets

    public static String SUN = "Ravi";
    public static String MOON = "Chan";
    public static String MERCURY = "Budh";
    public static String VENUS = "Sukr";
    public static String MARS = "Kuja";
    public static String JUPITER = "Guru";
    public static String SATURN = "Sani";
    public static String URANUS = "Uran";
    public static String NEPTUNE = "Nept";
    public static String PLUTO = "Plut";
    public static String RAHU = "Rahu";
    public static String KETU = "Ketu";
    public static String FORTUNA = "Ftna";
    //This is the order of the planets for Vimsottari dasa
    public static final String[] DASA = new String[]{
        KETU, //0
        VENUS, //1
        SUN, //2
        MOON, //3
        MARS, //4
        RAHU, //5
        JUPITER, //6
        SATURN, //7
        MERCURY //8
    };
    public static final int[] DASA_PERIOD = new int[]{
        7, //Ketu 7 years
        20, //Sukra 20 years
        6, //Ravi 6 years
        10, //Chan 10 year
        7, //Kuja 7 years
        18, //Rahu 18 years
        16, //Guru 16 years
        19, //Sani 19 years
        17 //Budh 17 years
    };

    //Names of Houses
    public static final String[] HOUSE = new String[]{
        "LAGN", //0
        "II  ", //1
        "III ", //2
        "IV  ", //3
        "V   ", //4
        "VI  ", //5
        "VII ", //6
        "VIII", //7
        "IX  ", //8
        "X   ", //9
        "XI  ", //10
        "XII " //11
    };

    //Names of Signs
    public static final String[] SIGN = new String[]{
        "Mesham    ", //0
        "Vrishbham ", //1
        "Midhunam  ", //2
        "Karkatakam", //3
        "Simham    ", //4
        "Kanya     ", //5
        "Tula      ", //6
        "Vrishikam ", //7
        "Dhanus    ", //8
        "Makaram   ", //9
        "Kumbham   ", //10
        "Meenam    " //11
    };

    //Names of Stars
    public static final String[] STAR = new String[]{
        "Aswini      ", //0
        "Bharani     ", //1
        "Krittika    ", //2
        "Rohini      ", //3
        "Mrigasira   ", //4
        "Arudra      ", //5
        "Punarvasu   ", //6
        "Pushyami    ", //7
        "Aslesha     ", //8
        "Makha       ", //9
        "Pubba       ", //10
        "Uttara      ", //11
        "Hasta       ", //12
        "Chitta      ", //13
        "Swati       ", //14
        "Visakha     ", //15
        "Anuradha    ", //16
        "Jyeshta     ", //17
        "Moola       ", //18
        "Poorvashadha", //19
        "Uttarashadha", //20
        "Sravanam    ", //21
        "Dhanishta   ", //22
        "Satabhisham ", //23
        "PoorvaBhadra", //24
        "UttaraBhadra", //25
        "Revati      " //26
    };
    public static final String[] STAR_QUARTER = new String[]{
        "1st Padam", //0
        "2nd Padam", //1
        "3rd Padam", //2
        "4th Padam" //3
    };
    public static final String[] TITHI = new String[]{
        "SUKLA PADYAMI    ",
        "SUKLA VIDIYA     ",
        "SUKLA TADIYA     ",
        "SUKLA CHAVITI    ",
        "SUKLA PANCHAMI   ",
        "SUKLA SHASHTI    ",
        "SUKLA SAPTAMI    ",
        "SUKLA ASHTAMI    ",
        "SUKLA NAVAMI     ",
        "SUKLA DASAMI     ",
        "SUKLA EKADASI    ",
        "SUKLA DWADASI    ",
        "SUKLA TRAYODASI  ",
        "SUKLA CHATURDASI ",
        "POURNAMI         ",
        "BAHULA PADYAMI   ",
        "BAHULA VIDIYA    ",
        "BAHULA TADIYA    ",
        "BAHULA CHAVITI   ",
        "BAHULA PANCHAMI  ",
        "BAHULA SHASHTI   ",
        "BAHULA SAPTAMI   ",
        "BAHULA ASHTAMI   ",
        "BAHULA NAVAMI    ",
        "BAHULA DASAMI    ",
        "BAHULA EKADASI   ",
        "BAHULA DWADASI   ",
        "BAHULA TRAYODASI ",
        "BAHULA CHATURDASI",
        "AMAVASYA  "
    };

    public static Planet computeFortuna(House ascendant, Planet moon, Planet sun) {

        int ascAndMoon = ascendant.getTotalSeconds() + moon.getTotalSeconds();

        int fortunaTotalSeconds = ascAndMoon - sun.getTotalSeconds();
        if (fortunaTotalSeconds < 0) {
            fortunaTotalSeconds += (360 * 3600);
        } else if (fortunaTotalSeconds >= (360 * 3600)) {
            fortunaTotalSeconds -= 1296000;
        }
        return new Planet("Ftna", 0, 0, 0, fortunaTotalSeconds, false);
    }

    public static int getAmsaSign(AstroObj astroObj, int amsa) {
        int amsaSeconds = (30 * 3600) / amsa;
        return (astroObj.getTotalSeconds() / amsaSeconds) % 12;
    }

    public static String[] getMahaDasas(Planet moon, Calendar dob) {
        String[] mahaDasas = new String[9];

        int runningDasa = moon.getStarLord();
        double runningDasaBalance = moon.getDasaBalance();

        int dasaBalanceInSeconds = (int) Math.round(runningDasaBalance * (365.24219 * 24 * 60 * 60));
        dob.add(Calendar.SECOND, dasaBalanceInSeconds);

        int i = 0;

        mahaDasas[i] = DASA[runningDasa] + " dasa ends on : " + formatCalendar(dob);

        for (int dasa = (runningDasa + 1); dasa < (runningDasa + 9); dasa++) {
            i++;
            int dasaDurationInSeconds = (int) Math.round(DASA_PERIOD[dasa % 9] * (365.24219 * 24 * 60 * 60));
            dob.add(Calendar.SECOND, dasaDurationInSeconds);
            mahaDasas[i] = DASA[dasa % 9] + " dasa ends on : " + formatCalendar(dob);
        }
        return mahaDasas;
    }

    public static String[] getAntarDasas(Planet moon, Calendar dob) {
        String[] antarDasas = new String[81];

        int runningMahaDasa = moon.getStarLord();
        int runningMahaDasaYears = DASA_PERIOD[runningMahaDasa];

        double runningMahaDasaBalance = moon.getDasaBalance();
        double runningMahaDasaElapsed = runningMahaDasaYears - runningMahaDasaBalance;

        //Findout which is the starting Antardasa
        double elapsedAntarDasaTotal = 0;
        double runningAntarDasaBalance = 0;

        boolean forStartingAntarDasa = true;

        int i = 0;

        for (int dasa = runningMahaDasa; dasa < (runningMahaDasa + 9); dasa++) {
            for (int antarDasa = dasa; antarDasa < (dasa + 9); antarDasa++) {
                double antarDasaDuration = DASA_PERIOD[dasa % 9] * DASA_PERIOD[antarDasa % 9] / 120.0;

                if (forStartingAntarDasa) {
                    elapsedAntarDasaTotal += antarDasaDuration;
                    runningAntarDasaBalance = runningMahaDasaElapsed - elapsedAntarDasaTotal;

                    if (runningAntarDasaBalance <= 0) {
                        runningAntarDasaBalance *= -1;
                        forStartingAntarDasa = false;

                        int antarDasaBalanceInSeconds = (int) Math.round(runningAntarDasaBalance * (365.24219 * 24 * 60 * 60));
                        dob.add(Calendar.SECOND, antarDasaBalanceInSeconds);
                        antarDasas[i] = DASA[dasa % 9] + "->" + DASA[antarDasa % 9] + " ends on : " + formatCalendar(dob);
                    }
                } else {
                    //Calculate for the remaining antarDasas in the running Mahadasa
                    int antarDasaDurationInSeconds = (int) Math.round(antarDasaDuration * (365.24219 * 24 * 60 * 60));
                    dob.add(Calendar.SECOND, antarDasaDurationInSeconds);
                    antarDasas[i] = DASA[dasa % 9] + "->" + DASA[antarDasa % 9] + " ends on : " + formatCalendar(dob);
                }
                i++;
            }
        }
        return antarDasas;
    }

    public static double[] getRunningAntarDasaAndBalance(Planet moon) {

        double[] runningAntarDasaAndBalance = new double[2];

        int runningMahaDasa = moon.getStarLord();
        int runningMahaDasaYears = DASA_PERIOD[runningMahaDasa];

        double runningMahaDasaBalance = moon.getDasaBalance();
        double runningMahaDasaElapsed = runningMahaDasaYears - runningMahaDasaBalance;

        //Findout which is the starting Antardasa
        double elapsedAntarDasaTotal = 0;
        double runningAntarDasaBalance = 0;

        for (int dasa = runningMahaDasa; dasa < (runningMahaDasa + 9); dasa++) {
            for (int antarDasa = dasa; antarDasa < (dasa + 9); antarDasa++) {
                double antarDasaDuration = DASA_PERIOD[dasa % 9] * DASA_PERIOD[antarDasa % 9] / 120.0;
                elapsedAntarDasaTotal += antarDasaDuration;
                runningAntarDasaBalance = runningMahaDasaElapsed - elapsedAntarDasaTotal;

                if (runningAntarDasaBalance <= 0) {
                    runningAntarDasaBalance *= -1;
                    runningAntarDasaAndBalance[0] = antarDasa % 9;
                    runningAntarDasaAndBalance[1] = runningAntarDasaBalance;
                    return runningAntarDasaAndBalance;
                }
            }
        }
        return runningAntarDasaAndBalance;
    }

    public static String[] getViDasas(Planet moon, Calendar dob) {
        String[] viDasas = new String[729];

        int runningMahaDasa = moon.getStarLord();

        double[] runningAntarDasaAndBalance = getRunningAntarDasaAndBalance(moon);
        int runningAntarDasa = (int) runningAntarDasaAndBalance[0];
        double runningAntarDasaBalance = runningAntarDasaAndBalance[1];
        double runningAntarDasaElapsed = (DASA_PERIOD[runningMahaDasa] *
                DASA_PERIOD[runningAntarDasa] / 120.0) - runningAntarDasaBalance;

        //Findout which is the starting Vidasa
        double elapsedViDasaTotal = 0;
        double runningViDasaBalance = 0;

        boolean forStartingViDasa = true;

        int i = 0;

        int remainingAntarDasas = -1;
        if (runningAntarDasa >= runningMahaDasa) {
            remainingAntarDasas = runningMahaDasa + 9;
        } else {
            remainingAntarDasas = runningMahaDasa;
        }
        for (int antarDasa = runningAntarDasa; antarDasa < remainingAntarDasas; antarDasa++) {
            for (int viDasa = antarDasa; viDasa < (antarDasa + 9); viDasa++) {
                double viDasaDuration = (DASA_PERIOD[runningMahaDasa] *
                        DASA_PERIOD[antarDasa % 9] / 120.0) *
                        DASA_PERIOD[viDasa % 9] / 120.0;


                if (forStartingViDasa) {
                    elapsedViDasaTotal += viDasaDuration;
                    runningViDasaBalance = runningAntarDasaElapsed - elapsedViDasaTotal;
                    if (runningViDasaBalance <= 0) {
                        runningViDasaBalance *= -1;
                        forStartingViDasa = false;

                        int viDasaBalanceInSeconds = (int) Math.round(runningViDasaBalance * (365.24219 * 24 * 60 * 60));
                        dob.add(Calendar.SECOND, viDasaBalanceInSeconds);
                        viDasas[i] = DASA[runningMahaDasa] + "->" + DASA[antarDasa % 9] + "->" + DASA[viDasa % 9] + " ends on : " + formatCalendar(dob);
                    }
                } else {
                    int viDasaDurationInSeconds = (int) Math.round(viDasaDuration * (365.24219 * 24 * 60 * 60));
                    dob.add(Calendar.SECOND, viDasaDurationInSeconds);
                    viDasas[i] = DASA[runningMahaDasa] + "->" + DASA[antarDasa % 9] + "->" + DASA[viDasa % 9] + " ends on : " + formatCalendar(dob);

                }
                i++;
            }
        }

        for (int dasa = (runningMahaDasa + 1); dasa < (runningMahaDasa + 9); dasa++) {
            for (int antarDasa = dasa; antarDasa < (dasa + 9); antarDasa++) {
                for (int viDasa = antarDasa; viDasa < (antarDasa + 9); viDasa++) {
                    double viDasaDuration = (DASA_PERIOD[dasa % 9] *
                            DASA_PERIOD[antarDasa % 9] / 120.0) *
                            DASA_PERIOD[viDasa % 9] / 120.0;

                    //Calculate for the remaining viDasas in the running Antardasa & Mahadasa
                    int viDasaDurationInSeconds = (int) Math.round(viDasaDuration * (365.24219 * 24 * 60 * 60));
                    dob.add(Calendar.SECOND, viDasaDurationInSeconds);
                    viDasas[i] = DASA[dasa % 9] + "->" + DASA[antarDasa % 9] + "->" + DASA[viDasa % 9] + " ends on : " + formatCalendar(dob);

                    i++;
                }
            }
        }
        return viDasas;
    }

    public static int getTithi(Planet moon, Planet sun) {
        int totalSeconds = moon.getTotalSeconds() - sun.getTotalSeconds();

        if (totalSeconds < 0) {
            totalSeconds += (360 * 3600);
        }

        return totalSeconds / (12 * 3600);
    }

    public static String getBaseData() {
        return null;
    }

    public static String formatCalendar(Calendar cal) {
        DecimalFormat yFormat = new DecimalFormat("0000");
        DecimalFormat mdFormat = new DecimalFormat("00");
        return yFormat.format(cal.get(Calendar.YEAR)) + "-" +
                mdFormat.format((cal.get(Calendar.MONTH) + 1)) + "-" +
                mdFormat.format(cal.get(Calendar.DAY_OF_MONTH));
    }
    
    
    public static String getDifferenceBetweenDatesAsString(Calendar fromDate, Calendar toDate) {
        //From the two dates, identify which date is bigger. 
        //The bigger one is set as toDate and the smaller one is fromDate so that  always the positive duration is returned.
        
        int fromDay, fromMonth, fromYear;
        int toDay, toMonth, toYear;
        
        if (fromDate.before(toDate)) {
            fromDay = fromDate.get(Calendar.DAY_OF_MONTH);
            fromMonth = fromDate.get(Calendar.MONTH) + 1;
            fromYear = fromDate.get(Calendar.YEAR);

            toDay = toDate.get(Calendar.DAY_OF_MONTH);
            toMonth = toDate.get(Calendar.MONTH) + 1;
            toYear = toDate.get(Calendar.YEAR);
        } else {
            fromDay = toDate.get(Calendar.DAY_OF_MONTH);
            fromMonth = toDate.get(Calendar.MONTH) + 1;
            fromYear = toDate.get(Calendar.YEAR);

            toDay = fromDate.get(Calendar.DAY_OF_MONTH);
            toMonth = fromDate.get(Calendar.MONTH) + 1;
            toYear = fromDate.get(Calendar.YEAR);
        }
        
        int[] monthDay = new int[]{31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        
        int year;
        int month;
        int day;        

        // Day Calculation
        int increment = 0;

        if (fromDay > toDay) {
            increment = monthDay[fromMonth - 1];
        }

        // if it is february month
        // if it's to day is less then from day
        if (increment == -1) {
            GregorianCalendar cal = new GregorianCalendar();
            if (cal.isLeapYear(fromYear)) {
                // leap year february contain 29 days
                increment = 29;
            } else {
                increment = 28;
            }
        }

        if (increment != 0) {
            day = (toDay + increment) - fromDay;
            increment = 1;
        } else {
            day = toDay - fromDay;
        }

        //month calculation
        if ((fromMonth + increment) > toMonth) {
            month = (toMonth + 12) - (fromMonth + increment);
            increment = 1;
        } else {
            month = (toMonth) - (fromMonth + increment);
            increment = 0;
        }

        // year calculation
        year = toYear - (fromYear + increment);
        
        return year + " YEARS " + month + " MONTHS " + day + " DAYS ";        
    }
}

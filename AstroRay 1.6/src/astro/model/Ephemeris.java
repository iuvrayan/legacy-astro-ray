/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astro.model;

import swisseph.SweConst;
import swisseph.SweDate;
import swisseph.SwissEph;

/**
 *
 * @author Rayan Ivaturi
 */
public class Ephemeris {

    private SwissEph swissEph;
    private SweDate sweDate;
    private double julianDay;
    private double ephemerisTime;
    private double latitude;
    private double longitude;
    private double siderealTime;

    public Ephemeris(int year, int month, int day, double hours, double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;

        //Create SwissEpth with source directory of ephemeris files
        swissEph = new SwissEph("ephe");

        //Set LAHIRI Ayanamsa
        swissEph.swe_set_sid_mode(SweConst.SE_SIDM_LAHIRI, 0.0, 0.0);

        //Set the latitude and logitude to calculate topgographic longitudes
        swissEph.swe_set_topo(latitude, longitude, 0.0);

        //Convert hours, day, month and year to JulianDay (JD) Number
        sweDate = new SweDate();
        sweDate.setDate(year, month, day, hours);
        sweDate.setCalendarType(sweDate.SE_GREG_CAL, sweDate.SE_KEEP_DATE);

        julianDay = sweDate.getJulDay();

        //compute Ephemeris time from Universal time by adding delta_t
        ephemerisTime = julianDay + sweDate.getDeltaT(julianDay);
    }

    public Planet[] getPlanets() {
        //13th Planet fortuna will not be populated here.
        //It should be calculatged separately
        Planet[] planet = new Planet[13];

        //Add speed, siderial, topocentric flags
        int flag = SweConst.SEFLG_SPEED + SweConst.SEFLG_SIDEREAL + SweConst.SEFLG_TOPOCTR;

        for (int p = SweConst.SE_SUN; p <= SweConst.SE_MEAN_NODE; p++) {

            if (p == SweConst.SE_EARTH) {
                continue;
            }

            double positions[] = new double[6];
            StringBuffer sbError = new StringBuffer();
            int returnFlag = swissEph.swe_calc(ephemerisTime, p, flag, positions, sbError);

            //If there is a problem, a negative value is returned and an error message is returned in sbError
            if (returnFlag < 0) {
                //System.out.print("Ephemeris Error: " + sbError.toString() + "\n");
                return null;
            }
            //else if (returnFlag != flag) {
            //System.out.print("warning: returnFlag != flag "+sbError.toString()+" flag : "+flag+" returnFlag : "+returnFlag+"\n");
            //}

            String name = swissEph.swe_get_planet_name(p);

            boolean isRetrograde = false;
            if (positions[3] < 0) {
                isRetrograde = true;
            }

            //Planets Rahu and Ketu are always Retrograde. So no need to represent them as Retrograde
            if (p == SweConst.SE_MEAN_NODE) {
                planet[p] = new Planet(getPlanetName(p), positions[0], false);
            } else {
                planet[p] = new Planet(getPlanetName(p), positions[0], isRetrograde);
            }

            //If Planet is Rahu, calculate Ketu also
            if (p == SweConst.SE_MEAN_NODE) {
                double ketuPosition = positions[0] + 180;
                if (ketuPosition >= 360) {
                    ketuPosition -= 360;
                }
                planet[11] = new Planet(getPlanetName(11), ketuPosition, false);
            }
        }
        return planet;
    }

    public House[] getHouses() {

        House[] house = new House[12];

        double cusps[] = new double[13];
        double ascmc[] = new double[10];

        StringBuffer sbError = new StringBuffer();
        int flag = SweConst.SEFLG_SIDEREAL;
        int returnFlag = swissEph.swe_houses(ephemerisTime, flag, latitude, longitude, 'P', cusps, ascmc);

        //If there is a problem, a negative value is returned and an error message is returned in sbError
        if (returnFlag < 0) {
            //System.out.print("Ephemeris Error: " + sbError.toString() + "\n");
            return null;
        }
        //else if (returnFlag != flag) {
        //System.out.print("warning: returnFlag != flag "+sbError.toString()+" flag : "+flag+" returnFlag : "+returnFlag+"\n");
        //}

        for (int i = 1; i < cusps.length; i++) {
            house[i - 1] = new House(AstroFunctions.HOUSE[i - 1], cusps[i]);
        }

        siderealTime = ascmc[2];

        return house;
    }

    //Sidereal time in Degrees
    public double getSiderealTime() {
        if (siderealTime == 0.0) {
            getHouses();
        }
        return siderealTime;
    }

    public double getAyanamsa() {
        return swissEph.swe_get_ayanamsa(ephemerisTime);
    }

    private String getPlanetName(int p) {
        String planet = null;
        switch (p) {
            case 0:
                planet = AstroFunctions.SUN;
                break;
            case 1:
                planet = AstroFunctions.MOON;
                break;
            case 2:
                planet = AstroFunctions.MERCURY;
                break;
            case 3:
                planet = AstroFunctions.VENUS;
                break;
            case 4:
                planet = AstroFunctions.MARS;
                break;
            case 5:
                planet = AstroFunctions.JUPITER;
                break;
            case 6:
                planet = AstroFunctions.SATURN;
                break;
            case 7:
                planet = AstroFunctions.URANUS;
                break;
            case 8:
                planet = AstroFunctions.NEPTUNE;
                break;
            case 9:
                planet = AstroFunctions.PLUTO;
                break;
            case 10:
                planet = AstroFunctions.RAHU;
                break;
            case 11:
                planet = AstroFunctions.KETU;
                break;
            case 12:
                planet = AstroFunctions.FORTUNA;
                break;
        }
        return planet;
    }
}

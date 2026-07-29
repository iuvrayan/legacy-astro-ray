/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astro.model;

import java.text.DecimalFormat;

/**
 *
 * @author Rayan Ivaturi
 */
public abstract class AstroObj {

    protected String name;
    protected int totalSeconds;

    public String getName() {
        return name;
    }

    public int getSign() {
        return (totalSeconds / 3600) / 30;
    }

    public int getDegrees() {
        return (totalSeconds / 3600) % 30;
    }

    public int getMinutes() {
        return (totalSeconds % 3600) / 60;
    }

    public int getSeconds() {
        return totalSeconds % 60;
    }

    public int getTotalSeconds() {
        return totalSeconds;
    }

    public int getStar() {
        //One Star is 13 degrees 20 minutes
        //One Padam is 3 degrees 20 minutes
        return totalSeconds / (13 * 3600 + 20 * 60);
    }

    public int getStarQuarter() {
        //One Star is 13 degrees 20 minutes
        //One Padam is 3 degrees 20 minutes
        int totalQtrSec = totalSeconds % (13 * 3600 + 20 * 60);
        int quarter = totalQtrSec / (3 * 3600 + 20 * 60);
        return quarter;
    }

    public String getSignLord() {
        String planet = null;

        switch (getSign()) {
            //Mesham, Vrichikam -> Kuja
            case 0:
            case 7:
                planet = AstroFunctions.MARS;
                break;

            //Vrishabham, Tula -> Sukr
            case 1:
            case 6:
                planet = AstroFunctions.VENUS;
                break;

            //Midhunam, Kanya -> Budh
            case 2:
            case 5:
                planet = AstroFunctions.MERCURY;
                break;

            //Karkatakam -> Chan
            case 3:
                planet = AstroFunctions.MOON;
                break;

            //Simham -> Ravi
            case 4:
                planet = AstroFunctions.SUN;
                break;

            //Dhanus, Meenam -> Guru
            case 8:
            case 11:
                planet = AstroFunctions.JUPITER;
                break;

            //Makaram, Kumbham -> Sani
            case 9:
            case 10:
                planet = AstroFunctions.SATURN;
                break;
        }
        return planet;
    }

    //The planet number returned is based on the Vimsottari dasa order of planets
    //it returns the dasa lord (planet) of the star
    public int getStarLord() {
        return getStar() % 9;
    }

    public int getSubLord() {
        int runningMahaDasa = getStarLord();
        int runningMahaDasaYears = AstroFunctions.DASA_PERIOD[runningMahaDasa];

        double runningMahaDasaBalance = getDasaBalance();
        double runningMahaDasaElapsed = runningMahaDasaYears - runningMahaDasaBalance;

        //Findout which is the running Anardasa
        double elapsedAntarDasaTotal = 0;
        double runningAntarDasaBalance = 0;

        int subLord = -1;

        for (int dasa = runningMahaDasa; dasa < (runningMahaDasa + 9); dasa++) {
            double antarDasaDuration = runningMahaDasaYears * AstroFunctions.DASA_PERIOD[dasa % 9] / 120.0;

            elapsedAntarDasaTotal += antarDasaDuration;

            runningAntarDasaBalance = runningMahaDasaElapsed - elapsedAntarDasaTotal;

            if (runningAntarDasaBalance <= 0) {
                subLord = dasa % 9;
                break;
            }
        }
        return subLord;
    }

    //Returns dasa balance in years
    public double getDasaBalance() {
        int birthStar = getStar();

        //Get the end position of the above Star in Zodiac in Seconds
        int totalStarEndSeconds = (13 * 3600 + 20 * 60) * (birthStar + 1);

        int balance = totalStarEndSeconds - getTotalSeconds();

        //48000 seconds for one star (13 * 3600 + 20 * 60)
        return (balance / 48000.0) * AstroFunctions.DASA_PERIOD[getStarLord()];
    }

    public String getDegreesAndMinutesAsString() {
        DecimalFormat df = new DecimalFormat("00");

        StringBuffer sb = new StringBuffer();
        sb.append(name);
        sb.append(' ');
        sb.append(df.format(getDegrees()));
        sb.append('-');
        sb.append(df.format(getMinutes()));

        return sb.toString();
    }

    public String getAsString() {
        DecimalFormat df = new DecimalFormat("00");

        StringBuffer sb = new StringBuffer();

        sb.append(name);
        sb.append(' ');
        sb.append(' ');
        sb.append(' ');
        sb.append(' ');

        sb.append(df.format(getSign()));
        sb.append(':');
        sb.append(df.format(getDegrees()));
        sb.append(':');
        sb.append(df.format(getMinutes()));
        sb.append(':');
        sb.append(df.format(getSeconds()));
        sb.append(' ');
        sb.append(' ');
        sb.append(' ');
        sb.append(AstroFunctions.STAR[getStar()]);
        sb.append(' ');
        sb.append(' ');
        sb.append(' ');
        sb.append((getStarQuarter() + 1));
        sb.append(' ');
        sb.append(' ');
        sb.append(' ');
        sb.append(' ');
        sb.append(AstroFunctions.DASA[getStarLord()]);
        sb.append(' ');
        sb.append(' ');
        sb.append(' ');
        sb.append(' ');
        sb.append(' ');
        sb.append(' ');
        sb.append(AstroFunctions.DASA[getSubLord()]);
        return sb.toString();
    }
}

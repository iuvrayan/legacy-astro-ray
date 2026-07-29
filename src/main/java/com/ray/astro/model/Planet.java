/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ray.astro.model;

import java.text.DecimalFormat;

/**
 *
 * @author Rayan Ivaturi
 */
public class Planet extends AstroObj {

    private boolean isRetrograde;

    public Planet(String name, int sign, int degrees, int minutes, int seconds, boolean isRetrograde) {
        this.name = name;
        this.totalSeconds = (sign * 108000) + (degrees * 3600) + (minutes * 60) + seconds;
        if (this.totalSeconds >= 1296000) {
            this.totalSeconds -= 1296000;
        }
        this.isRetrograde = isRetrograde;
    }

    public Planet(String name, double decimalDegrees, boolean isRetrograde) {
        this.name = name;
        this.totalSeconds = (int) Math.round(decimalDegrees * 3600.0);
        if (this.totalSeconds >= 1296000) {
            this.totalSeconds -= 1296000;
        }
        this.isRetrograde = isRetrograde;
    }

    @Override
    public String getDegreesAndMinutesAsString() {
        if (isRetrograde) {
            return super.getDegreesAndMinutesAsString().replace(' ', '®');
        } else {
            return super.getDegreesAndMinutesAsString();
        }
    }

    @Override
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
        if (isRetrograde) {
            sb.append('R');
        } else {
            sb.append(' ');
        }
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astro.model;

/**
 *
 * @author Rayan Ivaturi
 */
public class House extends AstroObj {

    public House(String name, int sign, int degrees, int minutes, int seconds) {
        this.name = name;
        this.totalSeconds = (sign * 108000) + (degrees * 3600) + (minutes * 60) + seconds;
        if (this.totalSeconds >= 1296000) {
            this.totalSeconds -= 1296000;
        }
    }

    public House(String name, double decimalDegrees) {
        this.name = name;
        this.totalSeconds = (int) Math.round(decimalDegrees * 3600.0);
        if (this.totalSeconds >= 1296000) {
            this.totalSeconds -= 1296000;
        }
    }
}

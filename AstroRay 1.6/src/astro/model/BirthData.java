/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astro.model;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author Rayan Ivaturi
 */
public class BirthData {

    public static final String AM = "AM";
    public static final String PM = "PM";
    public static final String EAST = "EAST";
    public static final String WEST = "WEST";
    public static final String NORTH = "NORTH";
    public static final String SOUTH = "SOUTH";
    //Set default value of name to null string
    private String name = "";
    private int day;
    private int month;
    private int year;
    private int hours;
    private int minutes;
    private int seconds;
    private String meridian = AM;
    //Set default value of locationName to null string
    private String locationName = "";
    private int longitudeDegrees;
    private int longitudeMinutes;
    private int longitudeSeconds;
    private String longitudeDirection = EAST;
    private int latitudeDegrees;
    private int latitudeMinutes;
    private int latitudeSeconds;
    private String latitudeDirection = NORTH;
    private int timeZoneHours;
    private int timeZoneMinutes;
    private int timeZoneSeconds;
    private String timeZoneDirection = EAST;
    private boolean inDST;
    private int dstHours;
    private int dstMinutes;
    private int dstSeconds;
    private boolean isMoonGiven;
    private int moonSign;
    private int moonDegrees;
    private int moonMinutes;
    private int moonSeconds;

    public boolean isMoonGiven() {
        return isMoonGiven;
    }

    public void setIsMoonGiven(boolean isMoonGiven) {
        this.isMoonGiven = isMoonGiven;
    }

    public int getMoonDegrees() {
        return moonDegrees;
    }

    public void setMoonDegrees(int moonDegrees) {
        this.moonDegrees = moonDegrees;
    }

    public int getMoonMinutes() {
        return moonMinutes;
    }

    public void setMoonMinutes(int moonMinutes) {
        this.moonMinutes = moonMinutes;
    }

    public int getMoonSeconds() {
        return moonSeconds;
    }

    public void setMoonSeconds(int moonSeconds) {
        this.moonSeconds = moonSeconds;
    }

    public int getMoonSign() {
        return moonSign;
    }

    public void setMoonSign(int moonSign) {
        this.moonSign = moonSign;
    }

    //Set default value of remarks to null string
    private String remarks = "";

    public void setInDST(boolean inDST) {
        this.inDST = inDST;
    }

    public boolean isInDST() {
        return inDST;
    }

    public int getDstHours() {
        return dstHours;
    }

    public void setDstHours(int dstHours) {
        this.dstHours = dstHours;
    }

    public int getDstMinutes() {
        return dstMinutes;
    }

    public void setDstMinutes(int dstMinutes) {
        this.dstMinutes = dstMinutes;
    }

    public int getDstSeconds() {
        return dstSeconds;
    }

    public void setDstSeconds(int dstSeconds) {
        this.dstSeconds = dstSeconds;
    }

    public String getTimeZoneDirection() {
        return timeZoneDirection;
    }

    public void setTimeZoneDirection(String timeZoneDirection) {
        this.timeZoneDirection = timeZoneDirection;
    }

    public int getTimeZoneHours() {
        return timeZoneHours;
    }

    public void setTimeZoneHours(int timeZoneHours) {
        this.timeZoneHours = timeZoneHours;
    }

    public int getTimeZoneMinutes() {
        return timeZoneMinutes;
    }

    public void setTimeZoneMinutes(int timeZoneMinutes) {
        this.timeZoneMinutes = timeZoneMinutes;
    }

    public int getTimeZoneSeconds() {
        return timeZoneSeconds;
    }

    public void setTimeZoneSeconds(int timeZoneSeconds) {
        this.timeZoneSeconds = timeZoneSeconds;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getLatitudeDegrees() {
        return latitudeDegrees;
    }

    public void setLatitudeDegrees(int latitudeDegrees) {
        this.latitudeDegrees = latitudeDegrees;
    }

    public String getLatitudeDirection() {
        return latitudeDirection;
    }

    public void setLatitudeDirection(String latitudeDirection) {
        this.latitudeDirection = latitudeDirection;
    }

    public int getLatitudeMinutes() {
        return latitudeMinutes;
    }

    public void setLatitudeMinutes(int latitudeMinutes) {
        this.latitudeMinutes = latitudeMinutes;
    }

    public int getLatitudeSeconds() {
        return latitudeSeconds;
    }

    public void setLatitudeSeconds(int latitudeSeconds) {
        this.latitudeSeconds = latitudeSeconds;
    }

    public int getLongitudeDegrees() {
        return longitudeDegrees;
    }

    public void setLongitudeDegrees(int longitudeDegrees) {
        this.longitudeDegrees = longitudeDegrees;
    }

    public String getLongitudeDirection() {
        return longitudeDirection;
    }

    public void setLongitudeDirection(String longitudeDirection) {
        this.longitudeDirection = longitudeDirection;
    }

    public int getLongitudeMinutes() {
        return longitudeMinutes;
    }

    public void setLongitudeMinutes(int longitudeMinutes) {
        this.longitudeMinutes = longitudeMinutes;
    }

    public int getLongitudeSeconds() {
        return longitudeSeconds;
    }

    public void setLongitudeSeconds(int longitudeSeconds) {
        this.longitudeSeconds = longitudeSeconds;
    }

    public String getMeridian() {
        return meridian;
    }

    public void setMeridian(String meridian) {
        this.meridian = meridian;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getLongitudeAsDecimalDegrees() {
        double longitude = this.longitudeDegrees + this.longitudeMinutes / 60.0 + this.longitudeSeconds / 3600.0;
        if (this.longitudeDirection.equals(BirthData.SOUTH)) {
            longitude = -1 * longitude;
        }
        return longitude;
    }

    public double getLatitudeAsDecimalDegrees() {
        double latitude = this.latitudeDegrees + this.latitudeMinutes / 60.0 + this.latitudeSeconds / 3600.0;
        if (this.latitudeDirection.equals(BirthData.WEST)) {
            latitude = -1 * latitude;
        }
        return latitude;
    }

    public Calendar getDateOfBirth() {
        //Create Calendar at UTC with no daylight saving time schedule
        Calendar dob = Calendar.getInstance(TimeZone.getTimeZone("UTC"), new Locale("", "", ""));
        dob.set(year, month - 1, day);
        return dob;
    }

    public Calendar getBirthTimeAtUTC() {
        int hourOfDay = hours;

        if (meridian.equals(AM) && hourOfDay == 12) {
            hourOfDay = 0;
        } else if (meridian.equals(PM) && (hourOfDay >= 1 && hourOfDay <= 11)) {
            hourOfDay += 12;
        }

        //Create Calendar at UTC with no daylight saving time schedule
        Calendar dob = Calendar.getInstance(TimeZone.getTimeZone("UTC"), new Locale("", "", ""));

        dob.set(year, month - 1, day, hourOfDay, minutes, seconds);

        //deduct DST if any

        dob.add(Calendar.HOUR_OF_DAY, -1 * dstHours);
        dob.add(Calendar.MINUTE, -1 * dstMinutes);
        dob.add(Calendar.SECOND, -1 * dstSeconds);


        //deduct if Timezone is EAST, add if Timezone is WEST, to get UTC time
        int tmz = 1;

        if (timeZoneDirection.equals(EAST)) {
            tmz = -1;
        }

        dob.add(Calendar.HOUR_OF_DAY, tmz * timeZoneHours);
        dob.add(Calendar.MINUTE, tmz * timeZoneMinutes);
        dob.add(Calendar.SECOND, tmz * timeZoneSeconds);

        return dob;
    }

    public String getWeekDay() {
        int hourOfDay = hours;

        if (meridian.equals(AM) && hourOfDay == 12) {
            hourOfDay = 0;
        } else if (meridian.equals(PM) && (hourOfDay >= 1 && hourOfDay <= 11)) {
            hourOfDay += 12;
        }

        //Create Calendar at UTC with no daylight saving time schedule
        Calendar dob = Calendar.getInstance(TimeZone.getTimeZone("UTC"), new Locale("", "", ""));

        dob.set(year, month - 1, day, hourOfDay, minutes, seconds);


        int weekDay = dob.get(Calendar.DAY_OF_WEEK);
        String strWeekDay = null;

        switch (weekDay) {
            case 1:
                strWeekDay = "SUNDAY";
                break;
            case 2:
                strWeekDay = "MONDAY";
                break;
            case 3:
                strWeekDay = "TUESDAY";
                break;
            case 4:
                strWeekDay = "WEDNESDAY";
                break;
            case 5:
                strWeekDay = "THRUSDAY";
                break;
            case 6:
                strWeekDay = "FRIDAY";
                break;
            case 7:
                strWeekDay = "SATURDAY";
                break;
        }
        return strWeekDay;
    }
}

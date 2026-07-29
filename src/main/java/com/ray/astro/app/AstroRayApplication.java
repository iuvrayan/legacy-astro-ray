/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ray.astro.app;

import com.ray.astro.db.LocationDAO;
import com.ray.astro.model.AstroFunctions;
import com.ray.astro.model.BirthData;
import com.ray.astro.model.Ephemeris;
import com.ray.astro.model.House;
import com.ray.astro.model.Planet;
import com.ray.astro.ui.FrmBirthData;
import com.ray.astro.ui.TextChart;
import com.ray.astro.ui.TextToPdfConverter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

/**
 *
 * @author Rayan Ivaturi
 */
public class AstroRayApplication {

    //FrmBirthData
    FrmBirthData frmBirthData;

    //Location Name, Latitude, Longitude Embedded Database DAO
    LocationDAO locationDAO;

    //Output directory where the horoscope is created.
    private String outputTxtDir;
    private String outputPdfDir;
    //private String pdfReaderExe;

    //File names of the output text file, pdf file
    private File textFile;
    private File pdfFile;

    public AstroRayApplication() {
        Properties props = null;
        Properties defaultProps = new Properties();
        String userHome = System.getProperty("user.home"); // Gets C:/Users/Username
        defaultProps.setProperty("output_txt_dir", userHome + "/Desktop/Horoscopes/Txt");
        defaultProps.setProperty("output_pdf_dir", userHome + "/Desktop/Horoscopes/Pdf");
        //defaultProps.setProperty("pdf_reader_exe", ""); // No longer needed!

        try {
            props = new Properties(defaultProps);
            props.load(new FileReader("props/astroray.props"));
            outputTxtDir = props.getProperty("output_txt_dir");
            outputPdfDir = props.getProperty("output_pdf_dir");
            //pdfReaderExe = props.getProperty("pdf_reader_exe");
        } catch (Exception e) {
        } finally {
            if (props != null) {
                outputTxtDir = props.getProperty("output_txt_dir");
                outputPdfDir = props.getProperty("output_pdf_dir");
                //pdfReaderExe = props.getProperty("pdf_reader_exe");
            } else {
                outputTxtDir = defaultProps.getProperty("output_txt_dir");
                outputPdfDir = defaultProps.getProperty("output_pdf_dir");
                //pdfReaderExe = defaultProps.getProperty("pdf_reader_exe");
            }
        }

        //Init LocationDAO
        locationDAO = new LocationDAO();

        //Init FrmBirthData
        frmBirthData = new FrmBirthData(this);

        //show
        frmBirthData.setVisible(true);
    }  

    public LocationDAO getLocationDAO() {
        return locationDAO;
    }

    public String prepareHoroscope(BirthData birthData) throws AstroRayAppException {
        Calendar dobUT = birthData.getBirthTimeAtUTC();

        int year = dobUT.get(Calendar.YEAR);
        int month = dobUT.get(Calendar.MONTH) + 1;
        int day = dobUT.get(Calendar.DAY_OF_MONTH);

        int hour = dobUT.get(Calendar.HOUR_OF_DAY);
        int minute = dobUT.get(Calendar.MINUTE);
        int second = dobUT.get(Calendar.SECOND);

        double hours = (hour + minute / 60.0 + second / 3600.0);

        double latitude = birthData.getLatitudeAsDecimalDegrees();
        double longitude = birthData.getLongitudeAsDecimalDegrees();

        Ephemeris ephemeris = new Ephemeris(year, month, day, hours, latitude, longitude);

        Planet[] arrPlanets = ephemeris.getPlanets();
        if (birthData.isMoonGiven()) {
            arrPlanets[1] = new Planet(AstroFunctions.MOON,
                    birthData.getMoonSign(),
                    birthData.getMoonDegrees(),
                    birthData.getMoonMinutes(),
                    birthData.getMoonSeconds(),
                    false);
        }
        House[] arrHouses = ephemeris.getHouses();
        double siderealTime = ephemeris.getSiderealTime();
        double ayanamsa = ephemeris.getAyanamsa();

        House ascendant = arrHouses[0];
        Planet moon = arrPlanets[1];
        Planet sun = arrPlanets[0];

        //Compute Fortuna
        if (arrPlanets != null && arrHouses != null) {
            arrPlanets[arrPlanets.length - 1] = AstroFunctions.computeFortuna(ascendant, moon, sun);

        } else {
            throw new AstroRayAppException("Ephemeris Error. Planets and Houses could not be created");
        }

        StringBuffer sb = getBaseData(birthData, arrPlanets, arrHouses, siderealTime, ayanamsa);

        //Preapre the Input data for Text Chart
        ArrayList[] lstZodiac = prepareRasiChartData(arrHouses, arrPlanets);
        String rasiChakra = TextChart.getChart(lstZodiac, TextChart.RASI);
        sb.append('\r');
        sb.append('\n');
        sb.append(rasiChakra);
        sb.append("\r\n---------Horoscope Casted by IVATURI SUBBARAYAN");
        sb.append('\r');
        sb.append('\f');

        lstZodiac = prepareAmsaChartData(arrHouses[0], arrPlanets, 9);
        String navamsaChakra = TextChart.getChart(lstZodiac, TextChart.AMSA);
        sb.append('\r');
        sb.append('\n');
        sb.append(navamsaChakra);
        sb.append("\r\n---------Horoscope Casted by IVATURI SUBBARAYAN");
        sb.append('\r');
        sb.append('\f');
        /*
        lstZodiac = prepareAmsaChartData(arrHouses[0], arrPlanets, 3);
        String drekkanam = TextChart.getChart(lstZodiac, TextChart.D3);
        sb.append('\r');
        sb.append('\n');
        sb.append(drekkanam);
        sb.append("\r\n---------Horoscope Casted by IVATURI SUBBARAYAN");
        sb.append('\r');
        sb.append('\f');
         */
        sb.append("\r\nMAHA DASAS : \r\n\r\n");
        String[] mahaDasas = AstroFunctions.getMahaDasas(moon, birthData.getDateOfBirth());

        for (int i = 0; i < mahaDasas.length; i++) {
            sb.append(mahaDasas[i]);
            sb.append('\r');
            sb.append('\n');
        }

        sb.append("\r\nANTAR DASAS : \r\n\r\n");
        String[] antarDasas = AstroFunctions.getAntarDasas(moon, birthData.getDateOfBirth());

        int startIndex = -1;
        for (int i = 0; i < antarDasas.length; i++) {
            if (antarDasas[i] != null) {
                startIndex = i;
                break;
            }
        }

        int middleIndex = (antarDasas.length - startIndex + 1) / 2;

        for (int i = startIndex; i < startIndex + middleIndex; i++) {

            sb.append(antarDasas[i]);

            if ((i + middleIndex) < antarDasas.length && antarDasas[i + middleIndex] != null) {
                sb.append(' ');
                sb.append(' ');
                sb.append(antarDasas[i + middleIndex]);
            }

            sb.append('\r');
            sb.append('\n');
        }
        sb.append("\r\n---------Horoscope Casted by IVATURI SUBBARAYAN");
        sb.append('\r');
        sb.append('\f');

        sb.append("\r\nVIDASAS : \r\n");

        String[] viDasas = AstroFunctions.getViDasas(moon, birthData.getDateOfBirth());
        startIndex = -1;
        for (int i = 0; i < viDasas.length; i++) {
            if (viDasas[i] != null) {
                startIndex = i;
                break;
            }
        }

        int pageCount = (viDasas.length - startIndex) / 100;

        for (int i = 0; i < pageCount; i++) {
            sb.append('\r');
            sb.append('\n');
            for (int j = 0; j < 50; j++) {
                sb.append(viDasas[i * 100 + j + startIndex]);
                sb.append(' ');
                sb.append(' ');
                if (viDasas[i * 100 + 50 + j + startIndex] != null) {
                    sb.append(viDasas[i * 100 + 50 + j + startIndex]);
                }
                sb.append('\r');
                sb.append('\n');
            }
            if (i != (pageCount - 1)) {
                sb.append("\r\n---------Horoscope Casted by IVATURI SUBBARAYAN");
                sb.append('\r');
                sb.append('\f');
            }
        }

        if (viDasas[pageCount * 100 + startIndex] != null) {
            sb.append("\r\n---------Horoscope Casted by IVATURI SUBBARAYAN");
            sb.append('\r');
            sb.append('\f');
            sb.append('\r');
            sb.append('\n');
        }

        for (int i = pageCount * 100 + startIndex; i < viDasas.length; i++) {
            if (viDasas[i] != null) {
                sb.append(viDasas[i]);
                sb.append('\r');
                sb.append('\n');
            }
        }

        sb.append("\r\n---------Horoscope Casted by IVATURI SUBBARAYAN");
        sb.append('\r');
        sb.append('\f');

        sb.append('\r');
        sb.append('\n');
        sb.append("REMARKS: \r\n\r\n");
        sb.append(birthData.getRemarks());

        //Save the data into a file
        DecimalFormat df = new DecimalFormat("00");
        String prefix = birthData.getName().trim().toUpperCase().replace(' ', '_') + "_" + df.format(birthData.getDay()) +
                df.format(birthData.getMonth()) + birthData.getYear();


        File txtDir = new File(outputTxtDir);
        boolean isTxtDirExists = true;

        //Check if the output txt directory exists or not and otherwise try to create
        if (!txtDir.exists()) {
            isTxtDirExists = txtDir.mkdirs();
        }

        //if dir(s) are not created, then create the file in current dir
        if (isTxtDirExists) {
            //textFile variable is defined as class variable
            textFile = new File(outputTxtDir + "/" + prefix + ".txt");
        } else {
            textFile = new File(prefix + ".txt");
        }

        //Write to text file
        try {
            FileWriter fileWriter = new FileWriter(textFile);
            fileWriter.write(sb.toString());
            fileWriter.close();
        } catch (IOException ioe) {
            throw new AstroRayAppException("Error occured while Writing output to Text File\n" + textFile.getAbsolutePath());
        }

        //Create pdf file
        File pdfDir = new File(outputPdfDir);
        boolean isPdfDirExists = true;

        //Check if the output pdf directory exists or not and otherwise try to create

        if (!pdfDir.exists()) {
            isPdfDirExists = pdfDir.mkdirs();
        }

        //if dir(s) are not created, then create the file in current dir
        if (isPdfDirExists) {
            //pdfFile variable is defined as class variable
            pdfFile = new File(outputPdfDir + "/" + prefix + ".pdf");
        } else {
            pdfFile = new File(prefix + ".pdf");
        }

        //Convert text file to pdf file
        try {
            TextToPdfConverter.convert(textFile.getAbsolutePath(), pdfFile.getAbsolutePath());
        } catch (Exception e) {
            throw new AstroRayAppException("Error occured while Writing output to Pdf File\n " + e.getMessage());
        }

        return "Horoscope Generated Successfully\n" + pdfFile.getAbsolutePath();
    }

    public void viewPdfFile() throws Exception {
        if (java.awt.Desktop.isDesktopSupported() && java.awt.Desktop.getDesktop().isSupported(java.awt.Desktop.Action.OPEN)) {
            java.awt.Desktop.getDesktop().open(pdfFile);
        } else {
            // Truly cross-platform fallback just in case Desktop isn't supported
            String os = System.getProperty("os.name").toLowerCase();
            Runtime rt = Runtime.getRuntime();
            
            if (os.contains("win")) {
                rt.exec("cmd /c start \"\" \"" + pdfFile.getAbsolutePath() + "\"");
            } else if (os.contains("mac")) {
                rt.exec("open \"" + pdfFile.getAbsolutePath() + "\"");
            } else if (os.contains("nix") || os.contains("nux")) {
                rt.exec("xdg-open \"" + pdfFile.getAbsolutePath() + "\"");
            }
        }
    }

    ArrayList[] prepareRasiChartData(House[] arrHouses, Planet[] arrPlanets) {
        //Declare an array of ArryList
        ArrayList[] lstZodiac = new ArrayList[12];

        //Initialize each ArrayList
        for (int i = 0; i < lstZodiac.length; i++) {
            lstZodiac[i] = new ArrayList();
        }

        for (int i = 0; i < arrHouses.length; i++) {
            House house = arrHouses[i];
            int sign = house.getSign();
            lstZodiac[sign].add(house.getDegreesAndMinutesAsString());
        }

        for (int i = 0; i < arrPlanets.length; i++) {
            Planet planet = arrPlanets[i];
            int sign = planet.getSign();
            lstZodiac[sign].add(planet.getDegreesAndMinutesAsString());
        }

        return lstZodiac;
    }

    ArrayList[] prepareAmsaChartData(House ascendant, Planet[] arrPlanets, int amsa) {
        ArrayList[] lstZodiac = new ArrayList[12];
        for (int i = 0; i < lstZodiac.length; i++) {
            lstZodiac[i] = new ArrayList();
        }

        //Add Ascendant first
        int navamsaSign = AstroFunctions.getAmsaSign(ascendant, amsa);
        lstZodiac[navamsaSign].add(ascendant.getName());

        for (int i = 0; i < arrPlanets.length; i++) {
            Planet planet = arrPlanets[i];
            navamsaSign = AstroFunctions.getAmsaSign(planet, amsa);
            lstZodiac[navamsaSign].add(planet.getName());
        }

        return lstZodiac;
    }

    StringBuffer getBaseData(BirthData birthData, Planet[] arrPlanets, House[] arrHouses, double siderealTime, double ayanamsa) {

        Planet sun = arrPlanets[0];
        Planet moon = arrPlanets[1];

        DecimalFormat format4d = new DecimalFormat("0000");
        DecimalFormat format3d = new DecimalFormat("000");
        DecimalFormat format2d = new DecimalFormat("00");

        StringBuffer sb = new StringBuffer();

        /*
        for (int i=0; i<25; i++) {
            sb.append('\r');
            sb.append('\n');
        }

        sb.append("Horoscope of ");
        sb.append(birthData.getName().toUpperCase());

        for (int i=0; i<25; i++) {
            sb.append('\r');
            sb.append('\n');
        }

        sb.append("\r\n---------Horoscope Casted by IVATURI SUBBARAYAN");
        sb.append('\r');
        sb.append('\f');

        sb.append("\r\nNAME           : ");
        */

        sb.append("HOROSCOPE OF   : ");
        sb.append(birthData.getName().toUpperCase());
        sb.append('\r');
        sb.append('\n');
        sb.append('\r');
        sb.append('\n');

        sb.append("DATE  OF BIRTH : ");
        sb.append(format2d.format(birthData.getDay()));
        sb.append('-');
        sb.append(format2d.format(birthData.getMonth()));
        sb.append('-');
        sb.append(format4d.format(birthData.getYear()));
        sb.append(' ');
        sb.append(birthData.getWeekDay());
        sb.append('\r');
        sb.append('\n');

        sb.append("TIME  OF BIRTH : ");
        sb.append(format2d.format(birthData.getHours()));
        sb.append(':');
        sb.append(format2d.format(birthData.getMinutes()));
        sb.append(':');
        sb.append(format2d.format(birthData.getSeconds()));
        sb.append(' ');
        sb.append(birthData.getMeridian());
        sb.append(' ');
        if (birthData.isInDST()) {
            sb.append("(in DST)");
        }
        sb.append('\r');
        sb.append('\n');


        sb.append("TIME ZONE      : ");
        sb.append("GMT");
        if (birthData.getTimeZoneDirection().equals(BirthData.EAST)) {
            sb.append(" + ");
        } else {
            sb.append(" - ");
        }
        sb.append(format2d.format(birthData.getTimeZoneHours()));
        sb.append(':');
        sb.append(format2d.format(birthData.getTimeZoneMinutes()));
        sb.append(':');
        sb.append(format2d.format(birthData.getTimeZoneSeconds()));
        sb.append('\r');
        sb.append('\n');
        sb.append('\r');
        sb.append('\n');

        sb.append("PLACE OF BIRTH : ");
        sb.append(birthData.getLocationName().toUpperCase());
        sb.append('\r');
        sb.append('\n');

        sb.append("LATITUDE       : ");
        sb.append(format2d.format(birthData.getLatitudeDegrees()));
        sb.append(':');
        sb.append(format2d.format(birthData.getLatitudeMinutes()));
        sb.append(':');
        sb.append(format2d.format(birthData.getLatitudeSeconds()));
        sb.append(' ');
        sb.append(birthData.getLatitudeDirection());
        sb.append('\r');
        sb.append('\n');

        sb.append("LONGITUDE      : ");
        sb.append(format3d.format(birthData.getLongitudeDegrees()));
        sb.append(':');
        sb.append(format2d.format(birthData.getLongitudeMinutes()));
        sb.append(':');
        sb.append(format2d.format(birthData.getLongitudeSeconds()));
        sb.append(' ');
        sb.append(birthData.getLongitudeDirection());
        sb.append('\r');
        sb.append('\n');
        sb.append('\r');
        sb.append('\n');

        sb.append("AYANAMSA       : ");
        House objAyanamsa = new House("AYANAMSA", ayanamsa);
        sb.append(format2d.format(objAyanamsa.getSign()));
        sb.append(':');
        sb.append(format2d.format(objAyanamsa.getDegrees()));
        sb.append(':');
        sb.append(format2d.format(objAyanamsa.getMinutes()));
        sb.append(':');
        sb.append(format2d.format(objAyanamsa.getSeconds()));
        sb.append('\r');
        sb.append('\n');

        sb.append("SIDEREAL TIME  : ");

        double hours = siderealTime / 15.0;
        int seconds = (int) Math.round(hours * 3600);
        Calendar st = Calendar.getInstance(TimeZone.getTimeZone("UTC"), new Locale("", "", ""));
        st.set(0, 0, 0, 0, 0, 0);
        st.add(Calendar.SECOND, seconds);

        sb.append(format2d.format(st.get(Calendar.HOUR_OF_DAY)));
        sb.append(':');
        sb.append(format2d.format(st.get(Calendar.MINUTE)));
        sb.append(':');
        sb.append(format2d.format(st.get(Calendar.SECOND)));
        sb.append(" HRS");
        sb.append('\r');
        sb.append('\n');
        sb.append('\r');
        sb.append('\n');

        sb.append("STAR           : ");
        sb.append(AstroFunctions.STAR[moon.getStar()].trim().toUpperCase());
        sb.append(' ');
        sb.append((moon.getStarQuarter() + 1));
        sb.append(" PADA");
        sb.append('\r');
        sb.append('\n');
        sb.append("RASI           : ");
        sb.append(AstroFunctions.SIGN[moon.getSign()].toUpperCase());
        sb.append('\r');
        sb.append('\n');

        sb.append("TITHI          : ");
        sb.append(AstroFunctions.TITHI[AstroFunctions.getTithi(moon, sun)].toUpperCase());
        sb.append('\r');
        sb.append('\n');
        sb.append('\r');
        sb.append('\n');

        sb.append("ASCENDANT      : ");
        sb.append(AstroFunctions.SIGN[arrHouses[0].getSign()].toUpperCase());
        sb.append('\r');
        sb.append('\n');
        sb.append('\r');
        sb.append('\n');

        sb.append("PLANETARY & HOUSE POSITIONS : \r\n\r\n");
        sb.append("PLANET  POSITION      STAR         PADAM  STARLORD  SUBLORD\r\n");


        for (int i = 0; i < arrPlanets.length; i++) {
            sb.append(arrPlanets[i].getAsString().toUpperCase());
            sb.append('\r');
            sb.append('\n');
        }
        sb.append('\r');
        sb.append('\n');

        sb.append("HOUSE   POSITION      STAR         PADAM  STARLORD  SUBLORD\r\n");


        for (int i = 0; i < arrHouses.length; i++) {
            sb.append(arrHouses[i].getAsString().toUpperCase());
            sb.append('\r');
            sb.append('\n');
        }
        sb.append('\r');
        sb.append('\n');

        sb.append(AstroFunctions.DASA[moon.getStarLord()].toUpperCase());
        sb.append(" DASA BALANCE AT THE TIME OF BIRTH : ");

        seconds = (int) Math.round(moon.getDasaBalance() * 365.24219 * 24 * 60 * 60);
        
        Calendar dasaBalanceDate = birthData.getDateOfBirth();
        dasaBalanceDate.add(Calendar.SECOND, seconds);
        
        /*
        int years = seconds / (365 * 24 * 60 * 60);
        int months = (seconds % (365 * 24 * 60 * 60)) / (30 * 24 * 60 * 60);
        int days = ((seconds % (365 * 24 * 60 * 60)) % (30 * 24 * 60 * 60)) / (24 * 60 * 60);
        */
        String dasaBalanaceString = AstroFunctions.getDifferenceBetweenDatesAsString(birthData.getDateOfBirth(), dasaBalanceDate);
        sb.append(dasaBalanaceString);
        
        /*
        sb.append(years);
        sb.append(" YEARS ");

        sb.append(months);
        sb.append(" MONTHS ");

        sb.append(days);
        sb.append(" DAYS ");
        */
        
        sb.append('\r');
        sb.append('\n');
        sb.append('\r');
        sb.append('\n');
        sb.append("\r\n---------Horoscope Casted by IVATURI SUBBARAYAN");
        sb.append('\r');
        sb.append('\f');

        return sb;
    }

    public static void main(String[] args) {
        new AstroRayApplication();
    }
}

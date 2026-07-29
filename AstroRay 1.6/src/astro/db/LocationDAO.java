/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astro.db;

import astro.main.AstroRayAppException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author Family
 */
public class LocationDAO {

    private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private String protocol = "jdbc:derby:";
    private String dbName = "geo";
    private Connection con;

    public void initDB() throws AstroRayAppException {
        /*
         *  The JDBC driver is loaded by loading its class.
         *  If you are using JDBC 4.0 (Java SE 6) or newer, JDBC drivers may
         *  be automatically loaded, making this code optional.
         *
         *  In an embedded environment, this will also start up the Derby
         *  engine (though not any databases), since it is not already
         *  running. In a client environment, the Derby engine is being run
         *  by the network server framework.
         *
         *  In an embedded environment, any static Derby system properties
         *  must be set before loading the driver to take effect.
         */
        try {
            //Load the Driver
            Class.forName(driver).newInstance();

            //Initialize the Connection
            con = DriverManager.getConnection(protocol + dbName);

        } catch (ClassNotFoundException cnfe) {
            throw new AstroRayAppException("Unable to load the JDBC driver " + driver);
        } catch (InstantiationException ie) {
            throw new AstroRayAppException("Unable to load the JDBC driver " + driver);
        } catch (IllegalAccessException iae) {
            throw new AstroRayAppException("Not allowed to access the JDBC driver " + driver);
        } catch (SQLException sqle) {
            throw new AstroRayAppException("Unable to get Database Connection " + driver);
        }
    }

    public void closeDB() throws AstroRayAppException {
        try {
            if (con != null) {
                con.close();
                con = null;
            }
        } catch (SQLException sqle) {
            throw new AstroRayAppException("Unable to close the Database");
        }
    }

    public void addLocation(String location, String latitude, String longitude) throws AstroRayAppException {
        PreparedStatement pstmt = null;

        try {
            if (con == null) {
                con = DriverManager.getConnection(protocol + dbName);
            }

            pstmt = con.prepareStatement("INSERT INTO tbl_location (latitude, longitude, location) VALUES(?, ?, ?)");

            pstmt.setString(1, latitude);
            pstmt.setString(2, longitude);
            pstmt.setString(3, location);

            pstmt.executeUpdate();

        } catch (SQLException sqle) {
            throw new AstroRayAppException("Unable to add " + location + " to the Database " + sqle);
        } finally {
            // release all open resources to avoid unnecessary memory usage

            // Statement
            try {
                if (pstmt != null) {
                    pstmt.close();
                    pstmt = null;
                }
            } catch (SQLException sqle) {
                throw new AstroRayAppException("Unable to close the Database Statement");
            }
        }
    }

    public void modifyLocation(String oldLocation, String newLocation, String latitude, String longitude) throws AstroRayAppException {
        PreparedStatement pstmt = null;

        try {
            if (con == null) {
                con = DriverManager.getConnection(protocol + dbName);
            }

            pstmt = con.prepareStatement("UPDATE tbl_location SET latitude=?, longitude=?, location=? WHERE location=?");

            pstmt.setString(1, latitude);
            pstmt.setString(2, longitude);
            pstmt.setString(3, newLocation);
            pstmt.setString(4, oldLocation);

            pstmt.executeUpdate();

        } catch (SQLException sqle) {
            throw new AstroRayAppException("Unable to update " + oldLocation + " in the Database " + sqle);
        } finally {
            // release all open resources to avoid unnecessary memory usage

            // Statement
            try {
                if (pstmt != null) {
                    pstmt.close();
                    pstmt = null;
                }
            } catch (SQLException sqle) {
                throw new AstroRayAppException("Unable to close the Database Statement");
            }
        }
    }

    public void deleteLocation(String location) throws AstroRayAppException {
        PreparedStatement pstmt = null;

        try {
            if (con == null) {
                con = DriverManager.getConnection(protocol + dbName);
            }

            pstmt = con.prepareStatement("DELETE FROM tbl_location WHERE location=?");

            pstmt.setString(1, location);

            pstmt.executeUpdate();

        } catch (SQLException sqle) {
            throw new AstroRayAppException("Unable to delete " + location + " from the Database " + sqle);
        } finally {
            // release all open resources to avoid unnecessary memory usage

            // Statement
            try {
                if (pstmt != null) {
                    pstmt.close();
                    pstmt = null;
                }
            } catch (SQLException sqle) {
                throw new AstroRayAppException("Unable to close the Database Statement");
            }
        }
    }

    public String[] getLatitudeAndLongitude(String location) throws AstroRayAppException {
        Statement stmt = null;
        ResultSet rs = null;
        String[] latitudeAndLongitue = null;

        try {
            if (con == null) {
                con = DriverManager.getConnection(protocol + dbName);
            }
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT latitude, longitude FROM tbl_location WHERE location='" + location + "'");


            if (rs.next()) {
                latitudeAndLongitue = new String[2];
                latitudeAndLongitue[0] = rs.getString(1);
                latitudeAndLongitue[1] = rs.getString(2);
            }

            return latitudeAndLongitue;
        } catch (SQLException sqle) {
            throw new AstroRayAppException("Unable to execute Select query from the Database");
        } finally {
            // release all open resources to avoid unnecessary memory usage

            // ResultSet
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            } catch (SQLException sqle) {
                throw new AstroRayAppException("Unable to close the Resultset");
            }

            // Statement
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            } catch (SQLException sqle) {
                throw new AstroRayAppException("Unable to close the Database Statement");
            }
        }
    }

    public Vector getAllLocations() throws AstroRayAppException {
        Statement stmt = null;
        ResultSet rs = null;
        Vector vecLocations = new Vector();

        try {
            if (con == null) {
                con = DriverManager.getConnection(protocol + dbName);
            }
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT location FROM tbl_location ORDER BY location");


            while (rs.next()) {
                vecLocations.add(rs.getString(1));
            }

            if (vecLocations.isEmpty()) {
                vecLocations = null;
            } else {
                vecLocations.trimToSize();
            }

            return vecLocations;
            
        } catch (SQLException sqle) {
            throw new AstroRayAppException("Unable to execute Select query from the Database");
        } finally {
            // release all open resources to avoid unnecessary memory usage

            // ResultSet
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            } catch (SQLException sqle) {
                throw new AstroRayAppException("Unable to close the Resultset");
            }

            // Statement
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            } catch (SQLException sqle) {
                throw new AstroRayAppException("Unable to close the Database Statement");
            }
        }
    }

    public Vector getMatchingLocations(String searchString) throws AstroRayAppException {
        Statement stmt = null;
        ResultSet rs = null;
        Vector vecLocations = new Vector();

        try {
            if (con == null) {
                con = DriverManager.getConnection(protocol + dbName);
            }
            stmt = con.createStatement();

            String query = null;
            if (searchString.contains("%")) {
                query = "SELECT location FROM tbl_location WHERE location LIKE '" + searchString + "'";
            } else {
                query = "SELECT location FROM tbl_location WHERE location = '" + searchString + "'";
            }

            rs = stmt.executeQuery(query);


            while (rs.next()) {
                vecLocations.add(rs.getString(1));
            }

            if (vecLocations.isEmpty()) {
                vecLocations = null;
            } else {
                vecLocations.trimToSize();
            }

            return vecLocations;
            
        } catch (SQLException sqle) {
            throw new AstroRayAppException("Unable to execute Select query from the Database");
        } finally {
            // release all open resources to avoid unnecessary memory usage

            // ResultSet
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            } catch (SQLException sqle) {
                throw new AstroRayAppException("Unable to close the Resultset");
            }

            // Statement
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            } catch (SQLException sqle) {
                throw new AstroRayAppException("Unable to close the Database Statement");
            }
        }
    }
}
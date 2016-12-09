package com.belk.pep.app;

import com.belk.pep.config.ConnectionFactory;
import com.belk.pep.constants.ImageDeleteConstants;
import com.belk.pep.util.PropertyLoader;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DeleteImage {

    @SuppressWarnings("serial")
    private static List<String> envTypes = new ArrayList<String>() {
        {
            add(ImageDeleteConstants.DEV);
            add(ImageDeleteConstants.SIT);
            add(ImageDeleteConstants.QA);
            add(ImageDeleteConstants.PERF);
            add(ImageDeleteConstants.PROD);
        }
    };

    private static Properties prop = PropertyLoader
            .getPropertyLoader(ImageDeleteConstants.LOAD_DELETE_IMAGE_PROPERTY_FILE);

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(DeleteImage.class.getName());

    public static void main(String[] args) {
        String env = null;
        if (args != null && args.length >= 1) {
            env = args[0];
        }
        LOGGER.debug("----env value being passed---- " + env);
        if (env == null || !isEnvValid(env)) {
            LOGGER.error("Please specify valid environment.");
            return;
        }

        DeleteImage deleteImage = new DeleteImage();
        LOGGER.debug("----Started Processing Image Delete----");
        deleteImage.removeImage(env.toLowerCase());
        LOGGER.debug("----Completed Processing Image Delete----");
    }// end main

    /**
     * This method reads image records from IMAGE_SOFT_DELETE table, then performs hard-delete of corresponding image.
     */
    public void removeImage(String env) {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        String sql = "";
        String fileToBeDeleted = "";
        boolean fileRemove;
        String fileDir = "";
        try {
            connection = ConnectionFactory.getConnection(env);
            if (connection != null) {
                LOGGER.debug("Creating statement...");
                stmt = connection.createStatement();
                stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

                fileDir = prop.getProperty(env + "." + ImageDeleteConstants.FILEDIR);

                sql = "select image_name, delete_status from IMAGE_SOFT_DELETE where delete_status = 'N'";
                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    String image_name = rs.getString("image_name");
                    if (image_name == null)
                        continue;
                    image_name = image_name.trim();

                    fileToBeDeleted = fileDir + image_name;
                    LOGGER.debug("fileToBeDeleted ::::::: " + fileToBeDeleted);
                    fileRemove = fileDelete(fileToBeDeleted);
                    LOGGER.debug("fileRemove::" + fileRemove);
                    if (fileRemove) {
                        rs.updateString("delete_status", "Y");
                        rs.updateRow();
                    } else {
                        rs.updateString("delete_status", "F");
                        rs.updateRow();
                    }

                }
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while trying to remove image! ", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se) {
                LOGGER.error("Error occurred while closing statement/rs ", se);
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                LOGGER.error("Error occurred while closing connection ", se);
            }
        }
    }

    /**
     * This method performs physical delete of image.
     */
    public boolean fileDelete(String fileName) throws IOException, FileNotFoundException {
        LOGGER.debug("Entering....fileDelete");
        boolean fileDelete = false;
        File file = new File(fileName);
        try {
            if (file.exists()) {
                LOGGER.debug("Exist****");
                if (file.delete()) {
                    LOGGER.debug("File Deleted");
                    fileDelete = true;
                }
            } else {
                LOGGER.debug("File not Deleted");
                fileDelete = false;
            }

        } catch (Exception ex) {
            LOGGER.error("Error occurred while deleting image " + fileName,  ex);
        }
        LOGGER.debug("Exiting....fileDelete");
        return fileDelete;
    }

    /**
     * This method checks if env argument is valid.
     */
    private static boolean isEnvValid(String env) {

        return envTypes.contains(env);
    }
}
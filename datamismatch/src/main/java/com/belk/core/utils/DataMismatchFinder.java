package com.belk.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class DataMismatchFinder {

	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";	
//	private static final String DB_CONNECTION =  "jdbc:oracle:thin:@" + "000PIMPMDBDV02.belkinc.com" + ":" + "1521" + "/" + "VPORTDV";
//	private static final String DB_USER = "VENDORPORTAL";
//	private static final String DB_PASSWORD = "vendorportal";

	public static void main(String[] args) {
		

		DataMismatchFinder dataMismatchFinder = new DataMismatchFinder();		
		
		Properties props = dataMismatchFinder.loadProperties();		
		
		ADSEOracleTablesCountFetcher aDSEOracleTablesCountFetcher = new ADSEOracleTablesCountFetcher(props);
		
		Map<String,String> oracleTablesCountMap=null;
		Map<String,String> db2TablesCountMap=null;
		
		Map<String,String> outputOracleTablesCountMap=new LinkedHashMap<String,String>();
		Map<String,String> outputDb2TablesCountMap=new LinkedHashMap<String,String>();
		
		try {
			 oracleTablesCountMap = aDSEOracleTablesCountFetcher.getDataFromOracleTables();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		PimDb2TablesCountFetcher pimDb2TablesCountFetcher = new PimDb2TablesCountFetcher(props);
//		
		
		try {
			 db2TablesCountMap = pimDb2TablesCountFetcher.getDataFromDb2Tables();
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		if(oracleTablesCountMap !=null && db2TablesCountMap!=null && db2TablesCountMap.size()>0 && oracleTablesCountMap.size() > 0) {
			
			for (Map.Entry<String, String> oracleTblEntry : oracleTablesCountMap.entrySet()) {
				
				
				
				if(db2TablesCountMap.containsKey(oracleTblEntry.getKey())) {
					
					if(!oracleTblEntry.getValue().equals(db2TablesCountMap.get(oracleTblEntry.getKey())) ) {
						outputOracleTablesCountMap.put(oracleTblEntry.getKey(), oracleTblEntry.getValue());
						outputDb2TablesCountMap.put(oracleTblEntry.getKey(), db2TablesCountMap.get(oracleTblEntry.getKey()));
					} 
				}
				
			}
		
		}
		
		
		for (Map.Entry<String, String> oracleTblEntry : outputOracleTablesCountMap.entrySet()) {
			
			System.out.println("oracle Key : " + oracleTblEntry.getKey() + " oracle Value : " + oracleTblEntry.getValue());
			
		}
		
		for (Map.Entry<String, String> db2TblEntry : outputDb2TablesCountMap.entrySet()) {
			
			System.out.println("db2 Key : " + db2TblEntry.getKey() + " db2 Value : " + db2TblEntry.getValue());
			
		}
		
		SendEmail sendEmail = new SendEmail(outputOracleTablesCountMap,outputDb2TablesCountMap);
		
		try {
			
			if(outputOracleTablesCountMap!=null && outputDb2TablesCountMap!=null && outputOracleTablesCountMap.size()>0 && outputDb2TablesCountMap.size()>0)   {
				
				String hostname=dataMismatchFinder.getHostName();
				
				String env="";
				
				
				if(hostname!=null && !"".equals(hostname)) {
					
					if(hostname.toUpperCase().contains("SIT02")) {
						env= "SIT";
					} else if(hostname.toUpperCase().contains("DV")) { 
						env= "DEV";
					} else if(hostname.toUpperCase().contains("PROD")) { 
						env= "PROD";
					}
				}
				
				sendEmail.sendEmail(props.getProperty("mailTo"),props.getProperty("mailCc"), props.getProperty("mailFrom"),   env + "- Mismatch Data","",props.getProperty("mailSmtpHost"));
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	
	private String getHostName() {
		
		InetAddress ip;
        String hostname = null;
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            System.out.println("Your current IP address : " + ip);
            System.out.println("Your current Hostname : " + hostname);
 
        } catch (UnknownHostException e) {
 
            e.printStackTrace();
        }
        
        return hostname;
	}

	private void selectRecordsFromDbUserTable(Properties props) throws SQLException {

		Connection dbConnection = null;
		Statement statement = null;

		String selectTableSQL = "SELECT BELK_ID, BELK_EMAIL from BELK_USER";

		try {
			dbConnection = getDBConnection(props);
			statement = dbConnection.createStatement();

			System.out.println(selectTableSQL);

			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);

			while (rs.next()) {

				String userid = rs.getString("BELK_ID");
				String username = rs.getString("BELK_EMAIL");

				System.out.println("userid : " + userid);
				System.out.println("username : " + username);

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

//	private static Connection getDBConnection() {
//
//		Connection dbConnection = null;
//
//		try {
//
//			Class.forName(DB_DRIVER);
//
//		} catch (ClassNotFoundException e) {
//
//			System.out.println(e.getMessage());
//
//		}
//
//		try {
//			
//			System.out.println("Connecting to " + DB_CONNECTION );
//
//			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
//					DB_PASSWORD);
//			return dbConnection;
//
//		} catch (SQLException e) {
//
//			System.out.println(e.getMessage());
//
//		}
//
//		return dbConnection;
//
//	}
	
	
	private static Connection getDBConnection(Properties props) {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {
			
			System.out.println(e.getMessage());

		}

		try {
			
			String dburl= "jdbc:oracle:thin:@" + props.getProperty("dbHost").trim() + ":" + props.getProperty("dbPort").trim() + "/" + props.getProperty("dbServiceName").trim() ; 			
			System.out.println("Connecting to " + dburl );

			dbConnection = DriverManager.getConnection(dburl, props.getProperty("dbUserName").trim(),props.getProperty("dbPassword").trim());			
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}
	
	
	
	
	
	private Properties loadProperties() {

		//to load application's properties, we use this class
	    Properties dataMismatchFinderProperties = new Properties();

		try {
			
			
			File directory = new File (".");
			
			System.out.println(" Reading properties file from the path jarPath 1 =>"+ directory.getCanonicalPath());
			System.out.println(" Reading properties file from the path jarPath 2=>"+ directory.getPath());
			 
			
			boolean isfileExists = new File(directory, "datamismatchfinder.properties").exists();
			 
			if(!isfileExists) {
				System.out.println("Please check, datamismatchfinder.properties file is missing in the directory =>"+ directory.getAbsolutePath());				
				//System.exit(1);
				
				System.out.println("WARNING!!! PROPERTIES not FOUND , So reading the default properties from the resource fodler within the project");
				ClassLoader classLoader = getClass().getClassLoader();
				File file = new File(classLoader.getResource("datamismatchfinder.properties").getFile());
				
				dataMismatchFinderProperties.load(new FileInputStream(file));
				
			}	else {
				
				 dataMismatchFinderProperties.load(new FileInputStream("./datamismatchfinder.properties"));
			}
	        	        
	       
	        
	        System.out.println("Oracle dbHost:" + dataMismatchFinderProperties.getProperty("dbHost"));
			System.out.println("Oracle dbPort:" + dataMismatchFinderProperties.getProperty("dbPort"));
			System.out.println("Oracle dbServiceName:" +  dataMismatchFinderProperties.getProperty("dbServiceName"));
			System.out.println("Oracle dbUserName:" +  dataMismatchFinderProperties.getProperty("dbUserName"));

		    System.out.println(" mailSmtpHost:" + dataMismatchFinderProperties.getProperty("mailSmtpHost"));
			System.out.println(" mailTo:" + dataMismatchFinderProperties.getProperty("mailTo"));
			System.out.println(" mailCc:" +  dataMismatchFinderProperties.getProperty("mailCc"));
			System.out.println(" mailFrom:" +  dataMismatchFinderProperties.getProperty("mailFrom"));
			
			System.out.println("Db2 dbHost:" + dataMismatchFinderProperties.getProperty("db2Host"));
			System.out.println("Db2 dbPort:" + dataMismatchFinderProperties.getProperty("db2Port"));			
			System.out.println("Db2 dbUserName:" +  dataMismatchFinderProperties.getProperty("db2UserName"));


		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			
		}

		return dataMismatchFinderProperties;
	}

	

}

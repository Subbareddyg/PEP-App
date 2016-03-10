package com.belk.core.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ADSEOracleTablesCountFetcher  {	
	
	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	
	private Properties props;
	
	ADSEOracleTablesCountFetcher(Properties props) {
		this.props=props;
	}
	
	ADSEOracleTablesCountFetcher() {
		
	}
	
	
	public Map<String,String> getDataFromOracleTables() throws SQLException {
		
		
		Connection dbConnection = null;
		Statement statement = null;

		String selectTableSQL = buildSqlQuery();
		
		List<String> columnNames = new ArrayList<String>();
		
		Map<String,String> adseTablesCountMap = new LinkedHashMap<String,String>();

		try {
			dbConnection = getDBConnection(props);
			statement = dbConnection.createStatement();

			// execute select SQL statement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			
			if (rs != null) {
		        ResultSetMetaData columns = rs.getMetaData();
		        int i = 0;
		        while (i < columns.getColumnCount()) {
		          i++;
		          
		          columnNames.add(columns.getColumnName(i));
		        }
		       

		        while (rs.next()) {
		          for (i = 0; i < columnNames.size(); i++) {
		        	  
		        	String columnName= columnNames.get(i);
		        	String columnValue= rs.getString(columnNames.get(i));
		        	
		        	System.out.print("$$$$ key = " + columnName + " value= " + columnValue  + "\n");
		        	  
		            //System.out.print("$$$$ key = " + columnNames.get(i) + " value= " + rs.getString(columnNames.get(i))  + "\n");
		            adseTablesCountMap.put(columnName, columnValue);

		          }
		          System.out.print("\n");
		        }

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
		
		return adseTablesCountMap;
		
	}
	
	private String buildSqlQuery() {
		
		StringBuilder sb = new StringBuilder();
		
		List<String> lstAdseTables= getADSETableNames();
		
		sb.append("select");
		sb.append("\n");
		
		int count=0;
		
		for(String tbl : lstAdseTables) {
			count++;
			
			if(count!= lstAdseTables.size()) {
				sb.append("(select count(*) from " + tbl + ")" + " as " + tbl +  ",");
			} else {
				sb.append("(select count(*) from " + tbl + ")" + " as " + tbl );
			}
			
			
		}
		
		sb.append("\n");
		sb.append("from dual");
		
		System.out.println("ADSE tables SqlQuery  => " + sb.toString());
		
		
		return sb.toString();
		
		
//		select     
//		  (select count(*) from BELK_USER ) as tabA,
//		  (select count(*) from PEP_DEPARTMENT ) as tabB
//		  
//		from dual;

		
		
		
	}
	
	
	private List<String> getADSETableNames() {
		
		List<String> arrTables= new ArrayList<String>();
		
		arrTables.add("ADSE_454_CALENDAR_HIERARCHY");
		arrTables.add("ADSE_454CALENDAR_CATALOG");
		arrTables.add("ADSE_ATTRIBUTE_DICTIONARY");
		arrTables.add("ADSE_BM_ATTRIBUTE_XRF");
		arrTables.add("ADSE_CFA_CATALOG");
		arrTables.add("ADSE_CFA_TYPE_HIERARCHY");
		arrTables.add("ADSE_DATA_DICTIONARY_TYPE");
		arrTables.add("ADSE_GROUP_CATALOG");
		
		arrTables.add("ADSE_GROUP_TYPE_HIERARCHY");
		arrTables.add("ADSE_ITEM_CATALOG");
		arrTables.add("ADSE_ITEM_PRIMARY_HIERARCHY");
		arrTables.add("ADSE_ITEM_TYPE_HIERARCHY");
		
		arrTables.add("ADSE_LOCATION_CATALOG");
		arrTables.add("ADSE_LOCATION_TYPE_HIERARCHY");
		arrTables.add("ADSE_MERCHANDISE_HIERARCHY");
		arrTables.add("ADSE_ORGANIZATION_HIERARCHY");
		
		arrTables.add("ADSE_PET_CATALOG");
		arrTables.add("ADSE_PET_TYPE_HIERARCHY");
		arrTables.add("ADSE_REFERENCE_DATA");
		arrTables.add("ADSE_REFERENCE_DATA_HIERARCHY");
		arrTables.add("ADSE_SIZEXRF_CATALOG");
		
		arrTables.add("ADSE_SIZEXRF_HIERARCHY");
		arrTables.add("ADSE_SUPPLIER_CATALOG");		
		arrTables.add("ADSE_SUPPLIER_GROUP_HIERARCHY");
		
		arrTables.add("ADSE_SUPPLIER_TYPE_HIERARCHY");
		arrTables.add("ADSE_UDA_CATALOG");
		arrTables.add("ADSE_UDA_TYPE_HIERARCHY");
		arrTables.add("ADSE_VENDOR_OMNISIZE_DESC");
		
		
		
		return arrTables;
		
	}
	
	
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
	
	
	public static void main(String args[]) {
		
		ADSEOracleTablesCountFetcher aDSEOracleTablesCountFetcher = new ADSEOracleTablesCountFetcher();
		
		aDSEOracleTablesCountFetcher.buildSqlQuery();
		
	}
	
	

}

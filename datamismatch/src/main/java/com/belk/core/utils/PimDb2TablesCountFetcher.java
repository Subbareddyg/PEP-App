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

public class PimDb2TablesCountFetcher {
	
		
	private static final String DB2_DRIVER = "com.ibm.db2.jcc.DB2Driver";
	
	private Properties props;
	
	
	PimDb2TablesCountFetcher(Properties props) {
		this.props=props;
	}
	
	PimDb2TablesCountFetcher() {
		
	}
	
	
public Map<String,String> getDataFromDb2Tables() throws SQLException {
		
		
		Connection dbConnection = null;
		Statement statement = null;

		String selectTableSQL = buildSqlQuery();
		
		List<String> columnNames = new ArrayList<String>();
		
		Map<String,String> db2adseTablesCountMap = new LinkedHashMap<String,String>();

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
		            db2adseTablesCountMap.put(columnName, columnValue);

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
		
		return db2adseTablesCountMap;
		
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
				sb.append("(select count(*) from PIMDBUSR." + tbl + ")" + " as " + tbl +  ",");
			} else {
				sb.append("(select count(*) from PIMDBUSR." + tbl + ")" + " as " + tbl );
			}
			
			
		}
		
		sb.append("\n");
		sb.append("from SYSIBM.SYSDUMMY1");
		
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
		//Changed to _CATALOG
		arrTables.add("ADSE_ATTRIBUTE_DICTIONARY_CATALOG");
		arrTables.add("ADSE_BM_ATTRIBUTE_XRF");
		arrTables.add("ADSE_CFA_CATALOG");
		arrTables.add("ADSE_CFA_TYPE_HIERARCHY");
		//_HIERARCHY added
		arrTables.add("ADSE_DATA_DICTIONARY_TYPE_HIERARCHY");
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
		arrTables.add("ADSE_REFERENCE_DATA_HIER");
		arrTables.add("ADSE_SIZEXRF_CATALOG");
		//changed to  
		arrTables.add("ADSE_SIZE_XRF_HIERARCHY");
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

			Class.forName(DB2_DRIVER);

		} catch (ClassNotFoundException e) {
			
			System.out.println(e.getMessage());

		}

		try {
			
			//"jdbc:db2://localhost:50000/exampledb";
			
			String dburl= "jdbc:db2://" + props.getProperty("db2Host").trim() + ":" + props.getProperty("db2Port").trim() + "/pimdb"; 
			
			System.out.println("Connecting to " + dburl );

			dbConnection = DriverManager.getConnection(dburl, props.getProperty("db2UserName").trim(),props.getProperty("db2Password").trim());
			
			return dbConnection;

		} catch (SQLException e) {
			
			e.printStackTrace();

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}
	
	

}

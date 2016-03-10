import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

 

public class ExcelRead {

	
	public static void main (String ars[]){
		
		getExternalId();
		
		/*HSSFCell cellA1 = null;
		HSSFCell innerCell = null;
		StringBuffer sql = null;
	 
		try {
			File fout = new File("Testing_30th.txt");
			FileOutputStream fos = new FileOutputStream(fout);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			
		    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("Testing_latest.xls"));
		    HSSFWorkbook wb = new HSSFWorkbook(fs);
		    HSSFSheet sheet = wb.getSheetAt(0);
			
		    int rows; // No of rows
		    rows = sheet.getPhysicalNumberOfRows();
		   // System.out.println(rows);
		    for(int i=0; i<rows; i++){
		    	sql = null;
		    	HSSFRow row1 = sheet.getRow(i);
				
		    	cellA1 = row1.getCell((short) 0);
		    	double attreibuteId =0;
		    	try{
				 attreibuteId = cellA1.getNumericCellValue();
		    	}catch(java.lang.NumberFormatException nm){
					continue;
				}
				Double attId = new Double(attreibuteId);
				int attIdVal = attId.intValue();
				
				cellA1 = row1.getCell((short) 1);
				double categoyId = 0;
				try{
				categoyId = cellA1.getNumericCellValue();
				}catch(java.lang.NumberFormatException nm){
					continue;
				}
				Double cateId = new Double(categoyId);
				int catIdVal = cateId.intValue();
				
				cellA1 = row1.getCell((short) 3);
				String dataType = cellA1.getStringCellValue();
				//System.out.println("Data Type"+dataType);
				
				 
				//System.out.println("Row Count "+a1Val);
				if(dataType != null && ( dataType.equalsIgnoreCase("Drop Down") || dataType.equalsIgnoreCase("Radio Button")) ){
					innerCell = row1.getCell((short)2);
					String valueSet = innerCell.getStringCellValue();
					 System.out.println(" String lenght ------------------------------- "+valueSet.length());
					String valueList  = valueSet.replace('|', '~');
					valueList = valueList.replace(';', ' ');
					System.out.println("Value Count "+valueList);
					
					String ar[] =  valueList.split("~");
					//System.out.println("Length "+ar.length);
					sql = new StringBuffer();
					int z=0; 
					 for (int k =0; k<(ar.length); k++){
						// System.out.println(""+k);
						// System.out.println("Value Count "+ar[k].trim());
						 z++;
						 sql = new StringBuffer();
						 sql.append(" INSERT INTO PET_ATTRIBUTE_VALUE  ");
							sql.append(" ");
							sql.append(" VALUES ('"+attIdVal+"',");
							sql.append(" '"+catIdVal+"',");
							sql.append(" "+(z)+",");
							sql.append(" '"+ar[k].trim()+"',");
							sql.append(" 'Admin', ");
							sql.append(" CURRENT_DATE,");
							sql.append(" 'Admin',");
							sql.append(" CURRENT_DATE ); ");
						//	System.out.println(sql);
							
							bw.write(sql.toString());
							bw.newLine();
					 }
					
				} else if(dataType != null && dataType.equalsIgnoreCase("Text Field")){
					sql = new StringBuffer();
					 sql.append(" INSERT INTO PET_ATTRIBUTE_VALUE  ");
						sql.append(" ");
						sql.append(" VALUES ('"+attIdVal+"',");
						sql.append(" '"+catIdVal+"',");
						sql.append(" "+1+",");
						sql.append("  ' ',");
						sql.append(" 'Admin', ");
						sql.append(" CURRENT_DATE,");
						sql.append(" 'Admin',");
						sql.append(" CURRENT_DATE ); ");
						//System.out.println(sql);
						
						bw.write(sql.toString());
						bw.newLine();
					
				} else if(dataType != null && dataType.equalsIgnoreCase("Check Boxes")){
					//TODO Need to check this attribute
					sql = new StringBuffer();
					 sql.append(" INSERT INTO PET_ATTRIBUTE_VALUE  ");
						sql.append(" ");
						sql.append(" VALUES ('"+attIdVal+"',");
						sql.append(" '"+catIdVal+"',");
						sql.append(" "+1+",");
						sql.append("  ' ',");
						sql.append(" 'Admin', ");
						sql.append(" CURRENT_DATE,");
						sql.append(" 'Admin',");
						sql.append(" CURRENT_DATE ); ");
						//System.out.println(sql);
						
						bw.write(sql.toString());
						bw.newLine();
				} else if(dataType != null && dataType.equalsIgnoreCase("Radio Button")){
					//TODO Need to check this attribute
					innerCell = row1.getCell((short)2);
					String valueSet = innerCell.getStringCellValue();
					 
					String valueList  = valueSet.replace('|', '~');
					valueList = valueList.replace(';', ' ');
					//System.out.println("Value Count "+valueList);
					
					String ar[] =  valueList.split("~");
					//System.out.println("Length "+ar.length);
					sql = new StringBuffer();
					int z=0; 
					 for (int k =0; k<(ar.length); k++){
						// System.out.println(""+k);
						// System.out.println("Value Count "+ar[k].trim());
						 z++;
						 sql = new StringBuffer();
						 sql.append(" INSERT INTO PET_ATTRIBUTE_VALUE  ");
							sql.append(" ");
							sql.append(" VALUES ('"+attIdVal+"',");
							sql.append(" '"+catIdVal+"',");
							sql.append(" "+(z)+",");
							sql.append(" '"+ar[k].trim()+"',");
							sql.append(" 'Admin', ");
							sql.append(" CURRENT_DATE,");
							sql.append(" 'Admin',");
							sql.append(" CURRENT_DATE ); ");
							//System.out.println(sql);
							
							bw.write(sql.toString());
							bw.newLine();
					 }
				}
				
				}
				
				//System.out.println(sql);
		  
		    bw.close();
		} catch(Exception ioe) {
		    ioe.printStackTrace();
		}*/
	}
	
	public static void getExternalId(){
		String fileName = "External.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            int i=0;
            String concat = "";
            while((line = bufferedReader.readLine()) != null) {
                System.out.println(i);
                if(i==0){
                	concat = "(";
                }else{
                	concat = concat + "'" + line +"',";
                }
                i++;
            }   
System.out.println("Final "+concat);
            // Always close files.
            bufferedReader.close();         
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
	}
}

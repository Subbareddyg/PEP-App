
package com.belk.pep.constants;

//import java.util.logging.Logger;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.belk.pep.util.PropertiesFileLoader;

/**
 * The Class XqueryConstants.
 *
 * @author AFUSOS3
 */
public class XqueryConstants {

    /** The Constant LOGGER. */
    private final static Logger LOGGER = Logger.getLogger(XqueryConstants.class.getName());

    /**
     * Gets the cars brand query.
     *
     * @param orinNumber the orin number
     * @param supplierId the supplier id
     * @return the cars brand query
     */
    public String getCarsBrandQuery(String orinNumber, String supplierId) {
        
            String GET_CARS_BRAND_XQUERY = " WITH Input(SUPPLIER_ID, ORIN) AS " 
               +"   ( SELECT :supplier SUPPLIER_ID, :orinNo ORIN FROM dual " 
               +"   ), "
               /**
                * MODIFICATION START BY AFUAXK4
                * DATE: 02/05/2016
                */
               /*
               +"   referencefirst AS " 
               +"   (SELECT LISTAGG(CODE,'|') WITHIN GROUP ( " 
               +"   ORDER BY CODE) CODE, " 
               +"     S.SUPPLIERID, " 
               +"     ARD.CONTAINER, " 
               +"     IP.ORIN " 
               +"   FROM VENDORPORTAL.ADSE_REFERENCE_DATA ARD, " 
               +"     INPUT IP, " 
               +"     XMLTABLE( ' <category>                <code>{  for $j in $XML_DATA/pim_entry/entry/OmniChannelBrand_Lkp_Spec         return             if (not( ($j) is ($XML_DATA/pim_entry/entry/OmniChannelBrand_Lkp_Spec[last()])[1] ) and not(empty($j//Code)) and not($j//Code = \"\"))         then           concat(concat($j//Code,\"-\"),$j//Description)                          else                            string(concat(concat($j//Code,\"-\"),$j//Description))     }                  </code>                    <id>{    for $i in $XML_DATA/pim_entry/entry/OmniChannelBrand_Lkp_Spec    return $i//Supplier_Site_ID    }    </id>                 </category>' passing ARD.xml_data AS \"XML_DATA\" COLUMNS CODE VARCHAR(100) path '/category/code', SUPPLIERID VARCHAR(100) path '/category/id') S " 
               +"   WHERE IP.SUPPLIER_ID =S.SUPPLIERID " 
               +"   AND ARD.CONTAINER    = 'Cars_Brand_Names' " 
               +"   GROUP BY S.SUPPLIERID, " 
               +"     ARD.CONTAINER, " 
               +"     IP.ORIN " 
               +"   ), " 
               +"   pettable AS " 
               +"   (SELECT RF.CODE, " 
               +"     RF.SUPPLIERID, " 
               +"     RF.CONTAINER, " 
               +"     CASE WHEN T.OMNICODE IS NULL " 
               +"     THEN T.OMNICODECOMPLEX  " 
               +"     ELSE T.OMNICODE END AS OMNICODE, " 
               +"     APC.MDMID " 
               +"   FROM referencefirst RF, " 
               +"     VENDORPORTAL.ADSE_PET_CATALOG APC, " 
               +"     XMLTABLE( '  for $j in $XML_DATA/pim_entry/entry " 
               +"     let              " 
               +"     $code := $j/Ecomm_Style_Spec/Cars_Brand          " 
               +"     return               " 
               +"     <category>            <code>{$code}</code><codeComplex>{$j/Ecomm_ComplexPack_Spec/Cars_Brand}</codeComplex>            " 
               +"     </category>' passing APC.xml_data AS \"XML_DATA\" COLUMNS OMNICODE VARCHAR(100) path '/category/code',OMNICODECOMPLEX VARCHAR(100) path '/category/codeComplex') T " 
               +"   WHERE APC.MDMID = RF.ORIN " 
               +"   ), " 
               +"   referencetable AS " 
               +"   (SELECT V.SUPPLIERIDS, " 
               +"     V.IDCHECK " 
               +"   FROM INPUT I, " 
               +"     VENDORPORTAL.ADSE_REFERENCE_DATA ARDT, " 
               +"     XMLTABLE( '  <category>            <code>{  for $j in $XML_DATA/pim_entry/entry/OmniChannelBrand_Lkp_Spec  return                string(concat(concat($j//Code,\"-\"),$j//Description))     }          </code>            <id>{    for $i in $XML_DATA/pim_entry/entry/OmniChannelBrand_Lkp_Spec    return $i//Supplier_Site_ID    }    </id>           <check>{  for $j in $XML_DATA/pim_entry/entry/OmniChannelBrand_Lkp_Spec  return         string($j//Code)     }   </check>          </category>' passing ARDT.xml_data AS \"XML_DATA\" COLUMNS SUPPLIERIDS VARCHAR(100) path '/category/id', IDCHECK VARCHAR(100) path '/category/check') V " 
               +"   WHERE ARDT.CONTAINER = 'Cars_Brand_Names' " 
               +"   AND I.SUPPLIER_ID    =V.SUPPLIERIDS " 
               +"   ) " 
               +" SELECT PT.CODE CAR_BRANDS, " 
               +"   PT.OMNICODE CARS_BRAND, " 
               +"   PT.SUPPLIERID SUPPLIER, " 
               +"   PT.CONTAINER CONTAINER_TYPE, " 
               +"   PT.MDMID ORIN " 
               +" FROM PETTABLE PT " 
               +" LEFT OUTER JOIN referencetable rt " 
               +" ON PT.OMNICODE = RT.IDCHECK " ;*/
               +"   referencefirst AS                                                                               "
               +"   (SELECT /*LISTAGG(CODE,'|') WITHIN GROUP (ORDER BY CODE) CODE,*/                                "
               +"  ARD.MDMID CODE,                                                                                  "
               +"     ARD.PARENT_MDMID SUPPLIERID,                                                                  "
               +"     ARD.CONTAINER,                                                                                "
               +"     IP.ORIN,                                                                                      "
               +"     ARD.THEVALUE CODEDESCRIPTION                                                                  "
               +"   FROM ADSE_REFERENCE_DATA ARD,                                                                   "
               +"     INPUT IP                                                                                      "
               +"   WHERE IP.SUPPLIER_ID =ARD.PARENT_MDMID                                                          "
               +"   AND ARD.CONTAINER in ('Cars_Brand_Names')                                                       "
               +"   order by CODE                                                                                   "
               +"   )                                                                                               "
               +"   SELECT RF.CODE CAR_BRANDS,                                                                      "
               +"     RF.CODEDESCRIPTION CAR_BRANDS_DESCRIPTION,                                                    "
               +"     /*RF.CODEDESCRIPTION CARS_BRAND,*/                                                            "
               +"     T.OMNICODE CARS_BRAND,                                                                        "
               +"     T.OMNICODECOMPLEXPACK, /*Added for Complex Pack*/                                             "
               +"     RF.SUPPLIERID SUPPLIER,                                                                       "
               +"     RF.CONTAINER CONTAINER_TYPE,                                                                  "
               +"     APC.MDMID ORIN,                                                                               "
               +"     APC.ENTRY_TYPE                                                                                "
               +"   FROM referencefirst RF,                                                                         "
               +"     VENDORPORTAL.ADSE_PET_CATALOG APC,                                                            "
               +"     XMLTABLE( '                                                                                   "
               +"     let $code := $XML_DATA/pim_entry/entry/Ecomm_Style_Spec/Cars_Brand,                           "
               +"     $codeComplex := $XML_DATA/pim_entry/entry/Ecomm_ComplexPack_Spec/Cars_Brand                   "
               +"     return                                                                                        "
               +"     <category>                                                                                    "
               +"     <code>{$code}</code>                                                                          "
               +"     <codeComplex>{$codeComplex}</codeComplex>                                                     "
               +"     </category>'                                                                                  "
               +"  passing APC.xml_data AS \"XML_DATA\" COLUMNS OMNICODE VARCHAR(100) path '/category/code',OMNICODECOMPLEXPACK VARCHAR(100) path '/category/codeComplex' ) T  "
               +"   WHERE APC.MDMID = RF.ORIN  ";
            
            /**
             * MODIFICATION END BY AFUAXK4
             * DATE: 02/05/2016              
             */

      //  System.out.println("Query - >"+GET_CARS_BRAND_XQUERY);
        return GET_CARS_BRAND_XQUERY;


    }


    /**
     * Gets the child sku details.
     *
     * @param orinNumber the orin number
     * @return the child sku details
     */
    public  String getChildSKUDetails(String orinNumber) {

        final String GET_CHILD_SKU_INFORMATION_XQUERY= 
            " WITH INPUT( ORIN) AS " +
            " ( SELECT :orinNo ORIN FROM dual " +
            " ) , " +
            " TypeIndex(indx, typ) AS ( " +
            " (SELECT 1 indx, 'Style' typ FROM dual " +
            " ) " +
            " UNION " +
            " (SELECT 2 indx, 'StyleColor' typ FROM dual " +
            " ) " +
            " UNION " +
            " (SELECT 3 indx, 'SKU' typ FROM dual " +
            " )) " +
            " SELECT aic.MDMID ORIN, " +
            " ven_name VENDOR_NAME, " +
            " Name Style_Name, " +
            " NVL(aic.PARENT_MDMID, aic.MDMID) Style, " +
            " XMLCAST(XMLQUERY('/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code' " + 
            " PASSING aic.xml_Data  " +
            " RETURNING CONTENT " +
            " ) as varchar2(1000) ) " +
            " COLOR_CODE, " +
            " XMLCAST(XMLQUERY('/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description' " + 
            " PASSING aic.xml_Data  " +
            " RETURNING CONTENT " +
            " ) as varchar2(1000) ) " +
            " COLOR_NAME, " +
            " XMLCAST(XMLQUERY('/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description' " + 
            " PASSING aic.xml_Data  " +
            " RETURNING CONTENT " +
            " ) as varchar2(1000) ) " +
            " SIZE_NAME, " +
            " aic.ENTRY_TYPE " +
            " FROM VENDORPORTAL.ADSE_ITEM_CATALOG aic, " +
            " VENDORPORTAL.ADSE_PET_CATALOG pet, " +
            " XMLTABLE('for $pet in $pets/pim_entry/entry    " +
            " return <out>    " +
            " <name>{$pet/Pet_Ctg_Spec/Name}</name> " +
            " <priority>{$pet/Pet_Ctg_Spec/PO_Number}</priority>  " +
            " </out>' passing pet.xml_data AS \"pets\" Columns Name VARCHAR2(20) path '/out/name', " +
            " priority VARCHAR2(10) path '/out/priority') p, " +
            " VENDORPORTAL.ADSE_SUPPLIER_CATALOG supplier, " +
            " XMLTABLE('for $supplier in $supplierCatalog/pim_entry/entry/Supplier_Ctg_Spec  " +
            " let $ven_name := $supplier/Name return  <out> <ven_name>{$ven_name}</ven_name> </out>'  " +
            " PASSING supplier.XML_DATA AS \"supplierCatalog\" columns ven_name VARCHAR2(80) path '/out/ven_name') s, " +
            " Input inp " +
            " WHERE aic.MOD_DTM    = '01-JAN-00 12.00.00.000000000 PM' " +
            " AND aic.mdmid        = pet.mdmid " +
            " AND AIC.PETEXISTS = 'Y'   " +
            " AND pet.MOD_DTM      = '01-JAN-00 12.00.00.000000000 PM' " +
            " AND supplier.MOD_DTM = '01-JAN-00 12.00.00.000000000 PM' " +
            " AND  aic.primary_supplier_id = supplier.mdmid " +
            " AND (aic.mdmid       = inp.ORIN " +
            " OR aic.parent_mdmid  = inp.ORIN) " +
            " AND aic.Entry_type   = 'SKU'";

        return GET_CHILD_SKU_INFORMATION_XQUERY;
    }





    /**
     * Gets the content history.
     *
     * @param orinNumber the orin number
     * @return the content history
     */
    public  String getContentHistory(String orinNumber) {

        String GET_CONTENT_HISTORY_INFORMATION_XQUERY = " WITH Input(ORIN) AS " 
                +"   ( SELECT :orinNo FROM dual " 
                +"   ) " 
                +" SELECT pet.mdmid, " 
                +"   CASE " 
                +"     WHEN i.createdOn IS NOT NULL " 
                +"     THEN TO_CHAR(TO_DATE(i.createdOn, 'YYYY-MM-DD'),'MM/DD/YYYY') " 
                +"     ELSE NULL " 
                +"   END AS createdOn, " 
                +"   i.createdBy, " 
                +"   i.lastUpdateBy, " 
                +"   CASE " 
                +"     WHEN i.lastUpdateOn IS NOT NULL " 
                +"     THEN TO_CHAR(TO_DATE(i.lastUpdateOn, 'YYYY-MM-DD'),'MM/DD/YYYY') " 
                +"     ELSE NULL " 
                +"   END AS lastUpdateOn, " 
                +"   (SELECT thevalue " 
                +"   From Vendorportal.Adse_Reference_Data " 
                +"   WHERE mdmid   = i.lastState " 
                +"   AND Container = 'ContentState_Lookup' " 
                +"   ) Content_Status, " 
                +"   pet.entry_type " 
                +" FROM VENDORPORTAL.ADSE_PET_CATALOG pet, " 
                +"   XMLTable( 'for $i in $pet/pim_entry/entry/Pet_Ctg_Spec/Audit/Content       " 
                +"   let    " 
                +"     $lastState := $i//Last_State, " 
                +"     $lastUpdate := $i//Last_State_On, " 
                +"     $lastUpdateBy := $i//Last_State_By, " 
                +"     $createdBy := $i//..//Created_By, " 
                +"     $createdOn := $i//..//Created_On, " 
                +"     $currentState := $i//..//..//State, " 
                +"   $container := $pet/pim_entry/entry/Pet_Ctg_Spec/ContentState  " 
                +"   return           " 
                +"   <petStatus>        " 
                +"   <lastState>{$lastState}</lastState>       " 
                +"   <lastUpdate>{$lastUpdate}</lastUpdate>    " 
                +"   <lastUpdateBy>{$lastUpdateBy}</lastUpdateBy>     " 
                +"   <createdBy>{$createdBy}</createdBy>             " 
                +"   <createdOn>{$createdOn}</createdOn>            " 
                +"   <currentState>{$currentState}</currentState>     " 
                +"   <container>{$container}</container>      " 
                +"   </petStatus>' passing pet.XML_DATA AS \"pet\" columns  " 
                +"   lastState VARCHAR2(40) path '/petStatus/lastState' , " 
                +"   lastUpdateOn VARCHAR2 (10) path '/petStatus/lastUpdate' , " 
                +"   lastUpdateBy VARCHAR2(40) path '/petStatus/lastUpdateBy', " 
                +"   createdBy VARCHAR2( 40) path '/petStatus/createdBy', " 
                +"   createdOn VARCHAR2(10) path '/petStatus/createdOn', " 
                +"   currentState VARCHAR2( 40) path '/petStatus/currentState', " 
                +"   container VARCHAR2(40) path '/petStatus/container' ) i , " 
                +"   Input inp " 
                +" Where Pet.Mdmid Like Concat(Inp.Orin,'%') " ;
        
       // LOGGER.info("GET_CONTENT_HISTORY_INFORMATION_XQUERY-----"+GET_CONTENT_HISTORY_INFORMATION_XQUERY);


        return GET_CONTENT_HISTORY_INFORMATION_XQUERY;

    }

    /**
     * Gets the copy attribute details.
     *
     * @param orinNumber the orin number
     * @return the copy attribute details
     */
    public  String getCopyAttributeDetails(String orinNumber) {

        final String GET_COPY_ATTRIBUTE_INFORMATION_XQUERY=

                " with " + "  INPUT( ORIN) as (" + "  Select "
                        + " :orinNo "
                        + "  ORIN FROM dual),"

                    + "  TypeIndex(indx, typ) as ((Select 1 indx, 'Style' typ  from  dual) union (select 2 indx, 'StyleColor' typ from dual) union (select 3 indx, 'SKU' typ from dual)),"

                    + "  Items as (select "
                    + "          aic.MDMID ORIN, (case when aic.PARENT_MDMID is null then aic.MDMID else aic.PARENT_MDMID end) Style_Id,"
                    + "         (VENDOR_COLOR_CODE || ' ' || VENDOR_COLOR_DESC) Vendor_Color, "
                    + "           VENDOR_SIZE, aic.ENTRY_TYPE "
                    + "   from VENDORPORTAL.ADSE_ITEM_CATALOG aic, XMLTABLE('for $i in $XML_DATA/pim_entry/entry "
                    + "            let $uda80 := (fn:count($i/Item_UDA_Spec/UDA/Id) gt 0 and $i/Item_UDA_Spec/UDA/Id eq \"80\"),"
                    + "                $non_sellable_pack := (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0 and "
                    + "                    ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")),"
                    + "                $removal := $i/Item_Ctg_Spec/System/Removal_Flag eq \"true\" "
                    + "       return  <out> "
                    + "                   <dept_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\" )[5]}</dept_id> "
                    + "                  <class_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[6]}</class_id> "
                    + "                   <supplier_id>{$i/Item_Ctg_Spec/Supplier[Primary_Flag eq \"true\"]/Id}</supplier_id> "
                    + "                  <desc>{$i/Item_Ctg_Spec/Description/Long}</desc> "
                    + "                  <flag>{$uda80 and $non_sellable_pack and $removal}</flag> "
                    + "                   <colorCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code}</colorCode> "
                    + "                   <colorDesc>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description}</colorDesc> "
                    + "               <size>{$i/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description}</size> "
                    + "           </out>' "
                    + "    passing aic.XML_DATA as \"XML_DATA\" "
                    + "    columns "
                    + "    flag varchar(10) path '/out/flag',"
                    + "    VENDOR_COLOR_CODE   varchar2(10) path '/out/colorCode',"
                    + "    VENDOR_COLOR_DESC   varchar2(40) path '/out/colorDesc',"
                    + "    VENDOR_SIZE    varchar2(10) path '/out/size') i, Input inp "
                    + " where flag = 'false' and    aic.PETEXISTS = 'Y' AND "
                    + "  aic.MOD_DTM = '01-JAN-00 12.00.00.000000000 PM' "
                    + "     and (aic.Parent_MDMID=inp.ORIN or aic.MDMID=inp.ORIN) and "
                    + "     aic.Entry_type = 'SKU') " + "    select * from items ";

        return GET_COPY_ATTRIBUTE_INFORMATION_XQUERY;

    }
    /**
     * Gets the family category from iph.
     *
     * @param categoryId the category id
     * @return the family category from iph
     */
    public  String getFamilyCategoryFromIPH (String categoryId){
        String query = " WITH Input(MDMID) AS "
                +"   (SELECT :categoryId MDMID FROM dual "
                +"   ) , "
                +"   attribfieldvalue AS "
                +"   (SELECT iph.MDMID, "
                +"     s.SEC_SPEC_VALUE, "
                +"     s.SEC_SPEC_VALUE1 "
                +"   FROM Input inp, "
                +"     VENDORPORTAL.ADSE_ITEM_PRIMARY_HIERARCHY IPH , "
                +"     XMLTABLE('    "
                +" for $j in $XML_DATA/pim_category/iph_category_header   "
                +" let     "
                +" $fullPath := $j//full_path   , "
                +"  "
                +" $categoryname := tokenize($j//full_path, \"///\") "
                +"  "
                +" return "
                +" <category>   "
                +"  "
                +" <data>{$fullPath}</data>   "
                +" <categoryname>{$categoryname}</categoryname>   "
                +"  "
                +" </category>' passing IPH.xml_data AS \"XML_DATA\" COLUMNS SEC_SPEC_VALUE VARCHAR(100) path '/category/data', SEC_SPEC_VALUE1 VARCHAR(100) path '/category/categoryname' ) s "
                +"   WHERE iph.MDMID =inp.mdmid "
                +"   ) , "
                +"   codeDescVal AS "
                +"   (SELECT regexp_substr(atv.SEC_SPEC_VALUE,'[^///]+',1,LEVEL) AS codeDesc "
                +"   FROM dual, "
                +"     attribfieldvalue atv "
                +"     CONNECT BY regexp_substr(atv.SEC_SPEC_VALUE,'[^///]+',1,LEVEL) IS NOT NULL "
                +"   ) , "
                +"   allCategory AS "
                +"   (SELECT SUBSTR(cdv.codeDesc, 1, Instr(cdv.codeDesc, '-') - 1) CategoryId, "
                +"     SUBSTR(cdv.codeDesc, Instr(cdv.codeDesc, '-')          + 1) CategoryName "
                +"   FROM dual, "
                +"     codeDescVal cdv "
                +"   ) "
                +" SELECT CategoryId, "
                +"   CategoryName "
                +" FROM allCategory "
                +" WHERE CategoryId IS NOT NULL  " ;
       // System.out.println("query--getFamilyCategoryFromIPH---"+query);
        return query;

    }


    /**
     * Gets the global attributes query.
     *
     * @param orinNumber the orin number
     * @return the global attributes query
     */
    public String getGlobalAttributesQuery(String orinNumber){

        String GET_GLOBAL_ATTRIBUTES_XQUERY = "   WITH Input(ORIN) AS " 
                +"       ( SELECT :orinNo ORIN FROM dual " 
                +"   ), " 
                +"   PetDetails AS " 
                +"   (SELECT p.OmniChannelBrand, " 
                +"     p.GXS_Long_Description Long_Description, " 
                +"     p.Belk_Exclusive, " 
                +"     p.Channel_Exclusive, " 
                +"     p.BOPIS, " 
                +"     p.ImportedOrDomestic, " 
                +"     pet.mdmid, " 
                +"     pet.entry_type , " 
                +"     p.Is_PYG, " 
                +"     p.is_GWP, " 
                +"     p.is_PWP, pet.pet_state PETSTATUS " 
                +"   FROM vendorportal.ADSE_PET_CATALOG pet, " 
                +"     XMLTable( 'for $i in $item/pim_entry/entry/*                 " 
                +" let        " 
                +" $item_type := $i/../../pet_entry_header/category_paths/category[pk = (\"Style\", \"ComplexPack\")]               " 
                +" where name($i) eq concat(\"Ecomm_\",$item_type//pk,\"_Spec\")                " 
                +" return     " 
                +" $i' passing pet.XML_DATA AS \"item\" columns OmniChannelBrand VARCHAR2(50) path 'OmniChannelBrand', Product_Description VARCHAR2(60) Path 'Product_Description', Launch_Date VARCHAR2(50) path 'Launch_Date', Belk_Exclusive VARCHAR2(10) Path 'Belk_Exclusive', Channel_Exclusive VARCHAR2(30) path 'Channel_Exclusive', Sdf VARCHAR2(50) Path 'Supplier_Direct_Fulfillment', Product_Dimensions_UOM VARCHAR2(50) Path 'Product_Dimensions/Product_Dimensions_UOM', Product_Length VARCHAR2(50) path 'Product_Dimensions/Length', Product_Height VARCHAR2(50) path 'Product_Dimensions/Height', Product_Width VARCHAR2(50) path 'Product_Dimensions/Width', Product_Weight VARCHAR2(40) Path 'Product_Weight/Weight', Product_Weight_Uom VARCHAR2(40) Path " 
                +"     'Product_Weight/UOM', Bopis                                                                                                                                                                                                                                                                                                          VARCHAR2(60) Path 'BOPIS', Gift_Box VARCHAR2(10) Path 'Gift_Box', Importedordomestic VARCHAR2(10) Path 'if(SourcedDomestically eq \"false\") then \"Imported\" else \"Domestic\"' , GXS_Long_Description VARCHAR2(40) path 'GXS_Long_Description', Product_Name VARCHAR2(40) Path 'Product_Name', PO_Box_Shippable VARCHAR2(40) path 'PO_Box_Shippable', cOPY_BULLET VARCHAR2(40) path 'cOPY_BULLET', Fraud_Quantity VARCHAR2(40) path 'Fraud_Quantity', Is_PYG VARCHAR2(40) path 'Is_PYG', Is_Gwp VARCHAR2(40) Path 'Is_GWP', Is_PWP VARCHAR2(40) path 'Is_PWP' ) p, " 
                +"     Input inp " 
                +"   WHERE inp.ORIN = pet.mdmid " 
                +"   ) " 
                +" SELECT pet.OmniChannelBrand, " 
                +"   Ia.Long_Description, " 
                +"   pet.Belk_Exclusive, " 
                +"   pet.Channel_Exclusive, " 
                +"   ia.Direct_Ship_Flag SDF, " 
                +"   pet.BOPIS, " 
                +"   pet.ImportedOrDomestic, " 
                +"   pet.mdmid, " 
                +"   pet.entry_type , " 
                +"   pet.Is_PYG, " 
                +"   pet.is_GWP, " 
                +"   Pet.Is_Pwp, pet.PETSTATUS  " 
                /*RMS - UDA enhancement start */
                +",Ia.Vendor_Collection, Ia.Trends, Ia.Yc_Trends, Ia.Women_Big_Ideas, Ia.Age_Group "
                /*RMS - UDA enhancement end */
                +" FROM PetDetails pet, " 
                +"   vendorportal.ADSE_ITEM_CATALOG item, " 
                +"   XMLTABLE( 'for $i in $XML_DATA/pim_entry/entry      " 
                +" let           " 
                +" $directShipFlag := $i/Item_Ctg_Spec/Supplier[Primary_Flag eq \"true\"]/Direct_Ship_Flag/text(),        " 
                +" $primary_flag := $i/Item_Ctg_Spec/Supplier/Country/Primary_Flag/text(),         " 
                +" $long_description := $i/Item_Ctg_Spec/Description/Long/text(),           " 
                +"$trends := $i/Item_UDA_Spec/Trends/text(),"
                +"$yc_trends := $i/Item_UDA_Spec/Yc_Trends/text(),"
                +"$women_ideas := $i/Item_UDA_Spec/Fall_Fy17_Womens_Big_Ideas/text(),"
                +"$vendor_collection := $i/Ecomm_Style_Spec/Vendor_Collection/text(),"
                +" $age_group := $i/Item_UDA_Spec/Kids_Shoe_Sizes"
                +" return    " 
                +" <out>        " 
                +" <Direct_Ship_Flag>{$directShipFlag}</Direct_Ship_Flag>    " 
                +" <primary_flag>{$primary_flag}</primary_flag>         " 
                +" <Long_Description>{$long_description}</Long_Description>     " 
                +"<Trends>{$trends}</Trends>"
                +"<Yc_Trends>{$yc_trends}</Yc_Trends>"
                +"<Women_Big_Ideas>{$women_ideas}</Women_Big_Ideas>"
                +"<Age_Group>{$age_group}</Age_Group>"
                +"<Vendor_Collection>{$vendor_collection}</Vendor_Collection>"
                +" </out>' passing item.XML_DATA AS \"XML_DATA\" columns Direct_Ship_Flag VARCHAR2(100) path '/out/Direct_Ship_Flag', Primary_Flag VARCHAR(25) Path '/out/primary_flag', Long_Description VARCHAR2(100) Path '/out/Long_Description', " 
                +"Trends VARCHAR2(60) PATH '/out/Trends',"
                +"Yc_Trends VARCHAR2(60) PATH '/out/Yc_Trends',"
                +"Women_Big_Ideas VARCHAR2(60) PATH '/out/Women_Big_Ideas',"
                +"Vendor_Collection VARCHAR2(60) PATH '/out/Vendor_Collection', Age_Group VARCHAR2(60) PATH '/out/Age_Group' ) Ia "
                +" WHERE Pet.Mdmid = Item.Mdmid AND item.PETEXISTS = 'Y' ";
				LOGGER.info("GET_GLOBAL_ATTRIBUTES_XQUERY-->"+GET_GLOBAL_ATTRIBUTES_XQUERY);
        return GET_GLOBAL_ATTRIBUTES_XQUERY;
    }


    /**
     * Gets the iph categories from adse merchandise hierarchy.
     *
     * @param orinNmber the orin nmber
     * @return the iph categories from adse merchandise hierarchy
     */
    public  String getIphCategoriesFromAdseMerchandiseHierarchy(String orinNmber ) {

        /**
         * MODICATION START AFUAXK4
         * DATE: 02/05/2016
         */
        String  GET_IPH_CATEGORY_INFORMATION_XQUERY = " WITH Input(ORIN) AS " 
            +"   ( SELECT :orinNo ORIN " 
            +"   FROM dual " 
            +"   ) , "                                 
            +"     petcatalog  AS      "
            +"     (SELECT pet.mdmid ORIN,                          "
            +"       ias.CATEGORY_ID,                               "
            +"       ias.CATEGORY_DESC,                             "
            +"       ias.CATEGORY_NAME,                             "
            +"       pet.ENTRY_TYPE                                 "
            +"     FROM Input inp,                                  "
            +"       VENDORPORTAL.ADSE_PET_CATALOG pet,             "
            +"       XMLTABLE( 'for $j in $XML_DATA/pim_entry/pet_entry_header/category_paths/category[last()]  let           $categoryid := $j//pk,        $categorydesc := $j//path,        $categoryname := tokenize(tokenize($j//path, \"///\")[last()],\"-\")[last()]        return         <category>      <pk>{$categoryid}</pk>      <path>{$categorydesc}</path>      <name>{$categoryname}</name>     </category>' PASSING pet.XML_DATA AS \"XML_DATA\" COLUMNS CATEGORY_ID VARCHAR(100) path '/category/pk', CATEGORY_DESC VARCHAR(100) path '/category/path', CATEGORY_NAME VARCHAR(100) path '/category/name') ias "
            +"     WHERE pet.mdmid = inp.ORIN                        "
            +"     ) ,                                               "
            +"     petitemcatalog  AS   "
            +"     (SELECT pc.ORIN,                                  "
            +"       pc.CATEGORY_ID,                                 "
            +"       pc.CATEGORY_NAME,                               "
            +"       ibs.ITEM_CATEGORY_ID,                           "
            +"       pc.ENTRY_TYPE                                   "
            +"     FROM petcatalog pc,                               "
            +"       VENDORPORTAL.ADSE_ITEM_CATALOG aic,             "
            +"       XMLTABLE( 'for $j in $XML_DATA/pim_entry/item_header/category_paths/category  let           $itemcategoryid := $j//pk,        $itemcategorydesc := $j//path,        $itemcategoryname := tokenize($j//path, \"///\")[1]        return         <category>        <pk>{$itemcategoryid}</pk>        <path>{$itemcategorydesc}</path>        <name>{$itemcategoryname}</name>       </category>' PASSING aic.XML_DATA AS \"XML_DATA\" COLUMNS ITEM_CATEGORY_ID VARCHAR(100) path '/category/pk', ITEM_CATEGORY_DESC VARCHAR(100) path '/category/path', ITEM_CATEGORY_NAME VARCHAR(100) path '/category/name') ibs "
            +"     WHERE pc.ORIN                     = aic.MDMID  AND aic.PETEXISTS = 'Y'                                                                "
            +"     AND ( pc.ENTRY_TYPE              IS NULL                                                                     "
            +"     OR pc.ENTRY_TYPE                 IN ('Style','StyleColor','SKU', 'ComplexPack', 'PackColor' , '' ) )         "
            +"     AND ( pc.ENTRY_TYPE              IS NULL                                                                     "
            +"     OR pc.ENTRY_TYPE                 IN ('Style','StyleColor','SKU', 'ComplexPack', 'PackColor', '') )           "
            +"     AND UPPER(ibs.ITEM_CATEGORY_NAME) = UPPER('Merchandise_Hierarchy')                                           "
            +"     ) ,                                                                                                          "
            +"     petitemmerch  AS                                                                "
            +"     (SELECT pic.ORIN,                                                                                            "
            +"       pic.CATEGORY_ID,                                                                                           "
            +"       pic.CATEGORY_NAME,                                                                                         "
            +"       pic.ITEM_CATEGORY_ID,                                                                                      "
            +"       ics.SUB_CLASS,                                                                                             "
            +"       pic.ENTRY_TYPE                                                                                             "
            +"     FROM petitemcatalog pic,                                                                                     "
            +"       VENDORPORTAL.ADSE_MERCHANDISE_HIERARCHY amh,                                                               "
            +"       XMLTABLE( 'for $j in $XML_DATA/pim_category/merchandise_category_header      let           $subclass := tokenize($j//full_path, \"///\")[last()]              return           <merchandise_category_header>          <full_path>{$subclass}</full_path>         </merchandise_category_header>' PASSING amh.XML_DATA AS \"XML_DATA\" COLUMNS SUB_CLASS VARCHAR(100) path '/merchandise_category_header/full_path') ics "
            +"     WHERE pic.ITEM_CATEGORY_ID = amh.MDMID                                                                 "
            +"     AND ( pic.ENTRY_TYPE      IS NULL                                                                      "
            +"     OR pic.ENTRY_TYPE         IN ('Style','StyleColor','SKU', 'ComplexPack', 'PackColor' , '' ) )          "
            +"     AND ( pic.ENTRY_TYPE      IS NULL                                                                      "
            +"     OR pic.ENTRY_TYPE         IN ('Style','StyleColor','SKU', 'ComplexPack', 'PackColor', '') )            "
            +"     )                                                                                                      "
            +"   SELECT pim.ORIN ORIN,                                                                                    "
            +"     pim.CATEGORY_ID PET_CATEGORY_ID,                                                                       "
            +"     pim.CATEGORY_NAME PET_CATEGORY_NAME,                                                                   "
            +"     pim.SUB_CLASS SUB_CLASS,                                                                               "
            +"     ids.leafnode7,                                                                                         "
            +"     ids.leafnode6,                                                                                         "
            +"     ids.leafnode5,                                                                                         "
            +"     ids.leafnode4,                                                                                         "
            +"     ids.leafnode3,                                                                                         "
            +"     ids.leafnode2,                                                                                         "
            +"     ids.leafnode1                                                                                          "
            +"   FROM petitemmerch pim,                                                                                   "
            +"     VENDORPORTAL.ADSE_MERCHANDISE_HIERARCHY amh1,                                                          "
            +"     XMLTABLE( 'for $j in $XML_DATA/pim_category/entry/Merchandise_Hier_Spec/IPH_Category_Mappings          "
            +"     let                                                                                                    "
            +"     $leafnode7 := $j//Level_7,                                                                             "
            +"     $leafnode6 := $j//Level_6,                                                                             "
            +"     $leafnode5 := $j//Level_5,                                                                             "
            +"     $leafnode4 := $j//Level_4,                                                                             "
            +"     $leafnode3 := $j//Level_3,                                                                             "
            +"     $leafnode2 := $j//Level_2,                                                                             "
            +"     $leafnode1 := $j//Level_1                                                                              "
            +"     return                                                                                                 "
            +"     <IPH_Category_Mappings>                                                                                "
            +"     <leafnode7>{$leafnode7}</leafnode7>                                                                    "
            +"     <leafnode6>{$leafnode6}</leafnode6>                                                                    "
            +"     <leafnode5>{$leafnode5}</leafnode5>                                                                    "
            +"     <leafnode4>{$leafnode4}</leafnode4>                                                                    "
            +"     <leafnode3>{$leafnode3}</leafnode3>                                                                    "
            +"     <leafnode2>{$leafnode2}</leafnode2>                                                                    "
            +"     <leafnode1>{$leafnode1}</leafnode1>                                                                    "
            +"     </IPH_Category_Mappings>'                                                                              "
            +"     PASSING amh1.XML_DATA AS                                                                               "
            +"     \"XML_DATA\" COLUMNS                                                                                     "
            +"     leafnode7 VARCHAR(100) path '/IPH_Category_Mappings/leafnode7',                                        "
            +"     leafnode6 VARCHAR(100) path '/IPH_Category_Mappings/leafnode6',                                        "
            +"     leafnode5 VARCHAR(100) path '/IPH_Category_Mappings/leafnode5',                                        "
            +"     leafnode4 VARCHAR(100) path '/IPH_Category_Mappings/leafnode4',                                        "
            +"     leafnode3 VARCHAR(100) path '/IPH_Category_Mappings/leafnode3',                                        "
            +"     leafnode2 VARCHAR(100) path '/IPH_Category_Mappings/leafnode2',                                        "
            +"     leafnode1 VARCHAR(100) path '/IPH_Category_Mappings/leafnode1') ids                                    "
            +"   WHERE pim.ITEM_CATEGORY_ID = amh1.MDMID                                                                  "
            +"   AND ( pim.ENTRY_TYPE      IS NULL                                                                        "
            +"   OR pim.ENTRY_TYPE         IN ('Style','StyleColor','SKU', 'ComplexPack', 'PackColor', '' ) )             "
            +"   AND ( pim.ENTRY_TYPE      IS NULL                                                                        "
            +"   OR pim.ENTRY_TYPE         IN ('Style','StyleColor','SKU', 'ComplexPack', 'PackColor', '') )              ";
        /*
        String  GET_IPH_CATEGORY_INFORMATION_XQUERY = " WITH Input(ORIN) AS " 
                +"   ( SELECT '"+orinNmber+"' ORIN " 
                +"   FROM dual " 
                +"   ) , " 
                +"   petcatalog AS " 
                +"   (SELECT pet.mdmid ORIN, " 
                +"     ias.CATEGORY_ID, " 
                +"     ias.CATEGORY_DESC, " 
                +"     ias.CATEGORY_NAME, " 
                +"     pet.ENTRY_TYPE " 
                +"   FROM Input inp, " 
                +"     VENDORPORTAL.ADSE_PET_CATALOG pet, " 
                +"     XMLTABLE( 'for $j in $XML_DATA/pim_entry/pet_entry_header/category_paths/category[last()]  let           $categoryid := $j//pk,        $categorydesc := $j//path,        $categoryname := tokenize(tokenize($j//path, \"///\")[last()],\"-\")[last()]        return         <category>      <pk>{$categoryid}</pk>      <path>{$categorydesc}</path>      <name>{$categoryname}</name>     </category>' PASSING pet.XML_DATA AS \"XML_DATA\" COLUMNS CATEGORY_ID VARCHAR(100) path '/category/pk', CATEGORY_DESC VARCHAR(100) path '/category/path', CATEGORY_NAME VARCHAR(100) path '/category/name') ias " 
                +"   WHERE pet.mdmid = inp.ORIN " 
                +"   ) , " 
                +"   petitemcatalog AS " 
                +"   (SELECT pc.ORIN, " 
                +"     pc.CATEGORY_ID, " 
                +"     pc.CATEGORY_NAME, " 
                +"     ibs.ITEM_CATEGORY_ID, " 
                +"     pc.ENTRY_TYPE " 
                +"   FROM petcatalog pc, " 
                +"     VENDORPORTAL.ADSE_ITEM_CATALOG aic, " 
                +"     XMLTABLE( 'for $j in $XML_DATA/pim_entry/item_header/category_paths/category  let           $itemcategoryid := $j//pk,        $itemcategorydesc := $j//path,        $itemcategoryname := tokenize($j//path, \"///\")[1]        return         <category>        <pk>{$itemcategoryid}</pk>        <path>{$itemcategorydesc}</path>        <name>{$itemcategoryname}</name>       </category>' PASSING aic.XML_DATA AS \"XML_DATA\" COLUMNS ITEM_CATEGORY_ID VARCHAR(100) path '/category/pk', ITEM_CATEGORY_DESC VARCHAR(100) path '/category/path', ITEM_CATEGORY_NAME VARCHAR(100) path '/category/name') ibs " 
                +"   WHERE pc.ORIN                     = aic.MDMID " 
                +"   AND ( pc.ENTRY_TYPE              IS NULL " 
                +"   OR pc.ENTRY_TYPE                 IN ('Style','StyleColor','SKU', 'ComplexPack', 'PackColor' , '' ) ) " 
                +"   AND ( pc.ENTRY_TYPE              IS NULL " 
                +"   OR pc.ENTRY_TYPE                 IN ('Style','StyleColor','SKU', 'ComplexPack', 'PackColor', '') ) " 
                +"   AND UPPER(ibs.ITEM_CATEGORY_NAME) = UPPER('Merchandise_Hierarchy') " 
                +"   ) , " 
                +"   petitemmerch AS " 
                +"   (SELECT pic.ORIN, " 
                +"     pic.CATEGORY_ID, " 
                +"     pic.CATEGORY_NAME, " 
                +"     pic.ITEM_CATEGORY_ID, " 
                +"     ics.SUB_CLASS, " 
                +"     pic.ENTRY_TYPE " 
                +"   FROM petitemcatalog pic, " 
                +"     VENDORPORTAL.ADSE_MERCHANDISE_HIERARCHY amh, " 
                +"     XMLTABLE( 'for $j in $XML_DATA/pim_category/merchandise_category_header      let           $subclass := tokenize($j//full_path, \"///\")[last()]              return           <merchandise_category_header>          <full_path>{$subclass}</full_path>         </merchandise_category_header>' PASSING amh.XML_DATA AS \"XML_DATA\" COLUMNS SUB_CLASS VARCHAR(100) path '/merchandise_category_header/full_path') ics " 
                +"   WHERE pic.ITEM_CATEGORY_ID = amh.MDMID " 
                +"   AND ( pic.ENTRY_TYPE      IS NULL " 
                +"   OR pic.ENTRY_TYPE         IN ('Style','StyleColor','SKU', 'ComplexPack', 'PackColor' , '' ) ) " 
                +"   AND ( pic.ENTRY_TYPE      IS NULL " 
                +"   OR pic.ENTRY_TYPE         IN ('Style','StyleColor','SKU', 'ComplexPack', 'PackColor', '') ) " 
                +"   ) " 
                +" SELECT pim.ORIN ORIN, " 
                +"   pim.CATEGORY_ID PET_CATEGORY_ID, " 
                +"   pim.CATEGORY_NAME PET_CATEGORY_NAME, " 
                +"   pim.SUB_CLASS SUB_CLASS, " 
                +"   ids.MERCH_CATEGORY_ID MERCH_CATEGORY_ID, " 
                +"   ids.MERCH_CATEGORY_NAME MERCH_CATEGORY_NAME " 
                +" FROM petitemmerch pim, " 
                +"   VENDORPORTAL.ADSE_MERCHANDISE_HIERARCHY amh1, " 
                +"   XMLTABLE( 'for $j in $XML_DATA/pim_category/entry/Merchandise_Hier_Spec/IPH_Category_Mappings  let         $leafnode7 := $j//Level_7,        $leafnode6 := if(string-length($leafnode7) = 0) then $j//Level_6 else $leafnode7,        $leafnode5 := if(string-length($leafnode6) = 0) then $j//Level_5 else $leafnode6,        $leafnode4 := if(string-length($leafnode5) = 0) then $j//Level_4 else $leafnode5,        $leafnode3 := if(string-length($leafnode4) = 0) then $j//Level_3 else $leafnode4,        $leafnode2 := if(string-length($leafnode3) = 0) then $j//Level_2 else $leafnode3,        $leafnode1 := if(string-length($leafnode2) = 0) then $j//Level_1 else $leafnode2,        $merchcategoryid := tokenize($leafnode1,\"-\")[1],        $merchcategoryname := tokenize($leafnode1,\"-\")[last()]        return         <IPH_Category_Mappings>      <category_id>{$merchcategoryid}</category_id>      <category_name>{$merchcategoryname}</category_name>     </IPH_Category_Mappings>' PASSING amh1.XML_DATA AS " 
                +"   \"XML_DATA\" COLUMNS MERCH_CATEGORY_ID VARCHAR(100) path '/IPH_Category_Mappings/category_id', MERCH_CATEGORY_NAME VARCHAR(100) path '/IPH_Category_Mappings/category_name') ids " 
                +" WHERE pim.ITEM_CATEGORY_ID = amh1.MDMID " 
                +" AND ( pim.ENTRY_TYPE      IS NULL " 
                +" OR pim.ENTRY_TYPE         IN ('Style','StyleColor','SKU', 'ComplexPack', 'PackColor', '' ) ) " 
                +" AND ( pim.ENTRY_TYPE      IS NULL " 
                +" OR pim.ENTRY_TYPE         IN ('Style','StyleColor','SKU', 'ComplexPack', 'PackColor', '') )   " ;*/
        /**
         * MODICATION END AFUAXK4
         * DATE: 02/05/2016
         */

       // System.out.println("GET_IPH_CATEGORIES FROM MERCH -" +GET_IPH_CATEGORY_INFORMATION_XQUERY);
        return GET_IPH_CATEGORY_INFORMATION_XQUERY;

    }



    public  String getIphCategoriesFromAdsePetCatalog(String orinNmber ) {
        LOGGER.info("start of getIphCategoriesFromAdsePetCatalog------------------");
        String GET_IPH_CATEGORY_INFORMATION_XQUERY = " WITH Input(ORIN) AS " 
            +"   ( SELECT :orinNo ORIN FROM dual " 
            +"   ) " 
            +" SELECT pet.mdmid ORIN, " 
            +"   ias.CATEGORY_DESC PET_CATEGORY_NAME, " 
            +"   NULL SUB_CLASS, " 
            +"   NULL MERCH_CATEGORY_ID, " 
            +"   NULL MERCH_CATEGORY_NAME " 
            +" FROM Input inp, " 
            +"   VENDORPORTAL.ADSE_PET_CATALOG pet, " 
            +"   XMLTABLE( 'for $j in $XML_DATA/pim_entry/pet_entry_header/category_paths/category     " 
            +"   let            " 
            +"   $categorydesc := $j//path,     " 
            +"   $categoryname := tokenize($j//path, \"///\")[1]         " 
            +"   return       " 
            +"   <category>         " 
            +"     <path>{$categorydesc}</path>       " 
            +"     <name>{$categoryname}</name>      " 
            +"   </category>'  " 
            +"   PASSING pet.XML_DATA AS \"XML_DATA\"  " 
            +"   COLUMNS   " 
            +"   CATEGORY_DESC VARCHAR(1000) path '/category/path',  " 
            +"   CATEGORY_NAME VARCHAR(100) path '/category/name') ias " 
            +" WHERE pet.mdmid = inp.ORIN " 
            +" AND ias.CATEGORY_NAME LIKE 'Item_Primary_Hierarchy' " ;
        return GET_IPH_CATEGORY_INFORMATION_XQUERY;

    }



    /**
     * Gets the iph categories from adse pet catalog.
     *
     * @param orinNmber the orin nmber
     * @return the iph categories from adse pet catalog
     */

    public  String getIphCategoriesFromAdsePetCatalog2(String orinNmber ) {
        LOGGER.info("start of getIphCategoriesFromAdsePetCatalog------------------");

        final String GET_IPH_CATEGORY_INFORMATION_XQUERY=" With "
                +"  "
                +" Input(ORIN) as (Select :orinNo ORIN from dual) "
                +"  "
                +" select  "
                +" pet.mdmid ORIN,  "
                +" ias.CATEGORY_ID PET_CATEGORY_ID, "
                +" ias.CATEGORY_NAME PET_CATEGORY_NAME, "
                +" NULL SUB_CLASS, "
                +" NULL MERCH_CATEGORY_ID, "
                +" NULL MERCH_CATEGORY_NAME "
                +" from Input inp,VENDORPORTAL.ADSE_PET_CATALOG pet, "
                +" XMLTABLE( "
                +" 'for $j in $XML_DATA/pim_entry/pet_entry_header/category_paths/category[last()] "
                +" let  "
                +"                     $categoryid := $j//pk, "
                +"       $categorydesc := $j//path, "
                +"       $categoryname := tokenize(tokenize($j//path, \"///\")[last()],\"-\")[last()] "
                +"       return   "
                +"                  <category> "
                +"     <pk>{$categoryid}</pk> "
                +"     <path>{$categorydesc}</path> "
                +"     <name>{$categoryname}</name> "
                +"    </category>' "
                +"       PASSING pet.XML_DATA AS \"XML_DATA\" "
                +"       COLUMNS  "
                +"     CATEGORY_ID VARCHAR(100) path '/category/pk', "
                +"                   CATEGORY_DESC VARCHAR(100) path '/category/path', "
                +"     CATEGORY_NAME VARCHAR(100) path '/category/name') ias "
                +"     where "
                +"     pet.mdmid = inp.ORIN " ;
      //  LOGGER.info("GET_IPH_CATEGORY_INFORMATION_XQUERY-----------------"+GET_IPH_CATEGORY_INFORMATION_XQUERY);
        return GET_IPH_CATEGORY_INFORMATION_XQUERY;

    }


    /**
     * Gets the omini channle color family.
     *
     * @return the omini channle color family
     */
    public  String getOminiChannleColorFamily( ) {

        final String GET_OMINI_CHANNEL_COLORFAMILY_XQUERY=


                "  with "

                    + "   referencetable AS( "
                    + "   select "
                    + "    CODE,"
                    + "   COLOR_CODE_START,"
                    + "   COLOR_CODE_END,"
                    + "    SUPER_COLOR_CODE,"
                    + "   SUPER_COLOR_DESC  "
                    + "   from vendorportal.ADSE_REFERENCE_DATA, "
                    + "   xmltable('for $i in $pet/pim_entry/entry/Omni_Channel_Color_Family_Spec  "
                    + "   let "
                    + "    $code := $i//Code,"
                    + "   $colorcodestart := $i//COLOR_CODE_BEGIN,"
                    + "    $colorcodeend := $i//COLOR_CODE_END,"
                    + "   $supercolorcode := $i//SUPER_COLOR_CODE,"
                    + "   $supercolordesc := $i//SUPER_COLOR_NAME "
                    + "  return "
                    + " <ref> "
                    + "  <code>{$code}</code> "
                    + "  <colorcodestart>{$colorcodestart}</colorcodestart> "
                    + "   <colorcodeend>{$colorcodeend}</colorcodeend> "
                    + "  <supercolorcode>{$supercolorcode}</supercolorcode> "
                    + "  <supercolordesc>{$supercolordesc}</supercolordesc>  "
                    + "  </ref>' "
                    + "  passing XML_DATA as \"pet\" "
                    + "    columns "
                    + "    CODE         varchar2(40) path '/ref/code', "
                    + "   COLOR_CODE_START varchar2(40) path '/ref/colorcodestart',"
                    + "   COLOR_CODE_END varchar2(40) path '/ref/colorcodeend',"
                    + "  SUPER_COLOR_CODE varchar2(40) path '/ref/supercolorcode',"
                    + "   SUPER_COLOR_DESC varchar2(40) path '/ref/supercolordesc') i "
                    + "   where Entry_Type = 'Omnichannel_Color_Family' ) "

                    + "    Select * from referencetable ";

        return GET_OMINI_CHANNEL_COLORFAMILY_XQUERY;

    }



    /**
     * Ge cars brand query.
     *
     * @return the string
     */
    public String getOmniChannelBrandQuery(String orinNumber, String supplierId) {
        String GET_OMNI_CHANNEL_BRAND_XQUERY = " WITH Input(SUPPLIER_ID, ORIN) AS " 
                +"   ( SELECT :supplier SUPPLIER_ID, :orinNo ORIN FROM dual " 
                +"   ), "
                /**
                 * MODIFIED BY AFUAXK4
                 * DATE: 02/05/2016
                 */
                
                +"   referencefirst AS                                                                            "
                +"   (SELECT /*LISTAGG(CODE,'|') WITHIN GROUP (ORDER BY CODE) CODE,*/                             "
                +"  ARD.MDMID CODE,                                                                               "
                +"     ARD.PARENT_MDMID SUPPLIERID,                                                               "
                +"     ARD.CONTAINER,                                                                             "
                +"     IP.ORIN,                                                                                   "
                +"     ARD.THEVALUE CODEDESCRIPTION                                                               "
                +"   FROM ADSE_REFERENCE_DATA ARD,                                                                "
                +"     INPUT IP                                                                                   "
                +"   WHERE IP.SUPPLIER_ID =ARD.PARENT_MDMID                                                       "
                +"   AND ARD.CONTAINER IN ('OmniChannelBrand')                                                    "
                +"   order by CODE                                                                                "
                +"   )                                                                                            "
                +"   SELECT RF.CODE OMNI_BRANDS,                                                                  "
                +"     RF.CODEDESCRIPTION OMNI_BRANDS_DESCRIPTION,                                                "
                +"     /*RF.CODEDESCRIPTION CARS_BRAND,*/                                                         "
                +"     T.OMNICODE OMNI_BRAND,                                                                     "
                +"     T.OMNICODECOMPLEXPACK, /*Added for Complex Pack*/                                          "
                +"     RF.SUPPLIERID SUPPLIER,                                                                    "
                +"     RF.CONTAINER CONTAINER_TYPE,                                                               "
                +"     APC.MDMID ORIN,                                                                            "
                +"     APC.ENTRY_TYPE                                                                             "
                +"   FROM referencefirst RF,                                                                      "
                +"     VENDORPORTAL.ADSE_PET_CATALOG APC,                                                         "
                +"     XMLTABLE( '                                                                                "
                +"     let $code := $XML_DATA/pim_entry/entry/Ecomm_Style_Spec/OmniChannelBrand,                  "
                +"     $codeComplex := $XML_DATA/pim_entry/entry/Ecomm_ComplexPack_Spec/OmniChannelBrand          "
                +"     return                                                                                     "
                +"     <category>                                                                                 "
                +"     <code>{$code}</code>                                                                       "
                +"     <codeComplex>{$codeComplex}</codeComplex>                                                  "
                +"     </category>'                                                                               "
                +"  passing APC.xml_data AS \"XML_DATA\" COLUMNS OMNICODE VARCHAR(100) path '/category/code',OMNICODECOMPLEXPACK VARCHAR(100) path '/category/codeComplex' ) T "
                +"   WHERE APC.MDMID = RF.ORIN ";
                 
          /**
           * MODIFICATION END AFUAXK4 
           * DATE: 02/05/2016
           */

        return GET_OMNI_CHANNEL_BRAND_XQUERY;
    }

    
    /**
     * Gets the pet content managment details.
     *
     * @param orinNumber the orin number
     * @return the pet content managment details
     */
    public  String getPetContentManagmentDetails(String orinNumber) {

        final String GET_PET_CONTENT_MANAGEMENT_INFORMATION_XQUERY=


                " with "
                        +"   INPUT( ORIN) as ( "
                        +"   Select  :orinNo  ORIN FROM dual),"


              +"    TypeIndex(indx, typ) as ((Select 1 indx, 'Style' typ  from  dual) union (Select 2 indx, 'StyleColor' typ  from  dual) union (select 3 indx, 'SKU' typ from dual)) "

              +"    select  "
              +"            aic.MDMID ORIN, nvl(aic.PARENT_MDMID, aic.MDMID) Style, "
              +"            Name Style_Name, Brand, priority, aic.ENTRY_TYPE "
              +"    from VENDORPORTAL.ADSE_ITEM_CATALOG aic, XMLTABLE('for $i in $XML_DATA/pim_entry/entry "
              +"              return <out> "
              +"                          <brand>{$i/Item_UDA_Spec/Brand}</brand> "
              +"                    </out>' "
              +"        passing aic.XML_DATA as \"XML_DATA\" "
              +"        columns "
              +"        Brand varchar(10) path '/out/brand') i, VENDORPORTAL.ADSE_PET_CATALOG pet,"
              +"  XMLTABLE('for $pet in $pets/pim_entry/entry    "
              +"  return <out>   "
              +"   <name>{$pet/Pet_Ctg_Spec/Name}</name> "
              +"   <priority>{$pet/Pet_Ctg_Spec/PO_Number}</priority> "
              +"   </out>' "
              +"    passing pet.xml_data AS \"pets\"  "
              +"    Columns "
              +"    Name VARCHAR2(20) path '/out/name',"
              +"    priority varchar2(10) path '/out/priority') p, Input inp "
              +"    where   aic.PETEXISTS = 'Y'  AND " 
              +"    aic.MOD_DTM = '01-JAN-00 12.00.00.000000000 PM' and aic.mdmid = pet.mdmid "
              +"    and pet.MOD_DTM = '01-JAN-00 12.00.00.000000000 PM' "
              +"        and aic.mdmid = inp.ORIN AND aic.Entry_Type in ('Style', 'Complex Pack') ";

      //  LOGGER.info("GET_PET_CONTENT_MANAGEMENT_INFORMATION_XQUERY------"+GET_PET_CONTENT_MANAGEMENT_INFORMATION_XQUERY);

        return GET_PET_CONTENT_MANAGEMENT_INFORMATION_XQUERY;

    }




    /**
     * Gets the product details.
     *
     * @param orinNumber the orin number
     * @return the product details
     */
    public  String getProductDetails(String orinNumber) {
        String GET_PRODUCT_INFORMATION_XQUERY = " WITH "
                +"   INPUT(ORIN) AS "
                +"   ( "
                +"     SELECT :orinNo ORIN "
                +"     FROM "
                +"       dual "
                +"   ) "
                +"   , "
                +"   TypeIndex(indx, typ) AS ( "
                +"   ( "
                +"     SELECT "
                +"       1 indx, "
                +"       'Style' typ "
                +"     FROM "
                +"       dual "
                +"   ) "
                +" UNION "
                +"   ( "
                +"     SELECT "
                +"       2 indx, "
                +"       'StyleColor' typ "
                +"     FROM "
                +"       dual "
                +"   ) "
                +" UNION "
                +"   ( "
                +"     SELECT "
                +"       3 indx, "
                +"       'SKU' typ "
                +"     FROM "
                +"       dual "
                +"   ) "
                +"   UNION "
                +"   ( "
                +"     SELECT "
                +"       4 indx, "
                +"       'Complex Pack' typ "
                +"     FROM "
                +"       dual "
                +"   ) "
                +"   UNION "
                +"   ( "
                +"     SELECT "
                +"       5 indx, "
                +"       'PackColor' typ "
                +"     FROM "
                +"       dual "
                +"   ) "
                +"   ) "
                +" SELECT "
                +"   NVL(aic.PARENT_MDMID, aic.MDMID) Style_Id, "
                +"   aic.MDMID ORIN_NUM, "
                +"   i2.ProductName Product_Name, "
                +"   i2.ProductDescription DESCRIPTION, "
                +"   aic.Entry_Type "
                +" FROM "
                +"   VENDORPORTAL.ADSE_ITEM_CATALOG aic, "
                +"   XMLTABLE( "
                +"   'for $i in $XML_DATA/pim_entry/entry            let $uda80 := (fn:count($i/Item_UDA_Spec/UDA/Id) gt 0 and $i/Item_UDA_Spec/UDA/Id eq \"80\"),  $non_sellable_pack := (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0 and        ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")),               $removal := $i/Item_Ctg_Spec/System/Removal_Flag eq \"true\"     return  <out>                <dept_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[5]}</dept_id>    <supplier_id>{$i/Item_Ctg_Spec/Supplier[Primary_Flag eq \"true\"]/Id}</supplier_id>   <desc>{$i/Item_Ctg_Spec/Description/Long}</desc>   <flag>{$uda80 and $non_sellable_pack and $removal}</flag>   </out>' "
                +"   passing aic.XML_DATA AS \"XML_DATA\" columns flag VARCHAR(10) path '/out/flag', "
                +"   supplier_id                                     VARCHAR2(20) path "
                +"   '/out/supplier_id', deptid                      VARCHAR2(20) path "
                +"   '/out/dept_id', descr                           VARCHAR2(64) path '/out/desc' "
                +"   ) i, "
                +"   Input inp, "
                +"   VENDORPORTAL.ADSE_PET_CATALOG aic2, "
                +"   XMLTABLE( "
                +"   'for $i in $XML_DATA/pim_entry/entry        return  <out>   <prod_name>{$i//Product_Name}</prod_name>  <prod_desc>{$i//Product_Description}</prod_desc>    </out>' "
                +"   passing aic2.XML_DATA AS \"XML_DATA\" columns ProductName VARCHAR2(300) path "
                +"   '/out/prod_name', ProductDescription                    VARCHAR2(2000) path "
                +"   '/out/prod_desc') i2 "
                +" WHERE  "
                +"   flag                              = 'false'  AND aic.PETEXISTS = 'Y' "
                +" AND aic.MOD_DTM                     = '01-JAN-00 12.00.00.000000000 PM' "
                +" AND aic.MDMID                       =inp.ORIN "
                +" AND aic.Entry_type                 IN ('SKU', 'StyleColor', 'Style','Complex Pack','PackColor') "
                +" AND aic2.MOD_DTM                    = '01-JAN-00 12.00.00.000000000 PM' "
                +" AND aic2.Entry_type                 IN ('Style','ComplexPack') "
                +" AND NVL(aic.PARENT_MDMID, aic.MDMID)=aic2.mdmid " ;
      //  LOGGER.info("GET_PRODUCT_INFORMATION_XQUERY--------------"+GET_PRODUCT_INFORMATION_XQUERY);
        return GET_PRODUCT_INFORMATION_XQUERY;

    }




    /**
     * Gets the sku attributes.
     *
     * @param skuOrinNumber the sku orin number
     * @return the sku attributes
     */
    
    public  String getSkuAttributes(String skuOrinNumber)
    {
        String GET_SKU_ATTRIBUTES_INFORMATION_XQUERY = "   WITH INPUT( ORIN) AS " 
                +"   ( Select :orinNo Orin From Dual " 
                +"   ), " 
                +"    " 
                +"   Items As   " 
                +"   ( " 
                +"   Select  " 
                +"     item.mdmid ORIN, " 
                +"   item.number_04 BELK_UPC, " 
                +"   '/pim_entry/entry/Item_Ctg_Spec/IDB_Id' BELK_UPC_XPath, " 
                +"   item.Primary_UPC VENDOR_UPC, " 
                +"   '/pim_entry/entry/Item_Ctg_Spec/Supplier/UPCs/UPC' VENDOR_UPC_XPath, " 
                +"   i.vendor_size, " 
                +"   '/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description' Vendor_Size_Xpath, " 
                +"   '/pim_entry/entry/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Code' OMNI_SIZE_DESC_Code_XPath, " 
                +"   I.Source_Domestic, " 
                +"   i.launchDate, " 
                +"   i.giftBox, " 
                +"   i.gwp, " 
                +"   I.Pwp, " 
                +"   I.Classid, " 
                +"   I.Vendor_Size_Code, " 
                +"   I.Deptid, " 
                +"   S.VENID, " 
                +"   I.Pyg, " 
                +"   i.productDimesionUom, " 
                +"   i.productLength, " 
                +"   i.productHeight, " 
                +"   i.productWidth, " 
                +"   i.productWeightUom, " 
                +"   I.Productweight " 
                +"   From " 
                +"   Vendorportal.Adse_Item_Catalog Item, " 
                +"   xmltable(  'for $i in $pet/pim_entry/entry    " 
                +"   let    " 
                +"   $belk_upc := (for $upc in $i/Item_Ctg_Spec/IDB_Id return $upc)[1],   " 
                +"   $vendor_upc := (for $upc in $i/Item_Ctg_Spec/Supplier/UPCs/UPC return $upc)[1],     " 
                +"   $vendor_size_code := $i/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Code,       " 
                +"   $vendor_size := $i/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description,    " 
                +"   $SourcedDomestic :=$i/Ecomm_SKU_Spec/SourcedDomestically,    " 
                +"   $launchDate :=$i/Ecomm_SKU_Spec/Launch_Date,   " 
                +"   $giftBox :=$i/Ecomm_SKU_Spec/Gift_Box,    " 
                +"   $gwp :=$i/Ecomm_SKU_Spec/is_GWP,      " 
                +"   $pwp :=$i/Ecomm_SKU_Spec/is_PWP,     " 
                +"   $pyg :=$i/Ecomm_SKU_Spec/Is_PYG,    " 
                +"   $productDimesionUom :=$i/Ecomm_SKU_Spec/Product_Dimensions/Product_Dimensions_UOM,   " 
                +"   $productLength :=$i/Ecomm_SKU_Spec/Product_Dimensions/Length,    " 
                +"   $productHeight :=$i/Ecomm_SKU_Spec/Product_Dimensions/Height,   " 
                +"   $productWidth :=$i/Ecomm_SKU_Spec/Product_Dimensions/Width,  " 
                +"   $productWeigthUom :=$i/Ecomm_SKU_Spec/Product_Weight/UOM,    " 
                +"   $productWeigth :=$i/Ecomm_SKU_Spec/Product_Weight/Weight    " 
                +"   return   " 
                +"   <out>      " 
                +"   <dept_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[5]}</dept_id>     " 
                +"   <class_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[6]}</class_id>     " 
                +"   <supplier_id>{$i/Item_Ctg_Spec/Supplier[Primary_Flag eq \"true\"]/Id}</supplier_id>               " 
                +"   <belk_upc>{$belk_upc}</belk_upc>              " 
                +"   <vendor_upc>{$vendor_upc}</vendor_upc>            " 
                +"   <vendor_size_code>{$vendor_size_code}</vendor_size_code>        " 
                +"   <vendor_size>{$vendor_size}</vendor_size>           " 
                +"   <SourcedDomestic>{$SourcedDomestic}</SourcedDomestic>          " 
                +"   <launchDate>{$launchDate}</launchDate>          " 
                +"   <giftBox>{$giftBox}</giftBox>        " 
                +"   <gwp>{$gwp}</gwp>     " 
                +"   <pwp>{$pwp}</pwp>     " 
                +"   <pyg>{$pyg}</pyg>     " 
                +"   <productDimesionUom>{$productDimesionUom}</productDimesionUom>    " 
                +"   <productLength>{$productLength}</productLength>      " 
                +"   <productHeight>{$productHeight}</productHeight>      " 
                +"   <productWidth>{$productWidth}</productWidth>        " 
                +"   <productWeigthUom>{$productWeigthUom}</productWeigthUom>     " 
                +"   <productWeigth>{$productWeigth}</productWeigth>   " 
                +"   </out>' passing item.XML_DATA AS \"pet\" columns  " 
                +"   Supplier_Id Varchar2(20) Path '/out/supplier_id',  " 
                +"   deptid VARCHAR2(20) path '/out/dept_id', " 
                +"   Classid Varchar2(20) Path '/out/class_id',  " 
                +"   VENDOR_UPC VARCHAR2(40) path '/out/vendor_upc', " 
                +"   Belk_Upc Varchar2(40) Path '/out/belk_upc',  " 
                +"   Vendor_Size_Code Varchar2(6) Path '/out/vendor_size_code',  " 
                +"   Vendor_Size Varchar2(40) Path '/out/vendor_size',  " 
                +"   Source_Domestic Varchar2(40) Path '/out/SourcedDomestic',  " 
                +"   Launchdate Varchar2(40) Path '/out/launchDate',  " 
                +"   Giftbox Varchar2(40) Path '/out/giftBox',  " 
                +"   Gwp Varchar2(40) Path '/out/gwp',  " 
                +"   pwp VARCHAR2(40) path '/out/pwp', " 
                +"   pyg VARCHAR2(40) path '/out/pyg', " 
                +"   productDimesionUom VARCHAR2(40) path '/out/productDimesionUom', " 
                +"   productLength VARCHAR2(40) path '/out/productLength', " 
                +"   productHeight VARCHAR2(40) path '/out/productHeight', " 
                +"   productWidth VARCHAR2(40) path '/out/productWidth', " 
                +"   productWeightUom VARCHAR2(40) path '/out/productWeigthUom', " 
                +"   productWeight VARCHAR2(40) path '/out/productWeigth') i, " 
                +"  VENDORPORTAL.ADSE_SUPPLIER_CATALOG sup, " 
                +"   XMLTABLE('for $i in $XML_DATA/pim_entry/entry       " 
                +"   return $i' passing sup.xml_data AS \"XML_DATA\" COLUMNS " 
                +"   Id Varchar2(20) Path 'Supplier_Ctg_Spec/Id',  " 
                +"   VenId VARCHAR2(20) path 'Supplier_Ctg_Spec/VEN_Id' ) s, " 
                +"   Input Inp " 
                +"   Where  " 
                +"   item.Entry_Type  = 'SKU'  AND item.PETEXISTS = 'Y' " 
                +"   And Item.Mdmid         =Inp.Orin " 
                +"   And I.Supplier_Id      = Sup.Mdmid " 
                +"   And Item.Mod_Dtm       = '01-JAN-00 12.00.00.000000000 PM' " 
                +"   And Sup.Mod_Dtm        = '01-JAN-00 12.00.00.000000000 PM' " 
                +"   ) " 
                +"   Select  " 
                +"   I.Orin, " 
                +"   I.Belk_Upc, " 
                +"   I.Belk_Upc_Xpath, " 
                +"   I.Vendor_Upc, " 
                +"   I.Vendor_Upc_Xpath, " 
                +"   I.Vendor_Size, " 
                +"   I.Vendor_Size_Xpath, " 
                +"   (case  " 
                +"           when o.OMNI_SIZE_DESC is not null then o.OMNI_SIZE_DESC " 
                +"           Else O2.Omni_Size_Desc " 
                +"           End ) Omni_Size_Desc, " 
                +"   I.Omni_Size_Desc_Code_Xpath, " 
                +"   I.Source_Domestic, " 
                +"   i.vendor_size_code as NRF_SIZE_CODE, " 
                +"   I.LaunchDate, " 
                +"   I.Giftbox, " 
                +"   I.Gwp, " 
                +"   I.Pwp, " 
                +"   I.Pyg, " 
                +"   I.Productdimesionuom, " 
                +"   I.Productlength, " 
                +"   I.Productheight, " 
                +"   I.Productwidth, " 
                +"   I.Productweightuom, " 
                +"   I.Productweight " 
                +"   From " 
                +"   Items I " 
                +"   LEFT OUTER JOIN " 
                +"         Vendorportal.Adse_Vendor_Omnisize_Desc Omni " 
                +"         On  " 
                +"         I.Vendor_Size_Code = Omni.Nrf_Size_Code " 
                +"         And I.Deptid = Omni.Dept_Id " 
                +"         And I.Classid = Omni.Class_Id " 
                +"         And I.Venid = Omni.Vendor_Id " 
                +"         LEFT OUTER JOIN " 
                +"         xmltable('for $i in $XML//omni_size_desc return $i' passing omni.XML_DATA AS \"XML\" columns  " 
                +"         OMNI_SIZE_DESC VARCHAR(40) path '.' ) o " 
                +"         ON 1 = 1 " 
                +"         LEFT OUTER JOIN " 
                +"        VENDORPORTAL.ADSE_VENDOR_OMNISIZE_DESC omni2 " 
                +"         On  " 
                +"         I.Vendor_Size_Code = Omni2.Nrf_Size_Code " 
                +"         And I.Deptid = Omni2.Dept_Id " 
                +"         And I.Classid = Omni2.Class_Id " 
                +"         LEFT OUTER JOIN " 
                +"         xmltable('for $i in $XML//omni_size_desc return $i' passing omni2.XML_DATA AS \"XML\" columns  " 
                +"         Omni_Size_Desc Varchar(40) Path '.' ) O2 " 
                +"         On 1 = 1       " ;

     //   LOGGER.info("SKU Attributes Query ----------------> "+GET_SKU_ATTRIBUTES_INFORMATION_XQUERY);

        return  GET_SKU_ATTRIBUTES_INFORMATION_XQUERY;

    }

    /**
     * Gets the style and its child details.
     *
     * @param orinNumber the orin number
     * @param userRole the user role
     * @return the style and its child details
     */
    public  String getStyleAndItsChildDetails(String orinNumber,String userRole)
    {


        final String GET_STYLE_STYLECOLOR_SKU_INFORMATION_XQUERY=
                " WITH "
                        +" INPUT( ORIN) AS "
                        + " ( "
                        +"   SELECT :orinNo  ORIN "
               +"   FROM "
               +"     dual "
               +" ) "
               +" , "
               +" TypeIndex(indx, typ) AS ( "
               +"  ( "
               +"   SELECT "
               +"     1 indx,"
               +"     'Style' typ "
               +  " FROM "
               +"     dual "
               +" ) "
               + " UNION "
               +"  ( "
               +"    SELECT "
               +"      2 indx, "
               +"      'StyleColor' typ "
               +"    FROM "
               +"      dual "
               +"  ) "
               +" UNION "
               +"  ( "
               +"    SELECT "
               +"      3 indx,"
               +"      'SKU' typ "
               +"    FROM "
               +"      dual "
               +"  ) "
               +"  ),"
               +"  Items AS "
               +"  ( "
               +"    SELECT "
               +"      NVL(aic.PARENT_MDMID, aic.MDMID) PARENT_MDMID,"
               +"      aic.MDMID,"
               +"      aic.ENTRY_TYPE,"
               +"      i.supplier_id PRIMARY_SUPPLIER_ID,"
               +"      deptid dept_id,"
               +"      classid Class_id,"
               +"      VENDOR_COLOR_CODE,"
               +"      VENDOR_COLOR_DESC,"
               +"      VENDOR_SIZE_CODE,"
               +"      VENDOR_SIZE_DESC,"
               +"      p.ContentState,"
               +"      p.completion_date "
               +"    FROM "
               +"      VENDORPORTAL.ADSE_ITEM_CATALOG aic,"
               +"      XMLTABLE( "
               +"      'for $i in $XML_DATA/pim_entry/entry   "
               +" let $uda80 := (fn:count($i/Item_UDA_Spec/UDA/Id) gt 0 and $i/Item_UDA_Spec/UDA/Id eq \"80\"),   "
               +" $non_sellable := (fn:count($i/Item_Simple_Pack_Spec/Sellable_Flag/text()) gt 0 and "
               +" ($i/Item_Simple_Pack_Spec/Sellable_Flag eq \"false\")),"
               +" $non_sellable_pack := (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0 and "
               +" ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")),    "
               +"  $removal := $i/Item_Ctg_Spec/System/Removal_Flag eq \"true\"  "
               +"  return  <out> "
               +"  <dept_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[5]}</dept_id> "
               +"  <class_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[6]}</class_id>  "
               +"  <supplier_id>{$i/Item_Ctg_Spec/Supplier[Primary_Flag eq \"true\"]/Id}</supplier_id>   "
               +"  <flag>{$uda80 and $non_sellable_pack and $removal}</flag> "
               +"  <colorCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code}</colorCode>  "
               +"  <colorDesc>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description}</colorDesc> "
               +"  <sizeCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Code}</sizeCode> "
               +"  <sizeDesc>{$i/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description}</sizeDesc>  "
               +"  </out>' "
               +"        passing aic.XML_DATA AS \"XML_DATA\" columns flag VARCHAR(10) path "
               +"        '/out/flag', supplier_id                        VARCHAR2(20) path "
               +"        '/out/supplier_id', deptid                      VARCHAR2(20) path "
               +"        '/out/dept_id', classid                         VARCHAR2(20) path "
               +"        '/out/class_id', descr                          VARCHAR2(64) path "
               +"        '/out/desc', VENDOR_COLOR_CODE                  VARCHAR2(10) path "
               +"        '/out/colorCode', VENDOR_COLOR_DESC             VARCHAR2(40) path "
               +"        '/out/colorDesc', VENDOR_SIZE_CODE              VARCHAR2(10) path "
               +"        '/out/sizeCode', VENDOR_SIZE_DESC               VARCHAR2(10) path "
               +"        '/out/sizeDesc') i,"
               +"        ADSE_PET_CATALOG pet,"
               +"        XMLTABLE( "
               +"       'for $pet in $pets/pim_entry/entry  "
               +"  let $completionDate := $pet//Completion_Date, "
               +"  $contentState := $pet//ContentState  "
               +"  return <out>  "
               +"  <id>{$pet/Pet_Ctg_Spec/Id}</id>  "
               +"  <completion_date>{$completionDate}</completion_date> "
               +"  <contentState>{$contentState}</contentState>  "
               +"  <contentContainer>{fn:data($contentState/@container)}</contentContainer> "
               +"  </out>' "
               +"        passing pet.XML_DATA AS \"pets\" columns completion_date VARCHAR2(10) path "
               +"        '/out/completion_date', contentState                   VARCHAR2(20) path "
               +"        '/out/contentState', contentContainer                  VARCHAR2(40) PATH "
               +"        '/out/contentContainer') p,"
               +"        Input inp,"
               +"        VENDORPORTAL.adse_reference_data cont "
               +"      WHERE "
               +"        flag             = 'false' "
               +"      AND aic.mdmid      =pet.mdmid "
               +"      AND cont.mdmid     =p.ContentState "
               +"      AND cont.entry_type=p.contentContainer "
               +"      AND "
               +"      ( "
               +"          cont.thevalue  IS NULL "
               +"        OR cont.thevalue <> 'Completed' "
               +"       ) "
               +"      AND pet.MOD_DTM = '01-JAN-00 12.00.00.000000000 PM' "
               +"      AND aic.MOD_DTM = '01-JAN-00 12.00.00.000000000 PM' "
               +"      AND "
               +"        ( "
               +"          aic.MDMID        =inp.ORIN "
               +"        OR aic.parent_mdmid=inp.ORIN "
               +"        ) "
               +"      AND aic.Entry_type IN ('SKU', 'Style', 'StyleColor') "
               +"    ) "
               +"    , "
               +"    ItemSupplier AS "
               +"    ( "
               +"      SELECT "
               +"        i.PARENT_MDMID, "
               +"        i.MDMID,"
               +"        i.PRIMARY_SUPPLIER_ID,"
               +"        i.dept_id, "
               +"        i.Class_Id,"
               +"        i.VENDOR_COLOR_CODE,"
               +"        i.VENDOR_COLOR_DESC,"
               +"        i.VENDOR_SIZE_CODE,"
               +"        i.VENDOR_SIZE_DESC,"
               +"        i.ENTRY_TYPE,"
               +"        s.VenId,"
               +"        i.ContentState,"
               +"        i.completion_date "
               +"      FROM "
               +"        Items i,"
               +"        VENDORPORTAL.ADSE_SUPPLIER_CATALOG sup,"
               +"        XMLTABLE('for $i in $XML_DATA/pim_entry/entry  "
               +"  return $i' "
               +"        passing sup.xml_data AS \"XML_DATA\" COLUMNS Id VARCHAR2(20) path "
               +"        'Supplier_Ctg_Spec/Id', VenId                 VARCHAR2(20) path "
               +"        'Supplier_Ctg_Spec/VEN_Id' ) s "
               +"      WHERE "
               +"        sup.MOD_DTM             = '01-JAN-00 12.00.00.000000000 PM' "
               +"      AND i.PRIMARY_SUPPLIER_ID = sup.MDMID "
               +"    ) "
               +"  SELECT "
               +"    PARENT_MDMID Style_ID,"
               +"    MDMID ORIN,"
               +"    VENDOR_COLOR_CODE "
               +"    || ' ' "
               +"    || VENDOR_COLOR_DESC COLOR,"
               +"    VENDOR_SIZE_CODE "
               +"   || ' ' "
               +"    || VENDOR_SIZE_DESC Vendor_Size,"
               +"    OMNI_SIZE_DESC,"
               +"    ContentState,"
               +"    completion_date,"
               +"    ENTRY_TYPE "
               +"  FROM "
               +"   ( "
               +"      SELECT "
               +"        PARENT_MDMID ,"
               +"        MDMID ,"
               +"        PRIMARY_SUPPLIER_ID,"
               +"        dept_id,"
               +"        VENDOR_COLOR_CODE,"
               +"        VENDOR_COLOR_DESC ,"
               +"        VENDOR_SIZE_CODE,"
               +"        VENDOR_SIZE_DESC ,"
               +"        OMNI_SIZE_DESC,"
               +"        ContentState,"
               +"        completion_date,"
               +"        ENTRY_TYPE "
               +"      FROM "
               +"        ( "
               +"          SELECT "
               +"            i.PARENT_MDMID,"
               +"            i.MDMID,"
               +"            i.PRIMARY_SUPPLIER_ID,"
               +"            i.dept_id,"
               +"            i.VENDOR_COLOR_CODE,"
               +"            i.VENDOR_COLOR_DESC,"
               +"            i.VENDOR_SIZE_CODE,"
               +"            i.VENDOR_SIZE_DESC,"
               +"            o.OMNI_SIZE_DESC,"
               +"            i.ContentState,"
               +"            i.completion_date,"
               +"            i.ENTRY_TYPE "
               +"          FROM "
               +"            ItemSupplier i,"
               +"            ADSE_VENDOR_OMNISIZE_DESC omni,"
               +"            xmltable('for $i in $XML//omni_size_desc return $i' passing "
               +"            omni.XML_DATA AS \"XML\" columns OMNI_SIZE_DESC VARCHAR(40) path '.' ) "
               +"            o "
               +"          WHERE "
               +"           i.Entry_Type         = 'SKU' "
               +"         AND i.VENDOR_SIZE_CODE =omni.NRF_SIZE_CODE "
               +"         AND i.dept_id          =omni.DEPT_ID "
               +"         AND i.Class_Id         =omni.CLASS_ID "
               +"         AND i.VenId            =omni.VENDOR_ID "
               +"       ) "
               +"     UNION "
               +"       ( "
               +"        SELECT "
               +"           i.PARENT_MDMID,"
               +"           i.MDMID,"
               +"           i.PRIMARY_SUPPLIER_ID,"
               +"           i.dept_id,"
               +"           i.VENDOR_COLOR_CODE,"
               +"           i.VENDOR_COLOR_DESC,"
               +"           i.VENDOR_SIZE_CODE,"
               +"           i.VENDOR_SIZE_DESC,"
               +"           NULL OMNI_SIZE_DESC,"
               +"           i.ContentState,"
               +"           i.completion_date,"
               +"           i.ENTRY_TYPE"
               +"         FROM "
               +"           ItemSupplier i "
               +"         WHERE "
               +"           i.Entry_Type IN ('Style', 'StyleColor') "
               +"       )"
               +"   ) "
               +"   ,"
               +"   TypeIndex typInd "
               +" WHERE "
               +"   ENTRY_TYPE = typInd.typ "
               +" ORDER BY "
               +"   NVL(PARENT_MDMID, MDMID),"
               +"   indx";

       // LOGGER.info("GET_STYLE_STYLECOLOR_SKU_INFORMATION_XQUERY......"+GET_STYLE_STYLECOLOR_SKU_INFORMATION_XQUERY);
        return GET_STYLE_STYLECOLOR_SKU_INFORMATION_XQUERY;

    }

    /**
     * Gets the style and style color and sku.
     *
     * @param orinNumber the orin number
     * @param orinNumber2
     * @return the style and style color and sku
     */
    public  String getStyleAndStyleColorAndSKU(String roleName, String orinNumber)
    {
        /**
         * MODIFICATION START AFUAXK4
         * DATE: 02/05/2016
         */
        String GET_STYLE_STYLECOLOR_SKU_INFORMATION_XQUERY = " WITH INPUT( ORIN) AS " 
            +"   (SELECT :orinNo ORIN " 
            +"   FROM dual " 
            +"   ) , " 
            +"   TypeIndex(indx, typ) AS (  " 
            +"   ( SELECT 1 indx, 'Style' typ FROM dual " 
            +"   ) " 
            +" UNION " 
            +"   ( SELECT 2 indx, 'StyleColor' typ FROM dual " 
            +"   ) " 
            +" UNION " 
            +"   ( SELECT 3 indx, 'SKU' typ FROM dual " 
            +"   ) " 
            +" UNION " 
            +"   ( SELECT 4 indx, 'Complex Pack' typ FROM dual " 
            +"   ) " 
            +" UNION " 
            +"   ( SELECT 5 indx, 'PackColor' typ FROM dual " 
            +"   ) ), Items AS " 
            +"    (SELECT NVL(aic.PARENT_MDMID, aic.MDMID) PARENT_MDMID,           "
            +"      aic.MDMID,                                                     "
            +"      aic.ENTRY_TYPE,                                                "
            +"      i.supplier_id PRIMARY_SUPPLIER_ID,                             "
            +"      deptid dept_id,                                                "
            +"      classid Class_id,                                              "
            +"      VENDOR_COLOR_CODE,                                             "
            +"      VENDOR_COLOR_DESC,                                             "
            +"      VENDOR_SIZE_CODE,                                              "
            +"      VENDOR_SIZE_DESC,                                              "
            +"                                                                     "
            +"      pet.pet_state PETSTATUS,                                       "
            +"      pet.content_status CONTENTSTATUS,                              "
            +"      pet.LAST_PO_DATE completion_date,                              "
            +"      pet.PET_EARLIEST_COMP_DATE, pet.image_status IMAGESTATUS                                      "
            +"    FROM VENDORPORTAL.ADSE_ITEM_CATALOG aic,                         "
            +"      XMLTABLE(                                                      "
            +"      'for $i in $XML_DATA/pim_entry/entry         let               "
            +"      $uda80 := (fn:count($i/Item_UDA_Spec/UDA/Id) gt 0 and $i/Item_UDA_Spec/UDA/Id eq \"80\"),                                                             "
            +"      $non_sellable := (fn:count($i/Item_Simple_Pack_Spec/Sellable_Flag/text()) gt 0 and  ($i/Item_Simple_Pack_Spec/Sellable_Flag eq \"false\")),           "
            +"      $non_sellable_pack := (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0 and  ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")),    "       
            +"      $removal := $i/Item_Ctg_Spec/System/Removal_Flag eq \"true\"        return                                                                            "
            +"      <out>                                                                                                                                               "
            +"      <dept_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[5]}</dept_id>         "
            +"      <class_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[6]}</class_id>       "  
            +"      <supplier_id>{$i/Item_Ctg_Spec/Supplier[Primary_Flag eq \"true\"]/Id}</supplier_id>                                                                   "
            +"      <flag>{$uda80 and $non_sellable_pack and $removal}</flag>                                                                                           "
            +"      <colorCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code}</colorCode>                                                                     "
            +"      <colorDesc>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description}</colorDesc>                                                       "
            +"      <sizeCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Code}</sizeCode>                                                                        "
            +"      <sizeDesc>{$i/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description}</sizeDesc>        </out>'                                           "
            +"      passing aic.XML_DATA AS \"XML_DATA\" columns                                                                                                          "
            +"      flag VARCHAR(10) path '/out/flag',                                                                                                                  "
            +"      supplier_id VARCHAR2(20) path '/out/supplier_id', deptid VARCHAR2(20) path '/out/dept_id',                                                          "
            +"      classid VARCHAR2(20) path '/out/class_id', descr VARCHAR2(64) path '/out/desc',                                                                     "
            +"      VENDOR_COLOR_CODE VARCHAR2(10) path '/out/colorCode', VENDOR_COLOR_DESC VARCHAR2(40) path '/out/colorDesc',                                         "
            +"      VENDOR_SIZE_CODE VARCHAR2(10) path '/out/sizeCode', VENDOR_SIZE_DESC VARCHAR2(10) path '/out/sizeDesc') i,                                          "
            +"                                                                                                                                                          "
            +"      VENDORPORTAL.ADSE_PET_CATALOG pet,                                                                                                                  "
            +"      INPUT INP                                                                                                                                           "
            +"    WHERE flag          = 'false'   AND AIC.PETEXISTS = 'Y'  AND                                                                                                                   "
            +"    AIC.MDMID       =PET.MDMID                                                                                                                            "
            +"    AND pet.MOD_DTM     = '01-JAN-00 12.00.00.000000000 PM'                                                                                               "
            +"    AND aic.MOD_DTM     = '01-JAN-00 12.00.00.000000000 PM'                                                                                               "
            +"    AND ( aic.MDMID     =inp.ORIN OR aic.parent_mdmid =inp.ORIN )                                                                                         "
            +"    AND aic.Entry_type IN ('SKU', 'Style', 'StyleColor', 'Complex Pack', 'PackColor')                                                                     "
            +"    AND pet.pet_state not in ('05')                                                                                                                       "
            +"    ) ,                                                                                                                                                   "
            +"                                                                                                                                                          "
            +"                                                                                                                                                          "
            +"    /*ItemSupplier AS                                                                                                                                     "
            +"    (SELECT i.PARENT_MDMID,                                                                                                                               "
            +"      i.MDMID,                                                                                                                                            "
            +"      i.PRIMARY_SUPPLIER_ID,                                                                                                                              "
            +"      i.dept_id,                                                                                                                                          "
            +"      i.Class_Id,                                                                                                                                         "
            +"      i.VENDOR_COLOR_CODE,                                                                                                                                "
            +"      i.VENDOR_COLOR_DESC,                                                                                                                                "
            +"      i.VENDOR_SIZE_CODE,                                                                                                                                 "
            +"      i.VENDOR_SIZE_DESC,                                                                                                                                 "
            +"      i.ENTRY_TYPE,                                                                                                                                       "
            +"      s.VenId,                                                                                                                                            "
            +"      i.PETSTATUS,                                                                                                                                        "
            +"      i.CONTENTSTATUS,                                                                                                                                    "
            +"      i.completion_date,                                                                                                                                  "
            +"      i.PET_EARLIEST_COMP_DATE, i.IMAGESTATUS                                                                                                                            "
            +"    FROM Items i,                                                                                                                                         "
            +"      VENDORPORTAL.ADSE_SUPPLIER_CATALOG sup,                                                                                                             "
            +"      XMLTABLE('for $i in $XML_DATA/pim_entry/entry    return $i' passing sup.xml_data AS \"XML_DATA\" COLUMNS Id VARCHAR2(20) path 'Supplier_Ctg_Spec/Id', VenId VARCHAR2(20) path 'Supplier_Ctg_Spec/VEN_Id' ) s "
            +"    WHERE sup.MOD_DTM         = '01-JAN-00 12.00.00.000000000 PM'                          "
            +"    AND i.PRIMARY_SUPPLIER_ID = sup.MDMID                                                  "
            +"    ) ,*/                                                                                  "
            +"    Final AS                                                                               "
            +"    (SELECT PARENT_MDMID Style_ID,                                                         "
            +"      MDMID ORIN,                                                                          "
            +"      VENDOR_COLOR_CODE COLOR_CODE,                                                        "
            +"      VENDOR_COLOR_CODE                                                                    "
            +"      || ' '                                                                               "
            +"      || VENDOR_COLOR_DESC COLOR,                                                          "
            +"      VENDOR_SIZE_CODE                                                                     "
            +"      || ' '                                                                               "
            +"      || VENDOR_SIZE_DESC Vendor_Size,                                                     "
            +"      OMNI_SIZE_DESC,                                                                      "
            +"      PETSTATUS,                                                                           "
            +"      CONTENTSTATUS,                                                                       "
            +"      completion_date,                                                                     "
            +"      PET_EARLIEST_COMP_DATE,                                                              "
            +"      ENTRY_TYPE, IMAGESTATUS                                                                           "
            +"    FROM                                                                                   "
            +"      (SELECT PARENT_MDMID ,                                                               "
            +"        MDMID ,                                                                            "
            +"        PRIMARY_SUPPLIER_ID,                                                               "
            +"        dept_id,                                                                           "
            +"        VENDOR_COLOR_CODE,                                                                 "
            +"        VENDOR_COLOR_DESC ,                                                                "
            +"        VENDOR_SIZE_CODE,                                                                  "
            +"        VENDOR_SIZE_DESC ,                                                                 "
            +"        OMNI_SIZE_DESC,                                                                    "
            +"      PETSTATUS,                                                                           "
            +"      CONTENTSTATUS,                                                                       "
            +"      completion_date,                                                                     "
            +"      PET_EARLIEST_COMP_DATE,                                                              "
            +"        ENTRY_TYPE, IMAGESTATUS                                                                         "
            +"      FROM                                                                                 "
            +"        (SELECT i.PARENT_MDMID,                                                            "
            +"          i.MDMID,                                                                         "
            +"          i.PRIMARY_SUPPLIER_ID,                                                           "
            +"          i.dept_id,                                                                       "
            +"          i.VENDOR_COLOR_CODE,                                                             "
            +"          i.VENDOR_COLOR_DESC,                                                             "
            +"          i.VENDOR_SIZE_CODE,                                                              "
            +"          i.VENDOR_SIZE_DESC,                                                              "
            +"          o.OMNI_SIZE_DESC,                                                                "
            +"      i.PETSTATUS,                                                                         "
            +"      i.CONTENTSTATUS,                                                                     "
            +"      i.completion_date,                                                                   "
            +"      i.PET_EARLIEST_COMP_DATE,                                                            "
            +"          i.ENTRY_TYPE, i.IMAGESTATUS                                                                     "
            +"        FROM Items i                                                                       "
            +"        LEFT OUTER JOIN VENDORPORTAL.ADSE_VENDOR_OMNISIZE_DESC omni                        "
            +"        ON i.VENDOR_SIZE_CODE =omni.NRF_SIZE_CODE                                          "
            +"        AND i.dept_id         =omni.DEPT_ID                                                "
            +"        AND i.Class_Id        =omni.CLASS_ID                                               "
            +"        LEFT OUTER JOIN xmltable('for $i in $XML//omni_size_desc return $i' passing omni.XML_DATA AS \"XML\" columns OMNI_SIZE_DESC VARCHAR(40) path '.' ) o "
            +"        ON 1               = 1                                                                    "
            +"        WHERE i.Entry_Type = 'SKU'                                                                "
            +"        )                                                                                         "
            +"      UNION                                                                                       "
            +"        (SELECT i.PARENT_MDMID,                                                                   "
            +"          i.MDMID,                                                                                "
            +"          i.PRIMARY_SUPPLIER_ID,                                                                  "
            +"          i.dept_id,                                                                              "
            +"          i.VENDOR_COLOR_CODE,                                                                    "
            +"          i.VENDOR_COLOR_DESC,                                                                    "
            +"          i.VENDOR_SIZE_CODE,                                                                     "
            +"          i.VENDOR_SIZE_DESC,                                                                     "
            +"          NULL OMNI_SIZE_DESC,                                                                    "
            +"      i.PETSTATUS,                                                                                "
            +"      i.CONTENTSTATUS,                                                                            "
            +"      i.completion_date,                                                                          "
            +"      i.PET_EARLIEST_COMP_DATE,                                                                   "
            +"          i.ENTRY_TYPE, i.IMAGESTATUS                                                                            "
            +"        FROM Items i                                                                              "
            +"        WHERE i.Entry_Type IN ('Style', 'StyleColor', 'Complex Pack', 'PackColor')                "
            +"        )                                                                                         "
            +"      ) ,                                                                                         "
            +"      TypeIndex typInd                                                                            "
            +"    WHERE ENTRY_TYPE = typInd.typ                                                                 "
            +"    ORDER BY NVL(PARENT_MDMID, MDMID),                                                            "
            +"      INDX                                                                                        "
            +"    )                                                                                             "
            +"  SELECT F.STYLE_ID,                                                                              "
            +"    f.ORIN,                                                                                       "
            +"    f.COLOR_CODE,                                                                                 "
            +"    f.COLOR,                                                                                      "
            +"    F.VENDOR_SIZE,                                                                                "
            +"    MAX (F.OMNI_SIZE_DESC),                                                                       "
            +"    f.CONTENTSTATUS,                                                                              "
            +"    f.PETSTATUS,                                                                                  "
            +"    F.COMPLETION_DATE,                                                                            "
            +"      f.PET_EARLIEST_COMP_DATE,                                                                   "
            +"    F.ENTRY_TYPE, F.IMAGESTATUS                                                                                  "
            +"  FROM Final F                                                                                    "
            +"  WHERE CONTENTSTATUS IN ('01','02','08')                                                         "
            +"  OR (entry_type       = 'SKU'                                                                    "
            +"  AND CONTENTSTATUS   IN ('01','02','08', '06'))                                                  "
            +"  GROUP BY F.STYLE_ID,                                                                            "
            +"    f.ORIN,                                                                                       "
            +"    f.COLOR_CODE,                                                                                 "
            +"    f.COLOR,                                                                                      "
            +"    F.VENDOR_SIZE,                                                                                "
            +"    f.CONTENTSTATUS,                                                                              "
            +"    f.PETSTATUS,                                                                                  "
            +"    F.COMPLETION_DATE,                                                                            "
            +"    f.PET_EARLIEST_COMP_DATE,                                                                     "
            +"    F.ENTRY_TYPE, F.IMAGESTATUS                                                                                 ";
        /*
        String GET_STYLE_STYLECOLOR_SKU_INFORMATION_XQUERY = " WITH INPUT( ORIN) AS " 
                +"   (SELECT '"+orinNumber+"' ORIN " 
                +"   FROM dual " 
                +"   ) , " 
                +"   TypeIndex(indx, typ) AS (  " 
                +"   ( SELECT 1 indx, 'Style' typ FROM dual " 
                +"   ) " 
                +" UNION " 
                +"   ( SELECT 2 indx, 'StyleColor' typ FROM dual " 
                +"   ) " 
                +" UNION " 
                +"   ( SELECT 3 indx, 'SKU' typ FROM dual " 
                +"   ) " 
                +" UNION " 
                +"   ( SELECT 4 indx, 'Complex Pack' typ FROM dual " 
                +"   ) " 
                +" UNION " 
                +"   ( SELECT 5 indx, 'PackColor' typ FROM dual " 
                +"   ) ), Items AS " 
                +"   (SELECT NVL(aic.PARENT_MDMID, aic.MDMID) PARENT_MDMID, " 
                +"     aic.MDMID, " 
                +"     aic.ENTRY_TYPE, " 
                +"     i.supplier_id PRIMARY_SUPPLIER_ID, " 
                +"     deptid dept_id, " 
                +"     classid Class_id, " 
                +"     VENDOR_COLOR_CODE, " 
                +"     VENDOR_COLOR_DESC, " 
                +"     VENDOR_SIZE_CODE, " 
                +"     VENDOR_SIZE_DESC, " 
                +"     P.CONTENTSTATE, " 
                +"     p.contentcontainer, " 
                +"     P.PETSTATE, " 
                +"     p.petcontainer, " 
                +"     p.completion_date " 
                +"   FROM VENDORPORTAL.ADSE_ITEM_CATALOG aic, " 
                +"     XMLTABLE( " 
                +"     'for $i in $XML_DATA/pim_entry/entry    " 
                +"     let " 
                +"     $uda80 := (fn:count($i/Item_UDA_Spec/UDA/Id) gt 0 and $i/Item_UDA_Spec/UDA/Id eq \"80\"),   " 
                +"     $non_sellable := (fn:count($i/Item_Simple_Pack_Spec/Sellable_Flag/text()) gt 0 and  ($i/Item_Simple_Pack_Spec/Sellable_Flag eq \"false\")),  " 
                +"     $non_sellable_pack := (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0 and  ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")),     " 
                +"     $removal := $i/Item_Ctg_Spec/System/Removal_Flag eq \"true\"   " 
                +"     return  " 
                +"     <out>   " 
                +"     <dept_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[5]}</dept_id>  " 
                +"     <class_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[6]}</class_id>   " 
                +"     <supplier_id>{$i/Item_Ctg_Spec/Supplier[Primary_Flag eq \"true\"]/Id}</supplier_id>    " 
                +"     <flag>{$uda80 and $non_sellable_pack and $removal}</flag>   " 
                +"     <colorCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code}</colorCode>    " 
                +"     <colorDesc>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description}</colorDesc>   " 
                +"     <sizeCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Code}</sizeCode>  " 
                +"     <sizeDesc>{$i/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description}</sizeDesc>   " 
                +"     </out>' " 
                +"     passing aic.XML_DATA AS \"XML_DATA\" columns  " 
                +"     flag VARCHAR(10) path '/out/flag',  " 
                +"     supplier_id VARCHAR2(20) path '/out/supplier_id',  " 
                +"     deptid VARCHAR2(20) path '/out/dept_id',  " 
                +"     classid VARCHAR2(20) path '/out/class_id', " 
                +"     descr VARCHAR2(64) path '/out/desc',  " 
                +"     VENDOR_COLOR_CODE VARCHAR2(10) path '/out/colorCode',  " 
                +"     VENDOR_COLOR_DESC VARCHAR2(40) path '/out/colorDesc',  " 
                +"     VENDOR_SIZE_CODE VARCHAR2(10) path '/out/sizeCode', " 
                +"     VENDOR_SIZE_DESC VARCHAR2(10) path '/out/sizeDesc') i, " 
                +"     VENDORPORTAL.ADSE_PET_CATALOG pet, " 
                +"     XMLTABLE( 'for $pet in $pets/pim_entry/entry     " 
                +"     let " 
                +"     $completionDate := $pet//Completion_Date,      " 
                +"     $contentState := $pet//ContentState , " 
                +"     $petState := $pet//State    " 
                +"     return  " 
                +"     <out>    " 
                +"     <id>{$pet/Pet_Ctg_Spec/Id}</id>      " 
                +"     <completion_date>{$completionDate}</completion_date>   " 
                +"     <contentState>{$contentState}</contentState>       " 
                +"     <contentContainer>{fn:data($contentState/@container)}</contentContainer>       " 
                +"     <petState>{$petState}</petState> " 
                +"     <petContainer>{fn:data($petState/@container)}</petContainer>      " 
                +"     </out>'  " 
                +"     passing pet.XML_DATA AS \"pets\" columns " 
                +"     completion_date VARCHAR2(10) path '/out/completion_date', " 
                +"     contentState VARCHAR2(20) path '/out/contentState', CONTENTCONTAINER VARCHAR2(40) PATH '/out/contentContainer', petState VARCHAR2(20) path '/out/petState',petContainer VARCHAR2(40) PATH '/out/petContainer') p, " 
                +"     INPUT INP " 
                +"   WHERE flag          = 'false' " 
                +"   AND AIC.MDMID       =PET.MDMID " 
                +"   AND pet.MOD_DTM     = '01-JAN-00 12.00.00.000000000 PM' " 
                +"   AND aic.MOD_DTM     = '01-JAN-00 12.00.00.000000000 PM' " 
                +"   AND ( aic.MDMID     =inp.ORIN " 
                +"   OR aic.parent_mdmid =inp.ORIN ) " 
                +"   AND aic.Entry_type IN ('SKU', 'Style', 'StyleColor', 'Complex Pack', 'PackColor') " 
                +"   ) , " 
                +"   ItemSupplier AS " 
                +"   (SELECT i.PARENT_MDMID, " 
                +"     i.MDMID, " 
                +"     i.PRIMARY_SUPPLIER_ID, " 
                +"     i.dept_id, " 
                +"     i.Class_Id, " 
                +"     i.VENDOR_COLOR_CODE, " 
                +"     i.VENDOR_COLOR_DESC, " 
                +"     i.VENDOR_SIZE_CODE, " 
                +"     i.VENDOR_SIZE_DESC, " 
                +"     i.ENTRY_TYPE, " 
                +"     s.VenId, " 
                +"     I.CONTENTSTATE, " 
                +"     i.contentcontainer, " 
                +"     I.PETSTATE, " 
                +"     i.petcontainer, " 
                +"     i.completion_date " 
                +"   FROM Items i, " 
                +"     VENDORPORTAL.ADSE_SUPPLIER_CATALOG sup, " 
                +"     XMLTABLE('for $i in $XML_DATA/pim_entry/entry    return $i' passing sup.xml_data AS \"XML_DATA\" COLUMNS Id VARCHAR2(20) path 'Supplier_Ctg_Spec/Id', VenId VARCHAR2(20) path 'Supplier_Ctg_Spec/VEN_Id' ) s " 
                +"   WHERE sup.MOD_DTM         = '01-JAN-00 12.00.00.000000000 PM' " 
                +"   AND i.PRIMARY_SUPPLIER_ID = sup.MDMID " 
                +"   ) , " 
                +"   Final AS " 
                +"   (SELECT PARENT_MDMID Style_ID, " 
                +"     MDMID ORIN, " 
                +"     VENDOR_COLOR_CODE COLOR_CODE, " 
                +"     VENDOR_COLOR_CODE " 
                +"     || ' ' " 
                +"     || VENDOR_COLOR_DESC COLOR, " 
                +"     VENDOR_SIZE_CODE " 
                +"     || ' ' " 
                +"     || VENDOR_SIZE_DESC Vendor_Size, " 
                +"     OMNI_SIZE_DESC, " 
                +"     CONTENTSTATE, " 
                +"     contentcontainer, " 
                +"     PETSTATE, " 
                +"     petcontainer, " 
                +"     completion_date, " 
                +"     ENTRY_TYPE " 
                +"   FROM " 
                +"     (SELECT PARENT_MDMID , " 
                +"       MDMID , " 
                +"       PRIMARY_SUPPLIER_ID, " 
                +"       dept_id, " 
                +"       VENDOR_COLOR_CODE, " 
                +"       VENDOR_COLOR_DESC , " 
                +"       VENDOR_SIZE_CODE, " 
                +"       VENDOR_SIZE_DESC , " 
                +"       OMNI_SIZE_DESC, " 
                +"       CONTENTSTATE, " 
                +"       CONTENTCONTAINER, " 
                +"       PETSTATE, " 
                +"       petcontainer, " 
                +"       completion_date, " 
                +"       ENTRY_TYPE " 
                +"     FROM " 
                +"       (SELECT i.PARENT_MDMID, " 
                +"         i.MDMID, " 
                +"         i.PRIMARY_SUPPLIER_ID, " 
                +"         i.dept_id, " 
                +"         i.VENDOR_COLOR_CODE, " 
                +"         i.VENDOR_COLOR_DESC, " 
                +"         i.VENDOR_SIZE_CODE, " 
                +"         i.VENDOR_SIZE_DESC, " 
                +"         o.OMNI_SIZE_DESC, " 
                +"         I.CONTENTSTATE, " 
                +"         i.contentcontainer, " 
                +"         I.PETSTATE, " 
                +"         i.petcontainer, " 
                +"         i.completion_date, " 
                +"         i.ENTRY_TYPE " 
                +"       FROM ItemSupplier i " 
                +"       LEFT OUTER JOIN VENDORPORTAL.ADSE_VENDOR_OMNISIZE_DESC omni " 
                +"       ON i.VENDOR_SIZE_CODE =omni.NRF_SIZE_CODE " 
                +"       AND i.dept_id         =omni.DEPT_ID " 
                +"       AND i.Class_Id        =omni.CLASS_ID " 
                +"       LEFT OUTER JOIN xmltable('for $i in $XML//omni_size_desc return $i' " 
                +"       passing omni.XML_DATA AS \"XML\" columns OMNI_SIZE_DESC VARCHAR(40) path '.' ) o " 
                +"       ON 1 = 1 " 
                +"       WHERE i.Entry_Type = 'SKU' " 
                +"       ) " 
                +"     UNION " 
                +"       (SELECT i.PARENT_MDMID, " 
                +"         i.MDMID, " 
                +"         i.PRIMARY_SUPPLIER_ID, " 
                +"         i.dept_id, " 
                +"         i.VENDOR_COLOR_CODE, " 
                +"         i.VENDOR_COLOR_DESC, " 
                +"         i.VENDOR_SIZE_CODE, " 
                +"         i.VENDOR_SIZE_DESC, " 
                +"         NULL OMNI_SIZE_DESC, " 
                +"         I.CONTENTSTATE, " 
                +"         i.contentcontainer, " 
                +"         I.PETSTATE, " 
                +"         i.petcontainer, " 
                +"         i.completion_date, " 
                +"         i.ENTRY_TYPE " 
                +"       FROM ItemSupplier i " 
                +"       WHERE i.Entry_Type IN ('Style', 'StyleColor', 'Complex Pack', 'PackColor') " 
                +"       ) " 
                +"     ) , " 
                +"     TypeIndex typInd " 
                +"   WHERE ENTRY_TYPE = typInd.typ " 
                +"   ORDER BY NVL(PARENT_MDMID, MDMID), " 
                +"     INDX " 
                +"   ) , " 
                +"   finaltable AS " 
                +"   (SELECT F.STYLE_ID, " 
                +"     f.ORIN, " 
                +"     f.COLOR_CODE, " 
                +"     f.COLOR, " 
                +"     F.VENDOR_SIZE, " 
                +"     F.OMNI_SIZE_DESC, " 
                +"     CONT.THEVALUE CONTENTSTATUS, " 
                +"     PET.THEVALUE PETSTATUS, " 
                +"     F.COMPLETION_DATE, " 
                +"     F.ENTRY_TYPE " 
                +"   FROM FINAL F " 
                +"   LEFT OUTER JOIN VENDORPORTAL.adse_reference_data cont " 
                +"   ON CONT.MDMID       =F.CONTENTSTATE " 
                +"   AND cont.entry_type =f.contentContainer " 
                +"   LEFT OUTER JOIN VENDORPORTAL.ADSE_REFERENCE_DATA PET " 
                +"   ON PET.MDMID        =F.PETSTATE " 
                +"   AND PET.ENTRY_TYPE  =F.PETCONTAINER " 
                +"   AND f.petstate NOT IN(05) " 
                +"   ) " 
                +" SELECT F.STYLE_ID, " 
                +"     f.ORIN, " 
                +"     f.COLOR_CODE, " 
                +"     f.COLOR, " 
                +"     F.VENDOR_SIZE, " 
                +"     max (F.OMNI_SIZE_DESC), " 
                +"     f.CONTENTSTATUS, " 
                +"     f.PETSTATUS, " 
                +"     F.COMPLETION_DATE, " 
                +"     F.ENTRY_TYPE " 
                +" FROM finaltable F " 
                +" WHERE CONTENTSTATUS IN ('Initiated','Completed','Ready_For_Review') " 
                +" OR (entry_type       = 'SKU' " 
                +" AND CONTENTSTATUS   IN ('Initiated','Completed','Ready_For_Review', 'Closed')) " 
                +" group by F.STYLE_ID, f.ORIN, f.COLOR_CODE, f.COLOR, F.VENDOR_SIZE, f.CONTENTSTATUS, f.PETSTATUS, F.COMPLETION_DATE, F.ENTRY_TYPE " ;*/
        /**
         * MODIFICATION END AFUAXK4
         * DATE: 02/05/2016
         */

       /* String GET_STYLE_STYLECOLOR_SKU_INFORMATION_XQUERY =   " WITH INPUT( ORIN) AS " 
                +"   (SELECT '"+orinNumber+"' ORIN " 
                +"   FROM dual " 
                +"   ) , " 
                +"   TypeIndex(indx, typ) AS ( " 
                +"   ( SELECT 1 indx, 'Style' typ FROM dual " 
                +"   ) " 
                +" UNION " 
                +"   ( SELECT 2 indx, 'StyleColor' typ FROM dual " 
                +"   ) " 
                +" UNION " 
                +"   ( SELECT 3 indx, 'SKU' typ FROM dual " 
                +"   ) " 
                +" UNION " 
                +"   ( SELECT 4 indx, 'Complex Pack' typ FROM dual " 
                +"   ) " 
                +" UNION " 
                +"   ( SELECT 5 indx, 'PackColor' typ FROM dual " 
                +"   ) ), Items AS " 
                +"   (SELECT NVL(aic.PARENT_MDMID, aic.MDMID) PARENT_MDMID, " 
                +"     aic.MDMID, " 
                +"     aic.ENTRY_TYPE, " 
                +"     i.supplier_id PRIMARY_SUPPLIER_ID, " 
                +"     deptid dept_id, " 
                +"     classid Class_id, " 
                +"     VENDOR_COLOR_CODE, " 
                +"     VENDOR_COLOR_DESC, " 
                +"     VENDOR_SIZE_CODE, " 
                +"     VENDOR_SIZE_DESC, " 
                +"     P.CONTENTSTATE, " 
                +"     p.contentcontainer, " 
                +"     P.PETSTATE, " 
                +"     p.petcontainer, " 
                +"     p.completion_date " 
                +"   FROM VENDORPORTAL.ADSE_ITEM_CATALOG aic, " 
                +"     XMLTABLE( " 
                +"     'for $i in $XML_DATA/pim_entry/entry    let $uda80 := (fn:count($i/Item_UDA_Spec/UDA/Id) gt 0 and $i/Item_UDA_Spec/UDA/Id eq \"80\"),   " 
                +"     $non_sellable := (fn:count($i/Item_Simple_Pack_Spec/Sellable_Flag/text()) gt 0 and  ($i/Item_Simple_Pack_Spec/Sellable_Flag eq \"false\")), " 
                +"     $non_sellable_pack := (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0 and  ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")),      $removal := $i/Item_Ctg_Spec/System/Removal_Flag eq \"true\"    return  <out>   <dept_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[5]}</dept_id>   <class_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[6]}</class_id>    <supplier_id>{$i/Item_Ctg_Spec/Supplier[Primary_Flag eq \"true\"]/Id}</supplier_id>     <flag>{$uda80 and $non_sellable_pack and $removal}</flag>   <colorCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Code}</colorCode>    <colorDesc>{$i/Item_SKU_Spec/Differentiators[Type eq \"COLOR\"]/Vendor_Description}</colorDesc>   <sizeCode>{$i/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Code}</sizeCode>   <sizeDesc>{$i/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description}</sizeDesc>    </out>' " 
                +"     passing aic.XML_DATA AS \"XML_DATA\" columns flag VARCHAR(10) path '/out/flag', supplier_id VARCHAR2(20) path '/out/supplier_id', deptid VARCHAR2(20) path '/out/dept_id', classid VARCHAR2(20) path '/out/class_id', descr VARCHAR2(64) path '/out/desc', VENDOR_COLOR_CODE VARCHAR2(10) path '/out/colorCode', VENDOR_COLOR_DESC VARCHAR2(40) path '/out/colorDesc', VENDOR_SIZE_CODE VARCHAR2(10) path '/out/sizeCode', " 
                +"     VENDOR_SIZE_DESC VARCHAR2(10) path '/out/sizeDesc') i, " 
                +"     VENDORPORTAL.ADSE_PET_CATALOG pet, " 
                +"     XMLTABLE( 'for $pet in $pets/pim_entry/entry    let $completionDate := $pet//Completion_Date,  " 
                +"     $contentState := $pet//ContentState ,$petState := $pet//State   return <out>    <id>{$pet/Pet_Ctg_Spec/Id}</id> " 
                +"     <completion_date>{$completionDate}</completion_date>   <contentState>{$contentState}</contentState>  " 
                +"     <contentContainer>{fn:data($contentState/@container)}</contentContainer>  " 
                +"     <petState>{$petState}</petState><petContainer>{fn:data($petState/@container)}</petContainer>   " 
                +"     </out>' passing pet.XML_DATA AS \"pets\" columns completion_date VARCHAR2(10) path '/out/completion_date', " 
                +"     contentState VARCHAR2(20) path '/out/contentState', CONTENTCONTAINER VARCHAR2(40) PATH '/out/contentContainer', " 
                +"     petState VARCHAR2(20) path '/out/petState',petContainer VARCHAR2(40) PATH '/out/petContainer') p, " 
                +"     INPUT INP " 
                +"   WHERE flag          = 'false' " 
                +"   AND AIC.MDMID       =PET.MDMID " 
                +"   AND pet.MOD_DTM     = '01-JAN-00 12.00.00.000000000 PM' " 
                +"   AND aic.MOD_DTM     = '01-JAN-00 12.00.00.000000000 PM' " 
                +"   AND ( aic.MDMID     =inp.ORIN " 
                +"   OR aic.parent_mdmid =inp.ORIN ) " 
                +"   AND aic.Entry_type IN ('SKU', 'Style', 'StyleColor', 'Complex Pack', 'PackColor') " 
                +"   ) , " 
                +"   ItemSupplier AS " 
                +"   (SELECT i.PARENT_MDMID, " 
                +"     i.MDMID, " 
                +"     i.PRIMARY_SUPPLIER_ID, " 
                +"     i.dept_id, " 
                +"     i.Class_Id, " 
                +"     i.VENDOR_COLOR_CODE, " 
                +"     i.VENDOR_COLOR_DESC, " 
                +"     i.VENDOR_SIZE_CODE, " 
                +"     i.VENDOR_SIZE_DESC, " 
                +"     i.ENTRY_TYPE, " 
                +"     s.VenId, " 
                +"     I.CONTENTSTATE, " 
                +"     i.contentcontainer, " 
                +"     I.PETSTATE, " 
                +"     i.petcontainer, " 
                +"     i.completion_date " 
                +"   FROM Items i, " 
                +"     VENDORPORTAL.ADSE_SUPPLIER_CATALOG sup, " 
                +"     XMLTABLE('for $i in $XML_DATA/pim_entry/entry    return $i' passing sup.xml_data AS \"XML_DATA\" COLUMNS Id VARCHAR2(20) path 'Supplier_Ctg_Spec/Id', VenId VARCHAR2(20) path 'Supplier_Ctg_Spec/VEN_Id' ) s " 
                +"   WHERE sup.MOD_DTM         = '01-JAN-00 12.00.00.000000000 PM' " 
                +"   AND i.PRIMARY_SUPPLIER_ID = sup.MDMID " 
                +"   ) , " 
                +"   Final AS " 
                +"   (SELECT PARENT_MDMID Style_ID, " 
                +"     MDMID ORIN, " 
                +"     VENDOR_COLOR_CODE COLOR_CODE, " 
                +"     VENDOR_COLOR_CODE " 
                +"     || ' ' " 
                +"     || VENDOR_COLOR_DESC COLOR, " 
                +"     VENDOR_SIZE_CODE " 
                +"     || ' ' " 
                +"     || VENDOR_SIZE_DESC Vendor_Size, " 
                +"     OMNI_SIZE_DESC, " 
                +"     CONTENTSTATE, " 
                +"     contentcontainer, " 
                +"     PETSTATE, " 
                +"     petcontainer, " 
                +"     completion_date, " 
                +"     ENTRY_TYPE " 
                +"   FROM " 
                +"     (SELECT PARENT_MDMID , " 
                +"       MDMID , " 
                +"       PRIMARY_SUPPLIER_ID, " 
                +"       dept_id, " 
                +"       VENDOR_COLOR_CODE, " 
                +"       VENDOR_COLOR_DESC , " 
                +"       VENDOR_SIZE_CODE, " 
                +"       VENDOR_SIZE_DESC , " 
                +"       OMNI_SIZE_DESC, " 
                +"       CONTENTSTATE, " 
                +"       CONTENTCONTAINER, " 
                +"       PETSTATE, " 
                +"       petcontainer, " 
                +"       completion_date, " 
                +"       ENTRY_TYPE " 
                +"     FROM " 
                +"       (SELECT i.PARENT_MDMID, " 
                +"         i.MDMID, " 
                +"         i.PRIMARY_SUPPLIER_ID, " 
                +"         i.dept_id, " 
                +"         i.VENDOR_COLOR_CODE, " 
                +"         i.VENDOR_COLOR_DESC, " 
                +"         i.VENDOR_SIZE_CODE, " 
                +"         i.VENDOR_SIZE_DESC, " 
                +"         o.OMNI_SIZE_DESC, " 
                +"         I.CONTENTSTATE, " 
                +"         i.contentcontainer, " 
                +"         I.PETSTATE, " 
                +"         i.petcontainer, " 
                +"         i.completion_date, " 
                +"         i.ENTRY_TYPE " 
                +"       FROM ItemSupplier i LEFT OUTER JOIN " 
                +"         VENDORPORTAL.ADSE_VENDOR_OMNISIZE_DESC omni " 
                +"         ON i.VENDOR_SIZE_CODE =omni.NRF_SIZE_CODE " 
                +"         AND i.dept_id          =omni.DEPT_ID " 
                +"         AND i.Class_Id         =omni.CLASS_ID " 
          //      +"         AND i.VenId            =omni.VENDOR_ID " 
                +"         LEFT OUTER JOIN " 
                +"         xmltable('for $i in $XML//omni_size_desc return $i' passing omni.XML_DATA AS \"XML\" columns OMNI_SIZE_DESC VARCHAR(40) path '.' ) o " 
                +"         ON 1 = 1 " 
                +"       WHERE i.Entry_Type     = 'SKU' " 
                +"       ) " 
                +"     UNION " 
                +"       (SELECT i.PARENT_MDMID, " 
                +"         i.MDMID, " 
                +"         i.PRIMARY_SUPPLIER_ID, " 
                +"         i.dept_id, " 
                +"         i.VENDOR_COLOR_CODE, " 
                +"         i.VENDOR_COLOR_DESC, " 
                +"         i.VENDOR_SIZE_CODE, " 
                +"         i.VENDOR_SIZE_DESC, " 
                +"         NULL OMNI_SIZE_DESC, " 
                +"         I.CONTENTSTATE, " 
                +"         i.contentcontainer, " 
                +"         I.PETSTATE, " 
                +"         i.petcontainer, " 
                +"         i.completion_date, " 
                +"         i.ENTRY_TYPE " 
                +"       FROM ItemSupplier i " 
                +"       WHERE i.Entry_Type IN ('Style', 'StyleColor', 'Complex Pack', 'PackColor') " 
                +"       ) " 
                +"     ) , " 
                +"     TypeIndex typInd " 
                +"   WHERE ENTRY_TYPE = typInd.typ " 
                +"   ORDER BY NVL(PARENT_MDMID, MDMID), " 
                +"     INDX " 
                +"   ) , " 
                +"   finaltable AS " 
                +"   (SELECT F.STYLE_ID, " 
                +"     f.ORIN, " 
                +"     f.COLOR_CODE, " 
                +"     f.COLOR, " 
                +"     F.VENDOR_SIZE, " 
                +"     F.OMNI_SIZE_DESC, " 
                +"     CONT.THEVALUE CONTENTSTATUS, " 
                +"     PET.THEVALUE PETSTATUS, " 
                +"     F.COMPLETION_DATE, " 
                +"     F.ENTRY_TYPE " 
                +"   FROM FINAL F " 
                +"   LEFT OUTER JOIN VENDORPORTAL.adse_reference_data cont " 
                +"   ON CONT.MDMID       =F.CONTENTSTATE " 
                +"   AND cont.entry_type =f.contentContainer " 
                +"   LEFT OUTER JOIN VENDORPORTAL.ADSE_REFERENCE_DATA PET " 
                +"   ON PET.MDMID        =F.PETSTATE " 
                +"   AND PET.ENTRY_TYPE  =F.PETCONTAINER " 
                +"   AND f.petstate NOT IN(05) " 
                +"   ) " 
                +" SELECT * " 
                +" FROM finaltable " 
                +" WHERE CONTENTSTATUS IN ('Initiated','Completed','Ready_For_Review') " 
                +" OR (entry_type       = 'SKU' " 
                +" AND CONTENTSTATUS   IN ('Initiated','Completed','Ready_For_Review', 'Closed')) " ;*/

      

       // LOGGER.info("GET_STYLE_STYLECOLOR_SKU_INFORMATION_XQUERY---------"+GET_STYLE_STYLECOLOR_SKU_INFORMATION_XQUERY);
        return GET_STYLE_STYLECOLOR_SKU_INFORMATION_XQUERY;
    }


    /**
     * Gets the style color attributes.
     *
     * @param orinNmber the orin nmber
     * @return the style color attributes
     */
    public  String getStyleColorAttributes(String orinNmber ) {




        String GET_STYLE_COLOR_ATTRIBUTES_XQUERY = " WITH "
                +" INPUT(ORIN) AS "
                +"   ( "
                +"     SELECT  :orinNo ORIN "
                +"     FROM "
                +"       dual "
                +"   ), "
                +"   pettable AS "
                +"   ( "
                +"   (select NRF_Color_Code, "
                +"   NRF_Color_Description, "
                +"   Secondary_Color_1, "
                +"   Secondary_Color_2, "
                +"   Secondary_Color_3, "
                +"   Secondary_Color_4, "
                +"   Omni_Channel_Color, "
                +"   Color_Family_Code   "
                +"   FROM "
                +"   vendorportal.ADSE_PET_CATALOG, "
                +"   xmltable('for $i in $pet/pim_entry/entry/Ecomm_StyleColor_Spec return $i' "
                +"   passing XML_DATA AS \"pet\" columns  "
                +"   NRF_Color_Code          VARCHAR2(40) path  'NRF_Color_Code',  "
                +"   NRF_Color_Description   VARCHAR2(40) path  'NRF_Color_Description',  "
                +"   Secondary_Color_1       VARCHAR2(40) path  'Secondary_Color_1',  "
                +"   Secondary_Color_2       VARCHAR2(40) path  'Secondary_Color_2',  "
                +"   Secondary_Color_3       VARCHAR2(40) path  'Secondary_Color_3',  "
                +"   Secondary_Color_4       VARCHAR2(40) path  'Secondary_Color_4',  "
                +"   Omni_Channel_Color      VARCHAR2(40) path  'Omni_Channel_Color_Description',  "
                +"   Color_Family_Code       VARCHAR2(40) path  'Omnichannel_Color_Family' "
                +"    ) i, "
                +"   Input inp "
                +" WHERE "
                +"   Entry_Type = 'StyleColor' "
                +" AND mdmid    =inp.ORIN) "
                +" UNION ALL "
                +"   (select NRF_Color_Code, "
                +"   NRF_Color_Description, "
                +"   Secondary_Color_1, "
                +"   Secondary_Color_2, "
                +"   Secondary_Color_3, "
                +"   Secondary_Color_4, "
                +"   Omni_Channel_Color, "
                +"   Color_Family_Code   "
                +"   FROM "
                +"   vendorportal.ADSE_PET_CATALOG, "
                +"   xmltable('for $i in $pet/pim_entry/entry/Ecomm_PackColor_Spec return $i' "
                +"   passing XML_DATA AS \"pet\" columns  "
                +"   NRF_Color_Code          VARCHAR2(40) path  'NRF_Color_Code',  "
                +"   NRF_Color_Description   VARCHAR2(40) path  'NRF_Color_Description',  "
                +"   Secondary_Color_1       VARCHAR2(40) path  'Secondary_Color_1',  "
                +"   Secondary_Color_2       VARCHAR2(40) path  'Secondary_Color_2',  "
                +"   Secondary_Color_3       VARCHAR2(40) path  'Secondary_Color_3',  "
                +"   Secondary_Color_4       VARCHAR2(40) path  'Secondary_Color_4',  "
                +"   Omni_Channel_Color      VARCHAR2(40) path  'Omni_Channel_Color_Description',  "
                +"   Color_Family_Code       VARCHAR2(40) path  'Omnichannel_Color_Family' "
                +"    ) i, "
                +"   Input inp "
                +" WHERE "
                +"   Entry_Type = 'PackColor' "
                +" AND mdmid    =inp.ORIN) "
                +" ) "
                +" select pt.*, s.code,s.super_Color_name from pettable pt, VENDORPORTAL.ADSE_REFERENCE_DATA ard, "
                +" xmltable('for $i in $ref/pim_entry/entry/Omni_Channel_Color_Family_Spec return $i' "
                +"   passing ard.XML_DATA AS \"ref\" columns "
                +"   Code   VARCHAR2(40) path  'Code', "
                +"   SUPER_COLOR_NAME  VARCHAR2(40) path  'SUPER_COLOR_NAME') s "
                +"   where  "
                +"   ard.Container = 'Omnichannel_Color_Family' "
                +"   and  "
                +"   pt.Color_Family_Code = s.code " ;

      //  System.out.println("GET_STYLE_COLOR_ATTRIBUTES_XQUERY-----"+GET_STYLE_COLOR_ATTRIBUTES_XQUERY);
        return GET_STYLE_COLOR_ATTRIBUTES_XQUERY;

    }


    /**
     * Gets the style information.
     *
     * @param orinNumber the orin number
     * @return the style information
     */
    public  String getStyleInformation(String orinNumber) {

            String GET_STYLE_INFORMATION_XQUERY = " WITH INPUT( ORIN) AS " 
                    +"   (SELECT :orinNo ORIN   " 
                    +"   FROM dual " 
                    +"   ) , " 
                    +"   TypeIndex(indx, typ) AS ( " 
                    +"   ( SELECT 1 indx, 'Style' typ FROM dual " 
                    +"   ) " 
                    +" UNION " 
                    +"   ( SELECT 2 indx, 'StyleColor' typ FROM dual " 
                    +"   ) " 
                    +" UNION " 
                    +"   ( SELECT 3 indx, 'SKU' typ FROM dual " 
                    +"   ) " 
                    +" UNION " 
                    +"   ( SELECT 4 indx, 'Complex Pack' typ FROM dual " 
                    +"   ) " 
                    +" UNION " 
                    +"   ( SELECT 5 indx, 'PackColor' typ FROM dual " 
                    +"   ) ) , Items AS " 
                    +"   (SELECT NVL(aic.PARENT_MDMID, aic.MDMID) PARENT_MDMID, " 
                    +"     aic.MDMID, " 
                    +"     aic.ENTRY_TYPE, " 
                    +"     descr DESCRIPTION, " 
                    +"     i.supplier_id PRIMARY_SUPPLIER_ID, " 
                    +"     deptid dept_id, " 
                    +"     classid Class_Id, " 
                    +"     completion_date, " 
                    +"     aic.XML_DATA " 
                    +"   FROM VENDORPORTAL.ADSE_ITEM_CATALOG aic, " 
                    +"     XMLTABLE( " 
                    +"     'for $i in $XML_DATA/pim_entry/entry            " 
                    +"     let            " 
                    +"     $uda80 := (fn:count($i/Item_UDA_Spec/UDA/Id) gt 0 and $i/Item_UDA_Spec/UDA/Id eq \"80\"),         " 
                    +"     $non_sellable_pack := (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0 and ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")), " 
                    +"     $removal := $i/Item_Ctg_Spec/System/Removal_Flag eq \"true\" ,            " 
                    +"     $ven_style := $i//Vendor_Style/text()         " 
                    +"     return             " 
                    +"     <out>                " 
                    +"       <dept_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path, \"\\||///\")[5]}</dept_id>  " 
                    +"       <class_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[6]}</class_id> " 
                    +"       <supplier_id>{$i/Item_Ctg_Spec/Supplier[Primary_Flag eq \"true\"]/Id}</supplier_id>   " 
                    +"       <desc>{$i/Item_Ctg_Spec/Description/Long}</desc>    " 
                    +"       <flag>{$uda80 and $non_sellable_pack and $removal}</flag>     " 
                    +"     </out>' " 
                    +"     passing aic.XML_DATA AS \"XML_DATA\"  " 
                    +"     columns  " 
                    +"     Flag VARCHAR(10) Path '/out/flag',  " 
                    +"     Supplier_Id VARCHAR2(20) Path '/out/supplier_id',  " 
                    +"     Deptid VARCHAR2(3) Path '/out/dept_id',  " 
                    +"     classid VARCHAR2(20) path '/out/class_id',  " 
                    +"     descr VARCHAR2(64) path '/out/desc') i, " 
                    +"     Input inp, " 
                    +"     VENDORPORTAL.ADSE_PET_CATALOG pet, " 
                    +"     XMLTABLE( 'for $i in $pet/pim_entry/entry         " 
                    +"     let $completionDate := $i//Completion_Date       " 
                    +"     return         " 
                    +"     <out>           " 
                    +"       <completion_date>{$completionDate}</completion_date>        " 
                    +"     </out>'  " 
                    +"     passing pet.xml_data AS \"pet\"  " 
                    +"     columns  " 
                    +"     completion_date VARCHAR2(10) path '/out/completion_date') s " 
                    +"   WHERE flag = 'false'    AND aic.PETEXISTS   = 'Y' " 
                    +"   AND pet.mdmid = aic.mdmid " 
                    +"   AND aic.MDMID = inp.ORIN " 
                    +"   AND aic.Entry_type IN ('SKU', 'Style', 'StyleColor','Complex Pack','PackColor') " 
                    +"   ) , " 
                    +"   VendorStyle AS " 
                    +"   (SELECT i.PARENT_MDMID, " 
                    +"     i.MDMID, " 
                    +"     i.DESCRIPTION, " 
                    +"     i.PRIMARY_SUPPLIER_ID, " 
                    +"     i.dept_id, " 
                    +"     i.ENTRY_TYPE, " 
                    +"     i.Class_Id, " 
                    +"     ia.Vendor_Sytle, " 
                    +"     NULL AS completion_date " 
                    +"   FROM Items i, " 
                    +"     XMLTABLE( 'for $i in $XML_DATA/pim_entry/entry/Item_Ctg_Spec/Supplier        let                 $ven_style := $i/VPN/text(),        $primary_flag := $i/Primary_Flag/text()                  return           <out>            <Vendor_Sytle>{$ven_style}</Vendor_Sytle>                  <primary_flag>{$primary_flag}</primary_flag>           </out>' passing i.XML_DATA AS \"XML_DATA\" columns Vendor_Sytle VARCHAR2(100) path '/out/Vendor_Sytle', primary_flag VARCHAR(25) path '/out/primary_flag') ia " 
                    +"   WHERE primary_flag = 'true' " 
                    +"   ), " 
                    +"   ItemSupplier AS " 
                    +"   (SELECT i.PARENT_MDMID, " 
                    +"     i.MDMID, " 
                    +"     i.DESCRIPTION, " 
                    +"     i.PRIMARY_SUPPLIER_ID, " 
                    +"     i.dept_id, " 
                    +"     s.SITE_NAME, " 
                    +"     i.ENTRY_TYPE, " 
                    +"     s.VenId, " 
                    +"     i.Class_Id, " 
                    +"     s.OmnichannelIndicator, " 
                    +"     s.VenName, " 
                    +"     sup.XML_DATA, " 
                    +"     i.Vendor_Sytle, " 
                    +"     completion_date, " 
                    +"     Sample_Image, " 
                    +"     s.id, "
                    +"     CASE WHEN s.OmnichannelIndicator = 'Y' AND (Sample_Image = 'Image' OR Sample_Image = 'Both') AND Image_Indicator = 'true' " 
                    +"     THEN 'Y' ELSE 'N' END Vendor_Image, " 
                    +"     CASE WHEN s.OmnichannelIndicator = 'Y' AND (Sample_Image = 'Sample' OR Sample_Image = 'Both') AND Sample_Indicator = 'true' " 
                    +"     THEN 'Y' ELSE 'N' END Vendor_Sample " 
                    +"   FROM VendorStyle i, " 
                    +"     VENDORPORTAL.ADSE_SUPPLIER_CATALOG sup, " 
                    +"     XMLTABLE('for $i in $XML_DATA/pim_entry/entry            " 
                    +"     return $i' passing sup.xml_data AS \"XML_DATA\" COLUMNS  " 
                    +"     Id VARCHAR2(20) path 'Supplier_Ctg_Spec/Id',  " 
                    +"     VenName VARCHAR2(20) path 'Supplier_Ctg_Spec/Name',  " 
                    +"     VenId VARCHAR2(20) path 'Supplier_Ctg_Spec/VEN_Id',  " 
                    +"     Site_Name VARCHAR2(50) Path 'Supplier_Ctg_Spec/Name', " 
                    +"     Sample_Image VARCHAR2(50) Path 'Supplier_Site_Spec/Omni_Channel/Vendor_Image_or_Sample_Indicator',  " 
                    +"     Image_Indicator VARCHAR2(50) Path 'Supplier_Site_Spec/Omni_Channel/Image_Certification',  " 
                    +"     Sample_Indicator VARCHAR2(50) Path 'Supplier_Site_Spec/Omni_Channel/Return_Sample_Indicator',  " 
                    +"     OmnichannelIndicator VARCHAR(2) path 'if (Supplier_Site_Spec/Omni_Channel/Omni_Channel_Indicator eq \"true\") then \"Y\" else \"N\"' ) s " 
                    +"   WHERE I.Primary_Supplier_Id = Sup.Mdmid " 
                    +"   ) " 
                    +" SELECT i.MDMID ORIN, " 
                    +"   I.Dept_Id, " 
                    +"   VenName Vendor_Name, " 
                    +"   i.Vendor_Sytle Style_Id, " 
                    +"   I.Class_Id, " 
                    +"   i.id Vendor_Id, " 
                    +"   i.DESCRIPTION, " 
                    +"   i.Omnichannelindicator Omnichannel_Vendor, " 
                    +"   i.Vendor_Image, " 
                    +"   i.Vendor_Sample, " 
                    +"   i.Entry_Type, " 
                    +"   i.Completion_Date, " 
                    +"   d.Name Dept_Desc, " 
                    +"   c.Name Class_Desc, " 
                    +"  i.id supplier_site_id "
                    +" FROM ItemSupplier i, " 
                    +"   Typeindex T , " 
                    +"   Vendorportal.Adse_Merchandise_Hierarchy AmC, " 
                    +"   Vendorportal.Adse_Merchandise_Hierarchy AmD, " 
                    +"   Xmltable('$i/pim_category/entry/Merchandise_Hier_Spec' Passing Amc.Xml_Data AS \"i\" Columns RMS_ID VARCHAR(10) path 'Identifiers/RMS_Id', Name VARCHAR(100) Path 'Name')C, " 
                    +"   Xmltable('$i/pim_category/entry/Merchandise_Hier_Spec' Passing AmD.Xml_Data AS \"i\" Columns RMS_ID VARCHAR(10) path 'Identifiers/RMS_Id', Name VARCHAR(100) Path 'Name')D " 
                    +" WHERE AmD.Entry_Type='4' " 
                    +" AND AmC.Entry_Type  ='5' " 
                    +" AND C.rms_id        = i.Class_Id " 
                    +" AND D.rms_id        = i.Dept_Id " 
                    +" AND I.Entry_Type    = T.Typ " ;

       // LOGGER.info("---------------------GET_STYLE_INFORMATION_XQUERY---------------------"+GET_STYLE_INFORMATION_XQUERY );

        return GET_STYLE_INFORMATION_XQUERY;
    }


    /**
     * Method to get the Copy attribute details query.
     *    
     * @return the query string
     * 
     * Method added For PIM Phase 2 - Regular Item Copy Attribute
     * Date: 05/16/2016
     * Added By: Cognizant
     */
    public String fetchCopyAttributesQuery() {
        
        LOGGER.info("***Entering fetchCopyAttributesQuery() method.");
        
        String COPY_ATTRIBUTE_QUERY = " SELECT AIC.MDMID,                                                                                                       "+
        "   T.PRODUCTCOPYTEXT,                                                                                                     "+
        "   T.COPYLINE1,                                                                                                           "+
        "   T.COPYLINE2,                                                                                                           "+
        "   T.COPYLINE3,                                                                                                           "+
        "   T.COPYLINE4,                                                                                                           "+
        "   T.COPYLINE5,                                                                                                           "+
        "   T.COPYPRODUCTNAME,                                                                                                     "+
        "   T.COPYMATERIAL,                                                                                                        "+
        "   T.COPYCARE,                                                                                                            "+
        "   T.COPYCOUNTRYOFORIGIN,                                                                                                 "+
        "   T.COPYEXCLUSIVE,                                                                                                       "+
        "   T.COPYCAPROP65COMPLIANT, T.COPYIMPORTDOMESTIC                                                                          "+
        " FROM ADSE_PET_CATALOG AIC,                                                                                               "+
        "   XMLTABLE(                                                                                                              "+
        "   'let $productCopyText := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Product_Copy_Text,                                    "+
        " $copyLine1 := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Line_1,                                                       "+
        " $copyLine2 := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Line_2,                                                       "+
        " $copyLine3 := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Line_3,                                                       "+
        " $copyLine4 := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Line_4,                                                       "+
        " $copyLine5 := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Line_5,                                                       "+
        " $copyProductName := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Product_Name,                                           "+
        " $copyMaterial := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Material,                                                  "+
        " $copyCare := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Care,                                                          "+
        " $copyCountryOfOrigin := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Country_Of_Origin,                                  "+
        " $copyImportDomestic := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Import_Domestic,                                     "+
        " $copyExclusive := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Exclusive,                                                "+
        " $copyCAProp65Compliant := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_CAProp65_Compliant                                "+
        " return                                                                                                                   "+
        " <category>                                                                                                               "+
        " <productCopyText>{$productCopyText}</productCopyText>                                                                    "+
        " <copyLine1>{$copyLine1}</copyLine1>                                                                                      "+
        " <copyLine2>{$copyLine2}</copyLine2>                                                                                      "+
        " <copyLine3>{$copyLine3}</copyLine3>                                                                                      "+
        " <copyLine4>{$copyLine4}</copyLine4>                                                                                      "+
        " <copyLine5>{$copyLine5}</copyLine5>                                                                                      "+
        " <copyProductName>{$copyProductName}</copyProductName>                                                                    "+
        " <copyMaterial>{$copyMaterial}</copyMaterial>                                                                             "+
        " <copyCare>{$copyCare}</copyCare>                                                                                         "+
        " <copyCountryOfOrigin>{$copyCountryOfOrigin}</copyCountryOfOrigin>                                                        "+
        " <copyImportDomestic>{$copyImportDomestic}</copyImportDomestic>                                                           "+
        " <copyExclusive>{$copyExclusive}</copyExclusive>                                                                          "+
        " <copyCAProp65Compliant>{$copyCAProp65Compliant}</copyCAProp65Compliant>                                                  "+
        " </category>'                                                                                                             "+
        "   passing AIC.XML_DATA AS \"XML_DATA\" COLUMNS PRODUCTCOPYTEXT CLOB path '/category/productCopyText', " +
        "  COPYLINE1 VARCHAR(3000) path '/category/copyLine1', COPYLINE2 VARCHAR(3000) path '/category/copyLine2', " +
        "  COPYLINE3 VARCHAR(3000) path '/category/copyLine3', COPYLINE4 VARCHAR(3000) path '/category/copyLine4', " +
        "  COPYLINE5 VARCHAR(3000) path '/category/copyLine5', COPYPRODUCTNAME VARCHAR(3000) path '/category/copyProductName', " +
        "  COPYMATERIAL VARCHAR(3000) path '/category/copyMaterial', COPYCARE VARCHAR(3000) path '/category/copyCare', " +
        "  COPYCOUNTRYOFORIGIN VARCHAR(3000) path '/category/copyCountryOfOrigin', " +
        "  COPYIMPORTDOMESTIC VARCHAR(3000) path '/category/copyImportDomestic', COPYEXCLUSIVE VARCHAR(3000) path '/category/copyExclusive', " +
        "  COPYCAPROP65COMPLIANT VARCHAR(3000) path '/category/copyCAProp65Compliant') T "+
        " WHERE AIC.MDMID = :orinNum   ";

        LOGGER.debug("COPY ATTRIBUTE QUERY -- \n" + COPY_ATTRIBUTE_QUERY);
        LOGGER.info("***Exiting fetchCopyAttributesQuery() method.");
        return COPY_ATTRIBUTE_QUERY;
    }
    
    public String getGroupingInfoAttribute(){
        System.out.println("XQueryConstants getGroupingInfoAttribute : Starts");
        
        StringBuffer queryBuffer = new StringBuffer();
        
        queryBuffer.append("    SELECT                                                                                                                                  ");
        queryBuffer.append("    AGC.MDMID GROUP_ID,                                                                                                                     ");
        queryBuffer.append("    AGC.DEF_DEPT_ID DEPT_ID,                                                                                                                ");
        queryBuffer.append("    AGC.DEF_PRIMARYSUPPLIERVPN VENDOR_STYLE,                                                                                                ");
        queryBuffer.append("    AGC.ENTRY_TYPE GROUP_TYPE,                                                                                                              ");
        queryBuffer.append("  AGC.DEF_PRIMARY_SUPPLIER_ID VenId,                                                                                                                                  ");
        queryBuffer.append("    s.VenName,                                                                                                                              ");
        queryBuffer.append("    s.OmnichannelIndicator,                                                                                                                 ");
        queryBuffer.append("    AIC.CLASS_ID,                                                                                                                           ");
        queryBuffer.append("  CASE                                                                                                                                      ");
        queryBuffer.append("        WHEN s.OmnichannelIndicator = 'Y'                                                                                                   ");
        queryBuffer.append("        AND (Sample_Image           = 'Image'                                                                                               ");
        queryBuffer.append("        OR Sample_Image             = 'Both')                                                                                               ");
        queryBuffer.append("        AND Image_Indicator         = 'true'                                                                                                ");
        queryBuffer.append("        THEN 'Y'                                                                                                                            ");
        queryBuffer.append("        ELSE 'N'                                                                                                                            ");
        queryBuffer.append("      END Vendor_Image,                                                                                                                     ");
        queryBuffer.append("      CASE                                                                                                                                  ");
        queryBuffer.append("        WHEN s.OmnichannelIndicator = 'Y'                                                                                                   ");
        queryBuffer.append("        AND (Sample_Image           = 'Sample'                                                                                              ");
        queryBuffer.append("        OR Sample_Image             = 'Both')                                                                                               ");
        queryBuffer.append("        AND Sample_Indicator        = 'true'                                                                                                ");
        queryBuffer.append("        THEN 'Y'                                                                                                                            ");
        queryBuffer.append("        ELSE 'N'                                                                                                                            ");
        queryBuffer.append("      END Vendor_Sample,                                                                                                                    ");
        queryBuffer.append("    AGC.COMPLETION_DATE,                                                                                                                     ");
        queryBuffer.append("    AGC.GROUP_CONTENT_STATUS_CODE,                                                                                                                     ");
        
        queryBuffer.append("    AGC.GROUP_IMAGE_STATUS_CODE,                                                                                                                     ");
        queryBuffer.append("    AGC.GROUP_OVERALL_STATUS_CODE                                                                                                                     ");
        
        queryBuffer.append("    FROM VENDORPORTAL.ADSE_GROUP_CATALOG AGC LEFT OUTER join                                                                                ");
        queryBuffer.append("    VENDORPORTAL.ADSE_SUPPLIER_CATALOG sup on SUP.MDMID=AGC.DEF_PRIMARY_SUPPLIER_ID                                                         ");
        queryBuffer.append("      LEFT OUTER JOIN                                                                                                                       ");
        queryBuffer.append("        VENDORPORTAL.ADSE_GROUP_CHILD_MAPPING AGCM  ON                                                                                      ");
        queryBuffer.append("        AGCM.MDMID=AGC.MDMID AND AGCM.COMPONENT_DEFAULT='true'                                                                              ");
        queryBuffer.append("      LEFT OUTER JOIN VENDORPORTAL.ADSE_ITEM_CATALOG AIC                                                                                    ");
        queryBuffer.append("        on AIC.MDMID = AGCM.COMPONENT_STYLE_ID                                                                                              ");
        queryBuffer.append("      LEFT OUTER JOIN VENDORPORTAL.ADSE_PET_CATALOG APC                                                                                     ");
        queryBuffer.append("        ON APC.MDMID=AIC.MDMID,                                                                                                             ");
        queryBuffer.append("      XMLTABLE('for $i in $pet/pim_entry/entry  return                                                                                      ");
        queryBuffer.append("      <out>                                                                                                                                 ");
        queryBuffer.append("        <img>{if(count($i/Image_Sec_Spec/Images//*) gt 0) then \"Y\" else \"N\"}</img>                                                          ");
        queryBuffer.append("        <sample>{if(count($i/Image_Sec_Spec/Sample//*) gt 0) then \"Y\" else \"N\"}</sample>                                                    ");
        queryBuffer.append("      </out>'                                                                                                                               ");
        queryBuffer.append("      passing APC.xml_data AS \"pet\"                                                                                                         ");
        queryBuffer.append("      columns                                                                                                                               ");
        queryBuffer.append("        Vendor_Image VARCHAR2(1) path '/out/img',                                                                                           ");
        queryBuffer.append("        Vendor_Sample VARCHAR2(1) path '/out/sample') (+)PET_XML,                                                                           ");
        queryBuffer.append("        XMLTABLE('for $i in $XML_DATA/pim_entry/entry   return $i' passing sup.xml_data AS \"XML_DATA\"                                       ");
        queryBuffer.append("                    COLUMNS Id VARCHAR2(20) path 'Supplier_Ctg_Spec/Id',                                                                                  ");
        queryBuffer.append("                    VenName VARCHAR2(20) path 'Supplier_Ctg_Spec/Name',                                                                                   ");
        queryBuffer.append("                    Sample_Image VARCHAR2(50) Path 'Supplier_Site_Spec/Omni_Channel/Vendor_Image_or_Sample_Indicator',                                    ");
        queryBuffer.append("                    Image_Indicator VARCHAR2(50) Path 'Supplier_Site_Spec/Omni_Channel/Image_Certification',                                              ");
        queryBuffer.append("                    Sample_Indicator VARCHAR2(50) Path 'Supplier_Site_Spec/Omni_Channel/Return_Sample_Indicator',                                         ");
        queryBuffer.append("                    OmnichannelIndicator VARCHAR(2) path 'if (Supplier_Site_Spec/Omni_Channel/Omni_Channel_Indicator eq \"true\") then \"Y\" else \"N\"' ) (+)s ");
        queryBuffer.append("  WHERE AGC.MDMID=:groupId ");

        
        String query = queryBuffer.toString();
        
        return query;
    }
    
    /**
     * This method retrieves query string for department details for teh group from department id
     * @param departmentId
     * @return query
     */
    public String getGroupDepartmentDetails(String departmentId, String classId){
        StringBuffer buff = new StringBuffer();
        
        buff.append(" SELECT d.Name DEPT_DESC, c.Name CLASS_DESC FROM   ");
        buff.append(" Vendorportal.Adse_Merchandise_Hierarchy AmC,  ");
        buff.append(" Vendorportal.Adse_Merchandise_Hierarchy AmD,  ");
        buff.append(" Xmltable('$i/pim_category/entry/Merchandise_Hier_Spec' Passing Amc.Xml_Data AS    ");
        buff.append(" \"i\" Columns RMS_ID VARCHAR(10) path 'Identifiers/RMS_Id',   ");
        buff.append(" Name VARCHAR(100) Path 'Name')C,  ");
        buff.append(" Xmltable('$i/pim_category/entry/Merchandise_Hier_Spec' Passing AmD.Xml_Data AS    ");
        buff.append(" \"i\" Columns RMS_ID VARCHAR(10) path 'Identifiers/RMS_Id',   ");
        buff.append(" Name VARCHAR(100) Path 'Name')D   ");
        buff.append(" WHERE AmD.Entry_Type='4'  ");
        buff.append(" AND AmC.Entry_Type  ='5'  ");
        buff.append(" AND C.rms_id = '");
        buff.append(classId);
        buff.append("'");
        buff.append(" AND D.rms_id = '");
        buff.append(departmentId);
        buff.append("'");
        
        return buff.toString();
    }
    
    /**
     * This method populates Omni Channel Brand list for group
     * @return OMNICHANNEL_BRAND_QUERY
     */
    public String getGroupingOmniChannelBrand(){
                final String OMNICHANNEL_BRAND_QUERY = "    SELECT OMNI_BRANDS,  "
    + "      OMNI_BRANDS_DESCRIPTION,                        "
    + "      OMNI_BRAND,                                     "
    + "      OMNI_GROUP_BRAND,                               "
    + "      SUPPLIER,                                       "
    + "      CONTAINER_TYPE,                                 "
    + "      ORIN,                                           "
    + "      ENTRY_TYPE                                      "
    + "   FROM (SELECT DISTINCT RF.MDMID OMNI_BRANDS,        "
    + "      RF.THEVALUE OMNI_BRANDS_DESCRIPTION,            "
    + "      T.OMNICODE OMNI_BRAND,                          "
    + "      T.OMNIGROUPCODE OMNI_GROUP_BRAND,               "
    + "      RF.PARENT_MDMID SUPPLIER,                       "
    + "      RF.CONTAINER CONTAINER_TYPE,                    "
    + "      AGC.MDMID ORIN,                                 "
    + "      AGC.ENTRY_TYPE                                  "
    + "         FROM ADSE_REFERENCE_DATA RF,                 "
    + "         VENDORPORTAL.ADSE_GROUP_CATALOG AGC LEFT OUTER JOIN ADSE_GROUP_CHILD_MAPPING AGCM        "  
    + "         ON AGC.MDMID=AGCM.MDMID AND AGCM.PEP_COMPONENT_TYPE<>'Group'                             "
    + "         LEFT OUTER JOIN ADSE_ITEM_CATALOG AIC ON AIC.MDMID=AGCM.COMPONENT_STYLE_ID,              "
    + "          XMLTABLE( '                                                                             "
    + "      let $code := $XML_DATA/pim_entry/entry/Ecomm_Style_Spec/OmniChannelBrand,                   "
    + "         $groupcode:=$XML_DATA/pim_entry/entry/Collection_Spec/OmniChannelBrand                   "
    + "      return                                                                                      "
    + "      <category>                                                                                  "
    + "      <code>{$code}</code>                                                                        "
    + "         <groupcode>{$groupcode} </groupcode>                                                     "
    + "          </category>' passing AGC.xml_data AS \"XML_DATA\" COLUMNS OMNICODE VARCHAR(100) path '/category/code', OMNIGROUPCODE VARCHAR(100) path '/category/groupcode') (+)T  "      
    + "         WHERE AGC.MDMID = :groupingNo     AND AIC.PETEXISTS = 'Y'  "
    + "         AND RF.CONTAINER   ='OmniChannelBrand' "        
    + "     AND RF.PARENT_MDMID = AIC.PRIMARY_SUPPLIER_ID "     
    + "  UNION "
    + "  SELECT DISTINCT RF.MDMID OMNI_BRANDS, "    
    + "      RF.THEVALUE OMNI_BRANDS_DESCRIPTION, " 
    + "      T.OMNICODE OMNI_BRAND,     "
    + "      T.OMNIGROUPCODE OMNI_GROUP_BRAND, "        
    + "      RF.PARENT_MDMID SUPPLIER,      "
    + "      RF.CONTAINER CONTAINER_TYPE,   "   
    + "      AGC.MDMID ORIN,    "
    + "      AGC.ENTRY_TYPE     "
    + "         FROM ADSE_REFERENCE_DATA RF, "  
    + "         VENDORPORTAL.ADSE_GROUP_CATALOG AGC LEFT OUTER JOIN ADSE_GROUP_CHILD_MAPPING AGCM "     
    + "         ON AGC.MDMID=AGCM.MDMID AND AGCM.PEP_COMPONENT_TYPE='Group'                  "
    + "         LEFT OUTER JOIN ADSE_GROUP_CATALOG AIC ON AIC.MDMID=AGCM.COMPONENT_GROUPING_ID, "   
    + "          XMLTABLE( '      "
    + "      let $code := $XML_DATA/pim_entry/entry/Ecomm_Style_Spec/OmniChannelBrand, "    
    + "         $groupcode:=$XML_DATA/pim_entry/entry/Collection_Spec/OmniChannelBrand  " 
    + "      return       "
    + "      <category>    " 
    + "      <code>{$code}</code> "
    + "         <groupcode>{$groupcode} </groupcode> "
    + "          </category>' passing AGC.xml_data AS \"XML_DATA\" COLUMNS OMNICODE VARCHAR(100) path '/category/code', OMNIGROUPCODE VARCHAR(100) path '/category/groupcode') (+)T          "
    + "         WHERE AGC.MDMID = :groupingNo                            "
    + "         AND RF.CONTAINER   ='OmniChannelBrand'               "
    + "     AND RF.PARENT_MDMID = AIC.DEF_PRIMARY_SUPPLIER_ID)       "
    + "     ORDER BY OMNI_BRANDS                                 ";
        
        return OMNICHANNEL_BRAND_QUERY;
    }
    /**
     * This method populates CAR Brand query for group
     * @return CAR_BRAND_QUERY
     */
    public String getGroupingCarBrandQuery(){
        final String CAR_BRAND_QUERY = "    SELECT CAR_BRANDS,      "
     + "   CAR_BRANDS_DESCRIPTION,                       "
    + "       CARS_BRAND,                                "
    + "       CARS_GROUP_BRAND,                          "
    + "       SUPPLIER,                                  "
    + "       CONTAINER_TYPE,                            "
    + "       ORIN,                                      "
    + "       ENTRY_TYPE FROM (                          "
    + "    SELECT DISTINCT RF.MDMID CAR_BRANDS,          "
    + "           RF.THEVALUE CAR_BRANDS_DESCRIPTION,    "
    + "       T.OMNICODE CARS_BRAND,                     "
    + "       T.OMNIGROUPCODE CARS_GROUP_BRAND,          "
    + "       RF.PARENT_MDMID SUPPLIER,                  "
    + "       RF.CONTAINER CONTAINER_TYPE,               "
    + "       AGC.MDMID ORIN,                            "
    + "       AGC.ENTRY_TYPE                             "
    + "         FROM ADSE_REFERENCE_DATA RF,             "
    + "       VENDORPORTAL.ADSE_GROUP_CATALOG AGC LEFT OUTER JOIN ADSE_GROUP_CHILD_MAPPING AGCM    "
    + "       ON AGC.MDMID=AGCM.MDMID AND AGCM.PEP_COMPONENT_TYPE<>'Group'                         "
    + "       LEFT OUTER JOIN ADSE_ITEM_CATALOG AIC ON AIC.MDMID=AGCM.COMPONENT_STYLE_ID,          "
    + "       XMLTABLE( '                                                                          "
    + "       let $code := $XML_DATA/pim_entry/entry/Ecomm_Style_Spec/Cars_Brand,                  "
    + "         $groupcode:=$XML_DATA/pim_entry/entry/Collection_Spec/CarBrand                     "
    + "       return                                                                               "
    + "       <category>                                                                           "
    + "       <code>{$code}</code>                                                                 "
    + "         <groupcode>{$groupcode} </groupcode>                                               "
    + "       </category>' passing AGC.xml_data AS \"XML_DATA\" COLUMNS OMNICODE VARCHAR(100) path '/category/code', OMNIGROUPCODE VARCHAR(100) path '/category/groupcode') (+)T "
    + "         WHERE AGC.MDMID = :groupingNo      AND AIC.PETEXISTS = 'Y'    "
    + "         AND RF.CONTAINER   ='Cars_Brand_Names'          "
    + "         AND RF.PARENT_MDMID = AIC.PRIMARY_SUPPLIER_ID   "
    + "    UNION                                                "
    + "     SELECT DISTINCT RF.MDMID CAR_BRANDS,                "
    + "           RF.THEVALUE CAR_BRANDS_DESCRIPTION,           "
    + "       T.OMNICODE CARS_BRAND,                            "
    + "       T.OMNIGROUPCODE CARS_GROUP_BRAND,                 "
    + "       RF.PARENT_MDMID SUPPLIER,                         "
    + "       RF.CONTAINER CONTAINER_TYPE,                      "
    + "       AGC.MDMID ORIN,                                   "
    + "       AGC.ENTRY_TYPE                                    "
    + "         FROM ADSE_REFERENCE_DATA RF,                    "
    + "       VENDORPORTAL.ADSE_GROUP_CATALOG AGC LEFT OUTER JOIN ADSE_GROUP_CHILD_MAPPING AGCM     "
    + "       ON AGC.MDMID=AGCM.MDMID AND AGCM.PEP_COMPONENT_TYPE='Group'                           "
    + "       LEFT OUTER JOIN ADSE_GROUP_CATALOG AIC ON AIC.MDMID=AGCM.COMPONENT_GROUPING_ID,       "
    + "       XMLTABLE( '                                                                           "
    + "       let $code := $XML_DATA/pim_entry/entry/Ecomm_Style_Spec/Cars_Brand,                   "
    + "         $groupcode:=$XML_DATA/pim_entry/entry/Collection_Spec/CarBrand                      "
    + "       return                                                                                "
    + "       <category>                                                                            "
    + "       <code>{$code}</code>                                                                  "
    + "         <groupcode>{$groupcode} </groupcode>                                                "
    + "       </category>' passing AGC.xml_data AS \"XML_DATA\" COLUMNS OMNICODE VARCHAR(100) path '/category/code', OMNIGROUPCODE VARCHAR(100) path '/category/groupcode') (+)T "
    + "         WHERE AGC.MDMID = :groupingNo                             "
    + "         AND RF.CONTAINER   ='Cars_Brand_Names'                "
    + "         AND RF.PARENT_MDMID = AIC.DEF_PRIMARY_SUPPLIER_ID   ) "
    + "         ORDER BY CAR_BRANDS                                  ";
        
        return CAR_BRAND_QUERY;
    }
    
    /**
     * This method populates query for grouping details section
     * @return 
     */
    public String getGroupingDetails(){
        
        final String GROUPING_DETAILS  = "  SELECT  " +
                "  AGC.MDMID GROUP_ID,  " +
                "  AGC.GROUP_NAME,  " +
                "  AGCXML.Description,  " +
                "  AGCXML.Effective_Start_Date, " +
                "  AGCXML.Effective_End_Date,   " +
                "  AGCXML.CARS_Group_Type,  " +
                "  AGC.ENTRY_TYPE GROUP_TYPE,   " +
                "  AGC.GROUP_OVERALL_STATUS_CODE,   " +
                "  AGC.CREATED_BY   " +
                "  /*XML_DATA*/ " +
                "   FROM    " +
                "  ADSE_GROUP_CATALOG AGC,  " +
                "  XMLTABLE(    " +
                "  'let                                                                          $Description:= /pim_entry/entry/Group_Ctg_Spec/Description,                             $Effective_Start_Date:= /pim_entry/entry/Group_Ctg_Spec/Effective_Start_Date,           $Effective_End_Date:= /pim_entry/entry/Group_Ctg_Spec/Effective_End_Date,                $CARS_Group_Type:= /pim_entry/entry/Group_Ctg_Spec/CARS_Group_Type                return                                                                                <out>                                                                                       <Description>{$Description}</Description>                                               <Effective_Start_Date>{$Effective_Start_Date}</Effective_Start_Date>                    <Effective_End_Date>{$Effective_End_Date}</Effective_End_Date>                          <CARS_Group_Type>{$CARS_Group_Type}</CARS_Group_Type>                      </out>'    " +
                "  passing AGC.XML_DATA Columns Description CLOB path '/out/Description',   " +
                "  Effective_Start_Date VARCHAR2(50) path '/out/Effective_Start_Date' , " +
                "  Effective_End_Date   VARCHAR2(50) path '/out/Effective_End_Date',    " +
                "  CARS_Group_Type      VARCHAR2(50) path '/out/CARS_Group_Type') AGCXML    " +
                "   WHERE   " +
                "  MDMID = :groupingNo";
        
        
        return GROUPING_DETAILS;
    }
    
    /**
     * This method populates query for Grouping Copy Attributes
     * @return COPY_ATTRIBUTES_GROUP_CONTENT
     */
    public String getGroupingCopyAttributes(){
        final String COPY_ATTRIBUTES_GROUP_CONTENT = "  SELECT AGC.MDMID,                                                          "+
        "   T.PRODUCTCOPYTEXT,                                                                                                     "+
        "   T.COPYLINE1,                                                                                                           "+
        "   T.COPYLINE2,                                                                                                           "+
        "   T.COPYLINE3,                                                                                                           "+
        "   T.COPYLINE4,                                                                                                           "+
        "   T.COPYLINE5,                                                                                                           "+
        "   T.COPYPRODUCTNAME,                                                                                                     "+
        "   T.COPYMATERIAL,                                                                                                        "+
        "   T.COPYCARE,                                                                                                            "+
        "   T.COPYCOUNTRYOFORIGIN,                                                                                                 "+
        "   T.COPYEXCLUSIVE,                                                                                                       "+
        "   T.COPYCAPROP65COMPLIANT, T.COPYIMPORTDOMESTIC                                                                          "+
        " FROM ADSE_GROUP_CATALOG AGC,                                                                                               "+
        "   XMLTABLE(                                                                                                              "+
        "   'let $productCopyText := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Product_Copy_Text,                                    "+
        " $copyLine1 := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Line_1,                                                       "+
        " $copyLine2 := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Line_2,                                                       "+
        " $copyLine3 := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Line_3,                                                       "+
        " $copyLine4 := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Line_4,                                                       "+
        " $copyLine5 := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Line_5,                                                       "+
        " $copyProductName := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Product_Name,                                           "+
        " $copyMaterial := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Material,                                                  "+
        " $copyCare := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Care,                                                          "+
        " $copyCountryOfOrigin := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Country_Of_Origin,                                  "+
        " $copyImportDomestic := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Import_Domestic,                                     "+
        " $copyExclusive := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_Exclusive,                                                "+
        " $copyCAProp65Compliant := $XML_DATA/pim_entry/entry/Copy_Sec_Spec/Copy_CAProp65_Compliant                                "+
        " return                                                                                                                   "+
        " <category>                                                                                                               "+
        " <productCopyText>{$productCopyText}</productCopyText>                                                                    "+
        " <copyLine1>{$copyLine1}</copyLine1>                                                                                      "+
        " <copyLine2>{$copyLine2}</copyLine2>                                                                                      "+
        " <copyLine3>{$copyLine3}</copyLine3>                                                                                      "+
        " <copyLine4>{$copyLine4}</copyLine4>                                                                                      "+
        " <copyLine5>{$copyLine5}</copyLine5>                                                                                      "+
        " <copyProductName>{$copyProductName}</copyProductName>                                                                    "+
        " <copyMaterial>{$copyMaterial}</copyMaterial>                                                                             "+
        " <copyCare>{$copyCare}</copyCare>                                                                                         "+
        " <copyCountryOfOrigin>{$copyCountryOfOrigin}</copyCountryOfOrigin>                                                        "+
        " <copyImportDomestic>{$copyImportDomestic}</copyImportDomestic>                                                           "+
        " <copyExclusive>{$copyExclusive}</copyExclusive>                                                                          "+
        " <copyCAProp65Compliant>{$copyCAProp65Compliant}</copyCAProp65Compliant>                                                  "+
        " </category>'                                                                                                             "+
        "   passing AGC.XML_DATA AS \"XML_DATA\" COLUMNS PRODUCTCOPYTEXT CLOB path '/category/productCopyText', " +
        "  COPYLINE1 VARCHAR(3000) path '/category/copyLine1', COPYLINE2 VARCHAR(3000) path '/category/copyLine2', " +
        "  COPYLINE3 VARCHAR(3000) path '/category/copyLine3', COPYLINE4 VARCHAR(3000) path '/category/copyLine4', " +
        "  COPYLINE5 VARCHAR(3000) path '/category/copyLine5', COPYPRODUCTNAME VARCHAR(3000) path '/category/copyProductName', " +
        "  COPYMATERIAL VARCHAR(3000) path '/category/copyMaterial', COPYCARE VARCHAR(3000) path '/category/copyCare', " +
        "  COPYCOUNTRYOFORIGIN VARCHAR(3000) path '/category/copyCountryOfOrigin', " +
        "  COPYIMPORTDOMESTIC VARCHAR(3000) path '/category/copyImportDomestic', COPYEXCLUSIVE VARCHAR(3000) path '/category/copyExclusive', " +
        "  COPYCAPROP65COMPLIANT VARCHAR(3000) path '/category/copyCAProp65Compliant') T "+
        " WHERE AGC.MDMID = :groupingNo";
        
        return COPY_ATTRIBUTES_GROUP_CONTENT;
    }
    
   
    
    /**
     * This method populates query string for group components
     * @return GROUPING_COMPONENT_QUERY
     */
    public String getGroupingComponentQuery(){
        StringBuffer queryBuffer = new StringBuffer();
        queryBuffer.append(" SELECT TAB.GROUPING_ID,                                                                                                                                   ");
        queryBuffer.append("   TAB.PARENT_MDMID STYLE_ID,                                                                                                                              ");
        queryBuffer.append("   TAB.MDMID COMPONENT_ID,                                                                                                                                 ");
        queryBuffer.append("   TAB.COMPLETION_DATE,                                                                                                                                    ");
        queryBuffer.append("   TAB.PET_STATE,                                                                                                                                          ");
        queryBuffer.append("   TAB.CONTENT_STATE,                                                                                                                                      ");
        queryBuffer.append("   TAB.ENTRY_TYPE,                                                                                                                                         ");
        queryBuffer.append("   TAB.PEP_COMPONENT_TYPE COMPONENT_TYPE,                                                                                                                  ");
        queryBuffer.append("   TAB.Color_code,                                                                                                                                         ");
        queryBuffer.append("   TAB.Color,                                                                                                                                              ");
        queryBuffer.append("   TAB.Vendor_Size,                                                                                                                                        ");
        queryBuffer.append("   TAB.PRIMARYSUPPLIERVPN,                                                                                                                                 ");
        queryBuffer.append("   TAB.DEPT_ID,                                                                                                                                            ");
        queryBuffer.append("   TAB.CLASS_ID,                                                                                                                                           ");
        queryBuffer.append("   TAB.VENDOR_SIZE_CODE,                                                                                                                                   ");
        queryBuffer.append("   OMNI_SIZE_DESC                                                                                                                                          ");
        queryBuffer.append("                                                                                                                                                           ");
        queryBuffer.append(" FROM                                                                                                                                                      ");
        queryBuffer.append("   (SELECT AGCM.MDMID GROUPING_ID,                                                                                                                         ");
        queryBuffer.append("     NULL PARENT_MDMID,                                                                                                                                    ");
        queryBuffer.append("     NULL PARENT_STYLECOLOR,                                                                                                                               ");
        queryBuffer.append("     AGC.MDMID,                                                                                                                                            ");
        queryBuffer.append("     TO_CHAR(AGC.COMPLETION_DATE,'YYYY-MM-DD') COMPLETION_DATE,                                                                                            ");
        queryBuffer.append("     AGC.GROUP_OVERALL_STATUS_CODE PET_STATE,                                                                                                              ");
        queryBuffer.append("     AGC.GROUP_CONTENT_STATUS_CODE CONTENT_STATE,                                                                                                          ");
        queryBuffer.append("     AGC.ENTRY_TYPE,                                                                                                                                       ");
        queryBuffer.append("     AGCM.PEP_COMPONENT_TYPE,                                                                                                                              ");
        queryBuffer.append("     NULL Color_code,                                                                                                                                      ");
        queryBuffer.append("     NULL Color,                                                                                                                                           ");
        queryBuffer.append("     NULL Vendor_Size,                                                                                                                                     ");
        queryBuffer.append("     AGC.DEF_PRIMARYSUPPLIERVPN PRIMARYSUPPLIERVPN,                                                                                                        ");
        queryBuffer.append("     NULL DEPT_ID,                                                                                                                                         ");
        queryBuffer.append("     NULL CLASS_ID,                                                                                                                                        ");
        queryBuffer.append("     NULL VENDOR_SIZE_CODE                                                                                                                                 ");
        queryBuffer.append("   FROM ADSE_GROUP_CATALOG AGC,                                                                                                                            ");
        queryBuffer.append("     ADSE_GROUP_CHILD_MAPPING AGCM                                                                                                                         ");
        queryBuffer.append("   WHERE AGC.MDMID            = AGCM.COMPONENT_GROUPING_ID                                                                                                 ");
        queryBuffer.append("   AND AGCM.PEP_COMponent_Type='Group'                                                                                                                     ");
        queryBuffer.append("   AND AGCM.MDMID            =:groupingNo                                                                                                                  ");
        queryBuffer.append("   UNION                                                                                                                                                   ");
        queryBuffer.append("   SELECT AGCM.MDMID,                                                                                                                                      ");
        queryBuffer.append("     AIC.PARENT_MDMID,                                                                                                                                     ");
        queryBuffer.append("     AIC.PARENT_STYLECOLOR,                                                                                                                                ");
        queryBuffer.append("     AIC.MDMID,                                                                                                                                            ");
        queryBuffer.append("     CASE                                                                                                                                                  ");
        queryBuffer.append("       WHEN AIC.ENTRY_TYPE='Style'                                                                                                                         ");
        queryBuffer.append("       THEN TO_CHAR(APC.PET_EARLIEST_COMP_DATE,'YYYY-MM-DD')                                                                                               ");
        queryBuffer.append("       ELSE PET_XML.completion_date                                                                                                                        ");
        queryBuffer.append("     END completion_date,                                                                                                                                  ");
        queryBuffer.append("     APC.PET_STATE PET_STATE,                                                                                                                              ");
        queryBuffer.append("     APC.CONTENT_STATUS CONTENT_STATE,                                                                                                                     ");
        queryBuffer.append("     AIC.ENTRY_TYPE,                                                                                                                                       ");
        queryBuffer.append("     AGCM.PEP_COMponent_Type,                                                                                                                              ");
        queryBuffer.append("     AIC_XML.VENDOR_COLOR_CODE Color_code,                                                                                                                 ");
        queryBuffer.append("     AIC_XML.VENDOR_COLOR_CODE                                                                                                                             ");
        queryBuffer.append("     || ' '                                                                                                                                                ");
        queryBuffer.append("     ||AIC_XML.VENDOR_COLOR_DESC Color,                                                                                                                    ");
        queryBuffer.append("     AIC_XML.VENDOR_SIZE_CODE                                                                                                                              ");
        queryBuffer.append("     || ' '                                                                                                                                                ");
        queryBuffer.append("     ||AIC_XML.VENDOR_SIZE_DESC Vendor_Size,                                                                                                               ");
        queryBuffer.append("     AIC.PRIMARYSUPPLIERVPN,                                                                                                                               ");
        queryBuffer.append("     AIC.DEPT_ID,                                                                                                                                          ");
        queryBuffer.append("     AIC.CLASS_ID,                                                                                                                                         ");
        queryBuffer.append("     AIC_XML.VENDOR_SIZE_CODE                                                                                                                              ");
        queryBuffer.append("   FROM ADSE_GROUP_CHILD_MAPPING AGCM,                                                                                                                     ");
        queryBuffer.append("     ADSE_ITEM_CATALOG AIC                                                                                                                                 ");
        queryBuffer.append("   INNER JOIN ADSE_PET_CATALOG APC                                                                                                                         ");
        queryBuffer.append("   ON AIC.MDMID=APC.MDMID,                                                                                                                                 ");
        queryBuffer.append("     XMLTABLE( 'let                                                                                                                                        ");
        queryBuffer.append("     $completionDate := $pets/pim_entry/entry/Pet_Ctg_Spec/Completion_Date,                                                                                ");
        queryBuffer.append("     $colordesc:= $pets/pim_entry/entry/Ecomm_StyleColor_Spec/NRF_Color_Description   return                                                               ");
        queryBuffer.append("     <out>        <completion_date>{$completionDate}</completion_date>                                                                                     ");
        queryBuffer.append("     <COLO_DESC>{$colordesc}</COLO_DESC>     </out>' passing APC.xml_data AS \"pets\"                                                                        ");
        queryBuffer.append("     Columns completion_date VARCHAR2(10) path '/out/completion_date',                                                                                     ");
        queryBuffer.append("     COLO_DESC VARCHAR2(50) path '/out/COLO_DESC' ) (+)PET_XML,                                                                                            ");
        queryBuffer.append("     XMLTABLE(                                                                                                                                             ");
        queryBuffer.append("     'for $i in $XML_DATA/pim_entry/entry                                                                                                                  ");
        queryBuffer.append("     let                                                                                                                                                   ");
        queryBuffer.append("     $uda80 := (fn:count($i/Item_UDA_Spec/UDA/Id) gt 0 and $i/Item_UDA_Spec/UDA/Id eq \"80\"),                                                               ");
        queryBuffer.append("     $non_sellable :=   (fn:count($i/Item_Simple_Pack_Spec/Sellable_Flag/text()) gt 0 and                                                                  ");
        queryBuffer.append("     ($i/Item_Simple_Pack_Spec/Sellable_Flag eq \"false\")),                                                                                                 ");
        queryBuffer.append("     $non_sellable_pack :=   (fn:count($i/Item_Complex_Pack_Spec/Sellable_Flag/text()) gt 0                                                                ");
        queryBuffer.append("     and  ($i/Item_Complex_Pack_Spec/Sellable_Flag eq \"false\")),                                                                                           ");
        queryBuffer.append("     $removal := $i/Item_Ctg_Spec/System/Removal_Flag eq     \"true\"                                                                                        ");
        queryBuffer.append("     return    <out>                                                                                                                                       ");
        queryBuffer.append("     <dept_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path, \"Merchandise_Hierarchy\")]/path,\"\\||///\")[5]}</dept_id>           ");
        queryBuffer.append("     <class_id>{fn:tokenize($i/../item_header/category_paths/category[fn:starts-with(path,   \"Merchandise_Hierarchy\")]/path,\"\\||///\")[6]}</class_id>       ");
        queryBuffer.append("     <supplier_id>{$i/Item_Ctg_Spec/Supplier[Primary_Flag eq \"true\"]/Id}</supplier_id>                                                                     ");
        queryBuffer.append("     <flag>{$uda80 and     $non_sellable_pack and $removal}</flag>                                                                                         ");
        queryBuffer.append("     <colorCode>{$i/Item_SKU_Spec/Differentiators[Type eq   \"COLOR\"]/Code}</colorCode>                                                                     ");
        queryBuffer.append("     <colorDesc>{$i/Item_SKU_Spec/Differentiators[Type eq   \"COLOR\"]/Vendor_Description}</colorDesc>                                                       ");
        queryBuffer.append("     <sizeCode>{$i/Item_SKU_Spec/Differentiators[Type eq   \"SIZE\"]/Code}</sizeCode>                                                                        ");
        queryBuffer.append("     <sizeDesc>{$i/Item_SKU_Spec/Differentiators[Type eq \"SIZE\"]/Vendor_Description}</sizeDesc>        </out>'                                             ");
        queryBuffer.append("     passing aic.XML_DATA AS \"XML_DATA\" columns flag VARCHAR(10) path '/out/flag',                                                                         ");
        queryBuffer.append("     supplier_id VARCHAR2(20) path '/out/supplier_id', deptid VARCHAR2(20) path '/out/dept_id',                                                            ");
        queryBuffer.append("     classid VARCHAR2(20) path '/out/class_id', descr VARCHAR2(64) path '/out/desc',                                                                       ");
        queryBuffer.append("     VENDOR_COLOR_CODE VARCHAR2(10) path '/out/colorCode', VENDOR_COLOR_DESC VARCHAR2(40) path '/out/colorDesc',                                           ");
        queryBuffer.append("     VENDOR_SIZE_CODE VARCHAR2(10) path '/out/sizeCode', VENDOR_SIZE_DESC VARCHAR2(10) path '/out/sizeDesc') AIC_XML                                       ");
        queryBuffer.append("   WHERE NVL(AIC.PARENT_MDMID,AIC.MDMID) = AGCM.COMPONENT_STYLE_ID                                                                                         ");
        queryBuffer.append("   AND (                                                                                                                                                   ");
        queryBuffer.append("     CASE                                                                                                                                                  ");
        queryBuffer.append("       WHEN AGCM.PEP_COMPONENT_TYPE ='SKU'                                                                                                                 ");
        queryBuffer.append("       AND AIC.ENTRY_TYPE           ='Style'  AND AIC.PETEXISTS = 'Y'                                                                                                               ");
        queryBuffer.append("       AND AIC.MDMID                =AGCM.COMPONENT_STYLE_ID                                                                                               ");
        queryBuffer.append("       OR (AIC.ENTRY_TYPE           ='StyleColor'                                                                                                          ");
        queryBuffer.append("       AND AIC.MDMID                =AGCM.COMPONENT_STYLECOLOR_ID)                                                                                         ");
        queryBuffer.append("       OR (AIC.ENTRY_TYPE           ='SKU'                                                                                                                 ");
        queryBuffer.append("       AND AIC.MDMID                =AGCM.COMPONENT_SKU_ID)                                                                                                ");
        queryBuffer.append("       THEN 1                                                                                                                                              ");
        queryBuffer.append("       WHEN AGCM.PEP_COMPONENT_TYPE ='StyleColor'                                                                                                          ");
        queryBuffer.append("       AND ((AIC.ENTRY_TYPE         ='Style'                                                                                                               ");
        queryBuffer.append("       AND AIC.MDMID                =AGCM.COMPONENT_STYLE_ID)                                                                                              ");
        queryBuffer.append("       OR (AIC.ENTRY_TYPE           ='StyleColor'                                                                                                          ");
        queryBuffer.append("       AND AIC.MDMID                =AGCM.COMPONENT_STYLECOLOR_ID))                                                                                        ");
        queryBuffer.append("       THEN 1                                                                                                                                              ");
        queryBuffer.append("       WHEN AGCM.PEP_COMPONENT_TYPE ='Style'                                                                                                               ");
        queryBuffer.append("       THEN 1                                                                                                                                              ");
        queryBuffer.append("       ELSE 0                                                                                                                                              ");
        queryBuffer.append("     END )                      =1                                                                                                                         ");
        queryBuffer.append("   AND AGCM.PEP_COMponent_Type! ='Group'                                                                                                                   ");
        queryBuffer.append("   AND AGCM.MDMID               =:groupingNo                                                                                                                ");
        queryBuffer.append("   ) TAB   LEFT OUTER JOIN ADSE_VENDOR_OMNISIZE_DESC omni                                                                                                  ");
        queryBuffer.append("   ON TAB.VENDOR_SIZE_CODE =omni.NRF_SIZE_CODE                                                                                                             ");
        queryBuffer.append("   AND TAB.dept_id         =omni.DEPT_ID                                                                                                                   ");
        queryBuffer.append("   AND TAB.Class_Id        =omni.CLASS_ID                                                                                                                  ");
        queryBuffer.append("   LEFT OUTER JOIN xmltable('for $i in $XML//omni_size_desc return $i'                                                                                     ");
        queryBuffer.append("   passing omni.XML_DATA AS \"XML\" columns OMNI_SIZE_DESC VARCHAR(40) path '.' ) o                                                                          ");
        queryBuffer.append("   ON TAB.Entry_Type = 'SKU'                                                                                                                               ");
        queryBuffer.append(" ORDER BY PEP_COMPONENT_TYPE DESC,                                                                                                                          ");                         
        queryBuffer.append("   TAB.MDMID ");
        
        return queryBuffer.toString();
    }

    
    public String populateGroupIPHcategoryPath(){
        final String CONTENT_IPH_CATEFGORY_PATH = " SELECT pet.mdmid ORIN,  "
            + "  ias.CATEGORY_DESC PET_CATEGORY_NAME    "
            + " FROM    "
            + "   VENDORPORTAL.ADSE_GROUP_CATALOG pet,  "
            + "  XMLTABLE( 'for $j in $XML_DATA/pim_entry/group_entry_header/category_paths/category    "
            + "  let               $categorydesc := $j//path,        $categoryname := tokenize($j//path, \"///\")[1]      "
            + "  return          <category>              <path>{$categorydesc}</path>            <name>{$categoryname}</name>         </category>' PASSING pet.XML_DATA AS \"XML_DATA\" COLUMNS CATEGORY_DESC VARCHAR(1000) path '/category/path', CATEGORY_NAME VARCHAR(100) path '/category/name') ias    "
            + " WHERE pet.mdmid = :groupingNo   "
            + " AND ias.CATEGORY_NAME LIKE 'Item_Primary_Hierarchy' ";
        
        return CONTENT_IPH_CATEFGORY_PATH;
    }
    
    
    public String populateGroupIPH(){
        final String CONTENT_IPH_CATEGORY_OTHERS = "  SELECT aic.mdmid, "+
            "  XMLCAST( (XMLQUERY('//pim_entry/item_header/category_paths/category[last()]/pk' PASSING aic.xml_Data RETURNING CONTENT ) ) AS VARCHAR2(1000) ) PET_CATEGORY_ID, "+
            "  SUBSTR(XMLCAST( (XMLQUERY('//pim_entry/item_header/category_paths/category[last()]/path' PASSING aic.xml_Data RETURNING CONTENT ) ) AS VARCHAR2(1000) ) , instr(XMLCAST( (XMLQUERY('//pim_entry/item_header/category_paths/category[last()]/path' PASSING aic.xml_Data RETURNING CONTENT ) ) AS VARCHAR2(1000) ) ,'-',-1,1) +1 ) PET_CATEGORY_NAME, "+
            "  '' SUB_CLASS, "+
            "  ids.*, "+
            "  XMLCAST( (XMLQUERY('//pim_entry/item_header/category_paths/category[1]/pk' PASSING aic.xml_Data RETURNING CONTENT ) ) AS VARCHAR2(1000) ) ITEM_CATEGORY_ID "+
            "  FROM ADSE_GROUP_CHILD_MAPPING AGCM, "+
            "  adse_item_catalog aic, "+
            "  ADSE_MERCHANDISE_HIERARCHY amh, "+
            "  XMLTABLE( 'for $j in $XML_DATA/pim_category/entry/Merchandise_Hier_Spec/IPH_Category_Mappings               let "+   
            "  $leafnode7 := $j//Level_7, "+   
            "  $leafnode6 := $j//Level_6, "+  
            "  $leafnode5 := $j//Level_5, "+  
            "  $leafnode4 := $j//Level_4, "+  
            "  $leafnode3 := $j//Level_3, "+  
            "  $leafnode2 := $j//Level_2, "+  
            "  $leafnode1 := $j//Level_1 "+     
            "  return "+        
            "  <IPH_Category_Mappings> "+    
            "  <leafnode7>{$leafnode7}</leafnode7> "+   
            "  <leafnode6>{$leafnode6}</leafnode6>   "+ 
            "  <leafnode5>{$leafnode5}</leafnode5> "+   
            "  <leafnode4>{$leafnode4}</leafnode4> "+   
            "  <leafnode3>{$leafnode3}</leafnode3>    "+
            "  <leafnode2>{$leafnode2}</leafnode2> "+   
            "  <leafnode1>{$leafnode1}</leafnode1>   "+
            "  </IPH_Category_Mappings>' PASSING amh.XML_DATA AS \"XML_DATA\" COLUMNS leafnode7 VARCHAR(100) path '/IPH_Category_Mappings/leafnode7', leafnode6 VARCHAR(100) path '/IPH_Category_Mappings/leafnode6', leafnode5 VARCHAR(100) path '/IPH_Category_Mappings/leafnode5', leafnode4 VARCHAR(100) path '/IPH_Category_Mappings/leafnode4', leafnode3 VARCHAR(100) path "+
            "   '/IPH_Category_Mappings/leafnode3', leafnode2                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              VARCHAR(100) path '/IPH_Category_Mappings/leafnode2', leafnode1 VARCHAR(100) path '/IPH_Category_Mappings/leafnode1') ids "+ 
            "  WHERE AGCM.mdmid           =:groupingNo "+
            "  AND AGCM.COMPONENT_DEFAULT ='true' "+
            "  AND AGCM.COMPONENT_STYLE_ID=aic.mdmid "+
            "  AND amh.MDMID              = XMLCAST( (XMLQUERY('//pim_entry/item_header/category_paths/category[1]/pk' PASSING aic.xml_Data RETURNING CONTENT ) ) AS VARCHAR2(1000) ) ";

            LOGGER.debug("CONTENT_IPH_CATEGORY_OTHERS updated here-->" + CONTENT_IPH_CATEGORY_OTHERS);
            return CONTENT_IPH_CATEGORY_OTHERS;
    }
    
    public String getGroupContentHistoryQuery(){
        final String GROUP_CONTENT_HISTORY = "  SELECT pet.mdmid,   "
            + "  i.createdBy,   "
            + "  i.createdOn,   "
            + "  i.lastState,   "
            + "  i.lastUpdateOn,    "
            + "  i.lastUpdateBy,    "
            + "  i.currentState     "
            + " FROM VENDORPORTAL.ADSE_GROUP_CATALOG pet,   "
            + "  XMLTable(  "
            + "  'for $i in $pet/pim_entry/entry/Group_Ctg_Spec/Audit/Content      "
            + "  let  $lastState := $i//Last_State,     "
            + "  $lastUpdate := $i//Last_State_On,      "
            + "  $lastUpdateBy := $i//Last_State_By,    "
            + "  $createdBy := $i//..//Created_By,      "
            + "  $createdOn := $i//..//Created_On,      "
            + "  $currentState := $i//..//..//State                                                                                                                                  return                                                                       <petStatus>                                                                              <lastState>{$lastState}</lastState>                                                     <lastUpdate>{$lastUpdate}</lastUpdate>                                                  <lastUpdateBy>{$lastUpdateBy}</lastUpdateBy>                                            <createdBy>{$createdBy}</createdBy>                                                     <createdOn>{$createdOn}</createdOn>                                                     <currentState>{$currentState}</currentState>                                </petStatus>'    "
            + "  passing pet.XML_DATA AS \"pet\"    "
            + "  columns    "
            + "    lastState VARCHAR2(40) path '/petStatus/lastState' ,     "
            + "    lastUpdateOn VARCHAR2(40) path '/petStatus/lastUpdate' ,     "
            + "    lastUpdateBy VARCHAR2(40) path '/petStatus/lastUpdateBy',    "
            + "    createdBy VARCHAR2(40) path '/petStatus/createdBy',      "
            + "    createdOn VARCHAR2(40) path '/petStatus/createdOn',  "
            + "    currentState VARCHAR2(40) path '/petStatus/currentState' ) i     "
            + "WHERE pet.mdmid=:groupingNo";
        
        return GROUP_CONTENT_HISTORY;
    }
    
    /**
     * Method to get the group copy validation query.
     *    
     * @return the query string
     * 
     * Method added For PIM Phase 2 - Group Content
     * Date: 06/18/2016
     * Added By: Cognizant
     */
    public String getGroupCopyValidation() {
        final StringBuffer groupCopyValidationQuery = new StringBuffer();
        LOGGER.info("***Entering getGroupCopyValidation() method.");
        groupCopyValidationQuery
                .append("SELECT COUNT(*) COUNT_GROUP FROM ADSE_GROUP_CHILD_MAPPING WHERE COMPONENT_STYLE_ID = :styleId AND MDMID= :groupId");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("GROUP COPY VALIDATION QUERY -- \n"
                    + groupCopyValidationQuery.toString());
        }
        LOGGER.info("***Exiting getGroupCopyValidation() method.");
        return groupCopyValidationQuery.toString();
    }
    
    /**
     * Method to get the group copy validation query.
     *    
     * @return the query string
     * 
     * Method added For PIM Phase 2 - Group Content
     * Date: 06/23/2016
     * Added By: Cognizant
     */
    public String getPETStateCopyValidation() {
        final StringBuffer groupCopyValidationQuery = new StringBuffer();
        LOGGER.info("***Entering getPETStateCopyValidation() method.");
        groupCopyValidationQuery
                .append("SELECT PET_STATE FROM ADSE_PET_CATALOG WHERE MDMID = :styleId ");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("GROUP COPY VALIDATION QUERY -- \n"
                    + groupCopyValidationQuery.toString());
        }
        LOGGER.info("***Exiting getGroupCopyValidation() method.");
        return groupCopyValidationQuery.toString();
    }
    
    public String populateGroupIPHForCollections(){
        
        final Properties prop =   PropertiesFileLoader.getPropertyLoader(ContentScreenConstants.MESS_PROP);
        String categoryId = prop.getProperty(ContentScreenConstants.DUMMY_CATEGORY_ID_COLLECTION);
        if(LOGGER.isDebugEnabled())
            LOGGER.debug("categoryId in  populateGroupIPHForCollections--- >"+categoryId);
        
        StringBuilder query = new StringBuilder();
        
        query.append(" SELECT pet.mdmid ORIN,                                                                                                                                  " );
        query.append("   ias.CATEGORY_ID PET_CATEGORY_ID,                                                                                                                      " );
        query.append("   ias.CATEGORY_NAME PET_CATEGORY_NAME,                                                                                                                  " );
        query.append("   ics.SUB_CLASS SUB_CLASS,                                                                                                                              " );
        query.append("   ids.leafnode7,                                                                                                                                        " );
        query.append("   ids.leafnode6,                                                                                                                                        " );
        query.append("   ids.leafnode5,                                                                                                                                        " );
        query.append("   ids.leafnode4,                                                                                                                                        " );
        query.append("   ids.leafnode3,                                                                                                                                        " );
        query.append("   ids.leafnode2,                                                                                                                                        " );
        query.append("   ids.leafnode1                                                                                                                                         " );
        query.append(" FROM VENDORPORTAL.ADSE_GROUP_CATALOG pet,                                                                                                               " );
        query.append("   XMLTABLE( 'for $j in $XML_DATA/pim_entry/group_entry_header/category_paths/category[last()]                                                           " );
        query.append(" let           $categoryid := $j//pk,                                                                                                                    " );
        query.append("                                                                                                                                                         " );
        query.append(" $categoryname := tokenize(tokenize($j//path, \"///\")[last()],\"-\")[last()]                                                                                " );
        query.append(" return                                                                                                                                                  " );
        query.append(" <category>                                                                                                                                              " );
        query.append(" <pk>{$categoryid}</pk>                                                                                                                                  " );
        query.append("                                                                                                                                                         " );
        query.append(" <name>{$categoryname}</name>                                                                                                                            " );
        query.append(" </category>' PASSING pet.XML_DATA AS \"XML_DATA\" COLUMNS CATEGORY_ID VARCHAR(100) path '/category/pk',                                                   " );
                                                                                             
        query.append("   CATEGORY_NAME VARCHAR(100) path '/category/name') ias ,                                                                                               " );
        query.append("   VENDORPORTAL.ADSE_MERCHANDISE_HIERARCHY amh,                                                                                                          " );
        query.append("   XMLTABLE( 'for $j in $XML_DATA/pim_category/merchandise_category_header                                                                               " );
        query.append(" let           $subclass := tokenize($j//full_path, \"///\")[last()]                                                                                       " );
        query.append(" return                                                                                                                                                  " );
        query.append(" <merchandise_category_header>                                                                                                                           " );
        query.append(" <full_path>{$subclass}</full_path>                                                                                                                      " );
        query.append(" </merchandise_category_header>' PASSING amh.XML_DATA AS \"XML_DATA\" COLUMNS SUB_CLASS VARCHAR(100) path '/merchandise_category_header/full_path') ics ,  " );
        query.append("   VENDORPORTAL.ADSE_MERCHANDISE_HIERARCHY amh1,                                                                                                         " );
        query.append("   XMLTABLE(                                                                                                                                             " );
        query.append("   'for $j in $XML_DATA/pim_category/entry/Merchandise_Hier_Spec/IPH_Category_Mappings                                                                   " );
        query.append("   let                                                                                                                                                   " );
        query.append("   $leafnode7 := $j//Level_7,                                                                                                                            " );
        query.append("   $leafnode6 := $j//Level_6,                                                                                                                            " );
        query.append("   $leafnode5 := $j//Level_5,                                                                                                                            " );
        query.append("   $leafnode4 := $j//Level_4,                                                                                                                            " );
        query.append("   $leafnode3 := $j//Level_3,                                                                                                                            " );
        query.append("   $leafnode2 := $j//Level_2,                                                                                                                            " );
        query.append("   $leafnode1 := $j//Level_1                                                                                                                             " );
        query.append("   return                                                                                                                                                " );
        query.append("   <IPH_Category_Mappings>                                                                                                                               " );
        query.append("   <leafnode7>{$leafnode7}</leafnode7>                                                                                                                   " );
        query.append("   <leafnode6>{$leafnode6}</leafnode6>                                                                                                                   " );
        query.append("   <leafnode5>{$leafnode5}</leafnode5>                                                                                                                   " );
        query.append("   <leafnode4>{$leafnode4}</leafnode4>                                                                                                                   " );
        query.append("   <leafnode3>{$leafnode3}</leafnode3>                                                                                                                   " );
        query.append("   <leafnode2>{$leafnode2}</leafnode2>                                                                                                                   " );
        query.append("   <leafnode1>{$leafnode1}</leafnode1>                                                                                                                   " );
        query.append("   </IPH_Category_Mappings>'                                                                                                                             " );
        query.append("   PASSING amh1.XML_DATA AS \"XML_DATA\"                                                                                                                   " );
        query.append("   COLUMNS leafnode7 VARCHAR(100) path '/IPH_Category_Mappings/leafnode7',                                                                               " );
        query.append("   leafnode6 VARCHAR(100) path '/IPH_Category_Mappings/leafnode6',                                                                                       " );
        query.append("   leafnode5 VARCHAR(100) path '/IPH_Category_Mappings/leafnode5',                                                                                       " );
        query.append("   leafnode4 VARCHAR(100) path '/IPH_Category_Mappings/leafnode4',                                                                                       " );
        query.append("   leafnode3 VARCHAR(100) path '/IPH_Category_Mappings/leafnode3',                                                                                       " );
        query.append("   leafnode2 VARCHAR(100) path '/IPH_Category_Mappings/leafnode2',                                                                                       " );
        query.append("   leafnode1 VARCHAR(100) path '/IPH_Category_Mappings/leafnode1') ids                                                                                   " );
        query.append(" WHERE amh1.MDMID = '");
        query.append(categoryId);                                                                                                                              
        query.append("' AND amh1.MDMID = amh.MDMID                                                                                                                              " );
        query.append(" AND pet.mdmid  = :groupingNo                                                                             "); 
        
            return query.toString();
    }
    
    
    /**
     * @return String
     */
    public static String getGroupGlobalAttributes(){
        StringBuilder query = new StringBuilder();
        
        query.append("  SELECT                                                        ");
        query.append("    p.Belk_Exclusive,                                           ");
        query.append("    p.Channel_Exclusive,                                        ");
        query.append("    p.BOPIS,                                                    ");
        query.append("    p.Is_PYG,                                                   ");
        query.append("    p.is_GWP,                                                   ");
        query.append("    p.is_PWP,                                                    ");
        query.append("    p.Supplier_Direct_Fulfillment                               ");
        query.append("  FROM ADSE_GROUP_CATALOG pet,                                  ");
        query.append("    XMLTable( 'for $i in $item/pim_entry/entry/Ecomm_Style_Spec ");
        query.append("  return  $i' passing pet.XML_DATA AS \"item\"                        ");
        query.append("    columns                                                       ");
        query.append("    Belk_Exclusive VARCHAR2(10) Path 'Belk_Exclusive',            ");
        query.append("    Channel_Exclusive VARCHAR2(30) path 'Channel_Exclusive',      ");
        query.append("    Bopis VARCHAR2(60) Path 'BOPIS',                              ");
        query.append("    Is_PYG VARCHAR2(40) path 'Is_PYG',                            ");
        query.append("    Is_Gwp VARCHAR2(40) Path 'Is_GWP',                            ");
        query.append("    Is_PWP VARCHAR2(40) path 'Is_PWP',                         ");
        query.append("    Supplier_Direct_Fulfillment VARCHAR2(40) path 'Supplier_Direct_Fulfillment' ) p                         ");
        query.append("    WHERE  pet.mdmid = :groupingId                                ");
        
        return query.toString();
    }
public String getComponentIDs(){       
        
        final String COMPONENT_IDS= " select * from ADSE_GROUP_CHILD_MAPPING AGCM   where  AGCM.MDMID= ?";          
        
        return COMPONENT_IDS;
              
  }
    
/**
 * Modified
 * @return
 */
public String getGroupingComponents() {

    final String GROUPING_DTLS_QUERY = " SELECT TAB.GROUPING_ID, "
            + " TAB.PARENT_MDMID STYLE_ID, "
            + " TAB.MDMID COMPONENT_ID, "
            + " TAB.COMPLETION_DATE, "
            + " TAB.PET_STATE, "
            + " TAB.CONTENT_STATE, "
            + "  TAB.ENTRY_TYPE, "
            + " TAB.PEP_COMPONENT_TYPE COMPONENT_TYPE, "
            + " TAB.Color_code, "
            + " TAB.Color, "
            + " TAB.Vendor_Size, "
            + " TAB.PRIMARYSUPPLIERVPN, "
            + " TAB.DEPT_ID, "
            + " TAB.CLASS_ID, "
            + " TAB.VENDOR_SIZE_CODE, "
            + " OMNI_SIZE_DESC "
            + " FROM "
            + " (SELECT AGCM.MDMID GROUPING_ID, "
            + " NULL PARENT_MDMID, "
            + " NULL PARENT_STYLECOLOR, "
            + " AGC.MDMID, "
            + " TO_CHAR(AGC.COMPLETION_DATE,'YYYY-MM-DD') COMPLETION_DATE, "
            + " AGC.GROUP_OVERALL_STATUS_CODE PET_STATE, "
            + " AGC.GROUP_CONTENT_STATUS_CODE CONTENT_STATE, "
            + " AGC.ENTRY_TYPE, "
            + " AGCM.PEP_COMPONENT_TYPE, "
            + " NULL Color_code, "
            + " NULL Color, "
            + " NULL Vendor_Size, "
            + " AGC.DEF_PRIMARYSUPPLIERVPN PRIMARYSUPPLIERVPN, "
            + " NULL DEPT_ID, "
            + " NULL CLASS_ID, "
            + " NULL VENDOR_SIZE_CODE "
            + " FROM ADSE_GROUP_CATALOG AGC, "
            + " ADSE_GROUP_CHILD_MAPPING AGCM "
            + " WHERE AGC.MDMID            = AGCM.COMPONENT_GROUPING_ID "
            + " AND AGCM.PEP_COMponent_Type='Group' "
            + " AND AGCM.MDMID             =:groupingNo "
            + " UNION "
            + " SELECT AGCM.MDMID, "
            + " AIC.PARENT_MDMID, "
            + " AIC.PARENT_STYLECOLOR, "
            + " AIC.MDMID, "
            + " CASE "
            + " WHEN AIC.ENTRY_TYPE='Style' "
            + " THEN TO_CHAR(APC.PET_EARLIEST_COMP_DATE,'YYYY-MM-DD') "
            + " ELSE PET_XML.completion_date "
            + " END completion_date, "
            + " APC.PET_STATE PET_STATE, "
            + " APC.CONTENT_STATUS CONTENT_STATE, "
            + "  AIC.ENTRY_TYPE, "
            + " AGCM.PEP_COMponent_Type, "
            + " AIC.COLOR_CODE, "
            + " AIC.COLOR_CODE "
            + " || ' ' "
            + " ||AIC.COLOR_NAME Color, "
            + " AIC.SIZE_CODE "
            + " || ' ' "
            + " ||AIC.SIZE_NAME Vendor_Size, "
            + " AIC.PRIMARYSUPPLIERVPN, "
            + " AIC.DEPT_ID, "
            + " AIC.CLASS_ID, "
            + " AIC.SIZE_NAME "
            + " FROM ADSE_GROUP_CHILD_MAPPING AGCM, "
            + " ADSE_ITEM_CATALOG AIC "
            + " INNER JOIN ADSE_PET_CATALOG APC "
            + " ON AIC.MDMID=APC.MDMID, "
            + " XMLTABLE('let  "
            + "$completionDate := $pets/pim_entry/entry/Pet_Ctg_Spec/Completion_Date return "
            + "<out> <completion_date>{$completionDate}</completion_date> </out>' "
            + "passing APC.xml_data AS \"pets\" Columns completion_date VARCHAR2(10) path '/out/completion_date') (+)PET_XML "
            + " WHERE (AIC.PARENT_MDMID in (:componentIds) or AIC.MDMID in (:componentIds)) "
            + " AND (APC.PARENT_MDMID in (:componentIds) or APC.MDMID in (:componentIds))  AND AIC.PETEXISTS = 'Y' "
            + " AND ( "
            + " CASE "
            + "  WHEN AGCM.PEP_COMPONENT_TYPE ='SKU' "
            + " AND AIC.ENTRY_TYPE           ='Style' "
            + " AND AIC.MDMID                =AGCM.COMPONENT_STYLE_ID "
            + " OR (AIC.ENTRY_TYPE           ='StyleColor' "
            + " AND AIC.MDMID                =AGCM.COMPONENT_STYLECOLOR_ID) "
            + " OR (AIC.ENTRY_TYPE           ='SKU' "
            + " AND AIC.MDMID                =AGCM.COMPONENT_SKU_ID) "
            + " THEN 1 "
            + " WHEN AGCM.PEP_COMPONENT_TYPE ='StyleColor' "
            + " AND ((AIC.ENTRY_TYPE         ='Style' "
            + " AND AIC.MDMID                =AGCM.COMPONENT_STYLE_ID) "
            + " OR (AIC.ENTRY_TYPE           ='StyleColor' "
            + " AND AIC.MDMID                =AGCM.COMPONENT_STYLECOLOR_ID)) "
            + " THEN 1 "
            + " WHEN AGCM.PEP_COMPONENT_TYPE ='Style' "
            + " THEN 1 "
            + " ELSE 0 "
            + " END )                      =1 "
            + " AND AGCM.PEP_COMponent_Type! ='Group' "
            + " AND AGCM.MDMID               =:groupingNo "
            + " ) TAB "
            + " LEFT OUTER JOIN ADSE_VENDOR_OMNISIZE_DESC omni "
            + " ON TAB.VENDOR_SIZE_CODE =omni.NRF_SIZE_CODE "
            + " AND TAB.dept_id         =omni.DEPT_ID "
            + " AND TAB.Class_Id        =omni.CLASS_ID "
            + " LEFT OUTER JOIN xmltable('for $i in $XML//omni_size_desc return $i' passing omni.XML_DATA AS \"XML\" columns OMNI_SIZE_DESC VARCHAR(40) path '.' ) o "
            + " ON TAB.Entry_Type = 'SKU' "
            + " ORDER BY PEP_COMPONENT_TYPE DESC, " + " TAB.MDMID ";
    
    return GROUPING_DTLS_QUERY;
}

public static String copyRegularContentValidationQuery(){
	
	 final String query = "SELECT MDMID,ENTRY_TYPE FROM ADSE_ITEM_CATALOG WHERE PETEXISTS='Y' AND DELETED_FLAG='false' " +
			"AND ENTRY_TYPE IN ('Style','Complex Pack','ComplexPack') AND (MDMID= :styleId OR PRIMARYSUPPLIERVPN = :styleId )";
	
	return query;
	
}

}

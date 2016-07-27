create or replace procedure VENDORPORTAL.update_adse_item_catalog
as
  cursor c_data is
  select mdmid 
    from adse_item_catalog 
   where entry_type = 'SKU';
  l_count NUMBER := 0;
  l_row c_data%rowtype;
  mdmid adse_pet_catalog.mdmid%type;
  errordtl varchar2(250);
begin
    open c_data;
    loop
        fetch c_data into l_row;
        exit when c_data%notfound;
            begin
            mdmid:=l_row.mdmid;
                        update adse_item_catalog 
                           set xml_data = xml_data 
						 where mdmid = l_row.mdmid;
                        l_count :=l_count+1;  
                          if l_count = 100 then
                            commit; -- commit since 10 records are done
                            l_count := 0; --resetting the counter for next lot
                        end if; 
            exception 
                when others then
                errordtl:=substr(sqlerrm,1,250);
                insert into log_error_data(key_value,error_message) 
                values(mdmid, errordtl);   
            end;
                    commit; -- committing remaining records;
    end loop;
    close c_data;
              
 exception 
     when others then
        raise_application_error(-20001,substr(sqlerrm,1,100)); 
 end ;
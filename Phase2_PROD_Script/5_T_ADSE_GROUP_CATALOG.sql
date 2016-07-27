--------------------------------------------------------
--  DDL for Trigger T_ADSE_GROUP_CATALOG
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TRIGGER "VENDORPORTAL"."T_ADSE_GROUP_CATALOG" before update ON VENDORPORTAL.adse_group_catalog
	for each row
Begin
	  :new.LST_UPT_DTM := current_timestamp;

End;
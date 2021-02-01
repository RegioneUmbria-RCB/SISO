/* Query di Utility*/
SQL_DWH_IS_DROP_SOGG=SELECT scp.processid FROM sit_correlazione_processid scp WHERE scp.id_ente_sorg =? \
					AND data_agg_sogg IS NOT null AND scp.processid NOT IN (SELECT DISTINCT(seu.processid) from $TAB seu)

SQL_DWH_IS_DROP_OGG=SELECT scp.processid FROM sit_correlazione_processid scp WHERE scp.id_ente_sorg =? \
					AND data_agg_ogg IS NOT null AND scp.processid NOT IN (SELECT DISTINCT(seu.processid) from $TAB seu)
			
SQL_DWH_IS_DROP_CIV=SELECT scp.processid FROM sit_correlazione_processid scp WHERE scp.id_ente_sorg =? \
					AND data_agg_civico IS NOT null AND scp.processid NOT IN (SELECT DISTINCT(seu.processid) from $TAB seu)

SQL_DWH_IS_DROP_VIA=SELECT scp.processid FROM sit_correlazione_processid scp WHERE scp.id_ente_sorg =? \
					AND data_agg_via IS NOT null AND scp.processid NOT IN (SELECT DISTINCT(seu.processid) from $TAB seu)
					
SQL_GET_ALL_PID_DWH=select distinct(PROCESSID) as prID from

SQL_INSERT_PID_SOGG=INSERT INTO SIT_CORRELAZIONE_PROCESSID \
					(PROCESSID, ID_ENTE_SORG, DATA_AGG_SOGG) VALUES (?,?,?)

SQL_INSERT_PID_OGG=INSERT INTO SIT_CORRELAZIONE_PROCESSID \
					(PROCESSID, ID_ENTE_SORG, DATA_AGG_OGG) VALUES (?,?,?)

SQL_INSERT_PID_CIV=INSERT INTO SIT_CORRELAZIONE_PROCESSID \
					(PROCESSID, ID_ENTE_SORG, DATA_AGG_CIVICO) VALUES (?,?,?)

SQL_INSERT_PID_VIA=INSERT INTO SIT_CORRELAZIONE_PROCESSID \
					(PROCESSID, ID_ENTE_SORG, DATA_AGG_VIA) VALUES (?,?,?)

SQL_UPDATE_PID_SOGG=UPDATE SIT_CORRELAZIONE_PROCESSID SET DATA_AGG_SOGG = ? WHERE PROCESSID = ?

SQL_UPDATE_PID_OGG=UPDATE SIT_CORRELAZIONE_PROCESSID SET DATA_AGG_OGG = ? WHERE PROCESSID = ?

SQL_UPDATE_PID_CIV=UPDATE SIT_CORRELAZIONE_PROCESSID SET DATA_AGG_CIVICO = ? WHERE PROCESSID = ?

SQL_UPDATE_PID_VIA=UPDATE SIT_CORRELAZIONE_PROCESSID SET DATA_AGG_VIA = ? WHERE PROCESSID = ?

SQL_NEW_PID_DWH_SOGG=(SELECT 1 from SIT_CORRELAZIONE_PROCESSID pid WHERE pid.processid=tabDWH.processid AND id_ente_sorg = ? \
						AND data_agg_sogg is not null)

SQL_NEW_PID_DWH_OGG=(SELECT 1 from SIT_CORRELAZIONE_PROCESSID pid WHERE pid.processid=tabDWH.processid AND id_ente_sorg = ? \
					AND data_agg_ogg is not null)

SQL_NEW_PID_DWH_CIV=(SELECT 1 from SIT_CORRELAZIONE_PROCESSID pid WHERE pid.processid=tabDWH.processid AND id_ente_sorg = ? \
					AND data_agg_civico is not null)

SQL_NEW_PID_DWH_VIA=(SELECT 1 from SIT_CORRELAZIONE_PROCESSID pid WHERE pid.processid=tabDWH.processid AND id_ente_sorg = ? \
					AND data_agg_via is not null)
				

SQL_PID_EXIST=SELECT * FROM SIT_CORRELAZIONE_PROCESSID WHERE PROCESSID=? AND ID_ENTE_SORG =? AND \
			(data_agg_sogg is not null OR data_agg_ogg is not null OR data_agg_via is not null OR data_agg_civico is not null)
			

SQL_GET_PID_DWH_SOGG=select * from sit_correlazione_processid where id_ente_sorg = ? and data_agg_sogg is not null

SQL_GET_PID_DWH_OGG=select * from sit_correlazione_processid where id_ente_sorg = ? and data_agg_ogg is not null

SQL_GET_PID_DWH_CIV=select * from sit_correlazione_processid where id_ente_sorg = ? and data_agg_civico is not null

SQL_GET_PID_DWH_VIA=select * from sit_correlazione_processid where id_ente_sorg = ? and data_agg_via is not null
		

SQL_DELETE_PID_SOGG=update sit_correlazione_processid set data_agg_sogg=null where id_ente_sorg =?

SQL_DELETE_PID_OGG=update sit_correlazione_processid set data_agg_ogg=null where id_ente_sorg =?

SQL_DELETE_PID_CIV=update sit_correlazione_processid set data_agg_civico=null where id_ente_sorg =?

SQL_DELETE_PID_VIA=update sit_correlazione_processid set data_agg_via=null where id_ente_sorg =?

SQL_DELETE_ALL_PID=DELETE FROM sit_correlazione_processid WHERE id_ente_sorg =? AND data_agg_sogg IS NULL AND data_agg_ogg IS NULL \
					AND data_agg_via IS NULL AND data_agg_civico IS NULL

SQL_DELETE_COLL_CIV=DELETE FROM sit_civico_totale WHERE id_dwh=? AND fk_ente_sorgente=? AND prog_es=? \
					AND ctr_hash NOT IN($lista)
					
SQL_AGG_CIV_DA_VIA=UPDATE SIT_CIVICO_TOTALE SET FK_CIVICO = null, FK_VIA = null, REL_DESCR=null, RATING=null WHERE ID_ORIG_VIA_TOTALE = ? AND FK_ENTE_SORGENTE = ? AND PROG_ES = ?					
		

					
/* Query gestione*/
SQL_ENTE_SORGENTE=SELECT ID FROM SIT_ENTE_SORGENTE \
				   WHERE ID = ?
											
SQL_INS_SOGG_TOTALE=INSERT INTO /*+APPEND*/  SIT_SOGGETTO_TOTALE (\
    				ID_DWH, FK_ENTE_SORGENTE,PROG_ES,CTR_HASH, COGNOME,NOME,\
    				DENOMINAZIONE,SESSO,CODFISC,PI,DT_NASCITA,\
    				TIPO_PERSONA,COD_PROVINCIA_NASCITA,COD_COMUNE_NASCITA,DESC_PROVINCIA_NASCITA,DESC_COMUNE_NASCITA,\
					COD_PROVINCIA_RES,COD_COMUNE_RES,DESC_PROVINCIA_RES,DESC_COMUNE_RES,ID_STORICO,DT_FINE_VAL,ANOMALIA,STATO,CODICE_SOGGETTO) \
					VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
					
SQL_DEL_SOGG_TOTALE=DELETE FROM SIT_SOGGETTO_TOTALE \
				   WHERE FK_ENTE_SORGENTE = ? AND PROG_ES = ?
				   
SQL_UPDATE_SOGG_TOTALE=UPDATE SIT_SOGGETTO_TOTALE \
					   SET CTR_HASH=?, COGNOME=?, NOME=?, DENOMINAZIONE=?, SESSO=?, CODFISC=?, PI=?, DT_NASCITA=?, \
					   TIPO_PERSONA=?, COD_PROVINCIA_NASCITA=?, COD_COMUNE_NASCITA=?, DESC_PROVINCIA_NASCITA=?, \
					   DESC_COMUNE_NASCITA=?, COD_PROVINCIA_RES=?, COD_COMUNE_RES=?, DESC_PROVINCIA_RES=?, DESC_COMUNE_RES=?, \
					   FK_SOGGETTO=?, REL_DESCR=?, RATING=?, NOTE=?, ID_STORICO=?, DT_FINE_VAL=?, ANOMALIA=?, STATO=?, CODICE_SOGGETTO=? \
					   WHERE ID_DWH=? AND FK_ENTE_SORGENTE=? AND PROG_ES=?

SQL_CERCA_SOGG_TOTALE=SELECT * FROM SIT_SOGGETTO_TOTALE \
						WHERE ID_DWH=? AND FK_ENTE_SORGENTE=? AND PROG_ES=?	   
					   


SQL_DEL_VIA_TOTALE=DELETE FROM SIT_VIA_TOTALE \
				   WHERE FK_ENTE_SORGENTE = ? AND PROG_ES = ?
				   

SQL_INS_VIA_TOTALE=INSERT INTO /*+ APPEND*/  SIT_VIA_TOTALE \
				   (ID_DWH, FK_ENTE_SORGENTE,CTR_HASH, SEDIME, INDIRIZZO, PROG_ES, NOTE,ID_STORICO,DT_FINE_VAL,ANOMALIA,STATO,CODICE_VIA) \
				   VALUES (?,?,?,?,?,?,?,?,?,?,?,?)
				   
SQL_UPDATE_VIA_TOTALE=UPDATE SIT_VIA_TOTALE \
					   SET CTR_HASH=?, FK_VIA=?, REL_DESCR=?, RATING=?, SEDIME=?, INDIRIZZO=?, NOTE=?, ID_STORICO=?, DT_FINE_VAL=?, ANOMALIA=?, STATO=?, CODICE_VIA=? \
					   WHERE ID_DWH=? AND FK_ENTE_SORGENTE=? AND PROG_ES=?
					   
SQL_CERCA_VIA_TOTALE=SELECT * FROM SIT_VIA_TOTALE \
						WHERE ID_DWH=? AND FK_ENTE_SORGENTE=? AND PROG_ES=?				   					   			   

SQL_INS_OGG_TOTALE=INSERT INTO /*+ APPEND*/  SIT_OGGETTO_TOTALE \
				   (ID_DWH, FK_ENTE_SORGENTE,CTR_HASH, PROG_ES, FOGLIO, PARTICELLA, SUB, CATEGORIA, CLASSE, RENDITA, ZONA, FOGLIO_URBANO, SUPERFICIE, SCALA, INTERNO, PIANO, DT_INIZIO_VAL, FK_OGGETTO, REL_DESCR, RATING, NOTE,ID_STORICO,DT_FINE_VAL,ANOMALIA,STATO,SEZIONE) \
				   VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)

SQL_DEL_OGG_TOTALE=DELETE FROM SIT_OGGETTO_TOTALE \
				   WHERE FK_ENTE_SORGENTE = ? and PROG_ES = ?
				   
SQL_UPDATE_OGG_TOTALE=UPDATE SIT_OGGETTO_TOTALE \
					   SET CTR_HASH=?, FOGLIO=?, PARTICELLA=?, SUB=?, CATEGORIA=?, CLASSE=?, RENDITA=?, ZONA=?, FOGLIO_URBANO=?, \
					   SUPERFICIE=?, SCALA=?, INTERNO=?, PIANO=?, DT_INIZIO_VAL=?, FK_OGGETTO=?, REL_DESCR=?, RATING=?, NOTE=?, \
					   ID_STORICO=?, DT_FINE_VAL=?, ANOMALIA=?, STATO=?, SEZIONE=? \
					   WHERE ID_DWH=? AND FK_ENTE_SORGENTE=? AND PROG_ES=? AND CTR_HASH=?

SQL_CERCA_OGG_TOTALE=SELECT * FROM SIT_OGGETTO_TOTALE \
						WHERE ID_DWH=? AND FK_ENTE_SORGENTE=? AND PROG_ES=? AND CTR_HASH=?
				   
						
SQL_INS_CIVICO_TOTALE=INSERT INTO /*+APPEND*/  SIT_CIVICO_TOTALE \
				   (ID_DWH, FK_ENTE_SORGENTE,PROG_ES,CTR_HASH, CIVICO_COMPOSTO,\
				   CIV_LIV1,CIV_LIV2,CIV_LIV3,ID_ORIG_VIA_TOTALE,ID_STORICO,DT_FINE_VAL,NOTE,ANOMALIA,STATO,CODICE_CIVICO) \
				   VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)				   
				   
SQL_DEL_CIVICO_TOTALE=DELETE FROM SIT_CIVICO_TOTALE \
				   WHERE FK_ENTE_SORGENTE = ? AND PROG_ES = ?
				   
SQL_UPDATE_CIV_TOTALE=UPDATE SIT_CIVICO_TOTALE \
					   SET CTR_HASH=?, FK_CIVICO=?, REL_DESCR=?, RATING=?, CIVICO_COMPOSTO=?, CIV_LIV1=?, CIV_LIV2=?, CIV_LIV3=?, ID_ORIG_VIA_TOTALE=?, \
					   ID_STORICO=?, DT_FINE_VAL=?, NOTE=?, ANOMALIA=?, STATO=?, CODICE_CIVICO=? \
					   WHERE ID_DWH=? AND FK_ENTE_SORGENTE=? AND PROG_ES=? AND CTR_HASH=?
				   
SQL_CERCA_CIV_TOTALE=SELECT * FROM SIT_CIVICO_TOTALE \
						WHERE ID_DWH=? AND FK_ENTE_SORGENTE=? AND PROG_ES=? AND CTR_HASH=?
				   				   
				 				  

/*#############################################################################################################################################################################################################*/
/*########################################      Query SOGGETTI      #############################################################################################################################################*/
/*#############################################################################################################################################################################################################*/
			   
SQL_SOGG_DEMOGRAFIA=SELECT PER.ID AS ID_DWH, PER.ID_EXT AS ID_STORICO, \
					PER.DT_INIZIO_VAL AS DT_INIZIO_VAL, \
					PER.DT_FINE_VAL AS DT_FINE_VAL, \
					PER.COGNOME AS COGNOME,\
					PER.NOME AS NOME,\
					SUBSTR (PER.COGNOME || ' ' || PER.NOME,1,150) AS DENOMINAZIONE, \
					PER.SESSO AS SESSO, \
					PER.CODFISC AS COD_FISC, \
					NULL AS PI, \
					PER.DATA_NASCITA DT_NASCITA,\
					'F' AS TIPO_PERSONA, \
					PRO1.ID_ORIG AS COD_PROVINCIA_NASCITA, \
					COM1.ID_ORIG AS COD_COMUNE_NASCITA, \
					PRO1.DESCRIZIONE AS DESC_PROVINCIA_NASCITA, \
					COM1.DESCRIZIONE AS DESC_COMUNE_NASCITA, \
					PER.POSIZ_ANA,\
			        PER.STATO_CIVILE,\
			        PER.FLAG_CODICE_FISCALE,\
			        NULL AS COD_PROVINCIA_RES,\
			        NULL AS COD_COMUNE_RES,\
			        NULL AS DESC_PROVINCIA_RES,\
			        NULL AS DESC_COMUNE_RES, PER.ID_ORIG AS CODICE_SOGGETTO \
					FROM SIT_D_PERSONA PER, \
					SIT_PROVINCIA PRO1, \
					SIT_COMUNE COM1 \
					WHERE 1 = 1 \
					AND PER.ID_EXT_PROVINCIA_NASCITA = PRO1.ID_EXT(+) \
					AND PRO1.DT_FINE_VAL IS NULL \
					AND PER.ID_EXT_COMUNE_NASCITA = COM1.ID_EXT(+) \
					AND COM1.DT_FINE_VAL IS NULL									

					
SQL_SOGG_TRIBUTI_ICI=SELECT ID AS ID_DWH, ID_EXT AS ID_STORICO, DECODE(TIP_SOGG, 'F', COG_DENOM, NULL) AS COGNOME, NOME, \
					DECODE(TIP_SOGG, 'G', COG_DENOM, NULL) AS DENOMINAZIONE, SESSO, COD_FISC, \
					DECODE (PART_IVA, '0', NULL, PART_IVA) AS PI, util.fchar_to_date(DT_NSC,'dd/mm/yyyy' ) AS DT_NASCITA, \
					TIP_SOGG AS TIPO_PERSONA, NULL AS COD_PROVINCIA_NASCITA, NULL AS COD_COMUNE_NASCITA, NULL AS DESC_PROVINCIA_NASCITA, \
					DES_COM_NSC AS DESC_COMUNE_NASCITA, DECODE(TIP_SOGG, 'G', DES_COM_RES, NULL) AS LUOGOESTERNO, \
					DECODE(TIP_SOGG, 'G', IND_RES_EXT || DECODE(NUM_CIV_EXT, NULL, '', ', ' || NUM_CIV_EXT), NULL) AS INDIRIZZOESTERNO, \
					NULL AS COD_PROVINCIA_RES, NULL AS COD_COMUNE_RES, NULL AS DESC_PROVINCIA_RES, NULL AS DESC_COMUNE_RES, \
					DT_FINE_VAL, ID_ORIG_SOGG_U CODICE_SOGGETTO \
					FROM SIT_T_ICI_SOGG 
					
SQL_SOGG_TRIBUTI_TARSU=SELECT ID AS ID_DWH, ID_EXT AS ID_STORICO, DECODE(TIP_SOGG, 'F', COG_DENOM, NULL) AS COGNOME, NOME, \
					DECODE(TIP_SOGG, 'G', COG_DENOM, NULL) AS DENOMINAZIONE, SESSO, COD_FISC, \
					DECODE (PART_IVA, '0', NULL, PART_IVA) AS PI, util.fchar_to_date(DT_NSC,'dd/mm/yyyy' ) AS DT_NASCITA, \
					TIP_SOGG AS TIPO_PERSONA, NULL AS COD_PROVINCIA_NASCITA, NULL AS COD_COMUNE_NASCITA, NULL AS DESC_PROVINCIA_NASCITA, \
					DES_COM_NSC AS DESC_COMUNE_NASCITA, DECODE(TIP_SOGG, 'G', DES_COM_RES, NULL) AS LUOGOESTERNO, \
					DECODE(TIP_SOGG, 'G', IND_RES_EXT || DECODE(NUM_CIV_EXT, NULL, '', ', ' || NUM_CIV_EXT), NULL) AS INDIRIZZOESTERNO, \
					NULL AS COD_PROVINCIA_RES, NULL AS COD_COMUNE_RES, NULL AS DESC_PROVINCIA_RES, NULL AS DESC_COMUNE_RES, \
					DT_FINE_VAL, ID_ORIG_SOGG_U CODICE_SOGGETTO \
					FROM SIT_T_TAR_SOGG					 				
							
SQL_SOGG_FORNITURE_ELETTRICHE=SELECT   ID AS ID_DWH, ID_EXT AS ID_STORICO, \
				  DT_FINE_VAL AS DT_FINE_VAL, \
                  CODICE_FISCALE AS COD_FISC, \
                  NULL AS PI, \
                  DECODE(SESSO, NULL, 'G','P') AS TIPO_PERSONA, \
                  DENOMINAZIONE AS DENOMINAZIONE, \
                  NULL AS COGNOME, \
                  NULL AS NOME, \
                  SESSO AS SESSO, \
                  UTIL.FCHAR_TO_DATE(DATA_NASCITA, 'YYYY-MM-DD') AS DT_NASCITA, \
                  COMUNE_NASCITA_SEDE AS DESC_COMUNE_NASCITA, \
                  NULL AS COD_COMUNE_NASCITA, \
                  NULL AS DESC_PROVINCIA_NASCITA, \
                  PROVINCIA_NASCITA_SEDE AS COD_PROVINCIA_NASCITA, \
                  NULL AS COD_PROVINCIA_RES, \
    			  NULL AS COD_COMUNE_RES, \
			  	  NULL AS DESC_PROVINCIA_RES, \
				  NULL AS DESC_COMUNE_RES \
				  FROM SIT_ENEL_UTENTE				  							  
											  
SQL_SOGG_GAS=SELECT   ID AS ID_DWH, ID_EXT AS ID_STORICO, \
				  DT_FINE_VAL AS DT_FINE_VAL, \
				  decode(g.TIPO_SOGGETTO,'Persona Fisica', g.CF_TITOLARE_UTENZA,NULL) AS COD_FISC, \
                  decode(g.TIPO_SOGGETTO,'Soggetto diverso da persona fisica', g.CF_TITOLARE_UTENZA,NULL)  AS PI, \
                  DECODE(g.TIPO_SOGGETTO,'Soggetto diverso da persona fisica', 'G','P') AS TIPO_PERSONA, \
                  G.RAGIONE_SOCIALE AS DENOMINAZIONE, \
                  G.COGNOME_UTENTE AS COGNOME, \
                  G.NOME_UTENTE AS NOME, \
                  G.SESSO AS SESSO, \
                  UTIL.FCHAR_TO_DATE(G.DATA_NASCITA , 'dd/mm/yyyy') AS DT_NASCITA, \
                  g.DESC_COMUNE_NASCITA AS DESC_COMUNE_NASCITA, \
                  NULL AS COD_COMUNE_NASCITA, \
                  NULL AS DESC_PROVINCIA_NASCITA,\
                  g.SIGLA_PROV_NASCITA AS COD_PROVINCIA_NASCITA, \
                  NULL AS COD_PROVINCIA_RES, \
    			  NULL AS COD_COMUNE_RES, \
			  	  NULL AS DESC_PROVINCIA_RES,\
				  NULL AS DESC_COMUNE_RES \
				  FROM SIT_U_GAS g				

				  
SQL_SOGG_CATASTO=SELECT SOG.PKID AS ID_DWH , NULL AS ID_STORICO, NULL AS DT_FINE_VAL, \
					SOG.CODI_FISC AS COD_FISC,\
					SOG.CODI_PIVA AS PI,\
					SOG.FLAG_PERS_FISICA AS TIPO_PERSONA,\
					DECODE (SOG.FLAG_PERS_FISICA,'G',SOG.RAGI_SOCI,NULL) AS DENOMINAZIONE,\
					DECODE (SOG.FLAG_PERS_FISICA,'P',SOG.RAGI_SOCI,NULL) AS COGNOME,\
					SOG.NOME AS NOME,\
					SOG.SESSO AS SESSO,\
					SOG.DATA_NASC AS DT_NASCITA,\
					IST.NOME AS DESC_COMUNE_NASCITA,\
					IST.ISTATC AS COD_COMUNE_NASCITA,\
					IST.ISTATP AS COD_PROVINCIA_NASCITA,\
					IST.DENO_PROV AS DESC_PROVINCIA_NASCITA,\
					NULL AS COD_PROVINCIA_RES,\
	                NULL AS COD_COMUNE_RES,\
	                NULL AS DESC_PROVINCIA_RES,\
	                NULL AS DESC_COMUNE_RES \
					FROM CONS_SOGG_TAB SOG,\
					SITICOMU_ISTAT IST \
					WHERE SOG.COMU_NASC = IST.CODI_FISC_LUNA (+) \
					AND NOT exists \
					(select 1 from sit_soggetto_totale where id_dwh = SOG.PKID and fk_ente_sorgente =? and prog_es=?)				  
											  				  
SQL_SOGG_LOCAZIONI=SELECT   LOC.UFFICIO || '|' || LOC.ANNO || '|' || LOC.SERIE || '|' || LOC.NUMERO || '|' || LOC.PROG_SOGGETTO AS ID_DWH, \
				  NULL AS ID_STORICO, NULL AS DT_FINE_VAL, \
				  DECODE (LENGTH (LOC.CODICEFISCALE),16, LOC.CODICEFISCALE,NULL) AS COD_FISC, \
				  DECODE (LENGTH (LOC.CODICEFISCALE),11, LOC.CODICEFISCALE,NULL) AS PI, \
				  DECODE(LOC.SESSO, 'S', 'G','P') AS TIPO_PERSONA, \
				  NULL AS DENOMINAZIONE, \
				  NULL AS COGNOME, \
				  NULL AS NOME, \
				  DECODE (LOC.SESSO, 'S', NULL, LOC.SESSO) AS SESSO, \
				  UTIL.FCHAR_TO_DATE(LOC.DATA_NASCITA, 'YYYY-MM-DD') AS DT_NASCITA, \
				  LOC.CITTA_NASCITA AS DESC_COMUNE_NASCITA, \
				  NULL AS COD_COMUNE_NASCITA, \
				  LOC.PROV_NASCITA AS DESC_PROVINCIA_NASCITA, \
				  NULL AS COD_PROVINCIA_NASCITA, \
				  NULL AS COD_PROVINCIA_RES, \
				  NULL AS COD_COMUNE_RES, \
			  	  LOC.PROV_RESIDENZA AS DESC_PROVINCIA_RES, \
				  LOC.CITTA_RESIDENZA AS DESC_COMUNE_RES \
				  FROM LOCAZIONI_B LOC \
				  WHERE  NOT exists \
                  (select 1 from sit_soggetto_totale where id_dwh = LOC.UFFICIO || '|' || LOC.ANNO || '|' || LOC.SERIE || '|' || LOC.NUMERO || '|' || LOC.PROG_SOGGETTO and fk_ente_sorgente =? and prog_es=?)				  

SQL_SOGG_CONCESSIONI=SELECT ID AS ID_DWH, ID_EXT AS ID_STORICO, DT_FINE_VAL AS DT_FINE_VAL, \
					CODICE_FISCALE AS COD_FISC, PIVA AS PI, TIPO_PERSONA AS TIPO_PERSONA, \
					DENOMINAZIONE AS DENOMINAZIONE, COGNOME AS COGNOME, NOME AS NOME, \
					NULL AS SESSO, UTIL.FCHAR_TO_DATE(DATA_NASCITA, 'YYYY-MM-DD') AS DT_NASCITA, \
					COMUNE_NASCITA AS DESC_COMUNE_NASCITA, NULL AS COD_COMUNE_NASCITA, \
					PROV_NASCITA AS DESC_PROVINCIA_NASCITA, NULL AS COD_PROVINCIA_NASCITA, \
					COMUNE_RESIDENZA AS DESC_COMUNE_RES, NULL AS COD_COMUNE_RES, \
					PROV_RESIDENZA AS DESC_PROVINCIA_RES, NULL AS COD_PROVINCIA_RES, \
					DT_FINE_VAL AS DT_FINE_VAL, ID_ORIG AS CODICE_SOGGETTO \
					FROM SIT_C_PERSONA
					
SQL_SOGG_SUCCESSIONI_DEFUNTO=SELECT PK_ID_SUCCA AS ID_DWH, NULL AS ID_STORICO, NULL AS DT_FINE_VAL, \
 					SUC.CF_DEFUNTO AS COD_FISC, \
 					NULL AS PI, \
 					'P' AS TIPO_PERSONA, \
 					NULL AS DENOMINAZIONE, \
 					SUC.COGNOME_DEFUNTO AS COGNOME, \
 					SUC.NOME_DEFUNTO AS NOME, \
 					SUC.SESSO AS SESSO, \
 					UTIL.FCHAR_TO_DATE(SUC.DATA_NASCITA, 'YYYY-MM-DD') AS DT_NASCITA, \
 					NULL AS COD_COMUNE_NASCITA, \
 					NULL AS COD_PROVINCIA_NASCITA, \
 					SUC.CITTA_NASCITA AS DESC_COMUNE_NASCITA, \
 					SUC.PROV_NASCITA AS DESC_PROVINCIA_NASCITA, \
 					NULL AS COD_PROVINCIA_RES, \
 					NULL AS COD_COMUNE_RES, \
 					SUC.CITTA_RESIDENZA AS DESC_COMUNE_RES, \
 					SUC.PROV_RESIDENZA AS DESC_PROVINCIA_RES \
 					FROM SUCCESSIONI_A SUC \
 					WHERE NOT exists \
 					(select 1 from sit_soggetto_totale where id_dwh = SUC.PK_ID_SUCCA and fk_ente_sorgente =? and prog_es=?)					
  									
SQL_SOGG_SUCCESSIONI_EREDE=SELECT PK_ID_SUCCB AS ID_DWH, NULL AS ID_STORICO, NULL AS DT_FINE_VAL, \
			     SUC.CF_EREDE AS COD_FISC, \
			     NULL AS PI,\
			     DECODE(SESSO,'S','G','P') AS TIPO_PERSONA,\
			     SUC.DENOMINAZIONE AS DENOMINAZIONE,\
			     NULL AS COGNOME, \
			     NULL AS NOME, \
			     DECODE(SUC.SESSO,'S',NULL,SUC.SESSO) AS SESSO,\
			     UTIL.FCHAR_TO_DATE(SUC.DATA_NASCITA, 'YYYY-MM-DD') AS DT_NASCITA,\
			     NULL AS COD_COMUNE_NASCITA,\
			     NULL AS COD_PROVINCIA_NASCITA,\
			     SUC.CITTA_NASCITA AS DESC_COMUNE_NASCITA,\
			     SUC.PROV_NASCITA AS DESC_PROVINCIA_NASCITA,\
			     NULL AS COD_PROVINCIA_RES,\
			     NULL AS COD_COMUNE_RES,\
			     SUC.CITTA_RESIDENZA AS DESC_COMUNE_RES,\
			     SUC.PROV_RESIDENZA AS DESC_PROVINCIA_RES \
				 FROM SUCCESSIONI_B SUC \
				 WHERE NOT exists \
				 (select 1 from sit_soggetto_totale where id_dwh = SUC.PK_ID_SUCCB and fk_ente_sorgente =? and prog_es=?)				  
  								
/*					
SQL_SOGG_SUCCESSIONI_DEFUNTO=SELECT PK_ID_SUCCA AS ID_DWH, NULL AS ID_STORICO, NULL AS DT_FINE_VAL, \
 					SUC.CF_DEFUNTO AS COD_FISC, \
 					NULL AS PI, \
 					'P' AS TIPO_PERSONA, \
 					NULL AS DENOMINAZIONE, \
 					SUC.COGNOME_DEFUNTO AS COGNOME, \
 					SUC.NOME_DEFUNTO AS NOME, \
 					SUC.SESSO AS SESSO, \
 					UTIL.FCHAR_TO_DATE(SUC.DATA_NASCITA, 'YYYY-MM-DD') AS DT_NASCITA, \
 					NULL AS COD_COMUNE_NASCITA, \
 					NULL AS COD_PROVINCIA_NASCITA, \
 					SUC.CITTA_NASCITA AS DESC_COMUNE_NASCITA, \
 					SUC.PROV_NASCITA AS DESC_PROVINCIA_NASCITA, \
 					NULL AS COD_PROVINCIA_RES, \
 					NULL AS COD_COMUNE_RES, \
 					SUC.CITTA_RESIDENZA AS DESC_COMUNE_RES, \
 					SUC.PROV_RESIDENZA AS DESC_PROVINCIA_RES \
 					FROM SUCCESSIONI_A SUC,SIT_ENTE E \
 					WHERE E.CODENT = ? \
 					AND NOT exists \
 					(select 1 from sit_soggetto_totale where id_dwh = SUC.PK_ID_SUCCA and fk_ente_sorgente =? and prog_es=?)					
  									
SQL_SOGG_SUCCESSIONI_EREDE=SELECT PK_ID_SUCCB AS ID_DWH, NULL AS ID_STORICO, NULL AS DT_FINE_VAL, \
			     SUC.CF_EREDE AS COD_FISC, \
			     NULL AS PI,\
			     DECODE(SESSO,'S','G','P') AS TIPO_PERSONA,\
			     SUC.DENOMINAZIONE AS DENOMINAZIONE,\
			     NULL AS COGNOME, \
			     NULL AS NOME, \
			     DECODE(SUC.SESSO,'S',NULL,SUC.SESSO) AS SESSO,\
			     UTIL.FCHAR_TO_DATE(SUC.DATA_NASCITA, 'YYYY-MM-DD') AS DT_NASCITA,\
			     NULL AS COD_COMUNE_NASCITA,\
			     NULL AS COD_PROVINCIA_NASCITA,\
			     SUC.CITTA_NASCITA AS DESC_COMUNE_NASCITA,\
			     SUC.PROV_NASCITA AS DESC_PROVINCIA_NASCITA,\
			     NULL AS COD_PROVINCIA_RES,\
			     NULL AS COD_COMUNE_RES,\
			     SUC.CITTA_RESIDENZA AS DESC_COMUNE_RES,\
			     SUC.PROV_RESIDENZA AS DESC_PROVINCIA_RES \
				 FROM SUCCESSIONI_B SUC,SIT_ENTE E \
				 WHERE E.CODENT = ?  \
				 AND E.DESCRIZIONE = UPPER(TRIM(SUC.CITTA_RESIDENZA )) \
				 AND NOT exists \
				 (select 1 from sit_soggetto_totale where id_dwh = SUC.PK_ID_SUCCB and fk_ente_sorgente =? and prog_es=?)				  
*/  			
SQL_SOGG_LIC_COMM=SELECT ID AS ID_DWH, ID_EXT AS ID_STORICO, DT_FINE_VAL AS DT_FINE_VAL, CODICE_FISCALE AS COD_FISC, \
					NULL AS PI, NULL AS TIPO_PERSONA, DENOMINAZIONE AS DENOMINAZIONE, NULL AS COGNOME, NULL AS NOME, \
					NULL AS SESSO, UTIL.FCHAR_TO_DATE(DATA_NASCITA, 'YYYY-MM-DD') AS DT_NASCITA, NULL AS DESC_COMUNE_NASCITA, \
					COMUNE_NASCITA AS COD_COMUNE_NASCITA, NULL AS DESC_PROVINCIA_NASCITA, PROVINCIA_NASCITA AS COD_PROVINCIA_NASCITA, \
					NULL AS DESC_PROVINCIA_RES, PROVINCIA_RESIDENZA AS COD_PROVINCIA_RES, NULL AS DESC_COMUNE_RES, \
					COMUNE_RESIDENZA AS COD_COMUNE_RES, ID_ORIG AS CODICE_SOGGETTO \
					FROM SIT_LICENZE_COMMERCIO_ANAG				 
	
SQL_SOGG_REDDITI_DICHIARANTE=SELECT IDE_TELEMATICO || '|' || CODICE_FISCALE_DIC AS ID_DWH, NULL AS ID_STORICO, \
				 NULL AS DT_FINE_VAL, CODICE_FISCALE_DIC AS COD_FISC, NULL AS PI, FLAG_PERSONA AS TIPO_PERSONA,  DENOMINAZIONE AS DENOMINAZIONE, \
				 COGNOME AS COGNOME, NOME AS NOME, SESSO AS SESSO, UTIL.FCHAR_TO_DATE(DATA_NASCITA, 'YYYY-MM-DD') AS DT_NASCITA, \
				 COMUNE_NASCITA AS COD_COMUNE_NASCITA, NULL AS COD_PROVINCIA_NASCITA, NULL AS DESC_COMUNE_NASCITA, NULL AS DESC_PROVINCIA_NASCITA, \
				 NULL AS COD_PROVINCIA_RES, NULL AS COD_COMUNE_RES, NULL AS DESC_COMUNE_RES, NULL AS DESC_PROVINCIA_RES \
				 FROM RED_DATI_ANAGRAFICI \
				 WHERE NOT exists (SELECT 1 FROM sit_soggetto_totale WHERE id_dwh = IDE_TELEMATICO || '|' ||   CODICE_FISCALE_DIC AND fk_ente_sorgente =? AND prog_es=?)	
					
SQL_SOGG_DOCFA_TECNICO=SELECT fornitura || '|' || PROTOCOLLO_REG AS ID_DWH, NULL AS ID_STORICO, NULL AS DT_FINE_VAL, DIC_COGNOME AS COGNOME, DIC_NOME NOME, NULL AS DENOMINAZIONE, \
					   NULL SESSO, NULL COD_FISC, NULL AS PI, NULL AS DT_NASCITA, NULL AS TIPO_PERSONA, NULL AS COD_PROVINCIA_NASCITA, NULL AS COD_COMUNE_NASCITA, NULL AS DESC_PROVINCIA_NASCITA, \
					   NULL AS DESC_COMUNE_NASCITA, null AS LUOGOESTERNO, NULL AS INDIRIZZOESTERNO, DIC_RES_PROV AS COD_PROVINCIA_RES, DIC_RES_CAP AS COD_COMUNE_RES, DIC_RES_PROV AS DESC_PROVINCIA_RES, \
					   DIC_RES_COM AS DESC_COMUNE_RES \
					   FROM docfa_dichiaranti \
					   WHERE NOT exists (SELECT 1 FROM sit_soggetto_totale WHERE id_dwh = fornitura || '|' || PROTOCOLLO_REG AND fk_ente_sorgente =? AND prog_es=?)					 
						
SQL_SOGG_DOCFA_DICHIARANTE=SELECT fornitura || '|' || PROTOCOLLO_REG AS ID_DWH, NULL AS ID_STORICO, NULL AS DT_FINE_VAL, TEC_COGNOME AS COGNOME, TEC_NOME NOME, NULL AS DENOMINAZIONE, NULL SESSO, TEC_CODFISC COD_FISC, \
						   decode(length(TEC_CODFISC),11,TEC_CODFISC,null) AS PI, NULL AS DT_NASCITA, NULL AS TIPO_PERSONA, NULL AS COD_PROVINCIA_NASCITA, NULL AS COD_COMUNE_NASCITA, NULL AS DESC_PROVINCIA_NASCITA, \
						   NULL AS DESC_COMUNE_NASCITA, null AS LUOGOESTERNO, NULL AS INDIRIZZOESTERNO, NULL AS COD_PROVINCIA_RES, NULL AS COD_COMUNE_RES, NULL AS DESC_PROVINCIA_RES, NULL AS DESC_COMUNE_RES \
						   FROM docfa_dichiaranti \
						   WHERE NOT exists (SELECT 1 FROM sit_soggetto_totale WHERE id_dwh = fornitura || '|' || PROTOCOLLO_REG AND fk_ente_sorgente =? AND prog_es=?)

SQL_SOGG_COMPRAVENDITE=SELECT iid AS ID_DWH, NULL AS ID_STORICO, NULL AS DT_FINE_VAL, COGNOME AS COGNOME, NOME NOME, DENOMINAZIONE AS DENOMINAZIONE, \
					   SESSO SESSO, CODICE_FISCALE COD_FISC, CODICE_FISCALE_G AS PI, DATA_NASCITA AS DT_NASCITA, tipo_soggetto AS TIPO_PERSONA, NULL AS COD_PROVINCIA_NASCITA,\
					   luogo_nascita AS COD_COMUNE_NASCITA, NULL AS DESC_PROVINCIA_NASCITA, NULL AS DESC_COMUNE_NASCITA, null AS LUOGOESTERNO, NULL AS INDIRIZZOESTERNO, null AS COD_PROVINCIA_RES, \
					   null aS COD_COMUNE_RES, null AS DESC_PROVINCIA_RES, null AS DESC_COMUNE_RES \
					   FROM mui_soggetti \
					   WHERE NOT exists (SELECT 1 FROM sit_soggetto_totale WHERE id_dwh = iid AND fk_ente_sorgente =? AND prog_es=?)
					
/*#############################################################################################################################################################################################################*/
/*########################################      Query VIE      #############################################################################################################################################*/
/*#############################################################################################################################################################################################################*/
				
SQL_VIA_FORNITURE_ELETTRICHE=select ID ID_DWH, ID_EXT ID_STORICO, DT_FINE_VAL AS DT_FINE_VAL, INDIRIZZO_UBICAZIONE INDIRIZZO \
					from sit_enel_utenza

SQL_VIA_DEMOGRAFIA=SELECT ID ID_DWH, ID_EXT ID_STORICO, DT_FINE_VAL AS DT_FINE_VAL, VIASEDIME, DESCRIZIONE, ID_ORIG CODICE_VIA \
				   FROM SIT_D_VIA				 				  	
				   
SQL_VIA_GAS=select ID ID_DWH, ID_EXT ID_STORICO, DT_FINE_VAL AS DT_FINE_VAL, INDIRIZZO_UTENZA INDIRIZZO \
					from SIT_U_GAS	
					
SQL_VIA_CATASTO=SELECT PKID_STRA AS ID_DWH, NULL AS ID_STORICO, NULL AS DT_FINE_VAL, PREFISSO,SITIDSTR.NOME,NUMERO CODICE_VIA \
				   FROM SITIDSTR, SITICOMU C \
				   WHERE SITIDSTR.cod_nazionale = C.COD_NAZIONALE \
				   AND C.CODI_FISC_LUNA = ? \
				   AND  NOT exists \
				   (select 1 from sit_via_totale where id_dwh = PKID_STRA and fk_ente_sorgente =? and prog_es=?)
				   
SQL_VIA_CATASTO2=SELECT DISTINCT ID_IMM || '|' || PROGRESSIVO || '|' || SEQ || '|' || IND.CODI_FISC_LUNA || '|' || IND.SEZIONE AS ID_DWH, NULL AS ID_STORICO, NULL AS DT_FINE_VAL \
				,t.descr PREFISSO \
				, ind.ind AS NOME \
				, COD_STRADA AS NUMERO 		FROM load_cat_uiu_ind IND , cat_d_toponimi t , siticomu c \
					WHERE c.cODI_FISC_LUNA = ? \
					AND t.pk_id = ind.toponimo \
					AND ind.codi_fisc_luna = c.codi_fisc_luna \
                                        AND SEQ =  \
                                       (SELECT MAX(SEQ) FROM load_cat_uiu_ind IND2 \
                                        WHERE IND2.ID_IMM = IND.ID_IMM \
                                        AND IND2.CODI_FISC_LUNA = IND.CODI_FISC_LUNA \
                                        AND IND2.PROGRESSIVO = IND.PROGRESSIVO) \
                                        AND PROGRESSIVO = \
                                        (SELECT MAX(PROGRESSIVO) FROM load_cat_uiu_ind IND2 \
                                        WHERE IND2.ID_IMM = IND.ID_IMM \
                                        AND IND2.CODI_FISC_LUNA = IND.CODI_FISC_LUNA \
                                        ) \
                                        AND  NOT exists \
                                        (select 1 from sit_via_totale where id_dwh = ID_IMM || '|' || PROGRESSIVO || '|' || SEQ || '|' || IND.CODI_FISC_LUNA || '|' || IND.SEZIONE and fk_ente_sorgente =? and prog_es=?)				  
                                       
SQL_VIA_LOCAZIONI=SELECT UFFICIO||'|'||ANNO||'|'||SERIE||'|'||NUMERO AS ID_DWH, NULL AS ID_STORICO, NULL AS DT_FINE_VAL, INDIRIZZO, NULL CODICE_VIA \
					FROM LOCAZIONI_A \
					WHERE  NOT exists \
					(select 1 from sit_via_totale where id_dwh = UFFICIO||'|'||ANNO||'|'||SERIE||'|'||NUMERO and fk_ente_sorgente =? and prog_es=?)				  
					
SQL_VIA_LOCAZIONI2=SELECT UFFICIO||'|'||ANNO||'|'||SERIE||'|'||NUMERO||'|'||PROG_SOGGETTO AS ID_DWH, NULL AS ID_STORICO, NULL AS DT_FINE_VAL, \
				  INDIRIZZO_RESIDENZA AS INDIRIZZO \
				  FROM LOCAZIONI_B , SIT_ENTE E \
				  WHERE E.CODENT = ? \
				  AND UPPER(TRIM(CITTA_RESIDENZA))=E.DESCRIZIONE \
				  AND NOT exists \
				  (select 1 from sit_via_totale where id_dwh = UFFICIO||'|'||ANNO||'|'||SERIE||'|'||NUMERO||'|'||PROG_SOGGETTO and fk_ente_sorgente =? and prog_es=?)
				  
SQL_VIA_TRIBUTI_ICI=SELECT DISTINCT iciv.ID AS ID_DWH, iciv.ID_EXT ID_STORICO, iciv.PREFISSO AS VIASEDIME, iciv.DESCRIZIONE AS INDIRIZZO, iciv.DT_FINE_VAL, iciv.ID_ORIG AS CODICE_VIA \
					FROM sit_t_ici_oggetto icio INNER JOIN sit_t_ici_via iciv ON (icio.ID_EXT_VIA = iciv.ID_EXT )
SQL_VIA_TRIBUTI_TARSU=SELECT DISTINCT iciv.ID AS ID_DWH, iciv.ID_EXT ID_STORICO, iciv.PREFISSO AS VIASEDIME, iciv.DESCRIZIONE INDIRIZZO, iciv.DT_FINE_VAL, iciv.ID_ORIG AS CODICE_VIA \
					FROM sit_t_ici_oggetto icio INNER JOIN sit_t_tar_via iciv ON (icio.ID_EXT_VIA = iciv.ID_EXT )
					
SQL_VIA_CONCESSIONI_PRATICA=SELECT DISTINCT ID AS ID_DWH, ID_EXT ID_STORICO, SEDIME AS VIASEDIME, INDIRIZZO AS INDIRIZZO, DT_FINE_VAL, CODICE_VIA AS CODICE_VIA FROM SIT_C_CONC_INDIRIZZI
				  
SQL_VIA_SUCCESSIONI_OGGETTO=SELECT  UFFICIO || '|' || ANNO || '|' || VOLUME || '|' || NUMERO || '|' || SOTTONUMERO || '|' || COMUNE || '|' || PROGRESSIVO || '|' || PROGRESSIVO_IMMOBILE || '|' || FORNITURA AS ID_DWH, \
							NULL AS ID_STORICO, NULL AS DT_FINE_VAL, INDIRIZZO_IMMOBILE AS INDIRIZZO \
							FROM SUCCESSIONI_C WHERE COMUNE = ? \
							AND  NOT exists \
							(select 1 from sit_via_totale where id_dwh = UFFICIO || '|' || ANNO || '|' || VOLUME || '|' || NUMERO || '|' || SOTTONUMERO || '|' || COMUNE || '|' || PROGRESSIVO || '|' || PROGRESSIVO_IMMOBILE || '|' || FORNITURA and fk_ente_sorgente =? and prog_es=?)							
                 
SQL_VIA_LIC_COMM=SELECT DISTINCT ID AS ID_DWH, ID_EXT ID_STORICO, SEDIME AS VIASEDIME, SEDIME || ' ' || INDIRIZZO AS INDIRIZZO, DT_FINE_VAL, ID_ORIG AS CODICE_VIA FROM SIT_LICENZE_COMMERCIO_VIE
				
SQL_VIA_REDDITI_DICHIARANTE=SELECT IDE_TELEMATICO || '|' || CODICE_FISCALE_DIC AS ID_DWH, NULL AS ID_STORICO, NULL AS DT_FINE_VAL, INDIRIZZO_ATTUALE AS INDIRIZZO \
				FROM RED_DOMICILIO_FISCALE \
				WHERE NOT exists (select 1 from sit_via_totale where id_dwh = IDE_TELEMATICO || '|' || CODICE_FISCALE_DIC and fk_ente_sorgente = ? and prog_es = ?)

SQL_VIA_DOCFA=SELECT FORNITURA || '|' || PROTOCOLLO_REG || '|'|| NR_PROG AS ID_DWH, NULL ID_STORICO, NULL AS DT_FINE_VAL, NULL VIASEDIME , INDIR_TOPONIMO INDIRIZZO, NULL CODICE_VIA FROM DOCFA_UIU WHERE INDIR_TOPONIMO  is not null \
			  AND NOT exists (select 1 from sit_via_totale where id_dwh = FORNITURA || '|' || PROTOCOLLO_REG || '|'|| NR_PROG and fk_ente_sorgente =? and prog_es =?)
			  
SQL_VIA_COMPRAV_SOGG=SELECT IID AS ID_DWH, NULL ID_STORICO, NULL AS DT_FINE_VAL, NULL VIASEDIME , INDIRIZZO INDIRIZZO, NULL CODICE_VIA FROM MUI_INDIRIZZI_SOG WHERE NOT exists (select 1 from sit_via_totale where id_dwh = IID and fk_ente_sorgente =? and prog_es =?)

SQL_VIA_COMPRAV_OGG=SELECT FABB.IID AS ID_DWH , NULL ID_STORICO, NULL AS DT_FINE_VAL, T.DESCR VIASEDIME , C_INDIRIZZO INDIRIZZO, NULL CODICE_VIA   FROM mui_fabbricati_identifica FABB , mui_fabbricati_info fabinfo, CAT_D_TOPONIMI T  \
				    WHERE fabinfo.IID_FORNITURA = fabb.IID_FORNITURA AND  fabinfo.iId_nota = fabb.iId_nota AND fabinfo.id_immobile = fabb.id_immobile AND T.PK_ID = fabinfo.C_TOPONIMO (+) AND NOT exists (select 1 from sit_via_totale where id_dwh = FABB.IID and fk_ente_sorgente =? and prog_es =?)
                   
/*#############################################################################################################################################################################################################*/
/*########################################      Query OGGETTI      #############################################################################################################################################*/
/*#############################################################################################################################################################################################################*/

SQL_OGG_FORNITURE_ELETTRICHE=SELECT ID ID_DWH, ID_EXT ID_STORICO, SEZIONE, FOGLIO, PARTICELLA, SUBALTERNO,DT_INIZIO_VAL dt_inizio_val, DT_FINE_VAL AS DT_FINE_VAL FROM sit_enel_utenza     

SQL_OGG_SUCCESSIONI=SELECT UFFICIO || '|' || ANNO || '|' || VOLUME || '|' || NUMERO || '|' || SOTTONUMERO || '|' || COMUNE || '|' || PROGRESSIVO || '|' || PROGRESSIVO_IMMOBILE || '|' || FORNITURA AS ID_DWH, \
					NULL AS ID_STORICO, SEZIONE, FOGLIO, PARTICELLA1, SUBALTERNO1, PARTICELLA2, SUBALTERNO2, NULL AS DT_INIZIO_VAL, NULL AS DT_FINE_VAL \
					FROM SUCCESSIONI_C \
					WHERE COMUNE = ? \
					AND UFFICIO || '|' || ANNO || '|' || VOLUME || '|' || NUMERO || '|' || SOTTONUMERO || '|' || COMUNE || '|' || PROGRESSIVO || '|' || PROGRESSIVO_IMMOBILE || '|' || FORNITURA \
					NOT IN (SELECT id_dwh FROM sit_oggetto_totale WHERE fk_ente_sorgente =? AND prog_es=?)

SQL_OGG_CONCESSIONI=SELECT ID ID_DWH, ID_EXT ID_STORICO, SEZIONE, FOGLIO, PARTICELLA, SUBALTERNO,DT_INIZIO_VAL dt_inizio_val, DT_FINE_VAL AS DT_FINE_VAL FROM SIT_C_CONCESSIONI_CATASTO

SQL_OGG_CATASTO=SELECT PKID_UIU ID_DWH, NULL ID_STORICO, id_sezc AS SEZIONE, FOGLIO, PARTICELLA, SUB AS SUBALTERNO,DATA_INIZIO_VAL dt_inizio_val, DATA_FINE_VAL AS DT_FINE_VAL \
				FROM SITICOMU, SITIUIU WHERE siticomu.COD_NAZIONALE = sitiuiu.COD_NAZIONALE \
				AND sitiuiu.COD_NAZIONALE = ? \
				AND PKID_UIU NOT IN (SELECT id_dwh FROM sit_oggetto_totale WHERE fk_ente_sorgente = ? and prog_es= ?)

SQL_OGG_TRIBUTI_ICI=SELECT ID ID_DWH, ID_EXT ID_STORICO, SEZ AS SEZIONE, FOGLIO, NUMERO AS PARTICELLA, SUB AS SUBALTERNO,DT_INIZIO_VAL dt_inizio_val, DT_FINE_VAL AS DT_FINE_VAL FROM SIT_T_ICI_OGGETTO

SQL_OGG_TRIBUTI_TARSU=SELECT ID ID_DWH, ID_EXT ID_STORICO, SEZ AS SEZIONE, FOGLIO, NUMERO AS PARTICELLA, SUB AS SUBALTERNO,DT_INIZIO_VAL dt_inizio_val, DT_FINE_VAL AS DT_FINE_VAL FROM SIT_T_TAR_OGGETTO
		  
SQL_OGG_DOCFA=SELECT  TO_CHAR(FORNITURA,'DD-MM-YYYY')||'|'||PROTOCOLLO_REG||'|'||TO_CHAR(NR_PROG) AS ID_DWH, NULL ID_STORICO, SEZIONE AS SEZIONE, FOGLIO, NUMERO AS PARTICELLA, SUBALTERNO, NULL dt_inizio_val, NULL AS DT_FINE_VAL \
			  FROM DOCFA_UIU WHERE NOT exists (SELECT 1 FROM sit_oggetto_totale WHERE id_dwh = TO_CHAR(FORNITURA,'DD-MM-YYYY') || '|' || PROTOCOLLO_REG || '|'|| NR_PROG AND fk_ente_sorgente =? AND prog_es=?)
			  
SQL_OGG_COMPRAVENDITE=SELECT FABB.IID  AS ID_DWH, NULL ID_STORICO, FABB.SEZIONE_URBANA AS SEZIONE, FABB.FOGLIO, FABB.NUMERO AS PARTICELLA, FABB.SUBALTERNO, FABINFO.CATEGORIA, FABINFO.CLASSE, RENDITA_EURO RENDITA, \
					  FABINFO.ZONA, NULL FOGLIO_URBANO, FABINFO.SUPERFICIE, T_SCALA SCALA, T_INTERNO1 INTERNO, T_PIANO1 PIANO, NULL dt_inizio_val, NULL AS DT_FINE_VAL FROM mui_fabbricati_identifica FABB , \
					  mui_fabbricati_info fabinfo WHERE fabinfo.IID_FORNITURA = fabb.IID_FORNITURA AND  fabinfo.iId_nota = fabb.iId_nota AND fabinfo.id_immobile = fabb.id_immobile \
					  AND NOT exists (SELECT 1 FROM sit_oggetto_totale WHERE id_dwh = FABB.IID AND fk_ente_sorgente =? AND prog_es=?)  
			  
/*#############################################################################################################################################################################################################*/
/*########################################      Query CIVICI    #############################################################################################################################################*/
/*#############################################################################################################################################################################################################*/

SQL_CIV_FORNITURE_ELETTRICHE=select ID ID_DWH, ID_EXT ID_STORICO, DT_FINE_VAL AS DT_FINE_VAL, INDIRIZZO_UBICAZIONE INDIRIZZO \
					from sit_enel_utenza					
					
SQL_CIV_GAS=select ID ID_DWH, ID_EXT ID_STORICO, DT_FINE_VAL AS DT_FINE_VAL, INDIRIZZO_UTENZA INDIRIZZO \
					from SIT_U_GAS					
					
SQL_CIVICO_DEMOGRAFIA=SELECT C.ID AS ID_DWH,C.ID_EXT AS ID_STORICO, C.DT_FINE_VAL AS DT_FINE_VAL, C.CIVICO_COMPOSTO,\
				   C.CIV_LIV1,C.CIV_LIV2,C.CIV_LIV3,\
				   V.ID AS ID_ORIG_VIA, C.ID_ORIG CODICE_CIVICO, V.DESCRIZIONE AS INDIRIZZO \
				   FROM SIT_D_CIVICO_V C \
				   ,SIT_D_VIA V \
				   WHERE C.ID_EXT_D_VIA = V.ID_EXT \
				   AND V.DT_FINE_VAL IS NULL				   				   
				   
SQL_CIVICO_CATASTO=SELECT V.PKID_STRA AS ID_ORIG_VIA, \
				   C.PKID_CIVI AS ID_DWH, NULL AS ID_STORICO, NULL AS DT_FINE_VAL, '<civicocomposto><att tipo=\"numero\" valore=\"'||C.CIVICO||'\"/></civicocomposto>' AS CIVICO_COMPOSTO, \
				   C.CIVICO AS CIV_LIV1, NULL AS CIV_LIV2, NULL AS CIV_LIV3 \
				   FROM SITICIVI C,SITIDSTR V \
				   WHERE C.PKID_STRA = V.PKID_STRA \
				   AND V.DATA_FINE_VAL = TO_DATE('31/12/9999','DD/MM/YYYY') \
				   AND NOT exists \
				   (select 1 from sit_civico_totale where id_dwh = C.PKID_CIVI and fk_ente_sorgente =? and prog_es=?)				  
				   
SQL_CIVICO_CATASTO2=SELECT DISTINCT  ID_IMM || '|' || PROGRESSIVO || '|' || SEQ  || '|' || IND.CODI_FISC_LUNA || '|' || IND.SEZIONE AS ID_DWH, NULL AS ID_STORICO, NULL AS DT_FINE_VAL \
				,civ1,civ2,civ3 \
				FROM load_cat_uiu_ind IND , siticomu c \
					WHERE c.cODI_FISC_LUNA = ? \
					AND ind.codi_fisc_luna = c.codi_fisc_luna \
                                        AND SEQ =  \
                                       (SELECT MAX(SEQ) FROM load_cat_uiu_ind IND2 \
                                        WHERE IND2.ID_IMM = IND.ID_IMM \
                                        AND IND2.CODI_FISC_LUNA = IND.CODI_FISC_LUNA \
                                        AND IND2.PROGRESSIVO = IND.PROGRESSIVO) \
                                        AND PROGRESSIVO = \
                                        (SELECT MAX(PROGRESSIVO) FROM load_cat_uiu_ind IND2 \
                                        WHERE IND2.ID_IMM = IND.ID_IMM \
                                        AND IND2.CODI_FISC_LUNA = IND.CODI_FISC_LUNA \
                                        ) \
                                        AND NOT exists \
                                        (select 1 from sit_civico_totale where id_dwh = ID_IMM || '|' || PROGRESSIVO || '|' || SEQ || '|' || IND.CODI_FISC_LUNA || '|' || IND.SEZIONE and fk_ente_sorgente =? and prog_es=?)				                      
                     
SQL_CIV_LOCAZIONI=SELECT UFFICIO||'|'||ANNO||'|'||SERIE||'|'||NUMERO AS ID_DWH, NULL AS ID_STORICO, NULL AS DT_FINE_VAL, INDIRIZZO \
				  FROM LOCAZIONI_A \
				  WHERE NOT exists \
				  (select 1 from sit_civico_totale where id_dwh = UFFICIO||'|'||ANNO||'|'||SERIE||'|'||NUMERO and fk_ente_sorgente =? and prog_es=?)
				  
SQL_CIV_LOCAZIONI2=SELECT UFFICIO||'|'||ANNO||'|'||SERIE||'|'||NUMERO||'|'||PROG_SOGGETTO AS ID_DWH, NULL AS ID_STORICO, NULL AS DT_FINE_VAL, CIVICO_RESIDENZA AS CIVICO \
				   FROM LOCAZIONI_B , SIT_ENTE E \
				   WHERE E.CODENT = ? \
				   AND UPPER(TRIM(CITTA_RESIDENZA))=E.DESCRIZIONE \
				   AND NOT exists \
				   (select 1 from sit_civico_totale where id_dwh = UFFICIO||'|'||ANNO||'|'||SERIE||'|'||NUMERO||'|'||PROG_SOGGETTO and fk_ente_sorgente =? and prog_es=?)
				   
SQL_CIVICO_TRIBUTI_ICI=SELECT DISTINCT iciv.ID || '|' || NVL(icio.NUM_CIV, '') || '|' || NVL(icio.ESP_CIV, '') AS ID_DWH, \
						iciv.ID_EXT ID_STORICO, icio.NUM_CIV AS CIV_LIV1, icio.ESP_CIV AS CIV_LIV2, NULL AS CIV_LIV3, iciv.ID AS ID_ORIG_VIA, \
						iciv.PREFISSO AS VIASEDIME, iciv.DESCRIZIONE INDIRIZZO, iciv.DT_FINE_VAL, iciv.ID_ORIG AS CODICE_CIVICO \
						FROM SIT_T_ICI_OGGETTO icio INNER JOIN SIT_T_ICI_VIA iciv ON (icio.ID_EXT_VIA = iciv.ID_EXT ) 
SQL_CIVICO_TRIBUTI_TARSU=SELECT DISTINCT iciv.ID || '|' || NVL(icio.NUM_CIV, '') || '|' || NVL(icio.ESP_CIV, '') AS ID_DWH, \
						iciv.ID_EXT ID_STORICO, icio.NUM_CIV AS CIV_LIV1, icio.ESP_CIV AS CIV_LIV2, NULL AS CIV_LIV3, iciv.ID AS ID_ORIG_VIA, \
						iciv.PREFISSO AS VIASEDIME, iciv.DESCRIZIONE INDIRIZZO, iciv.DT_FINE_VAL, iciv.ID_ORIG AS CODICE_CIVICO \
						FROM SIT_T_ICI_OGGETTO icio INNER JOIN SIT_T_TAR_VIA iciv ON (icio.ID_EXT_VIA = iciv.ID_EXT ) 
						
SQL_CIVICO_CONCESSIONI_PRATICA=SELECT DISTINCT ID AS ID_DWH, ID_EXT ID_STORICO, SEDIME AS VIASEDIME, INDIRIZZO AS INDIRIZZO, CIV_LIV1 AS CIV_LIV1, NULL AS CIV_LIV2, NULL AS CIV_LIV3, DT_FINE_VAL, ID AS ID_ORIG_VIA, ID_ORIG AS CODICE_CIVICO FROM SIT_C_CONC_INDIRIZZI

SQL_CIV_SUCCESSIONI_OGGETTO=SELECT  UFFICIO || '|' || ANNO || '|' || VOLUME || '|' || NUMERO || '|' || SOTTONUMERO || '|' || COMUNE || '|' || PROGRESSIVO || '|' || PROGRESSIVO_IMMOBILE || '|' || FORNITURA AS ID_DWH, \
							NULL AS ID_STORICO,NULL AS VIASEDIME, INDIRIZZO_IMMOBILE AS INDIRIZZO,NULL AS CIV_LIV1,NULL AS CIV_LIV2, NULL AS CIV_LIV3, \
							UFFICIO || '|' || ANNO || '|' || VOLUME || '|' || NUMERO || '|' || SOTTONUMERO || '|' || COMUNE || '|' || PROGRESSIVO || '|' || PROGRESSIVO_IMMOBILE || '|' || FORNITURA AS ID_ORIG_VIA, \
							NULL AS DT_FINE_VAL FROM SUCCESSIONI_C WHERE COMUNE = ? \
							AND  NOT exists \
							(select 1 from sit_civico_totale where id_dwh = UFFICIO || '|' || ANNO || '|' || VOLUME || '|' || NUMERO || '|' || SOTTONUMERO || '|' || COMUNE || '|' || PROGRESSIVO || '|' || PROGRESSIVO_IMMOBILE || '|' || FORNITURA and fk_ente_sorgente =? and prog_es=?)
                 
SQL_CIVICO_LIC_COMM=SELECT DISTINCT lcc.ID AS ID_DWH, lcc.ID_EXT AS ID_STORICO, lcc.CIVICO AS CIV_LIV1, lcc.CIVICO2 AS CIV_LIV2, lcc.CIVICO3 AS CIV_LIV3, lcv.ID AS ID_ORIG_VIA, \
					lcv.SEDIME AS VIASEDIME, lcv.INDIRIZZO AS INDIRIZZO, lcc.DT_FINE_VAL, lcc.ID_ORIG AS CODICE_CIVICO \
					FROM SIT_LICENZE_COMMERCIO lcc INNER JOIN SIT_LICENZE_COMMERCIO_VIE lcv \
					ON (lcc.ID_EXT_VIE = lcv.ID_EXT)					
				
SQL_CIVICO_REDDITI_DICHIARANTE=SELECT IDE_TELEMATICO || '|' || CODICE_FISCALE_DIC  AS ID_DWH, NULL AS ID_STORICO, NULL AS VIASEDIME, INDIRIZZO_ATTUALE AS INDIRIZZO, NULL AS CIV_LIV1,NULL AS CIV_LIV2, NULL AS CIV_LIV3, NULL AS ID_ORIG_VIA, NULL AS DT_FINE_VAL \
				   FROM RED_DOMICILIO_FISCALE \
				   WHERE NOT EXISTS (SELECT 1 FROM sit_civico_totale WHERE id_dwh = IDE_TELEMATICO || '|' || CODICE_FISCALE_DIC AND fk_ente_sorgente = ? AND prog_es = ?)
				   
SQL_CIVICO_DOCFA=SELECT  FORNITURA || '|' || PROTOCOLLO_REG || '|'|| NR_PROG AS ID_DWH, NULL ID_STORICO, NULL AS VIASEDIME, INDIR_TOPONIMO AS INDIRIZZO, INDIR_NCIV_UNO AS CIV_LIV1,  INDIR_NCIV_DUE AS CIV_LIV2,  INDIR_NCIV_TRE AS CIV_LIV3, \
				 NULL DT_FINE_VAL, FORNITURA || '|' || PROTOCOLLO_REG || '|'|| NR_PROG AS ID_ORIG_VIA, NULL AS CODICE_CIVICO FROM DOCFA_UIU WHERE INDIR_TOPONIMO  is not null AND  INDIR_NCIV_UNO is not null \
				 AND NOT EXISTS (SELECT 1 FROM sit_civico_totale WHERE id_dwh = FORNITURA || '|' || PROTOCOLLO_REG || '|'|| NR_PROG AND fk_ente_sorgente =? AND prog_es =?)				   
        
SQL_CIV_COMPRAV_SOGG=SELECT  IID AS ID_DWH, NULL ID_STORICO, NULL AS VIASEDIME, INDIRIZZO AS INDIRIZZO, NULL AS CIV_LIV1,  NULL AS CIV_LIV2,  NULL AS CIV_LIV3, NULL DT_FINE_VAL, NULL AS ID_ORIG_VIA, NULL AS CODICE_CIVICO \
					 FROM MUI_INDIRIZZI_SOG WHERE NOT EXISTS (SELECT 1 FROM sit_civico_totale WHERE id_dwh = IID AND fk_ente_sorgente =? AND prog_es =?)
SQL_CIV_COMPRAV_OGG=SELECT FABB.IID AS ID_DWH , NULL ID_STORICO, NULL AS DT_FINE_VAL, T.DESCR VIASEDIME , C_INDIRIZZO INDIRIZZO, C_CIVICO1 AS CIV_LIV1,  NULL AS CIV_LIV2,  NULL AS CIV_LIV3, NULL DT_FINE_VAL \
				    FROM mui_fabbricati_identifica FABB , mui_fabbricati_info fabinfo, CAT_D_TOPONIMI T WHERE fabinfo.IID_FORNITURA = fabb.IID_FORNITURA AND  fabinfo.iId_nota = fabb.iId_nota AND fabinfo.id_immobile = fabb.id_immobile \
				    AND T.PK_ID = fabinfo.C_TOPONIMO (+)  AND NOT EXISTS (SELECT 1 FROM sit_civico_totale WHERE id_dwh = FABB.IID AND fk_ente_sorgente =? AND prog_es =?)					 
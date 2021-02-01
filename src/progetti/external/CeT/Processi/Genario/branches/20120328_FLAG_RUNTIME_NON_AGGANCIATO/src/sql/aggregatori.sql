/*#############################################################################################################################################################################################################*/
/*########################################      Query SOGGETTI      #############################################################################################################################################*/
/*#############################################################################################################################################################################################################*/

SQL_FONTE_PROGRESSIVO_RIF_SOGG=select ID ENTE, SOGGETTI_RIFERIMENTO as PROGR from sit_ente_sorgente where SOGGETTI_RIFERIMENTO is not null and rownum = 1
SQL_RICERCA_RIGHE_SOGG=select  /*+ INDEX (sit_soggetto_totale idx_sogg_totale_fk_sogg_null)*/ id_dwh iddwh,  fk_ente_sorgente enteSorgente, ctr_hash ctrHash , cognome, nome, denominazione, sesso, codfisc codfis \
										, pi piva, dt_nascita dataNascita,\
										tipo_persona tipoPersona, cod_provincia_nascita codProvNasc, cod_comune_nascita codComNasc \
										,desc_provincia_nascita descProvNasc,desc_comune_nascita descComNasc, cod_provincia_res codProvRes \
										, cod_comune_res codComRes, desc_provincia_res descProvRes, desc_comune_res descComRes, \
										prog_es progEs, rating, rel_descr , fk_soggetto, codice_soggetto, validato \
										from sit_soggetto_totale WHERE (FK_ENTE_SORGENTE <> ? OR PROG_ES <> ?) \
										AND FLAG_RUNTIME_NON_AGGANCIATO = '0' AND RATING is null AND nvl(FK_SOGGETTO,0) = '0' AND REL_DESCR is null AND ANOMALIA is null										
SQL_CERCA_SOGGETTO_UNICO=SELECT * FROM sit_soggetto_unico WHERE id_soggetto = ?										 			 
SQL_CERCA_COD_SOGGETTO_UNICO=SELECT id_soggetto from sit_soggetto_unico where codice_soggetto = ?
SQL_LEGGI_SOGGETTO_TOTALE_FONTE_RIF=SELECT id_dwh iddwh,  fk_ente_sorgente enteSorgente, ctr_hash ctrHash , cognome, nome, denominazione, sesso, codfisc codfis \
							, pi piva, dt_nascita dataNascita, tipo_persona tipoPersona, cod_provincia_nascita codProvNasc, cod_comune_nascita codComNasc \
							,desc_provincia_nascita descProvNasc,desc_comune_nascita descComNasc, cod_provincia_res codProvRes \
							, cod_comune_res codComRes, desc_provincia_res descProvRes, desc_comune_res descComRes, \
							prog_es progEs, rating,rel_descr, fk_soggetto, codice_soggetto, validato \
							FROM sit_soggetto_totale \
							WHERE FK_ENTE_SORGENTE = ? AND PROG_ES = ? \
							AND RATING is null AND nvl(FK_SOGGETTO,0) = '0' AND REL_DESCR is null AND ANOMALIA is null
SQL_RICERCA_SOGGETTO_TOTALE_COGNOMCFDT=select id_dwh iddwh,  fk_ente_sorgente enteSorgente, ctr_hash ctrHash , cognome, nome, denominazione, sesso, codfisc codfis \
										, pi piva, dt_nascita dataNascita,\
										tipo_persona tipoPersona, cod_provincia_nascita codProvNasc, cod_comune_nascita codComNasc \
										,desc_provincia_nascita descProvNasc,desc_comune_nascita descComNasc, cod_provincia_res codProvRes \
										, cod_comune_res codComRes, desc_provincia_res descProvRes, desc_comune_res descComRes, \
										prog_es progEs, rating, FK_SOGGETTO fk_soggetto, REL_DESCR rel_Descr, attendibilita, codice_soggetto, validato from sit_soggetto_totale t \
									where t.cognome = ? \
									and t.nome = ? \
									and t.CODFISC = ?  \
									and NVL(t.DT_NASCITA,SYSDATE+1) = NVL(?,SYSDATE+2) \
										and t.DT_NASCITA BETWEEN TO_DATE('01-01-1800','DD-MM-YYYY') AND SYSDATE \
										and (fk_ente_sorgente <> ? or prog_es<>? or ctr_hash<>? or id_dwh<>?) 
SQL_RICERCA_SOGGETTO_TOTALE_COGNOMCF=select id_dwh iddwh,  fk_ente_sorgente enteSorgente, ctr_hash ctrHash , cognome, nome, denominazione, sesso, codfisc codfis \
										, pi piva, dt_nascita dataNascita,\
										tipo_persona tipoPersona, cod_provincia_nascita codProvNasc, cod_comune_nascita codComNasc \
										,desc_provincia_nascita descProvNasc,desc_comune_nascita descComNasc, cod_provincia_res codProvRes \
										, cod_comune_res codComRes, desc_provincia_res descProvRes, desc_comune_res descComRes, \
										prog_es progEs, rating, FK_SOGGETTO fk_soggetto , REL_DESCR rel_Descr, attendibilita, codice_soggetto, validato from sit_soggetto_totale t \
									where t.cognome = ? \
									and t.nome = ? \
									and t.CODFISC = ? \
									and (fk_ente_sorgente <> ? or prog_es<>? or ctr_hash<>? or id_dwh<>?) 									
SQL_RICERCA_SOGGETTO_TOTALE_DENOMCOGNOM=select id_dwh iddwh,  fk_ente_sorgente enteSorgente, ctr_hash ctrHash , cognome, nome, denominazione, sesso, codfisc codfis \
										, pi piva, dt_nascita dataNascita,\
										tipo_persona tipoPersona, cod_provincia_nascita codProvNasc, cod_comune_nascita codComNasc \
										,desc_provincia_nascita descProvNasc,desc_comune_nascita descComNasc, cod_provincia_res codProvRes \
										, cod_comune_res codComRes, desc_provincia_res descProvRes, desc_comune_res descComRes, \
										prog_es progEs, rating, FK_SOGGETTO fk_soggetto, REL_DESCR rel_Descr, attendibilita, codice_soggetto, validato from sit_soggetto_totale t \
									where t.COGNOME || ' ' || T.NOME = ? \
									AND T.CODFISC = ? \
									and (fk_ente_sorgente <> ? or prog_es<>? or ctr_hash<>? or id_dwh<>?) 									
SQL_RICERCA_SOGGETTO_TOTALE_CF=select id_dwh iddwh,  fk_ente_sorgente enteSorgente, ctr_hash ctrHash , cognome, nome, denominazione, sesso, codfisc codfis \
										, pi piva, dt_nascita dataNascita,\
										tipo_persona tipoPersona, cod_provincia_nascita codProvNasc, cod_comune_nascita codComNasc \
										,desc_provincia_nascita descProvNasc,desc_comune_nascita descComNasc, cod_provincia_res codProvRes \
										, cod_comune_res codComRes, desc_provincia_res descProvRes, desc_comune_res descComRes, \
										prog_es progEs, rating, FK_SOGGETTO fk_soggetto, REL_DESCR rel_Descr, attendibilita, codice_soggetto, validato from sit_soggetto_totale t \
									where t.CODFISC = ?  \
									and (fk_ente_sorgente <> ? or prog_es<>? or ctr_hash<>? or id_dwh<>?) 									
SQL_RICERCA_SOGGETTO_TOTALE_COGNOMDT=select id_dwh iddwh,  fk_ente_sorgente enteSorgente, ctr_hash ctrHash , cognome, nome, denominazione, sesso, codfisc codfis \
										, pi piva, dt_nascita dataNascita,\
										tipo_persona tipoPersona, cod_provincia_nascita codProvNasc, cod_comune_nascita codComNasc \
										,desc_provincia_nascita descProvNasc,desc_comune_nascita descComNasc, cod_provincia_res codProvRes \
										, cod_comune_res codComRes, desc_provincia_res descProvRes, desc_comune_res descComRes, \
										prog_es progEs, rating , FK_SOGGETTO fk_soggetto, REL_DESCR rel_Descr, attendibilita, codice_soggetto, validato from sit_soggetto_totale t \
									where t.cognome = ? \
									and t.nome = ? \
									and NVL(t.DT_NASCITA,SYSDATE+1) = NVL(?,SYSDATE+2) \
									and t.DT_NASCITA BETWEEN TO_DATE('01-01-1800','DD-MM-YYYY') AND SYSDATE \
									and (fk_ente_sorgente <> ? or prog_es<>? or ctr_hash<>? or id_dwh<>?) 									
SQL_RICERCA_SOGGETTO_TOTALE_PIVA=select id_dwh iddwh,  fk_ente_sorgente enteSorgente, ctr_hash ctrHash , cognome, nome, denominazione, sesso, codfisc codfis \
										, pi piva, dt_nascita dataNascita,\
										tipo_persona tipoPersona, cod_provincia_nascita codProvNasc, cod_comune_nascita codComNasc \
										,desc_provincia_nascita descProvNasc,desc_comune_nascita descComNasc, cod_provincia_res codProvRes \
										, cod_comune_res codComRes, desc_provincia_res descProvRes, desc_comune_res descComRes, \
										prog_es progEs, rating, FK_SOGGETTO fk_soggetto, REL_DESCR rel_Descr, attendibilita, codice_soggetto, validato from sit_soggetto_totale t \
									where t.PI = ? \
									and (fk_ente_sorgente <> ? or prog_es<>? or ctr_hash<>? or id_dwh<>?) 									
SQL_RICERCA_SOGGETTO_TOTALE_LASCA1=select id_dwh iddwh,  fk_ente_sorgente enteSorgente, ctr_hash ctrHash , cognome, nome, denominazione, sesso, codfisc codfis \
									, pi piva, dt_nascita dataNascita,\
									tipo_persona tipoPersona, cod_provincia_nascita codProvNasc, cod_comune_nascita codComNasc \
									,desc_provincia_nascita descProvNasc,desc_comune_nascita descComNasc, cod_provincia_res codProvRes \
									, cod_comune_res codComRes, desc_provincia_res descProvRes, desc_comune_res descComRes, \
									prog_es progEs, rating, FK_SOGGETTO fk_soggetto, REL_DESCR rel_Descr, attendibilita, codice_soggetto, validato from sit_soggetto_totale t \
									where t.COGNOME = ? \
									and t.NOME = ? \
									and (fk_ente_sorgente <> ? OR prog_es <> ?) 									
SQL_AGGIORNA_SOGGETTO_UNICO=UPDATE SIT_SOGGETTO_UNICO T \
							SET T.COGNOME = ?, T.NOME = ?, T.DENOMINAZIONE = ?, T.SESSO = ?, T.CODFISC = ?, \
							T.PI = ?, T.DT_NASCITA = ?, T.TIPO_PERSONA = ?, T.COD_PROVINCIA_NASCITA = ?, T.COD_COMUNE_NASCITA = ?, \
							T.DESC_PROVINCIA_NASCITA = ?, T.DESC_COMUNE_NASCITA = ?, T.COD_PROVINCIA_RES = ?, T.COD_COMUNE_RES = ?, \
							T.DESC_PROVINCIA_RES = ?, T.DESC_COMUNE_RES = ?, DT_INS = ?, T.CTRL_UTIL = ?, T.RATING = ?, \
							T.CODICE_SOGGETTO = ?, T.VALIDATO = ? \
							WHERE T.ID_SOGGETTO = ?
SQL_RICERCA_RIGHE_NON_AGGANCIATE_SOGG_RIF=select id_dwh iddwh,  fk_ente_sorgente enteSorgente, ctr_hash ctrHash , cognome, nome, denominazione, sesso, codfisc codfis \
										, pi piva, dt_nascita dataNascita,\
										tipo_persona tipoPersona, cod_provincia_nascita codProvNasc, cod_comune_nascita codComNasc \
										,desc_provincia_nascita descProvNasc,desc_comune_nascita descComNasc, cod_provincia_res codProvRes \
										, cod_comune_res codComRes, desc_provincia_res descProvRes, desc_comune_res descComRes, \
										prog_es progEs, rating,rel_descr,  fk_soggetto, codice_soggetto, validato \
										from sit_soggetto_totale WHERE nvl(FK_SOGGETTO,0) = '0' and FK_ENTE_SORGENTE = ? AND PROG_ES = ? \
										AND RATING is null AND REL_DESCR is null AND ANOMALIA is null
SQL_RICERCA_RIGHE_NON_AGGANCIATE_SOGG=select  /*+ INDEX (sit_soggetto_totale idx_sogg_totale_fk_sogg_null)*/  id_dwh iddwh,  fk_ente_sorgente enteSorgente, ctr_hash ctrHash , cognome, nome, denominazione, sesso, codfisc codfis \
										, pi piva, dt_nascita dataNascita,\
										tipo_persona tipoPersona, cod_provincia_nascita codProvNasc, cod_comune_nascita codComNasc \
										,desc_provincia_nascita descProvNasc,desc_comune_nascita descComNasc, cod_provincia_res codProvRes \
										, cod_comune_res codComRes, desc_provincia_res descProvRes, desc_comune_res descComRes, \
										prog_es progEs, rating,rel_descr,  fk_soggetto, codice_soggetto, validato \
										from sit_soggetto_totale WHERE  nvl(FK_SOGGETTO,0) = '0' and (FK_ENTE_SORGENTE <> ? OR PROG_ES <> ?) \
										AND RATING is null AND REL_DESCR is null AND ANOMALIA is null
SQL_PULISCI_SOGGETTI_UNICO=DELETE FROM sit_soggetto_unico u WHERE	NOT EXISTS (SELECT 1 FROM sit_soggetto_totale t WHERE t.fk_soggetto = u.id_soggetto) AND (u.validato <> 1 OR u.validato IS NULL)										
SQL_RESET_FLAG_RUNTIME_SOGGETTO=UPDATE sit_soggetto_totale u SET FLAG_RUNTIME_NON_AGGANCIATO = '0' WHERE	FLAG_RUNTIME_NON_AGGANCIATO = 1	

SQL_NEXT_UNICO_SOGG=select SEQ_SOGG_UNICO.nextval id from dual
SQL_INSERISCI_SOGGETTO_UNICO=INSERT INTO SIT_SOGGETTO_UNICO (CTRL_UTIL, COGNOME, NOME, DENOMINAZIONE,\
                                SESSO,CODFISC,PI,DT_NASCITA,TIPO_PERSONA,COD_PROVINCIA_NASCITA,COD_COMUNE_NASCITA,\
                                DESC_PROVINCIA_NASCITA,DESC_COMUNE_NASCITA,COD_PROVINCIA_RES,COD_COMUNE_RES,\
                                DESC_PROVINCIA_RES,DESC_COMUNE_RES,DT_INS,ID_SOGGETTO,RATING,CODICE_SOGGETTO,VALIDATO) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?)
SQL_UPDATE_SOGGETTO_TOTALE_FK=update sit_soggetto_totale tt set fk_soggetto = ?\
								, rating = ? \
								, rel_descr = ? \
								, attendibilita = ? \
								WHERE fk_ente_sorgente= ? and prog_es = ? and ctr_hash=? and id_dwh = ?
SQL_UPDATE_FLAG_RUNTIME_NON_AGGANCIATO=update sit_soggetto_totale tt set FLAG_RUNTIME_NON_AGGANCIATO = ? \
								WHERE fk_ente_sorgente= ? and prog_es = ? and ctr_hash=? and id_dwh = ?
SQL_SELECT_SOGGETTO_TOTALE_FK=SELECT FK_SOGGETTO FROM sit_soggetto_totale tt \
								WHERE fk_ente_sorgente= ? and prog_es = ? and ctr_hash=? and id_dwh = ?
SQL_UPDATE_SOGGETTO_TOTALE_FK_ALL=update sit_soggetto_totale tt set fk_soggetto = ?\
								WHERE FK_SOGGETTO = ?

									
								
/*#############################################################################################################################################################################################################*/
/*########################################      Query VIE      #############################################################################################################################################*/
/*#############################################################################################################################################################################################################*/

SQL_FONTE_PROGRESSIVO_RIF_VIE=select ID ENTE, VIE_RIFERIMENTO as PROGR from sit_ente_sorgente where VIE_RIFERIMENTO is not null and rownum = 1
SQL_INSERISCI_VIA_RIFERIMENTO=INSERT INTO SIT_VIA_UNICO (ID_VIA, INDIRIZZO, SEDIME, DT_INS, VALIDATO, CODICE_VIA) \
								select  SEQ_VIA_UNICO.nextval  , INDIRIZZO, SEDIME, DT_INS, 0, CODICE_VIA \
								from (select distinct \
								decode(t.indirizzo,null,'-',t.indirizzo) indirizzo, \
								decode(t.sedime,null,' ',t.sedime) sedime, \
								sysdate DT_INS, CODICE_VIA \
								from SIT_VIA_TOTALE t where FK_ENTE_SORGENTE=? and prog_es=? \
								AND RATING is null AND nvl(FK_VIA,0) = '0' AND REL_DESCR is null AND ANOMALIA is null)							
SQL_AGGIORNA_DATI_DEFAULT_VIA_TOT=UPDATE sit_via_totale t \
										   SET t.fk_via = \
										          (SELECT u.id_via \
										             FROM sit_via_unico u \
										            WHERE decode(t.sedime,null,' ',t.sedime) = decode(u.sedime,null,' ',u.sedime) \
										              AND decode(t.indirizzo,null,'-',t.indirizzo) = decode(u.indirizzo,null,'-',u.indirizzo) \
										              AND NVL(t.codice_via,'-')=NVL(u.codice_via,'-')), \
										              t.rating = 100, t.rel_descr = 'NATIVA' \
										     WHERE t.FK_ENTE_SORGENTE=? and t.prog_es=?
SQL_LEGGI_VIE_UNICO=select ID_VIA idvia, UPPER(INDIRIZZO) indirizzo ,UPPER(SEDIME) sedime FROM SIT_VIA_UNICO										 
SQL_LEGGI_VIA_TOTALE=select  /*+ INDEX (sit_via_totale idx_via_totale_fk_via_null)*/  distinct \
								ID_DWH iddwh,FK_ENTE_SORGENTE fkentesorgente, \
								UPPER(decode(t.indirizzo,null,'-',t.indirizzo)) indirizzo, \
								UPPER(t.sedime) sedime, \
								prog_es proges, codice_via codiceViaOrig \
								from SIT_VIA_TOTALE t where RATING is null \
								AND nvl(FK_VIA,0) = '0' AND REL_DESCR is null AND ANOMALIA is null \
								order by length(indirizzo) desc, sedime, prog_es
SQL_PULISCI_VIA_UNICO=DELETE FROM sit_via_unico u WHERE	NOT EXISTS (SELECT 1 FROM sit_via_totale t WHERE t.fk_via = u.id_via) AND (u.validato <> 1 OR u.validato IS NULL)
SQL_CERCA_VIA_UNICO=select id_via from sit_via_unico where codice_via = ?
SQL_CERCA_VIA_FONTI_DIVERSE=SELECT cod_via_unico ID_VIA FROM sit_fonti_ass_diverse WHERE \
							(cod_via =? AND fk_ente_sorgente = ? AND prog_es = ?)												
SQL_AGGIORNA_VIA_TOTALE=update SIT_VIA_TOTALE t set t.FK_VIA = ? , t.NOTE = ?, t.RATING = ?, t.REL_DESCR = ? WHERE t.id_dwh = ? and t.fk_ente_sorgente = ? and t.prog_es = ?
SQL_NEXT_UNICO=select SEQ_VIA_UNICO.nextval id from dual
SQL_NUOVO_VIA_UNICO=insert into sit_via_unico (ID_VIA,SEDIME,INDIRIZZO,DT_INS,VALIDATO) values (?,?,?,sysdate,0)


/*#############################################################################################################################################################################################################*/
/*########################################      Query CIVICI      #############################################################################################################################################*/
/*#############################################################################################################################################################################################################*/

SQL_FONTE_PROGRESSIVO_RIF_CIVICI=select ID ENTE, VIE_RIFERIMENTO as PROGR from sit_ente_sorgente where CIVICI_RIFERIMENTO is not null and rownum = 1
SQL_LEGGI_CIVICO_TOTALE_FONTE_RIF=SELECT id_dwh iddwh, fk_ente_sorgente enteSorgente, prog_es progEs, ctr_hash ctrHash, \
									fk_civico, rel_descr, rating, note, civico_composto, civ_liv1 CIVICO, id_orig_via_totale, \
									fk_via, id_storico, dt_fine_val, anomalia, validato, stato, codice_civico \
									FROM sit_civico_totale WHERE FK_ENTE_SORGENTE = ? AND PROG_ES = ? \
									AND RATING is null AND nvl(FK_CIVICO,0) = '0' AND REL_DESCR is null AND ANOMALIA is null
SQL_CERCA_COD_CIVICO_UNICO=SELECT id_civico from sit_civico_unico where codice_civico = ?									
SQL_UPDATE_CIVICO_TOTALE_FK=UPDATE sit_civico_totale tt SET fk_civico = ?, rating = ?, rel_descr = ?, attendibilita = ? WHERE fk_ente_sorgente= ? and prog_es = ? and ctr_hash=? and id_dwh = ?	
SQL_RICERCA_CIVICO_TOTALE_CIV_FKVIA=SELECT id_dwh iddwh, fk_ente_sorgente enteSorgente, prog_es progEs, ctr_hash ctrHash, fk_civico, rel_descr, rating, note, \
									civico_composto, civ_liv1 CIVICO, id_orig_via_totale, fk_via, id_storico, dt_fine_val, anomalia, validato, stato, codice_civico \
									FROM sit_civico_totale WHERE FK_VIA = ? AND CIV_LIV1 = ? \
									AND (fk_ente_sorgente <> ? OR prog_es <> ? OR ctr_hash <> ? OR id_dwh <> ?)	
SQL_SELECT_CIVICO_TOTALE_FK=SELECT FK_CIVICO FROM sit_civico_totale tt WHERE fk_ente_sorgente= ? and prog_es = ? and ctr_hash=? and id_dwh = ?
SQL_NEXT_UNICO_CIV=SELECT SEQ_CIVICO_UNICO.nextval id FROM dual
SQL_INSERISCI_CIVICO_UNICO=INSERT INTO SIT_CIVICO_UNICO (ID_CIVICO, CIVICO, FK_VIA, DT_INS, VALIDATO, CODICE_CIVICO, RATING) VALUES (?,?,?,sysdate,?,?,?)
SQL_CERCA_CIVICO_UNICO=SELECT * FROM sit_civico_unico WHERE id_civico = ?
SQL_AGGIORNA_CIVICO_UNICO=UPDATE SIT_CIVICO_UNICO T SET T.CIVICO = ?, T.FK_VIA = ?, T.VALIDATO = ?, T.CODICE_CIVICO = ?, T.RATING = ? WHERE T.ID_CIVICO = ?
SQL_UPDATE_CIVICO_TOTALE_FK_ALL=UPDATE sit_civico_totale tt set fk_civico = ? WHERE FK_CIVICO = ?
SQL_RICERCA_RIGHE_NON_AGGANCIATE_CIV_RIF=select id_dwh iddwh, fk_ente_sorgente enteSorgente, prog_es progEs, ctr_hash ctrHash, fk_civico, rel_descr, rating, note, civico_composto, \
										 civ_liv1 CIVICO, id_orig_via_totale, fk_via, id_storico, dt_fine_val, anomalia, validato, stato, codice_civico \
										 from sit_civico_totale WHERE nvl(FK_CIVICO,0) = '0' and FK_ENTE_SORGENTE = ? AND PROG_ES = ? \
										 AND RATING is null AND REL_DESCR is null AND ANOMALIA is null
SQL_RICERCA_RIGHE_NON_AGGANCIATE_CIV=select id_dwh iddwh, fk_ente_sorgente enteSorgente, prog_es progEs, ctr_hash ctrHash, fk_civico, rel_descr, rating, note, civico_composto, \
										 civ_liv1 CIVICO, id_orig_via_totale, fk_via, id_storico, dt_fine_val, anomalia, validato, stato, codice_civico \
										 from sit_civico_totale WHERE nvl(FK_CIVICO,0) = '0' and (FK_ENTE_SORGENTE <> ? OR PROG_ES <> ?) \
										 AND RATING is null AND REL_DESCR is null AND ANOMALIA is null										 
SQL_AGGIORNA_FK_VIA_CIVICI=UPDATE SIT_CIVICO_TOTALE CT SET FK_VIA = (SELECT v.fk_via FROM sit_via_totale v WHERE ct.ID_ORIG_VIA_TOTALE = v.ID_DWH \
							AND ct.fk_ente_sorgente = v.FK_ENTE_SORGENTE AND ct.PROG_ES = v.PROG_ES) WHERE fk_via IS null
SQL_AGGIORNA_CIVICI_MODIFICATI=UPDATE SIT_CIVICO_TOTALE CT SET FK_CIVICO = NULL , REL_DESCR = NULL , RATING = NULL, NOTE = NULL, ATTENDIBILITA = NULL, \
								FK_VIA = (select v.fk_via from sit_via_totale v where ct.ID_ORIG_VIA_TOTALE = v.ID_DWH \
								and ct.fk_ente_sorgente = v.FK_ENTE_SORGENTE and ct.PROG_ES = v.PROG_ES) \
								where fk_via is not null and fk_via <> (select v.fk_via	from sit_via_totale v where ct.ID_ORIG_VIA_TOTALE = v.ID_DWH \
								and ct.fk_ente_sorgente = v.FK_ENTE_SORGENTE and ct.PROG_ES = v.PROG_ES) 							
SQL_RICERCA_RIGHE_CIV=SELECT id_dwh iddwh, fk_ente_sorgente enteSorgente, prog_es progEs, ctr_hash ctrHash, \
							 fk_civico, rel_descr, rating, note, civico_composto, civ_liv1 CIVICO, id_orig_via_totale, \
							 fk_via, id_storico, dt_fine_val, anomalia, validato, stato, codice_civico \
							 FROM sit_civico_totale WHERE (FK_ENTE_SORGENTE <> ? OR PROG_ES <> ?) \
							 AND RATING is null AND nvl(FK_CIVICO,0) = '0' AND REL_DESCR is null AND ANOMALIA is null
SQL_PULISCI_CIVICO_UNICO=DELETE FROM sit_civico_unico u WHERE NOT EXISTS (SELECT 1 FROM sit_civico_totale t WHERE t.fk_civico = u.id_civico) AND (u.validato <> 1 OR u.validato IS NULL)



/*#############################################################################################################################################################################################################*/
/*########################################      Query OGGETTI      #############################################################################################################################################*/
/*#############################################################################################################################################################################################################*/

SQL_FONTE_PROGRESSIVO_RIF_OGGETTI=SELECT ID ENTE, OGGETTI_RIFERIMENTO AS PROGR FROM sit_ente_sorgente WHERE OGGETTI_RIFERIMENTO IS NOT NULL AND rownum = 1

SQL_LEGGI_OGGETTO_TOTALE_FONTE_RIF=SELECT id_dwh iddwh, fk_ente_sorgente enteSorgente, ctr_hash ctrHash, foglio, particella, sub, categoria, classe, rendita, zona, foglio_urbano, \
								   superficie, scala, interno, piano, dt_inizio_val, dt_fine_val, info, id_storico, prog_es progEs, note, rating,rel_descr, fk_oggetto, \
								   codice_oggetto, anomalia, validato, stato, sezione \
								   FROM sit_oggetto_totale \
								   WHERE FK_ENTE_SORGENTE = ?  AND PROG_ES = ? \
								   AND RATING is null AND nvl(FK_OGGETTO,0) = '0' AND REL_DESCR is null AND ANOMALIA is null

SQL_RICERCA_RIGHE_NON_AGGANCIATE_OGG_RIF=SELECT id_dwh iddwh, fk_ente_sorgente enteSorgente, ctr_hash ctrHash, foglio, particella, sub, categoria, classe, rendita, zona, foglio_urbano, \
										 superficie, scala, interno, piano, dt_inizio_val, dt_fine_val, info, id_storico, prog_es progEs, note, rating,rel_descr, fk_oggetto, \
										 codice_oggetto, anomalia, validato, stato, sezione \
										 FROM sit_oggetto_totale WHERE nvl(FK_OGGETTO,0) = '0' and FK_ENTE_SORGENTE = ? AND PROG_ES = ? \
										 AND RATING IS NULL AND REL_DESCR IS NULL AND ANOMALIA IS NULL	

SQL_RICERCA_RIGHE_NON_AGGANCIATE_OGG=SELECT id_dwh iddwh, fk_ente_sorgente enteSorgente, ctr_hash ctrHash, foglio, particella, sub, categoria, classe, rendita, zona, foglio_urbano, \
									 superficie, scala, interno, piano, dt_inizio_val, dt_fine_val, info, id_storico, prog_es progEs, note, rating,rel_descr, fk_oggetto, \
									 codice_oggetto, anomalia, validato, stato, sezione \
									 FROM sit_oggetto_totale WHERE nvl(FK_OGGETTO,0) = '0' and (FK_ENTE_SORGENTE <> ? OR PROG_ES <> ?) \
									 AND RATING is null AND REL_DESCR is null AND ANOMALIA is null										 
										 
SQL_NEXT_UNICO_OGG=SELECT SEQ_OGGETTO_UNICO.nextval id FROM dual										 
										 
SQL_INSERISCI_OGGETTO_UNICO=INSERT INTO SIT_OGGETTO_UNICO (ID_OGGETTO, FOGLIO, PARTICELLA, SUB, DT_INS, VALIDATO, CODICE_OGGETTO, SEZIONE, RATING) VALUES (?,?,?,?,sysdate,?,?,?,?)

SQL_UPDATE_OGGETTO_TOTALE_FK=UPDATE sit_oggetto_totale tt SET fk_oggetto = ?\
								, rating = ? \
								, rel_descr = ? \
								, attendibilita = ? \
								WHERE fk_ente_sorgente= ? and prog_es = ? and ctr_hash=? and id_dwh = ?

SQL_RICERCA_OGGETTO_TOTALE_FOGLIOPARTSUB=SELECT id_dwh iddwh, fk_ente_sorgente enteSorgente, ctr_hash ctrHash, foglio, particella, sub, categoria, classe, rendita, zona, foglio_urbano, \
										 superficie, scala, interno, piano, dt_inizio_val, dt_fine_val, info, id_storico, prog_es progEs, note, rating,rel_descr, fk_oggetto, \
										 codice_oggetto, anomalia, validato, stato, sezione \
										 FROM sit_oggetto_totale t \
										 WHERE t.FOGLIO = ? AND t.PARTICELLA = ? AND t.SUB = ? \
										 AND (fk_ente_sorgente <> ? OR prog_es<>? OR ctr_hash<>? OR id_dwh<>?) 								

SQL_RICERCA_OGGETTO_TOTALE_SEZFOGLIOPARTSUB=SELECT id_dwh iddwh, fk_ente_sorgente enteSorgente, ctr_hash ctrHash, foglio, particella, sub, categoria, classe, rendita, zona, foglio_urbano, \
										 superficie, scala, interno, piano, dt_inizio_val, dt_fine_val, info, id_storico, prog_es progEs, note, rating,rel_descr, fk_oggetto, \
										 codice_oggetto, anomalia, validato, stato, sezione \
										 FROM sit_oggetto_totale t \
										 WHERE t.FOGLIO = ? AND t.PARTICELLA = ? AND t.SUB = ? AND t.SEZIONE = ? \
										 AND (fk_ente_sorgente <> ? OR prog_es<>? OR ctr_hash<>? OR id_dwh<>?) 								
										 
										 
SQL_SELECT_OGGETTO_TOTALE_FK=SELECT FK_OGGETTO FROM sit_oggetto_totale tt WHERE fk_ente_sorgente= ? and prog_es = ? and ctr_hash=? and id_dwh = ?								
			
SQL_CERCA_OGGETTO_UNICO=SELECT * FROM sit_oggetto_unico WHERE id_oggetto = ?

SQL_AGGIORNA_OGGETTO_UNICO=UPDATE SIT_OGGETTO_UNICO T SET T.FOGLIO = ?, T.PARTICELLA = ?, T.SUB = ?, T.SEZIONE = ?, T.VALIDATO = ?, T.CODICE_OGGETTO = ?, T.RATING = ? WHERE T.ID_OGGETTO = ?

SQL_UPDATE_OGGETTO_TOTALE_FK_ALL=UPDATE sit_oggetto_totale tt set fk_oggetto = ? WHERE FK_OGGETTO = ?

SQL_RICERCA_RIGHE_OGG=SELECT id_dwh iddwh, fk_ente_sorgente enteSorgente, ctr_hash ctrHash, foglio, particella, sub, categoria, classe, rendita, zona, foglio_urbano, \
					  superficie, scala, interno, piano, dt_inizio_val, dt_fine_val, info, id_storico, prog_es progEs, note, rating,rel_descr, fk_oggetto, \
					  codice_oggetto, anomalia, validato, stato, sezione \
					  FROM sit_oggetto_totale WHERE (FK_ENTE_SORGENTE <> ? OR PROG_ES <> ?) \
					  AND RATING IS NULL AND nvl(FK_OGGETTO,0) = '0' AND REL_DESCR IS NULL AND ANOMALIA IS NULL		
					  
SQL_PULISCI_OGGETTI_UNICO=DELETE FROM sit_oggetto_unico u WHERE	NOT EXISTS (SELECT 1 FROM sit_oggetto_totale t WHERE t.fk_oggetto = u.id_oggetto) AND (u.validato <> 1 OR u.validato IS NULL)					  
								

/*#############################################################################################################################################################################################################*/
/*########################################      Query FABBRICATI      #########################################################################################################################################*/
/*#############################################################################################################################################################################################################*/

SQL_FONTE_PROGRESSIVO_RIF_FABBRICATI=SELECT ID ENTE, FABBRICATI_RIFERIMENTO AS PROGR FROM sit_ente_sorgente WHERE FABBRICATI_RIFERIMENTO IS NOT NULL AND rownum = 1

SQL_LEGGI_FABBRICATO_TOTALE_FONTE_RIF=SELECT id_dwh iddwh, fk_ente_sorgente enteSorgente, ctr_hash ctrHash, foglio, particella, categoria, classe, rendita, zona, foglio_urbano, \
								   superficie, scala, interno, piano, dt_inizio_val, dt_fine_val, info, id_storico, prog_es progEs, note, rating,rel_descr, fk_fabbricato, \
								   codice_fabbricato, anomalia, validato, stato, sezione \
								   FROM sit_fabbricato_totale \
								   WHERE FK_ENTE_SORGENTE = ?  AND PROG_ES = ? \
								   AND RATING is null AND nvl(FK_FABBRICATO,0) = '0' AND REL_DESCR is null AND ANOMALIA is null

SQL_RICERCA_RIGHE_NON_AGGANCIATE_FABB_RIF=SELECT id_dwh iddwh, fk_ente_sorgente enteSorgente, ctr_hash ctrHash, foglio, particella, categoria, classe, rendita, zona, foglio_urbano, \
										 superficie, scala, interno, piano, dt_inizio_val, dt_fine_val, info, id_storico, prog_es progEs, note, rating,rel_descr, fk_fabbricato, \
										 codice_fabbricato, anomalia, validato, stato, sezione \
										 FROM sit_fabbricato_totale WHERE nvl(FK_FABBRICATO,0) = '0' and FK_ENTE_SORGENTE = ? AND PROG_ES = ? \
										 AND RATING IS NULL AND REL_DESCR IS NULL AND ANOMALIA IS NULL	

SQL_RICERCA_RIGHE_NON_AGGANCIATE_FABB=SELECT id_dwh iddwh, fk_ente_sorgente enteSorgente, ctr_hash ctrHash, foglio, particella, categoria, classe, rendita, zona, foglio_urbano, \
									 superficie, scala, interno, piano, dt_inizio_val, dt_fine_val, info, id_storico, prog_es progEs, note, rating,rel_descr, fk_fabbricato, \
									 codice_fabbricato, anomalia, validato, stato, sezione \
									 FROM sit_fabbricato_totale WHERE nvl(FK_FABBRICATO,0) = '0' and (FK_ENTE_SORGENTE <> ? OR PROG_ES <> ?) \
									 AND RATING is null AND REL_DESCR is null AND ANOMALIA is null										 
										 
SQL_NEXT_UNICO_FABB=SELECT SEQ_FABBRICATO_UNICO.nextval id FROM dual										 
										 
SQL_INSERISCI_FABBRICATO_UNICO=INSERT INTO SIT_FABBRICATO_UNICO (ID_FABBRICATO, FOGLIO, PARTICELLA, DT_INS, VALIDATO, CODICE_FABBRICATO, SEZIONE, RATING) VALUES (?,?,?,sysdate,?,?,?,?)

SQL_UPDATE_FABBRICATO_TOTALE_FK=UPDATE sit_fabbricato_totale tt SET fk_fabbricato = ?\
								, rating = ? \
								, rel_descr = ? \
								, attendibilita = ? \
								WHERE fk_ente_sorgente= ? and prog_es = ? and ctr_hash=? and id_dwh = ?

SQL_RICERCA_FABBRICATO_TOTALE_FOGLIOPART=SELECT id_dwh iddwh, fk_ente_sorgente enteSorgente, ctr_hash ctrHash, foglio, particella, categoria, classe, rendita, zona, foglio_urbano, \
										 superficie, scala, interno, piano, dt_inizio_val, dt_fine_val, info, id_storico, prog_es progEs, note, rating,rel_descr, fk_fabbricato, \
										 codice_fabbricato, anomalia, validato, stato, sezione \
										 FROM sit_fabbricato_totale t \
										 WHERE t.FOGLIO = ? AND t.PARTICELLA = ? \
										 AND (fk_ente_sorgente <> ? OR prog_es<>? OR ctr_hash<>? OR id_dwh<>?) 								

SQL_RICERCA_FABBRICATO_TOTALE_SEZFOGLIOPART=SELECT id_dwh iddwh, fk_ente_sorgente enteSorgente, ctr_hash ctrHash, foglio, particella, categoria, classe, rendita, zona, foglio_urbano, \
										 superficie, scala, interno, piano, dt_inizio_val, dt_fine_val, info, id_storico, prog_es progEs, note, rating,rel_descr, fk_fabbricato, \
										 codice_fabbricato, anomalia, validato, stato, sezione \
										 FROM sit_fabbricato_totale t \
										 WHERE t.FOGLIO = ? AND t.PARTICELLA = ? AND t.SEZIONE = ? \
										 AND (fk_ente_sorgente <> ? OR prog_es<>? OR ctr_hash<>? OR id_dwh<>?) 								
										 
										 
SQL_SELECT_FABBRICATO_TOTALE_FK=SELECT FK_FABBRICATO FROM sit_fabbricato_totale tt WHERE fk_ente_sorgente= ? and prog_es = ? and ctr_hash=? and id_dwh = ?								
			
SQL_CERCA_FABBRICATO_UNICO=SELECT * FROM sit_fabbricato_unico WHERE id_fabbricato = ?

SQL_AGGIORNA_FABBRICATO_UNICO=UPDATE SIT_FABBRICATO_UNICO T SET T.FOGLIO = ?, T.PARTICELLA = ?, T.SEZIONE = ?, T.VALIDATO = ?, T.CODICE_FABBRICATO = ?, T.RATING = ? WHERE T.ID_FABBRICATO = ?

SQL_UPDATE_FABBRICATO_TOTALE_FK_ALL=UPDATE sit_fabbricato_totale tt set fk_fabbricato = ? WHERE FK_FABBRICATO = ?

SQL_RICERCA_RIGHE_FABB=SELECT id_dwh iddwh, fk_ente_sorgente enteSorgente, ctr_hash ctrHash, foglio, particella, categoria, classe, rendita, zona, foglio_urbano, \
					  superficie, scala, interno, piano, dt_inizio_val, dt_fine_val, info, id_storico, prog_es progEs, note, rating,rel_descr, fk_fabbricato, \
					  codice_fabbricato, anomalia, validato, stato, sezione \
					  FROM sit_fabbricato_totale WHERE (FK_ENTE_SORGENTE <> ? OR PROG_ES <> ?) \
					  AND RATING IS NULL AND nvl(FK_FABBRICATO,0) = '0' AND REL_DESCR IS NULL AND ANOMALIA IS NULL		
					  
SQL_PULISCI_FABBRICATI_UNICO=DELETE FROM sit_fabbricato_unico u WHERE	NOT EXISTS (SELECT 1 FROM sit_fabbricato_totale t WHERE t.fk_fabbricato = u.id_fabbricato) AND (u.validato <> 1 OR u.validato IS NULL)					  
								

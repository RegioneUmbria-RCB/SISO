package it.webred.rulengine.brick.elab.cartellaSociale;

import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.ComponenteFamigliaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.rulengine.type.def.TypeFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

public class AggiornamentoFamiglie extends Command implements Rule  {
	
	private static final Logger log = Logger.getLogger(AggiornamentoFamiglie.class.getName());
	private Connection conn=null; 
	private String enteID;
	private SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	private final String ITALIA = "ITALIA";
	private Map<String, Integer> parentelaGitCsMap;
	private final String ORIG_RELAZIONE = "SIT_D_PERS_FAM.RELAZ_PAR";
    private final String DEST_RELAZIONE = "CS_TB_TIPO_RAPPORTO_CON.ID";
	private final String USER = "'CAR-FAM-CS'";
	private final int INTESTATARIO_SCHEDA_ID = 999;
	
	public AggiornamentoFamiglie(BeanCommand bc) {
		super(bc);
	}

	public AggiornamentoFamiglie(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		log.debug("AggiornamentoFamiglie run()");
		CommandAck retAck = null; 
		enteID= ctx.getBelfiore();
		log.debug("ENTE IN ELABORAZIONE (DA CTX): " + enteID);
		conn = ctx.getConnection((String)ctx.get("connessione"));
		
		try {
			
			log.info("Database Username: " + conn.getMetaData().getUserName());
			
			this.getJellyParam(ctx);
			
			caricaMappaRelazioni();
			
			elaboraAggiornamentoFamiglie(conn);
			
			retAck = new ApplicationAck("ESECUZIONE OK");
			return retAck;
			
		}catch (SQLException e) {
			log.error("ERRORE SQL " + e, e);
			ErrorAck ea = new ErrorAck(e);
			return ea;
		}catch(Exception eg){
			log.error("ERRORE Generico " + eg, eg);
			ErrorAck ea = new ErrorAck(eg);
			return ea;
		}
	}
	
	private void caricaMappaRelazioni() throws Exception {
		PreparedStatement pst=null;
		ResultSet rs=null; 
		parentelaGitCsMap = new HashMap<String, Integer>();
		try{
			String SQL = "select COD_ORIG, COD_DEST from IDS_MAPPING_GIT2CS "
					   + "where CAMPO_ORIG= ? AND CAMPO_DEST= ? AND ENTE = ? ";
			pst = conn.prepareStatement(SQL);
			pst.setString(1, this.ORIG_RELAZIONE);
			pst.setString(2, this.DEST_RELAZIONE);
			pst.setString(3, this.enteID);
			rs = pst.executeQuery();
			
			while(rs.next()){
			    String origine = rs.getString("COD_ORIG");
			    BigDecimal destinazione = rs.getBigDecimal("COD_DEST");
			    if(destinazione!=null){
			    	parentelaGitCsMap.put(origine, destinazione.intValue());
			    	log.debug(origine+" >> "+destinazione.intValue());
			    }
			}
		
		}catch (SQLException e) {
			log.error("ERRORE SQL caricaMappaRelazioni per l'ente: " + enteID + " " + e, e);
			throw e;
		}catch(Exception eg){
			log.error("ERRORE caricaMappaRelazioni per l'ente: " + enteID + " " + eg, eg);
			throw eg;
		}finally {
			try {
				DbUtils.close(rs);
				DbUtils.close(pst);
			}
			catch (SQLException sqle) {
				log.error("ERRORE CHIUSURA OGGETTI SQL", sqle);
			}
		}
	}

	private void elaboraAggiornamentoFamiglie(Connection conn) throws Exception{
		
		long dataModifica = new Date().getTime();
		
		AnagrafeService anagrafeService = (AnagrafeService) ServiceLocator.getInstance()
				.getService("CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
			
		PreparedStatement pstSogg=null;
		ResultSet rsSogg=null; 
		
		PreparedStatement pstFamCS=null;
		ResultSet rsFamCS=null; 
		
		PreparedStatement pstCompAllCS=null;
		ResultSet rsCompAllCS=null;
		
		PreparedStatement pstCompCS=null;
		ResultSet rsCompCS=null;
		
		PreparedStatement pstUpdateCS=null;
		PreparedStatement pstUpdateFamCS=null;
		PreparedStatement pstInsertCS=null;
		PreparedStatement pstDeleteCS=null;
		
		String SQL_SOGGETTI =
				"SELECT a.cf, a.id FROM cs_a_anagrafica a, cs_a_soggetto s , cs_a_indirizzo ind, cs_a_ana_indirizzo aind " +
				"WHERE a.id = s.anagrafica_id and AIND.ID=IND.ANA_INDIRIZZO_ID and IND.SOGGETTO_ANAGRAFICA_ID=A.ID and ind.tipo_indirizzo_id='1' "+
				"and nvl(ind.data_fine_app,TO_DATE('31/12/9999','DD/MM/YYYY'))= TO_DATE('31/12/9999','DD/MM/YYYY')";
		
		String SQL_FAM_CS =
				"SELECT * FROM cs_a_famiglia_gruppo_git WHERE soggetto_anagrafica_id = :id AND (data_fine_app is null OR data_fine_app = TO_DATE('31/12/9999','DD/MM/YYYY'))";
		
		String SQL_COMPALL_CS =
				"SELECT * FROM cs_a_componente_git WHERE famiglia_gruppo_git_id = :id";
		
		String SQL_COMP_CS =
				"SELECT * FROM cs_a_componente_git WHERE UPPER(cf) = UPPER(:cf) and famiglia_gruppo_git_id = :id";
		
		String SQL_UPDATE_CS =
				"UPDATE cs_a_componente_git SET @COLUMNVALUE , USR_MOD = " +this.USER+ ", DT_MOD  = :dtMod "+
				"WHERE UPPER(cf) = UPPER(:cf) and famiglia_gruppo_git_id = :id";
		
		String SQL_UPDATEFAM_CS =
				"UPDATE cs_a_famiglia_gruppo_git SET FLG_SEGNALAZIONE = '1', USR_MOD = " +this.USER+ ", DT_MOD  = :dtMod WHERE ID = :id";
		
		String SQL_INSERT_CS =
				"INSERT INTO cs_a_componente_git (COGNOME,NOME,SESSO,CF,DATA_NASCITA,DATA_DECESSO," +
						"COMUNE_NASCITA_COD,COMUNE_NASCITA_DES,PROV_NASCITA_COD,STATO_NASCITA_COD,STATO_NASCITA_DES," +
						"INDIRIZZO_RES,NUM_CIV_RES,COM_RES_COD,COM_RES_DES,PROV_RES_COD," +
						"ID, FAMIGLIA_GRUPPO_GIT_ID, PARENTELA_ID, USER_INS, DT_INS,PARENTELA_VALIDA,CITTADINANZA,FLG_SEGNALAZIONE) " +
				"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
						"SEQ_CAR_SOCIALE.nextval,?,?,"+this.USER+",?,?,?,'1')";
		
		String SQL_DELETE_CS = 
				"DELETE FROM cs_a_componente_git WHERE id = :id";
		
		String codFisc = null;
		Integer anagraficaId = null;
		
		try {
		
			log.debug("Query SQL_SOGGETTI da eseguire:\n"+ SQL_SOGGETTI);
			pstSogg = conn.prepareStatement(SQL_SOGGETTI);
			
			log.debug("Query SQL_TUTTI_COMPONENTI_GIT da eseguire:\n"+ SQL_COMPALL_CS);
			pstCompAllCS = conn.prepareStatement(SQL_COMPALL_CS);
			
			log.debug("Query SQL_COMPONENTI_GIT da eseguire:\n"+ SQL_COMP_CS);
			pstCompCS = conn.prepareStatement(SQL_COMP_CS);
			
			log.debug("Query SQL_FAMIGLIA_GIT da eseguire:\n"+ SQL_FAM_CS);
			pstFamCS = conn.prepareStatement(SQL_FAM_CS);
			
			log.debug("Query SQL_INSERT da eseguire:\n"+ SQL_INSERT_CS);
			pstInsertCS = conn.prepareStatement(SQL_INSERT_CS);
			
			log.debug("Query SQL_DELETE da eseguire:\n"+ SQL_DELETE_CS);
			pstDeleteCS = conn.prepareStatement(SQL_DELETE_CS);
			
			log.debug("Query SQL_UPDATE_FAMIGLIA da eseguire:\n"+ SQL_UPDATEFAM_CS);
			pstUpdateFamCS = conn.prepareStatement(SQL_UPDATEFAM_CS);
			
			//Elaborazione dei soli soggetti residenti nel comune corrente (gestiti in Cartella Sociale)
			rsSogg = pstSogg.executeQuery();
			
			while(rsSogg.next()) {
				codFisc = rsSogg.getString("CF");
				anagraficaId = rsSogg.getInt("ID");
				

				//cerco i componenti git esistenti in cs
				pstFamCS.setInt(1, anagraficaId);
				rsFamCS = pstFamCS.executeQuery();
				
				boolean segnalaFamiglia = false;
				Integer famigliaId = null;
				while(rsFamCS.next()) {
					famigliaId = rsFamCS.getInt("ID");
				}
				
				if(famigliaId != null) {
					HashMap<String, Integer> compGitMap = new HashMap<String, Integer>();
					pstCompAllCS.setInt(1, famigliaId);
					rsCompAllCS = pstCompAllCS.executeQuery();
					while(rsCompAllCS.next()) {
						//creo una mappa con i componenti git esistenti
						compGitMap.put(rsCompAllCS.getString("CF"), rsCompAllCS.getInt("ID"));
					}
					
					
					RicercaSoggettoAnagrafeDTO rsaDto = new RicercaSoggettoAnagrafeDTO();
					rsaDto.setEnteId(this.enteID);
					rsaDto.setCodFis(codFisc.toUpperCase());
					log.debug("Ricerco famiglia di "+codFisc+" in anagrafe "+this.enteID);
					List<ComponenteFamigliaDTO> lista = anagrafeService.getListaCompFamigliaInfoAggiuntiveByCf(rsaDto);
										
					/* Ricerco il soggetto titolare della cartella e verifico se è intestatario della scheda anagrafica*/
					boolean isIntestatarioScheda = false;
					boolean trovato = false;
					int i=0;
					while(i<lista.size() && !trovato){
						ComponenteFamigliaDTO componenteDto = lista.get(i);
						if(componenteDto.getPersona().getCodfisc() != null && componenteDto.getPersona().getCodfisc().equalsIgnoreCase(codFisc)){
							trovato = true;
							
							Integer relazione =  getValoreRelazPar(componenteDto.getRelazPar());
							if(relazione!=null && relazione==INTESTATARIO_SCHEDA_ID) isIntestatarioScheda = true;
						}
						i++;
					}
					
					Map<String, Object> diffMap = null;
					
					for(ComponenteFamigliaDTO componente: lista) {
						SitDPersona persona = componente.getPersona();
						
						//salto il soggetto da cui provengo
						if(codFisc.equalsIgnoreCase(persona.getCodfisc()))
							continue;
						
					
						diffMap = new HashMap<String, Object>();
	
						boolean exists = false;
						log.debug("Componente Famiglia da anagrafe: "+ persona.getCodfisc());
						
						/* Devo ricercarlo tra i soggetti presenti per famiglia, altrimenti se in una famiglia è incluso 
						 * e nell'altra no se ne trova uno aggiorna quello esistente e basta! */
						pstCompCS.setString(1, persona.getCodfisc());
						pstCompCS.setInt(2, famigliaId);
						rsCompCS = pstCompCS.executeQuery();
						
						//modifica
						while(rsCompCS.next()) {
							log.debug("Componente Famiglia: "+ persona.getCodfisc() +" già presente in famiglia ["+famigliaId+"]"); 
							
							exists = true;
							compGitMap.remove(rsCompCS.getString("CF"));
						if(!compare(persona.getCognome(), rsCompCS.getString("COGNOME")))
							diffMap.put("COGNOME", replaceApici(persona.getCognome()));
						if(!compare(persona.getNome(), rsCompCS.getString("NOME")))
							diffMap.put("NOME", replaceApici(persona.getNome()));
						if(!compare(persona.getSesso(), rsCompCS.getString("SESSO")))
							diffMap.put("SESSO", persona.getSesso());
						if(!compare(persona.getCodfisc(), rsCompCS.getString("CF")))
							diffMap.put("CF", persona.getCodfisc());
						if(persona.getDataNascita() != null &&
							!persona.getDataNascita().equals(rsCompCS.getDate("DATA_NASCITA")))
							diffMap.put("DATA_NASCITA", persona.getDataNascita());
						if(persona.getDataMor() != null && !persona.getDataMor().equals(rsCompCS.getDate("DATA_DECESSO")))
							diffMap.put("DATA_DECESSO", persona.getDataMor());
						
						if(!compare(componente.getCodComNas(), rsCompCS.getString("COMUNE_NASCITA_COD")))
							diffMap.put("COMUNE_NASCITA_COD", componente.getCodComNas());
						if(!compare(componente.getDesComNas(), rsCompCS.getString("COMUNE_NASCITA_DES")))
							diffMap.put("COMUNE_NASCITA_DES", replaceApici(componente.getDesComNas()));
						if(!compare(componente.getSiglaProvNas(), rsCompCS.getString("PROV_NASCITA_COD")))
							diffMap.put("PROV_NASCITA_COD", componente.getSiglaProvNas());
						
						String codStatoOld = rsCompCS.getString("STATO_NASCITA_COD");
						if(!(ITALIA.equalsIgnoreCase(componente.getDesStatoNas()) && codStatoOld==null)){
							if(!compare(componente.getIstatStatoNas(), codStatoOld))
								diffMap.put("STATO_NASCITA_COD", componente.getIstatStatoNas());
							if(!compare(componente.getDesStatoNas(), rsCompCS.getString("STATO_NASCITA_DES")))
								diffMap.put("STATO_NASCITA_DES", replaceApici(componente.getDesStatoNas()));
						}
	
						if(!compare(componente.getIndirizzoResidenza(), rsCompCS.getString("INDIRIZZO_RES")))
							diffMap.put("INDIRIZZO_RES", replaceApici(componente.getIndirizzoResidenza()));
						if(!compare(componente.getCivicoResidenza(), rsCompCS.getString("NUM_CIV_RES")))
							diffMap.put("NUM_CIV_RES", replaceApici(componente.getCivicoResidenza()));
						if(!compare(componente.getCodComRes(), rsCompCS.getString("COM_RES_COD")))
							diffMap.put("COM_RES_COD", componente.getCodComRes());
						if(!compare(componente.getDesComRes(), rsCompCS.getString("COM_RES_DES")))
							diffMap.put("COM_RES_DES", replaceApici(componente.getDesComRes()));
						if(!compare(componente.getSiglaProvRes(), rsCompCS.getString("PROV_RES_COD")))
							diffMap.put("PROV_RES_COD", componente.getSiglaProvRes());
						
						Integer tipoRapportoId = getValoreRelazPar(componente.getRelazPar());
						BigDecimal comParId = rsCompCS.getBigDecimal("PARENTELA_ID");
						if(!compare(tipoRapportoId, comParId!=null ? new Integer(comParId.intValue()) : null))
							diffMap.put("PARENTELA_ID", tipoRapportoId);
						
						boolean parValida = rsCompCS.getBoolean("PARENTELA_VALIDA");
						if(isIntestatarioScheda != parValida)
							diffMap.put("PARENTELA_VALIDA", isIntestatarioScheda);
						
						if(!compare(componente.getCittadinanza(), rsCompCS.getString("CITTADINANZA")))
							diffMap.put("CITTADINANZA", componente.getCittadinanza());
						
						if(!diffMap.isEmpty()) {
							String columnValue = "";
							Iterator<Map.Entry<String, Object>> it = diffMap.entrySet().iterator();
							while (it.hasNext()) {
							  Map.Entry<String, Object> entry = it.next();
							  if(entry.getValue() instanceof Integer)
								  columnValue += entry.getKey() + " = " + (entry.getValue() != null? entry.getValue(): "NULL") + ",";
							  if(entry.getValue() instanceof String)
								  columnValue += entry.getKey() + " = '" + (entry.getValue() != null? entry.getValue(): "") + "',";
							  if(entry.getValue() instanceof Boolean)
								  columnValue += entry.getKey() + " = " + ( ((Boolean)entry.getValue())? " 1 " : " 0 ")+ ",";
							  if(entry.getValue() instanceof Date) {
								  if(entry.getValue() != null) {
									  String data = SDF.format(entry.getValue());
								  	  columnValue = columnValue + entry.getKey() + " = TO_DATE('" + data + "','DD/MM/YYYY'),";
								  } else columnValue += entry.getKey() + " = NULL,";
							  }
							}
							
						    columnValue += " FLG_SEGNALAZIONE = '1'";		
							
							String sqlUpdate = SQL_UPDATE_CS.replace("@COLUMNVALUE", columnValue);
							log.debug("Query SQL_UPDATE_COMPONENTE da eseguire:\n"+ sqlUpdate);
							pstUpdateCS = conn.prepareStatement(sqlUpdate);
							pstUpdateCS.setDate(1, new java.sql.Date(dataModifica));
							pstUpdateCS.setString(2, persona.getCodfisc());
							pstUpdateCS.setInt(3, famigliaId);
							pstUpdateCS.executeUpdate();
							
							//Max cursori aperti
							try { DbUtils.close(pstUpdateCS);} 
							catch(SQLException e){ log.warn("Errore chiusura pstUpdateCS "+ e.getMessage());}
							
							segnalaFamiglia = true;
						}
								
						}
						
						//insert
						if(!exists) {
							log.debug("Componente Famiglia: "+ persona.getCodfisc() +" non presente in famiglia ["+famigliaId+"] --> INSERISCO "); 
							if(famigliaId != null) {
								pstInsertCS.setString(1, replaceApici(persona.getCognome()));
								pstInsertCS.setString(2, replaceApici(persona.getNome()));
								pstInsertCS.setString(3, persona.getSesso());
								pstInsertCS.setString(4, persona.getCodfisc());
								if(persona.getDataNascita() != null)
									pstInsertCS.setDate(5, new java.sql.Date(persona.getDataNascita().getTime()));
								else pstInsertCS.setDate(5, null);
								if(persona.getDataMor() != null)
									pstInsertCS.setDate(6, new java.sql.Date(persona.getDataMor().getTime()));
								else pstInsertCS.setDate(6, null);
								
								pstInsertCS.setString(7, componente.getCodComNas());
								pstInsertCS.setString(8, replaceApici(componente.getDesComNas()));
								pstInsertCS.setString(9, componente.getSiglaProvNas());
								pstInsertCS.setString(10, !ITALIA.equalsIgnoreCase(componente.getDesStatoNas()) ? componente.getIstatStatoNas() : null);
								pstInsertCS.setString(11, !ITALIA.equalsIgnoreCase(componente.getDesStatoNas()) ? replaceApici(componente.getDesStatoNas()) : null);
								
								pstInsertCS.setString(12, replaceApici(componente.getIndirizzoResidenza()));
								pstInsertCS.setString(13, replaceApici(componente.getCivicoResidenza()));
								pstInsertCS.setString(14, componente.getCodComRes());
								pstInsertCS.setString(15, replaceApici(componente.getDesComRes()));
								pstInsertCS.setString(16, componente.getSiglaProvRes());
								
								pstInsertCS.setInt(17, famigliaId);
								Integer tipoRapportoId = getValoreRelazPar(componente.getRelazPar());
								if(tipoRapportoId != null) pstInsertCS.setInt(18, tipoRapportoId);
								else pstInsertCS.setNull(18, java.sql.Types.INTEGER);
								
								pstInsertCS.setDate(19, new java.sql.Date(dataModifica));
								pstInsertCS.setInt(20, isIntestatarioScheda ? 1 : 0);
								pstInsertCS.setString(21,componente.getCittadinanza());
								
								
								pstInsertCS.executeUpdate();
								
								segnalaFamiglia = true;
							}
							
						}
					}
						
					//Se qualche elemento è nell'hashmap significa che è presente in cs, ma non in git, quindi lo elimino
					for (Map.Entry<String, Integer> cursor : compGitMap.entrySet()) {
						pstDeleteCS.setInt(1, cursor.getValue());
						pstDeleteCS.executeUpdate();
						log.debug("Rimosso componente "+cursor.getKey()+" da famiglia ["+famigliaId+"]");
					}
					
					//se ho eseguito un update o un inserimento aggiorno anche il flag della famiglia
					if(segnalaFamiglia && famigliaId != null) {
						pstUpdateFamCS.setDate(1, new java.sql.Date(dataModifica));
						pstUpdateFamCS.setInt(2, famigliaId);
						pstUpdateFamCS.executeUpdate();
					}
					
				} else log.info("ATTENZIONE: SOGGETTO: " + codFisc + " non ha un dato in CS_A_FAMIGLIA_GRUPPO_GIT");
			}
			
		}catch (SQLException e) {
			log.error("ERRORE SQL elaboraAggiornamentoFamiglie al CODFISC: " + codFisc + " " + e, e);
			throw e;
		}catch(Exception eg){
			log.error("ERRORE elaboraAggiornamentoFamiglie al CODFISC: " + codFisc + " " + eg, eg);
			throw eg;
		}finally {
			try {
				DbUtils.close(rsSogg);
				DbUtils.close(pstSogg);
				
				DbUtils.close(rsCompAllCS);
				DbUtils.close(pstCompAllCS);
				
				DbUtils.close(rsCompCS);
				DbUtils.close(pstCompCS);
				
				DbUtils.close(rsFamCS);
				DbUtils.close(pstFamCS);
				
				//DbUtils.close(pstUpdateCS);
				DbUtils.close(pstUpdateFamCS);
				DbUtils.close(pstInsertCS);
			}
			catch (SQLException sqle) {
				log.error("ERRORE CHIUSURA OGGETTI SQL", sqle);
			}
		}
	}
	
	public static boolean compare(String str1, String str2) {
	    return (str1 == null ? str2 == null : str1.equals(str2));
	}
	
	public static boolean compare(Integer str1, Integer str2) {
	    boolean uguali = (str1 == null ? str2 == null : str1.equals(str2));
	    
	    //log.info(str1+" e "+ str2+ " >>" +uguali);
	    
	    return uguali;
	}
	
	private void getJellyParam(Context ctx) throws Exception {
		try {
			
			int index = 1;
			log.info("**************************************************************rengine.rule.param.in."+index+".descr");
			
			
		
		}catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			throw e;
		}
	}

	private String getJellyParam(Context ctx, int index, String desc) throws Exception{
		
		String variabile = "";
		
		log.info("rengine.rule.param.in."+index+".descr");
		
		ComplexParam paramSql= (ComplexParam) ctx.get(_jrulecfg.getProperty("rengine.rule.param.in."+index+".descr"));
		
		HashMap<String,ComplexParamP> p = paramSql.getParams();
		Set set = p.entrySet();
		Iterator it = set.iterator();
		int i = 1;
		while (it.hasNext()) {
			Entry entry = (Entry)it.next();
			ComplexParamP cpp = (ComplexParamP)entry.getValue();
			Object o = TypeFactory.getType(cpp.getValore(),cpp.getType());
			variabile = o.toString();
		}
		
		log.info("Query "+desc+" da eseguire:"+ variabile);
		
		return variabile;
	}
	
	private Integer getValoreRelazPar(String s){
		Integer tipoRapportoId = null;
		if(s!=null){
			tipoRapportoId = parentelaGitCsMap.get(s.toUpperCase());
		if(tipoRapportoId==null)
			log.warn("Nessun valore trovato per "+s);
		}
		return tipoRapportoId;
	}
	
	private String replaceApici(String s){
		if(s!=null)
			return s.replaceAll("'", "''");
		else
			return s;
	}
	
}

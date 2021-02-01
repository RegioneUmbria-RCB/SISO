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
	
	private void elaboraAggiornamentoFamiglie(Connection conn) throws Exception{
		
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
				"SELECT * FROM cs_a_componente_git WHERE UPPER(cf) = UPPER(:cf)";
		
		String SQL_UPDATE_CS =
				"UPDATE cs_a_componente_git SET @COLUMNVALUE WHERE UPPER(cf) = UPPER(:cf)";
		
		String SQL_UPDATEFAM_CS =
				"UPDATE cs_a_famiglia_gruppo_git SET FLG_SEGNALAZIONE = '1' WHERE ID = :id";
		
		String SQL_INSERT_CS =
				"INSERT INTO cs_a_componente_git (COGNOME,NOME,SESSO,CF,DATA_NASCITA,DATA_DECESSO," +
						"COMUNE_NASCITA_COD,COMUNE_NASCITA_DES,PROV_NASCITA_COD,STATO_NASCITA_COD,STATO_NASCITA_DES," +
						"INDIRIZZO_RES,NUM_CIV_RES,COM_RES_COD,COM_RES_DES,PROV_RES_COD," +
						"ID, FAMIGLIA_GRUPPO_GIT_ID, PARENTELA_ID, USER_INS, DT_INS,FLG_SEGNALAZIONE) " +
				"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
						"SEQ_CAR_SOCIALE.nextval,?,?,'Controller',?,'1')";
		
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
					List<ComponenteFamigliaDTO> lista = anagrafeService.getListaCompFamigliaInfoAggiuntiveByCf(rsaDto);
										
					Map<String, Object> diffMap = null;
					
					for(ComponenteFamigliaDTO componente: lista) {
						SitDPersona persona = componente.getPersona();
						//salto il soggetto da cui provengo
						if(codFisc.equalsIgnoreCase(persona.getCodfisc()))
							continue;
					
						diffMap = new HashMap<String, Object>();
	
						boolean exists = false;
						log.debug("Componente Famiglia: "+ persona.getCodfisc());
						pstCompCS.setString(1, persona.getCodfisc());
						rsCompCS = pstCompCS.executeQuery();
						
						//modifica
						while(rsCompCS.next()) {
						
							exists = true;
							compGitMap.remove(rsCompCS.getString("CF"));
						if(!compare(componente.getPersona().getCognome(), rsCompCS.getString("COGNOME")))
							diffMap.put("COGNOME", componente.getPersona().getCognome());
						if(!compare(componente.getPersona().getNome(), rsCompCS.getString("NOME")))
							diffMap.put("NOME", componente.getPersona().getNome());
						if(!compare(componente.getPersona().getSesso(), rsCompCS.getString("SESSO")))
							diffMap.put("SESSO", componente.getPersona().getSesso());
						if(!compare(componente.getPersona().getCodfisc(), rsCompCS.getString("CF")))
							diffMap.put("CF", componente.getPersona().getCodfisc());
						if(componente.getPersona().getDataNascita() != null &&
							!componente.getPersona().getDataNascita().equals(rsCompCS.getDate("DATA_NASCITA")))
							diffMap.put("DATA_NASCITA", componente.getPersona().getDataNascita());
						if(componente.getPersona().getDataMor() != null &&
							!componente.getPersona().getDataMor().equals(rsCompCS.getDate("DATA_DECESSO")))
							diffMap.put("DATA_DECESSO", componente.getPersona().getDataMor());
						
						if(!compare(componente.getCodComNas(), rsCompCS.getString("COMUNE_NASCITA_COD")))
							diffMap.put("COMUNE_NASCITA_COD", componente.getCodComNas());
						if(!compare(componente.getDesComNas(), rsCompCS.getString("COMUNE_NASCITA_DES")))
							diffMap.put("COMUNE_NASCITA_DES", componente.getDesComNas());
						if(!compare(componente.getSiglaProvNas(), rsCompCS.getString("PROV_NASCITA_COD")))
							diffMap.put("PROV_NASCITA_COD", componente.getSiglaProvNas());
						
						String codStatoOld = rsCompCS.getString("STATO_NASCITA_COD");
						if(!(ITALIA.equalsIgnoreCase(componente.getDesStatoNas()) && codStatoOld==null)){
							if(!compare(componente.getIstatStatoNas(), codStatoOld))
								diffMap.put("STATO_NASCITA_COD", componente.getIstatStatoNas());
							if(!compare(componente.getDesStatoNas(), rsCompCS.getString("STATO_NASCITA_DES")))
								diffMap.put("STATO_NASCITA_DES", componente.getDesStatoNas());
						}
	
						if(!compare(componente.getIndirizzoResidenza(), rsCompCS.getString("INDIRIZZO_RES")))
							diffMap.put("INDIRIZZO_RES", componente.getIndirizzoResidenza());
						if(!compare(componente.getCivicoResidenza(), rsCompCS.getString("NUM_CIV_RES")))
							diffMap.put("NUM_CIV_RES", componente.getCivicoResidenza());
						if(!compare(componente.getCodComRes(), rsCompCS.getString("COM_RES_COD")))
							diffMap.put("COM_RES_COD", componente.getCodComRes());
						if(!compare(componente.getDesComRes(), rsCompCS.getString("COM_RES_DES")))
							diffMap.put("COM_RES_DES", componente.getDesComRes());
						if(!compare(componente.getSiglaProvRes(), rsCompCS.getString("PROV_RES_COD")))
							diffMap.put("PROV_RES_COD", componente.getSiglaProvRes());
						
						if(!diffMap.isEmpty()) {
							String columnValue = "";
							Iterator<Map.Entry<String, Object>> it = diffMap.entrySet().iterator();
							while (it.hasNext()) {
							  Map.Entry<String, Object> entry = it.next();
							  if(entry.getValue() instanceof String)
								  columnValue += entry.getKey() + " = '" + (entry.getValue() != null? entry.getValue(): "") + "',";
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
							pstUpdateCS.setString(1, componente.getPersona().getCodfisc());
							pstUpdateCS.executeUpdate();
							
							segnalaFamiglia = true;
						}
						
						}
						
						//insert
						if(!exists) {
							
							if(famigliaId != null) {
								pstInsertCS.setString(1, componente.getPersona().getCognome());
								pstInsertCS.setString(2, componente.getPersona().getNome());
								pstInsertCS.setString(3, componente.getPersona().getSesso());
								pstInsertCS.setString(4, componente.getPersona().getCodfisc());
								if(componente.getPersona().getDataNascita() != null)
									pstInsertCS.setDate(5, new java.sql.Date(componente.getPersona().getDataNascita().getTime()));
								else pstInsertCS.setDate(5, null);
								if(componente.getPersona().getDataMor() != null)
									pstInsertCS.setDate(6, new java.sql.Date(componente.getPersona().getDataMor().getTime()));
								else pstInsertCS.setDate(6, null);
								
								pstInsertCS.setString(7, componente.getCodComNas());
								pstInsertCS.setString(8, componente.getDesComNas());
								pstInsertCS.setString(9, componente.getSiglaProvNas());
								pstInsertCS.setString(10, !ITALIA.equalsIgnoreCase(componente.getDesStatoNas()) ? componente.getIstatStatoNas() : null);
								pstInsertCS.setString(11, !ITALIA.equalsIgnoreCase(componente.getDesStatoNas()) ? componente.getDesStatoNas() : null);
								
								pstInsertCS.setString(12, componente.getIndirizzoResidenza());
								pstInsertCS.setString(13, componente.getCivicoResidenza());
								pstInsertCS.setString(14, componente.getCodComRes());
								pstInsertCS.setString(15, componente.getDesComRes());
								pstInsertCS.setString(16, componente.getSiglaProvRes());
								
								pstInsertCS.setInt(17, famigliaId);
								Integer tipoRapportoId = componente.getRelazPar()!=null ?  parentelaGitCsMap.get(componente.getRelazPar().toUpperCase()) : null;
								if(tipoRapportoId != null)
									pstInsertCS.setInt(18, tipoRapportoId);
								else pstInsertCS.setNull(18, java.sql.Types.INTEGER);
								pstInsertCS.setDate(19, new java.sql.Date(new Date().getTime()));
								
								pstInsertCS.executeUpdate();
								
								segnalaFamiglia = true;
							}
							
						}
					}
						
					//se qualche elemento è nell'hashmap significa che è presente in cs, ma non in git, quindi lo elimino
					for (Map.Entry<String, Integer> cursor : compGitMap.entrySet()) {
						pstDeleteCS.setInt(1, cursor.getValue());
						pstDeleteCS.executeUpdate();
					}
					
					//se ho eseguito un update o un inserimento aggiorno anche il flag della famiglia
					if(segnalaFamiglia && famigliaId != null) {
						pstUpdateFamCS.setInt(1, famigliaId);
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
				
				DbUtils.close(pstUpdateCS);
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
	
	private static final Map<String, Integer> parentelaGitCsMap = new HashMap<String, Integer>();
    static {
    	parentelaGitCsMap.put("CGN", 3);
    	parentelaGitCsMap.put("CUG", 6);
    	parentelaGitCsMap.put("AFFILIATA", 6);
    	parentelaGitCsMap.put("AFFILIATO", 6);
    	parentelaGitCsMap.put("CV", 7);
    	parentelaGitCsMap.put("MC", 7);
    	parentelaGitCsMap.put("FG", 9);
    	parentelaGitCsMap.put("FIGLIASTRO", 11);
    	parentelaGitCsMap.put("FIGLIASTRA", 11);
    	parentelaGitCsMap.put("FR", 13);
    	parentelaGitCsMap.put("FRATELLASTRO", 14);
    	parentelaGitCsMap.put("GE", 15);
    	parentelaGitCsMap.put("MD", 16);
    	parentelaGitCsMap.put("MA", 19);
    	parentelaGitCsMap.put("MATRIGNA", 20);
    	parentelaGitCsMap.put("PATRIGNO", 20);
    	parentelaGitCsMap.put("MG", 21);
    	parentelaGitCsMap.put("NP", 22);
    	parentelaGitCsMap.put("PRONIPOTE", 22);
    	parentelaGitCsMap.put("NA", 23);
    	parentelaGitCsMap.put("NO", 24);
    	parentelaGitCsMap.put("BISNONNA", 23);
    	parentelaGitCsMap.put("BISNONNO", 24);
    	parentelaGitCsMap.put("NU", 25);
    	parentelaGitCsMap.put("PD", 26);
    	parentelaGitCsMap.put("SO", 29);
    	parentelaGitCsMap.put("SORELLASTRA", 30);
    	parentelaGitCsMap.put("SC", 31);
    	parentelaGitCsMap.put("ZA", 34);
    	parentelaGitCsMap.put("ZI", 34);
    }

}

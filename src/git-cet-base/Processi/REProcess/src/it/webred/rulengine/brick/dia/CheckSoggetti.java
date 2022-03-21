package it.webred.rulengine.brick.dia;

import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.ario.GestioneStringheVie;
import it.webred.rulengine.ario.GestioneStringheVie;
import it.webred.rulengine.ario.bean.Civico;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.dia.bean.SoggettoCorrenteToCheck;
import it.webred.rulengine.brick.superc.dia.AbstractDiagnosticsExport;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.DateFormat;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.Logger;

/**
 * Valuta i soggetti contenuti nella tabella SIT_CHECK_SOGGETTI
 * per la ricerca di agganci ed eventuali altre informazioni
 * 
 * @author marcoric
 *
 */
public class CheckSoggetti extends AbstractDiagnosticsExport implements Rule {

	private static final org.apache.log4j.Logger log = Logger.getLogger(CheckSoggetti.class.getName());

	
	public CheckSoggetti(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	@Override
	public CommandAck run(Context ctx) throws CommandException {
		Connection conn = null;
		
		try {
			log.debug("Recupero parametro da contesto");
			ctx.getConnection((String)ctx.get("connessione"));
			
			//recupero eventuali parametri di ingresso
			PreparedStatement pstmt = null;
			List<RAbNormal> abnormals = new ArrayList<RAbNormal>();
			ResultSet rs = null;
			CallableStatement cs =null;

		try {
			String sql = "select C.* from SIT_SOGGETTI_TO_CHECK C ";
			
			cs = conn.prepareCall(sql, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
			CallableStatement cs1 = null;
			rs = cs.executeQuery();
			

			boolean primorecord=true;
			String tab = null;
			while (rs.next()) {
				BigDecimal idToCheck =rs.getBigDecimal("ID");
				String prefissoViaToCheck = rs.getString("PREFISSO_VIA");
				String indirizzoToCheck = rs.getString("VIA");
				String civicoToCheck = rs.getString("CIVICO");
				String denominazioneToCheck =  rs.getString("DENOMINAZIONE");
					
				if("FERNANDO MIRISAGE ANNE".equals(denominazioneToCheck))
					denominazioneToCheck = denominazioneToCheck;
	        	   
				// sono i dati del soggetto che devo trovare
				String sql1 = "select DISTINCT T.FK_SOGGETTO from SIT_SOGGETTO_TOTALE T " 
					+						" WHERE (? = T.DENOMINAZIONE"
					+						" OR ? = T.COGNOME || ' ' || T.NOME" 
					+		"				  )";


				LinkedHashMap<String, String> dati = new LinkedHashMap<String, String>();
				cs1 = conn.prepareCall(sql1, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
				cs1.setString(1,denominazioneToCheck );
				cs1.setString(2,denominazioneToCheck );
				ResultSet rs1 = null;
				rs1 = cs1.executeQuery();
				try {
				int numVolteTtrovatoInAnagrafe = 0;
				boolean trovatoSicuramente=false;
	            String note =null;
    		    String cf =null;
        	    String cognome =null;
        	    String nome =null;
        	    String denominazione =null;
	            Timestamp dataNascita =null;
	            Timestamp dataMor = null;
			    String indirizzoAnagrafico=null;
			    String prefissoAnagrafico=null;
			    String civicoAnagrafico=null;
         	    String indirizzoTarsu="";
        	    String indirizzoDichiaratoTarsu="";
        	    String annoImposta = null;
        	    String valore = null;
				float percAttendibilitaIndirizzo = 0;

        	    boolean  presenteNegliArchivi=false;
        	    SoggettoCorrenteToCheck ultimoSoggett = new SoggettoCorrenteToCheck();
        	    
				while (rs1.next()) {
						presenteNegliArchivi = true;
						boolean trovatoInAnagrafe=false;
						boolean trovatoIndirizzoAttendibile=false;
						boolean trovatoViaAttendibile=false;

			            String note1 =null;
			            String cf1 =null;
			            String cognome1 =null;
			            String nome1 =null;
			            String denominazione1 =null;
			            Timestamp dataNascita1 =null;
			            Timestamp dataMor1 = null;
			           
			            String indirizzoAnagrafico1=null;
			            String prefissoAnagrafico1=null;
			            String civicoAnagrafico1=null;
		        	    String descrizioneRreddito1=null;
		        	    String annoImposta1 = null;
		        	    String valore1 = null;
		        	    String indirizzoTarsu1="";
		        	    String indirizzoDichiaratoTarsu1="";
		        	    
			            String fkSoggetto = rs1.getString("FK_SOGGETTO");
					
			            try {
			           
				           String idDwhAna = null;
	
				           try {
				        	    MapListHandler h = new MapListHandler();
					            String sqlAna = "select * from sit_soggetto_totale where FK_SOGGETTO = ? and FK_ENTE_SORGENTE  = 1 AND PROG_ES = 1";
								QueryRunner run = new QueryRunner();
								Object[] params = new Object[] {fkSoggetto};
					            List<Map> result = new ArrayList<Map>();
					            result = (List<Map>) run.query(conn, sqlAna,params, h);
					            if (result!=null && !result.isEmpty())  {
					            	note1 = null;
				            		numVolteTtrovatoInAnagrafe+=result.size();
				            		trovatoInAnagrafe = true;
					            }
					            
					            if ((result==null || result.isEmpty())) {
					            	if (numVolteTtrovatoInAnagrafe==0)
					            		note1 = "SOGGETTO NON TROVATO IN ANAGRAFE";
					            	continue;
					            } 
					            
					            // PER OGNI SOGGETTO ANAGRAFICO TROVATO 
					            // POSSO ANDARE A VERIFICARE L'INDIRIZZO
					            for (Iterator iterator = result.iterator(); iterator.hasNext();) {
									Map map = (Map) iterator.next();
					            	idDwhAna = (String)map.get("ID_DWH");
	
								           try {
								        	    ResultSetHandler h1 = new MapHandler();
									            String sqlAnaDWH = "select * from sit_d_persona where id_ext = ? and dt_fine_val is null";
												QueryRunner run1 = new QueryRunner();
												Object[] params1 = new Object[] {idDwhAna};
												Map result1 = new HashMap();
									            result1 = (Map) run1.query(conn, sqlAnaDWH,params1, h1);
									            if (result1!=null) {
										            cf1 = (String) result1.get("CODFISC");
										            cognome1 = (String) result1.get("COGNOME");
										            nome1 = (String) result1.get("NOME");
										            denominazione1 = (String) result1.get("DENOMINAZINE");
										            dataMor1 = (Timestamp)result1.get("DATA_MOR");
										            dataNascita1 = (Timestamp)result1.get("DATA_NASCITA");
									            }
								           } catch (Exception e) {
								        	   throw e;
								           }
								           if ("RSSNTN65L02F839Z".equals(cf1))
								        	   cf1=cf1;
								           String idDwhCivico =  null;
								           try {
								        	    ResultSetHandler h1 = new MapHandler();
									            String sqlCivico = "select ID_EXT_D_CIVICO from sit_d_persona_civico where id_ext_d_persona = ? AND DT_FINE_VAL IS NULL";
												QueryRunner run1 = new QueryRunner();
												Object[] params1 = new Object[] {idDwhAna};
												Map result1 = new HashMap();
									            result1 = (Map) run1.query(conn, sqlCivico,params1, h1);
									            if (result1!=null) 
									            	idDwhCivico = (String) result1.get("ID_EXT_D_CIVICO");
								           } catch (Exception e) {
								        	   throw e;
								           }
								           note = "";
								           if (idDwhCivico!=null ) {
									            // ORA POSSO VEDERE QUALE FK_CIVICO IN CIVICO_TOTALE E' E CONFRONTARE LA DESCRIZIONE CHE HO IN INPUT DALLA TABELLA
									            // SIT_SOGGETTI_TO_CHECK CON LE VARIE COLLEGATE AL CIVICO
								        	   ResultSetHandler h1 = new MapHandler();
									            String sqlCiv = "select t2.*, v.SEDIME, v.INDIRIZZO , V.FK_VIA "
									            	+" from sit_civico_totale t1, sit_civico_totale t2, sit_via_totale v  "
									            	+" where t1.ID_DWH = ? "
									            	+" and t1.FK_ENTE_SORGENTE  = 1  "
									            	+" AND t1.PROG_ES = 1 "
									            	+" and t1.fk_civico = t2.fk_civico "
									            	+" and t2.ID_ORIG_VIA_TOTALE = v.ID_DWH "
									            	+" and t2.FK_ENTE_SORGENTE = v.FK_ENTE_SORGENTE "
									            	+" and t2.PROG_ES = v.PROG_ES";
												QueryRunner run1 = new QueryRunner();
												Object[] params1 = new Object[] {idDwhCivico};
												Map result1 = new HashMap();
									            result1 = (Map) run1.query(conn, sqlCiv,params1, h1);
									            String prefisso= null, indirizzo= null, civico = null;
									            if (result1!=null) {
									             prefisso = (String)result1.get("SEDIME");
									             indirizzo = (String)result1.get("INDIRIZZO");
									             civico = (String)result1.get("CIV_LIV1");
									            }
									            // valuto la via dell'anagrafe con la via fornita
									            Collection<Civico> numeroCivico = new ArrayList<Civico>();
									            if (civicoToCheck==null)
									            	numeroCivico = GestioneStringheVie.restituisciCivico(indirizzoToCheck);
									            else {
									            	Civico c= new Civico(prefissoViaToCheck, indirizzoToCheck, civicoToCheck);
									            	numeroCivico.add(c);
									            }
									            for (Iterator iterator1 = numeroCivico.iterator(); iterator1.hasNext();) {
													Civico c = (Civico) iterator1.next();
													float perc = GestioneStringheVie.similarityStrings( (String)c.getInd(), indirizzo, true);
													indirizzoAnagrafico1 = indirizzo;
													prefissoAnagrafico1 = prefisso;
													civicoAnagrafico1 = civico;
													if (percAttendibilitaIndirizzo<=perc && perc>0) {
														percAttendibilitaIndirizzo = perc;
														trovatoViaAttendibile = true;
														if (compareNullableValue(evalToNumeroCivico(civicoAnagrafico1), c.getCivLiv1())==0)
															trovatoIndirizzoAttendibile = true;
														else {
															note1="VIA DA ANAGRAFE COMPATIBILE ,CIVICO DA CONTROLLARE";
														}
													} 
												}
								           } else {
								        	   note1 = "SOGGETTO ANAGRAFICO TROVATO MA RESIDENZA NON PRESENTE";
								           }				            	
					            	}
					            	
					            
				           } catch (Exception e) {
				        	   throw e;
				           }

				           
			           
			        	   


				           if (trovatoInAnagrafe) {
					           
				        	   try {
					        	    MapListHandler h = new MapListHandler();
					        	    String sqlReddito = "SELECT RED_RED_DIC.IDE_TELEMATICO , D.DIC_CORRETTIVA, D.DIC_INTEGRATIVA, RED_TRAS.DESCRIZIONE DESCRIZIONE_REDDITO, RED_RED_DIC.ANNO_IMPOSTA, TO_CHAR(TO_NUMBER_ALL(RED_RED_DIC.VALORE_CONTABILE / RED_TRAS.CENT_DIVISORE)) VALORE"
							               +" FROM RED_DESCRIZIONE D, RED_REDDITI_DICHIARATI RED_RED_DIC"
							               +" LEFT OUTER JOIN RED_TRASCODIFICA RED_TRAS"
							               +" ON RED_RED_DIC.CODICE_QUADRO = RED_TRAS.CODICE_RIGA"
							               +" AND RED_RED_DIC.ANNO_IMPOSTA = RED_TRAS.ANNO_IMPOSTA"
							               +" WHERE RED_RED_DIC.CODICE_FISCALE_DIC = ?"
							               +" and D.ANNO_IMPOSTA = RED_RED_DIC.ANNO_IMPOSTA"
							               +" AND D.CODICE_FISCALE_DIC = RED_RED_DIC.CODICE_FISCALE_DIC"
							               + " AND D.IDE_TELEMATICO = red_red_dic.IDE_TELEMATICO"
							               +" AND RED_RED_DIC.ANNO_IMPOSTA = (SELECT MAX(ANNO_IMPOSTA) FROM red_dati_anagrafici WHERE codice_fiscale_dic=?)"
							               + " order by RED_RED_DIC.IDE_TELEMATICO ";
									QueryRunner run = new QueryRunner();
									Object[] params = new Object[] {cf1, cf1};
									List<Map> result = new ArrayList<Map>();
						            result = (List<Map>) run.query(conn, sqlReddito,params, h);
						            double v=0;
						            for (Iterator iterator = result.iterator(); iterator.hasNext();) {
										Map map = (Map) iterator.next();
										boolean sostituzione = "1".equals((String) map.get("DIC_CORRETTIVA")) || "1".equals((String) map.get("DIC_INTEGRATIVA"));
										if (sostituzione)
											v=0;
										
										annoImposta1 = (String) map.get("ANNO_IMPOSTA");
										double d;
										try {
											d = new Double((String)map.get("VALORE")).doubleValue();
										} catch (Exception e){
											d=0;
										}
										v += d;

						            }
						           if (annoImposta1!=null)
						        	   valore1 = Double.toString(v);
					           
				        	   } catch (Exception e) {
					        	   throw e;
					           }
				        	   
				        	   // ricerca TARSU
				        	   String idDwhtTrib = null;
					           try {
					        	    ResultSetHandler h = new MapHandler();
						            String sqlTRIB = "select * from sit_soggetto_totale where FK_SOGGETTO = ? and FK_ENTE_SORGENTE  = 2 AND PROG_ES = 1";
									QueryRunner run = new QueryRunner();
									Object[] params = new Object[] {fkSoggetto};
						            Map result = new HashMap();
						            result = (Map) run.query(conn, sqlTRIB,params, h);
						            if (result!=null)  {

						            		idDwhtTrib = (String)result.get("ID_DWH");
						            	
						            	   Connection conn2  =			RulesConnection.getConnection("DBTOTALE_" + ctx.getBelfiore());
							        	   try {
							        		    MapListHandler  mlh = new MapListHandler ();
								        	    String sqlContribuenti = "select c.DESCR_INDIRIZZO indirizzo_dichiarato, t.descr_indirizzo FROM tri_contribuenti C , tri_oggetti_tarsu T WHERE c.pk_sequ_contribuenti = ?" +
								        	    						" AND C.CODICE_CONTRIBUENTE = t.FK_CONTRIBUENTE		" +
								        	    						" AND C.PROVENIENZA = T.PROVENIENZA";
								        	    
												QueryRunner run1 = new QueryRunner();
												Object[] params1 = new Object[] {idDwhtTrib};
												List<Map> result1 = new ArrayList<Map>();
									            result1 = ( ArrayList<Map>) run1.query(conn2, sqlContribuenti,params1, mlh);
									            
									            String separator = "";
									            for (Iterator iterator = result1.iterator(); iterator.hasNext();) {
													Map map = (Map) iterator.next();
													indirizzoDichiaratoTarsu1 = (String) map.get("INDIRIZZO_DICHIARATO");
													indirizzoTarsu1 += separator + (String) map.get("DESCR_INDIRIZZO"); 
													separator = "|";
												}
								           
							        	   } catch (Exception e) {
								        	   throw e;
								           }	finally {
								        	   DbUtils.close(conn2);
								           }
						            }
						            	
						            
						            
						            
					           } catch (Exception e) {
					        	   throw e;
					           }
				        	   
							   ultimoSoggett = new SoggettoCorrenteToCheck();
					           ultimoSoggett.setAnnoImposta1(annoImposta1);
					           ultimoSoggett.setCf1(cf1);
					           ultimoSoggett.setCivicoAnagrafico1(civicoAnagrafico1);
					           ultimoSoggett.setCognome1(cognome1);
					           ultimoSoggett.setDataMor1(dataMor1);
					           ultimoSoggett.setDataNascita1(dataNascita1);
					           ultimoSoggett.setDenominazione1(denominazione1);
					           ultimoSoggett.setDescrizioneRreddito1(descrizioneRreddito1);
					           ultimoSoggett.setIndirizzoAnagrafico1(indirizzoAnagrafico1);
					           ultimoSoggett.setIndirizzoDichiaratoTarsu(indirizzoDichiaratoTarsu1);
					           ultimoSoggett.setIndirizzoTarsu1(indirizzoTarsu1);
					           ultimoSoggett.setNome1(nome1);
					           ultimoSoggett.setNote1(note1);
					           ultimoSoggett.setPrefissoAnagrafico1(prefissoAnagrafico1);
					           ultimoSoggett.setValore1(valore1);
				        	   
				           } 
				           
				           

				           
 			               note =ultimoSoggett.getNote1();
				           if (trovatoInAnagrafe && (trovatoViaAttendibile || trovatoIndirizzoAttendibile)) {
					            cf =ultimoSoggett.getCf1();
					            cognome =ultimoSoggett.getCognome1();
					            nome =ultimoSoggett.getNome1();
					            denominazione =ultimoSoggett.getDenominazione1();
					            dataNascita =ultimoSoggett.getDataNascita1();
					            dataMor = ultimoSoggett.getDataMor1();
					            indirizzoAnagrafico=ultimoSoggett.getIndirizzoAnagrafico1();
					            prefissoAnagrafico=ultimoSoggett.getPrefissoAnagrafico1();
					            civicoAnagrafico=ultimoSoggett.getCivicoAnagrafico1();
				        	    annoImposta = ultimoSoggett.getAnnoImposta1();
				        	    valore = ultimoSoggett.getValore1();
				        	    indirizzoTarsu=ultimoSoggett.getIndirizzoTarsu1();
				        	    indirizzoDichiaratoTarsu=ultimoSoggett.getIndirizzoDichiaratoTarsu();
				           }
				           if(trovatoInAnagrafe && trovatoIndirizzoAttendibile) {
				        	   trovatoSicuramente = true;
				        	   break;
				           }

			            } catch (Exception e) {
			            	log.error("Errore!", e);
			            	RAbNormal rabn = new RAbNormal();
							rabn.setEntity("SIT_SOGGETTI_TO_CHECK");
							rabn.setMessage(e.getMessage());
							rabn.setKey(rs1.getString("FK_SOGGETTO"));
							rabn.setLivelloAnomalia(1);
							rabn.setMessageDate(new Timestamp(new java.util.Date().getTime()));
							abnormals.add(rabn);
		
						}
		            
		             
					
				}
				
				boolean inserisci=false;
				if (numVolteTtrovatoInAnagrafe==1 && !trovatoSicuramente) {
						cf =ultimoSoggett.getCf1();
			            cognome =ultimoSoggett.getCognome1();
			            nome =ultimoSoggett.getNome1();
			            denominazione =ultimoSoggett.getDenominazione1();
			            dataNascita =ultimoSoggett.getDataNascita1();
			            dataMor = ultimoSoggett.getDataMor1();
			            indirizzoAnagrafico=ultimoSoggett.getIndirizzoAnagrafico1();
			            prefissoAnagrafico=ultimoSoggett.getPrefissoAnagrafico1();
			            civicoAnagrafico=ultimoSoggett.getCivicoAnagrafico1();
		        	    annoImposta = ultimoSoggett.getAnnoImposta1();
		        	    valore = ultimoSoggett.getValore1();
		        	    indirizzoTarsu=ultimoSoggett.getIndirizzoTarsu1();
		        	    indirizzoDichiaratoTarsu = ultimoSoggett.getIndirizzoDichiaratoTarsu();
		        	    inserisci=true;
				}
				else if (numVolteTtrovatoInAnagrafe>0 || trovatoSicuramente)
					inserisci = true;	
				
				dati = new LinkedHashMap<String, String>();
        	   dati.put("ID_IN",idToCheck.toString());
        	   dati.put("DENOMINAZIONE_IN", denominazioneToCheck);
        	   dati.put("PREFISSO_IN", prefissoViaToCheck);
        	   dati.put("INDIRIZZO_IN", indirizzoToCheck);
        	   dati.put("CIVICO_IN", civicoToCheck);

				if (!trovatoSicuramente && numVolteTtrovatoInAnagrafe==1 && note==null) {
					note = "SOGGETTO TROVATO SU INDIRIZZO DIVERSO! CONTROLLARE INDIRIZZO!";
					trovatoSicuramente= !trovatoSicuramente;
				} 
				if(!presenteNegliArchivi)
					note = "SOGGETTO SCONOSCIUTO";
					
				if (inserisci) {
					   dati.put("CODFISC", cf);
		        	   dati.put("COGNOME", cognome);
		        	   dati.put("NOME", nome);
		        	   dati.put("DENOMINAZIONE", denominazione);
		        	   dati.put("DATA_NASCITA", DateFormat.dateToString(dataNascita, "dd/MM/yyyy"));
		        	   dati.put("DATA_MORTE", DateFormat.dateToString(dataMor, "dd/MM/yyyy") );
		        	   dati.put("PREFISSO_ANAGRAFICO", prefissoAnagrafico );
		        	   dati.put("INDIRIZZO_ANAGRAFICO", indirizzoAnagrafico );
		        	   dati.put("CIVICO_ANAGRAFICO", civicoAnagrafico);
		        	   dati.put("INDIRIZZO_DICHIARATO_TARSU",indirizzoDichiaratoTarsu);
		        	   dati.put("INDIRIZZO_IMMOBILE_TARSU",indirizzoTarsu);
		        	   dati.put("ANNO_IMPOSTA", annoImposta);
		        	   dati.put("VALORE_REDDITO",valore);
				} else {
					
		        	   dati.put("CODFISC", null);
		        	   dati.put("COGNOME", null);
		        	   dati.put("NOME", null);
		        	   dati.put("DENOMINAZIONE", null);
		        	   dati.put("DATA_NASCITA", null);
		        	   dati.put("DATA_MORTE", null );
		        	   dati.put("PREFISSO_ANAGRAFICO", null );
		        	   dati.put("INDIRIZZO_ANAGRAFICO", null );
		        	   dati.put("CIVICO_ANAGRAFICO", null);
		        	   dati.put("INDIRIZZO_DICHIARATO_TARSU",null);
		        	   dati.put("INDIRIZZO_IMMOBILE_TARSU",null);
		        	   dati.put("ANNO_IMPOSTA", null);
		        	   dati.put("VALORE_REDDITO",null);
		           }
	        	   dati.put("NOTE",note);
				
	        	   if(primorecord)
	        		   tab = creaTabella(dati, ctx.getBelfiore());
	        	   	  		
	        		primorecord = false;
	        		
					
				
				if (tab!=null && !dati.isEmpty()) {
					insert(tab, dati, ctx.getBelfiore());
				}

				} finally {
	            	DbUtils.close(rs1);
	            	DbUtils.close(cs1);        	
	            	
	            	
	    		}

			}
        
		}			
        finally {
        	DbUtils.close(rs);        	
        	DbUtils.close(cs);        	
		}
        
        
			
			ApplicationAck ack = new ApplicationAck(abnormals, "CheckSoggetti eseguito");
			return ack;
		}catch (Exception e)
		{
			log.error("Errore nell'esecuzione di CheckSoggetti",e);
			ErrorAck ea = new ErrorAck(e);
			return ea;
		} 
	}

	
	private void insert(String tab, LinkedHashMap<String, String> dati, String belfiore) throws Exception {
		
		if (dati.isEmpty())
			return;

		java.sql.PreparedStatement stmt =null;
		Connection conn  =null;
		String sqlIns = "INSERT INTO " + tab + " values (";
		try {
			conn = RulesConnection.getConnection("DWH_" + belfiore);
			Set s = dati.keySet();
			Iterator i = s.iterator();
			int c=1;
			while(i.hasNext())
			{
				i.next();
				String virgola= null;
				if (c!=1)
					virgola = ",";
				else { 
					virgola = " ";
				}
				sqlIns+=virgola + "?";
				c++;
			}
			sqlIns += ")";
			stmt = conn.prepareStatement(sqlIns);
			i = dati.keySet().iterator();
			c=1;
			while(i.hasNext())
			{
				Object key = i.next();
				Object value = dati.get(key);
				String virgola= null;
				if (c!=1)
					virgola = ",";
				else { 
					virgola = " ";
				}
				
				if (value!=null)
					stmt.setString(c, (String)value);
				else
					stmt.setNull(c,java.sql.Types.VARCHAR);
				
				c++;
								
				
			}

			stmt.execute();
			stmt.close();
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage()  ,e);
			log.error( sqlIns,e);
			throw e;
		} finally {
			try {
				DbUtils.close(conn);
			} catch (SQLException e) {

			}
		}

	}

	/**
	 * @param dati
	 * @return il nome della tabella creata
	 * @throws Exception 
	 * @throws Exception 
	 */
	private String creaTabella(LinkedHashMap<String, String> dati, String belfiore) throws Exception {
		java.sql.Statement stmt =null;
		Connection conn  =null;
		String tabella ="SIT_SOGGETTI_TO_CHECK_OUT";
		try {
			conn = RulesConnection.getConnection("DWH_"+belfiore);
			String sqlCreate = "CREATE TABLE " + tabella + " ("; 
			Set s = dati.keySet();
			Iterator i = s.iterator();
			int c=1;
			while(i.hasNext())
			{
				Object key = i.next();
				Object value = dati.get(key);
				String virgola = null;
				if (c!=1)
					virgola = ",";
				else {
					virgola = " ";
					c++;
				}
				sqlCreate += virgola + key + " VARCHAR2(1000)";
					
			}
			sqlCreate += ")";

			// drop prima di creare
			String dropSql = "DROP TABLE " + tabella ;
			try {
				stmt = conn.createStatement();
		   		stmt.executeUpdate(dropSql);
				stmt.close();
			} catch (Exception e) {
				// non faccio niente
			}
			stmt = conn.createStatement();
	   		stmt.executeUpdate(sqlCreate);
			stmt.close();

		} catch(SQLException ex) {
			log.error("SQLException: " + ex.getMessage(),ex);
			throw ex;
		} catch (Exception e) {
			log.error("Exception: " + e.getMessage(),e);
			throw e;
		} finally {
			try {
				DbUtils.close(conn);
			} catch (SQLException e) {

			}
		}

		return tabella;
		
		
	}
	
	private int compareNullableValue(Comparable c1, Comparable c2) {
		int res;
		res = (c1 != null ? (c2 != null ? c1.compareTo(c2) : 1)
				: (c2 == null ? 0 : -1));
		return res;
	}
	
	
	private String evalToNumeroCivico(String numeroCivico) {
		String newNumeroCivico = null;
		if (numeroCivico != null) {
			try {
				newNumeroCivico = String.valueOf(Integer.valueOf(numeroCivico));
			} catch (Exception e) {
				newNumeroCivico = numeroCivico.trim().toUpperCase();
			}
		}
		return newNumeroCivico;
	}
	
	
}
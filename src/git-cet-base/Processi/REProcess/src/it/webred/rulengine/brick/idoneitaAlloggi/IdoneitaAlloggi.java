package it.webred.rulengine.brick.idoneitaAlloggi;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;

import it.webred.rulengine.Context;
import it.webred.rulengine.DbCommand;
import it.webred.rulengine.Rule;
import it.webred.rulengine.Utils;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.idoneitaAlloggi.bean.Anagrafe;
import it.webred.rulengine.brick.idoneitaAlloggi.bean.Catasto;
import it.webred.rulengine.brick.idoneitaAlloggi.bean.ConsistenzaUI;
import it.webred.rulengine.brick.idoneitaAlloggi.bean.DatiDocfa;
import it.webred.rulengine.brick.idoneitaAlloggi.bean.DatiTarsu;
import it.webred.rulengine.brick.idoneitaAlloggi.bean.Indirizzo;
import it.webred.rulengine.brick.idoneitaAlloggi.bean.Titolare;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.StringUtils;

public class IdoneitaAlloggi extends DbCommand implements Rule{

	protected static org.apache.log4j.Logger log = Logger.getLogger(IdoneitaAlloggi.class.getName());
	
	private String folderNameMdb = "";
	
	//private Date oggi = new Date(System.currentTimeMillis());
	private NumberFormat numberFormatter = new DecimalFormat("#0.00");
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	
	public IdoneitaAlloggi(BeanCommand bc) {
		super(bc);
		
	}

	@Override
	public CommandAck runWithConnection(Context ctx, Connection conn) throws CommandException {
		
		String nomeSchemaDiogene = RulesConnection.getConnections().get("DWH");
		String nomeSchemaSiti = RulesConnection.getConnections().get("SITI");

		List<RAbNormal> abnormals = new ArrayList<RAbNormal>();
		/*
		 * RAbNormal rabn = new RAbNormal();
			rabn.setEntity("SIT_SOGGETTI_TO_CHECK");
			rabn.setMessage(e.getMessage());
			rabn.setKey(rs1.getString("FK_SOGGETTO"));
			rabn.setLivelloAnomalia(1);
			rabn.setMessageDate(new Timestamp(new java.util.Date().getTime()));
			abnormals.add(rabn);
		 */
		//Connection connSiti = null;
		String errorMsg = "";
		try{
			//	connSiti = RulesConnection.getConnection("SITI");
			/*
			 * Recupero il file di testo TXT con l'elenco delle vie 
			 */
				
			IdoneitaAlloggiEnv env = new IdoneitaAlloggiEnv((String)ctx.get("connessione"), ctx);
				
			String folderNameFrom = env.getPercorsoFiles() + env.folderNameFrom;
			String folderNameTo = env.getPercorsoFiles() + env.folderNameTo;
				
			File f = new File (folderNameFrom + env.NOME_FILE_ELENCO_VIE);
			ArrayList<String> aryLines = new ArrayList<String>();
			if (f != null && f.exists()){
				log.info("Inizio Lettura Elenco Vie da File");
				FileInputStream fis = new FileInputStream ( f );
		        DataInputStream in = new DataInputStream(fis);
		        BufferedReader br = new BufferedReader(new InputStreamReader(in));
		        String strLine = "";
		        while ((strLine = br.readLine()) != null){
	//	             System.out.println (strLine);
		             aryLines.add(strLine);
		        }
		        br.close();
		        in.close();
		        fis.close();
				log.info("Fine Lettura Elenco Vie da File");
			}else{
				log.info("FILE CONTENENTE ELENCO DELLE VIE ASSENTE");
				errorMsg += "FILE " + env.NOME_FILE_ELENCO_VIE + " ASSENTE; ";
			}
			/*
			 * Verifico l'esistenza della cartella ELABORATI che conterrà i ZIPs processati
			 * oppure uno zip dei PDFs processati a seconda della fornitura
			 */
			File fElaborati = new File(folderNameFrom + "ELABORATI");
			if (!fElaborati.exists()){
				fElaborati.mkdir();
			}
			/*
			 * Recupero dei codici strade dalla SITIDSTR
			 */
			log.info("Inizio Recupero PKID_STR dalla SITIDSTR");
			String[] codiciStrade = null;
			if (aryLines != null && aryLines.size()>0){
				codiciStrade = new String[aryLines.size()];
				String sql = "SELECT PKID_STRA FROM " + nomeSchemaDiogene + ".SITIDSTR WHERE rtrim(ltrim(PREFISSO)) = ? AND rtrim(ltrim(NOME)) = ? ";
				log.debug(sql);
				CallableStatement cstmt = conn.prepareCall(sql, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
				for (int index=0; index<aryLines.size();index++){
					String prefissoVia = aryLines.get(index);
					String[] linea = prefissoVia.split(";");
					cstmt.setString(1, linea[0].trim() );
					log.debug("Progressivo via: " + index + " PAR. 1 (PREFISSO): " + linea[0].trim() );	
					cstmt.setString(2, linea[1].trim() );
					log.debug("Progressivo via: " + index + " PAR. 2 (NOME): " + linea[1].trim() );
	
					ResultSet rs1 = cstmt.executeQuery();
					while(rs1.next()){
						codiciStrade[index] = rs1.getString("PKID_STRA");	
					}
					DbUtils.close(rs1);
				}
				DbUtils.close(cstmt);		
			}else{
				log.info("Nessuna via recuperata dal file sorgente");
				errorMsg += "Nessuna via recuperata dal file sorgente: " + env.NOME_FILE_ELENCO_VIE + "; ";
			}
			log.info("Fine Recupero PKID_STR dalla SITIDSTR");
			/*
			 * Recupero dei civici delle strade 
			 */
			Hashtable<String, ArrayList<String>> htCivici = new Hashtable<String, ArrayList<String>>();
			if (codiciStrade != null && codiciStrade.length>0){
				log.info("Inizio Recupero tutti i civici di ogni strada (PKID_CIVI dalla SITICIVI)");
				String sql = "SELECT STR.PKID_STRA, STR.PREFISSO, STR.NOME, CIV.PKID_CIVI, CIV.CIVICO, CIV.COD_NAZIONALE FROM " + nomeSchemaDiogene + ".SITICIVI CIV " +
						"LEFT JOIN " + nomeSchemaDiogene + ".SITIDSTR STR ON CIV.PKID_STRA = STR.PKID_STRA WHERE CIV.PKID_STRA = ? ";
				log.debug(sql);
				CallableStatement cstmt = conn.prepareCall(sql, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
				for (int index=0; index<codiciStrade.length;index++){
					if (!StringUtils.isEmpty(codiciStrade[index])){
						cstmt.setLong(1, new Long(codiciStrade[index]) );
						log.debug("Progressivo via: " + index + " PAR. 1 (PKID_STRA): " + codiciStrade[index] );
						ResultSet rs1 = cstmt.executeQuery();
						while(rs1.next()){
							String idStra = rs1.getString("PKID_STRA");
							String prefisso = rs1.getString("PREFISSO");
							String nome = rs1.getString("NOME");
							String idCivi = rs1.getString("PKID_CIVI");
							String civico = rs1.getString("CIVICO");
							String codNaz = rs1.getString("COD_NAZIONALE");
							
							String key = codiciStrade[index];
							String value = "";
							value+=!StringUtils.isEmpty(idStra)?idStra+";":""+";";
							value+=!StringUtils.isEmpty(prefisso)?prefisso+";":""+";";
							value+=!StringUtils.isEmpty(nome)?nome+";":""+";";
							value+=!StringUtils.isEmpty(idCivi)?idCivi+";":""+";";
							value+=!StringUtils.isEmpty(civico)?civico+";":""+";";
							value+=!StringUtils.isEmpty(codNaz)?codNaz+";":""+";";
							
							ArrayList<String> alCivici = null;
							if (htCivici.containsKey(key)){
								alCivici = htCivici.get(key);
							}else{
								alCivici = new ArrayList<String>();
							}
							alCivici.add(value);
							htCivici.put(key, alCivici);
						}
						DbUtils.close(rs1);
					}
				}
				DbUtils.close(cstmt);	
				log.info("Fine Recupero tutti i civici di ogni strada (PKID_CIVI dalla SITICIVI)");
			}
			/*
			 * Recuperiamo i dati che ci servono per l'indagine del civico:
			 * - INDIRIZZI 
			 * - RESIDENTI AL CIVICO
			 * - LISTA UIU AL CIVICO CON TITOLARI
			 * - CONSISTENZA UI
			 */
			//ArrayList<Indirizzo> listaIndirizzi = new ArrayList<Indirizzo>();
			ArrayList<Anagrafe> listaResidenti = new ArrayList<Anagrafe>();
			ArrayList<Catasto> listaUIConTitolare = new ArrayList<Catasto>();
			ArrayList<ConsistenzaUI> listaUIConsistenza= new ArrayList<ConsistenzaUI>();
			if (htCivici != null && htCivici.size()>0){
				Enumeration<String> eCiv = htCivici.keys();
				while(eCiv.hasMoreElements()){
					String key = eCiv.nextElement();
					ArrayList<String> alCiv = htCivici.get(key);
					if (alCiv != null && alCiv.size()>0){
						String sql = env.SQL_DETT_RESIDENTI1;
						String sql2 = env.SQL_DETT_UI;
						CallableStatement cstmt = conn.prepareCall(sql, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
						CallableStatement cstmt2 = conn.prepareCall(sql2, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
						for(int ind=0; ind<alCiv.size(); ind++){
							String strIndirizzo = alCiv.get(ind);
							String[] valIndirizzo = strIndirizzo.split(";");
							/*
							 * 0: idStr
							 * 1: prefisso
							 * 2: nome
							 * 3: idCivi
							 * 4: civico
							 * 5: codNaz
							 */
							String idStr = valIndirizzo[0];
							String prefix = valIndirizzo[1];
							String name = valIndirizzo[2];
							String idCiv = valIndirizzo[3];
							String number = valIndirizzo[4];
							String codEnt = valIndirizzo[5];
							/*
							 * Recupero residenti
							 */
							log.info("Inizio Recupero Residenti al Civico " + " idStr: " + idStr + " idCiv: " + idCiv);
							cstmt.setInt(1, !StringUtils.isEmpty(valIndirizzo[3])?Integer.parseInt(valIndirizzo[3]):0);
							ResultSet rs = cstmt.executeQuery();
							while(rs.next()){
								Anagrafe ana = new Anagrafe();
								ana.setPrefisso( valIndirizzo[1] );
								ana.setNomeVia( valIndirizzo[2] );
								ana.setCivico( valIndirizzo[4] );
								ana.setIdCivico( !StringUtils.isEmpty(valIndirizzo[3])?Integer.parseInt(valIndirizzo[3]):0 );
								if (rs.getString("COD_ANAGRAFE") != null)
									ana.setCodAnagrafe(rs.getString("COD_ANAGRAFE"));
								ana.setId(rs.getString("ID"));
								ana.setIdExt(rs.getString("ID_EXT"));
								if(rs.getString("COGNOME") != null)
								   ana.setCognome(rs.getString("COGNOME"));
								if(rs.getString("NOME") != null)
								   ana.setNome(rs.getString("NOME"));
								if(rs.getString("CODICE_FISCALE") != null)
									ana.setCodFiscale(rs.getString("CODICE_FISCALE"));
								if(rs.getString("DATA_NASCITA") != null)
									ana.setDataNascita(rs.getString("DATA_NASCITA"));
								if(rs.getString("CITTADINANZA") != null)
									ana.setCittadinanza(rs.getString("CITTADINANZA"));
								if(rs.getString("COMUNE_NASCITA") != null)
									ana.setComuneNascita(rs.getString("COMUNE_NASCITA"));
								if(rs.getString("CODICE_NAZIONALE") != null)
									ana.setCodiceNazionale(rs.getString("CODICE_NAZIONALE"));
								if(rs.getString("ID_FAM") != null)
									ana.setFamiglia(rs.getString("ID_FAM"));
								if(rs.getString("RELAZ_PAR") != null)
									ana.setTipoParentela(rs.getString("RELAZ_PAR"));
								listaResidenti.add(ana);
							}
							DbUtils.close(rs);
							log.info("Fine Recupero Residenti al Civico ");
							/*
							 * Recupero uiu e titolari
							 */
							log.info("Inizio Recupero UIU e Titolari " + " idStr: " + idStr + " idCiv: " + idCiv);
							cstmt2.setString(1, valIndirizzo[5]);
							cstmt2.setInt(2, !StringUtils.isEmpty(valIndirizzo[3])?Integer.parseInt(valIndirizzo[3]):0);
							ResultSet rs2 = cstmt2.executeQuery();
							while(rs2.next()){
								//DATI CENSUARI
								Catasto cat = new Catasto();
								String foglio = "" + rs2.getInt("FOGLIO"); 
								String particella =  rs2.getString("PARTICELLA");
								String sub = "" + rs2.getInt("UNIMM"); 
								cat.setFoglio(Utils.eliminaZeriSx(foglio));
								cat.setMappale(Utils.eliminaZeriSx(particella));
								cat.setSub(Utils.eliminaZeriSx(sub));
								if (rs2.getString("CATEGORIA") != null)
									cat.setCategoria(rs2.getString("CATEGORIA"));
								if (rs2.getString("CLASSE") != null)
									cat.setClasse(rs2.getString("CLASSE"));
								if (rs2.getObject("RENDITA") != null) {
									cat.setRendita(rs2.getDouble("RENDITA"));
									cat.setRenditaStrFmt( numberFormatter.format( rs2.getDouble("RENDITA") ) );
								}
								Double consistenza = 0d;
								if (rs2.getObject("CONSISTENZA") != null )	
									consistenza = rs2.getDouble("CONSISTENZA");
								Double supCat = 0d;
								if (rs2.getObject("SUP_CAT") != null)
									supCat = rs2.getDouble("SUP_CAT");
								Double supC340 = 0d;
								if (rs2.getObject("SUP_C340") != null)
									supC340 = rs2.getDouble("SUP_C340");
								/*
								 * acquisizione indirizzi dal catasto che potrebbero
								 * essere diversi dall'anagrafe strade 
								 */
								Indirizzo indirizzo = this.recuperaIndirizzoCatasto(env, conn, foglio, particella, sub, valIndirizzo[5]);
								cat.setIndirizzo(indirizzo);
								cat.setPrefisso(valIndirizzo[1]);
								cat.setNomeVia(valIndirizzo[2]);
								cat.setCivico(valIndirizzo[4]);
								cat.setIdCivico( !StringUtils.isEmpty(valIndirizzo[3])?Integer.parseInt(valIndirizzo[3]):0 );
								//acquisizione titolari: N.B. deve essere l'ultima cosa
								//in maniera tale che i dati siano poi eventualmente ripetuti per tutti i titolari
								ArrayList<Titolare> listaTit = this.recuperaTitolari(env, conn, foglio, particella, sub, valIndirizzo[5]);
								/*
								 * 
								 */
								if (listaTit.size() > 0){
									int contaTit = 0;
									for(Titolare tit: listaTit) {
										contaTit++;
										if (contaTit == 1) {
											cat.setTitolare(tit);
											listaUIConTitolare.add(cat);
										}else {
											Catasto cat1 = new Catasto();
											cat1.setTitolare(tit);
											cat1.setIndirizzo(cat.getIndirizzo());
											cat1.setPrefisso(cat.getPrefisso());
											cat1.setNomeVia(cat.getNomeVia());
											cat1.setCivico(cat.getCivico());
											cat1.setIdCivico(cat.getIdCivico());
											cat1.setFoglio(cat.getFoglio());
											cat1.setMappale(cat.getMappale());
											cat1.setSub(cat.getSub());
											cat1.setCategoria(cat.getCategoria());
											cat1.setClasse(cat.getClasse());
											cat1.setRendita(cat.getRendita());
											cat1.setRenditaStrFmt( cat.getRenditaStrFmt() );
											
											listaUIConTitolare.add(cat1);
										}
									}
								}else {
									listaUIConTitolare.add(cat);
								}
								/*
								 * Recupero consistenza: per ogni civico ho piu di un foglio
								 * particella e subalterno
								 */
								log.info("Inizio Recupero Consistenza " + " idStr: " + idStr + " idCiv: " + idCiv);
								ConsistenzaUI consUi = new ConsistenzaUI();
								consUi.setPrefisso(valIndirizzo[1]);
								consUi.setNomeVia(valIndirizzo[2]);
								consUi.setCivico(valIndirizzo[4]);
								consUi.setConsistenza(consistenza);
								consUi.setSuperficie(supCat);
								consUi.setSupC340(supC340);
								consUi.setFoglio(Utils.eliminaZeriSx(foglio));
								consUi.setMappale(Utils.eliminaZeriSx(particella));
								consUi.setSub(Utils.eliminaZeriSx(sub));
								// acquisizione info docfa  edati tarsu
								Hashtable ht = this.getInfoDocfa(env, conn, foglio, particella, sub);
								consUi.setInfoDocfa((String)ht.get("info"));
								DatiDocfa datiDocfa = (DatiDocfa)ht.get("dati");
								consUi.setDatiDocfa(datiDocfa);
								//manca l'immobile..ora si aggiunge
								datiDocfa.setIdImmobile(getIdImmobile(env, conn, foglio, particella, sub, datiDocfa.getDataFornitura(), datiDocfa.getProtocollo()));
								DatiTarsu datiTarsu = getDatiTarsu(env, conn, foglio, particella, sub);
								consUi.setSupTarsu(datiTarsu.getSupTot());
								consUi.setDicTarsuCFPi(datiTarsu.getCodFisPIva());
								consUi.setDicTarsuDenominazione(datiTarsu.getDenominazione());
								//aggiungo alla lista
								listaUIConsistenza.add(consUi);
								log.info("Fine Recupero Consistenza");
	
							}
							DbUtils.close(rs2);
							log.info("Fine Recupero UIU e Titolari");
							
						}
						DbUtils.close(cstmt);
						DbUtils.close(cstmt2);
					}
				}
				Hashtable<String, ArrayList> ht = new Hashtable<String, ArrayList>();
				//ht.put("LISTA_INDIRIZZI", listaIndirizzi);
				ht.put("RESIDENTI", listaResidenti);
				ht.put("DATI_CENSUARI_UI", listaUIConTitolare);
				ht.put("CONSISTENZA_UI", listaUIConsistenza);
				/*
				 *  Esportazione in mdb
				 */
				this.exportMdb(ht, folderNameTo);
			}else{
				log.info("Nessun civico recuperato");
				errorMsg += "Nessun civico recuperato; ";
			}
				
			log.info("Inizio Spostamento file vie in ELABORATI");
			File currentFile = new File(folderNameFrom + env.NOME_FILE_ELENCO_VIE);
			currentFile.renameTo( new File(folderNameFrom + "ELABORATI/" + env.NOME_FILE_ELENCO_VIE) );
			log.info("Fine Spostamento file vie in ELABORATI");
		
		}catch(Exception e){
			log.error(e);
			errorMsg += e.getMessage();
		}
//		finally{
//			try{
//				if (connSiti != null)
//					connSiti.close();
//			}catch(Exception e){
//				log.error(e);
//			}
//		}
		
		return new ApplicationAck(abnormals, "Fine Elaborazione - " + errorMsg);
	}//-------------------------------------------------------------------------
	
	private Indirizzo recuperaIndirizzoCatasto(IdoneitaAlloggiEnv env, Connection conn, String foglio, String particella, String sub, String codNazionale ) throws Exception {
    	ResultSet rs =null;
		String sql=null;
		PreparedStatement pst =null;
		Indirizzo address = new Indirizzo();
		boolean closeConn = false;
		try {
			if (conn == null) {
				closeConn = true;
			}				
			sql = env.SQL_INDIRIZZO_CAT_UI;
			log.debug(sql);	
			
//			if (foglio.equals("273") && particella.equals("00010") && sub.equals("2")){
//				System.out.println("XXX: Ecco la via catastale diversa dall'anagrafe");
//			}
			
 			pst = conn.prepareStatement(sql);
			pst.setString(1, codNazionale);
			log.debug("PAR. 1 (CODNAZIONALE): " + codNazionale );	
			pst.setString(2, foglio.trim());
			log.debug("PAR. 2 (FOGLIO): " + foglio );
			pst.setString(3, particella.trim());
			log.debug("PAR. 3 (PARTICELLA): " + particella );
			pst.setString(4, sub.trim());
			log.debug("PAR. 4 (SUB): " + sub );
			rs = pst.executeQuery();
			if (rs.next()){
				
				String prefisso = rs.getString("PREFISSO");
				String via = rs.getString("VIA");
				String civico = rs.getString("CIVICO");
				
				address.setPrefisso(prefisso!=null?prefisso.trim():"");
				address.setNomeVia(via!=null?via.trim():"");
				address.setCivico(civico!=null?civico.trim():"");
				String indirizzoCompleto = prefisso!=null?prefisso.trim():"";
				if (via != null && !via.equals("")) {
					if (!indirizzoCompleto.equals("")) {
						indirizzoCompleto += " ";
					}
					indirizzoCompleto += via;
				}
				if (civico != null && !civico.equals("")) {
					if (!indirizzoCompleto.equals("")) {
						indirizzoCompleto += " ";
					}
					indirizzoCompleto += civico;
				}
				address.setIndirizzoCompleto(indirizzoCompleto);
			}
			rs.close();
			pst.close();
		}catch(Exception e) {
			log.error(e);
			throw e;
		}
		finally {
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
			if (closeConn) {
				if (conn != null && !conn.isClosed())
					conn.close();
			}
		}
		return address;
	}//-------------------------------------------------------------------------	
	
    private ArrayList<Titolare> recuperaTitolari(IdoneitaAlloggiEnv env, Connection conn, String foglio, String particella, String sub, String codNazionale) throws Exception {
		ResultSet rs = null;
		PreparedStatement pst = null;
		String sql = null;
		ArrayList<Titolare> listaTitolari = new ArrayList<Titolare>();
		boolean closeConn = false;
		try {
			if (conn == null) {
				closeConn = true;
			}	
			sql = env.SQL_TITOLARI;
			pst = conn.prepareStatement(sql);
			pst.setString(1, codNazionale);
			pst.setString(2, foglio.trim());
			pst.setString(3, particella.trim());
			pst.setString(4, sub.trim());
			pst.setString(5, codNazionale);
			pst.setString(6, foglio.trim());
			pst.setString(7, particella.trim());
			pst.setString(8, sub.trim());
			rs= pst.executeQuery();
			while (rs.next()){
				Titolare tit = new Titolare();
				tit.setCodFisc(rs.getString("CUAA"));
				tit.setDenom(rs.getString("RAGI_SOCI"));
				tit.setTipoTitolo(rs.getString("DESCRIPTION"));
				listaTitolari.add(tit);
			}
			rs.close();
			pst.close();
			
		}catch(Exception e) {
			log.error(e);
			throw e;
		}
		finally {
			if (rs != null)
				rs.close();
			if (pst != null)
				pst.close();
			if (closeConn) {
				if (conn != null && !conn.isClosed())
					conn.close();
			}
		}
		return listaTitolari;
	}//-------------------------------------------------------------------------
    
    private boolean exportMdb(Hashtable<String, ArrayList> ht, String folderNameTo) throws Exception{
    	boolean esito = false;
    	
    	String mdbName = "idoneitaAlloggi_" + System.currentTimeMillis() + ".mdb";
    	folderNameMdb = folderNameTo;
    	/*
    	 * mdbStructure è una hashtable strutturata in questo modo:
    	 * key: String nome della tabella
    	 * value: array di String composta da [nome del campo] + [numero relativo al tipo (=java.sql.Types.INTEGER)] 
    	 */
    	Hashtable<String, String[]> htMdbSkeleton = new Hashtable<String, String[]>(); 
    	String[] aryFieldsResidenti = new String[12];
       	aryFieldsResidenti[0] = "PREFISSO";
    	aryFieldsResidenti[1] = "NOME_VIA";
    	aryFieldsResidenti[2] = "CIVICO";
    	aryFieldsResidenti[3] = "CODICE_FAMIGLIA";
    	aryFieldsResidenti[4] = "TIPO_PAR";
    	aryFieldsResidenti[5] = "COD_IND_ANAG";
    	aryFieldsResidenti[6] = "COGNOME";
    	aryFieldsResidenti[7] = "NOME";
    	aryFieldsResidenti[8] = "CODICE_FISCALE";
    	aryFieldsResidenti[9] = "DATA_NASCITA";
    	aryFieldsResidenti[10] = "COMUNE_NASCITA";
    	aryFieldsResidenti[11] = "CITT";
    	String[] aryFieldsDatiCensuari = new String[13];
    	aryFieldsDatiCensuari[0] = "PREFISSO";
    	aryFieldsDatiCensuari[1] = "NOME_VIA";
    	aryFieldsDatiCensuari[2] = "CIVICO";
    	aryFieldsDatiCensuari[3] = "FOGLIO";
    	aryFieldsDatiCensuari[4] = "PART";
    	aryFieldsDatiCensuari[5] = "SUB";
    	aryFieldsDatiCensuari[6] = "CAT";
    	aryFieldsDatiCensuari[7] = "CLASSE";
    	aryFieldsDatiCensuari[8] = "RENDITA";
    	aryFieldsDatiCensuari[9] = "CODICE_FISCALE";
    	aryFieldsDatiCensuari[10] = "DENOMINAZIONE";
    	aryFieldsDatiCensuari[11] = "TITOLO";
    	aryFieldsDatiCensuari[12] = "INDIRIZZO_CATASTALE";
    	String[] aryFieldsConsistenzaUI = new String[13];
    	aryFieldsConsistenzaUI[0] = "PREFISSO";
    	aryFieldsConsistenzaUI[1] = "NOME_VIA";
    	aryFieldsConsistenzaUI[2] = "CIVICO";
    	aryFieldsConsistenzaUI[3] = "FOGLIO";
    	aryFieldsConsistenzaUI[4] = "PART";
    	aryFieldsConsistenzaUI[5] = "SUB";
    	aryFieldsConsistenzaUI[6] = "SUP_DIC_TARSU";
    	aryFieldsConsistenzaUI[7] = "CONTRIBUENTE_TARSU";
    	aryFieldsConsistenzaUI[8] = "CONTRIBUENTE_TARSU_DENOM";
    	aryFieldsConsistenzaUI[9] = "CONS_CAT";
    	aryFieldsConsistenzaUI[10] = "SUP_CAT";
    	aryFieldsConsistenzaUI[11] = "SUP_C340";
    	aryFieldsConsistenzaUI[12] = "DOCFA";
    	htMdbSkeleton.put("RESIDENTI", aryFieldsResidenti);
    	htMdbSkeleton.put("DATI_CENSUARI_UI", aryFieldsDatiCensuari);
    	htMdbSkeleton.put("CONSISTENZA_UI", aryFieldsConsistenzaUI);
    	
    	
    	try{
    		Database db = null;
        	File fMdb = new File (folderNameMdb + mdbName);
        	if (fMdb.exists()){
        		db = Database.open( fMdb );
        	}else{
        		db = Database.create( fMdb );        		
        	}
    		
        	Boolean creato = this.scheletroMdb(db, htMdbSkeleton);
        	Boolean gonfiato = false;
        	if (creato){
        		log.info("Struttura tabelle in MDB creata con successo");
            	gonfiato = this.gonfiaMdb(db, ht);        		
        	}else{
        		log.info("Impossibile creare struttura tabelle in MDB");
        	}
	    	
	    	db.close();
	    	
		}catch(Exception e){
			log.error(e);
			throw e;
		}
		
    	return esito;
    }//-------------------------------------------------------------------------
    
	private Boolean scheletroMdb(Database db, Hashtable<String, String[]> htMdbSkeleton) throws Exception {
		Boolean creato = false;
		try{
			Table table = null;
			TableBuilder tableBuilder = null;
			Enumeration<String> eSkt = htMdbSkeleton.keys();
			while(eSkt.hasMoreElements()){
				String nomeTabella = eSkt.nextElement();
				String[] records = htMdbSkeleton.get(nomeTabella);
				tableBuilder = new TableBuilder(nomeTabella);
				if (records != null && records.length>0){
					for (int index=0; index<records.length; index++){
						ColumnBuilder cb = new ColumnBuilder(records[index]);
						int colType = Types.VARCHAR;
						cb.setSQLType(colType);
						cb.setLengthInUnits(255);
						Column col = cb.toColumn();
						tableBuilder.addColumn(col);
					}
				}
				table = tableBuilder.toTable(db);
				creato = true;
			}
			
		}catch(Exception e){
			log.error(e);
			throw e;
		}
		return creato;
	}//-------------------------------------------------------------------------
    
	private Boolean gonfiaMdb(Database db, Hashtable<String, ArrayList> ht) throws Exception{
		Boolean gonfiato = false;
		try{
	    	//ArrayList<Indirizzo> alIndirizzi = ht.get("LISTA_INDIRIZZI");
	    	ArrayList<Anagrafe> alResidenti = ht.get("RESIDENTI");
	    	ArrayList<Catasto> alCatasto = ht.get("DATI_CENSUARI_UI");
	    	ArrayList<ConsistenzaUI> alConsistenza = ht.get("CONSISTENZA_UI");

	    	Enumeration<String> eHt = ht.keys();
	    	while(eHt.hasMoreElements()){
	    		String nomeTabella = eHt.nextElement();
	    		ArrayList<Object> objs = ht.get(nomeTabella);
	    		if (objs != null && objs.size()>0){
	    			Object obj = null;
	    			log.info("Inizio Inserimento in tabella " + nomeTabella);
					Table table = db.getTable(nomeTabella);
	    			for (int i=0; i<objs.size(); i++){
	    				String[] values = null;
	    				obj = objs.get(i);
	    				if (obj instanceof Anagrafe){
	    					Anagrafe anagrafe = (Anagrafe)obj;
	    					if (anagrafe != null){
		    					values = new String[12];
		    					values[0] = anagrafe.getPrefisso()!=null?anagrafe.getPrefisso():"";
		    					values[1] = anagrafe.getNomeVia()!=null?anagrafe.getNomeVia():"";
		    					values[2] = anagrafe.getCivico()!=null?anagrafe.getCivico():"";
		    					values[3] = anagrafe.getFamiglia()!=null?anagrafe.getFamiglia():"";
		    					values[4] = anagrafe.getTipoParentela()!=null?anagrafe.getTipoParentela():"";
		    					values[5] = anagrafe.getCodAnagrafe()!=null?anagrafe.getCodAnagrafe():"";
		    					values[6] = anagrafe.getCognome()!=null?anagrafe.getCognome():"";
		    					values[7] = anagrafe.getNome()!=null?anagrafe.getNome():"";
		    					values[8] = anagrafe.getCodFiscale()!=null?anagrafe.getCodFiscale():"";
		    					values[9] = anagrafe.getDataNascita()!=null?anagrafe.getDataNascita():"";
		    					values[10] = anagrafe.getComuneNascita()!=null?anagrafe.getComuneNascita():"";
		    					values[11] = anagrafe.getCittadinanza()!=null?anagrafe.getCittadinanza():"";	    						
	    					}
	    				}else if (obj instanceof Catasto){
	    					Catasto catasto = (Catasto)obj;
	    					if (catasto != null){
	    						values = new String[13];
		    					values[0] = catasto.getPrefisso()!=null?catasto.getPrefisso():"";
		    					values[1] = catasto.getNomeVia()!=null?catasto.getNomeVia():"";
		    					values[2] = catasto.getCivico()!=null?catasto.getCivico():"";
		    					values[3] = catasto.getFoglio()!=null?catasto.getFoglio():"";
		    					values[4] = catasto.getMappale()!=null?catasto.getMappale():"";
		    					values[5] = catasto.getSub()!=null?catasto.getSub():"";
		    					values[6] = catasto.getCategoria()!=null?catasto.getCategoria():"";
		    					values[7] = catasto.getClasse()!=null?catasto.getClasse():"";
		    					values[8] = numberFormatter.format( catasto.getRendita() );
		    					values[9] = (catasto.getTitolare()!=null && catasto.getTitolare().getCodFisc()!=null)?catasto.getTitolare().getCodFisc():"";
		    					values[10] = (catasto.getTitolare()!=null && catasto.getTitolare().getDenom()!=null)?catasto.getTitolare().getDenom():"";
		    					values[11] = (catasto.getTitolare()!=null && catasto.getTitolare().getTipoTitolo()!=null)?catasto.getTitolare().getTipoTitolo():"";
		    					values[12] = (catasto.getIndirizzo() !=null && catasto.getIndirizzo().getIndirizzoCompleto() != null)?catasto.getIndirizzo().getIndirizzoCompleto():"";	    						
	    					}
	    				}else if (obj instanceof ConsistenzaUI){
	    					ConsistenzaUI consistenza = (ConsistenzaUI)obj;
	    					if (consistenza != null){
	    						values = new String[13];
	        					values[0] = consistenza.getPrefisso()!=null?consistenza.getPrefisso():"";
		    					values[1] = consistenza.getNomeVia()!=null?consistenza.getNomeVia():"";
		    					values[2] = consistenza.getCivico()!=null?consistenza.getCivico():"";
		    					values[3] = consistenza.getFoglio()!=null?consistenza.getFoglio():"";
		    					values[4] = consistenza.getMappale()!=null?consistenza.getMappale():"";
		    					values[5] = consistenza.getSub()!=null?consistenza.getSub():"";
		    					values[6] = numberFormatter.format( consistenza.getSupTarsu() );
		    					values[7] = consistenza.getDicTarsuCFPi()!=null?consistenza.getDicTarsuCFPi():"";
		    					values[8] = consistenza.getDicTarsuDenominazione()!=null?consistenza.getDicTarsuDenominazione():"";
		    					values[9] = numberFormatter.format( consistenza.getConsistenza() );
		    					values[10] = numberFormatter.format( consistenza.getSuperficie() );
		    					values[11] = numberFormatter.format( consistenza.getSupC340() );
		    					if (consistenza.getDatiDocfa()!=null){
		    						String docfaInfo = "";
		    						if (consistenza.getDatiDocfa().getProtocollo() != null && consistenza.getDatiDocfa().getDataFornitura() != null){
		    							docfaInfo += consistenza.getDatiDocfa().getProtocollo() + "-" + dateFormatter.format(consistenza.getDatiDocfa().getDataFornitura());
		    						}else if (consistenza.getDatiDocfa().getProtocollo() != null){
		    							docfaInfo += consistenza.getDatiDocfa().getProtocollo();
		    						}else if (consistenza.getDatiDocfa().getDataFornitura() != null){
		    							docfaInfo += dateFormatter.format(consistenza.getDatiDocfa().getDataFornitura());
		    						}
		    						values[12] = docfaInfo;
		    					}else{
		    						values[12] = "";
		    					}
	    					}
	    				}
	    				if (values != null)
	    					table.addRow(values);
	    			}
	    			
	    		}
	    	}
		}catch(Exception e){
			log.error(e);
			throw e;
		}
		return gonfiato;
	}//-------------------------------------------------------------------------
    
    private Hashtable getInfoDocfa(IdoneitaAlloggiEnv env, Connection conn, String foglio, String particella, String sub) throws Exception {
		Hashtable ht = new Hashtable();
		DatiDocfa datiDocfa = new DatiDocfa();
    	PreparedStatement pst= null;
    	ResultSet rs =null;
		String sql=null;
		String infoDocfa="";
		boolean closeConn = false;
		try {
			if (conn == null) {
				closeConn = true;
			}
			sql = env.SQL_DOCFA_UIU;
 		    pst = conn.prepareStatement(sql);
 		    pst.setString(1, Utils.fill("0", "sx", foglio, 4)) ;
			pst.setString(2, Utils.fill("0", "sx", particella, 5));
			pst.setString(3, Utils.fill("0", "sx", sub, 4));
			rs = pst.executeQuery();
			if (rs.next()){ // prendo solo il primo, perché sono ordinati per data desc e mi serve l'ultimo 
				infoDocfa = componiInfoDocfa(rs.getDate("FORNITURA"), rs.getString("PROTOCOLLO_REG")) ;
				datiDocfa.setFornitura(rs.getString("FORNITURA")); 
				datiDocfa.setDataFornitura(rs.getDate("FORNITURA"));
				datiDocfa.setProtocollo(rs.getString("PROTOCOLLO_REG"));
				String dtFornStr =rs.getDate("FORNITURA").toString(); 
				datiDocfa.setChiaveDocfa(rs.getString("PROTOCOLLO_REG") + "|" + dtFornStr.replace("-", "") );
			}
			rs.close();
			pst.close();
			ht.put("info", infoDocfa);
			ht.put("dati", datiDocfa);
			return ht;
		}catch(Exception e) {
			log.error(e);
			throw e;
		}
		finally {
			if (pst != null)
				pst.close();
			if (rs != null)
				rs.close();
			if (closeConn) {
				if (conn != null && !conn.isClosed())
					conn.close();
			}
		}
	}//-------------------------------------------------------------------------    
    
    private String componiInfoDocfa(java.sql.Date dtFornitura, String prot){
    	String infoDocfa = "";
    	int anno = Utils.getYear(dtFornitura );
		int mese = Utils.getMonth( dtFornitura );

		String meseData = "";
		if (mese <10)
			meseData = "0" + mese;
		else
			meseData = "" + mese;

		infoDocfa = prot + "-" + meseData + "/" + anno;

    	return infoDocfa;
    }//-------------------------------------------------------------------------
    
    private String getIdImmobile(IdoneitaAlloggiEnv env, Connection conn, String foglio, String particella, String sub, java.sql.Date dtFornitura, String protocollo) throws Exception {
		String idImm=""; 
		PreparedStatement pst= null;
    	ResultSet rs =null;
		String sql=null;
		String codEnt=null;
		String infoDocfa="";
		boolean closeConn = false;
		try {
			if (conn == null) {
				closeConn = true;
			}
			sql = env.SQL_DOCFA_ID_IMM;
 		    pst = conn.prepareStatement(sql);
 		    pst.setString(1, protocollo);
 		    pst.setDate(2, dtFornitura);
 		    pst.setString(3, Utils.fill("0", "sx", foglio, 4)) ;
			pst.setString(4, Utils.fill("0", "sx", particella, 5));
			pst.setString(5, Utils.fill("0", "sx", sub, 4));
 			rs = pst.executeQuery();
			if (rs.next()){
				idImm = rs.getString("ID_IMM");
			}
			rs.close();
			pst.close();
			return idImm;
		}catch(Exception e) {
			log.error(e);
			throw e;
		}
		finally {
			if (pst != null)
				pst.close();
			if (rs != null)
				rs.close();
			if (closeConn) {
				if (conn != null && !conn.isClosed())
					conn.close();
			}
		}
	}//-------------------------------------------------------------------------	
    
    private DatiTarsu getDatiTarsu(IdoneitaAlloggiEnv env, Connection conn, String foglio, String particella, String sub) throws Exception {
		DatiTarsu dati= new DatiTarsu();
    	PreparedStatement pst= null;
    	ResultSet rs =null;
		String sql=null;
		boolean closeConn = false;
		try {
			if (conn == null) {
				closeConn = true;
			}
			sql = env.SQL_TARSU;
			int foglioNum=-1, particellaNum=-1, subNum=-1;
			try {
				foglioNum= Integer.parseInt(foglio);
				particellaNum= Integer.parseInt(particella);
				subNum= Integer.parseInt(sub);
			}
			catch(NumberFormatException nfe) {
				return dati;
			}
 		    pst = conn.prepareStatement(sql);
		    pst.setString(1, "" + foglioNum);
			pst.setString(2, "" +particellaNum);
			pst.setString(3, "" +subNum);
			rs = pst.executeQuery();
			if (rs.next()){  
				dati.setCodFisPIva(rs.getString("CODFISC"));//se partitaiva è valorizzata, allora è riportata  anche su codfisc
				dati.setSupTot(rs.getDouble("SUPERFICE_TOTALE"));
				dati.setDenominazione(rs.getString("DENOMINAZIONE"));
			}
			rs.close();
			pst.close();
			
			return dati;
		}catch(Exception e) {
			log.error(e);
			throw e;
		}
		finally {
			if (pst != null)
				pst.close();
			if (rs != null)
				rs.close();
			if (closeConn) {
				if (conn != null && !conn.isClosed())
					conn.close();
			}
		}
	}//-------------------------------------------------------------------------	
    
	
}//-----------------------------------------------------------------------------


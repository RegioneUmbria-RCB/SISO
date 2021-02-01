package it.webred.rulengine.brick.cened;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.cened.bean.CenedBean;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.StringUtils;

public class Cened  extends Command implements Rule{
	
	protected static org.apache.log4j.Logger log = Logger.getLogger(Cened.class.getName());

	private static final String SIT_ENTE = "SIT_ENTE";
	private static final String CENED = "CENED";
	private static final String DATI_TEC_FABBR_CERT_ENER = "DATI_TEC_FABBR_CERT_ENER";
	
	private Date oggi = new Date(System.currentTimeMillis());
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
	
	public Cened(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}//-------------------------------------------------------------------------
	
	@Override
	public CommandAck run(Context ctx) throws CommandException {
		
		String nomeSchemaDiogene = RulesConnection.getConnections().get("DWH_" + ctx.getBelfiore());
		if (nomeSchemaDiogene == null || nomeSchemaDiogene.equals("")) {
			nomeSchemaDiogene = RulesConnection.getConnections().get("DWH");
		}
		Connection conn = null;
		
		List<RAbNormal> abnormals = new ArrayList<RAbNormal>();
		String errorMsg = "";
		try{
			
			CenedEnv env = new CenedEnv((String)ctx.get("connessione"), ctx);
			String folderNameFrom = env.getPercorsoFiles();
			/*
			 * Verifico l'esistenza della cartella ELABORATI che conterrà le forniture 
			 * processate 
			 */
			File fElaborati = new File(folderNameFrom + "ELABORATI");
			if (!fElaborati.exists()){
				boolean creataElaborati = fElaborati.mkdir();
				if (creataElaborati){
					log.info("Cartella ELABORATI creata con successo");				
				}else{
					log.info("Impossibile creare cartella ELABORATI");
				}
			}else{
				log.info("Cartella ELABORATI gia esistente");
			}

			String fileNameCsv = "";
			String fileCurrent = "";
			File f = new File (folderNameFrom);
			String[] files = f.list();
			boolean elaborato = false;
			int cnt = 0;
			if (files != null){
				for (int index=0; index<files.length; index++){
					File file = new File(folderNameFrom + files[index]);
					fileCurrent = file.getName();
					if (!file.isDirectory()){
						if (files[index].endsWith(".csv") || files[index].endsWith(".CSV")){
							elaborato = true;
							fileNameCsv = file.getName();
							/*
							 * Lo split del file con separatore = virgola
							 * e delimitatore testo nel caso di presenza della stessa
							 * virgola con doppie virgolette "bla bla, bla"
							 */
							String line = "";
							BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
							
//							String otherThanQuote = " [^\"] ";
//					        String quotedString = String.format(" \" %s* \" ", otherThanQuote);
//					        String regex = String.format("(?x) "+ // enable comments, ignore white spaces
//					                ",                         "+ // match a comma
//					                "(?=                       "+ // start positive look ahead
//					                "  (                       "+ //   start group 1
//					                "    %s*                   "+ //     match 'otherThanQuote' zero or more times
//					                "    %s                    "+ //     match 'quotedString'
//					                "  )*                      "+ //   end group 1 and repeat it zero or more times
//					                "  %s*                     "+ //   match 'otherThanQuote'
//					                "  $                       "+ // match the end of the string
//					                ")                         ", // stop positive look ahead
//					                otherThanQuote, quotedString, otherThanQuote);
//					        System.out.println("REGEX: " + regex); //(?x),(?=([^"]*"[^"]*")*[^"]*$)
					        
					        conn = env.getConn();
					        /*
					         * Recupero del nome dell'ente
					         */
							String sql1 = "select DESCRIZIONE FROM " + nomeSchemaDiogene + "." + SIT_ENTE + " WHERE CODENT = '" + ctx.getBelfiore() + "' ";
							CallableStatement cs = conn.prepareCall(sql1, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
							ResultSet rs1 = cs.executeQuery();
							Hashtable<String, String> htContenutoZip = new Hashtable<String, String>();
							String nomeEnte = "";
							while (rs1.next()) {
								nomeEnte = rs1.getString("DESCRIZIONE");
							}
							DbUtils.close(rs1);
							DbUtils.close(cs);

							if (nomeEnte != null && !nomeEnte.trim().equalsIgnoreCase("")){
								
								/*
								 * Cancello tutte le righe di CENED e DATI_TEC_FABBR_CERT_ENER
								 */
								String sql2 = "delete from " + nomeSchemaDiogene + "." + CENED;
								CallableStatement cs2 = conn.prepareCall(sql2);
								cs2.execute();
								DbUtils.close(cs2);
								log.info(sql2);
								
								sql2 = "delete from " + nomeSchemaDiogene + "." + DATI_TEC_FABBR_CERT_ENER;
								cs2 = conn.prepareCall(sql2);
								cs2.execute();
								DbUtils.close(cs2);
								log.info(sql2);
								
								String sql = "INSERT INTO " + nomeSchemaDiogene + "." + CENED + " " +
										"(CODICE_IDENTIFICATIVO_PRATICA, DATA_CHIUSURA_PRATICA, INDIRIZZO, PROVINCIA, COMUNE, SEZIONE, FOGLIO, PARTICELLA, SUBALTERNO, NOME_CERTIFICATORE, COGNOME_CERTIFICATORE, "
										+ "EDIFICIO_PUBBLICO, DESTINAZIONE_DI_USO, ANNO_COSTRUZIONE, MOTIVAZIONE_APE, SUPERFICIE_LORDA, SUPERFICIE_NETTA, VOLUME_LORDO, VOLUME_NETTO, SUPERFICIE_DISPERDENTE, "
										+ "SUPERFICIE_VETRATA_OPACA, TRASMITTANZA_MEDIA_INVOLUCRO, TRASMITTANZA_MEDIA_COPERTURA, TRASMITTANZA_MEDIA_BASAMENTO, TRASMITTANZA_MEDIA_SERRAMENTO, CLASSE_ENERGETICA, "
										+ "EPH, ETH, ETC, EFER, EMISSIONI_DI_CO2, EPW, EPT, EF_GLOB_MEDIA_RISCALDAMENTO, EF_GLOB_MEDIA_ACQUA_CALDA_SAN, EGHW, TIPOLOGIA_VENTILAZIONE, NUMERO_RICAMBI_ORARI, "
										+ "TIPOLOGIA_PANNELLO_ST, TIPOLOGIA_PANNELLO_FV, SUPERFICIE_CAPTANTE_FV, SUPERFICIE_APERTURA_ST, SUP_PAN_FV_SUP_UTILE, SUP_PAN_ST_SUP_UTILE, TIPOLOGIA_COMBUSTIBILE, "
										+ "TIPOLOGIA_GENERATORE, POTENZA_GENERATORE ) VALUES " +
										"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
								log.info(sql);
								CallableStatement cstmt = conn.prepareCall(sql);
								
								while ((line = br.readLine()) != null) {
								    
							        String[] riga = line.split("(?x),(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
							        
									//System.out.println( line );
									System.out.println("RIGA [data= " + riga[1] + " , indirizzo=" + riga[2] + ", comune=" + riga[4] + "]");
									 /*
									  * faccio insert in CENED delle righe del solo comune di interesse
									  * confrontano il nome ente riga per riga
									  */
									if (riga[4] != null && !riga[4].trim().equalsIgnoreCase("")){
										/*
										 * comune corrente esistente 
										 */
										if ( riga[4].trim().equalsIgnoreCase(nomeEnte.trim()) ){
											/*
											 * Nome ente coincide
											 */
											/*
											 * Testata File:
											 * 0= CODICE_IDENTIFICATIVO_PRATICA
											 * 1= DATA_CHIUSURA_PRATICA
											 * 2= INDIRIZZO
											 * 3= PROVINCIA
											 * 4= COMUNE
											 * 5= SEZIONE
											 * 6= FOGLIO
											 * 7= PARTICELLA
											 * 8= SUBALTERNO
											 * 9= NOME_CERTIFICATORE
											 * 10= COGNOME_CERTIFICATORE
											 * 11= EDIFICIO_PUBBLICO
											 * 12= DESTINAZIONE_DI_USO
											 * 13= ANNO_COSTRUZIONE
											 * 14= MOTIVAZIONE_APE
											 * 15= SUPERFICIE_LORDA
											 * 16= SUPERFICIE_NETTA
											 * 17= VOLUME_LORDO
											 * 18= VOLUME_NETTO
											 * 19= SUPERFICIE_DISPERDENTE
											 * 20= SUPERFICIE_VETRATA_OPACA
											 * 21= TRASMITTANZA_MEDIA_INVOLUCRO
											 * 22= TRASMITTANZA_MEDIA_COPERTURA
											 * 23= TRASMITTANZA_MEDIA_BASAMENTO
											 * 24= TRASMITTANZA_MEDIA_SERRAMENTO
											 * 25= CLASSE_ENERGETICA
											 * 26= EPH
											 * 27= ETH
											 * 28= ETC
											 * 29= EFER
											 * 30= EMISSIONI_DI_CO2
											 * 31= EPW
											 * 32= EPT
											 * 33= EF_GLOB_MEDIA_RISCALDAMENTO
											 * 34= EF_GLOB_MEDIA_ACQUA_CALDA_SAN
											 * 35= EGHW
											 * 36= TIPOLOGIA_VENTILAZIONE
											 * 37= NUMERO_RICAMBI_ORARI
											 * 38= TIPOLOGIA_PANNELLO_ST
											 * 39= TIPOLOGIA_PANNELLO_FV
											 * 40= SUPERFICIE_CAPTANTE_FV
											 * 41= SUPERFICIE_APERTURA_ST
											 * 42= SUP_PAN_FV_SUP_UTILE
											 * 43= SUP_PAN_ST_SUP_UTILE
											 * 44= TIPOLOGIA_COMBUSTIBILE
											 * 45= TIPOLOGIA_GENERATORE
											 * 46= POTENZA_GENERATORE
											 */
											CenedBean cened = new CenedBean();
											cened.setCodiceIdentificativoPratica( !StringUtils.isEmpty(riga[0])?riga[0].trim():"" );
											cened.setDataChiusuraPratica( !StringUtils.isEmpty(riga[1])?riga[1].trim():"" );
											cened.setIndirizzo( !StringUtils.isEmpty(riga[2])?riga[2].trim().replace("\"", ""):"" );
											cened.setProvincia(!StringUtils.isEmpty(riga[3])?riga[3].trim():"");
											cened.setComune(!StringUtils.isEmpty(riga[4])?riga[4].trim():"");	//indirizzo che a volte è delimitato dalle doppie virgolette
											cened.setSezione(!StringUtils.isEmpty(riga[5])?riga[5].trim():"");
											cened.setFoglio(!StringUtils.isEmpty(riga[6])?riga[6].trim():"");
											cened.setParticella(!StringUtils.isEmpty(riga[7])?riga[7].trim():"");
											cened.setSubalterno(!StringUtils.isEmpty(riga[8])?riga[8].trim():"");
											cened.setNomeCertificatore(!StringUtils.isEmpty(riga[9])?riga[9].trim():"");
											cened.setCognomeCertificatore(!StringUtils.isEmpty(riga[10])?riga[10].trim():"");
											cened.setEdificioPubblico(!StringUtils.isEmpty(riga[11])?riga[11].trim():"");
											cened.setDestinazioneDiUso(!StringUtils.isEmpty(riga[12])?riga[12].trim():"");
											cened.setAnnoCostruzione(!StringUtils.isEmpty(riga[13])?riga[13].trim():"");
											cened.setMotivazioneApe(!StringUtils.isEmpty(riga[14])?riga[14].trim():"");
											cened.setSuperficieLorda(!StringUtils.isEmpty(riga[15])?riga[15].trim():"");
											cened.setSuperficieNetta(!StringUtils.isEmpty(riga[16])?riga[16].trim():"");
											cened.setVolumeLordo(!StringUtils.isEmpty(riga[17])?riga[17].trim():"");
											cened.setVolumeNetto(!StringUtils.isEmpty(riga[18])?riga[18].trim():"");
											cened.setSuperficieDisperdente(!StringUtils.isEmpty(riga[19])?riga[19].trim():"");
											cened.setSuperficieVetrataOpaca(!StringUtils.isEmpty(riga[20])?riga[20].trim():"");
											cened.setTrasmittanzaMediaInvolucro(!StringUtils.isEmpty(riga[21])?riga[21].trim():"");
											cened.setTrasmittenzaMediaCopertura(!StringUtils.isEmpty(riga[22])?riga[22].trim():"");
											cened.setTrasmittanzaMediaBasamento(!StringUtils.isEmpty(riga[23])?riga[23].trim():"");
											cened.setTrasmittanzaMediaSerramento(!StringUtils.isEmpty(riga[24])?riga[24].trim():"");
											cened.setClasseEnergetica(!StringUtils.isEmpty(riga[25])?riga[25].trim():"");
											cened.setEph(!StringUtils.isEmpty(riga[26])?riga[26].trim():"");
											cened.setEth(!StringUtils.isEmpty(riga[27])?riga[27].trim():"");
											cened.setEtc(!StringUtils.isEmpty(riga[28])?riga[28].trim():"");
											cened.setEfer(!StringUtils.isEmpty(riga[29])?riga[29].trim():"");
											cened.setEmissioniDiCO2(!StringUtils.isEmpty(riga[30])?riga[30].trim():"");
											cened.setEpw(!StringUtils.isEmpty(riga[31])?riga[31].trim():"");
											cened.setEpt(!StringUtils.isEmpty(riga[32])?riga[32].trim():"");
											cened.setEfGlobMediaRiscaldamento(!StringUtils.isEmpty(riga[33])?riga[33].trim():"");
											cened.setEfGlobMediaAcquaCaldaSan(!StringUtils.isEmpty(riga[34])?riga[34].trim():"");
											cened.setEghw(!StringUtils.isEmpty(riga[35])?riga[35].trim():"");
											cened.setTipologiaVentilazione(!StringUtils.isEmpty(riga[36])?riga[36].trim():"");
											cened.setNumeroRicambiOrari(!StringUtils.isEmpty(riga[37])?riga[37].trim():"");
											cened.setTipologiaPannelloSt(!StringUtils.isEmpty(riga[38])?riga[38].trim():"");
											cened.setTipologiaPannelloFv(!StringUtils.isEmpty(riga[39])?riga[39].trim():"");
											cened.setSuperficieCaptanteFv(!StringUtils.isEmpty(riga[40])?riga[40].trim():"");
											cened.setSuperficieAperturaSt(!StringUtils.isEmpty(riga[41])?riga[41].trim():"");
											cened.setSupPanFvSupUtile(!StringUtils.isEmpty(riga[42])?riga[42].trim():"");
											cened.setSupPanStSupUtile(!StringUtils.isEmpty(riga[43])?riga[43].trim():"");
											cened.setTipologiaCombustibile(!StringUtils.isEmpty(riga[44])?riga[44].trim():"");
											cened.setTipologiaGeneratore(!StringUtils.isEmpty(riga[45])?riga[45].trim():"");
											cened.setPotenzaGeneratore(!StringUtils.isEmpty(riga[46])?riga[46].trim():"");
											
											cstmt.setString(1, cened.getCodiceIdentificativoPratica() );
											cstmt.setString(2, cened.getDataChiusuraPratica() );
											cstmt.setString(3, cened.getIndirizzo() );
											cstmt.setString(4, cened.getProvincia() );
											cstmt.setString(5, cened.getComune() );
											cstmt.setString(6, cened.getSezione() );
											cstmt.setString(7, cened.getFoglio() );
											cstmt.setString(8, cened.getParticella() );
											cstmt.setString(9, cened.getSubalterno() );
											cstmt.setString(10, cened.getNomeCertificatore() );
											cstmt.setString(11, cened.getCognomeCertificatore() );
											cstmt.setString(12, cened.getEdificioPubblico() );
											cstmt.setString(13, cened.getDestinazioneDiUso() );
											cstmt.setString(14, cened.getAnnoCostruzione() );
											cstmt.setString(15, cened.getMotivazioneApe() );
											cstmt.setString(16, cened.getSuperficieLorda() );
											cstmt.setString(17, cened.getSuperficieNetta() );
											cstmt.setString(18, cened.getVolumeLordo() );
											cstmt.setString(19, cened.getVolumeNetto() );
											cstmt.setString(20, cened.getSuperficieDisperdente() );
											cstmt.setString(21, cened.getSuperficieVetrataOpaca() );
											cstmt.setString(22, cened.getTrasmittanzaMediaInvolucro() );
											cstmt.setString(23, cened.getTrasmittenzaMediaCopertura() );
											cstmt.setString(24, cened.getTrasmittanzaMediaBasamento() );
											cstmt.setString(25, cened.getTrasmittanzaMediaSerramento() );
											cstmt.setString(26, cened.getClasseEnergetica() );
											cstmt.setString(27, cened.getEph() );
											cstmt.setString(28, cened.getEth() );
											cstmt.setString(29, cened.getEtc() );
											cstmt.setString(30, cened.getEfer() );
											cstmt.setString(31, cened.getEmissioniDiCO2() );
											cstmt.setString(32, cened.getEpw() );
											cstmt.setString(33, cened.getEpt() );
											cstmt.setString(34, cened.getEfGlobMediaRiscaldamento() );
											cstmt.setString(35, cened.getEfGlobMediaAcquaCaldaSan() );
											cstmt.setString(36, cened.getEghw() );
											cstmt.setString(37, cened.getTipologiaVentilazione() );
											cstmt.setString(38, cened.getNumeroRicambiOrari() );
											cstmt.setString(39, cened.getTipologiaPannelloSt() );
											cstmt.setString(40, cened.getTipologiaPannelloFv() );
											cstmt.setString(41, cened.getSuperficieCaptanteFv() );
											cstmt.setString(42, cened.getSuperficieAperturaSt() );
											cstmt.setString(43, cened.getSupPanFvSupUtile() );
											cstmt.setString(44, cened.getSupPanStSupUtile() );
											cstmt.setString(45, cened.getTipologiaCombustibile() );
											cstmt.setString(46, cened.getTipologiaGeneratore() );
											cstmt.setString(47, cened.getPotenzaGeneratore() );

											boolean okInsert = cstmt.execute();
											if (!okInsert){
												System.out.println("INSERIMENTO EFFETTUATO IN CENED [identificativo pratica= " + cened.getCodiceIdentificativoPratica() + "]");
												cnt++;
											}else
												System.out.println("IMPOSSIBILE EFFETTUARE INSERIMENTO IN CENED [identificativo pratica= " + cened.getCodiceIdentificativoPratica() + "]");
											
										}else{
											/*
											 * Nome ente non coincide
											 */
										}
									}else{
										/*
										 * comune corrente non esistente
										 */
									}
								}
								DbUtils.close(cstmt);
								
							}else{
								System.out.println(" Impossibile recuperare NOME ENTE per belfiore " + ctx.getBelfiore() + "; ");
								errorMsg += " Impossibile recuperare NOME ENTE per belfiore " + ctx.getBelfiore() + "; ";
							}
							
							conn.commit();
							
							br.close();

						}
					}
					/*
					 * Sposto il file esaminato se CSV
					 */
					if (elaborato){
						file.renameTo( new File(folderNameFrom + "/ELABORATI/" + fileNameCsv) );
						log.info("Fornitura CSV spostata in elaborati: " + fileNameCsv);
					}else{
						log.info("Fornitura NON CSV ignorata: " + fileCurrent);
						errorMsg += "Fornitura NON CSV ignorata: " + fileCurrent;
					}
				}
			}
			/*
			 * TODO Travaso i record inseriti oggi del comune corrente nella tabella 
			 * definitiva
			 */
			String sql = "INSERT INTO " + nomeSchemaDiogene + "." + DATI_TEC_FABBR_CERT_ENER + " " +
					"(ID, CODICE_IDENTIFICATIVO_PRATICA, DATA_PROT, INDIRIZZO, PROVINCIA, COMUNE, SEZIONE, FOGLIO, PARTICELLA, SUB_TUTTI, DENOM_CERTIFICATORE, EDIFICIO_PUBBLICO, "
					+ " DESTINAZIONE_DI_USO, ANNO_COSTRUZIONE, MOTIVAZIONE_ACE, SUPERFICIE_LORDA, SUPERFICIE_NETTA, VOLUME_LORDO, VOLUME_NETTO, SUPERFICIE_DISPERDENTE, "
					+ " SUPERFICIE_VETRATA_OPACA, TRASMITTANZA_MEDIA_INVOLUCRO, TRASMITTANZA_MEDIA_COPERTURA, TRASMITTANZA_MEDIA_BASAMENTO, TRASMITTANZA_MEDIA_SERRAMENTO, "
					+ " CLASSE_ENERGETICA, EPH, ETH, ETC, EFER, EMISSIONI, EPW, EPT, EF_GLOB_MEDIA_RISCALDAMENTO, EF_GLOB_MEDIA_ACQUA_CALDA_SAN, EGHW, TIPOLOGIA_VENTILAZIONE, "
					+ " NUMERO_RICAMBI_ORARI, TIPOLOGIA_PANNELLO_ST, TIPOLOGIA_PANNELLO_FV, SUPERFICIE_CAPTANTE_FV, SUPERFICIE_APERTURA_ST, SUP_PAN_FV_SUP_UTILE, SUP_PAN_ST_SUP_UTILE, "
					+ " TIPOLOGIA_COMBUSTIBILE, TIPOLOGIA_GENERATORE, POTENZA_GENERATORE, BELFIORE, DT_SCA_VALIDITA ) VALUES " +
					"( " + nomeSchemaDiogene + ".SEQ_CENED.nextval ,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			log.info(sql);
			CallableStatement cstmt = conn.prepareCall(sql);
			if (cnt > 0){
				String sql1 = "select * FROM " + nomeSchemaDiogene + "." + CENED + " ";
				CallableStatement cs1 = conn.prepareCall(sql1, ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY );
				log.info(sql1);
				ResultSet rs1 = cs1.executeQuery();
				while (rs1.next()) {

					String codiceIdentificativoPratica = rs1.getString("CODICE_IDENTIFICATIVO_PRATICA");
					String dataChiusuraPratica = rs1.getString("DATA_CHIUSURA_PRATICA");
					String indirizzo = rs1.getString("INDIRIZZO");
					String provincia = rs1.getString("PROVINCIA");
					String comune = rs1.getString("COMUNE");
					String sezione = rs1.getString("SEZIONE");
					String foglio = rs1.getString("FOGLIO");
					String particella = rs1.getString("PARTICELLA");
					String subalterno = rs1.getString("SUBALTERNO");
					String nomeCertificatore = rs1.getString("NOME_CERTIFICATORE");
					String cognomeCertificatore = rs1.getString("COGNOME_CERTIFICATORE");
					String edificioPubblico = rs1.getString("EDIFICIO_PUBBLICO");
					String destinazioneDiUso = rs1.getString("DESTINAZIONE_DI_USO");
					String annoCostruzione = rs1.getString("ANNO_COSTRUZIONE");
					String motivazioneApe = rs1.getString("MOTIVAZIONE_APE");
					String superficieLorda = rs1.getString("SUPERFICIE_LORDA");
					String superficieNetta = rs1.getString("SUPERFICIE_NETTA");
					String volumeLordo = rs1.getString("VOLUME_LORDO");
					String volumeNetto = rs1.getString("VOLUME_NETTO");
					String superficieDisperdente = rs1.getString("SUPERFICIE_DISPERDENTE");
					String superficieVetrataOpaca = rs1.getString("SUPERFICIE_VETRATA_OPACA");
					String trasmittanzaMediaInvolucro = rs1.getString("TRASMITTANZA_MEDIA_INVOLUCRO");
					String trasmittanzaMediaCopertura = rs1.getString("TRASMITTANZA_MEDIA_COPERTURA");
					String trasmittanzaMediaBasamento = rs1.getString("TRASMITTANZA_MEDIA_BASAMENTO");
					String trasmittanzaMediaSerramento = rs1.getString("TRASMITTANZA_MEDIA_SERRAMENTO");
					String classeEnergetica = rs1.getString("CLASSE_ENERGETICA");
					String eph = rs1.getString("EPH");
					String eth = rs1.getString("ETH");
					String etc = rs1.getString("ETC");
					String efer = rs1.getString("EFER");
					String emissioniDiCo2 = rs1.getString("EMISSIONI_DI_CO2");
					String epw = rs1.getString("EPW");
					String ept = rs1.getString("EPT");
					String efGlobalMediaRiscaldamento = rs1.getString("EF_GLOB_MEDIA_RISCALDAMENTO");
					String efGlobalMediaAcquaCaldaSan = rs1.getString("EF_GLOB_MEDIA_ACQUA_CALDA_SAN");
					String eghw = rs1.getString("EGHW");
					String tipologiaVentilazione = rs1.getString("TIPOLOGIA_VENTILAZIONE");
					String numeroRicambiOrari = rs1.getString("NUMERO_RICAMBI_ORARI");
					String tipologiaPannelloSt = rs1.getString("TIPOLOGIA_PANNELLO_ST");
					String tipologiaPannelloFv = rs1.getString("TIPOLOGIA_PANNELLO_FV");
					String superficieCaptanteFv = rs1.getString("SUPERFICIE_CAPTANTE_FV");
					String superficieAperturaSt = rs1.getString("SUPERFICIE_APERTURA_ST");
					String supPanFvSupUtile = rs1.getString("SUP_PAN_FV_SUP_UTILE");
					String supPanStSupUtile = rs1.getString("SUP_PAN_ST_SUP_UTILE");
					String tipologiaCombustibile = rs1.getString("TIPOLOGIA_COMBUSTIBILE");
					String tipologiaGeneratore = rs1.getString("TIPOLOGIA_GENERATORE");
					String potenzaGeneratore = rs1.getString("POTENZA_GENERATORE");

					// !StringUtils.isEmpty(riga[0])?riga[0].trim():""

					/*
					 * ID [sequence]
					 * 1= CODICE_IDENTIFICATIVO_PRATICA
					 * 2= DATA_PROT
					 * 3= INDIRIZZO
					 * 4= PROVINCIA
					 * 5= COMUNE
					 * 6= SEZIONE
					 * 7= FOGLIO
					 * 8= PARTICELLA
					 * 9= SUB_TUTTI
					 * 10= DENOM_CERTIFICATORE
					 * 11= EDIFICIO_PUBBLICO
					 * 12= DESTINAZIONE_DI_USO
					 * 13= ANNO_COSTRUZIONE
					 * 14= MOTIVAZIONE_ACE
					 * 15= SUPERFICIE_LORDA
					 * 16= SUPERFICIE_NETTA
					 * 17= VOLUME_LORDO
					 * 18= VOLUME_NETTO
					 * 19= SUPERFICIE_DISPERDENTE
					 * 20= SUPERFICIE_VETRATA_OPACA
					 * 21= TRASMITTANZA_MEDIA_INVOLUCRO
					 * 22= TRASMITTANZA_MEDIA_COPERTURA
					 * 23= TRASMITTANZA_MEDIA_BASAMENTO
					 * 24= TRASMITTANZA_MEDIA_SERRAMENTO
					 * 25= CLASSE_ENERGETICA
					 * 26= EPH
					 * 27= ETH
					 * 28= ETC
					 * 29= EFER
					 * 30= EMISSIONI
					 * 31= EPW
					 * 32= EPT
					 * 33= EF_GLOB_MEDIA_RISCALDAMENTO
					 * 34= EF_GLOB_MEDIA_ACQUA_CALDA_SAN
					 * 35= EGHW
					 * 36= TIPOLOGIA_VENTILAZIONE
					 * 37= NUMERO_RICAMBI_ORARI
					 * 38= TIPOLOGIA_PANNELLO_ST
					 * 39= TIPOLOGIA_PANNELLO_FV
					 * 40= SUPERFICIE_CAPTANTE_FV
					 * 41= SUPERFICIE_APERTURA_ST
					 * 42= SUP_PAN_FV_SUP_UTILE
					 * 43= SUP_PAN_ST_SUP_UTILE
					 * 44= TIPOLOGIA_COMBUSTIBILE
					 * 45= TIPOLOGIA_GENERATORE
					 * 46= POTENZA_GENERATORE
					 * 47= BELFIORE
					 * 48= DT_SCA_VALIDITA
					 */

					// SEQ.nextValue
					cstmt.setString(1, !StringUtils.isEmpty(codiceIdentificativoPratica)?codiceIdentificativoPratica.trim():"" );
					java.util.Date chiusuraPraticaData = !StringUtils.isEmpty(dataChiusuraPratica)? sdf.parse(dataChiusuraPratica.trim()) : null; 
					cstmt.setDate(2,  new Date(chiusuraPraticaData.getTime()) );
					cstmt.setString(3, !StringUtils.isEmpty(indirizzo)?indirizzo.trim():"" );
					cstmt.setString(4, !StringUtils.isEmpty(provincia)?provincia.trim():"" );
					cstmt.setString(5, !StringUtils.isEmpty(comune)?comune.trim():"" );
					cstmt.setString(6, !StringUtils.isEmpty(sezione)?sezione.trim():"" );
					cstmt.setString(7, !StringUtils.isEmpty(foglio)?foglio.trim():"" );
					cstmt.setString(8, !StringUtils.isEmpty(particella)?particella.trim():"" );
					//9= subalternida splittare [vedi sotto]
					String nomCertificatore = !StringUtils.isEmpty(nomeCertificatore)?nomeCertificatore.trim():"";
					String cogCertificatore = !StringUtils.isEmpty(cognomeCertificatore)?cognomeCertificatore.trim():"";
					String denomCertificatore = cogCertificatore + " " + nomCertificatore;
					cstmt.setString(10, denomCertificatore );
					cstmt.setString(11, !StringUtils.isEmpty(edificioPubblico)?edificioPubblico.trim():"" );
					cstmt.setString(12, !StringUtils.isEmpty(destinazioneDiUso)?destinazioneDiUso.trim():"" );
					cstmt.setString(13, !StringUtils.isEmpty(annoCostruzione)?annoCostruzione.trim():"" );
					cstmt.setString(14, !StringUtils.isEmpty(motivazioneApe)?motivazioneApe.trim():"" );
					if (!StringUtils.isEmpty(superficieLorda)){
						cstmt.setDouble(15, new Double(superficieLorda.trim()));	
					}else{
						cstmt.setNull(15, java.sql.Types.DOUBLE);	
					}
					if (!StringUtils.isEmpty(superficieNetta)){
						cstmt.setDouble(16, new Double(superficieNetta.trim()));	
					}else{
						cstmt.setNull(16, java.sql.Types.DOUBLE);	
					}
					if (!StringUtils.isEmpty(volumeLordo)){
						cstmt.setDouble(17, new Double(volumeLordo.trim()));	
					}else{
						cstmt.setNull(17, java.sql.Types.DOUBLE);	
					}
					if (!StringUtils.isEmpty(volumeNetto)){
						cstmt.setDouble(18, new Double(volumeNetto.trim()));	
					}else{
						cstmt.setNull(18, java.sql.Types.DOUBLE);	
					}
					if (!StringUtils.isEmpty(superficieDisperdente)){
						cstmt.setDouble(19, new Double(superficieDisperdente.trim()));	
					}else{
						cstmt.setNull(19, java.sql.Types.DOUBLE);	
					}
					if (!StringUtils.isEmpty(superficieVetrataOpaca)){
						cstmt.setDouble(20, new Double(superficieVetrataOpaca.trim()));	
					}else{
						cstmt.setNull(20, java.sql.Types.DOUBLE);	
					}
					if (!StringUtils.isEmpty(trasmittanzaMediaInvolucro)){
						cstmt.setDouble(21, new Double(trasmittanzaMediaInvolucro.trim()));	
					}else{
						cstmt.setNull(21, java.sql.Types.DOUBLE);	
					}
					if (!StringUtils.isEmpty(trasmittanzaMediaCopertura)){
						cstmt.setDouble(22, new Double(trasmittanzaMediaCopertura.trim()));	
					}else{
						cstmt.setNull(22, java.sql.Types.DOUBLE);	
					}
					if (!StringUtils.isEmpty(trasmittanzaMediaBasamento)){
						cstmt.setDouble(23, new Double(trasmittanzaMediaBasamento.trim()));	
					}else{
						cstmt.setNull(23, java.sql.Types.DOUBLE);	
					}
					if (!StringUtils.isEmpty(trasmittanzaMediaSerramento)){
						cstmt.setDouble(24, new Double(trasmittanzaMediaSerramento.trim()));	
					}else{
						cstmt.setNull(24, java.sql.Types.DOUBLE);	
					}
					cstmt.setString(25, !StringUtils.isEmpty(classeEnergetica)?classeEnergetica.trim():"" );
					if (!StringUtils.isEmpty(eph)){
						cstmt.setDouble(26, new Double(eph.trim()));	
					}else{
						cstmt.setNull(26, java.sql.Types.DOUBLE);	
					}
					if (!StringUtils.isEmpty(eth)){
						cstmt.setDouble(27, new Double(eth.trim()));	
					}else{
						cstmt.setNull(27, java.sql.Types.DOUBLE);	
					}
					if (!StringUtils.isEmpty(etc)){
						cstmt.setDouble(28, new Double(etc.trim()));	
					}else{
						cstmt.setNull(28, java.sql.Types.DOUBLE);	
					}
					if (!StringUtils.isEmpty(efer)){
						cstmt.setDouble(29, new Double(efer.trim()));	
					}else{
						cstmt.setNull(29, java.sql.Types.DOUBLE);	
					}
					if (!StringUtils.isEmpty(emissioniDiCo2)){
						cstmt.setDouble(30, new Double(emissioniDiCo2.trim()));	
					}else{
						cstmt.setNull(30, java.sql.Types.DOUBLE);	
					}
					if (!StringUtils.isEmpty(epw)){
						cstmt.setDouble(31, new Double(epw.trim()));	
					}else{
						cstmt.setNull(31, java.sql.Types.DOUBLE);	
					}
					if (!StringUtils.isEmpty(ept)){
						cstmt.setDouble(32, new Double(ept.trim()));	
					}else{
						cstmt.setNull(32, java.sql.Types.DOUBLE);	
					}
					if (!StringUtils.isEmpty(efGlobalMediaRiscaldamento)){
						cstmt.setDouble(33, new Double(efGlobalMediaRiscaldamento.trim()));	
					}else{
						cstmt.setNull(33, java.sql.Types.DOUBLE);	
					}
					if (!StringUtils.isEmpty(efGlobalMediaAcquaCaldaSan)){
						cstmt.setDouble(34, new Double(efGlobalMediaAcquaCaldaSan.trim()));	
					}else{
						cstmt.setNull(34, java.sql.Types.DOUBLE);	
					}
					if (!StringUtils.isEmpty(eghw)){
						cstmt.setDouble(35, new Double(eghw.trim()));	
					}else{
						cstmt.setNull(35, java.sql.Types.DOUBLE);	
					}
					cstmt.setString(36, !StringUtils.isEmpty(tipologiaVentilazione)?tipologiaVentilazione.trim():"" );
					cstmt.setString(37, !StringUtils.isEmpty(numeroRicambiOrari)?numeroRicambiOrari.trim():"" );
					cstmt.setString(38, !StringUtils.isEmpty(tipologiaPannelloSt)?tipologiaPannelloSt.trim():"" );
					cstmt.setString(39, !StringUtils.isEmpty(tipologiaPannelloFv)?tipologiaPannelloFv.trim():"" );
					cstmt.setString(40, !StringUtils.isEmpty(superficieCaptanteFv)?superficieCaptanteFv.trim():"" );
					cstmt.setString(41, !StringUtils.isEmpty(superficieAperturaSt)?superficieAperturaSt.trim():"" );
					cstmt.setString(42, !StringUtils.isEmpty(supPanFvSupUtile)?supPanFvSupUtile.trim():"" );
					cstmt.setString(43, !StringUtils.isEmpty(supPanStSupUtile)?supPanStSupUtile.trim():"" );
					cstmt.setString(44, !StringUtils.isEmpty(tipologiaCombustibile)?tipologiaCombustibile.trim():"" );
					cstmt.setString(45, !StringUtils.isEmpty(tipologiaGeneratore)?tipologiaGeneratore.trim():"" );
					cstmt.setString(46, !StringUtils.isEmpty(potenzaGeneratore)?potenzaGeneratore.trim():"" );
					cstmt.setString(47, ctx.getBelfiore() );
					/*
					 * Dalla data di chiusura pratica la certificazione dura 10 anni
					 * per cui aggiungiamo tale lasso di tempo per decretare la scadenza
					 * da oggi 13/04/2016 però questa informazione (=scadenza) verrà
					 * rimossa dalla visualizzazione diogene e fascicolo fabbricato
					 * perchè non è presente nella fornitura
					 */
					Calendar cal = Calendar.getInstance();
					cal.setTime(chiusuraPraticaData);
					cal.add(Calendar.YEAR, 10);
					cstmt.setDate(48, new Date(cal.getTimeInMillis()) );

					String[] arySubalterni = null;
					if (subalterno != null)
						arySubalterni = subalterno.split("\\|");
					
					if (arySubalterni != null && arySubalterni.length>0){
						/*
						 * dobbiamo fare tante inser quanti sono i subalterni coinvolti
						 * nella certificazione corrente
						 */
						for (int i=0; i<arySubalterni.length; i++){
							cstmt.setString(9, !StringUtils.isEmpty(arySubalterni[i].trim())?arySubalterni[i].trim():"" );
							boolean okInsert = cstmt.execute();
						}
					}else{
						/*
						 * Se non ci sono subalterni devo comunque fare insert della 
						 * certificazione
						 */
						cstmt.setString(9, "" );
						boolean okInsert = cstmt.execute();
					}
				}

				DbUtils.close(rs1);
				DbUtils.close(cs1);

				conn.commit();

			}else{
				log.info("CENED per il Comune corrente INESISTENTI!!!");
				errorMsg += " CENED per il Comune corrente INESISTENTI!!!";
			}
			
			DbUtils.close(cstmt);
			
	}catch(Exception e){
		e.printStackTrace();
			log.error(e.getMessage());
		} finally {
			try {
				DbUtils.close(conn);
			} catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		
		return new ApplicationAck(abnormals, "Fine Elaborazione - " + errorMsg);
		
	}//-------------------------------------------------------------------------
	
	
	
}

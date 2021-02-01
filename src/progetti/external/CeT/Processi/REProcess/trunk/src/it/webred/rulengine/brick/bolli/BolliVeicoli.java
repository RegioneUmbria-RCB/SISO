package it.webred.rulengine.brick.bolli;

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
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.bolli.bean.BolloVeicoloBean;
import it.webred.rulengine.brick.cened.bean.CenedBean;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.StringUtils;

public class BolliVeicoli  extends Command implements Rule{
	
	protected static org.apache.log4j.Logger log = Logger.getLogger(BolliVeicoli.class.getName());

	private static final String SIT_ENTE = "SIT_ENTE";
	private static final String BOLLI_VEICOLI = "BOLLI_VEICOLI";
	private static final String SEQ_BOLLI = "SEQ_BOLLI";
	
	private Date oggi = new Date(System.currentTimeMillis());
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public BolliVeicoli(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}//-------------------------------------------------------------------------
	
	@Override
	public CommandAck run(Context ctx) throws CommandException {
		CommandAck retAck = null;
		String nomeSchemaDiogene = RulesConnection.getConnections().get("DWH_" + ctx.getBelfiore());
		if (nomeSchemaDiogene == null || nomeSchemaDiogene.equals("")) {
			nomeSchemaDiogene = RulesConnection.getConnections().get("DWH");
		}
		Connection conn = null;
		
		List<RAbNormal> abnormals = new ArrayList<RAbNormal>();
		String errorMsg = "";
		try{
			
			BolliVeicoliEnv env = new BolliVeicoliEnv((String)ctx.get("connessione"), ctx);
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
								 * Cancello tutte le righe di BOLLI_VEICOLI
								 */
								String sql2 = "delete from " + nomeSchemaDiogene + "." + BOLLI_VEICOLI;
								CallableStatement cs2 = conn.prepareCall(sql2);
								cs2.execute();
								DbUtils.close(cs2);
								log.info(sql2);
								
								String sql = " INSERT INTO " + nomeSchemaDiogene + "." + BOLLI_VEICOLI + " " +
										" (ID, TARGA, USO, DESTINAZIONE, PORTATA, FLAG_ANN_MASSA_RIMORC, CILINDRATA, ALIMENTAZIONE, "
										+ " MASSA_RIMORCHIABILE, NUMERO_POSTI, TIPO_ALIMENTAZIONE_IMPIANTO, KW, DT_PRIMA_IMMATRICOLAZIONE, "
										+ " NUMERO_ASSI, COD_SIGLA_EURO, EMISSIONI_CO2, PESO_COMPLESSIVO, CODICE_TELAIO, CODICE_CARROZZERIA, "
										+ " POTENZA_FISCALE, SOSPENSIONE_PNEUMATICA, COMUNE_ISTATC, PROVINCIA_ISTATP, TIPOSOGGETTO, "
										+ " CODICEFISCALE_PIVA, COGNOME, RAGIONESOCIALE, NOME, CODICESESSO, DATANASCITA, COMUNENASCITA, "
										+ " PROVINCIANASCITA, COMUNERESIDENZA, PROVINCIARESIDENZA, INDIRIZZO, NUMEROCIVICO, CAP, "
										+ " REGIONERESIDENZA, DATAINIZIOPROPRIETA, DATAFINEPROPRIETA, ESENZIONE, DATAINIZIOESENZIONE, "
										+ " DATAFINEESENZIONE, STATOESENZIONE, TIPOHANDICAP, ENTE, DATA_INSERIMENTO ) VALUES " +
										"(" + nomeSchemaDiogene + "." + SEQ_BOLLI + ".nextVal, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
								log.info(sql);
								CallableStatement cstmt = conn.prepareCall(sql);
								int cnt = 0;
								while ((line = br.readLine()) != null) {
								    
							        String[] riga = line.split("(?x);(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
									
//									String otherThanQuote = " [^\"] ";
//							        String quotedString = String.format(" \" %s* \" ", otherThanQuote);
//							        String regex = String.format("(?x) "+ // enable comments, ignore white spaces
//							                ";                         "+ // match a semicolon
//							        		"(?=                       "+ // start positive look ahead
//							                "  (                       "+ //   start group 1
//							                "    %s*                   "+ //     match 'otherThanQuote' zero or more times
//							                "    %s                    "+ //     match 'quotedString'
//							                "  )*                      "+ //   end group 1 and repeat it zero or more times
//							                "  %s*                     "+ //   match 'otherThanQuote'
//							                "  $                       "+ // match the end of the string
//							                ")                         ", // stop positive look ahead
//							                otherThanQuote, quotedString, otherThanQuote);
//							        
//							        String[] riga = line.split(regex, -1);
							        
									//System.out.println( line );
									System.out.println("RIGA [targa= " + riga[0] + " , indirizzo=" + riga[33] + ", civico=" + riga[34] + ", comune=" + riga[31] + ", ISTATC RESIDENZA=" + riga[20] + ", ISTATP RESIDENZA=" + riga[21] + "]");
									 /*
									  * faccio insert in BOLLI_VEICOLI delle righe del solo comune di interesse
									  * confrontano il nome ente riga per riga
									  */
									String istatc = riga[20];
									String istatp = riga[21];
									String comuneRes = riga[31];
									if (riga[31] != null && !riga[31].trim().equalsIgnoreCase("")){
										/*
										 * comune corrente esistente 
										 */
										if ( riga[31].trim().equalsIgnoreCase(nomeEnte.trim()) ){
											/*
											 * Nome ente coincide
											 */
											/*
											 * Testata File Fornitura: 
											 * 0=TARGA; 1=USO; 2=DESTINAZIONE; 3=PORTATA; 4=FLAG_ANN_MASSA_RIMORC; 5=CILINDRATA; 6=ALIMENTAZIONE;
											 * 7=MASSA_RIMORCHIABILE; 8=NUMERO_POSTI; 9=TIPO_ALIMENTAZIONE_IMPIANTO; 10=KW; 11=DT_PRIMA_IMMATRICOLAZIONE;
											 * 12=NUMERO_ASSI; 13=COD_SIGLA_EURO; 14=EMISSIONI_CO2; 15=PESO_COMPLESSIVO; 16=CODICE_TELAIO;
											 * 17=CODICE_CARROZZERIA; 18=POTENZA_FISCALE; 19=SOSPENSIONE_PNEUMATICA; 20=COMUNE; 21=PROVINCIA;
											 * 22=TIPOSOGGETTO; 23=CODICEFISCALE; 24=COGNOME; 25=RAGIONESOCIALE; 26=NOME; 27=CODICESESSO; 28=DATANASCITA;
											 * 29=COMUNENASCITA; 30=PROVINCIANASCITA; 31=COMUNERESIDENZA; 32=PROVINCIARESIDENZA; 33=INDIRIZZO;
											 * 34=NUMEROCIVICO; 35=CAP; 36=REGIONERESIDENZA; 37=INIZIOPROPRIETA; 38=DATAFINEPROPRIETA; 39=ESENZIONE;
											 * 40=DATAINIZIOESENZIONE; 41=DATAFINEESENZIONE; 42=STATOESENZIONE; 43=TIPOHANDICAP
											 */
											BolloVeicoloBean bv = new BolloVeicoloBean();
											/*
											 * questo è il motivo per cui è stata inserita una try catch
											 * RIGA [targa= CL778KC , indirizzo="P;ZA BAUSAN", civico=3, comune=monza, ISTATC RESIDENZA=033, ISTATP RESIDENZA=108]
											 * il carattere separatore è interno alla descrizione del campo indirizzo .... anche se dovrebbe essere possibile 
											 * intercettarlo perche in questo caso ci sono i caratteri di delimitazione del testo: le doppie virgolette
											 */
											try{
												
												bv.setAlimentazione( !StringUtils.isEmpty(riga[6])?riga[6].trim():"" );
												bv.setCap( !StringUtils.isEmpty(riga[35])?riga[35].trim():"" );
												bv.setCilindrata( !StringUtils.isEmpty(riga[5])?riga[5].trim().replace("\"", ""):"" );
												bv.setCodiceCarrozzeria( !StringUtils.isEmpty(riga[17])?riga[17].trim():"");
												bv.setCodiceFiscalePiva( !StringUtils.isEmpty(riga[23])?riga[23].trim():"");
												bv.setCodiceSesso( !StringUtils.isEmpty(riga[27])?riga[27].trim():"");
												bv.setCodiceTelaio( !StringUtils.isEmpty(riga[16])?riga[16].trim():"");
												bv.setCodSiglaEuro( !StringUtils.isEmpty(riga[13])?riga[13].trim():"");
												bv.setCognome( !StringUtils.isEmpty(riga[24])?riga[24].trim():"");
												bv.setComuneIstatC( !StringUtils.isEmpty(riga[20])?riga[20].trim():"");
												bv.setComuneNascita( !StringUtils.isEmpty(riga[29])?riga[29].trim():"");
												bv.setComuneResidenza( !StringUtils.isEmpty(riga[31])?riga[31].trim():"");
												bv.setDataFineEsenzione( !StringUtils.isEmpty(riga[41])?riga[41].trim():"");
												bv.setDataFineProprieta( !StringUtils.isEmpty(riga[38])?riga[38].trim():"");
												bv.setDataInizioEsenzione( !StringUtils.isEmpty(riga[40])?riga[40].trim():"");
												bv.setDataInizioProprieta( !StringUtils.isEmpty(riga[37])?riga[37].trim():"");
												bv.setDataNascita( !StringUtils.isEmpty(riga[28])?riga[28].trim():"");
												bv.setDestinazione( !StringUtils.isEmpty(riga[2])?riga[2].trim():"");
												bv.setDtPrimaImmatricolazione( !StringUtils.isEmpty(riga[11])?riga[11].trim():"");
												bv.setEmissioniCo2( !StringUtils.isEmpty(riga[14])?riga[14].trim():"");
												bv.setEnte( ctx.getBelfiore() );
												bv.setEsenzione( !StringUtils.isEmpty(riga[39])?riga[39].trim():"");
												bv.setFlagAnnMassaRimorc( !StringUtils.isEmpty(riga[4])?riga[4].trim():"");
												//bv.setId( !StringUtils.isEmpty(riga[23])?riga[23].trim():"");
												bv.setIndirizzo( !StringUtils.isEmpty(riga[33])?riga[33].trim():"");
												bv.setKw( !StringUtils.isEmpty(riga[10])?riga[10].trim():"");
												bv.setMassaRimorchiabile( !StringUtils.isEmpty(riga[7])?riga[7].trim():"");
												bv.setNome( !StringUtils.isEmpty(riga[26])?riga[26].trim():"");
												bv.setNumeroAssi( !StringUtils.isEmpty(riga[12])?riga[12].trim():"");
												bv.setNumeroCivico( !StringUtils.isEmpty(riga[34])?riga[34].trim():"");
												bv.setNumeroPosti( !StringUtils.isEmpty(riga[8])?riga[8].trim():"");
												bv.setPesoComplessivo( !StringUtils.isEmpty(riga[15])?riga[15].trim():"");
												bv.setPortata( !StringUtils.isEmpty(riga[3])?riga[3].trim():"");
												bv.setPotenzaFiscale( !StringUtils.isEmpty(riga[18])?riga[18].trim():"");
												bv.setProvinciaIstatP( !StringUtils.isEmpty(riga[21])?riga[21].trim():"");
												bv.setProvinciaNascita( !StringUtils.isEmpty(riga[30])?riga[30].trim():"");
												bv.setProvinciaResidenza( !StringUtils.isEmpty(riga[32])?riga[32].trim():"");
												bv.setRagioneSociale( !StringUtils.isEmpty(riga[25])?riga[25].trim():"");
												bv.setRegioneResidenza( !StringUtils.isEmpty(riga[36])?riga[36].trim():"");
												bv.setSospensionePneumatica( !StringUtils.isEmpty(riga[19])?riga[19].trim():"");
												bv.setStatoEsenzione( !StringUtils.isEmpty(riga[42])?riga[42].trim():"");
												bv.setTarga( !StringUtils.isEmpty(riga[0])?riga[0].trim():"");
												bv.setTipoAlimentazioneImpianto( !StringUtils.isEmpty(riga[9])?riga[9].trim():"");
												bv.setTipoHandicap( !StringUtils.isEmpty(riga[43])?riga[43].trim():"");
												bv.setTipoSoggetto( !StringUtils.isEmpty(riga[22])?riga[22].trim():"");
												bv.setUso( !StringUtils.isEmpty(riga[1])?riga[1].trim():"");
												bv.setDataInserimento(new Date(System.currentTimeMillis()));
	
												/*
												 * Progressivi INSERT
												   1=TARGA, 2=USO, 3=DESTINAZIONE, 4=PORTATA, 5=FLAG_ANN_MASSA_RIMORC, 6=CILINDRATA, 7=ALIMENTAZIONE, 
													8=MASSA_RIMORCHIABILE, 9=NUMERO_POSTI, 10=TIPO_ALIMENTAZIONE_IMPIANTO, 11=KW, 12=DT_PRIMA_IMMATRICOLAZIONE, 
													13=NUMERO_ASSI, 14=COD_SIGLA_EURO, 15=EMISSIONI_CO2, 16=PESO_COMPLESSIVO, 17=CODICE_TELAIO, 18=CODICE_CARROZZERIA,
													19=POTENZA_FISCALE, 20=SOSPENSIONE_PNEUMATICA, 21=COMUNE_ISTATC, 22=PROVINCIA_ISTATP, 23=TIPOSOGGETTO, 
													24=CODICEFISCALE_PIVA, 25=COGNOME, 26=RAGIONESOCIALE, 27=NOME, 28=CODICESESSO, 29=DATANASCITA, 30=COMUNENASCITA, 
													31=PROVINCIANASCITA, 32=COMUNERESIDENZA, 33=PROVINCIARESIDENZA, 34=INDIRIZZO, 35=NUMEROCIVICO, 36=CAP, 
													37=REGIONERESIDENZA, 38=DATAINIZIOPROPRIETA, 39=DATAFINEPROPRIETA, 40=ESENZIONE, 41=DATAINIZIOESENZIONE,
													42=DATAFINEESENZIONE, 43=STATOESENZIONE, 44=TIPOHANDICAP, 45=ENTE, 46=DATA_INSERIMENTO
												 */
	
												
												cstmt.setString(1, bv.getTarga() );
												cstmt.setString(2, bv.getUso() );
												cstmt.setString(3, bv.getDestinazione() );
												if (!StringUtils.isEmpty(bv.getPortata())){
													cstmt.setInt(4, new Integer(bv.getPortata().trim()));	
												}else{
													cstmt.setNull(4, java.sql.Types.INTEGER);	
												}
												cstmt.setString(5, bv.getFlagAnnMassaRimorc() );
												if (!StringUtils.isEmpty(bv.getCilindrata())){
													cstmt.setDouble(6, new Double(bv.getCilindrata().trim().replace(',', '.')));
												}else{
													cstmt.setNull(6, java.sql.Types.DOUBLE);	
												}
												cstmt.setString(7, bv.getAlimentazione() );
												if (!StringUtils.isEmpty(bv.getMassaRimorchiabile())){
													cstmt.setInt(8, new Integer(bv.getMassaRimorchiabile().trim()));
												}else{
													cstmt.setNull(8, java.sql.Types.INTEGER);	
												}
												if (!StringUtils.isEmpty(bv.getNumeroPosti())){
													cstmt.setInt(9, new Integer(bv.getNumeroPosti().trim()));
												}else{
													cstmt.setNull(9, java.sql.Types.INTEGER);	
												}
												cstmt.setString(10, bv.getTipoAlimentazioneImpianto() );
												if (!StringUtils.isEmpty(bv.getKw())){
													cstmt.setDouble(11, new Double(bv.getKw().trim()));
												}else{
													cstmt.setNull(11, java.sql.Types.DOUBLE);	
												}
												java.util.Date dataPrimaimmatricolazione = !StringUtils.isEmpty(bv.getDtPrimaImmatricolazione())? sdf.parse(bv.getDtPrimaImmatricolazione().trim()) : null;
												if (dataPrimaimmatricolazione != null )
													cstmt.setDate(12,  new Date(dataPrimaimmatricolazione.getTime()) );
												else
													cstmt.setNull(12,  java.sql.Types.DATE );
												if (!StringUtils.isEmpty(bv.getNumeroAssi())){
													cstmt.setInt(13, new Integer(bv.getNumeroAssi().trim()));
												}else{
													cstmt.setNull(13, java.sql.Types.INTEGER);	
												}
												cstmt.setString(14, bv.getCodSiglaEuro() );
												if (!StringUtils.isEmpty(bv.getEmissioniCo2())){
													cstmt.setDouble(15, new Double(bv.getEmissioniCo2().trim()));
												}else{
													cstmt.setNull(15, java.sql.Types.DOUBLE);	
												}
												if (!StringUtils.isEmpty(bv.getPesoComplessivo())){
													cstmt.setDouble(16, new Double(bv.getPesoComplessivo().trim()));
												}else{
													cstmt.setNull(16, java.sql.Types.DOUBLE);	
												}
												cstmt.setString(17, bv.getCodiceTelaio() );
												cstmt.setString(18, bv.getCodiceCarrozzeria() );
												if (!StringUtils.isEmpty(bv.getPotenzaFiscale())){
													cstmt.setInt(19, new Integer(bv.getPotenzaFiscale().trim()));
												}else{
													cstmt.setNull(19, java.sql.Types.INTEGER);	
												}
												cstmt.setString(20, bv.getSospensionePneumatica() );
												cstmt.setString(21, bv.getComuneIstatC() );
												cstmt.setString(22, bv.getProvinciaIstatP() );
												cstmt.setString(23, bv.getTipoSoggetto() );
												cstmt.setString(24, bv.getCodiceFiscalePiva() );
												cstmt.setString(25, bv.getCognome() );
												cstmt.setString(26, bv.getRagioneSociale() );
												cstmt.setString(27, bv.getNome() );
												cstmt.setString(28, bv.getCodiceSesso() );
												java.util.Date dataNascita = !StringUtils.isEmpty(bv.getDataNascita())? sdf.parse(bv.getDataNascita().trim()) : null;											
	 											if (dataNascita != null )
													cstmt.setDate(29,  new Date(dataNascita.getTime()) );
												else
													cstmt.setNull(29,  java.sql.Types.DATE );
												cstmt.setString(30, bv.getComuneNascita() );
												cstmt.setString(31, bv.getProvinciaNascita() );
												cstmt.setString(32, bv.getComuneResidenza() );
												cstmt.setString(33, bv.getProvinciaResidenza() );
												cstmt.setString(34, bv.getIndirizzo() );
												cstmt.setString(35, bv.getNumeroCivico() );
												cstmt.setString(36, bv.getCap() );
												cstmt.setString(37, bv.getRegioneResidenza() );
												java.util.Date dataInizioProprieta = !StringUtils.isEmpty(bv.getDataInizioProprieta())? sdf.parse(bv.getDataInizioProprieta().trim()) : null;
												if (dataInizioProprieta != null )
													cstmt.setDate(38,  new Date(dataInizioProprieta.getTime()) );
												else
													cstmt.setNull(38,  java.sql.Types.DATE );
												java.util.Date dataFineProprieta = !StringUtils.isEmpty(bv.getDataFineProprieta())? sdf.parse(bv.getDataFineProprieta().trim()) : null;
												if (dataFineProprieta != null )
													cstmt.setDate(39,  new Date(dataFineProprieta.getTime()) );
												else
													cstmt.setNull(39,  java.sql.Types.DATE );
												cstmt.setString(40, bv.getEsenzione() );
												java.util.Date dataInizioEsenzione = !StringUtils.isEmpty(bv.getDataInizioEsenzione())? sdf.parse(bv.getDataInizioEsenzione().trim()) : null;
												if (dataInizioEsenzione != null )
													cstmt.setDate(41,  new Date(dataInizioEsenzione.getTime()) );
												else
													cstmt.setNull(41,  java.sql.Types.DATE );
												java.util.Date dataFineEsenzione = !StringUtils.isEmpty(bv.getDataFineEsenzione() )? sdf.parse(bv.getDataFineEsenzione().trim()) : null;
												if (dataFineEsenzione != null )
													cstmt.setDate(42,  new Date(dataFineEsenzione.getTime()) );
												else
													cstmt.setNull(42,  java.sql.Types.DATE );
												cstmt.setString(43, bv.getStatoEsenzione() );
												cstmt.setString(44, bv.getTipoHandicap() );
												cstmt.setString(45, bv.getEnte() );
												if ( bv.getDataInserimento() != null ){
													cstmt.setDate(46, bv.getDataInserimento());
												}else{
													cstmt.setNull(46, java.sql.Types.DATE);	
												}
	
												boolean okInsert = cstmt.execute();
												if (!okInsert){
													System.out.println("INSERIMENTO EFFETTUATO IN BOLLI_VEICOLI [targa = " + bv.getTarga() + "]");
													cnt++;
												}else
													System.out.println("IMPOSSIBILE EFFETTUARE INSERIMENTO IN BOLLI_VEICOLI [targa = " + bv.getTarga() + "]");
											
											}catch(ArrayIndexOutOfBoundsException aioobe){
												aioobe.printStackTrace();
												log.error(aioobe.getMessage());
											}
											
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

								conn.commit();
								
								
								
							}else{
								errorMsg += " Impossibile recuperare NOME ENTE per belfiore " + ctx.getBelfiore() + "; ";
								log.info(" Impossibile recuperare NOME ENTE per belfiore " + ctx.getBelfiore() + "; ");
								retAck = new ErrorAck(errorMsg);
							}
							
							br.close();

						}
					}
					/*
					 * Sposto il file esaminato se CSV
					 */
					if (elaborato){
						errorMsg += "Elaborazione terminata con successo: Fornitura CSV spostata in elaborati: " + fileNameCsv;
						file.renameTo( new File(folderNameFrom + "/ELABORATI/" + fileNameCsv) );
						log.info("Elaborazione terminata con successo: Fornitura CSV spostata in elaborati: " + fileNameCsv);
						retAck = new ApplicationAck(abnormals, errorMsg);
					}else{
						log.info("Fornitura NON CSV ignorata: " + fileCurrent);
						errorMsg += "Fornitura NON CSV ignorata: " + fileCurrent;
						retAck = new NotFoundAck(errorMsg);
					}
				}
			}else{
				errorMsg += "Non esistono file da elaborare nella cartella " + folderNameFrom;
				log.info(errorMsg);
				retAck = new NotFoundAck(errorMsg);
			}
			
	}catch(Exception e){
		e.printStackTrace();
		errorMsg += e.getMessage(); 
		log.error(errorMsg);
		retAck = new ErrorAck(errorMsg);
		} finally {
			try {
				DbUtils.close(conn);
			} catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}
		
		//return new ErrorAck("Espressione " + espressione + " non valutata correttamente " + ee.getMessage());

		//return new ApplicationAck(abnormals, "Fine Elaborazione - " + errorMsg);
		return retAck;
		
	}//-------------------------------------------------------------------------
	
	
	
}

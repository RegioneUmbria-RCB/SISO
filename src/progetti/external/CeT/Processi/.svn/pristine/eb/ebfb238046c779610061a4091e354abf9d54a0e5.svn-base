package it.webred.rulengine.brick.carcontrib;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.Utils;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.carcontrib.bean.SoggettoBean;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.UtilityScambioPortale;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ReadXmlPraticheRule extends Command implements Rule {

	private static Logger log = Logger.getLogger(ReadXmlPraticheRule.class
			.getName());
	private Connection conn = null;
	protected HashMap<String, String> esitoLetturaXml;

	private static final String CC = "CartellaContribuente";
	private static final String FF = "FascicoloFabbricato";

	public ReadXmlPraticheRule(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		CommandAck retAck = null;

		FTPClient ftpClient = null;

		try {
			ftpClient = new FTPClient();

			conn = ctx.getConnection((String) ctx.get("connessione"));

			String ftpHost = Utils.getConfigProperty(
					"ftp.host.scambio.portale", ctx.getBelfiore());
			String ftpUser = Utils.getConfigProperty(
					"ftp.user.scambio.portale", ctx.getBelfiore());
			String ftpPwd = Utils.getConfigProperty("ftp.pwd.scambio.portale",
					ctx.getBelfiore());
			String ftpDirCC = Utils.getConfigProperty(
					"ftp.pathcc.scambio.portale", ctx.getBelfiore());
			String ftpDirFF = Utils.getConfigProperty(
					"ftp.pathff.scambio.portale", ctx.getBelfiore());

			ftpClient = UtilityScambioPortale.openConnectionFTP(ftpHost,
					ftpUser, ftpPwd);

			if (ftpClient == null || !ftpClient.isConnected())
				return new NotFoundAck(
						"Impossibile scaricare la cartella dal sito ftp");

			String enteID = ctx.getBelfiore();
			// cartella cittadino
			this.readDirectoryFTP(ftpClient, ftpDirCC, enteID, CC);

			// fascicolo fabbricato
			this.readDirectoryFTP(ftpClient, ftpDirFF, enteID, FF);

			UtilityScambioPortale.closeConnectionFTP(ftpClient);

			retAck = new ApplicationAck("ReadXmlPraticheRule");

		} catch (Exception e) {
			log.error("Errore grave importando i dati", e);
			retAck = new ErrorAck(e);
		} finally {
			try {
				if (ftpClient.isConnected())
					ftpClient.disconnect();
			} catch (IOException e) {
				log.error(e);
			}
		}
		return retAck;
	}

	private String readDirectoryFTP(FTPClient ftpClient, String ftpDir,
			String enteID, String XMLtype) {
		try {
			ftpClient.changeWorkingDirectory(ftpDir);
			FTPFile[] listaFileFtp = ftpClient.listFiles();
			for (FTPFile fileXml : listaFileFtp) {
				if (fileXml.getName().startsWith("."))
					continue;

				if (fileXml.isDirectory()) {
					readDirectoryFTP(ftpClient,
							ftpDir + "/" + fileXml.getName(), enteID, XMLtype);
				}

				boolean inserisciRichiesta = true;
				// IL PROCESSO DI LETTURA DEVE PRENDERE IN CONSIDERAZIONE SOLO I
				// FILE XML E NON I FILE DI LCK
				// CHE SONO FILE GENERATI DAL PORTALE MENTRE LI CREA
				String nomeFileCompleto = fileXml.getName();
				if (fileXml.isFile()
						&& nomeFileCompleto.toString().endsWith("xml")) {
					// se è un file di esito (IDPRATICA_esito.xml) si salta
					if (nomeFileCompleto.toString().endsWith("_esito.xml")) {
						log.info(" FILE DI ESITO " + nomeFileCompleto
								+ " - NON ELABORATO ");
						continue;
					}
					// se è un file di stato (IDPRATICA_change_stato.xml) si
					// salta
					if (nomeFileCompleto.toString().endsWith(
							"_change_stato.xml")) {
						log.info(" FILE DI STATO " + nomeFileCompleto
								+ " - NON ELABORATO ");
						continue;
					}

					String nomeFile = (String) fileXml.getName().subSequence(0,
							fileXml.getName().toString().length() - 4);
					Long idPratica = new Long(nomeFile);

					// SE ESISTE il file IDPRATICA_esito.xml NON si deve
					// prendere in considerazione il file
					if (this.findFileName(nomeFile + "_esito.xml", listaFileFtp)) {
						log.info(" ESISTE IL FILE DI ESITO PER IL FILE "
								+ nomeFileCompleto + " - NON ELABORATO ");
						continue;
					}
					// SE ESISTE il file IDPRATICA__change_stato.xml NON si deve
					// prendere in considerazione il file
					if (this.findFileName(nomeFile + "_change_stato.xml",
							listaFileFtp)) {
						log.info(" ESISTE IL FILE DI STATO PER IL FILE "
								+ nomeFileCompleto + " - NON ELABORATO ");
						continue;
					}
					// SE ESISTE il file IDPRATICA.lck NON is deve prendere in
					// considerazione il file
					if (this.findFileName(nomeFile + ".lck", listaFileFtp)) {
						log.info(" ESISTE IL FILE DI LOCK PER IL FILE "
								+ nomeFileCompleto + " - NON ELABORATO ");
						continue;
					}

					Long idRichiesta = null;
					String esito = null;

					String nomeFileLockLetturaXml = nomeFile + ".lka";
					// System.out.println( " nomeFileLockLetturaXml == " +
					// nomeFileLockLetturaXml);

					// GENERAZIONE FILE DI LOCK PER LA LETTURA FILE XML
					UtilityScambioPortale.uploadFTP(ftpClient,
							nomeFileLockLetturaXml, true, false);

					// log.info(" CREATO FILE DI LOCK APP = " +
					// nomeFileLockLetturaXml);
					File fileLocalXml = null;
					try {// LETTURA DATI FILE XML e INSERT IN DB
							// log.info(" START LETTURA DATI FILE XML e INSERT IN DB");

						SimpleDateFormat formatterDateTime = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss.S");
						SimpleDateFormat formatterDate = new SimpleDateFormat(
								"yyyy-MM-dd");

						fileLocalXml = UtilityScambioPortale.downloadFTP(
								ftpClient, ftpDir, nomeFileCompleto);

						if (fileLocalXml != null) {
							DocumentBuilder builder = null;
							DocumentBuilderFactory factory = DocumentBuilderFactory
									.newInstance();
							factory.setNamespaceAware(true);
							builder = factory.newDocumentBuilder();
							Document doc = builder.parse(fileLocalXml);

							if (doc.getXmlEncoding() == null) {
								// non è presente l'intestazione xml
								fileLocalXml = addXmlHeader(fileLocalXml);
								doc = builder.parse(fileLocalXml);
							}

							Node rootXml = doc.getFirstChild();

							Node nodoSoggettoRichiedente = ((Element) rootXml)
									.getElementsByTagName("soggettoRichiedente")
									.item(0);
							Node nodoSoggettoCartella = ((Element) rootXml)
									.getElementsByTagName("soggettoFruitore")
									.item(0);
							Node nodoPratica = ((Element) rootXml)
									.getElementsByTagName("pratica").item(0);
							Node nodoImmobile = ((Element) rootXml)
									.getElementsByTagName("immobile").item(0);

							String codFiscaleSoggRich = null;
							String cognomeSoggRich = null;
							String nomeSoggRich = null;
							Date dataNascitaSoggRich = null;
							String codComuneNascitaSoggRich = null;
							String email = null;
							String telefono = null;
							Date dataProtocollo = null;
							String codTipMezzoRisp = null;
							String codFiscaleSoggCartella = null;
							String cognomeSoggCartella = null;
							String nomeSoggCartella = null;
							Date dataNascitaSoggCartella = null;
							String codComuneNascitaSoggCartella = null;
							String sezione = null;
							String foglio = null;
							String particella = null;

							if (nodoSoggettoRichiedente != null) {
								codFiscaleSoggRich = ((Element) nodoSoggettoRichiedente)
										.getElementsByTagName("codiceFiscale")
										.item(0).getTextContent();
								cognomeSoggRich = ((Element) nodoSoggettoRichiedente)
										.getElementsByTagName("cognome")
										.item(0).getTextContent();
								nomeSoggRich = ((Element) nodoSoggettoRichiedente)
										.getElementsByTagName("nome").item(0)
										.getTextContent();
								Node nodoDataN = ((Element) nodoSoggettoRichiedente)
										.getElementsByTagName("dataNascita")
										.item(0);
								String str_date = nodoDataN != null ? nodoDataN
										.getTextContent() : null;
								if (str_date != null) {
									try {
										dataNascitaSoggRich = (Date) formatterDateTime
												.parse(str_date);
									} catch (java.text.ParseException e) {
										try {
											dataNascitaSoggRich = (Date) formatterDate
													.parse(str_date);
										} catch (java.text.ParseException e2) {
											System.out
													.println("dataNascitaSoggRich_Exception :"
															+ e2);
										}
									}
								}

								Node nodoCodComuneNascita = ((Element) nodoSoggettoRichiedente)
										.getElementsByTagName("codIstatComune")
										.item(0);
								codComuneNascitaSoggRich = nodoCodComuneNascita != null ? nodoCodComuneNascita
										.getTextContent() : null;

							}
							if (nodoPratica != null) {
								try {
									String str_date = ((Element) nodoPratica)
											.getElementsByTagName("dataPratica")
											.item(0).getTextContent();
									dataProtocollo = (Date) formatterDateTime
											.parse(str_date);
								} catch (java.text.ParseException e) {
									System.out
											.println("dataProtocollo_Exception :"
													+ e);
								}
								email = ((Element) nodoPratica)
										.getElementsByTagName("email").item(0)
										.getTextContent();
								Node nodoTelefono = ((Element) nodoPratica)
										.getElementsByTagName("telefono").item(
												0);
								telefono = nodoTelefono != null ? nodoTelefono
										.getTextContent() : null;
								codTipMezzoRisp = ((Element) nodoPratica)
										.getElementsByTagName("tipoRitiro")
										.item(0).getTextContent();
							}
							if (nodoImmobile != null) {
								Node nodoSezione = ((Element) nodoImmobile)
										.getElementsByTagName("sezione")
										.item(0);
								sezione = nodoSezione != null ? nodoSezione
										.getTextContent() : null;
								foglio = ((Element) nodoImmobile)
										.getElementsByTagName("foglio").item(0)
										.getTextContent();
								particella = ((Element) nodoImmobile)
										.getElementsByTagName("particella")
										.item(0).getTextContent();
							}
							if (nodoSoggettoCartella != null) {
								codFiscaleSoggCartella = ((Element) nodoSoggettoCartella)
										.getElementsByTagName("codiceFiscale")
										.item(0).getTextContent();
								cognomeSoggCartella = ((Element) nodoSoggettoCartella)
										.getElementsByTagName("cognome")
										.item(0).getTextContent();
								nomeSoggCartella = ((Element) nodoSoggettoCartella)
										.getElementsByTagName("nome").item(0)
										.getTextContent();
								try {
									String str_date = ((Element) nodoSoggettoCartella)
											.getElementsByTagName("dataNascita")
											.item(0).getTextContent();
									dataNascitaSoggCartella = (Date) formatterDateTime
											.parse(str_date);
								} catch (java.text.ParseException e) {
									System.out
											.println("dataNascitaSoggCartella_Exception :"
													+ e);
								}
								codComuneNascitaSoggCartella = ((Element) nodoSoggettoCartella)
										.getElementsByTagName("codIstatComune")
										.item(0).getTextContent();
								SoggettoBean soggBean = new SoggettoBean();
								soggBean.setCodFis(codFiscaleSoggCartella);
								soggBean.setCognome(cognomeSoggCartella);
								soggBean.setNome(nomeSoggCartella);
								soggBean.setDtNas(dataNascitaSoggCartella);
								soggBean.setTipoSogg("F");
								log.debug("VERIFICA L'ESISTENZA DEL SOGGETTO PER CUI E' STATA RICHIESTA LA CARTELLA");
								boolean esisteSogg = esisteSoggettoCartella(
										soggBean, enteID);
								if (!esisteSogg) {
									log.info(" Il soggetto per cui è stata richiesta la cartella non esiste in banca dati (DEMOGRAFIA;ICI;TARSU)"
											+ idPratica
											+ " - OPERAZIONE CANCELLATA");
									esito = "5_Soggetto richiesta cartella non presente in banca dati (DEMOGRAFIA;ICI;TARSU)";
									inserisciRichiesta = false;
								}
							}

							if (inserisciRichiesta
									&& (email == null || email.equals("")
											|| codTipMezzoRisp == null || codTipMezzoRisp
												.equals(""))) {// EMAIL e
																// COD_TIP_MEZZO_RISP
																// OBBLIGATORI
								log.info(" Dati importazione insufficenti "
										+ idPratica
										+ " - OPERAZIONE CANCELLATA");
								esito = "5_Dati importazione insufficenti";
								inserisciRichiesta = false;
							}
							// LA PRATICA VA INSERITA SOLO SE NON GIA' PRESENTE
							// COME RICHIESTA
							if (inserisciRichiesta
									&& ((XMLtype.equals(this.CC) && this
											.searchPraticaCCInDB(idPratica)) || (XMLtype
											.equals(this.FF) && this
											.searchPraticaFFInDB(idPratica)))) { // ID
																					// PRATICA
																					// GIA'
																					// PRESENTE
																					// -
																					// OPERAZIONE
																					// CANCELLATA
								log.info(" ID PRATICA "
										+ idPratica.toString()
										+ " GIA' PRESENTE - OPERAZIONE CANCELLATA");
								esito = "1_Pratica già presente nel database delle richieste";
								inserisciRichiesta = false;
							}
							if (inserisciRichiesta) {// INSERIMETNO SOGG E
														// RICHIESTA
								Long idSoggRich = null;
								Long idSoggCartella = null;

								if (nodoSoggettoRichiedente != null
										&& cognomeSoggRich != null
										&& nomeSoggRich != null
										&& dataNascitaSoggRich != null
										&& codFiscaleSoggRich != null) {
									if (XMLtype.equals(this.CC))
										idSoggRich = this
												.insertSoggettoCCIntoDB(
														cognomeSoggRich,
														nomeSoggRich,
														codFiscaleSoggRich,
														dataNascitaSoggRich,
														codComuneNascitaSoggRich);
									if (XMLtype.equals(this.FF))
										idSoggRich = this
												.insertSoggettoFFIntoDB(
														cognomeSoggRich,
														nomeSoggRich,
														codFiscaleSoggRich,
														dataNascitaSoggRich,
														codComuneNascitaSoggRich);
								}

								if (nodoSoggettoCartella != null
										&& cognomeSoggCartella != null
										&& nomeSoggCartella != null
										&& dataNascitaSoggCartella != null
										&& codFiscaleSoggCartella != null) {
									if (XMLtype.equals(this.CC))
										idSoggCartella = this
												.insertSoggettoCCIntoDB(
														cognomeSoggCartella,
														nomeSoggCartella,
														codFiscaleSoggCartella,
														dataNascitaSoggCartella,
														codComuneNascitaSoggCartella);
									if (XMLtype.equals(this.FF))
										idSoggCartella = this
												.insertSoggettoFFIntoDB(
														cognomeSoggCartella,
														nomeSoggCartella,
														codFiscaleSoggCartella,
														dataNascitaSoggCartella,
														codComuneNascitaSoggCartella);
								}

								// CASI:
								// - Sogg Richiedente presente , Sogg Cartella
								// non presente --> sogg Richiedente diventa
								// ANCHE soggetto Cartella
								// - Sogg Richiedente non presente , Sogg
								// Cartella presente --> sogg Richiedente rimane
								// nullo
								// - Sogg Richiedente presente , Sogg Cartella
								// presente --> si passano alla funzione di
								// inserimento richieste
								// - Sogg Richiedente non presente , Sogg
								// Cartella non presente --> non si fa
								// inserimento
								if (idSoggCartella == null)
									idSoggCartella = idSoggRich;

								if (idSoggCartella != null && idPratica != null) {
									esito = "0_OK";
									if (XMLtype.equals(this.CC)) {
										idRichiesta = this
												.insertRichiestaCCIntoDB(
														idPratica,
														dataProtocollo,
														idSoggRich,
														idSoggCartella, email,
														telefono,
														codTipMezzoRisp);
										log.info(" END LETTURA DATI FILE XML e INSERT IN DB ESITO = "
												+ esito);
									}
									if (XMLtype.equals(this.FF)) {
										if (validaFP(foglio, particella)) {
											idRichiesta = this
													.insertRichiestaFFIntoDB(
															idPratica,
															dataProtocollo,
															idSoggRich,
															idSoggCartella,
															email, telefono,
															codTipMezzoRisp,
															sezione, foglio,
															particella);
											log.info(" END LETTURA DATI FILE XML e INSERT IN DB ESITO = "
													+ esito);
										} else {
											log.info("Foglio e/o particella non trovati "
													+ idPratica
													+ " - OPERAZIONE CANCELLATA");
											esito = "5_Foglio e/o particella non trovati";
										}
									}

								} else {
									log.info(" Soggetto cartella e soggetto richiedente non specificati "
											+ idPratica
											+ " - OPERAZIONE CANCELLATA");
									esito = "5_Soggetto cartella e soggetto richiedente non specificati";
								}

							}
						} else {
							log.info(" Errore Download file xml " + idPratica
									+ " - OPERAZIONE CANCELLATA");
							esito = "5_Errore Download file xml";
						}

					} catch (IOException ioEx) {
						System.out.println(" ERRORE IO = " + ioEx.getMessage());
					} catch (Exception ex) {
						System.out.println(" ERRORE " + ex.getMessage());
						log.error(" Errore su lettura file " + nomeFile
								+ " ERRORE = " + ex.getMessage());
						esito = "5_" + ex.getMessage();
					} finally {
						UtilityScambioPortale.deleteLocalFile(fileLocalXml
								.getName());
					}
					// ************************ START ESITO
					// ******************************

					String nomefileEsito = nomeFile + "_esito.xml";
					// log.info(" NOME FILE ESITO " + nomefileEsito);
					String nomefileLockEsito = nomeFile + "_esito.lck";
					// log.info(" NOME FILE LOCK x ESITO " + nomefileLockEsito);

					UtilityScambioPortale.writeXmlFileEsito(ftpClient, ftpDir,
							nomefileEsito, nomefileLockEsito, esito, idPratica,
							idRichiesta);

					// ************************ END ESITO
					// ******************************

					if (inserisciRichiesta) {
						// ************************ START STATO AVANZAMENTO
						// ******************************
						String nomefileStato = nomeFile + "_change_stato.xml";
						// log.info(" NOME FILE ESITO " + nomefileStato);
						String nomefileLockStato = nomeFile
								+ "_change_stato.lck";
						// log.info(" NOME FILE LOCK x ESITO " +
						// nomefileLockStato);

						UtilityScambioPortale.writeXmlFileChangeStato(
								ftpClient, ftpDir + "/stati", nomefileStato,
								nomefileLockStato, idPratica, idRichiesta,
								"10", "10", "In carico all'ente", "");

						// ************************ END STATO AVANZAMENTO
						// ******************************
					}
					// CANCELLAZIONE FILE DI LOCK PER LA LETTURA FILE XML SU FTP
					UtilityScambioPortale.deleteFileFTP(ftpClient, ftpDir,
							nomeFileLockLetturaXml);
					// CANCELLAZIONE FILE DI LOCK PER LA LETTURA FILE XML IN
					// LOCALE
					UtilityScambioPortale
							.deleteLocalFile(nomeFileLockLetturaXml);

					// log.info(" **************  FINE PROCEDURA PER FILE " +
					// nomeFileCompleto);
				} else
					System.out.println(" IL FILE " + fileXml.getName()
							+ " NON E' VALIDO");
			}

			return "";

		} catch (Exception ex) {
			log.error(" readDirectory " + ex.getMessage());
			return ex.getMessage();
		}
	}

	private boolean findFileName(String nomeFileDaFiltrare,
			FTPFile[] listaFileFtp) {
		for (FTPFile fileXml : listaFileFtp) {
			if (fileXml.getName().equals(nomeFileDaFiltrare))
				return true;
		}
		return false;
	}

	private Long insertSoggettoCCIntoDB(String cognome, String nome,
			String codFiscale, Date dataNascita, String codComune)
			throws Exception {
		Long idSogg = null;
		PreparedStatement pstmt = null;

		try {

			pstmt = conn
					.prepareStatement("select ID_SOGG from S_CC_SOGGETTI WHERE COGNOME=? and NOME=? and DT_NAS=? and COD_FIS=?");
			pstmt.setString(1, cognome);
			pstmt.setString(2, nome);
			// TODO: ho tamponato in modotemporaneo solo perche mi arrivava la
			// data alle 23:00 del giorno prima
			pstmt.setDate(3, new java.sql.Date(dataNascita.getTime() + 3600001));
			pstmt.setString(4, codFiscale);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {// SOGGETTO GIA' PRESENTE
				log.info("SOGGETTO RICHIEDENTE GIA' PRESENTE");
				idSogg = rs.getLong(1);
			} else {// INSERIMENTO NUOVO SOGGETTO
				pstmt.clearParameters();

				pstmt = conn
						.prepareStatement("select S_CC_SOGG_SEQ.nextval from dual");
				rs = pstmt.executeQuery();

				if (rs.next()) {
					idSogg = rs.getLong(1);
				}

				// System.out.println(" ID CREATO PER SOGGETTO RICHIEDENTE = "+
				// idSogg);

				pstmt.clearParameters();

				pstmt = conn
						.prepareStatement("INSERT INTO S_CC_SOGGETTI VALUES (?, 'F', ?, ?, ?, ?, ?, null, null)");

				pstmt.setLong(1, idSogg);
				pstmt.setString(2, cognome);
				pstmt.setString(3, nome);
				// TODO: ho tamponato in modotemporaneo solo perche mi arrivava
				// la data alle 23:00 del giorno prima
				pstmt.setDate(4, new java.sql.Date(
						dataNascita.getTime() + 3600001));
				pstmt.setString(5, codComune);
				pstmt.setString(6, codFiscale);

				pstmt.executeUpdate();

			}
		} catch (SQLException e) {
			log.error(e);
			throw new SQLException(e.getMessage());
		} catch (Exception e) {
			log.error(e);
			throw new Exception(e.getMessage());
		}

		finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					log.error(e);
					throw new SQLException(e.getMessage());
				}
		}
		return idSogg;
	}

	private Long insertSoggettoFFIntoDB(String cognome, String nome,
			String codFiscale, Date dataNascita, String codComune)
			throws Exception {
		Long idSogg = null;
		PreparedStatement pstmt = null;

		try {

			pstmt = conn
					.prepareStatement("select ID_SOGG from S_FF_SOGGETTI WHERE COGNOME=? and NOME=? and DT_NAS=? and COD_FIS=?");
			pstmt.setString(1, cognome);
			pstmt.setString(2, nome);
			pstmt.setDate(3, new java.sql.Date(dataNascita.getTime()));
			pstmt.setString(4, codFiscale);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {// SOGGETTO GIA' PRESENTE
				log.info("SOGGETTO RICHIEDENTE GIA' PRESENTE");
				idSogg = rs.getLong(1);
			} else {// INSERIMENTO NUOVO SOGGETTO
				pstmt.clearParameters();

				pstmt = conn
						.prepareStatement("select S_FF_SOGG_SEQ.nextval from dual");
				rs = pstmt.executeQuery();

				if (rs.next()) {
					idSogg = rs.getLong(1);
				}

				// System.out.println(" ID CREATO PER SOGGETTO RICHIEDENTE = "+
				// idSogg);

				pstmt.clearParameters();

				pstmt = conn
						.prepareStatement("INSERT INTO S_FF_SOGGETTI VALUES (?, 'F', ?, ?, ?, ?, ?, null, null)");

				pstmt.setLong(1, idSogg);
				pstmt.setString(2, cognome);
				pstmt.setString(3, nome);
				pstmt.setDate(4, new java.sql.Date(dataNascita.getTime()));
				pstmt.setString(5, codComune);
				pstmt.setString(6, codFiscale);

				pstmt.executeUpdate();

				// System.out.println(" INSERITO SOGGETTO RICHIEDENTE");
			}
		} catch (SQLException e) {
			log.error(e);
			throw new SQLException(e.getMessage());
		} catch (Exception e) {
			log.error(e);
			throw new Exception(e.getMessage());
		}

		finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					log.error(e);
					throw new SQLException(e.getMessage());
				}
		}
		return idSogg;
	}

	private Long insertRichiestaCCIntoDB(Long idPratica, Date dataProtocollo,
			Long idSoggRich, Long idSoggCartella, String email,
			String telefono, String codTipMezzoRisp) throws Exception {
		Long idRichiesta = null;
		PreparedStatement pstmt = null;
		String userName = "portale";

		try {

			pstmt = conn
					.prepareStatement("select S_CC_RICH_SEQ.nextval from dual");
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				idRichiesta = rs.getLong(1);
			}

			System.out.println(" ID CREATO PER RICHIESTA= " + idRichiesta);
			System.out.println(" ID PRATICA PER RICHIESTA= " + idPratica);

			pstmt.clearParameters();

			pstmt = conn
					.prepareStatement("INSERT INTO S_CC_RICHIESTE VALUES (?, SYSDATE, 'C', ?, ?, 'W', ?, ?, ?, null, null, null, null, ?, null, null, ?, ?)");

			pstmt.setLong(1, idRichiesta);
			pstmt.setLong(2, idPratica);
			pstmt.setDate(3, new java.sql.Date(dataProtocollo.getTime()));
			pstmt.setLong(4, idSoggRich);
			pstmt.setString(5, userName);
			pstmt.setLong(6, idSoggCartella);
			pstmt.setString(7, email);
			pstmt.setString(8, telefono);
			pstmt.setString(9, codTipMezzoRisp);

			pstmt.executeUpdate();

			pstmt.clearParameters();

			pstmt = conn
					.prepareStatement("INSERT INTO S_CC_GES_RIC VALUES (?, ?, SYSDATE, null)");

			pstmt.setLong(1, idRichiesta);
			pstmt.setString(2, userName);
			pstmt.executeUpdate();

			System.out.println(" INSERITA RICHIESTA IN DB = " + idRichiesta);

		} catch (SQLException e) {
			log.error(e);
			throw new SQLException(e.getMessage());
		} catch (Exception e) {
			log.error(e);
			throw new Exception(e.getMessage());
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					log.error(e);
					throw new SQLException(e.getMessage());
				}
		}
		return idRichiesta;
	}

	private Long insertRichiestaFFIntoDB(Long idPratica, Date dataProtocollo,
			Long idSoggRich, Long idSoggCartella, String email,
			String telefono, String codTipMezzoRisp, String sezione,
			String foglio, String particella) throws Exception {
		Long idRichiesta = null;
		PreparedStatement pstmt = null;
		String userName = "portale";

		try {

			pstmt = conn
					.prepareStatement("select S_FF_RICH_SEQ.nextval from dual");
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				idRichiesta = rs.getLong(1);
			}

			System.out.println(" ID CREATO PER RICHIESTA= " + idRichiesta);
			System.out.println(" ID PRATICA PER RICHIESTA= " + idPratica);

			pstmt.clearParameters();

			pstmt = conn
					.prepareStatement("INSERT INTO S_FF_RICHIESTE VALUES (?, SYSDATE, 'C', ?, ?, 'W', ?, ?, ?, ?, ?, null, null, null, null, ?, null, null, ?, ?, SYSDATE,?)");

			pstmt.setLong(1, idRichiesta);
			pstmt.setLong(2, idPratica);
			pstmt.setDate(3, new java.sql.Date(dataProtocollo.getTime()));
			pstmt.setLong(4, idSoggRich);
			pstmt.setString(5, userName);
			pstmt.setString(6, sezione);
			pstmt.setString(7, foglio);
			pstmt.setString(8, particella);
			pstmt.setString(9, email);
			pstmt.setString(10, telefono);
			pstmt.setString(11, codTipMezzoRisp);
			pstmt.setLong(12, idSoggCartella);

			pstmt.executeUpdate();

			pstmt.clearParameters();

			pstmt = conn
					.prepareStatement("INSERT INTO S_FF_GES_RIC VALUES (?, ?, SYSDATE, null)");

			pstmt.setLong(1, idRichiesta);
			pstmt.setString(2, userName);
			pstmt.executeUpdate();

			System.out.println(" INSERITA RICHIESTA IN DB = " + idRichiesta);

		} catch (SQLException e) {
			log.error(e);
			throw new SQLException(e.getMessage());
		} catch (Exception e) {
			log.error(e);
			throw new Exception(e.getMessage());
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					log.error(e);
					throw new SQLException(e.getMessage());
				}
		}
		return idRichiesta;
	}

	private boolean searchPraticaCCInDB(Long idPratica) throws Exception {
		PreparedStatement pstmt = null;
		boolean res = false;
		;
		try {
			// LA PRATICA VA INSERITA SOLO SE NON GIA' PRESENTE COME RICHIESTA
			pstmt = conn
					.prepareStatement("select ID_RIC from S_CC_RICHIESTE WHERE NUM_PROT=?");
			pstmt.setLong(1, idPratica);

			res = pstmt.executeQuery().next();
		} catch (SQLException e) {
			log.error(e);
			throw new SQLException(e.getMessage());
		} catch (Exception e) {
			log.error(e);
			throw new Exception(e.getMessage());
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					log.error(e);
					throw new SQLException(e.getMessage());
				}
		}
		return res;
	}

	private boolean searchPraticaFFInDB(Long idPratica) throws Exception {
		PreparedStatement pstmt = null;
		boolean res = false;
		;
		try {
			// LA PRATICA VA INSERITA SOLO SE NON GIA' PRESENTE COME RICHIESTA
			pstmt = conn
					.prepareStatement("select ID_RIC from S_FF_RICHIESTE WHERE NUM_PROT=?");
			pstmt.setLong(1, idPratica);

			res = pstmt.executeQuery().next();
		} catch (SQLException e) {
			log.error(e);
			throw new SQLException(e.getMessage());
		} catch (Exception e) {
			log.error(e);
			throw new Exception(e.getMessage());
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					log.error(e);
					throw new SQLException(e.getMessage());
				}
		}
		return res;
	}

	private boolean esisteSoggettoCartella(SoggettoBean soggBean, String enteID) {

		// todo: modificare questo codice in quanto sembra che per il soggetto
		// RSALRT63C06F704E a monza, venga detto che non esiste in anagrafe
		// quando invece esiste. (Per colpa della data nasciata????)
		// comunque accedere all'indice , alla tabella SIT_SOGGETTO_TOTALE con
		// cf, cognome, nome, data nascita per verificare se esiste. Non sono a
		// demografia e tributi.

		return true;

		/*
		 * boolean esiste=false; //Ricerca in anagrafe
		 * RicercaSoggettoAnagrafeDTO rsa = new RicercaSoggettoAnagrafeDTO();
		 * rsa.setEnteId(enteID); rsa.setCodFis(soggBean.getCodFis());
		 * rsa.setCognome(soggBean.getCognome());
		 * rsa.setNome(soggBean.getNome()); rsa.setDtNas(soggBean.getDtNas());
		 * rsa.setUpperCase(); AnagrafeService anagrafeService =
		 * (AnagrafeService)ServiceLocator.getInstance().getService(
		 * "CT_Service/AnagrafeServiceBean/remote"); List<SoggettoAnagrafeDTO>
		 * listaSoggAna = anagrafeService.searchSoggetto(rsa); if (listaSoggAna
		 * != null && listaSoggAna.size() > 0) {
		 * log.debug("IDENTIFICATO SOGGETTO IN DEMOGRAFIA"); esiste=true; return
		 * esiste; } //Ricerca In Ici RicercaSoggettoIciDTO rsi = new
		 * RicercaSoggettoIciDTO(); rsi.setEnteId(enteID);
		 * rsi.setTipoSogg(soggBean.getTipoSogg());
		 * rsi.setCodFis(soggBean.getCodFis());
		 * rsi.setCognome(soggBean.getCognome());
		 * rsi.setNome(soggBean.getNome()); rsi.setDtNas(soggBean.getDtNas());
		 * //da valorizzare se si introducono nell'xml i dati per la PG
		 * //rsi.setParIva(parIva); String provenienza =
		 * Utils.getConfigProperty("provenienza.dati.ici", enteID,
		 * "param.comune"); if (provenienza==null) provenienza="T";
		 * rsi.setProvenienza(provenienza); rsi.setUpperCase(); IciService
		 * iciService = (IciService)ServiceLocator.getInstance().getService(
		 * "CT_Service/IciServiceBean/remote"); List<SitTIciSogg> listaSoggIci =
		 * iciService.searchSoggetto(rsi); if (listaSoggIci != null &&
		 * listaSoggIci.size() > 0) { log.debug("IDENTIFICATO SOGGETTO IN ICI");
		 * esiste=true; return esiste; } //Ricerca In Tarsu
		 * RicercaSoggettoTarsuDTO rst = new RicercaSoggettoTarsuDTO();
		 * rst.setEnteId(enteID ); rst.setTipoSogg(soggBean.getTipoSogg());
		 * rst.setCodFis(soggBean.getCodFis());
		 * rst.setCognome(soggBean.getCognome());
		 * rst.setNome(soggBean.getNome()); rst.setDtNas(soggBean.getDtNas());
		 * //da valorizzare se si introducono nell'xml i dati per la PG
		 * //rst.setParIva(parIva); //rst.setDenom(denom); provenienza =
		 * Utils.getConfigProperty("provenienza.dati.tarsu",
		 * enteID,"param.comune"); if (provenienza==null) provenienza="T";
		 * rsi.setProvenienza(provenienza); rst.setUpperCase(); TarsuService
		 * tarService = (TarsuService)ServiceLocator.getInstance().getService(
		 * "CT_Service/TarsuServiceBean/remote"); List<SitTTarSogg>
		 * listaSoggTarsu = tarService.searchSoggetto(rst); if (listaSoggTarsu
		 * != null && listaSoggTarsu.size() > 0) {
		 * log.debug("IDENTIFICATO SOGGETTO IN TARSU"); esiste=true; return
		 * esiste; } log.debug("SOGGETTO NON IDENTIFICATO"); return esiste;
		 */

	}

	private boolean validaFP(String foglio, String particella) throws Exception {

		boolean ret = false;
		PreparedStatement pstmt = null;

		if (foglio == null || particella == null)
			return ret;

		try {

			pstmt = conn
					.prepareStatement("select FOGLIO, PARTICELLA from SITIPART WHERE FOGLIO = ? AND PARTICELLA = LPAD(? ,5,'0')");
			pstmt.setLong(1, new Long(foglio));
			pstmt.setString(2, particella);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				ret = true;
			}

		} catch (SQLException e) {
			log.error(e);
			throw new SQLException(e.getMessage());
		} catch (Exception e) {
			log.error(e);
			throw new Exception(e.getMessage());
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					log.error(e);
					throw new SQLException(e.getMessage());
				}
		}

		log.info(" VALIDA FOGLIO: " + foglio + ", PARTICELLA: " + particella
				+ "     " + ret);
		return ret;
	}

	private File addXmlHeader(File file) {

		File fileOk = new File(file.getName());
		BufferedReader reader = null;
		BufferedWriter writer = null;
		ArrayList list = new ArrayList();

		try {
			reader = new BufferedReader(new FileReader(file));
			String tmp;
			while ((tmp = reader.readLine()) != null)
				list.add(tmp);
			reader.close();

			list.add(0,
					"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");

			writer = new BufferedWriter(new FileWriter(fileOk));
			for (int i = 0; i < list.size(); i++)
				writer.write(list.get(i) + "\r\n");
		} catch (Exception e) {
			log.error(e);
		} finally {
			try {
				reader.close();
				writer.close();
			} catch (IOException e) {
				log.error(e);
			}
		}

		return fileOk;
	}
}

package it.webred.rulengine.brick.loadDwh.load.gas;

import it.webred.rulengine.brick.loadDwh.load.gas.bean.Testata;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesWithTipoRecord;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.utils.DateFormat;
import it.webred.utils.FileUtils;
import it.webred.utils.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GasTipoRecordFiles<T extends GasTipoRecordEnv<Testata>> extends ImportFilesWithTipoRecord<T> {
	
	public GasTipoRecordFiles(T env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	
	public String getTipoRecordFromLine(String currentLine)
	throws RulEngineException {
		return currentLine.substring(0,1);
	}

	@Override
	public List<String> getValoriFromLine(String tipoRecord,String currentLine) throws RulEngineException {
		
		String[] ret = null;
		if ("1".equals(tipoRecord)) {
			boolean is2012 = currentLine != null && currentLine.length() >= 6 && Integer.parseInt(currentLine.substring(2, 6)) >= 2012;
			String[] s;
			if (!is2012) {
				s = StringUtils.getFixedFieldsFromString(currentLine,1,1,4,4,16,16,1,80,30,3,1,35,5,13,10,2,77,1);
			} else {
				s = StringUtils.getFixedFieldsFromString(currentLine,1,1,4,4,16,16,1,80,30,3,1,35,5,1,9,4,10,2,1,1,74,1);
			}			
			String anagrafici = s[7];
			String[] s1 = null;
			if("0".equals(s[6]))  // soggetto fisico
				s1 = getAnagValues(s[2], anagrafici);
			else if ("1".equals(s[6]))  { // soggetto giuridico
				s1 = new String[1];
				s1[0] = anagrafici;
			} else 
				throw new RulEngineException("Tipo soggetto anagrafico non previsto :" +s[6] );
		
			ret = new String[29];			
			ret[0] = s[0];
			ret[1] = s[1];
			ret[2] = s[2];
			ret[3] = s[3];
			ret[4] = s[4];
			ret[5] = s[5];
			ret[6] = s[6];
			
			String ragSocSogFis = null;
			if ("0".equals(s[6])) {
				ret[7] = s1[0];
				ret[8] = s1[1];
				ret[9] = s1[2];
				ret[10] = s1[3];
				ret[11] = s1[4];
				ret[12] = s1[5];				
				if ((ret[7] != null && !ret[7].equals("")) &&
					(ret[8] == null || ret[8].equals(""))  &&
					(ret[9] == null || ret[9].equals(""))  &&
					(ret[10] == null || ret[10].equals(""))  &&
					(ret[11] == null || ret[11].equals(""))  &&
					(ret[12] == null || ret[12].equals(""))) {
					ragSocSogFis = ret[7];
					ret[7] = "";
				}
			} else
				ret[13] = s1[0];
			
			if (ragSocSogFis != null) {
				ret[13] = ragSocSogFis;
			}			

			ret[14] = s[8];
			ret[15] = s[9];
			ret[16] = s[10];
			ret[17] = s[11];
			ret[18] = s[12];
			if (!is2012) {
				ret[19] = s[13];
				ret[20] = s[14];
				ret[21] = s[15];
				ret[22] = null;
				ret[23] = null;
				ret[24] = null;
				ret[25] = null;
				ret[26] = null;
				ret[27] = s[16];
				ret[28] = s[17];
			} else {
				ret[19] = null;
				ret[20] = s[15];
				ret[21] = s[17];
				ret[22] = s[13];
				ret[23] = s[14];
				ret[24] = s[16];
				ret[25] = s[18];
				ret[26] = s[19];
				ret[27] = s[20];
				ret[28] = s[21];
			}
		} else if ("9".equals(tipoRecord))  {
			ret = new String[7];
			ret = StringUtils.getFixedFieldsFromString(currentLine,1,9,4,8,184,84,1);
		} else
			throw new RulEngineException("Tipo record non gestito:" +tipoRecord );
			
		return Arrays.asList(ret);
		
	}

	@Override
	public Timestamp getDataExport() throws RulEngineException {
		String dt = env.getTestata().getDataFornitura();
		Date t = it.webred.utils.DateFormat.stringToDate(dt, "yyyyMMdd");
		return new Timestamp(t.getTime());
	}

	/* (non-Javadoc)
	 * Vado a scompattare la fornitura del GAS
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFiles#preProcesing(java.sql.Connection)
	 */
	@Override
	public void preProcesing(Connection con) throws RulEngineException {
		// vado a scompattare la fornitura del GAS, se trovo zip
		 
		Statement st = null;
		try {
			st = con.createStatement();
			st.execute(env.createTableUNO);
		} catch (SQLException e1) {
			log.warn("Tabella esiste già : OK , BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		try {
			st = con.createStatement();
			st.execute(env.RE_GAS_IDX);
		} catch (SQLException e1) {
			log.warn("INDICE esiste già : OK , BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		
		createTabTempNomi(con);
		
	}

	@Override
	public it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.Testata getTestata(String file) throws RulEngineException {
		
			BufferedReader fileIn = null;

			Testata t = new Testata();
			try {
					
					fileIn = new BufferedReader(new FileReader(this.percorsoFiles+file));
					String currentLine=null;
					while ((currentLine = fileIn.readLine()) != null)
					{
						// LEGGO IL RECORD DI TESTATA
						String[] testata = StringUtils.getFixedFieldsFromString(currentLine,1,9,4,8,184 );
						t.setIdentificativoFornitura(testata[1]);
						t.setProgressivoFornitura(testata[2]);
						t.setDataFornitura(testata[3]);
						t.setFiller(testata[4]);
						
						break;
					}
					
					return t;	
			} catch (Exception e) {
				log.error("Errore cercando di leggere la testata del file",e);
				throw new RulEngineException("Errore cercando di leggere la testata del file",e);
			} finally {
				FileUtils.close(fileIn);
			}

		
	}

	@Override
	public void sortFiles(List<String> files) throws RulEngineException {
		Collections.sort(files);
		
	}


	@Override
	public String getProvenienzaDefault() throws RulEngineException {
		return "GAS";
	}


	@Override
	public boolean isIntestazioneSuPrimaRiga() throws RulEngineException {
		return true;
	}


	@Override
	public void tracciaFornitura(String file, String cartellaDati, String line)
			throws RulEngineException {
		// TODO Auto-generated method stub
		
	}

	private String[] getAnagValues(String anno, String anagrafici) {
		int annoRif = 0;
		try {
			annoRif = Integer.parseInt(anno);
		} catch (Exception e) {
		}
		
		if (annoRif >= 2011) {
			while (anagrafici.indexOf("''") > -1) {
				//qui sembrano non esserci i doppi apici, ma si gestiscono lo stesso
				anagrafici = anagrafici.replace("''", "'");
			}
			anagrafici = anagrafici.trim();
			
			String[] dati = anagrafici.split(" ");
			
			//si trova la data di nascita
			int idxData = -1;
			String myData = ""; //deve essere in formato dd/MM/yyyy
			for (int i = 0; i < dati.length; i++) {
				String dato = dati[i];
				Date data = DateFormat.stringToDate(dato, "yyyy-MM-dd"); //si presenta invece in questo formato
				if (data != null && dato.length() == 10 && dato.indexOf("-") == 4 && dato.lastIndexOf("-") == 7) {
					idxData = i;
					myData = dato.substring(8, 10) + "/" + dato.substring(5, 7) + "/" + dato.substring(0, 4);
					break;
				} else {
					//provare comunque anche con il formato corretto
					data = DateFormat.stringToDate(dato, "dd/MM/yyyy");
					if (data != null && dato.length() == 10 && dato.indexOf("/") == 2 && dato.lastIndexOf("/") == 5) {
						idxData = i;
						myData = dato;
						break;
					}
				}
			}
			if (idxData > -1) {
				//il sesso sembra non esserci, in ogni caso si prova a vedere se c'è (M o F tra cognome/nome e data)
				String sesso = "";
				int idxSesso = -1;
				if (idxData > 2 &&
				(dati[idxData - 1].equalsIgnoreCase("M") || dati[idxData - 1].equalsIgnoreCase("F"))) {
					sesso = dati[idxData - 1];
					idxSesso = idxData - 1;
				}
				
				//denominazione (cognome/nome)
				String denominazione = "";
				int myIdx = idxSesso > -1 ? idxSesso : idxData;					
				for (int i = 0; i < myIdx; i++) {
					if (!denominazione.equalsIgnoreCase("")) {
						denominazione += " ";
					}
					denominazione += dati[i].trim();
				}
				
				//qui bisogna separare cognome e nome				
				String cognome = "";
				String nome = "";
				String[] cognomeNome = getCognomeNome(denominazione);
				cognome = cognomeNome[0];
				nome = cognomeNome[1];
				
				//sigla provincia nascita, sembra esserci sempre (2 caratteri) se presenti data e comune di nascita
				String provincia = "";
				int idxProvincia = -1;
				if (dati.length > 4 && dati[dati.length - 1].length() == 2) {
					provincia = dati[dati.length - 1];
					idxProvincia = dati.length - 1;
				}				

				//a questo punto, quello che resta è il comune di nascita
				String comune = "";
				int myIdxFrom = idxData + 1;
				int myIdxTo = idxProvincia > -1 ? idxProvincia : dati.length;
				for (int i = myIdxFrom; i < myIdxTo; i++) {
					if (!comune.equalsIgnoreCase("")) {
						comune += " ";
					}
					comune += dati[i].trim();
				}
				
				//cognome 24 caratteri
				if (cognome.length() > 24) {
					cognome = cognome.substring(0, 24);
				}
				cognome = StringUtils.padding(cognome, 24, ' ', false);
				//nome 20 caratteri
				if (nome.length() > 20) {
					nome = nome.substring(0, 20);
				}
				nome = StringUtils.padding(nome, 20, ' ', false);
				//sesso 1 carattere
				sesso = StringUtils.padding(sesso, 1, ' ', false);
				//data nascita 10 caratteri
				myData = StringUtils.padding(myData, 10, ' ', false);
				//comune nascita 23 caratteri
				if (comune.length() > 23) {
					comune = comune.substring(0, 23);
				}
				comune = StringUtils.padding(comune, 23, ' ', false);
				//provincia nascita 2 caratteri
				provincia = StringUtils.padding(provincia, 2, ' ', false);
				
				return new String[] {cognome, nome, sesso, myData, comune, provincia};
			} else {
				//caso di soggetto fisico con sola ragione sociale, viene restituita solo quella, che poi sarà inserita come ragione sociale (è corretto?)
				String denominazione = "";
				for (int i = 0; i < dati.length; i++) {
					if (!denominazione.equalsIgnoreCase("")) {
						denominazione += " ";
					}
					denominazione += dati[i].trim();
				}
				denominazione = StringUtils.padding(denominazione, 80, ' ', false);
				
				return new String[] {denominazione, "", "", "", "", ""};
			}
		}

		return StringUtils.getFixedFieldsFromString(anagrafici,24,20,1,10,23,2);
	}
	
	private void dropTabTempNomi(Connection con) {
		Statement st = null;
		try {
			st = con.createStatement();
			st.execute("DROP TABLE TMP_ANAG_NOMI");
		} catch (SQLException e1) {
			log.warn("La tabella temporanea dei nomi non esiste o è già stata droppata: OK, BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}		
	}
	
	private void createTabTempNomi(Connection con) {
		dropTabTempNomi(con);
		
		Statement st = null;
		try {
			st = con.createStatement();
			st.execute("CREATE TABLE TMP_ANAG_NOMI AS " +
					"SELECT DISTINCT NOME_UTENTE AS NOME FROM SIT_U_GAS " +
					"WHERE NOME_UTENTE IS NOT NULL " +
					"UNION " +
					"SELECT DISTINCT NOME FROM SIT_D_PERSONA " +
					"WHERE NOME IS NOT NULL");
			
			st = con.createStatement();
			st.execute("CREATE UNIQUE INDEX TMP_ANAG_NOMI_PK ON TMP_ANAG_NOMI (NOME)");
			
			st = con.createStatement();
			st.execute("ALTER TABLE TMP_ANAG_NOMI ADD " +
					"(CONSTRAINT TMP_ANAG_NOMI_PK PRIMARY KEY (NOME) " +
					"USING INDEX TMP_ANAG_NOMI_PK " +
					"ENABLE VALIDATE)");
		} catch (SQLException e1) {
			log.warn("La tabella temporanea dei nomi è già stata creata: OK, BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}		
	}
	
	protected void postElaborazioneAction(String file, List<String> fileDaElaborare, String cartellaFiles) {
		//si opera su una connessione separata
		Connection myConn = null;
		try {
			myConn = RulesConnection.getConnection(env.getConnectionName());
			dropTabTempNomi(myConn);
		} catch (Exception e) {			 
			log.debug(e);
		} finally {
			try	{
				if (myConn != null && !myConn.isClosed()) {
					myConn.close();
				}
			}catch (SQLException e)	{
				log.debug(e);
			}
		}		
	}
	
	private String[] getCognomeNome(String denominazione) {
		if (denominazione != null && !denominazione.trim().equals("")) {
			denominazione = denominazione.trim();
			String[] arrDenom = denominazione.split(" ");
			if (arrDenom.length == 1) {
				return new String[] {arrDenom[0].trim(), ""};
			} else if (arrDenom.length == 2) {
				return new String[] {arrDenom[0].trim(), arrDenom[1].trim()};
			} else {
				String cognome = "";
				String nome = "";
				int idxNome = getDefIdxNome(arrDenom);
				int myIdxNome = idxNome;
				boolean trovato = false;
				while(myIdxNome < arrDenom.length && !trovato) {
					PreparedStatement pstmt = null;
					try {						
						String myNome = "";
						for (int i = myIdxNome; i < arrDenom.length; i++) {
							if (!myNome.equals("")) {
								myNome += " ";
							}
							myNome += arrDenom[i].trim();
						}
						pstmt = con.prepareStatement("SELECT * FROM TMP_ANAG_NOMI WHERE NOME = ?");
						pstmt.setString(1, myNome);
						ResultSet rs = pstmt.executeQuery();
						if (rs.next()) {
							trovato = true;
							break;
						}
					} catch (Exception e) {
						//in caso di eccezione si torna al default
						log.debug(e);
						myIdxNome = getDefIdxNome(arrDenom);
						trovato = true;
						break;
					} finally {
						if (pstmt != null) {
							try {
								pstmt.close();
							} catch (SQLException e) {
								log.debug(e);
							}
						}							
					}
					myIdxNome++;
				}
				if (trovato) {
					idxNome = myIdxNome;
				}
				for (int i = 0; i < idxNome; i++) {
					if (!cognome.equals("")) {
						cognome += " ";
					}
					cognome += arrDenom[i].trim();
				}
				for (int i = idxNome; i < arrDenom.length; i++) {
					if (!nome.equals("")) {
						nome += " ";
					}
					nome += arrDenom[i].trim();
				}
				return new String[] {cognome, nome};
			}			
		}
		return new String[] {"", ""};
	}
	
	private int getDefIdxNome(String[] arrDenom) {
		String primo = arrDenom[0];
		//caso es. DI MARIA PAOLA che verrebbe letto come DI e MARIA PAOLA
		//se necessario integrare l'if
		if (primo.equalsIgnoreCase("di") ||
			primo.equalsIgnoreCase("de") ||
			primo.equalsIgnoreCase("de'") ||
			primo.equalsIgnoreCase("del") ||
			primo.equalsIgnoreCase("dello") ||
			primo.equalsIgnoreCase("della") ||
			primo.equalsIgnoreCase("dei") ||
			primo.equalsIgnoreCase("degli") ||
			primo.equalsIgnoreCase("delle") ||
			primo.equalsIgnoreCase("da") ||
			primo.equalsIgnoreCase("van") ||
			primo.equalsIgnoreCase("von")) {
			return 2;
		}
		return 1;
	}
	
}

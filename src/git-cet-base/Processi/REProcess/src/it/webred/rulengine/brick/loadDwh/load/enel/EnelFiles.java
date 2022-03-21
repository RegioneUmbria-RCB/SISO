package it.webred.rulengine.brick.loadDwh.load.enel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesFlat;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.utils.DateFormat;
import it.webred.utils.StringUtils;

public class EnelFiles<T extends EnelEnv> extends ImportFilesFlat<T> {

	public EnelFiles(T env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Timestamp getDataExport() throws RulEngineException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProvenienzaDefault() throws RulEngineException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getValoriFromLine(String tipoRecord, String currentLine)
			throws RulEngineException {
		
		List<String> campi = new ArrayList<String>();
		
		//viene restituita la riga per intero
		String line = getLine(currentLine);
		campi.add(line);
		
		return campi;
	}

	@Override
	public boolean isIntestazioneSuPrimaRiga() throws RulEngineException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void preProcesing(Connection con) throws RulEngineException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void preProcesingFile(String file) throws RulEngineException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sortFiles(List<String> files) throws RulEngineException {
		Collections.sort(files);
	}

	@Override
	public void tracciaFornitura(String file, String cartellaDati, String line)
			throws RulEngineException {
		// TODO Auto-generated method stub
		
	}
	
	protected void postElaborazioneAction(String file, List<String> fileDaElaborare, String cartellaFiles) {
		//si opera su una connessione separata
		Connection myConn = null;
		Statement st = null;
		try {
			myConn = RulesConnection.getConnection(env.getConnectionName());			
			st = myConn.createStatement();
			st.execute("UPDATE SIT_ENEL_UTENTE SET DENOMINAZIONE = LTRIM(RTRIM(DENOMINAZIONE))");
			myConn.commit();
		} catch (Exception e) {			 
			log.debug(e);
		} finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e) {
				log.debug(e);
			}	
			try	{
				if (myConn != null && !myConn.isClosed()) {
					myConn.close();
				}
			} catch (SQLException e)	{
				log.debug(e);
			}
		}		
	}
	
	private String getLine(String currentLine) {
		int annoRif = 0;
		try {
			annoRif = Integer.parseInt(currentLine.substring(2, 6));
		} catch (Exception e) {
		}
		
		if (annoRif >= 2011) {
			StringBuffer sb = new StringBuffer();
			sb.append(currentLine.substring(0, 43));
			String tipoSoggetto = currentLine.substring(42, 43);
			String anagrafici = currentLine.substring(43, 123);
			while (anagrafici.indexOf("''") > -1) {
				//ci sono i doppi apici...
				anagrafici = anagrafici.replace("''", "'");
			}
			anagrafici = anagrafici.trim();
			if (tipoSoggetto.equals("1") || true) { //annullato l'if con || true, entra sempre qui
				String[] dati = anagrafici.split(" ");
				
				//si trova la data di nascita
				int idxData = -1;
				String myData = ""; //deve essere in formato ddMMyyyy
				for (int i = 0; i < dati.length; i++) {
					String dato = dati[i];
					Date data = DateFormat.stringToDate(dato, "yyyy-MM-dd"); //si presenta invece in questo formato
					if (data != null && dato.length() == 10 && dato.indexOf("-") == 4 && dato.lastIndexOf("-") == 7) {
						idxData = i;
						myData = dato.substring(8, 10) + dato.substring(5, 7) + dato.substring(0, 4);
						break;
					} else {
						//provare comunque anche con il formato corretto
						data = DateFormat.stringToDate(dato, "ddMMyyyy");
						if (data != null && dato.length() == 8 && dato.indexOf("-") == -1) {
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
					
					//denominazione (cognome/nome), va tutto in un unico campo, quindi non serve distinguere cognome e nome
					String denominazione = "";
					int myIdx = idxSesso > -1 ? idxSesso : idxData;					
					for (int i = 0; i < myIdx; i++) {
						if (!denominazione.equalsIgnoreCase("")) {
							denominazione += " ";
						}
						denominazione += dati[i].trim();
					}
					
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
					
					//denominazione 44 caratteri
					if (denominazione.length() > 44) {
						denominazione = denominazione.substring(0, 44);
					}
					denominazione = StringUtils.padding(denominazione, 44, ' ', false);
					sb.append(denominazione);
					//sesso 1 carattere
					sesso = StringUtils.padding(sesso, 1, ' ', false);
					sb.append(sesso);
					//data nascita 8 caratteri
					myData = StringUtils.padding(myData, 8, ' ', false);
					sb.append(myData);
					//comune nascita 25 caratteri
					if (comune.length() > 25) {
						comune = comune.substring(0, 25);
					}
					comune = StringUtils.padding(comune, 25, ' ', false);
					sb.append(comune);
					//provincia nascita 2 caratteri
					provincia = StringUtils.padding(provincia, 2, ' ', false);
					sb.append(provincia);					
				} else {
					//si presume che ci sia solo la denominazione (cognome/nome)
					String denominazione = "";
					for (int i = 0; i < dati.length; i++) {
						if (!denominazione.equalsIgnoreCase("")) {
							denominazione += " ";
						}
						denominazione += dati[i].trim();
					}
					denominazione = StringUtils.padding(denominazione, 80, ' ', false);
					sb.append(denominazione);
				}
			} else {
				anagrafici = StringUtils.padding(anagrafici, 80, ' ', false);
				sb.append(anagrafici);
			}			
			sb.append(currentLine.substring(123, currentLine.length()));
			return sb.toString();
		}
		
		return currentLine;
	}

}

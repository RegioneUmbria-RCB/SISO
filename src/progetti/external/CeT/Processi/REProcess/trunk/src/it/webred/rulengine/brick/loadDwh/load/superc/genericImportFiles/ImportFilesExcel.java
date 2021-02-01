package it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportFactory;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.brick.loadDwh.load.util.Util;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.RulEngineException;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public abstract class ImportFilesExcel<T extends EnvImport> extends
		ImportFiles<T> {

	public ImportFilesExcel(T env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	public abstract HashMap<String, String> elabCurrentRow(int rowNum,
			List<String> campi) throws RulEngineException;
	
	public abstract int numeroColonne();

	// Esegue un azione dopo l'elaborazione del file
	public abstract void postElaborazione(String file,
			List<String> fileDaElaborare, String cartellaFiles);

	@Override
	protected boolean leggiFile(String file, String cartellaDati,
			String tempTable, String tipoRecord, Timestamp dataExport)
			throws Exception {

		boolean lettoqualcosa = false;
		List<String> errori = new ArrayList<String>();

		try {

			if (new File(cartellaDati + "ELABORATI/" + file).exists()) {
				log.debug("Scartato file perche già elaborato " + file);
				RAbNormal abn = new RAbNormal();
				abn.setMessage("Scartato file perche già elaborato " + file);
				abn.setMessageDate(new Timestamp(new Date().getTime()));
				env.getAbnormals().add(abn);
				new File(cartellaDati + file).delete();
				return false;
			}

			// traccia file forniture
			tracciaFornitura(file, cartellaDati, new String());

			InputStream inp = new FileInputStream(cartellaDati + file);
			DataFormatter formatter = new DataFormatter(Locale.ITALIAN);
			Workbook wb = WorkbookFactory.create(inp);
			inp.close();
			
			Sheet sheet = wb.getSheetAt(0);
			Iterator rows = sheet.rowIterator();
			int number = sheet.getLastRowNum();
			System.out.println("LETTURA FILE " + file + " NUMERO DI RIGHE: "
					+ number);
			int rowNum = 1;
			while (rows.hasNext()) {

				List<String> riga = new ArrayList<String>();
				Row row = ((Row) rows.next());
				for(int i = 0; i<numeroColonne();i++) {
					Cell cell = (Cell) row.getCell(i, Row.CREATE_NULL_AS_BLANK);
					String value = formatter.formatCellValue(cell);
					riga.add(value);
				}

				HashMap<String, String> hmCampi = elabCurrentRow(rowNum, riga);

				if(hmCampi != null){
					boolean saved = insertInTmp(hmCampi, tempTable, dataExport,
							errori);
					lettoqualcosa = lettoqualcosa || saved;
				}
				rowNum++;
			}
			
			return lettoqualcosa;

		} finally {
			if (errori.size() > 0) {
				PrintWriter pw = new PrintWriter(cartellaDati + "ELABORATI/"
						+ file + ".err");
				for (int ii = 0; ii < errori.size(); ii++) {
					pw.println(errori.get(ii));
				}
				pw.close();
				throw new RulEngineException(
						"Errore di inserimento ! Prodotto file " + file
								+ ".err");
			}
			
		}
	}

	protected boolean insertInTmp(HashMap<String, String> hmCampi,
			String tempTable, Timestamp dataExport, List<String> errori)
			throws Exception {


		String insertSql = null;
		boolean lettoqualcosa = false;
		java.sql.PreparedStatement ps = null;

		StringBuffer s = new StringBuffer();
		String campi = "";
		String valori = "";
		String valori_errore = "";
		Iterator it = hmCampi.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			campi += pairs.getKey() + ",";
			valori += "?,";
		}
		s.append("INSERT INTO ");
		s.append(tempTable + " (");
		s.append(campi + "processid,re_flag_elaborato,dt_exp_dato)");
		s.append(" VALUES(");
		s.append(valori);

		s.append("?,"); // processid
		s.append("?,"); // re_flag_elaborato
		s.append("?)"); // dt_exp_dato
		insertSql = s.toString();

		try {
			ps = con.prepareStatement(insertSql);

			int ii = 0;
			it = hmCampi.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry) it.next();
				if (pairs.getValue() != null && !pairs.getValue().equals(""))
					ps.setString(++ii, (String) pairs.getValue());
				else
					ps.setNull(++ii, Types.VARCHAR);
				valori_errore += pairs.getValue() + ", ";
			}

			ps.setString(++ii, processId);
			ps.setString(++ii, "0"); // re_flag_elaborato
			ps.setTimestamp(++ii, dataExport); // dt_exp_dato

			ps.executeUpdate();
			lettoqualcosa = true;
		} catch (Exception e) {
			log.error("Errore di inserimento record", e);
			log.error(insertSql);
			errori.add(insertSql + " VALORI: "+ valori_errore +"_____ECCEZIONE: " + e);
		} finally {
			if (ps != null)
				ps.close();
		}

		return lettoqualcosa;
	}

	protected String underscoreFormat(String campo) {

		String campoFormattato = String.valueOf(campo.charAt(0));
		// ad ogni uppercase (65 to 90 ASCII) aggiungo un underscore per i campi
		// della tabella
		for (int i = 1; i < campo.length(); i++) {
			int charPoint = (char) campo.charAt(i);
			char charValue = (char) campo.charAt(i);
			if (charPoint >= 65 && charPoint <= 90)
				campoFormattato += "_" + charValue;
			else
				campoFormattato += charValue;
		}
		return campoFormattato;
	}

	protected void procesingFile(String file, String cartellaDati)
			throws RulEngineException {
		boolean gestisciTmp = false;
		boolean disabilitaStorico = false;
		if (env.getEnteSorgente().isInReplace())
			gestisciTmp = true;

		if (env.getEnteSorgente().isDisabilitaStorico())
			disabilitaStorico = true;

		LinkedHashMap<String, String> tabs = new LinkedHashMap<String, String>();
		ConcreteImport ci = ConcreteImportFactory.getConcreteImport(this);
		tabs = ci.getTabelleAndTipiRecord();

		// METTO IL FILE NELLA TABELLA TEMPORANEA
		// ogni file lo tratto per tutti i tipi record che ha
		for (String key : tabs.keySet()) {
			String tr = tabs.get(key);
			log.info("CARICO " + file);
			try {
				leggiFile(file, cartellaDati, key, tr, getDataExport());
			} catch (Exception e) {
				throw new RulEngineException("Problema in lettura del file "
						+ file + " tr=" + tr, e);
			}
		}
		ci.postLetturaFileAndFilter(cartellaDati, file, gestisciTmp);

		log.info("AVVIO NORMALIZZAZIONE " + file);

		boolean norm = ci.normalizza(super.ctx.getBelfiore());

		log.info("Aggiornamento ocntesto con info per eventuale normalizzazione e reverse dati");
		// mettere su ctx le tabelle DWH
		Map m = new HashMap();
		m.put("reverse.tabelleDWH", ci.getTabelleFinaliDWH());
		m.put("reverse.tabs", tabs);
		ctx.addReverseObjects(m);

		/*
		 * se la normalizzazione non avviene (false) allora non faccio neanche
		 * tutto il giochino di riversamento!!
		 */
		if (norm) {
			if (gestisciTmp) {
				// RIVERSO DA TABELLA TMP A PRODUZIONE
				try {
					ArrayList<String> tabelleDWH = ci.getTabelleFinaliDWH();
					Connection conn = ci.getConnection();
					Util.riversaSetDatiDaTmpADwh(tabelleDWH, conn,disabilitaStorico,env.getEnteSorgente().getInReplaceValue(),ci.getGestoreCorrelazioneVariazioni());
				} catch (Exception e) {
					throw new RulEngineException(e.getMessage(), e);
				}
			}

			// dopo la normalizzazione setto ad elaborati tutti i record che
			// sono rimasti con flag a zero per via del fatto che
			// non avevano chiave
			if (!gestisciTmp) {
				log.info("setReFlagElaboratoConChiaveNullaONoChiave");
				ci.setReFlagElaboratoConChiaveNullaONoChiave();
			} else {
				Connection conn = ci.getConnection();
				for (String key : tabs.keySet()) {
					try {
						log.info("TRUNCATE TABELLA " + key);
						Util.truncateTable(key, conn);
					} catch (Exception e) {
						log.error("ERRORE IN TRUNCATE TABELLA " + key);
						throw new RulEngineException(
								"ERRORE IN TRUNCATE TABELLA " + key, e);
					}

				}
			}
		}

	}

	@Override
	protected void postElaborazioneAction(String file,
			List<String> fileDaElaborare, String cartellaFiles) {
		postElaborazione(file, fileDaElaborare, cartellaFiles);
	}

}

package it.webred.rulengine.brick.loadDwh.load.acqua.tributaria;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.acqua.tributaria.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesWithTipoRecord;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.utils.FileUtils;
import it.webred.utils.StringUtils;

public class AcquaTipoRecordFiles<T extends AcquaTipoRecordEnv<Testata>> extends ImportFilesWithTipoRecord<T> {

	public AcquaTipoRecordFiles(T env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	public String getTipoRecordFromLine(String currentLine)
			throws RulEngineException {
		return currentLine.substring(0, 1);
	}

	@Override
	public List<String> getValoriFromLine(String tipoRecord, String currentLine)
			throws RulEngineException {

		String[] ret = null;
		if ("1".equals(tipoRecord)) {
			String[] s = StringUtils.getFixedFieldsFromString(currentLine,
					1, 16, 24, 20, 1, 8, 40, 2, 60, 40, 2, 1, 30, 1, 1, 8, 40, 4, 1, 1, 3, 5, 5, 4, 4, 1, 2, 9, 9);

			ret = new String[40];
			ret[0] = s[2];//cognome
			ret[1] = s[3];//nome
			ret[2] = s[4];//sesso
			ret[3] = s[5];//dt nasc
			ret[4] = s[6];//comune nasc
			ret[5] = s[7];//pr nasc
			if(s[1].trim().length() == 16)
				ret[6] = s[1];//cod fiscale
			else ret[6] = null;//cod fiscale
			ret[7] = s[8];//denom
			if(s[1].trim().length() == 11)
				ret[8] = s[1];//pi
			else ret[8] = null;//pi
			ret[9] = null;//via res
			ret[10] = null;//civ res
			ret[11] = null;//cap res
			ret[12] = s[9];//com res
			ret[13] = s[10];//pr res
			ret[14] = null;//tel
			ret[15] = null;//fax
			ret[16] = s[12];//cod servizio
			ret[17] = null;//descr categoria
			String cod_qualifica = s[11];
			String qualifica = null;
			if("1".equals(cod_qualifica))
				qualifica = "Proprietario";
			else if("2".equals(cod_qualifica))
				qualifica = "Usufruttario";
			else if("3".equals(cod_qualifica))
				qualifica = "Titolare di altro diritto sull'immobile";
			else if("4".equals(cod_qualifica))
				qualifica = "Rappresentante Legale o Volontario di uno degli aventi diritto";
			ret[18] = qualifica;//qualifica tit
			String cod_tipo = s[13];
			String tipo = null;
			if("1".equals(cod_tipo))
				tipo = "Utenza Domestica con residenza anagrafica presso il luogo di fornitura";
			else if("2".equals(cod_tipo))
				tipo = "Utenza Domestica con residenza diversa dal luogo di fornitura";
			else if("3".equals(cod_tipo))
				tipo = "Utenza Non Domestica";
			ret[19] = tipo;//tipologia
			ret[20] = s[14];//tipo contr
			ret[21] = s[15];//dt utenza
			ret[22] = null;//rag soc ubi
			ret[23] = s[16];//via ubi
			ret[24] = null;//civ ubi
			ret[25] = null;//cap ubi
			ret[26] = s[17];//comune ubi
			ret[27] = s[18];//tipologia ubi
			ret[28] = s[26];//mesi fatt
			ret[29] = s[27];//consumo
			ret[30] = null;//stacco
			ret[31] = null;//giro
			ret[32] = s[28];//fatturato
			cod_tipo = s[19];
			tipo = null;
			if("1".equals(cod_tipo))
				tipo = "Immobile non accatastato";
			else if("2".equals(cod_tipo))
				tipo = "Immobile non accatastabile";
			else if("3".equals(cod_tipo))
				tipo = "Dati non forniti dal titolare dell'utenza";
			else if("4".equals(cod_tipo))
				tipo = "Forniture temporanee";
			else if("5".equals(cod_tipo))
				tipo = "Condominio";
			ret[33] = tipo;//assenza dati cat
			ret[34] = s[20];//sezione
			ret[35] = s[21];//foglio
			ret[36] = s[22];//part
			ret[37] = s[23];//sub
			ret[38] = s[24];//estensione part
			cod_tipo = s[25];
			tipo = null;
			if("F".equals(cod_tipo))
				tipo = "Fondiario";
			else if("E".equals(cod_tipo))
				tipo = "Edificiale";
			ret[39] = tipo;//tipologia part

		/*} else if ("9".equals(tipoRecord)) {
			ret = new String[7];
			ret = StringUtils.getFixedFieldsFromString(currentLine, 1, 9, 4, 8,
					184, 84, 1);*/
		} else
			throw new RulEngineException("Tipo record non gestito:"
					+ tipoRecord);

		return Arrays.asList(ret);

	}

	@Override
	public Timestamp getDataExport() throws RulEngineException {
		return new Timestamp(env.getTestata().getDataA().getTime());
	}

	@Override
	public void preProcesing(Connection con) throws RulEngineException {

		Statement st = null;
		try {
			st = con.createStatement();
			st.execute(env.createTableA);
		} catch (SQLException e1) {
			log.warn("Tabella esiste già : OK , BENE");
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {
			}
		}
		try {
			st = con.createStatement();
			st.execute(env.RE_ACQUA_A_IDX);
		} catch (SQLException e1) {
			log.warn("INDICE esiste già : OK , BENE");
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {
			}
		}

	}

	@Override
	public it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.Testata getTestata(
			String file) throws RulEngineException {

		BufferedReader fileIn = null;

		Testata t = new Testata();
		try {

			fileIn = new BufferedReader(new FileReader(this.percorsoFiles
					+ file));
			String currentLine = null;
			while ((currentLine = fileIn.readLine()) != null) {
				// LEGGO IL RECORD DI TESTATA
				String[] testata = StringUtils.getFixedFieldsFromString(
						currentLine, 1, 5, 2, 1, 17, 16, 60, 40, 2, 24, 20, 1, 8, 40, 2, 4, 16);
				t.setTipoRecord(testata[0]);
				t.setCodiceIdentificativo(testata[1]);
				t.setCodiceNumerico(testata[2]);
				t.setTipoComunicazione(testata[3]);
				t.setProtocolloDaSostituire(testata[4]);
				t.setCodiceFiscale(testata[5]);
				String anno = testata[15];
				SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
				t.setDataDa(sdf.parse("01/01/"+anno));
				t.setDataA(sdf.parse("31/12/"+anno));

				break;
			}

			return t;
		} catch (Exception e) {
			log.error("Errore cercando di leggere la testata del file", e);
			throw new RulEngineException(
					"Errore cercando di leggere la testata del file", e);
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
		return "1";
	}

	@Override
	public boolean isIntestazioneSuPrimaRiga() throws RulEngineException {
		return true;
	}

	@Override
	public void tracciaFornitura(String file, String cartellaDati, String line)
			throws RulEngineException {
		
		env.saveFornitura(env.getTestata().getDataA(), file);
	}

}

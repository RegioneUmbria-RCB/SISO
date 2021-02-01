package it.webred.rulengine.brick.loadDwh.load.acqua.AdE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.acqua.AdE.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesWithTipoRecord;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.utils.FileUtils;
import it.webred.utils.StringUtils;

public class AcquaTipoRecordFiles<T extends AcquaTipoRecordEnv<Testata>> extends ImportFilesWithTipoRecord<T> {
	
	private String dataFornitura = "";

	public AcquaTipoRecordFiles(T env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	public String getTipoRecordFromLine(String currentLine)
			throws RulEngineException {
		return currentLine.substring(0, 1);
	}

	@Override
	public List<String> getValoriFromLine(String tipoRecord, String currentLine) throws RulEngineException {

		String[] ret = null;
		
		if ("1".equals( tipoRecord )) {
			
			try{
				
			String[] s = StringUtils.getFixedFieldsFromString(currentLine, 
					1,	//00=Tipo Record 
					1,  //01=Tipo Fornitura
					4,  //02=Anno riferimento fornitura
					4,  //03=Codice catastale ubicazione utenza
					16, //04=CF erogante
					16, //05=CF titolare utenza
					1,  //06=Tipo soggetto
					80, //07=Dati Anagrafici
					30, //08=Codice identificativo
					3,  //09=Filler
					1,  //10=Tipo Utenza
					35, //11=Indirizzo utenza
					5,  //12=CAP utenza
					1,  //13=Segno ammontare fatturato + o -
					9,  //14=Ammontare fatturato eur
					4,  //15=Filler
					10, //16=Consumo fatturato mc
					2,  //17=Numero mesi fatturazione
					1,  //18=Esito controllo formale del dato
					1,  //19=Esito controllo formale dato dopo riscontro AdE
					74, //20=Spazio a disposizione
					1); //21=Carattere di fine riga

			ret = new String[40];
			ret[0] = null;//cognome
			ret[1] = null;//nome
			ret[2] = null;//sesso
			ret[3] = null;//dt nasc
			ret[4] = null;//comune nasc
			ret[5] = null;//pr nasc
			if( s[6].trim().equalsIgnoreCase("0") ) //Persona fisica
				ret[6] = s[5];//cod fiscale
			else ret[6] = null;//cod fiscale
			ret[7] = s[7];//denom
			if( s[6].trim().equalsIgnoreCase("1") ) //Diverso da Persona fisica
				ret[8] = s[5];//pi
			else ret[8] = null;//pi
			ret[9] = null;//via res
			ret[10] = null;//civ res
			ret[11] = null;//cap res
			ret[12] = null;//com res
			ret[13] = null;//pr res
			ret[14] = null;//tel
			ret[15] = null;//fax
			ret[16] = s[8];//cod servizio
			ret[17] = null;//descr categoria
			String cod_qualifica = "";
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
			String cod_tipo = s[10];
			String tipo = null;
			if("1".equals(cod_tipo))
				tipo = "Utenza Domestica con residenza anagrafica presso il luogo di fornitura";
			else if("2".equals(cod_tipo))
				tipo = "Utenza Domestica con residenza diversa dal luogo di fornitura";
			else if("3".equals(cod_tipo))
				tipo = "Utenza Non Domestica";
			ret[19] = tipo;//tipologia
			ret[20] = s[1];//tipo contr
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//			String dtFornitura = sdf.format( sdf.parse(dataFornitura) );
			Date dtFornitura = sdf.parse( dataFornitura );
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			ret[21] = simpleDateFormat.format(dtFornitura);//dt utenza
			ret[22] = s[7];//rag soc ubi
			ret[23] = s[11];//via ubi
			ret[24] = null;//civ ubi
			ret[25] = s[12];//cap ubi
			ret[26] = s[3];//comune ubi (= in questo tracciato dell'AdE è il codice catastale)
			ret[27] = "";//tipologia ubi
			ret[28] = s[17];//mesi fatt
			ret[29] = s[16];//consumo mc
			ret[30] = null;//stacco
			ret[31] = null;//giro
			if ( s[13].trim().equalsIgnoreCase("-") )
				ret[32] = "-" + s[14];//fatturato eur
			else
				ret[32] = s[14];//fatturato eur
			cod_tipo = "";
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
			ret[34] = "";//sezione
			ret[35] = "";//foglio
			ret[36] = "";//part
			ret[37] = "";//sub
			ret[38] = "";//estensione part
			cod_tipo = "";
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
			
			}catch(Exception pe){
				pe.printStackTrace();
			}
			
		} else
			throw new RulEngineException("Tipo record non gestito:" + tipoRecord);

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
	public it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.Testata getTestata(String file) throws RulEngineException {

		BufferedReader fileIn = null;

		Testata t = new Testata();
		try {

			fileIn = new BufferedReader(new FileReader(this.percorsoFiles + file));
			String currentLine = null;
			while ((currentLine = fileIn.readLine()) != null) {
				// LEGGO IL RECORD DI TESTATA
				String[] testata = StringUtils.getFixedFieldsFromString( currentLine, 1, 4, 1, 4, 4, 8, 184, 93, 1);
				t.setTipoRecord(testata[0]);
				t.setCodiceIdentificativo(testata[1]+testata[2]+testata[3]);
				t.setCodiceNumerico(testata[4]);
				t.setTipoComunicazione("");
				t.setProtocolloDaSostituire("");
				t.setCodiceFiscale("");
				String anno = testata[3];
				SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
				t.setDataDa(sdf.parse("01/01/"+anno));
				t.setDataA(sdf.parse("31/12/"+anno));
				
				this.dataFornitura = testata[5];
				
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

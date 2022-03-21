package it.webred.rulengine.brick.loadDwh.load.ruolo.tarsu;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesFlat;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.utils.DateFormat;
import it.webred.utils.StringUtils;

public class RTarsuFiles<T extends RTarsuEnv> extends ImportFilesFlat<T> {

	public RTarsuFiles(T env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Timestamp getDataExport() throws RulEngineException {
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
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
		currentLine=this.getLine(currentLine);
		campi = new ArrayList(Arrays.asList(currentLine.split("#")));
		
		//String anno = campi.get(19).substring(0, 4);
		String tipo = env.ACCONTO;
		if(campi.size()==406)
			tipo = env.SUPPLETIVO;
		else{
			//Aggiungere 5 righe alla fine
			for(int i=0; i<5; i++)
				campi.add("");
			
			//Aggiungere 3 righe prima della penultima
			String[] x3 = new String[3];
			campi.addAll(337, Arrays.asList(x3));
			
			//Aggiungere 60 righe dopo la 299
			String[] x1 = new String[60];
			campi.addAll(299, Arrays.asList(x1));
			
		}
		campi.add(0,null);
		campi.add(env.anno);
		campi.add(tipo);
		
		return campi;
	}

	@Override
	public boolean isIntestazioneSuPrimaRiga() throws RulEngineException {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void preProcesing(Connection con) throws RulEngineException {
	
		
		String[] campi = env.campiTableA.split("#");
		
		String sql = "CREATE TABLE "+env.tableRE+"(" +
				     "RID  INTEGER PRIMARY KEY,";
		
		for(String c: campi)
			sql+= c+"  VARCHAR2(100 CHAR), ";
		
		sql+=  "PROCESSID          VARCHAR2(50 BYTE), "+
			   "RE_FLAG_ELABORATO  VARCHAR2(1 BYTE), "+
			   "DT_EXP_DATO        DATE)";
		
		
		Statement st = null;
		try{
			st = con.createStatement();
			st.execute("create sequence SEQ_RUOLO_TARSU");
		}catch(Exception e){
			try {
				st.close();
			} catch (SQLException ee) {}
		}
		
		
		try {

			st = con.createStatement();
			st.execute(sql);
			
			String tri = "create or replace "+
				      "trigger TRI_RUOLO_TARSU "+
				      "before insert "+
				      "on "+env.tableRE+" for each row " +
				      "begin " +
				      "select SEQ_RUOLO_TARSU.nextval into :new.RID from dual; " +
				      "end;";
			try{
				st.execute(tri);
			}catch(Exception e){}
					
		} catch (SQLException e1) {
			log.warn(e1.getMessage());
			log.warn("Tabella env.tableRE esiste già: OK, BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
	
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String fs = file.substring(0,file.indexOf(".txt"));
		String[] s = fs.split("_");
		env.anno = s[s.length-1];
		String data =env.anno +"1231";
		try {
			env.saveFornitura(sdf.parse(data), file);
		} catch (ParseException e) {	
			log.debug("_______ ! Errore su parsing data Tracciamento fornitura: " + file);
		}
		
	}
	
	protected void postElaborazioneAction(String file, List<String> fileDaElaborare, String cartellaFiles) {
	}
	
	private String getLine(String currentLine) {
		
		currentLine =  currentLine.replaceAll("''", "'");
		
		currentLine +=" ";
		
		return currentLine;
		
	}

}

package it.webred.rulengine.brick.loadDwh.load.ruolo.tares;

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

public class RTaresFiles<T extends RTaresEnv> extends ImportFilesFlat<T> {

	public RTaresFiles(T env) {
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
		
		
		String tipo = env.ACCONTO;
		
		if(campi.size()==env.campiTableSupp.split("#").length)
			tipo = env.SUPPLETIVO;
		else{
			
			for(int i=env.shiftAtoS.size()-1; i>=0; i--){
				int[] shift = env.shiftAtoS.get(i);
				
				String[] x1 = new String[shift[1]];
				campi.addAll(shift[0], Arrays.asList(x1));
			}
		}
		
		//String anno = campi.get(15).substring(0, 4);
		
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
		
		
		env.calcolaShift();
	
		String[] campi = env.campiTableSupp.split("#");
		
		String sql = "CREATE TABLE "+env.tableRE+"(" +
				     "RID  INTEGER PRIMARY KEY,";
		
		for(String c: campi)
			sql+= c+"  VARCHAR2(100 CHAR), ";
		
		sql+=  "ANNO  VARCHAR2(4 CHAR), "+
			   "TIPO  VARCHAR2(1 CHAR), ";
		
		sql+=  "PROCESSID          VARCHAR2(50 BYTE), "+
			   "RE_FLAG_ELABORATO  VARCHAR2(1 BYTE), "+
			   "DT_EXP_DATO        DATE)";
		
		
		Statement st = null;
		try{
			st = con.createStatement();
			st.execute("create sequence SEQ_RUOLO_TARES");
		}catch(Exception e){
			try {
				st.close();
			} catch (SQLException ee) {}
		}
		
		
		try {

			st = con.createStatement();
			st.execute(sql);
			
			String tri = "create or replace "+
				      "trigger tri_RUOLO_TARES "+
				      "before insert "+
				      "on "+env.tableRE+" for each row " +
				      "begin " +
				      "select SEQ_RUOLO_TARES.nextval into :new.RID from dual; " +
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
		String data = s[s.length-1]+"1231";
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

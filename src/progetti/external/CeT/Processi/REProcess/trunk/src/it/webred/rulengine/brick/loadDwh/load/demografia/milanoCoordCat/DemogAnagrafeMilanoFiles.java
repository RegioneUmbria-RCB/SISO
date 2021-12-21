package it.webred.rulengine.brick.loadDwh.load.demografia.milanoCoordCat;

import it.webred.rulengine.brick.loadDwh.load.demografia.dto.MetricheTracciato;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesFlat;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.exception.RulEngineException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

public class DemogAnagrafeMilanoFiles <T extends DemogAnagrafeMilanoEnv> extends ImportFilesFlat<T> {

	private String nomeFile = null;
	private boolean pregresso;
	private static final int ID_FONTE_ANAGRAFE = 1;
	
	private final String SQL_VERIF_PREGRESSO = "SELECT * " +
												"FROM R_TRACCIA_FORNITURE " +
												"WHERE BELFIORE = ? " +
												"AND ID_FONTE = " + ID_FONTE_ANAGRAFE + " " +
												"AND NOME_FILE = ?";
	
	private final String SQL_DEL_CFR = "DELETE FROM RE_POPOLAZIONEMILANO_1_1_CFR " +
										"WHERE NOME_FILE = ?";
	
	private final String SQL_CFR_OLD = "INSERT INTO RE_POPOLAZIONEMILANO_1_1_CFR " +
										"SELECT FLAG_STATO, MATRICOLA, ?, 'OLD' " +
										"FROM RE_POPOLAZIONEMILANO_1_1 " +
										"WHERE PROCESSID IN (" +
										"SELECT PROCESSID " +
										"FROM R_TRACCIA_FORNITURE " +
										"WHERE BELFIORE = ? " +
										"AND ID_FONTE = " + ID_FONTE_ANAGRAFE + " " +
										"AND NOME_FILE = ? " +
										") " +
										"MINUS " +
										"SELECT FLAG_STATO, MATRICOLA, ?, 'OLD' " +
										"FROM RE_POPOLAZIONE_COORD_CAT_1_1 " +
										"WHERE NOME_FILE = ?";
	
	private final String SQL_CFR_NEW = "INSERT INTO RE_POPOLAZIONEMILANO_1_1_CFR " +
										"SELECT FLAG_STATO, MATRICOLA, ?, 'NEW' " +
										"FROM RE_POPOLAZIONE_COORD_CAT_1_1 " +
										"WHERE NOME_FILE = ? " +
										"MINUS " +
										"SELECT FLAG_STATO, MATRICOLA, ?, 'NEW' " +
										"FROM RE_POPOLAZIONEMILANO_1_1 " +
										"WHERE PROCESSID IN (" +
										"SELECT PROCESSID " +
										"FROM R_TRACCIA_FORNITURE " +
										"WHERE BELFIORE = ? " +
										"AND ID_FONTE = " + ID_FONTE_ANAGRAFE + " " +
										"AND NOME_FILE = ?" +
										")";
	
	
	public DemogAnagrafeMilanoFiles(T env) {
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
		
		List<MetricheTracciato> mmtt = super.env.metricheTracciato;
		for(MetricheTracciato mt: mmtt) {
			Integer endIndex = mt.getStart()+mt.getEnd();
			String field = null;
			try {
				field = currentLine.substring(mt.getStart(),endIndex);
			} catch (StringIndexOutOfBoundsException exc) {
				//caso particolare:
				//possono esserci righe troncate alla fine del penultimo campo, intendendo l'ultimo = ""
				field = "";
			}
			campi.add(field.trim());
		}
		campi.add(nomeFile);
		
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

		String newFile = new String(file);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		file = file.replaceAll(".TXT", "");
		file = file.replaceAll(".txt", "");
		file = file.replaceAll(".NEW", "");
		file = file.replaceAll(".new", "");
		file = file.substring(file.length() - 6, file.length());
		file = "20" + file;
		
		nomeFile = file;
		pregresso = isPregresso();
			
		try {
			env.saveFornitura(sdf.parse(file), newFile);
		} catch (ParseException e) {
			throw new RulEngineException(e.getMessage());
		}
		
	}
	
	@Override
	protected void postElaborazioneAction(String file, List<String> fileDaElaborare, String cartellaFiles) {	    
		if (pregresso) {
			PreparedStatement stmt = null;
			
			try {
				this.con = env.getConn();
				this.con.setAutoCommit(false);
				
				stmt = con.prepareStatement(SQL_DEL_CFR);
				stmt.setString(1, nomeFile);
				stmt.executeUpdate();
				stmt.cancel();
				
				stmt = con.prepareStatement(SQL_CFR_OLD);
				stmt.setString(1, nomeFile);
				stmt.setString(2, this.ctx.getBelfiore());
				stmt.setString(3, nomeFile);
				stmt.setString(4, nomeFile);
				stmt.setString(5, nomeFile);
				stmt.executeUpdate();
				stmt.cancel();
				
				stmt = con.prepareStatement(SQL_CFR_NEW);
				stmt.setString(1, nomeFile);
				stmt.setString(2, nomeFile);
				stmt.setString(3, nomeFile);
				stmt.setString(4, this.ctx.getBelfiore());
				stmt.setString(5, nomeFile);
				stmt.executeUpdate();
				stmt.cancel();
				
				this.con.commit();
				
			} catch (Exception e) {
				try {
					log.error(e);
					this.con.rollback();
					DbUtils.close(stmt);
	                DbUtils.close(this.con);
		        } catch (SQLException e1) {
		        	log.error(e1);
		        }
			}	
		}
	}
	
	protected boolean isPregresso() {
		boolean retVal = false;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			this.con = env.getConn();
			stmt = con.prepareStatement(SQL_VERIF_PREGRESSO);
			stmt.setString(1, this.ctx.getBelfiore());
			stmt.setString(2, nomeFile);
			rs = stmt.executeQuery();
			retVal = rs.next();
		} catch (Exception e) {
			try {
				log.error(e);
				DbUtils.close(rs);
				DbUtils.close(stmt);
                DbUtils.close(this.con);
	        } catch (SQLException e1) {
	        	log.error(e1);
	        }
		}		
		return retVal;
	}

}

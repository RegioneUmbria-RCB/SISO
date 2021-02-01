package it.webred.rulengine.dwh.table;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.apache.log4j.Logger;

import it.webred.rulengine.brick.cataloghi.UnitaAbitativeConDocfa;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.def.Chiave;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.CtrHash;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtFineVal;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.DtIniVal;
import it.webred.rulengine.dwh.def.Identificativo;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.RelazioneFkEnteSorgente;
import it.webred.rulengine.dwh.def.Tabella;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.utils.CollectionsUtils;
import it.webred.utils.StringUtils;

public abstract class TabellaDwh implements Tabella
{
	protected static final Logger log = Logger.getLogger(TabellaDwh.class.getName());
	
	// Fields non modificabili se non da TabellaDwhao
	public static HashMap<String, String> notModificableFields = new HashMap<String, String>();
	
	private Chiave id;
	private ChiaveEsterna idExt;
	private ChiaveOriginale idOrig=new ChiaveOriginale();
	private CtrHash ctrHash = new CtrHash();
	private DtIniVal dtInizioVal = new DtIniVal();
	private DtFineVal dtFineVal = new DtFineVal();
	
	
	private RelazioneFkEnteSorgente fkEnteSorgente = new RelazioneFkEnteSorgente(SitEnteSorgente.class,new Identificativo(),null);
	private DataDwh dtIns = new DataDwh();
	private DataDwh dtMod = new DataDwh();
	private BigDecimal flagDtValDato;
	private DtExpDato dtExpDato = new DtExpDato();
	private DtIniDato dtInizioDato = new DtIniDato();
	private DtFineDato dtFineDato = new DtFineDato();

	private ProcessId processid;
	
	static  {
		notModificableFields.put("id", null);
		notModificableFields.put("idExt", null);
		notModificableFields.put("ctrHash", null);
		notModificableFields.put("dtInizioVal", null);
		notModificableFields.put("dtFineVal", null);
		notModificableFields.put("dtIns", null);
		notModificableFields.put("dtMod", null);
		notModificableFields.put("processid", null);

		notModificableFields.put("sequenceName", null);
		
		
	}
	
	/**
	 * @param stringa - la stringa sulla quale viene calcolato l'hash - ogni tabella avrà un suo metodo per calcolare la stringa
	 */
	public void setCtrHash() {
		MessageDigest md = null;
		try
		{
			md = MessageDigest.getInstance("SHA");
		}
		catch (NoSuchAlgorithmException e)
		{
			log.error(e);
		}
		String stringa = this.getValueForCtrHash();
		
		if (stringa != null) {
			md.update(stringa.getBytes());
	
	
			byte[] b = md.digest();
			
			String hash = new String(StringUtils.toHexString(b));
		
	
			
			this.ctrHash.setValore(hash);
		}

	}
	


	public ChiaveOriginale getIdOrig()
	{
		return idOrig;
	}

	public void setIdOrig(ChiaveOriginale chiaveOriginale)
	{
		this.idOrig = chiaveOriginale;
	}

	public CtrHash getCtrHash()
	{
		return ctrHash;
	}

	public abstract String getValueForCtrHash();
	
	public DtExpDato getDtExpDato()
	{
		return dtExpDato;
	}

	public void setDtExpDato(DtExpDato dtExpDato)
	{
		this.dtExpDato = dtExpDato;
	}

	public DtFineDato getDtFineDato()
	{
		return dtFineDato;
	}

	public void setDtFineDato(DtFineDato dtFineDato)
	{
		this.dtFineDato = dtFineDato;
	}

	public DtFineVal getDtFineVal()
	{
		return dtFineVal;
	}

	public void setDtFineVal(DtFineVal dtFineVal)
	{
		this.dtFineVal = dtFineVal;
	}

	public DtIniDato getDtInizioDato()
	{
		return dtInizioDato;
	}

	public void setDtInizioDato(DtIniDato dtIniDato)
	{
		this.dtInizioDato = dtIniDato;
	}

	public DtIniVal getDtInizioVal()
	{
		return dtInizioVal;
	}



	public DataDwh getDtIns()
	{
		return dtIns;
	}

	public void setDtIns(DataDwh dtIns)
	{
		this.dtIns = dtIns;
	}

	public DataDwh getDtMod()
	{
		return dtMod;
	}

	public void setDtMod(DataDwh dtMod)
	{
		this.dtMod = dtMod;
	}

	public Relazione getFkEnteSorgente()
	{
		return fkEnteSorgente;
	}
	public void setFkEnteSorgente(Identificativo chiave, String belfiore)
	{
		RelazioneFkEnteSorgente r = new RelazioneFkEnteSorgente(SitEnteSorgente.class, chiave, belfiore);
		fkEnteSorgente = r;
	}

	
	
	public BigDecimal getFlagDtValDato()
	{
		return flagDtValDato;
	}

	public void setFlagDtValDato(BigDecimal flagDtValDato)
	{
		this.flagDtValDato = flagDtValDato;
	}
	
	public Chiave getId() {
		if (id==null) {
			id =  new Chiave();
			id.setValore(this.getIdExt(),this.getDtInizioVal(),this.getCtrHash());
		}
		return id;
	}
	
	public ChiaveEsterna getIdExt() {
		if (idExt==null) {
			if (this instanceof IdExtFromSequence) {
				//recupero il valore dalla sequence
				IdExtFromSequence idExtFromSequenceThis = (IdExtFromSequence)this;				
				String sequenceName = idExtFromSequenceThis.getSequenceName();
				String sql = "SELECT " + sequenceName + ".NEXTVAL AS SEQNEXTVAL FROM DUAL";
				long seqNextval = -1;
				Connection conn = null;
				try {
					
					conn = RulesConnection.getConnection("DWH_" + ((RelazioneFkEnteSorgente)fkEnteSorgente).getBelfiore());
				} catch (Exception e1) {
					// non faccio nulla , tanto il problema è grosso e viene dato NullPointerException appena sotto
				}
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				try {
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					while (rs.next()) {
						seqNextval = rs.getLong("SEQNEXTVAL");
					}
				} catch (Exception e) {}
				finally {					
					try {
						if (rs != null) {
							rs.close();
						}
						if (pstmt != null) {
							pstmt.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (Exception e) {}
				}
				
				ChiaveOriginale co =  new ChiaveOriginale();					
				co.setValore("" + seqNextval);
				idExt= new ChiaveEsterna();
				if (this.getIdOrig() == null) {
					//imposto anche l'idOrig con il valore preso dalla sequence
					this.setIdOrig(co);
					idExt.setValore(this.getIdOrig(), (Identificativo)this.getFkEnteSorgente().getRelazione());
				} else {
					//lascio l'idOrig com'è e imposto l'idExt con il valore preso dalla sequence
					idExt.setValore(co, (Identificativo)this.getFkEnteSorgente().getRelazione());
				}
			} else {
				idExt= new ChiaveEsterna();
				idExt.setValore(this.getIdOrig(), (Identificativo)this.getFkEnteSorgente().getRelazione());
			}			
		}
		return idExt;
	}

	public void setId(Chiave id)
	{
		this.id = id;
	}

	public void setIdExt(ChiaveEsterna idExt)
	{
		this.idExt = idExt;
	}

	public void setCtrHash(CtrHash ctrHash)
	{
		this.ctrHash = ctrHash;
	}

	public void setDtInizioVal(DtIniVal dtInizioVal)
	{
		this.dtInizioVal = dtInizioVal;
	}

	public ProcessId getProcessid()
	{
		return processid;
	}

	public void setProcessid(ProcessId processid)
	{
		this.processid = processid;
	}



	public static void calcolaHash(){
		
	}
	
	
	
}




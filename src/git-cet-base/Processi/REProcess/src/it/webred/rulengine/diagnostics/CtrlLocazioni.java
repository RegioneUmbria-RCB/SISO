package it.webred.rulengine.diagnostics;

import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;
import it.webred.ct.data.access.basic.indice.civico.CivicoService;
import it.webred.ct.data.access.basic.indice.civico.dto.RicercaCivicoIndiceDTO;
import it.webred.ct.data.model.indice.SitCivicoTotale;
import it.webred.rulengine.Context;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.diagnostics.bean.CivicoBean;
import it.webred.rulengine.diagnostics.bean.DatiUiBean;
import it.webred.rulengine.diagnostics.bean.SoggettoLocazioni;
import it.webred.rulengine.db.model.RRuleParamIn;
import it.webred.rulengine.diagnostics.bean.DiagnosticConfigBean;
import it.webred.rulengine.diagnostics.utils.ChkDatiUI;

import it.webred.rulengine.type.def.DeclarativeType;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;


public class CtrlLocazioni extends ElaboraDiagnosticsNonStandard {
	Connection conn;
	final String SQL_CIV = "SELECT DISTINCT S.PREFISSO,  S.NOME, C.CIVICO , S.PKID_STRA, C.PKID_CIVI FROM SITIDSTR S, SITICIVI C, SITICIVI_UIU CU, SITIUIU U " +
	" WHERE S.PKID_STRA=C.PKID_STRA AND CU.PKID_CIVI=C.PKID_CIVI AND CU.PKID_UIU = U.PKID_UIU "+
	" AND S.DATA_FINE_VAL=TO_DATE('31129999','DDMMYYYY') AND C.DATA_FINE_VAL=TO_DATE('31129999','DDMMYYYY') "+
	" AND CU.DATA_FINE_VAL=TO_DATE('31129999','DDMMYYYY') AND U.DATA_FINE_VAL=TO_DATE('31129999','DDMMYYYY') "+
	" # ORDER BY NOME, PREFISSO, CIVICO ";
	final String SQL_LOCAZ = "SELECT * FROM LOCAZIONI_A WHERE  UFFICIO = ? AND ANNO=? AND SERIE=? AND NUMERO=?  AND NVL(SOTTONUMERO,0) = ? "+
	" AND NVL(PROG_NEGOZIO,0) =?  AND DATA_FINE > SYSDATE";
	
	private String enteID;
	private CivicoService civicoService ;
	private AnagrafeService anagService ;
	
	public CtrlLocazioni() {
		super();
		
	}

	public CtrlLocazioni(Connection connPar, Context ctxPar,
			List<RRuleParamIn> paramsPar) {
		super(connPar, ctxPar, paramsPar);
		
	}

	@Override
	protected void ElaborazioneNonStandard(DiagnosticConfigBean diaConfig,
			long idTestata) throws Exception {
		log.info("[ElaborazioneNonStandard] - Invoke class CtrlLocazioni ");
		enteID= this.getCodBelfioreEnte();
		conn= this.getConn();
		PreparedStatement pst =null; ResultSet rs =null; 
		//Statement st =null;
		String sqlCiv= SQL_CIV;
		Object parm = getParamValueByName("FOGLIO");
		Long foglioParm=null;
		String foglioStr="";
		if (parm != null && parm instanceof String) {
			try {
				foglioStr = (String)parm;
				foglioParm = new Long(foglioStr);
			}catch(NumberFormatException e) {}
		}
			
		log.info("FOGLIO PARM---> " + foglioParm);
		if (foglioParm!=null)
			sqlCiv = sqlCiv.replace("#","AND U.FOGLIO=?");
		else
			sqlCiv  = sqlCiv.replace("#","");
		try {
			pst = conn.prepareStatement(sqlCiv);
			if (foglioParm!=null)
				pst.setLong(1, foglioParm);
			rs = pst.executeQuery();
			while(rs.next()){
				Object[] valori = new Object[11];
				CivicoBean civBean = valCivicoBean(rs);
				log.info("--> Indirizzo: " + civBean.getDescrVia()+ "; " + civBean.getCivico()+ "ID_STRA: " + civBean.getPkidStra()+ "-ID_CIVI: " + civBean.getPkidCivi());
				valori[0]=civBean.getPkidStra();
				valori[1]=civBean.getDescrVia();
				valori[2]=civBean.getPkidCivi();
				valori[3]=civBean.getCivico();
				ChkDatiUI checkUi = new ChkDatiUI(conn);
				List<DatiUiBean> listaUi = checkUi.getListaUiAlCivico(civBean.getPkidCivi());
				boolean civConUiCollegateAPiuCivici = false;
				for (DatiUiBean datiUi: listaUi) {
					checkUi = new ChkDatiUI(conn, datiUi.getCodNazionale(), datiUi.getFoglio(), datiUi.getParticella(), datiUi.getUnimm(), datiUi.getSub());
					civConUiCollegateAPiuCivici = checkUi.isUiCiviciMultipli();
					if (civConUiCollegateAPiuCivici)
						break;
				}
				Long numUiResidenziali=null; Long numFamResidenti=null; Long numFamTitRes=null;
				Long numContrLocaz=null; Long numContrEnel=null; Long numContrGas=null; 
				if (!civConUiCollegateAPiuCivici ) {
					log.debug("-Questo civico  NON ha ui collegate a più civici");
					valori[4]="N";	
					checkUi = new ChkDatiUI(conn);
					numUiResidenziali = checkUi.contaUiuResidenzialiAlCivico(civBean.getPkidCivi());
					numFamResidenti = contaNumFamiglieResidentiAlCivico(civBean.getPkidCivi());
					numFamTitRes = checkUi.getDatiTitolariUI(civBean.getPkidCivi().toString()).getNumFamiglieTitRes();
					numContrLocaz = contaNumLocazioniAttive(civBean.getPkidCivi());
					numContrEnel = contaContrattiEnelAttivi(civBean.getPkidCivi());
					numContrGas = contaContrattiGasAttivi(civBean.getPkidCivi());
					valori[5]=numUiResidenziali;	
					valori[6]=numFamResidenti	;
					valori[7]=numFamTitRes;	
					valori[8]=numContrLocaz;
					valori[9]=numContrEnel;
					valori[10]=numContrGas;
				} else {
					log.debug("-Questo civico ha ui collegate a più civici");
					valori[4]="S";	
				}
				insertDati(idTestata, valori);
					
				
			}
		}catch (SQLException e) {
			log.error("ERRORE SQL " + e, e);
		}finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		
		super.ElaborazioneNonStandard(diaConfig, idTestata);
	}
	
	private CivicoBean valCivicoBean(ResultSet rs) throws SQLException{
		CivicoBean civBean = new CivicoBean();
		try {
			String descVia = (rs.getString("PREFISSO") != null)?   rs.getString("PREFISSO"): "";
			descVia += " ";
			descVia += rs.getString("NOME");	
			civBean.setDescrVia(descVia);
			civBean.setCivico(rs.getString("CIVICO"));
			civBean.setPkidStra(rs.getLong("PKID_STRA"));
			civBean.setPkidCivi(rs.getLong("PKID_CIVI"));
			
		}
		catch(SQLException sqle) {
			log.error("ERRORE SQL IN FASE DI ACQUISIZIONE DATI CIVICO DAL ResultSet ", sqle);
		}
	
		return civBean;
	}
	
	private Object getParamValueByName(String namePar) {
		DeclarativeType param =  this.getCtx().getDeclarativeType(namePar); 
		if (param!=null && param.getValue() !=null) {
			log.debug("Parametro recuperato. type [" + param.getType()+ "]; value [" + param.getValue().toString() + "]");
			return param.getValue();
		}
		else
			return null;
	}
	
	private Long contaNumFamiglieResidentiAlCivico(Long pkIdCivi) {
		Long numFam = null;
		RicercaCivicoIndiceDTO rci=new RicercaCivicoIndiceDTO(); 
		rci.setEnteId(enteID);
		rci.setIdCivico(pkIdCivi.toString());
		rci.setDestEnteSorgente("1");
		rci.setDestProgEs("1");
		rci.setEnteSorgente("4");
		rci.setProgEs("2");
		civicoService=	(CivicoService)ServiceLocator.getInstance().getService("CT_Service", "CT_Service_Data_Access", "CivicoServiceBean");
		List<SitCivicoTotale> listCiviciDemografia = civicoService.getListaCivTotaleByCivicoFonte(rci);
		if (listCiviciDemografia ==null || listCiviciDemografia.size() == 0) {
			log.debug("NON TROVATI CIVICI IN DEMOGRAFIA CORRELATO AL CIVICO SITI " + pkIdCivi);
			return null;
		}
		if (listCiviciDemografia.size() > 1 )
			log.debug("TROVATI PIU' DI UN CIVICO IN DEMOGRAFIA CORRELATO AL CIVICO SITI " + pkIdCivi);
		for (SitCivicoTotale civ: listCiviciDemografia) {
			anagService = (AnagrafeService)ServiceLocator.getInstance().getService("CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
			RicercaCivicoDTO rc = new RicercaCivicoDTO();
			rc.setEnteId(enteID);
			rc.setIdCivico(civ.getId().getIdDwh());
			rc.setDataRif(new Date());
			numFam = anagService.getNumeroFamiglieResidentiAlCivico(rc);
			if (numFam !=null && numFam.intValue()!= 0 ) {
				break;
			}
		}
			
		return numFam;
	}
	
	private Long contaNumLocazioniAttive (Long pkIdCivi) throws SQLException {
		log.debug("contaNumLocazioniAttive()-pkidCivi: " + pkIdCivi); 
		Long numLocazAttive = null;
		RicercaCivicoIndiceDTO rci=new RicercaCivicoIndiceDTO(); 
		rci.setEnteId(enteID);
		rci.setIdCivico(pkIdCivi.toString());
		rci.setDestEnteSorgente("5");
		rci.setDestProgEs("1");
		rci.setEnteSorgente("4");
		rci.setProgEs("2");
		civicoService=	(CivicoService)ServiceLocator.getInstance().getService("CT_Service", "CT_Service_Data_Access", "CivicoServiceBean");
		List<SitCivicoTotale> listCiviciLocaz = civicoService.getListaCivTotaleByCivicoFonte(rci);
		if (listCiviciLocaz ==null || listCiviciLocaz.size() == 0)
			return null;
		
		PreparedStatement pst = null; ResultSet rs=null;
		try {
			numLocazAttive=0L;
			for(SitCivicoTotale civ: listCiviciLocaz) {
				pst = conn.prepareStatement(SQL_LOCAZ);
				log.debug("contaNumLocazioniAttive() - sql: " + SQL_LOCAZ);
				log.debug("contaNumLocazioniAttive() - idDwh(to be splitted): " + civ.getId().getIdDwh());
				String[] keys = civ.getId().getIdDwh().split("\\|");
				SoggettoLocazioni sogg = new SoggettoLocazioni();
				pst.setString(1,keys[0]);
				pst.setLong(2, Long.parseLong(keys[1]));
				pst.setString(3,keys[2]);
				pst.setLong(4,Long.parseLong(keys[3]));
				if (keys.length > 4)
					pst.setLong(5,Long.parseLong(keys[4]));
				else
					pst.setLong(5,new Long(0));
				if (keys.length > 5)
					pst.setLong(6,Long.parseLong(keys[5]));
				else
					pst.setLong(6,new Long(0));
				rs = pst.executeQuery();
				while(rs.next()){
					numLocazAttive++;
				}
				DbUtils.close(rs);
				DbUtils.close(pst);
			}
		}catch (SQLException e) {
			log.error("ERRORE SQL IN FASE DI CONTEGGIO NUM.LOCAZIONI ATTIVE - SQL: " + SQL_LOCAZ);
			log.error("ERRORE SQL IN FASE DI CONTEGGIO NUM.LOCAZIONI ATTIVE - PARMS: pkIdCivi [" + pkIdCivi + "]", e);
		
		}finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return numLocazAttive;
	}
	
	private Long contaContrattiEnelAttivi (Long pkIdCivi) throws SQLException {
		Long numContrattiEnelAttivi = null;
		RicercaCivicoIndiceDTO rci=new RicercaCivicoIndiceDTO(); 
		rci.setEnteId(enteID);
		rci.setIdCivico(pkIdCivi.toString());
		rci.setDestEnteSorgente("10");
		rci.setDestProgEs("2");
		rci.setEnteSorgente("4");
		rci.setProgEs("2");
		civicoService=	(CivicoService)ServiceLocator.getInstance().getService("CT_Service", "CT_Service_Data_Access", "CivicoServiceBean");
		List<SitCivicoTotale> listCiviciEnel = civicoService.getListaCivTotaleByCivicoFonte(rci);
		if (listCiviciEnel ==null || listCiviciEnel.size() == 0)
			return null;
		List<String> listaId = new ArrayList<String>();
		for(SitCivicoTotale civ: listCiviciEnel)  {
			listaId.add(civ.getId().getIdDwh());
		}
		PreparedStatement pst = null; ResultSet rs=null;
		String sqlEnel = "SELECT COUNT(DISTINCT CODICE_UTENZA) AS NUM_ENEL FROM  SIT_ENEL_UTENZA WHERE DT_FINE_VAL IS NULL AND ID IN(";
		sqlEnel += buildSqlIn(listaId) ;
		sqlEnel += ")";
		log.debug("CONTEGGIO NUM.CONTRATTI ENEL ATTIVI PER PKID_CIVI" +pkIdCivi +" SQL: " + sqlEnel);
		numContrattiEnelAttivi=0L;
		try {
			pst = conn.prepareStatement(sqlEnel);
			rs = pst.executeQuery();
			if(rs.next()){
				numContrattiEnelAttivi = rs.getLong("NUM_ENEL");
			}
		}catch (SQLException e) {
			log.error("ERRORE SQL IN FASE DI CONTEGGIO NUM.CONTRATTI ENEL ATTIVI - SQL: " + sqlEnel);
			log.error("ERRORE SQL IN FASE DI CONTEGGIO NUM.CONTRATTI ENEL ATTIVI - PARMS: pkIdCivi [" + pkIdCivi + "]", e);
		
		}finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return numContrattiEnelAttivi ;
	}
	
	private Long contaContrattiGasAttivi (Long pkIdCivi) throws SQLException {
		Long numContrattiGasAttivi = null;
		RicercaCivicoIndiceDTO rci=new RicercaCivicoIndiceDTO(); 
		rci.setEnteId(enteID);
		rci.setIdCivico(pkIdCivi.toString());
		rci.setDestEnteSorgente("12");
		rci.setDestProgEs("1");
		rci.setEnteSorgente("4");
		rci.setProgEs("2");
		civicoService=	(CivicoService)ServiceLocator.getInstance().getService("CT_Service", "CT_Service_Data_Access", "CivicoServiceBean");
		List<SitCivicoTotale> listCiviciGas = civicoService.getListaCivTotaleByCivicoFonte(rci);
		if (listCiviciGas ==null || listCiviciGas.size() == 0)
			return null;
		List<String> listaId = new ArrayList<String>();
		for(SitCivicoTotale civ: listCiviciGas)  {
			listaId.add(civ.getId().getIdDwh());
		}
		PreparedStatement pst = null; ResultSet rs=null;
		String sqlGas= "SELECT COUNT(DISTINCT IDENTIFICATIVO_UTENZA) AS NUM_GAS FROM  SIT_U_GAS WHERE DT_FINE_VAL IS NULL AND ID IN(";
		sqlGas += buildSqlIn(listaId) ;
		sqlGas += ")";
		log.debug("CONTEGGIO NUM.CONTRATTI GAS ATTIVI PER PKID_CIVI" +pkIdCivi +" SQL: " + sqlGas);
		numContrattiGasAttivi=0L;
		try {
			pst = conn.prepareStatement(sqlGas);
			rs = pst.executeQuery();
			if(rs.next()){
				numContrattiGasAttivi = rs.getLong("NUM_GAS");
			}
		}catch (SQLException e) {
			log.error("ERRORE SQL IN FASE DI CONTEGGIO NUM.CONTRATTI GAS ATTIVI - SQL: " + sqlGas);
			log.error("ERRORE SQL IN FASE DI CONTEGGIO NUM.CONTRATTI GAS ATTIVI - PARMS: pkIdCivi [" + pkIdCivi + "]", e);
		
		}finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return numContrattiGasAttivi ;
	}
	private String buildSqlIn(List<String> listaId) {
		String sqlIn="";
		int i=0;
		for (String id: listaId) {
			if (i==0)
				sqlIn="'" +id +"'";
			else
				sqlIn+= "," + "'" +id +"'" ;
			i++;
		}
		return sqlIn;
	}
	
	private void insertDati(long idTestata, Object[] valori) throws SQLException{
		Statement st=null; Statement stNextVal=null; ResultSet rsNextVal = null;
		String sqlIns ="";
		String sqlNextVal="select SEQ_DIA_DETTAGLIO_D_CTR_CAT.NEXTVAL AS ID FROM DUAL";
		try {
			stNextVal= conn.createStatement();
			rsNextVal = stNextVal.executeQuery(sqlNextVal);
			rsNextVal.next();
			Long id = rsNextVal.getLong("ID");
			String desVia = ((String)valori[1]).replace("'","''");
			sqlIns = "INSERT INTO DIA_DETTAGLIO_D_CTR_CAT03 VALUES ("
				+ id +"," + idTestata + "," + (Long)valori[0] + ",'" + desVia + "',"  + (Long)valori[2] + ",'"
				+ (String)valori[3]  +"','" + (String)valori[4] + "',"+ (Long)valori[5]+ ","+ (Long)valori[6]+ ","+ (Long)valori[7]
			    + ","+ (Long)valori[8]+ ","+ (Long)valori[9]+ ","+ (Long)valori[10]+ ")" ;
			log.info("INSERIMENTO DATI -->SQL:" + sqlIns);	
			st = conn.createStatement();
			st.execute(sqlIns);
		}catch (SQLException e) {
			log.error("ERRORE SQL IN FASE DI INSERIMENTO RIGA - SQL: " + sqlIns, e);
			throw e;
		}finally {
			DbUtils.close(st);
			DbUtils.close(rsNextVal);
			DbUtils.close(stNextVal);
		}
	}
	
	
	
}



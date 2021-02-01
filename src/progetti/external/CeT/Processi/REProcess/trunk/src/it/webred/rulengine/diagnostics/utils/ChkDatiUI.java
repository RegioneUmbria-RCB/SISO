package it.webred.rulengine.diagnostics.utils;

import it.webred.ct.data.access.basic.indice.ricerca.IndiceCorrelazioneService;
import it.webred.rulengine.diagnostics.bean.CivicoBean;
import it.webred.rulengine.diagnostics.bean.DatiTitolariUI;
import it.webred.rulengine.diagnostics.bean.DatiUiBean;
import it.webred.rulengine.diagnostics.bean.SoggettoDemografiaBean;
import it.webred.rulengine.diagnostics.bean.TitolareBean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

public class ChkDatiUI {
	protected static org.apache.log4j.Logger log = Logger.getLogger(ChkDatiUI.class.getName());
	private Connection conn;
	final String SQL_CIV = "SELECT S.PREFISSO,  S.NOME, C.CIVICO, C.* FROM SITIDSTR S, SITICIVI C, SITICIVI_UIU CU, SITIUIU U " +
		"WHERE S.PKID_STRA=C.PKID_STRA AND CU.PKID_CIVI=C.PKID_CIVI AND CU.PKID_UIU = U.PKID_UIU "+
		"AND S.DATA_FINE_VAL=TO_DATE('31129999','DDMMYYYY') AND C.DATA_FINE_VAL=TO_DATE('31129999','DDMMYYYY') "+
		"AND CU.DATA_FINE_VAL=TO_DATE('31129999','DDMMYYYY') AND U.DATA_FINE_VAL=TO_DATE('31129999','DDMMYYYY')"+
		" AND U.FOGLIO=? AND U.PARTICELLA = LPAD(?,5,'0') AND U.UNIMM=TO_NUMBER(?) AND NVL(SUB,'')=?";
	final String SQL_TIT = "SELECT DISTINCT TIPO_TITOLO, DESCRIPTION, PERC_POSS, C.DATA_INIZIO AS DATA_INIZIO_POSS, " +
		"C.DATA_FINE AS DATA_FINE_POSS, S.* FROM SITICONDUZ_IMM_ALL C, CONS_SOGG_TAB S, CONS_DECO_TAB DECO " +
		"WHERE DECO.TABLENAME = 'CONS_ATTI_TAB' AND DECO.FIELDNAME = 'TIPO_DOCUMENTO' AND DECO.CODE = C.TIPO_DOCUMENTO " +
		"AND S.PK_CUAA = C.PK_CUAA AND C.FOGLIO = TO_NUMBER(?) AND C.PARTICELLA = LPAD(?,5,'0') AND C.UNIMM = TO_NUMBER(?) " +
		"AND NVL(C.SUB,'')=? AND S.DATA_FINE = TO_DATE('31129999','DDMMYYYY') AND C.DATA_FINE = TO_DATE('31129999','DDMMYYYY')";
	final String SQL_SOGG_TOTALE = "SELECT ID_DWH, DT_FINE_VAL FROM SIT_SOGGETTO_TOTALE " +
		"WHERE  FK_ENTE_SORGENTE=1 AND PROG_ES=1 " +
		"AND FK_SOGGETTO IN (SELECT FK_SOGGETTO FROM SIT_SOGGETTO_TOTALE WHERE FK_ENTE_SORGENTE=4 AND PROG_ES=3 AND ID_DWH=?) " +
		"ORDER BY DT_FINE_VAL DESC";
	final String SQL_SOGG_DEM = "SELECT F.ID AS ID_FAM, F.ID_ORIG AS ID_ORIG_FAM , F.TIPOFAMI, P.*, V.VIASEDIME, V.DESCRIZIONE, " +
		"EXTRACT (civico_composto,'/civicocomposto/att[@tipo=numero]/@valore').getstringval () numero," +
		"EXTRACT (civico_composto,'/civicocomposto/att[@tipo=barrato]/@valore').getstringval () barrato," +
		"EXTRACT (civico_composto,'/civicocomposto/att[@tipo=subbarrato]/@valore').getstringval () subbarato , "+
		"C.ID AS ID_CIVICO "+
		"FROM SIT_D_PERSONA P, SIT_D_CIVICO C, SIT_D_PERSONA_CIVICO PC, SIT_D_VIA V, SIT_D_FAMIGLIA F, SIT_D_PERS_FAM PF "+
		"WHERE PC.ID_EXT_D_PERSONA =P.ID_EXT AND PC.ID_EXT_D_CIVICO= C.ID_EXT AND V.ID_EXT=C.ID_EXT_D_VIA " +
		"AND P.ID_EXT = PF.ID_EXT_D_PERSONA AND PF.ID_EXT_D_FAMIGLIA = F.ID_EXT " +
		"AND C.DT_FINE_VAL IS NULL AND PC.DT_FINE_VAL IS NULL AND V.DT_FINE_VAL IS NULL AND F.DT_FINE_VAL IS NULL "+
		"AND P.DT_FINE_VAL IS NULL AND PF.DT_FINE_VAL IS NULL "+
		"AND P.ID =?";
	final String SQL_CIV_CORR = "SELECT *  FROM SIT_CIVICO_TOTALE WHERE FK_ENTE_SORGENTE=1 AND PROG_ES=1 AND ID_DWH = ? " +
		" AND FK_CIVICO IN (SELECT FK_CIVICO FROM SIT_CIVICO_TOTALE WHERE FK_ENTE_SORGENTE=4 AND PROG_ES=2 AND ID_DWH IN (#)";
	
	final String SQL_UI_CIV ="SELECT DISTINCT U.COD_NAZIONALE, FOGLIO, PARTICELLA, UNIMM , SUB FROM SITIDSTR S, SITICIVI C, SITICIVI_UIU CU, SITIUIU U "+
		"WHERE S.PKID_STRA=C.PKID_STRA AND CU.PKID_CIVI=C.PKID_CIVI AND CU.PKID_UIU = U.PKID_UIU " +
		"AND S.DATA_FINE_VAL= TO_DATE('31129999','DDMMYYYY') AND C.DATA_FINE_VAL= TO_DATE('31129999','DDMMYYYY') " +
		"AND CU.DATA_FINE_VAL= TO_DATE('31129999','DDMMYYYY') AND U.DATA_FINE_VAL= TO_DATE('31129999','DDMMYYYY') " +
		"AND C.PKID_CIVI=? ORDER BY COD_NAZIONALE,	FOGLIO, PARTICELLA, UNIMM";
	private String SQL_UI_CIVICI_MULTIPLI = "SELECT U.COD_NAZIONALE, FOGLIO, PARTICELLA, UNIMM, SUB, COUNT(*) "+
		"FROM SITIDSTR S, SITICIVI C, SITICIVI_UIU CU, SITIUIU U " + 
		"WHERE S.PKID_STRA=C.PKID_STRA AND CU.PKID_CIVI=C.PKID_CIVI AND CU.PKID_UIU = U.PKID_UIU " +
		"AND S.DATA_FINE_VAL= TO_DATE('31129999','DDMMYYYY') AND C.DATA_FINE_VAL= TO_DATE('31129999','DDMMYYYY') " +
		"AND CU.DATA_FINE_VAL= TO_DATE('31129999','DDMMYYYY') AND U.DATA_FINE_VAL= TO_DATE('31129999','DDMMYYYY') "  +
		"AND U.COD_NAZIONALE = ? AND U.FOGLIO=? AND U.PARTICELLA= LPAD(?,5,'0')  AND U.UNIMM= TO_NUMBER(?) AND NVL(SUB,'')=? " +
		" HAVING COUNT(*)> 1 GROUP BY U.COD_NAZIONALE,FOGLIO, PARTICELLA, UNIMM, SUB";
	final String SQL_COUNT_UI_RESIDEN_AL_CIVICO="SELECT COUNT (DISTINCT U.PKID_UIU) AS NUM_UIU_RES "+
		"FROM SITIDSTR S, SITICIVI C, SITICIVI_UIU CU, SITIUIU U " + 
		"WHERE S.PKID_STRA=C.PKID_STRA AND CU.PKID_CIVI=C.PKID_CIVI AND CU.PKID_UIU = U.PKID_UIU "+
		"AND S.DATA_FINE_VAL= TO_DATE('31129999','DDMMYYYY') AND C.DATA_FINE_VAL= TO_DATE('31129999','DDMMYYYY') "+
		"AND CU.DATA_FINE_VAL= TO_DATE('31129999','DDMMYYYY') AND U.DATA_FINE_VAL= TO_DATE('31129999','DDMMYYYY') "+
		"AND C.PKID_CIVI=?  AND U.CATEGORIA IN  ('A01', 'A02', 'A03','A04', 'A05', 'A06','A07','A08','A09','A11')";
	
	private String codNazionale; 
	private Long foglio; 
    private String particella; 
    private Long unimm; 
    private String sub;
    
    private IndiceCorrelazioneService indiceService ;
    public ChkDatiUI(Connection conn){
    	this.conn= conn;
    }
    
    public ChkDatiUI(Connection conn,  Long foglio, String particella, Long unimm, String sub ) {
       	this(conn);
    	this.codNazionale = codNazionale;
    	this.foglio=foglio;
    	this.particella= particella;
    	this.sub =sub;
    	this.unimm=unimm;
    }
    
    public ChkDatiUI(Connection conn, String codNazionale, Long foglio, String particella, Long unimm, String sub ) {
       	this(conn, foglio, particella, unimm, sub);
    	this.codNazionale = codNazionale;
       }
    
    public DatiTitolariUI getDatiTitolariUI(String idCiv) 	throws SQLException {
       	List<String> listaIdCiv =null;
       	if (idCiv!=null && !idCiv.equals(""))  {
       		listaIdCiv = new ArrayList<String>();
       		listaIdCiv.add(idCiv);
       	}
       	return getDatiTitolariUI(listaIdCiv);
    }
    /*
     * Con le coordinate catastali valorizzate:
     * --> se il parm listIdCiv (=listra dei civici corrispondeti alla ui) è valorizzato, recupera i dati per i civivi corrispondenti
     * --> se il parm listIdCiv NON è valorizzato, acquisice prima la lista dei civici e poi recupera i dati 
     *  Con le coordinate catastali non valorizzate:
     * --> è necessario che sia passato uno ed un solo civico (= i dati sono elaborati a livello del civico): per questo civico sono acquisite tutte le u.i.
     *     e per tutte queste ui recuperate le info titolarità  
     */
	public DatiTitolariUI getDatiTitolariUI(List<String> listaIdCiv ) 
			throws SQLException {
		DatiTitolariUI dati =new DatiTitolariUI();
		boolean fpsOK = false;
		if (foglio != null && foglio.intValue()!=0 && particella!=null && !particella.equals("") && unimm!=null && unimm.longValue()!=0 )
			fpsOK = true;
		if ( !fpsOK && (listaIdCiv	==null || listaIdCiv.size() != 1)) {
			log.info("Parametri errati. Verifica titolari / residenti non possibile");
			return null;
		}
		
		List<DatiUiBean> listUiBean = new ArrayList<DatiUiBean>();
		if (fpsOK ){
			dati.setCodNazionale(codNazionale);
			dati.setFoglio(foglio);
			dati.setParticella(particella);
			dati.setUnimm(unimm);
			dati.setSub(sub);
			DatiUiBean uiBean = new DatiUiBean(codNazionale, foglio, particella, unimm, sub);
			listUiBean.add(uiBean);
		}
		//coordinate catastali non valorizzate: si recuperano le coordinate associate AL civico (in questo caso infatti si accetta un solo civico come parametro)
		if (!fpsOK) {
			listUiBean = getListaUiAlCivico(Long.parseLong(listaIdCiv.get(0)));
		}
		// nessun civico passato: sono recuperati i civici associati alle coordinate catastali	
		if (listaIdCiv ==null) {
			List<CivicoBean> listaCiv = getListaCiviciUI();
			if (listaCiv.size() == 0) {
				log.info("Non trovati civici. Verifica  non possibile");
				return dati;
			}
			listaIdCiv = new ArrayList<String>();
			for (CivicoBean civ:listaCiv) {	listaIdCiv.add(civ.getPkidCivi().toString());	}
				dati.setListaIdCiv(listaIdCiv);
		}
		List<TitolareBean> listaTit = new ArrayList<TitolareBean>();
		//recupera i titolari della UI (o delle UI associate al civico, se non valorizzate le coordinate catastali)
		for (DatiUiBean datiUi: listUiBean ) {
			List<TitolareBean> listaTitUi  = getListaTitolariUI(datiUi.getFoglio(), datiUi.getParticella(),datiUi.getUnimm(), datiUi.getSub());
			listaTit.addAll(listaTitUi);
			if (listaTit.size()== 0) {
				log.info("Non trovati titolari. Verifica  non possibile");
				return dati;
			}	
		}
		
		dati.setListaTitolari(listaTit);
		
		boolean esisteTitolarePersonaFisica=false;
		boolean unTitolareRisiede= false;
		Set<String> famiglieTitRes = new TreeSet<String>();
		for (TitolareBean tit: listaTit ) {
			if (tit.getFlagPersonaFisica().equals("G"))
				continue;
			esisteTitolarePersonaFisica=true;
			Long idSoggCat = tit.getPkidSogg();
			List<SoggettoDemografiaBean> listaSoggDem = getListaSoggDemCorrelati(idSoggCat);
			String idSoggDem = getIdSoggUltVarDem(listaSoggDem);
			SoggettoDemografiaBean soggDem = getDatiSoggDem(idSoggDem);
			boolean risiede=false;
			//se trovati i dati residenza/sogg in demografia 
			if (soggDem != null) {
				risiede = isCivicoCorrecolato(soggDem.getIdCivicoRes(), listaIdCiv);
				if(risiede && !unTitolareRisiede) {
					unTitolareRisiede=true;
				}
				tit.setFlagResidente(new Boolean(risiede));
				if(!risiede)
					log.info("Questo titolare " + tit.toString() + " NON è residente in nessnuo dei civici alla u.i."); 
				else {
					famiglieTitRes.add(soggDem.getIdOrigFam());
					log.info("Questo titolare " + tit.toString() + " è residente in uno dei civici alla u.i.ID-FAM: "+ soggDem.getIdOrigFam());
				}
			}
			else {
				log.info("Non trovato dato residenza in demografia per titolare " + tit.toString() +". Verifica residente non possibile");
			}
		}
		dati.setEsisteTitolarePersonaFisica(esisteTitolarePersonaFisica);
		dati.setUnTitolareRisiede(unTitolareRisiede);
		if (unTitolareRisiede)
			dati.setNumFamiglieTitRes(new Long(famiglieTitRes.size()));
		return dati; 

	} 	
	
	public List<DatiUiBean> getListaUiAlCivico(Long pkidCivi) throws SQLException{
		log.debug("getListaUiAlCivico()--sql: " + SQL_UI_CIV);
		log.debug("getListaUiAlCivico()--parm pkidCivi: " +pkidCivi );
		List<DatiUiBean>  dati = new ArrayList<DatiUiBean>();
		PreparedStatement pst = null; ResultSet rs=null;
		try {
			pst = conn.prepareStatement(SQL_UI_CIV);
			pst.setLong(1, pkidCivi);
			rs = pst.executeQuery();
			while(rs.next()){
				DatiUiBean bean = new DatiUiBean();
				bean.setCodNazionale(rs.getString("COD_NAZIONALE"));
				bean.setFoglio(rs.getLong("FOGLIO"));
				bean.setParticella(rs.getString("PARTICELLA"));
				bean.setSub(rs.getString("SUB"));
				bean.setUnimm(rs.getLong("UNIMM"));
				dati.add(bean);
			}
		}catch (SQLException e) {
			log.error("ERRORE SQL IN FASE DI REPERIMENTO UI AL CIVICO - SQL: " + 	SQL_UI_CIV );
			log.error("ERRORE SQL IN FASE DI REPERIMENTO UI AL CIVICO - parms: pkidCivi [" + pkidCivi + "]",e);
			throw e;
		}finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return dati;
	}
	
	public Long contaUiuResidenzialiAlCivico(Long pkidCivi) throws SQLException{
		log.debug("contaUiuResidenzialiAlCivico()--sql: " + SQL_COUNT_UI_RESIDEN_AL_CIVICO);
		log.debug("contaUiuResidenzialiAlCivico()--parm pkidCivi: " +pkidCivi );
		Long numUiu=null;
		PreparedStatement pst = null; ResultSet rs=null;
		try {
			pst = conn.prepareStatement(SQL_COUNT_UI_RESIDEN_AL_CIVICO);
			pst.setLong(1, pkidCivi);
			rs = pst.executeQuery();
			if(rs.next()){
				numUiu = rs.getLong("NUM_UIU_RES");
			}
		}catch (SQLException e) {
			log.error("ERRORE SQL IN FASE DI REPERIMENTO UI RESIDENZIALI AL CIVICO - SQL: " + 	SQL_UI_CIV );
			log.error("ERRORE SQL IN FASE DI REPERIMENTO UI RESIDENZIALI AL CIVICO- parms: pkidCivi [" + pkidCivi + "]",e);
			throw e;
		}finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return numUiu;
	}
	
	private List<CivicoBean> getListaCiviciUI () throws SQLException{
		List<CivicoBean> listaCiv = new ArrayList<CivicoBean>();
		PreparedStatement pst = null; ResultSet rs=null;
		try {
			pst = conn.prepareStatement(SQL_CIV);
			pst.setLong(1, foglio);
			pst.setString(2, particella);
			pst.setLong(3, unimm);
			pst.setString(4, sub);
			rs = pst.executeQuery();
			while(rs.next()){
				CivicoBean bean = new CivicoBean();
				bean.setPkidStra(rs.getLong("PKID_STRA"));
				bean.setPrefissoVia(rs.getString("PREFISSO"));
				bean.setDescrVia(rs.getString("NOME"));
				bean.setPkidCivi(rs.getLong("PKID_CIVI"));
				bean.setCivico(rs.getString("CIVICO"));
				listaCiv.add(bean);
			}
		}catch (SQLException e) {
			log.error("ERRORE SQL IN FASE DI REPERIMENTO CIVICI - SQL: " + 	SQL_CIV );
			log.error("ERRORE SQL IN FASE DI REPERIMENTO CIVICI - parms: foglio [" + foglio + "]; particella [" + particella+ "];sub[" + sub + "]; unimm [" + unimm + "]",e);
			throw e;
		}finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return listaCiv;
	}
	
	public boolean isUiCiviciMultipli () throws SQLException{
		log.debug("isUiCiviciMultipli()--sql: " + SQL_UI_CIVICI_MULTIPLI);
		log.debug("isUiCiviciMultipli()--parms codNazionale( " +codNazionale + ");foglio(" + foglio + "); particella( "+  particella + ");sub( " +sub + ");unimm( " +unimm + ")");
		boolean retVal = false;
		PreparedStatement pst = null; ResultSet rs=null;
		if (sub== null || sub.trim().equals("")) {
			sub = " ";
		}
		try {
			pst = conn.prepareStatement(SQL_UI_CIVICI_MULTIPLI);
			pst.setString(1, codNazionale);
			pst.setLong(2, foglio);
			pst.setString(3, particella);
			pst.setLong(4, unimm);
			pst.setString(5, sub);
			rs = pst.executeQuery();
			if (rs.next()){
				retVal= true;
			}
		}catch (SQLException e) {
			log.error("ERRORE SQL IN FASE DI VERIFICA SE UI ASSOCIATA A PIU' CIVICI - SQL: " + SQL_UI_CIVICI_MULTIPLI);
			log.error("ERRORE SQL IN FASE DI VERIFICA SE UI ASSOCIATA A PIU' CIVICI - PARMS: codNazionale [" + codNazionale + "];foglio [" + foglio + "]; particella [" + particella+ "];sub" + sub + "]; unimm [" + unimm + "]", e);

			throw e;
		}finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return retVal;
	}
	
	private List<TitolareBean> getListaTitolariUI (Long foglio, String particella, Long unimm, String sub) throws SQLException{
		List<TitolareBean> listaTit = new ArrayList<TitolareBean>();
		PreparedStatement pst = null; ResultSet rs=null;
		if (sub== null || sub.trim().equals("")) {
			sub = " ";
		}
		try {
			pst = conn.prepareStatement(SQL_TIT);
			pst.setLong(1, foglio);
			pst.setString(2, particella);
			pst.setLong(3, unimm);
			pst.setString(4, sub);
			rs = pst.executeQuery();
			while(rs.next()){
				TitolareBean bean = new TitolareBean();
				bean.setTipoTitolo(rs.getString("TIPO_TITOLO"));
				bean.setDescrTitolo(rs.getString("DESCRIPTION"));
				bean.setPercPoss(rs.getDouble("PERC_POSS"));
				bean.setDataInizio(rs.getDate("DATA_INIZIO_POSS"));
				bean.setPkidSogg(rs.getLong("PKID"));
				bean.setCodFisc(rs.getString("CODI_FISC"));
				bean.setPIva(rs.getString("CODI_PIVA"));
				bean.setRagSoc(rs.getString("RAGI_SOCI"));
				bean.setNome(rs.getString("NOME"));
				bean.setSesso(rs.getString("SESSO"));
				bean.setDtNascita(rs.getDate("DATA_NASC"));
				bean.setCodComuneNascita(rs.getString("COMU_NASC"));
				bean.setFlagPersonaFisica(rs.getString("FLAG_PERS_FISICA"));
				listaTit.add(bean);
			}
		}catch (SQLException e) {
			log.error("ERRORE SQL IN FASE DI REPERIMENTO TITOLARI - SQL: " + SQL_TIT);
			log.error("ERRORE SQL IN FASE DI REPERIMENTO TITOLARI - PARMS: foglio [" + foglio + "]; particella [" + particella+ "];sub" + sub + "]; unimm [" + unimm + "]", e);

			throw e;
		}finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return listaTit;
	}
	private List<SoggettoDemografiaBean> getListaSoggDemCorrelati(Long idSoggCat) throws SQLException{
		List<SoggettoDemografiaBean> listaSoggDem = new ArrayList<SoggettoDemografiaBean>();
		PreparedStatement pst = null; ResultSet rs=null;
		try {
			pst = conn.prepareStatement(SQL_SOGG_TOTALE);
			pst.setString(1, new String(idSoggCat.toString()));
			rs = pst.executeQuery();
			while(rs.next()){
				SoggettoDemografiaBean bean = new SoggettoDemografiaBean();
				bean.setId(rs.getString("ID_DWH"));
				bean.setDtFineVal(rs.getDate("DT_FINE_VAL"));
				listaSoggDem.add(bean);
			}
		}catch (SQLException e) {
			log.error("ERRORE SQL IN FASE DI REPERIMENTO ID_DWH DA SIT_SOGGETTO_TOTALE - SQL: " + SQL_SOGG_TOTALE);
			log.error("ERRORE SQL IN FASE DI REPERIMENTO ID_DWH DA SIT_SOGGETTO_TOTALE - PARMS: id [" + idSoggCat + "]", e);
			throw e;
		}finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return listaSoggDem;
	}
	
	private String getIdSoggUltVarDem(List<SoggettoDemografiaBean> lista) {
		if (lista.size() == 0) return null;
		return lista.get(0).getId();
	}
	private SoggettoDemografiaBean getDatiSoggDem(String id) throws SQLException{
		SoggettoDemografiaBean soggDem = null;
		PreparedStatement pst = null; ResultSet rs=null;
		try {
			pst = conn.prepareStatement(SQL_SOGG_DEM);
			pst.setString(1, id);
			rs = pst.executeQuery();
			if (rs.next()){
				soggDem=new SoggettoDemografiaBean();
				soggDem.setId(rs.getString("ID"));
				soggDem.setDtFineVal(rs.getDate("DT_FINE_VAL"));
				soggDem.setIdFam(rs.getString("ID_FAM"));
				soggDem.setIdOrigFam(rs.getString("ID_ORIG_FAM"));
				soggDem.setIdCivicoRes(rs.getString("ID_CIVICO"));
			}
		}catch (SQLException e) {
			log.error("ERRORE SQL IN FASE DI REPERIMENTO DATI SOGGETTO IN DEMOGRAFIA - SQL: " + SQL_SOGG_DEM, e);
			log.error("ERRORE SQL IN FASE DI REPERIMENTO DATI SOGGETTO IN DEMOGRAFIA - PARMS: id [" + id + "]", e);
			throw e;
		}finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return soggDem;
	}
	private boolean isCivicoCorrecolato (String idCivDem, List<String> listaCiviciCat) throws SQLException{
		boolean retVal=false;
		PreparedStatement pst = null; ResultSet rs=null;
		String sqlIn = buildSqlIn(listaCiviciCat);
		String sqlCivCorr = SQL_CIV_CORR;
		sqlCivCorr = sqlCivCorr.replace("#", sqlIn); 
		sqlCivCorr = sqlCivCorr + ")";
		try {
			pst = conn.prepareStatement(sqlCivCorr);
			pst.setString(1, idCivDem);
			rs = pst.executeQuery();
			if (rs.next()){
				retVal=true;
			}
		}catch (SQLException e) {
			log.error("ERRORE SQL IN FASE DI REPERIMENTO DATI CORRELAZIONI CIVICI - SQL: " + sqlCivCorr);
			log.error("ERRORE SQL IN FASE DI REPERIMENTO DATI CORRELAZIONI CIVICI - parms: id [" + idCivDem + "]", e);
			throw e;
		}finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return retVal;
	}
		
	private String buildSqlIn(List<String> listaCivici) {
		String sqlIn="";
		int i=0;
		for (String idCivCat: listaCivici) {
			if (i==0)
				sqlIn="'" +idCivCat +"'";
			else
				sqlIn+= "," + "'" +idCivCat +"'" ;
			i++;
		}
		return sqlIn;
	}
	
}

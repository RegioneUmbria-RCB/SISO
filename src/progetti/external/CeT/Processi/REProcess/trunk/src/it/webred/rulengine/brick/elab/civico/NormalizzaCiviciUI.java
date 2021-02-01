package it.webred.rulengine.brick.elab.civico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import it.webred.ct.data.access.basic.ici.IciService;
import it.webred.ct.data.access.basic.indice.ricerca.IndiceCorrelazioneService;
import it.webred.ct.data.access.basic.indice.ricerca.RicercaIndiceDTO;
import it.webred.ct.data.access.basic.tarsu.TarsuService;
import it.webred.ct.data.model.ici.SitTIciOggetto;
import it.webred.ct.data.model.indice.IndicePK;
import it.webred.ct.data.model.tarsu.SitTTarOggetto;
import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.diagnostics.bean.CivicoBean;
import it.webred.rulengine.diagnostics.bean.DatiTitolariUI;
import it.webred.rulengine.diagnostics.utils.ChkDatiUI;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

public class NormalizzaCiviciUI extends Command implements Rule {
	private static final Logger log = Logger.getLogger(NormalizzaCiviciUI.class.getName());
	private Connection conn=null;
	final String SQL_UIU_CIV_MUL="SELECT U.COD_NAZIONALE, LPAD(U.FOGLIO, 4,'0') AS FOGLIO, U.PARTICELLA, LPAD(U.UNIMM,5,'0')  AS UNIMM,U.PKID_UIU , COUNT(*) "+
						" FROM SITIDSTR S, SITICIVI C, SITICIVI_UIU CU, SITIUIU U "+
						" WHERE S.PKID_STRA=C.PKID_STRA AND CU.PKID_CIVI=C.PKID_CIVI AND CU.PKID_UIU = U.PKID_UIU "+
						"AND S.DATA_FINE_VAL= TO_DATE('31129999','DDMMYYYY') AND C.DATA_FINE_VAL= TO_DATE('31129999','DDMMYYYY') "+
						"AND CU.DATA_FINE_VAL= TO_DATE('31129999','DDMMYYYY') AND U.DATA_FINE_VAL= TO_DATE('31129999','DDMMYYYY') " +
						"HAVING COUNT(*)> 1 GROUP BY U.COD_NAZIONALE, FOGLIO, PARTICELLA, UNIMM, U.PKID_UIU " +
						"ORDER BY U.COD_NAZIONALE, FOGLIO, PARTICELLA, UNIMM";
	final String SQL_SEZIONE ="SELECT * FROM SITICOMU WHERE COD_NAZIONALE=?";
	final String SQL_CIV_UIU = "SELECT S.PREFISSO,  S.NOME, C.CIVICO, C.* FROM SITIDSTR S, SITICIVI C, SITICIVI_UIU CU, SITIUIU U " +
						"WHERE S.PKID_STRA=C.PKID_STRA AND CU.PKID_CIVI=C.PKID_CIVI AND CU.PKID_UIU = U.PKID_UIU "+
						"AND S.DATA_FINE_VAL=TO_DATE('31129999','DDMMYYYY') AND C.DATA_FINE_VAL=TO_DATE('31129999','DDMMYYYY') "+
						"AND CU.DATA_FINE_VAL=TO_DATE('31129999','DDMMYYYY') AND U.DATA_FINE_VAL=TO_DATE('31129999','DDMMYYYY')"+
						" AND U.PKID_UIU=?";
	final String SQL_ICI_VIA = "SELECT * FROM SIT_T_ICI_VIA WHERE ID_EXT=?";
	final String SQL_CIV_CORR="SELECT * FROM SIT_CIVICO_TOTALE WHERE FK_ENTE_SORGENTE=? AND PROG_ES=?  AND ID_DWH = ? "+ 
						" AND FK_CIVICO IN (SELECT FK_CIVICO FROM SIT_CIVICO_TOTALE WHERE FK_ENTE_SORGENTE=4 AND PROG_ES=2 AND ID_DWH =?)";
	final String SQL_TAR_VIA = "SELECT * FROM SIT_T_TAR_VIA WHERE ID_EXT=?";
	final String SQL_CAT_UIU ="SELECT A.ID_IMM AS ID_IMM,A.PROGRESSIVO AS PROG, A.SEQ AS SEQ, A.CODI_FISC_LUNA AS CODI_FISC_LUNA, A.SEZIONE AS SEZIONE FROM LOAD_CAT_UIU_ID A,  LOAD_CAT_UIU_IND B "+
								"WHERE LPAD(FOGLIO,4, '0') = LPAD(?,4,'0') AND LPAD(MAPPALE,5, '0') = LPAD(?,5,'0') "+
								" AND LPAD(SUB,4,'0') = LPAD(?,4,'0') " + 
								" AND A.ID_IMM = B.ID_IMM AND A.PROGRESSIVO = B.PROGRESSIVO AND A.SEZIONE= B.SEZIONE";
	final String SQL_DOCFA_UIU ="SELECT PROTOCOLLO_REG, FORNITURA, NR_PROG FROM DOCFA_UIU "+
								"WHERE  LPAD(FOGLIO,4, '0') = LPAD(?,4,'0') AND LPAD(NUMERO,5, '0') = LPAD(?,5,'0') AND LPAD(SUBALTERNO,4, '0') = LPAD(?,4,'0') "+
								"AND SEZIONE # ORDER BY FORNITURA DESC";
	final String SQL_DEL_CIV_UIU = "DELETE FROM SITICIVI_UIU WHERE PKID_CIVI=? AND PKID_UIU=? AND DATA_FINE_VAL= TO_DATE('31129999','DDMMYYYY')";
 
	private IndiceCorrelazioneService indiceService ;
	private IciService iciService ;
	private TarsuService tarsuService ;
	private String enteID;
	
	public NormalizzaCiviciUI(BeanCommand bc) {
		super(bc);
	}

	public NormalizzaCiviciUI(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}

	@Override
	public CommandAck run(Context ctx) throws CommandException {
		log.debug("NormalizzaCiviciUI run()");
		CommandAck retAck = null; 
		enteID= ctx.getBelfiore();
		log.debug("ENTE IN ELABORAZIONE (DA CTX): " + enteID);
		conn = ctx.getConnection((String)ctx.get("connessione"));
		PreparedStatement pst =null; ResultSet rs =null; Statement st =null;
		indiceService=	(IndiceCorrelazioneService)ServiceLocator.getInstance().getService("CT_Service", "CT_Service_Data_Access", "IndiceCorrelazioneServiceBean");
		iciService=	(IciService)ServiceLocator.getInstance().getService("CT_Service", "CT_Service_Data_Access", "IciServiceBean");
		tarsuService=	(TarsuService)ServiceLocator.getInstance().getService("CT_Service", "CT_Service_Data_Access", "TarsuServiceBean");
		try {
			pst = conn.prepareStatement(SQL_UIU_CIV_MUL);
			rs = pst.executeQuery();
			scorreUiu:
			while(rs.next()){
				Long idUiu = rs.getLong("PKID_UIU");
				String codNazionale= rs.getString("COD_NAZIONALE");
				String foglio= rs.getString("FOGLIO"); String particella = rs.getString("PARTICELLA"); String unimm = rs.getString("UNIMM"); 
				log.debug("*************************");
				log.debug("-------------------->U.I. " + foglio + "-" + particella + "-"  + unimm + " *** PKID_UIU: " + idUiu);
				List<CivicoBean> listaCiv = getListaCiviciUI(idUiu);
				int i=0;
				boolean correlato=false;
				//CORRELAZIONI ICI
				SitTIciOggetto oggIci =getUltimoIciUI(idUiu);
				String idCivicoIci = getKeyCivicoIci(oggIci);
				for (CivicoBean civico:listaCiv) {
					correlato = isCivicoCorrelatoIci(civico, idCivicoIci);
					if (correlato) {
						cancellaAltriCivici(i, listaCiv, idUiu);
						break;
					}
					i++;
				}
				if (correlato)
					continue scorreUiu;
				i=0;
				//CORRELAZIONI TARSU
				SitTTarOggetto oggTar =getUltimoTarsuUI(idUiu);
				String idCivicoTarsu = getKeyCivicoTarsu(oggTar);
				for (CivicoBean civico:listaCiv) {
					correlato = isCivicoCorrelatoTarsu(civico, idCivicoTarsu);
					if (correlato) {
						cancellaAltriCivici(i, listaCiv, idUiu);
						break;
					}
					i++;
				}
				if (correlato)
					continue scorreUiu;
				String sezione = getSezione(codNazionale);
				i=0;
				//CORRELAZIONI CATASTO
				List<String> listaCivCat = getListIdCivicoCat(sezione, foglio, particella, unimm);
				for (CivicoBean civico:listaCiv) {
					correlato = isCivicoCorrelatoCatasto(civico, listaCivCat);
					if (correlato) {
						cancellaAltriCivici(i, listaCiv, idUiu);
						break;
					}
					i++;
				}
				if (correlato)
					continue scorreUiu;
				i=0;
				//CORRELAZIONI DOCFA
				List<String> listaCivDocfa = getListIdCivicoDocfa(sezione, foglio, particella, unimm);
				for (CivicoBean civico:listaCiv) {
					correlato = isCivicoCorrelatoDocfa(civico, listaCivDocfa);
					if (correlato) {
						cancellaAltriCivici(i, listaCiv, idUiu);
						break;
					}
					i++;
				}
				i=0;
				//CORRELAZIONI SOGGETTI TITOLARI CATATASTALI -DEMOGRAFIA (controllo se titolare risiede al civico)
				for (CivicoBean civico:listaCiv) {
					ChkDatiUI checkUi = new ChkDatiUI(conn, Long.parseLong(foglio), particella, Long.parseLong(unimm), "");
					List<String> listaIdCiv = new ArrayList<String>();
					listaIdCiv.add(civico.getPkidCivi()+"");
					DatiTitolariUI datiTitUi =checkUi.getDatiTitolariUI(listaIdCiv);
					correlato = datiTitUi.isUnTitolareRisiede();
					if (correlato) {
						cancellaAltriCivici(i, listaCiv, idUiu);
						break;
					}
					i++;
				}
			}
		}catch (SQLException e) {
			log.error("ERRORE SQL " + e, e);
		}finally {
			try {
				DbUtils.close(rs);
				DbUtils.close(pst);
			}
			catch (SQLException sqle) {
				log.error("ERRORE CHIUSURA OGGETTI SQL", sqle);
			}
		}
		retAck = new ApplicationAck("ESECUZIONE OK");
		return retAck;
	}
	
	
	private List<CivicoBean> getListaCiviciUI(Long pkidUiu)  throws SQLException{
		List<CivicoBean> listaCiv = new ArrayList<CivicoBean>();
		PreparedStatement pst = null; ResultSet rs=null;
		try {
			pst = conn.prepareStatement(SQL_CIV_UIU);
			pst.setLong(1, pkidUiu);
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
			log.error("ERRORE SQL IN FASE DI REPERIMENTO CIVICI - SQL: " + 	SQL_CIV_UIU );
			log.error("ERRORE SQL IN FASE DI REPERIMENTO CIVICI - parms: pkidUiu [" + pkidUiu + "]",e);
			throw e;
		}finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return listaCiv;
	}
	private SitTIciOggetto getUltimoIciUI (Long pkIdUiu) {
		List<SitTIciOggetto> listaObjIci = getListaObjIci(pkIdUiu.toString());
		SitTIciOggetto oggIci =null;
		if (listaObjIci != null && listaObjIci.size()>0) {
			oggIci=listaObjIci.get(0);
		}
		return oggIci;
	}
	
	private List<SitTIciOggetto> getListaObjIci(String idUiu) {
		List<Object> listaObj= getListObjIndice(idUiu, "2", "2");
		List<SitTIciOggetto> listaKeyObjIci = new ArrayList<SitTIciOggetto> ();
		if (listaObj==null || listaObj.size()==0) {
			return new ArrayList<SitTIciOggetto> ();
		}
		for (Object obj: listaObj) {
			if (obj instanceof SitTIciOggetto)
				listaKeyObjIci.add((SitTIciOggetto)obj);
		}
		return iciService.getListaOggettiByListaIdOggDWh(listaKeyObjIci);
	}
	
	private List<Object> getListObjIndice(String idUiu, String destFonte, String progEs) {
		RicercaIndiceDTO ri = new RicercaIndiceDTO();
		ri.setEnteId(enteID);
		IndicePK indPk = new IndicePK();
		indPk.setFkEnteSorgente(4);
		indPk.setProgEs(1);
		indPk.setIdDwh(idUiu);
		ri.setObj(indPk);
		ri.setDestFonte(destFonte);
		ri.setDestProgressivoEs(progEs);
		List<Object> listaObj =(List<Object>)indiceService.getOggettiCorrelatiById(ri);
		return listaObj;
	}
	private String getKeyCivicoIci(SitTIciOggetto ogg) throws SQLException{
		if (ogg==null)
			return null;
		String idVia = null;
		String civ = ogg.getNumCivDaDB();
		String esp = ogg.getEspCiv();
		if (esp==null)
			esp="";
		PreparedStatement pst = null; ResultSet rs=null;
		String idCivicoIci="";
		try {
			pst = conn.prepareStatement(SQL_ICI_VIA);
			pst.setString(1, ogg.getIdExtVia());
			rs = pst.executeQuery();
			
			if (rs.next()){
				idVia = rs.getString("ID");
			}
			rs.close();
			pst.close();
			idCivicoIci = idVia + "|" + civ + "|"  + esp.trim();
		}catch (SQLException e) {
			log.error("getKeyCivicoIci()-ERRORE SQL", e );
			throw e;
		}
			
		finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return idCivicoIci ;
	
	}
	private boolean isCivicoCorrelatoIci(CivicoBean civico, String idCivicoIci ) 	throws SQLException{
		if (idCivicoIci==null)
			return false;
		boolean retVal=false;
		PreparedStatement pst = null; ResultSet rs=null;
		try {
			pst = conn.prepareStatement(SQL_CIV_CORR);
			pst.setString(1, "2");
			pst.setString(2, "2");
			pst.setString(3, idCivicoIci);
			pst.setString(4, civico.getPkidCivi()+"");
			log.info("isCivicoCorrelatoIci()-sqlCorr:" + SQL_CIV_CORR);
			log.info("isCivicoCorrelatoIci()-parm:" + "(2);(2);("+ idCivicoIci + ");(" + civico.getPkidCivi() +")");
			rs = pst.executeQuery();
			if (rs.next())
				retVal=true;
		}catch (SQLException e) {
			log.error("isCivicoCorrelatoIci()-ERRORE SQL", e );
			throw e;
		}
			
		finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return retVal;
		
	}
	
	private SitTTarOggetto getUltimoTarsuUI (Long pkIdUiu) {
		List<SitTTarOggetto> listaObjTarsu = getListaObjTarsu(pkIdUiu.toString());
		SitTTarOggetto oggTar =null;
		if (listaObjTarsu != null && listaObjTarsu.size()>0) {
			oggTar=listaObjTarsu.get(0);
		}
		return oggTar;
	}
	private List<SitTTarOggetto> getListaObjTarsu(String idUiu) {
		List<Object> listaObj= getListObjIndice(idUiu, "2", "3");
		List<SitTTarOggetto> listaKeyObjTar = new ArrayList<SitTTarOggetto> ();
		if (listaObj==null || listaObj.size()==0) {
			return new ArrayList<SitTTarOggetto> ();
		}
		for (Object obj: listaObj) {
			if (obj instanceof SitTTarOggetto)
				listaKeyObjTar.add((SitTTarOggetto)obj);
		}
		return tarsuService.getListaOggettiByListaIdOggDWh(listaKeyObjTar);

	}
	
	private String getKeyCivicoTarsu(SitTTarOggetto ogg) throws SQLException{
		if (ogg==null)
			return null;
		String idVia = null;
		String civ = ogg.getNumCiv();
		String esp = ogg.getEspCiv();
		if (esp==null)
			esp="";
		PreparedStatement pst = null; ResultSet rs=null;
		String idCivicoTarsu="";
		try {
			pst = conn.prepareStatement(SQL_TAR_VIA);
			pst.setString(1, ogg.getIdExtVia());
			rs = pst.executeQuery();
			if (rs.next()){
				idVia = rs.getString("ID");
			}
			rs.close();
			pst.close();
			idCivicoTarsu =  idVia + "|" + civ + "|"  + esp.trim();
			
		}catch (SQLException e) {
			log.error("getKeyCivicoTarsu()-ERRORE SQL", e );
			throw e;
		}
			
		finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return idCivicoTarsu ;
	
	}
	
	private boolean isCivicoCorrelatoTarsu(CivicoBean civico, String idCivicoTarsu  ) 	throws SQLException{
		if (idCivicoTarsu==null)
			return false;
		boolean retVal = false;
		PreparedStatement pst = null; ResultSet rs=null;
		try {
			pst = conn.prepareStatement(SQL_CIV_CORR);
			pst.setString(1, "2");
			pst.setString(2, "3");
			pst.setString(3, idCivicoTarsu);
			pst.setString(4, civico.getPkidCivi()+"");
			log.info("isCivicoCorrelatoTarsu()-sqlCorr:" + SQL_CIV_CORR);
			log.info("isCivicoCorrelatoTarsu()-parm:" + "(2);(3);("+ idCivicoTarsu + ");(" + civico.getPkidCivi()+ ")");
			rs = pst.executeQuery();
			if (rs.next())
				retVal=true;
		}catch (SQLException e) {
			log.error("isCivicoCorrelatoTarsu()-ERRORE SQL",e);
			throw e;
		}
		finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return retVal;
		
	}
	private List<String> getListIdCivicoCat(String sezione, String foglio, String particella, String sub )throws SQLException{
		PreparedStatement pst = null; ResultSet rs=null;
		List<String> lista = new ArrayList<String>();
		String sqlCivicoCat=SQL_CAT_UIU;
		if (sezione !=null && !sezione.trim().equals(""))	
			sqlCivicoCat += " AND A.SEZIONE = ?" ; 
		
		try {
			String idCivicoCat="";
			pst = conn.prepareStatement(sqlCivicoCat);
			pst.setString(1, foglio);
			pst.setString(2, particella);
			pst.setString(3, sub);
			if (sezione !=null && !sezione.trim().equals(""))
				pst.setString(4, sezione);
			log.info("getListIdCivicoCat()-sql:" + sqlCivicoCat);
			log.info("getListIdCivicoCat()-parms (" + foglio +");(" + particella + ");(" + sub + ");(" + sezione + ")");
			rs = pst.executeQuery();
			while (rs.next()){
				idCivicoCat = rs.getString("ID_IMM") + "|"; 
				idCivicoCat += rs.getString("PROG") + "|" ;
				idCivicoCat += rs.getString("SEQ") + "|" ;
				idCivicoCat += rs.getString("CODI_FISC_LUNA")+ "|" ;
				if (rs.getString("SEZIONE")!=null)
					idCivicoCat += rs.getString("SEZIONE");
				lista.add(idCivicoCat);
				log.info("getIdCivicoCat:" + idCivicoCat);
			}
		}catch (SQLException e) {
			log.error("getListIdCivicoCat()-ERRORE SQL",e);
			throw e;
		}
		finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return lista;
	}	
	
	private boolean isCivicoCorrelatoCatasto(CivicoBean civico, List<String> listaCivCat) 	throws SQLException{
		boolean retVal = false;
		if (listaCivCat.size() == 0){
			return false;
		}
		PreparedStatement pst = null; ResultSet rs=null;
		try {
			for (String idCivicoCat: listaCivCat) {
				pst = conn.prepareStatement(SQL_CIV_CORR);
				pst.setString(1, "4");
				pst.setString(2, "4");
				pst.setString(3, idCivicoCat);
				pst.setString(4, civico.getPkidCivi()+"");
				log.info("isCivicoCorrelatoCatasto()-sqlCorr:" + SQL_CIV_CORR);
				log.info("isCivicoCorrelatoCatasto()-parm:" + "(4);(4);("+ idCivicoCat + ");(" + civico.getPkidCivi()+")");
				rs = pst.executeQuery();
				if (rs.next()) {
					retVal=true;
					break;
				}
				rs.close();
				pst.close();		
			}
			
		}catch (SQLException e) {
			log.error("isCivicoCorrelatoCatasto()-ERRORE SQL",e);
			throw e;
		}
		finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return retVal;
	}
	private List<String> getListIdCivicoDocfa(String sezione, String foglio, String particella, String sub ) 	throws SQLException{
		List<String> lista = new ArrayList<String>();
		PreparedStatement pst = null; ResultSet rs=null;
		String sqlDocfa = SQL_DOCFA_UIU;
		if (sezione ==null || sezione.trim().equals(""))	
			sqlDocfa= sqlDocfa.replace("#", " IS NULL");
		else {
			sqlDocfa= sqlDocfa.replace("#", " =?");
		}
			
		try {
			String idCivicoDocfa="";
			pst = conn.prepareStatement(sqlDocfa);
			pst.setString(1, foglio);
			pst.setString(2, particella);
			pst.setString(3, sub);
			if (sezione !=null && !sezione.trim().equals(""))
				pst.setString(4, sezione);
			log.info("getListIdCivicoDocfa()-sql:" + SQL_DOCFA_UIU);
			log.info("getListIdCivicoDocfa()-parms:foglio;partiella;sub;sezione (" + foglio +");(" + particella + ");(" + sub + ");(" + sezione +")");
			rs = pst.executeQuery();
			while (rs.next()){
				idCivicoDocfa = rs.getString("PROTOCOLLO_REG");
				String fornitura="";
				try  {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					fornitura =  sdf.format(rs.getDate("FORNITURA"));
				}catch (Exception e) {	}
				idCivicoDocfa +="|" + fornitura + "|" + rs.getString("NR_PROG"); 
				lista.add(idCivicoDocfa);
			}
			rs.close();
			pst.close();
		}catch (SQLException e) {
			log.error("getListIdCivicoDocfa()-ERRORE SQL",e);
			throw e;
		}
		finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return lista;
		
	}
	
	
	private boolean isCivicoCorrelatoDocfa(CivicoBean civico, List<String> listaCivDocfa ) 	throws SQLException{
		boolean retVal = false;
		if (listaCivDocfa.size() == 0) 
			return false;
		PreparedStatement pst = null; ResultSet rs=null;
		try {
			for (String idCivicoDocfa: listaCivDocfa ) {
				pst = conn.prepareStatement(SQL_CIV_CORR);
				pst.setString(1, "9");
				pst.setString(2, "3");
				pst.setString(3, idCivicoDocfa);
				pst.setString(4, civico.getPkidCivi()+"");
				log.info("isCivicoCorrelatoDocfa()-sqlCorr:" + SQL_CIV_CORR);
				log.info("isCivicoCorrelatoDocfa()-parm:" + "9/3/"+ idCivicoDocfa + "/" + civico.getPkidCivi());
				rs = pst.executeQuery();
				if (rs.next()) {
					retVal=true;
					break;
				}
				rs.close();
				pst.close();
						
			}
			
		}catch (SQLException e) {
			log.error("isCivicoCorrelatoDocfa()-ERRORE SQL",e);
			throw e;
		}
		finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		return retVal;
		
	}
	
	private String getSezione(String codNazionale) throws SQLException{
		String sez="";
		PreparedStatement pst = null; ResultSet rs=null;
		String idCivicoDocfa="";
		try {
			pst = conn.prepareStatement(SQL_SEZIONE);
			pst.setString(1, codNazionale);
			log.info("getSezione()-sql:" + SQL_SEZIONE);
			log.info("getSezione()-parm:" + codNazionale);
			rs = pst.executeQuery();
			if (rs.next()){
				sez= rs.getString("ID_SEZC");
			}
			
		}catch (SQLException e) {
			log.error("getSezione()-ERRORE SQL",e);
			throw e;
		}
		finally {
			DbUtils.close(rs);
			DbUtils.close(pst);
		}
		if (sez==null)
			sez="";
			
		return sez;
	}	
	
	private void cancellaAltriCivici (int numEle , List<CivicoBean> listaCiv, Long idUiu) throws SQLException{
		
		log.debug("----------ID_UIU: " + idUiu);
		log.debug("-->CIVICO GIUSTO: " + listaCiv.get(numEle).getPkidCivi());
		PreparedStatement pst = null; ResultSet rs=null;
		for (int i = 0; i<listaCiv.size(); i++) {
			if (i != numEle)  {
				log.info("*-CIVICO DA CANCELLARE: " + listaCiv.get(i).getPkidCivi());
				log.info("*-SQL_CANCELLAZIONE: " + SQL_DEL_CIV_UIU);
				try {
					pst = conn.prepareStatement(SQL_DEL_CIV_UIU);
					pst.setLong(1, listaCiv.get(i).getPkidCivi());
					pst.setLong(2, idUiu);
					pst.execute();
	
				}catch (SQLException e) {
					log.error("cancellaAltriCivici()-ERRORE SQL",e);
					throw e;
				}
				finally {
					DbUtils.close(rs);
					DbUtils.close(pst);
				}
			}
		}
		
	}
}

package it.webred.ct.data.access.basic.indice.ricerca.dao;

import it.webred.ct.data.access.basic.catasto.dao.CatastoQueryBuilder;
import it.webred.ct.data.access.basic.indice.IndiceServiceException;
import it.webred.ct.data.access.basic.indice.oggetto.dto.SitOggettoDTO;
import it.webred.ct.data.access.basic.indice.ricerca.KeyFabbricatoDTO;
import it.webred.ct.data.access.basic.indice.ricerca.KeyUIDTO;
import it.webred.ct.data.model.catasto.Siticivi;
import it.webred.ct.data.model.concedilizie.SitCConcIndirizzi;
import it.webred.ct.data.model.concedilizie.SitCConcessioniCatasto;
import it.webred.ct.data.model.ici.VTIciCiviciAll;
import it.webred.ct.data.model.tarsu.VTTarCiviciAll;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

public class IndiceCorrelazioneIndirettaJPAImpl implements	IndiceCorrelazioneIndirettaDAO {
	
	protected Logger logger = Logger.getLogger("ctservice.log");
	
	@PersistenceContext(unitName="CT_Diogene")
	protected EntityManager manager_siti;
	 
	private String SQL_LISTA_INDIRIZZI_BY_FP = 
		"SELECT DISTINCT SITICIVI.PKID_CIVI " +
		"FROM SITIUIU, SITICOMU, SITICIVI_UIU, SITIDSTR , SITICIVI " +
		"WHERE SITIUIU.COD_NAZIONALE = SITICOMU.COD_NAZIONALE " +
		"AND   SITICIVI_UIU.PKID_UIU = SITIUIU.PKID_UIU " +
		"AND   SITICIVI_UIU.PKID_CIVI = SITICIVI.PKID_CIVI " +
		"AND   SITICIVI.PKID_STRA = SITIDSTR.PKID_STRA " +
		"AND   SITICOMU.CODI_FISC_LUNA = ? " +
		"AND   (SITICOMU.ID_SEZC IS NULL  OR SITICOMU.ID_SEZC =?) " +
		"AND   SITIUIU.FOGLIO =  TO_NUMBER(?) " +
		"AND   SITIUIU.PARTICELLA= LPAD(? ,5,'0') " +
		"AND   SITICIVI_UIU.DATA_INIZIO_VAL <= ? AND SITICIVI_UIU.DATA_FINE_VAL >= ?  ";
	
	private String SQL_LISTA_INDIRIZZI_BY_FPS=  SQL_LISTA_INDIRIZZI_BY_FP + "AND SITIUIU.UNIMM= ? " ;
	
	private String SQL_BASE_LISTA_OGGETTI_ICI_BY_CIVICO = 
		"SELECT O.ID FROM SIT_T_ICI_OGGETTO O,  SIT_T_ICI_VIA V " +
		 "WHERE O.ID_EXT_VIA= V.ID_EXT 	AND V.ID=? AND NUM_CIV=? "; 
	
	private String SQL_BASE_LISTA_OGGETTI_TARSU_BY_CIVICO = 
		"SELECT O.ID FROM SIT_T_TAR_OGGETTO O,  SIT_T_TAR_VIA V " +
		 "WHERE O.ID_EXT_VIA= V.ID_EXT 	AND V.ID=? AND NUM_CIV=? "; 
	
	private String SQL_BASE_LISTA_OGGETTI_CONCESSIONI_BY_ID_CONC_INDIRIZZI= 
		"SELECT CO.* FROM SIT_C_CONCESSIONI CO, SIT_C_CONC_INDIRIZZI CI " +
		" WHERE CI.ID_EXT_C_CONCESSIONI=CO.ID_EXT AND CI.ID=?";

	private String SQL_BASE_LISTA_OGGETTI_CONCESSIONI_BY_ID_CONC_CATASTO= 
		"SELECT CO.* FROM SIT_C_CONCESSIONI CO, SIT_C_CONCESSIONI_CATASTO CC " +
		" WHERE CC.ID_EXT_C_CONCESSIONI=CO.ID_EXT AND CC.ID=?";

	@Override
	public List<BigDecimal> getCiviciFromFabbricato(KeyFabbricatoDTO keyFabbr)	throws IndiceServiceException {
		List<BigDecimal>  listaPkidCivici = new ArrayList<BigDecimal>(); 
		String codNazionale = keyFabbr.getCodNazionale();
		String sezione = keyFabbr.getSezione();
		String foglio = keyFabbr.getFoglio();
		String particella = keyFabbr.getParticella();
		Date dtVal =  keyFabbr.getDtVal();
		
		try {
			String sql = SQL_LISTA_INDIRIZZI_BY_FP;
			if (sezione==null)
				sezione="";
			else
				sezione = sezione.trim();
			if (dtVal==null)
				dtVal = new Date();
			
		    Query q = manager_siti.createNativeQuery(sql);
			q.setParameter(1, codNazionale);
			q.setParameter(2, sezione);
			q.setParameter(3, foglio);
			q.setParameter(4, particella);
			q.setParameter(5, dtVal);
			q.setParameter(6, dtVal);
			logger.info("getCiviciFromFabbricato().SQL: " + sql);
			logger.info("PARMS SQL(codNaz-sez-f-p-s-dtVal): " + codNazionale + "-" + sezione + "-" + foglio + "-" + particella + "-" + dtVal);
			listaPkidCivici= (List<BigDecimal>)q.getResultList();
			logger.info("getCiviciFromFabbricato().N.ELE: " + listaPkidCivici.size());
		} catch (Throwable t) {
			throw new IndiceServiceException(t);
		}
		return listaPkidCivici;
	}

	@Override
	public List<BigDecimal> getCiviciFromUI(KeyUIDTO keyUI)	throws IndiceServiceException {
		List<BigDecimal>  listaPkidCivici = new ArrayList<BigDecimal>(); 
		String codNazionale = keyUI.getCodNazionale();
		String sezione = keyUI.getSezione();
		String foglio = keyUI.getFoglio();
		String particella = keyUI.getParticella();
		String sub = keyUI.getSub();
		Date dtVal =  keyUI.getDtVal();
		
		try {
			String sql = SQL_LISTA_INDIRIZZI_BY_FPS;
			if (sezione==null)
				sezione="";
			else
				sezione = sezione.trim();
			if (dtVal==null)
				dtVal = new Date();
			
		    Query q = manager_siti.createNativeQuery(sql);
			q.setParameter(1, codNazionale);
			q.setParameter(2, sezione);
			q.setParameter(3, foglio);
			q.setParameter(4, particella);
			q.setParameter(5, dtVal);
			q.setParameter(6, dtVal);
			q.setParameter(7, sub);
			logger.info("getCiviciFromUI().SQL: " + sql);
			logger.info("PARMS SQL (codNaz-sez-f-p-s-dtVal): " + codNazionale + "-" + sezione + "-" + foglio + "-" + particella + "-" + sub + "-" + dtVal);
			listaPkidCivici= (List<BigDecimal>)q.getResultList();
			logger.info("getCiviciFromUI().N.ELE: " + listaPkidCivici.size());
		} catch (Throwable t) {
			throw new IndiceServiceException(t);
		}
		return listaPkidCivici;
	}

	public List<String> getOggettiIciFromCivicoIci(VTIciCiviciAll civIci )	throws IndiceServiceException {
		List<String> listaIdOggIci = new ArrayList<String>();
		String idVia = civIci.getId();
		String numCiv = civIci.getNumCiv();
		String espCiv = civIci.getEspCiv();
		try {
			String sql = SQL_BASE_LISTA_OGGETTI_ICI_BY_CIVICO;
			if (espCiv!=null && !espCiv.equals(""))
				sql+= " AND ESP_CIV=? ";
			else
				sql+= " AND ESP_CIV IS NULL ";
		    Query q = manager_siti.createNativeQuery(sql);
			q.setParameter(1, idVia);
			q.setParameter(2, numCiv);
			if (espCiv!=null && !espCiv.equals(""))
				q.setParameter(3, espCiv);
			
			logger.info("getOggettiIciFromCivicoIci().SQL: " + sql);
			logger.info("PARMS SQL (idVvia-civico-espCivico): " + idVia + "-" + numCiv + "-" + espCiv );
			listaIdOggIci= (List<String>)q.getResultList();
			logger.info("getCiviciFromUI().N.ELE: " + listaIdOggIci.size());
		} catch (Throwable t) {
			throw new IndiceServiceException(t);
		}
		return listaIdOggIci;
	}
	
	public List<String> getOggettiTarsuFromCivicoTarsu(VTTarCiviciAll civIci )	throws IndiceServiceException {
		List<String> listaIdOggIci = new ArrayList<String>();
		String idVia = civIci.getId();
		String numCiv = civIci.getNumCiv();
		String espCiv = civIci.getEspCiv();
		try {
			String sql = SQL_BASE_LISTA_OGGETTI_TARSU_BY_CIVICO;
			if (espCiv!=null && !espCiv.equals(""))
				sql+= " AND ESP_CIV=? ";
			else
				sql+= " AND ESP_CIV IS NULL ";
		    Query q = manager_siti.createNativeQuery(sql);
			q.setParameter(1, idVia);
			q.setParameter(2, numCiv);
			if (espCiv!=null && !espCiv.equals(""))
				q.setParameter(3, espCiv);
			
			logger.info("getOggettiTarsuFromCivicoTarsu().SQL: " + sql);
			logger.info("PARMS SQL (idVvia-civico-espCivico): " + idVia + "-" + numCiv + "-" + espCiv );
			listaIdOggIci= (List<String>)q.getResultList();
			logger.info("getOggettiTarsuFromCivicoTarsu().N.ELE: " + listaIdOggIci.size());
		} catch (Throwable t) {
			throw new IndiceServiceException(t);
		}
		return listaIdOggIci;
	}
	
	public List<String> getConcessioniFromIndirizzoConcessioni(SitCConcIndirizzi indConc )	throws IndiceServiceException {
		List<String> listaIdConc = new ArrayList<String>();
		String idIndirizzoConc = indConc.getId();
		
		try {
			String sql = SQL_BASE_LISTA_OGGETTI_CONCESSIONI_BY_ID_CONC_INDIRIZZI;
		    Query q = manager_siti.createNativeQuery(sql);
			q.setParameter(1, idIndirizzoConc);
			logger.info("getConcessioniFromIndirizzoConcessioni().SQL: " + sql);
			logger.info("PARMS SQL (SitCConcIndirizzi.ID): " + idIndirizzoConc );
			listaIdConc= (List<String>)q.getResultList();
			logger.info("getConcessioniFromIndirizzoConcessioni().N.ELE: " + listaIdConc.size());
		} catch (Throwable t) {
			throw new IndiceServiceException(t);
		}
		return listaIdConc;
	}
	public List<String> getConcessioniFromDatiCatastoConcessioni(SitCConcessioniCatasto concCat )	throws IndiceServiceException {
		List<String> listaIdConc = new ArrayList<String>();
		String idConcCat = concCat.getId();
		
		try {
			String sql = SQL_BASE_LISTA_OGGETTI_CONCESSIONI_BY_ID_CONC_CATASTO;
		    Query q = manager_siti.createNativeQuery(sql);
			q.setParameter(1, idConcCat);
			logger.info("getConcessioniFromDatiCatastoConcessioni().SQL: " + sql);
			logger.info("PARMS SQL (SitCConcessioniCatasto.ID): " + idConcCat );
			listaIdConc= (List<String>)q.getResultList();
			logger.info("getConcessioniFromDatiCatastoConcessioni().N.ELE: " + listaIdConc.size());
		} catch (Throwable t) {
			throw new IndiceServiceException(t);
		}
		return listaIdConc;
	}
	

}

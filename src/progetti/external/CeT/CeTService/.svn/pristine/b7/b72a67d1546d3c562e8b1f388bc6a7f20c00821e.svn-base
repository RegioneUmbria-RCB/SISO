package it.webred.ct.data.access.basic.docfa.dao;

import it.webred.ct.data.access.aggregator.elaborazioni.dto.ClassamentoDTO;
import it.webred.ct.data.access.aggregator.elaborazioni.dto.ClasseMaxCategoriaDTO;
import it.webred.ct.data.access.aggregator.elaborazioni.dto.ControlloClassamentoConsistenzaDTO;
import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.catasto.dto.ParametriCatastaliDTO;
import it.webred.ct.data.access.basic.common.utils.Info;
import it.webred.ct.data.access.basic.common.utils.StringUtils;
import it.webred.ct.data.access.basic.docfa.DocfaQueryBuilder;
import it.webred.ct.data.access.basic.docfa.DocfaServiceException;
import it.webred.ct.data.access.basic.docfa.dto.BeniNonCensDTO;
import it.webred.ct.data.access.basic.docfa.dto.DatiDocfaDTO;
import it.webred.ct.data.access.basic.docfa.dto.DatiGeneraliDocfaDTO;
import it.webred.ct.data.access.basic.docfa.dto.DocfaInParteUnoDTO;
import it.webred.ct.data.access.basic.docfa.dto.DocfaSearchCriteria;
import it.webred.ct.data.access.basic.docfa.dto.FoglioMicrozonaDTO;
import it.webred.ct.data.access.basic.docfa.dto.OperatoreDTO;
import it.webred.ct.data.access.basic.docfa.dto.RicercaOggettoDocfaDTO;
import it.webred.ct.data.access.basic.docfa.dto.ZonaOmiDTO;
import it.webred.ct.data.model.docfa.DocfaAnnotazioni;
import it.webred.ct.data.model.docfa.DocfaDatiCensuari;
import it.webred.ct.data.model.docfa.DocfaDatiCensuariPK;
import it.webred.ct.data.model.docfa.DocfaDatiGenerali;
import it.webred.ct.data.model.docfa.DocfaDatiMetrici;
import it.webred.ct.data.model.docfa.DocfaDichiaranti;
import it.webred.ct.data.model.docfa.DocfaFogliMicrozona;
import it.webred.ct.data.model.docfa.DocfaInParteDueH;
import it.webred.ct.data.model.docfa.DocfaInParteUno;
import it.webred.ct.data.model.docfa.DocfaIntestati;
import it.webred.ct.data.model.docfa.DocfaPlanimetrie;
import it.webred.ct.data.model.docfa.DocfaUiu;
import it.webred.ct.data.model.docfa.DocfaValori;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

public class DocfaJPAImpl extends CTServiceBaseDAO implements DocfaDAO { 
	
	private static final long serialVersionUID = 1L;

	private SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	
	private static final Map<String, String> causali = new HashMap<String, String>() {
		{
			put("NC", "ACCATASTAMENTO");
			put("AFF", "DENUNCIA UNITA AFFERENTE");
			put("DIV", "DIVISIONE");
			put("FRZ", "FRAZIONAMENTO");
			put("FUS", "FUSIONE");
			put("AMP", "AMPLIAMENTO");
			put("DET", "DEMOLIZIONE TOTALE");
			put("DEP", "DEMOLIZIONE PARZIALE");
			put("VSI", "VARIAZIONE SPAZI INTERNI");
			put("RST", "RISTRUTTURAZIONE");
			put("FRF", "FRAZIONAMENTO E FUSIONE");
			put("VTO", "VARIAZIONE TOPONOMASTICA");
			put("UFU", "ULTIMAZIONE FABBRICATO URBANO");
			put("VDE", "VARIAZIONE DELLA DESTINAZIONE");
			put("VAR", "ALTRE VARIAZIONI");
			put("VRP", "PRESENTAZIONE PLANIMETRIA MANCANTE");
			put("VMI", "MODIFICA DI IDENTIFICATIVO");
		}
	};
	
	@Override
	public List<String> getSuggestionVieDocfaUiu(String via) throws DocfaServiceException {
				
		try {
				Query q = manager_diogene.createNamedQuery("DocfaUiu.getVie");
				q.setParameter("via", via.toUpperCase());
				q.setMaxResults(10);
				return q.getResultList();
				
		} catch (Throwable t) {
			throw new DocfaServiceException(t);
		}
	}
	
	@Override
	public List<String> getSuggestionCiviciByViaDocfaUiu(String via) throws DocfaServiceException {
				
		try {
				Query q = manager_diogene.createNamedQuery("DocfaUiu.getCiviciByVia");
				q.setParameter("via", via.toUpperCase());
				q.setMaxResults(10);
				return q.getResultList();
				
		} catch (Throwable t) {
			throw new DocfaServiceException(t);
		}
	}
	
	@Override
	public List<String> getSuggestionDichiarante(String denominazione) throws DocfaServiceException {
		List<String> lista = new ArrayList<String>();
		try {

				Query q = manager_diogene.createNamedQuery("DocfaDichiaranti.getSuggestionDichiarante");
				q.setParameter("denominazione", denominazione.toUpperCase());
				q.setMaxResults(10);
				List<Object[]> result =  q.getResultList();
				for(Object[] res : result){
					String s = (res[0]+" "+res[1]);
					lista.add(s);
				}
				
		} catch (Throwable t) {
			throw new DocfaServiceException(t);
		}
		
		return lista;
	}
	
	@Override
	public List<Date> getListaForniture(){
		List<Date> lista = new ArrayList<Date>();
		/*
		 * 
		 */
		CeTBaseObject cet = new CeTBaseObject();
		cet.setEnteId("FFFF");
		cet.setUserId("ALE");
		getListaTest(cet);
		/*
		 * 
		 */
		try {
			Query q = manager_diogene.createNamedQuery("DocfaUiu.getForniture");
			lista =  q.getResultList();

	} catch (Throwable t) {
		throw new DocfaServiceException(t);
	}
		
		return lista;
	}
	
	public int getListaTest(CeTBaseObject cet){
		logger.debug("getListaTest(");
		return 1;
	}
	
	public List<DocfaDatiCensuari> getListaDocfaImmobile(RicercaOggettoDocfaDTO ro) {
		
		List<DocfaDatiCensuari> docfa = new ArrayList<DocfaDatiCensuari>();		
		
		String foglio = ro.getFoglio();
		String particella = ro.getParticella();
		String unimm = ro.getUnimm();
		String sezione = ro.getSezione();
		logger.info("RICERCA DOCFA [" +
				"Foglio: "+foglio+", " +
				"Particella: "+particella+", " +
				"Subalterno: "+unimm+"]");
		
		if
		(
				(foglio != null && !foglio.equals("")) &&
				(particella != null && !particella.equals("")) &&
				(unimm != null && !unimm.equals(""))
		)
		
		{
			try{
					//Esecuzione Query per estrazione dati docfa
					Query q1 = null;
					if (sezione==null)	
						q1 = manager_diogene.createNamedQuery("DocfaDatiCensuari.getDocfaDatiCensuariByFPS");
					else {
						q1 = manager_diogene.createNamedQuery("DocfaDatiCensuari.getDocfaDatiCensuariBySezFPS");
						q1.setParameter("sezione", sezione);
					}
						
					q1.setParameter("foglio", foglio);
					q1.setParameter("particella", particella);
					q1.setParameter("subalterno", unimm);
					docfa = q1.getResultList();
					logger.debug("Result size ["+docfa.size()+"]");
					
			}catch(Throwable t) {
				logger.error("", t);
				throw new DocfaServiceException(t);
			}
		
		}else{
			
			String message = "Parametri non validi per la ricerca DOCFA [" +
             				"Foglio: "+foglio+", " +
            				"Particella: "+particella+", " +
            				"Subalterno: "+unimm+"]";
			
			throw new DocfaServiceException(message);
		}
		
		return docfa;
		
	}

	@Override
	public List<DatiDocfaDTO> getListaDatiDocfaUiu(RicercaOggettoDocfaDTO ro) {
		List<DatiDocfaDTO> lista = new ArrayList<DatiDocfaDTO>();
		try {
			String sql = "";
			String sezione =  ro.getSezione();
			if (sezione==null)
				sql = (new DocfaQueryBuilder()).getSQL_LISTA_DATI_DOCFA_BY_FPS();
			else 
				sql = (new DocfaQueryBuilder()).getSQL_LISTA_DATI_DOCFA_BY_FPS(true);
			
			Query q = manager_diogene.createNativeQuery(sql);
			q.setParameter(1,ro.getFoglio().trim());
			q.setParameter(2,ro.getParticella().trim());
			q.setParameter(3,ro.getUnimm().trim());
			logger.debug("Docfa - getListaDatiDocfaUiu(). SQL: " + sql);
			logger.debug("PARMS: [SEZIONE: "+ ro.getSezione()+"];[FOGLIO: "+ ro.getFoglio()+"];[PARTICELLA: "+ ro.getParticella()+"];[SUB: "+ ro.getUnimm()+"];") ;
			if (sezione !=null)
				q.setParameter(4,sezione.trim());
			List<Object[]> result= q.getResultList();		
			lista=  getListaDatiDocfaPerUI(result);
			logger.debug("Result size ["+lista.size()+"]");
			
		}catch(Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
		return lista;
		
	}

	@Override
	public List<DatiDocfaDTO> getListaDatiDocfaFPSubNonVal(RicercaOggettoDocfaDTO ro) {
		List<DatiDocfaDTO> lista = new ArrayList<DatiDocfaDTO>();
		String sql = "";
		String sezione =  ro.getSezione();
		if (sezione==null)
			sql = (new DocfaQueryBuilder()).getSQL_LISTA_DATI_DOCFA_BY_FPSubNonVal();
		else 
			sql = (new DocfaQueryBuilder()).getSQL_LISTA_DATI_DOCFA_BY_FPSubNonVal(true);
		try {
			Query q = manager_diogene.createNativeQuery(sql);
			q.setParameter(1,ro.getFoglio().trim());
			q.setParameter(2,ro.getParticella().trim());
			if (sezione !=null)
				q.setParameter(3,sezione.trim());
			logger.debug("Docfa - getListaDatiDocfaFPSubNonVal(). SQL: " + sql);
			logger.debug("PARMS: [SEZIONE: "+ ro.getSezione()+"];[FOGLIO: "+ ro.getFoglio()+"];[PARTICELLA: "+ ro.getParticella()+"]") ;
			List<Object[]> result= q.getResultList();		
			logger.debug("Result size ["+lista.size()+"]");
			lista=  getListaDatiDocfaPerUI(result);
		}catch(Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
		return lista;
	}
	private List<DatiDocfaDTO> getListaDatiDocfaPerUI(List<Object[]> result) {
		List<DatiDocfaDTO> lista = new ArrayList<DatiDocfaDTO>();
		for (Object[] rs : result) {
			DatiDocfaDTO dati = new DatiDocfaDTO();
			dati.setFornitura((Date)rs[0]);
			dati.setProtocolloReg((String)rs[1]);
			String data = (String)rs[2];
			if (data!=null)
				dati.setDataRegistrazione(data.substring(6, 8)+ "/" + data.substring(4, 6) + "/" +  data.substring(0, 4));
			else
				dati.setDataRegistrazione("");
			dati.setDesTipoOperazione((String)rs[3]);
			String indirizzo = (String)rs[4];
			if (indirizzo!=null) {
				indirizzo= StringUtils.pulisciVia(indirizzo);
				String civ1= (String)rs[5];
				String civ2= (String)rs[6];
				String civ3= (String)rs[7];
				if (civ1!=null)
					indirizzo += ", " + StringUtils.removeLeadingZero(civ1);
				if (civ2!=null)
					indirizzo += " " + StringUtils.removeLeadingZero(civ2);
				if (civ3!=null)
					indirizzo += " " + StringUtils.removeLeadingZero(civ3);
				dati.setIndirizzoUiuCompleto(indirizzo);
			}else
				dati.setIndirizzoUiuCompleto("");
			lista.add(dati);	
		}
		return lista;
	}
	
	@Override
	public List<DocfaDichiaranti> getDichiaranti(Date fornitura, String protocollo){
						
		try {

			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
			logger.info("Estrazione Docfa Dichiaranti: "
			        +fornitura+" "
					+protocollo);
			Query q = manager_diogene.createNamedQuery("DocfaDichiaranti.getDichiaranti");
			q.setParameter("fornitura", fornitura);
			q.setParameter("protocollo", protocollo);
			List<DocfaDichiaranti> list = q.getResultList();
			logger.info("Risultati: "+ list.size());
			return list;
			
			
		} catch (Throwable t) {
			throw new DocfaServiceException(t);
		}
		
	}
	
	@Override
	public List<DocfaIntestati> getIntestati(Date fornitura, String protocollo){
						
		try {

			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
			logger.info("Estrazione Docfa Intestati: "
			        +fornitura+" "
					+protocollo);
			
			Query q = manager_diogene.createNamedQuery("DocfaIntestati.getIntestati");
			
			q.setParameter("fornitura", fornitura);
			q.setParameter("protocollo", protocollo);
			
			List<DocfaIntestati> list =  q.getResultList();
			logger.info("Risultati: "+ list.size());
			return list;
			
			
		} catch (Throwable t) {
			throw new DocfaServiceException(t);
		}
		
	}
	
	@Override
	public List<DocfaUiu> getListaDocfaUiu(Date fornitura, String protocollo){
						
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
			logger.info("Estrazione Immobili del Docfa: "
			        +fornitura+" "
					+protocollo);
			
			Query q = manager_diogene.createNamedQuery("DocfaUiu.getListaDocfaUiuByDOCFA");
			q.setParameter("fornitura", fornitura);
			q.setParameter("protocollo", protocollo);
			
			List<DocfaUiu> list =  q.getResultList();
			logger.info("Risultati: "+ list.size());
			return list;
			
		} catch (Throwable t) {
			throw new DocfaServiceException(t);
		}
		
	}
	@Override
	public List<DocfaDatiCensuari> getListaDatiCensuariDocfa(Date fornitura, String protocollo){
		
		try {
			logger.info("Estrazione Dati Censuari per gli Immobili del Docfa: "
			        +fornitura+" "
					+protocollo);
			
			Query q = manager_diogene.createNamedQuery("DocfaDatiCensuari.getListaDatiCensuariByDOCFA");
			q.setParameter("fornitura", fornitura);
			q.setParameter("protocollo", protocollo);
			
			List<DocfaDatiCensuari> list =  q.getResultList();
			logger.info("Risultati: "+ list.size());
			return list;
			
		} catch (Throwable t) {
			throw new DocfaServiceException(t);
		}
		
	}
	
	@Override
	public List<DocfaAnnotazioni> getAnnotazioni(Date fornitura, String protocollo){
		
		try {
			
			logger.info("Estrazione Annotazioni del Docfa: "
			        +fornitura+" "
					+protocollo);
			
			Query q = manager_diogene.createNamedQuery("DocfaAnnotazioni.getAnnotazioniByDOCFA");
			q.setParameter("fornitura", fornitura);
			q.setParameter("protocollo", protocollo);
			
			List<DocfaAnnotazioni> list =  q.getResultList();
			logger.info("Risultati: "+ list.size());
			return list;
			
		} catch (Throwable t) {
			throw new DocfaServiceException(t);
		}
		
	}
	
	@Override
	public List<DocfaDatiMetrici> getDatiMetrici(RicercaOggettoDocfaDTO rs){
						
		try {
			
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
			logger.info("Estrazione Docfa Dati Metrici: "
					        +f.format(rs.getFornitura())+" "
							+rs.getProtocollo()+" "
							+rs.getDataRegistrazione()+" "
							+rs.getFoglio()+" "
							+rs.getParticella()+" "
							+rs.getUnimm());
			
			Query q = manager_diogene.createNamedQuery("DocfaDatiMetrici.getDatiMetrici");
			
			q.setParameter("fornitura", f.format(rs.getFornitura()));
			q.setParameter("protocollo", rs.getProtocollo());
			q.setParameter("data", rs.getDataRegistrazione());
			q.setParameter("foglio", rs.getFoglio());
			q.setParameter("particella", rs.getParticella());
			if(rs.getUnimm() != null)
				q.setParameter("sub", rs.getUnimm());
			else q.setParameter("sub", "0");
			List<DocfaDatiMetrici> list = q.getResultList();
			logger.info("Risultati: "+ list.size());
			return list;
			
		} catch (Throwable t) {
			throw new DocfaServiceException(t);
		}
		
	}
	
	@Override
	public List<DocfaInParteUnoDTO> getListaDocfaInParteUno(Date fornitura, String protocollo){
		List<DocfaInParteUnoDTO> lista = new ArrayList<DocfaInParteUnoDTO>();		
		try {

			Query q = manager_diogene.createNamedQuery("DocfaInParteUno.getListaParteUnoByDocfa");
			logger.info("Estrazione Lista DocfaInParteUno: "
					+protocollo+" "
					+fornitura);
			
			q.setParameter("protocollo", protocollo);
			q.setParameter("fornitura", fornitura);
			
			List<DocfaInParteUno> result = q.getResultList();
			logger.info("Risultati: "+ result.size());
			for(DocfaInParteUno d : result){
				DocfaInParteUnoDTO dto = new DocfaInParteUnoDTO();
				dto.setDocfaInParteUno(d);
				lista.add(dto);
			}
			
		} catch (Throwable t) {
			throw new DocfaServiceException(t);
		}
		
		return lista;
		
	}
	
	
	@Override
	public List<DocfaInParteDueH> getListaDocfaInParteDue(String protocollo, Date fornitura){
		List<DocfaInParteDueH> lista = new ArrayList<DocfaInParteDueH>();		
		try {

			Query q = manager_diogene.createNamedQuery("DocfaInParteDueH.getListaParteDueHByDocfa");
			logger.info("Estrazione Lista DocfaInParteDueH: "
					+protocollo+" "
					+fornitura);
			
			q.setParameter("protocollo", protocollo);
			q.setParameter("fornitura", fornitura);
			
			lista = q.getResultList();
			logger.info("Risultati: "+ lista.size());
			
			
		} catch (Throwable t) {
			throw new DocfaServiceException(t);
		}
		
		return lista;
		
	}
	
	@Override
	public DocfaInParteDueH getDatiMetriciABC(RicercaOggettoDocfaDTO rs){
						
		try {

			Query q = manager_diogene.createNamedQuery("DocfaInParteDueH.getDatiMetriciABC");
			logger.info("Estrazione DocfaInParteDueH: "
					+rs.getProtocollo()+" "
					+rs.getFornitura()+" "
					+rs.getFoglio()+" "
					+rs.getParticella()+" "
					+rs.getUnimm());
			
			q.setParameter("protocollo", rs.getProtocollo());
			q.setParameter("fornitura", rs.getFornitura());
			q.setParameter("foglio", rs.getFoglio());
			q.setParameter("particella", rs.getParticella());
			if(rs.getUnimm() != null)
				q.setParameter("sub", rs.getUnimm());
			else q.setParameter("sub", "0");
			List<DocfaInParteDueH> lista = q.getResultList();
			logger.info("Risultati: "+ lista.size());
			if(lista.size() > 0)
				return lista.get(0);
			
		} catch (Throwable t) {
			throw new DocfaServiceException(t);
			
		}
		return null;
	}
	
	@Override
	public List<DocfaPlanimetrie> getPlanimetrieDocfa(RicercaOggettoDocfaDTO rs){
						
		try {

			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat f2 = new SimpleDateFormat("dd/MM/yyyy");
			logger.info("Estrazione Docfa Planimetrie: "
			        +f.format(rs.getFornitura())+" "
					+rs.getProtocollo()+" "
					+rs.getDataRegistrazione()+" "
					+rs.getFoglio()+" "
					+rs.getParticella()+" "
					+rs.getUnimm());
			
			Query q = manager_diogene.createNamedQuery("DocfaPlanimetrie.getPlanimetrieDocfa");
			q.setParameter("fornitura", f.format(rs.getFornitura()));
			q.setParameter("protocollo", rs.getProtocollo());
			q.setParameter("data", rs.getDataRegistrazione());
		//	if (rs.getDataRegistrazione()!= null && !rs.getDataRegistrazione().equals(""))
		//		q.setParameter("dataDate", f2.parse(rs.getDataRegistrazione()));
			q.setParameter("foglio", rs.getFoglio());
			q.setParameter("particella", rs.getParticella());
			if(rs.getUnimm() != null)
				q.setParameter("sub", rs.getUnimm());
			else q.setParameter("sub", "0");
			
			List<DocfaPlanimetrie> list = q.getResultList();
			logger.info("Risultati: "+ list.size());
			return list;
			
		} catch (Throwable t) {
			throw new DocfaServiceException(t);
		}
		
	}
	
	@Override
	public List<DocfaPlanimetrie> getPlanimetriePerDocfa(RicercaOggettoDocfaDTO rs){
						
		try {

			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
			logger.info("Estrazione Docfa Planimetrie: "
			        +f.format(rs.getFornitura())+" "
					+rs.getProtocollo()+" "
					+rs.getFoglio()+" "
					+rs.getParticella());
			
			Query q = manager_diogene.createNamedQuery("DocfaPlanimetrie.getPlanimetriePerDocfa");
			q.setParameter("fornitura", f.format(rs.getFornitura()));
			q.setParameter("protocollo", rs.getProtocollo());
			q.setParameter("foglio", rs.getFoglio());
			q.setParameter("particella", rs.getParticella());
			
			
			List<DocfaPlanimetrie> list = q.getResultList();
			logger.info("Risultati: "+ list.size());
			return list;
			
		} catch (Throwable t) {
			throw new DocfaServiceException(t);
		}
		
	}
	
	@Override
	public List<DocfaPlanimetrie> getPlanimetriePerSezFglNum(RicercaOggettoDocfaDTO rs) {
		
		try {
		
			logger.info("Estrazione Docfa Planimetrie: "
					+rs.getSezione()+" "
					+rs.getFoglio()+" "
					+rs.getParticella());
			
			Query q = manager_diogene.createNamedQuery("DocfaPlanimetrie.getPlanimetriePerSezFglNum");
			q.setParameter("sezione", rs.getSezione());
			q.setParameter("foglio", rs.getFoglio());
			q.setParameter("particella", rs.getParticella());
			
			
			List<DocfaPlanimetrie> list = q.getResultList();
			logger.info("Risultati: "+ list.size());
			return list;
			
		} catch (Throwable t) {
			throw new DocfaServiceException(t);
		}

	}
	
	@Override
	public List<DocfaDatiCensuari> getDocfaDatiCensuariPerNomePlan(RicercaOggettoDocfaDTO rs) {
		try {
			
			String nomePlan = rs.getNomePlan();
			BigDecimal progressivo = rs.getProgressivo();
			
			logger.info("Estrazione Dati Censuari per Nome Planimetria: "
					+ nomePlan + ", Progressivo: " + progressivo.toString());
			
			String sql = "SELECT DISTINCT DC.FORNITURA, DC.SEZIONE, DC.FOGLIO, DC.NUMERO, DC.SUBALTERNO, DC.CATEGORIA " +
					"FROM DOCFA_DATI_CENSUARI DC, DOCFA_PLANIMETRIE DP " +
					"WHERE DP.NOME_PLAN = :nomePlan " +
					"AND DP.PROGRESSIVO = :progressivo " + 
					"AND (DC.IDENTIFICATIVO_IMMOBILE = DP.IDENTIFICATIVO_IMMO OR " +
					"(DP.IDENTIFICATIVO_IMMO = 0 AND DC.PROTOCOLLO_REGISTRAZIONE = DP.PROTOCOLLO AND DC.FORNITURA = DP.FORNITURA)) " +
					"ORDER BY DC.SEZIONE, DC.FOGLIO, DC.NUMERO, DC.SUBALTERNO";
			
			Query q = manager_diogene.createNativeQuery(sql);
			q.setParameter("nomePlan", nomePlan);
			q.setParameter("progressivo", progressivo);
			
			List<DocfaDatiCensuari> list = new ArrayList<DocfaDatiCensuari>();
			
			List<Object[]> result = q.getResultList();
			for(Object[] res : result){
				DocfaDatiCensuari ddc = new DocfaDatiCensuari();
				ddc.setId(new DocfaDatiCensuariPK());
				ddc.setFornitura((Date)res[0]);
				ddc.setSezione((String)res[1]);
				ddc.setFoglio((String)res[2]);
				ddc.setNumero((String)res[3]);
				ddc.setSubalterno((String)res[4]);
				ddc.setCategoria((String)res[5]);
				list.add(ddc);
			}
			
			logger.info("Risultati: "+ list.size());
			return list;
			
		} catch (Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
	}
	
	private DatiGeneraliDocfaDTO fillDatiGeneraliDocfaDTO(Object[] rs, RicercaOggettoDocfaDTO ro){
			logger.debug("fillDatiGeneraliDocfaDTO.....estrae tutte le u.i.: " + ro.isEstraiTutteLeUI());
			DatiGeneraliDocfaDTO dati = new DatiGeneraliDocfaDTO();
			dati.setProtocollo((String)rs[0]);
			dati.setDataFornitura((Date)rs[1]);
			dati.setDataVariazione((Date)rs[2]);
			if (rs[3] != null) {
				dati.setCausale(causali.get((String)rs[3]));
			}
			else
				dati.setCausale("");
			if (rs[4]!=null) 
				dati.setNumUiInSoppressione(((BigDecimal)rs[4]).intValue());
			if (rs[5]!=null) 
				dati.setNumUiInVariazione(((BigDecimal)rs[5]).intValue());
			if (rs[6]!=null) 
				dati.setNumUiInCostituzione(((BigDecimal)rs[6]).intValue());
			dati.setDataRegistrazione((String)rs[7]);
			//aggiungo le UI
			
			List<DocfaUiu> listaUI  = new ArrayList<DocfaUiu>();
			String elencoUI="";
			if (ro.isEstraiTutteLeUI()){
				logger.debug("estrae tutte le u.i.");
				listaUI = getListaDocfaUiu((Date)rs[1],(String)rs[0]);
				if(listaUI!=null && listaUI.size() > 0){
					for(DocfaUiu ui: listaUI) {
					if (elencoUI.equals(""))
						elencoUI= ui.getFoglio() +"-" + ui.getNumero() +"-" + ui.getSubalterno();
					else	
						elencoUI+= " / " +  ui.getFoglio() +"-" + ui.getNumero() +"-" + ui.getSubalterno();
					}
					dati.setElencoUI(elencoUI);
				}
			}
			else {	
				RicercaOggettoDocfaDTO ro1 = new RicercaOggettoDocfaDTO();
				ro1.setEnteId(ro.getEnteId());
				ro1.setUserId(ro.getUserId());
				ro1.setSezione(ro.getSezione());
				ro1.setFoglio(ro.getFoglio());
				ro1.setParticella(ro.getParticella());
				ro1.setProtocollo((String)rs[0]);
				ro1.setFornitura((Date)rs[1]);
				ro1.setDataRif(ro.getDataRif());
				listaUI = getListaDocfaUiuFabbricatoDocfa(ro1); 
				if(listaUI!=null && listaUI.size() > 0){
					logger.debug("ListaUi-size: " + listaUI.size());
					for(DocfaUiu ui: listaUI) {
						logger.debug("sub: " + ui.getSubalterno());
						if (elencoUI.equals(""))
							elencoUI= ui.getSubalterno();
						else	
							elencoUI+= " / " +  ui.getSubalterno();
					}
					dati.setElencoUI(elencoUI);
				}
			}
			
			
			
		return dati;
	}
	
	
	@Override
	public List<DatiGeneraliDocfaDTO> getListaDatiGeneraliByImmobile(RicercaOggettoDocfaDTO ro) {
		logger.debug("DocfaJPAImpl.getListaDatiGeneraliByImmobile-INIZIO- ESTRAI TUTTE LE UI["+ ro.isEstraiTutteLeUI() +"];" );
		List<DatiGeneraliDocfaDTO> lista = new ArrayList<DatiGeneraliDocfaDTO>();
		try {
			String sezione =  ro.getSezione();
			if (sezione==null)
				sezione="";
			Date dataRif =ro.getDataRif();
			if (dataRif == null)
				dataRif=new Date();
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String dataRifStr="";
			dataRifStr= df.format(dataRif);

			String sql = (new DocfaQueryBuilder()).getSQL_LISTA_DATI_GENERALI_IMMOBILE();
			Query q = manager_diogene.createNativeQuery(sql);
			q.setParameter(1,ro.getFoglio().trim());
			q.setParameter(2,ro.getParticella().trim());
			q.setParameter(3,ro.getUnimm().trim());
			q.setParameter(4,sezione);
			q.setParameter(5,dataRifStr);
			
			logger.debug("DocfaJPAImpl.getListaDatiGeneraliByImmobile - SQL["+sql+"]");
			logger.debug("PARMS - DATA_RIF["+ dataRifStr+"];" +
								 "SEZIONE["+ sezione+"];" +
								 "FOGLIO["+ ro.getFoglio().trim()+"];" +
								 "PARTICELLA["+ ro.getParticella().trim()+"];" +
								 "UNIMM["+ ro.getUnimm().trim()+"]") ;
			logger.debug("PARMS - ESTRAI TUTTE LE UI["+ ro.isEstraiTutteLeUI() +"];" );
			List<Object[]> result= q.getResultList();		
			
			for (Object[] rs : result) {
				DatiGeneraliDocfaDTO dati = this.fillDatiGeneraliDocfaDTO(rs, ro);
				lista.add(dati);	
			}
			
			logger.debug("Result size ["+lista.size()+"]");
			
		}catch(Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
		return lista;
	}
	
	

	@Override
	public List<DatiGeneraliDocfaDTO> getListaDatiGeneraliFabbricato(RicercaOggettoDocfaDTO ro) {
		List<DatiGeneraliDocfaDTO> lista = new ArrayList<DatiGeneraliDocfaDTO>();
		try {
			String sezione =  ro.getSezione();
			if (sezione==null)
				sezione="";
			Date dataRif =ro.getDataRif();
			if (dataRif == null)
				dataRif=new Date();
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String dataRifStr="";
			dataRifStr= df.format(dataRif);
			String sql = (new DocfaQueryBuilder()).getSQL_LISTA_DATI_GENERALI_FABBRICATO();
			Query q = manager_diogene.createNativeQuery(sql);
			q.setParameter(1,ro.getFoglio().trim());
			q.setParameter(2,ro.getParticella().trim());
			q.setParameter(3,sezione);
			q.setParameter(4,dataRifStr);
			logger.debug("Docfa - getListaDatiGeneraliFabbricato(). SQL: " + sql);
		
			logger.debug("PARMS - DATA_RIF["+ dataRifStr+"];SEZIONE["+ sezione+"];FOGLIO["+ ro.getFoglio().trim()+"];PARTICELLA["+ ro.getParticella().trim()+"]") ;
			List<Object[]> result= q.getResultList();		
			
			for (Object[] rs : result) {
				DatiGeneraliDocfaDTO dati = this.fillDatiGeneraliDocfaDTO(rs, ro);
				lista.add(dati);	
			}
			
			logger.debug("Result size ["+lista.size()+"]");
			
		}catch(Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
		return lista;
	}
	
	
	@Override
	public List<DocfaUiu> getListaDocfaUiu(RicercaOggettoDocfaDTO ro) {
		List<DocfaUiu> lista = new ArrayList<DocfaUiu>();
		try {
			String sezione =  ro.getSezione();
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			Query q =null;
			if (sezione==null)	
				q=manager_diogene.createNamedQuery("DocfaUiu.getDocfaUiu");
			else{
				q=manager_diogene.createNamedQuery("DocfaUiu.getDocfaUiuBySez");
				q.setParameter("sezione", ro.getSezione());
			}
				
			q.setParameter("fornitura", df.format(ro.getFornitura()));
			q.setParameter("protocollo", ro.getProtocollo());
			q.setParameter("foglio", ro.getFoglio());
			q.setParameter("particella", ro.getParticella());
			
			logger.debug("PARAMS getListaDocfaUiu - SEZIONE["+ sezione+"];FOGLIO["+ ro.getFoglio().trim()+"];[PARTICELLA["+ ro.getParticella().trim()+ "] FORNITURA["+  df.format(ro.getFornitura())+ "]; PROTOCOLLO["+ ro.getProtocollo()+"]") ;

			 lista =  q.getResultList();
			 
			 for (DocfaUiu doc: lista){
				 String indirizzo= doc.getIndirToponimo();
				 if (indirizzo != null){
					 indirizzo= StringUtils.pulisciVia(indirizzo);
					 doc.setIndirToponimo(indirizzo);
				 }
			 }
			 
			 
			logger.info("Risultati: "+ lista.size());
			
			
		}catch(Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
		return lista;
	}

	@Override
	public List<DocfaUiu> getListaDocfaUiuFabbricatoDocfa(RicercaOggettoDocfaDTO ro) {
		List<DocfaUiu> lista = new ArrayList<DocfaUiu>();
		try {
			String sezione =  ro.getSezione();
			if (sezione==null)
				sezione="";
			Date dtRif = ro.getDataRif();
			if (dtRif ==null)
				dtRif= new Date();
			Query q =manager_diogene.createNamedQuery("DocfaUiu.getDocfaUiuByFabbricato_Docfa_Data");
			q.setParameter("sezione", ro.getSezione());
			q.setParameter("fornitura", ro.getFornitura());
			q.setParameter("protocollo", ro.getProtocollo());
			q.setParameter("foglio", ro.getFoglio());
			q.setParameter("particella", ro.getParticella());
			q.setParameter("dataRif", dtRif);
			
			logger.debug("PARAMS getListaDocfaUiuFabbricatoDocfa - SEZIONE["+ sezione+"];FOGLIO["+ ro.getFoglio().trim()+"];[PARTICELLA["+ ro.getParticella().trim()+ "] FORNITURA["+  ro.getFornitura()+ "]; PROTOCOLLO["+ ro.getProtocollo()+"]") ;

			lista =  q.getResultList();
			logger.debug("getListaDocfaUiuFabbricatoDocfa - Risultati: "+ lista.size());
			
			
		}catch(Throwable t) {
			logger.error(t.getMessage(), t);
			throw new DocfaServiceException(t);
		}
		return lista;
	}
	
	@Override 
	public List<DocfaUiu> getListaDocfaUiuByUI(RicercaOggettoDocfaDTO ro){
		
		try {
			logger.debug("Estrazione Docfa Uiu: "
					+"Sezione ["+ro.getSezione()+"]; "
					+"Foglio["+ro.getFoglio()+"]; "
					+"Particella["+ro.getParticella()+"]; "
					+"Subalterno["+ro.getUnimm()+"]");
			
			DocfaSearchCriteria dsc = new DocfaSearchCriteria();
			dsc.setRicercaOggetto(ro);
			
			String sql = new DocfaQueryBuilder(dsc).createQueryGettingFPS(false);
			logger.debug("SQL ["+sql+"]");
			
			Query q = null;
			if(ro.getSezione() == null)
				q = manager_diogene.createNamedQuery("DocfaUiu.getListaDocfaUiuByFPS");
			else{
			    q = manager_diogene.createNamedQuery("DocfaUiu.getListaDocfaUiuBySezFPS");
				q.setParameter("sezione", ro.getSezione());
			}
			
			q.setParameter("foglio", ro.getFoglio());
			q.setParameter("particella", ro.getParticella());
			
			String unimm      = "0";
			if(ro.getUnimm()!=null && ro.getUnimm().trim().length()>0 )
				unimm = ro.getUnimm();
			
			q.setParameter("subalterno", unimm);
			
			List<DocfaUiu> list = q.getResultList();
			logger.info("getListaDocfaUiuByUI - Risultati: "+ list.size());
			return list;
			
		} catch (Throwable t) {
			throw new DocfaServiceException(t);
		}
		
	}
	
	@Override
	public DocfaDatiCensuari getDatiCensuariByUiuDocfa(RicercaOggettoDocfaDTO ro){
		DocfaDatiCensuari ddc = null;
		try {
			String sezione =  ro.getSezione();
			if (sezione==null)
				sezione="";
			
			logger.debug("Estrazione Docfa Dati Censuari: "
					+"Foglio["+ro.getFoglio()+"]; "
					+"Particella["+ro.getParticella()+"]; "
					+"Subalterno["+ro.getUnimm()+"]; "
					+"Fornitura["+ro.getFornitura()+"]; "
					+"Protocollo["+ro.getProtocollo()+"]");
			
			Query q =manager_diogene.createNamedQuery("DocfaDatiCensuari.getDatiCensuariByUiu_Docfa");
			
			q.setParameter("fornitura", ro.getFornitura());
			q.setParameter("protocollo", ro.getProtocollo());
			q.setParameter("foglio", ro.getFoglio());
			q.setParameter("particella", ro.getParticella());
			q.setParameter("unimm", ro.getUnimm());
			
			List<DocfaDatiCensuari> list = q.getResultList();
			logger.debug("getDatiCensuariByUiuDocfa - Result size ["+list.size()+"]");
			if(list.size()>0){
				ddc = list.get(0);
				if(list.size()> 1)
				logger.warn("getDatiCensuariByUiuDocfa - Rilevati più risultati: [" + list.size()+"]");
			}
			
		}catch(Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
		
		return ddc;
	}
	
	
	@Override
	public List<String> getListaClassiMin(String zona, String foglio, String categoria){
		List<String> classiMin = new ArrayList<String>();
		try {
			
			logger.debug("Estrazione Docfa Dati Censuari: "
					+"Foglio["+foglio+"]; "
					+"Categoria["+categoria+"]; "
					+"Zona["+zona+"]");
			
			String sql = "SELECT " +
			"classe_min " +
			"FROM " +
			"docfa_classi_min " +
			"WHERE " +
			"NVL (TO_NUMBER (zc), -1) = NVL ('" + StringUtils.checkNullString(zona) + "', -1) " +
			"AND TO_NUMBER (foglio) = '" + StringUtils.checkNullString(foglio) + "' " +
			"AND categoria = '" + StringUtils.checkNullString(categoria) + "' ";
			
			Query q =manager_diogene.createNativeQuery(sql);
			classiMin = (List<String>)q.getResultList();
			logger.debug("SQL ["+sql+"]");
			logger.debug("ClassiMin - Result size ["+classiMin.size()+"]");
			
		}catch(Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
		
		return classiMin;
		
	}
	
	@Override
	public List<DocfaDatiGenerali> getDocfaDatiGenerali(Date fornitura, String protocollo){
		
		List<DocfaDatiGenerali> list  = new ArrayList<DocfaDatiGenerali>();
		try {
			
			logger.debug("Estrazione Docfa Dati Generali: "
						+"Fornitura ["+fornitura+"]; "
						+"Protocollo["+protocollo+"]");
			
			Query q =manager_diogene.createNamedQuery("DocfaDatiGenerali.getDocfaDatiGeneraliByDocfa");
			q.setParameter("fornitura", fornitura);
			q.setParameter("protocollo", protocollo);
			
			list = q.getResultList();
			logger.debug("Dati Generali - Result size ["+list.size()+"]");
			
			if(list.size()> 0)
				logger.warn("Rilevati più risultati: [" + list.size()+"]");
			
			
		}catch(Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
		
		return list;
	}
	
	@Override
	public List<DocfaValori> getDocfaValori(String microzona, String tipolEdilizia, String stato){
		
		List<DocfaValori> list  = new ArrayList<DocfaValori>();
		try {
			if (microzona != null && tipolEdilizia!= null && stato!=null){	
				/*
				 * Recupero valore medio SE I PARAMETRI PER LA QUERY SONO OK
				 */
				logger.debug("getDocfaValori: "
						+"Microzona["+microzona+"]; "
						+"TipolEdilizia["+tipolEdilizia+"]; "
						+"Stato["+stato+"]");
			
				Query q =manager_diogene.createNamedQuery("DocfaValori.getDocfaValoriByMicrozonaStatoCat");
				q.setParameter("microzona", microzona);
				q.setParameter("tipologiaEdilizia", tipolEdilizia);
				q.setParameter("flgNuovaCostruzione", stato);
				
				list = q.getResultList();
				logger.debug("getDocfaValori - Result size ["+list.size()+"]");
					
				if(list.size()> 1)
				logger.warn("getDocfaValori - Rilevati più risultati: [" + list.size()+"]");
			}else{
				logger.warn("getDocfaValori - Parametri Non Validi = microzona: " + microzona + "; categoria catastale: " + tipolEdilizia + "; Flag Nuova Costituzione: " + stato);
			}
			
		}catch(Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
		
		return list;
	}//-------------------------------------------------------------------------
	
	public List<DocfaValori> getDocfaValoriByZonaOmi(String zonaOmi, String tipolEdilizia, String stato){
		
		List<DocfaValori> list  = new ArrayList<DocfaValori>();
		try {
			if (zonaOmi != null && tipolEdilizia!= null && stato!=null){	
				/*
				 * Recupero valore medio SE I PARAMETRI PER LA QUERY SONO OK
				 */
				logger.debug("getDocfaValoriByZonaOmi: "
						+"ZonaOmi["+zonaOmi+"]; "
						+"TipolEdilizia["+tipolEdilizia+"]; "
						+"Stato["+stato+"]");
			
				Query q = manager_diogene.createNamedQuery("DocfaValori.getDocfaValoriByZonaOmiStatoCat");
				q.setParameter("zonaOmi", zonaOmi);
				q.setParameter("tipologiaEdilizia", tipolEdilizia);
				q.setParameter("flgNuovaCostruzione", stato);

				list = q.getResultList();
				logger.debug("getDocfaValoriByZonaOmi - Result size ["+list.size()+"]");
					
				if(list.size()> 1)
				logger.warn("getDocfaValoriByZonaOmi - Rilevati più risultati: [" + list.size()+"]");
			}else{
				logger.warn("getDocfaValoriByZonaOmi - Parametri Non Validi = zona OMI: " + zonaOmi + "; categoria catastale: " + tipolEdilizia + "; Flag Nuova Costituzione: " + stato);
			}
			
		}catch(Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
		
		return list;
	}//-------------------------------------------------------------------------
	
	@Override
	public List<FoglioMicrozonaDTO> getFoglioMicrozona(String foglio, String zonaCens){
		
		List<FoglioMicrozonaDTO> lista = new ArrayList<FoglioMicrozonaDTO>();
		
		try{
			
			String sql = 
				"SELECT DISTINCT M.ZC, M.OLD_MICROZONA, M.NEW_MICROZONA  " +
				"FROM DOCFA_FOGLI_MICROZONA M " +
				"where TO_NUMBER(M.FOGLIO) = to_number('" + StringUtils.checkNullString( foglio ) + "') " +
				"and NVL(to_number(m.zc), -1) = NVL(to_number('" + StringUtils.checkNullString( zonaCens ) + "'), -1) ";
			
			logger.debug("FogliMicrozona - SQL ["+sql+"]");
			
			Query q = manager_diogene.createNativeQuery(sql);
			List<Object[]> result = q.getResultList();
			
			logger.debug("DocfaFogliMicrozona - Result size ["+result.size()+"]");
			for(Object[] objs : result){
				FoglioMicrozonaDTO dto = new FoglioMicrozonaDTO();
				dto.setZc((String)objs[0]);
				dto.setOldMicrozona((String)objs[1]);
				dto.setNewMicrozona((String)objs[2]);
				lista.add(dto);
			}
		}catch(Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
		
		return lista;

	}//-------------------------------------------------------------------------
	
	public BigDecimal getAreaIntersectZonaOmiParticella(String zonaOmi, String foglio, String particella){
		
		BigDecimal areaMax = new BigDecimal(0);
		
		/* Questa query cosi messa potrebbe generare errore ORACLE: La query
		 * secondaria monoriga restituisce piu di una riga; per cui è stata
		 * divisa in due query.
		 * 1. Recupero uno o piu shape che si intersecano (per evitare di gestire 
		 * l'oggetto shape si è optato per introdurre la ROWNUM e recuperare solo
		 * il primo shape )
		 * 2. Ne calcolo l'area di intersezione 
		 * SELECT SDO_GEOM.SDO_AREA(
					(
					SELECT SDO_GEOM.SDO_INTERSECTION(ZOMI.shape, PART.shape, 0.005)
					   FROM SHP_ZONE_OMI ZOMI, SITIPART PART 
					   WHERE ZOMI.NAME = :zonaOmi AND 
					   PART.FOGLIO = TO_NUMBER(:foglio) 
					AND PART.PARTICELLA = :particella
					AND ROWNUM = 1
					), 0.005) AS AREA_MQ FROM DUAL
		 */
		try{
				BigDecimal areaMq = new BigDecimal(0);
				if (zonaOmi != null && foglio!= null && particella!=null){
					String sql = "SELECT SDO_GEOM.SDO_AREA( ( "
							+ "SELECT SDO_GEOM.SDO_INTERSECTION(ZOMI.shape, PART.shape, 0.005) "
							+ "FROM SHP_ZONE_OMI ZOMI, SITIPART PART "
							+ "WHERE ZOMI.NAME = '" + StringUtils.checkNullString( zonaOmi ) + "' AND "
							+ "PART.FOGLIO = TO_NUMBER(" + StringUtils.checkNullString( foglio ) + ") "
							+ "AND PART.PARTICELLA = '" + StringUtils.checkNullString( particella ) + "' "
							+ "AND ROWNUM = 1 "
							+ "), 0.005) AS AREA_MQ FROM DUAL ";
					/*
					 * Recupero Superficie Area di Intersezione tra particella e Zona Omi 
					 */
					logger.debug(sql);
					logger.debug("getAreaIntersectZonaOmiParticella: "
							+"ZonaOmi["+zonaOmi+"]; "
							+"Foglio["+foglio+"]; "
							+"Particella["+particella+"]");

					Query q = manager_diogene.createNativeQuery( sql );
					areaMq = (BigDecimal)q.getSingleResult();
					logger.debug("getAreaIntersectZonaOmiParticella - Area mq ["+areaMq+"]");
					if (areaMq != null && areaMq.compareTo(areaMax) > 0)
						areaMax = areaMq;
				}else{
					logger.warn("getAreaIntersectZonaOmiParticella - Parametri Non Validi = zona OMI: " + zonaOmi + "; foglio: " + foglio + "; particella: " + particella);
				}

		}catch(Throwable t) {
			t.printStackTrace();
			logger.error(t.getMessage(),t);
			throw new DocfaServiceException(t);
		}
		
		return areaMax;
	}//-------------------------------------------------------------------------
	
	public List<ZonaOmiDTO> getZoneOmiByFP(String foglio, String particella){
		
		List<ZonaOmiDTO> lista = new ArrayList<ZonaOmiDTO>();
		
		try{
			/*
			 * select ZOMI.NAME, PART.FOGLIO, PART.PARTICELLA, PART.SUB 
from SHP_ZONE_OMI ZOMI, SITIPART PART
where sdo_relate (ZOMI.shape, PART.shape, 'mask=ANYINTERACT')='TRUE' and
PART.FOGLIO = 2 
AND PART.PARTICELLA = '00130';
			 */
			String sql = 
				"select ZOMI.NAME, PART.FOGLIO, lpad(PART.PARTICELLA, 5, '0')  " +
				"from SHP_ZONE_OMI ZOMI, SITIPART PART " +
				"where sdo_relate (ZOMI.shape, PART.shape, 'mask=ANYINTERACT')='TRUE' and " + 
				"TO_NUMBER(PART.FOGLIO) = to_number('" + StringUtils.checkNullString( foglio ) + "') " +
				"and PART.PARTICELLA = '" + StringUtils.checkNullString( particella ) + "' ";
			
			logger.debug("Zona OMI - SQL ["+sql+"]");
			
			Query q = manager_diogene.createNativeQuery(sql);
			List<Object[]> result = q.getResultList();
			
			logger.debug("Zona OMI - Result size ["+result.size()+"]");
			for(Object[] objs : result){
				ZonaOmiDTO dto = new ZonaOmiDTO();
				dto.setName((String)objs[0]);
				BigDecimal bdFoglio = (BigDecimal)objs[1];
				dto.setFoglio(bdFoglio.toString());
				String cParticella = objs[2].toString();
				dto.setParticella(cParticella.toString());
				lista.add(dto);
			}
		}catch(Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
		
		return lista;

	}//-------------------------------------------------------------------------
	
	@Override
	public Double getDocfaRapporto(){
		
		Double rapportoD= new Double(1);
		
		try{
		
			Query q = manager_diogene.createNativeQuery("SELECT valore FROM sit_tab_params where nome='DOCFA_RAPPORTO' ");
			
			List<Object> lstRapporti = q.getResultList();
			String rapporto = "1";
			if (lstRapporti != null && lstRapporti.size()>0){
				rapporto = StringUtils.checkNullString((String)lstRapporti.get(0));
			}
			
			rapportoD= new Double(rapporto);
			logger.debug("Docfa Rapporto: " + rapportoD);
		
		}catch(Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
		
		return rapportoD;
	}
	
	@Override
	public List<ControlloClassamentoConsistenzaDTO> getListaClassiCompatibili(double valComm, double consistenza, double rapporto, String zona, String categoria, String classe){
		
		List<ControlloClassamentoConsistenzaDTO> lstClaCom = new ArrayList<ControlloClassamentoConsistenzaDTO>();
		
		try{
			
			if(zona!=null && zona.trim().length()==0) zona = "-1";
			
			double tariffaMin = valComm/consistenza*0.9/105/rapporto;
			double tariffaMax = valComm/consistenza*1.1/105/rapporto;
			
			String sql = "SELECT " +
			"zona, categoria, classe, tariffa_euro " +
			"FROM " +
			"docfa_classe " +
			"WHERE " +
			"tariffa_euro >= "+tariffaMin +
			" AND tariffa_euro <= "+tariffaMax +
			" AND (    UPPER (categoria) LIKE 'A%' " +
			" AND UPPER (categoria) <> 'A10' " +
			" AND UPPER (categoria) <> 'A05' " +
			" ) " +
			" AND NVL (zona, '-1') = NVL (TO_NUMBER (" + zona + "), '-1') " +
			" AND (categoria <> '" + categoria + "' OR classe <> TO_NUMBER ('" + classe + "'))  ";
			
			logger.debug("Classamento Compatibile - SQL["+sql+"]");
			
			Query q = manager_diogene.createNativeQuery(sql);
			List<Object> listaClaCom = q.getResultList();
			if (listaClaCom != null && listaClaCom.size()>0){
				ControlloClassamentoConsistenzaDTO claCom = null;
				for (int i=0; i<listaClaCom.size(); i++){
					claCom = new ControlloClassamentoConsistenzaDTO();
					Object[] objs = (Object[])listaClaCom.get(i);
					claCom.setZona((String)objs[0]);
					claCom.setCategoria((String)objs[1]);
					claCom.setClasse((String)objs[2]);
					claCom.setTariffa( ((BigDecimal)objs[3]).doubleValue() );
					
					claCom.setRendita(((BigDecimal)objs[3]).doubleValue() * consistenza );
					
					lstClaCom.add(claCom);
				}
			}
		}catch(Exception e){
			logger.error("getListaClassiCompatibili - "+e.getMessage(),e);
			throw new DocfaServiceException(e);
		}
		
		return lstClaCom;
	}

	@Override
	public List<FoglioMicrozonaDTO> getFoglioMicrozona(String foglio) {

		List<FoglioMicrozonaDTO> lista = new ArrayList<FoglioMicrozonaDTO>();
		
		try {
			logger.debug("getFoglioMicrozona - Foglio["+foglio+"]");
			
			Query q =manager_diogene.createNamedQuery("DocfaFogliMicrozona.getDatiZonaByFoglio");
			q.setParameter("foglio", StringUtils.checkNullString( foglio ));
			
			List<DocfaFogliMicrozona> lz = q.getResultList();
			logger.debug("DocfaFoglioMicrozona - Result size ["+lz.size()+"]");
			for (DocfaFogliMicrozona dfm : lz) {
				if(dfm != null){
					FoglioMicrozonaDTO dto = new FoglioMicrozonaDTO();
					
					dto.setZc(dfm.getId().getZc());
					dto.setOldMicrozona(dfm.getOldMicrozona());
					dto.setNewMicrozona(dfm.getNewMicrozona());
					
					logger.debug("getFoglioMicrozona - "+dto.print());
					
					lista.add(dto);
				}
			}
		}catch(Throwable t) {
			logger.error("getFoglioMicrozona", t);
			throw new DocfaServiceException(t);
		}
		
		return lista;
	}
	
	@Override
	public List<String> getDocfaFogliMicrozona_ListaFogli() {

		List<String> lz = new ArrayList<String>();
		
		try {
			logger.debug("Recupero lista fogli da DocfaFogliMicrozona");
			
			Query q =manager_diogene.createNamedQuery("DocfaFogliMicrozona.getListaFogli");
			lz = q.getResultList();
			logger.debug("DocfaFoglioMicrozona - Lista Fogli Result size ["+lz.size()+"]");
			
		}catch(Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
		
		return lz;
	}
	
	
	

    @Override
    public List<OperatoreDTO> getListaOperatori(Date fornitura, String protocollo){
		
    	List<OperatoreDTO> lst = new ArrayList<OperatoreDTO>();
		
		String sql ="SELECT SUBSTR(CAMPO14,0,6) AS CF, CAMPO4 AS TIPO_OPE, CAMPO8 AS NOTE " +
				"FROM DOCFA_OPERATORI OPE " +
				"WHERE 	PROTOCOLLO = :protocollo "+
				"AND    ANNO =  :anno ";
		
		logger.debug("getListaOperatori - SQL["+sql+"]");
		
		try{

			SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
					
			String anno =  yyyy.format(fornitura);
		
			logger.debug("getListaOperatori - Protocollo["+protocollo+"],Anno["+anno+"]");
		
			Query q = manager_diogene.createNativeQuery(sql);
			q.setParameter("protocollo", protocollo);
			q.setParameter("anno", anno);
			List<Object[]> result = (List<Object[]>)q.getResultList();
			logger.debug("getListaOperatori - Result size ["+result.size()+"]");
			for(Object[] r : result){
				OperatoreDTO op = new OperatoreDTO();
				
				op.setCf((String)r[0]);
				op.setTipo((String)r[1]);
				op.setNote((String)r[2]);
			
				lst.add(op);
			}
		
		}catch(Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
		
		return lst;
		
		
	}
			
	
	@Override
	public List<BeniNonCensDTO> getListaBeniNonCensibili(Date fornitura, String protocollo){
		
		List<BeniNonCensDTO> lst = new ArrayList<BeniNonCensDTO>();
		
		String sql ="SELECT DISTINCT " +
				"      FOGLIO_01          AS FOG_01,"+
				"	   NUMERO_01          AS NUM_01,"+
				"	   SUBALTERNO_01      AS SUB_01,"+
				"	   FOGLIO_02          AS FOG_02,"+
				"	   NUMERO_02          AS NUM_02,"+
				"	   SUBALTERNO_02      AS SUB_02,"+
				"	   FOGLIO_03          AS FOG_03,"+
				"	   NUMERO_03          AS NUM_03,"+
				"	   SUBALTERNO_03      AS SUB_03"+
				"	   FROM DOCFA_BENI_NON_CENS  BNC  "+
				"	   WHERE 	BNC.PROTOCOLLO_REG = :protocollo "+
				"	   AND BNC.FORNITURA =  :fornitura " +
				"      ORDER BY FOG_01, NUM_01, SUB_01";
		
		logger.debug("getListaBeniNonCensibili - SQL["+sql+"]");
		logger.debug("getListaBeniNonCensibili - Protocollo["+protocollo+"],Fornitura["+fornitura+"]");
		
		try{
		
			Query q = manager_diogene.createNativeQuery(sql);
			q.setParameter("protocollo", protocollo);
			q.setParameter("fornitura", fornitura);
			List<Object[]> result = (List<Object[]>)q.getResultList();
			logger.debug("getListaBeniNonCensibili - Result size ["+result.size()+"]");
			for(Object[] r : result){
				BeniNonCensDTO nc = new BeniNonCensDTO();
				
				ParametriCatastaliDTO p1 = new ParametriCatastaliDTO();
				p1.setFoglio((String)r[0]);
				p1.setParticella((String)r[1]);
				p1.setSubalterno((String)r[2]);
				nc.setParams01(p1);
				
				ParametriCatastaliDTO p2 = new ParametriCatastaliDTO();
				p2.setFoglio((String)r[3]);
				p2.setParticella((String)r[4]);
				p2.setSubalterno((String)r[5]);
				nc.setParams02(p2);
				
				ParametriCatastaliDTO p3 = new ParametriCatastaliDTO();
				p3.setFoglio((String)r[6]);
				p3.setParticella((String)r[7]);
				p3.setSubalterno((String)r[8]);
				nc.setParams03(p3);
				
				lst.add(nc);
			}
		
		}catch(Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
		
		return lst;
		
	}
	
	@Override
	public List<ClassamentoDTO> getClassamenti(String zc,String categoriaEdilizia, Double tariffa) {
	
		List<ClassamentoDTO> ll = new ArrayList<ClassamentoDTO>();
	
		try{
			
			if(zc!=null && zc.trim().length()==0)
				zc = "-1";
			
			String sql = "SELECT " +
			"zona, categoria, classe, tariffa, tariffa_euro, vanisup, conteggio " +
			"FROM " +
			"docfa_classe " +
			"WHERE categoria IN ";
			
			if(categoriaEdilizia.equals("1"))
				sql += "('A01','A08') ";
			else if(categoriaEdilizia.equals("2"))
				sql += "('A02','A07') ";
			else if(categoriaEdilizia.equals("3"))
				sql += "('A03') ";
			else if(categoriaEdilizia.equals("4"))
				sql += "('A04','A05') ";
			
			if(zc!=null && !"".equals(zc))
			sql += "AND NVL (zona, '-1') = NVL (TO_NUMBER (" + zc + "), '-1') " ;
			
			logger.debug("Classamento - SQL["+sql+"]");
			
			Query q = manager_diogene.createNativeQuery(sql);
			List<Object> listaCla = q.getResultList();
			if (listaCla != null && listaCla.size()>0){
				ClassamentoDTO cl = null;
				for (int i=0; i<listaCla.size(); i++){
					cl = new ClassamentoDTO();
					Object[] objs = (Object[])listaCla.get(i);
					
					
					
					double tariffaEuroD = 0;
					double tariffaCalc = 0;
					BigDecimal tariffaEuro= (BigDecimal)objs[4];
					if (tariffaEuro!= null){
						tariffaEuroD= tariffaEuro.doubleValue();
					}
					
					if (tariffa!= null)
						tariffaCalc= tariffa.doubleValue();
					
					//verifica che la tariffa sia compresa tra il 90% e il 110% della tariffa per vano calcolata
					if (tariffaEuroD > (tariffaCalc * 0.9) && tariffaEuroD < (tariffaCalc * 1.1)) {

						cl.setCategoria((String)objs[1]);
						cl.setClasse((String)objs[2]);
						cl.setTariffa(tariffaEuroD);
						
						ll.add(cl);
					}
					
				}
			}
		}catch(Exception e){
			
		}
	
		return ll;
	}

	@Override
	public List<ClasseMaxCategoriaDTO> getClassiMaxCategoria(String foglio,String categoriaEdilizia, String mappale) {
		
		List<ClasseMaxCategoriaDTO> ll = new ArrayList<ClasseMaxCategoriaDTO>();
		
		try {
			StringBuilder sql = 
				new StringBuilder("select categoria, max(classe) from sitiuiu where data_fine_val=to_date('99991231','yyyymmdd')");
			
			sql.append(" and foglio = ");
			sql.append(foglio);
			sql.append(" and particella = '"+mappale+"' ");
			
			if (categoriaEdilizia.equals("1")) {
				sql.append(" and categoria IN ('A01','A02','A07','A08') ");
			}
			else if (categoriaEdilizia.equals("2")) {
				sql.append(" and categoria IN ('A03','A04') ");
			}
			
			sql.append(" group by categoria ");
			
			logger.debug("ClassiMaxCategoria - SQL["+sql.toString()+"]");
			
			Query q = manager_diogene.createNativeQuery(sql.toString());
			List<Object> listaCla = q.getResultList();
			if (listaCla != null && listaCla.size()>0) {
				ClasseMaxCategoriaDTO cm = null;
				for (int i=0; i<listaCla.size(); i++) {
					Object[] objs = (Object[])listaCla.get(i);
					
					cm = new ClasseMaxCategoriaDTO();
					cm.setCategoria((String)objs[0]);
					cm.setClasse((String)objs[1]);
					
					ll.add(cm);
				}
			}
			
		}catch(Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
		
		return ll;
	}

	@Override
	public List<ClassamentoDTO> getClassamentiComp(String zc, String categoriaEdilizia, 
			String tipologiaIntervento,	List<ClasseMaxCategoriaDTO> clm, boolean flgAscensore,
			Double consistenza, Double tariffa) {
		
		List<ClassamentoDTO> ll = new ArrayList<ClassamentoDTO>();
		
		try{
			
			String sql = "select * from docfa_classe where ";
			if (zc != null && !zc.equals("") ){
				sql += " zona= '"+ zc + "' and ";
			}
						
			if(categoriaEdilizia.equals("1"))
				sql += "categoria IN ('A01','A02','A07','A08') ";
			else if(categoriaEdilizia.equals("2"))
				sql += "categoria IN ('A03','A04') ";
			
			logger.debug("Classamento compatibile - SQL["+sql+"]");
			
			Query q = manager_diogene.createNativeQuery(sql);
			List<Object> listaCla = q.getResultList();
			if (listaCla != null && listaCla.size()>0){
				ClassamentoDTO cl = null;
				for (int i=0; i<listaCla.size(); i++){
					cl = new ClassamentoDTO();
					Object[] objs = (Object[])listaCla.get(i);
					
					String categoria =(String)objs[1];
					String classe =(String)objs[2];
					BigDecimal tariffaEuro= (BigDecimal)objs[4];
					
					//se nuova costruzione e abitazioni economiche, allora scarto categoria A4
					if (tipologiaIntervento!= null && tipologiaIntervento.equals("1") &&  
						categoriaEdilizia != null && categoriaEdilizia.equals("2")) {
						
						if (categoria != null && categoria.equals("A04"))
							continue;
					}
					
					//se ristrutturazione e abitazioni economiche e flgAscensore = 'S',
					//allora scarto categoria A4
					if (tipologiaIntervento!= null && tipologiaIntervento.equals("2") &&  
						categoriaEdilizia != null && categoriaEdilizia.equals("2") && 
						flgAscensore){
						
						if (categoria != null && categoria.equals("A04"))
							continue;
					}
					
					//se ristrutturazione scartare le categorie per cui  la  classe è 
					//minore di quella massima per la categoria
					if (tipologiaIntervento!= null && tipologiaIntervento.equals("2")){
						 if(clm != null)  {
							 boolean next = false;
							 for(ClasseMaxCategoriaDTO c: clm) {
								 String cls = c.getClasse();
								 String categ = c.getCategoria();
								 
								 if(categ.equals(categoria)) {  //stessa categoria
									 if(Integer.valueOf(classe).intValue() < Integer.valueOf(cls).intValue()) {
										 next = true;
										 break;
									 }
								 }
							 }
							 
							 if(next) continue;
						 }
					}
					
					
					double tariffaEuroD= 0;
					double tariffaCalc=0;
					if (tariffaEuro!= null)
						tariffaEuroD= tariffaEuro.doubleValue();
					
					if (tariffa!= null)
							tariffaCalc= tariffa.doubleValue();
					
					//se sono nel caso di nuova costruzione verifico  che la tariffa sia compresa tra il 90% 
					//e il 110% della tariffa per vano calcolata
					if (tipologiaIntervento!= null && tipologiaIntervento.equals("1")) {
						if (tariffaEuroD> (tariffaCalc * 0.9) && tariffaEuroD< (tariffaCalc * 1.1)) {
							
							cl.setCategoria(categoria);
							cl.setClasse(classe);
							cl.setTariffa(tariffaEuroD);
							
							if (tariffaEuro!= null) {
								double tariffaPerVanoD= tariffaEuro.doubleValue();
								
								if (consistenza!= null){
									double consistenzaD= consistenza.doubleValue();
									double renditaComplessivaD= tariffaPerVanoD*consistenzaD;
									Double renditaComplessiva = new Double(renditaComplessivaD);									
									cl.setRenditaComplessiva(renditaComplessiva);
								}
							}
							ll.add(cl);
						}
					}
					//se sono nel caso di ristrutturazione verifico  che la tariffa sia compresa tra il 90% e 
					//il 110% della tariffa per vano calcolata ; 
					//oppure, se la classe è = classeMax per categoria, allora la tariffa può essere 
					//anche superiore al 110%
					else if (tipologiaIntervento!= null && tipologiaIntervento.equals("2")) {
						
						boolean classiuguali = false;
						if(clm != null)  {
							 for(ClasseMaxCategoriaDTO c: clm) {
								 String cls = c.getClasse();
								 String categ = c.getCategoria();
								 
								 if(categ.equals(categoria)) {  //stessa categoria
									 if(Integer.valueOf(classe).intValue() == Integer.valueOf(cls).intValue()) {
										 classiuguali = true;
										 break;
									 }
								 }
							 }
						 }
						
						
						
						if ((tariffaEuroD > (tariffaCalc * 0.9) && tariffaEuroD < (tariffaCalc * 1.1)) || 
							(tariffaEuroD > (tariffaCalc * 1.1)  && classiuguali)) {
							
							cl.setCategoria(categoria);
							cl.setClasse(classe);
							cl.setTariffa(tariffaEuro.toString());
							
							if (tariffaEuro!= null){
								double tariffaPerVanoD= tariffaEuro.doubleValue();
								
								if (consistenza!= null){
									double consistenzaD= consistenza.doubleValue();								
									double renditaComplessivaD= tariffaPerVanoD * consistenzaD;
									Double renditaComplessiva = new Double(renditaComplessivaD);
									
									cl.setRenditaComplessiva(renditaComplessiva.toString());
								}
							}
							
							ll.add(cl);
						}
					}
				}
			}
		}catch(Exception e){
			logger.error("", e);
			throw new DocfaServiceException(e);
		}
	
		return ll;
		
		
	}//-------------------------------------------------------------------------
	
	public List<DocfaDatiCensuari> getDocfaDatiCensuari(RicercaOggettoDocfaDTO ro) {
		
		List<DocfaDatiCensuari> lstDocfaDatiCensuari = null;		
		
		String foglio = ro.getFoglio();
		String particella = ro.getParticella();
		String unimm = ro.getUnimm();
		String sezione = ro.getSezione();
		
		String protocollo = ro.getProtocollo();
		String fornitura = ro.getDataRegistrazione();
		try{
			
			logger.info("RICERCA DOCFA [" +
					"Sezione: "+sezione+", " +
					"Foglio: "+foglio+", " +
					"Particella: "+particella+", " +
					"Subalterno: "+unimm+", "
							+ "Protocollo: " +protocollo+", "
									+ "Data Registrazione: " + fornitura+"] ");
	
			String hql = (new DocfaQueryBuilder()).getDocfaDatiCensuari(ro);
			
			Query q = manager_diogene.createQuery(hql);
			if (ro.getLimit()>0)
				q.setFirstResult(0).setMaxResults(ro.getLimit());
			
			lstDocfaDatiCensuari = q.getResultList();
			if (lstDocfaDatiCensuari != null)
				logger.debug("getDocfaDatiCensuari - List Result size ["+lstDocfaDatiCensuari.size()+"]");
			else{
				lstDocfaDatiCensuari = new ArrayList<DocfaDatiCensuari>();
				logger.debug("getDocfaDatiCensuari - List Result size [0]");
			}
		
		}catch(Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
		return lstDocfaDatiCensuari;
		
	}//-------------------------------------------------------------------------
	
	@Override
	public Date[] getLastSitPlanimetrie(RicercaOggettoDocfaDTO rod) {
		try {
			
			String sqlUiu = "SELECT MAX(DP.FORNITURA), MAX(DP.DATA_REGISTRAZIONE) " +
					"FROM DOCFA_DATI_CENSUARI DC, DOCFA_PLANIMETRIE DP " +
					"WHERE NVL(TRIM(DC.SEZIONE),'-') = NVL(TRIM(:sezione),'-') " +
					"AND DC.FOGLIO = LPAD(:foglio, 4, '0') " +
					"AND DC.NUMERO = LPAD(:numero, 5, '0') " +
					"AND DC.SUBALTERNO = LPAD(:subalterno, 4, '0') " +
					"AND DC.IDENTIFICATIVO_IMMOBILE = DP.IDENTIFICATIVO_IMMO";
			
			String sqlFab = "SELECT MAX(DP.FORNITURA), MAX(DP.DATA_REGISTRAZIONE) " +
					"FROM DOCFA_DATI_CENSUARI DC, DOCFA_PLANIMETRIE DP " +
					"WHERE NVL(TRIM(DC.SEZIONE),'-') = NVL(TRIM(:sezione),'-') " +
					"AND DC.FOGLIO = LPAD(:foglio, 4, '0') " +
					"AND DC.NUMERO = LPAD(:numero, 5, '0') " +
					"AND DP.IDENTIFICATIVO_IMMO = 0 " +
					"AND DC.PROTOCOLLO_REGISTRAZIONE = DP.PROTOCOLLO " +
					"AND DC.FORNITURA = DP.FORNITURA";
			
			String sql = null;
			boolean isUiu = rod.getUnimm() != null && !rod.getUnimm().equals(" ") 
					&& !rod.getUnimm().equals("-") && !rod.getUnimm().equals("0") && !rod.getUnimm().equals("9999");
			
			if (isUiu) {
				sql = sqlUiu;
				logger.info("Estrazione ultima situazione disponibile planimetrie unità immobiliare per sezione: "
						+ rod.getSezione() + ", foglio: " + rod.getFoglio()
						+ ", particella: "+ rod.getParticella() + ", subalterno: " + rod.getUnimm());
			} else {
				sql = sqlFab;
				logger.info("Estrazione ultima situazione disponibile elaborati planimetrici per sezione: "
						+ rod.getSezione() + ", foglio: " + rod.getFoglio()
						+ ", particella: "+ rod.getParticella());
			}

			Query q = manager_diogene.createNativeQuery(sql);
			
			q.setParameter("sezione", rod.getSezione());
			q.setParameter("foglio", rod.getFoglio());
			q.setParameter("numero", rod.getParticella());
			if (isUiu) {
				q.setParameter("subalterno", rod.getUnimm());
			}
			
			List<Object[]> result = q.getResultList();
			return new Date[] {(Date)result.get(0)[0], (Date)result.get(0)[1]};
			
		} catch (Throwable t) {
			logger.error("", t);
			throw new DocfaServiceException(t);
		}
	}
		
}

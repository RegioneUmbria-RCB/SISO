package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDocumentoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIndirizzoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dao.CasoDAO;
import it.webred.cs.csa.ejb.dao.ConfigurazioneDAO;
import it.webred.cs.csa.ejb.dao.DiarioDAO;
import it.webred.cs.csa.ejb.dao.DocumentoDAO;
import it.webred.cs.csa.ejb.dao.SchedaDAO;
import it.webred.cs.csa.ejb.dao.SoggettoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ColloquioDTO;
import it.webred.cs.csa.ejb.dto.ConfrontoSsCsDTO;
import it.webred.cs.csa.ejb.dto.DiarioAnagraficaDTO;
import it.webred.cs.csa.ejb.dto.DocIndividualeDTO;
import it.webred.cs.csa.ejb.dto.InterventoDTO;
import it.webred.cs.csa.ejb.dto.JsonBaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.PaiDTO;
import it.webred.cs.csa.ejb.dto.PaiDTOExt;
import it.webred.cs.csa.ejb.dto.RelazioneDTO;
import it.webred.cs.csa.ejb.dto.SchedaBarthelDTO;
import it.webred.cs.csa.ejb.dto.fascicolo.colloquio.ListaDatiColloquioDTO;
import it.webred.cs.csa.ejb.dto.fascicolo.docIndividuali.DocIndividualeBean;
import it.webred.cs.csa.ejb.dto.fascicolo.isee.ListaDatiIseeDTO;
import it.webred.cs.csa.ejb.dto.fascicolo.scuola.ListaDatiScuolaDTO;
import it.webred.cs.csa.ejb.dto.pai.ListaDatiPaiDTO;
import it.webred.cs.csa.ejb.dto.pai.PaiSearchCriteria;
import it.webred.cs.csa.ejb.dto.pai.PaiSintesiDTO;
import it.webred.cs.csa.ejb.dto.pai.SoggettoPaiBean;
import it.webred.cs.csa.ejb.dto.relazione.RelazioneSintesiDTO;
import it.webred.cs.csa.ejb.dto.relazione.SaveRelazioneDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipoDiario;
import it.webred.cs.data.DataModelCostanti.TipoVisibilitaDocumento;
import it.webred.cs.data.base.ICsDDiarioChild;
import it.webred.cs.data.base.ICsDRelazioneChild;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsADatiSociali;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsASoggettoStatoCivile;
import it.webred.cs.data.model.CsCDiarioConchi;
import it.webred.cs.data.model.CsCDiarioDove;
import it.webred.cs.data.model.CsCTipoColloquio;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsDClob;
import it.webred.cs.data.model.CsDColloquio;
import it.webred.cs.data.model.CsDColloquioBASIC;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDDiarioAna;
import it.webred.cs.data.model.CsDDiarioBASIC;
import it.webred.cs.data.model.CsDDiarioDoc;
import it.webred.cs.data.model.CsDDiarioDocPK;
import it.webred.cs.data.model.CsDDocIndividuale;
import it.webred.cs.data.model.CsDIsee;
import it.webred.cs.data.model.CsDPai;
import it.webred.cs.data.model.CsDRelSal;
import it.webred.cs.data.model.CsDRelazione;
import it.webred.cs.data.model.CsDScuola;
import it.webred.cs.data.model.CsDTriage;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsFlgIntervento;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsLoadDocumento;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsPaiMastSogg;
import it.webred.cs.data.model.CsRelDiarioDiariorif;
import it.webred.cs.data.model.CsRelDiarioDiariorifPK;
import it.webred.cs.data.model.CsRelRelazioneProbl;
import it.webred.cs.data.model.CsRelRelazioneTipoint;
import it.webred.cs.data.model.CsTbTipoDiario;
import it.webred.cs.data.model.view.CsVistaPai;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.ValidationStateless;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.ExcludeDefaultInterceptors;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Stateless
@Interceptors(ValidationStateless.class)
public class AccessTableDiarioSessionBean extends CarSocialeBaseSessionBean implements AccessTableDiarioSessionBeanRemote {

	private static final long serialVersionUID = 1L;
	@Autowired
	private DiarioDAO diarioDao;
	
	@Autowired
	private DocumentoDAO documentoDao;
	
	@Autowired
	private CasoDAO casoDao;
	
	@Autowired
	private ConfigurazioneDAO configurazioneDao;
	
	@Autowired
	private SoggettoDAO soggettoDao;
	
	@Autowired
	private SchedaDAO schedaDao;
	
	@EJB
	public AccessTableDocumentoSessionBeanRemote docSessionBean;
	@EJB
	public AccessTableIndirizzoSessionBeanRemote indirizzoBean;
	
	@Override
	public CsDDiario createDiario(BaseDTO dto) throws Exception {
		return newDiario(dto);
	}

	@Override
	public CsDDiario updateDiario(BaseDTO dto) throws Exception {
		return diarioDao.updateDiario((CsDDiario) dto.getObj());
	}

	@Override
	public CsDDiario updateDiarioNR(BaseDTO dto) throws Exception {
		return diarioDao.updateDiarioNR((CsDDiario) dto.getObj());
	}

	public CsDDiario newDiario(BaseDTO dto) {
		CsDDiario diario = (CsDDiario) dto.getObj();

		return diarioDao.newDiario(diario);
	}

	protected void saveDiarioChild(ICsDDiarioChild dChild) {
		CsDDiario csDiario = dChild.getCsDDiario();
		csDiario = diarioDao.newDiario(csDiario);
		dChild.setDiarioId(csDiario.getId());
		diarioDao.saveDiarioChild(dChild);
	}
		
	protected void saveDiarioChild(ICsDDiarioChild dChild, ICsDRelazioneChild rChild) {
		CsDDiario csDiario = dChild.getCsDDiario();
		csDiario = diarioDao.newDiario(csDiario);
		//relazione child
		dChild.setDiarioId(csDiario.getId());
		diarioDao.saveDiarioChild(dChild);
		//triage child or Sal child
		rChild.setRelazioneId(csDiario.getId());
		diarioDao.saveDiarioChild(rChild);
	}

	public void saveDiarioChild(Object relazione, Object triage, Object sal) {
		ICsDDiarioChild dChild = (ICsDDiarioChild) relazione;
        ICsDRelazioneChild rChild;
        //mi assicuro che l'obj alla posizione 2 sia effettivamente un triage
        if(triage!=null && triage instanceof CsDTriage){
        	rChild = (ICsDRelazioneChild) triage;
        	saveDiarioChild(dChild,rChild);
        }else if(sal != null && sal instanceof CsDRelSal){
        	rChild = (ICsDRelazioneChild) sal;
        	saveDiarioChild(dChild,rChild);
        }
        else {
        	saveDiarioChild(dChild);
        }
	}

	@Override
	@Interceptors(AccessoFascicoloInterceptor.class)
	public List<ListaDatiColloquioDTO> findColloquiByCaso(BaseDTO dto) {
		List<ListaDatiColloquioDTO> lista = new ArrayList<ListaDatiColloquioDTO>();
		List<CsDColloquioBASIC> colloquios = new ArrayList<CsDColloquioBASIC>();
		Long casoId = (Long) dto.getObj();
		if (casoId!=null){
			colloquios = diarioDao.getColloquiByCaso(casoId);
			for(CsDColloquioBASIC c : colloquios){
				ListaDatiColloquioDTO el = new ListaDatiColloquioDTO(c.getCsDDiario().getVisSecondoLivello(),c.getCsDDiario().getCsOOperatoreSettore().getCsOSettore().getId());
				
				Date dtMod = c.getCsDDiario().getDtMod();
				Date dtIns = c.getCsDDiario().getDtIns();
				el.setDataUltimaModifica( dtMod != null ? dtMod : dtIns);
				
				el.setUserIns(c.getCsDDiario().getUserIns());
				el.setUserMod(c.getCsDDiario().getUsrMod());
				
				el.setTestoDiario(c.getTestoDiario());
				el.setDiarioId(c.getDiarioId());
				el.setDtAmministrativa(c.getCsDDiario().getDtAmministrativa());
				el.setCognome(c.getCsDDiario().getCsACaso().getCsASoggetto().getCsAAnagrafica().getCognome());
				el.setNome(c.getCsDDiario().getCsACaso().getCsASoggetto().getCsAAnagrafica().getNome());
				el.setDescrizioneTipoColloquio(c.getCsCTipoColloquio()!=null ? c.getCsCTipoColloquio().getDescrizione() : null);
				el.setDescrizioneDiarioDove(c.getCsCDiarioDove()!=null ? c.getCsCDiarioDove().getDescrizione() : null);
				el.setDiarioConChiAltro(c.getDiarioConChiAltro());
				el.setDescrizioneDiarioConChi(c.getCsCDiarioConchi()!=null ? c.getCsCDiarioConchi().getDescrizione() : null);
				el.setRiservato(c.getRiservato());
				
				lista.add(el);
			}
		}
		return lista;
	}

	private String ripulisciTesto(String testoOrig){
		String testoFinale = testoOrig;
		while(testoFinale.contains("<xml>")){
			testoFinale = testoFinale.replaceAll("(?s)<!--.*?-->", "");
			int indexIni = testoFinale.indexOf("<xml>");
			int indexFin = testoFinale.indexOf("</xml>");
			if(indexFin>indexIni)
				testoFinale = testoFinale.substring(0, indexIni) + testoFinale.substring(indexFin+6);
		}
		return testoFinale;
	}

	@Override
	public void salvaColloquio(BaseDTO dto) throws Exception{
		Long idCaso = (Long) dto.getObj();
		ColloquioDTO cdto = (ColloquioDTO) dto.getObj2();
		CsOOperatoreSettore opSettore = (CsOOperatoreSettore)dto.getObj3();
		
		CsDColloquio colloquio = cdto.getColloquio();
		
		CsCTipoColloquio tipoColloquio = configurazioneDao.getTipoColloquio(cdto.getDiarioTipoSelected());
		CsCDiarioDove diarioDove = configurazioneDao.getDiarioDove(cdto.getDiarioDoveSelected()); 
		CsCDiarioConchi diarioConchi = configurazioneDao.getDiarioConChi(cdto.getDiarioConChiSelected()); 

		colloquio.setCsCDiarioDove(diarioDove);
		colloquio.setCsCDiarioConchi(diarioConchi);
		colloquio.setCsCTipoColloquio(tipoColloquio);

		if( cdto.isAbilitato4riservato() ) {
			//selectedColloquio.getColloquio().setTestoDiario(selectedColloquio.getCampoTesto());
			colloquio.setRiservato(cdto.isRiservato()?"1":"0");
		}
		
		//Ripulisco il contenuto testuale da tag relativi a formattazione word
		String testoDiario = colloquio.getTestoDiario();
		colloquio.setTestoDiario(ripulisciTesto(testoDiario));

		// New Colloquio
		if (colloquio.getDiarioId() == null) {
			CsACaso caso = casoDao.findCasoById(idCaso);
			CsOOperatoreBASIC responsabile = casoDao.findResponsabileBASIC(idCaso);
			
			colloquio.getCsDDiario().setCsACaso(caso);
			colloquio.getCsDDiario().setResponsabileCaso(responsabile!=null  ? responsabile.getId() : null);

			CsTbTipoDiario tipo = new CsTbTipoDiario(); 
		    tipo.setId(new Long(DataModelCostanti.TipoDiario.COLLOQUIO_ID)); 
		    
			colloquio.getCsDDiario().setCsTbTipoDiario(tipo);
			colloquio.getCsDDiario().setCsOOperatoreSettore(opSettore);
			colloquio.getCsDDiario().setDtIns(new Date());
			colloquio.getCsDDiario().setUserIns(dto.getUserId());
			colloquio.getCsDDiario().setVisSecondoLivello(cdto.getVisSecondoLivello());
			
			this.saveDiarioChild(colloquio, null, null);
			
			long diarioId = colloquio.getCsDDiario().getId();
			colloquio.setDiarioId(diarioId);
			
			//Invio alert nuovo inserimento
			BaseDTO bdto = new BaseDTO();
			this.copiaCsTBaseObject(dto, bdto);
			bdto.setObj(caso.getCsASoggetto());
			bdto.setObj2(opSettore);
			bdto.setObj3(DataModelCostanti.TipiAlertCod.COLLOQUIO);
			bdto.setObj4("un nuovo diario");
			
			alertSessionBean.addAlertNuovoInserimentoToResponsabileCaso(bdto);
			
		} else {
			colloquio.getCsDDiario().setUsrMod(dto.getUserId());
			colloquio.getCsDDiario().setDtMod(new Date());
			colloquio.getCsDDiario().setVisSecondoLivello(cdto.getVisSecondoLivello());
			
			dto.setObj(colloquio);
			dto.setObj2(null);
			dto.setObj3(null);

			this.updateColloquio(dto);
		}
		
	}
	
	@Override
	public void updateColloquio(BaseDTO dto) throws Exception {
		CsDColloquio colloquio = (CsDColloquio) dto.getObj();

		diarioDao.updateColloquio(colloquio);
	}

	@Override
	@Interceptors(AccessoFascicoloInterceptor.class)
	public List<RelazioneDTO> findRelazioniByCaso(BaseDTO i) {
		Long idCaso = (Long) i.getObj();
		List<RelazioneDTO> lst = new ArrayList<RelazioneDTO>();
		if(idCaso!=null){
			logger.debug("findRelazioniByCaso CASO_ID["+idCaso+"]");
			List<CsDRelazione> lstr = diarioDao.findRelazioniByCaso(idCaso);
			logger.debug("findRelazioniByCaso RES["+lstr.size()+"]");
			for (CsDRelazione r : lstr) {
				List <CsRelRelazioneProbl> lstProbematiche = r.getCsRelRelazioneProbl();
				List<CsRelRelazioneTipoint> listaInterventi  = r.getCsRelRelazioneTipoint();
				CsLoadDocumento doc = documentoDao.findLoadDocumentoByDiarioId(r.getDiarioId());
				List<PaiDTOExt> lstPai = diarioDao.loadPaiEntities(r.getCsDDiario());
				RelazioneDTO dto = new RelazioneDTO(r, doc, lstPai, lstProbematiche,listaInterventi);
				lst.add(dto);
			}
			logger.debug("findRelazioniByCaso END");
			
		}
		return lst;
	}
	
	@Override
	public List<RelazioneSintesiDTO> findRelazioniSintesiByCaso(BaseDTO i) {
		Long idCaso = (Long) i.getObj();
		if(idCaso!=null){
			return diarioDao.findRelazioniSintesiByCaso(idCaso);
		}
		return new ArrayList<RelazioneSintesiDTO>();
	}


    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
	public List<CsDRelazione> findRelazioniInScadenza(CeTBaseObject i){	
		return diarioDao.findRelazioniInScadenza();
    }
    
    @AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID 
    public  List<CsDValutazione> getSchedeValutazionebyTipo(BaseDTO dto){
    	List<CsDValutazione> schedeMinori = new ArrayList<CsDValutazione>();

    	schedeMinori = diarioDao.findAllSchedeValutazioneTipo((Integer) dto.getObj2());

		return schedeMinori;
		}
	
	@Override
	public List<KeyValueDTO> findRelazioniByCasoTipoIntervento(InterventoDTO i) {
		Long idCaso = i.getCasoId();
		Long idTipoIntervento = i.getIdTipoIntervento();

		List<CsDRelazione> lstTmp = diarioDao.findRelazioniByCaso(idCaso);
		List<CsDRelazione> lstRelTi = new ArrayList<CsDRelazione>();
		List<KeyValueDTO> lst = new ArrayList<KeyValueDTO>();
		for (CsDRelazione r : lstTmp) {
			Set<CsCTipoIntervento> lstTi = r.getCsCTipoInterventos();
			if (lstTi == null || r.getCsCTipoInterventos().size() == 0)
				lstRelTi.add(r);
			else {
				//Verifico che tra i tipi associati ci sia quello corrente
				boolean trovato = false;
				Iterator<CsCTipoIntervento> iter = lstTi.iterator();
				while (iter.hasNext() && !trovato) {
					CsCTipoIntervento ti = iter.next();
					if (ti.getId() == idTipoIntervento.longValue()) {
						lstRelTi.add(r);
						trovato = true;
					}
				}
			}

		}

		if (lstRelTi != null) {
			for (CsDRelazione s : lstRelTi) {
				Date dataRelazione = s.getCsDDiario().getDtAmministrativa();
				String descrRelazione = "";
				List<CsDDiarioDoc> lstDocumenti = diarioDao.findDiarioDocById(s.getCsDDiario().getId());
				if (lstDocumenti != null && !lstDocumenti.isEmpty())
					descrRelazione = lstDocumenti.iterator().next().getCsLoadDocumento().getNome();
				else
					descrRelazione = s.getProposta() != null ? (s
							.getProposta().length() > 20 ? s
							.getProposta().substring(0, 20) + "..." : s
							.getProposta()) : "Nessuna proposta";

				String descrizione = (dataRelazione != null ? ddMMyyyy.format(s.getCsDDiario().getDtAmministrativa()) : "") + " - " + descrRelazione;
				lst.add(new KeyValueDTO(s.getDiarioId(), descrizione));
			}
		}
		return lst;
	}

	@Override
	public CsDRelazione findRelazioneLazyById(BaseDTO dto) {
		return diarioDao.findRelazioneById((Long) dto.getObj());
	}

	@Override
	public RelazioneDTO findRelazioneFullById(BaseDTO dto) {
		Long idRelazione = (Long) dto.getObj();
		Long idCaso = (Long) dto.getObj2();
		CsDRelazione rel = diarioDao.findRelazioneById(idRelazione);
		diarioDao.loadPadreFiglioEntities(rel.getCsDDiario());
		diarioDao.loadDocEntities(rel.getCsDDiario());

		CsLoadDocumento doc = findDocRelazione(rel);
		RelazioneDTO relDTO;
		List<PaiDTOExt> listaPai = diarioDao.loadPaiEntities(rel.getCsDDiario());
		if(rel.getCsDTriage() != null){
			relDTO = new RelazioneDTO(rel, doc, listaPai ,rel.getCsDTriage());
		} else	if(rel.getCsDRelSal() != null){
			relDTO = new RelazioneDTO(rel, doc, listaPai,rel.getCsDRelSal());
		}else{
			relDTO = new RelazioneDTO(rel, doc, listaPai);
		}
		
		boolean relazioneCollegata = isRelazioneUlterioriSoggetti(idCaso, idRelazione);
		relDTO.setCasoSoggettoInteressato(relazioneCollegata);
		
		return relDTO;
	}
	
	private Boolean isRelazioneUlterioriSoggetti(Long idCaso, Long idRelazioneSelezionata) {
		Boolean relazioneCollegata = true;
		List<CsDRelazione> relazioniCaso = diarioDao.findRelazioniByCaso(idCaso);
		for(CsDRelazione d : relazioniCaso){
			if(d.getCsDDiario().getId() == idRelazioneSelezionata){
				relazioneCollegata = false;
			}
		}
		return relazioneCollegata;
	}

	public RelazioneSintesiDTO findRelazioneSintesiById(BaseDTO dto){
		CsDRelazione rel = diarioDao.findRelazioneById((Long) dto.getObj());
		List<Long> listaPai = diarioDao.findPadriCollegatiByFiglio(TipoDiario.PAI_ID, TipoDiario.RELAZIONE_ID, rel.getDiarioId());
		RelazioneSintesiDTO out = this.fillRelazioneSintesi(rel, listaPai);
		return out;
	}

	
	@Override
	public void updateRelazione(SaveRelazioneDTO dto) throws Exception {
		CsDRelazione relazione = (CsDRelazione) dto.getRelazione();
		
		relazione.getCsDDiario().setUsrMod(dto.getUserId());
		relazione.getCsDDiario().setDtMod(new Date());
		
		//mi assicuro che l'obj alla posizione 2 sia effettivamente un triage
		if(dto.getTriage()!=null){
			diarioDao.updateTriage(relazione,dto.getTriage());
		}
		else if(dto.getSal() != null){
			diarioDao.updateSal(relazione,dto.getSal());
		}
		
		else{
			diarioDao.updateRelazione(relazione);
		}
		
	}

	protected CsLoadDocumento findDocRelazione(CsDRelazione relazione) {
		CsLoadDocumento csLoadDoc = null;
		List<CsDDiarioDoc> lst = new ArrayList<CsDDiarioDoc>();
		if (relazione.getCsDDiario().getCsDDiarioDocs() != null)
			lst.addAll(relazione.getCsDDiario().getCsDDiarioDocs());
		if (lst != null && lst.size() > 0)
			csLoadDoc = lst.get(0).getCsLoadDocumento();

		return csLoadDoc;
	}

	@Override
	public RelazioneDTO saveRelazione(SaveRelazioneDTO dto) throws Exception {
		CsDRelazione jpa = (CsDRelazione) dto.getRelazione();
		
		if (dto.getCasoId() != null) {
			CsACaso csa = casoDao.findCasoById(dto.getCasoId());
			CsOOperatoreBASIC responsabile = casoDao.findResponsabileBASIC(dto.getCasoId());
			
			jpa.getCsDDiario().setCsACaso(csa);
			jpa.getCsDDiario().setResponsabileCaso(responsabile!=null  ? responsabile.getId() : null);
		}
		
		if(dto.getOpSettore()!=null)
			jpa.getCsDDiario().setCsOOperatoreSettore(dto.getOpSettore());

		CsTbTipoDiario cstd = new CsTbTipoDiario();
		cstd.setId(new Long(DataModelCostanti.TipoDiario.RELAZIONE_ID));
		
		jpa.getCsDDiario().setCsTbTipoDiario(cstd);
		jpa.getCsDDiario().setDtIns(new Date());
		jpa.getCsDDiario().setUserIns(dto.getUserId());
		
//		List<CsCTipoIntervento> listaTipoInt = new ArrayList<CsCTipoIntervento>();
//		if(relazione.getCsCTipoInterventos()!=null) listaTipoInt.addAll(relazione.getCsCTipoInterventos());
		jpa.setCsCTipoInterventos(null);
		
		List<CsRelRelazioneTipoint> listaInterventi = new ArrayList<CsRelRelazioneTipoint>();
		if(jpa.getCsRelRelazioneTipoint()!=null) listaInterventi.addAll(jpa.getCsRelRelazioneTipoint());
		jpa.setCsRelRelazioneTipoint(null);
		
		List<CsRelRelazioneProbl> listaProblematiche = new ArrayList<CsRelRelazioneProbl>();
		if(jpa.getCsRelRelazioneProbl()!=null) listaProblematiche.addAll(jpa.getCsRelRelazioneProbl());
		jpa.setCsRelRelazioneProbl(null);
		
		saveDiarioChild(dto.getRelazione(), dto.getTriage(), dto.getSal());
		
		if(listaInterventi !=null)
		{
			for (CsRelRelazioneTipoint csRelRelazioneTipoint : listaInterventi) {
				csRelRelazioneTipoint.getId().setRelazioneDiarioId(jpa.getDiarioId());
				csRelRelazioneTipoint.setCsDRelazione(null);
				diarioDao.saveCsRelRelazioneTipoint(csRelRelazioneTipoint);				
			}
		}		
		
		if(listaProblematiche != null)
		{
			for (CsRelRelazioneProbl csRelRelazioneProbl : listaProblematiche) {
				csRelRelazioneProbl.getId().setRelazioneDiarioId(jpa.getDiarioId());
				csRelRelazioneProbl.setCsDRelazione(null);
				diarioDao.saveCsRelRelazioneProbl(csRelRelazioneProbl);				
			}
		}		
		
		CsLoadDocumento csLoadDoc = findDocRelazione(jpa);
		if(csLoadDoc!=null) {
			
		}
		RelazioneDTO res = new RelazioneDTO(jpa, csLoadDoc, diarioDao.loadPaiEntities(jpa.getCsDDiario()));
		return res;
	}

	@Override
	public CsDPai saveSchedaPai(PaiDTO dto) throws Exception {

		CsDPai pai = (CsDPai) dto.getPai();

		BaseDTO bdto = new BaseDTO();
		copiaCsTBaseObject(dto, bdto);

		if (pai.getDiarioId() == null || pai.getCsDDiario().getId() == 0) {

			CsACaso caso = casoDao.findCasoById(dto.getCasoId());

			Date dtIns = new Date();
			pai.getCsDDiario().setCsACaso(caso);
			pai.getCsDDiario().setUserIns(dto.getUserId());
			pai.getCsDDiario().setDtIns(dtIns);
			pai.getCsDDiario().setUsrMod(null);
			pai.getCsDDiario().setDtMod(null);
			pai.getCsDDiario().setResponsabileCaso(dto.getResponsabileId());
			pai.getCsDDiario().setDtAmministrativa(dtIns);
			pai.getCsDDiario().setCsOOperatoreSettore(dto.getOpSettore());

			CsTbTipoDiario tipo = new CsTbTipoDiario();
			tipo.setId(new Long(DataModelCostanti.TipoDiario.PAI_ID));
			pai.getCsDDiario().setCsTbTipoDiario(tipo);

			//Recupero il diario associato alla relazione da inserire
			if (dto.getIdRelazione() != null) {
				bdto.setObj(dto.getIdRelazione());
				CsDDiario relazione = findDiarioById(bdto);
				pai.getCsDDiario().addCsDDiariFiglio(relazione);
			}

			bdto.setObj(pai.getCsDDiario());
			CsDDiario diario = createDiario(bdto);
			pai.setDiarioId(diario.getId());
			pai.setCsDDiario(diario);

			diarioDao.saveSchedaPai(pai);
			
			BaseDTO adto = new BaseDTO();
			copiaCsTBaseObject(dto, adto);
			adto.setObj(caso.getCsASoggetto());
			adto.setObj2(dto.getOpSettore());
			adto.setObj3(DataModelCostanti.TipiAlertCod.PAI);
			adto.setObj4("un nuovo PAI");
			
			alertSessionBean.addAlertNuovoInserimentoToResponsabileCaso(adto);

		} else {

			pai.getCsDDiario().setUsrMod(dto.getUserId());
			pai.getCsDDiario().setDtMod(new Date());
			diarioDao.updateSchedaPai(pai);

		}

		return pai;
	}

	@Override
	public CsDDiario findDiarioById(BaseDTO dto) throws Exception {
		return diarioDao.findDiarioById((Long) dto.getObj());
	}

	@Override
	public CsDDiarioBASIC findDiarioBASICById(BaseDTO dto) throws Exception {
		return diarioDao.findDiarioBASICById((Long) dto.getObj());
	}

	@Override
	public void deleteDiario(BaseDTO b) {
		diarioDao.deleteDiario((Long) b.getObj());
	}

	@Override
	public List<CsDDiarioDoc> findDiarioDocById(BaseDTO b) {
		return diarioDao.findDiarioDocById((Long) b.getObj());
	}

	@Override
	public void saveDiarioDoc(BaseDTO b) {
		diarioDao.saveDiarioDoc((CsDDiarioDoc) b.getObj());
	}

	@Override
	@Interceptors(AccessoFascicoloInterceptor.class)
	public List<CsDValutazione> getSchedeValutazioneSoggetto(BaseDTO dto) {
		List<CsDValutazione> schedeMultidimAnzs = new ArrayList<CsDValutazione>();

		if (dto.getObj() != null)
			schedeMultidimAnzs = diarioDao.getSchedeValutazioneSoggetto((Long) dto.getObj(), (Integer) dto.getObj2());
		else
			schedeMultidimAnzs = diarioDao.findAllSchedeValutazioneTipo((Integer) dto.getObj2());

		return schedeMultidimAnzs;
	}

	@Override
	public CsDClob createClob(BaseDTO clobDTO) {
		CsDClob clob = (CsDClob) clobDTO.getObj();

		return diarioDao.saveClob(clob);
	}

	@Override
	public void updateClob(BaseDTO clobDTO) {
		CsDClob clob = (CsDClob) clobDTO.getObj();

		diarioDao.updateClob(clob);
	}

	@Override
	public void updateSchedaMultidimAnz(BaseDTO schedaMultidimAnzDto) {
		CsDValutazione schedaMultidimAnz = (CsDValutazione) schedaMultidimAnzDto.getObj();

		diarioDao.updateValutazione(schedaMultidimAnz);
	}

	@Override
	public void deleteSchedaPai(PaiDTO dto) {

		Long idDiario = dto.getPai().getCsDDiario().getId();

		diarioDao.deleteSchedaPai(dto.getPai());
		diarioDao.deleteRelDiarioDiarioRif(idDiario);
		diarioDao.deleteDiario(idDiario);

	}
	
	//SISO-812
	@Override
	@Interceptors(AccessoFascicoloInterceptor.class)
	public List<DocIndividualeBean> findDocIndividualiByCaso(BaseDTO dto) {
		List<DocIndividualeBean> lstOut = new ArrayList<DocIndividualeBean>();
		Long casoId = (Long) dto.getObj();
		Boolean permessoDownload = (Boolean) dto.getObj2(); // DataModelCostanti.PermessiFascicolo.TAB_DOC_INDIVIDUALI_DOWN
		Long opSettoreId = (Long) dto.getObj3();
		Boolean visibilita = (Boolean) dto.getObj4();
		
		List<Long> downBypass = configurazioneDao.findTipoDocAuthDownload(opSettoreId);
		
		List<CsDDocIndividuale> lista = diarioDao.findDocIndividualiByCaso(casoId, visibilita);
		for (CsDDocIndividuale docInd : lista) {
			CsLoadDocumento loadDocumento = documentoDao.findLoadDocumentoByDiarioId(docInd.getDiarioId());
			if(loadDocumento!=null){
				DocIndividualeBean d = new DocIndividualeBean(docInd, loadDocumento, permessoDownload, dto.getUserId(), downBypass);
				logger.debug("permessoDownload["+permessoDownload+"], isAutore["+docInd.getCsDDiario().isAutore(dto.getUserId())+"] --> disableDownload["+d.isDisableDownload()+"]");
				boolean docPrivato = docInd.getPrivato()!=null && docInd.getPrivato().booleanValue();
				boolean docPrivatoAutorizzato = TipoVisibilitaDocumento.PRIVATO == visibilita.booleanValue() && docPrivato && dto.getUserId().equals(docInd.getCsDDiario().getUserIns());
				
				if(TipoVisibilitaDocumento.PUBBLICO == visibilita || docPrivatoAutorizzato)
					lstOut.add(d);
			}
		}

		return lstOut;
	}

	@Override
	public void updateDocIndividuale(BaseDTO dto) throws Exception {
		CsDDocIndividuale docIndividuale = (CsDDocIndividuale) dto.getObj();
		diarioDao.updateDocIndividuale(docIndividuale);
	}

	@Override
	public CsDDiario saveDocIndividuale(BaseDTO dto) throws Exception {
		CsDDocIndividuale docIndividuale = (CsDDocIndividuale) dto.getObj();
		saveDiarioChild(docIndividuale, null, null);
		return docIndividuale.getCsDDiario();
	}

	@Override
	public void deleteDocIndividuale(BaseDTO dto) throws Exception {

		CsDDocIndividuale docIndIn = (CsDDocIndividuale) dto.getObj();

		CsDDocIndividuale docInd = diarioDao.findDocIndividuale(docIndIn.getDiarioId());

		CsLoadDocumento loadDoc = docInd.getCsDDiario().getCsDDiarioDocs().iterator().next().getCsLoadDocumento();

		dto.setObj(loadDoc.getId());

		//INIZIO SISO-438 
		if (docInd.getCsDDiario().getCsDDiariPadre()!=null && !docInd.getCsDDiario().getCsDDiariPadre().isEmpty()) {
			diarioDao.deleteDiarioRif(
					docInd.getCsDDiario().getCsDDiariPadre().get(0).getId(), 
					docInd.getCsDDiario().getId() );
		}
		//INIZIO SISO-438
		
		docSessionBean.deleteLoadDocumento(dto);
		diarioDao.deleteDocIndividualeById(docInd.getDiarioId());
		diarioDao.deleteDiario(docInd.getDiarioId());
	}
	
	@Override
	public CsDValutazione saveSchedaJson(JsonBaseDTO schedaJson) throws Exception {
		CsDValutazione valutazione = null;
		String username = schedaJson.getUserId();
		CsOOperatoreBASIC responsabile = casoDao.findResponsabileBASIC(schedaJson.getCasoId());

		if (schedaJson.getDiarioId() == null) {
			valutazione = new CsDValutazione();

			CsACaso caso = casoDao.findCasoById(schedaJson.getCasoId());

			CsTbTipoDiario tipoDiario = configurazioneDao.findTipoDiarioById(schedaJson.getTipoDiarioId());

			if (tipoDiario == null)
				logger.error("Nessun CS_TB_TIPO_DIARIO definito per ID:" + schedaJson.getTipoDiarioId());

			CsDClob jsonClob = new CsDClob();
			jsonClob.setDatiClob(schedaJson.getJsonString());
			jsonClob = diarioDao.saveClob(jsonClob);

			CsDDiario diario = new CsDDiario();
			diario.setCsACaso(caso);
			diario.setSchedaId(schedaJson.getSchedaUdcId());
			diario.setCsTbTipoDiario(tipoDiario);
			diario.setCsOOperatoreSettore(schedaJson.getOpSettore());
			diario.setResponsabileCaso(responsabile != null ? responsabile.getId() : null);
			diario.setCsDClob(jsonClob);
			diario.setDtIns(new Date());
			diario.setUserIns(username);
			diario.setDtAmministrativa(schedaJson.getDtAmministrativa());
			diario.setDtAttivazioneDa(schedaJson.getDtAttivazioneDa());
			diario.setDtAttivazioneA(schedaJson.getDtAttivazioneA());
			diario.setDtSospensioneDa(schedaJson.getDtSospensioneDa());
			diario.setDtSospensioneA(schedaJson.getDtSospensioneA());
			diario.setDtChiusuraDa(schedaJson.getDtChiusuraDa());
			diario.setDtChiusuraA(schedaJson.getDtChiusuraA());
            diario.setVisSecondoLivello(schedaJson.getVisSecLivello()); //SISO-812
			
			valutazione.setCsDDiario(diario);
			valutazione.setDiarioId(diario.getId());

			valutazione.setVersioneScheda(schedaJson.getVersione());
			valutazione.setDescrizioneScheda(schedaJson.getDescrizione());

			saveDiarioChild(valutazione);

			if (schedaJson.getDiarioPadreId() != null) {
				CsDDiario diarioPadre = diarioDao.findDiarioById(schedaJson.getDiarioPadreId());
				if (diarioPadre != null)
					diarioDao.saveDiarioRif(diarioPadre.getId(), valutazione.getCsDDiario().getId());
			}
			
			//Aggiungo l'alert solo per i diari relativi al fascicolo
			if(DataModelCostanti.TipoDiario.VALUTAZIONE_MDS_ID==schedaJson.getTipoDiarioId() ||
			   DataModelCostanti.TipoDiario.VALUTAZIONE_SINBA==schedaJson.getTipoDiarioId() ||
			   DataModelCostanti.TipoDiario.PROVVEDIMENTI_TRIBUNALE==schedaJson.getTipoDiarioId()){
				BaseDTO dto = new BaseDTO();
				this.copiaCsTBaseObject(schedaJson, dto);
				dto.setObj(caso.getCsASoggetto());
				dto.setObj2(schedaJson.getOpSettore());
				
				if(DataModelCostanti.TipoDiario.VALUTAZIONE_MDS_ID==schedaJson.getTipoDiarioId()){
					dto.setObj3(DataModelCostanti.TipiAlertCod.MULTIDIM);
					dto.setObj4("una nuova valutazione multidimensionale");
				}else if(DataModelCostanti.TipoDiario.VALUTAZIONE_SINBA==schedaJson.getTipoDiarioId()){
					dto.setObj3(DataModelCostanti.TipiAlertCod.SINBA);
					dto.setObj4("una nuova valutazione S.In.Ba.");
			    }else if(DataModelCostanti.TipoDiario.PROVVEDIMENTI_TRIBUNALE==schedaJson.getTipoDiarioId()){
			    	dto.setObj3(DataModelCostanti.TipiAlertCod.PROTRIB);
					dto.setObj4("uno nuovo provvedimento del tribunale");
			    }
				
			
				alertSessionBean.addAlertNuovoInserimentoToResponsabileCaso(dto);
				
					
		  }
			
		} else {
			valutazione = diarioDao.findValutazioneById(schedaJson.getDiarioId(), true);

			valutazione.getCsDDiario().setUsrMod(username);
			valutazione.getCsDDiario().setDtMod(new Date());
			valutazione.getCsDDiario().setResponsabileCaso(responsabile != null ? responsabile.getId() : null);
			valutazione.getCsDDiario().setDtAmministrativa(schedaJson.getDtAmministrativa());
			valutazione.getCsDDiario().setDtAttivazioneDa(schedaJson.getDtAttivazioneDa());
			valutazione.getCsDDiario().setDtAttivazioneA(schedaJson.getDtAttivazioneA());
			valutazione.getCsDDiario().setDtSospensioneDa(schedaJson.getDtSospensioneDa());
			valutazione.getCsDDiario().setDtSospensioneA(schedaJson.getDtSospensioneA());
			valutazione.getCsDDiario().setDtChiusuraDa(schedaJson.getDtChiusuraDa());
			valutazione.getCsDDiario().setDtChiusuraA(schedaJson.getDtChiusuraA());
			valutazione.getCsDDiario().setVisSecondoLivello(schedaJson.getVisSecLivello()); //SISO-812
			valutazione.setVersioneScheda(schedaJson.getVersione());
			valutazione.setDescrizioneScheda(schedaJson.getDescrizione());

			//Update Clob with json
			CsDClob clob = valutazione.getCsDDiario().getCsDClob();
			clob.setDatiClob(schedaJson.getJsonString());
			diarioDao.updateClob(clob);

			diarioDao.updateValutazione(valutazione);
		}

		return valutazione;
	}

	@Override
	public void saveSchedaBarthel(SchedaBarthelDTO schedaBarthelDTO) throws Exception {

		String username = schedaBarthelDTO.getUserId();

		if (schedaBarthelDTO.getDiarioId() == null) {
			CsDValutazione valutazioneMultidim = diarioDao.findValutazioneById(schedaBarthelDTO.getDiarioPadreId(), true);
			CsDValutazione valutazioneSchedaBarthel = new CsDValutazione();

			CsACaso caso = casoDao.findCasoById(valutazioneMultidim.getCsDDiario().getCsACaso().getId());

			CsTbTipoDiario tipoDiario = configurazioneDao.findTipoDiarioById(DataModelCostanti.TipoDiario.BARTHEL_ID);

			CsDClob jsonClob = new CsDClob();
			jsonClob.setDatiClob(schedaBarthelDTO.getJsonString());
			jsonClob = diarioDao.saveClob(jsonClob);

			CsDDiario diario = new CsDDiario();
			diario.setCsACaso(caso);
			diario.setCsTbTipoDiario(tipoDiario);
			diario.setCsOOperatoreSettore(schedaBarthelDTO.getOpSettore());
			diario.setCsDClob(jsonClob);
			diario.setDtIns(new Date());
			diario.setUserIns(username);
			diario.setDtAmministrativa(schedaBarthelDTO.getDtAmministrativa());

			valutazioneSchedaBarthel.setCsDDiario(diario);
			valutazioneSchedaBarthel.setDiarioId(diario.getId());

			valutazioneSchedaBarthel.setVersioneScheda(schedaBarthelDTO.getVersione());
			valutazioneSchedaBarthel.setDescrizioneScheda(schedaBarthelDTO.getDescrizione());

			saveDiarioChild(valutazioneSchedaBarthel);

			diarioDao.saveDiarioRif(valutazioneMultidim.getCsDDiario().getId(), valutazioneSchedaBarthel.getCsDDiario().getId());
		} else {
			CsDValutazione valutazioneSchedaBarthel = diarioDao.findValutazioneById(schedaBarthelDTO.getDiarioId(), true);

			valutazioneSchedaBarthel.getCsDDiario().setUsrMod(username);
			valutazioneSchedaBarthel.getCsDDiario().setDtMod(new Date());
			valutazioneSchedaBarthel.getCsDDiario().setDtAmministrativa(schedaBarthelDTO.getDtAmministrativa());

			valutazioneSchedaBarthel.setVersioneScheda(schedaBarthelDTO.getVersione());
			valutazioneSchedaBarthel.setDescrizioneScheda(schedaBarthelDTO.getDescrizione());

			//Update Clob with json
			CsDClob clob = valutazioneSchedaBarthel.getCsDDiario().getCsDClob();
			clob.setDatiClob(schedaBarthelDTO.getJsonString());
			diarioDao.updateClob(clob);

			diarioDao.updateValutazione(valutazioneSchedaBarthel);
		}
	}

	@Override
	public CsDValutazione findValutazioneChildByPadreId(JsonBaseDTO dto) {
		CsDValutazione barthel = diarioDao.findValutazioneChildByPadreId(dto.getDiarioPadreId(), dto.getTipoDiarioId());
		return barthel;
	}

	//SISO-812
	@Override
	@Interceptors(AccessoFascicoloInterceptor.class)
	public List<ListaDatiScuolaDTO> findScuoleByCaso(BaseDTO dto) {
		return diarioDao.findScuoleByCaso((Long) dto.getObj());
	}

	@Override
	public void updateScuola(BaseDTO dto) throws Exception {
		CsDScuola scuola = (CsDScuola) dto.getObj();
		//modifica
		scuola.getCsDDiario().setUsrMod(dto.getUserId());
		scuola.getCsDDiario().setDtMod(new Date());
		
		diarioDao.updateScuola(scuola);
	}

	@Override
	public CsDDiario saveScuola(BaseDTO dto) throws Exception {
		CsDScuola jpa = (CsDScuola)dto.getObj();
		Long casoId = dto.getObj2()!=null ? (Long)dto.getObj2() : null;
		CsOOperatoreSettore opSettore = (CsOOperatoreSettore) dto.getObj3();
        
		CsACaso csa = casoDao.findCasoById(casoId);
		CsOOperatoreBASIC responsabile = casoDao.findResponsabileBASIC(casoId);
			
		jpa.getCsDDiario().setCsACaso(csa);
		jpa.getCsDDiario().setResponsabileCaso(responsabile!=null ? responsabile.getId() : null);
		
		
        CsTbTipoDiario cstd = new CsTbTipoDiario(); 
        cstd.setId(new Long(DataModelCostanti.TipoDiario.DATI_SCUOLA_ID)); 
        
        Date dtIns = new Date();
        jpa.getCsDDiario().setDtIns(dtIns);
        jpa.getCsDDiario().setUserIns(dto.getUserId());
        jpa.getCsDDiario().setCsTbTipoDiario(cstd);
        jpa.getCsDDiario().setCsOOperatoreSettore(opSettore);
        jpa.getCsDDiario().setDtAmministrativa(dtIns);
        dto.setObj(jpa);
		
		saveDiarioChild(jpa, null, null);
		
		try{
			BaseDTO bdto = new BaseDTO();
			this.copiaCsTBaseObject(dto, bdto);
			//Invio alert nuovo inserimento
			bdto.setObj(csa.getCsASoggetto());
			bdto.setObj2(opSettore);
			bdto.setObj3(DataModelCostanti.TipiAlertCod.SCUOLA);
			bdto.setObj4("una nuova scuola");
			
			alertSessionBean.addAlertNuovoInserimentoToResponsabileCaso(bdto);
		}catch(CarSocialeServiceException e){
			logger.error("Errore invio alert nuova SCUOLA:"+e.getMessage(), e);
		}
		return jpa.getCsDDiario();
	}

	@Override
	public void deleteScuola(BaseDTO dto) throws Exception {
		CsDScuola scuola = (CsDScuola) dto.getObj();
		diarioDao.deleteScuolaById(scuola.getDiarioId());
		diarioDao.deleteDiario(scuola.getDiarioId());
	}

	//SISO-812
	@Override
	@Interceptors(AccessoFascicoloInterceptor.class)
	public List<ListaDatiIseeDTO> findIseeAttiveByCaso(BaseDTO dto) {
		List<ListaDatiIseeDTO> out = new ArrayList<ListaDatiIseeDTO>();
		List<CsDIsee> in = diarioDao.findIseeAttiveByCaso((Long) dto.getObj());	
		for(CsDIsee isee :  in){
			CsDDiario d = isee.getCsDDiario();
			ListaDatiIseeDTO el = new ListaDatiIseeDTO(d.getVisSecondoLivello(), d.getCsOOperatoreSettore().getCsOSettore().getId());
			el.setDataChiusuraDa(d.getDtChiusuraDa());
			el.setAnnoRif(isee.getAnnoRif());
			el.setIsee(isee.getIsee());
			el.setDiarioId(d.getId());
			el.setIse(isee.getIse());
			//Inserire altri elementi, se necessari. al momento non viene usato per caricare la lista del fascicolo --> JSON
			el.setDataUltimaModifica(d.getDataUltimaModifica());
			el.setOpUltimaModifica(d.getOperatoreUltimaModifica());
			out.add(el);
		}
		return out;
	}
	
	//SISO-812
	@Override
	@Interceptors(AccessoFascicoloInterceptor.class)
	public List<CsDIsee> findIseeByCaso(BaseDTO dto) {
		return diarioDao.findIseeByCaso((Long) dto.getObj());
	}

	@Override
	public void updateIsee(BaseDTO dto) throws Exception {
		CsDIsee jpa = (CsDIsee) dto.getObj();
		
		jpa.getCsDDiario().setUsrMod(dto.getUserId());
		jpa.getCsDDiario().setDtMod(new Date());
		
		diarioDao.updateIsee(jpa);
	}

	@Override
	public CsDDiario saveIsee(BaseDTO dto) throws Exception {
		CsDIsee jpa = (CsDIsee)dto.getObj();
		Long casoId = dto.getObj2()!=null ? (Long)dto.getObj2() : null;
		CsOOperatoreSettore opSettore = (CsOOperatoreSettore) dto.getObj3();
        
		CsACaso csa = casoDao.findCasoById(casoId);
		CsOOperatoreBASIC responsabile = casoDao.findResponsabileBASIC(casoId);
		
		jpa.getCsDDiario().setCsACaso(csa);
		jpa.getCsDDiario().setResponsabileCaso(responsabile!=null ? responsabile.getId() : null);

		
        CsTbTipoDiario cstd = new CsTbTipoDiario(); 
        cstd.setId(new Long(DataModelCostanti.TipoDiario.ISEE_ID)); 
        
        jpa.getCsDDiario().setDtIns(new Date());
        jpa.getCsDDiario().setUserIns(dto.getUserId());
        jpa.getCsDDiario().setCsTbTipoDiario(cstd);
        jpa.getCsDDiario().setCsOOperatoreSettore(opSettore);
		
        dto.setObj(jpa);
        
        CsDIsee isee = (CsDIsee) dto.getObj();
		saveDiarioChild(isee, null, null);
		
		try{
			//Invio alert nuovo inserimento
			BaseDTO bdto = new BaseDTO();
			this.copiaCsTBaseObject(dto, bdto);
			bdto.setObj(csa.getCsASoggetto());
			bdto.setObj2(opSettore);
			bdto.setObj3(DataModelCostanti.TipiAlertCod.ISEE);
			bdto.setObj4("un nuovo ISEE");
			alertSessionBean.addAlertNuovoInserimentoToResponsabileCaso(bdto);
		}catch(CarSocialeServiceException e){
			logger.error("Errore invio alert nuovo ISEE:"+e.getMessage(), e);
		}
		
		return isee.getCsDDiario();
	}

	@Override
	public void deleteIsee(BaseDTO dto) throws Exception {
		Long iseeId = (Long) dto.getObj();
		diarioDao.deleteIseeById(iseeId);
		diarioDao.deleteDiario(iseeId);
	}

	@Override
	public void deleteSchedaJson(BaseDTO dto) throws Exception {
		Long diarioId = (Long) dto.getObj();
		if (diarioId != null && diarioId > 0) {
			CsDDiario diario = diarioDao.findDiarioById(diarioId);
			if (diario.getCsDDiariFiglio() == null || diario.getCsDDiariFiglio().isEmpty()) {
				dto.setObj(diario.getId());
				CsDValutazione val = findValutazioneById(dto);
				CsDClob clob = diario.getCsDClob();
				diarioDao.deleteValutazione(val.getDiarioId());
				diarioDao.deleteDiario(diarioId);
				if (clob.getCsDDiarios().isEmpty())
					diarioDao.deleteClob(clob.getId());
			} else {
				throw new CarSocialeServiceException("Impossibile eliminare il record: sono presenti diari che fanno riferimento ad esso.");
			}
		}
	}

	@Override
	public List<CsDValutazione> getSchedeValutazioneUdcId(BaseDTO dto) {
		List<CsDValutazione> valutazioni = new ArrayList<CsDValutazione>();
		
		List<Integer> tipi = new ArrayList<Integer>();
		if(dto.getObj2() instanceof List){
			tipi.addAll((List<Integer>)dto.getObj2());
		}else
			tipi.add((Integer)dto.getObj2());
		
		if (dto.getObj() != null)
			valutazioni = diarioDao.getSchedeValutazioneUdcId((Long) dto.getObj(), tipi);
		else
			valutazioni = diarioDao.findAllSchedeValutazioneTipo((Integer) dto.getObj2());

		return valutazioni;
	}

	@Override
	public void deleteSchedeValutazioneByUdcId(BaseDTO dto) throws Exception {
		List<CsDValutazione> valutazioni = new ArrayList<CsDValutazione>();
		valutazioni = getSchedeValutazioneUdcId(dto);
		for (CsDValutazione v : valutazioni) {
			dto.setObj(v.getDiarioId());
			dto.setObj2(null);
			deleteSchedaJson(dto);
		}
	}

	@Override
	public CsDColloquio findColloquioById(BaseDTO dto) throws Exception {
		return diarioDao.findColloquioById((Long) dto.getObj());
	}

	@Override
	public CsDIsee findIseeById(BaseDTO dto) {
		return diarioDao.findIseeId((Long) dto.getObj());
	}

	@Override
	public CsFlgIntervento findFglInterventoById(BaseDTO dto) {
		return diarioDao.findFglInterventoById((Long) dto.getObj());
	}

	@Override
	public CsDValutazione findValutazioneById(BaseDTO dto) {
		return diarioDao.findValutazioneById((Long) dto.getObj());
	}

	/*
		@Override
		public CsDPai saveSchedaPai(PaiDTO dto) throws Exception {

			CsDPai pai = (CsDPai) dto.getPai();

			BaseDTO bdto = new BaseDTO();
			bdto.setEnteId(dto.getEnteId());
			bdto.setUserId(dto.getUserId());
			bdto.setSessionId(dto.getSessionId());

			if (pai.getDiarioId() == null || pai.getCsDDiario().getId() == 0) {

				IterDTO iter = new IterDTO();

				iter.setIdCaso(dto.getCasoId());
				iter.setUserId(dto.getUserId());
				iter.setEnteId(dto.getEnteId());
				iter.setSessionId(dto.getSessionId());

				CsACaso caso = casoSessionBean.findCasoById(iter);

				Date dtIns = new Date();
				pai.getCsDDiario().setCsACaso(caso);
				pai.getCsDDiario().setUserIns(dto.getUserId());
				pai.getCsDDiario().setDtIns(dtIns);
				pai.getCsDDiario().setUsrMod(null);
				pai.getCsDDiario().setDtMod(null);
				pai.getCsDDiario().setResponsabileCaso(dto.getResponsabileId());
				pai.getCsDDiario().setDtAmministrativa(dtIns);
				pai.getCsDDiario().setCsOOperatoreSettore(dto.getOpSettore());

				CsTbTipoDiario tipo = new CsTbTipoDiario();
				tipo.setId(new Long(DataModelCostanti.TipoDiario.PAI_ID));
				pai.getCsDDiario().setCsTbTipoDiario(tipo);

				//Recupero il diario associato alla relazione da inserire
				if (dto.getIdRelazione() != null) {
					bdto.setObj(dto.getIdRelazione());
					CsDDiario relazione = findDiarioById(bdto);
					pai.getCsDDiario().addCsDDiariFiglio(relazione);
				}

				bdto.setObj(pai.getCsDDiario());
				CsDDiario diario = createDiario(bdto);
				pai.setDiarioId(diario.getId());
				pai.setCsDDiario(diario);

				diarioDao.saveSchedaPai(pai);

			} else {

				pai.getCsDDiario().setUsrMod(dto.getUserId());
				pai.getCsDDiario().setDtMod(new Date());
				diarioDao.updateSchedaPai(pai);

			}

			return pai;
		}
	*/
	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors
	public CsDPai savePai(BaseDTO dto) throws Exception {
		CsDPai pai = (CsDPai) dto.getObj();
		//SISO-1280 Inizio
		Long idCaso = dto.getObj2() != null ? (Long)dto.getObj2() : null;
		if (idCaso != null) {
			CsACaso csa = casoDao.findCasoById(idCaso);
			CsOOperatoreBASIC responsabile = casoDao.findResponsabileBASIC(idCaso);
			
			pai.getCsDDiario().setCsACaso(csa);
			pai.getCsDDiario().setResponsabileCaso(responsabile!=null  ? responsabile.getId() : null);
		}
		//SISO-1280 Fine
		CsOOperatoreSettore opSettore = dto.getObj3() != null ? (CsOOperatoreSettore) dto.getObj3() : null;
		CsTbTipoDiario cstd = new CsTbTipoDiario();
		cstd.setId(new Long(DataModelCostanti.TipoDiario.PAI_ID));
		
		pai.getCsDDiario().setCsTbTipoDiario(cstd);
		pai.getCsDDiario().setDtIns(new Date());
		pai.getCsDDiario().setUserIns(dto.getUserId());
		pai.getCsDDiario().setCsOOperatoreSettore(opSettore);
		
		for(CsPaiMastSogg s : pai.getBeneficiari()){
			s.setUserIns(dto.getUserId());
			s.setDtIns(new Date());
		}
		
		saveDiarioChild(pai, null, null);
		return pai;
	}

	@Override
	public PaiDTO findPaiFullById(BaseDTO dto) {
		CsDPai pai = diarioDao.findPaiByDiarioId((Long) dto.getObj());
		diarioDao.loadPadreFiglioEntities(pai.getCsDDiario());
		diarioDao.loadDocEntities(pai.getCsDDiario());

		PaiDTO paiDTO = new PaiDTO(pai);
		return paiDTO;
	}

	@Override
	public void updatePai(BaseDTO dto) throws Exception {
		CsDPai pai = (CsDPai) dto.getObj();
		
		pai.getCsDDiario().setUsrMod(dto.getUserId());
		pai.getCsDDiario().setDtMod(new Date());
		
		for(CsPaiMastSogg s : pai.getBeneficiari()){
			s.setUserMod(dto.getUserId());
			s.setDtMod(new Date());
		}
		
		diarioDao.updateSchedaPai(pai);		
	}

	@Override
	public void deletePai(BaseDTO dto) {
		CsDPai pai = (CsDPai) dto.getObj();
		Long idDiario = pai.getCsDDiario().getId();
		diarioDao.deleteSchedaPai(pai);
		diarioDao.deleteRelDiarioDiarioRif(idDiario);
		diarioDao.deleteDiario(idDiario);
	}

	//SISO-812
	@Override
	@Interceptors(AccessoFascicoloInterceptor.class)
	public List<CsDPai> findPaiByCaso(BaseDTO dto) {
		Long idCaso = (Long) dto.getObj();
		return diarioDao.findPaiByCaso(idCaso);
	}

	@Override
	public void salvaRifRelazioneToPai(BaseDTO dto) {		
		
		Long idRelazione = (Long) dto.getObj();
		Long idPaiNuovoRif = (Long) dto.getObj2();
		Long idPaiRifDaSostituireId = (Long) dto.getObj3();		
		
		if(idPaiNuovoRif==null) //rimozione			
		{			
			diarioDao.deleteDiarioRif(idPaiRifDaSostituireId, idRelazione);
		}
		else //aggiunta
		{
			CsDDiario relazione= diarioDao.findDiarioById(idRelazione);
			List<CsDDiario> pais =relazione.getCsDDiariPadre();
			if(pais!=null){ 
				for(CsDDiario d: pais){	
					if(Long.valueOf(d.getId()).equals(idPaiNuovoRif)){
						return; // skip: è già presente
					}			
				}
			}
			diarioDao.saveDiarioRif(idPaiNuovoRif, idRelazione);
		}
	}

	@Override
	public CsDRelazione findLastRelazioneProblAperte(BaseDTO dto) {
		if(dto.getObj()!=null && (Long)dto.getObj()!=0)
			return diarioDao.findLastRelazioneProblAperte((Long) dto.getObj());
		else
			return null;			
	}
	
	@Override
	public void deleteCsRelRelazioneProbl(BaseDTO dto) {
		if(dto.getObj()!=null && (CsRelRelazioneProbl)dto.getObj()!=null)
		{			
			diarioDao.deleteCsRelRelazioneProbl((CsRelRelazioneProbl) dto.getObj());
		}
	}

	@Override
	public List<CsDDiario> findDiarioBySchedaId(BaseDTO dto) {
		return diarioDao.findDiarioBySchedaId((Long) dto.getObj());
	}

	@Override
	public CsDValutazione saveSchedaValutazione(CsDValutazione schedaValutazione) {
		return diarioDao.saveSchedaValutazione(schedaValutazione);
	}
	

	//INIZIO SISO-438
	@Override
	public CsDDocIndividuale findDocIndividualeById(BaseDTO dto) {
		try {
			return diarioDao.findDocIndividuale((Long) dto.getObj());			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	//FINE SISO-438
	
	
	//INIZIO SISO-438
		@Override
		public void createAndsaveDocIndividuale(DocIndividualeDTO dto) throws Exception {

			try {

				long idDiarioRichiestaServizio = dto.getIdDiarioRichiestaServizio(); //Viene popolato quando l'inserimento del documento proviene dai servizi CUSTOM UDC
				
				CsDDiario csDDiario =  diarioDao.findDiarioById(idDiarioRichiestaServizio);
				
				CsDDiario diarioDocIndividuale = createDiarioDocIndividuale(dto); //userIns, (csDDiario!=null ? csDDiario.getSchedaId() : null) , nome, documento, tipo, casoId);

				if(csDDiario!=null){
					logger.debug("DIARIO PADRE id " + csDDiario.getId() );
					
					CsRelDiarioDiariorif csRif = new CsRelDiarioDiariorif();
					csRif.setId(new CsRelDiarioDiariorifPK());
					csRif.getId().setDiarioId(csDDiario.getId());
					csRif.getId().setDiarioIdRif(diarioDocIndividuale.getId());
					diarioDao.saveCsRelDiarioDiariorif(csRif);
	
					logger.debug("CsRelDiarioDiariorif padre " + csDDiario.getId() + " figlio " + diarioDocIndividuale.getId() );
				}
				
				CsDDocIndividuale csDDocIndividuale = createCsDDocIndividuale(diarioDocIndividuale, dto);
				diarioDao.savecCsDDocIndividuale(csDDocIndividuale); 
				
			} catch (Exception e) {
				logger.error(e);
				throw new RuntimeException(e);
			}		 
		}
		
 

		private CsDDiario createDiarioDocIndividuale(DocIndividualeDTO dto){
			
			long idDiarioRichiestaServizio = dto.getIdDiarioRichiestaServizio(); //Viene popolato quando l'inserimento del documento proviene dai servizi CUSTOM UDC	
			CsDDiario csDDiario =  diarioDao.findDiarioById(idDiarioRichiestaServizio);
		
			CsDDiario diario = new CsDDiario(); 
			
			if (dto.getCasoId()!=null) {
				CsACaso caso = casoDao.findCasoById(dto.getCasoId()); 
				diario.setCsACaso(caso);
				
				CsOOperatoreBASIC responsabile = casoDao.findResponsabileBASIC(dto.getCasoId());
				diario.setResponsabileCaso(responsabile!=null ? responsabile.getId() : null);
			}
			diario.setSchedaId(csDDiario!=null ? csDDiario.getSchedaId() : null);
			diario.setCsTbTipoDiario(configurazioneDao.findTipoDiarioById(DataModelCostanti.TipoDiario.DOC_INDIVIDUALE_ID));
			
			diario.setCsOOperatoreSettore(null);
			
			diario.setCsDClob(null);
			diario.setDtIns(new Date());
			diario.setUserIns(dto.getUserId());
			diario.setDtAmministrativa(new Date());
		
			diario =  diarioDao.mergeCsDDiario(diario); 
			logger.debug("diario id " + diario.getId());
			 
			CsLoadDocumento csLoadDocumento = createCsLoadDocumento(dto.getNome(), dto.getDocumento(), dto.getTipo(), dto.getUserId());
			csLoadDocumento = diarioDao.mergeCsLoadDocumento(csLoadDocumento);   
			logger.debug("csLoadDocumento id " + csLoadDocumento.getId()); 
			
			 
			CsDDiarioDoc csDDiarioDoc = new CsDDiarioDoc();  
			CsDDiarioDocPK ddPK = new CsDDiarioDocPK(); 
			
			ddPK.setDiarioId(diario.getId());
			ddPK.setDocumentoId(csLoadDocumento.getId());
			csDDiarioDoc.setId(ddPK);
			csDDiarioDoc.setCsLoadDocumento(csLoadDocumento); 
			csDDiarioDoc.setCsDDIario(diario);
			
			diarioDao.persistCsDDiarioDoc(csDDiarioDoc);
			
			return diario;
		}
		


		private CsDDocIndividuale createCsDDocIndividuale(CsDDiario diarioDocIndividuale, DocIndividualeDTO dto) {
			CsDDocIndividuale csDDocIndividuale = new CsDDocIndividuale();

			csDDocIndividuale.setDescrizione(dto.getNome());
			csDDocIndividuale.setDiarioId(diarioDocIndividuale.getId());
			csDDocIndividuale.setCsDDiario(diarioDocIndividuale);
			csDDocIndividuale.setCsTbSottocartellaDoc(configurazioneDao.getTipoCartellaById(dto.getSottoCartella())); 
			csDDocIndividuale.setPrivato(dto.isPrivato());
			csDDocIndividuale.setLettura(null);
			csDDocIndividuale.setLetto(dto.isLetto());
			
			return csDDocIndividuale;
		}

		private static CsLoadDocumento createCsLoadDocumento(String nome, byte[] documento, String tipo, String userIns) {
			CsLoadDocumento csLoadDocumento = new CsLoadDocumento();
			csLoadDocumento.setNome(nome);
			csLoadDocumento.setDocumento(documento); 
			csLoadDocumento.setDtIns(new Date());
			csLoadDocumento.setTipo(tipo);
			csLoadDocumento.setUsrIns(userIns);
			return csLoadDocumento;
		}

		@Override
		public List<DocIndividualeBean> findDocIndividualeByCfSchedaSegnalato(BaseDTO dto) {
			List<DocIndividualeBean> lstOut = new ArrayList<DocIndividualeBean>();
			String cf = (String) dto.getObj();
			Boolean permessoDownload = (Boolean)dto.getObj2();
			Long opSettoreId = (Long) dto.getObj3();
			Long organizzazioneId = (Long) dto.getObj4();
			List<Long> downBypass = configurazioneDao.findTipoDocAuthDownload(opSettoreId);
			
			if(organizzazioneId!=null){
				List<CsDDocIndividuale> lst = diarioDao.findDocIndividualeByCfSchedaSegnalato(cf, organizzazioneId);
				for (CsDDocIndividuale docInd : lst) {
					CsLoadDocumento loadDocumento = documentoDao.findLoadDocumentoByDiarioId(docInd.getDiarioId());
					if(loadDocumento!=null){
						
						DocIndividualeBean d = new DocIndividualeBean(docInd, loadDocumento, permessoDownload, dto.getUserId(), downBypass);
						lstOut.add(d);
					}
				}
			}
			return lstOut;
		}


	//FINE SISO-438
		
		//SISO-763
		@Override
		public List<DiarioAnagraficaDTO> findDiarioAnagraficaByDiarioId(BaseDTO dto){
			List<DiarioAnagraficaDTO> result = new ArrayList<DiarioAnagraficaDTO>();
			Long diarioId = (Long) dto.getObj();
			List<CsDDiarioAna> resultDao = diarioDao.findDiarioAnagraficaByDiarioId(diarioId);
			
			for (CsDDiarioAna csDDiarioAna : resultDao) {
				DiarioAnagraficaDTO da = new DiarioAnagraficaDTO();
				BeanUtils.copyProperties(csDDiarioAna, da);
				result.add(da);
			}
			
			return result;
		}
		
		//SISO-763
		@Override
		public List<DiarioAnagraficaDTO> saveDiarioAnagrafica(BaseDTO dto) throws Exception{
			List<CsDDiarioAna> result = new ArrayList<CsDDiarioAna>();
			List<DiarioAnagraficaDTO> toReturn = new ArrayList<DiarioAnagraficaDTO>();
			
			List<DiarioAnagraficaDTO> diarioAnagrafice = (List<DiarioAnagraficaDTO>) dto.getObj();
			Long diarioId = (Long) dto.getObj2();
			
			for (DiarioAnagraficaDTO da : diarioAnagrafice) {
				CsDDiarioAna cda = new CsDDiarioAna();
				BeanUtils.copyProperties(da, cda);
				if(da.getId() == null){
					cda.setUserIns(dto.getUserId());
					cda.setDataIns(new Date());
				}else{
					cda.setUserMod(dto.getUserId());
					cda.setDataMod(new Date());
				}
				
				result.add(cda);
			}
			
			diarioDao.deleteDiarioAnagrafica(diarioId);
			for (CsDDiarioAna da : result) {
				diarioDao.saveDiarioAnagrafica(da);
				
				DiarioAnagraficaDTO dadto = new DiarioAnagraficaDTO();
				BeanUtils.copyProperties(da, dadto);
				toReturn.add(dadto);
			}
			
			return toReturn;
	   }
		
	   //SISO-763
	   @Override
	   @Interceptors(AccessoFascicoloInterceptor.class)
	   public List<RelazioneDTO> findRelazioniFascicolo(BaseDTO dto){
		   Long anagraficaId = (Long)dto.getObj2();
		   String cf = (String) dto.getObj3();
		   
		   List<RelazioneDTO> listaRelazioniDTO = findRelazioniByCaso(dto);
			
		   listaRelazioniDTO.addAll(findRelazioniPaiEsterniByCF(cf)); 
         	
			// carico le relazioni collegate
		   CeTBaseObject cet = new CeTBaseObject();
		   this.copiaCsTBaseObject(dto, cet);
		   List<RelazioneDTO> relCollegate = findRelazioniCollegate(anagraficaId, cet);
		   listaRelazioniDTO.addAll(relCollegate);
		   
		   return listaRelazioniDTO;
	   }
	   
	   private List<RelazioneDTO> findRelazioniCollegate(Long idAnagrafica, CeTBaseObject cet) {
		   List<RelazioneDTO> lst = new ArrayList<RelazioneDTO>();
		   List<Long> diarioIds =  diarioDao.findDiarioIdsByAnagraficaId(idAnagrafica, DataModelCostanti.TipoDiario.RELAZIONE_ID); //6 = ATTIVITA PROFESSIONALI
		  
		   BaseDTO dto = new BaseDTO();
		   this.copiaCsTBaseObject(cet, dto);
		   dto.setObj(diarioIds);
		   lst.addAll(findRelazioniByIds(dto));
		   return lst;
	  }
	   
		@Override
		@AuditConsentiAccessoAnonimo
		@AuditSaltaValidazioneSessionID
		public ConfrontoSsCsDTO estraiDatiSchedaSS(BaseDTO dto) {
			return diarioDao.estraiDatiSchedaSS((BigDecimal)dto.getObj());
		}
		@Override
		@AuditConsentiAccessoAnonimo
		@AuditSaltaValidazioneSessionID
		public ConfrontoSsCsDTO estraiDatiSchedaCS(BaseDTO in) {
			ConfrontoSsCsDTO dto = new ConfrontoSsCsDTO();
			String cf = (String)in.getObj();
			CsASoggettoLAZY soggetto = soggettoDao.getSoggettoByCF(cf);
			
			CsAAnagrafica ana = soggetto.getCsAAnagrafica();
			dto.setNome(ana.getNome());
			dto.setCognome(ana.getCognome());
			dto.setCf(ana.getCf());
			dto.setCittadinanza(ana.getCittadinanza());
			dto.setIdentificativo(new BigDecimal(soggetto.getCsACaso().getIdentificativo()));
			dto.setCaso(soggetto.getCsACaso());
			
			
			CsADatiSociali ds = schedaDao.findDatiSocialiAttiviBySoggettoCf(cf);
			if(ds!=null){
				dto.setLavoro(ds.getCondLavorativaId()!=null ? ds.getCondLavorativaId().toString() : null);
				dto.setProfessione(ds.getProfessioneId()!=null ? ds.getProfessioneId().toString() : null);
				dto.setAbitazione(ds.getAbitazione());
				dto.setStranieri(ds.getStraniero());
			}
			
			
			in.setObj(soggetto.getAnagraficaId());
			CsAAnaIndirizzo ind = indirizzoBean.getIndirizzoResidenza(in);
			if(ind!=null){
				Boolean sfd = DataModelCostanti.SENZA_FISSA_DIMORA.equalsIgnoreCase(ind.getIndirizzo()); 
				dto.setSenzaFissaDimora(sfd);
				dto.setResidenzaVia(ind.getIndirizzo());
				dto.setResidenzaCivico(ind.getCivicoNumero());
				dto.setResidenzaComune(ind.getComCod());
				dto.setResidenzaNazione(ind.getStatoCod());
			}
				
		    	
			CsASoggettoStatoCivile staCiv = soggettoDao.getSoggettoStatoCivileAllaDataBySoggetto(soggetto.getAnagraficaId(), new Date());
			dto.setStatoCivile(staCiv!=null ? staCiv.getCsTbStatoCivile().getDescrizione() : null);
			
			return dto;
		}

		@Override
		public List<KeyValueDTO> findSintesiIseeByCaso(BaseDTO dto){
			return diarioDao.findSintesiIseeByCaso((Long)dto.getObj());
		}

		@Override
		@Interceptors(AccessoFascicoloInterceptor.class)
		public List<ListaDatiPaiDTO> findListaPaiFascicolo(PaiSearchCriteria dto){
			List<ListaDatiPaiDTO> lstOut = new ArrayList<ListaDatiPaiDTO>();
			List<CsVistaPai> lstIn = diarioDao.findListaPaiFascicolo(dto);
			lstOut = valorizzaPaiViewListaDTO(lstIn);
			return lstOut;
		}
		
		public Integer countListaPaiFascicolo(PaiSearchCriteria dto){
			BigDecimal count = diarioDao.countListaPaiFascicolo(dto);
			return count.intValue();
		}
		
		@Override
		public List<ListaDatiPaiDTO> findListaPaiEsterni(PaiSearchCriteria dto) {
			List<ListaDatiPaiDTO> lstOut = new ArrayList<ListaDatiPaiDTO>();
			List<CsVistaPai> lstIn =  new ArrayList<CsVistaPai>();
			lstIn = diarioDao.findListaPaiEsterni(dto);
			lstOut =  this.valorizzaPaiViewListaDTO(lstIn);
			return lstOut;
		}
			
		private List<ListaDatiPaiDTO> valorizzaPaiViewListaDTO(List<CsVistaPai> lstIn){
			ArrayList<ListaDatiPaiDTO> lstOut = new ArrayList<ListaDatiPaiDTO>();
			for(CsVistaPai p : lstIn){
				ListaDatiPaiDTO dto = new ListaDatiPaiDTO(p.getVisSecondoLivello(), p.getSettoreId());
				List<SoggettoPaiBean> beneficiari = new ArrayList<SoggettoPaiBean>();
				for(CsPaiMastSogg s : p.getBeneficiari()){
					SoggettoPaiBean b = new SoggettoPaiBean(s);
					beneficiari.add(b);
				}
				dto.setBeneficiari(beneficiari);
				dto.setChiuso(p.getChiuso());
				dto.setDaChiudere(p.getDaChiudere());
				dto.setDaControllare(p.getDaControllare());
				dto.setDataAttivazioneDa(p.getDataAttivazioneDa());
				dto.setDataChiusuraDa(p.getDataChiusuraDa());
				dto.setDataChiusuraPrevista(p.getDataChiusuraPrevista());
				dto.setDataUltimaModifica(p.getDataUltimaModifica());
				dto.setDiarioId(p.getDiarioId());
				dto.setOpModifica(p.getOpUltimaModifica());
				dto.setTipoBeneficiario(p.getTipoBeneficiario());
				dto.setTipoPai(p.getTipoPaiDesc());
				dto.setTxtObiettivi(p.getTxtObiettivi());
			
				lstOut.add(dto);
			}
			return lstOut;
		}
		
	private List<RelazioneDTO> findRelazioniPaiEsterniByCF(String cf) {
		List<RelazioneDTO> lst = new ArrayList<RelazioneDTO>();
		if(!StringUtils.isBlank(cf)){
			List<CsDRelazione> lstr = diarioDao.findRelazioniPaiEsterniByCF(cf);
			for (CsDRelazione r : lstr) {
				List<CsRelRelazioneProbl> lstProbematiche = r.getCsRelRelazioneProbl();
				List<CsRelRelazioneTipoint> listaInterventi = r.getCsRelRelazioneTipoint();
				CsLoadDocumento doc = documentoDao.findLoadDocumentoByDiarioId(r.getDiarioId());
				List<PaiDTOExt> lstPai = diarioDao.loadPaiEntities(r.getCsDDiario());
				RelazioneDTO relDTO = new RelazioneDTO(r, doc, lstPai, lstProbematiche, listaInterventi);
				lst.add(relDTO);
			}
		}
		return lst;
	}
	
	@Override
	public List<RelazioneDTO> findRelazioniByIds(BaseDTO dto) {
		List<CsDRelazione> lstr = diarioDao.findRelazioniByIds((List<Long>)dto.getObj());
		List<RelazioneDTO> lst = new ArrayList<RelazioneDTO>();
		for (CsDRelazione r : lstr) {
			List<CsRelRelazioneProbl> lstProbematiche = r.getCsRelRelazioneProbl();
			List<CsRelRelazioneTipoint> listaInterventi = r.getCsRelRelazioneTipoint();
			CsLoadDocumento doc = documentoDao.findLoadDocumentoByDiarioId(r.getDiarioId());
			List<PaiDTOExt> lstPai = diarioDao.loadPaiEntities(r.getCsDDiario());
			RelazioneDTO relDTO = new RelazioneDTO(r, doc, lstPai, lstProbematiche, listaInterventi);
			lst.add(relDTO);
		}
		return lst;
	}
	
	@Override
	public List<RelazioneSintesiDTO> findRelazioniSintesiPaiEsterniByCF(BaseDTO dto) {
		List<CsDRelazione> lstr = diarioDao.findRelazioniPaiEsterniByCF((String) dto.getObj());
		
		List<RelazioneSintesiDTO> lst = new ArrayList<RelazioneSintesiDTO>();
		for (CsDRelazione r : lstr) {
			List<Long> lstPai = diarioDao.findPadriCollegatiByFiglio(TipoDiario.PAI_ID, TipoDiario.RELAZIONE_ID, r.getDiarioId());
			RelazioneSintesiDTO relDTO = this.fillRelazioneSintesi(r, lstPai);
			lst.add(relDTO);
		}
		return lst;
	}
	
	private RelazioneSintesiDTO fillRelazioneSintesi(CsDRelazione rel, List<Long> lstPai){
		RelazioneSintesiDTO out = new RelazioneSintesiDTO();
		out.setDiarioId(rel.getDiarioId());
		out.setDtAmministrativa(rel.getCsDDiario().getDtAmministrativa());
    	out.setSituazioneAmbientale(rel.getSituazioneAmb());
    	out.setSituazioneParentale(rel.getSituazioneParentale());
    	out.setSituazioneSanitaria(rel.getSituazioneSanitaria());
    	out.setProposta(rel.getProposta());
    	out.setDescMacroAttivita(rel.getMacroAttivita().getDescrizione());
    	out.setDescMicroAttivita(rel.getMicroAttivita().getDescrizione());
    	out.setTipoFormMicroAttivita(rel.getMicroAttivita().getFlagTipoForm());
    	out.setPaiCollegati(lstPai);
    	return out;
	}

	// SISO-1280
	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors
	public void saveBeneficiariPai(BaseDTO dto) {

		List <CsPaiMastSogg> lstBeneficiari = (List<CsPaiMastSogg>) dto.getObj();
		if (lstBeneficiari != null) {
			diarioDao.saveBeneficiariPai (lstBeneficiari);
		}
	}

	@Override
	public List<CsIIntervento> findInterventiPaiEsterniByCF(BaseDTO dto) {
		return diarioDao.findInterventiPaiEsterniByCF((String)dto.getObj());
	}

	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors 
	public List<CsPaiMastSogg> findSoggettiPaiSenzaCaso(BaseDTO dto) {
		return diarioDao.findSoggettiPaiSenzaCaso();
	}

	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors
	public void updateSoggettoPai(BaseDTO dto) {
		diarioDao.updateSoggettoPai((CsPaiMastSogg) dto.getObj());
	}

	@Override
	public CsDScuola getScuolaById(BaseDTO dto) {
		return diarioDao.getScuolaById((Long)dto.getObj());
	}
	
	@Override
	public PaiSintesiDTO findSintesiPaiById(BaseDTO dto){
		return diarioDao.findSintesiPaiById((Long)dto.getObj());
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors 
	public CsPaiMastSogg findSoggettoPaiByDiarioId(BaseDTO dto) {
		return diarioDao.findSoggettoPaiByDiarioId((Long)dto.getObj());
	}

	@Override
	public CsDPai getPaiById(BaseDTO dto) {
		return diarioDao.findPaiByDiarioId((Long)dto.getObj());
	}

	@Override
	public Integer countListaPaiEsterni(PaiSearchCriteria dto) {
		BigDecimal count = diarioDao.countListaPaiEsterni(dto);
		return count.intValue();
	}

	@Override
	public List<PaiSintesiDTO> findDatePai(PaiSearchCriteria psc) {
		return diarioDao.findDatePai(psc);
	}

}

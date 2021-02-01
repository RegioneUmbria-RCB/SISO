package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.*;
import it.webred.cs.csa.ejb.dao.*;
import it.webred.cs.csa.ejb.dto.*;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.base.ICsDDiarioChild;
import it.webred.cs.data.base.ICsDRelazioneChild;
import it.webred.cs.data.model.*;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.math.BigDecimal;
import java.util.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Stateless
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
	@EJB
	public AccessTableSoggettoSessionBeanRemote soggettoSessionBean;
	@EJB
	public AccessTableSchedaSessionBeanRemote schedaSessionBean;
	@EJB
	public AccessTableInterventoSessionBeanRemote interventoSessionBean;
	@EJB
	public AccessTableOperatoreSessionBeanRemote operatoreSessionBean;
	@EJB
	public AccessTableCasoSessionBeanRemote casoSessionBean;
	@EJB
	public AccessTableConfigurazioneSessionBeanRemote confSessionBean;
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
		//triage child
		rChild.setRelazioneId(csDiario.getId());
		diarioDao.saveDiarioChild(rChild);
	}

	public void saveDiarioChild(BaseDTO dto) {
		ICsDDiarioChild dChild = (ICsDDiarioChild) dto.getObj();
        ICsDRelazioneChild rChild;
        //mi assicuro che l'obj alla posizione 2 sia effettivamente un triage
        if(dto.getObj2() != null && dto.getObj2() instanceof CsDTriage){
        	rChild = (ICsDRelazioneChild) dto.getObj2();
        	saveDiarioChild(dChild,rChild);
        }
        else {
        	saveDiarioChild(dChild);
        }
	}

	@Override
	@Interceptors(AccessoFascicoloInterceptor.class)
	public List<CsDColloquioBASIC> getColloquios(BaseDTO dto) throws Exception {
		List<CsDColloquioBASIC> colloquios = new ArrayList<CsDColloquioBASIC>();

		if (dto.getObj() != null)
			colloquios = diarioDao.getColloquios((Long) dto.getObj());
		else
			colloquios = diarioDao.findAllColloquio();

		return colloquios;
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
		ColloquioDTO cdto = (ColloquioDTO) dto.getObj();
		CsASoggettoLAZY csASoggetto = (CsASoggettoLAZY) dto.getObj2();
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

			CsACaso caso = casoDao.findCasoById(csASoggetto.getCsACaso().getId());

			CsTbTipoDiario tipo = new CsTbTipoDiario(); 
		    tipo.setId(new Long(DataModelCostanti.TipoDiario.COLLOQUIO_ID)); 
		    
		    CsOOperatoreBASIC opResponsabile = casoDao.findResponsabileBASIC(caso.getId());
		    
			colloquio.getCsDDiario().setCsACaso(caso);
			colloquio.getCsDDiario().setCsTbTipoDiario(tipo);
			colloquio.getCsDDiario().setResponsabileCaso(opResponsabile.getId());
			colloquio.getCsDDiario().setCsOOperatoreSettore(opSettore);
			colloquio.getCsDDiario().setDtIns(new Date());
			colloquio.getCsDDiario().setUserIns(dto.getUserId());
			colloquio.getCsDDiario().setVisSecondoLivello(cdto.getVisSecondoLivello());
			
			dto.setObj(colloquio);
			dto.setObj2(null);
			dto.setObj3(null);
			this.saveDiarioChild(dto);
			
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
	public CsTbTipoDiario findTipoDiarioById(BaseDTO tipoDiarioIdDTO) throws Exception {
		CsTbTipoDiario tipoDiario = diarioDao.findTipoDiarioById((Long) tipoDiarioIdDTO.getObj());

		return tipoDiario;
	}

	@Override
	@Interceptors(AccessoFascicoloInterceptor.class)
	public List<RelazioneDTO> findRelazioniByCaso(BaseDTO i) {
		Long idCaso = (Long) i.getObj();
		List<RelazioneDTO> lst = new ArrayList<RelazioneDTO>();
		if(idCaso!=null){
			List<CsDRelazione> lstr = diarioDao.findRelazioniByCaso(idCaso);
			for (CsDRelazione r : lstr) {
				List <CsRelRelazioneProbl> lstProbematiche = r.getCsRelRelazioneProbl();
				List<CsRelRelazioneTipoint> listaInterventi  = r.getCsRelRelazioneTipoint();
				CsLoadDocumento doc = documentoDao.findLoadDocumentoByDiarioId(r.getDiarioId());
				List<PaiDTOExt> lstPai = diarioDao.loadPaiEntities(r.getCsDDiario());
				RelazioneDTO dto = new RelazioneDTO(r, doc, lstPai, lstProbematiche,listaInterventi);
				lst.add(dto);
			}
		}
		return lst;
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
	public List<CsDRelazione> findRelazioniByCasoTipoIntervento(InterventoDTO i) {
		Long idCaso = i.getCasoId();
		Long idTipoIntervento = i.getIdTipoIntervento();

		List<CsDRelazione> lstTmp = diarioDao.findRelazioniByCaso(idCaso);
		List<CsDRelazione> lstRelTi = new ArrayList<CsDRelazione>();

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

		return lstRelTi;
	}

	@Override
	public CsDRelazione findRelazioneLazyById(BaseDTO dto) {
		return diarioDao.findRelazioneById((Long) dto.getObj());
	}

	@Override
	public RelazioneDTO findRelazioneFullById(BaseDTO dto) {
		CsDRelazione rel = diarioDao.findRelazioneById((Long) dto.getObj());
		diarioDao.loadPadreFiglioEntities(rel.getCsDDiario());
		diarioDao.loadDocEntities(rel.getCsDDiario());

		CsLoadDocumento doc = findDocRelazione(rel);
		RelazioneDTO relDTO;
		
		if(rel.getCsDTriage() != null){
			relDTO = new RelazioneDTO(rel, doc, diarioDao.loadPaiEntities(rel.getCsDDiario()),rel.getCsDTriage());
		}else{
			relDTO = new RelazioneDTO(rel, doc, diarioDao.loadPaiEntities(rel.getCsDDiario()));
		}
		
		
		return relDTO;
	}

	@Override
	public void updateRelazione(BaseDTO dto) throws Exception {
		CsDRelazione relazione = (CsDRelazione) dto.getObj();
		
		relazione.getCsDDiario().setUsrMod(dto.getUserId());
		relazione.getCsDDiario().setDtMod(new Date());
		
		//mi assicuro che l'obj alla posizione 2 sia effettivamente un triage
		if(dto.getObj2() != null && dto.getObj2() instanceof CsDTriage){
			CsDTriage triage = (CsDTriage) dto.getObj2();
			diarioDao.updateTriage(relazione,triage);
		}else{
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
	public RelazioneDTO saveRelazione(BaseDTO dto) throws Exception {
		CsDRelazione relazione = (CsDRelazione) dto.getObj();
		
		CsACaso csa = new CsACaso();
		csa.setId((Long)dto.getObj2());
		
		CsOOperatoreBASIC responsabile = casoDao.findResponsabileBASIC(csa.getId());

		CsTbTipoDiario cstd = new CsTbTipoDiario();
		cstd.setId(new Long(DataModelCostanti.TipoDiario.RELAZIONE_ID));
		
		CsOOperatoreSettore opSettore = (CsOOperatoreSettore)dto.getObj3();
		
		relazione.getCsDDiario().setCsACaso(csa);
		relazione.getCsDDiario().setResponsabileCaso(responsabile.getId());
		relazione.getCsDDiario().setCsTbTipoDiario(cstd);
		relazione.getCsDDiario().setDtIns(new Date());
		relazione.getCsDDiario().setUserIns(dto.getUserId());
		relazione.getCsDDiario().setCsOOperatoreSettore(opSettore);
		
//		List<CsCTipoIntervento> listaTipoInt = new ArrayList<CsCTipoIntervento>();
//		if(relazione.getCsCTipoInterventos()!=null) listaTipoInt.addAll(relazione.getCsCTipoInterventos());
		relazione.setCsCTipoInterventos(null);
		
		List<CsRelRelazioneTipoint> listaInterventi = new ArrayList<CsRelRelazioneTipoint>();
		if(relazione.getCsRelRelazioneTipoint()!=null) listaInterventi.addAll(relazione.getCsRelRelazioneTipoint());
		relazione.setCsRelRelazioneTipoint(null);
		
		List<CsRelRelazioneProbl> listaProblematiche = new ArrayList<CsRelRelazioneProbl>();
		if(relazione.getCsRelRelazioneProbl()!=null) listaProblematiche.addAll(relazione.getCsRelRelazioneProbl());
		relazione.setCsRelRelazioneProbl(null);
		
		saveDiarioChild(dto);
		
		if(listaInterventi !=null)
		{
			for (CsRelRelazioneTipoint csRelRelazioneTipoint : listaInterventi) {
				csRelRelazioneTipoint.getId().setRelazioneDiarioId(relazione.getDiarioId());
				csRelRelazioneTipoint.setCsDRelazione(null);
				diarioDao.saveCsRelRelazioneTipoint(csRelRelazioneTipoint);				
			}
		}		
		
		if(listaProblematiche != null)
		{
			for (CsRelRelazioneProbl csRelRelazioneProbl : listaProblematiche) {
				csRelRelazioneProbl.getId().setRelazioneDiarioId(relazione.getDiarioId());
				csRelRelazioneProbl.setCsDRelazione(null);
				diarioDao.saveCsRelRelazioneProbl(csRelRelazioneProbl);				
			}
		}		
		
		CsLoadDocumento csLoadDoc = findDocRelazione(relazione);
		RelazioneDTO res = new RelazioneDTO(relazione, csLoadDoc, diarioDao.loadPaiEntities(relazione.getCsDDiario()));
		return res;
	}

	@Override
	public CsDPai saveSchedaPai(PaiDTO dto) throws Exception {

		CsDPai pai = (CsDPai) dto.getPai();

		BaseDTO bdto = new BaseDTO();
		copiaCsTBaseObject(dto, bdto);

		if (pai.getDiarioId() == null || pai.getCsDDiario().getId() == 0) {

			bdto.setObj(dto.getCasoId());
			CsACaso caso = casoSessionBean.findCasoById(bdto);

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
	public void deleteDiarioDoc(BaseDTO b) {
		diarioDao.deleteDiarioDoc((Long) b.getObj());
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
	public List<CsDDocIndividuale> findDocIndividualiByCaso(BaseDTO dto) {
		return diarioDao.findDocIndividualiByCaso((Long) dto.getObj());
	}

	@Override
	public void updateDocIndividuale(BaseDTO dto) throws Exception {
		CsDDocIndividuale docIndividuale = (CsDDocIndividuale) dto.getObj();
		diarioDao.updateDocIndividuale(docIndividuale);
	}

	@Override
	public CsDDiario saveDocIndividuale(BaseDTO dto) throws Exception {
		saveDiarioChild(dto);
		CsDDocIndividuale docIndividuale = (CsDDocIndividuale) dto.getObj();
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
		diarioDao.deleteDiarioDoc(loadDoc.getId());
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

			BaseDTO bdto = new BaseDTO();
			copiaCsTBaseObject(schedaJson, bdto);
			bdto.setObj(schedaJson.getCasoId());
			CsACaso caso = casoSessionBean.findCasoById(bdto);

			CsTbTipoDiario tipoDiario = diarioDao.findTipoDiarioById(schedaJson.getTipoDiarioId());

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
				bdto.setObj(caso.getCsASoggetto());
				bdto.setObj2(schedaJson.getOpSettore());
				
				if(DataModelCostanti.TipoDiario.VALUTAZIONE_MDS_ID==schedaJson.getTipoDiarioId()){
					bdto.setObj3(DataModelCostanti.TipiAlertCod.MULTIDIM);
					bdto.setObj4("una nuova valutazione multidimensionale");
				}else if(DataModelCostanti.TipoDiario.VALUTAZIONE_SINBA==schedaJson.getTipoDiarioId()){
					bdto.setObj3(DataModelCostanti.TipiAlertCod.SINBA);
					bdto.setObj4("una nuova valutazione S.In.Ba.");
			    }else if(DataModelCostanti.TipoDiario.PROVVEDIMENTI_TRIBUNALE==schedaJson.getTipoDiarioId()){
			    	bdto.setObj3(DataModelCostanti.TipiAlertCod.PROTRIB);
					bdto.setObj4("uno nuovo provvedimento del tribunale");
			    }
				
			
				alertSessionBean.addAlertNuovoInserimentoToResponsabileCaso(bdto);
				
					
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

			BaseDTO bdto = new BaseDTO();
			copiaCsTBaseObject(schedaBarthelDTO, bdto);
			bdto.setObj(valutazioneMultidim.getCsDDiario().getCsACaso().getId());
			CsACaso caso = casoSessionBean.findCasoById(bdto);

			CsTbTipoDiario tipoDiario = diarioDao.findTipoDiarioById(DataModelCostanti.TipoDiario.BARTHEL_ID);

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

	@Override
	public List<CsRelSettCatsocEsclusiva> findRelSettCatsocEsclusivaByTipoDiarioId(RelSettCatsocEsclusivaDTO dto) {
		return diarioDao.findRelSettCatsocEsclusivaByTipoDiarioId(dto.getTipoDiarioId());
	}

	@Override
	public CsRelSettCatsocEsclusiva findRelSettCatsocEsclusivaByIds(RelSettCatsocEsclusivaDTO dto) {
		return diarioDao.findRelSettCatsocEsclusivaByIds(dto.getTipoDiarioId(), dto.getCategoriaSocialeId(), dto.getSettoreId());
	}

	@Override
	public void saveCsRelSettCatsocEsclusiva(BaseDTO dto) {
		diarioDao.saveCsRelSettCatsocEsclusiva((CsRelSettCatsocEsclusiva) dto.getObj());
	}

	@Override
	public void updateCsRelSettCatsocEsclusiva(BaseDTO dto) {
		diarioDao.updateCsRelSettCatsocEsclusiva((CsRelSettCatsocEsclusiva) dto.getObj());
	}

	@Override
	public void deleteCsRelSettCatsocEsclusiva(RelSettCatsocEsclusivaDTO dto) {
		diarioDao.deleteCsRelSettCatsocEsclusiva(dto.getTipoDiarioId(), dto.getCategoriaSocialeId(), dto.getSettoreId());
	}

	//SISO-812
	@Override
	@Interceptors(AccessoFascicoloInterceptor.class)
	public List<CsDScuola> findScuoleByCaso(BaseDTO dto) {
		return diarioDao.findScuoleByCaso((Long) dto.getObj());
	}

	@Override
	public void updateScuola(BaseDTO dto) throws Exception {
		CsDScuola scuola = (CsDScuola) dto.getObj();
		diarioDao.updateScuola(scuola);
	}

	@Override
	public CsDDiario saveScuola(BaseDTO dto) throws Exception {
		saveDiarioChild(dto);
		CsDScuola scuola = (CsDScuola) dto.getObj();
		return scuola.getCsDDiario();
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
	public List<CsDIsee> findIseeByCaso(BaseDTO dto) {
		return diarioDao.findIseeByCaso((Long) dto.getObj());
	}

	@Override
	public void updateIsee(BaseDTO dto) throws Exception {
		CsDIsee isee = (CsDIsee) dto.getObj();
		diarioDao.updateIsee(isee);
	}

	@Override
	public CsDDiario saveIsee(BaseDTO dto) throws Exception {
		saveDiarioChild(dto);
		CsDIsee isee = (CsDIsee) dto.getObj();
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
	public CsDPai savePai(BaseDTO dto) throws Exception {
		CsDPai pai = (CsDPai) dto.getObj();
		
		CsACaso csa = new CsACaso();
		csa.setId((Long)dto.getObj2());
		
		CsOOperatoreBASIC responsabile = casoDao.findResponsabileBASIC(csa.getId());

		CsTbTipoDiario cstd = new CsTbTipoDiario();
		cstd.setId(new Long(DataModelCostanti.TipoDiario.PAI_ID));
		
		CsOOperatoreSettore opSettore = (CsOOperatoreSettore)dto.getObj3();
		
		pai.getCsDDiario().setCsACaso(csa);
		pai.getCsDDiario().setResponsabileCaso(responsabile.getId());
		pai.getCsDDiario().setCsTbTipoDiario(cstd);
		pai.getCsDDiario().setDtIns(new Date());
		pai.getCsDDiario().setUserIns(dto.getUserId());
		pai.getCsDDiario().setCsOOperatoreSettore(opSettore);
		
		saveDiarioChild(dto);
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
		
		if(idPaiNuovoRif==null) //sostituzione				
		{			
			diarioDao.deleteDiarioRif(idPaiRifDaSostituireId, idRelazione);
		}
		else //aggiunta
		{
			CsDDiario relazione= diarioDao.findDiarioById(idRelazione);
			List<CsDDiario> pais =relazione.getCsDDiariPadre();
			if(pais!=null) for(CsDDiario d: pais)
			{	
				if(Long.valueOf(d.getId()).equals(idPaiNuovoRif))
				{
					//skip
					return;
				}			
			}
				
			diarioDao.saveDiarioRif(idPaiNuovoRif, idRelazione);
		}
	}

	@Override
	public CsCDiarioConchi getDiarioConchi(BaseDTO dto) {	
		if(dto.getObj()!=null && (Long)dto.getObj()!=0)
			return diarioDao.getConchiById((Long) dto.getObj());
		else
			return null;						
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
			e.printStackTrace();
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
				BaseDTO bdto = new BaseDTO();
				copiaCsTBaseObject(dto, bdto);
				bdto.setObj(dto.getCasoId());
				
				CsACaso caso = casoSessionBean.findCasoById(bdto); 
				diario.setCsACaso(caso);
				
				CsACasoOpeTipoOpe responsabile = casoDao.findCasoOpeResponsabile(dto.getCasoId());
				diario.setResponsabileCaso(responsabile!=null ? responsabile.getCsOOperatoreTipoOperatore().getCsOOperatoreSettore().getCsOOperatore().getId() : null);
			}
			diario.setSchedaId(csDDiario!=null ? csDDiario.getSchedaId() : null);
			diario.setCsTbTipoDiario(diarioDao.findTipoDiarioById(DataModelCostanti.TipoDiario.DOC_INDIVIDUALE_ID));
			
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
		public List<CsDDocIndividuale> findDocIndividualeByCfSchedaSegnalato(
				BaseDTO dto) { 
			return diarioDao.findDocIndividualeByCfSchedaSegnalato((String) dto.getObj(), (Long) dto.getObj2());
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
	   public List<Long> findDiarioAnaAttProfessionaliCasoIdsByAnagraficaId(BaseDTO bdto) throws Exception {
		   List<Long> toReturn = new ArrayList<Long>();
		   Long idAnagrafica = (Long) bdto.getObj();
		   List<CsACaso> lCasi = diarioDao.findDiarioAnaCasoIdsByAnagraficaId(idAnagrafica,new Long(6)); //6 = ATTIVITA PROFESSIONALI
		   
		   for (CsACaso csACaso : lCasi) {
			   toReturn.add(csACaso.getId());
		   }
		   return toReturn;
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
			CsASoggettoLAZY soggetto = soggettoSessionBean.getSoggettoByCF(in);
			
			CsAAnagrafica ana = soggetto.getCsAAnagrafica();
			dto.setNome(ana.getNome());
			dto.setCognome(ana.getCognome());
			dto.setCf(ana.getCf());
			dto.setCittadinanza(ana.getCittadinanza());
			dto.setIdentificativo(new BigDecimal(soggetto.getCsACaso().getIdentificativo()));
			dto.setCaso(soggetto.getCsACaso());
			
			
			CsADatiSociali ds = schedaSessionBean.findDatiSocialiAttiviBySoggettoCf(in);
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
				
		    	
			CsASoggettoStatoCivile staCiv = soggettoSessionBean.getStatoCivileAttualeBySoggetto(in);
			dto.setStatoCivile(staCiv!=null ? staCiv.getCsTbStatoCivile().getDescrizione() : null);
			
			return dto;
		}

		@Override
		@AuditConsentiAccessoAnonimo
		@AuditSaltaValidazioneSessionID
		public List<CsDPai> findPaiAperti(CeTBaseObject dto) {
			return diarioDao.findPaiAperti();
		}
}

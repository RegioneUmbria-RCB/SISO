package it.webred.cs.csa.ejb.ejb;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.ExcludeDefaultInterceptors;
import javax.interceptor.Interceptors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTablePaiPTISessionBeanRemote;
import it.webred.cs.csa.ejb.dao.PaiPTIDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.ArCsPaiInfoSinteticheDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.ArCsPaiPTIDocumentoDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.CsPaiPTIDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.CsPaiPtiFaseDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.CsPaiPtiRevisioniDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.InserimentoConsuntivazioneDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.RichiestaDisponibilitaPaiPtiDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.StrutturaDisponibilitaDTO;
import it.webred.cs.data.model.VStrutturaArea;
import it.webred.cs.data.model.pti.CsPaiPTI;
import it.webred.cs.data.model.pti.CsPaiPTIFase;
import it.webred.cs.data.model.pti.CsPaiPTIRevisioni;
import it.webred.cs.data.model.pti.CsPaiPtiDocumento;
import it.webred.cs.data.model.pti.InserimentoConsuntivazione;
import it.webred.cs.data.model.pti.InserimentoMinoreDaStruttura;
import it.webred.cs.data.model.pti.RichiestaDisponibilitaPaiPti;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ct.support.validation.ValidationStateless;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

@Stateless
@Interceptors(ValidationStateless.class)
public class AccessTablePaiPTISessionBean extends CarSocialeBaseSessionBean implements AccessTablePaiPTISessionBeanRemote {

	private static final long serialVersionUID = -727841109224649514L;
	
	@Autowired
	private PaiPTIDAO paiPTIDAO;

	@Override
	public CsPaiPTIDTO findPTIByDiarioPaiId(BaseDTO dto) throws Exception {
		Long diarioPaiId = (Long) dto.getObj();
		CsPaiPTI csPaiPTI = paiPTIDAO.findPTIByDiarioPaiId(diarioPaiId);

		return toDTO(csPaiPTI);
	}

	private CsPaiPTIDTO toDTO(CsPaiPTI source) {
		if (source == null) {
			return null;
		}

		CsPaiPTIDTO target = new CsPaiPTIDTO();
		String[] ignore = { "fasiPTI", "richiesteDisponibilita", "documentiPTI" };
		BeanUtils.copyProperties(source, target, ignore);

		if (source.getFasiPTI() != null && !source.getFasiPTI().isEmpty()) {
			target.setFasiPTI(new ArrayList<CsPaiPtiFaseDTO>());
			for (CsPaiPTIFase fase : source.getFasiPTI()) {
				target.getFasiPTI().add(toDTO(fase));
			}
		}
//		if (source.getDocumenti() != null && !source.getDocumenti().isEmpty()) {
//			target.setDocumentiPTI(new ArrayList<CsPaiPtiDocumentoDTO>());
//			for (CsPaiPtiDocumento doc : source.getDocumenti()) {
//				target.getDocumentiPTI().add(toDTO(doc));
//			}
//		}
		
		if (source.getDocumenti() != null && !source.getDocumenti().isEmpty()) {
			target.setDocumentiPTI(new ArrayList<ArCsPaiPTIDocumentoDTO>());
			for (CsPaiPtiDocumento doc : source.getDocumenti()) {
				target.getDocumentiPTI().add(toDTO(doc));
			}
		}

		return target;
	}

	private CsPaiPtiFaseDTO toDTO(CsPaiPTIFase source) {
		if (source == null) {
			return null;
		}

		CsPaiPtiFaseDTO target = new CsPaiPtiFaseDTO();
		String[] ignore = { "paiPTI", "fase" };
		BeanUtils.copyProperties(source, target, ignore);

		return target;
	}

	private ArCsPaiPTIDocumentoDTO toDTO(CsPaiPtiDocumento source) {
		if (source == null) {
			return null;
		}

		ArCsPaiPTIDocumentoDTO target = new ArCsPaiPTIDocumentoDTO();
		String[] ignore = { "paiPTI" };
		BeanUtils.copyProperties(source, target, ignore);
		
		if(source.getArCsPaiInfoSintetiche()!=null) {
		String [] ignoreInfo = {"csPaiPtiDocumento"};
		ArCsPaiInfoSinteticheDTO infoDTO = new ArCsPaiInfoSinteticheDTO();
		BeanUtils.copyProperties(source.getArCsPaiInfoSintetiche(), infoDTO, ignoreInfo);

		target.setArCsPaiInfoSinteticheDTO(infoDTO);
		}
		return target;
	}

	public static CsPaiPTI toEntity(CsPaiPTIDTO source) throws IllegalAccessException, InvocationTargetException {
		CsPaiPTI target = new CsPaiPTI();
		String[] ignore = { "fasiPTI", "richiesteDisponibilita", "documenti" };

		BeanUtils.copyProperties(source, target, ignore);

		if (!source.getFasiPTI().isEmpty()) {
			target.setFasiPTI(new ArrayList<CsPaiPTIFase>());
			for (CsPaiPtiFaseDTO stato : source.getFasiPTI()) {
				target.getFasiPTI().add(toEntity(stato, target));
			}
		}

		if (!source.getRichiesteDisponibilita().isEmpty()) {
			for (RichiestaDisponibilitaPaiPtiDTO richiestaDispo : source.getRichiesteDisponibilita()) {
				target.getRichiesteDisponibilita().add(toEntity(richiestaDispo, target));
			}
		}

		if (!source.getDocumentiPTI().isEmpty()) {
			for (ArCsPaiPTIDocumentoDTO documento : source.getDocumentiPTI()) {
				target.getDocumenti().add(toEntity(documento, target));
			}
		}
		return target;

	}

	@Override
	public CsPaiPTIDTO savePTI(BaseDTO dto) throws Exception {

		CsPaiPTIDTO pti = (CsPaiPTIDTO) dto.getObj();

		// gestione fase
		Long codiceUltimaFase = (Long) dto.getObj2();

		CsPaiPtiFaseDTO faseAttuale = pti.getFaseAttuale();
		Long codiceFaseAttuale = faseAttuale != null ? faseAttuale.getIdStato() : null;
		if (!codiceUltimaFase.equals(codiceFaseAttuale)) {
			for (CsPaiPtiFaseDTO fase : pti.getFasiPTI()) {
				if (fase.getValidaA() == null) {
					fase.setValidaA(new Date());
					break;
				}
			}

			CsPaiPtiFaseDTO nuovaFase = new CsPaiPtiFaseDTO();
			nuovaFase.setPaiPTI(pti);
			nuovaFase.setIdStato(codiceUltimaFase);
			nuovaFase.setValidaDA(new Date());

			pti.getFasiPTI().add(nuovaFase);

		}

		// aggiornamento dati json da salvare su argo
		if (pti.getRichiesteDisponibilita() != null && !pti.getRichiesteDisponibilita().isEmpty()) {
			for (RichiestaDisponibilitaPaiPtiDTO rich : pti.getRichiesteDisponibilita()) {
				rich.createDettaglioPTI(pti);
			}
		}

		CsPaiPTI res = paiPTIDAO.savePTI(toEntity(pti));
		return toDTO(res);

	}
	
	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors
	public CsPaiPTI saveNuovoPTIFromBatch(BaseDTO dto) throws Exception {

		CsPaiPTI pti = (CsPaiPTI) dto.getObj();

		// gestione fase
		Long codiceUltimaFase = (Long) dto.getObj2();

		CsPaiPTIFase nuovaFase = new CsPaiPTIFase();
		nuovaFase.setPaiPTI(pti);
		nuovaFase.setIdStato(codiceUltimaFase);
		nuovaFase.setValidaDA(new Date());

		pti.addFasePTI(nuovaFase);

		return paiPTIDAO.savePTI(pti);

	}

	public static CsPaiPTIFase toEntity(CsPaiPtiFaseDTO source, CsPaiPTI pti) {
		CsPaiPTIFase target = new CsPaiPTIFase();
		String[] ignore = { "paiPTI", "fase" };
		BeanUtils.copyProperties(source, target, ignore);

		target.setPaiPTI(pti);

		return target;
	}

	public static RichiestaDisponibilitaPaiPti toEntity(RichiestaDisponibilitaPaiPtiDTO source, CsPaiPTI pti) {
		RichiestaDisponibilitaPaiPti target = new RichiestaDisponibilitaPaiPti();
		String[] ignore = { "strutturaArea" };
		BeanUtils.copyProperties(source, target, ignore);

		target.setPaiPTI(pti);

		return target;
	}

	public static CsPaiPtiDocumento toEntity(ArCsPaiPTIDocumentoDTO source, CsPaiPTI pti) {
		CsPaiPtiDocumento target = new CsPaiPtiDocumento();
		String[] ignore = { "paiPTI" };
		BeanUtils.copyProperties(source, target, ignore);
		target.setPaiPTI(pti);

		return target;
	}

	@Override
	public List<StrutturaDisponibilitaDTO> findElencoStrutture(BaseDTO dto) throws Exception {
		Long tipoStruttura = (Long)dto.getObj();
		Integer etaBeneficiario = (Integer)dto.getObj2();
		List<StrutturaDisponibilitaDTO> elencoStrutturedisponibilita = new ArrayList<StrutturaDisponibilitaDTO>();
		List<VStrutturaArea> elencoStrutture = paiPTIDAO.findElencoStrutture(tipoStruttura, etaBeneficiario);
		for (VStrutturaArea struttura : elencoStrutture) {
			StrutturaDisponibilitaDTO strDispDto =  toDTO(struttura);
			elencoStrutturedisponibilita.add(strDispDto);
		}
		return elencoStrutturedisponibilita;

	}

	private StrutturaDisponibilitaDTO toDTO(VStrutturaArea source)
			throws IllegalAccessException, InvocationTargetException {
		if (source == null) {
			return null;
		}

		StrutturaDisponibilitaDTO target = new StrutturaDisponibilitaDTO();
		String[] ignore = { "richiesteDisponibilita" };

		BeanUtils.copyProperties(source, target, ignore);

//		if(!source.getRichiesteDisponibilita().isEmpty() && source.getRichiesteDisponibilita().get(0).getId()!=null) {
//			for(RichiestaDisponibilitaPaiPti richiesta : source.getRichiesteDisponibilita()) {
//				target.getRichiesteDisponibilita().add(toDTO(richiesta));
//			}
//			
//		}
//	

		return target;
	}

	public static RichiestaDisponibilitaPaiPtiDTO toDTO(RichiestaDisponibilitaPaiPti source)
			throws IllegalAccessException, InvocationTargetException {
		RichiestaDisponibilitaPaiPtiDTO target = new RichiestaDisponibilitaPaiPtiDTO();
		BeanUtils.copyProperties(source, target);
		return target;
	}

	@Override
	public List<StrutturaDisponibilitaDTO> findElencoStruttureDisponibilita(BaseDTO dto) throws Exception {
		List<StrutturaDisponibilitaDTO> elencoStruttDisp = new ArrayList<StrutturaDisponibilitaDTO>();
		Long idPTI = (Long) dto.getObj();
		elencoStruttDisp = paiPTIDAO.findElencoStruttureDisponibilita(idPTI);
		return elencoStruttDisp;
	}

	@Override
	public RichiestaDisponibilitaPaiPtiDTO findDisponibilitaById(BaseDTO dto) throws Exception {
		RichiestaDisponibilitaPaiPti richiestaDisp = new RichiestaDisponibilitaPaiPti();
		Long idRichiestaDisponibilita = (Long) dto.getObj();
		richiestaDisp = paiPTIDAO.findDisponibilitaById(idRichiestaDisponibilita);

		return toDTO(richiestaDisp);

	}

	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors
	public List<InserimentoMinoreDaStruttura> findInsMinoriInStruttura(CeTBaseObject cet) throws Exception {
		return paiPTIDAO.findInsMinoriDaStruttura();
	}

	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors
	public InserimentoMinoreDaStruttura saveInsMinoreStruttura(BaseDTO dto) throws Exception {
		return paiPTIDAO.save((InserimentoMinoreDaStruttura) dto.getObj());
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors
	public CsPaiPTI findPTIByDiarioPaiIdFromBatch(BaseDTO dto) throws Exception {
		Long diarioPaiId = (Long) dto.getObj();
		return paiPTIDAO.findPTIByDiarioPaiId(diarioPaiId);
	}

	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors
	public CsPaiPTI savePTIFromBatch(BaseDTO dto) throws Exception {
		CsPaiPTI pti = (CsPaiPTI) dto.getObj();
		return paiPTIDAO.savePTI(pti);
	}

	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors
	public CsPaiPtiDocumento saveDocumentoFromBatch(BaseDTO dto) throws Exception {
		CsPaiPtiDocumento doc = (CsPaiPtiDocumento) dto.getObj();
		return paiPTIDAO.inserisciDocumento(doc);
	}

	@Override
	public List<InserimentoConsuntivazioneDTO> findConsuntivazioneDaStruttura(BaseDTO dto) throws Exception {
		List<InserimentoConsuntivazione> consuntivazioni = new ArrayList<InserimentoConsuntivazione>();
		List<InserimentoConsuntivazioneDTO> consuntivazioniDTO = new ArrayList<InserimentoConsuntivazioneDTO>();
		consuntivazioni = paiPTIDAO.findConsuntivazioneDaStruttura((Long) dto.getObj(), dto.getObj2().toString());
		for (InserimentoConsuntivazione cons : consuntivazioni) {

			consuntivazioniDTO.add(toDTO(cons));
		}
		return consuntivazioniDTO;
	}

	@Override
	public List<InserimentoConsuntivazioneDTO> findConsuntivazioneErogate(BaseDTO dto) throws Exception {
		List<InserimentoConsuntivazione> consuntivazioni = new ArrayList<InserimentoConsuntivazione>();
		List<InserimentoConsuntivazioneDTO> consuntivazioniDTO = new ArrayList<InserimentoConsuntivazioneDTO>();
		consuntivazioni = paiPTIDAO.findConsuntivazioneErogate((Long) dto.getObj(), dto.getObj2().toString());
		for (InserimentoConsuntivazione cons : consuntivazioni) {

			consuntivazioniDTO.add(toDTO(cons));
		}
		return consuntivazioniDTO;
	}

	
	public static InserimentoConsuntivazioneDTO toDTO(InserimentoConsuntivazione source)
			throws IllegalAccessException, InvocationTargetException {
		InserimentoConsuntivazioneDTO target = new InserimentoConsuntivazioneDTO();
		BeanUtils.copyProperties(source, target);
		return target;
	}

	
	
	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors
	public void salvaConsuntivazione(InserimentoConsuntivazioneDTO consuntivazione) throws Exception {
		try {
			InserimentoConsuntivazione res = paiPTIDAO.saveConsuntivazione(consuntivazione) ;
				
		}
		catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public CsPaiPtiRevisioniDTO savePtiRevisione(BaseDTO dto) throws Exception {
		CsPaiPtiRevisioniDTO ptiRevisione = (CsPaiPtiRevisioniDTO) dto.getObj();

		CsPaiPTIRevisioni res = paiPTIDAO.savePTIRevisione(toEntity(ptiRevisione));
		return toDTO(res);

	}
	
	public static CsPaiPTIRevisioni toEntity(CsPaiPtiRevisioniDTO source) throws IllegalAccessException, InvocationTargetException {
		CsPaiPTIRevisioni target = new CsPaiPTIRevisioni();

		BeanUtils.copyProperties(source, target);

		return target;
	}
	
	private CsPaiPtiRevisioniDTO toDTO(CsPaiPTIRevisioni source) {
		if (source == null) {
			return null;
		}

		CsPaiPtiRevisioniDTO target = new CsPaiPtiRevisioniDTO();
		BeanUtils.copyProperties(source, target);

		return target;
	}

	@Override
	public List<CsPaiPtiRevisioniDTO> findRevisioniPTI(BaseDTO dto) throws Exception {
		List<CsPaiPTIRevisioni> lstRevisioni = new ArrayList<CsPaiPTIRevisioni>();
		List<CsPaiPtiRevisioniDTO> lstRevisioniDTO = new ArrayList<CsPaiPtiRevisioniDTO>();
		Long idPaiPTI = (Long) dto.getObj();
		lstRevisioni = paiPTIDAO.findRevisioni(idPaiPTI);
		for (CsPaiPTIRevisioni revisione : lstRevisioni) {

			lstRevisioniDTO.add(toDTO(revisione));
		}

		return lstRevisioniDTO;
	}

	@Override
	public List<ArCsPaiPTIDocumentoDTO> findDocumentiRichiestaSelezionata(BaseDTO dto) {
		return paiPTIDAO.findDocumentiRichiestaSelezionata(dto);
	}

	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors
	public void saveDocumento(BaseDTO dto) throws IllegalAccessException, InvocationTargetException, Exception {
		// TODO Auto-generated method stub
		ArCsPaiPTIDocumentoDTO doc = (ArCsPaiPTIDocumentoDTO) dto.getObj();
		paiPTIDAO.saveDocumento(toEntity(doc));
	
	}

	public static CsPaiPtiDocumento toEntity(ArCsPaiPTIDocumentoDTO source) throws IllegalAccessException, InvocationTargetException {
		if (source == null) {
			return null;
		}
		CsPaiPtiDocumento target = new CsPaiPtiDocumento();

		BeanUtils.copyProperties(source, target);

		return target;
	}

	@Override
	public List<RichiestaDisponibilitaPaiPtiDTO> findProgettiAltriEnti(BaseDTO dto) throws Exception {
		String codRouting = (String) dto.getObj();
		List<RichiestaDisponibilitaPaiPtiDTO>  lstRichieste = paiPTIDAO.findProgettiAltriEnti(codRouting);
		
	return lstRichieste;	
	}

	@Override
	public StrutturaDisponibilitaDTO findStrutturaById(BaseDTO dto) throws Exception {
		VStrutturaArea struttura = paiPTIDAO.findStrutturaById((Long)dto.getObj());

		return toDTO(struttura);
	}


	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors
	public List<ArCsPaiPTIDocumentoDTO> findDocumentiDaProcessare(BaseDTO dto) throws Exception {
		List<ArCsPaiPTIDocumentoDTO> lstDocumentiDTO = new ArrayList<ArCsPaiPTIDocumentoDTO>();
		String enteId = dto.getEnteId();
		List<CsPaiPtiDocumento> lstDocumenti = paiPTIDAO.findDocumentiDaProcessare(enteId);
		
		
		for (CsPaiPtiDocumento doc : lstDocumenti) {

			lstDocumentiDTO.add(toDTO(doc));
		}
		return lstDocumentiDTO;
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors
	public CsPaiPTI findPTIById(BaseDTO dto) throws Exception {
		CsPaiPTI paiPTI = paiPTIDAO.findPTIById((Long)dto.getObj());
	
		return paiPTI;
	}

	@Override
	@AuditConsentiAccessoAnonimo
    @AuditSaltaValidazioneSessionID
    @ExcludeDefaultInterceptors
	public List<InserimentoConsuntivazioneDTO> findConsuntiDaProcessare(BaseDTO dto) throws Exception {
		List<InserimentoConsuntivazioneDTO> lstConsuntivoDTO = new ArrayList<InserimentoConsuntivazioneDTO>();
		String enteId = dto.getEnteId();
		List<InserimentoConsuntivazione> lstConsuntivi = paiPTIDAO.findConsuntiviDaProcessare(enteId);
		
		if (lstConsuntivi != null) {
			for (InserimentoConsuntivazione doc : lstConsuntivi) {

				lstConsuntivoDTO.add(toDTO(doc));
			}
		}
		return lstConsuntivoDTO;
	}
	

}

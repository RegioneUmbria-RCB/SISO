package it.webred.ct.data.access.basic.versamenti.iciDM;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.versamenti.iciDM.dao.VersIciDmDAO;
import it.webred.ct.data.access.basic.versamenti.iciDM.dto.AnagSoggIciDmDTO;
import it.webred.ct.data.access.basic.versamenti.iciDM.dto.IciDmDataIn;
import it.webred.ct.data.access.basic.versamenti.iciDM.dto.VersamentoIciDmDTO;
import it.webred.ct.data.access.basic.versamenti.iciDM.dto.ViolazioneIciDmDTO;
import it.webred.ct.data.model.versamenti.iciDM.SitTIciDmAnag;
import it.webred.ct.data.model.versamenti.iciDM.SitTIciDmVers;
import it.webred.ct.data.model.versamenti.iciDM.SitTIciDmViolazione;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public   class VersIciDmServiceBean extends CTServiceBaseBean implements VersIciDmService {

	@Autowired
	private VersIciDmDAO icidmDAO;

	@Override
	public List<VersamentoIciDmDTO> getListaVersamentiByCodFis(IciDmDataIn dataIn)
			throws VersIciDmServiceException {
		
		List<VersamentoIciDmDTO> lstDto = new ArrayList<VersamentoIciDmDTO>();
		List<SitTIciDmVers> vers = icidmDAO.getListaVersamentiByCodFis(dataIn.getCf());
		for(SitTIciDmVers v : vers){
			VersamentoIciDmDTO dto = this.fillVersamento(v);
			lstDto.add(dto);
		}
		return lstDto;
		
	}
	
	@Override
	public List<VersamentoIciDmDTO> getListaVersamentiByCodFisAnno(IciDmDataIn dataIn)
			throws VersIciDmServiceException {
		
		List<VersamentoIciDmDTO> lstDto = new ArrayList<VersamentoIciDmDTO>();
		List<SitTIciDmVers> vers = icidmDAO.getListaVersamentiByCodFisAnno(dataIn.getCf(), dataIn.getAnno());
		for(SitTIciDmVers v : vers){
			VersamentoIciDmDTO dto = this.fillVersamento(v);
			lstDto.add(dto);
		}
		return lstDto;
		
	}

	@Override
	public List<ViolazioneIciDmDTO> getListaViolazioniByCodFis(IciDmDataIn dataIn)
			throws VersIciDmServiceException {
		
		List<ViolazioneIciDmDTO> lstDto = new ArrayList<ViolazioneIciDmDTO>();
		List<SitTIciDmViolazione> vers = icidmDAO.getListaViolazioniByCodFis(dataIn.getCf());
		for(SitTIciDmViolazione v : vers){
			ViolazioneIciDmDTO dto = this.fillViolazione(v);
			lstDto.add(dto);
		}
		return lstDto;
		
	}
	
	private AnagSoggIciDmDTO getSoggettoByIdExt(String idExt){
		
		AnagSoggIciDmDTO dto = null;
		
		SitTIciDmAnag an = icidmDAO.getSoggByIdExt(idExt);
		if(an!=null){
			dto = new AnagSoggIciDmDTO();
			dto.setCognome(an.getCognome());
			dto.setNome(an.getNome());
			dto.setDenominazione(an.getDenominazione());
			dto.setComDomicilio(an.getComDomicilio());
		}
		
		return dto;

	}
	

	private String getDescrizione(String col, String val){
		
		String des = null;
		
		if(val!=null){
			des = icidmDAO.getDescrizioneByCodValue(col, val);
			if(des==null && "0".equals(val))
				des="-";
		}
		return des;
		
	}
	
	private ViolazioneIciDmDTO fillViolazione(SitTIciDmViolazione v){
		
		ViolazioneIciDmDTO dto = new ViolazioneIciDmDTO();
		
		dto.setId(v.getId());
		dto.setCodConcessione(v.getCodConcessione());
		dto.setCodEnte(v.getCodEnte());
		dto.setNumQuietanza(v.getNumQuietanza());
		dto.setDtVersamento(v.getDtVersamento());
		dto.setCfVersante(v.getCfVersante());
		dto.setNumQuietanzaRif(v.getNumQuietanzaRif());
		dto.setImpVersato(v.getImpVersato());
		
		dto.setImpVersato(v.getImpVersato());
		dto.setImpSoprattassa(v.getSoprattassa());
		dto.setImpPenaPecuniaria(v.getPenaPec());
		dto.setImpInteressi(v.getInteressi());
		
		dto.setFlgQuadratura(v.getFlgQuadratura());
		dto.setDesQuadratura(getDescrizione("FLG_QUADRATURA", v.getFlgQuadratura()));
		
		dto.setFlgReperibilita(v.getFlgReperibilita());
		dto.setDesReperibilita(getDescrizione("FLG_REPERIBILITA", v.getFlgReperibilita()));
		
		dto.setCodTipoVersamento(v.getTipoVersamento());
		dto.setDesTipoVersamento(getDescrizione("TIPO_VERSAMENTO", v.getTipoVersamento()));

		dto.setDtRegistrazione(v.getDtRegistrazione());
		
		dto.setFlgCompetenza(v.getFlgCompetenza());
		dto.setDesCompetenza(getDescrizione("FLG_COMPETENZA", v.getFlgCompetenza()));
		
		dto.setComuneImm(v.getComune());
		dto.setCapImm(v.getCap());
		
		dto.setFlgIdentificazione(v.getFlgIdentificazione());
		dto.setDesIdentificazione(getDescrizione("FLG_IDENTIFICAZIONE", v.getFlgIdentificazione()));
		
		dto.setCodTipoImposta(v.getTipoImposta());
		dto.setDesTipoImposta(getDescrizione("TIPO_IMPOSTA", v.getTipoImposta()));

		dto.setNumProvLiq(v.getNumProvLiq());
		dto.setDtProvLiq(v.getDtProvLiq());
		
		dto.setDatiSogg(this.getSoggettoByIdExt(v.getIdExtAn()));
		
		return dto;
	}
	
	private VersamentoIciDmDTO fillVersamento(SitTIciDmVers v){
		VersamentoIciDmDTO dto = new VersamentoIciDmDTO();
		
		dto.setId(v.getId());
		dto.setCodConcessione(v.getCodConcessione());
		dto.setCodEnte(v.getCodEnte());
		dto.setNumQuietanza(v.getNumQuietanza());
		dto.setDtVersamento(v.getDtVersamento());
		dto.setCfVersante(v.getCfVersante());
		dto.setNumQuietanzaRif(v.getNumQuietanzaRif());
		dto.setImpVersato(v.getImpVersato());
		
		dto.setImpVersato(v.getImpVersato());
		dto.setImpTerrAgricoli(v.getImpTerr());
		dto.setImpAreeFabbricabili(v.getImpAreeFab());
		dto.setImpAbitazPrincipale(v.getImpAbPr());
		dto.setImpAltriFabbricati(v.getImpFabb());
		dto.setImpDetrazione(v.getImpDetrazione());
		
		dto.setFlgQuadratura(v.getFlgQuadratura());
		dto.setDesQuadratura(getDescrizione("FLG_QUADRATURA", v.getFlgQuadratura()));
		
		dto.setFlgReperibilita(v.getFlgReperibilita());
		dto.setDesReperibilita(getDescrizione("FLG_REPERIBILITA", v.getFlgReperibilita()));
		
		dto.setCodTipoVersamento(v.getTipoVersamento());
		dto.setDesTipoVersamento(getDescrizione("TIPO_VERSAMENTO", v.getTipoVersamento()));

		dto.setDtRegistrazione(v.getDtRegistrazione());
		
		dto.setFlgCompetenza(v.getFlgCompetenza());
		dto.setDesCompetenza(getDescrizione("FLG_COMPETENZA", v.getFlgCompetenza()));
		
		dto.setComuneImm(v.getComune());
		dto.setCapImm(v.getCap());
		dto.setNumFabb(v.getNumFabb());
		
		dto.setFlgAccSaldo(v.getFlgAccSaldo());
		dto.setDesAccSaldo(getDescrizione("FLG_ACC_SALDO", v.getFlgAccSaldo()));
		
		dto.setFlgIdentificazione(v.getFlgIdentificazione());
		dto.setDesIdentificazione(getDescrizione("FLG_IDENTIFICAZIONE", v.getFlgIdentificazione()));
		
		dto.setAnnoImposta(v.getAnnoImposta());
		
		dto.setFlgRavvedimento(v.getFlgRavvedimento());
		dto.setDesRavvedimento(getDescrizione("FLG_RAVVEDIMENTO", v.getFlgRavvedimento()));

		dto.setDatiSogg(this.getSoggettoByIdExt(v.getIdExtAn()));
		
		return dto;
	}

	@Override
	public VersamentoIciDmDTO getVersamentoById(IciDmDataIn dataIn)
			throws VersIciDmServiceException {
		VersamentoIciDmDTO dto = null;
		SitTIciDmVers v = icidmDAO.getVersamentoById(dataIn.getId());
		if(v!=null)
			dto = this.fillVersamento(v);
		return dto;
	}

	@Override
	public ViolazioneIciDmDTO getViolazioneById(IciDmDataIn dataIn)
			throws VersIciDmServiceException {
		ViolazioneIciDmDTO dto = null;
		SitTIciDmViolazione v = icidmDAO.getViolazioneById(dataIn.getId());
		if(v!=null)
			dto = this.fillViolazione(v);
		return dto;
	}
	


}

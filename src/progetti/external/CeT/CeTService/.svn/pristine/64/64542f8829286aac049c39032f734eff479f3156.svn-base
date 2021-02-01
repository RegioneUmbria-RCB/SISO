package it.webred.ct.data.access.basic.f24;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.f24.dao.F24DAO;
import it.webred.ct.data.access.basic.f24.dto.*;
import it.webred.ct.data.model.f24.SitTF24Annullamento;
import it.webred.ct.data.model.f24.SitTF24Versamenti;





import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless 
public class F24ServiceBean extends CTServiceBaseBean implements F24Service {
	@Autowired
	private F24DAO f24DAO;
	private static final long serialVersionUID = 1L;
	
	@Override
	public F24VersamentoDTO getVersamentoById(RicercaF24DTO search) {
		
		SitTF24Versamenti jpa = f24DAO.getVersamentoById(search.getId());
		
		HashMap<String,String> mappaCodiciTributo = new HashMap<String,String>();
		HashMap<String,String> mappaCodiciSogg = new HashMap<String,String>();
		
		F24VersamentoDTO dto = this.getVersamentoDTO(jpa,mappaCodiciTributo,mappaCodiciSogg);
		
		return dto;
	}
	
	@Override
	public F24AnnullamentoDTO getAnnullamentoById(RicercaF24DTO search) {
		
		SitTF24Annullamento jpa = f24DAO.getAnnullamentoById(search.getId());
		
		HashMap<String,String> mappaCodiciTributo = new HashMap<String,String>();
		
		F24AnnullamentoDTO dto = this.getAnnullamentoDTO(jpa, mappaCodiciTributo);
		
		return dto;
	}
	
	@Override
	public List<TipoTributoDTO> getListaDescTipoTributo(RicercaF24DTO search) {
		
		return f24DAO.getListaTipoTributo(false,false);
	}
	
	@Override
	public List<TipoTributoDTO> getListaCodTipoTributo(RicercaF24DTO search) {
		
		return f24DAO.getListaTipoTributo(false,true);
	}
	
	@Override
	public List<TipoTributoDTO> getListaCodAnnoTipoTributo(RicercaF24DTO search) {
		
		return f24DAO.getListaTipoTributo(true,true);
	}
	
	@Override
	public List<F24AnnullamentoDTO> getListaAnnullamentiByCF(RicercaF24DTO search) {
		
		List<F24AnnullamentoDTO> result = new ArrayList<F24AnnullamentoDTO>();
		
		String cf = search.getCf();
		List<SitTF24Annullamento> ann = f24DAO.getListaAnnullamentiByCF(cf);
		HashMap<String,String> mappaCodiciTributo = new HashMap<String,String>();
		for(SitTF24Annullamento jpa: ann){
			F24AnnullamentoDTO dto = this.getAnnullamentoDTO(jpa, mappaCodiciTributo);
			result.add(dto);
		}
		
		return result;
	}
	
	
	@Override
	public List<F24VersamentoDTO> getListaVersamentiByCF(RicercaF24DTO search) {
		
		List<F24VersamentoDTO> result = new ArrayList<F24VersamentoDTO>();
		
		String cf = search.getCf();
		List<SitTF24Versamenti> vers = f24DAO.getListaVersamentiByCF(cf);
		HashMap<String,String> mappaCodiciTributo = new HashMap<String,String>();
		HashMap<String,String> mappaCodiciSogg = new HashMap<String,String>();
		for(SitTF24Versamenti jpa: vers){
			F24VersamentoDTO dto = this.getVersamentoDTO(jpa, mappaCodiciTributo, mappaCodiciSogg);
			result.add(dto);
		}
		
		return result;
	}
	
	@Override
	public List<F24VersamentoDTO> getListaVersamentiByCFOrderByTipoAnno(RicercaF24DTO search) {
		
		List<F24VersamentoDTO> result = new ArrayList<F24VersamentoDTO>();
		
		String cf = search.getCf();
		List<SitTF24Versamenti> vers = f24DAO.getListaVersamentiByCFOrderByTipoAnno(cf);
		HashMap<String,String> mappaCodiciTributo = new HashMap<String,String>();
		HashMap<String,String> mappaCodiciSogg = new HashMap<String,String>();
		for(SitTF24Versamenti jpa: vers){
			F24VersamentoDTO dto = this.getVersamentoDTO(jpa, mappaCodiciTributo, mappaCodiciSogg);
			result.add(dto);
		}
		
		return result;
	}

	@Override
	public List<F24AnnullamentoDTO> getListaAnnullamentiByVer(RicercaF24DTO search) {
		List<F24AnnullamentoDTO> result = new ArrayList<F24AnnullamentoDTO>();
		
		List<SitTF24Annullamento> ann = f24DAO.getListaAnnullamentiByVer(search);
		HashMap<String,String> mappaCodiciTributo = new HashMap<String,String>();
		HashMap<String,String> mappaCodiciSogg = new HashMap<String,String>();
		for(SitTF24Annullamento jpa: ann){
			F24AnnullamentoDTO dto = this.getAnnullamentoDTO(jpa, mappaCodiciTributo);
			result.add(dto);
		}
		
		return result;
	}

	@Override
	public List<F24VersamentoDTO> getListaVersamentiByAnn(RicercaF24DTO search) {
		List<F24VersamentoDTO> result = new ArrayList<F24VersamentoDTO>();
		
		List<SitTF24Versamenti> vers = f24DAO.getListaVersamentiByAnn(search);
		HashMap<String,String> mappaCodiciTributo = new HashMap<String,String>();
		HashMap<String,String> mappaCodiciSogg = new HashMap<String,String>();
		for(SitTF24Versamenti jpa: vers){
			F24VersamentoDTO dto = this.getVersamentoDTO(jpa, mappaCodiciTributo, mappaCodiciSogg);
			result.add(dto);
		}
		
		return result;
	}
	
	
	private F24VersamentoDTO getVersamentoDTO(SitTF24Versamenti jpa, HashMap<String,String> mappaCodiciTributo, HashMap<String,String> mappaCodiciSogg){
		
		F24VersamentoDTO dto = new F24VersamentoDTO();
		
		dto.setVersamento(jpa);
		
		String tipoTributo = mappaCodiciTributo.get(jpa.getCodTributo());
		if(tipoTributo==null){
			tipoTributo = f24DAO.getDescTributoByCodice(jpa.getCodTributo());
			mappaCodiciTributo.put(jpa.getCodTributo(), tipoTributo);
		}
		
		String tipoSoggetto = mappaCodiciSogg.get(jpa.getCodIdCf2());
		if(tipoSoggetto==null){
			tipoSoggetto =  f24DAO.getDescSoggByCodice(jpa.getCodIdCf2());
			mappaCodiciSogg.put(jpa.getCodIdCf2(), tipoSoggetto);
		}
		
		dto.setDescTipoTributo(tipoTributo);
		dto.setDescTipoCf2(tipoSoggetto);
		
		return dto;
	}
	
	private F24AnnullamentoDTO getAnnullamentoDTO(SitTF24Annullamento jpa, HashMap<String,String> mappaCodiciTributo){
		F24AnnullamentoDTO dto = new F24AnnullamentoDTO();
		
		dto.setAnnullamento(jpa);
		
		String tipoTributo = mappaCodiciTributo.get(jpa.getCodTributo());
		if(tipoTributo==null){
			tipoTributo = f24DAO.getDescTributoByCodice(jpa.getCodTributo());
			mappaCodiciTributo.put(jpa.getCodTributo(), tipoTributo);
		}
		
		dto.setDescTipoTributo(tipoTributo);
		
		return dto;
	}
	
	public String getDescTipoTributoByCod(RicercaF24DTO search){
		
		return f24DAO.getDescTributoByCodice(search.getCodTributo());
		
	}
	
	@Override
	public String getDtAggVersamenti(RicercaF24DTO search) {
		Date dtAgg = f24DAO.getDtAggVersamenti(search);
		return dtAgg == null ? "-" : new SimpleDateFormat("dd/MM/yyyy").format(dtAgg);
	}
	
}

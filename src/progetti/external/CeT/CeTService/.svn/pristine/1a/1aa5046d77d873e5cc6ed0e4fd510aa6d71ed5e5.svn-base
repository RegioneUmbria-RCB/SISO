package it.webred.ct.data.access.basic.f24;

import java.util.List;

import it.webred.ct.data.access.basic.f24.dto.*;

import javax.ejb.Remote;

@Remote
public interface F24Service {
	
	public F24VersamentoDTO getVersamentoById(RicercaF24DTO rs);
	
	public F24AnnullamentoDTO getAnnullamentoById(RicercaF24DTO rs);

	public List<TipoTributoDTO> getListaDescTipoTributo(RicercaF24DTO search);

	public List<TipoTributoDTO> getListaCodAnnoTipoTributo(RicercaF24DTO search);

	public List<TipoTributoDTO> getListaCodTipoTributo(RicercaF24DTO search);

	public List<F24VersamentoDTO> getListaVersamentiByCF(RicercaF24DTO search);
	
	public List<F24VersamentoDTO> getListaVersamentiByCFOrderByTipoAnno(RicercaF24DTO search);

	public List<F24AnnullamentoDTO> getListaAnnullamentiByVer(RicercaF24DTO search);

	public List<F24VersamentoDTO> getListaVersamentiByAnn(RicercaF24DTO search);
	
	public String getDescTipoTributoByCod(RicercaF24DTO search);

	public List<F24AnnullamentoDTO> getListaAnnullamentiByCF(RicercaF24DTO rs);
	
	public String getDtAggVersamenti(RicercaF24DTO search);
	
}

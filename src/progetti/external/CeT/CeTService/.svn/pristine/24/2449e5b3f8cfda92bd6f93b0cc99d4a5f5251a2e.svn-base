package it.webred.ct.data.access.basic.f24.dao;

import it.webred.ct.data.access.basic.f24.dto.RicercaF24DTO;
import it.webred.ct.data.access.basic.f24.dto.TipoTributoDTO;
import it.webred.ct.data.model.f24.SitTF24Annullamento;
import it.webred.ct.data.model.f24.SitTF24Versamenti;

import java.util.Date;
import java.util.List;

public interface F24DAO {

	public SitTF24Versamenti getVersamentoById(String id);
	
	public String getDescTributoByCodice(String codTributo);
	
	public String getDescSoggByCodice(String codSogg);
	
	public List<SitTF24Versamenti> getListaVersamentiByCF(String cf);
	
	public List<SitTF24Versamenti> getListaVersamentiByCFOrderByTipoAnno(String cf);
	
	public List<TipoTributoDTO> getListaTipoTributo(boolean anno, boolean codice);

	public SitTF24Annullamento getAnnullamentoById(String id);

	public List<SitTF24Versamenti> getListaVersamentiByAnn(RicercaF24DTO search);

	public List<SitTF24Annullamento> getListaAnnullamentiByVer(RicercaF24DTO search);

	public List<SitTF24Annullamento> getListaAnnullamentiByCF(String cf);
	
	public Date getDtAggVersamenti(RicercaF24DTO search);

}

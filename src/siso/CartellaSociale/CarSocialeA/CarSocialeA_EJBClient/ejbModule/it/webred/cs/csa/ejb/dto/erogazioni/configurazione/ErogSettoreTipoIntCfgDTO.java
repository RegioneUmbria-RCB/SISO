package it.webred.cs.csa.ejb.dto.erogazioni.configurazione;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ErogSettoreTipoIntCfgDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long settoreId;
	private Long tipoInterventoId;
	
	//Lista di stati caricati in base ai permessi associati all'operatore sul settore (erogativo/autorizzativo)
	private List<ErogStatoCfgDTO> lstStati;
	
	public ErogSettoreTipoIntCfgDTO(){}

	public ErogSettoreTipoIntCfgDTO(Long tipoIntervento, Long settore){
		this.tipoInterventoId = tipoIntervento;
		this.settoreId = settore;
	}
	
	public Long getTipoInterventoId() {
		return tipoInterventoId;
	}

	public List<ErogStatoCfgDTO> getLstStati() {
		return lstStati;
	}

	public void setTipoInterventoId(Long tipoInterventoId) {
		this.tipoInterventoId = tipoInterventoId;
	}

	public void setLstStati(List<ErogStatoCfgDTO> lstStati) {
		this.lstStati = lstStati;
	}

	public Long getSettoreId() {
		return settoreId;
	}

	public void setSettoreId(Long settoreId) {
		this.settoreId = settoreId;
	}
	
	@JsonIgnore
	public String getId(){
		return settoreId+"-@-"+tipoInterventoId;
	}
}

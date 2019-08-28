package it.webred.cs.json.serviziorichiestocustom;

import java.util.ArrayList;
import java.util.List;

import it.webred.cs.json.dto.JsonBaseBean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ServizioRichiestoCustomBaseBean extends JsonBaseBean { 
	private String tipoInterventoCustom;
	private int tipoInterventoCustomId;
	private boolean erogabileInAccoglienza;

	private List<ItemConsuntivoServizioRichiestoCustom> consuntivoList = new ArrayList<ItemConsuntivoServizioRichiestoCustom>();
	
	public String getTipoInterventoCustom() {
		return tipoInterventoCustom;
	}

	public void setTipoInterventoCustom(String tipoInterventoCustom) {
		this.tipoInterventoCustom = tipoInterventoCustom;
	}

	public int getTipoInterventoCustomId() {
		return tipoInterventoCustomId;
	}

	public void setTipoInterventoCustomId(int tipoInterventoCustomId) {
		this.tipoInterventoCustomId = tipoInterventoCustomId;
	}

	public boolean isErogabileInAccoglienza() {
		return erogabileInAccoglienza;
	}

	public void setErogabileInAccoglienza(boolean erogabileInAccoglienza) {
		this.erogabileInAccoglienza = erogabileInAccoglienza;
	}

	public List<ItemConsuntivoServizioRichiestoCustom> getConsuntivoList() {
		return consuntivoList;
	}

	public void setConsuntivoList(
			List<ItemConsuntivoServizioRichiestoCustom> consuntivoList) {
		this.consuntivoList = consuntivoList;
	}

}

package it.webred.cs.jsf.bean;

import java.io.Serializable;

import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.amprofiler.model.AmUserUfficio;

public class DatiOperatore extends AmAnagrafica implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idOperatore;
	private AmUserUfficio ufficio;
	private boolean abilitato;
	
	public Long getIdOperatore() {
		return idOperatore;
	}

	public void setIdOperatore(Long idOperatore) {
		this.idOperatore = idOperatore;
	}

	public AmUserUfficio getUfficio() {
		return ufficio;
	}
	
	public void setUfficio(AmUserUfficio ufficio) {
		this.ufficio = ufficio;
	}
	
	public boolean isAbilitato() {
		return abilitato;
	}
	
	public void setAbilitato(boolean abilitato) {
		this.abilitato = abilitato;
	}
	
	public String getComuneProvNascita() {
		return getComuneProvincia(getComuneNascita(), getProvinciaNascita());
	}
	
	public String getComuneProvResidenza() {
		return getComuneProvincia(getComuneResidenza(), getProvinciaResidenza());
	}
	
	protected String getComuneProvincia(String comune, String provincia) {
		if(comune == null || "".equals(comune)) {
			return null;
		}
		String comuneProv = comune.trim();
		if (provincia != null && !"".equals(provincia.trim())) {
			comuneProv += (" (" + provincia.trim() + ")");
		}
		return comuneProv;
	}
	
	public String getIndirizzoResidenza() {
		return getIndirizzo(getViaResidenza(), getCivicoResidenza());
	}
	
	protected String getIndirizzo(String via, String civico) {
		if(via == null || "".equals(via)) {
			return null;
		}
		String indirizzo = via.trim();
		if (civico != null && !"".equals(civico.trim())) {
			while(civico.startsWith("0")) {
				civico = civico.substring(1);
			}
			indirizzo += (", " + civico);
		}
		return indirizzo;
	}
	
	public static DatiOperatore copyFromAmAnagrafica(AmAnagrafica fromObj) {
		if (fromObj == null) return null;
		DatiOperatore obj = new DatiOperatore();
		obj.setId(fromObj.getId());
		obj.setAmUser(fromObj.getAmUser());
		obj.setCodiceFiscale(fromObj.getCodiceFiscale());
		obj.setCognome(fromObj.getCognome());
		obj.setNome(fromObj.getNome());
		obj.setDataNascita(fromObj.getDataNascita());
		obj.setComuneNascita(fromObj.getComuneNascita());
		obj.setProvinciaNascita(fromObj.getProvinciaNascita());
		obj.setViaResidenza(fromObj.getViaResidenza());
		obj.setCivicoResidenza(fromObj.getCivicoResidenza());
		obj.setComuneResidenza(fromObj.getComuneResidenza());
		obj.setProvinciaResidenza(fromObj.getProvinciaResidenza());
		obj.setTelefonoResidenza(fromObj.getTelefonoResidenza());
		obj.setCellulareResidenza(fromObj.getCellulareResidenza());
		return obj;
	}
	
}

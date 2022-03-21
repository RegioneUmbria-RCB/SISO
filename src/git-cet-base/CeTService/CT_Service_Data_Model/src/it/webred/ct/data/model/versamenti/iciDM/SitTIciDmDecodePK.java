package it.webred.ct.data.model.versamenti.iciDM;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class SitTIciDmDecodePK implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private String campo;

	private String valore;

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	
}
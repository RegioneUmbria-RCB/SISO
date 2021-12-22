package it.webred.cs.jsf.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

@ManagedBean
@NoneScoped
public class DatiSocApeBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String ufficio;
	private String dataPic;
	private String assistenteSociale;
	

	public String getUfficio() {
		return ufficio;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}

	public String getDataPic() {
		return dataPic;
	}

	public void setDataPic(String dataPic) {
		this.dataPic = dataPic;
	}

	public String getAssistenteSociale() {
		return assistenteSociale;
	}

	public void setAssistenteSociale(String assistenteSociale) {
		this.assistenteSociale = assistenteSociale;
	}

}

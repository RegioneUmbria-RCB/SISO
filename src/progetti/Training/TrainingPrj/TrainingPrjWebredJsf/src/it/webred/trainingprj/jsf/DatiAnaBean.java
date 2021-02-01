package it.webred.trainingprj.jsf;

import it.webred.trainingprj.jsf.bean.Comune;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;


@ManagedBean
@NoneScoped
public class DatiAnaBean implements Serializable {
	

	private static final long serialVersionUID = 1L;
	private String cognome;
	private String nome;
	private String dataNascita;
	private Comune comuneNascita;
	private String sesso;
	private String statoCivile;
	private String codiceFiscale;
	private String cittadinanza;
	private String status;
	private Comune comuneResidenza;
	private String medicoCurante;
	private String telefono;
	private String cellulare;
	private String eMail;
	
	private String ufficio;
	private String dataPic;
	private String assistenteSociale;
	
	private String periodoChiusura;
	private String dataEffettivaChiusura;
	private String motiviChiusura;
	
	
	public String getCognome() {
		return capitalize(cognome);
	}
	
	public void setCognome(String cognome) {
		this.cognome = capitalize(cognome);
	}

	public String getNome() {
		return capitalize(nome);
	}

	public void setNome(String nome) {
		this.nome = capitalize(nome);
	}

	public String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Comune getComuneNascita() {
		return comuneNascita;
	}

	public void setComuneNascita(Comune comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getStatoCivile() {
		return statoCivile;
	}

	public void setStatoCivile(String statoCivile) {
		this.statoCivile = statoCivile;
	}

	public String getCodiceFiscale() {
		return uppercase(codiceFiscale);
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = uppercase(codiceFiscale);
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Comune getComuneResidenza() {
		return comuneResidenza;
	}

	public void setComuneResidenza(Comune comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public String getMedicoCurante() {
		return medicoCurante;
	}

	public void setMedicoCurante(String medicoCurante) {
		this.medicoCurante = medicoCurante;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCellulare() {
		return cellulare;
	}

	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

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

	public String getPeriodoChiusura() {
		return periodoChiusura;
	}

	public void setPeriodoChiusura(String periodoChiusura) {
		this.periodoChiusura = periodoChiusura;
	}

	public String getDataEffettivaChiusura() {
		return dataEffettivaChiusura;
	}

	public void setDataEffettivaChiusura(String dataEffettivaChiusura) {
		this.dataEffettivaChiusura = dataEffettivaChiusura;
	}

	public String getMotiviChiusura() {
		return motiviChiusura;
	}

	public void setMotiviChiusura(String motiviChiusura) {
		this.motiviChiusura = motiviChiusura;
	}

	private String capitalize(String value) {
		if (value == null) {
			return value;
		}
		if (value.trim().equals("")) {
			return value.trim();
		}
		StringBuilder sb = new StringBuilder();
		String[] arr = value.split(" ");
		for (String s : arr) {
			if (s != null && !s.trim().equals("")) {
				sb.append(s.substring(0, 1).toUpperCase());
				sb.append(s.length() == 1 ? "" : s.substring(1));
				sb.append(" ");
			}
		}
		return sb.toString().trim();
	}
	
	private String uppercase(String value) {
		if (value == null) {
			return value;
		}
		if (value.trim().equals("")) {
			return value.trim();
		}
		return value.trim().toUpperCase();
	}
	

}

package it.webred.cs.json.mediazioneculturale.ver1;

//DONE
import it.webred.cs.json.dto.JsonBaseBean;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MediazioneCultBean extends JsonBaseBean {

	private String descrizione;
	private double oreRichieste;
	private Date dataInizioProposta = new Date();
	private String lingua;
	private String altraLingua;
	private String enteRichiedente;
	private String tipoSoggettoRichiedente = "Scuola";
	private String personaReferenteEnte;
	private String qualifica;
	private String telefono;
	private String email;

	@JsonIgnore
	private List<String> lstTipiSoggetto;

	public MediazioneCultBean() {
		super();

		lstTipiSoggetto = new LinkedList<String>();
		lstTipiSoggetto.add("Comune");
		lstTipiSoggetto.add("Scuola");
		lstTipiSoggetto.add("USL");
		lstTipiSoggetto.add("Altro");
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public double getOreRichieste() {
		return oreRichieste;
	}

	public void setOreRichieste(double oreRichieste) {
		this.oreRichieste = oreRichieste;
	}

	public Date getDataInizioProposta() {
		return dataInizioProposta;
	}

	public void setDataInizioProposta(Date dataInizioProposta) {
		this.dataInizioProposta = dataInizioProposta;
	}

	public String getLingua() {
		return lingua;
	}

	public void setLingua(String lingua) {
		this.lingua = lingua;
	}

	public String getAltraLingua() {
		return altraLingua;
	}

	public void setAltraLingua(String altraLingua) {
		this.altraLingua = altraLingua;
	}

	public String getEnteRichiedente() {
		return enteRichiedente;
	}

	public void setEnteRichiedente(String enteRichiedente) {
		this.enteRichiedente = enteRichiedente;
	}

	public String getTipoSoggettoRichiedente() {
		return tipoSoggettoRichiedente;
	}

	public void setTipoSoggettoRichiedente(String tipoSoggettoRichiedente) {
		this.tipoSoggettoRichiedente = tipoSoggettoRichiedente;
	}

	public String getPersonaReferenteEnte() {
		return personaReferenteEnte;
	}

	public void setPersonaReferenteEnte(String personaReferenteEnte) {
		this.personaReferenteEnte = personaReferenteEnte;
	}

	public String getQualifica() {
		return qualifica;
	}

	public void setQualifica(String qualifica) {
		this.qualifica = qualifica;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getLstTipiSoggetto() {
		return this.lstTipiSoggetto;
	}

	@Override
	public MediazioneCultBean autoClone() throws Exception {
		return (MediazioneCultBean) super.autoClone(); // TODO implementare?
	}

	/*TODO:Momentaneamente tolto il controllo dell'obbigatorietà dei dati, 
	*      in attesa di riuscire a collegare la richiesta del form alla tipologia di intervento in UDC*/
	@Override
	public List<String> checkObbligatorieta() {
		List<String> messages = new LinkedList<String>();
		
		
/*		if (StringUtils.isBlank(this.descrizione)) {
			messages.add("Inserire una breve descrizione del motivo della richiesta.");
		}
		if (this.oreRichieste == 0D) {
			messages.add("Inserire il numero di ore richieste.");
		}
		if (this.oreRichieste < 0D) {
			messages.add("Il numero di ore richieste non è valido.");
		}
		if (this.dataInizioProposta == null) {
			messages.add("Inserire la data di inizio proposta.");
			// TODO inserire altri controlli sulla validità della data?
		}
		if (StringUtils.isBlank(this.lingua)) {
			messages.add("Inserire la lingua.");
		}
		if (StringUtils.isBlank(this.enteRichiedente)) {
			messages.add("Inserire l'ente richiedente il servizio.");
		}
		if (StringUtils.isBlank(this.tipoSoggettoRichiedente)) {
			messages.add("Specificare il tipo di soggetto richiedente");
		}
		if (StringUtils.isBlank(this.personaReferenteEnte)) {
			messages.add("Indicare la persona referente dell'ente.");
		}
		if (StringUtils.isBlank(this.telefono) && StringUtils.isBlank(this.email)) {
			messages.add("Indicare almeno uno tra recapito telefonico e indirizzo email.");
		}

		if (!StringUtils.isBlank(this.email)) {
			if (!MailAddress.isValid(this.email)) {
				messages.add("L'indirizzo email non è valido.");
			}
		}*/

		return messages;
	}
}

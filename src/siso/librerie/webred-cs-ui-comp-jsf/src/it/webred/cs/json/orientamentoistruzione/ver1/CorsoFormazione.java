package it.webred.cs.json.orientamentoistruzione.ver1;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CorsoFormazione {
	private String descrizione;
	private boolean svoltoInItalia;
	private String principaliTematiche;
	private Integer durataOre;
	private boolean attestato;
	private String qualifica;

	public CorsoFormazione() {
		super();
		this.descrizione = "";
		this.svoltoInItalia = false;
		this.attestato = false;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public boolean isSvoltoInItalia() {
		return svoltoInItalia;
	}

	public void setSvoltoInItalia(boolean svoltoInItalia) {
		this.svoltoInItalia = svoltoInItalia;
	}

	public String getPrincipaliTematiche() {
		return principaliTematiche;
	}

	public void setPrincipaliTematiche(String principaliTematiche) {
		this.principaliTematiche = principaliTematiche;
	}

	public Integer getDurataOre() {
		return durataOre;
	}

	public void setDurataOre(Integer durataOre) {
		if (durataOre != null && durataOre <= 0) {
			this.durataOre = null;
		} else {
			this.durataOre = durataOre;
		}
	}

	public boolean isAttestato() {
		return attestato;
	}

	public void setAttestato(boolean attestato) {
		this.attestato = attestato;
	}

	public String getQualifica() {
		return qualifica;
	}

	public void setQualifica(String qualifica) {
		this.qualifica = qualifica;
	}

	public List<String> checkObbligatorieta() {
		List<String> messages = new LinkedList<String>();

		if (StringUtils.isBlank(this.descrizione)) {
			messages.add("Inserire la descrizione del corso di formazione.");
		}

		if (this.durataOre != null && this.durataOre <= 0) {
			messages.add("La durata in ore del corso di formazione non è un numero valido.");
		}

		return messages;
	}

	@JsonIgnore
	public String getTitle() {
		if (StringUtils.isBlank(this.descrizione))
			return "▶▶▶  inserire dati del corso ◀◀◀";
		else
			return "Corso di " + this.descrizione;
	}
}

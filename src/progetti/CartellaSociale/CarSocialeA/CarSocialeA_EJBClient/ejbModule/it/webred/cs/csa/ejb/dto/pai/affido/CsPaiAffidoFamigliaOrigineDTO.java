package it.webred.cs.csa.ejb.dto.pai.affido;

import java.io.Serializable;


public class CsPaiAffidoFamigliaOrigineDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	private Long id;
	
	private String motivazioni;
	
	private Long csAComponenteIdPadre;

	private Long csAComponenteIdMadre;
	
	private Boolean minoreAllontanato = false;
	
	private Boolean genitoriSconosciuti = false;
	
	private Boolean fuoriRegione = false;
	
	private String codiceIntervento;
	
    private Boolean contributoAlleSpese = false;
	
	private String noteContributoAlleSpese;
	
	private CsPaiAffidoSoggFamigliaDTO padre;
	
	private CsPaiAffidoSoggFamigliaDTO madre;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMotivazioni() {
		return motivazioni;
	}

	public void setMotivazioni(String motivazioni) {
		this.motivazioni = motivazioni;
	}

	public Long getCsAComponenteIdPadre() {
		return csAComponenteIdPadre;
	}

	public void setCsAComponenteIdPadre(Long csAComponenteIdPadre) {
		this.csAComponenteIdPadre = csAComponenteIdPadre;
	}

	public Long getCsAComponenteIdMadre() {
		return csAComponenteIdMadre;
	}

	public void setCsAComponenteIdMadre(Long csAComponenteIdMadre) {
		this.csAComponenteIdMadre = csAComponenteIdMadre;
	}

	public Boolean getMinoreAllontanato() {
		return minoreAllontanato;
	}

	public void setMinoreAllontanato(Boolean minoreAllontanato) {
		this.minoreAllontanato = minoreAllontanato;
	}

	public Boolean getGenitoriSconosciuti() {
		return genitoriSconosciuti;
	}

	public void setGenitoriSconosciuti(Boolean genitoriSconosciuti) {
		this.genitoriSconosciuti = genitoriSconosciuti;
	}

	public Boolean getFuoriRegione() {
		return fuoriRegione;
	}

	public void setFuoriRegione(Boolean fuoriRegione) {
		this.fuoriRegione = fuoriRegione;
	}

	public Boolean getContributoAlleSpese() {
		return contributoAlleSpese;
	}

	public void setContributoAlleSpese(Boolean contributoAlleSpese) {
		this.contributoAlleSpese = contributoAlleSpese;
	}

	public String getCodiceIntervento() {
		return codiceIntervento;
	}

	public void setCodiceIntervento(String codiceIntervento) {
		this.codiceIntervento = codiceIntervento;
	}

	public CsPaiAffidoSoggFamigliaDTO getPadre() {
		return padre;
	}

	public void setPadre(CsPaiAffidoSoggFamigliaDTO padre) {
		this.padre = padre;
	}

	public CsPaiAffidoSoggFamigliaDTO getMadre() {
		return madre;
	}

	public void setMadre(CsPaiAffidoSoggFamigliaDTO madre) {
		this.madre = madre;
	}

	//SISO-1172

	public String getNoteContributoAlleSpese() {
		return noteContributoAlleSpese;
	}

	public void setNoteContributoAlleSpese(String noteContributoAlleSpese) {
		this.noteContributoAlleSpese = noteContributoAlleSpese;
	}


	//FINE SISIO-1172
}

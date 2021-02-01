package it.webred.cs.data.model.affido;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="CS_PAI_AFFIDO_FAMIGLIA_ORIG")
public class CsPaiAffidoFamigliaOrigine {

	@Id
	@Column(name="ID")
	@SequenceGenerator(name="CS_PAI_AFFIDO_FAM_ORIG_ID_GENERATOR", sequenceName="SQ_PAI_AFFIDO",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_PAI_AFFIDO_FAM_ORIG_ID_GENERATOR")
	private Long id;
	
	@Column(name = "MOTIVAZIONI")
	private String motivazioni;
	
	@Column(name = "PADRE_ID")
	private Long csAComponenteIdPadre;
	
	@Column(name = "MADRE_ID")
	private Long csAComponenteIdMadre;
	
	@Column(name = "IS_MINORE_ALLONTANATO")
	private Boolean minoreAllontanato;
	
	@Column(name = "GENITORI_SCONOSCIUTI")
	private Boolean genitoriSconosciuti;
	
	@Column(name = "FUORI_REGIONE")
	private Boolean fuoriRegione;
	
	@Column(name = "CODICE_INTERVENTO")
	private String codiceIntervento;
	
	@Column(name = "CONTRIBUTO_ALLE_SPESE")
	private Boolean contributoAlleSpese;
	
	@Column(name = "NOTE_CONTRIBUTO_ALLE_SPESE")
	private String noteContributoAlleSpese;
	
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

	public String getCodiceIntervento() {
		return codiceIntervento;
	}

	public void setCodiceIntervento(String codiceIntervento) {
		this.codiceIntervento = codiceIntervento;
	}
    //Inizio SISO-1172
	public Boolean getContributoAlleSpese() {
		return contributoAlleSpese;
	}

	public void setContributoAlleSpese(Boolean contributoAlleSpese) {
		this.contributoAlleSpese = contributoAlleSpese;
	}

	public String getNoteContributoAlleSpese() {
		return noteContributoAlleSpese;
	}

	public void setNoteContributoAlleSpese(String noteContributoAlleSpese) {
		this.noteContributoAlleSpese = noteContributoAlleSpese;
	}
	//SISO-1172 FIne 
	
}

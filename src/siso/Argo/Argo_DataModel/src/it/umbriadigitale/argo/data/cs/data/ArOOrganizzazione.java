package it.umbriadigitale.argo.data.cs.data;

// Generated 26-ott-2015 13.12.17 by Hibernate Tools 4.0.0

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "AR_O_ORGANIZZAZIONE")
public class ArOOrganizzazione implements Serializable {

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	private Long id;
	
	@Column(name = "TOOLTIP", length = 4000)
	private String tooltip;
	
	private Boolean abilitato;
	
	@Column(name = "EMAIL", length = 100)
	private String email;
	
	@Column(name = "NOME", nullable = false, length = 100)
	private String nome;
	private String belfiore;
	
	@Column(name="PIVA_CF", nullable = false, length = 20)
	private String pivaCf;
	
	@Column(name="ZONA_NOME")
	private String zonaNome;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "arOOrganizzazione")
	private Set<ArFfFondo> arFfFondos = new HashSet<ArFfFondo>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "arOOrganizzazione")
	private Set<ArFfErogante> arFfErogantes = new HashSet<ArFfErogante>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "arOOrganizzazioneByConvOrganizzazioneId")
	private Set<ArFfServizioTerriotorio> arFfServizioTerriotoriosForConvOrganizzazioneId = new HashSet<ArFfServizioTerriotorio>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "arOOrganizzazioneByOrganizzazioneId")
	private Set<ArFfServizioTerriotorio> arFfServizioTerriotoriosForOrganizzazioneId = new HashSet<ArFfServizioTerriotorio>(0);

	public ArOOrganizzazione() {
	}

	public ArOOrganizzazione(long id, String nome, String pivaCf) {
		this.id = id;
		this.nome = nome;
		this.pivaCf = pivaCf;
	}

	public ArOOrganizzazione(
			long id,
			String tooltip,
			Boolean abilitato,
			String email,
			String nome,
			String belfiore,
			String pivaCf,
			Set<ArFfFondo> arFfFondos,
			Set<ArFfErogante> arFfErogantes,
			Set<ArFfServizioTerriotorio> arFfServizioTerriotoriosForConvOrganizzazioneId,
			Set<ArFfServizioTerriotorio> arFfServizioTerriotoriosForOrganizzazioneId) {
		this.id = id;
		this.tooltip = tooltip;
		this.abilitato = abilitato;
		this.email = email;
		this.nome = nome;
		this.belfiore = belfiore;
		this.pivaCf = pivaCf;
		this.arFfFondos = arFfFondos;
		this.arFfErogantes = arFfErogantes;
		this.arFfServizioTerriotoriosForConvOrganizzazioneId = arFfServizioTerriotoriosForConvOrganizzazioneId;
		this.arFfServizioTerriotoriosForOrganizzazioneId = arFfServizioTerriotoriosForOrganizzazioneId;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}


	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

	
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "BELFIORE", length = 5)
	public String getBelfiore() {
		return this.belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

	public String getPivaCf() {
		return this.pivaCf;
	}

	public void setPivaCf(String pivaCf) {
		this.pivaCf = pivaCf;
	}

	public Set<ArFfFondo> getArFfFondos() {
		return this.arFfFondos;
	}

	public void setArFfFondos(Set<ArFfFondo> arFfFondos) {
		this.arFfFondos = arFfFondos;
	}

	
	public Set<ArFfErogante> getArFfErogantes() {
		return this.arFfErogantes;
	}

	public void setArFfErogantes(Set<ArFfErogante> arFfErogantes) {
		this.arFfErogantes = arFfErogantes;
	}

	
	public Set<ArFfServizioTerriotorio> getArFfServizioTerriotoriosForConvOrganizzazioneId() {
		return this.arFfServizioTerriotoriosForConvOrganizzazioneId;
	}

	public void setArFfServizioTerriotoriosForConvOrganizzazioneId(
			Set<ArFfServizioTerriotorio> arFfServizioTerriotoriosForConvOrganizzazioneId) {
		this.arFfServizioTerriotoriosForConvOrganizzazioneId = arFfServizioTerriotoriosForConvOrganizzazioneId;
	}


	public Set<ArFfServizioTerriotorio> getArFfServizioTerriotoriosForOrganizzazioneId() {
		return this.arFfServizioTerriotoriosForOrganizzazioneId;
	}

	public void setArFfServizioTerriotoriosForOrganizzazioneId(
			Set<ArFfServizioTerriotorio> arFfServizioTerriotoriosForOrganizzazioneId) {
		this.arFfServizioTerriotoriosForOrganizzazioneId = arFfServizioTerriotoriosForOrganizzazioneId;
	}

	public String getZonaNome() {
		return zonaNome;
	}

	public void setZonaNome(String zonaNome) {
		this.zonaNome = zonaNome;
	}

}

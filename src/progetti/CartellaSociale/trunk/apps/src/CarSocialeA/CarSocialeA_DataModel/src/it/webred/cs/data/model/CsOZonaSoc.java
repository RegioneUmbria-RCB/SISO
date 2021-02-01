package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="CS_O_ZONA_SOC")
@NamedQuery(name="CsOZonaSoc.findAll", query="SELECT c FROM CsOZonaSoc c")
public class CsOZonaSoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_O_ZONA_SOC_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_O_ZONA_SOC_ID_GENERATOR")
	private Long id;

	private String abilitato;

	private String nome;
	
	@Column(name="COD_ISTAT_REGIONE")
	private String codIstatRegione;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodIstatRegione() {
		return codIstatRegione;
	}

	public void setCodIstatRegione(String codIstatRegione) {
		this.codIstatRegione = codIstatRegione;
	}
}
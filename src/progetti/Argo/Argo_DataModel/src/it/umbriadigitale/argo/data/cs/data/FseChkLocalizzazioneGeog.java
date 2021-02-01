package it.umbriadigitale.argo.data.cs.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="FSE_CHK_LOCALIZZAZIONE_GEOG")
public class FseChkLocalizzazioneGeog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private String codiceRegione;
	private String descrizioneRegione;
	private String codiceProvincia;
	private String codiceComune;
	private String descrizioneComune;
	private String nuts_1;
	private String nuts_2;
	private String nuts_3;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name ="CODICE_REGIONE")
	public String getCodiceRegione() {
		return codiceRegione;
	}
	public void setCodiceRegione(String codiceRegione) {
		this.codiceRegione = codiceRegione;
	}
	@Column(name ="DESCRIZIONE_REGIONE")
	public String getDescrizioneRegione() {
		return descrizioneRegione;
	}
	public void setDescrizioneRegione(String descrizioneRegione) {
		this.descrizioneRegione = descrizioneRegione;
	}
	@Column(name ="CODICE_PROVINCIA")
	public String getCodiceProvincia() {
		return codiceProvincia;
	}
	public void setCodiceProvincia(String codiceProvincia) {
		this.codiceProvincia = codiceProvincia;
	}
	@Column(name ="CODICE_COMUNE")
	public String getCodiceComune() {
		return codiceComune;
	}
	public void setCodiceComune(String codiceComune) {
		this.codiceComune = codiceComune;
	}
	@Column(name ="DESCRIZIONE_COMUNE")
	public String getDescrizioneComune() {
		return descrizioneComune;
	}
	public void setDescrizioneComune(String descrizioneComune) {
		this.descrizioneComune = descrizioneComune;
	}
	@Column(name ="NUTS_1")
	public String getNuts_1() {
		return nuts_1;
	}
	public void setNuts_1(String nuts_1) {
		this.nuts_1 = nuts_1;
	}
	@Column(name ="NUTS_2")
	public String getNuts_2() {
		return nuts_2;
	}
	public void setNuts_2(String nuts_2) {
		this.nuts_2 = nuts_2;
	}
	@Column(name ="NUTS_3")
	public String getNuts_3() {
		return nuts_3;
	}
	public void setNuts_3(String nuts_3) {
		this.nuts_3 = nuts_3;
	}
	
	
	
	
}

package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CS_O_ORGANIZZAZIONE database table.
 * 
 */
@Entity
@Table(name="CS_O_ORGANIZZAZIONE")
@NamedQuery(name="CsOOrganizzazione.findAll", query="SELECT c FROM CsOOrganizzazione c")
public class CsOOrganizzazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_O_ORGANIZZAZIONE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_O_ORGANIZZAZIONE_ID_GENERATOR")
	private Long id;

	private String abilitato;

	private String email;

	private String nome;

	private String tooltip;
	
	/*CODICE FITTIZIO UNIVOCO (eventualmente coincidente con il CODICE CATASTALE), 
	 * valorizzato solo per le organizzazioni abilitate ad accedere al sistema */
	@Column(name="COD_ROUTING")
	private String codRouting; 
	
	/*CODICE CATASTALE REALE DEL COMUNE (VALORIZZATO SOLO PER COMUNI), non è univoco (es. Municipi del Comune di Roma è lo stesso per tutti)*/
	@Column(name="COD_CATASTALE")
	private String codCatastale;   
	
	// SISO-663 SM
	@Column(name = "FLAG_CAPOFILA")
	private Boolean flagCapofila;

	@Column(name = "FLAG_RICEVI_SCHEDE_ACCOGLIENZA")
	private Boolean flagRiceviSchedeAccoglienza;
	
	@Column(name="COD_EXPORT_FLUSSO")
	private String codExportFlusso;

	private String cf;
	
	private String piva;
	
	public CsOOrganizzazione() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAbilitato() {
		return this.abilitato;
	}

	public void setAbilitato(String abilitato) {
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

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	
	public String getCodRouting() {
		return codRouting;
	}

	public void setCodRouting(String codRouting) {
		this.codRouting = codRouting;
	}

	public Boolean getFlagCapofila() {
		return flagCapofila;
	}

	public void setFlagCapofila(Boolean flagCapofila) {
		this.flagCapofila = flagCapofila;
	}

	public String getCodCatastale() {
		return codCatastale;
	}

	public void setCodCatastale(String codCatastale) {
		this.codCatastale = codCatastale;
	}

	public Boolean getFlagRiceviSchedeAccoglienza() {
		return flagRiceviSchedeAccoglienza;
	}

	public void setFlagRiceviSchedeAccoglienza(Boolean flagRiceviSchedeAccoglienza) {
		this.flagRiceviSchedeAccoglienza = flagRiceviSchedeAccoglienza;
	}
	
	public boolean isOrgAttiva(){
		return this.abilitato!=null && "1".equalsIgnoreCase(abilitato);
	}

	public String getCodExportFlusso() {
		return codExportFlusso;
	}

	public void setCodExportFlusso(String codExportFlusso) {
		this.codExportFlusso = codExportFlusso;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getPiva() {
		return piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}
	
}
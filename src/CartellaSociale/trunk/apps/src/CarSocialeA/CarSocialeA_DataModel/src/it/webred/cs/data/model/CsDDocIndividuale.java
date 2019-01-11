package it.webred.cs.data.model;

import it.webred.cs.data.base.ICsDDiarioChild;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the CS_D_DOC_INDIVIDUALE database table.
 * 
 */
@Entity
@Table(name="CS_D_DOC_INDIVIDUALE")
@NamedQuery(name="CsDDocIndividuale.findAll", query="SELECT c FROM CsDDocIndividuale c")
public class CsDDocIndividuale implements ICsDDiarioChild {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DIARIO_ID")
	private Long diarioId;

	private Boolean lettura;
	
	private String descrizione;
	
	private Boolean letto;
	
	private Boolean privato;
	
	@ManyToOne
	@JoinColumn(name="SUB_DIR_ID")
	private CsTbSottocartellaDoc csTbSottocartellaDoc;
	
	//bi-directional one-to-one association to CsDDiario
	@OneToOne
	@JoinColumn(name="DIARIO_ID")
	private CsDDiario csDDiario;
		
	public CsDDocIndividuale() {
	}

	public Long getDiarioId() {
		return diarioId;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public CsDDiario getCsDDiario() {
		return csDDiario = (csDDiario==null) ? new CsDDiario() : csDDiario;
	}

	public void setCsDDiario(CsDDiario csDDiario) {
		this.csDDiario = csDDiario;
	}

	public Boolean getLettura() {
		return lettura;
	}

	public void setLettura(Boolean lettura) {
		this.lettura = lettura;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Boolean getLetto() {
		return letto;
	}

	public void setLetto(Boolean letto) {
		this.letto = letto;
	}

	public Boolean getPrivato() {
		return privato;
	}

	public void setPrivato(Boolean privato) {
		this.privato = privato;
	}

	public CsTbSottocartellaDoc getCsTbSottocartellaDoc() {
		return csTbSottocartellaDoc;
	}

	public void setCsTbSottocartellaDoc(CsTbSottocartellaDoc csTbSottocartellaDoc) {
		this.csTbSottocartellaDoc = csTbSottocartellaDoc;
	}

}
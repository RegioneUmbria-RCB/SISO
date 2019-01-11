package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the CS_A_ANA_INDIRIZZO database table.
 * 
 */
@Entity
@Table(name="CS_A_ANA_INDIRIZZO")
@NamedQuery(name="CsAAnaIndirizzo.findAll", query="SELECT c FROM CsAAnaIndirizzo c")
public class CsAAnaIndirizzo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_A_ANA_INDIRIZZO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_ANA_INDIRIZZO_ID_GENERATOR")
	private Long id;

	@Column(name="CIVICO_ALTRO")
	private String civicoAltro;

	@Column(name="CIVICO_NUMERO")
	private String civicoNumero;

	@Column(name="CODICE_VIA")
	private String codiceVia;

	@Column(name="COM_COD")
	private String comCod;

	@Column(name="COM_DES")
	private String comDes;

	private String indirizzo;

	private String prov;

	@Column(name="STATO_COD")
	private String statoCod;

	@Column(name="STATO_DES")
	private String statoDes;

	
	/**
	 * 
	 * non serve il bi-diractional
	//bi-directional one-to-one association to CsAIndirizzo
	@OneToOne(mappedBy="csAAnaIndirizzo")
	private CsAIndirizzo csAIndirizzo;

	//bi-directional many-to-one association to CsCComunita
	@OneToMany(mappedBy="csAAnaIndirizzo")
	private List<CsCComunita> csCComunitas;

	//bi-directional many-to-one association to CsOSettore
	@OneToMany(mappedBy="csAAnaIndirizzo")
	private List<CsOSettore> csOSettores;
	 */

	public CsAAnaIndirizzo() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCivicoAltro() {
		return this.civicoAltro;
	}

	public void setCivicoAltro(String civicoAltro) {
		this.civicoAltro = civicoAltro;
	}

	public String getCivicoNumero() {
		return this.civicoNumero;
	}

	public void setCivicoNumero(String civicoNumero) {
		this.civicoNumero = civicoNumero;
	}

	public String getCodiceVia() {
		return this.codiceVia;
	}

	public void setCodiceVia(String codiceVia) {
		this.codiceVia = codiceVia;
	}

	public String getComCod() {
		return this.comCod;
	}

	public void setComCod(String comCod) {
		this.comCod = comCod;
	}

	public String getComDes() {
		return this.comDes;
	}

	public void setComDes(String comDes) {
		this.comDes = comDes;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getProv() {
		return this.prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getStatoCod() {
		return this.statoCod;
	}

	public void setStatoCod(String statoCod) {
		this.statoCod = statoCod;
	}

	public String getStatoDes() {
		return this.statoDes;
	}

	public void setStatoDes(String statoDes) {
		this.statoDes = statoDes;
	}

	@Transient
	public String getLabelIndirizzoCompleto(){
		String lbl = "";
		lbl =  this.indirizzo+" "+ this.getCivicoFromNumeroAltro()+", ";
		lbl += (this.getComDes()!=null) ?    " "+this.getComDes()+" ("+this.getProv()+")" : "";
		lbl += (this.getStatoDes()!=null) ?  " - "+this.getStatoDes() : "";
		return lbl;
	}
	
	public String getLabelIndirizzo() {
		String indirizzo = this.indirizzo;
		if (indirizzo == null) {
			indirizzo = "";
		}
		indirizzo = indirizzo.trim();
		String civico = getCivicoFromNumeroAltro();
		if (civico.equals("-")) {
			civico = "";
		}
		civico = civico.trim();
		if (!indirizzo.equals("")) {
			if (!civico.equals("")) {
				indirizzo += (", " + civico);
			}
		} else {
			indirizzo = civico;
		}
		return indirizzo.trim().equals("") ? "-" : indirizzo.trim();
	}
	
	@Transient
	public String getCivicoFromNumeroAltro() {
		String numero=this.civicoNumero;
		String altro=this.civicoAltro;
		if (numero == null) {
			numero = "";
		}
		if (altro == null) {
			altro = "";
		}
		if (altro.startsWith("/")) {
			altro = altro.substring(1);
		}
		numero = numero.trim();
		altro = altro.trim();
		if (!altro.equals("")) {
			numero += ("/" + altro);
		}
		return numero.trim().equals("") ? "-" : numero.trim();
	}
	
}
package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the CS_TB_TUTELA database table.
 * 
 */
@Entity
@Table(name="CS_VISTA_CASI_SS")
@NamedQuery(name="CsVistaCasiSS.findAll", query="SELECT csView FROM CsVistaCasiSS csView")
public class CsVistaCasiSS implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ID_SCHEDA")
	private long idScheda;
	
	@Temporal(TemporalType.DATE)
	@Column(name="ACCESSO_DATA")
	private Date accessoData;
	
	@Column(name="ACCESSO_OPER_CF")
	private String accesso_Oper_Cf;
	
	@Column(name="ACCESSO_OPER_COGNOME")
	private String accesso_Oper_Cognome;
	
	@Column(name="ACCESSO_OPER_NOME")
	private String accesso_Oper_Nome;
	
	@Column(name="ACCESSO_UFFICIO")
	private String accesso_Ufficio;
	
	@Column(name="SEGNALATO_COGNOME")
	private String segnalatoCognome;
	
	@Column(name="SEGNALATO_NOME")
	private String segnalatoNome;
	
	@Column(name="TIPO_INTERVENTO")
	private String tipoIntervento;
	
	//uni-directional many-to-one association to CsCCategoriaSociale
	@ManyToOne
	@JoinColumn(name="CATEGORIA_SOCIALE_ID")
	private CsCCategoriaSociale csCCategoriaSociale;
	
	public long getIdScheda() {
		return idScheda;
	}

	public void setIdScheda(long idScheda) {
		this.idScheda = idScheda;
	}

	public Date getAccessoData() {
		return accessoData;
	}

	public void setAccessoData(Date accessoData) {
		this.accessoData = accessoData;
	}

	public String getAccesso_Oper_Cf() {
		return accesso_Oper_Cf;
	}

	public void setAccesso_Oper_Cf(String accesso_Oper_Cf) {
		this.accesso_Oper_Cf = accesso_Oper_Cf;
	}

	public String getAccesso_Oper_Cognome() {
		return accesso_Oper_Cognome;
	}

	public void setAccesso_Oper_Cognome(String accesso_Oper_Cognome) {
		this.accesso_Oper_Cognome = accesso_Oper_Cognome;
	}

	public String getAccesso_Oper_Nome() {
		return accesso_Oper_Nome;
	}

	public void setAccesso_Oper_Nome(String accesso_Oper_Nome) {
		this.accesso_Oper_Nome = accesso_Oper_Nome;
	}

	public String getAccesso_Ufficio() {
		return accesso_Ufficio;
	}

	public void setAccesso_Ufficio(String accesso_Ufficio) {
		this.accesso_Ufficio = accesso_Ufficio;
	}

	public String getSegnalatoCognome() {
		return segnalatoCognome;
	}

	public void setSegnalatoCognome(String segnalatoCognome) {
		this.segnalatoCognome = segnalatoCognome;
	}

	public String getSegnalatoNome() {
		return segnalatoNome;
	}

	public void setSegnalatoNome(String segnalatoNome) {
		this.segnalatoNome = segnalatoNome;
	}

	public String getTipoIntervento() {
		return tipoIntervento;
	}

	public void setTipoIntervento(String tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}

	public CsCCategoriaSociale getCsCCategoriaSociale() {
		return csCCategoriaSociale;
	}

	public void setCsCCategoriaSociale(CsCCategoriaSociale csCCategoriaSociale) {
		this.csCCategoriaSociale = csCCategoriaSociale;
	}

}

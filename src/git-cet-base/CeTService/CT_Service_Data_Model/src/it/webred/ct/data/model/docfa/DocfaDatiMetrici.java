package it.webred.ct.data.model.docfa;

import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DOCFA_DATI_METRICI database table.
 * 
 */
@Entity
@Table(name="DOCFA_DATI_METRICI")
public class DocfaDatiMetrici implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DocfaDatiMetriciPK id;
	
	private String altezza;

	@Column(name="ALTEZZA_MAX")
	private String altezzaMax;

	private String ambiente;

	@Column(name="CODICE_AMMINISTRATIVO")
	private String codiceAmministrativo;

	@Column(name="DATA_REGISTRAZIONE")
	private String dataRegistrazione;

    @Temporal( TemporalType.DATE)
	private Date fornitura;

	@Column(name="PROTOCOLLO_REGISTRAZIONE")
	private String protocolloRegistrazione;

	private String sezione;

	private String superficie;

    public DocfaDatiMetrici() {
    }

	public String getAltezza() {
		return this.altezza;
	}

	public void setAltezza(String altezza) {
		this.altezza = altezza;
	}

	public String getAltezzaMax() {
		return this.altezzaMax;
	}

	public void setAltezzaMax(String altezzaMax) {
		this.altezzaMax = altezzaMax;
	}

	public String getAmbiente() {
		return this.ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public String getCodiceAmministrativo() {
		return this.codiceAmministrativo;
	}

	public void setCodiceAmministrativo(String codiceAmministrativo) {
		this.codiceAmministrativo = codiceAmministrativo;
	}

	public String getDataRegistrazione() {
		return this.dataRegistrazione;
	}

	public void setDataRegistrazione(String dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public Date getFornitura() {
		return this.fornitura;
	}

	public void setFornitura(Date fornitura) {
		this.fornitura = fornitura;
	}

	public String getProtocolloRegistrazione() {
		return this.protocolloRegistrazione;
	}

	public void setProtocolloRegistrazione(String protocolloRegistrazione) {
		this.protocolloRegistrazione = protocolloRegistrazione;
	}

	public String getSezione() {
		return this.sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getSuperficie() {
		return this.superficie;
	}

	public void setSuperficie(String superficie) {
		this.superficie = superficie;
	}

	public DocfaDatiMetriciPK getId() {
		return id;
	}

	public void setId(DocfaDatiMetriciPK id) {
		this.id = id;
	}

}
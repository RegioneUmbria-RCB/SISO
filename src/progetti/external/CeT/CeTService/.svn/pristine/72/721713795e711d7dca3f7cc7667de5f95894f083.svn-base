package it.webred.ct.data.model.cnc.flusso750;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the RE_CNC_R7F_ULT_BENEFICIARI database table.
 * 
 */
@Entity
@Table(name="RE_CNC_R7F_ULT_BENEFICIARI")
@NamedQueries({
	@NamedQuery(name="Flusso750.getUlterioriBenefByChiavePartita",
			    query="SELECT uben FROM VUlterioriBeneficiari uben WHERE uben.chiavePartita.annoRiferimento=:annoRiferimento" + 
			    	  " AND uben.chiavePartita.codicePartita=:codicePartita " + 
			    	  " AND uben.chiavePartita.codTipoUfficio=:codTipoUfficio " +
			    	  " AND uben.chiavePartita.codUfficio=:codUfficio " +
			    	  " AND uben.chiavePartita.annoRiferimento=:annoRiferimento " +
			    	  " AND uben.chiavePartita.annoRuolo=:annoRuolo" +
			    	  " AND uben.chiavePartita.progressivoRuolo=:progressivoRuolo" +
			    	  " AND uben.chiavePartita.codAmbito=:codAmbito " +
			    	  " AND uben.chiavePartita.codEnteCreditore=:codEnteCreditore")
})
public class VUlterioriBeneficiari implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Surrogate Key
	@Id
	@Column(name="ID")
	private Long id;

	@Embedded
	private ChiaveULPartita chiavePartita;

	private String articolo;

	private String capitolo;

	private String capo;


	@Column(name="COD_ENTRATA")
	private String codEntrata;

	@Column(name="COD_TIPO_ENTRATA")
	private String codTipoEntrata;

	@Column(name="COD_TIPO_UFFICIO_ULT_BENEF")
	private String codTipoUfficioUltBenef;


	@Column(name="COD_UFFICIO_ULT_BENEF")
	private String codUfficioUltBenef;

	@Column(name="COD_ULT_ENTE_BENEF")
	private String codUltEnteBenef;


    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

	@Column(name="IMPORTO_AGG_ANTIC_ULT_BENEF")
	private String importoAggAnticUltBenef;

	@Column(name="IMPORTO_AGG_ULT_BENEF")
	private String importoAggUltBenef;

	@Column(name="IMPORTO_ART_RUOLO_ULT_BENEF")
	private String importoArtRuoloUltBenef;

	@Column(name="IMPORTO_CARICO_ULT_BENEF")
	private String importoCaricoUltBenef;

	private String processid;

	@Column(name="PROGRESSIVO_ARTICOLO_RUOLO")
	private String progressivoArticoloRuolo;

	@Column(name="PROGRESSIVO_RECORD")
	private String progressivoRecord;

	
	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

    public VUlterioriBeneficiari() {
    }

	

	public String getArticolo() {
		return this.articolo;
	}

	public void setArticolo(String articolo) {
		this.articolo = articolo;
	}

	public String getCapitolo() {
		return this.capitolo;
	}

	public void setCapitolo(String capitolo) {
		this.capitolo = capitolo;
	}

	public String getCapo() {
		return this.capo;
	}

	public void setCapo(String capo) {
		this.capo = capo;
	}

	


	public String getCodEntrata() {
		return this.codEntrata;
	}

	public void setCodEntrata(String codEntrata) {
		this.codEntrata = codEntrata;
	}

	public String getCodTipoEntrata() {
		return this.codTipoEntrata;
	}

	public void setCodTipoEntrata(String codTipoEntrata) {
		this.codTipoEntrata = codTipoEntrata;
	}

	
	public String getCodTipoUfficioUltBenef() {
		return this.codTipoUfficioUltBenef;
	}

	public void setCodTipoUfficioUltBenef(String codTipoUfficioUltBenef) {
		this.codTipoUfficioUltBenef = codTipoUfficioUltBenef;
	}

	

	public String getCodUfficioUltBenef() {
		return this.codUfficioUltBenef;
	}

	public void setCodUfficioUltBenef(String codUfficioUltBenef) {
		this.codUfficioUltBenef = codUfficioUltBenef;
	}

	public String getCodUltEnteBenef() {
		return this.codUltEnteBenef;
	}

	public void setCodUltEnteBenef(String codUltEnteBenef) {
		this.codUltEnteBenef = codUltEnteBenef;
	}

	

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public String getImportoAggAnticUltBenef() {
		return this.importoAggAnticUltBenef;
	}

	public void setImportoAggAnticUltBenef(String importoAggAnticUltBenef) {
		this.importoAggAnticUltBenef = importoAggAnticUltBenef;
	}

	public String getImportoAggUltBenef() {
		return this.importoAggUltBenef;
	}

	public void setImportoAggUltBenef(String importoAggUltBenef) {
		this.importoAggUltBenef = importoAggUltBenef;
	}

	public String getImportoArtRuoloUltBenef() {
		return this.importoArtRuoloUltBenef;
	}

	public void setImportoArtRuoloUltBenef(String importoArtRuoloUltBenef) {
		this.importoArtRuoloUltBenef = importoArtRuoloUltBenef;
	}

	public String getImportoCaricoUltBenef() {
		return this.importoCaricoUltBenef;
	}

	public void setImportoCaricoUltBenef(String importoCaricoUltBenef) {
		this.importoCaricoUltBenef = importoCaricoUltBenef;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProgressivoArticoloRuolo() {
		return this.progressivoArticoloRuolo;
	}

	public void setProgressivoArticoloRuolo(String progressivoArticoloRuolo) {
		this.progressivoArticoloRuolo = progressivoArticoloRuolo;
	}

	public String getProgressivoRecord() {
		return this.progressivoRecord;
	}

	public void setProgressivoRecord(String progressivoRecord) {
		this.progressivoRecord = progressivoRecord;
	}



	public String getReFlagElaborato() {
		return this.reFlagElaborato;
	}

	public void setReFlagElaborato(String reFlagElaborato) {
		this.reFlagElaborato = reFlagElaborato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public ChiaveULPartita getChiavePartita() {
		return chiavePartita;
	}



	public void setChiavePartita(ChiaveULPartita chiavePartita) {
		this.chiavePartita = chiavePartita;
	}

	
}
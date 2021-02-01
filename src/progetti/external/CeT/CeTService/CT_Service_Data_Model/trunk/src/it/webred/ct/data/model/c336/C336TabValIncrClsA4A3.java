package it.webred.ct.data.model.c336;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the S_C336_TAB_VAL_INCR_CLS_A4_A3 database table.
 * 
 */
@Entity
@Table(name="S_C336_TAB_VAL_INCR_CLS_A4_A3")
public class C336TabValIncrClsA4A3 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PRATICA")
	private Long idPratica;

	@Column(name="VAL_ASCENSORE")
	private String valAscensore;

	@Column(name="VAL_ASCENSORE_NOTE")
	private String valAscensoreNote;

	@Column(name="VAL_AUMENTO_SERVIZI")
	private String valAumentoServizi;

	@Column(name="VAL_AUMENTO_SERVIZI_NOTE")
	private String valAumentoServiziNote;

	@Column(name="VAL_MIGL_DISTR_VANI_NOTE")
	private String valMiglDistrVaniNote;

	@Column(name="VAL_MIGLIORAMENTO_DISTR_VANI")
	private String valMiglioramentoDistrVani;

	@Column(name="VAL_RISCALDAMENTO")
	private String valRiscaldamento;

	@Column(name="VAL_RISCALDAMENTO_NOTE")
	private String valRiscaldamentoNote;

	@Column(name="VAL_RISTRUTTURAZIONE")
	private String valRistrutturazione;

	@Column(name="VAL_RISTRUTTURAZIONE_NOTE")
	private String valRistrutturazioneNote;
	
	@Transient
	private String valAscensoreDecoded;
	@Transient
	private String valRiscaldamentoDecoded;
	@Transient
	private String valRistrutturazioneDecoded;
	@Transient
	private String valMiglDistrVaniDecoded;
	@Transient
	private String valAumentoServiziDecoded;
	
    public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public C336TabValIncrClsA4A3() {
    }

	public Long getIdPratica() {
		return this.idPratica;
	}

	public void setIdPratica(Long idPratica) {
		this.idPratica = idPratica;
	}

	public String getValAscensore() {
		return this.valAscensore;
	}

	public void setValAscensore(String valAscensore) {
		this.valAscensore = valAscensore;
	}

	public String getValAscensoreNote() {
		return this.valAscensoreNote;
	}

	public void setValAscensoreNote(String valAscensoreNote) {
		this.valAscensoreNote = valAscensoreNote;
	}

	public String getValAumentoServizi() {
		return this.valAumentoServizi;
	}

	public void setValAumentoServizi(String valAumentoServizi) {
		this.valAumentoServizi = valAumentoServizi;
	}

	public String getValAumentoServiziNote() {
		return this.valAumentoServiziNote;
	}

	public void setValAumentoServiziNote(String valAumentoServiziNote) {
		this.valAumentoServiziNote = valAumentoServiziNote;
	}

	public String getValMiglDistrVaniNote() {
		return this.valMiglDistrVaniNote;
	}

	public void setValMiglDistrVaniNote(String valMiglDistrVaniNote) {
		this.valMiglDistrVaniNote = valMiglDistrVaniNote;
	}

	public String getValMiglioramentoDistrVani() {
		return this.valMiglioramentoDistrVani;
	}

	public void setValMiglioramentoDistrVani(String valMiglioramentoDistrVani) {
		this.valMiglioramentoDistrVani = valMiglioramentoDistrVani;
	}

	public String getValRiscaldamento() {
		return this.valRiscaldamento;
	}

	public void setValRiscaldamento(String valRiscaldamento) {
		this.valRiscaldamento = valRiscaldamento;
	}

	public String getValRiscaldamentoNote() {
		return this.valRiscaldamentoNote;
	}

	public void setValRiscaldamentoNote(String valRiscaldamentoNote) {
		this.valRiscaldamentoNote = valRiscaldamentoNote;
	}

	public String getValRistrutturazione() {
		return this.valRistrutturazione;
	}

	public void setValRistrutturazione(String valRistrutturazione) {
		this.valRistrutturazione = valRistrutturazione;
	}

	public String getValRistrutturazioneNote() {
		return this.valRistrutturazioneNote;
	}

	public void setValRistrutturazioneNote(String valRistrutturazioneNote) {
		this.valRistrutturazioneNote = valRistrutturazioneNote;
	}
	
	public String getValAscensoreDecoded() {
		return decodeSiNo(valAscensore);
	}

	public String getValRiscaldamentoDecoded() {
		return decodeSiNo(valRiscaldamento);
	}

	public String getValRistrutturazioneDecoded() {
		return decodeTotaleIParziale(valRistrutturazione);
	}

	public String getValMiglDistrVaniDecoded() {
		this.valMiglDistrVaniDecoded = decodeSiNo(this.valMiglioramentoDistrVani);
		return this.valMiglDistrVaniDecoded;
	}

	public String getValAumentoServiziDecoded() {
		return decodeSiNo(this.valAumentoServizi);
	}
	
	private String decodeSiNo(String val){
		if(val!=null){
			if(val.equals("N"))
				return "No";
			else
				return "Si";
		}else return "";
	}
	
	private String decodeTotaleIParziale(String val){
		if(val!=null){
			if(val.equals("P"))
				return "Parziale";
			else if(val.equals("T"))
				return "Totale";
			else if(val.equals("I"))
				return "Totale/Parziale";
			else
				return "Nessuno";
		}else return "";
	}

}
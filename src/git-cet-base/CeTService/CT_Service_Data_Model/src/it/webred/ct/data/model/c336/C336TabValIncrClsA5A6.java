package it.webred.ct.data.model.c336;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the S_C336_TAB_VAL_INCR_CLS_A5_A6 database table.
 * 
 */
@Entity
@Table(name="S_C336_TAB_VAL_INCR_CLS_A5_A6")
public class C336TabValIncrClsA5A6 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PRATICA")
	private Long idPratica;

	@Column(name="VAL_AGGIUNTA_SERVIZI")
	private String valAggiuntaServizi;

	@Column(name="VAL_AGGIUNTA_SERVIZI_NOTE")
	private String valAggiuntaServiziNote;

	@Column(name="VAL_RISCALDAMENTO")
	private String valRiscaldamento;

	@Column(name="VAL_RISCALDAMENTO_NOTE")
	private String valRiscaldamentoNote;

	@Column(name="VAL_RISTRUTTURAZIONE")
	private String valRistrutturazione;

	@Column(name="VAL_RISTRUTTURAZIONE_NOTE")
	private String valRistrutturazioneNote;
	
	@Transient
	private String valRiscaldamentoDecoded;
	@Transient
	private String valRistrutturazioneDecoded;
	@Transient
	private String valAggiuntaServiziDecoded;

    public C336TabValIncrClsA5A6() {
    }

	public Long getIdPratica() {
		return this.idPratica;
	}

	public void setIdPratica(Long idPratica) {
		this.idPratica = idPratica;
	}

	public String getValAggiuntaServizi() {
		return this.valAggiuntaServizi;
	}

	public void setValAggiuntaServizi(String valAggiuntaServizi) {
		this.valAggiuntaServizi = valAggiuntaServizi;
	}

	public String getValAggiuntaServiziNote() {
		return this.valAggiuntaServiziNote;
	}

	public void setValAggiuntaServiziNote(String valAggiuntaServiziNote) {
		this.valAggiuntaServiziNote = valAggiuntaServiziNote;
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

	public String getValRiscaldamentoDecoded() {
		return decodeSiNo(valRiscaldamento);
	}

	public String getValRistrutturazioneDecoded() {
		return decodeTotaleParziale(valRistrutturazione);
	}

	public String getValAggiuntaServiziDecoded() {
		return decodeSiNo(valAggiuntaServizi);
	}

	private String decodeSiNo(String val){
		if(val!=null){
			if(val.equals("N"))
				return "No";
			else
				return "Si";
		}else return "";
	}
	
	private String decodeTotaleParziale(String val){
		if (val!=null){
			if(val.equals("P"))
				return "Parziale";
			else if(val.equals("T"))
				return "Totale";
			else
				return "Nessuno";
		}else return "";
	}
	
}
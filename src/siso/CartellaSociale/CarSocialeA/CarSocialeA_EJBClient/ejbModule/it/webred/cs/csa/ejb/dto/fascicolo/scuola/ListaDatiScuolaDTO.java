package it.webred.cs.csa.ejb.dto.fascicolo.scuola;

import it.webred.cs.csa.ejb.dto.fascicolo.DatiInterceptorDTO;

import java.util.Date;

public class ListaDatiScuolaDTO extends DatiInterceptorDTO{
	
	public ListaDatiScuolaDTO(Long secondoLivello, Long idSettoreDiario) {
		super(secondoLivello, idSettoreDiario);
	}

	private Long diarioId;
	private String annoScolastico;
	//private Boolean fermo;
	private String grado;
	private String nome;
	//private String progetto;
	private String note;
	private ScuolaDTO scuola;
	private Date dataUltimaModifica;
	private String opModifica;
	private String tipoScuola;
	
	public ScuolaDTO getScuola() {
		return scuola;
	}
	public void setScuola(ScuolaDTO scuola) {
		this.scuola = scuola;
	}
	public Date getDataUltimaModifica() {
		return dataUltimaModifica;
	}
	public void setDataUltimaModifica(Date dataUltimaModifica) {
		this.dataUltimaModifica = dataUltimaModifica;
	}
	
	public String getOpModifica() {
		return opModifica;
	}
	public void setOpModifica(String opModifica) {
		this.opModifica = opModifica;
	}
	public Long getDiarioId() {
		return diarioId;
	}
	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}
	public String getGrado() {
		return grado;
	}
	public void setGrado(String grado) {
		this.grado = grado;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getAnnoScolastico() {
		return annoScolastico;
	}
	public void setAnnoScolastico(String annoScolastico) {
		this.annoScolastico = annoScolastico;
	}
	public String getTipoScuola() {
		return tipoScuola;
	}
	public void setTipoScuola(String tipoScuola) {
		this.tipoScuola = tipoScuola;
	}
	
	public String getNoteRid(){
		if(note!=null && note.length()>40)
			return (note.substring(0,40)+" [...]");
		else
			return note;
	}
}

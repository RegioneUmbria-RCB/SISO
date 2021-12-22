package it.webred.cs.csa.ejb.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class DocIndividualeDTO extends CeTBaseObject{
	private static final long serialVersionUID = 1L;
	
	private long idDiarioRichiestaServizio;
	//private String userIns; 
	private String nome;
	private String tipo;
	private byte[] documento;
	private Long casoId;
	private Long sottoCartella;
	private boolean letto;
	private boolean privato;
	
	public long getIdDiarioRichiestaServizio() {
		return idDiarioRichiestaServizio;
	}
	public void setIdDiarioRichiestaServizio(long idDiarioRichiestaServizio) {
		this.idDiarioRichiestaServizio = idDiarioRichiestaServizio;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public byte[] getDocumento() {
		return documento;
	}
	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}
	public Long getCasoId() {
		return casoId;
	}
	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}
	public Long getSottoCartella() {
		return sottoCartella;
	}
	public void setSottoCartella(Long sottoCartella) {
		this.sottoCartella = sottoCartella;
	}
	public boolean isLetto() {
		return letto;
	}
	public void setLetto(boolean letto) {
		this.letto = letto;
	}
	public boolean isPrivato() {
		return privato;
	}
	public void setPrivato(boolean privato) {
		this.privato = privato;
	} 
}

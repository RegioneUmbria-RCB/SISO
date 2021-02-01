package it.webred.cs.csa.ejb.dto;

public class SearchRdCDTO extends PaginationDTO {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long idAnagrafica;
	private String cf;
	private String cognome;
	private String nome;
	private String ente;
	
	
	private boolean onlyNew;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdAnagrafica() {
		return idAnagrafica;
	}

	public void setIdAnagrafica(Long idAnagrafica) {
		this.idAnagrafica = idAnagrafica;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public boolean isOnlyNew() {
		return onlyNew;
	}

	public void setOnlyNew(boolean onlyNew) {
		this.onlyNew = onlyNew;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
}

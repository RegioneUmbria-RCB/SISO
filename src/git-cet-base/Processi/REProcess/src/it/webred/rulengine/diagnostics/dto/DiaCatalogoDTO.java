package it.webred.rulengine.diagnostics.dto;

import java.io.Serializable;

public class DiaCatalogoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idCatalogo;
	private String fkCodCommand;
	private String descrizione;
	
	
	public DiaCatalogoDTO(Long idCatalogo, String fkCodCommand,
			String descrizione) {
		super();
		this.idCatalogo = idCatalogo;
		this.fkCodCommand = fkCodCommand;
		this.descrizione = descrizione;
	}

	public Long getIdCatalogo() {
		return idCatalogo;
	}

	public void setIdCatalogo(Long idCatalogo) {
		this.idCatalogo = idCatalogo;
	}

	public String getFkCodCommand() {
		return fkCodCommand;
	}

	public void setFkCodCommand(String fkCodCommand) {
		this.fkCodCommand = fkCodCommand;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	
	
	
	
	
	
}

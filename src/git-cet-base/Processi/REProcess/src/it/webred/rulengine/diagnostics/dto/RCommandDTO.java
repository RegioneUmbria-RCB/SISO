package it.webred.rulengine.diagnostics.dto;


import java.io.Serializable;


public class RCommandDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String codCommand;
	private String descr;
	private String longDescr;
	private Long systemCommand;
	private Long rCommandType;
	

	public RCommandDTO(Long id, String codCommand, String descr,
			String longDescr, Long systemCommand, Long rCommandType) {
		
		super();
		
		this.id = id;
		this.codCommand = codCommand;
		this.descr = descr;
		this.longDescr = longDescr;
		this.systemCommand = systemCommand;
		rCommandType = rCommandType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodCommand() {
		return codCommand;
	}

	public void setCodCommand(String codCommand) {
		this.codCommand = codCommand;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getLongDescr() {
		return longDescr;
	}

	public void setLongDescr(String longDescr) {
		this.longDescr = longDescr;
	}

	public Long getSystemCommand() {
		return systemCommand;
	}

	public void setSystemCommand(Long systemCommand) {
		this.systemCommand = systemCommand;
	}

	public Long getRCommandType() {
		return rCommandType;
	}

	public void setRCommandType(Long rCommandType) {
		rCommandType = rCommandType;
	}
	
	
}

package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DIA_CATALOGO")
public class DiaCatalogo implements Serializable{

private static final long serialVersionUID = 1L;
	
	
	@Id
	@Column(name="IDCATALOGODIA")
	private long id;
	
	@Column(name="FK_COD_COMMAND_GROUP")
	private String codCommandGrp;
	
	@Column(name="DESCRIZIONE")
	private String descrizione;
	
	@Column(name="SQLCOMMANDPROP")
	private String sqlCommandProp;
		
	@Column(name="TOOLTIPTEXT")
	private String toolTipText;
	
	@Column(name="ABILITATA")
	private int abilitata;
	
	@Column(name="FK_COD_COMMAND")
	private String codCommand;
	
	@Column(name="CLASS_TYPE_DIA_NON_STD")
	private String classTypeDiaNonStd;
	
	
	public DiaCatalogo() {}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getCodCommandGrp() {
		return codCommandGrp;
	}


	public void setCodCommandGrp(String codCommandGrp) {
		this.codCommandGrp = codCommandGrp;
	}


	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public String getSqlCommandProp() {
		return sqlCommandProp;
	}


	public void setSqlCommandProp(String sqlCommandProp) {
		this.sqlCommandProp = sqlCommandProp;
	}


	public String getToolTipText() {
		return toolTipText;
	}


	public void setToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
	}


	public int getAbilitata() {
		return abilitata;
	}


	public void setAbilitata(int abilitata) {
		this.abilitata = abilitata;
	}


	public String getCodCommand() {
		return codCommand;
	}


	public void setCodCommand(String codCommand) {
		this.codCommand = codCommand;
	}


	public String getClassTypeDiaNonStd() {
		return classTypeDiaNonStd;
	}


	public void setClassTypeDiaNonStd(String classTypeDiaNonStd) {
		this.classTypeDiaNonStd = classTypeDiaNonStd;
	}
	
	
}

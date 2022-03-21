package it.webred.rulengine.impl.bean;

/**
 * Contiene e trasporta i dati relativi alle sorgenti dati, servono al meccanismo di storicizzazione
 * @author marcoric
 *
 */
public class BeanEnteSorgente {
	private Integer id;
	private String codSorgente;
	private String descrizione;
	private boolean disabilitaStorico;
	private boolean inReplace;
	private Integer inReplaceValue;
	
	public String getCodSorgente() {
		return codSorgente;
	}
	public void setCodSorgente(String codSorgente) {
		this.codSorgente = codSorgente;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public boolean isDisabilitaStorico() {
		return disabilitaStorico;
	}
	public void setDisabilitaStorico(boolean disabilitaStorico) {
		this.disabilitaStorico = disabilitaStorico;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setInReplace(boolean b) {
		
		this.inReplace = b;
	}
	public boolean isInReplace() {
		return inReplace;
	}
	public Integer getInReplaceValue() {
		return inReplaceValue;
	}
	public void setInReplaceValue(Integer inReplaceValue) {
		this.inReplaceValue = inReplaceValue;
	}
	

	
}

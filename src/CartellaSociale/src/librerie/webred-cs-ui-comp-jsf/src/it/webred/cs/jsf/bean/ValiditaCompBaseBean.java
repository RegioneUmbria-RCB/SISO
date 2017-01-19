package it.webred.cs.jsf.bean;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.jsf.interfaces.IDatiValidita;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.dto.utility.KeyValuePairBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

public class ValiditaCompBaseBean extends CsUiCompBaseBean implements IDatiValidita{

	private Long id;
	private String tipo;
	private String descrizione;
	private List<KeyValuePairBean> infoAggiuntive;
	private Date dataInizio;
	private Date dataFine;
	private Date dataTemp = new Date();
	private boolean prevalente;
	//private String appartenenza;
	
	protected void reset() {
		id = null;
		dataFine = null;
		dataInizio = null;
		prevalente=false;
		
	}
	
	@Override
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public Date getDataInizio() {
		return dataInizio;
	}
	
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}
	
	@Override
	public Date getDataFine() {
		return dataFine;
	}
	
	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}
	
	@Override
	public boolean isAttivo() {
		Date dataRif = new Date();
		boolean dtIniPrecedente = dataInizio==null || !dataInizio.after(dataRif);
		boolean dtFinSuccessiva = dataFine==null   || !dataFine.before(dataRif);
	    
	    return 	dtIniPrecedente && 	dtFinSuccessiva;
	}
	
	@Override
	public boolean isFinito() {
		if (dataFine != null) 
			return dataFine.compareTo(DataModelCostanti.END_DATE) != 0;
		else return false;
	}

	@Override
	public Date getDataTemp() {
		return dataTemp;
	}

	public void setDataTemp(Date dataTemp) {
		this.dataTemp = dataTemp;
	}

	@Override
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	@Override
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public boolean isPrevalente() {
		return prevalente;
	}

	public void setPrevalente(boolean prevalente) {
		this.prevalente = prevalente;
	}

	public List<KeyValuePairBean> getInfoAggiuntive() {
		return infoAggiuntive;
	}

	public void setInfoAggiuntive(List<KeyValuePairBean> infoAggiuntive) {
		this.infoAggiuntive = infoAggiuntive;
	}
	
}

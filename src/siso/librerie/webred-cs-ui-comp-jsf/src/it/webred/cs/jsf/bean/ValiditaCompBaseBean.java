package it.webred.cs.jsf.bean;

import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.jsf.interfaces.IDatiValidita;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.dto.utility.KeyValuePairBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ValiditaCompBaseBean extends CsUiCompBaseBean implements IDatiValidita{

	protected SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
	protected SimpleDateFormat ddMMyyyyhhmmss = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	private Long id;
	private String tipo;
	private String descrizione;
	private List<KeyValuePairBean> infoAggiuntive;
	private Date dataInizio;
	private Date dataFine;
	private Date dataTemp = new Date();
	private boolean prevalente;
	private String usrInserimento;
	private String usrModifica;
	private Date dataInserimento;
	private Date dataModifica;
	
	private String tipologia; //SISO-812
	
	private Long idSettore;
	private Long idOrganizzazione; //idOrganizzazione
	private Boolean nascondiInformazioni=false;
	
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
	//SISO-1060
	public String getUsrInserimento() {
		return usrInserimento;
	}

	public void setUsrInserimento(String usrInserimento) {
		this.usrInserimento = usrInserimento;
	}

	public String getUsrModifica() {
		return usrModifica;
	}

	public void setUsrModifica(String usrModifica) {
		this.usrModifica = usrModifica;
	}

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Date getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}
	//Fine SISO-1060
	
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

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia= tipologia;
	}

	public Long getIdSettore() {
		return idSettore;
	}

    public void setIdSettore(Long idSettore) {
		this.idSettore = idSettore;
	}

	public Long getIdOrganizzazione() {
		return idOrganizzazione;
	}

	public void setIdOrganizzazione(Long idOrganizzazione) {
		this.idOrganizzazione = idOrganizzazione;
	}

	public Boolean getNascondiInformazioni() {
		return nascondiInformazioni;
	}

	public void setNascondiInformazioni(Boolean nascondiInformazioni) {
		this.nascondiInformazioni = nascondiInformazioni;
	}

	}

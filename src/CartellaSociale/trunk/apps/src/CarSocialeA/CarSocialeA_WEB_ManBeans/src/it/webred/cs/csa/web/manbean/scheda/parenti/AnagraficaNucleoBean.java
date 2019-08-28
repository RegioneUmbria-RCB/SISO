package it.webred.cs.csa.web.manbean.scheda.parenti;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.jsf.bean.SessoBean;

public abstract class AnagraficaNucleoBean extends CsUiCompBaseBean {

	private String msgInfoRecapiti;
	private String cognome;
	private String nome;
	private String codFiscale;
	private SessoBean datiSesso=new SessoBean();
	private Date dataNascita;
	private ComuneNazioneNascitaBean comuneNazioneNascitaBean = new ComuneNazioneNascitaBean();
	private String indirizzo;
	private String civico;
	private ComuneNazioneResidenzaBean comuneNazioneResidenzaBean = new ComuneNazioneResidenzaBean();
	
	private String email;
	private String telefono;
	private String cellulare;
	private String note;
	
	private Long idParentela;
	private CsTbTipoRapportoCon rapportoModel;
	private List<SelectItem> lstParentela;
	private boolean convivente;

	
	public void reset() {
		cognome = null;
		nome = null;
		datiSesso = new SessoBean();
		
		codFiscale = null;
		indirizzo = null;
		civico = null;
		telefono = null;
		cellulare = null;
		email = null;
		note = null;
		
		idParentela = null;
		convivente = false;
		rapportoModel = null;
	
		dataNascita = null;
		comuneNazioneNascitaBean.getComuneMan().setComune(null);
		comuneNazioneResidenzaBean.getComuneMan().setComune(null);
	}
	
	
	public String getMsgInfoRecapiti() {
		return msgInfoRecapiti;
	}
	public String getCognome() {
		return cognome;
	}
	public String getNome() {
		return nome;
	}
	public String getCodFiscale() {
		return codFiscale;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public String getCivico() {
		return civico;
	}
	public String getEmail() {
		return email;
	}
	public String getTelefono() {
		return telefono;
	}
	public String getCellulare() {
		return cellulare;
	}
	public void setMsgInfoRecapiti(String msgInfoRecapiti) {
		this.msgInfoRecapiti = msgInfoRecapiti;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public ComuneNazioneNascitaBean getComuneNazioneNascitaBean() {
		return comuneNazioneNascitaBean;
	}
	public ComuneNazioneResidenzaBean getComuneNazioneResidenzaBean() {
		return comuneNazioneResidenzaBean;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public void setComuneNazioneNascitaBean(ComuneNazioneNascitaBean comuneNazioneNascitaBean) {
		this.comuneNazioneNascitaBean = comuneNazioneNascitaBean;
	}
	public void setComuneNazioneResidenzaBean(ComuneNazioneResidenzaBean comuneNazioneResidenzaBean) {
		this.comuneNazioneResidenzaBean = comuneNazioneResidenzaBean;
	}
	public SessoBean getDatiSesso() {
		return datiSesso;
	}
	public void setDatiSesso(SessoBean datiSesso) {
		this.datiSesso = datiSesso;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public Long getIdParentela() {
		return idParentela;
	}
	
	public CsTbTipoRapportoCon getRapportoModel() {
		return rapportoModel;
	}
	
	public void setRapportoModel(CsTbTipoRapportoCon rapportoModel) {
		this.rapportoModel = rapportoModel;
	}
	
	public List<SelectItem> getLstParentela() {
		if(lstParentela == null){
			lstParentela = new ArrayList<SelectItem>();
			List<CsTbTipoRapportoCon> lstParentelaModel = getLstParentelaModel(); // = confService.getTipoRapportoConParenti(bo);
			if (lstParentelaModel != null) {
				for (CsTbTipoRapportoCon obj : lstParentelaModel) {
					lstParentela.add(new SelectItem(obj.getId().longValue(), obj.getDescrizione()));
				}
			}		
		}
		
		return lstParentela;
	}

	public void setLstParentela(List<SelectItem> lstParentela) {
		this.lstParentela = lstParentela;
	}

	public abstract List<CsTbTipoRapportoCon> getLstParentelaModel();
	
	public void setIdParentela(Long idParentela) {
		this.idParentela = idParentela;
		if(lstParentela!= null){
			for(CsTbTipoRapportoCon item: getLstParentelaModel()){
				if(idParentela.longValue() == item.getId().longValue())
					this.rapportoModel = item;
			}
		}
		
	}
	
	public boolean getConvivente() {
		return convivente;
	}

	public void setConvivente(boolean convivente) {
		this.convivente = convivente;
	}

}

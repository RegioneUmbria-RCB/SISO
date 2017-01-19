package it.webred.cs.csa.web.manbean.scheda.parenti;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.jsf.interfaces.INuovoConoscente;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;


public class NuovoConoscenteBean extends CsUiCompBaseBean implements INuovoConoscente{

	private AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");

	private String dialogHeader = "Anagrafica conoscente";
	private String cognome;
	private String nome;
	private String indirizzo;
	private String civico;
	private String telefono;
	private String cellulare;
	private String note;
	private Long idParentela;
	private CsTbTipoRapportoCon rapportoModel;
	
	private List<CsTbTipoRapportoCon> lstParentelaModel;
	private List<SelectItem> lstParentela;
	
	private ComuneNazioneResidenzaBean comuneNazioneResidenzaBean = new ComuneNazioneResidenzaBean();
	
	@Override
	public void reset() {
		cognome = null;
		nome = null;
		indirizzo = null;
		civico = null;
		telefono = null;
		cellulare = null;
		note = null;
		idParentela = null;
		comuneNazioneResidenzaBean.getComuneMan().setComune(null);
	}

	public String getDialogHeader() {
		return dialogHeader;
	}

	public void setDialogHeader(String dialogHeader) {
		this.dialogHeader = dialogHeader;
	}

	@Override
	public Long getIdParentela() {
		return idParentela;
	}

	public void setIdParentela(Long idParentela) {
		if(lstParentela != null){
			for(CsTbTipoRapportoCon item: lstParentelaModel){
				if(idParentela.equals(item.getId()))
					this.rapportoModel = item;
			}
		}
		this.idParentela = idParentela;
	}

	@Override
	public List<SelectItem> getLstParentela() {
		
		if(lstParentela == null){
			lstParentela = new ArrayList<SelectItem>();
			lstParentela.add(new SelectItem("", "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			lstParentelaModel = confService.getTipoRapportoConConoscenti(bo);
			if (lstParentelaModel != null) {
				for (CsTbTipoRapportoCon obj : lstParentelaModel) {
					lstParentela.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}		
		}
		
		return lstParentela;
	}

	public void setLstParentela(List<SelectItem> lstParentela) {
		this.lstParentela = lstParentela;
	}

	@Override
	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	@Override
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	@Override
	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	@Override
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public String getCellulare() {
		return cellulare;
	}

	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}

	@Override
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public ComuneNazioneResidenzaBean getComuneNazioneResidenzaBean() {
		return comuneNazioneResidenzaBean;
	}

	public void setComuneNazioneResidenzaBean(
			ComuneNazioneResidenzaBean comuneNazioneResidenzaBean) {
		this.comuneNazioneResidenzaBean = comuneNazioneResidenzaBean;
	}

	@Override
	public CsTbTipoRapportoCon getRapportoModel() {
		return this.rapportoModel;
	}
	
}

package it.webred.cs.csa.web.manbean.interscambio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.hl7.v3.request.PRSSIN001004ZZ;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import it.umbriadigitale.argo.ejb.client.cs.bean.ArInterscambioService;
import it.umbriadigitale.argo.ejb.client.cs.dto.OrgInterscambioDTO;
import it.umbriadigitale.interscambio.utils.XMLReaderUtils;
import it.umbriadigitale.interscambio.utils.XMLValidationUtils;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.EventoDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.alert.AlertDTO;
import it.webred.cs.csa.web.manbean.listacasi.LazyListaCasiModel;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.jsf.interfaces.IDialogEvento;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class InterscambioDialogEventoBean extends CsUiCompBaseBean implements IDialogEvento {

	private InterscambioCartellaSociale ics = new InterscambioCartellaSociale();

	private List<SelectItem> listaDestinatari;

	private OrgInterscambioDTO orgInterscambioCorrente;
	private OrgInterscambioDTO orgInterscambioDest;
	private String selCodDestinatario;
	
	private Date exportDate = new Date();

	public InterscambioDialogEventoBean() {
	}


	@Override
	public List<SelectItem> getListaDestinatari() {
		if(this.listaDestinatari==null){
			listaDestinatari = new ArrayList<SelectItem>();
			ArInterscambioService serv = this.ics.getArgoService();
			List<OrgInterscambioDTO> orgExport = serv.getListaComuniInterscambio();
			for(OrgInterscambioDTO o : orgExport){
				SelectItem si = new SelectItem(o.getCodiceOrg(), o.getNome());
				this.getOrgInterscambioCorrente();
				boolean disabled = !o.getAbilitato() || (orgInterscambioCorrente!=null && o.getCodiceOrg().equalsIgnoreCase(this.orgInterscambioCorrente.getCodiceOrg()));
				si.setDisabled(disabled);
				listaDestinatari.add(si);
			}
		}
		return listaDestinatari;
	}

	
	@Override
	public OrgInterscambioDTO getOrgInterscambioCorrente(){
		CeTBaseObject cet = new CeTBaseObject();
		fillEnte(cet);
		ArInterscambioService serv = this.ics.getArgoService();
		this.orgInterscambioCorrente = serv.getOrgInterscambioByBelfiore(cet.getEnteId());
		return this.orgInterscambioCorrente;
	}

	@Override
	public String getSelCodDestinatario() {
		return selCodDestinatario;
	}

	@Override
	public void setSelCodDestinatario(String selCodDestinatario) {
		this.selCodDestinatario = selCodDestinatario;
		CeTBaseObject cet = new CeTBaseObject();
		fillEnte(cet);
		ArInterscambioService serv = this.ics.getArgoService();
		this.orgInterscambioDest = serv.getOrgInterscambioByCodOrg(selCodDestinatario);
	}

	@Override
	public OrgInterscambioDTO getOrgInterscambioDest() {
		return orgInterscambioDest;
	}

	@Override
	public void setOrgInterscambioDest(OrgInterscambioDTO orgInterscambioDest) {
		this.orgInterscambioDest = orgInterscambioDest;
	}

	@Override
	public Date getExportDate() {
		return exportDate;
	}


	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}

	public void setListaDestinatari(List<SelectItem> listaDestinatari) {
		this.listaDestinatari = listaDestinatari;
	}

	public void setOrgInterscambioCorrente(
			OrgInterscambioDTO orgInterscambioCorrente) {
		this.orgInterscambioCorrente = orgInterscambioCorrente;
	}
	

}

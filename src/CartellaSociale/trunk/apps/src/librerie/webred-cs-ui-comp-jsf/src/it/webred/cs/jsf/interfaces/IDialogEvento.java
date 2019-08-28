package it.webred.cs.jsf.interfaces;

import it.umbriadigitale.argo.ejb.client.cs.dto.OrgInterscambioDTO;

import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;


public interface IDialogEvento {

	public List<SelectItem> getListaDestinatari();

	public OrgInterscambioDTO getOrgInterscambioCorrente();

	public OrgInterscambioDTO getOrgInterscambioDest();

	public Date getExportDate();

	public String getSelCodDestinatario();
	public void setSelCodDestinatario(String selCodDestinatario);

	public void setOrgInterscambioDest(OrgInterscambioDTO orgInterscambioDest);

	

}
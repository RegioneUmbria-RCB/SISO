package it.umbriadigitale.argo.ejb.client.cs.bean;

import it.umbriadigitale.argo.ejb.client.cs.dto.OrgInterscambioDTO;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface ArInterscambioService {

	public List<OrgInterscambioDTO> getListaComuniInterscambio();

	public OrgInterscambioDTO getOrgInterscambioByBelfiore(String belfiore);

	public OrgInterscambioDTO getOrgInterscambioByCodOrg(String selCodDestinatario);
}

package it.umbriadigitale.argo.ejb;

import it.umbriadigitale.argo.data.cs.data.ArOOrgImpExp;
import it.umbriadigitale.argo.data.cs.data.ArOOrganizzazione;
import it.umbriadigitale.argo.data.cs.data.ArOOrganizzazioneEst;
import it.umbriadigitale.argo.ejb.base.dao.ArCsInterscambioDAO;
import it.umbriadigitale.argo.ejb.client.cs.bean.ArInterscambioService;
import it.umbriadigitale.argo.ejb.client.cs.dto.OrgInterscambioDTO;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class ArInterscambioServiceBean implements ArInterscambioService {

	@Autowired
	private ArCsInterscambioDAO arInterscambioDAO;
	
	
	@Override
	public List<OrgInterscambioDTO> getListaComuniInterscambio() {

		List<ArOOrgImpExp> lst = arInterscambioDAO.getListaComuniInterscambio();
		List<OrgInterscambioDTO> lstOut = new ArrayList<OrgInterscambioDTO>();
		for(ArOOrgImpExp o : lst){
			
			OrgInterscambioDTO dto = this.fillOrgInterscambioDTO(o);
			
			ArOOrganizzazione org = o.getArOOrganizzazione();
			ArOOrganizzazioneEst orgExt = o.getArOOrganizzazioneEst();
			
			if(org!=null || orgExt!=null){
				if(dto.getAbilitato().booleanValue())
					lstOut.add(dto);
			}
		}
		
		return lstOut;
	}
	
	private OrgInterscambioDTO fillOrgInterscambioDTO(ArOOrgImpExp o){
		if(o!=null){
			OrgInterscambioDTO dto = new OrgInterscambioDTO();
			dto.setCodiceOrg(o.getCodiceOrg());
			dto.setFlussoTipo(o.getFlussoTipo());
			dto.setFlussoVer(o.getFlussoVer());
			
			ArOOrganizzazione org = o.getArOOrganizzazione();
			ArOOrganizzazioneEst orgExt = o.getArOOrganizzazioneEst();
			
			if(o.getArOOrganizzazione()!=null){	 
				dto.setId(org.getId());
				dto.setBelfiore(org.getBelfiore());
				dto.setEmail(org.getEmail());
				dto.setNome(org.getNome());
				dto.setPivaCf(org.getPivaCf());
				dto.setZonaSociale(org.getZonaNome());
				dto.setAbilitato(org.getAbilitato());
				
			}else if(o.getArOOrganizzazioneEst()!=null){
				dto.setId(orgExt.getId());
				dto.setBelfiore(orgExt.getBelfiore());
				dto.setEmail(orgExt.getEmail());
				dto.setNome(orgExt.getNome());
				dto.setPivaCf(orgExt.getPivaCf());
				dto.setAbilitato(orgExt.getAbilitato());	
			}
			return dto;
		}
		return null;
	}


	@Override
	public OrgInterscambioDTO getOrgInterscambioByBelfiore(String belfiore) {
		ArOOrgImpExp o = arInterscambioDAO.getOrgInterscambioByBelfiore(belfiore);
		return this.fillOrgInterscambioDTO(o);
	}

	@Override
	public OrgInterscambioDTO getOrgInterscambioByCodOrg(String selCodDestinatario) {
		ArOOrgImpExp o = arInterscambioDAO.getOrgInterscambioByCodOrg(selCodDestinatario);
		return this.fillOrgInterscambioDTO(o);
	}

	

}

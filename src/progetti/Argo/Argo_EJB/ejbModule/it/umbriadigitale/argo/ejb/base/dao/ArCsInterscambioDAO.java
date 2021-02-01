package it.umbriadigitale.argo.ejb.base.dao;

import it.umbriadigitale.argo.data.cs.data.ArOOrgImpExp;
import it.umbriadigitale.argo.ejb.ArgoBaseDAO;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class ArCsInterscambioDAO extends ArgoBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<ArOOrgImpExp> getListaComuniInterscambio(){
		Query q = em.createNamedQuery("ArOOrgImpExp.findExportOrg");
		return  q.getResultList();
		
	}

	public ArOOrgImpExp getOrgInterscambioByBelfiore(String belfiore){
		String sql = "select ie.* from ar_o_org_imp_exp ie "+
					"			left join ar_o_organizzazione o on ie.organizzazione_id= o.id "+
					"			left join ar_o_organizzazione_est oe on IE.ORGANIZZAZIONE_EST_ID= oe.id "+
					"			where o.belfiore = :belfiore or oe.belfiore=:belfiore ";
		Query q = em.createNativeQuery(sql, ArOOrgImpExp.class);
		q.setParameter("belfiore", belfiore);
		List<ArOOrgImpExp> res = q.getResultList();
		if(!res.isEmpty()) return res.get(0);
		return null;
	}

	public ArOOrgImpExp getOrgInterscambioByCodOrg(String selCodDestinatario) {
		Query q = em.createNamedQuery("ArOOrgImpExp.findExportOrgByCodOrg");
		q.setParameter("codOrg", selCodDestinatario);
		List<ArOOrgImpExp> res = q.getResultList();
		if(!res.isEmpty()) return res.get(0);
		return null;
	}
}

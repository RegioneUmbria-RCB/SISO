package it.webred.cs.csa.ejb.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.data.model.CsSchedeAltraProvenienza;

@Named
public class SchedaSegrVistaCasiAltriDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	public CsSchedeAltraProvenienza findVistaCasiAltriBySchedaIdProvenienza(Long idSchedaSegr, String provenienza) {
		Query q = em.createNamedQuery("CsSchedeAltraProvenienza.findVistaCasiAltriBySchedaIdProvenienza")
				.setParameter("idSchedaSegr", idSchedaSegr).setParameter("provenienza", provenienza);

		List<CsSchedeAltraProvenienza> lista = q.getResultList();
		if (lista != null && lista.size() > 0)
			return lista.get(0);

		return null;
	}

	public CsSchedeAltraProvenienza saveVistaCasiAltri(CsSchedeAltraProvenienza scheda) {
		return em.merge(scheda);
	}
}

package it.webred.cs.csa.ejb.dto.erogazioni;

import it.webred.cs.csa.ejb.dto.erogazioni.configurazione.ErogStatoCfgDTO;
import it.webred.cs.data.model.CsCfgAttr;
import it.webred.cs.data.model.CsCfgIntEsegAttUm;
import it.webred.cs.data.model.CsCfgIntEsegStatoInt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class IntEsegAttrUtilsBean {

	protected static IntEsegAttrBean Contains(List<IntEsegAttrBean> lstAttr, CsCfgAttr attr) {
		for (IntEsegAttrBean bean : lstAttr) {
			if (bean.getAttributo().getId() == attr.getId())
				return bean;
		}
		return null;
	}
	
	public static HashMap<Long, ErogStatoCfgDTO> getMappaStatoAttributi(List<CsCfgIntEsegStatoInt> listaStatiConfig) {
		HashMap<Long, ErogStatoCfgDTO> mappa = new HashMap<Long, ErogStatoCfgDTO>();
		if(listaStatiConfig!=null){
			for(CsCfgIntEsegStatoInt si: listaStatiConfig){
				List<CsCfgIntEsegAttUm> csCfgIntEsegAttUms = si.getCsCfgIntEsegAttUms();
				HashMap<Long,IntEsegAttrBean> mappaAttr = new HashMap<Long,IntEsegAttrBean>();
				if(csCfgIntEsegAttUms!=null){
					for (CsCfgIntEsegAttUm item : csCfgIntEsegAttUms) {
						Long idAttributo = item.getCsCfgAttrUm().getCsCfgAttributo().getId();
						IntEsegAttrBean bean = mappaAttr.get(idAttributo);
						
						if (bean == null) bean = new IntEsegAttrBean(item);
						else bean.addUnitaMisura(item.getCsCfgAttrUm());
						
						mappaAttr.put(idAttributo,bean);
					}
					
					
					List<IntEsegAttrBean> lst = new ArrayList<IntEsegAttrBean>();
					lst.addAll(mappaAttr.values());
					ErogStatoCfgDTO statodto = new ErogStatoCfgDTO(si.getCsCfgIntEsegStato(), lst);
					mappa.put(si.getCsCfgIntEsegStato().getId(), statodto);
				}
			}
		}
		return mappa;
	}
	
}

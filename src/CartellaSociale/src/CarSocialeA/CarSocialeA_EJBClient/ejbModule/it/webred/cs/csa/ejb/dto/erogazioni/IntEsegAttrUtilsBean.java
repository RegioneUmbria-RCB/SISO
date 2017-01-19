package it.webred.cs.csa.ejb.dto.erogazioni;

import java.util.LinkedList;
import java.util.List;

import it.webred.cs.data.model.CsCfgAttr;
import it.webred.cs.data.model.CsCfgAttrUnitaMisura;
import it.webred.cs.data.model.CsCfgIntEsegAttUm;
import it.webred.cs.data.model.CsTbUnitaMisura;

public class IntEsegAttrUtilsBean {

	protected static IntEsegAttrBean Contains(List<IntEsegAttrBean> lstAttr, CsCfgAttr attr) {
		for (IntEsegAttrBean bean : lstAttr) {
			if (bean.getCsCfgAttr().getId() == attr.getId())
				return bean;
		}
		return null;
	}

	public static List<IntEsegAttrBean> Initialize(List<CsCfgIntEsegAttUm> csCfgIntEsegAttUms) {
		List<IntEsegAttrBean> lstAttr = new LinkedList<IntEsegAttrBean>();
		if(csCfgIntEsegAttUms!=null){
			for (CsCfgIntEsegAttUm item : csCfgIntEsegAttUms) {
				IntEsegAttrBean bean = Contains(lstAttr, item.getCsCfgAttrUm().getCsCfgAttributo());
				if (bean == null) {
					bean = new IntEsegAttrBean(item);
					lstAttr.add(bean);
				}
			}
		}
		return lstAttr;
	}
}

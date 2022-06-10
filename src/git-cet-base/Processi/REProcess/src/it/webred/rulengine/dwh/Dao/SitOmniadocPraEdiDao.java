package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitOmniadocPraEdi;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitOmniadocPraEdiDao extends TabellaDwhDao {

	public SitOmniadocPraEdiDao(TabellaDwh tab) {
		super(tab);
	}//-------------------------------------------------------------------------

	public SitOmniadocPraEdiDao(SitOmniadocPraEdi tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}


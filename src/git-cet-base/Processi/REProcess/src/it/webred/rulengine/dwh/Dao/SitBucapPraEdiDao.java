package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitBucapPraEdi;

import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitBucapPraEdiDao extends TabellaDwhDao {

	public SitBucapPraEdiDao(TabellaDwh tab) {
		super(tab);
	}//-------------------------------------------------------------------------

	public SitBucapPraEdiDao(SitBucapPraEdi tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}


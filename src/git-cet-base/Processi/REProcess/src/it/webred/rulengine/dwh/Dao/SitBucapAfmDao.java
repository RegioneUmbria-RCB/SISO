package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitBucapAfm;


import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitBucapAfmDao extends TabellaDwhDao {

	public SitBucapAfmDao(TabellaDwh tab) {
		super(tab);
	}//-------------------------------------------------------------------------

	public SitBucapAfmDao(SitBucapAfm tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}


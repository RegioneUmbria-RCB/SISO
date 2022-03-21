package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitCurit;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitCuritDao extends TabellaDwhDao {

	public SitCuritDao(TabellaDwh tab) {
		super(tab);
	}//-------------------------------------------------------------------------

	public SitCuritDao(SitCurit tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}


package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitSchedeMartelli;

import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitSchedeMartelliDao extends TabellaDwhDao {

	public SitSchedeMartelliDao(TabellaDwh tab) {
		super(tab);
	}//-------------------------------------------------------------------------

	public SitSchedeMartelliDao(SitSchedeMartelli tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}


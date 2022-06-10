package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitSciaJpe;



import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitSciaJpeDao extends TabellaDwhDao {

	public SitSciaJpeDao(TabellaDwh tab) {
		super(tab);
	}//-------------------------------------------------------------------------

	public SitSciaJpeDao(SitSciaJpe tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}


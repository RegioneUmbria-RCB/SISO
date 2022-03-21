package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTIciVia;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTIciViaDao extends TabellaDwhDao {

	public SitTIciViaDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTIciViaDao(SitTIciVia tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

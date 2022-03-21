package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTIciOggUltsogg;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTIciOggUltsoggDao extends TabellaDwhDao {

	public SitTIciOggUltsoggDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTIciOggUltsoggDao(SitTIciOggUltsogg tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

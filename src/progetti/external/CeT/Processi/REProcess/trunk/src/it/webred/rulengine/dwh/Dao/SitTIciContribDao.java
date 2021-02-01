package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTIciContrib;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTIciContribDao extends TabellaDwhDao {

	public SitTIciContribDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTIciContribDao(SitTIciContrib tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTIciContit;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTIciContitDao extends TabellaDwhDao {

	public SitTIciContitDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTIciContitDao(SitTIciContit tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

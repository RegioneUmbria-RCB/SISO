package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTIciSogg;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTIciSoggDao extends TabellaDwhDao {

	public SitTIciSoggDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTIciSoggDao(SitTIciSogg tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

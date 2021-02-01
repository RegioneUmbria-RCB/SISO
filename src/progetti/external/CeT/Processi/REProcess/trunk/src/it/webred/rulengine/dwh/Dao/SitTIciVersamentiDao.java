package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTIciVersamenti;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTIciVersamentiDao extends TabellaDwhDao {

	public SitTIciVersamentiDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTIciVersamentiDao(SitTIciVersamenti tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTIciUltSogg;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTIciUltSoggDao extends TabellaDwhDao {

	public SitTIciUltSoggDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTIciUltSoggDao(SitTIciUltSogg tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

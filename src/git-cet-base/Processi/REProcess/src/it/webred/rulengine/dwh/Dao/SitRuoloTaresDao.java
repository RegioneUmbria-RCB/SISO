package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitRuoloTares;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitRuoloTaresDao extends TabellaDwhDao {

	public SitRuoloTaresDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitRuoloTaresDao(SitRuoloTares tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

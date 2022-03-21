package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitRuoloTarsuSt;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitRuoloTarsuStDao extends TabellaDwhDao {

	public SitRuoloTarsuStDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	
	public SitRuoloTarsuStDao(SitRuoloTarsuSt tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

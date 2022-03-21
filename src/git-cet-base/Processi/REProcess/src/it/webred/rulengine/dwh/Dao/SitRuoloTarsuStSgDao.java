package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitRuoloTarsuStSg;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitRuoloTarsuStSgDao extends TabellaDwhDao {

	public SitRuoloTarsuStSgDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	
	public SitRuoloTarsuStSgDao(SitRuoloTarsuStSg tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

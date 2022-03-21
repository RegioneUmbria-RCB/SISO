package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitRuoloTaresStSg;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitRuoloTaresStSgDao extends TabellaDwhDao {

	public SitRuoloTaresStSgDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	
	public SitRuoloTaresStSgDao(SitRuoloTaresStSg tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

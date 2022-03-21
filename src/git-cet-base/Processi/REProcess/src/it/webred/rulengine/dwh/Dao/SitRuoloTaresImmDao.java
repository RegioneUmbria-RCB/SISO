package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitRuoloTaresImm;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitRuoloTaresImmDao extends TabellaDwhDao {

	public SitRuoloTaresImmDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitRuoloTaresImmDao(SitRuoloTaresImm tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

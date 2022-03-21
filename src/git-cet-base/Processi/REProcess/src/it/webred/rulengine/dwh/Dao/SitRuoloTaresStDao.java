package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitRuoloTaresSt;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitRuoloTaresStDao extends TabellaDwhDao {

	public SitRuoloTaresStDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	
	public SitRuoloTaresStDao(SitRuoloTaresSt tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

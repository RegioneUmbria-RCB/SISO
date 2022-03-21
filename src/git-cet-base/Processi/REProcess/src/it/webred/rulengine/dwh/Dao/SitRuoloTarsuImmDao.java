package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitRuoloTarsuImm;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitRuoloTarsuImmDao extends TabellaDwhDao {

	public SitRuoloTarsuImmDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitRuoloTarsuImmDao(SitRuoloTarsuImm tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

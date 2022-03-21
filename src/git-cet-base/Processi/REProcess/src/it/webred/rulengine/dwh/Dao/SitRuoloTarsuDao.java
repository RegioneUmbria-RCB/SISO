package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitRuoloTarsu;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitRuoloTarsuDao extends TabellaDwhDao {

	public SitRuoloTarsuDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitRuoloTarsuDao(SitRuoloTarsu tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

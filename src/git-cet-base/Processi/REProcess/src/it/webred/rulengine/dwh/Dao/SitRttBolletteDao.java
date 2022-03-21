package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitRttBollette;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitRttBolletteDao extends TabellaDwhDao {

	public SitRttBolletteDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitRttBolletteDao(SitRttBollette tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

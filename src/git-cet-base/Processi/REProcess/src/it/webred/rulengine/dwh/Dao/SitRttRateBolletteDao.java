package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitRttRateBollette;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitRttRateBolletteDao extends TabellaDwhDao {

	public SitRttRateBolletteDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitRttRateBolletteDao(SitRttRateBollette tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

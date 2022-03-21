package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitSclClassi;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitSclClassiDao extends TabellaDwhDao {

	public SitSclClassiDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitSclClassiDao(SitSclClassi tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

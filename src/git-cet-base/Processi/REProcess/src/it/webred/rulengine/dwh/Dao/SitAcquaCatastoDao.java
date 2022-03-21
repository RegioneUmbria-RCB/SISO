package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitAcquaCatasto;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitAcquaCatastoDao extends TabellaDwhDao {

	public SitAcquaCatastoDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitAcquaCatastoDao(SitAcquaCatasto tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

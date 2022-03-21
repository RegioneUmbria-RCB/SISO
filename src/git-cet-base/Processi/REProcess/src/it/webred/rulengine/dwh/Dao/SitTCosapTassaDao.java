package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTCosapTassa;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTCosapTassaDao extends TabellaDwhDao {

	public SitTCosapTassaDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTCosapTassaDao(SitTCosapTassa tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTCosapContrib;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTCosapContribDao extends TabellaDwhDao {

	public SitTCosapContribDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTCosapContribDao(SitTCosapContrib tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

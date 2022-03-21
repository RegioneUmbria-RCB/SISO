package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTTarContrib;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTTarContribDao extends TabellaDwhDao {

	public SitTTarContribDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTTarContribDao(SitTTarContrib tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

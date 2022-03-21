package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTF24IdAccredito;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTF24IdAccreditoDao extends TabellaDwhDao {

	public SitTF24IdAccreditoDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTF24IdAccreditoDao(SitTF24IdAccredito tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTF24Accredito;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTF24AccreditoDao extends TabellaDwhDao {

	public SitTF24AccreditoDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTF24AccreditoDao(SitTF24Accredito tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

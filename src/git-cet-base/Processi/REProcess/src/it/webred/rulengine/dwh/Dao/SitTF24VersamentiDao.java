package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTF24Versamenti;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTF24VersamentiDao extends TabellaDwhDao {

	public SitTF24VersamentiDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTF24VersamentiDao(SitTF24Versamenti tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

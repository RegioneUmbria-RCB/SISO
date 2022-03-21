package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTF24Recupero;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTF24RecuperoDao extends TabellaDwhDao {

	public SitTF24RecuperoDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTF24RecuperoDao(SitTF24Recupero tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

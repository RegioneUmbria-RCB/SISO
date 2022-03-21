package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTF24Testata;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTF24TestataDao extends TabellaDwhDao {

	public SitTF24TestataDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTF24TestataDao(SitTF24Testata tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTF24Anticipo;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTF24AnticipoDao extends TabellaDwhDao {

	public SitTF24AnticipoDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTF24AnticipoDao(SitTF24Anticipo tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTTarDich;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTTarDichDao extends TabellaDwhDao {

	public SitTTarDichDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTTarDichDao(SitTTarDich tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}
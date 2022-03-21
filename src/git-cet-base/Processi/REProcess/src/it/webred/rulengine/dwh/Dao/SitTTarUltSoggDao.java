package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTTarUltSogg;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTTarUltSoggDao extends TabellaDwhDao {

	public SitTTarUltSoggDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTTarUltSoggDao(SitTTarUltSogg tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

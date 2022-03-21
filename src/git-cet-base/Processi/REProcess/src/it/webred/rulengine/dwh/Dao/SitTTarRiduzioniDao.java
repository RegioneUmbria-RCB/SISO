package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTTarRiduzioni;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTTarRiduzioniDao extends TabellaDwhDao {

	public SitTTarRiduzioniDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTTarRiduzioniDao(SitTTarRiduzioni tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

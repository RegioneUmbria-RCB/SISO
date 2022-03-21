package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTTarOggetto;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTTarOggettoDao extends TabellaDwhDao {

	public SitTTarOggettoDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTTarOggettoDao(SitTTarOggetto tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}
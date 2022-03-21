package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTIciOggetto;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTIciOggettoDao extends TabellaDwhDao {

	public SitTIciOggettoDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTIciOggettoDao(SitTIciOggetto tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

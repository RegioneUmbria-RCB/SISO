package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTIciRiduzioni;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTIciRiduzioniDao extends TabellaDwhDao {

	public SitTIciRiduzioniDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTIciRiduzioniDao(SitTIciRiduzioni tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

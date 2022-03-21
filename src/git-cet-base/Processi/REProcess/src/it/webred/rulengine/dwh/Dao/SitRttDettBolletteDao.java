package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitRttDettBollette;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitRttDettBolletteDao extends TabellaDwhDao {

	public SitRttDettBolletteDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitRttDettBolletteDao(SitRttDettBollette tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

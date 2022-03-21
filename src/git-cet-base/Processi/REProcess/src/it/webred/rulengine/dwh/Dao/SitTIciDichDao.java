package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTIciDich;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTIciDichDao extends TabellaDwhDao {

	public SitTIciDichDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTIciDichDao(SitTIciDich tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

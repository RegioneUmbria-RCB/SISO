package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTF24AccreditoSuccessivo;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTF24AccreditoSuccessivoDao extends TabellaDwhDao {

	public SitTF24AccreditoSuccessivoDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTF24AccreditoSuccessivoDao(SitTF24AccreditoSuccessivo tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

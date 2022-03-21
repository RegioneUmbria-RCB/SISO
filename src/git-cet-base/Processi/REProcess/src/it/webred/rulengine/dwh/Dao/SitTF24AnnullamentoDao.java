package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitTF24Annullamento;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitTF24AnnullamentoDao extends TabellaDwhDao {

	public SitTF24AnnullamentoDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitTF24AnnullamentoDao(SitTF24Annullamento tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}

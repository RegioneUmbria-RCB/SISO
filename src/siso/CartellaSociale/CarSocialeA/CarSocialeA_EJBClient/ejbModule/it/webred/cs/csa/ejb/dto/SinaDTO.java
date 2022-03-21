package it.webred.cs.csa.ejb.dto;

import it.webred.cs.data.model.CsTbSinaDomanda;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SinaDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	private HashMap<Long,CsTbSinaDomanda> domandas = new HashMap<Long,CsTbSinaDomanda>();

	public static SinaDTO create(List<CsTbSinaDomanda> csDSinaDomandas) {
		return new SinaDTO().init(csDSinaDomandas);
	}

	protected SinaDTO() {
	}

	private SinaDTO init(List<CsTbSinaDomanda> csDSinaDomandas) {
		for (Iterator<CsTbSinaDomanda> i = csDSinaDomandas.iterator(); i.hasNext();) {
			CsTbSinaDomanda csDSinaDomanda = i.next();

			this.domandas.put(csDSinaDomanda.getId(), csDSinaDomanda);
		}

		return this;
	}

	public CsTbSinaDomanda getDomandaById(Long id) {
		return this.domandas.get(id);
	}
}

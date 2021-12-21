package it.umbriadigitale.soclav.service.interfaccia;

import it.umbriadigitale.soclav.model.Test;

public interface IGeneralService {
	

	public Test save(Test t);
	public Test find(Long id);

}

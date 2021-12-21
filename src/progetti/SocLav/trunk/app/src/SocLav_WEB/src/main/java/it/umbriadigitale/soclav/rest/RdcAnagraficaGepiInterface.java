package it.umbriadigitale.soclav.rest;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;

import it.umbriadigitale.soclav.model.gepi.RdCAnagraficaGepi;
 

@Remote
public interface RdcAnagraficaGepiInterface {

	public Messaggio getAnagraficheByCodEnte(String codEnte);
	
}

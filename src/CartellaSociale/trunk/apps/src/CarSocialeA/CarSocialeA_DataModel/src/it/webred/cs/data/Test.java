package it.webred.cs.data;

import java.util.Date;

import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoLAZY;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Test {
	EntityManager em=null;
	public Test() {
		
	}

	public static void main(String args[]){
		EntityManager em=null;
		em= Persistence.createEntityManagerFactory("CarSocialeA_DataModel").createEntityManager();
		
		em.getTransaction().begin();
		CsACaso caso =new CsACaso();
		
		CsASoggettoLAZY soggetto=new CsASoggettoLAZY();

		soggetto.setUserIns("user");
		soggetto.setDtIns(new Date());

		caso.setCsASoggetto(soggetto);
		em.merge(soggetto);
		em.merge(caso);
		em.getTransaction().commit();
	}
}
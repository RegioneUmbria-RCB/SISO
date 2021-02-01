package it.umbriadigitale.rest.base;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;;
 


/**
 * @author marcoultra
 *
 */
public abstract class DefaultRestApplication extends Application {

	
	public DefaultRestApplication() {
		singletons.add(new MainWs(this.getClass().getPackage().getName()));
	}
	
	
	protected Set<Object> singletons = new HashSet<Object>();


	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
	

 /*    @Override
     public Set<Class<?>> getClasses() {
         Set<Class<?>> classes = new HashSet<Class<?>>();
         classes.add(MainWs.class);
         return classes;
     }
*/
  	 
     
}
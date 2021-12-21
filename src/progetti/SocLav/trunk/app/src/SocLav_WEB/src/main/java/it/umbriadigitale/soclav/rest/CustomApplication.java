package it.umbriadigitale.soclav.rest;
 

import org.glassfish.jersey.server.ResourceConfig;

public class CustomApplication extends ResourceConfig 
{
	public CustomApplication() 
	{
		packages("it.umbriadigitale.soclav.rest");
		 
		//register(AuthenticationFilter.class);
	}
}


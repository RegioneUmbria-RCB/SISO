package it.webred.AMProfiler.rest.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;;
 


public class ProfilerApplication extends Application {

     @Override
     public Set<Class<?>> getClasses() {
         Set<Class<?>> classes = new HashSet<Class<?>>();
         classes.add(LoginResource.class);
         return classes;
     }
}
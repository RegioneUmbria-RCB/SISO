package it.webred.cs.sample.aop.aspect.bean;

import javax.inject.Named;

@Named
public class DependencyBean {
        public void sayHi() {
                System.out.println("Ciao , come stai? ");
        }

        public void greet() {
                System.out.println("Ci rivediamo alla prossima!");
        }
}
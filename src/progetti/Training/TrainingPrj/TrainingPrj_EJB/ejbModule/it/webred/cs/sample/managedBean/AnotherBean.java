package it.webred.cs.sample.managedBean;


import javax.inject.Inject;
import javax.inject.Named;





@Named
public class AnotherBean  {
 
    private  MyBean myBean;

	public String sayAPresto() {
    	
    	ISampleManBean b = new MyBean();
    	b.sayAPresto();

    	return myBean.sayAPresto();
    }
 
    @Inject
    public void setMyBean(MyBean bean) {
        myBean = bean;
    }
 
}
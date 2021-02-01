package it.webred.cs.sample.aop.aspect.bean;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;

import org.springframework.beans.factory.annotation.Autowired;

@Named
public class SimpleBean {
	
        private DependencyBean dependency1;
        private DependencyBean dependency2;
        private DependencyBean dependencyProxedSpring;

        public void salutareProxyBean() {
                dependency1.sayHi();
                dependency1.greet();

                dependencyProxedSpring.sayHi();
                dependencyProxedSpring.greet();
        
        }

        
        public void salutareBean() {
            dependency2.sayHi();

            dependency2.greet();
    }
        
		@Inject 
	    //@Named("dependencyBean")
        @Named("dependencyBeanProxy1")
        public void setDependency1(DependencyBean dependency1) {
			this.dependency1 = dependency1;
		}

		@Inject 
	    @Named("dependencyBean")
     //   @Named("dependencyBeanProxy2")
		public void setDependency2( DependencyBean dependency2) {
			this.dependency2 = dependency2;
		}


		@Inject 
        @Named("dependencyBeanProxy3")
		public void setDependencyProxedSpring(DependencyBean dependencyProxedSpring) {
			this.dependencyProxedSpring = dependencyProxedSpring;
		}


}
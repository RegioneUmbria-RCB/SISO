package it.webred.cs.sample.aop.aspect.springAOP;

import java.lang.reflect.Method;

import javax.inject.Named;



import org.springframework.aop.MethodBeforeAdvice;


@Named
public class SimpleAdvice implements MethodBeforeAdvice {

        @Override
        public void before(Method method, Object[] objects, Object object)
                        throws Throwable {
                // TODO Auto-generated method stub
                System.out.println("SimpleAdvice before: " + method + "-" + object.getClass());
        }

}
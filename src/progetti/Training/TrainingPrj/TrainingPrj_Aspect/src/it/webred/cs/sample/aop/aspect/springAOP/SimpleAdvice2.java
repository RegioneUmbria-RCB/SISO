package it.webred.cs.sample.aop.aspect.springAOP;

import java.lang.reflect.Method;

import javax.inject.Named;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.MethodBeforeAdvice;

@Named
public class SimpleAdvice2 implements MethodBeforeAdvice{


		@Override
		public void before(Method method, Object[] objects, Object object)
				throws Throwable {
            System.out.println("SimpleAdvice2 before: " + method+ "-" + object.getClass());

			
		}

}

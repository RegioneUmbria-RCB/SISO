package it.webred.cs.sample.aop.aspect.springAOP;


import java.lang.reflect.Method;

import javax.inject.Named;



@Named
public class HijackBeforeMethodBean  implements org.springframework.aop.MethodBeforeAdvice

{
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see org.springframework.aop.MethodBeforeAdvice#before(java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 * N.B. NESSUN NESSUNA ANNTOAZIONE TIPO BEFORE ECC. E' NECESSARIO : E' SPRING AOP , NON ASPECTJ!!
	 */
	public void before(Method method, Object[] args, Object target)
		throws Throwable {
	        System.out.println("HijackBeforeMethod : - -- Before method " + method.getName()  );
	}
	


}


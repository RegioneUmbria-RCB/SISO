package it.webred.cs.sample.aop.aspect.aspectj;



import javax.inject.Named;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


@Named
@Aspect
public class AnnotatedAroundAdvice {


    	@Pointcut("execution(* *.salutare*(..))")
    	public void businessMethods() { }
    
		@Around("businessMethods()")
		public Object profile(ProceedingJoinPoint pjp) throws Throwable {
			
			   System.out.println("AnnotatedAroundAdvice:Fra poco inizia una sequenza di saluti ..... " );
			   Object output = pjp.proceed();
			   System.out.println("AnnotatedAroundAdvice: FINE Dei saluti!!! " );
			   return output;
			
		}

}

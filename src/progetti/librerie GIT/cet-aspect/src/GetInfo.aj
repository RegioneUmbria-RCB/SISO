/*
Copyright (c) Xerox Corporation 1998-2002.  All rights reserved.

Use and copying of this software and preparation of derivative works based
upon this software are permitted.  Any distribution of this software or
derivative works must comply with all applicable United States export control
laws.

This software is made available AS IS, and Xerox Corporation makes no warranty
about the software, its performance or its conformity to any specification.
*/



import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;

aspect GetInfo {

   static final void println(String s){ System.out.println(s); }

   public pointcut goCut(): cflow(this(Demo) && execution(void go()));

   // match tutti i metodi del package e dei sottopackage
   pointcut poitCutAll()
	: within(it.escsolution.escwebgis..*) && execution(* it.escsolution.escwebgis..*.*(..));
   
   // match titti i metodi mCaricareDett* 
   public pointcut mCaricareDettCut()
	: execution(* it.escsolution.escwebgis..*.mCaricareDett*(..));
   
   //  match titti i metodi mCaricareLista*  
   public pointcut mCaricareListaCut()
	: execution(* it.escsolution.escwebgis..*.mCaricareLista*(..));
   
   // math di tutti i metodi e di tutte le classi sotto il package
   // pointcut mCaricareListaCut5(): execution(* it.escsolution.escwebgis.anagrafe.logic..*(..));
   
   public pointcut demoExecs(): within(Demo) && execution(* *(..));

    
   Object around(): poitCutAll() {
	      println("poitCut2 message " +
	          thisJoinPointStaticPart.getSignature().getName());

	      Object result = proceed();

	      println("poitCut2 result: " + result );
	      return result;
	   }
   
   
   Object around(): mCaricareListaCut() {
	      println("mCaricareListaCut4 message " +
	          thisJoinPointStaticPart.getSignature().getName());

	      Object result = proceed();

	      println("mCaricareListaCut4 result: " + result );
	      return result;
	   }

   Object around(): mCaricareDettCut() {
	      println("mCaricareDett message " +
	          thisJoinPointStaticPart.getSignature().getName());

	      Object result = proceed();

	      println("mCaricareDett result: " + result );
	      return result;
	   } 
  
   
   Object around(): demoExecs() && !call(* go()) && goCut() {
      println("Intercepted message: " +
          thisJoinPointStaticPart.getSignature().getName());
      println("in class: " +
          thisJoinPointStaticPart.getSignature().getDeclaringType().getName());
      printParameters(thisJoinPoint);
      println("Running original method: \n" );
      Object result = proceed();
      println("  result: " + result );
      return result;
   }

   static private void printParameters(JoinPoint jp) {
      println("Arguments: " );
      Object[] args = jp.getArgs();
      String[] names = ((CodeSignature)jp.getSignature()).getParameterNames();
      Class[] types = ((CodeSignature)jp.getSignature()).getParameterTypes();
      for (int i = 0; i < args.length; i++) {
         println("  "  + i + ". " + names[i] +
             " : " +            types[i].getName() +
             " = " +            args[i]);
      }
   }
}

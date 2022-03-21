package it.webred.ct.support.audit.aspect.diogene;

aspect AuditLista extends AuditBase{

	// match tutti i metodi mCaricareLista*
	public pointcut mCaricareListCut()
	: execution(* it.escsolution.escwebgis..*logic.*.mCaricareLista*(..));

	Object around(): mCaricareListCut() {
		
		Object returnVal = null;
		String e = null;
		
		try {
			returnVal = proceed();
		} catch (Exception ex) {
			e = ex.getMessage();
		} finally {
			
			auditDiogeneMethod(thisJoinPoint, returnVal, e);

		}
		return returnVal;
	}
	
}

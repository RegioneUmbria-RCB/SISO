package it.webred.ct.support.audit.aspect.diogene;

aspect AuditDettaglio extends AuditBase{

	// match tutti i metodi mCaricareDett*
	public pointcut mCaricareDettCut()
	: execution(* it.escsolution.escwebgis..*logic.*.mCaricareDett*(..));

	Object around(): mCaricareDettCut() {
		
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

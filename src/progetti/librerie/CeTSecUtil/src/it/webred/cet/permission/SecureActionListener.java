package it.webred.cet.permission;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;

import it.webred.cet.permission.annotation.Security;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.servlet.http.HttpSession;

//import com.sun.faces.application.ActionListenerImpl;
//import com.sun.faces.application.MethodExpressionMethodBindingAdapter;

/**
 * @author Utente
 * lA CLASSE è DISMESSA . eRA STATA IMPLEMENTATA, PROBABILMENTE PER IL cnc 
 * Per controllare la sicurezza a livello di managed bean .
 * L'annotazioen che veniva usata era @Security , sempre presente in questo progetto
 * In Jboss 7 non compila più per mamncanza del package com.sun.faces.application
 * 
 * Comunue il listener e' inutilizzato 
 */
@Deprecated
public class SecureActionListener //extends ActionListenerImpl implements ActionListener 
{

	// @Override
	public void processAction(ActionEvent event) {

		System.out.println("Action event handler!!!! NON USATO, VALIDO SOLO SU JBOSS5 ");
/*
		FacesContext context = FacesContext.getCurrentInstance();

		UIComponent source = event.getComponent();
		ActionSource actionSource = (ActionSource) source;

		MethodBinding binding;

		binding = actionSource.getAction();
		
		String expr = "";
		try {
			expr = binding.getExpressionString();
		}
		catch(Throwable t) {}
		
		if (!expr.startsWith("#")) {
			super.processAction(event);
			return;
		}

		int idx = expr.indexOf('.');
		String target = expr.substring(0, idx).substring(2);
		String t = expr.substring(idx + 1);
		String method = t.substring(0, (t.length() - 1));

		MethodExpression expression = new MethodExpressionMethodBindingAdapter(
				binding);
		ELContext elContext = context.getELContext();
		ExpressionFactory factory = context.getApplication()
				.getExpressionFactory();
		ValueExpression ve = factory.createValueExpression(elContext, "#{"
				+ target + '}', Object.class);
		Object result = ve.getValue(elContext);

		Class c = result.getClass();
		System.out.println("# Object [" + result + "] - Class [" + c + "]");

		Annotation a = c.getAnnotation(Security.class);
		if (a != null) {
			System.out.println("Trovata annotazione su classe");
			Security s = (Security) a;
			boolean isAuth = isAuthorized(s, context);
			if (isAuth)
				super.processAction(event);
			else {
				denyAccess(context);
			}
		} else {
			System.out.println("Check method");
			Method[] methods = result.getClass().getMethods();
			for (Method meth : methods) {
				if (meth.getName().equals(method)) {

					Annotation am = meth.getAnnotation(Security.class);

					if (am != null) {
						System.out.println("Metodo annotato");
						Security s = (Security) am;
						boolean isAuth = isAuthorized(s, context);
						if (isAuth)
							super.processAction(event);
						else {
							denyAccess(context);
							return ;
						}

					} else
						super.processAction(event);
				}

			}
		
			super.processAction(event);
	}
*/

}

	private boolean isAuthorized(Security s, FacesContext context) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		CeTUser user = (CeTUser) session.getAttribute("user");

		if (user == null)
			return false;

		String ente = user.getEnteList().get(0);

		String item = s.item();
		String perm = s.permission();

		String permKey = createPermKey(ente, item, perm);

		HashMap<String, String> permMap = user.getPermList();

		if (permMap.get(permKey) != null) {
			System.out.println("Utente autorizzato [" + permKey + "]");
			return true;
		}

		return false;
	}

	private String createPermKey(String istanza, String item, String perm) {
		return "permission@-@" + istanza + "@-@" + item + "@-@" + perm;
	}

	private void denyAccess(FacesContext context) {

		System.out.println("Dispatch verso accessDenied");
		Application application = (Application) context.getApplication();

		NavigationHandler navHandler = (NavigationHandler) application
				.getNavigationHandler();

		navHandler.handleNavigation(context, null, "accessDenied");
		context.renderResponse();
	}

}

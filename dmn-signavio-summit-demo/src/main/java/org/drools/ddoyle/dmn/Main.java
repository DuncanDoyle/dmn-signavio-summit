package org.drools.ddoyle.dmn;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.core.api.DMNFactory;

public class Main {
	
	public static void main(String[] args) {
		
		KieServices kieServices = KieServices.Factory.get();
		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		KieSession kieSession = kieContainer.newKieSession();
		
		try {
			DMNRuntime dmnRuntime = kieSession.getKieRuntime(DMNRuntime.class);
			
			DMNModel dmnModel = dmnRuntime.getModel("http://www.signavio.com/dmn/1.1/diagram/fb3bb7d0c13940e38ef1c6b8c0387999.xml", "CreditRatingEdition");
			
			DMNContext dmnContext = DMNFactory.newContext();
			
			//Insert data into the context
			dmnContext.set("fICO", "800");
			dmnContext.set("medical", "450");
			dmnContext.set("bankruptcies", null);
			dmnContext.set("defaults", null);
			dmnContext.set("rentOrMortgage", null);
			dmnContext.set("income", null);
			dmnContext.set("settlements", null);
			dmnContext.set("consumerDebt", null);
			
			DMNResult dmnResult = dmnRuntime.evaluateAll( dmnModel, dmnContext );
	        DMNContext result = dmnResult.getContext();
			
			
		} finally {
			kieSession.dispose();
		}
		
		
		
	}
	
	
	
	
}

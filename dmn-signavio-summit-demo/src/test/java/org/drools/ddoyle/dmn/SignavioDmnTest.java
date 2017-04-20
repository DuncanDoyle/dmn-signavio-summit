package org.drools.ddoyle.dmn;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.drools.ddoyle.dmn.util.DMNRuntimeUtil;
import org.junit.Test;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNMessage;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.core.api.DMNFactory;

public class SignavioDmnTest {

	@Test
	public void testDmnCompilation() {

		
		
		
		DMNRuntime runtime = DMNRuntimeUtil.createRuntime("CreditRatingEdition.dmn", this.getClass());
		DMNModel dmnModel = runtime.getModel(
				"http://www.signavio.com/dmn/1.1/diagram/6b8d52148ad8422ea99ef8ff3af960cb.xml", "CreditRatingEdition");
		assertThat(dmnModel, notNullValue());
		assertThat(formatMessages(dmnModel.getMessages()), dmnModel.hasErrors(), is(false));

		DMNContext context = DMNFactory.newContext();
		
		context.set("bankruptcies", false);
		context.set("consumerDebt", 0);
		context.set("defaults", false);
		context.set("fICO", new BigDecimal(800));
		context.set("income", 14000);
		context.set("medical", 600);
		context.set("rentOrMortgage", 2200);
		context.set("settlements", false);
		

		DMNResult dmnResult = runtime.evaluateAll(dmnModel, context);
		assertThat(formatMessages(dmnResult.getMessages()), dmnResult.hasErrors(), is(false));

		DMNContext result = dmnResult.getContext();

		
		Map<String, Object> determineInterestRate = (Map<String, Object>) result.get("determineInterestRate");
		
		assertThat(determineInterestRate.get("cardRate"), is(new BigDecimal("0.1")));
		assertThat(determineInterestRate.get("score"), is(new BigDecimal("4")));
	}

	private String formatMessages(List<DMNMessage> messages) {
		return messages.stream().map(m -> m.toString()).collect(Collectors.joining("\n"));
	}

}

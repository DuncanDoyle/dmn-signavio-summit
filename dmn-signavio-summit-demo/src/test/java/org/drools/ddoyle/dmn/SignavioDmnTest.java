package org.drools.ddoyle.dmn;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.drools.ddoyle.dmn.util.DMNRuntimeUtil;
import org.junit.Test;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNMessage;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.core.api.DMNFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class SignavioDmnTest {

	@Test
	public void testDmnCompilation() {

		DMNRuntime runtime = DMNRuntimeUtil.createRuntime("CreditRatingEdition.dmn", this.getClass());
		DMNModel dmnModel = runtime.getModel(
				"http://www.signavio.com/dmn/1.1/diagram/fb3bb7d0c13940e38ef1c6b8c0387999.xml", "CreditRatingEdition");
		assertThat(dmnModel, notNullValue());
		assertThat(formatMessages(dmnModel.getMessages()), dmnModel.hasErrors(), is(false));

		DMNContext context = DMNFactory.newContext();
		context.set("Monthly Salary", 1000);

		DMNResult dmnResult = runtime.evaluateAll(dmnModel, context);
		assertThat(formatMessages(dmnResult.getMessages()), dmnModel.hasErrors(), is(false));

		DMNContext result = dmnResult.getContext();

		assertThat(result.get("Yearly Salary"), is(new BigDecimal("12000")));

	}

	private String formatMessages(List<DMNMessage> messages) {
		return messages.stream().map(m -> m.toString()).collect(Collectors.joining("\n"));
	}

}


package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.EconomicTransaction;

@Component
@Transactional
public class EconomicTransactionToStringConverter implements Converter<EconomicTransaction, String> {

	@Override
	public String convert(final EconomicTransaction economicTransaction) {
		String result;

		if (economicTransaction == null)
			result = null;
		else
			result = String.valueOf(economicTransaction.getId());

		return result;
	}
}

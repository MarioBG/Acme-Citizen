
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.EconomicTransactionRepository;
import domain.EconomicTransaction;

@Component
@Transactional
public class StringToEconomicTransactionConverter implements Converter<String, EconomicTransaction> {

	@Autowired
	EconomicTransactionRepository	economicTransactionRepository;


	@Override
	public EconomicTransaction convert(final String text) {
		EconomicTransaction result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.economicTransactionRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}

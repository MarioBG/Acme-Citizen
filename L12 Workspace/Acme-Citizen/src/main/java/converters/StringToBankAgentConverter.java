
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.BankAgentRepository;
import domain.BankAgent;

@Component
@Transactional
public class StringToBankAgentConverter implements Converter<String, BankAgent> {

	@Autowired
	BankAgentRepository	chirpRepository;


	@Override
	public BankAgent convert(final String text) {
		BankAgent result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.chirpRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}

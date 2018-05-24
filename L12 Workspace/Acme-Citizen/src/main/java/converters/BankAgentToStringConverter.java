
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.BankAgent;

@Component
@Transactional
public class BankAgentToStringConverter implements Converter<BankAgent, String> {

	@Override
	public String convert(final BankAgent chirp) {
		String result;

		if (chirp == null)
			result = null;
		else
			result = String.valueOf(chirp.getId());

		return result;
	}
}

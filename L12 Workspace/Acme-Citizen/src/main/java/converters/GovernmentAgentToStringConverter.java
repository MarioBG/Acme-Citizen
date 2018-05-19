
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.GovernmentAgent;

@Component
@Transactional
public class GovernmentAgentToStringConverter implements Converter<GovernmentAgent, String> {

	@Override
	public String convert(final GovernmentAgent admin) {
		String result;

		if (admin == null)
			result = null;
		else
			result = String.valueOf(admin.getId());

		return result;
	}
}

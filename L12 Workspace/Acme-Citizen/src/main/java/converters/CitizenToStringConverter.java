
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Citizen;

@Component
@Transactional
public class CitizenToStringConverter implements Converter<Citizen, String> {

	@Override
	public String convert(final Citizen citizen) {
		String result;

		if (citizen == null)
			result = null;
		else
			result = String.valueOf(citizen.getId());

		return result;
	}
}

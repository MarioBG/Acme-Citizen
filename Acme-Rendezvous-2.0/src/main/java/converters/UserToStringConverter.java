package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Citizen;

@Component
@Transactional
public class UserToStringConverter implements Converter<Citizen, String> {
	
	@Override
	public String convert(final Citizen user) {
		String result;

		if (user == null)
			result = null;
		else
			result = String.valueOf(user.getId());

		return result;
	}
	
}

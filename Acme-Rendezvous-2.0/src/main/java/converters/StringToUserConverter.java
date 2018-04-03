package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.UserRepository;
import domain.Citizen;

@Component
@Transactional
public class StringToUserConverter implements Converter<String, Citizen> {

	@Autowired
	UserRepository	userRepository;

	@Override
	public Citizen convert(final String text) {
		Citizen result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.userRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
	
}

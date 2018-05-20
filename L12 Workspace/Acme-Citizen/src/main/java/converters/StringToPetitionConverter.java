
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.PetitionRepository;
import domain.Petition;

@Component
@Transactional
public class StringToPetitionConverter implements Converter<String, Petition> {

	@Autowired
	PetitionRepository	petitionRepository;


	@Override
	public Petition convert(final String text) {
		Petition result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.petitionRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}

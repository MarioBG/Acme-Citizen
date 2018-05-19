
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CitizenRepository;
import domain.Citizen;

@Component
@Transactional
public class StringToCitizenConverter implements Converter<String, Citizen> {

	@Autowired
	CitizenRepository	articleRepository;


	@Override
	public Citizen convert(final String text) {
		Citizen result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.articleRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}

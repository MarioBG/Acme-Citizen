
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CandidatureRepository;
import domain.Candidature;

@Component
@Transactional
public class StringToCandidatureConverter implements Converter<String, Candidature> {

	@Autowired
	CandidatureRepository	candidatureRepository;


	@Override
	public Candidature convert(final String text) {
		Candidature result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.candidatureRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}

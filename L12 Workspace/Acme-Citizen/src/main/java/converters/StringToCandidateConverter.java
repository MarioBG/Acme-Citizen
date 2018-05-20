
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.CandidateRepository;
import domain.Candidate;

@Component
@Transactional
public class StringToCandidateConverter implements Converter<String, Candidate> {

	@Autowired
	CandidateRepository	candidateRepository;


	@Override
	public Candidate convert(final String text) {
		Candidate result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.candidateRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}

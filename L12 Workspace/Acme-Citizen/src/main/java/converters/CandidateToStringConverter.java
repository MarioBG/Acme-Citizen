
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Candidate;

public class CandidateToStringConverter implements Converter<Candidate, String> {

	@Override
	public String convert(final Candidate candidate) {
		String result;

		if (candidate == null)
			result = null;
		else
			result = String.valueOf(candidate.getId());

		return result;
	}

}

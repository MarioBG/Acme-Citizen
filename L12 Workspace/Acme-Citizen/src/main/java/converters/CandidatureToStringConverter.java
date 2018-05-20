
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Candidature;

public class CandidatureToStringConverter implements Converter<Candidature, String> {

	@Override
	public String convert(final Candidature candidature) {
		String result;

		if (candidature == null)
			result = null;
		else
			result = String.valueOf(candidature.getId());

		return result;
	}

}

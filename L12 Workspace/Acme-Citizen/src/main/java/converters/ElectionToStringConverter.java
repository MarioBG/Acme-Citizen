
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Election;

public class ElectionToStringConverter implements Converter<Election, String> {

	@Override
	public String convert(final Election election) {
		String result;

		if (election == null)
			result = null;
		else
			result = String.valueOf(election.getId());

		return result;
	}

}

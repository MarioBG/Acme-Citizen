
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Petition;

public class PetitionToStringConverter implements Converter<Petition, String> {

	@Override
	public String convert(final Petition petition) {
		String result;

		if (petition == null)
			result = null;
		else
			result = String.valueOf(petition.getId());

		return result;
	}

}

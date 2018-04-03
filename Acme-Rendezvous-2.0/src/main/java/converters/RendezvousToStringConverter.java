package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Petition;

@Component
@Transactional
public class RendezvousToStringConverter implements Converter<Petition, String>{

	@Override
	public String convert(final Petition rendezvous) {
		String result;

		if (rendezvous == null)
			result = null;
		else
			result = String.valueOf(rendezvous.getId());

		return result;
	}
}

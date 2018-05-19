
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.GovernmentAgentRepository;
import domain.GovernmentAgent;

@Component
@Transactional
public class StringToGovernmentAgentConverter implements Converter<String, GovernmentAgent> {

	@Autowired
	GovernmentAgentRepository	governmentAgentRepository;


	@Override
	public GovernmentAgent convert(final String text) {
		GovernmentAgent result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.governmentAgentRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}


package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Lottery;

@Component
@Transactional
public class LotteryToStringConverter implements Converter<Lottery, String> {

	@Override
	public String convert(final Lottery lottery) {
		String result;

		if (lottery == null)
			result = null;
		else
			result = String.valueOf(lottery.getId());

		return result;
	}
}

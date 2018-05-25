
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.LotteryRepository;
import domain.Lottery;

@Component
@Transactional
public class StringToLotteryConverter implements Converter<String, Lottery> {

	@Autowired
	LotteryRepository	lotteryRepository;


	@Override
	public Lottery convert(final String text) {
		Lottery result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.lotteryRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}

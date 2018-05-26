
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.LotteryTicketRepository;
import domain.LotteryTicket;

@Component
@Transactional
public class StringToLotteryTicketConverter implements Converter<String, LotteryTicket> {

	@Autowired
	LotteryTicketRepository	lotteryTicketRepository;


	@Override
	public LotteryTicket convert(final String text) {
		LotteryTicket result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.lotteryTicketRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}

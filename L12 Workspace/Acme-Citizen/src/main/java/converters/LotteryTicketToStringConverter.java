
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.LotteryTicket;

@Component
@Transactional
public class LotteryTicketToStringConverter implements Converter<LotteryTicket, String> {

	@Override
	public String convert(final LotteryTicket lotteryTicket) {
		String result;

		if (lotteryTicket == null)
			result = null;
		else
			result = String.valueOf(lotteryTicket.getId());

		return result;
	}
}

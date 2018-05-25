
package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.BankAccountRepository;
import domain.BankAccount;

@Component
@Transactional
public class StringToBankAccountConverter implements Converter<String, BankAccount> {

	@Autowired
	BankAccountRepository	bankAccountRepository;


	@Override
	public BankAccount convert(final String text) {
		BankAccount result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.bankAccountRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}

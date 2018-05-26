
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.BankAccount;

@Component
@Transactional
public class BankAccountToStringConverter implements Converter<BankAccount, String> {

	@Override
	public String convert(final BankAccount bankAccount) {
		String result;

		if (bankAccount == null)
			result = null;
		else
			result = String.valueOf(bankAccount.getId());

		return result;
	}
}

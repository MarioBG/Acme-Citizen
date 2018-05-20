
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Commentable;

public class CommentableToStringConverter implements Converter<Commentable, String> {

	@Override
	public String convert(final Commentable commentable) {
		String result;

		if (commentable == null)
			result = null;
		else
			result = String.valueOf(commentable.getId());

		return result;
	}

}

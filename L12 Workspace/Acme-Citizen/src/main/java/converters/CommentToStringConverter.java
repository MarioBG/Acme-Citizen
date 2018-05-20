
package converters;

import org.springframework.core.convert.converter.Converter;

import domain.Comment;

public class CommentToStringConverter implements Converter<Comment, String> {

	@Override
	public String convert(final Comment comment) {
		String result;

		if (comment == null)
			result = null;
		else
			result = String.valueOf(comment.getId());

		return result;
	}

}

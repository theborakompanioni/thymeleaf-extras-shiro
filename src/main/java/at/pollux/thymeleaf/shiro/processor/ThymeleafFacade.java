package at.pollux.thymeleaf.shiro.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.util.EvaluationUtil;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static org.thymeleaf.util.StringUtils.trim;
import static org.thymeleaf.util.Validate.notEmpty;
import static org.thymeleaf.util.Validate.notNull;

/**
 * Created by void on 06.10.15.
 */
public final class ThymeleafFacade {

    private ThymeleafFacade() {
        throw new UnsupportedOperationException();
    }

    public static String getRawValue(final Element element, final String attributeName) {
        notNull(element, "element must not be null");
        notEmpty(attributeName, "attributeName must not be empty");

        final String rawValue = trim(element.getAttributeValue(attributeName));
        notEmpty(rawValue, "value of '" + attributeName + "' must not be empty");

        return rawValue;
    }

    public static Object evaluateExpression(Arguments arguments, String expression) throws TemplateProcessingException {
        notNull(arguments, "arguments must not be null");
        notEmpty(expression, "expression must not be empty");

        final Configuration configuration = arguments.getConfiguration();

        final IStandardExpressionParser parser = StandardExpressions.getExpressionParser(configuration);

        final IStandardExpression evaluableExpression = parser.parseExpression(configuration, arguments, expression);

        return evaluableExpression.execute(configuration, arguments);
    }

    public static List<Object> evaluateAsIterable(Arguments arguments, String rawValue) throws TemplateProcessingException {
        notNull(arguments, "arguments must not be null");
        notEmpty(rawValue, "rawValue must not be empty");

        Object evaluatedExpression = evaluateExpression(arguments, rawValue);
        return EvaluationUtil.evaluateAsIterable(evaluatedExpression);
    }

    public static List<Object> evaluateAsIterableOrRawValue(Arguments arguments, String rawValue) {
        notNull(arguments, "arguments must not be null");
        notEmpty(rawValue, "rawValue must not be empty");

        List<Object> result = new ArrayList<Object>();
        try {
            result.addAll(evaluateAsIterable(arguments, rawValue));
        } catch (TemplateProcessingException ex) {
            result.add(rawValue);
        }

        return unmodifiableList(result);
    }

    public static List<String> evaluateAsStringsWithDelimiter(Arguments arguments, String rawValue, String delimiter) {
        notNull(arguments, "arguments must not be null");
        notEmpty(rawValue, "rawValue must not be empty");
        notEmpty(delimiter, "delimiter must not be empty");

        List<String> result = new ArrayList<String>();
        List<Object> iterables = evaluateAsIterableOrRawValue(arguments, rawValue);

        for (Object o : iterables) {
            result.addAll(asList(StringUtils.split(o, delimiter)));
        }

        return unmodifiableList(result);
    }
}

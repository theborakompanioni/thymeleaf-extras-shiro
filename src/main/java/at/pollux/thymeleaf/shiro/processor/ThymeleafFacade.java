package at.pollux.thymeleaf.shiro.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressionParser;
import org.thymeleaf.util.EvaluationUtils;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static org.thymeleaf.util.StringUtils.trim;
import static org.thymeleaf.util.Validate.notEmpty;
import static org.thymeleaf.util.Validate.notNull;

public final class ThymeleafFacade {

    private ThymeleafFacade() {
        throw new UnsupportedOperationException();
    }

    public static String getRawValue(final IProcessableElementTag element, final AttributeName attributeName) {
        notNull(element, "element must not be null");
        notNull(attributeName, "attributeName must not be empty");

        final String rawValue = trim(element.getAttributeValue(attributeName));
        notEmpty(rawValue, "value of '" + attributeName + "' must not be empty");

        return rawValue;
    }

    public static String getRawValue(final IProcessableElementTag element, final String attributeName) {
        notNull(element, "element must not be null");
        notEmpty(attributeName, "attributeName must not be empty");

        final String rawValue = trim(element.getAttributeValue(attributeName));
        notEmpty(rawValue, "value of '" + attributeName + "' must not be empty");

        return rawValue;
    }

    public static Object evaluateExpression(ITemplateContext arguments, String expression) throws TemplateProcessingException {
        notNull(arguments, "arguments must not be null");
        notEmpty(expression, "expression must not be empty");

        final IStandardExpressionParser parser = new StandardExpressionParser();

        final IStandardExpression evaluableExpression = parser.parseExpression(arguments, expression);

        return evaluableExpression.execute(arguments);
    }

    public static List<Object> evaluateAsIterable(ITemplateContext arguments, String rawValue) throws TemplateProcessingException {
        notNull(arguments, "arguments must not be null");
        notEmpty(rawValue, "rawValue must not be empty");

        final Object evaluatedExpression = evaluateExpression(arguments, rawValue);

        return EvaluationUtils.evaluateAsList(evaluatedExpression);
    }

    public static List<Object> evaluateAsIterableOrRawValue(ITemplateContext arguments, String rawValue) {
        notNull(arguments, "arguments must not be null");
        notEmpty(rawValue, "rawValue must not be empty");

        final List<Object> result = new ArrayList<Object>();
        try {
            result.addAll(evaluateAsIterable(arguments, rawValue));
        } catch (TemplateProcessingException ex) {
            result.add(rawValue);
        }

        return unmodifiableList(result);
    }

    public static List<String> evaluateAsStringsWithDelimiter(ITemplateContext arguments, String rawValue, String delimiter) {
        notNull(arguments, "arguments must not be null");
        notEmpty(rawValue, "rawValue must not be empty");
        notEmpty(delimiter, "delimiter must not be empty");

        final List<String> result = new ArrayList<String>();
        final List<Object> iterates = evaluateAsIterableOrRawValue(arguments, rawValue);

        for (Object o : iterates) {
            result.addAll(asList(StringUtils.split(o, delimiter)));
        }

        return unmodifiableList(result);
    }
}

package at.pollux.thymeleaf.shiro.processor;

import org.thymeleaf.dom.Element;
import org.thymeleaf.util.StringUtils;
import org.thymeleaf.util.Validate;

/**
 * Created by void on 06.09.15.
 */
public final class AttributeUtils {

    private AttributeUtils() {
        throw new UnsupportedOperationException();
    }

    public static String getRawValue(final Element element, final String attributeName) {
        Validate.notNull(element, "element must not be null");
        Validate.notEmpty(attributeName, "attributeName must not be empty");

        final String rawValue = StringUtils.trim(element.getAttributeValue(attributeName));
        Validate.notEmpty(rawValue, "value of '" + attributeName + "' must not be empty");

        return rawValue;
    }
}

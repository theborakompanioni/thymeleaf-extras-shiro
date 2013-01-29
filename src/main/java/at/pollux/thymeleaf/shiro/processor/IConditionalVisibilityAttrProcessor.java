package at.pollux.thymeleaf.shiro.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

public interface IConditionalVisibilityAttrProcessor {
    boolean isVisible(final Arguments arguments, final Element element, final String attributeName);

    int getPrecedence();
}

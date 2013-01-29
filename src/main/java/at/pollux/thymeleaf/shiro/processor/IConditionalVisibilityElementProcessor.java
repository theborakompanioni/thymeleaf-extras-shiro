package at.pollux.thymeleaf.shiro.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

public interface IConditionalVisibilityElementProcessor {
    boolean isVisible(final Arguments arguments, final Element element);

    int getPrecedence();

    boolean removeHostElementIfVisible(final Arguments arguments, final Element element);
}

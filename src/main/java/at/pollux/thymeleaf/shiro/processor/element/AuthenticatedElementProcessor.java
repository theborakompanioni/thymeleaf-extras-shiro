package at.pollux.thymeleaf.shiro.processor.element;

import org.apache.shiro.SecurityUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.element.AbstractConditionalVisibilityElementProcessor;

import at.pollux.thymeleaf.shiro.processor.IConditionalVisibilityElementProcessor;

public class AuthenticatedElementProcessor extends AbstractConditionalVisibilityElementProcessor implements IConditionalVisibilityElementProcessor {

    private static final String ATTRIBUTE_NAME = "authenticated";

    public static AuthenticatedElementProcessor create() {
        return new AuthenticatedElementProcessor();
    }

    protected AuthenticatedElementProcessor() {
        super(ATTRIBUTE_NAME);
    }

    protected AuthenticatedElementProcessor(final String elementName) {
        super(elementName);
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }

    @Override
    public boolean isVisible(final Arguments arguments, final Element element) {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    @Override
    public boolean removeHostElementIfVisible(final Arguments arguments, final Element element) {
        return true;
    }
}

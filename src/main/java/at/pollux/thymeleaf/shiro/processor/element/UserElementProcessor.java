package at.pollux.thymeleaf.shiro.processor.element;

import org.apache.shiro.SecurityUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

public class UserElementProcessor extends AuthenticatedElementProcessor {

    private static final String ELEMENT_NAME = "user";

    public static UserElementProcessor create() {
        return new UserElementProcessor();
    }

    protected UserElementProcessor() {
        super(ELEMENT_NAME);
    }

    @Override
    public boolean isVisible(final Arguments arguments, final Element element) {
        return super.isVisible(arguments, element) || SecurityUtils.getSubject().isRemembered();
    }
}

package at.pollux.thymeleaf.shiro.processor.element;

import at.pollux.thymeleaf.shiro.processor.InvertVisibilityElementProcessor;

public class NotAuthenticatedElementProcessor extends InvertVisibilityElementProcessor<AuthenticatedElementProcessor> {
    private static final String ATTRIBUTE_NAME = "notAuthenticated";

    public static NotAuthenticatedElementProcessor create() {
        return new NotAuthenticatedElementProcessor();
    }

    protected NotAuthenticatedElementProcessor() {
        super(ATTRIBUTE_NAME, AuthenticatedElementProcessor.create());
    }

}

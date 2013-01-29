package at.pollux.thymeleaf.shiro.processor.element;

import at.pollux.thymeleaf.shiro.processor.InvertVisibilityElementProcessor;


public class GuestElementProcessor extends InvertVisibilityElementProcessor<UserElementProcessor> {

    private static final String ELEMENT_NAME = "guest";

    public static GuestElementProcessor create() {
        return new GuestElementProcessor();
    }

    protected GuestElementProcessor() {
        super(ELEMENT_NAME, UserElementProcessor.create());
    }
}

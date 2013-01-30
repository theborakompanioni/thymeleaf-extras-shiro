package at.pollux.thymeleaf.shiro.processor.element;

import at.pollux.thymeleaf.shiro.processor.InvertVisibilityElementProcessor;

public class LacksPermissionElementProcessor extends InvertVisibilityElementProcessor<HasPermissionElementProcessor> {
    private static final String ATTRIBUTE_NAME = "lacksPermission";

    public static LacksPermissionElementProcessor create() {
        return new LacksPermissionElementProcessor();
    }

    protected LacksPermissionElementProcessor() {
        super(ATTRIBUTE_NAME, HasPermissionElementProcessor.create());
    }

}

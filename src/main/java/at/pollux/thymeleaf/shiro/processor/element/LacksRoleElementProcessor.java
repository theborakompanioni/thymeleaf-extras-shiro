package at.pollux.thymeleaf.shiro.processor.element;

import at.pollux.thymeleaf.shiro.processor.InvertVisibilityElementProcessor;

public class LacksRoleElementProcessor extends InvertVisibilityElementProcessor<HasRoleElementProcessor> {
    private static final String ATTRIBUTE_NAME = "lacksRole";

    public static LacksRoleElementProcessor create() {
        return new LacksRoleElementProcessor();
    }

    protected LacksRoleElementProcessor() {
        super(ATTRIBUTE_NAME, HasRoleElementProcessor.create());
    }

}

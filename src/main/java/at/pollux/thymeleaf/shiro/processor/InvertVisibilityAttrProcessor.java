package at.pollux.thymeleaf.shiro.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractConditionalVisibilityAttrProcessor;

public abstract class InvertVisibilityAttrProcessor<T extends IConditionalVisibilityAttrProcessor> extends AbstractConditionalVisibilityAttrProcessor implements IConditionalVisibilityAttrProcessor {

    private final T delegate;

    public InvertVisibilityAttrProcessor(final String attributeName, final T delegate) {
        super(attributeName);
        this.delegate = delegate;
    }

    @Override
    public int getPrecedence() {
        return delegate.getPrecedence();
    }

    @Override
    public boolean isVisible(final Arguments arguments, final Element element, final String attributeName) {
        return !delegate.isVisible(arguments, element, attributeName);
    }
}

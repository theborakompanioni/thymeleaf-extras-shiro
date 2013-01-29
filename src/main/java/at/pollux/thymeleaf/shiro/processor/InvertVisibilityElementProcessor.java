package at.pollux.thymeleaf.shiro.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.element.AbstractConditionalVisibilityElementProcessor;


public abstract class InvertVisibilityElementProcessor<T extends IConditionalVisibilityElementProcessor> extends AbstractConditionalVisibilityElementProcessor implements
        IConditionalVisibilityElementProcessor {

    private final T delegate;

    public InvertVisibilityElementProcessor(final String elementName, final T delegate) {
        super(elementName);
        this.delegate = delegate;
    }

    @Override
    public int getPrecedence() {
        return delegate.getPrecedence();
    }

    @Override
    public boolean isVisible(final Arguments arguments, final Element element) {
        return !delegate.isVisible(arguments, element);
    }

    @Override
    public boolean removeHostElementIfVisible(final Arguments arguments, final Element element) {
        return delegate.removeHostElementIfVisible(arguments, element);
    }
}

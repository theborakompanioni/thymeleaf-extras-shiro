package at.pollux.thymeleaf.shiro.dialect.processor;

import org.apache.shiro.SecurityUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.processor.attr.AbstractTextChildModifierAttrProcessor;

public class PrincipalAttrProcessor extends AbstractTextChildModifierAttrProcessor {
	private static final String ATTRIBUTE_NAME = "principal";
	private static final int PRECEDENCE = 10000;
	
	public static final IProcessor create() {
		return new PrincipalAttrProcessor();
	}
	
    public PrincipalAttrProcessor() {
        super(ATTRIBUTE_NAME);
    }
    
    public int getPrecedence() {
        return PRECEDENCE;
    }

    @Override
    protected String getText(final Arguments arguments, final Element element, final String attributeName) {
        return SecurityUtils.getSubject().getPrincipal() + "";
    }
}
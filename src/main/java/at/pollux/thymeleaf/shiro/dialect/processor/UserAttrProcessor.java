package at.pollux.thymeleaf.shiro.dialect.processor;

import org.apache.shiro.SecurityUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.processor.attr.AbstractConditionalVisibilityAttrProcessor;

public class UserAttrProcessor extends AbstractConditionalVisibilityAttrProcessor {
    private static final String ATTRIBUTE_NAME = "user";
	private static final int PRECEDENCE = 300;
	
	public static IProcessor create() {
		return new UserAttrProcessor();
	}
    
    public UserAttrProcessor() {
        super(ATTRIBUTE_NAME);
    }
    
    public UserAttrProcessor(final String attrName) {
        super(attrName);
    }
    
    @Override
    public int getPrecedence() {
        return PRECEDENCE;
    }
    
    @Override
    protected boolean isVisible(final Arguments arguments, final Element element, final String attributeName) {
    	return SecurityUtils.getSubject().isRemembered();
    }    
}
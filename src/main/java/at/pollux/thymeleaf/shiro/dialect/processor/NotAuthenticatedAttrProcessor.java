package at.pollux.thymeleaf.shiro.dialect.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.IProcessor;

public class NotAuthenticatedAttrProcessor extends AuthenticatedAttrProcessor {
	private static final String ATTRIBUTE_NAME = "notAuthenticated";
	
	public static IProcessor create() {
		return new NotAuthenticatedAttrProcessor();
	}
    
    public NotAuthenticatedAttrProcessor() {
        super(ATTRIBUTE_NAME);
    }
    
    public NotAuthenticatedAttrProcessor(final String attrName) {
        super(attrName);
    }
    
    @Override
    protected boolean isVisible(final Arguments arguments, final Element element, final String attributeName) {
    	return !super.isVisible(arguments, element, attributeName);
    }    
}
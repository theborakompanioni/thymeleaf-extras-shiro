package at.pollux.thymeleaf.shiro.dialect.processor;

import org.apache.shiro.SecurityUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.processor.attr.AbstractConditionalVisibilityAttrProcessor;
import org.thymeleaf.util.StringUtils;
import org.thymeleaf.util.Validate;

public class HasPermissionAttrProcessor extends AbstractConditionalVisibilityAttrProcessor {

	private static final String ATTRIBUTE_NAME = "hasPermission";
	private static final int PRECEDENCE = 300;
	
	public static IProcessor create() {
		return new HasPermissionAttrProcessor();
	}
    
    public HasPermissionAttrProcessor() {
        super(ATTRIBUTE_NAME);
    }
    
    public HasPermissionAttrProcessor(final String attrName) {
        super(attrName);
    }
    
    @Override
    public int getPrecedence() {
        return PRECEDENCE;
    }
    
    @Override
    protected boolean isVisible(final Arguments arguments, final Element element, final String attributeName) {
    	Validate.notNull(element, "element must not be null");
    	Validate.notEmpty(attributeName, "attributeName must not be empty");
    	
    	final String permission = StringUtils.trim(element.getAttributeValue(attributeName));
    	Validate.notEmpty(permission, "value of '" + attributeName +"' must not be empty");

    	return SecurityUtils.getSubject().isPermitted(permission);
    }    
}
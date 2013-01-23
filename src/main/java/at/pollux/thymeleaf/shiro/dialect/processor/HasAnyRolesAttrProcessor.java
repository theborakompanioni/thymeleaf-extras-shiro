package at.pollux.thymeleaf.shiro.dialect.processor;

import org.apache.shiro.SecurityUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.processor.attr.AbstractConditionalVisibilityAttrProcessor;
import org.thymeleaf.util.StringUtils;
import org.thymeleaf.util.Validate;

public class HasAnyRolesAttrProcessor extends AbstractConditionalVisibilityAttrProcessor {

	private static final String ATTRIBUTE_NAME = "hasAnyRoles";
	private static final int PRECEDENCE = 300;

	private static final String ROLES_DELIMITER = ",";
	
	public static IProcessor create() {
		return new HasAnyRolesAttrProcessor();
	}
    
    public HasAnyRolesAttrProcessor() {
        super(ATTRIBUTE_NAME);
    }
    
    public HasAnyRolesAttrProcessor(final String attrName) {
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
    	
    	final String rawRoles = StringUtils.trim(element.getAttributeValue(attributeName));
    	Validate.notEmpty(attributeName, "value of '" + attributeName +"' must not be empty");
    	
    	final String[] splittedRoles = StringUtils.split(rawRoles, ROLES_DELIMITER);
    	for(String rawRole : splittedRoles) {
    		final String role = StringUtils.trim(rawRole);
    		Validate.notEmpty(role, "value of '" + attributeName + "' ("+ rawRoles +") seems to be malformed");
        	if(SecurityUtils.getSubject().hasRole(role)) {
        		return true;
        	}
    	}
    	
    	return false;
    }    
}
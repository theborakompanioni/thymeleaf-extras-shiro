package at.pollux.thymeleaf.shiro.dialect.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.IProcessor;

public class LacksRoleAttrProcessor extends HasRoleAttrProcessor {
	private static final String ATTRIBUTE_NAME = "lacksRole";
	
	public static IProcessor create() {
		return new LacksRoleAttrProcessor();
	}
    
    public LacksRoleAttrProcessor() {
        super(ATTRIBUTE_NAME);
    }
    
    public LacksRoleAttrProcessor(final String attrName) {
        super(attrName);
    }
    
    @Override
    protected boolean isVisible(final Arguments arguments, final Element element, final String attributeName) {
    	return !super.isVisible(arguments, element, attributeName);
    }
}
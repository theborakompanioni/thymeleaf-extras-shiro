package at.pollux.thymeleaf.shiro.dialect.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.IProcessor;

public class LacksPermissionAttrProcessor extends HasPermissionAttrProcessor {

	private static final String ATTRIBUTE_NAME = "lacksPermission";
	
	public static IProcessor create() {
		return new LacksPermissionAttrProcessor();
	}
    
    public LacksPermissionAttrProcessor() {
        super(ATTRIBUTE_NAME);
    }
    
    public LacksPermissionAttrProcessor(final String attrName) {
        super(attrName);
    }
    
    @Override
    protected boolean isVisible(final Arguments arguments, final Element element, final String attributeName) {
    	return !super.isVisible(arguments, element, attributeName);
    }    
}
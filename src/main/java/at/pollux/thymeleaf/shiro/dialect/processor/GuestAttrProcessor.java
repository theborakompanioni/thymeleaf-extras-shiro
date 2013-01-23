package at.pollux.thymeleaf.shiro.dialect.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.IProcessor;

public class GuestAttrProcessor extends UserAttrProcessor {
    private static final String ATTRIBUTE_NAME = "guest";
	
	public static IProcessor create() {
		return new GuestAttrProcessor();
	}
    
    public GuestAttrProcessor() {
        super(ATTRIBUTE_NAME);
    }
    
    public GuestAttrProcessor(final String attrName) {
        super(attrName);
    }
    
    @Override
    protected boolean isVisible(final Arguments arguments, final Element element, final String attributeName) {
    	return !super.isVisible(arguments, element, attributeName);
    }    
}
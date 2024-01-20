package org.tbk.thymeleaf.shiro.processor.attribute;

import org.tbk.thymeleaf.shiro.processor.ShiroFacade;
import org.tbk.thymeleaf.shiro.processor.ThymeleafFacade;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.List;

import static org.tbk.thymeleaf.shiro.processor.ThymeleafFacade.getRawValue;

public class HasRoleAttrProcessor extends AbstractAttributeTagProcessor {
    private static final String DELIMITER = ",";

    private static final String ATTRIBUTE_NAME = "hasRole";
    private static final int PRECEDENCE = 300;

    public HasRoleAttrProcessor(String dialectPrefix) {
        super(
                TemplateMode.HTML, // This processor will apply only to HTML mode
                dialectPrefix, // Prefix to be applied to name for matching
                null, // No tag name: match any tag name
                false, // No prefix to be applied to tag name
                ATTRIBUTE_NAME, // Name of the attribute that will be matched
                true, // Apply dialect prefix to attribute name
                PRECEDENCE, // Precedence (inside dialect's precedence)
                true); // Remove the matched attribute afterwards
    }

    @Override
    protected void doProcess(ITemplateContext iTemplateContext,
                             IProcessableElementTag iProcessableElementTag,
                             AttributeName attributeName,
                             String attributeValue,
                             IElementTagStructureHandler iElementTagStructureHandler) {
        String rawValue = ThymeleafFacade.getRawValue(iProcessableElementTag, attributeName);
        List<String> values = ThymeleafFacade.evaluateAsStringsWithDelimiter(iTemplateContext, rawValue, DELIMITER);

        if (ShiroFacade.hasAllRoles(values)) {
            iElementTagStructureHandler.removeAttribute(attributeName);
        } else {
            iElementTagStructureHandler.removeElement();
        }
    }
}
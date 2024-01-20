package org.tbk.thymeleaf.shiro.processor.attribute;

import org.tbk.thymeleaf.shiro.processor.ShiroFacade;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;
import org.unbescape.html.HtmlEscape;

public class PrincipalAttrProcessor extends AbstractAttributeTagProcessor {
    private static final String ATTRIBUTE_NAME = "principal";

    private static final int PRECEDENCE = 300;

    public PrincipalAttrProcessor(String dialectPrefix) {
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

        String type = iProcessableElementTag.getAttributeValue("type");
        String property = iProcessableElementTag.getAttributeValue("property");

        String text = ShiroFacade.getPrincipalText(type, property);
        String elementCompleteName = iProcessableElementTag.getElementCompleteName();

        IModelFactory modelFactory = iTemplateContext.getModelFactory();
        IModel model = modelFactory.createModel();

        model.add(modelFactory.createOpenElementTag(elementCompleteName));
        model.add(modelFactory.createText(HtmlEscape.escapeHtml5(text)));
        model.add(modelFactory.createCloseElementTag(elementCompleteName));

        iElementTagStructureHandler.replaceWith(model, false);
    }
}
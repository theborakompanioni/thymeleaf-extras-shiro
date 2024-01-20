package org.tbk.thymeleaf.shiro.processor.element;

import org.tbk.thymeleaf.shiro.processor.ShiroFacade;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class NotAuthenticatedElementProcessor extends AbstractElementTagProcessor {
    private static final String ELEMENT_NAME = "notauthenticated";
    private static final int PRECEDENCE = 300;

    public NotAuthenticatedElementProcessor(String dialectPrefix) {
        super(
                TemplateMode.HTML, // This processor will apply only to HTML mode
                dialectPrefix, // Prefix to be applied to name for matching
                ELEMENT_NAME, // Tag name: match specifically this tag
                true, // Apply dialect prefix to tag name
                null, // No attribute name: will match by tag name
                false, // No prefix to be applied to attribute name
                PRECEDENCE); // Precedence (inside dialect's own precedence)
    }

    @Override
    protected void doProcess(ITemplateContext iTemplateContext,
                             IProcessableElementTag iProcessableElementTag,
                             IElementTagStructureHandler iElementTagStructureHandler) {
        if (ShiroFacade.isNotAuthenticated()) {
            iElementTagStructureHandler.removeTags();
        } else {
            iElementTagStructureHandler.removeElement();
        }
    }
}

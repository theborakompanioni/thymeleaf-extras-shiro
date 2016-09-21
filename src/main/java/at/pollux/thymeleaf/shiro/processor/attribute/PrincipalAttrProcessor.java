/*****************************************************************************
 * Copyright (c) 2013, theborakompanioni (http://www.example.org)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ****************************************************************************/
package at.pollux.thymeleaf.shiro.processor.attribute;


import at.pollux.thymeleaf.shiro.processor.ShiroFacade;
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


    protected void doProcess(ITemplateContext iTemplateContext,
                             IProcessableElementTag iProcessableElementTag,
                             AttributeName attributeName,
                             String s,
                             IElementTagStructureHandler iElementTagStructureHandler) {

        final String type = iProcessableElementTag.getAttributeValue("type");
        final String property = iProcessableElementTag.getAttributeValue("property");
//        if (iProcessableElementTag.hasAttribute("type")) {
//            iElementTagStructureHandler.removeAttribute("type");
//        }
//        if (iProcessableElementTag.hasAttribute("property")) {
//            iElementTagStructureHandler.removeAttribute("property");
//        }
//        iElementTagStructureHandler.removeAttribute(attributeName);

        String text = ShiroFacade.getPrincipalText(type, property);

//        iElementTagStructureHandler.setBody(text, false);
        String elementCompleteName = iProcessableElementTag.getElementCompleteName();
        final IModelFactory modelFactory = iTemplateContext.getModelFactory();
        final IModel model = modelFactory.createModel();
        model.add(modelFactory.createOpenElementTag(elementCompleteName));
        model.add(modelFactory.createText(HtmlEscape.escapeHtml5(text)));
        model.add(modelFactory.createCloseElementTag(elementCompleteName));
        iElementTagStructureHandler.replaceWith(model,false);

    }
}
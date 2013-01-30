/*****************************************************************************
 * Copyright (c) 2013, theborakompanioni (http://www.example.org)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ****************************************************************************/
package at.pollux.thymeleaf.shiro.processor.element;

import org.apache.shiro.SecurityUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.element.AbstractConditionalVisibilityElementProcessor;
import org.thymeleaf.util.StringUtils;
import org.thymeleaf.util.Validate;

import at.pollux.thymeleaf.shiro.processor.IConditionalVisibilityElementProcessor;

public class HasRoleElementProcessor extends AbstractConditionalVisibilityElementProcessor implements IConditionalVisibilityElementProcessor {

    private static final String ATTRIBUTE_NAME = "hasRole";
    private static final int    PRECEDENCE     = 300;

    public static HasRoleElementProcessor create() {
        return new HasRoleElementProcessor();
    }

    protected HasRoleElementProcessor() {
        super(ATTRIBUTE_NAME);
    }

    protected HasRoleElementProcessor(final String attrName) {
        super(attrName);
    }

    @Override
    public int getPrecedence() {
        return PRECEDENCE;
    }

    @Override
    public boolean isVisible(final Arguments arguments, final Element element) {
        Validate.notNull(element, "element must not be null");

        final String role = StringUtils.trim(element.getAttributeValue("name"));
        Validate.notEmpty(role, "value of 'name' must not be empty");

        return SecurityUtils.getSubject().hasRole(role);
    }

    @Override
    public boolean removeHostElementIfVisible(final Arguments arguments, final Element element) {
        return true;
    }
}
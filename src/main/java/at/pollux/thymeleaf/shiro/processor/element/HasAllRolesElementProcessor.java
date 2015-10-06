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

import at.pollux.thymeleaf.shiro.processor.ShiroFacade;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.element.AbstractConditionalVisibilityElementProcessor;
import org.thymeleaf.util.StringUtils;

import java.util.List;

import static at.pollux.thymeleaf.shiro.processor.ThymeleafFacade.evaluateAsStringsWithDelimiter;
import static at.pollux.thymeleaf.shiro.processor.ThymeleafFacade.getRawValue;

public class HasAllRolesElementProcessor extends AbstractConditionalVisibilityElementProcessor {

    public static HasAllRolesElementProcessor create() {
        return new HasAllRolesElementProcessor();
    }

    private static final String ELEMENT_NAME = "hasallroles";
    private static final int PRECEDENCE = 300;

    private static final String DELIMITER = ",";

    protected HasAllRolesElementProcessor() {
        super(ELEMENT_NAME);
    }

    @Override
    public int getPrecedence() {
        return PRECEDENCE;
    }

    @Override
    public boolean removeHostElementIfVisible(final Arguments arguments, final Element element) {
        return true;
    }

    @Override
    public boolean isVisible(final Arguments arguments, final Element element) {
        String rawValue = getRawValue(element, "name");
        List<String> values = evaluateAsStringsWithDelimiter(arguments, rawValue, DELIMITER);

        return ShiroFacade.hasAllRoles(values);
    }
}
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

import at.pollux.thymeleaf.shiro.processor.InvertVisibilityElementProcessor;

public class LacksRoleElementProcessor extends InvertVisibilityElementProcessor<HasRoleElementProcessor> {
    private static final String ATTRIBUTE_NAME = "lacksRole";

    public static LacksRoleElementProcessor create() {
        return new LacksRoleElementProcessor();
    }

    protected LacksRoleElementProcessor() {
        super(ATTRIBUTE_NAME, HasRoleElementProcessor.create());
    }

}

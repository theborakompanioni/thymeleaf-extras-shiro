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

package at.pollux.thymeleaf.shiro.dialect;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import at.pollux.thymeleaf.shiro.processor.attribute.AuthenticatedAttrProcessor;
import at.pollux.thymeleaf.shiro.processor.attribute.GuestAttrProcessor;
import at.pollux.thymeleaf.shiro.processor.attribute.HasAnyRolesAttrProcessor;
import at.pollux.thymeleaf.shiro.processor.attribute.HasPermissionAttrProcessor;
import at.pollux.thymeleaf.shiro.processor.attribute.HasRoleAttrProcessor;
import at.pollux.thymeleaf.shiro.processor.attribute.LacksPermissionAttrProcessor;
import at.pollux.thymeleaf.shiro.processor.attribute.LacksRoleAttrProcessor;
import at.pollux.thymeleaf.shiro.processor.attribute.NotAuthenticatedAttrProcessor;
import at.pollux.thymeleaf.shiro.processor.attribute.PrincipalAttrProcessor;
import at.pollux.thymeleaf.shiro.processor.attribute.UserAttrProcessor;
import at.pollux.thymeleaf.shiro.processor.element.AuthenticatedElementProcessor;
import at.pollux.thymeleaf.shiro.processor.element.GuestElementProcessor;
import at.pollux.thymeleaf.shiro.processor.element.HasPermissionElementProcessor;
import at.pollux.thymeleaf.shiro.processor.element.HasRoleElementProcessor;
import at.pollux.thymeleaf.shiro.processor.element.LacksPermissionElementProcessor;
import at.pollux.thymeleaf.shiro.processor.element.NotAuthenticatedElementProcessor;
import at.pollux.thymeleaf.shiro.processor.element.UserElementProcessor;

public class ShiroDialect extends AbstractDialect {
    private static final String          PREFIX     = "shiro";
    private static final Set<IProcessor> processors = new HashSet<IProcessor>();
    static {
        processors.add(PrincipalAttrProcessor.create());
        processors.add(LacksRoleAttrProcessor.create());
        processors.add(HasAnyRolesAttrProcessor.create());

        processors.add(HasRoleAttrProcessor.create());
        processors.add(HasRoleElementProcessor.create());

        processors.add(HasPermissionAttrProcessor.create());
        processors.add(HasPermissionElementProcessor.create());

        processors.add(LacksPermissionAttrProcessor.create());
        processors.add(LacksPermissionElementProcessor.create());

        processors.add(AuthenticatedAttrProcessor.create());
        processors.add(AuthenticatedElementProcessor.create());

        processors.add(NotAuthenticatedAttrProcessor.create());
        processors.add(NotAuthenticatedElementProcessor.create());

        processors.add(GuestAttrProcessor.create());
        processors.add(GuestElementProcessor.create());

        processors.add(UserAttrProcessor.create());
        processors.add(UserElementProcessor.create());
    }

    public ShiroDialect() {
        super();
    }

    public String getPrefix() {
        return PREFIX;
    }

    public boolean isLenient() {
        return false;
    }

    @Override
    public Set<IProcessor> getProcessors() {
        return Collections.unmodifiableSet(processors);
    }
}

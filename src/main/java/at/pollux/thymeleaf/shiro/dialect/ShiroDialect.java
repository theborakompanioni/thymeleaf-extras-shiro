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
package at.pollux.thymeleaf.shiro.dialect;

import at.pollux.thymeleaf.shiro.processor.attribute.*;
import at.pollux.thymeleaf.shiro.processor.element.*;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class ShiroDialect extends AbstractProcessorDialect {

    public static final String NAME = "Shiro";

    private static final String PREFIX = "shiro";



    public ShiroDialect() {
        super(NAME,PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }



    public Set<IProcessor> getProcessors(String s) {
        return createStandardProcessorsSet(s);
    }


    public static Set<IProcessor> createStandardProcessorsSet(String dialectPrefix) {
        LinkedHashSet processors = new LinkedHashSet();


        processors.add(new  PrincipalAttrProcessor(PREFIX));
        processors.add(new  PrincipalElementProcessor(PREFIX));

        processors.add(new  HasAllRolesAttrProcessor(PREFIX));
        processors.add(new  HasAllRolesElementProcessor(PREFIX));
     
        processors.add(new  HasAnyRolesAttrProcessor(PREFIX));
        processors.add(new  HasAnyRolesElementProcessor(PREFIX));
     
        processors.add(new  HasRoleAttrProcessor(PREFIX));
        processors.add(new  HasRoleElementProcessor(PREFIX));
     
        processors.add(new  LacksRoleAttrProcessor(PREFIX));
        processors.add(new  LacksRoleElementProcessor(PREFIX));
     
        processors.add(new  HasAllPermissionsAttrProcessor(PREFIX));
        processors.add(new  HasAllPermissionsElementProcessor(PREFIX));
     
        processors.add(new  HasAnyPermissionsAttrProcessor(PREFIX));
        processors.add(new  HasAnyPermissionsElementProcessor(PREFIX));
     
        processors.add(new  HasPermissionAttrProcessor(PREFIX));
        processors.add(new  HasPermissionElementProcessor(PREFIX));
     
        processors.add(new  LacksPermissionAttrProcessor(PREFIX));
        processors.add(new  LacksPermissionElementProcessor(PREFIX));
     
        processors.add(new  AuthenticatedAttrProcessor(PREFIX));
        processors.add(new  AuthenticatedElementProcessor(PREFIX));
     
        processors.add(new  NotAuthenticatedAttrProcessor(PREFIX));
        processors.add(new  NotAuthenticatedElementProcessor(PREFIX));
     
        processors.add(new  GuestAttrProcessor(PREFIX));
        processors.add(new  GuestElementProcessor(PREFIX));
     
        processors.add(new  UserAttrProcessor(PREFIX));
        processors.add(new  UserElementProcessor(PREFIX));


        return processors;
    }
}

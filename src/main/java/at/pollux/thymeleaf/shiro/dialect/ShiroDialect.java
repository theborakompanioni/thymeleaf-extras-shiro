/*****************************************************************************
 * Copyright (c) 2013, theborakompanioni (http://www.example.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ****************************************************************************/

package at.pollux.thymeleaf.shiro.dialect;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import at.pollux.thymeleaf.shiro.dialect.processor.AuthenticatedAttrProcessor;
import at.pollux.thymeleaf.shiro.dialect.processor.GuestAttrProcessor;
import at.pollux.thymeleaf.shiro.dialect.processor.HasAnyRolesAttrProcessor;
import at.pollux.thymeleaf.shiro.dialect.processor.HasPermissionAttrProcessor;
import at.pollux.thymeleaf.shiro.dialect.processor.HasRoleAttrProcessor;
import at.pollux.thymeleaf.shiro.dialect.processor.LacksPermissionAttrProcessor;
import at.pollux.thymeleaf.shiro.dialect.processor.LacksRoleAttrProcessor;
import at.pollux.thymeleaf.shiro.dialect.processor.NotAuthenticatedAttrProcessor;
import at.pollux.thymeleaf.shiro.dialect.processor.PrincipalAttrProcessor;
import at.pollux.thymeleaf.shiro.dialect.processor.UserAttrProcessor;

public class ShiroDialect extends AbstractDialect {
	private static final String PREFIX = "shiro";
	private static final Set<IProcessor> PROCESSORS = new HashSet<IProcessor>();
	static {
		PROCESSORS.add(PrincipalAttrProcessor.create());
		PROCESSORS.add(AuthenticatedAttrProcessor.create());
		PROCESSORS.add(NotAuthenticatedAttrProcessor.create());
		PROCESSORS.add(HasRoleAttrProcessor.create());
		PROCESSORS.add(LacksRoleAttrProcessor.create());
		PROCESSORS.add(HasAnyRolesAttrProcessor.create());
		PROCESSORS.add(HasPermissionAttrProcessor.create());
		PROCESSORS.add(LacksPermissionAttrProcessor.create());
		PROCESSORS.add(GuestAttrProcessor.create());
		PROCESSORS.add(UserAttrProcessor.create());
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
		return Collections.unmodifiableSet(PROCESSORS);
	}
}

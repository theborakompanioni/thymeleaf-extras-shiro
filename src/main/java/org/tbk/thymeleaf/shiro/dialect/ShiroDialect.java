package org.tbk.thymeleaf.shiro.dialect;

import org.tbk.thymeleaf.shiro.processor.attribute.*;
import org.tbk.thymeleaf.shiro.processor.element.*;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.processor.StandardXmlNsTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.LinkedHashSet;
import java.util.Set;

public class ShiroDialect extends AbstractProcessorDialect {
    private static final String NAME = "Shiro";
    private static final String PREFIX = "shiro";

    public ShiroDialect() {
        super(NAME, PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }

    public Set<IProcessor> getProcessors(String dialectPrefix) {
        return createStandardProcessorsSet(dialectPrefix);
    }

    private static Set<IProcessor> createStandardProcessorsSet(String dialectPrefix) {
        LinkedHashSet<IProcessor> processors = new LinkedHashSet<IProcessor>();

        processors.add(new PrincipalAttrProcessor(dialectPrefix));
        processors.add(new PrincipalElementProcessor(dialectPrefix));

        processors.add(new HasAllRolesAttrProcessor(dialectPrefix));
        processors.add(new HasAllRolesElementProcessor(dialectPrefix));

        processors.add(new HasAnyRolesAttrProcessor(dialectPrefix));
        processors.add(new HasAnyRolesElementProcessor(dialectPrefix));

        processors.add(new HasRoleAttrProcessor(dialectPrefix));
        processors.add(new HasRoleElementProcessor(dialectPrefix));

        processors.add(new LacksRoleAttrProcessor(dialectPrefix));
        processors.add(new LacksRoleElementProcessor(dialectPrefix));

        processors.add(new HasAllPermissionsAttrProcessor(dialectPrefix));
        processors.add(new HasAllPermissionsElementProcessor(dialectPrefix));

        processors.add(new HasAnyPermissionsAttrProcessor(dialectPrefix));
        processors.add(new HasAnyPermissionsElementProcessor(dialectPrefix));

        processors.add(new HasPermissionAttrProcessor(dialectPrefix));
        processors.add(new HasPermissionElementProcessor(dialectPrefix));

        processors.add(new LacksPermissionAttrProcessor(dialectPrefix));
        processors.add(new LacksPermissionElementProcessor(dialectPrefix));

        processors.add(new AuthenticatedAttrProcessor(dialectPrefix));
        processors.add(new AuthenticatedElementProcessor(dialectPrefix));

        processors.add(new NotAuthenticatedAttrProcessor(dialectPrefix));
        processors.add(new NotAuthenticatedElementProcessor(dialectPrefix));

        processors.add(new GuestAttrProcessor(dialectPrefix));
        processors.add(new GuestElementProcessor(dialectPrefix));

        processors.add(new UserAttrProcessor(dialectPrefix));
        processors.add(new UserElementProcessor(dialectPrefix));

		// Remove the xmlns:prefix attributes we might add for IDE validation but should not be exposed in the web browsers.
		processors.add(new StandardXmlNsTagProcessor(TemplateMode.HTML, dialectPrefix));

        return processors;
    }
}

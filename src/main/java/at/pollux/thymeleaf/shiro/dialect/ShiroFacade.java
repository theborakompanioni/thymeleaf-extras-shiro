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

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import org.apache.shiro.SecurityUtils;
import org.thymeleaf.util.StringUtils;
import org.thymeleaf.util.Validate;

public final class ShiroFacade {
    public static boolean isAuthenticated() {
        return SecurityUtils.getSubject() != null && SecurityUtils.getSubject().isAuthenticated();
    }

    public static boolean isNotAuthenticated() {
        return !ShiroFacade.isAuthenticated();
    }

    public static boolean isUser() {
        return SecurityUtils.getSubject() != null && SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static boolean isGuest() {
        return !ShiroFacade.isUser();
    }

    public static boolean hasPermission(final String p) {
        return SecurityUtils.getSubject() != null && SecurityUtils.getSubject().isPermitted(p);
    }

    public static boolean lacksPermission(final String p) {
        return !ShiroFacade.hasPermission(p);
    }

    public static boolean hasRole(final String roleName) {
        return SecurityUtils.getSubject() != null && SecurityUtils.getSubject().hasRole(roleName);
    }

    public static boolean lacksRole(final String roleName) {
        return !ShiroFacade.hasRole(roleName);
    }

    public static boolean hasAnyRoles(final String... roles) {
        if (SecurityUtils.getSubject() != null) {
            for (final String role : roles) {
                if (SecurityUtils.getSubject().hasRole(StringUtils.trim(role))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getPrincipalText(final String type, final String property) {
        if (SecurityUtils.getSubject() == null) {
            return "";
        }

        Object principal = SecurityUtils.getSubject().getPrincipal();

        if (type != null || property != null) {
            if (type != null) {
                principal = getPrincipalFromClassName(type);
            }
            if (principal != null) {
                return (property == null) ? principal.toString() : getPrincipalProperty(principal, property);
            }
        }

        return principal != null ? principal.toString() : "";
    }

    public static Object getPrincipalFromClassName(final String type) {
        Object principal = null;

        try {
            final Class<?> cls = Class.forName(type);
            principal = SecurityUtils.getSubject().getPrincipals().oneByType(cls);
        } catch (final ClassNotFoundException e) {
            Validate.isTrue(false, "Unable to find class for name [" + type + "]");
        }
        return principal;
    }

    public static String getPrincipalProperty(final Object principal, final String property) {
        try {
            final BeanInfo bi = Introspector.getBeanInfo(principal.getClass());
            for (final PropertyDescriptor pd : bi.getPropertyDescriptors()) {
                if (pd.getName().equals(property)) {
                    final Object value = pd.getReadMethod().invoke(principal, (Object[]) null);
                    return String.valueOf(value);
                }
            }
        } catch (final Exception e) {
            Validate.isTrue(false, "Error reading property [" + property + "] from principal of type [" + principal.getClass().getName() + "]");
        }

        Validate.isTrue(false, "Property [" + property + "] not found in principal of type [" + principal.getClass().getName() + "]");
        return "";
    }
}

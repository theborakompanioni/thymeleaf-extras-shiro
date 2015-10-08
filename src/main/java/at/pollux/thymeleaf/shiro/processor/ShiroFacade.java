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
package at.pollux.thymeleaf.shiro.processor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.thymeleaf.util.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Collection;

import static java.util.Collections.singleton;

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

    public static boolean hasPermission(final String permission) {
        return hasAllPermissions(singleton(permission));
    }

    public static boolean lacksPermission(final String permission) {
        return !ShiroFacade.hasPermission(permission);
    }

    public static boolean hasAnyPermissions(final Collection<String> permissions) {
        if (SecurityUtils.getSubject() != null) {
            final Subject subject = SecurityUtils.getSubject();
            for (final String permission : permissions) {
                if (subject.isPermitted(permission)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasAnyPermissions(final String... permissions) {
        return hasAnyPermissions(Arrays.asList(permissions));
    }


    public static boolean hasAllPermissions(Collection<String> permissions) {
        if (SecurityUtils.getSubject() != null) {
            if (permissions.isEmpty()) {
                return false;
            }

            final Subject subject = SecurityUtils.getSubject();
            for (final String permission : permissions) {
                if (!subject.isPermitted(permission)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean hasAllPermissions(String... permissions) {
        return hasAllPermissions(Arrays.asList(permissions));
    }

    public static boolean hasRole(final String roleName) {
        return hasAllRoles(singleton(roleName));
    }

    public static boolean lacksRole(final String roleName) {
        return !hasRole(roleName);
    }

    public static boolean hasAnyRoles(final Collection<String> roles) {
        if (SecurityUtils.getSubject() != null) {
            final Subject subject = SecurityUtils.getSubject();
            for (final String role : roles) {
                if (subject.hasRole(StringUtils.trim(role))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasAnyRoles(final String... roles) {
        return hasAnyRoles(Arrays.asList(roles));
    }

    public static boolean hasAllRoles(final Collection<String> roles) {
        if (SecurityUtils.getSubject() != null) {
            if (roles.isEmpty()) {
                return false;
            }
            
            final Subject subject = SecurityUtils.getSubject();
            for (final String role : roles) {
                if (!subject.hasRole(StringUtils.trim(role))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean hasAllRoles(final String... roles) {
        return hasAllRoles(Arrays.asList(roles));
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
        Object principal;

        try {
            final Class<?> cls = Class.forName(type);
            principal = SecurityUtils.getSubject().getPrincipals().oneByType(cls);
        } catch (final ClassNotFoundException e) {
            String message = "Unable to find class for name [" + type + "]";
            throw new IllegalArgumentException(message, e);
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
            String message = "Error reading property [" + property + "] from principal of type [" + principal.getClass().getName() + "]";
            throw new IllegalArgumentException(message, e);
        }

        throw new IllegalArgumentException("Property [" + property + "] not found in principal of type [" + principal.getClass().getName() + "]");
    }

}

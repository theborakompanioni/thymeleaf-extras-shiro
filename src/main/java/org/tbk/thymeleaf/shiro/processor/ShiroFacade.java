package org.tbk.thymeleaf.shiro.processor;

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

    private ShiroFacade() {
        throw new UnsupportedOperationException();
    }

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

    public static boolean hasPermission(String permission) {
        return hasAllPermissions(singleton(permission));
    }

    public static boolean lacksPermission(String permission) {
        return !ShiroFacade.hasPermission(permission);
    }

    public static boolean hasAnyPermissions(Collection<String> permissions) {
        if (SecurityUtils.getSubject() != null) {
            Subject subject = SecurityUtils.getSubject();
            for (String permission : permissions) {
                if (subject.isPermitted(permission)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasAnyPermissions(String... permissions) {
        return hasAnyPermissions(Arrays.asList(permissions));
    }


    public static boolean hasAllPermissions(Collection<String> permissions) {
        if (SecurityUtils.getSubject() != null) {
            if (permissions.isEmpty()) {
                return false;
            }

            Subject subject = SecurityUtils.getSubject();
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

    public static boolean hasRole(String roleName) {
        return hasAllRoles(singleton(roleName));
    }

    public static boolean lacksRole(String roleName) {
        return !hasRole(roleName);
    }

    public static boolean hasAnyRoles(Collection<String> roles) {
        if (SecurityUtils.getSubject() != null) {
            Subject subject = SecurityUtils.getSubject();
            for (String role : roles) {
                if (subject.hasRole(StringUtils.trim(role))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean hasAnyRoles(String... roles) {
        return hasAnyRoles(Arrays.asList(roles));
    }

    public static boolean hasAllRoles(Collection<String> roles) {
        if (SecurityUtils.getSubject() != null) {
            if (roles.isEmpty()) {
                return false;
            }

            Subject subject = SecurityUtils.getSubject();
            for (String role : roles) {
                if (!subject.hasRole(StringUtils.trim(role))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean hasAllRoles(String... roles) {
        return hasAllRoles(Arrays.asList(roles));
    }

    public static String getPrincipalText(String type, String property) {
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

    public static Object getPrincipalFromClassName(String type) {
        Object principal;

        try {
            Class<?> cls = Class.forName(type);
            principal = SecurityUtils.getSubject().getPrincipals().oneByType(cls);
        } catch (ClassNotFoundException e) {
            String message = "Unable to find class for name [" + type + "]";
            throw new IllegalArgumentException(message, e);
        }

        return principal;
    }

    public static String getPrincipalProperty(Object principal, String property) {
        try {
            BeanInfo bi = Introspector.getBeanInfo(principal.getClass());
            for (PropertyDescriptor pd : bi.getPropertyDescriptors()) {
                if (pd.getName().equals(property)) {
                    Object value = pd.getReadMethod().invoke(principal, (Object[]) null);
                    return String.valueOf(value);
                }
            }
        } catch (Exception e) {
            String message = "Error reading property [" + property + "] from principal of type [" + principal.getClass().getName() + "]";
            throw new IllegalArgumentException(message, e);
        }

        throw new IllegalArgumentException("Property [" + property + "] not found in principal of type [" + principal.getClass().getName() + "]");
    }

}

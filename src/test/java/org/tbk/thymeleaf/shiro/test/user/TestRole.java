package org.tbk.thymeleaf.shiro.test.user;

import java.util.Collections;
import java.util.Set;


/**
 * @author tbk
 */
public class TestRole {
    private final String label;
    private final Set<TestPermissions> permissions;

    public TestRole(String name, Set<TestPermissions> permissions) {
        this.label = name;
        this.permissions = Collections.unmodifiableSet(permissions);
    }

    public String getLabel() {
        return label;
    }

    public Set<TestPermissions> getPermissions() {
        return permissions;
    }
}

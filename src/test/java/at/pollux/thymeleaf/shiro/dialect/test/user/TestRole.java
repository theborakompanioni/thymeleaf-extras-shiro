package at.pollux.thymeleaf.shiro.dialect.test.user;

import java.util.Collections;
import java.util.Set;

/**
 * Created by void on 08.10.15.
 */
public class TestRole {
    private final String label;
    private final Set<Permissions> permissions;

    public TestRole(String name, Set<Permissions> permissions) {
        this.label = name;
        this.permissions = Collections.unmodifiableSet(permissions);
    }

    public String getLabel() {
        return label;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }
}

package at.pollux.thymeleaf.shiro.dialect.test.user;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

/**
 * @author tbk
 */
public enum TestRoles {

    ROLE_A(new TestRole("rolea", Sets.newHashSet(
            Permissions.PERMISSION_ALL
    ))),
    ROLE_B(new TestRole("roleb", Sets.newHashSet(
            Permissions.PERMISSION_TYPE_1_ACTION_1_INST_1
    ))),
    ROLE_C(new TestRole("rolec", Sets.newHashSet(
            Permissions.PERMISSION_TYPE_1_ACTION_2
    ))),
    ROLE_D(new TestRole("roled", Sets.newHashSet(
            Permissions.PERMISSION_TYPE_3
    ))),
    ROLE_E(new TestRole("rolee", Sets.<Permissions>newHashSet()));

    private static Joiner colonJoiner = Joiner.on(",");

    private final TestRole delegate;

    TestRoles(TestRole user) {
        this.delegate = user;
    }

    public String label() {
        return delegate.getLabel();
    }

    public String permissions() {
        return colonJoiner.join(Collections2.transform(delegate.getPermissions(), new Function<Permissions, String>() {
            public String apply(Permissions input) {
                return input.label();
            }
        }));
    }
}

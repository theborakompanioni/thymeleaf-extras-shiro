package at.pollux.thymeleaf.shiro.test.user;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

/**
 * @author tbk
 */
public enum TestRoles {

    ROLE_A(new TestRole("rolea", Sets.newHashSet(
            TestPermissions.PERMISSION_ALL
    ))),
    ROLE_B(new TestRole("roleb", Sets.newHashSet(
            TestPermissions.PERMISSION_TYPE_1_ACTION_1_INST_1
    ))),
    ROLE_C(new TestRole("rolec", Sets.newHashSet(
            TestPermissions.PERMISSION_TYPE_1_ACTION_2
    ))),
    ROLE_D(new TestRole("roled", Sets.newHashSet(
            TestPermissions.PERMISSION_TYPE_3
    ))),
    ROLE_E(new TestRole("rolee", Sets.<TestPermissions>newHashSet()));

    private static Joiner colonJoiner = Joiner.on(",");

    private final TestRole delegate;

    TestRoles(TestRole user) {
        this.delegate = user;
    }

    public String label() {
        return delegate.getLabel();
    }

    public String permissions() {
        return colonJoiner.join(Collections2.transform(delegate.getPermissions(), new Function<TestPermissions, String>() {
            public String apply(TestPermissions input) {
                return input.label();
            }
        }));
    }
}

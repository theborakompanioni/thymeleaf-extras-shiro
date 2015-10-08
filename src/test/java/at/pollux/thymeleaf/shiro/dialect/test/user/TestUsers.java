package at.pollux.thymeleaf.shiro.dialect.test.user;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

/**
 * Created by void on 08.10.15.
 */
public enum TestUsers {
    ALICE(new TestUser("u1", "p1", Sets.newHashSet(
            TestRoles.ROLE_A, TestRoles.ROLE_D
    ))),
    BOB(new TestUser("u2", "p2", Sets.newHashSet(
            TestRoles.ROLE_B, TestRoles.ROLE_C
    ))),
    CAESAR(new TestUser("u3", "p3", Sets.newHashSet(
            TestRoles.ROLE_C, TestRoles.ROLE_E
    )));

    private static Joiner comaJoiner = Joiner.on(",");

    private final TestUser delegate;

    TestUsers(TestUser user) {
        this.delegate = user;
    }

    public String email() {
        return delegate.getEmail();
    }

    public String password() {
        return delegate.getPassword();
    }

    public String roles() {
        return comaJoiner.join(
                delegate.getPassword(), comaJoiner.join(
                        Collections2.transform(delegate.getRoles(), new Function<TestRoles, String>() {
                            public String apply(TestRoles input) {
                                return input.label();
                            }
                        }))
        );
    }
}

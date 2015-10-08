package at.pollux.thymeleaf.shiro.processor;

import at.pollux.thymeleaf.shiro.dialect.test.AbstractThymeleafShiroDialectTest;
import at.pollux.thymeleaf.shiro.dialect.test.user.Permissions;
import at.pollux.thymeleaf.shiro.dialect.test.user.TestRoles;
import at.pollux.thymeleaf.shiro.dialect.test.user.TestUsers;
import com.google.common.collect.Sets;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.UUID;

import static at.pollux.thymeleaf.shiro.dialect.test.user.Permissions.PERMISSION_TYPE_1_ACTION_2;
import static at.pollux.thymeleaf.shiro.processor.ShiroFacade.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by void on 09.10.15.
 */
@RunWith(JUnitParamsRunner.class)
public class ShiroFacadeTest extends AbstractThymeleafShiroDialectTest {

    @Test(expected = IllegalArgumentException.class)
    @Parameters(value = {""})
    public void testIllegalArgumentExceptionWithLoggedInUser(String value) throws Exception {
        final Subject subject = createAndLoginSubject(TestUsers.ALICE);
        try {
            hasPermission(value);
        } finally {
            subject.logout();
        }
    }

    @Test
    public void testGuest() throws Exception {
        assertThat("Guest does not have permission", !hasPermission(null));
        assertThat("Guest does not have permission", !hasPermission(""));
        assertThat("Guest does not have permission", !hasPermission("foo"));
        assertThat("Guest lacks permission", lacksPermission(null));
        assertThat("Guest lacks permission", lacksPermission(""));
        assertThat("Guest lacks permission", lacksPermission("foo"));
        assertThat("Guest does not have all permissions", !hasAllPermissions(Collections.<String>emptySet()));
        assertThat("Guest does not have all permissions", !hasAllPermissions(null, "", "bar"));
        assertThat("Guest does not have all permissions", !hasAllPermissions("foo", "bar"));
        assertThat("Guest does not have any permissions", !hasAnyPermissions("foo", "bar"));
        assertThat("Guest does not have any permissions", !hasAnyPermissions(null, "", "bar"));

        assertThat("Guest does not have role", !hasRole(null));
        assertThat("Guest does not have role", !hasRole(""));
        assertThat("Guest does not have role", !hasRole("foo"));
        assertThat("Guest lacks role", lacksRole(null));
        assertThat("Guest lacks role", lacksRole(""));
        assertThat("Guest lacks role", lacksRole("foo"));
        assertThat("Guest does not have all role", !hasAllRoles(Collections.<String>emptySet()));
        assertThat("Guest does not have all role", !hasAllRoles(null, "", "bar"));
        assertThat("Guest does not have all role", !hasAllRoles("foo", "bar"));
        assertThat("Guest does not have any role", !hasAnyRoles(Collections.<String>emptySet()));
        assertThat("Guest does not have any role", !hasAnyRoles("foo", "bar"));
        assertThat("Guest does not have any role", !hasAnyRoles(null, "", "bar"));
    }

    @Test
    public void testAlice() throws Exception {
        final Subject subject = createAndLoginSubject(TestUsers.ALICE);

        String randomPermission = UUID.randomUUID().toString();
        final String roleAPermissions = TestRoles.ROLE_A.permissions();
        assertThat("User Alice has '*' permission", hasPermission("*"));
        assertThat("User Alice has Role A permission", hasPermission(roleAPermissions));
        assertThat("User Alice does not lack permission", !lacksPermission(randomPermission));

        assertThat("User Alice has all permissions", hasAllPermissions("foo", roleAPermissions, randomPermission));
        assertThat("User Alice has any permissions", hasAnyPermissions("foo", roleAPermissions, randomPermission));
        assertThat("User Alice has any permissions", hasAnyPermissions("foo", randomPermission));
        assertThat("User Alice has any random permission", hasPermission(randomPermission));

        subject.logout();
    }


    @Test
    public void testBob() throws Exception {
        final Subject subject = createAndLoginSubject(TestUsers.BOB);
        String randomPermission = UUID.randomUUID().toString();

        assertThat("User Bob does not have any random permission", !hasPermission(randomPermission));
        assertThat("User Bob has permission", hasPermission(Permissions.PERMISSION_TYPE_1_ACTION_1_INST_1.label()));

        assertThat("User Bob lacks random permission", lacksPermission(randomPermission));
        assertThat("User Bob does not lack permission", !lacksPermission(Permissions.PERMISSION_TYPE_1_ACTION_1_INST_1.label()));

        final String roleBPermissions = TestRoles.ROLE_B.permissions();
        final String roleCPermissions = TestRoles.ROLE_C.permissions();
        assertThat("User Bob does not have all permissions", !hasAllPermissions(Sets.<String>newHashSet()));
        assertThat("User Bob has role B permissions", hasAllPermissions(roleBPermissions));
        assertThat("User Bob has role C permissions", hasAllPermissions(roleCPermissions));
        assertThat("User Bob has permissions", hasAllPermissions(
                Permissions.PERMISSION_TYPE_1_ACTION_1_INST_1.label(),
                PERMISSION_TYPE_1_ACTION_2.label()
        ));
        assertThat("User Bob does not have all permissions", !hasAllPermissions("foo", "bar", randomPermission));

        assertThat("User Bob has any permissions", hasAnyPermissions("foo", "bar",
                Permissions.PERMISSION_TYPE_1_ACTION_1_INST_1.label()));
        assertThat("User Bob does not have any permissions", !hasAnyPermissions("foo", "bar", randomPermission));

        subject.logout();
    }

    @Test
    public void testCaesar() throws Exception {
        final Subject subject = createAndLoginSubject(TestUsers.CAESAR);

        assertThat("Caesar has permission", hasPermission(PERMISSION_TYPE_1_ACTION_2.label()));
        assertThat("Caesar does not have permission", !hasPermission("foo"));

        assertThat("Caesar lacks permission", lacksPermission("foo"));
        assertThat("Caesar does not lack permission", !lacksPermission(PERMISSION_TYPE_1_ACTION_2.label()));

        assertThat("Caesar has all permissions", hasAllPermissions(PERMISSION_TYPE_1_ACTION_2.label()));
        assertThat("Caesar does not have all permissions", !hasAllPermissions(Collections.<String>emptySet()));
        assertThat("Caesar does not have all permissions", !hasAllPermissions("foo", "bar"));

        assertThat("Caesar has any permissions", hasAnyPermissions("foo", PERMISSION_TYPE_1_ACTION_2.label()));
        assertThat("Caesar does not have any permissions", !hasAnyPermissions(Collections.<String>emptySet()));
        assertThat("Caesar does not have any permissions", !hasAnyPermissions("foo", "bar"));

        subject.logout();
    }
}
package org.tbk.thymeleaf.shiro.processor;

import org.tbk.thymeleaf.shiro.test.AbstractThymeleafShiroDialectTest;
import org.tbk.thymeleaf.shiro.test.user.TestPermissions;
import org.tbk.thymeleaf.shiro.test.user.TestRoles;
import org.tbk.thymeleaf.shiro.test.user.TestUsers;
import com.google.common.collect.Sets;
import junitparams.JUnitParamsRunner;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.UUID;

import static org.tbk.thymeleaf.shiro.processor.ShiroFacade.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(JUnitParamsRunner.class)
public class ShiroFacadeTest extends AbstractThymeleafShiroDialectTest {

    @Test(expected = IllegalArgumentException.class)
    public void testOddityOfShiroToThrowExceptionOnLoggedInUserWithEmptyPermissionString() throws Exception {
        final Subject subject = createAndLoginSubject(TestUsers.ALICE);
        final String emptyPermission = "";
        try {
            hasPermission(emptyPermission);
        } catch (Exception e) {
            String expectedMessage = "Wildcard string cannot be null or empty. Make sure permission strings are properly formatted.";
            assertThat(e.getMessage(), is(equalTo(expectedMessage)));

            throw e;
        } finally {
            subject.logout();
        }
    }

    @Test
    public void itShouldVerifyGuestRolesAndPermissions() throws Exception {
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
    public void itShouldVerifyUserAliceRolesAndPermissions() throws Exception {
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
    public void itShouldVerifyUserBobRolesAndPermissions() throws Exception {
        final Subject subject = createAndLoginSubject(TestUsers.BOB);
        String randomPermission = UUID.randomUUID().toString();

        assertThat("User Bob does not have any random permission", !hasPermission(randomPermission));
        assertThat("User Bob has permission", hasPermission(TestPermissions.PERMISSION_TYPE_1_ACTION_1_INST_1.label()));

        assertThat("User Bob lacks random permission", lacksPermission(randomPermission));
        assertThat("User Bob does not lack permission", !lacksPermission(TestPermissions.PERMISSION_TYPE_1_ACTION_1_INST_1.label()));

        final String roleBPermissions = TestRoles.ROLE_B.permissions();
        final String roleCPermissions = TestRoles.ROLE_C.permissions();
        assertThat("User Bob does not have all permissions", !hasAllPermissions(Sets.<String>newHashSet()));
        assertThat("User Bob has role B permissions", hasAllPermissions(roleBPermissions));
        assertThat("User Bob has role C permissions", hasAllPermissions(roleCPermissions));
        assertThat("User Bob has permissions", hasAllPermissions(
                TestPermissions.PERMISSION_TYPE_1_ACTION_1_INST_1.label(),
                TestPermissions.PERMISSION_TYPE_1_ACTION_2.label()
        ));
        assertThat("User Bob does not have all permissions", !hasAllPermissions("foo", "bar", randomPermission));

        assertThat("User Bob has any permissions", hasAnyPermissions("foo", "bar",
                TestPermissions.PERMISSION_TYPE_1_ACTION_1_INST_1.label()));
        assertThat("User Bob does not have any permissions", !hasAnyPermissions("foo", "bar", randomPermission));

        subject.logout();
    }

    @Test
    public void itShouldVerifyUserCaesarRolesAndPermissions() throws Exception {
        final Subject subject = createAndLoginSubject(TestUsers.CAESAR);

        assertThat("Caesar has permission", hasPermission(TestPermissions.PERMISSION_TYPE_1_ACTION_2.label()));
        assertThat("Caesar does not have permission", !hasPermission("foo"));

        assertThat("Caesar lacks permission", lacksPermission("foo"));
        assertThat("Caesar does not lack permission", !lacksPermission(TestPermissions.PERMISSION_TYPE_1_ACTION_2.label()));

        assertThat("Caesar has all permissions", ShiroFacade.hasAllPermissions(TestPermissions.PERMISSION_TYPE_1_ACTION_2.label()));
        assertThat("Caesar does not have all permissions", !hasAllPermissions(Collections.<String>emptySet()));
        assertThat("Caesar does not have all permissions", !hasAllPermissions("foo", "bar"));

        assertThat("Caesar has any permissions", hasAnyPermissions("foo", TestPermissions.PERMISSION_TYPE_1_ACTION_2.label()));
        assertThat("Caesar does not have any permissions", !hasAnyPermissions(Collections.<String>emptySet()));
        assertThat("Caesar does not have any permissions", !hasAnyPermissions("foo", "bar"));

        subject.logout();
    }
}
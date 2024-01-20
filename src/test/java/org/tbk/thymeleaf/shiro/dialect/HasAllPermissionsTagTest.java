package org.tbk.thymeleaf.shiro.dialect;

import org.tbk.thymeleaf.shiro.test.AbstractThymeleafShiroDialectTest;
import org.tbk.thymeleaf.shiro.test.mother.PermissionsMother.HasAllPermissions;
import org.tbk.thymeleaf.shiro.test.user.TestUsers;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.tbk.thymeleaf.shiro.test.user.TestPermissions;
import org.thymeleaf.context.Context;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

/**
 * @author tbk
 */
@RunWith(JUnitParamsRunner.class)
public class HasAllPermissionsTagTest extends AbstractThymeleafShiroDialectTest {

    private static final String FILE_UNDER_TEST = "shiro_hasAllPermissions.html";

    private static boolean hasAllFeaturesSanityCheck(Subject subject) {
        return subject.isPermitted(TestPermissions.PERMISSION_TYPE_1_ACTION_1_INST_1.label()) &&
               subject.isPermitted(TestPermissions.PERMISSION_TYPE_1_ACTION_2_EXAMPLE);
    }

    @Test
    public void itShouldNotRenderWithoutSubject() {
        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, not(containsString("HASALLPERMISSIONS_ATTRIBUTE_DYNAMIC")));
        assertThat(result, not(containsString("HASALLPERMISSIONS_ELEMENT_DYNAMIC")));
        assertThat(result, not(containsString("HASALLPERMISSIONS_ATTRIBUTE_STATIC")));
        assertThat(result, not(containsString("HASALLPERMISSIONS_ELEMENT_STATIC")));
    }

    @Test
    @Parameters(source = HasAllPermissions.ShouldRenderForUser.class)
    public void itShouldRenderOnUser(TestUsers user) {
        Subject subjectUnderTest = createAndLoginSubject(user);

        boolean hasAnyFeatures = hasAllFeaturesSanityCheck(subjectUnderTest);
        assertThat("User has all features", hasAnyFeatures);

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, containsString("HASALLPERMISSIONS_ATTRIBUTE_STATIC"));
        assertThat(result, containsString("HASALLPERMISSIONS_ELEMENT_STATIC"));

        subjectUnderTest.logout();
    }

    @Test
    @Parameters(source = HasAllPermissions.ShouldNotRenderForUser.class)
    public void itShouldNotRenderForUser(TestUsers user) {
        Subject subjectUnderTest = createAndLoginSubject(user);

        boolean hasAllFeatures = hasAllFeaturesSanityCheck(subjectUnderTest);
        assertThat("User does not have all features", !hasAllFeatures);

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());
        assertThat(result, not(containsString("shiro:")));
        assertThat(result, not(containsString("HASALLPERMISSIONS_ATTRIBUTE_STATIC")));
        assertThat(result, not(containsString("HASALLPERMISSIONS_ELEMENT_STATIC")));

        subjectUnderTest.logout();
    }

    @Test
    @Parameters(source = HasAllPermissions.ShouldRenderExpression.class)
    public void itShouldRenderOnCustomContext(TestUsers user, Context context) {
        Subject subjectUnderTest = createAndLoginSubject(user);
        String result = processThymeleafFile(FILE_UNDER_TEST, context);

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, containsString("HASALLPERMISSIONS_ATTRIBUTE_DYNAMIC"));
        assertThat(result, containsString("HASALLPERMISSIONS_ELEMENT_DYNAMIC"));

        subjectUnderTest.logout();
    }

    @Test
    @Parameters(source = HasAllPermissions.ShouldNotRenderExpression.class)
    public void itShouldNotRenderOnCustomContext(TestUsers user, Context context) {
        Subject subjectUnderTest = createAndLoginSubject(user);

        String result = processThymeleafFile(FILE_UNDER_TEST, context);

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, not(containsString("HASALLPERMISSIONS_ATTRIBUTE_DYNAMIC")));
        assertThat(result, not(containsString("HASALLPERMISSIONS_ELEMENT_DYNAMIC")));

        subjectUnderTest.logout();
    }
}
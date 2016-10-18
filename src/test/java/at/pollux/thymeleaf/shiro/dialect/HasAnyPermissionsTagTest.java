package at.pollux.thymeleaf.shiro.dialect;

import at.pollux.thymeleaf.shiro.test.mother.PermissionsMother.HasAnyPermissions;
import at.pollux.thymeleaf.shiro.test.user.TestUsers;
import at.pollux.thymeleaf.shiro.test.AbstractThymeleafShiroDialectTest;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.thymeleaf.context.Context;

import static at.pollux.thymeleaf.shiro.test.user.TestPermissions.PERMISSION_TYPE_1_ACTION_1_INST_1;
import static at.pollux.thymeleaf.shiro.test.user.TestPermissions.PERMISSION_TYPE_1_ACTION_2_EXAMPLE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

/**
 * @author tbk
 */
@RunWith(JUnitParamsRunner.class)
public class HasAnyPermissionsTagTest extends AbstractThymeleafShiroDialectTest {

    private static final String FILE_UNDER_TEST = "shiro_hasAnyPermissions.html";

    private static boolean hasAnyFeatureSanityCheck(Subject subjectUnderTest) {
        return subjectUnderTest.isPermitted(PERMISSION_TYPE_1_ACTION_1_INST_1.label()) ||
                subjectUnderTest.isPermitted(PERMISSION_TYPE_1_ACTION_2_EXAMPLE);
    }

    @Test
    public void itShouldNotRenderWithoutSubject() {
        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, not(containsString("HASANYPERMISSIONS_ATTRIBUTE_DYNAMIC")));
        assertThat(result, not(containsString("HASANYPERMISSIONS_ELEMENT_DYNAMIC")));
        assertThat(result, not(containsString("HASANYPERMISSIONS_ATTRIBUTE_DYNAMIC")));
        assertThat(result, not(containsString("HASANYPERMISSIONS_ELEMENT_DYNAMIC")));
    }

    @Test
    @Parameters(source = HasAnyPermissions.ShouldRenderForUser.class)
    public void itShouldRenderOnUser(TestUsers user) {
        Subject subjectUnderTest = createAndLoginSubject(user);

        boolean hasAnyFeatures = hasAnyFeatureSanityCheck(subjectUnderTest);
        assertThat("User has necessary features", hasAnyFeatures);

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, containsString("HASANYPERMISSIONS_ATTRIBUTE_STATIC"));
        assertThat(result, containsString("HASANYPERMISSIONS_ELEMENT_STATIC"));

        subjectUnderTest.logout();
    }

    @Test
    @Parameters(source = HasAnyPermissions.ShouldNotRenderForUser.class)
    public void itShouldNotRenderForUser(TestUsers user) {
        Subject subjectUnderTest = createAndLoginSubject(user);

        boolean hasAnyFeatures = hasAnyFeatureSanityCheck(subjectUnderTest);
        assertThat("User has necessary features", !hasAnyFeatures);

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());
        assertThat(result, not(containsString("shiro:")));
        assertThat(result, not(containsString("HASANYPERMISSIONS_ATTRIBUTE_STATIC")));
        assertThat(result, not(containsString("HASANYPERMISSIONS_ELEMENT_STATIC")));

        subjectUnderTest.logout();
    }

    @Test
    @Parameters(source = HasAnyPermissions.ShouldRenderExpression.class)
    public void itShouldRenderOnCustomContext(TestUsers user, Context context) {
        Subject subjectUnderTest = createAndLoginSubject(user);
        String result = processThymeleafFile(FILE_UNDER_TEST, context);

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, containsString("HASANYPERMISSIONS_ATTRIBUTE_DYNAMIC"));
        assertThat(result, containsString("HASANYPERMISSIONS_ELEMENT_DYNAMIC"));

        subjectUnderTest.logout();
    }

    @Test
    @Parameters(source = HasAnyPermissions.ShouldNotRenderExpression.class)
    public void itShouldNotRenderOnCustomContext(TestUsers user, Context context) {
        Subject subjectUnderTest = createAndLoginSubject(user);

        String result = processThymeleafFile(FILE_UNDER_TEST, context);

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, not(containsString("HASANYPERMISSIONS_ATTRIBUTE_DYNAMIC")));
        assertThat(result, not(containsString("HASANYPERMISSIONS_ELEMENT_DYNAMIC")));

        subjectUnderTest.logout();
    }

}
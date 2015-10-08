package at.pollux.thymeleaf.shiro.dialect.test;

import at.pollux.thymeleaf.shiro.dialect.test.mother.HasAnyPermissionsMother.ShouldNotRenderExpression;
import at.pollux.thymeleaf.shiro.dialect.test.mother.HasAnyPermissionsMother.ShouldNotRenderForUser;
import at.pollux.thymeleaf.shiro.dialect.test.mother.HasAnyPermissionsMother.ShouldRenderExpression;
import at.pollux.thymeleaf.shiro.dialect.test.mother.HasAnyPermissionsMother.ShouldRenderForUser;
import at.pollux.thymeleaf.shiro.dialect.test.user.TestUsers;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.thymeleaf.context.Context;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

/**
 * @author tbk
 */
@RunWith(JUnitParamsRunner.class)
public class HasAnyPermissionsTagTest extends AbstractThymeleafShiroDialectTest {

    private static final String FILE_UNDER_TEST = "shiro_hasAnyPermissions.html";

    @Test
    public void itShoulNotdRenderWithoutSubject() {
        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, not(containsString("HASANYPERMISSIONS_ATTRIBUTE_DYNAMIC")));
        assertThat(result, not(containsString("HASANYPERMISSIONS_ELEMENT_DYNAMIC")));
    }

    @Test
    @Parameters(source = ShouldRenderForUser.class)
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
    @Parameters(source = ShouldNotRenderForUser.class)
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
    @Parameters(source = ShouldRenderExpression.class)
    public void itShouldRenderOnCustomContext(TestUsers user, Context context) {
        Subject subjectUnderTest = createAndLoginSubject(user);
        String result = processThymeleafFile(FILE_UNDER_TEST, context);

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, containsString("HASANYPERMISSIONS_ATTRIBUTE_DYNAMIC"));
        assertThat(result, containsString("HASANYPERMISSIONS_ELEMENT_DYNAMIC"));

        subjectUnderTest.logout();
    }

    @Test
    @Parameters(source = ShouldNotRenderExpression.class)
    public void itShouldNotRenderOnCustomContext(TestUsers user, Context context) {
        Subject subjectUnderTest = createAndLoginSubject(user);

        String result = processThymeleafFile(FILE_UNDER_TEST, context);

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, not(containsString("HASANYPERMISSIONS_ATTRIBUTE_DYNAMIC")));
        assertThat(result, not(containsString("HASANYPERMISSIONS_ELEMENT_DYNAMIC")));

        subjectUnderTest.logout();
    }

    private boolean hasAnyFeatureSanityCheck(Subject subjectUnderTest) {
        return subjectUnderTest.isPermitted("permtype1:permaction1:perminst1") ||
                subjectUnderTest.isPermitted("permtype1:permaction1:xyz");
    }

}
package at.pollux.thymeleaf.shiro.dialect;

import at.pollux.thymeleaf.shiro.test.AbstractThymeleafShiroDialectTest;
import at.pollux.thymeleaf.shiro.test.user.TestUsers;
import com.google.common.collect.Lists;
import junitparams.JUnitParamsRunner;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.thymeleaf.context.Context;

import static at.pollux.thymeleaf.shiro.test.user.TestRoles.ROLE_B;
import static at.pollux.thymeleaf.shiro.test.user.TestRoles.ROLE_C;
import static com.google.common.base.Preconditions.checkArgument;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

/**
 * @author tbk
 */
@RunWith(JUnitParamsRunner.class)
public class HasAllRolesTagTest extends AbstractThymeleafShiroDialectTest {

    private static final String FILE_UNDER_TEST = "shiro_hasAllRoles.html";

    private static Subject hasAllRolesSanityCheck(Subject subject) {
        checkArgument(subject.hasAllRoles(Lists.newArrayList(ROLE_B.label(), ROLE_C.label())));
        return subject;
    }

    @Test
    public void itShouldNotRenderWithoutSubject() {
        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, not(containsString("HASALLROLES_ATTRIBUTE_STATIC")));
        assertThat(result, not(containsString("HASALLROLES_ELEMENT_STATIC")));
    }

    @Test
    public void itShouldRenderForUserAlice() {
        TestUsers user = TestUsers.ALICE;
        Subject subjectUnderTest = createAndLoginSubject(user);

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, not(containsString("HASALLROLES_ATTRIBUTE_STATIC")));
        assertThat(result, not(containsString("HASALLROLES_ELEMENT_STATIC")));

        subjectUnderTest.logout();
    }

    @Test
    public void itShouldRenderForUserBob() {
        TestUsers user = TestUsers.BOB;
        Subject subjectUnderTest = hasAllRolesSanityCheck(createAndLoginSubject(user));

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, containsString("HASALLROLES_ATTRIBUTE_STATIC"));
        assertThat(result, containsString("HASALLROLES_ELEMENT_STATIC"));

        subjectUnderTest.logout();
    }

    @Test
    public void itShouldRenderForUserCaesar() {
        TestUsers user = TestUsers.CAESAR;
        Subject subjectUnderTest = createAndLoginSubject(user);

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, not(containsString("HASALLROLES_ATTRIBUTE_STATIC")));
        assertThat(result, not(containsString("HASALLROLES_ELEMENT_STATIC")));

        subjectUnderTest.logout();
    }
}
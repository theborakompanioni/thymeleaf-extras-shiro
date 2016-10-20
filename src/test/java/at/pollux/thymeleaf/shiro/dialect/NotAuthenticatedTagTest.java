package at.pollux.thymeleaf.shiro.dialect;

import at.pollux.thymeleaf.shiro.test.AbstractThymeleafShiroDialectTest;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.thymeleaf.context.Context;

import static at.pollux.thymeleaf.shiro.test.user.TestUsers.ALICE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

/**
 * @author tbk
 */
public class NotAuthenticatedTagTest extends AbstractThymeleafShiroDialectTest {

    private static final String FILE_UNDER_TEST = "shiro_notAuthenticated.html";

    //TODO: Remembered user

    @Test
    public void itShouldRenderNotAuthenticatedTagContentOnGuestUser() {
        Subject subjectUnderTest = createSubject();
        setSubject(subjectUnderTest);

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, containsString("NOT_AUTHENTICATED_ATTRIBUTE"));
        assertThat(result, containsString("NOT_AUTHENTICATED_ELEMENT"));
    }

    @Test
    public void itShouldNotRenderNotAuthenticatedTagContentOnLoggedInUser() {
        Subject subjectUnderTest = createSubject();
        setSubject(subjectUnderTest);

        // Logged in user
        subjectUnderTest.login(new UsernamePasswordToken(ALICE.email(), ALICE.password()));

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, not(containsString("NOT_AUTHENTICATED_ATTRIBUTE")));
        assertThat(result, not(containsString("NOT_AUTHENTICATED_ELEMENT")));

        subjectUnderTest.logout();
    }
}
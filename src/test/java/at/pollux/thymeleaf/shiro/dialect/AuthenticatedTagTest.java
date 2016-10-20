package at.pollux.thymeleaf.shiro.dialect;

import at.pollux.thymeleaf.shiro.test.AbstractThymeleafShiroDialectTest;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.thymeleaf.context.Context;

import static at.pollux.thymeleaf.shiro.test.user.TestUsers.ALICE;
import static com.google.common.base.Preconditions.checkArgument;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

/**
 * @author tbk
 */
public class AuthenticatedTagTest extends AbstractThymeleafShiroDialectTest {

    private static final String FILE_UNDER_TEST = "shiro_authenticated.html";

    //TODO: Remembered user

    @Test
    public void itShouldNotRenderAuthenticatedTagContentOnGuestUser() {
        Subject subjectUnderTest = createSubject();
        setSubject(subjectUnderTest);

        checkArgument(!subjectUnderTest.isAuthenticated()); // sanity

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, not(containsString("AUTHENTICATED_ATTRIBUTE")));
        assertThat(result, not(containsString("AUTHENTICATED_ELEMENT")));
    }

    @Test
    public void itShouldRenderAuthenticatedTagContentOnLoggedInUser() {
        Subject subjectUnderTest = createSubject();
        setSubject(subjectUnderTest);

        // Logged in user
        subjectUnderTest.login(new UsernamePasswordToken(ALICE.email(), ALICE.password()));

        checkArgument(subjectUnderTest.isAuthenticated()); // sanity

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, containsString("AUTHENTICATED_ATTRIBUTE"));
        assertThat(result, containsString("AUTHENTICATED_ELEMENT"));

        subjectUnderTest.logout();
    }
}
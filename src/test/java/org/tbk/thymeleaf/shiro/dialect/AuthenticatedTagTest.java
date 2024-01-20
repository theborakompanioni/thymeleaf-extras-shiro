package org.tbk.thymeleaf.shiro.dialect;

import org.tbk.thymeleaf.shiro.test.AbstractThymeleafShiroDialectTest;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.tbk.thymeleaf.shiro.test.user.TestUsers;
import org.thymeleaf.context.Context;

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
        subjectUnderTest.login(new UsernamePasswordToken(TestUsers.ALICE.email(), TestUsers.ALICE.password()));

        checkArgument(subjectUnderTest.isAuthenticated()); // sanity

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, containsString("AUTHENTICATED_ATTRIBUTE"));
        assertThat(result, containsString("AUTHENTICATED_ELEMENT"));

        subjectUnderTest.logout();
    }
}
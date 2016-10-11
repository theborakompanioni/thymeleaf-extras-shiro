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
public class GuestTagTest extends AbstractThymeleafShiroDialectTest {

    private static final String FILE_UNDER_TEST = "shiro_guest.html";

    @Test
    public void itShouldRenderGuestTagContentOnGuestUser() {
        Subject subjectUnderTest = createSubject();
        setSubject(subjectUnderTest);

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, containsString("GUEST_ATTRIBUTE"));
        assertThat(result, containsString("GUEST_ELEMENT"));
    }

    @Test
    public void itShouldNotRenderGuestTagContentOnLoggedInUser() {
        Subject subjectUnderTest = createSubject();
        setSubject(subjectUnderTest);

        // Logged in user
        subjectUnderTest.login(new UsernamePasswordToken(ALICE.email(), ALICE.password()));

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, not(containsString("GUEST_ATTRIBUTE")));
        assertThat(result, not(containsString("GUEST_ELEMENT")));

        subjectUnderTest.logout();
    }
}
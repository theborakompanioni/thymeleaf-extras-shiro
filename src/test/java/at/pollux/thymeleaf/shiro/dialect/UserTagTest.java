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
public class UserTagTest extends AbstractThymeleafShiroDialectTest {

    private static final String FILE_UNDER_TEST = "shiro_user.html";

    @Test
    public void itShouldRenderUserTagContentOnLoggedInUser() {
        Subject subjectUnderTest = createSubject();
        setSubject(subjectUnderTest);

        // Logged in user
        subjectUnderTest.login(new UsernamePasswordToken(ALICE.email(), ALICE.password()));

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());
        assertThat(result, not(containsString("shiro:")));
        assertThat(result, containsString("USER_ATTRIBUTE"));
        assertThat(result, containsString("USER_ELEMENT"));

        subjectUnderTest.logout();
    }

    @Test
    public void itShouldNotRenderUserTagContentOnGuestUser() {
        Subject subjectUnderTest = createSubject();
        setSubject(subjectUnderTest);

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, not(containsString("USER_ATTRIBUTE")));
        assertThat(result, not(containsString("USER_ELEMENT")));
    }

}
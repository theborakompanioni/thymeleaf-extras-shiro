package org.tbk.thymeleaf.shiro.dialect;

import org.tbk.thymeleaf.shiro.test.AbstractThymeleafShiroDialectTest;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.tbk.thymeleaf.shiro.test.user.TestUsers;
import org.thymeleaf.context.Context;

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
        subjectUnderTest.login(new UsernamePasswordToken(TestUsers.ALICE.email(), TestUsers.ALICE.password()));

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());
        assertThat(result, not(containsString("shiro:")));
        assertThat(result, containsString("USER_ATTRIBUTE"));
        assertThat(result, containsString("USER_ELEMENT"));
        assertThat(result, containsString("USER_TH"));

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
        assertThat(result, not(containsString("USER_TH")));
    }

}
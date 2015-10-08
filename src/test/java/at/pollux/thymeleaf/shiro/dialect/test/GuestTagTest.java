/*
 * Copyright 2013 Art Gramlich.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.pollux.thymeleaf.shiro.dialect.test;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.thymeleaf.context.Context;

import static at.pollux.thymeleaf.shiro.dialect.test.user.TestUsers.ALICE;
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
        Subject subjectUnderTest = newSubect();
        setSubject(subjectUnderTest);

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, containsString("GUEST_ATTRIBUTE"));
        assertThat(result, containsString("GUEST_ELEMENT"));
    }

    @Test
    public void itShouldNotRenderGuestTagContentOnLoggedInUser() {
        Subject subjectUnderTest = newSubect();
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
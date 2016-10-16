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
package at.pollux.thymeleaf.shiro.dialect;

import at.pollux.thymeleaf.shiro.test.AbstractThymeleafShiroDialectTest;
import at.pollux.thymeleaf.shiro.test.user.TestUsers;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.thymeleaf.context.Context;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

/**
 * @author tbk
 */
public class HasPermissionTagTest extends AbstractThymeleafShiroDialectTest {

    private static final String FILE_UNDER_TEST = "shiro_hasPermission.html";

    @Test
    public void itShouldNotRenderWithoutSubject() {
        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, not(containsString("APERM")));
        assertThat(result, not(containsString("BPERM")));
        assertThat(result, not(containsString("CPERM")));
    }

    @Test
    public void itShouldRenderForUserAlice() {
        TestUsers user = TestUsers.ALICE;
        Subject subjectUnderTest = createAndLoginSubject(user);

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, containsString("APERM1"));
        assertThat(result, containsString("APERM2"));
        assertThat(result, containsString("BPERM1"));
        assertThat(result, containsString("BPERM2"));
        assertThat(result, containsString("CPERM1"));
        assertThat(result, containsString("CPERM2"));

        subjectUnderTest.logout();
    }

    @Test
    public void itShouldRenderForUserBob() {
        TestUsers user = TestUsers.BOB;
        Subject subjectUnderTest = createAndLoginSubject(user);

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, containsString("APERM1"));
        assertThat(result, containsString("APERM2"));
        assertThat(result, not(containsString("BPERM1")));
        assertThat(result, not(containsString("BPERM2")));
        assertThat(result, containsString("CPERM1"));
        assertThat(result, containsString("CPERM2"));

        subjectUnderTest.logout();
    }

    @Test
    public void itShouldRenderForUserCaesar() {
        TestUsers user = TestUsers.CAESAR;
        Subject subjectUnderTest = createAndLoginSubject(user);

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, not(containsString("APERM1")));
        assertThat(result, not(containsString("APERM2")));
        assertThat(result, not(containsString("BPERM1")));
        assertThat(result, not(containsString("BPERM2")));
        assertThat(result, containsString("CPERM1"));
        assertThat(result, containsString("CPERM2"));

        subjectUnderTest.logout();
    }
}
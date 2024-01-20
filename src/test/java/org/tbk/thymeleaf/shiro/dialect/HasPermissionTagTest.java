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
package org.tbk.thymeleaf.shiro.dialect;

import org.tbk.thymeleaf.shiro.test.AbstractThymeleafShiroDialectTest;
import org.tbk.thymeleaf.shiro.test.user.TestUsers;
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
        assertThat(result, not(containsString("PERM_A")));
        assertThat(result, not(containsString("PERM_B")));
        assertThat(result, not(containsString("PERM_C")));
    }

    @Test
    public void itShouldRenderForUserAlice() {
        TestUsers user = TestUsers.ALICE;
        Subject subjectUnderTest = createAndLoginSubject(user);

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, containsString("PERM_A_ATTRIBUTE_STATIC"));
        assertThat(result, containsString("PERM_A_ELEMENT_STATIC"));
        assertThat(result, containsString("PERM_B_ATTRIBUTE_STATIC"));
        assertThat(result, containsString("PERM_B_ELEMENT_STATIC"));
        assertThat(result, containsString("PERM_C_ATTRIBUTE_STATIC"));
        assertThat(result, containsString("PERM_C_ELEMENT_STATIC"));

        subjectUnderTest.logout();
    }

    @Test
    public void itShouldRenderForUserBob() {
        TestUsers user = TestUsers.BOB;
        Subject subjectUnderTest = createAndLoginSubject(user);

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, containsString("PERM_A_ATTRIBUTE_STATIC"));
        assertThat(result, containsString("PERM_A_ELEMENT_STATIC"));
        assertThat(result, not(containsString("PERM_B_ATTRIBUTE_STATIC")));
        assertThat(result, not(containsString("PERM_B_ELEMENT_STATIC")));
        assertThat(result, containsString("PERM_C_ATTRIBUTE_STATIC"));
        assertThat(result, containsString("PERM_C_ELEMENT_STATIC"));

        subjectUnderTest.logout();
    }

    @Test
    public void itShouldRenderForUserCaesar() {
        TestUsers user = TestUsers.CAESAR;
        Subject subjectUnderTest = createAndLoginSubject(user);

        String result = processThymeleafFile(FILE_UNDER_TEST, new Context());

        assertThat(result, not(containsString("shiro:")));
        assertThat(result, not(containsString("PERM_A_ATTRIBUTE_STATIC")));
        assertThat(result, not(containsString("PERM_A_ELEMENT_STATIC")));
        assertThat(result, not(containsString("PERM_B_ATTRIBUTE_STATIC")));
        assertThat(result, not(containsString("PERM_B_ELEMENT_STATIC")));
        assertThat(result, containsString("PERM_C_ATTRIBUTE_STATIC"));
        assertThat(result, containsString("PERM_C_ELEMENT_STATIC"));

        subjectUnderTest.logout();
    }
}
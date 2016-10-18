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

import at.pollux.thymeleaf.shiro.test.TestIniSecurityManagerFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.test.AbstractShiroTest;
import org.apache.shiro.util.Factory;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * @author artgramlich
 */
public class ShiroDialectTest extends AbstractShiroTest {

    private static final String PACKAGE_PATH = "at/pollux/thymeleaf/shiro/dialect/test";
    private static final String TEST_TEMPLATE_PATH = PACKAGE_PATH + "/test.html";
    private static final String USER1 = "u1";
    private static final String PASS1 = "p1";
    private static final String USER2 = "u2";
    private static final String PASS2 = "p2";
    private static final String USER3 = "u3";
    private static final String PASS3 = "p3";

    private static TemplateEngine templateEngine;

    private static void setupShiro() {
        Ini ini = new Ini();
        Ini.Section usersSection = ini.addSection("users");
        usersSection.put(USER1, PASS1 + ",rolea,roled");
        usersSection.put(USER2, PASS2 + ",roleb,rolec");
        usersSection.put(USER3, PASS3 + ",rolec,rolee");
        Ini.Section rolesSection = ini.addSection("roles");
        rolesSection.put("rolea", "*");
        rolesSection.put("roleb", "permtype1:permaction1:perminst1");
        rolesSection.put("rolec", "permtype1:permaction2:*");
        rolesSection.put("roled", "permtype3:*");
        Factory<SecurityManager> factory = new TestIniSecurityManagerFactory(ini);
        SecurityManager secMgr = factory.getInstance();
        setSecurityManager(secMgr);
    }

    private static void setupThymeleaf() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setCacheable(false);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect("shiro", new ShiroDialect());

    }

    @BeforeClass
    public static void beforeClass() {
        setupShiro();
        setupThymeleaf();
    }

    @After
    public void tearDownSubject() {
        clearSubject();
    }

    @Test
    public void testGuest() {
        Subject subjectUnderTest = new Subject.Builder(getSecurityManager()).buildSubject();
        setSubject(subjectUnderTest);

        Context context = new Context();
        String result;

        // Guest user
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertTrue(result.contains("GUEST1"));
        assertTrue(result.contains("GUEST2"));

        // Logged in user
        subjectUnderTest.login(new UsernamePasswordToken(USER1, PASS1));
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertFalse(result.contains("GUEST1"));
        assertFalse(result.contains("GUEST2"));
        subjectUnderTest.logout();
    }

    @Test
    public void testUser() {
        Subject subjectUnderTest = new Subject.Builder(getSecurityManager()).buildSubject();
        setSubject(subjectUnderTest);

        Context context = new Context();
        String result;

        // Guest user
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertFalse(result.contains("USER1"));
        assertFalse(result.contains("USER2"));

        // Logged in user
        subjectUnderTest.login(new UsernamePasswordToken(USER1, PASS1));
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertTrue(result.contains("USER1"));
        assertTrue(result.contains("USER2"));
        subjectUnderTest.logout();
    }

    @Test
    public void testAuthenticated() {
        Subject subjectUnderTest = new Subject.Builder(getSecurityManager()).buildSubject();
        setSubject(subjectUnderTest);

        Context context = new Context();
        String result;

        // Guest user
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(subjectUnderTest.isAuthenticated()); // sanity
        assertFalse(result.contains("shiro:"));
        assertFalse(result.contains("ISAUTHENTICATED1"));
        assertFalse(result.contains("ISAUTHENTICATED2"));

        // Logged in user
        subjectUnderTest.login(new UsernamePasswordToken(USER1, PASS1));
        assertTrue(subjectUnderTest.isAuthenticated()); // sanity
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertTrue(result.contains("ISAUTHENTICATED1"));
        assertTrue(result.contains("ISAUTHENTICATED2"));
        subjectUnderTest.logout();

        // Remembered user
        //TODO:
    }

    @Test
    public void testNotAuthenticated() {
        Subject subjectUnderTest = new Subject.Builder(getSecurityManager()).buildSubject();
        setSubject(subjectUnderTest);

        Context context = new Context();
        String result;

        // Guest user
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(subjectUnderTest.isAuthenticated()); // sanity
        assertFalse(result.contains("shiro:"));
        assertTrue(result.contains("NOTAUTHENTICATED1"));
        assertTrue(result.contains("NOTAUTHENTICATED2"));

        // Logged in user
        subjectUnderTest.login(new UsernamePasswordToken(USER1, PASS1));
        assertTrue(subjectUnderTest.isAuthenticated()); // sanity
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertFalse(result.contains("NOTAUTHENTICATED1"));
        assertFalse(result.contains("NOTAUTHENTICATED2"));
        subjectUnderTest.logout();

        // Remembered user
        //TODO:
    }

    @Test
    public void testPrincipal() {
        Subject subjectUnderTest = new Subject.Builder(getSecurityManager()).buildSubject();
        setSubject(subjectUnderTest);

        Context context = new Context();
        String result;

        // Guest user
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertNull(subjectUnderTest.getPrincipal()); // sanity
        assertFalse(result.contains("shiro:"));
        assertFalse(result.contains("DEFPRINCIPAL1"));
        assertFalse(result.contains("DEFPRINCIPAL2"));

        // Logged in user
        subjectUnderTest.login(new UsernamePasswordToken(USER1, PASS1));
        assertEquals(USER1, subjectUnderTest.getPrincipal()); // sanity
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertTrue(result.contains("DEFPRINCIPAL1<span>" + USER1 + "</span>DEFPRINCIPAL1"));
        assertTrue(result.contains("DEFPRINCIPAL2" + USER1 + "DEFPRINCIPAL2"));
        subjectUnderTest.logout();
    }

    @Test
    public void testPrincipalWithType() {
        Subject subjectUnderTest = new Subject.Builder(getSecurityManager()).buildSubject();
        setSubject(subjectUnderTest);

        Context context = new Context();
        String result;

        // Guest user
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertFalse(result.contains("TYPEPRINCIPAL1"));
        assertFalse(result.contains("TYPEPRINCIPAL2"));

        // Logged in user
        subjectUnderTest.login(new UsernamePasswordToken(USER1, PASS1));
        assertEquals(Integer.valueOf(0), SecurityUtils.getSubject().getPrincipals().oneByType(Integer.class)); // sanity
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertTrue(result.contains("TYPEPRINCIPAL1<span>0</span>TYPEPRINCIPAL1"));
        assertTrue(result.contains("TYPEPRINCIPAL20TYPEPRINCIPAL2"));
        subjectUnderTest.logout();
    }

    @Test
    public void testPrincipalWithProperty() {
        Subject subjectUnderTest = new Subject.Builder(getSecurityManager()).buildSubject();
        setSubject(subjectUnderTest);

        Context context = new Context();
        String result;

        // Guest user
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertFalse(result.contains("PROPPRINCIPAL1"));
        assertFalse(result.contains("PROPPRINCIPAL2"));

        // Logged in user
        subjectUnderTest.login(new UsernamePasswordToken(USER1, PASS1));
        assertEquals(Integer.valueOf(0), SecurityUtils.getSubject().getPrincipals().oneByType(Integer.class)); // sanity
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertTrue(result.contains("PROPPRINCIPAL1<span>" + USER1.toUpperCase() + " " + USER1.toUpperCase() + "</span>PROPPRINCIPAL1"));
        assertTrue(result.contains("PROPPRINCIPAL2" + USER1.toUpperCase() + " " + USER1.toUpperCase() + "PROPPRINCIPAL2"));
        subjectUnderTest.logout();
    }

    @Test
    public void testHasRole() {
        Subject subjectUnderTest = new Subject.Builder(getSecurityManager()).buildSubject();
        setSubject(subjectUnderTest);

        Context context = new Context();
        context.setVariable("roleExpression", "roled");
        String result;

        // Guest user
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertFalse(result.contains("HASROLE1"));
        assertFalse(result.contains("HASROLE2"));

        // Logged in user 1
        subjectUnderTest.login(new UsernamePasswordToken(USER1, PASS1));
        assertTrue(subjectUnderTest.hasRole("rolea")); // sanity
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertTrue(result.contains("HASROLE1"));
        assertTrue(result.contains("HASROLE2"));
        subjectUnderTest.logout();

        // Logged in user 2
        subjectUnderTest.login(new UsernamePasswordToken(USER2, PASS2));
        assertFalse(subjectUnderTest.hasRole("rolea")); // sanity
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertFalse(result.contains("HASROLE1"));
        assertFalse(result.contains("HASROLE2"));
        subjectUnderTest.logout();
    }

    @Test
    public void testLacksRole() {
        Subject subjectUnderTest = new Subject.Builder(getSecurityManager()).buildSubject();
        setSubject(subjectUnderTest);

        Context context = new Context();
        String result;

        // Guest user
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertTrue(result.contains("LACKSROLE1"));
        assertTrue(result.contains("LACKSROLE2"));

        // Logged in user 1
        subjectUnderTest.login(new UsernamePasswordToken(USER1, PASS1));
        assertTrue(subjectUnderTest.hasRole("rolea")); // sanity
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertFalse(result.contains("LACKSROLE1"));
        assertFalse(result.contains("LACKSROLE2"));
        subjectUnderTest.logout();

        // Logged in user 2
        subjectUnderTest.login(new UsernamePasswordToken(USER2, PASS2));
        assertFalse(subjectUnderTest.hasRole("rolea")); // sanity
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertTrue(result.contains("LACKSROLE1"));
        assertTrue(result.contains("LACKSROLE2"));
        subjectUnderTest.logout();
    }

    @Test
    public void testHasAnyRoles() {
        Subject subjectUnderTest = new Subject.Builder(getSecurityManager()).buildSubject();
        setSubject(subjectUnderTest);

        Context context = new Context();
        String result;

        // Guest user
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertFalse(result.contains("HASANYROLES1"));
        assertFalse(result.contains("HASANYROLES2"));

        // Logged in user 1
        subjectUnderTest.login(new UsernamePasswordToken(USER1, PASS1));
        assertTrue(subjectUnderTest.hasRole("rolea")); // sanity
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertTrue(result.contains("HASANYROLES1"));
        assertTrue(result.contains("HASANYROLES2"));
        subjectUnderTest.logout();

        // Logged in user 2
        subjectUnderTest.login(new UsernamePasswordToken(USER2, PASS2));
        assertFalse(subjectUnderTest.hasRole("rolea")); // sanity
        result = templateEngine.process(TEST_TEMPLATE_PATH, context);
        assertFalse(result.contains("shiro:"));
        assertFalse(result.contains("HASANYROLES1"));
        assertFalse(result.contains("HASANYROLES2"));
        subjectUnderTest.logout();
    }
}
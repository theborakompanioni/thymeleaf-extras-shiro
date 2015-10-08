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

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.test.AbstractShiroTest;
import org.apache.shiro.util.Factory;
import org.junit.After;
import org.junit.BeforeClass;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import static at.pollux.thymeleaf.shiro.dialect.test.user.TestRoles.*;
import static at.pollux.thymeleaf.shiro.dialect.test.user.TestUsers.*;

/**
 * @author artgramlich
 */
public class AbstractThymeleafShiroDialectTest extends AbstractShiroTest {

    private static final String PACKAGE_PATH = "at/pollux/thymeleaf/shiro/dialect/test";

    private static TemplateEngine templateEngine;

    private static void setupShiro() {
        Ini ini = new Ini();
        Ini.Section usersSection = ini.addSection("users");

        usersSection.put(ALICE.email(), ALICE.roles());
        usersSection.put(BOB.email(), BOB.roles());
        usersSection.put(CAESAR.email(), CAESAR.roles());

        Ini.Section rolesSection = ini.addSection("roles");
        rolesSection.put(ROLE_A.label(), ROLE_A.permissions());
        rolesSection.put(ROLE_B.label(), ROLE_A.permissions());
        rolesSection.put(ROLE_C.label(), ROLE_A.permissions());
        rolesSection.put(ROLE_D.label(), ROLE_A.permissions());

        Factory<SecurityManager> factory = new TestIniSecurityManagerFactory(ini);
        SecurityManager secMgr = factory.getInstance();
        setSecurityManager(secMgr);
    }

    private static void setupThymeleaf() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setCacheable(false);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode("HTML5");

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

    protected String processThymeleafFile(String fileName, Context context) {
        return templateEngine.process(PACKAGE_PATH + "/" + fileName, context);
    }

    protected Subject newSubect() {
        return new Subject.Builder(getSecurityManager()).buildSubject();
    }
}
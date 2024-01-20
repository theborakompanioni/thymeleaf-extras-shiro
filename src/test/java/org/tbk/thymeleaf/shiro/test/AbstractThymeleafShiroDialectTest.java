package org.tbk.thymeleaf.shiro.test;

import org.tbk.thymeleaf.shiro.test.user.TestRoles;
import org.tbk.thymeleaf.shiro.test.user.TestUsers;
import org.tbk.thymeleaf.shiro.dialect.ShiroDialect;
import com.google.common.base.Charsets;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.After;
import org.junit.BeforeClass;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * @author tbk
 */
public class AbstractThymeleafShiroDialectTest extends ShiroTest {

    private static final String PACKAGE_PATH = "org/tbk/thymeleaf/shiro/dialect/test";

    private static TemplateEngine templateEngine;

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

    protected Subject createSubject() {
        final Subject subjectUnderTest = new Subject.Builder(getSecurityManager()).buildSubject();

        setSubject(subjectUnderTest);

        return subjectUnderTest;
    }

    protected Subject createAndLoginSubject(TestUsers userOrNull) {
        final Subject subjectUnderTest = createSubject();

        if (userOrNull != null) {
            subjectUnderTest.login(new UsernamePasswordToken(userOrNull.email(), userOrNull.password()));
        }
        return subjectUnderTest;
    }

    private static void setupShiro() {
        Ini ini = new Ini();
        Ini.Section usersSection = ini.addSection("users");

        usersSection.put(TestUsers.ALICE.email(), TestUsers.ALICE.roles());
        usersSection.put(TestUsers.BOB.email(), TestUsers.BOB.roles());
        usersSection.put(TestUsers.CAESAR.email(), TestUsers.CAESAR.roles());

        Ini.Section rolesSection = ini.addSection("roles");
        rolesSection.put(TestRoles.ROLE_A.label(), TestRoles.ROLE_A.permissions());
        rolesSection.put(TestRoles.ROLE_B.label(), TestRoles.ROLE_B.permissions());
        rolesSection.put(TestRoles.ROLE_C.label(), TestRoles.ROLE_C.permissions());
        rolesSection.put(TestRoles.ROLE_D.label(), TestRoles.ROLE_D.permissions());

        Factory<SecurityManager> factory = new TestIniSecurityManagerFactory(ini);
        SecurityManager secMgr = factory.getInstance();
        setSecurityManager(secMgr);
    }

    private static void setupThymeleaf() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setCacheable(false);
        templateResolver.setCharacterEncoding(Charsets.UTF_8.name());
        templateResolver.setTemplateMode(TemplateMode.HTML);

        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        final ShiroDialect dialect = new ShiroDialect();
        templateEngine.addDialect(dialect.getPrefix(), dialect);

    }
}
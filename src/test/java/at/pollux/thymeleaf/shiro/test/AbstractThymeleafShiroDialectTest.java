package at.pollux.thymeleaf.shiro.test;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import at.pollux.thymeleaf.shiro.test.user.TestUsers;
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

import static at.pollux.thymeleaf.shiro.test.user.TestRoles.*;
import static at.pollux.thymeleaf.shiro.test.user.TestUsers.*;

/**
 * @author tbk
 */
public class AbstractThymeleafShiroDialectTest extends ShiroTest {

    private static final String PACKAGE_PATH = "at/pollux/thymeleaf/shiro/dialect/test";

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

        usersSection.put(ALICE.email(), ALICE.roles());
        usersSection.put(BOB.email(), BOB.roles());
        usersSection.put(CAESAR.email(), CAESAR.roles());

        Ini.Section rolesSection = ini.addSection("roles");
        rolesSection.put(ROLE_A.label(), ROLE_A.permissions());
        rolesSection.put(ROLE_B.label(), ROLE_B.permissions());
        rolesSection.put(ROLE_C.label(), ROLE_C.permissions());
        rolesSection.put(ROLE_D.label(), ROLE_D.permissions());

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
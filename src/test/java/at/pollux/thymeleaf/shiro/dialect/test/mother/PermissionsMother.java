package at.pollux.thymeleaf.shiro.dialect.test.mother;

import at.pollux.thymeleaf.shiro.dialect.test.user.TestUsers;
import com.google.common.collect.Sets;
import org.thymeleaf.context.Context;

import static at.pollux.thymeleaf.shiro.dialect.test.user.TestUsers.*;
import static junitparams.JUnitParamsRunner.$;

/**
 * Created by tbk.
 */
public class PermissionsMother {

    private static Context contextWithPermission(Object permissions) {
        final Context context = new Context();
        context.setVariable("permissions", permissions);
        return context;
    }

    public static class HasAnyPermissions {

        public static class ShouldRenderForUser {
            public static Object[] provideAlice() {
                return $(ALICE);
            }

            public static Object[] provideBob() {
                return $(BOB);
            }
        }

        public static class ShouldNotRenderForUser {
            public static Object[] provideGuest() {
                return new Object[]{null};
            }

            public static Object[] provideCaesar() {
                return $(CAESAR);
            }
        }

        public static class ShouldRenderExpression {
            private static Object[] paramsForUser(TestUsers user) {
                return $(
                        $(user, contextWithPermission("permtype1:permaction1:perminst1")),
                        $(user, contextWithPermission("foo, bar, permtype1:permaction1:perminst1")),
                        $(user, contextWithPermission("foo, permtype1:permaction1:perminst1, bar")),
                        $(user, contextWithPermission(Sets.newHashSet(
                                "foo",
                                "permtype1:permaction1:perminst1",
                                "bar"
                        ))),
                        $(user, contextWithPermission(Sets.newHashSet(
                                "foo, permtype1:permaction1:perminst1, bar"
                        ))),
                        $(user, contextWithPermission(Sets.newHashSet(
                                "permtype1:permaction1:perminst1",
                                "permtype1:permaction1:xyz"
                        ))));
            }

            public static Object[] provideAlice() {
                return paramsForUser(ALICE);
            }

            public static Object[] provideBob() {
                return paramsForUser(BOB);
            }
        }


        public static class ShouldNotRenderExpression {
            private static Object[] paramsForUser(TestUsers user) {
                return $(
                        $(user, new Context()),
                        $(user, contextWithPermission(null)),
                        $(user, contextWithPermission("foobar")),
                        $(user, contextWithPermission("foo, bar")),
                        $(user, contextWithPermission(Sets.newHashSet())),
                        $(user, contextWithPermission(Sets.newHashSet(
                                "foo",
                                "bar",
                                "foo, bar"
                        ))));
            }

            public static Object[] provideBob() {
                return paramsForUser(BOB);
            }

            public static Object[] provideCaesar() {
                return paramsForUser(CAESAR);
            }
        }
    }


    public static class HasAllPermissions {

        public static class ShouldRenderForUser {
            public static Object[] provideAlice() {
                return $(ALICE);
            }
        }

        public static class ShouldNotRenderForUser {
            public static Object[] provideGuest() {
                return new Object[]{null};
            }

            public static Object[] provideCaesar() {
                return $(CAESAR);
            }

            public static Object[] provideBob() {
                return $(BOB);
            }
        }

        public static class ShouldRenderExpression {
            private static Object[] paramsForUser(TestUsers user) {
                return $(
                        $(user, contextWithPermission("permtype1:permaction1:perminst1")),
                        $(user, contextWithPermission("foo, permtype1:permaction1:perminst1, bar")),
                        $(user, contextWithPermission(Sets.newHashSet(
                                "foo",
                                "permtype1:permaction1:perminst1",
                                "bar"
                        ))),
                        $(user, contextWithPermission(Sets.newHashSet(
                                "foo, permtype1:permaction1:perminst1, bar"
                        ))),
                        $(user, contextWithPermission(Sets.newHashSet(
                                "permtype1:permaction1:perminst1",
                                "permtype1:permaction1:xyz"
                        ))));
            }

            public static Object[] provideAlice() {
                return paramsForUser(ALICE);
            }

            public static Object[] provideBob() {
                return $(
                        $(BOB, contextWithPermission("permtype1:permaction1:perminst1"
                        )));
            }
        }


        public static class ShouldNotRenderExpression {
            private static Object[] paramsForUser(TestUsers user) {
                return $(
                        $(user, new Context()),
                        $(user, contextWithPermission(null)),
                        $(user, contextWithPermission("foobar")),
                        $(user, contextWithPermission("foo, bar")),
                        $(user, contextWithPermission(Sets.newHashSet())),
                        $(user, contextWithPermission(Sets.newHashSet(
                                "foo",
                                "bar",
                                "foo, bar"
                        ))));
            }

            public static Object[] provideBob() {
                return paramsForUser(BOB);
            }

            public static Object[] provideCaesar() {
                return paramsForUser(CAESAR);
            }
        }
    }
}

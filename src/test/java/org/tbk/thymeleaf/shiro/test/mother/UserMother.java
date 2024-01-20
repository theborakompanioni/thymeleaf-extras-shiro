package org.tbk.thymeleaf.shiro.test.mother;

import org.tbk.thymeleaf.shiro.test.user.TestUsers;

import static junitparams.JUnitParamsRunner.$;

/**
 * Created by void on 09.10.15.
 */
public class UserMother {
    public static class AllUsers {
        public static class ShouldRenderForUser {
            public static Object[] provideGuest() {
                return $(null);
            }

            public static Object[] provideAlice() {
                return $(TestUsers.ALICE);
            }

            public static Object[] provideBob() {
                return $(TestUsers.BOB);
            }

            public static Object[] provideCaesar() {
                return $(TestUsers.CAESAR);
            }
        }
    }
}

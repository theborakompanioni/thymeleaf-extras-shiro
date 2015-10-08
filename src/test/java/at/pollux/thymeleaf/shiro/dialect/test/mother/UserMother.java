package at.pollux.thymeleaf.shiro.dialect.test.mother;

import static at.pollux.thymeleaf.shiro.dialect.test.user.TestUsers.*;
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
                return $(ALICE);
            }

            public static Object[] provideBob() {
                return $(BOB);
            }

            public static Object[] provideCaesar() {
                return $(CAESAR);
            }
        }
    }
}

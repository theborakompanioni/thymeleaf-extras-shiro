package at.pollux.thymeleaf.shiro.test.user;


/**
 * @author tbk
 */
public enum TestPermissions {
    PERMISSION_ALL("*"),
    PERMISSION_TYPE_1_ACTION_1_INST_1("permtype1:permaction1:perminst1"),
    PERMISSION_TYPE_1_ACTION_2("permtype1:permaction2:*"),
    PERMISSION_TYPE_3("permtype3:*");

    private String label;

    TestPermissions(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    public static String PERMISSION_TYPE_1_ACTION_2_EXAMPLE = "permtype1:permaction1:xyz";
}

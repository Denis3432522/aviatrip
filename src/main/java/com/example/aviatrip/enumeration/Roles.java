package com.example.aviatrip.enumeration;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum Roles {
    ROLE_CUSTOMER,
    ROLE_REPRESENTATIVE;

    public static List<String> getLowercaseWithoutPrefixRolesList() {
        return Arrays.stream(values())
                .map(r -> r.name().toLowerCase(Locale.ROOT).substring(5))
                .toList();
    }

    public static boolean existsRoleByLowercaseName(String roleName) {
        roleName = "ROLE_" + roleName.toUpperCase(Locale.ROOT);

        for(Roles role : Roles.values()) {
            if(role.name().equals(roleName))
                return true;
        }

        return false;
    }

    public static Roles getRoleByLowercaseWithoutPrefixName(String roleName) {
        return Roles.valueOf("ROLE_" + roleName.toUpperCase(Locale.ROOT));
    }
}

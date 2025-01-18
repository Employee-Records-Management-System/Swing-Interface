package com.hahn.erms.utils;

import com.hahn.erms.models.Account;
import com.hahn.erms.models.Employee;

import java.util.Objects;

public class PermissionHelper {

    public static boolean hasPermission(Account account, Employee employeeToEdit) {
        if(Objects.equals(account.getRole().getName(), "ROLE_MANAGER")
                && Objects.equals(employeeToEdit.getDepartment()
                                                .getId(),
                account.getEmployee()
                       .getDepartment()
                       .getId())){
                return true;
        }

        return Objects.equals(account.getRole().getName(), "ROLE_ADMIN") ||
                Objects.equals(account.getRole().getName(), "ROLE_HR");
    }
}

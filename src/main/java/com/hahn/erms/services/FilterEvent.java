package com.hahn.erms.services;

public interface FilterEvent {
    void onFilter(String searchText, String selectedDepartment, String selectedStatus);
}

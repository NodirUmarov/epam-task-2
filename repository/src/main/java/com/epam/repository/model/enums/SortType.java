package com.epam.repository.model.enums;

public enum SortType {
    ASC("ASC"),
    DESC("DESC"),
    NONE("");

    private final String value;

    SortType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

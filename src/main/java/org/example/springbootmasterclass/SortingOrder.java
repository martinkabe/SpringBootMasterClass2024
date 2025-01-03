package org.example.springbootmasterclass;

public enum SortingOrder {
    ASC,
    DESC;

    public static boolean isValid(SortingOrder order) {
        return order == ASC || order == DESC;
    }
}

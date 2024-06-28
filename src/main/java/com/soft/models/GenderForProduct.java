package com.soft.models;

public enum GenderForProduct {
    MALE(1),
    FEMALE(2),
    OTHER(3); // Giá trị khác

    private final int value;

    private GenderForProduct(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GenderForProduct findByValue(int value) {
        for (GenderForProduct gender : GenderForProduct.values()) {
            if (gender.getValue() == value) {
                return gender;
            }
        }
        return null; // Hoặc xử lý trường hợp không tìm thấy nếu cần
    }
}

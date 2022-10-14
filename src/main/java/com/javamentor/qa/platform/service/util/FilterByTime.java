package com.javamentor.qa.platform.service.util;

public enum FilterByTime {
    Week (7),
    Month(30),
    Year(365),
    AllTime(36500);
    private int daysCount;
    FilterByTime(int daysCount) {
        this.daysCount = daysCount;
    }
    public int getDaysCount() {
        return daysCount;
    }
}

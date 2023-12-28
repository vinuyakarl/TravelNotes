package com.example.travelnotes.main.entity;

public class SortOption {
    private String sortType;
    private boolean isChecked;

    public SortOption(String sortType, boolean isChecked) {
        this.sortType = sortType;
        this.isChecked = isChecked;
    }

    public SortOption() {
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}

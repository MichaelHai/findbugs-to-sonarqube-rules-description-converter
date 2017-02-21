package com.worksap.wang_ha.fb2sqconverter;

public class BugPattern {
    private final String category;
    private final String key;
    private String name;
    private String description;

    public BugPattern(String key, String category) {
        this.key = key;
        this.category = category;
    }

    public String getKey() {
        return key;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

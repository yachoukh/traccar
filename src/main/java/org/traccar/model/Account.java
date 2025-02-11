package org.traccar.model;

public class Account extends BaseModel {
    private String name;
    private AccountType type;
    private boolean disabled;

    public enum AccountType {
        SYSADMIN,
        MANAGER,
        SINGLE
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
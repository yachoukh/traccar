package org.traccar.model;

import org.traccar.storage.StorageName;

@StorageName("tc_account_attributes")
public class AccountAttribute extends BaseModel {
    private String name;
    private String value;
    private long accountId;
    private AttributeType type;

    public enum AttributeType {
        BOOLEAN,
        NUMBER,
        STRING
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }
}
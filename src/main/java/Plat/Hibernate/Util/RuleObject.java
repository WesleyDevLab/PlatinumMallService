package Plat.Hibernate.Util;

public class RuleObject {
    private int operation;
    private String key;
    private Object value;
    private boolean DataTypeFlag = false;

    public RuleObject() {
    }

    public RuleObject(String key, int operation, Object value) {
        this.operation = operation;
        this.key = key;
        this.value = value;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return (DataTypeFlag) ? Integer.parseInt(this.value.toString()) : this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void convertValueIntoInteger() {
        this.DataTypeFlag = true;
    }

}
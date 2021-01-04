package com.maestro.app.utils.data.specifications;

public enum SearchOperator {
    EQ(":"), NE("<>"), GT(">"), LT("<"), GTE(">:"), LTE("<:"), EMPTY("!:"), CONTAINS("~:"), STARTS("^:"), ENDS("$:");

    private String operation;

    SearchOperator(String operation) {
        this.operation = operation;
    }

    public static SearchOperator getOperationByValue(String value) {
        for (SearchOperator op : SearchOperator.values()) {
            if (op.getValue().equals(value))
                return op;
        }
        return null;
    }

    public String getValue() {
        return this.operation;
    }
}

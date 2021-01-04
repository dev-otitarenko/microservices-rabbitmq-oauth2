package com.maestro.app.utils.data.specifications;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchField {
    private String key;
    private SearchOperator operation;
    private String value;

    @Override
    public String toString() {
        return String.format("\t Field:%s, Operator:%s, Value:%s", key, operation.toString(), value);
    }
}

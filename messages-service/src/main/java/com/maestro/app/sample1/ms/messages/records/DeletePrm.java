package com.maestro.app.sample1.ms.messages.records;

import lombok.Data;

import java.util.List;

@Data
public class DeletePrm {
    private List<String> ids;
}

package com.maestro.app.utils.queue;

import com.maestro.app.utils.auth.AuthUser;
import com.maestro.app.utils.types.QueueMessageState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueueUserMessage {
    private AuthUser user;
    private String code;
    private String title;
    private String message;
    private double executionTime;
    private String idDownload;
    private String downloadFileNm;
    private QueueMessageState state;
}

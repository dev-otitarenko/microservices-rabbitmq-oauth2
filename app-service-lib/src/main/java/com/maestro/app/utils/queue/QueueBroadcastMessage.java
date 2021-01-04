package com.maestro.app.utils.queue;

import com.maestro.app.utils.types.QueueMessageState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QueueBroadcastMessage {
    private String title;
    private String message;
    private QueueMessageState state;
}

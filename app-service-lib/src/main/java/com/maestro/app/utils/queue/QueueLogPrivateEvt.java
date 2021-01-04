package com.maestro.app.utils.queue;

import com.maestro.app.utils.auth.AuthUser;
import com.maestro.app.utils.types.QueueEventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueLogPrivateEvt {
    private AuthUser user;
    private QueueEventType mode;
    private String name;
    private String description;
}

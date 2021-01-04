package com.maestro.app.utils.queue;

import com.maestro.app.utils.types.QueueEventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueueLogPublicEvt {
    private QueueEventType mode;
    private String name;
    private String description;
    private String ip_address;
    private String countryCode;
    private String countryName;
    private String city;
}

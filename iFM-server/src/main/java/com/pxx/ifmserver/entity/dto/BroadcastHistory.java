package com.pxx.ifmserver.entity.dto;


import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import java.time.LocalDateTime;

@Data
public class BroadcastHistory {
    private Integer         broadcastId;
    private Integer         userId;
    private Integer         lastListenDuration;
    private LocalDateTime   historyTime;
}

package com.pxx.ifmserver.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BroadcastFavorite {
    private Integer         broadcastId;
    private Integer         userId;
    private LocalDateTime   gmt_create;
}

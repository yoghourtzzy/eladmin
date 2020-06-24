package me.zzy.wechat.service.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TaskMonthlyAveScoreDto {
    Long userid;
    Timestamp month;
}

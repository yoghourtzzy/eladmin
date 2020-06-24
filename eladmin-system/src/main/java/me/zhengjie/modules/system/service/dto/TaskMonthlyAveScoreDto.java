package me.zhengjie.modules.system.service.dto;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
public class TaskMonthlyAveScoreDto {
    Long userid;
    Timestamp month;
}

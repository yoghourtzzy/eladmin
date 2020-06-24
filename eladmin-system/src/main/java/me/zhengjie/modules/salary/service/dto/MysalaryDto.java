package me.zhengjie.modules.salary.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author zzy
* @date 2020-06-17
*/
@Data
public class MysalaryDto implements Serializable {

    private Long id;

    private Long userId;

    private String userName;

    private Double trafficSalary;

    private Double lunchSalary;

    private Double finalBonus;

    private Double insurance;

    private Double tax;

    private Double finalSalary;

    private Timestamp updateTime;

    private Timestamp createTime;

    private Timestamp month;

    private Double attendanceDeduction;
}
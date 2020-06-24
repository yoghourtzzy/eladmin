package me.zhengjie.modules.salary.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author zzy
* @date 2020-06-17
*/
@Entity
@Data
@Table(name="mysalary")
public class Mysalary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "traffic_salary")
    private Double trafficSalary;

    @Column(name = "lunch_salary")
    private Double lunchSalary;

    @Column(name = "final_bonus")
    private Double finalBonus;

    @Column(name = "insurance")
    private Double insurance;

    @Column(name = "tax")
    private Double tax;

    @Column(name = "final_salary")
    private Double finalSalary;

    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "month")
    private Timestamp month;

    @Column(name = "attendance_deduction")
    private Double attendanceDeduction;

    public void copy(Mysalary source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
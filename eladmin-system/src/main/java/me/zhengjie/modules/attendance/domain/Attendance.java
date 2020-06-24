package me.zhengjie.modules.attendance.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author zzy
* @date 2020-04-26
*/
@Entity
@Data
@Table(name="attendance")
public class Attendance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    /** 所记录的日期 */
    @Column(name = "rec_date")
    private Date recDate;

    @Column(name = "state")
    private Integer state;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "finish_time")
    private Timestamp finishTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    public void copy(Attendance source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
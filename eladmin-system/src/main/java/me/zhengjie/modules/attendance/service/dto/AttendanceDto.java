package me.zhengjie.modules.attendance.service.dto;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.io.Serializable;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
* @author zzy
* @date 2020-04-26
*/
@Data
public class AttendanceDto implements Serializable {

    /** 防止精度丢失 */
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    private Long userId;

    /** 所记录的日期 */
    private Date recDate;

    private Integer state;

    private Timestamp startTime;

    private Timestamp finishTime;

    private Timestamp updateTime;

    private Timestamp createTime;
}
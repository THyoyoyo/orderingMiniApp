package com.orderingMinAppAip.dto.reserve;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ReserveSavaDto {

    @ApiModelProperty(value = "自增ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 预定日期 */
    @ApiModelProperty(value = "预定日期")
    private Date creator;


    /** 预约家庭ID */
    @ApiModelProperty(value = "预约家庭ID")
    private Integer familyId;

    /** 早上 */
    @ApiModelProperty(value = "早上")
    private List<Integer> morning;

    /** 中午 */
    @ApiModelProperty(value = "中午")
    private List<Integer> noon;


    /** 晚上 */
    @ApiModelProperty(value = "晚上")
    private List<Integer> night;

    /** 夜宵 */
    @ApiModelProperty(value = "夜宵")
    private List<Integer> midnight;

}

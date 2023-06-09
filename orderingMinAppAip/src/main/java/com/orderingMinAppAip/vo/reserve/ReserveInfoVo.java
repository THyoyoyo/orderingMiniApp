package com.orderingMinAppAip.vo.reserve;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.orderingMinAppAip.model.family.Family;
import com.orderingMinAppAip.model.reserve.ReserveDayRemark;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ReserveInfoVo {
    @ApiModelProperty(value = "自增ID")
    private Integer id;

    /** 预定日期 */
    @ApiModelProperty(value = "预定日期")
    private Date creator;


    /** 预约家庭ID */
    @ApiModelProperty(value = "预约家庭ID")
    private Integer familyId;

    /** 预约家庭信息*/
    @ApiModelProperty(value = "预约家庭信息")
    private Family familyInfo;

    /** 早上 */
    @ApiModelProperty(value = "早上")
    private List<ReserveInfoItemVo> morning;

    /** 中午 */
    @ApiModelProperty(value = "中午")
    private List<ReserveInfoItemVo> noon;


    /** 晚上 */
    @ApiModelProperty(value = "晚上")
    private List<ReserveInfoItemVo> night;

    /** 夜宵 */
    @ApiModelProperty(value = "夜宵")
    private List<ReserveInfoItemVo> midnight;


    /** 备注 */
    @ApiModelProperty(value = "备注")
    private List<ReserveDayRemark> Remark;
}

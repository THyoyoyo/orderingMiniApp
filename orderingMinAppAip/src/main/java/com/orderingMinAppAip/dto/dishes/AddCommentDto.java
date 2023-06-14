package com.orderingMinAppAip.dto.dishes;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddCommentDto {


    /** 菜品ID */
    @ApiModelProperty(value = "菜品ID")
    private Integer dishesId;

    /** 评论内容 */
    @ApiModelProperty(value = "评论内容")
    private String content;


    /** 星级（0-5） */
    @ApiModelProperty(value = "星级（0-5）")
    private Integer starLevel;

}

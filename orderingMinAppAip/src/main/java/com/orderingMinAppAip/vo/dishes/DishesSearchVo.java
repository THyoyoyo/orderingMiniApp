package com.orderingMinAppAip.vo.dishes;


import lombok.Data;

import java.util.Date;

@Data
public class DishesSearchVo {
   private String brief;
   private Integer creatorUserId;
   private Integer familyId;
   private Integer userLike;
   private Date creatorTime;
   private String name;
   private Integer isPublic;
   private String course;
   private String creatorName;
   private Integer id;
   private Integer sort;

}

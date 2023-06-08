package com.blogs.vo.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageVo<T> implements Serializable {

    private  Long pageNum;
    private  Long pageSize;
    private  Long total;
    private  Long pages;
    private List<T> list;

}

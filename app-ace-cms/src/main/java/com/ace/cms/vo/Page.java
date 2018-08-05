package com.ace.cms.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 描述类的作用
 * Created by:sanhu Date:16/7/28 Time:下午4:26
 */
@Getter
@Setter
@ToString(callSuper = true)
public class Page  {

    /**
     * 当前页面数
     */
    private Integer pageNum = 1;

    /**
     * 每页显示多少行数据
     */
    private Integer pageCount = 10;

    /**
            * 站点订单每页显示多少行数据
     */
    private Integer dataCount = 50;

    /**
     * 当前页面开始行
     */
    private Integer startRow = 0;

    /**
     * 用于批量更新或者删除
     */
    private List<?> results;

    /**
     * 获取当前页面开始行
     *
     * @return
     */
    public Integer getStartRow() {
        return (this.pageNum - 1) * this.pageCount;
    }

    public Page(Integer pageNum, Integer pageCount) {
		super();
		this.pageNum = pageNum;
		this.pageCount = pageCount;
	}

	public Page() {
		super();
	}

	/**
     * 开始时间
     */
    private Date startDate;

    /**
     * 结束时间
     */
    private Date endDate;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;
}

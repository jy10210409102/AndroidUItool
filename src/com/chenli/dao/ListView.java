/**
 * Package com.chenli.dao
 * File Name:ListView.java
 * Date:2013-11-23下午2:02:32
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.dao;

import java.awt.Canvas;
import java.awt.Graphics;

import com.chenli.all.interfaces.DrawUnit;
import com.chenli.all.interfaces.View;

/**
 * 对应android的listview组件
 * ClassName:ListView <br/> 
 * date: 2013-11-23下午2:02:32 <br/>
 * @author zhonghong.chenli        
 */
public class ListView extends View {

	/** 列表滚动条默认为开 */
	private boolean scorllShow=true;
	/** 滚动条背景图片 */
	private String scorllBackground ;
	/** 滚动条滑块图片 */
	private String scorllThumb;
	/** 分隔条是否显示*/
	private boolean divideShow;
	/**  分隔条的颜色 或 图片*/
	private String divideColorOrPhoto;
	/** 分隔条高度 */
	private String divideHight;
	
	public boolean isScorllShow() {
		return scorllShow;
	}

	public void setScorllShow(boolean scorllShow) {
		this.scorllShow = scorllShow;
	}

	public String getScorllBackground() {
		return scorllBackground;
	}

	public void setScorllBackground(String scorllBackground) {
		this.scorllBackground = scorllBackground;
	}

	public String getScorllThumb() {
		return scorllThumb;
	}

	public void setScorllThumb(String scorllThumb) {
		this.scorllThumb = scorllThumb;
	}

	public boolean isDivideShow() {
		return divideShow;
	}

	public void setDivideShow(boolean divideShow) {
		this.divideShow = divideShow;
	}

	public String getDivideColorOrPhoto() {
		return divideColorOrPhoto;
	}

	public void setDivideColorOrPhoto(String divideColorOrPhoto) {
		this.divideColorOrPhoto = divideColorOrPhoto;
	}

	public String getDivideHight() {
		return divideHight;
	}

	public void setDivideHight(String divideHight) {
		this.divideHight = divideHight;
	}

	/**
	 * @param mDrawUnit
	 */
	public ListView(DrawUnit mDrawUnit) {
		super(mDrawUnit);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void drawMyself(Graphics g, Canvas canvas) {
		// TODO Auto-generated method stub

	}

}

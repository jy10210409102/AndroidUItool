/**
 * Package com.chenli.dao
 * File Name:Button.java
 * Date:2013-11-18下午2:27:02
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.dao;

import com.chenli.all.interfaces.DrawUnit;

/**
 * 对应android的Button组件 ClassName:Button <br/>
 * date: 2013-11-18下午2:27:02 <br/>
 * @author zhonghong.chenli
 */
public class Button extends TextView {
	/**
	 * @param mDrawUnit
	 */
	public Button(DrawUnit mDrawUnit) {
		super(mDrawUnit);
		// TODO Auto-generated constructor stub
	}
	/**文本是否打开开关*/
	public boolean isOpentext =false;	//默认为关
	/** 按下图片 */
	public String pressedImage;
	/** 无效图片 */
	public String disEnableImage;
	public boolean isOpentext() {
		return isOpentext;
	}
	
	public void setOpentext(boolean isOpentext) {
		this.isOpentext = isOpentext;
	}
	
	
	
}

/**
 * Package com.chenli.drawable.interfaces
 * File Name:CanvasEvent.java
 * Date:2013-11-16下午3:47:50
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.all.interfaces;

/**
 * 画布监听事件
 * ClassName:CanvasEvent <br/> 
 * date: 2013-11-16下午3:47:50 <br/>
 * @author zhonghong.chenli        
 */
public interface CanvasEvent {
	/**
	 * 返回当前选中的DrawUnit
	 * @return DrawUnit对象
	 */
	public DrawUnit getCurrentDrawUnit ();
	
	/**
	 * 早画布上删除选中框
	 */
	public void delRectCase();
}

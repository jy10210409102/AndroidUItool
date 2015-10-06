/**
 * Package com.chenli.drawable.interfaces
 * File Name:MyCanvisInterface.java
 * Date:2013-11-16上午10:35:23
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.all.interfaces;

import java.awt.Canvas;

/**
 * ClassName:MyCanvisInterface <br/> 
 * date: 2013-11-16上午10:35:23 <br/>
 * @author zhonghong.chenli        
 */
public interface MyCanvis {
	/**
	 * 更新整个画布
	 */
	public void updateCanVis();
	
	/**
	 * 添加画布事件 
	 * @param mCanvasEvent 画布事件对象
	 */
	public void addListener(Canvas mCanvas);


}

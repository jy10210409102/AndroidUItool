/**
 * Package com.chenli.drawable.interfaces
 * File Name:Operate.java
 * Date:2013-11-17下午4:22:07
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.all.interfaces;

/**
 * 操作接口，定义撤销，保存，复制，粘贴等操作
 * ClassName:Operate <br/>
 * date: 2013-11-17下午4:22:07 <br/>
 * @author zhonghong.chenli
 */
public interface Operate {
	/**
	 * 保存操作
	 */
	public void CtrlSOperate();
	
	/**
	 * 回到上一个操作
	 */
	public void CtrlZOperate();
	
	/**跳到下一个操作*/
	public void CtrlYOperate();
	
	/**
	 * 复制     在拖动的时候会默认有一个复制的按键值 要区分并屏蔽掉
	 */
	public void ctrlCOperate();
	
	/**
	 * 粘贴
	 */
	public void ctrlVOperate();
	
	/**
	 * 删除选中图片
	 */
	public void deleteOperate();
	
	/**
	 * 刷新 刷新画布
	 */
	public void refreshOperate();
	
	/**
	 * 弹出属性窗口
	 */
	public void showAttr(DrawUnit mDrawUnit);
	
	/**
	 * 设置分辨率为1024*600
	 */
	public void setFBL1024_600();
	
	/**
	 * 设置分辨率为800*480
	 */
	public void setFBL800_480();
	
	/**
	 * 把当前选中图片 置顶
	 */
	public void ToTop();
	
	/**
	 * 把当前选中图片 置底
	 */
	public void ToButtom();
	
	/**
	 * 创建背景的 按下等效果的xml
	 */
	public void createBGXml(DrawUnit mDrawUnit);
	
	/**
	 * 生成布局xml
	 */
	public void CreateXml();
}

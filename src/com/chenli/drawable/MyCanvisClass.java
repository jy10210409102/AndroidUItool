/**
 * Package com.chenli.drawable
 * File Name:MyCanvis.java
 * Date:2013-11-15下午7:53:01
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.drawable;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.chenli.all.interfaces.CanvasEvent;
import com.chenli.all.interfaces.DrawUnit;
import com.chenli.all.interfaces.MyCanvis;
import com.chenli.all.interfaces.PhotoManage;
import com.chenli.frame.MainJFrame;

/**
 * ClassName:MyCanvis <br/>
 * date: 2013-11-15下午7:53:01 <br/>
 * @author zhonghong.chenli
 */
@SuppressWarnings("serial")
public class MyCanvisClass extends Canvas implements MyCanvis {
	/** 当前画布 */
	Canvas mCanvas = null;

	/** 父窗体 */
	MainJFrame mMainJFrame = null;

	/** PhotoManage对象 把对象的创建放在frame中一边分发给需要的类使用*/  
	PhotoManage mPhotoManage ;
	
	/**画布监听对象*/
	CanvasEvent mCanvasEvent;

	/** 构造方法 */
	public MyCanvisClass(MainJFrame mMainJFrame) {
		this.mMainJFrame = mMainJFrame;
		this.mPhotoManage=this.mMainJFrame.getmPhotoManage();
		this.mCanvas = this;
		//addListener(this);
	}

	/** 画图源头方法 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		//drawWorkSpace(g);
		DrawPhoto(g);
	}

	/**
	 * 画工作区域  (目前没有使用)
	 * @param g
	 */
	public void drawWorkSpace(Graphics g) {
		g.drawLine(0, 0, 1024, 0);
		g.drawLine(0, 0, 0, 800);
		g.drawLine(0, 800, 1024, 800);
		g.drawLine(1024, 0, 1024, 800);
	}

	/**
	 * 画图
	 * @param g Graphics
	 */
	public void DrawPhoto(Graphics g) {
		mPhotoManage.drawAllDrawUnit(g, this);
	}

	@Override
	public void updateCanVis() {
		this.repaint();
	}

	@Override
	public void addListener(Canvas mCanvas) {
		//mCanvasEvent = new CanvasEventHandler(mMainJFrame,this);
	}

	
	
}

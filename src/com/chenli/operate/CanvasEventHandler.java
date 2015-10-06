/**
 * Package com.chenli.drawable
 * File Name:CanvasEventHandler.java
 * Date:2013-11-16下午3:44:48
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.operate;

import java.awt.Canvas;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JOptionPane;

import com.chenli.all.interfaces.CanvasEvent;
import com.chenli.all.interfaces.DrawUnit;
import com.chenli.all.interfaces.Operate;
import com.chenli.all.interfaces.PhotoManage;
import com.chenli.dao.DrawUnitClass;
import com.chenli.frame.MainJFrame;
import com.chenli.type.T;

/**
 * 这个类处理鼠标事件和右键菜单事件<br/>
 * 生成空TextView组件思路：如果当前为textView状态并且currentDrawUnit为空则在按下的地方开始记录textView起始坐标
 * 并新建一个选中框对象，并设置当前鼠标状态，在拖拽监听中实时画文本范围框， 在释放的时候生成texview文本 如果没有达到生成状态则取出当前选中框 <br/>
 * ClassName:CanvasEventHandler <br/>
 * date: 2013-11-16下午3:44:48 <br/>
 * 
 * @author zhonghong.chenli
 */
public class CanvasEventHandler implements CanvasEvent {

	/** 画布 */
	Canvas mCanvas = null;
	
	/** 主窗体对象*/
	MainJFrame mMainJFrame ;
	
	/** 鼠标当前状态 */
	private int currentMouseState = T.MouseState.MOUSESTATE_RELAX;

	/** 拖拽第一次记录的坐标值 */
	Point dropPoint = new Point();

	/** 图片管理对象 */
	PhotoManage mPhotoManage = null;

	/** 当前选中图片 当添加一张新图片时新图片就赋给当前图片 方便很多东西 ，比如能保证矩形选中框一直在最顶端*/
	DrawUnit currentDrawUnit = null;
	
	/** 选中矩形框*/
	DrawUnit rectCase = null;
	
	/** 弹出菜单*/
	PopupMenu popupMenu1;
	
	/** 操作对象*/
	Operate mOperate;
	
	/**记录生成空文本初始按下坐标    注意在释放中清零*/
	private Point createTextViewDownPoint ;

	/** 构造方法 */
	public CanvasEventHandler(MainJFrame mMainJFrame,Canvas mCanvas) {
		this.mMainJFrame=mMainJFrame;
		this.mCanvas = mCanvas;
		this.mPhotoManage = mMainJFrame.getmPhotoManage();
		initListener();
	}

	/** 提供get状态*/
	public int getCurrentMouseState() {
		return currentMouseState;
	}


	/**
	 * 初始化监听事件
	 */
	private void initListener() {
		mOperate = mMainJFrame.getmOperate();
		// 拖拽
		CanvasEventHandler.DndTargetListener dnd = new CanvasEventHandler.DndTargetListener();
		mCanvas.setDropTarget(new DropTarget(mCanvas,
				DnDConstants.ACTION_REFERENCE, dnd, true));
		// 鼠标
		mCanvas.addMouseMotionListener(canvisMouseMotionListener);
		mCanvas.addMouseListener(canvisMouseListener);
		mCanvas.addKeyListener(canvasKeyListener);
		
		//添加弹出菜单
		setRightMenu();
	}
	
	/**
	 * 画布添加弹出菜单
	 */
	private void setRightMenu(){
		popupMenu1 = new PopupMenu();
		//然后新建一些子菜单，这里我们使用建立三个菜单项
		MenuItem refresh = new MenuItem("refresh");			//刷新
		MenuItem attr = new MenuItem("attr");				//属性
		MenuItem cope = new MenuItem("cope （Ctrl + C）");	//复制
		MenuItem paste = new MenuItem("paste （Ctrl + V）");//粘贴
		MenuItem delete = new MenuItem("delete");			//删除
		MenuItem toTop = new MenuItem("toTop");				//置顶
		MenuItem bgXML = new MenuItem("createBGXml");		//创建
		System.out.println(refresh.getName());
		//然后再出初化，并加入监听事件中：
		popupMenu1.add(refresh);
		popupMenu1.add(attr);
		popupMenu1.add(cope);
		popupMenu1.add(paste);
		popupMenu1.add(delete);
		popupMenu1.add(toTop);
		popupMenu1.add(bgXML);
		//在this中加入popupMenu1
		mCanvas.add(popupMenu1);
		refresh.addActionListener(refreshActionListener);
		attr.addActionListener(attrActionListener);
		cope.addActionListener(copeActionListener);
		paste.addActionListener(pasteActionListener);
		delete.addActionListener(deleteActionListener);
		toTop.addActionListener(toTopActionListener);
		bgXML.addActionListener(createBGXmlActionListener);
	}

	/***********弹出菜单事件处理*******************/
	/** 刷新监听*/
	ActionListener refreshActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			mOperate.refreshOperate();
		}
	};
	
	/** 属性监听*/
	ActionListener attrActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent event) {
			mOperate.showAttr(currentDrawUnit);
		}
	};


	/** 复制监听*/
	ActionListener copeActionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent event) {
			mOperate.ctrlCOperate();
		}
	};
	
	/** 粘贴监听*/
	ActionListener pasteActionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent event) {
			mOperate.ctrlVOperate();
		}
	};
	
	/** 删除监听*/
	ActionListener deleteActionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent event) {
			mOperate.deleteOperate();
		}
	};
	
	/** 置顶*/
	ActionListener toTopActionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("置顶");
			mOperate.ToTop();
		}
	};
	
	
	/** 创建背景xml  即特殊效果*/
	ActionListener createBGXmlActionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("置顶");
			mOperate.createBGXml(currentDrawUnit);
		}
	};
	/**************弹出菜单end************************/
	
	
	/** 拖拽监听类 */
	private class DndTargetListener implements DropTargetListener {

		@Override
		public void dragEnter(DropTargetDragEvent arg0) {
			System.out.println("dragEnter");
		}

		@Override
		public void dragExit(DropTargetEvent arg0) {
			System.out.println("dragExit");
		}

		@Override
		public void dragOver(DropTargetDragEvent arg0) {
			// System.out.println("dragOver");
		}

		@SuppressWarnings("unchecked")
		@Override
		public void drop(DropTargetDropEvent arg0) {
			System.out.println("drop");
			arg0.acceptDrop(DnDConstants.ACTION_REFERENCE);
			if (arg0.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				try {
					Transferable tf = arg0.getTransferable();
					List<File> list = (List<File>) tf
							.getTransferData(DataFlavor.javaFileListFlavor);
					for (File f : list) {
						if (f.exists() && f.isFile()) {// 如果是文件
							if (!isPhotoFile(f)) {
								return;
							}
							canvasAddPhoto(arg0,f);  //拖拽添加图片
							return;
						} else if (f.exists() && f.isDirectory()) {// 如果是文件夹
							// 这里不对拖拽文件夹做操作
							// jTextField1.setText(f.getAbsolutePath());
							System.out.println(f.getAbsolutePath());
							return;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void dropActionChanged(DropTargetDragEvent arg0) {
			System.out.println("dropActionChanged");
		}
	}

	/**
	 * 拖拽添加图片
	 * @param arg0 事件
	 * @param file 图片文件
	 */
	public void canvasAddPhoto(DropTargetDropEvent arg0 , File file) throws Exception{
		Date d = new Date();
		int[] size = getPhotoSize(file.getAbsolutePath());
		DrawUnit mDrawUnit = new DrawUnitClass(mPhotoManage,d.getTime()		//对象id
				+ "", file.getAbsolutePath(),								//图片路径
				arg0.getLocation().x - size[0] / 2,							//x坐标
				arg0.getLocation().y - size[1] / 2,							//y坐标
				size[0], size[1] ,											//宽高
				T.viewName.viewNameMap.get(T.viewName.CurrentViewType));//组件类型
//		if(rectCase!=null){				//添加的删除选中框  避免产生新加图片属于选中框的混乱局面
//			//mPhotoManage.delDrawUnit(rectCase);
//			//delRectCase();
//		}
		mPhotoManage.addDrawUnit(mDrawUnit);
		currentDrawUnit = mDrawUnit;
		mCanvas.repaint();
		showCurrent();
		
		//添加layout 标示checkbox
		int viewType = T.viewName.viewNameMap.get(T.viewName.CurrentViewType);		 //获得组件类型
		if(viewType>= T.ViewType.LAYOUT_MIN  && viewType < T.ViewType.LAYOUT_MAX ){  //如果添加的是layout组件 则新建layout窗口
			//添加窗口
			mMainJFrame.getmLayoutManage().addLayout(mDrawUnit);
		}
	}
	/**
	 * 获得图片大小
	 * 
	 * @param photoPah
	 *            图片路径
	 * @return int[0] 宽 int[1]高
	 */
	public int[] getPhotoSize(String photoPah) throws Exception {
		File picture = new File(photoPah);
		BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
		System.out.println(String.format("%.1f", picture.length() / 1024.0));
		System.out.println(sourceImg.getWidth());
		System.out.println(sourceImg.getHeight());
		return new int[] { sourceImg.getWidth(), sourceImg.getHeight() };
	}

	/**
	 * 动作监听
	 */
	MouseMotionListener canvisMouseMotionListener = new MouseMotionListener() {

		@Override
		public void mouseMoved(MouseEvent arg0) { // 移动监听   按下状态时移动鼠标并没有走这个监听
			//System.out.println("移动监听·····");
			
			
		}

		@Override
		public void mouseDragged(MouseEvent event) { // 拖拽监听	
			if (currentMouseState != T.MouseState.MOUSESTATE_DROP  && currentMouseState != T.MouseState.MOUSESTATE_CREATTXT) { // 如果不为拖拽状态则记录第一次点击的值
				dropPoint.setLocation(event.getX(), event.getY());
				System.out.println("移动拖拽  ，不是生成文本状态");
				currentMouseState = T.MouseState.MOUSESTATE_DROP;
			}
			mouseDragChange(dropPoint, event);
			if(currentMouseState == T.MouseState.MOUSESTATE_CREATTXT ){ //如果当前是textview生成状态 当前选中单元是为空的  不用理会前面的函数
				System.out.println("拖拽生成空的文本大小确认中");
				changeChoose(event); //事实画选中框大小
			}
		}
	};
	
	
	/**生成无背景TextView 相关类 记录上一次变化矩形数据*/
	class Rect {
		int x;
		int y;
		int wight;
		int hight;
		public Rect(int x , int y,int wight, int hight) {
			this.wight = wight;
			this.hight = hight;
		}
		
	}
	
	Rect lastRect=null;
	/**
	 * 拖拽改变选中框 并刷新
	 * @param event 事件对象
	 */
	public void  changeChoose(MouseEvent event){
		int startX = (int) createTextViewDownPoint.getX();
		int startY = (int) createTextViewDownPoint.getY();
		int wight = Math.abs((int)(event.getX()-createTextViewDownPoint.getX()));
		int hight = Math.abs((int)(event.getY()-createTextViewDownPoint.getY()));

		startX  =  Math.min(startX,event.getX());
		startY =  Math.min(startY , event.getY());
		rectCase.setX(startX);
		rectCase.setY(startY);
		rectCase.setWight(wight);
		rectCase.setHight(hight);
		
		//记录上一个移动的大小 然后比较  取大的范围刷新
		if(lastRect == null){
			lastRect =new Rect(startX , startY , wight , hight);
		}
		//取最大宽和最大高刷新
		int minX = Math.min(lastRect.x , startX );
		int minY =  Math.min(lastRect.y , startY );
		int maxWidth = Math.max(lastRect.wight, wight);
		int maxHight = Math.max(lastRect.hight, hight);
		//partRefresh(rectCase,0,0);
		lastRect.x = startX;
		lastRect.y = startY;
		lastRect.wight = wight;
		lastRect.hight = hight;
		mCanvas.repaint(minX,minY, maxWidth, maxHight);
	}
	
	
	
	/**
	 * 拖拽时的处理
	 * @param downPoint 第一次按下的坐标 通过此坐标判断图片
	 * @param event 	鼠标移动事件对象
	 */
	private void mouseDragChange(Point dropPoint, MouseEvent event) {
		if (isMouseRightKey(event)) {// 如果是鼠标右键 则不拖拽
			return;
		}
//		// 判断是哪张图片
//		DrawUnit mDrawUnit = getCurrentPhoto(dropPoint.x, dropPoint.y);
		if (this.currentDrawUnit == null) {	
			//如果当前选中的
			
			return;
		}
		//this.currentDrawUnit = mDrawUnit;
		// 计算移动的位移
		int downX = event.getX();
		int downY = event.getY();
		int moveX = (int) (downX - dropPoint.getX());
		int moveY = (int) (downY - dropPoint.getY());
//		mDrawUnit.setX(mDrawUnit.getX() + moveX);
//		mDrawUnit.setY(mDrawUnit.getY() + moveY);
		this.currentDrawUnit.setOffset(moveX,moveY);
		dropPoint.setLocation(downX, downY);// 更新拖拽坐标
		partRefresh(this.currentDrawUnit, moveX, moveY);// 局部刷新（父节点的刷新包含了子节点的刷新）
	}

	/**
	 * 局部刷新
	 * @param mDrawUnit  mDrawUnit对象
	 * @param moveX  	移动的x坐标
	 * @param moveY  	移动的Y坐标
	 */
	public void partRefresh(DrawUnit mDrawUnit, int moveX, int moveY) {
		if(mDrawUnit ==null){
			return ;
		}
		int x = mDrawUnit.getX();
		int y = mDrawUnit.getY();
		int wight = mDrawUnit.getWight();
		int hight = mDrawUnit.getHight();
		if (moveX < 0) {
			wight -= moveX;
		}
		if (moveX > 0) {
			x -= moveX;
			wight += moveX;
		}
		if (moveY < 0) {
			hight -= moveY;
		}
		if (moveY > 0) {
			y -= moveY;
			hight += moveY;
		}
		mCanvas.repaint(x, y, wight, hight);
	}

	/**
	 * 鼠标监听
	 */
	MouseListener canvisMouseListener = new MouseListener() {
		@Override
		public void mouseReleased(MouseEvent event) { // 释放点击时调用
			System.out.println("mouseReleased");
			createNullTextView();//生成没有背景图片的TextView
			switch (currentMouseState) {
			case T.MouseState.MOUSESTATE_DROP: // 如果当前状态为拖拽
				System.out.println("拖拽移动完成");
				break;
			}
			currentMouseState = T.MouseState.MOUSESTATE_RELAX; // 释放状态
		}

		
		@Override
		public void mousePressed(MouseEvent event) { // 鼠标按下时触发    这里做无图片文本的创建
			getCurrentPhoto(event.getX(), event.getY());
			// System.out.println("mousePressed");
			if(isMouseRightKey(event)){
				showCurrent();
				popupMenu1.show(mCanvas,event.getX(),event.getY());//弹出菜单
			}
			if(currentDrawUnit ==null && T.viewName.viewNameMap.get(T.viewName.CurrentViewType)==T.ViewType.TEXTVIEW ){ //如果当前是textview
				if(isMouseRightKey(event)){
					return;
				}
				currentMouseState = T.MouseState.MOUSESTATE_CREATTXT;	//设当前状态为拖拽生成空文本状态
				createTextViewDownPoint = new Point(event.getX() , event.getY());//新建按下坐标
				//生成TextView组件
				Date d=new Date();
				if(rectCase == null){
					System.out.println("选中框不存在 新建");
					rectCase = new DrawUnitClass(mPhotoManage,
							d.getTime() + "", null, event.getX(),
							event.getY(), 0,
							0, T.ViewType.CURRENT_CASE);
					mPhotoManage.addDrawUnit(rectCase);
				}
				System.out.println("空文本开始拖拽");
			}
		}

		@Override
		public void mouseExited(MouseEvent event) { // 鼠标移出时触发
			// System.out.println("mouseExited");
			if (currentMouseState == T.MouseState.MOUSESTATE_CREATTXT) { // 如果当前是textview
				createNullTextView();
				System.out.println("已经移出，拖拽生成空的文本");
			}
			currentMouseState = T.MouseState.MOUSESTATE_RELAX; // 释放状态
		}

		@Override
		public void mouseEntered(MouseEvent event) { // 鼠标移入时触发
			// System.out.println("mouseEntered");
		}

		@Override
		public void mouseClicked(MouseEvent event) { // 点击事件 用做判断右点击 开启菜单
			getCurrentPhoto(event.getX(), event.getY());
			if(isMouseRightKey(event)){
				showCurrent();
				popupMenu1.show(mCanvas,event.getX(),event.getY());//弹出菜单
			}
		}
	};
	
	/**
	 * 生成空TextView 没有背景图片的TextView
	 */
	private void createNullTextView() {
		if ( currentMouseState == T.MouseState.MOUSESTATE_CREATTXT) { // 如果当前是textview
			// 生成空的TextView 含文字text
			if (rectCase.getWight() > 20 && rectCase.getHight() > 20 ) { // 如果textView
				Date d = new Date();
				DrawUnit textView = new DrawUnitClass(mPhotoManage,
						d.getTime() + "", T.ViewType.TEXTVIEW_EMPTY,
						rectCase.getX(), rectCase.getY(),
						rectCase.getWight(), rectCase.getHight(),
						T.ViewType.TEXTVIEW);
				mPhotoManage.addDrawUnit(textView);
			}
			// 删除选中框
			delRectCase();

			createTextViewDownPoint = null;// 清空按下坐标
			System.out.println("拖拽生成空的文本");
		}
	}
	

	/**
	 * 键盘点击事件
	 */
	KeyListener canvasKeyListener = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent event) {
			// TODO Auto-generated method stub
			// System.out.println("keyTyped");
		}

		@Override
		public void keyReleased(KeyEvent event) {
			// TODO Auto-generated method stub
			// System.out.println("keyReleased");
		}

		@Override
		public void keyPressed(KeyEvent event) { // 上38 下40 左37 右39    8删除键
													// 在这里添加移动事件进行图片位置微调
			System.out.println("keyPressed");
			System.out.println("按键值"+event.getKeyCode());
			int moveX = 0, moveY = 0;
			switch (event.getKeyCode()) {
			case 37:			// 左
				moveX = -1;
				break;
			case 38: 			// 上
				moveY = -1;
				break;
			case 39: 			// 右
				moveX = 1;
				break;
			case 40: 			// 下
				moveY = 1;
				break;
			case 8:				//删除 
				if(currentDrawUnit==null){
					return ;
				}
				delRectCase();
				mPhotoManage.delDrawUnit(currentDrawUnit); 
				System.out.println("删除currentDrawUnit");
				partRefresh(currentDrawUnit, 0, 0);// 局部刷新
				currentDrawUnit = null;
				return ;
			default:
				return;
			
			}
			
			if(currentDrawUnit ==null){
				return ;
			}
//			currentDrawUnit.setX(currentDrawUnit.getX() + moveX);
//			currentDrawUnit.setY(currentDrawUnit.getY() + moveY);
			currentDrawUnit.setOffset(moveX,moveY);					//新更新的方法  方便子节点一起移动
			partRefresh(currentDrawUnit, moveX, moveY);// 局部刷新
		}
	};

	
	/**
	 * 判断是否为图片文件
	 * 
	 * @param file
	 *            文件
	 * @return true 是文件 false 不是文件
	 */
	public boolean isPhotoFile(File file) {
		try {
			ImageInputStream iis = ImageIO.createImageInputStream(file);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (!iter.hasNext()) {
				return false;
			}
			iis.close();
			return true;
		} catch (IOException e) {
			//
		}
		return true;
	}

	/**
	 * 判断是否是鼠标右键点击
	 * 
	 * @param event
	 * @return
	 */
	public boolean isMouseRightKey(MouseEvent event) {
		int mods = event.getModifiers();
		// 鼠标右键
		if ((mods & InputEvent.BUTTON3_MASK) != 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据坐标获取图片 每次获取图片的同时会更新成员变量currentDrawUnit（当前选中的图片）  并画一个图片选中框显示图片范围
	 * @param x x坐标
	 * @param y Y坐标
	 * @return DrawUnit
	 */
	public DrawUnit getCurrentPhoto(int x, int y) {
		// 判断是哪张图片
		DrawUnit mDrawUnit = mPhotoManage.getCurrentDrawUnit(x, y);
		currentDrawUnit = mDrawUnit;
		showCurrent();
		//组件选择器跳动 选中当前组件
		selecteCurrentView();
		System.out.println("CanvasEventHandler --- getCurrentPhoto");
		return currentDrawUnit;
	}
	
	/**
	 * 设置当前组件选择状态
	 */
	private void selecteCurrentView(){
		if(currentDrawUnit ==null){
			return;
			}
		mMainJFrame.getmViewManage().selectCurrent(currentDrawUnit.getmView().getType());
	}
	
	/**
	 * 画矩形框标示选中的图片 ,如果有选中框则删除后移动如果没有选中框则新建  放在最上层 以免又要改处理复杂
	 */
	private void showCurrent(){
		if(currentDrawUnit==null){
			rectCase = null;
			System.out.println("showCurrent --- currentDrawUnit==null");
			return ;
		}
		if(rectCase == null){
			Date d=new Date();
			rectCase = new DrawUnitClass(mPhotoManage,d.getTime()+ "", null,currentDrawUnit.getX(),currentDrawUnit.getY(),currentDrawUnit.getWight(), currentDrawUnit.getHight(),T.ViewType.CURRENT_CASE);
			mPhotoManage.addDrawUnit(rectCase);
			mCanvas.repaint();
		}else{
			//mPhotoManage.delDrawUnit(rectCase);
			this.mPhotoManage.delPitchOn();
			partRefresh(rectCase, 0, 0);// 局部刷新
			if(currentDrawUnit ==null || rectCase==null){
				return ;	
			}
			rectCase.setX(currentDrawUnit.getX());
			rectCase.setY(currentDrawUnit.getY());
			rectCase.setWight(currentDrawUnit.getWight());
			rectCase.setHight(currentDrawUnit.getHight());
			mPhotoManage.addDrawUnit(rectCase);
			partRefresh(rectCase, 0, 0);// 局部刷新
		}
	}

	/**
	 * 获得当前DrawUnit
	 */
	public DrawUnit getCurrentDrawUnit() {
		return this.currentDrawUnit;
	}

	@Override
	public void delRectCase() {
		this.mPhotoManage.delPitchOn();
		rectCase = null;
	}
	
}

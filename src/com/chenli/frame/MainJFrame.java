/**
 * Package com.chenli.drawable
 * File Name:MainJrame.java
 * Date:2013-11-15下午7:53:58
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.frame;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import com.chenli.all.interfaces.LayoutManage;
import com.chenli.all.interfaces.Operate;
import com.chenli.all.interfaces.PhotoManage;
import com.chenli.all.interfaces.TransformToXML;
import com.chenli.drawable.MyCanvisClass;
import com.chenli.drawable.ShowPhotoManage;
import com.chenli.operate.CanvasEventHandler;
import com.chenli.operate.MenuOperate;
import com.chenli.type.T;

/**
 * 外观模式中的用户  所有大的对象在这里新建 也可以从这里获取
 * ClassName:MainJrame <mJMenuBar/>
 * date: 2013-11-15下午7:53:58 <mJMenuBar/>
 * 
 * @author zhonghong.chenli
 */
@SuppressWarnings("serial")
public class MainJFrame extends JFrame {
	private static MainJFrame mMainJFrame=new MainJFrame();
	private MainJFrame(){}
	public static MainJFrame getInstance(){
		return mMainJFrame;
	}
	
	/** 画布主体 */
	private MyCanvisClass mMyCanvis;

	/** PhotoManage对象 处理所有图片的操作,显示图片是遍历这个对象里的所有图片信息 一个工程只许在这里唯一定义一个对象 懒得写单例了 */
	private PhotoManage mPhotoManage;

	/** 顶部菜单 */
	private TopMenu mTopMenu;

	/** 操作对象 所有菜单和快捷键的操作 */
	private Operate mOperate;// 回到上一次保存的记录，保存一次则记录一次

	/** CanvasEventHandler 监听类 */
	private CanvasEventHandler mCanvasEventHandler;

	/** 主面板 */
	private JPanel mianJPanel;
	
	/** 布局显示管理器 */
	private LayoutManage mLayoutManage;
	
	/**组件选择管理器*/
	private ViewManage mViewManage;
	
	/** 底部路径选择管理器 */
	private ButtomPathManage mButtomPathManage;
	
	/** TransformToXML 对象 把所有attr转成xml */
	private TransformToXML mTransformToXML;

	/** XML Document对象 */
	private Document document;
	/**
	 * 初始化界面
	 */
	private void initJframe() {
		//对象创建
		mOperate=new MenuOperate(mMainJFrame);
		mPhotoManage = new ShowPhotoManage();
		mTopMenu=new TopMenu(mMainJFrame);
		mMyCanvis =new MyCanvisClass(mMainJFrame);
		mCanvasEventHandler = new CanvasEventHandler(mMainJFrame,mMyCanvis);
		
		//面板创建
		mianJPanel = new JPanel(null);
		mianJPanel.setSize(T.FrameParam.JFrameWith, T.FrameParam.JFrameHight);
		mianJPanel.setBackground(Color.BLACK);
		mianJPanel.add(mMyCanvis);
		mLayoutManage =new MyLayoutManage(mMainJFrame);/** layout划分*/
		mViewManage = new ViewManage(mMainJFrame);/** 组件选择区*/
		mButtomPathManage = new ButtomPathManage(mMainJFrame);/** 路径管理*/
		//initCreateDocument(); //生成
		this.setSize(T.FrameParam.JFrameWith, T.FrameParam.JFrameHight);
		this.setTitle("AndoirdUI-Tool");
		this.setLayout(null);
		this.setResizable(false);
		this.add(mianJPanel);
		initMyCanvis();
		this.setBackground(Color.blue);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		//一下是初始相应对象
		//this.setAlwaysOnTop(true);   //窗口置为最前端
		
	}	
	
	/** 初始化画布*/
	public void initMyCanvis(){
		mMyCanvis.setSize(1024, 600);
		mMyCanvis.setBackground(Color.blue);
		mMyCanvis.setBounds((T.FrameParam.JFrameWith-mMyCanvis.getWidth())/2, T.FrameParam.canvasTop, 1024, 600);
	}
	
	/**
	 * 设置画布大小和位置 即调节分辨率
	 * @param windowWith
	 * @param windowHight
	 */
	public void setFBL(int windowWith,int windowHight){
		T.FrameParam.windowWith = windowWith;
		T.FrameParam.windowHight = windowHight;
		mMyCanvis.setSize(windowWith, windowHight);
		mMyCanvis.setBounds((T.FrameParam.JFrameWith-windowWith)/2,T.FrameParam.canvasTop, windowWith, windowHight);
	}
	
	/** 初始生成document文档对象 */
	public Document initCreateDocument() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			this.document = builder.newDocument();
			return this.document;
		} catch (ParserConfigurationException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/****************************获取对象的方法***************************/	
	/**
	 * 获得画布对象
	 * @return 画布对象MyCanvisClass
	 */
	public MyCanvisClass getmMyCanvis() {
		return mMyCanvis;
	}
	/**
	 * 获取图片管理接口对象 PhotoManage
	 * @return 图片管理对象 PhotoManage
	 */
	public PhotoManage getmPhotoManage() {
		return mPhotoManage;
	}
	
	/**
	 * 获取顶部菜单对象 TopMenu
	 * @return顶部菜单对象 TopMenu
	 */
	public TopMenu getmTopMenu() {
		return mTopMenu;
	}
	/**
	 * 获得操作接口对象Operate
	 * @return 操作接口对象Operate
	 */
	public Operate getmOperate() {
		return mOperate;
	}
	
	/**
	 * 获得画布、弹出菜单等的事件监听的对象CanvasEventHandler
	 * @return 事件监听的对象CanvasEventHandler
	 */
	public CanvasEventHandler getmCanvasEventHandler() {
		return mCanvasEventHandler;
	}

	
	/**
	 * 获得主面板对象
	 * @return 主面板对象
	 */
	public JPanel getMianJPanel() {
		return mianJPanel;
	}
	public void setMianJPanel(JPanel mianJPanel) {
		this.mianJPanel = mianJPanel;
	}

	/**
	 * 设置分辨率宽
	 * @param windowWith 宽
	 */
	public  void setWindowWith(int windowWith) {
		T.FrameParam.windowWith = windowWith;
	}
	
	/**
	 * 设置分辨率高
	 * @param windowHight 高
	 */
	public  void setWindowHight(int windowHight) {
		T.FrameParam.windowHight = windowHight;
	}
	
	/**获得布局管理*/
	public LayoutManage getmLayoutManage() {
		return mLayoutManage;
	}
	
	/** 获得组件管理*/
	public ViewManage getmViewManage() {
		return mViewManage;
	}
	
	
	/**获得路径选择管理器*/
	public ButtomPathManage getmButtomPathManage() {
		return mButtomPathManage;
	}
	/**
	 * 获得文档对象
	 * @return
	 */
	public Document getDocument() {
		return document;
	}
	
	/****************************获取对象的方法end***************************/		
	
	
	
	
	
	/******主函数 启动应用 别误删*****/
	public static void main(String[] args) {
		MainJFrame.getInstance().initJframe();
	}
}

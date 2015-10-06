/**
 * Package com.chenli.drawable
 * File Name:TopMenu.java
 * Date:2013-11-17下午4:32:40
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.chenli.all.interfaces.Operate;

/**
 * ClassName:TopMenu <br/> 
 * date: 2013-11-17下午4:32:40 <br/>
 * @author zhonghong.chenli
 */
public class TopMenu {

	/** 主窗体对象*/
	private MainJFrame mJFrame;
	/** 菜单工具栏*/
	private JMenuBar mJMenuBar;
	/**菜单对象*/
	private JMenu editMenu,screenMenu;
	/** 菜单项*/
	private JMenuItem ctrlZMenuItem , ctrlYMenuItem,ctrlCMenuItem,ctrlVMenuItem,FBL1024_600,FBL800_480;
	/** 操作接口对象*/
	private Operate mOperate;
	/**构造函数*/
	public TopMenu(MainJFrame mJFrame){
		this.mJFrame=mJFrame;
		mOperate = this.mJFrame.getmOperate();
		initTopMenu();
	}
	
	/**
	 * 初始化顶部菜单样式
	 */
	private void initTopMenu() {
		mJMenuBar = new JMenuBar(); // 创建菜单工具栏
		//创建菜单对象
		editMenu = new JMenu("编辑"); // 创建JMenu菜单对象
		//新建菜单项
		ctrlZMenuItem = new JMenuItem("撤销"); // 菜单项
		ctrlYMenuItem = new JMenuItem("前进");// 菜单项
		ctrlCMenuItem = new JMenuItem("复制");// 菜单项
		ctrlVMenuItem = new JMenuItem("粘贴");// 菜单项
		
		//创建分辨率菜单对象
		screenMenu = new JMenu("分辨率选择"); // 屏幕选择
		FBL1024_600 = new JMenuItem("1024 * 600"); // 菜单项
		FBL800_480	= new JMenuItem("800 * 480"); // 菜单项
		screenMenu.add(FBL1024_600);
		screenMenu.add(FBL800_480);
		FBL1024_600.addActionListener(FBL1024_600ActionListener);//监听
		FBL800_480.addActionListener(FBL800_480ActionListener);//监听
		//菜单项加入菜单
		editMenu.add(screenMenu);
		editMenu.add(ctrlZMenuItem); // 将菜单项目添加到菜单
		editMenu.add(ctrlYMenuItem); // 将菜单项目添加到菜单
		editMenu.add(ctrlCMenuItem); // 将菜单项目添加到菜单
		editMenu.add(ctrlVMenuItem); // 将菜单项目添加到菜单
		
		
		// 将菜单增加到菜单工具栏
		mJMenuBar.add(editMenu); 
		
		
		//添加菜单工具栏
		mJFrame.setJMenuBar(mJMenuBar); 
		//事件添加
		addctrlZListener();//添加撤销事件监听
		addctrlYListener();//添加前进事件监听
		addctrlCListener();//添加复制事件监听
		addctrlVListener();//添加粘贴事件监听
	}
	
	/**
	 * 添加撤销菜单项监听事件 和 快捷键事件
	 */
	public void addctrlZListener(){
		KeyStroke keyStroke1 = KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_MASK);
		ctrlZMenuItem.setAccelerator(keyStroke1);
		ctrlZMenuItem.addActionListener(ctrlZActionListener);
	}
	
	/**
	 * 添加前进菜单项监听事件 和 快捷键事件
	 */
	public void addctrlYListener(){
		KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y,InputEvent.CTRL_MASK);
		ctrlYMenuItem.setAccelerator(keyStroke);
		ctrlYMenuItem.addActionListener(ctrlYActionListener);
	}
	
	
	/**
	 * 添加复制菜单项监听事件 和 快捷键事件
	 */
	public void addctrlCListener(){
		KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK);
		ctrlCMenuItem.setAccelerator(keyStroke);
		ctrlCMenuItem.addActionListener(ctrlCActionListener);
	}
	
	/**
	 * 添加粘贴菜单项监听事件 和 快捷键事件
	 */
	public void addctrlVListener(){
		KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_MASK);
		ctrlVMenuItem.setAccelerator(keyStroke);
		ctrlVMenuItem.addActionListener(ctrlVActionListener);
	}
	
	/**
	 * 撤销事件处理
	 */
	ActionListener	ctrlZActionListener =new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// 添加事件监听代码
			mOperate.CtrlZOperate();
		}
	};
	
	/**
	 * 前进事件处理
	 */
	ActionListener	ctrlYActionListener =new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// 添加事件监听代码
			mOperate.CtrlYOperate();
		}
	};
	
	/**
	 * 复制事件处理
	 */
	ActionListener	ctrlCActionListener =new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// 添加事件监听代码
			mOperate.ctrlCOperate();
		}
	};
	
	/**
	 * 粘贴事件处理
	 */
	ActionListener	ctrlVActionListener =new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// 添加事件监听代码
			mOperate.ctrlVOperate();
		}
	};
	
	/**
	 * 分辨率1024_800
	 */
	ActionListener	FBL1024_600ActionListener= new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			mOperate.setFBL1024_600();
		}
	};
	
	/**
	 * 分辨率800_480
	 */
	ActionListener FBL800_480ActionListener= new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			mOperate.setFBL800_480();
		}
	};
}

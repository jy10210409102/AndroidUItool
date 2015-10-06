/**
 * Package com.chenli.frame
 * File Name:ButtomPathManage.java
 * Date:2013-11-22上午9:07:10
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.frame;

import java.awt.Color;
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
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.chenli.all.interfaces.Operate;
import com.chenli.operate.Tool;
import com.chenli.type.T;

/**
 * ClassName:ButtomPathManage <br/> 
 * date: 2013-11-22上午9:07:10 <br/>
 * @author zhonghong.chenli        
 */
public class ButtomPathManage {
	/** 提示信息 */
	private final String tag = "ButtomPathManage";
	private  Tool tool =null;
	
	/** 主窗体对象 */
	MainJFrame mMainJFrame;
	/** 组件区域面板 */
	private JPanel ButtomJPanel;
	/**资源浏览lable 提示信息*/
	private JLabel  resScanfLable;
	/**资源浏览TextField 信息填写*/
	private JTextField resScanfTextField;
	/** 资源浏览按钮 */
	private JButton resScanfBut;
	
	/**xml保存lable 提示信息*/
	private JLabel  xmlSaveLable;
	/**资源浏览TextField 信息填写*/
	private JTextField xmlSaveTextField;
	/** XML保存路径浏览按钮 */
	private JButton xmlSaveScanfBut;
    /** XML生成按钮 */
	private JButton xmlCreateBut;
	/** 操作接口对象*/
	private Operate mOperate;
	
	/**
	 * 构造函数
	 * @param mMainJFrame 主窗体对象
	 */
	public ButtomPathManage(MainJFrame mMainJFrame){
		this.mMainJFrame = mMainJFrame;
		mOperate = mMainJFrame.getmOperate();
		tool = Tool.getInstance();
		initView();
	}
	
	/**
	 * 初始化
	 */
	private void initView(){
		ButtomJPanel = new JPanel(null);
		ButtomJPanel.setBackground(Color.gray);
		ButtomJPanel.setBounds(0, T.FrameParam.buttomJPanelY,T.FrameParam.JFrameWith, T.FrameParam.buttomJPanelHight);
		mMainJFrame.getMianJPanel().add(ButtomJPanel);
		
		//资源xml生成路径 lable 
		resScanfLable = new JLabel("     资源xml的保存路径（文件夹路径）：");
		resScanfLable.setBounds(0, 8, 240, 20);
		resScanfTextField = new JTextField();
		resScanfTextField.setBounds(240, 8, 250, 20);
		resScanfBut = new JButton("浏览");
		resScanfBut.setBounds(505, 8, 60, 20);
		ButtomJPanel.add(resScanfLable);
		ButtomJPanel.add(resScanfTextField);
		ButtomJPanel.add(resScanfBut);
		
		//XML保存路径
		xmlSaveLable = new JLabel("        布局xml的保存路径（包含文件名）：");
		xmlSaveLable.setBounds(565, 8, 240, 20);
		xmlSaveTextField = new JTextField();
		xmlSaveTextField.setBounds(805, 8, 250, 20);
		xmlSaveScanfBut = new JButton("浏览");
		xmlSaveScanfBut.setBounds(1065, 8, 60, 20);
		ButtomJPanel.add(xmlSaveLable);
		ButtomJPanel.add(xmlSaveTextField);
		ButtomJPanel.add(xmlSaveScanfBut);
		
		/**生成布局文件按钮*/
		xmlCreateBut = new JButton("生成布局");
		xmlCreateBut.setBounds(1125, 8, 105, 20);
		ButtomJPanel.add(xmlCreateBut);
		DndTargetListener resScanDnd = new DndTargetListener(resScanfTextField);
		DndTargetListener xmlSaveDnd = new DndTargetListener(xmlSaveTextField);
		
		// 设置拖拽事件
		resScanfTextField.setDropTarget(new DropTarget(resScanfTextField,DnDConstants.ACTION_REFERENCE, resScanDnd, true));
		resScanfTextField.setDropTarget(new DropTarget(xmlSaveTextField,DnDConstants.ACTION_REFERENCE, resScanDnd, true));
		xmlCreateBut.addActionListener(xmlCreateButActionListener);
	
	}
	
	/**
	 * 返回特效路径地址
	 * @return 特效路径地址
	 */
	public String getEffecXmlPath(){
		return resScanfTextField.getText();
	}
	
	/**
	 * 返回布局xml保存地址
	 * @return  布局xml保存地址
	 */
	public  String getLayoutXmlPath(){
		return xmlSaveTextField.getText();
	}
	
	/**
	 * 生成xml键监听
	 */
	ActionListener xmlCreateButActionListener=	new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			mOperate.CreateXml();
		}
	};
	
	
	  
	   /**
	    *   拖拽监听类 
	    * ClassName:DndTargetListener <br/> 
	    * date: 2013-11-21下午1:50:57 <br/>
	    * @author zhonghong.chenli
	    */
		private class DndTargetListener implements DropTargetListener {
			JTextField mJTextField =null;
			public DndTargetListener (JTextField mJTextField){
				this.mJTextField = mJTextField;
			}
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
								String path = f.getParent();
								mJTextField.setText(path);
								T.Path.EffecXmlPath = path;		//设置路径
								return;
							} else if (f.exists() && f.isDirectory()) {// 如果是文件夹
								String path =f.getPath();
								mJTextField.setText(path);
								T.Path.EffecXmlPath = path;		//设置路径
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
	    
	
}

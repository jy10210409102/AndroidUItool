/**
 * Package com.chenli.attrjfream
 * File Name:SeekBarAttrJF.java
 * Date:2013-11-23上午10:29:27
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.attrjfream;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.chenli.all.interfaces.DrawUnit;
import com.chenli.dao.SeekBar;
import com.chenli.frame.MainJFrame;
import com.chenli.operate.Tool;

/**
 * ClassName:SeekBarAttrJF <br/> 
 * date: 2013-11-23上午10:29:27 <br/>
 * @author zhonghong.chenli        
 */
public class SeekBarAttrJF extends JFrame {
	private final String tag = "SeekBarAttrJF";
	private static SeekBarAttrJF  JseekBar; 
	private static DrawUnit mDrawUnit = null;
	private  Tool tool =null;
	private static SeekBar seekbar ;
	private static String photoUrl ;				//记录第一次进的图片 用来判断图片是否改变 然后判断是否改变宽高
	
	public static  SeekBarAttrJF getSeekBarAttrInstance(DrawUnit mDrawUnit){
		SeekBarAttrJF.mDrawUnit = mDrawUnit;
		SeekBarAttrJF.seekbar = (SeekBar) mDrawUnit.getmView();
		if(JseekBar == null ){
			 try {
		            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
		                if ("Nimbus".equals(info.getName())) {
		                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
		                    break;
		                }
		            }
		        } catch (Exception ex) {
		        } 
			 JseekBar = new SeekBarAttrJF();
		}
		SeekBarAttrJF.photoUrl = mDrawUnit.getUrl();
		JseekBar.initView(mDrawUnit);
		JseekBar.setVisible(true);
		return JseekBar;
	}

	/**
	 * 构造函数
	 */
	private SeekBarAttrJF(){
		initComponents();
		this.setTitle("Seekbar");
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);   //窗口置为最前端
        
        tool = Tool.getInstance();
        DndTargetListener thumbPhotoDnd = new DndTargetListener(thumbPhoto);
        DndTargetListener emptyPhotoDnd = new DndTargetListener(emptyPhoto);
        DndTargetListener fullPhotoDnd = new DndTargetListener(fullPhoto);
        //设置拖拽事件
        thumbPhoto.setDropTarget(new DropTarget(thumbPhoto,
				DnDConstants.ACTION_REFERENCE, thumbPhotoDnd, true));
        emptyPhoto.setDropTarget(new DropTarget(emptyPhoto,
				DnDConstants.ACTION_REFERENCE, emptyPhotoDnd, true));
        fullPhoto.setDropTarget(new DropTarget(fullPhoto,
				DnDConstants.ACTION_REFERENCE, fullPhotoDnd, true));
	}
	/**
	 * @param 初始化组件
	 */
	private void initView(DrawUnit mDrawUnit2) {
		viewX.setText(mDrawUnit.getX()+"");
		viewY.setText(mDrawUnit.getY()+"");
		viewWide.setText(mDrawUnit.getWight()+"");
	    viewHight.setText(mDrawUnit.getHight()+"");
	    viewID.setText(mDrawUnit.getmView().getId());
	    
	    maxData.setText(seekbar.getMax()+"");			//进度条最大值
	    currentData.setText(seekbar.getProgress()+"");	//进度条当前值
	    thumbPhoto.setText(seekbar.getThumbPhoto());	//滑块图片
	    emptyPhoto.setText(mDrawUnit.getUrl());			//空图片
	    fullPhoto.setText(seekbar.getFullPhoto());		//满值图片
	    
	    Record.x =mDrawUnit.getX();
	    Record.y = mDrawUnit.getY();
	    Record.wide = mDrawUnit.getWight();
	    Record.hight = mDrawUnit.getHight();
		
	}
	
	
	/**
	 * 记录初始mDrawUnit对象 以便刷新画布以免残留
	 * ClassName:Record <br/> 
	 * date: 2013-11-20下午9:59:48 <br/>
	 * @author zhonghong.chenli
	 */
	static class Record {
		static int x;
		static int y;
		static int wide;
		static int hight;
	}
	
	/***
	 * 保存按键监听
	 * @param evt ActionEvent对象
	 */
	private void saveButActionPerformed(java.awt.event.ActionEvent evt) {
		mDrawUnit.setX(Integer.parseInt(viewX.getText()));					//x
    	mDrawUnit.setY(Integer.parseInt(viewY.getText()));					//y
    	mDrawUnit.setWight(Integer.parseInt(viewWide.getText()));			//wide
    	mDrawUnit.setHight(Integer.parseInt(viewHight.getText()));			//hight
    	mDrawUnit.setUrl(emptyPhoto.getText());						//url
    	
    	//组件属性
    	seekbar.setId(viewID.getText());									//id
    	seekbar.setThumbPhoto(thumbPhoto.getText());						//滑块图片
    	seekbar.setEmptyPhoto(emptyPhoto.getText())	;						//空值图片
    	seekbar.setFullPhoto(fullPhoto.getText());							//满值图片
    	seekbar.setMax(Integer.parseInt(maxData.getText()));				//进度条最大值
    	seekbar.setProgress(Integer.parseInt(currentData.getText()));		//进度条初始值
    																		//进度条风格默认为水平 ，如有其他风格在另行设置
    	if(!SeekBarAttrJF.photoUrl.equals(emptyPhoto.getText())){
    		updateDispressedPhoto(emptyPhoto.getText());			//更新宽高
    	}
    	mDrawUnit.refreshMyself();																		//刷新自己
    	MainJFrame.getInstance().getmMyCanvis().repaint(Record.x, Record.y, Record.wide, Record.hight);	//刷新上一个图片
    	JseekBar.setVisible(false);
	}
	
    private void dispressedPhotoButActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        // TODO add your handling code here:
    }                                                  

    private void pressedPhotoButActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void enablePhotoButActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    
    /**
     * 更新组件属性
     * @param path
     */
    private void updateDispressedPhoto(String path ){
			int[] size = tool.getPhotoSize(path);
			if(size==null){
				return ;
			}
			mDrawUnit.setWight(size[0]);
			mDrawUnit.setHight(size[1]);
    }
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
 							if (!tool.isPhotoFile(f)) {		//是否是文件图片
 								return;
 							}
 							String path = f.getAbsolutePath();
 							if(mJTextField.equals(emptyPhoto)){	
 								int[] size = tool.getPhotoSize(path);
 								if(size == null){
 									return ;
 								}
 								viewWide.setText(size[0]+"");
 								viewHight.setText(size[1]+"");
 							}
 							mJTextField.setText(path);
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
                       

	
    
    /** 界面 */
       @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        viewWide = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        viewY = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        viewHight = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        emptyPhoto = new javax.swing.JTextField();
        thumbPhoto = new javax.swing.JTextField();
        dispressedPhotoBut = new javax.swing.JButton();
        fullPhoto = new javax.swing.JTextField();
        pressedPhotoBut = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        enablePhotoBut = new javax.swing.JButton();
        viewX = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        viewID = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        maxData = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        currentData = new javax.swing.JTextField();
        saveBut = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel4.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel4.setText("组件高：");

        jLabel3.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel3.setText("组件宽：");

        jLabel2.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel2.setText("Y坐标：");

        jLabel7.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel7.setText("空值图片：");

        jLabel8.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel8.setText("满值图片：");

        jLabel6.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel6.setText("滑块图片：");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 153, 153), new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("宋体", 1, 24)); // NOI18N
        jLabel5.setText("Seekbar");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel5)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        dispressedPhotoBut.setText("浏览");
        dispressedPhotoBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispressedPhotoButActionPerformed(evt);
            }
        });

        pressedPhotoBut.setText("浏览");
        pressedPhotoBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pressedPhotoButActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel1.setText("X坐标：");

        enablePhotoBut.setText("浏览");
        enablePhotoBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enablePhotoButActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel11.setText("组件id:");

        viewID.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel9.setText("风    格：");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "水平进度条" }));

        jLabel10.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel10.setText("最大值：");

        jLabel12.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel12.setText("当前值：");

        saveBut.setFont(new java.awt.Font("幼圆", 1, 18)); // NOI18N
        saveBut.setText("保存");
        saveBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveBut))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(viewID, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel1))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(maxData, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(26, 26, 26)
                                            .addComponent(jLabel12)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(currentData, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(242, 242, 242))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(viewX)
                                                .addComponent(viewWide, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(26, 26, 26)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel2)
                                                .addComponent(jLabel4))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(viewY)
                                                .addComponent(viewHight, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(9, 9, 9))))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel8))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(fullPhoto)
                                            .addGap(18, 18, 18)
                                            .addComponent(enablePhotoBut))
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(thumbPhoto)
                                                .addComponent(emptyPhoto))
                                            .addGap(18, 18, 18)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(pressedPhotoBut, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(dispressedPhotoBut, javax.swing.GroupLayout.Alignment.TRAILING)))))))))
                .addGap(36, 36, 36))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel3)
                        .addComponent(viewWide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(viewID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                        .addComponent(jLabel1)
                                        .addComponent(viewX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                            .addComponent(jLabel2)
                                            .addComponent(viewY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                            .addComponent(jLabel4)
                                            .addComponent(viewHight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel10)
                        .addComponent(maxData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel12)
                        .addComponent(currentData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(thumbPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dispressedPhotoBut))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(emptyPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pressedPhotoBut))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(fullPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(enablePhotoBut))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveBut)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    // Variables declaration - do not modify                     
    private javax.swing.JTextField currentData;
    private javax.swing.JButton dispressedPhotoBut;
    private javax.swing.JTextField emptyPhoto;
    private javax.swing.JButton enablePhotoBut;
    private javax.swing.JTextField fullPhoto;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField maxData;
    private javax.swing.JButton pressedPhotoBut;
    private javax.swing.JButton saveBut;
    private javax.swing.JTextField thumbPhoto;
    private javax.swing.JTextField viewHight;
    private javax.swing.JTextField viewID;
    private javax.swing.JTextField viewWide;
    private javax.swing.JTextField viewX;
    private javax.swing.JTextField viewY;
    // End of variables declaration                   

}

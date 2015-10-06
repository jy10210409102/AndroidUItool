/**
 * Package com.chenli.attrjfream
 * File Name:ListViewAttrJF.java
 * Date:2013-11-23下午2:59:26
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
import com.chenli.dao.ListView;
import com.chenli.frame.MainJFrame;
import com.chenli.operate.Tool;

/**
 * ListView 属性设置窗口
 * ClassName:ListViewAttrJF <br/> 
 * date: 2013-11-23下午2:59:26 <br/>
 * @author zhonghong.chenli        
 */
public class ListViewAttrJF extends JFrame {
	
	private final String tag = "ListViewAttrJF";
	private static ListViewAttrJF  JListView; 
	private static DrawUnit mDrawUnit = null;
	private  Tool tool =null;
	private static ListView listView ;
	private static String photoUrl ;				//记录第一次进的图片 用来判断图片是否改变 然后判断是否改变宽高
	
	/**得到单例对象并显示窗体*/
	public static  ListViewAttrJF getListViewAttrInstance(DrawUnit mDrawUnit){
		ListViewAttrJF.mDrawUnit = mDrawUnit;
		ListViewAttrJF.listView = (ListView) mDrawUnit.getmView();
		if(JListView == null ){
			 try {
		            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
		                if ("Nimbus".equals(info.getName())) {
		                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
		                    break;
		                }
		            }
		        } catch (Exception ex) {
		        } 
			 JListView = new ListViewAttrJF();
		}
		ListViewAttrJF.photoUrl = mDrawUnit.getUrl();
		JListView.initView(mDrawUnit);
		JListView.setVisible(true);
		return JListView;
	}
	
	/**
	 * @param 初始化组件
	 */
	private void initView(DrawUnit mDrawUnit) {
		viewX.setText(mDrawUnit.getX()+"");
		viewY.setText(mDrawUnit.getY()+"");
		viewWide.setText(mDrawUnit.getWight()+"");
	    viewHight.setText(mDrawUnit.getHight()+"");
	    viewID.setText(mDrawUnit.getmView().getId());
	    
	    backgroud.setText(mDrawUnit.getUrl());			//背景图片
	    thumbPhoto.setText(listView.getScorllThumb());	//滑块图片
	    scrollBGPhoto.setText(listView.getScorllBackground());//滑块背景图
	    if(!listView.isScorllShow()){		// 滚动条是否隐藏
	    	jCheckBox1.setSelected(true);
	    }else{
	    	jCheckBox1.setSelected(false);
	    }
	    dividerPhoto.setText(listView.getDivideColorOrPhoto());	//分隔图片或颜色
	    dividerHight.setText(listView.getDivideHight());		//分隔高度
	    
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
	
	
	/**
	 * 构造函数
	 */
    public ListViewAttrJF() {
        initComponents();
        this.setTitle("ListView");
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true); 		  //窗口置为最前端
        
        this.tool = Tool.getInstance();
        DndTargetListener backgroudDnd = new DndTargetListener(backgroud);
        DndTargetListener thumbPhotoDnd = new DndTargetListener(thumbPhoto);
        DndTargetListener scrollBGPhotoDnd = new DndTargetListener(scrollBGPhoto);
        
        backgroud.setDropTarget(new DropTarget(backgroud,
				DnDConstants.ACTION_REFERENCE, backgroudDnd, true));
        thumbPhoto.setDropTarget(new DropTarget(thumbPhoto,
				DnDConstants.ACTION_REFERENCE, thumbPhotoDnd, true));
        scrollBGPhoto.setDropTarget(new DropTarget(scrollBGPhoto,
				DnDConstants.ACTION_REFERENCE, scrollBGPhotoDnd, true));
    }
    
    /** 保存按钮 */
    private void saveButActionPerformed(java.awt.event.ActionEvent evt) {                                        
    	mDrawUnit.setX(Integer.parseInt(viewX.getText()));					//x
    	mDrawUnit.setY(Integer.parseInt(viewY.getText()));					//y
    	mDrawUnit.setWight(Integer.parseInt(viewWide.getText()));			//wide
    	mDrawUnit.setHight(Integer.parseInt(viewHight.getText()));			//hight
    	mDrawUnit.setUrl(backgroud.getText());								//url
    	
    	//组件属性
    	listView.setId(viewID.getText());									//id
    	listView.setBackGround(backgroud.getText());						//默认背景图片
    	listView.setScorllThumb(thumbPhoto.getText());						//滑块图片
    	listView.setScorllBackground(scrollBGPhoto.getText());				//滑块背景图片
    	listView.setScorllShow(jCheckBox1.isSelected());					//滑块条是否隐藏
    	listView.setDivideColorOrPhoto(dividerPhoto.getText());				//分隔图片或颜色						
    	listView.setDivideHight(dividerHight.getText());					//分隔高度
    	if(!ListViewAttrJF.photoUrl.equals(backgroud.getText())){
    		updateDispressedPhoto(backgroud.getText());						//更新宽高
    	}
    	mDrawUnit.refreshMyself();																		//刷新自己
    	MainJFrame.getInstance().getmMyCanvis().repaint(Record.x, Record.y, Record.wide, Record.hight);	//刷新上一个图片
    	JListView.setVisible(false);
    	
    }   
    
    /** 是否显示滚动条标示*/
    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt){
    }
    
    private void dispressedPhotoButActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        // TODO add your handling code here:
    }                                                  

    private void enablePhotoButActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void pressedPhotoButActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void dispressedPhotoBut1ActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        // TODO add your handling code here:
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
 							if(mJTextField.equals(backgroud)){	
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
	
    
       @SuppressWarnings("unchecked")
      // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        viewWide = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        viewY = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        viewHight = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        backgroud = new javax.swing.JTextField();
        dispressedPhotoBut = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        viewX = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        viewID = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        enablePhotoBut = new javax.swing.JButton();
        pressedPhotoBut = new javax.swing.JButton();
        scrollBGPhoto = new javax.swing.JTextField();
        thumbPhoto = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        dividerPhoto = new javax.swing.JTextField();
        dispressedPhotoBut1 = new javax.swing.JButton();
        dividerHight = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        saveBut = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel4.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel4.setText("组件高：");

        jLabel3.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel3.setText("组件宽：");

        jLabel2.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel2.setText("Y坐标：");

        jLabel6.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel6.setText("背景图片：");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 153, 153), new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("宋体", 1, 24)); // NOI18N
        jLabel5.setText("ListView");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(31, 31, 31))
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

        jLabel1.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel1.setText("X坐标：");

        jLabel11.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel11.setText("组件id:");

        viewID.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

        jLabel7.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel7.setText("滑块图片：");

        jLabel8.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel8.setText("滑块背景图片：");

        enablePhotoBut.setText("浏览");
        enablePhotoBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enablePhotoButActionPerformed(evt);
            }
        });

        pressedPhotoBut.setText("浏览");
        pressedPhotoBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pressedPhotoButActionPerformed(evt);
            }
        });

        jCheckBox1.setFont(new java.awt.Font("宋体", 0, 14)); // NOI18N
        jCheckBox1.setText("隐藏滚动条");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        dispressedPhotoBut1.setText("浏览");
        dispressedPhotoBut1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispressedPhotoBut1ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel9.setText("分隔高度：");

        jLabel10.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
        jLabel10.setText("分隔图片：");

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
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(backgroud, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(dispressedPhotoBut))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel11)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(viewID, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel1)
                                                    .addComponent(jLabel3))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                                                    .addComponent(viewHight, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGap(40, 40, 40)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(15, 15, 15))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(34, 34, 34)
                                        .addComponent(jLabel7))
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(scrollBGPhoto)
                                    .addComponent(thumbPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pressedPhotoBut)
                                    .addComponent(enablePhotoBut)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(dividerPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(dispressedPhotoBut1))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(dividerHight, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(jCheckBox1)))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(saveBut)
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                .addComponent(jLabel3)
                                .addComponent(viewWide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(viewID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                            .addComponent(viewHight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(backgroud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dispressedPhotoBut))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(thumbPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pressedPhotoBut))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(scrollBGPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(enablePhotoBut))
                .addGap(27, 27, 27)
                .addComponent(jCheckBox1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(dividerPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dispressedPhotoBut1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(dividerHight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveBut)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>     
    // Variables declaration - do not modify                     
    private javax.swing.JTextField backgroud;
    private javax.swing.JButton dispressedPhotoBut;
    private javax.swing.JButton dispressedPhotoBut1;
    private javax.swing.JTextField dividerHight;
    private javax.swing.JTextField dividerPhoto;
    private javax.swing.JButton enablePhotoBut;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton pressedPhotoBut;
    private javax.swing.JButton saveBut;
    private javax.swing.JTextField scrollBGPhoto;
    private javax.swing.JTextField thumbPhoto;
    private javax.swing.JTextField viewHight;
    private javax.swing.JTextField viewID;
    private javax.swing.JTextField viewWide;
    private javax.swing.JTextField viewX;
    private javax.swing.JTextField viewY;
    // End of variables declaration                   
}


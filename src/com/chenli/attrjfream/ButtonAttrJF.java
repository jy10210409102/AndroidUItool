/**
 * Package com.chenli.attrjfream.button
 * File Name:ButtonAttrJF.java
 * Date:2013-11-20下午8:56:45
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

import javax.swing.JCheckBox;
import javax.swing.JTextField;

import com.chenli.all.interfaces.DrawUnit;
import com.chenli.dao.Button;
import com.chenli.frame.MainJFrame;
import com.chenli.operate.Tool;

/**
 * ClassName:ButtonAttrJF <br/> 
 * date: 2013-11-20下午8:56:45 <br/>
 * @author zhonghong.chenli        
 */
@SuppressWarnings("serial")
public class ButtonAttrJF extends javax.swing.JFrame {

	private final String tag = "ButtonAttrJF";
	private static ButtonAttrJF  button ; 
	private static DrawUnit mDrawUnit = null;
	private  Tool tool =null;
	private static Button but ;
	private static String photoUrl ;		//记录第一次进的图片 用来判断图片是否改变 然后判断是否改变宽高
	
	public static  ButtonAttrJF getButtonAttrInstance(DrawUnit mDrawUnit){
		ButtonAttrJF.mDrawUnit = mDrawUnit;
		ButtonAttrJF.but = (Button) mDrawUnit.getmView();
		if(button == null ){
			 try {
		            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
		                if ("Nimbus".equals(info.getName())) {
		                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
		                    break;
		                }
		            }
		        } catch (Exception ex) {
		        } 
			 button = new ButtonAttrJF();
		}
		ButtonAttrJF.photoUrl = mDrawUnit.getUrl();
		button.initView(mDrawUnit);
		button.setVisible(true);
		return button;
	}
	
	/**
	 * 初始化窗体
	 * @param mDrawUnit DrawUnit对象
	 */
	private void initView(DrawUnit mDrawUnit){
		Button butView = (Button) mDrawUnit.getmView();
		viewX.setText(mDrawUnit.getX()+"");
		viewY.setText(mDrawUnit.getY()+"");
		viewWide.setText(mDrawUnit.getWight()+"");
	    viewHight.setText(mDrawUnit.getHight()+"");
	    dispressedPhoto.setText(mDrawUnit.getUrl());
	    viewID.setText(mDrawUnit.getmView().getId());
	    pressedPhoto.setText(butView.getPressedPhoto());//按下图片
	    enablePhoto.setText(butView.getEnablePhoto());//无效图片
	    
	    Record.x =mDrawUnit.getX();
	    Record.y = mDrawUnit.getY();
	    Record.wide = mDrawUnit.getWight();
	    Record.hight = mDrawUnit.getHight();
	    
	    if(but.isOpentext){
	    	textShowCheck.setSelected(true);
	    	textAttrBut.setEnabled(true);
	    }else{
	    	textShowCheck.setSelected(false);
	    	textAttrBut.setEnabled(false);
	    }
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
     * 构造方法
     */
	private ButtonAttrJF() {
        initComponents();
        this.setTitle("Button");
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);   //窗口置为最前端
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        tool = Tool.getInstance();
        DndTargetListener dispressedDnd = new DndTargetListener(dispressedPhoto);
        DndTargetListener pressedDnd = new DndTargetListener(pressedPhoto);
        DndTargetListener enableDnd = new DndTargetListener(enablePhoto);
        //设置拖拽事件
        dispressedPhoto.setDropTarget(new DropTarget(dispressedPhoto,
				DnDConstants.ACTION_REFERENCE, dispressedDnd, true));
        pressedPhoto.setDropTarget(new DropTarget(pressedPhoto,
				DnDConstants.ACTION_REFERENCE, pressedDnd, true));
       enablePhoto.setDropTarget(new DropTarget(enablePhoto,
				DnDConstants.ACTION_REFERENCE, enableDnd, true));
       
    }
	
	
	/**文字属性设置监听 打开文本属性框*/
    private void textAttrButActionPerformed(java.awt.event.ActionEvent evt) {   
    	ButtonTextJF mButtonTextJF = ButtonTextJF.getButtonTextJFInstance(this,mDrawUnit);
    	
    }    
    
    /**保存按键监听*/
    private void saveButActionPerformed(java.awt.event.ActionEvent evt) {
    	mDrawUnit.setX(Integer.parseInt(viewX.getText()));					//x
    	mDrawUnit.setY(Integer.parseInt(viewY.getText()));					//y
    	mDrawUnit.setWight(Integer.parseInt(viewWide.getText()));			//wide
    	mDrawUnit.setHight(Integer.parseInt(viewHight.getText()));			//hight
    	mDrawUnit.setUrl(dispressedPhoto.getText());						//url
    	
    	//组件属性
    	Button butView = (Button) mDrawUnit.getmView();
    	butView.setId(viewID.getText());									//id
    	butView.setDispressedPhoto(dispressedPhoto.getText());				//默认图片
    	butView.setPressedPhoto(pressedPhoto.getText());					//按下图片
    	butView.setEnablePhoto(enablePhoto.getText());						//无效图片
    	butView.setAlpha(Float.parseFloat(viewAlpha.getText()));			//alpha
    	butView.setOpentext(textShowCheck.isSelected());					//是否开启文字							
    	
    	if(!ButtonAttrJF.photoUrl.equals(dispressedPhoto.getText())){
    		updateDispressedPhoto(dispressedPhoto.getText());			//更新宽高
    	}
    	mDrawUnit.refreshMyself();																		//刷新自己
    	MainJFrame.getInstance().getmMyCanvis().repaint(Record.x, Record.y, Record.wide, Record.hight);	//刷新上一个图片
    	button.setVisible(false);
    	
    	
    }                                       
    /**普通图片路径浏览*/
    private void dispressedPhotoButActionPerformed(java.awt.event.ActionEvent evt) {                                                   
    }                                                  
    /**按下图片路径浏览*/
    private void pressedPhotoButActionPerformed(java.awt.event.ActionEvent evt) {                                                
    }                                               
    /**无效图片路径浏览*/
    private void enablePhotoButActionPerformed(java.awt.event.ActionEvent evt) {                                               
    }                                              
    /**是否显示文字监听*/
	private void textShowCheckActionPerformed(java.awt.event.ActionEvent evt) {
		if (textShowCheck.isSelected()) {
			textAttrBut.setEnabled(true);
		} else {
			textAttrBut.setEnabled(false);
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
	
    
    
    
    /**
     * 返回文本开启框
     * @return  JCheckBox 对象
     */
    public JCheckBox getTextShowCheck(){
    	return textShowCheck;
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
							if(mJTextField.equals(dispressedPhoto)){	
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
    
    
    /**布局*/
	 @SuppressWarnings("unchecked")
	    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
	 
   private void initComponents() {

	        jDialog1 = new javax.swing.JDialog();
	        jLabel1 = new javax.swing.JLabel();
	        viewX = new javax.swing.JTextField();
	        jLabel2 = new javax.swing.JLabel();
	        viewY = new javax.swing.JTextField();
	        jLabel3 = new javax.swing.JLabel();
	        viewWide = new javax.swing.JTextField();
	        jLabel4 = new javax.swing.JLabel();
	        viewHight = new javax.swing.JTextField();
	        jPanel1 = new javax.swing.JPanel();
	        jLabel5 = new javax.swing.JLabel();
	        jLabel6 = new javax.swing.JLabel();
	        jLabel7 = new javax.swing.JLabel();
	        jLabel8 = new javax.swing.JLabel();
	        dispressedPhoto = new javax.swing.JTextField();
	        pressedPhoto = new javax.swing.JTextField();
	        enablePhoto = new javax.swing.JTextField();
	        dispressedPhotoBut = new javax.swing.JButton();
	        pressedPhotoBut = new javax.swing.JButton();
	        enablePhotoBut = new javax.swing.JButton();
	        jLabel9 = new javax.swing.JLabel();
	        viewAlpha = new javax.swing.JTextField();
	        viewAlpha.setText("1");
	        jLabel10 = new javax.swing.JLabel();
	        jLabel11 = new javax.swing.JLabel();
	        viewID = new javax.swing.JTextField();
	        textShowCheck = new javax.swing.JCheckBox();
	        textAttrBut = new javax.swing.JButton();
	        saveBut = new javax.swing.JButton();

	        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
	        jDialog1.getContentPane().setLayout(jDialog1Layout);
	        jDialog1Layout.setHorizontalGroup(
	            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGap(0, 400, Short.MAX_VALUE)
	        );
	        jDialog1Layout.setVerticalGroup(
	            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGap(0, 300, Short.MAX_VALUE)
	        );

	        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

	        jLabel1.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
	        jLabel1.setText("X坐标：");

	        jLabel2.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
	        jLabel2.setText("Y坐标：");

	        jLabel3.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
	        jLabel3.setText("组件宽：");

	        jLabel4.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
	        jLabel4.setText("组件高：");

	        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 153, 153), new java.awt.Color(0, 0, 0)));

	        jLabel5.setFont(new java.awt.Font("宋体", 1, 24)); // NOI18N
	        jLabel5.setText("Button");
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

	        jLabel6.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
	        jLabel6.setText("按键图片：");

	        jLabel7.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
	        jLabel7.setText("按下图片：");

	        jLabel8.setFont(new java.awt.Font("幼圆", 0, 15)); // NOI18N
	        jLabel8.setText("无效图片：");

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

	        enablePhotoBut.setText("浏览");
	        enablePhotoBut.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                enablePhotoButActionPerformed(evt);
	            }
	        });

	        jLabel9.setFont(new java.awt.Font("幼圆", 0, 13)); // NOI18N
	        jLabel9.setText("透明度：");

	        jLabel10.setFont(new java.awt.Font("宋体", 0, 10)); // NOI18N
	        jLabel10.setText("范围：0~1");

	        jLabel11.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
	        jLabel11.setText("组件id:");

	        viewID.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

	        textShowCheck.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
	        textShowCheck.setText("支持文字显示");
	        textShowCheck.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                textShowCheckActionPerformed(evt);
	            }
	        });

	        textAttrBut.setFont(new java.awt.Font("幼圆", 0, 16)); // NOI18N
	        textAttrBut.setText("文字属性");
	        textAttrBut.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                textAttrButActionPerformed(evt);
	            }
	        });

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
	                .addGap(45, 45, 45)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
	                    .addGroup(layout.createSequentialGroup()
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                            .addComponent(jLabel6)
	                            .addComponent(jLabel7)
	                            .addComponent(jLabel8)
	                            .addComponent(jLabel9))
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                            .addGroup(layout.createSequentialGroup()
	                                .addComponent(enablePhoto)
	                                .addGap(18, 18, 18)
	                                .addComponent(enablePhotoBut))
	                            .addGroup(layout.createSequentialGroup()
	                                .addComponent(pressedPhoto)
	                                .addGap(18, 18, 18)
	                                .addComponent(pressedPhotoBut))
	                            .addGroup(layout.createSequentialGroup()
	                                .addComponent(dispressedPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
	                                .addGap(18, 18, 18)
	                                .addComponent(dispressedPhotoBut))
	                            .addGroup(layout.createSequentialGroup()
	                                .addComponent(viewAlpha, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
	                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                                .addComponent(jLabel10)
	                                .addGap(53, 53, 53)
	                                .addComponent(textShowCheck)
	                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                                .addComponent(textAttrBut))))
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
	                                    .addComponent(viewX, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
	                                    .addComponent(viewWide))
	                                .addGap(26, 26, 26)
	                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                                    .addComponent(jLabel2)
	                                    .addComponent(jLabel4))
	                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                                    .addComponent(viewY, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
	                                    .addComponent(viewHight))))
	                        .addGap(55, 55, 55)
	                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                .addContainerGap(498, Short.MAX_VALUE)
	                .addComponent(saveBut)
	                .addGap(21, 21, 21))
	        );

	        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel6, jLabel7, jLabel8});

	        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {dispressedPhoto, enablePhoto, pressedPhoto});

	        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {viewHight, viewWide, viewX, viewY});

	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap(27, Short.MAX_VALUE)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
	                        .addComponent(jLabel3)
	                        .addComponent(viewWide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                        .addGroup(layout.createSequentialGroup()
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
	                .addGap(27, 27, 27)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel6)
	                    .addComponent(dispressedPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(dispressedPhotoBut))
	                .addGap(30, 30, 30)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel7)
	                    .addComponent(pressedPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(pressedPhotoBut))
	                .addGap(23, 23, 23)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel8)
	                    .addComponent(enablePhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(enablePhotoBut))
	                .addGap(42, 42, 42)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel9)
	                    .addComponent(viewAlpha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jLabel10)
	                    .addComponent(textShowCheck)
	                    .addComponent(textAttrBut))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(saveBut)
	                .addGap(17, 17, 17))
	        );

	        pack();
	    }// </editor-fold>                        
                                       

//	    /**
//	     * @param args the command line arguments
//	     */
//	    public static void main(String args[]) {
//	    	ButtonAttrJF.getButtonAttrInstance(null);
//	    }
	    // Variables declaration - do not modify                     

	    private javax.swing.JDialog jDialog1;
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
		private javax.swing.JButton dispressedPhotoBut;
		private javax.swing.JButton enablePhotoBut;
		private javax.swing.JButton pressedPhotoBut;
	
		private javax.swing.JTextField dispressedPhoto;
		private javax.swing.JTextField enablePhoto;
		private javax.swing.JTextField pressedPhoto;
		private javax.swing.JButton saveBut;
		private javax.swing.JButton textAttrBut;
		private javax.swing.JCheckBox textShowCheck;
		private javax.swing.JTextField viewAlpha;
		private javax.swing.JTextField viewHight;
		private javax.swing.JTextField viewID;
		private javax.swing.JTextField viewWide;
		private javax.swing.JTextField viewX;
		private javax.swing.JTextField viewY;
	    // End of variables declaration                   
	}

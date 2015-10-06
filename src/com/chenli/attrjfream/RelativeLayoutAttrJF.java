/**
 * Package com.chenli.attrjfream
 * File Name:RelativeLayoutAttrJF.java
 * Date:2013-11-21下午6:59:26
 * Copyright (c) 2013, jy10210409102@163.com All Rights Reserved.
 */
package com.chenli.attrjfream;

import com.chenli.all.interfaces.DrawUnit;
import com.chenli.attrjfream.ButtonAttrJF.Record;
import com.chenli.dao.Button;
import com.chenli.dao.RelativeLayout;
import com.chenli.frame.MainJFrame;
import com.chenli.operate.Tool;

/**
 * ClassName:RelativeLayoutAttrJF <br/> 
 * date: 2013-11-21下午6:59:26 <br/>
 * @author zhonghong.chenli        
 */
public class RelativeLayoutAttrJF extends javax.swing.JFrame {

	private final String tag = "RelativeLayoutAttrJF";
	private static RelativeLayoutAttrJF  mRelativeLayoutAttrJF ; 
	private static DrawUnit mDrawUnit = null;
	private  Tool tool =null;
	private static RelativeLayout view;
	private static String photoUrl ;		//记录第一次进的图片 用来判断图片是否改变 然后判断是否改变宽高
	
	public static RelativeLayoutAttrJF getRelativeLayoutJFInstance(DrawUnit mDrawUnit) {
		mRelativeLayoutAttrJF.mDrawUnit = mDrawUnit;
		view = (RelativeLayout) mDrawUnit.getmView();
		if (mRelativeLayoutAttrJF == null) {
			try {
				for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
					if ("Nimbus".equals(info.getName())) {
						javax.swing.UIManager.setLookAndFeel(info.getClassName());
						break;
					}
				}
			} catch (Exception ex) {
				System.out.println("RelativeLayoutAttrJF 异常！！！！");
			}
			mRelativeLayoutAttrJF = new RelativeLayoutAttrJF();
		}
		mRelativeLayoutAttrJF.updata(mDrawUnit);
		mRelativeLayoutAttrJF.setVisible(true);
		RelativeLayoutAttrJF.photoUrl = mDrawUnit.getUrl();
		return mRelativeLayoutAttrJF;
	}
	
	/**
	 * 更新数据
	 * @param mDrawUnit DrawUnit对象
	 */
	private void updata(DrawUnit mDrawUnit){
		viewX.setText(mDrawUnit.getX()+"");
		viewY.setText(mDrawUnit.getY()+"");
		viewWide.setText(mDrawUnit.getWight()+"");
	    viewHight.setText(mDrawUnit.getHight()+"");
	    dispressedPhoto.setText(mDrawUnit.getUrl());
	    viewID.setText(mDrawUnit.getmView().getId());
	    
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
    private RelativeLayoutAttrJF() {
        initComponents();
        this.setTitle("RelativeLayout");
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);   //窗口置为最前端
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**背景图片浏览监听*/
    private void dispressedPhotoButActionPerformed(java.awt.event.ActionEvent evt) {   
    	
    }                                                  
    
    /**保存监听*/
    private void saveButActionPerformed(java.awt.event.ActionEvent evt) {   
    	mDrawUnit.setX(Integer.parseInt(viewX.getText()));					//x
    	mDrawUnit.setY(Integer.parseInt(viewY.getText()));					//y
    	mDrawUnit.setWight(Integer.parseInt(viewWide.getText()));			//wide
    	mDrawUnit.setHight(Integer.parseInt(viewHight.getText()));			//hight
    	mDrawUnit.setUrl(dispressedPhoto.getText());						//url
    	
    	view.setId(viewID.getText());									//id
    	view.setDispressedPhoto(dispressedPhoto.getText());				//默认图片
    	
    	if(!RelativeLayoutAttrJF.photoUrl.equals(dispressedPhoto.getText())){
    		updateDispressedPhoto(dispressedPhoto.getText());			//更新宽高
    	}
    	mDrawUnit.refreshMyself();																		//刷新自己
    	MainJFrame.getInstance().getmMyCanvis().repaint(Record.x, Record.y, Record.wide, Record.hight);	//刷新上一个图片
    	mRelativeLayoutAttrJF.setVisible(false);
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

        jLabel19 = new javax.swing.JLabel();
        viewID = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        viewHight = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        viewX = new javax.swing.JTextField();
        viewY = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        viewWide = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        dispressedPhotoBut = new javax.swing.JButton();
        dispressedPhoto = new javax.swing.JTextField();
        saveBut = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel19.setFont(new java.awt.Font("幼圆", 0, 18)); // NOI18N
        jLabel19.setText("组件id:");

        viewID.setFont(new java.awt.Font("宋体", 0, 18)); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(153, 153, 153), new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("宋体", 1, 24)); // NOI18N
        jLabel5.setText("RelativeLayout");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        jLabel16.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel16.setText("组件宽：");

        jLabel15.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel15.setText("X坐标：");

        jLabel17.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel17.setText("Y坐标：");

        jLabel18.setFont(new java.awt.Font("幼圆", 0, 12)); // NOI18N
        jLabel18.setText("组件高：");

        jLabel13.setFont(new java.awt.Font("幼圆", 0, 14)); // NOI18N
        jLabel13.setText("背景图片：");

        dispressedPhotoBut.setText("浏览");
        dispressedPhotoBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispressedPhotoButActionPerformed(evt);
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dispressedPhoto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dispressedPhotoBut))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(viewID, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(viewX, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(viewY, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(viewWide, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(viewHight, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 14, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveBut)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {viewHight, viewWide, viewX, viewY});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel19)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel18)
                    .addComponent(viewWide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(viewHight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(viewY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(dispressedPhotoBut)
                    .addComponent(dispressedPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(saveBut)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

 
    // Variables declaration - do not modify                     
    private javax.swing.JTextField dispressedPhoto;
    private javax.swing.JButton dispressedPhotoBut;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton saveBut;
    private javax.swing.JTextField viewHight;
    private javax.swing.JTextField viewID;
    private javax.swing.JTextField viewWide;
    private javax.swing.JTextField viewX;
    private javax.swing.JTextField viewY;
    // End of variables declaration                   
}

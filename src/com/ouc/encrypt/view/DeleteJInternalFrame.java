/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ouc.encrypt.view;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lenovo
 */
public class DeleteJInternalFrame extends javax.swing.JInternalFrame {

    public List<String> list = new ArrayList<String>();
    private final static String AES = "AES";
    
    /**
     * Creates new form DecipherJInternalFrame
     */
    public DeleteJInternalFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        decipherJPanel1 = new com.ouc.encrypt.view.DecryptJPanel();
        btnSearchKey = new javax.swing.JButton();
        txtdir = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblList = new javax.swing.JTable();
        btnSearchList = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();

        setClosable(true);

        decipherJPanel1.setPreferredSize(new java.awt.Dimension(832, 501));

        btnSearchKey.setText("选择密钥");
        btnSearchKey.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchKeyActionPerformed(evt);
            }
        });

        btnDelete.setText("确认删除");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        tblList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "id"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblList);

        btnSearchList.setText("查询文件");
        btnSearchList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchListActionPerformed(evt);
            }
        });

        jLabel1.setText("删除文件id：");

        txtId.setEnabled(false);

        javax.swing.GroupLayout decipherJPanel1Layout = new javax.swing.GroupLayout(decipherJPanel1);
        decipherJPanel1.setLayout(decipherJPanel1Layout);
        decipherJPanel1Layout.setHorizontalGroup(
            decipherJPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(decipherJPanel1Layout.createSequentialGroup()
                .addGroup(decipherJPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(decipherJPanel1Layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(btnSearchList))
                    .addGroup(decipherJPanel1Layout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(decipherJPanel1Layout.createSequentialGroup()
                        .addGap(223, 223, 223)
                        .addGroup(decipherJPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(decipherJPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(decipherJPanel1Layout.createSequentialGroup()
                                .addComponent(btnSearchKey)
                                .addGap(18, 18, 18)
                                .addComponent(txtdir, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(88, 88, 88)
                        .addComponent(btnDelete)))
                .addContainerGap(116, Short.MAX_VALUE))
        );
        decipherJPanel1Layout.setVerticalGroup(
            decipherJPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(decipherJPanel1Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(btnSearchList)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(decipherJPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(decipherJPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSearchKey)
                    .addComponent(txtdir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete))
                .addGap(92, 92, 92))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(decipherJPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(decipherJPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // 选择解密密钥文件
    private void btnSearchKeyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchKeyActionPerformed
        JFileChooser openfile = new JFileChooser();//文件选择对话框
        FileFilter filter = new FileNameExtensionFilter("请选择密钥", "doc", "docx", "txt", "xlsx", "pdf");
        openfile.addChoosableFileFilter(filter);//添加过滤器
        openfile.setFileFilter(filter);
        int i = openfile.showOpenDialog(this);
        if (i == JFileChooser.APPROVE_OPTION) {
            //jFileChooser.show(true);
            //输出绝对路径
            System.out.println(openfile.getSelectedFile().getAbsolutePath());
            txtdir.setText(openfile.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_btnSearchKeyActionPerformed
    
    // 显示id列表
    private void showOnTable(List<String> list) {
         // 1. 获取表格(tblProduct)模型
        DefaultTableModel dtm = (DefaultTableModel) this.tblList.getModel();
        // 2. 清空表格信息
        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
        // 3.显示数据
        for (String s : list) {
            Vector vt = new Vector();
            vt.add(s);
            dtm.addRow(vt);
        }
    }
    
    // 查询获得id列表
    private void btnSearchListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchListActionPerformed
        
        list.add("1");
        list.add("2");
        list.add("3");
        //list = fileList();
        //3 显示在表格
        showOnTable(list);
    }//GEN-LAST:event_btnSearchListActionPerformed

    // 鼠标选中列表某行，该行信息显示到文本框
    private void tblListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListMouseClicked
        int row = this.tblList.getSelectedRow();
        // 根据row获取每列的值
        // 每行下标从0开始
        this.txtId.setText(this.tblList.getValueAt(row, 0)+"");
    }//GEN-LAST:event_tblListMouseClicked

    // 读取密钥文件，获取密钥
    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
    
    // 删除
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int answer =  JOptionPane.showConfirmDialog(this, "您确定要删除该文件吗？");
        if(answer == JOptionPane.YES_OPTION){
        // 1 获取删除编号
        int id = Integer.parseInt(this.txtId.getText());
        String path = this.txtdir.getText();
        File filename = new File(path); 
        String key = txt2String(filename);
        // 调用业务
        // public int deleFile(String title, String key)；
        //删除文件；返回值：0-成功；1-权限不足；2-文件不存在；3-网络错误
        //int result = deleFile(id,key);
        int result = 2;
        if(result == 0){
            JOptionPane.showMessageDialog(this, "删除成功");
            list.remove(id+"");
            //显示list中的信息
            showOnTable(list);
        }else if(result == 1){
            JOptionPane.showMessageDialog(this, "权限不足");
        }else if(result == 2){
            JOptionPane.showMessageDialog(this, "文件不存在");
        }else if(result == 3){
            JOptionPane.showMessageDialog(this, "网络异常");
        }
        // 清空面板信息
        clearInput();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed
    
    // 清空面板信息
    public void clearInput(){
        this.txtId.setText("");
        this.txtdir.setText("");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSearchKey;
    private javax.swing.JButton btnSearchList;
    private com.ouc.encrypt.view.DecryptJPanel decipherJPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblList;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtdir;
    // End of variables declaration//GEN-END:variables
}

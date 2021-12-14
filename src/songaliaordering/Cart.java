/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package songaliaordering;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import static songaliaordering.SongaliaOrdering.BASE_COLOR;

/**
 *
 * @author Songali A
 */
public final class Cart extends JPanel{
    final int PANEL_WIDTH = 300;
    final int PANEL_HEIGHT = 500;
    int cardVisibleIndex = 0;
    double totalCost = 99;
    Color infoColor = new Color(148, 83, 13);
    public Cart() {
        this.setBackground(BASE_COLOR);
        this.setLayout(new BorderLayout());
        this.add(mainPanel(),BorderLayout.CENTER);
    }
    
    JPanel mainPanel(){
        Color receiptBG = new Color(227, 196, 120);
        JPanel p = new JPanel(new BorderLayout());
        int padding = 20;
        p.setBorder(new EmptyBorder(padding,padding,padding,padding));
        p.setBackground(receiptBG);
        p.add(cartsList(),BorderLayout.CENTER);
        p.add(calculations(),BorderLayout.SOUTH);
        return p;
    }
    
    JPanel cartsList(){
        JPanel p = new JPanel();
        p.setBackground(null);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        
        JPanel cartTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cartTitle.setMaximumSize(new Dimension(300, 60));
        cartTitle.setBackground(null);
        JLabel title = new JLabel("CART");
        cartTitle.add(title);
        title.setForeground(infoColor);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        
        p.add(cartTitle);
        
        p.add(cartItem("Milk", "milk.jpg", 99,5));
        p.add(cartItem("Milk", "burger.jpeg", 99,5));
        
        return p;
    }
    
    JPanel cartItem(String name,String imageName,int price,int quantity){
        
        JPanel p = new JPanel();
        p.setBackground(null);
        p.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        p.setLayout(new BorderLayout());
        p.setPreferredSize(new Dimension(300, 70));
        p.setMinimumSize(new Dimension(300, 70));
        p.setMaximumSize(new Dimension(300, 70));
        JPanel itemName = new JPanel();
        
        JLabel labelName = new JLabel(name);
        labelName.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel categoryName = new JLabel("Large");
        categoryName.setForeground(infoColor);
        categoryName.setFont(new Font("Arial", Font.BOLD, 10));
        
        itemName.setBackground(BASE_COLOR);
        itemName.setLayout(new BorderLayout());
        itemName.add(labelName,BorderLayout.WEST);
        itemName.add(categoryName,BorderLayout.PAGE_END);
        
        itemName.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        
        JPanel itemImage = new JPanel();
        itemImage.setBackground(BASE_COLOR);
        try {
            BufferedImage myPicture = ImageIO.read(new File("src/items/" + imageName));
            Image newImage = myPicture.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
            JLabel picLabel = new JLabel(new ImageIcon(newImage));
            itemImage.add(picLabel);
            
        } catch (IOException ex) {
            Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JPanel itemPrice = new JPanel(new BorderLayout(5, 5));
        itemPrice.setBackground(BASE_COLOR);
        JLabel priceLabel = new JLabel("₱" + String.valueOf(price) );
        priceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        JLabel quantityLabel = new JLabel(quantity + " x");
        quantityLabel.setBackground(Color.green);
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 12));
        quantityLabel.setForeground(infoColor);
        
        JButton delButton = new JButton();
        delButton.setBackground(new Color(245, 127, 135));
        
        delButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        try {
            BufferedImage delPic = ImageIO.read(new File("src/icons/delete.png"));
            Image resizedDelPic = delPic.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
            delButton.setIcon(new ImageIcon(resizedDelPic));
        } catch (IOException ex) {
            Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        itemPrice.add(priceLabel,BorderLayout.CENTER);
        itemPrice.add(delButton,BorderLayout.EAST);
        itemPrice.add(quantityLabel,BorderLayout.WEST);
        p.add(itemImage,BorderLayout.WEST);
        p.add(itemName,BorderLayout.CENTER);
        p.add(itemPrice,BorderLayout.EAST);
        
        return p;
    }
    
    JPanel calculations(){
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        p.setBackground(null);
        
        JLabel total = new JLabel("Total: ₱" + this.totalCost,SwingConstants.RIGHT);
        total.setFont(new Font("Arial", Font.BOLD, 20));
        total.setForeground(new Color(17, 163, 20));

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBackground(null);
        totalPanel.add(total);
        
        JPanel confirmPanel = new JPanel(new BorderLayout());
        confirmPanel.setBackground(null);
        JButton confirmPurchase = new JButton("Confirm Purchase");
        confirmPurchase.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        confirmPurchase.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmPanel.add(confirmPurchase,BorderLayout.CENTER);
        confirmPurchase.setBackground(BASE_COLOR);
        
        
        p.add(totalPanel);
        p.add(confirmPanel);
        return p;
    }
    
}
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Songali A
 */
public class Cart extends JPanel{
    final int PANEL_WIDTH = 300;
    final int PANEL_HEIGHT = 500;
    int cardVisibleIndex = 0;
    double totalCost = 0;
    Color infoColor = new Color(148, 83, 13);
    Color BASE_COLOR;
    public JPanel cartList = cartsList();;
    JLabel totalLabel;
    public HashMap<Integer, Map> cartItems = new HashMap<>();
    
    public Cart(Color BASE_COLOR) {
        this.BASE_COLOR = BASE_COLOR;
        this.setBackground(BASE_COLOR);
        this.setLayout(new BorderLayout());
        this.add(mainPanel(),BorderLayout.CENTER);
//        this.setMinimumSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
    }
    
    JPanel mainPanel(){
        Color receiptBG = new Color(227, 196, 120);
        JPanel p = new JPanel(new BorderLayout());
        int padding = 20;
        p.setBorder(new EmptyBorder(padding,padding,padding,padding));
        p.setBackground(receiptBG);
        p.add(cartList,BorderLayout.CENTER);
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
        
        return p;
    }
    
    void addcartItem(String name, String imageFile, int totalPrice, int quantity, String category){
        int newId = this.cartItems.size() + 2;
        System.out.println(newId);
        this.cartItems.put(newId, Map.of(
                "name" , name,
                "imageFile", imageFile,
                "totalPrice", totalPrice,
                "quantity", quantity,
                "category", category
            ));
        
        this.cartList.add(cartItem(name,imageFile,totalPrice,quantity,category,newId));
        this.totalCost += totalPrice;
        repaintTotalLabel();
        this.cartList.repaint();
        System.out.println(this.cartItems);
    }
    
    void clearCart(){
        this.cartItems.clear();
        this.cartList.removeAll();
        this.totalCost = 0;
        repaintTotalLabel();
        JPanel cartTitle = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cartTitle.setBackground(null);
        JLabel title = new JLabel("CART");
        cartTitle.add(title);
        title.setForeground(infoColor);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        this.cartList.add(cartTitle);
        this.cartList.repaint();

    }
    
    void removeCartItem(int id){
        int toRemovePrice = Integer.valueOf(String.valueOf(this.cartItems.get(id).get("totalPrice")));
        this.totalCost -= toRemovePrice;
        repaintTotalLabel();
        this.cartItems.remove(id);
        System.out.println(this.cartItems);

    }
    
    JPanel cartItem(String name,String imageName,int price,int quantity,String category,int id){
        
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
        
        JLabel categoryName = new JLabel(category + " | " + quantity + " order(s)" );
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
        
        JButton delButton = new JButton();
        delButton.setBackground(new Color(245, 127, 135));
        delButton.setText(String.valueOf(id));
        delButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        delButton.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                String warningMessage = "Are you sure you want to delete " + name + " from cart?";
                int dialogResult = JOptionPane.showConfirmDialog (null, warningMessage ,"Warning",1);
                if(dialogResult == JOptionPane.YES_OPTION){
                    removeCartItem(id);
                    cartList.remove(p);
                    cartList.repaint();
                }
                
            } 
        });
        try {
            BufferedImage delPic = ImageIO.read(new File("src/icons/delete.png"));
            Image resizedDelPic = delPic.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
            delButton.setIcon(new ImageIcon(resizedDelPic));
        } catch (IOException ex) {
            Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        itemPrice.add(priceLabel,BorderLayout.CENTER);
        itemPrice.add(delButton,BorderLayout.EAST);
       
        p.add(itemImage,BorderLayout.WEST);
        p.add(itemName,BorderLayout.CENTER);
        p.add(itemPrice,BorderLayout.EAST);
        
        return p;
    }
    
    JPanel calculations(){
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        p.setBackground(null);
        
        totalLabel = new JLabel("Total: ₱" + this.totalCost,SwingConstants.RIGHT);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        totalLabel.setForeground(new Color(17, 163, 20));

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBackground(null);
        totalPanel.add(totalLabel);
        
        JPanel confirmPanel = new JPanel(new BorderLayout());
        confirmPanel.setBackground(null);
        JButton confirmPurchase = new JButton("Confirm Purchase");
        
        confirmPurchase.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        confirmPurchase.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmPanel.add(confirmPurchase,BorderLayout.CENTER);
        confirmPurchase.setBackground(BASE_COLOR);
        confirmPurchase.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if(cartItems.size() > 0){
                    JOptionPane.showMessageDialog(null, "Transaction successful. Please pay : ₱" + totalCost, "Purchase Confirmation", JOptionPane.PLAIN_MESSAGE, null);
                    clearCart();
                }
            }
        });
        
        p.add(totalPanel);
        p.add(confirmPanel);
        return p;
    }
    
    void repaintTotalLabel(){
        this.totalLabel.setText("Total: ₱" + this.totalCost);
    }    

}
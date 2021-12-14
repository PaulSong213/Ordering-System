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
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static songaliaordering.SongaliaOrdering.BASE_COLOR;


public final class ItemList extends JPanel{
    LinkedList<Map> items = new LinkedList<>();
    public ItemList() {
        this.setBackground(BASE_COLOR);
        addItems();
    }
    
    void addItems(){
        FlowLayout flow = new FlowLayout(0,5,5);
        this.setLayout(flow);
        this.add(item("Milk","milk.jpg",199));
        this.add(item("Burger","burger.jpeg",99));
        this.add(item("Milktea","milktea.jpeg",60));
    }
    
    
    JPanel item(String name,String imageName,int price){
        int width = 200;
        int height = 200;
        JPanel b = new JPanel();
        Color itemBg = new Color(227, 196, 120);
        
        
        b.setBackground(itemBg);
        b.setPreferredSize(new Dimension(width, height));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(itemBg);
        
        try {
            BufferedImage myPicture = ImageIO.read(new File("src/items/" + imageName));
            Image newImage = myPicture.getScaledInstance(width - 20, height - (height / 4), Image.SCALE_DEFAULT);
            JLabel picLabel = new JLabel(new ImageIcon(newImage));
            imagePanel.add(picLabel,BorderLayout.CENTER);
            
        } catch (IOException ex) {
            Logger.getLogger(ItemList.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(null);
        infoPanel.setPreferredSize(new Dimension(width - 20, 50));
        GridLayout infoGrid = new GridLayout(0,2);
        infoPanel.setLayout(infoGrid);
        JPanel itemName = new JPanel();
        itemName.setBackground(null);
        itemName.setLayout(new FlowLayout(FlowLayout.LEFT));
        itemName.add(new JLabel(name));
        
        JPanel itemPrice = new JPanel();
        itemPrice.add(new JLabel("₱" + String.valueOf(price)));
        itemPrice.setLayout(new FlowLayout(FlowLayout.RIGHT));
        itemPrice.setBackground(null);
        
        infoPanel.add(itemName);
        infoPanel.add(itemPrice);
        
        b.add(imagePanel,BorderLayout.CENTER);
        b.add(infoPanel,BorderLayout.SOUTH);
        
        
        b.addMouseListener(new MouseAdapter() {
            private Color background;

            @Override
            public void mousePressed(MouseEvent e) {
                background = b.getBackground();
                b.setBackground(new Color(153, 133, 84));
                repaint();
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                b.setBackground(background);
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                ItemOptions options = new ItemOptions();
                options.setTitle("Select Options for " + name);
                options.show(name,imageName,price);
            }
        });
        
        return b;
    }
    
}
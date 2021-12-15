package songaliaordering;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;
import static songaliaordering.SongaliaOrdering.BASE_COLOR;
//import static songaliaordering.SongaliaOrdering.cart;


public class ItemOptions extends SongaliaOrdering
{
    
    private String title;
    
    int totalPrice = 0;
    int totalQuantity = 1;
    String selectedCategory;
    Color infoColor = new Color(148, 83, 13);
    
    public ItemOptions()
    {

        setTitle("Custom dialog");
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    

    

    public void show(String name,String imageName,Map<String,Integer> categories)
    {
        Color itemBg = new Color(227, 196, 120);
        
        UIManager UI=new UIManager();
        UI.put("OptionPane.background", itemBg );
        UI.put("Panel.background", itemBg);
        UI.put("Spinner.background", itemBg );
        
        
        int width = 300;
        int height = 300;
        Object[] options = {};
        
        String[] categoryNames = new String[categories.size()];
        
        int categoryIndex = 0;
        for (String i : categories.keySet()) {
            categoryNames[categoryIndex] = i;
            categoryIndex++;
        }


        JPanel b = new JPanel(new BorderLayout(5, 5));
        
        b.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        b.setBackground(itemBg);
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
        GridLayout grid = new GridLayout(0,2);
        grid.setHgap(5);
        grid.setVgap(20);
        infoPanel.setLayout(grid);
        JPanel itemName = new JPanel();
        itemName.setBackground(null);
        itemName.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelName = new JLabel(name);
        labelName.setFont(new Font("Arial", Font.BOLD, 18));
        itemName.add(labelName);
        
        
        JPanel itemPrice = new JPanel();
       
        itemPrice.setLayout(new FlowLayout(FlowLayout.RIGHT));
        itemPrice.setBackground(null);
        
        JLabel priceLabel = new JLabel("₱" + String.valueOf(this.totalPrice * totalQuantity));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        priceLabel.setForeground(new Color(17, 163, 20));
        itemPrice.add(priceLabel);
        
        JPanel categoryPanel = new JPanel(new BorderLayout());
        categoryPanel.setBackground(null);
        JLabel categoryLabel = new JLabel("Category");
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 10));
        categoryLabel.setForeground(infoColor);
        
        JComboBox categoryList = new JComboBox(categoryNames);
        categoryList.setFont(new Font("Arial", Font.BOLD, 14));
        categoryList.setBackground(null);
        categoryList.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                String currentCategory = String.valueOf(categoryList.getSelectedItem());
                totalPrice = categories.get(currentCategory);
                selectedCategory = currentCategory;
                int newTotal = totalPrice * totalQuantity;
                totalPrice = newTotal;
                priceLabel.setText("₱" + String.valueOf(newTotal));
            }
        });
        categoryList.setSelectedIndex(1);
        
        categoryPanel.add(categoryLabel,BorderLayout.PAGE_START);
        categoryPanel.add(categoryList,BorderLayout.CENTER);
        
        
        JPanel quantityPanel = new JPanel(new BorderLayout());
        quantityPanel.setBackground(null);  

        
        final JSpinner quantity = new JSpinner();
        quantity.setValue(this.totalQuantity);
        quantity.getEditor().getComponent(0).setBackground(null);
        quantity.setFont(new Font("Arial", Font.BOLD, 14));
        JComponent comp = quantity.getEditor();
        JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
        DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
        formatter.setCommitsOnValidEdit(true);
        quantity.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int newQuantity = Integer.valueOf(String.valueOf(quantity.getValue()));
                String currentCategory = String.valueOf(categoryList.getSelectedItem());
                if(newQuantity < 1)newQuantity = 1;
                totalPrice = categories.get(currentCategory);
                quantity.setValue(newQuantity);
                totalQuantity = newQuantity;
                int newTotal = totalPrice * totalQuantity;
                totalPrice = newTotal;
                priceLabel.setText("₱" + String.valueOf(newTotal ));
            }
        });
        
        
        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 10));
        quantityLabel.setForeground(infoColor);
        
        quantityPanel.add(quantityLabel,BorderLayout.PAGE_START);
        quantityPanel.add(quantity,BorderLayout.CENTER);
        
        
        JPanel addPanel = new JPanel(new BorderLayout());
        addPanel.setBackground(null);
        JButton addPurchase = new JButton("Add to Cart");
        addPurchase.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        addPurchase.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addPanel.add(addPurchase,BorderLayout.CENTER);
        addPurchase.setBackground(BASE_COLOR);
        
        addPurchase.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
               addcartItem(name, imageName, totalPrice, totalQuantity, selectedCategory);
                Window w = SwingUtilities.getWindowAncestor(addPurchase);
                if (w != null) w.setVisible(false); 
            } 
          } );
        
        JPanel cancelPanel = new JPanel(new BorderLayout());
        addPanel.setBackground(null);
        JButton cancelPurchase = new JButton("Cancel");
        cancelPurchase.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        cancelPurchase.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelPanel.add(cancelPurchase,BorderLayout.CENTER);
        cancelPurchase.setBackground(null);
        cancelPurchase.addActionListener((ActionEvent evt) -> {
            Window w = SwingUtilities.getWindowAncestor(cancelPurchase);
            if (w != null) w.setVisible(false);
        });
        
        
        infoPanel.add(itemName);
        infoPanel.add(itemPrice);
        infoPanel.add(categoryPanel);
        infoPanel.add(quantityPanel);
        infoPanel.add(cancelPanel);
        infoPanel.add(addPanel);

        b.add(imagePanel,BorderLayout.CENTER);
        b.add(infoPanel,BorderLayout.SOUTH);
        
        
        
        
        JOptionPane.showOptionDialog(null, b, title,JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, null);
        
    }

    public static String getLineBreak()
    {
        return "<br>";
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package songaliaordering;

import java.awt.BorderLayout;
import java.awt.Color;

/**
 *
 * @author paul
 */
public class SongaliaOrdering {

    /**
     * @param args the command line arguments
     */
    ItemList itemList = new ItemList();;
    MainFrame frame = new MainFrame(BASE_COLOR);
    static Color BASE_COLOR = new Color(248,218,146);
    static Cart cart  = new Cart(BASE_COLOR);

     public static void main(String[] args) {
        // TODO code application logic here
        SongaliaOrdering gui = new SongaliaOrdering();
        gui.initApp();
    }
    
    public void initApp(){
        frame.setLayout(new BorderLayout());
        frame.add(itemList,BorderLayout.CENTER);
        frame.add(cart,BorderLayout.EAST);
        frame.setVisible(true);

    }
    
    void addcartItem(String name, String imageFile, int totalPrice, int quantity, String category){
        SongaliaOrdering.cart.addcartItem(name, imageFile, totalPrice, quantity, category);
    }
    
}

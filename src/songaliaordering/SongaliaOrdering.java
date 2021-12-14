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
    ItemList itemList;
    Cart receipt;
    static Color BASE_COLOR = new Color(248,218,146);
    
     public static void main(String[] args) {
        // TODO code application logic here
        SongaliaOrdering gui = new SongaliaOrdering();
        gui.initApp();
    }
    
    public void initApp(){
        MainFrame frame = new MainFrame(BASE_COLOR);
        frame.setLayout(new BorderLayout());
        itemList = new ItemList();
        receipt = new Cart();
        frame.add(itemList,BorderLayout.CENTER);
        frame.add(receipt,BorderLayout.EAST);
        frame.setVisible(true);
    }
    
}

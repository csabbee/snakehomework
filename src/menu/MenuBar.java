package menu;

import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar {

    public void addMenus(List<JMenu> menus){
        for (JMenu menu : menus) {
            this.add(menu);
        }
    }
}

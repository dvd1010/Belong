package com.belonginterview.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SuperProfs on 21/09/15.
 */
public enum MenuOptionsEnum {

    MOBILES(0, "Mobiles"), TABLETS(1, "Tablets"), CAMERAS(2, "Cameras"), LAPTOPS(3, "Laptops"), TVS(4, "TVs"), PRINTERS(5, "Printers"), TRIMMERS(6, "Trimmers");

    private int position;
    private String menuOptionName;

    private MenuOptionsEnum(int position, String menuOptionName){
        this.position = position;
        this.menuOptionName = menuOptionName;
    }

    public int getPosition(){
        return position;
    }

    public String getName(){
        return menuOptionName;
    }

    public static MenuOptionsEnum getMenuOption(int position){
        for(MenuOptionsEnum menuOptions : MenuOptionsEnum.values()){
            if(menuOptions.getPosition() == position){
                return menuOptions;
            }
        }
        throw new IllegalArgumentException("No Menu Option Found for given Position");
    }

    public static List<String> getMenuOptions(){
        List<String> menuOptionsList = new ArrayList<>();
        for(int i=0; i<MenuOptionsEnum.values().length ; i++) {
            menuOptionsList.add(MenuOptionsEnum.getMenuOption(i).getName());
        }
        return menuOptionsList;
    }
}

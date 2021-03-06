package com.belonginterview.enums;

import java.util.EnumMap;

/*Has a relation of products on navigation drawer and corresponding api tags*/
public class MenuEnumTagMap {

    private static final EnumMap<MenuOptionsEnum, String> menuOptionsEnumMap = new EnumMap<>(MenuOptionsEnum.class);

    static {
        menuOptionsEnumMap.put(MenuOptionsEnum.MOBILES, "mobiles");
        menuOptionsEnumMap.put(MenuOptionsEnum.TABLETS, "tablets");
        menuOptionsEnumMap.put(MenuOptionsEnum.CAMERAS, "cameras");
        menuOptionsEnumMap.put(MenuOptionsEnum.LAPTOPS, "laptops");
        menuOptionsEnumMap.put(MenuOptionsEnum.TVS, "tvs");
        menuOptionsEnumMap.put(MenuOptionsEnum.PRINTERS, "printers");
        menuOptionsEnumMap.put(MenuOptionsEnum.TRIMMERS, "trimmers");
    }

    public static String getTagString(MenuOptionsEnum menuOptions){
        return menuOptionsEnumMap.get(menuOptions);
    }
}

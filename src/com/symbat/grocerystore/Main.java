package com.symbat.grocerystore;

import com.symbat.grocerystore.menu.Menu;
import com.symbat.grocerystore.menu.MenuManager;

public class Main {
    public static void main(String[] args) {
        Menu menu = new MenuManager();
        menu.run();
    }
}
3
package Menus.Views;

import Menus.Menu;

public class ViewCommentMenu extends Menu {
    private String commentID;

    public ViewCommentMenu(Menu fatherMenu, String menuName, String commentID) {
        super(fatherMenu, menuName);
        this.commentID = commentID;
    }
}

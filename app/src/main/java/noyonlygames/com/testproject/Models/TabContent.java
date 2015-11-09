package noyonlygames.com.testproject.Models;

import android.support.v4.app.Fragment;

import java.util.Stack;

/**
 * Created by Алексей on 25.09.2015.
 */
public class TabContent {

    private Stack<Fragment> tabStack;
    private int containerId;

    public TabContent(int containerId){
        tabStack = new Stack<Fragment>();
        this.containerId = containerId;
    }

    public void push(Fragment fragment){
        tabStack.push(fragment);
    }

    public void pop(){
        tabStack.pop();
    }

    public int getContainerId(){
        return containerId;
    }

    public Fragment getLastFragment(){
        return tabStack.lastElement();
    }

    public int size(){
        return tabStack.size();
    }
}

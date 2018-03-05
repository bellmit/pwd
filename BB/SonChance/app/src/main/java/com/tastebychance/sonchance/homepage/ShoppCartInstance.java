package com.tastebychance.sonchance.homepage;

import com.tastebychance.sonchance.homepage.homeshoppingcart.bean.ShoppingCartBean;
import com.tastebychance.sonchance.homepage.order.bean.ShoppingcartBean;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 项目名称：SonChance
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/9/7 15:09
 * 修改人：Administrator
 * 修改时间：2017/9/7 15:09
 * 修改备注：
 */

public class ShoppCartInstance {

    private HashMap<Integer,ShoppingcartBean> shoppingCartHashMap;

    private ShoppCartInstance(){
        shoppingCartHashMap = new HashMap<Integer, ShoppingcartBean>();
    }

    public static ShoppCartInstance getInstance(){
        return ShoppingCartHolder.INSTANCE;
    }

    private static class ShoppingCartHolder{
        private static final ShoppCartInstance INSTANCE = new ShoppCartInstance();
    }

    public ShoppingcartBean get(int id){
        return shoppingCartHashMap.get(id);
    }

    public HashMap<Integer,ShoppingcartBean> getAll(){
        return shoppingCartHashMap;
    }

    //获取有几道菜
    public int getCount(){
        return shoppingCartHashMap.size();
    }

    //获取购物车所有菜品的数量
    public int getAllDishNum(){
        int num = 0 ;
        Set<Map.Entry<Integer, ShoppingcartBean>> set = ShoppCartInstance.getInstance().getAll().entrySet();
        Iterator<Map.Entry<Integer, ShoppingcartBean>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, ShoppingcartBean> entry = iterator.next();

            num += entry.getValue().getNum();
        }

        return num;
    }

    public void add(ShoppingcartBean ShoppingcartBean){
        shoppingCartHashMap.put(ShoppingcartBean.getId(),ShoppingcartBean);
    }

    public void remove(int id){
        shoppingCartHashMap.remove(id);
    }

    public void clear(){
        shoppingCartHashMap.clear();
    }

    public void release(){
        shoppingCartHashMap = null;
    }
}

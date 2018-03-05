package com.tastebychance.sonchance.util;

import android.content.Context;

import com.tastebychance.sonchance.homepage.bean.HomePageListRes;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 项目名称：SonChance
 * 类描述：
 * 创建人：Administrator
 * 创建时间： 2017/10/15 15:10
 * 修改人：Administrator
 * 修改时间：2017/10/15 15:10
 * 修改备注：
 */

public class RealmHelper {
    public static final String DB_NAME = "myRealm.realm";
    private Realm mRealm;

    public RealmHelper(Context context){
        mRealm = Realm.getDefaultInstance();
    }

    /**
     * add
     */
   /* public void addDish(final HomePageListRes homePageListRes){
        mRealm.beginTransaction();
        mRealm.copyToRealm(homePageListRes);
        mRealm.commitTransaction();
    }

    *//**
     * delete
     *//*
    public void deleteDish(int id){
        HomePageListRes temp = mRealm.where(HomePageListRes.class).equalTo("id",id).findFirst();
        mRealm.beginTransaction();
        temp.deleteFromRealm();
        mRealm.commitTransaction();
    }

    *//**
     * update
     *//*
    public void updateDish(int id,HomePageListRes homePageListRes){
        HomePageListRes temp = mRealm.where(HomePageListRes.class).equalTo("id",id).findFirst();
        mRealm.beginTransaction();
        temp.setBad_comments(homePageListRes.getBad_comments());
//        temp.setComments(homePageListRes.getComments());
        temp.setGood_comments(homePageListRes.getGood_comments());
        temp.setPrice(homePageListRes.getPrice());
        temp.setSort(homePageListRes.getSort());
        temp.setComment_count(homePageListRes.getComment_count());
        temp.setCreate_time(homePageListRes.getCreate_time());
        temp.setLogo(homePageListRes.getLogo());
        temp.setName(homePageListRes.getName());
        temp.setPromotion(homePageListRes.getPromotion());
        temp.setSail_count(homePageListRes.getSail_count());
        mRealm.commitTransaction();
    }

    *//**
     * queryAll
     *//*
    public List<HomePageListRes> queryAllDish(){
        RealmResults<HomePageListRes> temps = mRealm.where(HomePageListRes.class).findAll();
        *//**
         * 对查询结果，按ID进行排序，只能对查询结果进行排序
         *//*
        //自然排列
        temps.sort("id");
        //降序排列
//        temps.sort("id", Sort.DESCENDING);
        return mRealm.copyFromRealm(temps);
    }

    *//**
     * query
     *//*
    public HomePageListRes queryDishById(int id){
        return mRealm.where(HomePageListRes.class).equalTo("id",id).findFirst();
    }

    public boolean isDishExist(int id){
        HomePageListRes temp = mRealm.where(HomePageListRes.class).equalTo("id",id).findFirst();
        if (temp == null){
            return false;
        }else{
            return true;
        }
    }

    public Realm getRealm(){
        return mRealm;
    }

    public void close(){
        if (mRealm != null){
            mRealm.close();
        }
    }*/
}

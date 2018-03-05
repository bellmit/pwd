package company.webdemo.agile.com.technologycompany.util;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import java.io.IOException;
import java.util.List;

public class LocationUtil {

	//获取到城市名称并保存在本地

    private static Geocoder geocoder; // 此对象能通过经纬度来获取相应的城市等信息
    private Context context;

    public LocationUtil(Context context) {
        this.context = context;
        geocoder = new Geocoder(context);
    }

    public String getAddressName(){
        List<Address> addresses = getAddressList(getBestLocation(getLocationManager()));
        try {
            if (addresses != null) {
                return getProvinceAndCityByNameCoordinate(addresses);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Location（获取经纬度）
     *
     * @param locationManager
     * @return
     */
    private Location getBestLocation(LocationManager locationManager) {
        Location location = null;

        // 返回所有已知的位置提供者的名称列表，包括未获准访问或调用活动目前的
        List<String> lp = locationManager.getAllProviders();
        for (String string : lp) {
            System.out.println("可用位置服务：" + string);
        }

        if (locationManager != null) {

            String[] locProvider ={LocationManager.GPS_PROVIDER, LocationManager.NETWORK_PROVIDER, LocationManager.PASSIVE_PROVIDER};
            for (int i = 0; i < locProvider.length; i++) {
                location = locationManager.getLastKnownLocation(locProvider[i]);

                if (null != location) {
                    break;
                }
            }

            if (location == null) {
                Criteria criteria = new Criteria();
                criteria.setCostAllowed(false);// 该位置免费
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);// 设置水平位置精度

                // getBestProvider只有允许访问调用活动的位置供应商将被返回
                String providerName = locationManager.getBestProvider(criteria, true);
                System.out.println("位置服务：" + providerName);

                if (providerName != null) {
                    location = locationManager.getLastKnownLocation(providerName);
                } else {

                    // Toast.makeText(this, "1.请检查网络连接\n2.请打开我的位置",
                    // Toast.LENGTH_SHORT).show();
                }
            }
        }

        return location;
    }


    private LocationManager getLocationManager(){
        LocationManager locationManager = null;
        if (locationManager == null) {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }
        return locationManager;
    }

    private List<Address> getAddressList(Location location){
        if (location != null) {
            // 获取维度信息
            final double latitude = location.getLatitude();
            // 获取经度信息
            final double longitude = location.getLongitude();
            return getAddresses(longitude, latitude);
        }
        return null;
    }

    /**
     * 根据经纬度获取List<Address>
     *
     * @param longitude
     * @param latitude
     * @return
     */
    private List<Address> getAddresses(double longitude, double latitude) {
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return addresses;
    }

    /**
     * 根据List<Address>获取当前所在省份名称 和 城市名称
     *
     */
    private String getProvinceAndCityByNameCoordinate(List<Address> addresses) {

        String mcityName = "";
//        String mAdminArea = "";

        if (addresses != null && addresses.size() > 0) {
            for (int i = 0; i < addresses.size(); i++) {
                Address add = addresses.get(i);
//                mAdminArea += add.getAdminArea();
                mcityName += add.getLocality();
            }
        }
//        String result = null;
//        if (mAdminArea.length() != 0) {
//            result = mAdminArea;
//            if (mcityName.length() != 0) {
//                result += mcityName;
//            }
//        }
//        return result;
        if (mcityName.length() != 0){
            return mcityName;
        }
        return null;
    }
}

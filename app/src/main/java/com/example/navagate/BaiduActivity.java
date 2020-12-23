package com.example.navagate;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;
        import androidx.core.content.ContextCompat;

        import android.Manifest;
        import android.content.pm.PackageManager;
        import android.os.Bundle;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.baidu.location.BDAbstractLocationListener;
        import com.baidu.location.BDLocation;
        import com.baidu.location.BDLocationListener;
        import com.baidu.location.LocationClient;
        import com.baidu.location.LocationClientOption;
        import com.baidu.mapapi.SDKInitializer;
        import com.baidu.mapapi.map.BaiduMap;
        import com.baidu.mapapi.map.MapStatusUpdate;
        import com.baidu.mapapi.map.MapStatusUpdateFactory;
        import com.baidu.mapapi.map.MapView;
        import com.baidu.mapapi.model.LatLng;

        import java.util.ArrayList;
        import java.util.List;

public class BaiduActivity extends AppCompatActivity {
    TextView locationInfo;
    LocationClient mLocationClient;//创建一个终端
    MapView mMapView;//创建一个视图

    BaiduMap mBaiduMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //细节
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_baidu);

        locationInfo=findViewById(R.id.locationInfo);
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        mMapView=findViewById(R.id.bmapView);
        mBaiduMap=mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        mBaiduMap.setMyLocationEnabled(true);

        List<String> permissonList=new ArrayList<String>();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissonList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissonList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissonList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if(!permissonList.isEmpty()){
            String[] permissions=permissonList.toArray(new String[permissonList.size()]);
            ActivityCompat.requestPermissions(this,permissions,1);
        }else{
            requestLocation();
        }
    }
    //这里实际上也是一个消息的提醒 并没有本质上的区别
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for (int result:grantResults){
                        if (result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须统一所有的权限",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else{
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option= new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选初始化参数
        //Hight_Accuracy 高精度
        //Battery_Saving 省电模式
        //Device_Sensors 仅使用设备

        option.setCoorType("bd09ll");
        //可选 返回经纬度坐标类型
        //GCJ02 国测局坐标
        //BD09ll 百度坐标
        option.setScanSpan(1000);
        //int型 以ms为单位 必须大于1000才有效 否则只定位一次
        option.setOpenGps(true);
        //默认为false
        //使用高精度以及仅使用设备需要打开
        option.setLocationNotify(true);
        //设置在gps有效时1s/1次的频率输出GPS结果 默认false
        option.setIgnoreKillProcess(false);
        //定位SDK内部是一个service 并放到了独立进程
        //是否在stop的时候杀死这个进程 默认是不杀死 即setIgnoreKillProcess(true)
        option.setWifiCacheTimeOut(5*60*1000);
        option.setEnableSimulateGps(false);
        option.setIsNeedAddress(true);//显示具体位置 不写这句话无法显示具体位置
        mLocationClient.setLocOption(option);
    }

    private class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            navigateTo(bdLocation);
            showPosition(bdLocation);
        }
    }

    private void showPosition(BDLocation bdLocation) {
        StringBuilder currentPosition= new StringBuilder();
        currentPosition.append("纬度：").append(bdLocation.getLatitude()).append("\n");
        currentPosition.append("经度：").append(bdLocation.getLongitude()).append("\n");
        currentPosition.append("国家：").append(bdLocation.getCountry()).append("\n");
        currentPosition.append("省份：").append(bdLocation.getProvince()).append("\n");
        currentPosition.append("市：").append(bdLocation.getCity()).append("\n");
        currentPosition.append("区：").append(bdLocation.getDistrict()).append("\n");
        currentPosition.append("村镇：").append(bdLocation.getTown()).append("\n");
        currentPosition.append("街道：").append(bdLocation.getStreet()).append("\n");
        currentPosition.append("地址：").append(bdLocation.getAddrStr()).append("\n");
        currentPosition.append("定位方式：");
        if (bdLocation.getLocType()==BDLocation.TypeGpsLocation){
            currentPosition.append("GPS");
        }else if(bdLocation.getLocType()==BDLocation.TypeNetWorkLocation);{
            currentPosition.append("网络");
        }
        locationInfo.setText(currentPosition);
    }

    private void navigateTo(BDLocation bdLocation) {
        LatLng ll=new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());//经纬度
        MapStatusUpdate upData= MapStatusUpdateFactory.newLatLng(ll);
        mBaiduMap.animateMapStatus(upData);
//        upData=MapStatusUpdateFactory.zoomTo(16f);
//        mBaiduMap.animateMapStatus(upData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }
}
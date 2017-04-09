package com.hejian.maphejian;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private MapView map;
    private BaiduMap mapView;
    private PoiSearch poiSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        initView();

        initListener();
    }

    private void initListener() {
        //添加地图的事件
        initMapListener();
    }

    private void initMapListener() {
        //设置地图点击事件
        mapView.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mapView.clear();//清除地图上的所有的覆盖物

                //创建一个覆盖物的图标
                BitmapDescriptor icon = BitmapDescriptorFactory
                        .fromResource(R.mipmap.ic_launcher);

                //创建一个覆盖物
                OverlayOptions options =
                        new MarkerOptions().position(latLng).icon(icon);

                //将覆盖物添加到地图中
                mapView.addOverlay(options);

            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }


    private void initView() {
        map = (MapView) findViewById(R.id.mapview);
        //获取当前的地图
        mapView = map.getMap();
    }

    /**
     * 添加图层
     * @param view
     */

    public void btn1(View view){
        mapView.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

//        //普通地图
//        mapView.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//
//        //卫星地图
//        mapView.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
//
//        //空白地图, 基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
//        mapView.setMapType(BaiduMap.MAP_TYPE_NONE);
//
//        //开启交通图   实时交通图
//        mapView.setTrafficEnabled(true);
//
//        //开启交通图   百度城市热力图
//        mapView.setBaiduHeatMapEnabled(true);
    }


    /*
    * 添加覆盖物
    *
    * */
    public void btn2(View view) {
        //添加Marker
        //经纬度
//        LatLng l = new LatLng(39.963175,116.400244);
//
//        //创建一个覆盖物的图标
//        BitmapDescriptor icon = BitmapDescriptorFactory
//                .fromResource(R.mipmap.ic_launcher);
//
//        //创建一个覆盖物
//        OverlayOptions options = new MarkerOptions().position(l).icon(icon);
//
//        //将覆盖物添加到地图中
//        mapView.addOverlay(options);

        //添加几何图形
        //添加一个几何图形
        LatLng l1 = new LatLng(39.93925, 116.357428);

        LatLng l2 = new LatLng(39.90925, 116.327428);

        LatLng l3 = new LatLng(39.88925, 116.347428);

        //将三个坐标添加到集合中
        List<LatLng> pt = new ArrayList<>();
        pt.add(l1);
        pt.add(l2);
        pt.add(l3);

        OverlayOptions options = new PolygonOptions().points(pt)
                //2 边宽  和边的颜色  ， fillColor(填充的颜色)
                .stroke(new Stroke(2, 0xAA00FF00)).fillColor(0xAA00FF00);

        mapView.addOverlay(options);
    }

    /*
   * POI检索
   *
   * */
    public void btn3(View view) {

        //城市内检索
        /*
            PoiCitySearchOption	city(java.lang.String city)
            检索城市

            PoiCitySearchOption	keyword(java.lang.String key)
            搜索关键字

            PoiCitySearchOption	pageCapacity(int pageCapacity)
            设置每页容量，默认为每页10条

            PoiCitySearchOption	pageNum(int pageNum)
            分页编号
         */

        /*poiSearch.searchInCity(
                new PoiCitySearchOption().city("北京")
                        .keyword("ATM").pageNum(1).pageCapacity(10));*/

        /**

         keyword(java.lang.String key)
         检索关键字
         PoiNearbySearchOption	location(LatLng location)
         检索位置
         PoiNearbySearchOption	pageCapacity(int pageCapacity)
         设置每页容量，默认为每页10条
         PoiNearbySearchOption	pageNum(int pageNum)
         分页编号
         PoiNearbySearchOption	radius(int radius)
         设置检索的半径范围
         PoiNearbySearchOption	sortType(PoiSortType sortType)
         搜索结果排序规则，可选，默认
         */

        LatLng l1 = new LatLng(39.93925, 116.357428);
        poiSearch.searchNearby(new PoiNearbySearchOption()
                .radius(10000).pageCapacity(10).pageNum(1).location(l1).keyword("美食"));
    }

    public void btn4(View view){}
    public void btn5(View view){}
    public void btn6(View view){}


    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
        poiSearch.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    protected void onResume() {
        super.onStop();
        map.onResume();
    }
}

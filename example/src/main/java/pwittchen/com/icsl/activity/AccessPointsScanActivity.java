package pwittchen.com.icsl.activity;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.ListView;

import com.pwittchen.icsl.library.event.WifiAccessPointsRefreshedEvent;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import pwittchen.com.icsl.R;
import pwittchen.com.icsl.adapter.ScanResultAdapter;

public class AccessPointsScanActivity extends BaseActivity {
    private ListView lvAccessPointScanResults;
    private List<ScanResult> accessPoints = new ArrayList<ScanResult>();
    private ScanResultAdapter scanResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_points_scan);
        lvAccessPointScanResults = (ListView) findViewById(R.id.lv_access_point_scan_results);
    }

    @Subscribe
    public void wifiAccessPointsRefreshed(WifiAccessPointsRefreshedEvent event) {
        setScanResultAdapter();
    }

    private void setScanResultAdapter() {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiManager.startScan();
        accessPoints = wifiManager.getScanResults();
        scanResultAdapter = new ScanResultAdapter(this, R.layout.list_row, accessPoints);
        lvAccessPointScanResults.setAdapter(scanResultAdapter);
        scanResultAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setScanResultAdapter();
    }
}

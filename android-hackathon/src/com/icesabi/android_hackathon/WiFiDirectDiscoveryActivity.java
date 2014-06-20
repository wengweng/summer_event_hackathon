package com.icesabi.android_hackathon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class WiFiDirectDiscoveryActivity extends Activity implements PeerListListener {
	private WifiP2pManager mManager;
	private Channel mChannel;
	private BroadcastReceiver mReceiver;
	private IntentFilter mIntentFilter;
	public InetAddress host;
	
	
	private ListView listView;
	private Button discoverButton;
	private ArrayAdapter<WifiP2pDevice> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_wifi_direct_discovery);
		
		mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
	    mChannel = mManager.initialize(this, getMainLooper(), null);
	    mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);
	    
	    mIntentFilter = new IntentFilter();
	    mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
	    mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
	    mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
	    mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
	    
	    listView = (ListView) findViewById(R.id.listView1);
	    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    	@Override
	    	public void onItemClick(AdapterView<?> parent, View view,
	    			int position, long id) {
	    		connectToDevice(adapter.getItem(position));
	    	}

		});
	    discoverButton = (Button) findViewById(R.id.button1);
	    discoverButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				discover();
			}
		});
	}
	
	/* register the broadcast receiver with the intent values to be matched */
	@Override
	protected void onResume() {
	    super.onResume();
	    registerReceiver(mReceiver, mIntentFilter);
	}
	/* unregister the broadcast receiver */
	@Override
	protected void onPause() {
	    super.onPause();
	    unregisterReceiver(mReceiver);
	}
	
	private void discover() {
		mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
		    @Override
		    public void onSuccess() {
		       System.out.println("discover success");
		    }

		    @Override
		    public void onFailure(int reasonCode) {
		    	System.out.println("discover failed");
		       
		    }
		});
	}

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {
    	Collection<WifiP2pDevice> devices = peers.getDeviceList();
    	
    	adapter = new ArrayAdapter<WifiP2pDevice>(this, R.layout.buddy_list_row, R.id.textView1);
    	adapter.addAll(devices);
    	this.listView.setAdapter(adapter);
    	
    	for(WifiP2pDevice device: devices) {
    		System.out.println(device.deviceName);
    	}
    	
    }
    
	private void connectToDevice(final WifiP2pDevice device) {
		WifiP2pConfig config = new WifiP2pConfig();
		config.deviceAddress = device.deviceAddress;
		config.wps.setup = WpsInfo.PBC;
		mManager.connect(mChannel, config, new ActionListener() {

		    @Override
		    public void onSuccess() {
		        System.out.println("connect success");
		        MainApplication app = (MainApplication) getApplication();
		        app.connectToServer(host);
		        
		    }

		    @Override
		    public void onFailure(int reason) {
		        //failure logic
		    	System.out.println("connect failed");
		    }
		});
	}
	

}


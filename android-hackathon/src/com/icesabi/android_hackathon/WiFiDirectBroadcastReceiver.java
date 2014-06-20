package com.icesabi.android_hackathon;

import java.util.Collection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;

public class WiFiDirectBroadcastReceiver extends BroadcastReceiver implements PeerListListener {

    private WifiP2pManager mManager;
    private Channel mChannel;
    private WiFiDirectDiscoveryActivity mActivity;

    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, Channel channel,
    		WiFiDirectDiscoveryActivity activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
    }
    
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
        	System.out.println("Check to see if Wi-Fi is enabled and notify appropriate activity");
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi P2P is enabled
            	System.out.println("Wifi P2P is enabled");
            } else {
                // Wi-Fi P2P is not enabled
            	System.out.println("Wifi P2P is not enabled");
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers() to get a list of current peers
        	System.out.println("Call WifiP2pManager.requestPeers() to get a list of current peers");
        	if (mManager != null) {
                mManager.requestPeers(mChannel, this);
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
        	System.out.println("Respond to new connection or disconnections");
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
        	System.out.println("Respond to this device's wifi state changing");
        }
    }
    
    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {
    	Collection<WifiP2pDevice> devices = peers.getDeviceList();
    	for(WifiP2pDevice device: devices) {
    		System.out.println(device.deviceName);
    	}
    	
    }
}
package com.example.miniact6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv1 = findViewById(R.id.tv1);
        TextView tv2 = findViewById(R.id.tv2);

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        setText(tv2, getString(R.string.no_connected)); // Global Static Variable
        setText(tv1, getString(R.string.loading)); // Global Static Variable

        connectivityManager.registerDefaultNetworkCallback(
                new ConnectivityManager.NetworkCallback() {

                    @Override
                    public void onAvailable(Network network) {
                        setText(tv2, "working");
                    }
                    @Override
                    public void onLinkPropertiesChanged(@NonNull Network network, @NonNull LinkProperties linkProperties) {
                        super.onLinkPropertiesChanged(network, linkProperties);
                        setText(tv1, linkProperties.toString());
                    }
                    @Override
                    public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
                        super.onCapabilitiesChanged(network, networkCapabilities);
                        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                            setText(tv2, getString(R.string.wifi_connected));
                        else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                            setText(tv2, getString(R.string.connected));
                    }
                    @Override
                    public void onLost(Network network) {
                        setText(tv2, getString(R.string.no_connected)); // Global Static Variable
                    }
                }
        );



    }

    private void setText(TextView textView, String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(text);
            }
        });
    }
}
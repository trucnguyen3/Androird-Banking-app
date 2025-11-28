package aka.digital.banking_v2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;

import java.util.HashMap;
import java.util.Map;

public class WalletFragment extends Fragment {
    TextView txtWalletBalance;
    String LOG_TAG = "Android-Banking-app";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Map<String, Object> eventValues = new HashMap<String, Object>();
        eventValues.put("Screen Name", "Wallet Screen");
        eventValues.put("Deep Link", "aka://banking/wallet");
        eventValues.put("Screen ID", "4");

        AppsFlyerLib.getInstance().logEvent(requireContext(),
                "af_wallet_screen",
                eventValues,
                new AppsFlyerRequestListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(LOG_TAG, "Event sent successfully");
                    }
                    @Override
                    public void onError(int i, @NonNull String s) {
                        Log.d(LOG_TAG, "Event failed to be sent:\n" +
                                "Error code: " + i + "\n"
                                + "Error description: " + s);
                    }
                });

        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        txtWalletBalance  = view.findViewById(R.id.txtBalance);

        txtWalletBalance.setText("₫ 12,540,000");

        return view;
    }
}
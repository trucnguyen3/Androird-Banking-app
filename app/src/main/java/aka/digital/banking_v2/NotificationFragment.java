package aka.digital.banking_v2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;

import java.util.HashMap;
import java.util.Map;

public class NotificationFragment extends Fragment {

    public NotificationFragment() {}

    String LOG_TAG = "Android-Banking-app";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Map<String, Object> eventValues = new HashMap<String, Object>();
        eventValues.put("Screen Name", "Notification Screen");
        eventValues.put("Deep Link", "aka://banking/notification");
        eventValues.put("Screen ID", "3");

        AppsFlyerLib.getInstance().logEvent(requireContext(),
                "af_notification_screen",
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

        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        LinearLayout notificationList = view.findViewById(R.id.notificationList);

        // Sample notifications with title and body
        String[][] notifications = {
                {"Account Update", "Your account balance has been updated."},
                {"Transaction Alert", "A new transaction of ₫2,500,000 was made."},
                {"Payment Reminder", "Your credit card payment is due tomorrow."}
        };

        for (String[] notification : notifications) {
            // Container for each notification
            LinearLayout card = new LinearLayout(getContext());
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(24, 24, 24, 24);
            card.setBackgroundResource(R.drawable.notification_card_bg);

            // Title
            TextView title = new TextView(getContext());
            title.setText(notification[0]);
            title.setTextSize(16f);
            title.setTextColor(0xFF111827); // dark color
            title.setTypeface(null, android.graphics.Typeface.BOLD);

            // Body
            TextView body = new TextView(getContext());
            body.setText(notification[1]);
            body.setTextSize(14f);
            body.setTextColor(0xFF374151); // medium dark

            // Add title and body to card
            card.addView(title);
            card.addView(body);

            // LayoutParams with bottom margin
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 16);
            card.setLayoutParams(params);

            // Add card to the notification list
            notificationList.addView(card);
        }

        return view;
    }
}

package aka.digital.banking_v2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {

    public HomeFragment() {}

    TextView welcomeText, txtBalance;
    String LOG_TAG = "Android-Banking-app";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Map<String, Object> eventValues = new HashMap<String, Object>();
        eventValues.put("Screen Name", "Home Screen");
        eventValues.put("Deep Link", "aka://banking/home");
        eventValues.put("Screen ID", "2");

        AppsFlyerLib.getInstance().logEvent(requireContext(),
                "af_home_screen",
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

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        welcomeText = view.findViewById(R.id.txtWelcome);
        txtBalance  = view.findViewById(R.id.txtBalance);

        Bundle args = getArguments();
        String username = "";
        String emailaddress = "";
        if (args != null) {
            username = args.getString("USERNAME", "Guest");
            emailaddress = args.getString("EMAILADDRESS", "guest@gmail.com");
        }

        welcomeText.setText("Hello, " + username);

        txtBalance.setText("₫ 12,540,000");

        ImageView imgNotification = view.findViewById(R.id.imgNotification);
        imgNotification.setOnClickListener(v -> {
            NotificationFragment notificationFragment = new NotificationFragment();
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homeFragmentContainer, notificationFragment)
                    .addToBackStack(null)
                    .commit();
        });

        // --- News Carousel ---
        ViewPager2 newsViewPager = view.findViewById(R.id.newsViewPager);

        List<NewsItem> newsList = new ArrayList<>();
        newsList.add(new NewsItem("Bank Update", "New savings plan available now!"));
        newsList.add(new NewsItem("Promo Offer", "Get 5% cashback on your next top-up."));
        newsList.add(new NewsItem("Security Alert", "Enable 2FA for safer transactions."));

        NewsAdapter adapter = new NewsAdapter(newsList);
        newsViewPager.setAdapter(adapter);
        newsViewPager.setClipToPadding(false);
        newsViewPager.setClipChildren(false);
        newsViewPager.setOffscreenPageLimit(3);

        return view;
    }
}
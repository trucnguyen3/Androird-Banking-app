package aka.digital.banking_v2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    public HomeFragment() {}

    TextView welcomeText, txtBalance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
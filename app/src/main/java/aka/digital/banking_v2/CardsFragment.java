package aka.digital.banking_v2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CardsFragment extends Fragment {

    ViewPager2 viewPager;
    String LOG_TAG = "Android-Banking-app";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Map<String, Object> eventValues = new HashMap<String, Object>();
        eventValues.put("Screen Name", "Cards Screen");
        eventValues.put("Deep Link", "aka://banking/cards");
        eventValues.put("Screen ID", "5");

        AppsFlyerLib.getInstance().logEvent(requireContext(),
                "af_cards_screen",
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

        View view = inflater.inflate(R.layout.fragment_cards, container, false);

        viewPager = view.findViewById(R.id.viewPagerCards);

        // Sample cards
        List<CardModel> cardList = new ArrayList<>();
        cardList.add(new CardModel("Visa Classic","₫ 12,500,000","**** 8291"));
        cardList.add(new CardModel("Master Gold","₫ 25,800,000","**** 5402"));
        cardList.add(new CardModel("Visa Platinum","₫ 50,000,000","**** 1199"));

        CardsAdapter adapter = new CardsAdapter(cardList);
        viewPager.setAdapter(adapter);

        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        return view;
    }
}

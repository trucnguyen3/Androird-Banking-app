package aka.digital.banking_v2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class CardsFragment extends Fragment {

    ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

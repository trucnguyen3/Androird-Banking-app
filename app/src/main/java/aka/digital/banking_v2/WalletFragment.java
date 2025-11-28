package aka.digital.banking_v2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WalletFragment extends Fragment {
    TextView txtWalletBalance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);

        txtWalletBalance  = view.findViewById(R.id.txtBalance);

        txtWalletBalance.setText("₫ 12,540,000");

        return view;
    }
}
package aka.digital.banking_v2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    Switch switchDark;
    TextView userName, emailAddress;
    String LOG_TAG = "Android-Banking-app";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Map<String, Object> eventValues = new HashMap<String, Object>();
        eventValues.put("Screen Name", "Profile Screen");
        eventValues.put("Deep Link", "aka://banking/profile");
        eventValues.put("Screen ID", "6");

        AppsFlyerLib.getInstance().logEvent(requireContext(),
                "af_profile_screen",
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

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        switchDark = view.findViewById(R.id.switchDarkMode);

        Bundle args = getArguments();
        String username = "";
        String emailaddress = "";
        if (args != null) {
            username = args.getString("USERNAME", "Guest");
            emailaddress = args.getString("EMAILADDRESS", "guest@gmail.com");
        }

        userName = view.findViewById(R.id.txtName);
        userName.setText(username);
        emailAddress = view.findViewById(R.id.txtEmail);
        emailAddress.setText(emailaddress);

        // ---- BUTTON HANDLERS ----
        view.findViewById(R.id.btnMyCards).setOnClickListener(v -> {
            // Navigate to CardsFragment
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homeFragmentContainer, new CardsFragment())
                    .addToBackStack(null)
                    .commit();
        });

        view.findViewById(R.id.btnSecurity).setOnClickListener(v ->
                Toast.makeText(getActivity(), "Security settings coming soon", Toast.LENGTH_SHORT).show()
        );

        view.findViewById(R.id.btnNotifications).setOnClickListener(v ->
                Toast.makeText(getActivity(), "Notification settings coming soon", Toast.LENGTH_SHORT).show()
        );

        // ---- DARK MODE ----
        switchDark.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // ---- LOGOUT ----
        view.findViewById(R.id.btnLogout).setOnClickListener(v -> {
            requireActivity().finish();      // close HomeActivity
        });

        return view;
    }
}

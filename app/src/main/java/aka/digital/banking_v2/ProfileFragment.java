package aka.digital.banking_v2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    Switch switchDark;

    TextView userName, emailAddress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

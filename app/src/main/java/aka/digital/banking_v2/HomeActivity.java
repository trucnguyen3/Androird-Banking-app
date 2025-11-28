package aka.digital.banking_v2;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    String username, emailaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        username = getIntent().getStringExtra("USERNAME");
        emailaddress = getIntent().getStringExtra("EMAILADDRESS");

        // Initialize BottomNavigationView
        bottomNav = findViewById(R.id.bottomNav);

        loadFragment(new HomeFragment(), username, emailaddress);

        // Set listener
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selected = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) selected = new HomeFragment();
            else if (id == R.id.nav_wallet) selected = new WalletFragment();
            else if (id == R.id.nav_cards) selected = new CardsFragment();
            else if (id == R.id.nav_profile) selected = new ProfileFragment();

            if (selected != null) {
                loadFragment(selected, username, emailaddress);
            }
            return true;
        });

        handleDeepLink();
    }

    private void loadFragment(Fragment fragment, String username, String emailaddress) {
        Bundle bundle = new Bundle();
        bundle.putString("USERNAME", username);
        bundle.putString("EMAILADDRESS", emailaddress);
        fragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.homeFragmentContainer, fragment)
                .commit();
    }

    private void handleDeepLink() {
        Uri data = getIntent().getData();
        if (data != null) {
            Log.d("DEEPLINK", "Opened Home screen with: " + data.toString());
        }
    }
}

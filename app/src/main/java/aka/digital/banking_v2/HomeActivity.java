package aka.digital.banking_v2;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

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

        bottomNav = findViewById(R.id.bottomNav);

        // Determine which fragment to open
        String fragmentToOpen = getIntent().getStringExtra("fragment_to_open");
        if (fragmentToOpen == null && getIntent().getData() != null) {
            fragmentToOpen = getIntent().getData().getPath().replace("/", "");
        }

        openFragmentByName(fragmentToOpen != null ? fragmentToOpen : "home");

        // Bottom navigation listener
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selected = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) selected = new HomeFragment();
            else if (id == R.id.nav_wallet) selected = new WalletFragment();
            else if (id == R.id.nav_cards) selected = new CardsFragment();
            else if (id == R.id.nav_profile) selected = new ProfileFragment();

            if (selected != null) {
                loadFragment(selected);
            }
            return true;
        });
    }

    private void openFragmentByName(String name) {
        switch (name) {
            case "notifications":
                loadFragment(new NotificationFragment());
                break;
            case "home":
            default:
                loadFragment(new HomeFragment());
                break;
        }
    }

    private void loadFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("USERNAME", username);
        bundle.putString("EMAILADDRESS", emailaddress);
        fragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.homeFragmentContainer, fragment)
                .commit();
    }
}

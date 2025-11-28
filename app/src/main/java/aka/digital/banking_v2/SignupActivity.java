package aka.digital.banking_v2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText edtUserName, edtEmail, edtPassword;
    Button btnSignup;
    TextView btnGoLogin;
    String LOG_TAG = "Android-Banking-app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtUserName = findViewById(R.id.edtUserName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignup = findViewById(R.id.btnSignup);
        btnGoLogin = findViewById(R.id.btnGoLogin);

        btnSignup.setOnClickListener(v -> {
            String username = edtUserName.getText().toString().trim();
            String emailaddress = edtEmail.getText().toString().trim();

            Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
            intent.putExtra("USERNAME", username); // pass the username
            intent.putExtra("EMAILADDRESS", emailaddress);

            AppsFlyerLib.getInstance().setCustomerIdAndLogSession(username, this);

            Map<String, Object> eventValues = new HashMap<String, Object>();
            eventValues.put("Screen Name", "Signup Screen");
            eventValues.put("Deep Link", "aka://banking/signup");
            eventValues.put("Screen ID", "1");

            AppsFlyerLib.getInstance().logEvent(getApplicationContext(),
                    AFInAppEventType.COMPLETE_REGISTRATION,
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
            startActivity(intent);
        });

        btnGoLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        });

        handleDeepLink();
    }

    private void handleDeepLink() {
        Uri data = getIntent().getData();
        if (data != null) {
            Log.d("DEEPLINK", "Opened Signup screen with: " + data.toString());
        }
    }
}

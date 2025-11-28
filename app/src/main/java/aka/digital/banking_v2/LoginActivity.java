package aka.digital.banking_v2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText edtUserName, edtPassword;
    Button btnLogin;
    TextView btnGoSignup;

    String LOG_TAG = "Android-Banking-app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String afDevKey = "cYmtVpJCBSET23rRv4GWXa";
        AppsFlyerLib appsflyer = AppsFlyerLib.getInstance();
        appsflyer.setDebugLog(true);

        AppsFlyerConversionListener conversionListener =  new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionDataMap) {
                for (String attrName : conversionDataMap.keySet())
                    Log.d(LOG_TAG, "Conversion attribute: " + attrName + " = " + conversionDataMap.get(attrName));
                String status = Objects.requireNonNull(conversionDataMap.get("af_status")).toString();
                if(status.equals("Organic")){
                    // Business logic for Organic conversion goes here.
                }
                else {
                    // Business logic for Non-organic conversion goes here.
                }
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.d(LOG_TAG, "error getting conversion data: " + errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> attributionData) {
                // Must be overriden to satisfy the AppsFlyerConversionListener interface.
                // Business logic goes here when UDL is not implemented.
                Log.d(LOG_TAG, "onAppOpenAttribution : " + attributionData);
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                // Must be overriden to satisfy the AppsFlyerConversionListener interface.
                // Business logic goes here when UDL is not implemented.
                Log.d(LOG_TAG, "onAttributionFailure : " + errorMessage);
            }

        };

        appsflyer.init(afDevKey, conversionListener, this);
        AppsFlyerLib.getInstance().start(getApplicationContext(), "cYmtVpJCBSET23rRv4GWXa", new AppsFlyerRequestListener() {
            @Override
            public void onSuccess() {
                Log.d(LOG_TAG, "Launch sent successfully, got 200 response code from server");
            }

            @Override
            public void onError(int i, @NonNull String s) {
                Log.d(LOG_TAG, "Launch failed to be sent:\n" +
                        "Error code: " + i + "\n"
                        + "Error description: " + s);
            }
        });

        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoSignup = findViewById(R.id.btnGoSignup);

        // Handle login button
        btnLogin.setOnClickListener(v -> {
            String username = edtUserName.getText().toString().trim();

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("USERNAME", username); // pass the username

            AppsFlyerLib.getInstance().setCustomerIdAndLogSession(username, this);

            Map<String, Object> eventValues = new HashMap<String, Object>();
            eventValues.put("Screen Name", "Login Screen");
            eventValues.put("Deep Link", "aka://banking/login");
            eventValues.put("Screen ID", "0");

            AppsFlyerLib.getInstance().logEvent(getApplicationContext(),
                    AFInAppEventType.LOGIN,
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

        // Go to signup screen
        btnGoSignup.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });

        handleDeepLink();
    }

    private void handleDeepLink() {
        Uri data = getIntent().getData();
        if (data != null) {
            Log.d("DEEPLINK", "Opened Login screen with: " + data.toString());
        }
    }
}

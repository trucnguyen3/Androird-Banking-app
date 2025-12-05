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
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;
import com.appsflyer.deeplink.DeepLink;
import com.appsflyer.deeplink.DeepLinkListener;
import com.appsflyer.deeplink.DeepLinkResult;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText edtUserName, edtPassword;
    Button btnLogin;
    TextView btnGoSignup;

    private final String LOG_TAG = "Android-Banking-app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoSignup = findViewById(R.id.btnGoSignup);

        initAppsFlyer();

        // Login button
        btnLogin.setOnClickListener(v -> {
            String username = edtUserName.getText().toString().trim();
            if (username.isEmpty()) return;

            // Send AppsFlyer login event
            AppsFlyerLib.getInstance().setCustomerIdAndLogSession(username, this);
            Map<String, Object> eventValues = new HashMap<>();
            eventValues.put("Screen Name", "Login Screen");
            eventValues.put("Deep Link", "aka://banking/login");
            eventValues.put("Screen ID", "0");
            AppsFlyerLib.getInstance().logEvent(getApplicationContext(), AFInAppEventType.LOGIN, eventValues);

            // Start HomeActivity with optional fragment
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("USERNAME", username);
            intent.putExtra("fragment_to_open", "notifications"); // open notifications fragment after login
            startActivity(intent);
        });

        // Go to Signup
        btnGoSignup.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, SignupActivity.class))
        );

        // Handle deep links
        handleDeepLink();
    }

    private void initAppsFlyer() {
        String afDevKey = "cYmtVpJCBSET23rRv4GWXa";
        AppsFlyerLib appsflyer = AppsFlyerLib.getInstance();
        appsflyer.setDebugLog(true);
        AppsFlyerLib.getInstance().setAppInviteOneLink("vlMm");

        appsflyer.subscribeForDeepLink(new DeepLinkListener(){
            @Override
            public void onDeepLinking(@NonNull DeepLinkResult deepLinkResult) {
                DeepLinkResult.Status dlStatus = deepLinkResult.getStatus();
                if (dlStatus == DeepLinkResult.Status.FOUND) {
                    Log.d(LOG_TAG, "Deep link found");
                } else if (dlStatus == DeepLinkResult.Status.NOT_FOUND) {
                    Log.d(LOG_TAG, "Deep link not found");
                    return;
                } else {
                    // dlStatus == DeepLinkResult.Status.ERROR
                    DeepLinkResult.Error dlError = deepLinkResult.getError();
                    Log.d(LOG_TAG, "There was an error getting Deep Link data: " + dlError.toString());
                    return;
                }

                DeepLink deepLinkObj = deepLinkResult.getDeepLink();
                try {
                    Log.d(LOG_TAG, "The DeepLink data is: " + deepLinkObj.toString());
                } catch (Exception e) {
                    Log.d(LOG_TAG, "DeepLink data came back null");
                    return;
                }
                // An example for using is_deferred
                if (deepLinkObj.isDeferred()) {
                    Log.d(LOG_TAG, "This is a deferred deep link");
                } else {
                    Log.d(LOG_TAG, "This is a direct deep link");
                }

                try {
                    String deepLinkValue = deepLinkObj.getDeepLinkValue();
                    String referrerName = "";
                    String referrerID = "";
                    JSONObject dlData = deepLinkObj.getClickEvent();

                    // ** Next if statement is optional **
                    // Our sample app's user-invite carries the referrerID in deep_link_sub2
                    // See the user-invite section in FruitActivity.java
                    if (dlData.has("deep_link_sub")){
                        referrerName = deepLinkObj.getStringValue("deep_link_sub2");
                        Log.d(LOG_TAG, "The referrerID is: " + referrerName);
                    }  else {
                        Log.d(LOG_TAG, "deep_link_sub2/Referrer ID not found");
                    }

                    if (dlData.has("deep_link_sub2")){
                        referrerName = deepLinkObj.getStringValue("deep_link_sub2");
                        Log.d(LOG_TAG, "The referrerID is: " + referrerName);
                    }  else {
                        Log.d(LOG_TAG, "deep_link_sub2/Referrer ID not found");
                    }

                    if (dlData.has("deep_link_sub3")){
                        referrerID = deepLinkObj.getStringValue("deep_link_sub3");
                        Log.d(LOG_TAG, "The referrerID is: " + referrerID);
                    }  else {
                        Log.d(LOG_TAG, "deep_link_sub2/Referrer ID not found");
                    }

                    navigateByDeepLink(deepLinkValue, referrerName, referrerID);
                } catch (Exception e) {
                    Log.d(LOG_TAG, "There's been an error: " + e.toString());
                    return;
                }
            }
        });

        AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionDataMap) {
                for (String attrName : conversionDataMap.keySet())
                    Log.d(LOG_TAG, "Conversion attribute: " + attrName + " = " + conversionDataMap.get(attrName));
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.d(LOG_TAG, "error getting conversion data: " + errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> attributionData) {
                Log.d(LOG_TAG, "onAppOpenAttribution : " + attributionData);

                // Get the deep_link_value key
                String deepLinkValue = attributionData.get("deep_link_value"); // e.g., "signup", "notifications", etc.
                String deepLinkSub2 = attributionData.get("deep_link_sub2");   // e.g., "AKA Org"
                String deepLinkSub3 = attributionData.get("deep_link_sub3");   // e.g., "AA00BB1"

                if (deepLinkValue != null && !deepLinkValue.isEmpty()) {
                    Log.d(LOG_TAG, "AppsFlyer deep_link_value: " + deepLinkValue);

                    // Redirect to the correct screen
                    navigateByDeepLink(deepLinkValue, deepLinkSub2, deepLinkSub3);
                }
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d(LOG_TAG, "onAttributionFailure : " + errorMessage);
            }
        };

        appsflyer.init(afDevKey, conversionListener, this);
        AppsFlyerLib.getInstance().start(getApplicationContext(), afDevKey, new AppsFlyerRequestListener() {
            @Override
            public void onSuccess() {
                Log.d(LOG_TAG, "Launch sent successfully");
            }

            @Override
            public void onError(int i, @NonNull String s) {
                Log.d(LOG_TAG, "Launch failed: " + s);
            }
        });
    }

    private void handleDeepLink() {
        Uri data = getIntent().getData();
        if (data != null) {
            Log.d("DEEPLINK", "Opened Login screen with: " + data.toString());
            String path = data.getPath();
            if (path != null) navigateByDeepLink(path.replace("/", ""), "", "");
        }
    }

    private void navigateByDeepLink(String value, String sub2, String sub3) {
        switch (value) {
            case "signup":
                Intent signupIntent = new Intent(this, SignupActivity.class);
                signupIntent.putExtra("deep_link_sub2", sub2);
                signupIntent.putExtra("deep_link_sub3", sub3);
                startActivity(signupIntent);
                break;
            case "home":
            case "notifications":
                Intent homeIntent = new Intent(this, HomeActivity.class);
                homeIntent.putExtra("fragment_to_open", value);
                startActivity(homeIntent);
                break;
            default:
                break;
        }
    }

}

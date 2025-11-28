package aka.digital.banking_v2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText edtUserName, edtPassword;
    Button btnLogin;
    TextView btnGoSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoSignup = findViewById(R.id.btnGoSignup);

        // Handle login button
        btnLogin.setOnClickListener(v -> {
            String username = edtUserName.getText().toString().trim();

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("USERNAME", username); // pass the username
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

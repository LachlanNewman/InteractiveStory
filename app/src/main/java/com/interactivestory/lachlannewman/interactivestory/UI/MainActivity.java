package com.interactivestory.lachlannewman.interactivestory.UI;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.interactivestory.lachlannewman.interactivestory.R;

public class MainActivity extends AppCompatActivity {

    private EditText nameField;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.startbutton);
        nameField = (EditText) findViewById(R.id.nameeditText);

        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String name = nameField.getText().toString();
                Toast.makeText(MainActivity.this,name,Toast.LENGTH_LONG).show();
                StartStory(name);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        nameField.setText("");
    }

    private void StartStory(String name) {
        Intent intent = new Intent(this,StoryActivity.class);
        Resources res = getResources();
        String key = res.getString(R.string.key_name);
        intent.putExtra(key,name);
        startActivity(intent);
    }
}

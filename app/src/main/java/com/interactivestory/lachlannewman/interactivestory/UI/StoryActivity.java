package com.interactivestory.lachlannewman.interactivestory.UI;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.interactivestory.lachlannewman.interactivestory.R;
import com.interactivestory.lachlannewman.interactivestory.models.Page;
import com.interactivestory.lachlannewman.interactivestory.models.Story;

import java.util.Stack;

public class StoryActivity extends AppCompatActivity {

    private static final String TAG = StoryActivity.class.getSimpleName();

    private Story story;
    private TextView storyTextView;
    private Button choice1Button;
    private Button choice2Button;
    private ImageView storyImageView;
    private Stack<Integer> stack = new Stack<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        storyImageView = (ImageView) findViewById(R.id.storyimageView);
        storyTextView = (TextView)findViewById(R.id.storytextView);
        choice1Button = (Button)findViewById(R.id.choice1button);
        choice2Button = (Button)findViewById(R.id.choice2button);

        Intent intent = getIntent();
        String name = intent.getStringExtra(getString(R.string.key_name));
        Log.d(TAG,name);

        story = new Story();
        loadPage(0);
    }

    private void loadPage(final int pageNumber) {
        stack.push(pageNumber);
        final Page page = story.getPage(pageNumber);
        Drawable image = ContextCompat.getDrawable(this,page.getImageId());
        String text    = getString(page.getTextId());
        storyImageView.setImageDrawable(image);
        if(page.isFinalPage()){
            choice1Button.setVisibility(View.INVISIBLE);
            choice2Button.setText(R.string.play_again_button_text);
            choice2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //finish();
                    loadPage(0);
                }
            });
        }
        else {
            loadButtons(page);
        }


    }

    private void loadButtons(final Page page) {
        choice1Button.setVisibility(View.VISIBLE);
        choice2Button.setVisibility(View.VISIBLE);
        choice1Button.setText(page.getChoice1().getTextId());
        choice1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nextPage = page.getChoice1().getNextPage();
                loadPage(nextPage);
            }
        });
        choice2Button.setText(page.getChoice2().getTextId());
        choice2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nextPage = page.getChoice2().getNextPage();
                loadPage(nextPage);
            }
        });
    }

    @Override
    public void onBackPressed() {
        stack.pop();
        if(stack.empty()){
            super.onBackPressed();
        }
        else{
            loadPage(stack.pop());
        }
    }
}

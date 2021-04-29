package com.vijaya.speechtotext;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mVoiceInputTv;
    private ImageButton mSpeakBtn;
    private TextToSpeech mTts;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);
        mSpeakBtn = (ImageButton) findViewById(R.id.btnSpeak);

        mTts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    mTts.setLanguage(Locale.US);
                    mTts.speak("Hello, welcome to Text to Speech.", TextToSpeech.QUEUE_ADD,
                            null);
                    mVoiceInputTv.setText("Hello, welcome to Text to Speech.");
                } else if (status == TextToSpeech.ERROR) {
                    Toast.makeText(getApplicationContext(), "Error loading Text to Speech",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });

    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Unable to get voice input.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mVoiceInputTv.setText(result.get(0));
                    prompt(result.get(0));
                }
                break;
            }

        }
    }

    @SuppressLint("SetTextI18n")
    private void prompt(String input) {

        if (input.contains("hello")) {
            mTts.speak("What is your name?", TextToSpeech.QUEUE_ADD, null);
            mVoiceInputTv.setText("What is your name?");
        }
        else if (input.contains("my name")) {
            setName(input.split(" ")[3]);
        }
        else if (input.contains("not feeling well")) {
            mTts.speak("I can understand. Please tell your symptoms in short.",
                    TextToSpeech.QUEUE_FLUSH, null);
            mVoiceInputTv.setText("I can understand. Please tell your symptoms in short.");
        }
        else if (input.contains("thank you")) {
            mTts.speak("Thank you too. Take care",
                    TextToSpeech.QUEUE_FLUSH, null);
            mVoiceInputTv.setText("Thank you too. Take care");
        }
        else if (input.contains("medicines")) {
            mTts.speak("I think you have a fever. Please take Advil or Tylenol.",
                    TextToSpeech.QUEUE_FLUSH, null);
            mVoiceInputTv.setText("I think you have a fever. Please take Advil or Tylenol.");
        }
    }

    @SuppressLint("SetTextI18n")
    private void setName(String name) {
        preferences = getSharedPreferences("prefs",0);
        editor = preferences.edit();
        editor.putString("name", name).apply();

        mTts.speak("Good to meet you " + name, TextToSpeech.QUEUE_FLUSH, null);
        mVoiceInputTv.setText("Good to meet you " + name);
    }
}
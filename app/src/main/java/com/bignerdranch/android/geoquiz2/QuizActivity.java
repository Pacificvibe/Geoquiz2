package com.bignerdranch.android.geoquiz2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mPreviousButton;
    private ImageButton mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextview;

    private Question[] mQuestionBank = new Question[]{   //Array of question objects
            new Question(R.string.question_oceans, true),  // true or false for the appropriate answers.
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextview.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue(); /*checks question bank to
    check if its true */

        int messageResId = 0;

        if(mIsCheater) {
            messageResId = R.string.judgement_toast;
        }else{

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

              @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate(Bundle)called");
        setContentView(R.layout.activity_quiz);   //comes from r.java file

                @Override
                protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            if (requestCode == REQUEST_CODE_CHEAT) {
                if (data ==null) {
                    return;
                }
                mIsCheater = CheatActivity.wasAnswerShown(data);
            }
        }

        mQuestionTextview = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length; /* the "+ 1" advances current
                screen(index) to the next page */
                updateQuestion();

            }
        });


        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener()

                                       {
                                           @Override
                                           public void onClick(View v) {
                                               checkAnswer(true);


                                           }
                                       }

        );
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener()

                                        {
                                            @Override
                                            public void onClick(View v) {
                                                checkAnswer(false);

                                            }
                                        }

        );

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }

        });

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        updateQuestion();


        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length; /* the "- 1" advances current
                screen(index) to the previous page */
                updateQuestion();
            }
        });

        updateQuestion();


            mNextButton=(ImageButton) findViewById(R.id.next_button);
            mNextButton.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length; /* the "+ 1" advances current
                screen(index) to the next page */
                    mIsCheater=false;
                updateQuestion();
            }
            }

            );

            updateQuestion();
        }

    @Override
    public void onSaveInstanceState (Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }
    }

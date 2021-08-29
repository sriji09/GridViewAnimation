package com.example.gridviewanimation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private AnimatorSet flipAnimationFront, flipAnimationBack;
    private GridView gridList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridList = findViewById(R.id.gridList);

        float scaleFactor = getResources().getDisplayMetrics().density * 100;
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int columns = (int) ((float) displaymetrics.widthPixels / scaleFactor);
        gridList.setNumColumns(columns);

        ArrayList<ValueModel> valueModelArrayList = new ArrayList<>();
        for(int i=0; i<6; i++)
            for(int j=0; j<4; j++)
                valueModelArrayList.add(new ValueModel(j, i));

        GridValueAdapter gridValueAdapter = new GridValueAdapter(this, valueModelArrayList);
        gridList.setAdapter(gridValueAdapter);

        gridList.setOnItemClickListener((parent, view, position, id) -> {
            CardView gridCard = view.findViewById(R.id.GridCard);
            CardView backCard = view.findViewById(R.id.backCard);

            float scale = this.getResources().getDisplayMetrics().density;
            gridCard.setCameraDistance(8000*scale);
            backCard.setCameraDistance(8000*scale);

            final Animation animation1 = AnimationUtils.makeOutAnimation(this, true);
            animation1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}
                @Override
                public void onAnimationEnd(Animation animation) {
                    final AutoTransition autoTransition = new AutoTransition();
                    autoTransition.setDuration(250L);
                    TransitionManager.beginDelayedTransition(gridList, autoTransition);
                    gridValueAdapter.notifyDataSetChanged();
                }
                @Override
                public void onAnimationRepeat(Animation animation) {}
            });

            flipAnimationFront = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flip_animator_front);
            flipAnimationBack = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flip_animator_back);

            flipAnimationFront.setTarget(gridCard);
            flipAnimationBack.setTarget(backCard);
            flipAnimationFront.start();
            flipAnimationBack.start();
            flipAnimationBack.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    valueModelArrayList.remove(position);
                    view.startAnimation(animation1);
                }
            });
        });
    }
}
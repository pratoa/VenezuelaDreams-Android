package com.example.andresprato.venezueladream;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.andresprato.venezueladream.cards.SliderAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String defaultImageURL = "https://i.stack.imgur.com/l60Hf.png";

    private ArrayList<Child> children = new ArrayList<>();
    private ArrayList<String> URLs = new ArrayList<>();

    private SliderAdapter sliderAdapter;

    private CardSliderLayoutManager layoutManger;
    private RecyclerView recyclerView;
    private TextView bioTextView;
    private TextSwitcher descriptionsSwitcher;

    private TextView child1TextView;
    private TextView child2TextView;
    private int childOffset1;
    private int childOffset2;
    private long childAnimDuration;
    private int currentPosition;

    //get donate button, set listener, send child_id, age, name, photo
    Button donateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DatabaseReference firebaseRef = FirebaseDatabase.getInstance().getReference();
        firebaseRef.getRoot().child("/child").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childData : dataSnapshot.getChildren()) {
                    Child child = new Child(
                            childData.child("child_id").getValue(String.class),
                            childData.child("first_name").getValue(String.class),
                            childData.child("last_name").getValue(String.class),
                            childData.child("description").getValue(String.class),
                            childData.child("img_url").getValue(String.class),
                            childData.child("date_of_birth").getValue(String.class));

                    if (child.getImageUrl() == null) {
                        URLs.add(defaultImageURL);
                    } else {
                        URLs.add(child.getImageUrl());
                    }

                    Log.v("TACOOO", "Child added!");
                    Log.v("TACOOO", child.getFirstName());
                    Log.v("TACOOO", child.getLastName());
                    Log.v("TACOOO", child.getDescription());

                    children.add(child);
                }

                sliderAdapter = new SliderAdapter(getApplicationContext(), URLs, children.size(), new OnCardClickListener());

                initRecyclerView();
                initCountryText();
                initSwitchers();
                donateButton = findViewById(R.id.donateButton);
                donateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, DonationActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("name", children.get(currentPosition).getFirstName() + " " + children.get(currentPosition).getLastName());
                        bundle.putString("imageURL", children.get(currentPosition).getImageUrl());
                        bundle.putInt("age", children.get(currentPosition).getAge());
                        bundle.putString("id", children.get(currentPosition).getId());

                        intent.putExtras(bundle);

                        startActivity(intent);

                    }

                });
                //initGreenDot();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //go to settings
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.log_out:
                //log out
                return true;
        }

        return true;
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(sliderAdapter);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onActiveCardChange();
                }
            }
        });



        layoutManger = (CardSliderLayoutManager) recyclerView.getLayoutManager();

        new CardSnapHelper().attachToRecyclerView(recyclerView);
    }

    private void initSwitchers() {

        bioTextView = (TextView) findViewById(R.id.ts_place);
        bioTextView.setText("Bio:");

        descriptionsSwitcher = (TextSwitcher) findViewById(R.id.ts_description);
        descriptionsSwitcher.setInAnimation(this, android.R.anim.fade_in);
        descriptionsSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        descriptionsSwitcher.setFactory(new TextViewFactory(R.style.DescriptionTextView, false));
        descriptionsSwitcher.setCurrentText(children.get(0).getDescription());

    }

    private void initCountryText() {
        childAnimDuration = getResources().getInteger(R.integer.labels_animation_duration);
        childOffset1 = getResources().getDimensionPixelSize(R.dimen.left_offset);
        childOffset2 = getResources().getDimensionPixelSize(R.dimen.card_width);
        child1TextView = (TextView) findViewById(R.id.tv_country_1);
        child2TextView = (TextView) findViewById(R.id.tv_country_2);


        child1TextView.setX(childOffset1);
        child2TextView.setX(childOffset2);
        child1TextView.setText(children.get(0).getFirstName() + ", " + children.get(0).getAge());
        child2TextView.setAlpha(0f);
    }

    private void setCountryText(String text, boolean left2right) {
        final TextView invisibleText;
        final TextView visibleText;
        if (child1TextView.getAlpha() > child2TextView.getAlpha()) {
            visibleText = child1TextView;
            invisibleText = child2TextView;
        } else {
            visibleText = child2TextView;
            invisibleText = child1TextView;
        }

        final int vOffset;
        if (left2right) {
            invisibleText.setX(0);
            vOffset = childOffset2;
        } else {
            invisibleText.setX(childOffset2);
            vOffset = 0;
        }

        invisibleText.setText(text);

        final ObjectAnimator iAlpha = ObjectAnimator.ofFloat(invisibleText, "alpha", 1f);
        final ObjectAnimator vAlpha = ObjectAnimator.ofFloat(visibleText, "alpha", 0f);
        final ObjectAnimator iX = ObjectAnimator.ofFloat(invisibleText, "x", childOffset1);
        final ObjectAnimator vX = ObjectAnimator.ofFloat(visibleText, "x", vOffset);

        final AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(iAlpha, vAlpha, iX, vX);
        animSet.setDuration(childAnimDuration);
        animSet.start();
    }

    private void onActiveCardChange() {
        final int pos = layoutManger.getActiveCardPosition();
        if (pos == RecyclerView.NO_POSITION || pos == currentPosition) {
            return;
        }

        onActiveCardChange(pos);
    }

    private void onActiveCardChange(int pos) {
        int animH[] = new int[] {R.anim.slide_in_right, R.anim.slide_out_left};
        int animV[] = new int[] {R.anim.slide_in_top, R.anim.slide_out_bottom};

        final boolean left2right = pos < currentPosition;
        if (left2right) {
            animH[0] = R.anim.slide_in_left;
            animH[1] = R.anim.slide_out_right;

            animV[0] = R.anim.slide_in_bottom;
            animV[1] = R.anim.slide_out_top;
        }

        Child childMovingTo = children.get(pos % children.size());
        setCountryText(childMovingTo.getFirstName() + ", " + childMovingTo.getAge(), left2right);

        descriptionsSwitcher.setText(children.get(pos % children.size()).getDescription());

        currentPosition = pos;
    }

    private class TextViewFactory implements  ViewSwitcher.ViewFactory {

        @StyleRes
        final int styleId;
        final boolean center;

        TextViewFactory(@StyleRes int styleId, boolean center) {
            this.styleId = styleId;
            this.center = center;
        }

        @SuppressWarnings("deprecation")
        @Override
        public View makeView() {
            final TextView textView = new TextView(MainActivity.this);

            if (center) {
                textView.setGravity(Gravity.CENTER);
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                textView.setTextAppearance(MainActivity.this, styleId);
            } else {
                textView.setTextAppearance(styleId);
            }

            return textView;
        }

    }

    private class ImageViewFactory implements ViewSwitcher.ViewFactory {
        @Override
        public View makeView() {
            final ImageView imageView = new ImageView(MainActivity.this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            final ViewGroup.LayoutParams lp = new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(lp);

            return imageView;
        }
    }

    private class OnCardClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final CardSliderLayoutManager lm =  (CardSliderLayoutManager) recyclerView.getLayoutManager();

            if (lm.isSmoothScrolling()) {
                return;
            }

            final int activeCardPosition = lm.getActiveCardPosition();
            if (activeCardPosition == RecyclerView.NO_POSITION) {
                return;
            }

            final int clickedPosition = recyclerView.getChildAdapterPosition(view);
            if (clickedPosition == activeCardPosition) {
                final Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra(DetailsActivity.BUNDLE_IMAGE_ID, URLs.get(activeCardPosition % children.size()));

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent);
                } else {
                    final CardView cardView = (CardView) view;
                    final View sharedView = cardView.getChildAt(cardView.getChildCount() - 1);
                    final ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(MainActivity.this, sharedView, "shared");
                    startActivity(intent, options.toBundle());
                }
            } else if (clickedPosition > activeCardPosition) {
                recyclerView.smoothScrollToPosition(clickedPosition);
                onActiveCardChange(clickedPosition);
            }
        }
    }

}

package com.example.android.azkya_1202153371_modul6;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailPost extends AppCompatActivity {
    TextView mUsername, mTitlePost, mPost;
    ImageView mImagePost;
    EditText et_comment;
    DatabaseReference databaseComments;
    DatabaseReference databaseUser;
    private RecyclerView recyclerView;
    FirebaseAuth mAuth;
    private ArrayList<Comment> listComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        mAuth = FirebaseAuth.getInstance();
        //find Intent from Main Activity
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String username = intent.getStringExtra("Username");
        String image = intent.getStringExtra("image");
        String mTitle = intent.getStringExtra("Title");
        String mDescription = intent.getStringExtra("Post");

        databaseComments = FirebaseDatabase.getInstance().getReference(Home.table2).child(id);
        databaseUser = FirebaseDatabase.getInstance().getReference(Home.table3);

        recyclerView = findViewById(R.id.recyclerViewComment);

        listComments = new ArrayList<>();

        mUsername = findViewById(R.id.tv_username);
        mImagePost = findViewById(R.id.img_post);
        mTitlePost = findViewById(R.id.tv_title_post);
        mPost = findViewById(R.id.tv_post);

        Glide.with(DetailPost.this).load(image).into(mImagePost);

        et_comment = findViewById(R.id.et_comment);

        mUsername.setText(username);
        mTitlePost.setText(mTitle);
        mPost.setText(mDescription);
    }


    public void addComment(View view) {
        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                @SuppressLint("RestrictedApi") User user = dataSnapshot.child(mAuth.getUid()).getValue(User.class);
                String textReview = et_comment.getText().toString().trim();
                if (!TextUtils.isEmpty(textReview)) {

                    String id = databaseComments.push().getKey();
                    long timestamp = System.currentTimeMillis();

                    Comment track = new Comment(id, user.getUsername(), textReview,(0-timestamp));
                    databaseComments.child(id).setValue(track);
                    Toast.makeText(DetailPost.this, "Comment Sent", Toast.LENGTH_LONG).show();
                    et_comment.setText("");
                } else {
                    Toast.makeText(DetailPost.this, "Please enter Comment", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseComments.orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listComments.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Comment comment = postSnapshot.getValue(Comment.class);

                    listComments.add(comment);
                }
                recyclerView.setHasFixedSize(true);

                recyclerView.setLayoutManager(new GridLayoutManager(DetailPost.this, 1));

                CommentAdapter commentList = new CommentAdapter(DetailPost.this, listComments);

                recyclerView.setAdapter(commentList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

package com.example.entregaaplicacionesmoviles.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.entregaaplicacionesmoviles.R;
import com.example.entregaaplicacionesmoviles.list.Adapter.RecommedAdapter;
import com.example.entregaaplicacionesmoviles.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.atomic.AtomicBoolean;

public class RecommendActivity extends AppCompatActivity implements RecommedAdapter.OnRecommendClickListener {

    private RecyclerView recommednList;
    private Button continueBut;
    private RecommedAdapter recommedAdapter;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        recommednList = findViewById(R.id.recommendList);
        continueBut = findViewById(R.id.continueBut);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recommedAdapter = new RecommedAdapter();
        recommedAdapter.setListener(this);

        recommednList.setAdapter(recommedAdapter);
        recommednList.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recommednList.setLayoutManager(manager);

        continueBut.setOnClickListener(this::clickContinue);

    }

    public void loadRecommendList(){
        Query userReference = db.collection("users").limit(4);
        if(userReference!=null){
            userReference.get().addOnCompleteListener(
                    task -> {
                        recommedAdapter.clear();
                        for(QueryDocumentSnapshot doc : task.getResult()){
                            User user = doc.toObject(User.class);
                            if (!user.getId().equals(auth.getCurrentUser().getUid())){
                                recommedAdapter.addUserToRecommend(user);
                            }

                        }
                    }
            );
        }


    }

    private void clickContinue(View view) {
        AtomicBoolean isFollowing = new AtomicBoolean(false);
        db.collection("users").document(auth.getCurrentUser().getUid()).collection("following").get().addOnCompleteListener( task -> {
            if(task.isSuccessful()){
                if(task.getResult().size()>1){
                    Intent intent = new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(this,"Sigue al menos a 2 personas",Toast.LENGTH_LONG).show();
                }
            }

        });

    }

    @Override
    protected void onResume() {
        loadRecommendList();
        super.onResume();
    }

    @Override
    public void onRecommendClick(User user) {
        db.collection("users").document(auth.getCurrentUser().getUid()).collection("following").document(user.getId()).set(user);
        recommedAdapter.removeRecommend(user);
    }
}
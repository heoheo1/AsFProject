package com.hj.AsFProject;

import AsFProject.R;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;


public class WriteActivity extends AppCompatActivity {
    EditText edt_title,edt_contents;
    Button btn_OK;
    SQLiteDatabase db;
    ImageView imageView;
    String spemail,spsubject;
    Uri selectedImageUri;
    String  databaseName ="MemberDB";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        edt_title =findViewById(R.id.edt_title);
        edt_contents=findViewById(R.id.edt_contents);
        btn_OK=findViewById(R.id.btn_OK);
        imageView=findViewById(R.id.imageView);
        DBHelper dbHelper =new DBHelper(this,databaseName,null,1,"member"+spemail);
        db=dbHelper.getWritableDatabase();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,0);

            }
        });

        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=edt_title.getText().toString();
                String contents=edt_contents.getText().toString();
                sharedPreferences = getSharedPreferences("pref", MODE_PRIVATE);
                spemail = sharedPreferences.getString("email", "");
                spsubject=sharedPreferences.getString("subject","");
                ContentValues contentValues = new ContentValues();
                contentValues.put("Image", String.valueOf(selectedImageUri));
                contentValues.put("Title",title);
                contentValues.put("Contents",contents);
                contentValues.put("Subject",spsubject);
                db.insert("member"+spemail, null, contentValues); //???????????? ???????????? ??????
                Intent intent = new Intent(getApplicationContext(), MainActivity.class); //???????????? ?????????  ???????????? ??????
                startActivity(intent);
                finish();


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                try {

                    selectedImageUri =data.getData();
                    Glide.with(getApplicationContext()).load(selectedImageUri).into(imageView);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "?????? ?????? ??????", Toast.LENGTH_LONG).show();
        }
    }

}
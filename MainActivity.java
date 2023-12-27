package com.example.test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button btn1, btn2, btn3, btn4;

    private ImageView iv;

    private Bitmap bm;

    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bm = null;
        handler = new Handler();

        iv = findViewById(R.id.imageView);

        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.button3);
        btn3 = findViewById(R.id.button4);
        btn4 = findViewById(R.id.button5);

        btn1.setOnClickListener(myListener);
        btn2.setOnClickListener(myListener);
        btn3.setOnClickListener(myListener);
        btn4.setOnClickListener(myListener);


    }

    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch ((view.getId())) {
                case R.id.button:
                    downloadImageAndShow(1, 1);
                    break;
                case R.id.button3:
                    downloadImageAndShow(1, 2);
                    break;
                case R.id.button4:
                    downloadImageAndShow(2, 1);
                    break;
                case R.id.button5:
                    downloadImageAndShow(2, 2);
                    break;
            }

        }
    };



   private void downloadImageAndShow(final int type,final int id){

       new Thread(new Runnable() {
           @Override
           public void run() {
               OkHttpClient client = new OkHttpClient();

               //GET 部分 頭 part 1
               Request request = new Request.Builder()
                       .url("http://10.20.22.242:8080/MyServlet/ImageShower?"
                       + "type=" + type + "&id=" + id).build();
               //GET 部分 尾

               //GET 部分 頭 part 2
               Response response;
               try {
                   response = client.newCall(request).execute();
               } catch (IOException e) {
                   e.printStackTrace();
                   return;
               }

               if(!response.isSuccessful())
                   return;

               byte[] b = null;
               try{
                   b = response.body().bytes();
               } catch (IOException e) {
                   e.printStackTrace();
                   return;
               }

               bm = BitmapFactory.decodeByteArray(b, 0,b.length);
               handler.post(new Runnable() {
                   @Override
                   public void run() {
                       iv.setImageBitmap(bm);
                   }
               });

               //GET 部分 尾 part2

               /**/
               //POST 部分 頭 part 1
               /*
               RequestBody formBody = new FormBody.Builder()
                       .add("type",""+type)
                       .add("id","+id")
                       .build();
               Request request = new Request.Builder()
                       .url("http://192.168.8.171:8080/NewServlet/ImageShower")
                       .post(formBody)
                       .build();

               //POST 部分 尾


               //POST 部分 頭 part 2

               Response response;

               client.newCall(request).enqueue(new Callback() {
                   @Override
                   public void onFailure(@NonNull Call call, @NonNull IOException e) {
                       e.printStackTrace();
                   }

                   @Override
                   public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                       if(!response.isSuccessful())
                           return;

                       byte[] b = null;
                       try {
                           b = response.body().bytes();
                       } catch (IOException e){
                           e.printStackTrace();
                           return;
                       }

                       bm = BitmapFactory.decodeByteArray(b,0,b.length);
                       handler.post(new Runnable() {
                           @Override
                           public void run() {
                               iv.setImageBitmap(bm);
                           }
                       });
                   }
               });

                */
               //POST 部分 尾 part 2

           }
       }).start();
   }
}

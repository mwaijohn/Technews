package com.honetware.technology.technews.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.honetware.technology.technews.R;
import com.honetware.technology.technews.WebViewActivity;
import com.honetware.technology.technews.controllers.GetArticles;
import com.honetware.technology.technews.model.Article;
import com.honetware.technology.technews.model.StatusPayload;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.Constants;
import utils.RetrofitClientInstance;
import utils.Utils;

import static android.app.PendingIntent.FLAG_ONE_SHOT;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        if (Utils.isOnline(context)){
            GetArticles service = RetrofitClientInstance.getRetrofitInstance().create(GetArticles.class);
            Call<StatusPayload> call = service.getArticleResponse(context.getString(R.string.menu_techcrunch),10,
                    Constants.Companion.getLanguage(),
                    Constants.Companion.getApi_key());

            call.enqueue(new Callback<StatusPayload>() {
                @Override
                public void onResponse(Call<StatusPayload> call, Response<StatusPayload> response) {
                    List<Article> articleList = response.body().getArticles();

                    Random random = new Random();
                    Article article = null;
                    if (articleList.size() > 0){
                        int randomInt = random.nextInt(articleList.size()) ;
                        article = articleList.get(randomInt == 0 ? randomInt + 1 : randomInt );
                    }

                    Intent myIntent = new Intent(context, WebViewActivity.class);

                    myIntent.putExtra("url", article.getUrl());
                    myIntent.putExtra("notification", "FROM_NOTIFICATION");

                    PendingIntent pendingIntent = PendingIntent.getActivity(
                            context,
                            0,
                            myIntent,
                            FLAG_ONE_SHOT );

                    builder.setAutoCancel(true)
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(article.getTitle())
                            .setContentIntent(pendingIntent)
                            .setContentText(article.getDescription())
                            .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
                            //.setContentInfo();

                    NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1,builder.build());

                }

                @Override
                public void onFailure(Call<StatusPayload> call, Throwable t) {

                }
            });
        }
    }
}

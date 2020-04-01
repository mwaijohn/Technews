package utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.honetware.technology.technews.R;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import adapter.ArticleListAdapter;
import com.honetware.technology.technews.controllers.GetArticles;
import com.honetware.technology.technews.model.Article;
import com.honetware.technology.technews.model.StatusPayload;
import com.honetware.technology.technews.notification.AlarmReceiver;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Utils {

    public static void general(Context context, RecyclerView recyclerView, TextView textView,
                               String news_source,int page_size,SwipeRefreshLayout swipeRefreshLayout){

        if(isOnline(context)){
            GetArticles service = RetrofitClientInstance.getRetrofitInstance().create(GetArticles.class);
            Call<StatusPayload> call = service.getArticleResponse(news_source,page_size,
                    Constants.Companion.getLanguage(),
                    Constants.Companion.getApi_key());

            swipeRefreshLayout.setRefreshing(true);
            // SwipeRefreshLayout
            swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                    android.R.color.holo_green_dark,
                    android.R.color.holo_orange_dark,
                    android.R.color.holo_blue_dark);

            call.enqueue(new Callback<StatusPayload>() {
                @Override
                public void onResponse(Call<StatusPayload> call, Response<StatusPayload> response) {

                    if (response.code() == 200){
                        StatusPayload statusPayload = response.body();
                        List<Article> articles = statusPayload.getArticles();

                        if (articles.size()<1){
                            textView.setVisibility(View.VISIBLE);
                        }

                        // Toast.makeText(context, articles.get(0).getTitle(), Toast.LENGTH_SHORT).show();
                        ArticleListAdapter articleListAdapter = new   ArticleListAdapter(context,articles);

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(articleListAdapter);

                        swipeRefreshLayout.setRefreshing(false);
                    }else {
                        textView.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setRefreshing(false);
                        textView.setText(R.string.connetion_error);
                    }
                }

                @Override
                public void onFailure(Call<StatusPayload> call, Throwable t) {
                    textView.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(R.string.no_internet);
        }
    }


    public static void generalCategories(Context context, RecyclerView recyclerView, TextView textView,
                               String category,int pageSize,SwipeRefreshLayout swipeRefreshLayout){

        if(isOnline(context)){
            GetArticles service = RetrofitClientInstance.getRetrofitInstance().create(GetArticles.class);
            Call<StatusPayload> call = service.getCategoryResponse(category,pageSize,
                    Constants.Companion.getLanguage(),
                    Constants.Companion.getSort_by(),
                    Constants.Companion.getApi_key());

            swipeRefreshLayout.setRefreshing(true);
            // SwipeRefreshLayout
            swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                    android.R.color.holo_green_dark,
                    android.R.color.holo_orange_dark,
                    android.R.color.holo_blue_dark);

            call.enqueue(new Callback<StatusPayload>() {
                @Override
                public void onResponse(Call<StatusPayload> call, Response<StatusPayload> response) {

                    if (response.code() == 200){
                        StatusPayload statusPayload = response.body();
                        List<Article> articles = statusPayload.getArticles();

                        if (articles.size()<1){
                            textView.setVisibility(View.VISIBLE);
                        }

                        ArticleListAdapter articleListAdapter = new   ArticleListAdapter(context,articles);

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(articleListAdapter);

                        swipeRefreshLayout.setRefreshing(false);
                    }else {
                        textView.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setRefreshing(false);

                        textView.setText(R.string.connetion_error);
                    }
                }

                @Override
                public void onFailure(Call<StatusPayload> call, Throwable t) {
                    textView.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(R.string.no_internet);
        }
    }

    public static String toReadableDate(String date){
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date parsed;

        {
            try {
                parsed = parser.parse(date);
                return parsed.toString();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return date;
    }

    public static Boolean isOnline(Context mContext) {

        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }


    public static  void startAlarm(Context context, boolean isNotification, boolean isRepeat) {
        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;

        // SET TIME HERE
        Calendar calendar= Calendar.getInstance();

        String alarmSet = Prefs.Companion.read(Constants.Companion.getAlarm_set(),null);
        if ( alarmSet == null){

            calendar.set(Calendar.HOUR_OF_DAY,13);
            calendar.set(Calendar.MINUTE,17);

            myIntent = new Intent(context, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(context,0,myIntent,0);

            if(!isRepeat)
                manager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+3000,pendingIntent);
            else
                manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);

            Prefs.Companion.write(Constants.Companion.getAlarm_set(),"set");

            //Log.d("alarm___","alarm not set setting up");
        }else {
            //SharedPref.write(Constants.Companion.getAlarm_set(),"set");

            Prefs.Companion.write(Constants.Companion.getAlarm_set(),"set");
            //Log.d("alarm_","alarmset");
        }

    }

    public static boolean Ping(){

        Runtime runtime = Runtime.getRuntime();
        try
        {
            Process  mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 " + "www.google.com");
            int mExitValue = mIpAddrProcess.waitFor();

            if(mExitValue==0){
                return true;
            }else{
                return false;
            }
        }
        catch (InterruptedException ignore)
        {
            ignore.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}

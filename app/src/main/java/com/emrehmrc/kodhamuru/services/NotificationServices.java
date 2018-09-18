package com.emrehmrc.kodhamuru.services;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.emrehmrc.kodhamuru.MainActivity;
import com.emrehmrc.kodhamuru.R;
import com.emrehmrc.kodhamuru.model.BlogNotif;
import com.emrehmrc.kodhamuru.soap.BlogNotifSoap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationServices extends Service {
    final static int PERIOD = 30000;
    final static int DELAY = 0;
    Context context;
    Notification notification;
    Timer timer;
    BlogNotifSoap blogNotifSoap;
    ArrayList<BlogNotif> arrayList;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        context = getApplicationContext();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getNotifications();
            }
        }, DELAY, PERIOD);
        blogNotifSoap = new BlogNotifSoap();
        arrayList = new ArrayList<>();
    }

    private void getNotifications() {
        Notifications notifications = new Notifications();
        notifications.execute("");
    }

    private void sendNotification(String msj) {
        long when = System.currentTimeMillis();//notificationın ne zaman gösterileceği
        String baslik = msj;//notification başlık
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pending = PendingIntent.getActivity(context, 0, intent, 0);//Notificationa tıklanınca açılacak activityi belirliyoruz
        notification = new Notification(R.mipmap.ic_launcher, "Yeni Bildirim", when);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {

            try {
                Method deprecatedMethod = notification.getClass().getMethod("setLatestEventInfo", Context.class, CharSequence.class, CharSequence.class, PendingIntent.class);
                deprecatedMethod.invoke(notification, context, baslik, "cevap", pending);
            } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {

            }
        } else {
            // Use new API
            Notification.Builder builder = new Notification.Builder(context)
                    .setContentIntent(pending)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(baslik);
            notification = builder.build();
        }


        notification.flags |= Notification.FLAG_AUTO_CANCEL;//notificationa tıklanınca notificationın otomatik silinmesi için
        notification.defaults |= Notification.DEFAULT_SOUND;//notification geldiğinde bildirim sesi çalması için
        notification.defaults |= Notification.DEFAULT_VIBRATE;//notification geldiğinde bildirim titremesi için
        nm.notify(0, notification);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer.purge();

    }

    private class Notifications extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {

        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected void onPostExecute(String r) {
            if (arrayList.size() > 0) {
                sendNotification(arrayList.get(0).getCOMMENTCOUNT());
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                arrayList = blogNotifSoap.getAll();
            } catch (Exception ex) {
                Log.e("HATA", ex.getMessage());
            }

            return "";
        }
    }
}

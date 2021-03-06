package pers.jxnu.maguozhuang.jxnuwirelessnethelper;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
/**
 * 后台服务：在条件允许的情况下执行自动登录
 */
public class LoginIntentService extends IntentService
{
    public static final String WifiName="\"jxnu_stu\"";

    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "pers.jxnu.maguozhuang.jxnuwirelessnethelper.action.FOO";
    private static final String ACTION_BAZ = "pers.jxnu.maguozhuang.jxnuwirelessnethelper.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "pers.jxnu.maguozhuang.jxnuwirelessnethelper.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "pers.jxnu.maguozhuang.jxnuwirelessnethelper.extra.PARAM2";

    private AllMyInfo mAllMyInfo;

    public LoginIntentService()
    {
        super("LoginIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2)
    {
        Intent intent = new Intent(context, LoginIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2)
    {
        Intent intent = new Intent(context, LoginIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        if (intent != null)
        {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action))
            {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action))
            {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2)
    {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2)
    {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate()
    {
        mAllMyInfo=AllMyInfo.getInstance(getApplicationContext());
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId)
    {
        WifiManager wifiManager=(WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo=wifiManager.getConnectionInfo();
        String ssid=wifiInfo.getSSID();
        if(ssid.equals(WifiName))
        {
            if(mAllMyInfo.isAutoLogin())//检查是否自动登录
            {
                LoginAsyncTask task=new LoginAsyncTask(LoginAsyncTask.LoginMode,(s)->{
                    showNotification(s);
                });
                task.execute(mAllMyInfo);
            }

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    //显示通知信息
    private void showNotification(String str)
    {
        if(!mAllMyInfo.isShowNotification()) return;
        if(str.contains("login_ok"))
            str="登录成功";
        NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification=new NotificationCompat.Builder(this)
                .setContentTitle("Hi")
                .setContentText(str)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(pers.jxnu.maguozhuang.jxnuwirelessnethelper.R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), pers.jxnu.maguozhuang.jxnuwirelessnethelper.R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .build();
        manager.notify(1,notification);
    }
}

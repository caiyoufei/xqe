package pansong291.xposed.quickenergy.antForest;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

//蚂蚁森林通知栏
public class AntForestNotification {
  //通知id
  public static final int ANTFOREST_NOTIFICATION_ID = 46;
  //8.0通知分组
  public static final String CHANNEL_ID = "xqe.yuji.ANTFOREST_NOTIFY_CHANNEL";
  //通知管理
  private static NotificationManager mNotifyManager;
  //通知
  private static Notification mNotification;
  //更新通知的Builder
  private static Notification.Builder builder;
  //通知是否开启
  private static boolean isStart = false;

  private AntForestNotification() {
  }

  //开启通知
  public static void start(Context context) {
    //初始化通知
    initNotification(context);
    //如果没有开启才开启
    if (!isStart) {
      if (context instanceof Service) {
        //设置前台通知
        ((Service) context).startForeground(ANTFOREST_NOTIFICATION_ID, mNotification);
      } else {
        //更新通知栏
        getNotificationManager(context).notify(ANTFOREST_NOTIFICATION_ID, mNotification);
      }
      isStart = true;
    }
  }

  //更新状态栏的内容
  public static void setContentText(CharSequence cs) {
    if (isStart) {
      //更新通知
      mNotification = builder.setContentText(cs).build();
      //刷新到通知栏
      if (mNotifyManager != null) mNotifyManager.notify(ANTFOREST_NOTIFICATION_ID, mNotification);
    }
  }

  //关闭通知
  public static void stop(Context context, boolean remove) {
    //如果开启了才关闭
    if (isStart) {
      if (context instanceof Service) {
        //停止前台通知
        ((Service) context).stopForeground(remove);
      } else {
        //取消通知栏的通知
        getNotificationManager(context).cancel(ANTFOREST_NOTIFICATION_ID);
      }
      isStart = false;
    }
  }

  //初始化通知
  private static void initNotification(Context context) {
    //没有通知就创建通知
    if (mNotification == null) {
      //创建通知跳转
      Intent it = new Intent(Intent.ACTION_VIEW);
      //启动H5App http://myjsapi.alipay.com/jsapi/h5app-lifecycle.html#4__E9_80_9A_E8_BF_87scheme_E5_90_AF_E5_8A_A8
      it.setData(Uri.parse("alipays://platformapi/startapp?appId="));
      PendingIntent pi = PendingIntent.getActivity(context, 0, it, PendingIntent.FLAG_UPDATE_CURRENT);
      //8.0需要使用通知栏分组
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel nc = new NotificationChannel(CHANNEL_ID, "XQuickEnergy能量提醒", NotificationManager.IMPORTANCE_LOW);
        //设置通知出现时的闪灯（如果 android 设备支持的话）
        nc.enableLights(false);
        //设置通知出现时的震动（如果 android 设备支持的话）
        nc.enableVibration(false);
        //是否显示角标
        nc.setShowBadge(false);
        //初始化通知管理
        getNotificationManager(context).createNotificationChannel(nc);
        //创建通知栏builder
        builder = new Notification.Builder(context, CHANNEL_ID);
      } else {
        //初始化通知管理
        getNotificationManager(context);
        //创建通知栏builder
        builder = new Notification.Builder(context).setPriority(Notification.PRIORITY_LOW);
      }
      //创建通知
      mNotification = builder
          //通知栏小图标
          .setSmallIcon(android.R.drawable.sym_def_app_icon)
          //通知栏标题
          .setContentTitle("XQE·雨季")
          //通知栏内容
          .setContentText("正在检测好友")
          //设置点击自动消失
          .setAutoCancel(false)
          //设置延时意图
          .setContentIntent(pi)
          .build();
    }
  }

  //获取通知栏管理
  private static NotificationManager getNotificationManager(Context context) {
    if (mNotifyManager == null) mNotifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    return mNotifyManager;
  }
}

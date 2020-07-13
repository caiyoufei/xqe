package pansong291.xposed.quickenergy.antForest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;
import pansong291.xposed.quickenergy.hook.XposedHook;
import pansong291.xposed.quickenergy.util.Config;
import pansong291.xposed.quickenergy.util.Log;

//蚂蚁森林吐司
public class AntForestToast {
  //打印的tag
  private static final String TAG = AntForestToast.class.getCanonicalName();
  //Application
  @SuppressLint("StaticFieldLeak")
  public static Context context;

  //消息吐司
  public static void show(CharSequence cs) {
    try {
      if (context != null && Config.showToast()) {
        XposedHook.handler.post(
            //线程
            new Runnable() {
              //需要显示的文字
              CharSequence cs;

              //设置需要吐司的文字
              public Runnable setData(CharSequence c) {
                cs = c;
                return this;
              }

              @Override
              public void run() {
                try {
                  //执行吐司
                  Toast.makeText(context, cs, Toast.LENGTH_SHORT).show();
                } catch (Throwable t) {
                  Log.i(TAG, "show.run err:");
                  Log.printStackTrace(TAG, t);
                }
              }
            }.setData(cs));
      }
    } catch (Throwable t) {
      Log.i(TAG, "show err:");
      Log.printStackTrace(TAG, t);
    }
  }
}

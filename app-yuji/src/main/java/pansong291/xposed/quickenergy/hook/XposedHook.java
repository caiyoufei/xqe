package pansong291.xposed.quickenergy.hook;

import android.app.*;
import android.content.Intent;
import android.os.Handler;
import android.os.PowerManager;
import de.robv.android.xposed.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import java.util.Map;
import pansong291.xposed.quickenergy.*;
import pansong291.xposed.quickenergy.antForest.*;
import pansong291.xposed.quickenergy.util.*;

//Xposed核心文件
public class XposedHook implements IXposedHookLoadPackage {
  //打印的tag
  private static final String TAG = XposedHook.class.getCanonicalName();
  //唤醒锁, 是一种保持 CPU 运转防止设备休眠的方式
  private static PowerManager.WakeLock wakeLock;
  //执行Xposed的线程
  private static Runnable runnable;
  //感觉是检查次数，默认值为为0(即需要执行偷能量、运动等)，由于下面的技计算公式，可以理解为默认为30分钟执行一次(60分钟/默认2分钟=30)
  private static int times;
  //执行Runable的Handler
  public static Handler handler;

  //app启动时调用
  @Override
  public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
    //检查自己是否激活
    if ("xqe.yuji".equals(lpparam.packageName)) {
      /*
       * 参数1：类型
       * 参数2：类加载器
       * 参数3：方法名
       * 参数4：参数类型和调用
       */
      XposedHelpers.findAndHookMethod("pansong291.xposed.quickenergy.ui.MainActivity",
          lpparam.classLoader,
          "isModuleActive",
          new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
              YLog.e("1111111111111111111111111111111111111111111111");
              param.setResult(true);
              super.afterHookedMethod(param);
            }
          });
    }
    //检查支付宝
    if (ClassMember.com_eg_android_AlipayGphone.equals(lpparam.packageName)) {
      Log.i(TAG, lpparam.packageName);
      //监听支付宝服务创建和销毁
      hookLauncherService(lpparam.classLoader);
      //监听支付宝响应
      hookRpcCall(lpparam.classLoader);
    }
  }

  //监听支付宝服务创建和销毁
  private void hookLauncherService(ClassLoader loader) {
    try {
      /*
       * 参数1：类型
       * 参数2：类加载器
       * 参数3：方法名
       * 参数4：参数类型和调用
       */
      XposedHelpers.findAndHookMethod(
          ClassMember.com_alipay_android_launcher_service_LauncherService,
          loader,
          ClassMember.onCreate,
          new XC_MethodHook() {
            ClassLoader loader;

            public XC_MethodHook setData(ClassLoader cl) {
              loader = cl;
              return this;
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
              //获取支付宝的LauncherService对象
              Service service = (Service) param.thisObject;
              //初始化吐司环境
              AntForestToast.context = service.getApplicationContext();
              times = 0;
              //如果需要保持唤醒
              if (Config.stayAwake()) {
                //https://blog.csdn.net/u010164190/article/details/54618204
                PowerManager pm = (PowerManager) service.getSystemService(Service.POWER_SERVICE);
                //持有唤醒锁
                wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, service.getClass().getName());
                //设置亮屏时长(毫秒)
                wakeLock.acquire(Integer.MAX_VALUE);
              }
              //初始化Handler
              if (handler == null) handler = new Handler();
              //Runnable拦截处理
              if (runnable == null) {
                runnable = new Runnable() {
                  Service service;
                  ClassLoader loader;

                  public Runnable setData(Service s, ClassLoader cl) {
                    service = s;
                    loader = cl;
                    return this;
                  }

                  @Override
                  public void run() {
                    //重新加载配置
                    Config.shouldReload = true;
                    //发送XposedEdgePro广播
                    RpcCall.sendXEdgeProBroadcast = true;
                    //重置今日统计文件
                    Statistics.resetToday();
                    //开启定时检测收取能量
                    AntForest.start(loader, times);
                    //开启胶水
                    AntCooperate.start(loader, times);
                    //开启农村
                    AntFarm.start(loader);
                    //开启签到
                    AntMember.receivePoint(loader, times);
                    //开启运动
                    AntSports.start(loader, times);
                    //口碑签到
                    KBMember.start(loader);
                    //开启收集能力 或者 开启农场
                    if (Config.collectEnergy() || Config.enableFarm()) {
                      handler.postDelayed(this, Config.checkInterval());
                    } else {
                      //关闭通知
                      AntForestNotification.stop(service, false);
                    }
                    //(每小时/间隔时间)
                    //默认值为(0+1)%(60分钟/2分钟)=1%30=1
                    times = (times + 1) % (3600_000 / Config.checkInterval());
                  }
                }.setData(service, loader);
              }
              //开启收集能力 或者 启用农场
              if (Config.collectEnergy() || Config.enableFarm()) {
                //打开蚂蚁森林通知
                AntForestNotification.start(service);
                //开始执行抓取
                handler.post(runnable);
              }
            }
          }.setData(loader));
      Log.i(TAG, "hook " + ClassMember.onCreate + " successfully");
    } catch (Throwable t) {
      Log.i(TAG, "hook " + ClassMember.onCreate + " err:");
      Log.printStackTrace(TAG, t);
    }

    try {
      /*
       * 参数1：类型
       * 参数2：类加载器
       * 参数3：方法名
       * 参数4：参数类型和调用
       */
      XposedHelpers.findAndHookMethod(ClassMember.com_alipay_android_launcher_service_LauncherService,
          loader,
          ClassMember.onDestroy,
          new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
              if (wakeLock != null) {
                wakeLock.release();//释放锁，灭屏
                wakeLock = null;
              }
              //获取支付宝的LauncherService对象
              Service service = (Service) param.thisObject;
              //通知通知
              AntForestNotification.stop(service, false);
              //刷新通知栏消息
              AntForestNotification.setContentText("支付宝前台服务被销毁");
              Log.recordLog("支付宝前台服务被销毁", "");
              //移除监听服务
              handler.removeCallbacks(runnable);
              //开启自动重启服务
              if (Config.autoRestart()) {
                //闹钟服务
                AlarmManager alarmManager = (AlarmManager) service.getSystemService(Service.ALARM_SERVICE);
                Intent it = new Intent();
                it.setClassName(ClassMember.com_eg_android_AlipayGphone,
                    ClassMember.com_alipay_android_launcher_service_LauncherService);
                PendingIntent pi = PendingIntent.getService(service, 0, it, PendingIntent.FLAG_UPDATE_CURRENT);
                //1秒后重启支付宝LauncherService
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, pi);
              }
            }
          });
      Log.i(TAG, "hook " + ClassMember.onDestroy + " successfully");
    } catch (Throwable t) {
      Log.i(TAG, "hook " + ClassMember.onDestroy + " err:");
      Log.printStackTrace(TAG, t);
    }
  }

  //监听支付宝响应
  private void hookRpcCall(ClassLoader loader) {
    try {
      Class<?> clazz = loader.loadClass(ClassMember.com_alipay_mobile_nebulaappproxy_api_rpc_H5AppRpcUpdate);
      Class<?> H5PageClazz = loader.loadClass(ClassMember.com_alipay_mobile_h5container_api_H5Page);
      /*
       * 参数1：类型
       * 参数2：类加载器
       * 参数3：方法名
       * 参数4：参数类型和调用
       */
      XposedHelpers.findAndHookMethod(
          clazz,
          ClassMember.matchVersion,
          H5PageClazz,
          Map.class,
          String.class,
          //完全替换目标方法
          XC_MethodReplacement.returnConstant(false));
      Log.i(TAG, "hook " + ClassMember.matchVersion + " successfully");
    } catch (Throwable t) {
      Log.i(TAG, "hook " + ClassMember.matchVersion + " err:");
      Log.printStackTrace(TAG, t);
    }
  }
}

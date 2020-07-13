package pansong291.xposed.quickenergy.hook;

import android.content.Intent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import pansong291.xposed.quickenergy.antForest.AntForestToast;
import pansong291.xposed.quickenergy.util.Config;
import pansong291.xposed.quickenergy.util.Log;

public class RpcCall {
    private static final String TAG = RpcCall.class.getCanonicalName();
    private static Method rpcCallMethod;
    private static Method getResponseMethod;
    private static Object curH5PageImpl;
    public static boolean sendXEdgeProBroadcast;

    public static String invoke(ClassLoader loader, String args0, String args1) {
        if (rpcCallMethod == null) {
            try {
                Class<?> h5PageClazz = loader.loadClass(ClassMember.com_alipay_mobile_h5container_api_H5Page);
                Class<?> jsonClazz = loader.loadClass(ClassMember.com_alibaba_fastjson_JSONObject);
                Class<?> rpcClazz = loader.loadClass(ClassMember.com_alipay_mobile_nebulaappproxy_api_rpc_H5RpcUtil);
                rpcCallMethod = rpcClazz.getMethod(
                        ClassMember.rpcCall, String.class, String.class, String.class,
                        boolean.class, jsonClazz, String.class, boolean.class, h5PageClazz,
                        int.class, String.class, boolean.class, int.class, String.class);
                Log.i(TAG, "get RpcCallMethod successfully");
            } catch (Throwable t) {
                Log.i(TAG, "get RpcCallMethod err:");
            }
        }

        try {
            Object o;
            switch (rpcCallMethod.getParameterTypes().length) {
                case 12:
                    o = rpcCallMethod.invoke(
                            null, args0, args1, "", true, null, null, false, curH5PageImpl, 0, "", false, -1);
                    break;
                default:
                    o = rpcCallMethod.invoke(
                            null, args0, args1, "", true, null, null, false, curH5PageImpl, 0, "", false, -1, "");
            }
            String str = getResponse(o);
            Log.i(TAG, "argument: " + args0 + ", " + args1);
            Log.i(TAG, "response: " + str);
            return str;
        } catch (Throwable t) {
            Log.i(TAG, "invoke err:");
            Log.printStackTrace(TAG, t);
            if (t instanceof InvocationTargetException) {
                if (AntForestToast.context != null && sendXEdgeProBroadcast) {
                    sendXEdgeProBroadcast = false;
                    Intent it = new Intent("com.jozein.xedgepro.PERFORM");
                    it.putExtra("data", Config.xedgeproData());
                    AntForestToast.context.sendBroadcast(it);
                    Log.recordLog(t.getCause().getMessage() + ",发送XposedEdgePro广播", "");
                }
            }
        }
        return null;
    }

    public static String getResponse(Object resp) {
        try {
            if (getResponseMethod == null)
                getResponseMethod = resp.getClass().getMethod(ClassMember.getResponse);

            return (String) getResponseMethod.invoke(resp);
        } catch (Throwable t) {
            Log.i(TAG, "getResponse err:");
            Log.printStackTrace(TAG, t);
        }
        return null;
    }
}

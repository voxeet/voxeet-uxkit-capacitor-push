package com.voxeet.uxkit.capacitor.push;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.voxeet.sdk.push.center.RemoteMessageFactory;
import com.voxeet.sdk.services.notification.INotificationTokenProvider;
import com.voxeet.sdk.services.notification.NotificationTokenHolderFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CapacitorVoxeetPushMessagingService extends FirebaseMessagingService {
    private final static String TAG = CapacitorVoxeetPushMessagingService.class.getSimpleName();

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);

        onCapacitorNewToken(token);
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "onMessageReceived called, will try to forward to Voxeet's then after to Capacitor's");

        INotificationTokenProvider provider = NotificationTokenHolderFactory.provider;
        boolean managed = false;
        if (null != provider) {
            provider.log("New notification with body " + remoteMessage.getData());

            managed = RemoteMessageFactory.manageRemoteMessage(getApplicationContext(), remoteMessage.getData());

            provider.log("notification managed := " + managed);
        }

        if (!managed) {
            onCapacitorMessageReceived(remoteMessage);
        }
    }

    @Nullable
    private Class getPushNotifcations() {
        try {
            return Class.forName("com.getcapacitor.plugin.PushNotifications");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    private void onCapacitorNewToken(@NonNull String token) {
        invokeOneArgumentMethod("onNewToken", String.class, token);
    }

    @Nullable
    private void onCapacitorMessageReceived(@NonNull RemoteMessage remoteMessage) {
        invokeOneArgumentMethod("sendRemoteMessage", RemoteMessage.class, remoteMessage);
    }

    private void invokeOneArgumentMethod(@NonNull String methodName, @NonNull Class argument1Type, @NonNull Object argument) {
        try {
            Method onNewToken = getPushNotifcations().getMethod(methodName, argument1Type);
            onNewToken.invoke(null, argument);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}

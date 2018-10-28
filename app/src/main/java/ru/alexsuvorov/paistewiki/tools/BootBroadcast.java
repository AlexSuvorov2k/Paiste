package ru.alexsuvorov.paistewiki.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ru.alexsuvorov.paistewiki.AppParams;

public class BootBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

            context.startService(new Intent(context, NewsService.class));
            AppParams.callType=2;
        }
    }
}
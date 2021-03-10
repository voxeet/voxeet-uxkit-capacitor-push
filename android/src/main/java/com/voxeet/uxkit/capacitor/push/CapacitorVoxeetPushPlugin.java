package com.voxeet.uxkit.capacitor.push;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "CapacitorVoxeetPush")
public class CapacitorVoxeetPushPlugin extends Plugin {

    private CapacitorVoxeetPush implementation = new CapacitorVoxeetPush();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }
}

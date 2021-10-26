package com.zubie.capacitorzip;

import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@NativePlugin
public class CapacitorZip extends Plugin {

    @PluginMethod
    public void unZip(PluginCall call) {
        String source = call.getString("source");
        String destination = call.getString("destination");
        JSObject ret = new JSObject();

        try {
            this.unPack(source, destination);
            ret.put("path", destination);
            call.success(ret);
        } catch (IOException exception) {
            call.error(exception.getLocalizedMessage());
        }
    }

    private void unPack(String source, String destination) throws IOException {
        InputStream inputStream = new FileInputStream(source);
        ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(inputStream));
        byte[] buffer = new byte[1024];

        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            String filename = zipEntry.getName();
            String filePath = destination + "/" + filename;

            // Need to create directories if not exists, or
            // it will generate an Exception...
            if (zipEntry.isDirectory()) {
                File fmd = new File(filePath);
                fmd.mkdirs();
                continue;
            }

            FileOutputStream fileOutputStream = new FileOutputStream(filePath);

            int count;
            while ((count = zipInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, count);
            }

            fileOutputStream.close();
            zipInputStream.closeEntry();
        }

        zipInputStream.close();
    }
}

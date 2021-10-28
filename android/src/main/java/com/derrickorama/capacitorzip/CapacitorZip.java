package com.derrickorama.capacitorzip;

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
        String sourcePath = call.getString("sourcePath");
        String sourceDirectory = call.getString("sourcePath");
        String destinationPath = call.getString("destinationPath");
        String destinationDirectory = call.getString("destinationDirectory");

        File sourceFile = FilesystemUtils.getFileObject(getContext(), sourcePath, sourceDirectory);
        File destinationFile = FilesystemUtils.getFileObject(getContext(), destinationPath, destinationDirectory);

        JSObject ret = new JSObject();

        try {
            this.unPack(sourceFile, destinationFile);
            ret.put("path", destinationPath);
            ret.put("directory", destinationDirectory);
            call.success(ret);
        } catch (IOException exception) {
            call.error(exception.getLocalizedMessage());
        }
    }

    private void unPack(File source, File destination) throws IOException {
        InputStream inputStream = new FileInputStream(source);
        ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(inputStream));
        byte[] buffer = new byte[1024];

        ZipEntry zipEntry;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            String filename = zipEntry.getName();
            String filePath = destination + "/" + filename;

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

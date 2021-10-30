import Foundation
import Capacitor
import ZIPFoundation

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(CapacitorZip)
public class CapacitorZip: CAPPlugin {

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.success([
            "value": value
        ])
    }

    @objc func unZip(_ call: CAPPluginCall) {
        let sourcePath = call.getString("sourcePath") ?? ""
        let sourceDirectory = call.getString("sourceDirectory") ?? ""
        let destinationPath = call.getString("destinationPath") ?? ""
        let destinationDirectory = call.getString("destinationDirectory") ?? ""
        
        let sourceURL = FilesystemUtils.getFileUrl(sourcePath, sourceDirectory)
        let destinationURL = FilesystemUtils.getFileUrl(destinationPath, destinationDirectory)
        
        if (sourceURL == nil) {
            call.reject("Zip source file not found")
            return;
        }
        
        if (destinationURL == nil) {
            call.reject("Cannot unzip file at given destination")
            return;
        }
        
        let fileManager = FileManager()
        do {
            try fileManager.createDirectory(at: destinationURL!, withIntermediateDirectories: true, attributes: nil)
            try fileManager.unzipItem(at: sourceURL!, to: destinationURL!)
            
            call.resolve([
                "path": destinationPath,
                "directory": destinationDirectory
            ])
        } catch {
            call.reject(error.localizedDescription);
        }
    }
}

#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>
#import <ZIPFoundation/ZIPFoundation-umbrella.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(CapacitorZip, "CapacitorZip",
    CAP_PLUGIN_METHOD(unZip, CAPPluginReturnPromise);
)

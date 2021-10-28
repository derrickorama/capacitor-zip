import '@capacitor/core';

declare module '@capacitor/core' {
  interface PluginRegistry {
    CapacitorZip: CapacitorZipPlugin;
  }
}

export interface CapacitorZipPlugin {
  unZip(options: { sourcePath: string, sourceDirectory: string, destinationPath: string, destinationDirectory: string }): Promise<{ path: string, directory: string }>;
}

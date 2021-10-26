import '@capacitor/core';

declare module '@capacitor/core' {
  interface PluginRegistry {
    CapacitorZip: CapacitorZipPlugin;
  }
}

export interface CapacitorZipPlugin {
  unZip(options: { source: string, destination: string }): Promise<{ path: string }>;
}

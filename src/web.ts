import { WebPlugin } from '@capacitor/core';
import { CapacitorZipPlugin } from './definitions';

export class CapacitorZipWeb extends WebPlugin implements CapacitorZipPlugin {
  constructor() {
    super({
      name: 'CapacitorZip',
      platforms: ['web'],
    });
  }

  async unZip(options: { sourcePath: string, sourceDirectory: string, destinationPath: string, destinationDirectory: string }): Promise<{ path: string, directory: string }> {
    console.log('unZip', options);
    return {
      path: options.destinationPath,
      directory: options.destinationDirectory,
    };
  }
}

const CapacitorZip = new CapacitorZipWeb();

export { CapacitorZip };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(CapacitorZip);

import { WebPlugin } from '@capacitor/core';
import { CapacitorZipPlugin } from './definitions';

export class CapacitorZipWeb extends WebPlugin implements CapacitorZipPlugin {
  constructor() {
    super({
      name: 'CapacitorZip',
      platforms: ['web'],
    });
  }

  async unZip(options: { source: string, destination: string }): Promise<{ path: string }> {
    console.log('unZip', options);
    return {
      path: options.destination,
    };
  }
}

const CapacitorZip = new CapacitorZipWeb();

export { CapacitorZip };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(CapacitorZip);

import { WebPlugin } from '@capacitor/core';

import type { CapacitorVoxeetPushPlugin } from './definitions';

export class CapacitorVoxeetPushWeb
  extends WebPlugin
  implements CapacitorVoxeetPushPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}

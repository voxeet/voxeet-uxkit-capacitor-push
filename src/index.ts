import { registerPlugin } from '@capacitor/core';

import type { CapacitorVoxeetPushPlugin } from './definitions';

const CapacitorVoxeetPush = registerPlugin<CapacitorVoxeetPushPlugin>(
  'CapacitorVoxeetPush',
  {
    web: () => import('./web').then(m => new m.CapacitorVoxeetPushWeb()),
  },
);

export * from './definitions';
export { CapacitorVoxeetPush };

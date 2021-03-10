export interface CapacitorVoxeetPushPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}

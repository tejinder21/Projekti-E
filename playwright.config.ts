import { defineConfig } from '@playwright/test';

export default defineConfig({
  use: {
    headless: false,
    channel: 'chrome',
    launchOptions: {
      slowMo: 1000
    }
  }
});
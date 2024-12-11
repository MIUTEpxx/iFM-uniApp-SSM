import { createSSRApp } from "vue";
import App from "./App.vue";
import { createPinia } from 'pinia';

export function createApp() {
  const app = createSSRApp(App);
  const pinia = createPinia();
  app.use(pinia);
  return {
    app,
    // 不需要返回 Pinia，因为 createPinia 已经被调用并使用了
  };
}
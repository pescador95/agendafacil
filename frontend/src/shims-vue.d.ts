declare module '@/utils/contextHelpers';
declare module '@/pages/login/LoginPage.vue';
declare module '@/pages/home/HomePage.vue';


declare module "*.vue" {
  import { DefineComponent } from "vue";
  const component: DefineComponent<{}, {}, any>;
  export default component;
}

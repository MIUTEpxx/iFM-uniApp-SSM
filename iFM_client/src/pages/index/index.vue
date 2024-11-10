<template>
 <view class="content">
	<p>test</p>
	<tabbar></tabbar>
  </view>

</template>

<script setup lang="ts">
import { onLaunch, onShow, onHide } from "@dcloudio/uni-app";
onLaunch(() => {
  
});
onShow(() => {
  //隐藏pages.json里配置的导航栏，使用封装的tabbar组件
  	uni.hideTabBar({
          animation:false
       })
});
onHide(() => {
  
});



</script>

<style>

</style>

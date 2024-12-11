<script setup lang="ts">
import { onLaunch, onShow, onHide } from "@dcloudio/uni-app";
import { useInitPlayer } from '@/stores/player'
import { createPlayer } from '@/utils/initPlayer'

onLaunch(() => {
  console.log("App Launch");
  createPlayer()
  useInitPlayer() // 初始化播放器控件
});
onShow(() => {
  console.log("App Show");
});
onHide(() => {
  console.log("App Hide");
});
</script>
<style>
	@import url('asses/iconfont.css');
	@import url('styles/tailwind.css');
</style>

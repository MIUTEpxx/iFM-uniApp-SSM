<template>
	<view class="player-bar" :style="{ bottom: bottomValue + 'px' }">
		<view class="content">
			<uv-image
			class="audio-image"
			:src= playerStore.baseUrl+playerStore.broadcastPicture
			:lazy-load="true"
			mode="aspectFill"
			width="auto" height="80px"
			></uv-image>
			<view class="audio-info" @click="goToPlayerPage">
				<uv-text :lines="2" :text="playerStore.broadcastTitle" color="#343b44"  size="18px" ></uv-text>
				<view class="audio-time">
					<uv-text :text="formatDuration(playerStore.currentTime)" color="#7d8ea1"  size="16px"></uv-text>
					<uv-text text="|" color="#7d8ea1"  size="13px" style="margin: 0 3px;" bold="true"></uv-text>
					<uv-text :text="formatDuration(playerStore.broadcastDuration)" color="#7d8ea1"  size="16px"></uv-text>
				</view>
			</view>
			<view class="audio-buttom">
				<uv-icon v-if="playerStore.isPause||!playerStore.isPlaying" @click="playButton" name="play-circle-fill" color="#86c7f9" size="60px"></uv-icon>
				<uv-icon v-else @click="playButton" name="pause-circle-fill" color="#86c7f9" size="60px"></uv-icon>
				<uv-icon name="list-dot" @click="goToPlayerList" color="#86c7f9" size="30px"></uv-icon>
			</view>
		</view>
	</view>

</template>

<script setup lang="ts">
	import { computed, ref } from 'vue';
	import {usePlayerStore} from '@/stores/player';
	import { formatDuration } from '@/utils/durationFormatter';
	
	const playerStore = usePlayerStore()
	const props = defineProps({
		//是否预留底部导航栏空位
		reserveSpace: {
			type: Boolean,
			default: false
		}
	});
	// 使用计算属性来根据 reserveSpace 的值设置 bottomValue
	const bottomValue = computed(() => {
	  return props.reserveSpace ? 70 : 0;
	});
	//跳转播放页
	const goToPlayerPage =()=>{
		if(!playerStore.isPlaying){
			uni.showToast({
				title: '当前未播放节目',
				icon: 'none',
				duration: 1000
			}) 
			return;			
		}
		uni.navigateTo({
		  url:"/pages/player/player",
		});   
	}
	//播放键控制音频播放和暂停
	const playButton =()=>{
		if(!playerStore.isPlaying){
			uni.showToast({
				title: '当前未播放节目',
				icon: 'none',
				duration: 1000
			}) 
			return;			
		}
		playerStore.togglePlay();
	}
	//前往播放列表页
	const goToPlayerList =()=>{
		uni.navigateTo({
		  url:"/pages/player/player-list",
		});   
	}
</script>

<style>
	.player-bar {
		position: fixed;
		left: 0;
		width: 100%;
		height: 100px; /* 播放栏的高度 */
		/* z-index: 1000; */
		background: white;
		/* border: 1px solid #d8e7ff; */
		box-shadow: 0px 0px 3px 1px #b5becb;
	}
	.player-bar .content {
		display: flex;
		align-items: center;
		height: 100%;
	}
	.player-bar .content>*{
		flex: 1;
		height: 100%;
	}
	.content .audio-image {
		margin: 10px;
		box-shadow: 0px 0px 3px 1px #b5becb;
	}
	.content .audio-info {
		flex: 3;
		display: flex;
		flex-direction: column;
		align-items: flex-start;
	}
	.content .audio-info>*{
		flex: 2;
	}
	.content .audio-info>:first-child{
		flex: 3;
	}
	.audio-info .audio-time {
		display: flex;
		align-items: flex-start;
	}
	.content .audio-buttom {
		display: flex;
		margin: 0 10px 0 5px;
	}

</style>
<template>
	<view class="player">
		<image class="bg-img" src="@/static/bg.png"></image>
		<head-general  titleContent="" color="#fff"></head-general>
		<view class="player-body">
			<view class="audio-img">
				<uv-image
				class="audio-image"
				:src= playerStore.baseUrl+playerStore.broadcastPicture
				:lazy-load="true"
				mode="aspectFill"
				width="100%" height="90%"
				></uv-image>
			</view>
			<view class="audio-title" @click="goBroadcastDetail">
				<uv-text  align="center" :text="playerStore.broadcastTitle" color="#fff"  size="30px" ></uv-text>
			</view>
		</view>
		<view class="progress-bar">		
			<view class="audio-time">
				<uv-text align="left" :text="formatDuration(currentTime)" color="#ffffff"  size="16px"></uv-text>
				<uv-text align="right" :text="formatDuration(broadcastDuration)" color="#ffffff"  size="16px"></uv-text>
			</view>	
			<uv-slider 
			@changing="progressBarChanging"
			@change="progressBarChange"
			v-model="value"
			block-size="15" 
			block-color="#fff" 
			backgroundColor="#7c92a1" 
			activeColor="#86c7f9"></uv-slider>
		</view>
		<view class="player-buttom">
			<view class="audio-buttom">
				<uv-icon name="skip-back-left" @click="prevButtom" color="#ffffff" size="60px"></uv-icon>
				<uv-icon v-if="playerStore.isPause" @click="playButton" name="play-circle-fill" color="#ffffff" size="100px"></uv-icon>
				<uv-icon v-else name="pause-circle-fill" @click="playButton" color="#ffffff" size="100px"></uv-icon>
				<uv-icon name="skip-forward-right" @click="nextButtom" color="#ffffff" size="60px"></uv-icon>
			</view>
			<view class="player-list-buttom" @click="goToPlayerList">
				<uv-icon name="list-dot" color="#ffffff" size="38px"></uv-icon>
				<uv-text  align="right" text="播放列表" color="#fff"  size="30px" ></uv-text>
			</view>
		</view>
		
	</view>
</template>

<script setup lang="ts">
	import { ref, watch } from 'vue';
	import { usePlayerStore } from '@/stores/player';
	import { formatDuration } from '@/utils/durationFormatter'
	const playerStore = usePlayerStore()
	let value=ref(100*playerStore.currentTime/playerStore.broadcastDuration)
	let currentTime = ref(playerStore.currentTime)
	let broadcastDuration = ref(playerStore.broadcastDuration)
	
	// 监听 playerStore 的 currentTime 和 broadcastDuration
	watch(
	  () => [playerStore.currentTime, playerStore.broadcastDuration],
	  ([newCurrentTime, newBroadcastDuration]) => {
		if(!playerStore.sliderInput){
			//若当前未拖动进度条
			currentTime.value = newCurrentTime;
			value.value = 100 * newCurrentTime / newBroadcastDuration;
			broadcastDuration.value = newBroadcastDuration;
		}
	  }
	);
	
	//进度条拖动中触发
	const progressBarChanging =()=>{
		playerStore.sliderInput=true
		//四舍五入取整数
		 currentTime.value = Math.round(broadcastDuration.value * value.value * 0.01);
	}
	//进度条停止拖动时触发
	const progressBarChange =()=>{
		currentTime.value = Math.round(broadcastDuration.value * value.value * 0.01);
		playerStore.playAudioInval(currentTime.value)
		playerStore.sliderInput=false
	}	
	//播放键控制音频播放和暂停
	const playButton =()=>{
		playerStore.togglePlay();
	}
	//播放下一个节目
	const nextButtom =()=>{
		playerStore.next();
	}
	//播放上一个节目
	const prevButtom =()=>{
		playerStore.prev();
	}
	//前往播放列表页
	const goToPlayerList =()=>{
		uni.navigateTo({
		  url:"/pages/player/player-list",
		});   
	}
	//前往节目详情页
	const goBroadcastDetail =()=>{
		if(!playerStore.isPlaying){
			uni.showToast({
				title: '当前未播放节目',
				icon: 'none',
				duration: 1000
			}) 
			return;			
		}
		// 使用 uni.navigateTo 方法进行页面跳转，并传递 broadcastId参数
		uni.navigateTo({
		  url: "/pages/broadcast/broadcast?broadcastId="+playerStore.broadcastId,
		});   
	}
</script>

<style>
	.player {
	position: absolute;
	width: 100%;
	height: 100%;
	top: 0;
	left: 0;
	background: rgba(32, 36, 39, 0.6);
	}
	.player .bg-img {
		 position: fixed;
		 width: 100%;
		 height: 100%;
		 top: 0;
		 left: 0;
		 z-index: -1;
	}
	.player-body {
		display: flex;
	    flex-direction: column;
	    align-items: center;
	    justify-content: flex-start;
		margin: 20px;
		height: 50%;
		
	}
	.player-body>*{
		flex: 1;
	}
	.player-body .audio-img {
		flex: 5;
		display: flex;
		width: 90%;
	}
	.player-body .audio-title {
		max-height: 100%;
	}
	.player-buttom {
		display: flex;
	    flex-direction: column;
	    justify-content: center;
	    align-items: center;	
		margin: 0 15px;
	}
	.progress-bar {
		margin: 15px;
	}
	.audio-time {
		display: flex;
		margin-bottom: 15px;
	}
	.player-buttom .audio-buttom {
		display: flex;
	    justify-content: space-between;
	    align-items: center;	
		margin:20px 20px 10px 20px;
	}
	.player-buttom .audio-buttom>*{
		margin: 10px;
	}
	.player-buttom  .player-list-buttom {
		display: flex;
		align-items: flex-end;
	    justify-content: space-between;	
		padding: 10px 15px;
		width: 50%;
		background: rgba(32, 36, 39, 0.6);
		border-radius: 10px;
	}
	
</style>
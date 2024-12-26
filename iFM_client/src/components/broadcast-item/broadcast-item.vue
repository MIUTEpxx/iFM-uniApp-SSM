<template>
	<view class="broadcast-item">
		<view class="_image" @click="goBroadcastDetail">
			<uv-image 
			:src= base_url+props.broadcastPicture
			:lazy-load="true"
			mode="aspectFill"
			width="100%" 
			height="100%"></uv-image>
		</view>
		<view class="info" @click="goBroadcastDetail">
			<uv-text
			:lines="1" 
			:text="props.channelTitle" 
			 color="#8a8a8a" size="26rpx"></uv-text>
			<uv-text 
			:lines="2" 
			:text="props.broadcastTitle" 
			 color="#2b2b2b" size="32rpx" bold="true"></uv-text>
			<view class="other-info">
				<p class="play-count">播放{{numConversion(props.broadcastPlayCount)}}</p>
				<p class="audio-duration">时长{{formatDuration(props.broadcastDuration)}}</p>
			</view>
		</view>
		<view class="play-button" v-if="props.showPlayButton">
			<uv-icon @click="playBroadcastAudio" name="play-circle-fill" color="#86c7f9" size="55"></uv-icon>
		</view>
	</view>
</template>

<script setup lang="ts">
	import { defineProps, withDefaults } from 'vue';
	import { formatDuration } from '@/utils/durationFormatter';
	import { numConversion} from '@/utils/numConversion';
	import useBaseStores from '@/stores/base';
	import { usePlayerStore } from '@/stores/player';
	
	const base_url = useBaseStores().baseUrl;
	const playerStore=usePlayerStore();
	
	interface IBroadcast {
		//节目id
		broadcastId: number;
		//节目标题
		broadcastTitle: string;
		//节目所属频道的标题
		channelTitle: string;
		//节目封面图片路径
		broadcastPicture: string;
		//节目音频路径
		broadcastAudio: string;
		//节目时长(秒)
		broadcastDuration: number;
		//节目播放量
		broadcastPlayCount: number;
		//是否显示播放按钮
		showPlayButton: boolean;
		//是否启用组件内的点击方法
		enableComponentClick:boolean;
	};
	
	// 定义默认值
	const props = withDefaults(defineProps<IBroadcast>(), {
	  broadcastId: 0,
	  broadcastTitle: '默认标题默认标题默认标题默认标题默认标题默认标题默认标题默认标题',
	  channelTitle: '默认频道默认频道默认频道默认频道默认频道默认频道默认频道默认频道默认频道',
	  broadcastPicture: '/images/broadcast/D.png',
	  broadcastAudio: 'path/to/default-audio.mp3',
	  broadcastDuration: 3600, // 默认1小时
	  broadcastPlayCount: 1111111,
	  showPlayButton:true,
	  enableComponentClick:true,
	});
	
	const goBroadcastDetail =()=>{
		if(props.enableComponentClick==false){return;}
		// 使用 uni.navigateTo 方法进行页面跳转，并传递 broadcastId参数
		uni.navigateTo({
		  url: "/pages/broadcast/broadcast?broadcastId="+props.broadcastId,
		});   
	}
	
	//播放节目音频
	const playBroadcastAudio =()=>{
		if(props.enableComponentClick==false){return;}
		playerStore.play(props.broadcastId);
	}
</script>

<style>
	.broadcast-item {
		display: flex;
		margin: 0 5px 20rpx 5px;
		height: 110px;
		border: 2px solid #cae0f1;
		border-radius: 10px;
		overflow: hidden;
		background: #fff;
	}
	.broadcast-item ._image {
		/* flex: 0 0 30%; */
		flex: 1.8;
		height: 100%;
	}
	.broadcast-item .info {
	  /* flex: 0 0 50%; */ /* 设置 info 宽度为 55% */
	  flex: 3;
	  width: 50%;
	  padding-left:10px; 
	  display: flex;
	  flex-direction: column;
	  justify-content: space-around;
	  
	}
	.info .other-info {
		margin: 10px 0 5px 0;
		display: flex;
		justify-content: flex-start; 
		color: #8a8a8a;
		font-size: 12px;
	}
	.info .other-info .play-count {
		flex:0.9;
		/* margin-right: 10px; */
	}
	.info .other-info p {
		font-size: 12px;
	}
	.broadcast-item .play-button {
	  /* flex: 0 0 20%; */ /* 设置 play-button 宽度为 20% */
	  flex: 1;
	  display: flex;
	  align-items: center; /* 垂直居中对齐图标 */
	  justify-content: flex-start; /* 水平居中对齐图标 */
	}
</style>
<template>
	<view class="broadcast-item">
		<view class="_image">
			<uv-image 
			:src= props.broadcastPicture
			:lazy-load="true"
			mode="aspectFill"
			width="100%" 
			height="100%"></uv-image>
		</view>
		<view class="info">
			<uv-text
			:lines="1" 
			:text="props.channelTitle" 
			 color="#8a8a8a" size="16px"></uv-text>
			<uv-text 
			:lines="2" 
			:text="props.broadcastTitle" 
			 color="#2b2b2b" size="20px" bold="true"></uv-text>
			<view class="other-info">
				<p class="play-count">播放 {{numConversion(props.broadcastPlayCount)}}</p>
				<p class="audio-duration">时长 {{formatDuration(props.broadcastDuration)}}</p>
			</view>
		</view>
		<view class="play-button" v-if="props.showPlayButton">
			<uv-icon name="play-circle-fill" color="#86c7f9" size="55"></uv-icon>
		</view>
	</view>
</template>

<script setup lang="ts">
	import { defineProps, withDefaults } from 'vue';
	import { formatDuration } from '@/utils/durationFormatter';
	import { numConversion} from '@/utils/numConversion';
	
	interface IBroadcast {
		//节目id
		broadcastId: number;
		//节目标题
		broadcastTitle: string;
		//节目时长(秒)
		broadcastDuration: number;
		//节目封面图片路径
		broadcastPicture: string;
		//节目音频路径
		broadcastAudio: string;
		//节目播放量
		broadcastPlayCount: number;
		//节目收藏量
		broadcastCollectionCount: number;
		//节目关联帖子量
		broadcastPostCount: number;
		//节目创建时间(年月日小时分钟秒)
		broadcastCreateTime: Date;
		//节目所属频道的标题
		channelTitle: string;
		//节目作者名
		userName: string;
		//上次收听到的时长(秒)
		lastListenDuration: number;
		//上次收听的时间(年月日小时分钟秒)
		historyTime: Date | null;
		//收藏时间(年月日小时分钟秒)
		favoriteTime: Date | null;
		//是否显示播放按钮
		showPlayButton: boolean;
	}
	
	// 定义默认值
	const props = withDefaults(defineProps<IBroadcast>(), {
	  broadcastId: 0,
	  broadcastTitle: '默认标题默认标题默认标题默认标题默认标题默认标题默认标题默认标题',
	  broadcastDuration: 3600, // 默认1小时
	  broadcastPicture: '/static/template-images/broadcastItem.png',
	  broadcastAudio: 'path/to/default-audio.mp3',
	  broadcastPlayCount: 1111111,
	  broadcastCollectionCount: 0,
	  broadcastPostCount: 0,
	  broadcastCreateTime: () => new Date(), // 使用函数来返回一个新的Date对象
	  channelTitle: '默认频道默认频道默认频道默认频道默认频道默认频道默认频道默认频道默认频道',
	  userName: '匿名用户',
	  lastListenDuration: 0,
	  historyTime: null,
	  favoriteTime: null,
	  showPlayButton:true,
	});
	
</script>

<style>
	.broadcast-item {
		display: flex;
		margin-top: 15px;
		height: 110px;
		border: 1px solid #bdbdbd;
		border-radius: 5px;
		overflow: hidden;
		
		._image {
			flex: 0 0 25%;
			height: 100%;
		}
		
		.info {
		  flex: 0 0 55%; /* 设置 info 宽度为 55% */
		  width: 55%;
		  padding-left:10px; 
		  display: flex;
          flex-direction: column;
          justify-content: space-around;
		 
		  .other-info {
			margin: 10px 0 5px 0;
		    display: flex;
		    justify-content: flex-start; 
			color: #8a8a8a;
			font-size: 12px;
			.play-count{
				margin-right: 20px;
			}
		  }
		  
		}
		
		.play-button {
		  flex: 0 0 20%; /* 设置 play-button 宽度为 20% */
		  display: flex;
		  align-items: center; /* 垂直居中对齐图标 */
		  justify-content: flex-start; /* 水平居中对齐图标 */
		}
	}
	
	
	
</style>
<template>
	<view class="channel-item">
		<view class="_image" @click="goChannelDetail">
			<uv-image 
			:src= base_url+props.channelPicture
			:lazy-load="true"
			mode="aspectFill"
			width="100%" 
			height="100%"></uv-image>
		</view>
		<view class="info" @click="goChannelDetail">
			<uv-text
			:lines="1" 
			:text="props.userName" 
			 color="#8a8a8a" size="26rpx"></uv-text>
			<uv-text 
			:lines="2" 
			:text="props.channelTitle" 
			 color="#2b2b2b" size="30rpx" bold="true"></uv-text>
			<view class="other-info" >
				<!-- <p class="hashtag" v-for="(v,i) in props.channelHashtag" :key="i">{{v.hashtagName}}</p> -->
				<uv-tags class="hashtag" v-for="(v,i) in props.channelHashtag" :key="i" :text="v.hashtagName" plain size="mini" color="#86c7f9" borderColor="#86c7f9"></uv-tags>
			</view>
		</view>
		<view class="subscribe-button" v-if="props.showSubscribeButton">
			<uv-icon @click="subscribeClick" v-if="!hasSubscribe" name="plus-circle-fill" color="#86c7f9" size="40"></uv-icon>
			<uv-icon @click="subscribeClick" v-else name="close-circle" color="#86c7f9" size="40"></uv-icon>
		</view>
	</view>
</template>

<script setup lang="ts">
	import { defineProps, ref, withDefaults } from 'vue';
	import useBaseStores from '@/stores/base';
	import { onLoad, onShow } from '@dcloudio/uni-app';
	import useUserStore from '@/stores/user';
	import { changeSubscribe, checkSubscribe } from '@/request/api';
	
	const base_url = useBaseStores().baseUrl;
	const userStore= useUserStore();
	
	interface IBroadcast {
		//频道id
		channelId:number;
		//频道标题
		channelTitle:string;
		//频道封面图片路径
		channelPicture:string;
		//频道订阅数
		channelSubscrib:number;
		//频道主题标签数组 {{10,'社会'},{11,'科技'}}
		channelHashtag:Array<{ hashtagId: number; hashtagName: string }>;
		//频道作者昵称
		userName:string;
		//是否显示订阅按钮
		showSubscribeButton: boolean;
		//是否启用组件内的点击方法
		enableComponentClick:boolean;
	}
	//用户是否已订阅该频道
	let hasSubscribe= ref(false);
	
	// 定义数据默认值
	const props = withDefaults(defineProps<IBroadcast>(), {
	  channelId: 0,
	  channelTitle: '默认频道',
	  channelPicture: '/images/channel/D.png',
	  channelSubscrib: 0,
	  channelHashtag: () => [{ hashtagId: 1, hashtagName: '标签1' },{ hashtagId: 2, hashtagName: '标签2' }],
	  userName: '匿名用户',
	  showSubscribeButton:true,
	  enableComponentClick:true,
	});
	
	const goChannelDetail =()=>{			
		if(props.enableComponentClick==false){return;}
		// 使用 uni.navigateTo 方法进行页面跳转，并传递 channelId参数
		uni.navigateTo({
		  url: "/pages/channel/channel?channelId="+props.channelId,
		});   
	}
	const subscribeClick =()=>{
		if(!userStore.isLogin){
			uni.showToast({
				title: '请登录后操作',
				icon: 'error',
				duration: 1000
			}) 
			return;
		}
		hasSubscribe.value=!hasSubscribe.value
		//用户订阅/取消订阅
		changeSubscribe(userStore.userId,props.channelId).then((res: any) => {
			if(res.success==true){
				if(hasSubscribe.value==true){
					uni.showToast({
						title: "订阅成功",
						icon: 'none',
						duration: 1000
					})
				}else{
					uni.showToast({
						title: "取消订阅",
						icon: 'none',
						duration: 1000
					})
				}
			}else{
				uni.showToast({
					title: res.message+'\n'+res.data.error,
					icon: 'error',
					duration: 3000
				}) 
			}
		}).catch((err: any) => {
			console.error('频道订阅请求失败', err);
		});
		
	}

	onShow(()=>{
		if(userStore.isLogin===true){
			//若用户处于登录状态,检查用户是否已订阅该频道
			checkSubscribe (userStore.userId,props.channelId).then((res:any) => {
				// console.log("res",res)
				if(res.success===true){
					hasSubscribe.value=res.data.subscribe
				//	console.log("hasSubscribe",hasSubscribe.value)
				}else{
					uni.showToast({
						title: res.message+'\n'+res.data.error,
						icon: 'error',
						duration: 3000
					}) 
				}
			}).catch((err:any) => { 
			  console.error('订阅数据请求失败', err); 
			});
		}
	})
</script>

<style>
	.channel-item {
		display: flex;
		margin: 0 5px 20rpx 5px;
		height: 110px;
		border: 2px solid #cae0f1;
		border-radius: 10px;
		overflow: hidden;
		background-color: #fff;
	}
	.channel-item ._image {
		flex: 1.8;
		height: 100%;
	}
	
	.channel-item .info {
	  flex: 3;
	  width: 55%;
	  padding-left:10px; 
	  display: flex;
	  flex-direction: column;
	  justify-content: space-around;
	}
	.info .other-info {
			margin: 10px 0 5px 0;
	  display: flex;
	  justify-content: flex-start; 
	}
	
	.info .other-info p{
		font-size: 15px;
	}
	.info .other-info .hashtag {
		color: #8a8a8a;
		margin-right: 5px;
	}
	.channel-item .subscribe-button {
	  flex: 1;
	  display: flex;
	  align-items: center; /* 垂直居中对齐图标 */
	  justify-content: center; /* 水平居中对齐图标 */
	}
</style>
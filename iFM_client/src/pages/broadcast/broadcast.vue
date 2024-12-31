<template>
	<view class="broadcast-detail">
		<head-general titleContent="节目详情" ></head-general>
		<view class="broadcast-detail-head">
			<view class="broadcast-info">
				<uv-text :lines="3" :text="broadcast.broadcastTitle" color="#1d2b36" size="24px" bold="true"></uv-text>
				<uv-text :lines="2" :text="'发布: '+broadcast.gmtCreate" color="#4a6f8b" size="13px" customStyle="padding-top:5px"></uv-text>
				<uv-text :lines="2" :text="'节目时长: '+ broadcast.broadcastDuration" color="#4a6f8b" size="13px" customStyle="padding-top:5px"></uv-text>
			</view>
			<view class="broadcast-image">
				<uv-image
				:src= "base_url+broadcast.broadcastPicture"
				:lazy-load="true"
				mode="aspectFill"
				width="100%" 
				height="100%"
				customStyle=" position: absolute; top: 0px; right: 0; bottom: 0; left: 0;" >
				</uv-image>
			</view>
		</view>

		<view class="broadcast-button">
			<view class="play-button" @click="playBroadcastAudio">
				<uv-icon name="play-circle-fill" color="#86c7f9" size="75"></uv-icon>
				<uv-text :lines="3" text="点击播放" color="#5cbaf9" size="18px" bold="true"></uv-text>
			</view>
			<view class="other-button">
				<view class="post-button" @click="goPostAssociation">
					<uv-icon name="chat" color="#343b4c" size="50px"></uv-icon>
					<p>关联帖子</p>
					<p>{{broadcast.broadcastPostCount}}</p>
				</view>
				<view v-if="!hasCollect" class="favorite button" @click="favoriteClick">
					<uv-icon name="star" color="#343b4c" size="50px"></uv-icon>
					<p>收藏节目</p>
					<p>{{broadcast.broadcastCollectionCount}}</p>
				</view>
				<view v-else class="favorite button" @click="favoriteClick">
					<uv-icon name="star-fill" color="#86c7f9" size="50px"></uv-icon>
					<p style="color:#6cb3ff;">取消收藏</p>
					<p style="color:#6cb3ff;">{{numConversion(CollectionCount)}}</p>
				</view>
				
				<view class="add-playList-button" @click="addPlayerList">
					<uv-icon name="tianjialiebiaoxiang" custom-prefix="custom-icon" color="#343b4c" size="35px" customStyle="padding:7px;"></uv-icon>
					<p>添加至</p>
					<p>播放列表</p>
				</view>
			</view>
		</view>
		<view class="channel">
			<uv-text :lines="3" text="节目所属频道" color="#5cbaf9" size="20px" customStyle="margin:5px 10px;" ></uv-text>
			<channel-item v-bind="channel"></channel-item>

		</view>
		<view class="broadcast-detail-content">
			<uv-text :lines="3" text="节目详情内容" color="#5cbaf9" size="20px" bold="true" customStyle="flex-direction: row-reverse;"></uv-text>
			<uv-read-more ref="readMore" show-height="300px" :toggle="true" color="#61b7f9" shadowStyle="backgroundImage:none;">
				<uv-parse class="content" :content="broadcast.broadcastDetail" @load="load"></uv-parse>
				<!-- <uv-text :text="broadcast.broadcastDetail" color="#203d53" size="18px" customStyle="margin:15px 5px 5px 10px;"></uv-text> -->
			</uv-read-more>
		</view>	
		<player-bar></player-bar>
	</view>
</template>

<script  setup lang="ts">
	import { onLoad } from "@dcloudio/uni-app";
	import { getChannelDetail, getBroadcastDetail, checkCollect, changeFavorite } from "@/request/api"; 
	import { nextTick, ref,watch } from 'vue';
	import { changeTime } from "@/utils/timeChange"
	import { numConversion} from '@/utils/numConversion';
	import { formatDuration} from '@/utils/durationFormatter';
	import useBaseStores from '@/stores/base';
	import useUserStores from '@/stores/user';
	import { logOut } from "@/utils/logOut";
	import { usePlayerStore } from "@/stores/player";
	
	//后端请求路径
	const base_url = useBaseStores().baseUrl;
	//获取用户信息
	const userStore = useUserStores();
	//全局音频播放器
	const playerStore= usePlayerStore();
	//
	const readMore = ref<any>(null);
	
	let broadcastId = ref(0);
	//储存频道信息
	let channel = ref<any>([]);
	//储存节目项数据的数组
	let broadcast= ref<any>([]);
	//用户是否已订阅该频道
	let hasCollect= ref<boolean>(false);
	//节目收藏量
	let CollectionCount= ref<number>(0);
	
	//收藏按钮点击
	const favoriteClick =() =>{
		if(!userStore.isLogin){
			uni.showToast({
				title: '请登录后操作',
				icon: 'error',
				duration: 1000
			}) 
			return;
		}
		hasCollect.value=!hasCollect.value
		if(hasCollect.value===true){CollectionCount.value+=1}
		else {CollectionCount.value-=1}
		//用户收藏/取消收藏
		changeFavorite(userStore.userId,broadcastId.value).then((res: any) => {
			if(res.success===false){
				uni.showToast({
					title: res.message+'\n'+res.data.error,
					icon: 'error',
					duration: 3000
				}) 
			}
		}).catch((err: any) => {
			console.error('节目收藏请求失败', err);
		});
	}
	
	 onLoad((options:any) => {
		broadcastId.value = JSON.parse(options.broadcastId); // 字符串转对象
		//获取节目信息
		getBroadcastDetail (broadcastId.value).then((res:any) => {
			broadcast.value=res.data.broadcast
			//处理节目的数据
			broadcast.value.gmtCreate=changeTime(broadcast.value.gmtCreate)
			broadcast.value.broadcastDuration=formatDuration(broadcast.value.broadcastDuration)
			broadcast.value.dcastPostCount=numConversion(broadcast.value.broadcastPostCount)
			CollectionCount.value = broadcast.value.broadcastCollectionCount
			 // 在获取到节目信息后，再获取频道信息
			    if (broadcast.value.channelId) {
				/* console.log("broadcast.value.channelId",broadcast.value.channelId) */
			      // 获取频道信息
			      getChannelDetail(broadcast.value.channelId).then((res: any) => {
			        channel.value = res.data.channel;
			        //console.log("channel.value", channel.value);
			      }).catch((err: any) => {
			        console.error('频道数据请求失败', err);
			      });
			    }
			 readMore.value.init();	
		}).catch((err:any) => {
		  console.error('节目数据请求失败', err); 
		});
		if(userStore.isLogin===true){
			//若用户处于登录状态,检查用户是否已收藏该节目
			checkCollect(userStore.userId,broadcastId.value).then((res: any) => {
					if(res.success===true){
						hasCollect.value=res.data.favorite
					}else{
						uni.showToast({
							title: res.message+'\n'+res.data.error,
							icon: 'error',
							duration: 3000
						}) 
					}
				  }).catch((err: any) => {
				console.error('用户收藏信息失败', err);
			  });
		}        
		
		//
		nextTick(() => {
		  if (readMore.value) {
		   /* readMore.value.init(); */
			/* console.log("readMore.value.init();") */
		  }
		});
	});
	//将音频添加至播放列表
	const addPlayerList =()=>{
		playerStore.pushPlayList(false, [broadcastId.value]);
		uni.showToast({
			title: "音频已添加至列表",
			icon: 'none',
			duration: 3000
		}) 
	}
	//播放节目音频
	const playBroadcastAudio =()=>{
		playerStore.play(broadcastId.value);
	}
	//跳转关联帖子页
	const goPostAssociation =()=>{
		uni.navigateTo({
		  url: "/pages/community/post-association?postAssociation=1&associationId="+broadcastId.value,
		});   
	}
	//
/* 	const load =()=>{
		setTimeout(()=>{
			readMore.value.init();
		},1000)
	} */
</script>

<style>
	.broadcast-detail-head{
		display: flex;
	    justify-content: space-between;
	    align-items: center;
		margin: 0 20px;
	}
	.broadcast-detail-head .broadcast-image {
		position: relative;
		flex:0 0 35%;
		height: 0;
		padding: 0;
		padding-bottom: 35%; 
	}
	.channel{
		margin: 20px 10px;
	}

	.broadcast-button {
		display:flex;
	    justify-content: space-between;
	    align-items: center;	
		margin: 10px 20px;
	}

	.other-button {
		display: flex;
		align-items: center;	
	}
	.other-button >* {
		flex: 1;
		display: flex;
	    flex-direction: column;
	    align-items: center;
	    justify-content: space-between;
	}
	.other-button p{
		margin: 3px 5px;
		font-size: 16px;
	}
	.broadcast-detail-content {
		margin: 10px 20px;
		padding-bottom: 110px;
	}
	.broadcast-detail-content .uv-read-more {
		margin-top: 10px;
		padding-bottom: 30px;
		border-top: 3px dashed #9dcaf9;
	}

</style>
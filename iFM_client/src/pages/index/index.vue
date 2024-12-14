<template>
 <view class="index">
	 <view class="index-head">
		 <head-general :showBackButton="false" :showHomeButton="false" titleContent="发现"></head-general>
		<search-box></search-box>
		<uv-button 
		@click="goToChannelClassify"
		customStyle="margin:15px 0 0 20px; width: 100px; fontSize:18px" 
		class="btn" 
		color="#86c7f9" >
		频道分类</uv-button>
		
	 </view>
	<view class="index-body">
		<p class="title">今日<br>热门</p>
		<view class="pop-broadcast">
			<uv-text text="节目推荐" color="#86c7f9" size="26px"></uv-text>
			<scroll-view scroll-y class="broadcast-scroll">
				<broadcast-item  v-for="(item,i) in broadcastList" :key="i" v-bind="item"></broadcast-item>
			</scroll-view>

		</view>
		<view class="pop-channel">
			<uv-text text="频道推荐" color="#86c7f9" size="26px"></uv-text>
			<scroll-view scroll-y class="channle-scroll">
				<channel-item v-for="(item,i) in channelList" :key="i" v-bind="item"></channel-item>
			</scroll-view>
		</view>
	</view>
	
	<player-bar :reserveSpace="true"></player-bar>
	<tabbar></tabbar>
  </view>

</template>

<script setup lang="ts">
import { onLaunch, onShow, onHide } from "@dcloudio/uni-app";
import { getPopularBroadcast, getPopularChannel } from "@/request/api"; 
import { ref } from 'vue';

//储存节目项数据的数组
let broadcastList= ref([]);
//储存热门频道的数组
let channelList= ref([]);

//跳转到频道分类页
const goToChannelClassify = () => {
	  uni.navigateTo({
	    url: '/pages/channel/channel-classify'
	  });
	};	

onLaunch(() => {
  
});
onShow(() => {
	getPopularBroadcast ().then((res:any) => {
		broadcastList.value=res.data.broadcastList
	}).catch((err:any) => { 
	  console.error('热门频道请求失败', err); 
	});
	
	getPopularChannel ().then((res:any) => {
		channelList.value=res.data.channelList
		//console.log("channel",channelList.value)
	}).catch((err:any) => { 
	  console.error('热门频道请求失败', err); 
	});
	
  //隐藏pages.json里配置的导航栏，使用封装的tabbar组件
  	uni.hideTabBar({
          animation:false
       })
});
onHide(() => {
  
});




</script>




<style>

.index {
	
	
  .index-head {
    display: flex;
    align-items: stretch;
    flex-direction: column;

	}
	.index-body {
		margin: 15px 15px 0 10px;
		 display: flex;
		 align-items: stretch;
		 flex-direction: column;
		 margin-top: 15px;
		 position: relative;
		.title {
			margin-right: 20px;
			margin-left: auto;
			position: absolute;
			right: 0;
			top:-35px;
			font-size: 28px;
			color: #58b3f9;
		}
		.pop-broadcast{
			margin-bottom: 30px;
			.broadcast-scroll {
				height: 390px;
				margin-top: 10px;
				overflow: hidden;
				border: 3px dashed #9dcaf9;
				border-radius: 10px;
			}
		}
		.pop-channel{
			margin-bottom: 100px;
			
			.channle-scroll {
				height: 390px;
				margin-top: 10px;
				overflow: hidden;
				border: 3px dashed #9dcaf9;
				border-radius: 10px;
			}
		}
	}
}
</style>

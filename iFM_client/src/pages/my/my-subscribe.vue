<template>
	<view class="my-subscribe">
		<head-general titleContent="频道订阅"></head-general>
		<view class="my-subscribe-body">
			<view class="show-content-tbs">
				<uv-tabs
				@click=""
				:list="[{key:0,name:'最近订阅'},{key:1,name:'最近更新'}]"
				lineWidth="70" 
				lineColor="#63b1f9" 
				:activeStyle="{
					color: '#63b1f9',
					fontSize:'22px',
					transform: 'scale(1.05)',
				}"
				:inactiveStyle="{
					color: '#606266',
					fontSize:'22px',
					transform: 'scale(1)'
				}" 
				itemStyle="padding:15px 15px; height: 35px;"
				></uv-tabs>		
			</view>
			<view v-if="!inDelete" class="un-delete-button" @click="inDeleteClick">
				<p>选择删除</p>
			</view>
			<view v-if="inDelete" class="in-delete-button" @click="inDeleteClick">
				<p>取消选择</p>
			</view>
			<view class="subscribe-channel">
				<scroll-view scroll-y class="channel-scroll">
					<view class="channel-item-list" v-for="(item,i) in channelList" :key="i" >
						<channel-item class="item" :showSubscribeButton="false" v-bind="item" :showPlayButton="false"></channel-item>	
						<uv-icon v-if="inDelete" @click="deleteBroacast(item,i)" name="trash" color="#ff7d7d" size="28"></uv-icon>	
					</view>
				</scroll-view>
			</view>
		</view>
		<player-bar></player-bar>
	</view>
</template>

<script setup lang="ts">
import { ref } from "vue"
import { changeFavorite, changeSubscribe, getFavoriteBroadcast, getSubscribedChannel } from "@/request/api";
import { onShow } from "@dcloudio/uni-app";
import useUserStore from "../../stores/user";

//是否处于删除状态
let inDelete =ref(false)
const userStore =useUserStore()
//页面展示内容 0:收藏的节目 1:收藏的帖子
let sortMethod=ref(0)
//收藏的节目
let channelList=ref([])
//切换显示的内容
const sortMethodClick = (index:any) =>{
	sortMethod.value=index.key
}
//是否显示删除按钮
const inDeleteClick =() =>{
	inDelete.value=!inDelete.value
}
const deleteBroacast =(item:any,index:number) =>{
	//取消订阅
	changeSubscribe(userStore.userId,item.channelId).then((res: any) => {
		if(res.success===true){
			// 删除channelList中对应index的元素
			channelList.value.splice(index, 1);
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

onShow(() => {
	//获取用户订阅的频道
	 getSubscribedChannel(userStore.userId).then((res:any) => {
		 if(res.success==true){
			 channelList.value=res.data.channelList
		 }else{
			uni.showToast({
				title: res.message+'\n'+res.data.error,
				icon: 'error',
				duration: 3000
			}) 
		}
		
		console.log("channelList",channelList.value)
	}).catch((err:any) => { 
	  console.error('收藏的节目数据请求失败', err); 
	});
});
	
</script>

<style>
	.my-subscribe-body {
		display: flex;
	    flex-direction: column;
	    align-items: center;
		margin-top: 20px;
	}
	.un-delete-button {
		margin: 20px auto 15px 25px;
		padding: 3px 5px;
		color: #ff7d7d;
		font-size: 22px;
		border: 3px solid #ff7d7d;
		border-radius: 5px;
	}
	.in-delete-button {
		margin: 20px auto 15px 25px;
		padding: 3px 5px;
		color: white;
		background-color: #ff7d7d;
		font-size: 22px;
		border: 3px solid #ff7d7d;
		border-radius: 5px;
	}
	.subscribe-channel {
		width: 90%;
		border: 3px dashed #9dcaf9;
		border-radius: 10px;
	}
	.subscribe-channel .channel-scroll {
		height: 100%;
	}
	.channel-scroll .channel-item-list {
		display: flex;
	    align-items: center;
	    justify-content: space-between;	
	}
	.channel-item-list>*{
		flex: 1;
	}
	.channel-item-list>.item{
		flex: 6;
	}

</style>
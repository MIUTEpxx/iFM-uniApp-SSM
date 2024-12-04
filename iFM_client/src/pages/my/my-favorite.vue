<template>
	<view class="my-favorite">
		<head-general titleContent="我的收藏"></head-general>
		<view class="my-favorite-body">
			<view class="show-content-tbs">
				<uv-tabs
				@click=""
				:list="[{key:0,name:'节目'},{key:1,name:'帖子'}]"
				lineWidth="60" 
				lineColor="#63b1f9" 
				:activeStyle="{
					color: '#63b1f9',
					fontSize:'28px',
					transform: 'scale(1.05)',
				}"
				:inactiveStyle="{
					color: '#606266',
					fontSize:'28px',
					transform: 'scale(1)'
				}" 
				itemStyle="padding:15px 20px; height: 35px;"
				></uv-tabs>		
			</view>
			<view v-if="!inDelete" class="un-delete-button" @click="inDeleteClick">
				<p>选择删除</p>
			</view>
			<view v-if="inDelete" class="in-delete-button" @click="inDeleteClick">
				<p>取消选择</p>
			</view>
			<view class="favorite-broadcast">
				<scroll-view scroll-y class="broadcast-scroll">
					<view class="broadcast-item-list" v-for="(item,i) in broadcastList" :key="i" >
						<broadcast-item  v-bind="item" :showPlayButton="false"></broadcast-item>
						<uv-icon  v-if="inDelete" @click="deleteBroacast(item,i)" name="trash" color="#ff7d7d" size="28"></uv-icon>	
					</view>
				</scroll-view>
			</view>
		</view>
	</view>
</template>

<script setup lang="ts">
import { ref } from "vue"
import { changeFavorite, getFavoriteBroadcast } from "@/request/api";
import { onShow } from "@dcloudio/uni-app";
import useUserStore from "../../stores/user";

//是否处于删除状态
let inDelete =ref(false)
const userStore =useUserStore()
//页面展示内容 0:收藏的节目 1:收藏的帖子
let showContent=ref(0)
//收藏的节目
let broadcastList=ref([])
//切换显示的内容
const showContentClick = (index:any) =>{
	showContent.value=index.key
}
//
const inDeleteClick =() =>{
	inDelete.value=!inDelete.value
}
const deleteBroacast =(item:any,index:number) =>{
	
	changeFavorite(userStore.userId,item.broadcastId).then((res:any) => {
		console.log("res.data",res.data)
		//取消收藏成功
		if(res.success!=false){
			// 删除broadcastList中对应index的元素
			broadcastList.value.splice(index, 1);
		}else{
			uni.showToast({
				title: res.message+'\n'+res.data.error,
				icon: 'error',
				duration: 6000
			}) 
		}
	}).catch((err:any) => { 
	  console.error('取消收藏失败', err); 
	}); 
}
onShow(() => {
	//获取用户收藏的节目
	 getFavoriteBroadcast(userStore.userId).then((res:any) => {
		broadcastList.value=res.data.broadcastList
		//console.log("broadcastList",res.data)
	}).catch((err:any) => { 
	  console.error('收藏的节目数据请求失败', err); 
	});
});
	
</script>

<style>
	.my-favorite-body {
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
	.favorite-broadcast {
		width: 90%;
		border: 3px dashed #9dcaf9;
		border-radius: 10px;
	}
	.favorite-broadcast .broadcast-scroll {
		height: 100%;
	}
	.broadcast-scroll .broadcast-item-list {
		display: flex;
	    align-items: center;
	    justify-content: space-between;	
	}
	.broadcast-item-list>*{
		flex: 1;
	}
	.broadcast-item-list :first-child{
		flex: 5;
	}

</style>
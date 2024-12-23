<template>
	<view class="my-favorite">
		<head-general titleContent="我的收藏"></head-general>
		<view class="my-favorite-body">
			<view class="show-content-tbs">
				<uv-tabs
				@click="showContentClick"
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
			<view class="favorite-broadcast-list" v-if="showContent==0">
				<view class="broadcast-item-list" v-for="(item,i) in broadcastList" :key="i" >
					<broadcast-item  class="item" v-bind="item" :showPlayButton="false"></broadcast-item>
					<uv-icon  v-if="inDelete" @click="deleteBroacast(item,i)" name="trash" color="#ff7d7d" size="28"></uv-icon>	
				</view>
				<uv-text text="没有更多频道了" color="#8e9aa7"  size="16px" align="center"></uv-text>
			</view>
			<view class="favorite-post-list" v-else>
				<view class="post-item-list" v-for="(item,i) in postList" :key="i" >
					<uv-icon  class="delete-button" v-if="inDelete" @click="deletePost(item,i)" name="trash" color="#ff7d7d" size="28"></uv-icon>	
					<post-item v-bind="item"></post-item>
				</view>
				<uv-text text="没有更多帖子了" color="#8e9aa7"  size="16px" align="center"></uv-text>
			</view>
		</view>
		<player-bar></player-bar>
	</view>
</template>

<script setup lang="ts">
import { ref } from "vue"
import { changeFavorite, changePostCollection, getFavoriteBroadcast, getFavoritePost } from "@/request/api";
import { onShow } from "@dcloudio/uni-app";
import useUserStore from "../../stores/user";
import useBaseStore from "@/stores/base";

//是否处于删除状态
let inDelete =ref(false)
const userStore =useUserStore()
//页面展示内容 0:收藏的节目 1:收藏的帖子
let showContent=ref(0)
//收藏的节目
let broadcastList=ref([])
//收藏的帖子
let postList = ref([])
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

const deletePost =(item:any,index:number) =>{
	changePostCollection(userStore.userId,item.postId).then((res:any) => {
		console.log("res.data",res.data)
		//取消收藏成功
		if(res.success!=false){
			// 删除postList中对应index的元素
			postList.value.splice(index, 1);
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
	}).catch((err:any) => { 
	  console.error('收藏的节目数据请求失败', err); 
	});
	//获取用户收藏的帖子
	getFavoritePost(userStore.userId).then((res:any) => {
		postList.value=res.data.postList
		postList.value = res.data.postList.map((post: any) => {
			// 修改每个帖子的图片列表
			post.postImageList = post.postImageList.map((postImage: any) => {
			return useBaseStore().baseUrl + postImage;
			});
			return post;
		});
	}).catch((err:any) => { 
	  console.error('收藏的帖子数据请求失败', err); 
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
	.favorite-broadcast-list {
		width: 95%;
		padding-top: 25rpx;
		padding-bottom: 125rpx;
		border: 3px dashed #9dcaf9;
		border-radius: 10px;
	}
	.broadcast-item-list {
		display: flex;
	    align-items: center;
	    justify-content: space-between;	
	}
	.broadcast-item-list>*{
		flex: 1;
	}
	.broadcast-item-list>.item{
		flex: 9;
	}
	.favorite-post-list {
		width: 100%;
		min-height: 700rpx;
		padding-bottom: 125rpx;
		background: #dce8f9;
		padding-top: 25rpx;
		padding-bottom: 140rpx;
	}
	.post-item-list {
		display: flex;
		flex-direction: column;
		/* align-items: flex-end; */ 
		position:relative;
	}
	.post-item-list .delete-button {
		position:absolute;
		right: 150rpx;
		top: 18rpx;
	}


</style>
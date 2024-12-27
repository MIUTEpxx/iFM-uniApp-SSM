<template>
	<view class="user">
		<head-general titleContent="用户详情" style="background-color: #fff;"></head-general>
		<view class="user-head">
			<my-head v-bind="user" :isMyUser="false"></my-head>
			<view class="show-content-tbs">
				<uv-tabs
				@click="showContentClick"
				:list="[{key:0,name:'频道'},{key:1,name:'帖子'}]"
				lineWidth="40" 
				lineColor="#63b1f9" 
				:activeStyle="{
					color: '#63b1f9',
					fontSize:'45rpx',
					transform: 'scale(1.05)',
				}"
				:inactiveStyle="{
					color: '#606266',
					fontSize:'45rpx',
					transform: 'scale(1)'
				}" 
				itemStyle="padding:15px 20px; height: 35px;"
				></uv-tabs>		
			</view>	
		</view>
		<view class="user-body">
			<view class="item-list" v-if="showContent==0">
				<channel-item v-for="(item,i) in channelList" :key="i" v-bind="item"></channel-item>
				<uv-text text="没有更多频道了" color="#8e9aa7"  size="16px" align="center"></uv-text>
			</view>
			<view class="item-list" v-else>
				<post-item  v-for="(item,i) in postList" :key="i" v-bind="item"></post-item>
				<uv-text text="没有更多帖子了" color="#8e9aa7"  size="16px" align="center"></uv-text>
			</view>
		</view>
	</view>
</template>

<script setup lang="ts">import { onLoad, onShow } from '@dcloudio/uni-app';
import { ref } from 'vue';
import { getChannelByUserId, getPostByUserId, getUserById } from "@/request/api"; 
import useBaseStore from '../../stores/base';

	//页面展示内容 0:频道 1:帖子
	let showContent=ref(0)
	//用户id
	let userId = ref(-1)
	//用户信息
	let user = ref()
	//频道
	let channelList=ref([])
	//帖子
	let postList=ref([])
	
	//切换显示的内容
	const showContentClick = (index:any) =>{
		showContent.value=index.key
	}
	
	onLoad((options:any)=>{
		userId.value = JSON.parse(options.userId); // 字符串转对象
		if(userId.value!=-1) {
			//获取用户数据
			getUserById(userId.value).then((res:any) => {
				 console.log("res",res)
				if(res.success===true){
					user.value=res.data.user
				}else{
					uni.showToast({
						title: res.message+'\n'+res.data.error,
						icon: 'error',
						duration: 3000
					}) 
				}
			}).catch((err:any) => { 
			  console.error('用户信息请求失败', err); 
			});
			//获取用户的频道
			getChannelByUserId(userId.value).then((res:any) => {
				if(res.success===true){
					channelList.value=res.data.channelList
				}else{
					uni.showToast({
						title: res.message+'\n'+res.data.error,
						icon: 'error',
						duration: 3000
					}) 
				}
			}).catch((err:any) => { 
			  console.error('用户频道数据请求失败', err); 
			});
			//获取用户的帖子
			getPostByUserId(userId.value).then((res:any) => {
				if(res.success===true){
					postList.value=res.data.postList
					postList.value = res.data.postList.map((post: any) => {
						// 修改每个帖子的图片列表
						post.postImageList = post.postImageList.map((postImage: any) => {
						return useBaseStore().baseUrl + postImage;
						});
						return post;
					});
				}else{
					uni.showToast({
						title: res.message+'\n'+res.data.error,
						icon: 'error',
						duration: 3000
					}) 
				}
			}).catch((err:any) => { 
			  console.error('用户帖子数据请求失败', err); 
			});
		}
	})
	
</script>

<style scoped>
	.user {
		height: 100%;
		background-color: #dce8f9;
	}
	.user .user-head {
		padding:0 50rpx 25rpx 35rpx;
		background-color: #fff;
		border-bottom: 1px solid #91b9f8;
	}
	.user .user-head .show-content-tbs {
		display: flex;
		justify-content: center;
	}
	.user .user-body {
		min-height: 1150rpx;
		padding-top: 25rpx;
	}
</style>
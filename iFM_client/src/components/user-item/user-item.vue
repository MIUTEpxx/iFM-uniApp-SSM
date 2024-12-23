<template>
	<view class="user-item"  @click="goUserDetail">
		<view class="user-avatar">
			<uv-avatar
			:src="base_url+props.userPicture" 
			size="150rpx" :lazy-load="true"
			mode="aspectFill"></uv-avatar>	
		</view>
		<view class="user-info">
			<uv-text class="name" :lines="2" :text="props.userName" color="#1d2b36" size="40rpx" bold="true"></uv-text>
			<uv-text class="profile" :lines="2" :text="props.userProfile" color="#1d2b36" size="26rpx"></uv-text>
		</view>
	</view>
</template>

<script setup lang="ts">
	import { defineProps, withDefaults } from 'vue';
	import useBaseStores from '@/stores/base';
	
	const base_url = useBaseStores().baseUrl;
	
	const props = defineProps({
		userId:{
			type: Number,
			default: -1
		},
		userName: {
			type: String,
			default: "匿名用户"
		},
		userPicture:{
			type: String,
			default: "/images/user/head/D.png"
		},
		userProfile:{
			type: String,
			default: "这个人很懒,什么都没有写"
		}
	});
	
	const goUserDetail = () =>{
		uni.navigateTo({
		  url: "/pages/user/user?userId="+props.userId,
		});   
	}
</script>

<style scoped>
	.user-item {
		display: flex;
		margin-bottom: 25rpx;
		padding: 25rpx 10rpx;
		background-color: #fff;
		box-shadow: 0 0 5rpx 0.02rpx #bdc9dd;
	}
	.user-item .user-info {
		margin-left: 20rpx;
	}
	.user-item .user-info .name {
		padding: 10rpx 0;
	}
</style>
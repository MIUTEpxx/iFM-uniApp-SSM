<template>
		<view class="post-head">
			<view class="user-post-head">
				<uv-avatar :src="baseStore.baseUrl+'/images/user/head/D.png'" size="45px"></uv-avatar>
				<uv-text class="user-name" :lines="2" text="用户昵称" color="#78889c"  size="14px" align="left" bold="true"></uv-text>
			</view>
			<view class="post-post-head">
				<uv-text class="post-section" :lines="1" text="话题讨论" color="#86c7f9"  size="18px" bold="true" align="right"></uv-text>
				<uv-text class="post-create-time" :lines="2" text="发帖日期: 2024-12-10 21:00" color="#778195"  size="12px" align="right"></uv-text>
			</view>
		</view>
</template>

<script setup lang="ts">
	import useBaseStore from "@/stores/base"
	import { ref } from "vue";
	
	const baseStore=useBaseStore();
</script>

<style>
	
	.post-head {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 10rpx 0;
	}
	.post-head>* {
		flex: 1;
	}
	 .post-head .user-post-head {
		flex: 1.6;
		display: flex;
	}
	.post-head .user-post-head >* {
		margin:15rpx 20rpx 15rpx 5rpx;
	}
	.post-head .post-post-head .post-section{
		padding-bottom: 10rpx ;
	}
</style>
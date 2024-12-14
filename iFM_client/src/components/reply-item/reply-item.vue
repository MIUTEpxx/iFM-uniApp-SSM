<template>
	<view class="reply-item">
		<view class="user-avatar">
			<uv-avatar :src="baseStore.baseUrl+'/images/user/head/D.png'" size="40px"></uv-avatar>
		</view>
		<view class="reply-content">
			<uv-text class="user-name" :lines="2" text="用户昵称" color="#47515d"  size="14px" align="left"></uv-text>
			<uv-read-more show-height="100rpx" :toggle="true" color="#61b7f9" closeText="显示全部" shadowStyle="backgroundImage:none;">
				<uv-text class="content"  text="评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容" color="#78889c"  size="14px" align="left"></uv-text>
			</uv-read-more>

			<view class="reply-info">
				<uv-text class="reply-create-time" :lines="1" text="评论日期: 2024-12-10 21:00" color="#778195"  size="12px"></uv-text>
				<view class="reply-interaction">
					<view class="button">
						<uv-icon name="thumb-up" color="#676c7a" size="50rpx"></uv-icon>
						<uv-text class="data" text="114" color="#676c7a"  size="26rpx" align="left"></uv-text>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script setup lang="ts">
	import { ref } from 'vue';
	import useBaseStore from "@/stores/base"
	
	const baseStore=useBaseStore();
	let urls = ref([
		'https://via.placeholder.com/100x200.png/3c9cff/fff',
		'https://via.placeholder.com/200x200.png/3c9cff/fff',
		'https://via.placeholder.com/300x200.png/3c9cff/fff',
		'https://via.placeholder.com/280x200.png/3c9cff/fff',
		'https://via.placeholder.com/240x200.png/3c9cff/fff',
		'https://via.placeholder.com/180x200.png/3c9cff/fff',
		'https://via.placeholder.com/140x200.png/3c9cff/fff',
		'https://via.placeholder.com/150x200.png/3c9cff/fff',
		'https://via.placeholder.com/90x200.png/3c9cff/fff',		
	])
</script>

<style>
	.reply-item {
		display: flex;
		padding: 20rpx;
		margin-bottom: 15rpx;
		background: white;
	}
	.reply-item .user-avatar {
		margin-right: 15rpx;
	}
	.reply-content .user-name {
		padding: 10rpx 0;
	}
	.reply-content .content {
		padding-bottom: 15rpx;
	}
	.reply-content .reply-info {
		display: flex;
		align-items: center;
		padding: 10rpx 0;
	}
	.reply-content .reply-info>* {
		flex: 2;
	}
	.reply-info .reply-interaction {
		flex: 1;
		display:flex;	
		justify-content: space-around;
	}
	.reply-info .reply-interaction .button {
		display:flex;
		margin-left: 15rpx;
	}
</style>
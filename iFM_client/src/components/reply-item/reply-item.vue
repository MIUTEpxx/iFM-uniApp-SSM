<template>
	<view class="reply-item">
		<view class="user-avatar">
			<uv-avatar :src="baseStore.baseUrl+props.userPicture" size="40px"></uv-avatar>
		</view>
		<view class="reply-content">
			<uv-text class="user-name" :lines="2" :text="props.userName" color="#47515d"  size="16px" align="left"></uv-text>
			<uv-read-more show-height="100rpx" :toggle="true" color="#61b7f9" closeText="显示全部" shadowStyle="backgroundImage:none;">
				<uv-text class="content"  :text="props.replyDetail" color="#78889c"  size="14px" align="left"></uv-text>
			</uv-read-more>

			<view class="reply-info">
				<uv-text class="reply-create-time" :lines="1" :text="'回复日期: '+changeTimeStamp(props.replyCreateTime
				)" color="#778195"  size="12px"></uv-text>
				<view class="reply-interaction">
					<view class="button">
						<uv-icon name="thumb-up" color="#676c7a" size="50rpx"></uv-icon>
						<uv-text class="data" :text="props.replyLikeCount" color="#676c7a"  size="26rpx" align="left"></uv-text>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script setup lang="ts">
	import { ref } from 'vue';
	import useBaseStore from "@/stores/base"
	import { changeTimeStamp } from "@/utils/timeChange"
	
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
	
	const props = defineProps({
		replyId: {
			type: Number,
			default: -1
		},
		replyCreateTime:{
			type: Number,
			default: 1734153004
		},
		replyDetail: {
			type: String,
			default: "回复内容"
		},
		replyLikeCount:{
			type: Number,
			default: 0
		},
		userId:{
			type: Number,
			default: -1
		},
		userName:{
			type: String,
			default: "匿名用户"
		},
		userPicture:{
			type: String,
			default: "/images/user/head/D.png"
		},
		haveLike:{
			type:Boolean,
			default:false
		}
	});
	
	
	
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
	.reply-content {
		width: 100%;
	}
	.reply-content .user-name {
		padding: 10rpx 0;
		padding-bottom: 15rpx;
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
<template>
	<view class="comment-item">
		<view class="user-avatar">
			<uv-avatar :src="baseStore.baseUrl+'/images/user/head/D.png'" size="40px"></uv-avatar>
		</view>
		<view class="comment-content">
			<uv-text class="user-name" :lines="2" text="用户昵称" color="#47515d"  size="14px" align="left"></uv-text>
			<uv-read-more show-height="100rpx" :toggle="true" color="#61b7f9" closeText="显示全部" shadowStyle="backgroundImage:none;">
				<uv-text class="content"  text="评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容评论内容" color="#78889c"  size="14px" align="left"></uv-text>
			</uv-read-more>
			<view class="comment-picture">
				<uv-album :urls="urls" singleSize="130rpx" multipleSize="200rpx" space="8rpx" maxCount="3"  singleMode="aspectFill"></uv-album>
			</view>
			<view class="comment-reply" v-if="props.showReply">
				<view class="reply">
					<span>用户昵称:</span>
					<uv-text class="reply-content" :lines="1" text="回复内容" size="14px" color="#55aaff" align="left"></uv-text>
				</view>
				<view class="reply">
					<span>用户昵称:</span>
					<uv-text class="reply-content" :lines="1" text="回复内容" size="14px" color="#55aaff" align="left"></uv-text>
				</view>
				<view class="more-reply" @click="goToMoreReply">
					<uv-text class="reply-content" text="更多回复 >" size="14px" color="#55aaff" align="left"></uv-text>
				</view>
			</view>
			<view class="comment-info">
				<uv-text class="comment-create-time" :lines="2" text="评论日期: 2024-12-10 21:00" color="#778195"  size="12px"></uv-text>
				<view class="comment-interaction">
					<view class="button">
						<uv-icon name="chat" color="#676c7a" size="50rpx"></uv-icon>
						<uv-text class="data" text="114" color="#676c7a"  size="26rpx" align="left"></uv-text>
					</view>
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
	import useBaseStore from "@/stores/base"
	import { ref } from "vue";
	
	const baseStore=useBaseStore();
	
	const props = defineProps({
		showReply: {
			type: Boolean,
			default: true
		},
	});
	
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
	const goToMoreReply =()=> {
		uni.navigateTo({
		  url: "/pages/community/more-reply",
		});   
	}
</script>

<style>
	.comment-item {
		margin-bottom: 25rpx;
		padding: 20rpx;
		display: flex;
		background: white;
	}
	.comment-item .user-avatar {
		margin-right: 15rpx;
	}
	.comment-content .user-name {
		padding: 10rpx 0;
	}
	.comment-content .content {
		padding-bottom: 15rpx;
	}
	.comment-content .comment-picture {
		padding: 15rpx 0;
	}
	.comment-content .comment-reply {
		padding: 10rpx;
		background: #e9eff5;
		border-radius: 5rpx;
	}
	.comment-reply .reply {
		display:flex;
		margin-bottom: 5rpx;
	}
	.comment-reply .reply>span {
		margin-right: 5rpx;
		font-size: 14px;
		color: #55aaff;
	}
	.comment-content .comment-info {
		display: flex;
		padding: 10rpx 0;
	}
	.comment-content .comment-info>* {
		flex: 2;
	}
	.comment-info .comment-interaction {
		flex: 1;
		display:flex;
	}
	.comment-info .comment-interaction .button {
		display:flex;
		margin-left: 15rpx;
	}
</style>
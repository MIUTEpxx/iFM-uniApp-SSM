<template>
	<view class="post-detail">
		<uv-sticky class="post-top-sticky" bgColor="white" customStyle="top:-35rpx;">
			<head-general titleContent="帖子正文" ></head-general>
		</uv-sticky>
		<view class="post-detail-head">
			<uv-text  text="这里是帖子标题" lineHeight="60rpx" color="#39414b" align="left" size="24px" bold="true"></uv-text>
			<post-head></post-head>
		</view>
		<view class="post-detail-body">
			<uv-text class="post-detail"  text="帖子详情内容帖子详情内容帖子详情内容帖子详情内容帖子详情内容帖子详情内容帖子详情内容帖子详情内容帖子详情内容帖子详情内容帖子详情内容帖子详情内容帖子详情内容帖子详情内容帖子详情内容帖子详情内容" lineHeight="40rpx" color="#363e47"  size="16px"></uv-text>
		</view>
		<view class="post-detail-picture" v-if="urls.length!=0">
			<uv-album :urls="urls" singleSize="230rpx" multipleSize="230rpx" space="8rpx" maxCount="9"  singleMode="aspectFill"></uv-album>
		</view>
		<view class="post-detail-association">
			<uv-text class="text" :lines="1" text="关联节目" color="#86c7f9"  size="18px"></uv-text>
			<broadcast-item></broadcast-item>
		</view>
		<post-interaction></post-interaction>
		<view class="post-comment-section">
			<comment-item></comment-item>
			<comment-item></comment-item>
		</view>
		<uv-popup ref="popup" mode="bottom" custom-style="background: #dce8f9; height: 1500rpx;">
			<view class="more-reply-content">
				<comment-item :showReply="false"></comment-item>
				<scroll-view scroll-y class="more-reply-scroll">
					<reply-item></reply-item>
					<reply-item></reply-item>
					<reply-item></reply-item>
					<reply-item></reply-item>
					<reply-item></reply-item>
					<reply-item></reply-item>
					<uv-load-more status="nomore" customStyle="padding:5px 0 10px 0;" />
				</scroll-view>
			</view>
		</uv-popup>
		<button @click="open">打开</button>
	</view>
</template>

<script setup lang="ts">
	import { onShow } from "@dcloudio/uni-app";
	import useBaseStore from "@/stores/base"
	import { ref } from "vue";
	
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
	
	const popup = ref<any>(null);
	
	const open = () => {
	  if (popup.value) {
	    popup.value.open();
	  }
	};
</script>

<style>
	.post-top-sticky {
		/* border-bottom: 1rpx solid #d3deec; */
	}
	.post-detail-head {
		/* padding-bottom: 35rpx; */
		margin: 15rpx 20rpx;
	}
	.post-detail-body {
		margin:0 25rpx 30rpx 25rpx;
		min-height: 280rpx;
	}
	
	.post-detail-picture {
		display: flex;
		/* justify-content: center; */
		margin: 0 15rpx;
		padding:0 7rpx;
	}
	.post-detail-picture * {
		border-radius: 10rpx;
	}
	.post-detail-association {
		margin: 25rpx 10rpx;
	}
	.post-detail-association .text {
		padding-left: 15rpx;
	}
	.post-comment-section {
		min-height: 200rpx;
		background: #dce8f9;
		padding-top: 25rpx;
	}
	.more-reply-content .more-reply-scroll {
		height: 950rpx;
	}
/* 	.more-reply-content {
		background: #dce8f9;
	} */
</style>
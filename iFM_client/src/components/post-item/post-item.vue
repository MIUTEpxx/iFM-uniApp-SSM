<template>
	<view class="post-item">
		<post-head></post-head>
		<view class="post-item-body" @click="goToPostDetail">
			<uv-text class="post-titel" :lines="1" text="帖子标题帖子标题帖子标题帖子标题帖子标题帖子标题" lineHeight="60rpx" color="#39414b"  size="18px" bold="true"></uv-text>
			<uv-text class="post-detail" :lines="2" text="帖子详情内容帖子详情内容帖子详情内容帖子详情内容帖子详情内容帖子详情内容帖子详情内容帖子详情内容" lineHeight="40rpx" color="#78889c"  size="16px"></uv-text>
		</view>
		<view class="post-item-picture" v-if="urls.length!=0">
			<uv-album :urls="urls" singleSize="230rpx" multipleSize="230rpx" space="8rpx" maxCount="6"  singleMode="aspectFill"></uv-album>
		</view>
		<post-interaction iconSize="50" textSize="26"></post-interaction>
		
	</view>
</template>

<script setup lang="ts">
import { onShow } from "@dcloudio/uni-app";
import useBaseStore from "@/stores/base"
import { ref } from "vue";
	
	const baseStore=useBaseStore()
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
	
	onShow(() => {
	  //隐藏pages.json里配置的导航栏，使用封装的tabbar组件
	  	uni.hideTabBar({
	          animation:false
	       })
	});
	const goToPostDetail =()=>{
		uni.navigateTo({
		  url: "/pages/community/post-detail",
		});   
	}
</script>

<style>
	.post-item {
		padding: 0 15rpx 10rpx 15rpx;
		margin-bottom: 25rpx;
		background: white;
		box-shadow: 0 0 5rpx 0.02rpx #bdc9dd;
		/* box-shadow: 0px 0px 3px 1px #b5becb; */
	}
	.post-item-head {
		display: flex;
	}
	.post-item-head>* {
		flex: 1;
	}
	.post-item-head .user-info {
		flex: 2;
		display: flex;
	}
	.post-item-head .user-info >* {
		margin:15rpx 20rpx 15rpx 5rpx;
	}
	.post-item-body {
		margin:0 5rpx;
	}
	.post-item-body .post-titel {
		padding-bottom: 5rpx;
	}
	.post-item-picture {
		display: flex;
		/* justify-content: center; */
		margin-top: 20rpx;
		padding:0 7rpx;
	}


</style>
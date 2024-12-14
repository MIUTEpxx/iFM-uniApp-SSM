<template>
	<view class="post-detail">
		<uv-sticky class="post-top-sticky" bgColor="white" customStyle="top:-35rpx;">
			<head-general titleContent="帖子正文" ></head-general>
		</uv-sticky>
		<view class="post-detail-head">
			<uv-text  :text="post.postTitle" lineHeight="60rpx" color="#39414b" align="left" size="24px" bold="true"></uv-text>
			<post-head
			:userId="post.userId" 
			:userName="post.userName" 
			:userPicture="post.userPicture"
			:postCreateTime="post.postCreateTime"
			:postSection="post.postSection"></post-head>
		</view>
		<view class="post-detail-body">
			<uv-text class="post-detail"  :text="post.postDetail" lineHeight="40rpx" color="#363e47"  size="16px"></uv-text>
		</view>
		<view class="post-detail-picture" v-if="postImageList.length!=0">
			<uv-album :urls="postImageList" singleSize="230rpx" multipleSize="230rpx" space="8rpx" maxCount="9"  singleMode="aspectFill"></uv-album>
		</view>
		<view class="post-detail-association" v-if="post.postAssociation!=-1">
			<uv-text class="text" :lines="1" text="关联节目" color="#86c7f9"  size="18px"></uv-text>
			<channel-item v-if="post.postAssociation==0" v-bind="association"></channel-item>
			<broadcast-item v-else v-bind="association"></broadcast-item>
		</view>
		<post-interaction 
		:postId="post.postId"
		:postCollectionCount="postCollectionCount"
		:postLikeCount="postLikeCount"
		:postCommentCount="post.postCommentCount"
		v-bind="post"
		:haveCollection="haveCollect"
		></post-interaction>
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
	import { onLoad, onShow } from "@dcloudio/uni-app";
	import useBaseStore from "@/stores/base"
	import { ref } from "vue"; 
	import { checkPostCollection, getBroadcastDetail, getChannelDetail, getPostByPostId } from "@/request/api";
	import useUserStore from "@/stores/user";
	
	const baseStore=useBaseStore();
	const userStore=useUserStore();
	//帖子id
	const postId = ref(0);
	//关联内容
	const association = ref();
	//弹窗索引
	const popup = ref<any>(null);
	//帖子是否已被收藏
	let haveCollect = ref();
	//帖子数据
	let post = ref<any>([])
	//帖子图片数据
	let postImageList = ref<any>([])
	let postLikeCount = ref(-1);
	let postCollectionCount = ref(-1);
	const open = () => {
	  if (popup.value) {
	    popup.value.open();
	  }
	};
	
	onLoad((options:any)=>{
		postId.value = JSON.parse(options.postId); // 字符串转对象
		//获取帖子详情内容
		getPostByPostId(postId.value).then((res:any)=>{
			//console.log(res);
			if(res.success==true){
				post.value=res.data.post
				postImageList.value = post.value.postImageList.map((postImage:any) => {
					return useBaseStore().baseUrl + postImage;
				});
				postLikeCount.value=post.postLikeCount
				postCollectionCount.value=post.postCollectionCount
				//console.log(post.value)
			}else{
				uni.showToast({
					title: res.message+'\n'+res.data.error,
					icon: 'error',
					duration: 6000
				}) 
			}
		}).catch((err:any) => { 
			console.error('获取帖子详情数据请求失败', err); 
		});
		//获取帖子关联内容
		if(post.value.postAssociation==0){
			getChannelDetail(post.value.associationId).then((res:any)=>{
				if(res.success==true){
					association.value=res.data.channel
				}else{
					uni.showToast({
						title: res.message+'\n'+res.data.error,
						icon: 'error',
						duration: 6000
					}) 
				}
			}).catch((err:any) => { 
				console.error('关联频道数据请求失败', err); 
			});
		}else if(post.value.postAssociation==1){
			getBroadcastDetail(post.value.associationId).then((res:any)=>{
				if(res.success==true){
					association.value=res.data.broadcast
				}else{
					uni.showToast({
						title: res.message+'\n'+res.data.error,
						icon: 'error',
						duration: 6000
					}) 
				}
			}).catch((err:any) => { 
				console.error('关联节目数据请求失败', err); 
			});
		}
		
		if(userStore.isLogin==true){
			//检查帖子是否已被用户收藏
			checkPostCollection(userStore.userId,postId.value).then((res:any)=>{
				if(res.success==true){
					haveCollect.value=res.data.favorite
					//console.log(haveCollect.value)
				}else{
					uni.showToast({
						title: res.message+'\n'+res.data.error,
						icon: 'error',
						duration: 6000
					}) 
				}
			}).catch((err:any) => { 
				console.error('检查帖子是否被收藏请求失败', err); 
			});
		}

		
	})
	
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
		min-height: 200rpx;
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
<template>
	<view class="post-item">
		<post-head 
		:userId="props.userId" 
		:userName="props.userName" 
		:userPicture="props.userPicture"
		:postCreateTime="props.postCreateTime"
		:postSection="props.postSection"></post-head>
		<view class="post-item-body" @click="goToPostDetail">
			<uv-text class="post-titel" :lines="1" :text="props.postTitle" lineHeight="60rpx" color="#39414b"  size="18px" bold="true"></uv-text>
			<uv-text class="post-detail" :lines="2" :text="props.postDetail" lineHeight="40rpx" color="#78889c"  size="16px"></uv-text>
		</view>
		<view class="post-item-picture" v-if="props.postImageList.length!=0">
			<uv-album :urls="props.postImageList" singleSize="500rpx" multipleSize="230rpx" space="8rpx" maxCount="6"  singleMode="aspectFill"></uv-album>
		</view>
		<!-- <view class="line"></view> -->
		<view class="post-hashtag">
			<uv-tags class="hashtag" v-for="(item,i) in props.postHashtagList" :key="i" :text="item.hashtagName" plain size="mini" color="#86c7f9" borderColor="#86c7f9"></uv-tags>
		</view>
		<post-interaction 
		:postId="props.postId"
		:postLikeCount="props.postLikeCount" 
		:postCommentCount="props.postCommentCount" 
		:postCollectionCount="props.postCollectionCount" 
		:haveCollect="haveCollect"
		:commentClickGoDetail="true"
		iconSize="50" 
		textSize="26"></post-interaction>
	</view>
</template>

<script setup lang="ts">
import { onShow } from "@dcloudio/uni-app";
import useBaseStore from "@/stores/base"
import { ref, watch } from "vue";
import useUserStore from "@/stores/user";
import { checkPostCollection } from "@/request/api";
	
	const baseStore=useBaseStore()
	const userStore=useUserStore()
	
	//父组件传递数据
	const props = defineProps({
		postId: {
			type: Number,
			default: -1
		},
		postCreateTime:{
			type: Number,
			default: 1734153004
		},
		postTitle: {
			type: String,
			default: "默认帖子标题"
		},
		postDetail: {
			type: String,
			default: "默认帖子详情"
		},
		postImageList: {
			type:Array,
			default:[
		'https://via.placeholder.com/100x200.png/3c9cff/fff',
		'https://via.placeholder.com/200x200.png/3c9cff/fff',
		'https://via.placeholder.com/300x200.png/3c9cff/fff',
		'https://via.placeholder.com/280x200.png/3c9cff/fff',
		'https://via.placeholder.com/240x200.png/3c9cff/fff',
		'https://via.placeholder.com/180x200.png/3c9cff/fff',
		'https://via.placeholder.com/140x200.png/3c9cff/fff',
		'https://via.placeholder.com/150x200.png/3c9cff/fff',
		'https://via.placeholder.com/90x200.png/3c9cff/fff',]
		},
		postHashtagList: {
			type:Array,
			default:[
				{hashtagId:1,hashtagName:"标签1"},
				{hashtagId:2,hashtagName:"标签2"},
				{hashtagId:3,hashtagName:"标签3"},
			]
		},
		postCollectionCount:{
			type: Number,
			default: 0
		},
		postLikeCount:{
			type: Number,
			default: 0
		},
		postCommentCount:{
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
		postSection:{
			type: Number,
			default: 0
		},
	});
	
	let haveCollect = ref(false)
	
	onShow(()=>{
		if(props.postId!=-1&&userStore.isLogin==true){
			//检查帖子是否已被用户收藏
			checkPostCollection(userStore.userId,props.postId).then((res:any)=>{
				if(res.success==true){
					haveCollect.value=res.data.favorite
					//console.log(props.postId,haveCollect.value)
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
	
	const goToPostDetail =()=>{
		uni.navigateTo({
		  url: "/pages/community/post-detail?postId="+props.postId,
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
	.post-hashtag {
		display: flex;
		margin:15rpx 0 0 5rpx;
	}
	.line {
		margin-top: 15rpx;
		border-bottom: 2px solid #dce8f9;
	}
	.post-hashtag>* {
		margin-right: 15rpx;
	}
</style>
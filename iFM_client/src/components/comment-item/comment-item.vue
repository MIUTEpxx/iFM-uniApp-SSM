<template>
	<view class="comment-item">
		<view class="user-avatar">
			<uv-avatar :src="baseStore.baseUrl+props.userPicture" size="40px"></uv-avatar>
		</view>
		<view class="comment-content">
			<uv-text class="user-name" :lines="2" :text="props.userName" color="#2a3138"  size="16px" align="left"></uv-text>
			<uv-read-more show-height="100rpx" :toggle="true" color="#61b7f9" closeText="显示全部" shadowStyle="backgroundImage:none;">
				<uv-text class="content"  :text="props.commentDetail" color="#78889c"  size="14px" align="left"></uv-text>
			</uv-read-more>
			<view class="comment-picture">
				<uv-album :urls="commentImageList" singleSize="400rpx" multipleSize="200rpx" space="8rpx" maxCount="3"  singleMode="aspectFill"></uv-album>
			</view>
			<view class="comment-reply" v-if="props.showReply&&replyList.length>0" @click="showMoreReply">
				<view class="reply" v-for="(item,i) in replyList" :key="i">
					<span>{{item.userName}}:</span>
					<uv-text class="reply-content" :lines="1" :text="item.replyDetail" size="14px" color="#535e6c" align="left"></uv-text>
				</view>
				<view class="more-reply" v-if="replyList.length>2">
					<uv-text class="reply-content" text="更多回复 >" size="14px" color="#55aaff" align="left"></uv-text>
				</view>
			</view>
			<view class="comment-info">
				<uv-text class="comment-create-time" :lines="2" :text="'评论日期: '+changeTimeStamp(props.commentCreateTime)" color="#778195"  size="12px"></uv-text>
				<view class="comment-interaction">
					<view class="button" @click="replyComment">
						<uv-icon  name="chat" color="#676c7a" size="50rpx"></uv-icon>
						<uv-text class="data" :text="props.commentReplyCount" color="#676c7a"  size="26rpx" align="left"></uv-text>
					</view>
					<view class="button"  @click="likeClick">
						<uv-icon v-if="!haveLike" name="thumb-up" color="#676c7a" size="50rpx"></uv-icon>
						<uv-icon v-else name="thumb-up-fill" color="#86c7f9" size="50rpx"></uv-icon>
						<uv-text class="data" :text="commentLikeCount" color="#676c7a"  size="26rpx" align="left"></uv-text>
					</view>
				</view>
			</view>
			
		</view>
	</view>
</template>

<script setup lang="ts">
	import useBaseStore from "@/stores/base"
	import { ref, watch } from "vue";
	import { changeTimeStamp } from "@/utils/timeChange"
	import useUserStore from "@/stores/user";
	import { changeCommentLike, getReplyByCommentId } from "@/request/api";
	import { onShow } from "@dcloudio/uni-app";
	
	const baseStore=useBaseStore();
	const userStore=useUserStore()
	const props = defineProps({
		showReply: {
			type: Boolean,
			default: true
		},
		commentId: {
			type: Number,
			default: -1
		},
		commentCreateTime:{
			type: Number,
			default: 1734153004
		},
		commentDetail: {
			type: String,
			default: "评论内容"
		},
		commentImageList: {
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
		commentLikeCount:{
			type: Number,
			default: 0
		},
		commentReplyCount:{
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
	
	//父页面传递方法
	const emit = defineEmits(['showMoreReply','replyComment']);
	const showMoreReply = () => {
		emit('showMoreReply');
	};
	const replyComment = () =>{
		emit('replyComment');
	}
	
	//评论点赞数
	let commentLikeCount = ref(props.commentLikeCount);
	//评论是否已被用户点赞
	let haveLike = ref(props.haveLike)
	//评论图片
	let commentImageList = ref(props.commentImageList)
	//回复数据列表
	let replyList = ref<any>([])
	// 立即修改 commentImageList 的值
	commentImageList.value = commentImageList.value.map((postImage: any) => {
	  return baseStore.baseUrl + postImage;
	});
	
	onShow(()=>{
		if(props.commentId!=-1){
			//获取评论下的回复
			getReplyByCommentId(props.commentId).then((res:any)=>{
				//console.log("getReplyByCommentId",res);
				if(res.success==true){
					//获取前3的回复数据
					replyList.value=res.data.replyList.slice(0, 3);
				}else{
					uni.showToast({
						title: res.message+'\n'+res.data.error,
						icon: 'error',
						duration: 6000
					}) 
				}
			}).catch((err:any) => { 
				console.error('评论下的回复数据获取请求失败', err); 
			});
		}
	})
	
	// 监听 props 的变化并更新本地引用
	watch(() => props.commentId, (newVal) => {
		 console.log("getReplyByCommentId");
	  if(props.commentId!=-1){
		  //获取评论下的回复
		  getReplyByCommentId(props.commentId).then((res:any)=>{
			  console.log("getReplyByCommentId",res);
			  if(res.success==true){
				  //获取前3的回复数据
			  	replyList.value=res.data.replyList.slice(0, 3);
			  }else{
			  	uni.showToast({
			  		title: res.message+'\n'+res.data.error,
			  		icon: 'error',
			  		duration: 6000
			  	}) 
			  }
		  }).catch((err:any) => { 
			console.error('评论下的回复数据获取请求失败', err); 
		});
	  }
	});
	watch(() => props.commentLikeCount, (newVal) => {
	  commentLikeCount.value = newVal;
	});
	watch(() => props.haveLike, (newVal) => {
	  haveLike.value = newVal;
	});
	watch(() => props.commentImageList, (newVal) => {
	  commentImageList.value = newVal.map((postImage:any) => {
	  	return baseStore.baseUrl + postImage;
	  });
		console.log("commentImageList",commentImageList)
	});
	
	const likeClick =()=>{
		console.log(123123)
		if(!userStore.isLogin){
			uni.showToast({
				title: '请登录后操作',
				icon: 'error',
				duration: 1000
			}) 
			return;
		}
		let value = 1;
		if(haveLike.value==false){
			value=1;
			haveLike.value=true;
		}else{
			value=-1
			haveLike.value=false;
		}
		changeCommentLike(props.commentId,value).then((res:any) => {
			if(res.success==false){
				//点赞失败
				uni.showToast({
				title: res.message + '\n' + res.data.error,
				icon: 'error',
				duration: 3000
				});
			}
		}).catch((err:any) => { 
			console.error('帖子点赞请求失败', err); 
		}); 
		//更改点赞数据
		commentLikeCount.value+=value;
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
	.comment-content {
		width: 100%;
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
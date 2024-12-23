<template>
	<view class="post-interaction">
		<!-- <uv-line color="#809ab5"></uv-line> -->
		<view class="line"></view>
		<view class="post-button">
			<view class="button" @click="likeClick">
				<uv-icon v-if="!haveLike" name="thumb-up" color="#676c7a" :size="props.iconSize+'rpx'"></uv-icon>
				<uv-icon v-else name="thumb-up-fill" color="#86c7f9" :size="props.iconSize+'rpx'"></uv-icon>
				<uv-text class="data" :text="postLikeCount" color="#676c7a"  :size="props.textSize+'rpx'" align="left"></uv-text>
			</view>
			<view class="button" @click="CommentClick">
				<uv-icon  name="chat" color="#858b9d" :size="props.iconSize+'rpx'"></uv-icon>
				<uv-text class="data" :text="props.postCommentCount" color="#676c7a"  :size="props.textSize+'rpx'" align="left"></uv-text>
			</view>
			<view class="button" @click="collectClick">
				<uv-icon v-if="!haveCollect" name="star" color="#858b9d" :size="props.iconSize+'rpx'"></uv-icon>
				<uv-icon v-else name="star-fill" color="#86c7f9" :size="props.iconSize+'rpx'"></uv-icon>
				<uv-text class="data" :text="postCollectionCount" color="#676c7a"  :size="props.textSize+'rpx'" align="left"></uv-text>
			</view>
		</view>
	</view>
</template>

<script setup lang="ts">
	import { ref, watch } from 'vue';
	import { changePostCollection, changePostLike } from '@/request/api';
	import useUserStore from '@/stores/user';
	
	const userStore = useUserStore()
	
	const props = defineProps({
		iconSize: {
			type: Number,
			default: 65
		},
		textSize:{
			type: Number,
			default: 30
		},
		postId:{
			type: Number,
			default: 0
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
		haveCollect:{
			type:Boolean,
			default:false,
		},
		haveLike:{
			type:Boolean,
			default:false
		},
		commentClickGoDetail:{
			type:Boolean,
			default:false
		}
	});
	//父页面传递方法
	const emit = defineEmits(['commentClick']);
	const CommentClick = () => {
		if(props.commentClickGoDetail) {
			//前往详情页
			uni.navigateTo({
			  url: "/pages/community/post-detail?postId="+props.postId,
			});  
		}
		else { 
			//打开帖子评论弹窗
			emit('commentClick'); 
		}
		
	};
	
	let haveLike = ref(props.haveLike);
	let haveCollect = ref(props.haveCollect);
	let postLikeCount = ref(props.postLikeCount);
	let postCollectionCount = ref(props.postCollectionCount);
	
	// 监听 props 的变化并更新本地引用
	watch(() => props.postLikeCount, (newVal) => {
	  postLikeCount.value = newVal;
	});
	watch(() => props.postCollectionCount, (newVal) => {
	  postCollectionCount.value = newVal;
	});
	watch(() => props.haveLike, (newVal) => {
	  haveLike.value = newVal;
	});
	watch(() => props.haveCollect, (newVal) => {
	  haveCollect.value = newVal;
	});
	
	const likeClick =()=>{
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
		changePostLike(props.postId,value).then((res:any) => {
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
		postLikeCount.value+=value;
	}
	
	const collectClick =()=>{
		if(!userStore.isLogin){
			uni.showToast({
				title: '请登录后操作',
				icon: 'error',
				duration: 1000
			}) 
			return;
		}
		let value = 1;
		if(haveCollect.value==false){
			value=1;
			haveCollect.value=true;
		}else{
			value=-1
			haveCollect.value=false;
		}
		changePostCollection(userStore.userId,props.postId).then((res:any) => {
			if(res.success==false){
				//收藏操作失败
				uni.showToast({
				title: res.message + '\n' + res.data.error,
				icon: 'error',
				duration: 3000
				});
			}
		}).catch((err:any) => { 
			console.error('帖子收藏请求失败', err); 
		}); 
		//更改点赞数据
		postCollectionCount.value+=value;
	}
</script>

<style scoped>
	.line {
		border-bottom: 2px solid #dce8f9;
	}
	.post-interaction {
		margin-top: 15rpx;
		padding-bottom: 10rpx;
	}
	.post-interaction .post-button {
		display: flex;
	    justify-content: space-between;
	    align-items: center;	
		margin-top: 10rpx;
	}
	.post-interaction .post-button>*{
		flex: 1;
	}
	.post-button .button {
		display: flex;
	    align-items: center;
	    justify-content: center;	
	}
	.post-button .button>* {
		flex:1;
		display: flex;
		justify-content: flex-end;
	}
</style>
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
			<uv-read-more ref="readMore" show-height="600rpx" :toggle="true" color="#61b7f9" closeText="显示全部">
				<uv-parse class="content" :content="post.postDetail" @load="load"></uv-parse>
				<!-- <uv-text class="post-detail"  :text="post.postDetail" lineHeight="40rpx" color="#363e47"  size="16px"></uv-text> -->
			</uv-read-more>
		</view>
		<view class="post-detail-picture" v-if="postImageList.length!=0">
			<uv-album :urls="postImageList" singleSize="700rpx" multipleSize="230rpx" space="8rpx" maxCount="9"  singleMode="aspectFill"></uv-album>
		</view>
		<view class="post-detail-association" v-if="post.postAssociation==0">
			<uv-text class="text" :lines="1" text="关联频道" color="#86c7f9"  size="18px"></uv-text>
			<channel-item v-bind="association"></channel-item>
		</view>
		<view class="post-detail-association" v-else-if="post.postAssociation==1">
			<uv-text class="text" :lines="1" text="关联节目" color="#86c7f9"  size="18px"></uv-text>
			<broadcast-item v-bind="association"></broadcast-item>
		</view>
		<!-- 点赞,评论,收藏 -->
		<post-interaction 
		:postId="post.postId"
		:postCollectionCount="postCollectionCount"
		:postLikeCount="postLikeCount"
		:postCommentCount="post.postCommentCount"
		@commentClick="openComment"
		v-bind="post"
		:haveCollection="haveCollect"
		></post-interaction>
		<!-- 评论区 -->
		<view class="post-comment-section">
			<view class="comment-sort">
				<!-- 评论排序 -->
				<uv-text text="评论区" color="#61b7f9" size="24px"></uv-text>
				<uv-tabs 
				@click="sortMethodClick"
				:list="[{name:'热门'},{name:'最近'},{name:'最早'}]"
				lineWidth="20" 
				lineColor="#61b7f9" 
				:activeStyle="{
					color: '#303133',
					fontWeight: 'bold',
					transform: 'scale(1.05)'
				}"
				:inactiveStyle="{
					color: '#606266',
					transform: 'scale(1)'
				}" 
				itemStyle="padding:0 5px 5px 5px; height: 34px;"
				></uv-tabs>		
			</view>
			<comment-item 
			v-for="(item,i) in commentList" :key="i" 
			v-bind="item"
			@replyComment="openReply(item,i)"
			@showMoreReply="openMoreReply(i)"></comment-item>
			<uv-text text="没有更多评论了" color="#8e9aa7"  size="16px" align="center"></uv-text>
		</view>
		
		<uv-popup ref="popupComment" mode="bottom" custom-style="background: #dce8f9; height: 1500rpx;">
			<view class="write-comment-content">
				<view class="head">
					<uv-icon @click="closeComment" name="close-circle" color="#86c7f9" size="65rpx" ></uv-icon>
					<uv-button type="primary" @click="commentButtonClick" text="发布评论" color="#6eabf6" customStyle="width: 200rpx;"></uv-button>
				</view>
				<view class="body">
					<view class="comment-image">
						<uv-text :text="'添加图片 '+fileList.length+'/6'" color="#767f8b" size="20px" align="left"></uv-text>
						<uv-upload 
						name="3" multiple 
						:fileList="fileList" 
						:maxCount="6" 
						:previewFullImage="true"
						maxSize="10*1024*1024"
						width="180rpx" 
						height="180rpx"
						uploadIcon="plus-circle-fill"
						uploadIconColor="#d7e3ee"	
						accept="image"
						@afterRead="handleAfterRead"
						@delete="deletePic"
						customStyle="
						">
						<view class="upload-comment-image">
							<span>+</span>
						</view>
						</uv-upload>
					</view>
					<view class="comment-input">
						<uv-text text="评论内容" color="#767f8b" size="20px" align="left"></uv-text>
						<uv-textarea
						class="post-input"
						v-model="commentDetail" 
						placeholder="情输入评论内容"
						adjustPosition="true" 
						count="true"
						maxlength=150
						height="250rpx"
						border="none"
						customStyle="background:#ebeff5;"
						countStyle="background-color:rgba(0, 0, 0, 0.0);"
						textStyle="font-size:32rpx;color:#404753;"
						placeholderStyle="font-size:32rpx;color:#717d93;">
						</uv-textarea>
					</view>
				</view>
			</view>
		</uv-popup>
		
		<uv-popup ref="popupMoreRely" mode="bottom" custom-style="background: #dce8f9; height: 1500rpx;">
			<view class="more-reply-content">
				<comment-item 
				v-bind="commentList[currentCommentIndex]" 
				@replyComment="openReply(commentList[currentCommentIndex],currentCommentIndex)"
				:showReply="false"></comment-item>
				<scroll-view scroll-y class="more-reply-scroll">
					<reply-item 
					v-for="(item,i) in replyList" 
					:key="i" 
					v-bind="item"></reply-item>
					<uv-load-more status="nomore" customStyle="padding:5px 0 10px 0;" />
				</scroll-view>
			</view>
		</uv-popup>
		
		<uv-popup ref="popupReply" mode="bottom" custom-style="background: #dce8f9; height: 1500rpx;">
			<comment-item 
			v-bind="commentList[currentCommentIndex]" 
			:showReply="false"></comment-item>
			<view class="write-reply-content">
				<view class="head">
					<uv-icon @click="closeReply" name="close-circle" color="#86c7f9" size="65rpx" ></uv-icon>
					<uv-button type="primary" @click="replyButtonClick" text="回复评论" color="#6eabf6" customStyle="width: 200rpx;"></uv-button>
				</view>
				<view class="body">
					<view class="comment-input">
						<uv-text text="回复内容" color="#767f8b" size="20px" align="left"></uv-text>
						<uv-textarea
						class="post-input"
						v-model="replyDetail" 
						placeholder="情输入回复内容"
						adjustPosition="true" 
						count="true"
						maxlength=150
						height="250rpx"
						border="none"
						customStyle="background:#ebeff5;"
						countStyle="background-color:rgba(0, 0, 0, 0.0);"
						textStyle="font-size:32rpx;color:#404753;"
						placeholderStyle="font-size:32rpx;color:#717d93;">
						</uv-textarea>
					</view>
				</view>
			</view>
		</uv-popup>
		
		<popup-window
		ref="popupRefComment"
		popupTitleText="确认发布评论"
		popupWindowText="请检查填写的评论内容"
		leftButtonText="暂不发布"
		rightButtonText="确定发布"
		@leftButtonClick="concelComment"
		@rightButtonClick="confirmComment">
		</popup-window>
		<popup-window
		ref="popupRefReply"
		popupTitleText="确认回复评论"
		popupWindowText="请检查填写的回复内容"
		leftButtonText="暂不回复"
		rightButtonText="确定回复"
		@leftButtonClick="concelReply"
		@rightButtonClick="confirmReply">
		</popup-window>
	</view>
</template>

<script setup lang="ts">
	import { onLoad, onShow } from "@dcloudio/uni-app";
	import useBaseStore from "@/stores/base"
	import { nextTick, ref } from "vue"; 
	import { addComment, addImageForComment, addReply, checkPostCollection, getBroadcastDetail, getChannelDetail, getCommentByPostId, getPostByPostId, getReplyByCommentId } from "@/request/api";
	import useUserStore from "@/stores/user";
	import { logOut } from "@/utils/logOut";
	import { sortCommentClick } from "@/utils/commentSort";
	
	const baseStore=useBaseStore();
	const userStore=useUserStore();
	//帖子id
	const postId = ref(0);
	//关联内容类型 0:频道 1:节目
	const postAssociation = ref(-1)
	//关联内容的id
	const associationId = ref(-1)
	//关联内容
	const association = ref();
	
	//更多回复弹窗索引
	const popupMoreRely = ref<any>(null);
	//发布评论弹窗索引
	const popupComment = ref<any>(null);
	//发布回复弹窗索引
	const popupReply = ref<any>(null);
	//确认发布评论弹窗索引
	const  popupRefComment = ref<any>(null);
	//确认发布回复弹窗索引
	const  popupRefReply = ref<any>(null);
	//
	const readMore =ref<any>(null);
	
	//评论文本内容
	let commentDetail= ref("");
	//上传的评论图片
	let fileList = ref<any>([]);
	//回复文本内容
	let replyDetail= ref("");
	
	
	//帖子下的评论
	let commentList = ref<any>([])
	//评论下的回复
	let replyList = ref<any>([])
	
	//帖子是否已被收藏
	let haveCollect = ref();
	//帖子数据
	let post = ref<any>([])
	//帖子图片数据
	let postImageList = ref<any>([])
	//帖子点赞数据
	let postLikeCount = ref(-1);
	//帖子收藏数据
	let postCollectionCount = ref(-1);
	//帖子评论数
	let postCommentCount = ref(-1);
	
	//当前显示的更多回复的评论的索引
	let currentCommentIndex = ref(0)
	//当前回复的评论的id
	let currentCommentId = ref(0)
	
	//当前排序方式
	let currentSortMethod=ref(0)
	//点击排序选项
	const sortMethodClick =(e:any)=>{
		currentSortMethod.value=e.index;
		commentList.value = sortCommentClick(commentList.value,currentSortMethod.value)
	}
	
	//打开发布回复弹窗
	const openReply =  (item:any,index:number) => {
		if(!userStore.isLogin){
			uni.showToast({
				title: '请登录后操作',
				icon: 'error',
				duration: 1000
			}) 
			return;
		}
		console.log(123132)
	  if (popupReply.value) {
	    popupReply.value.open();
	  }
	  currentCommentId.value = item.commentId;
	  currentCommentIndex.value=index;
	};
	//关闭回复发布弹窗
	const closeReply = () => {
	  if (popupReply.value) {
	    popupReply.value.close();
	  }
	  currentCommentId.value = 0;
	  currentCommentIndex.value=0;
	};
	//发布回复按钮 确认提醒弹窗
	const replyButtonClick =()=>{
		if(replyDetail.value==""){
			uni.showToast({
				title: '回复内容不能为空',
				icon: 'error',
				duration: 2000
			}) 
			return;
		}
		//打开弹窗
		 if (popupRefReply.value) {
		    popupRefReply.value.open();
		}
	}
	//取消发布回复
	const concelReply =()=>{
		if (popupRefReply.value) {
		    popupRefReply.value.close();
		}
	}
	
	//确认回复评论
	const confirmReply =()=>{
		if(userStore.isLogin==false||userStore.userId==-1){
			logOut();
			return;
		}
	addReply(userStore.userId, currentCommentId.value,replyDetail.value,).then(async (res: any) => {
	/* console.log("res", res); */
		concelReply();
		if (res.success == true) {
			uni.showToast({
				title: '评论发布成功!',
				icon: 'none',
				duration: 3000
			});
			// 设置1秒后刷新页面
			setTimeout(() => {
				uni.redirectTo({
					 url: "/pages/community/post-detail?postId="+postId.value,
				});
			}, 1000); // 1000毫秒后执行
			replyDetail.value=""
			closeReply();
			postCollectionCount.value++;
		} else {
			uni.showToast({
			title: res.message + '\n' + res.data.error,
			icon: 'error',
			duration: 3000
			});
		}
		}).catch((err: any) => {
			console.error('评论发布请求失败', err);
		});
		
	}
	
	//打开更多回复底部弹窗
	const openMoreReply = (index:number) => {
	  currentCommentIndex.value=index;
	  if (popupMoreRely.value) {
	    popupMoreRely.value.open();
	  }
	  const commentId = commentList.value[index].commentId;
	  getReplyByCommentId(commentId).then((res:any)=>{
	  	console.log("getReplyByCommentId",res);
	  	if(res.success==true){
	  		//获取前3的回复数据
	  		replyList.value=res.data.replyList;
			console.log("replyList",replyList.value)
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
	  
	};
	
	//打开帖子评论发布弹窗
	const openComment = () => {
		if(!userStore.isLogin){
			uni.showToast({
				title: '请登录后操作',
				icon: 'error',
				duration: 1000
			}) 
			return;
		}
	  if (popupComment.value) {
	    popupComment.value.open();
	  }
	};
	//关闭评论发布弹窗
	const closeComment = () => {
	  if (popupComment.value) {
	    popupComment.value.close();
	  }
	};
	
	//发布评论按钮 确认提醒弹窗
	const commentButtonClick =()=>{
		if(commentDetail.value==""){
			uni.showToast({
				title: '评论内容不能为空',
				icon: 'error',
				duration: 2000
			}) 
			return;
		}
		//打开弹窗
		 if (popupRefComment.value) {
		    popupRefComment.value.open();
		}
	}
	
	//取消发布评论
	const concelComment =()=>{
		if (popupRefComment.value) {
		    popupRefComment.value.close();
		}
	}
	
	//确认发布评论
	const confirmComment =()=>{
		if(userStore.isLogin==false||userStore.userId==-1){
			logOut();
			return;
		}
	addComment(userStore.userId, postId.value,commentDetail.value,).then(async (res: any) => {
		/* console.log("res", res); */
		//关闭确认弹窗
		concelComment();
		if (res.success == true) {
			// 评论非图片内容发布成功,并获得评论id
			const commentId = res.data.commentId;
			if (fileList.value.length > 0) {
			// 为评论发布其图片数据
				for (const [index, file] of fileList.value.entries()) {
					await addImageForComment(userStore.userId,commentId, file).then((res_: any) => {
						console.log("res_",res_);
						if (res_.success != true) {
							uni.showToast({
							title: res_.message + '\n' + res_.data.error,
							icon: 'error',
							duration: 3000
							});
						}
					}).catch((err: any) => {
						console.error('评论图片内容发布请求失败', err);
					});
					uni.showToast({
					title: '评论发布中!',
					icon: 'none',
					duration: 1000
					});
					 await new Promise(resolve => setTimeout(resolve, 500)); // 等待0.5秒
				}
				fileList.value = [];
				// 设置1秒后刷新页面
				setTimeout(() => {
					uni.redirectTo({
						url: "/pages/community/post-detail?postId=" + postId.value,
					});
				}, 2000); // 1000毫秒后执行
			}
			
			// 循环结束后执行
			await uni.showToast({
				title: '评论发布成功!',
				icon: 'none',
				duration: 2000
			});
			//清空输入的评论内容
			commentDetail.value="" ;
			closeComment();
			postCollectionCount.value++;
		} else {
			uni.showToast({
			title: res.message + '\n' + res.data.error,
			icon: 'error',
			duration: 3000
			});
		}
		}).catch((err: any) => {
		console.error('评论发布请求失败', err);
		});
		
	}
	
	// 删除图片
	function deletePic(event:any) {
		fileList.value.splice(event.index, 1);
		console.log("fileList",fileList.value);
	}
	
	// 处理文件读取后的逻辑
	function handleAfterRead(event:any) {
		// 确保 event.file 是一个数组
		  let files = Array.isArray(event.file) ? event.file : [event.file];
		  
		  // 遍历文件数组，将每个文件添加到 fileList 中
		  files.forEach((file:any) => {
		    fileList.value.push({
		      ...file,
		      status: 'success ', // 可以设置文件的上传状态
		      message: '' // 上传状态的消息
		    });
		  });
		 console.log("fileList", fileList.value);
		
	}
	
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
				postCommentCount.value = post.postCommentCount
				postCollectionCount.value=post.postCollectionCount
				postAssociation.value=post.value.postAssociation
				//console.log(post.value)
				//获取帖子关联内容
				if(postAssociation.value==0){
					//关联频道
					associationId.value=post.value.associationId
					getChannelDetail(associationId.value).then((res:any)=>{
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
				}else if(postAssociation.value==1){
					//关联节目
					associationId.value=post.value.associationId
					getBroadcastDetail(associationId.value).then((res:any)=>{
						if(res.success==true){
							console.log(res);
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
		
		//获取帖子下的评论
		getCommentByPostId(postId.value).then((res:any)=>{
			if(res.success==true){
				commentList.value = res.data.commentList;
				/* console.log(commentList.value); */
				//对评论进行排序
				commentList.value = sortCommentClick(commentList.value,currentSortMethod.value)
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

		//
		nextTick(() => {
		  if (readMore.value) {
		    readMore.value.init();
			/* console.log("readMore.value.init();") */
		  }
		});
		
	})
	//
	const load =()=>{
		setTimeout(()=>{
			readMore.value.init();
		},1000)
	}
	
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
		justify-content: center;
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
	.post-comment-section .comment-sort {
		display:flex;
		margin-bottom: 25rpx;
		padding: 15rpx;
		background-color: #fff;
	}
	.more-reply-content .more-reply-scroll {
		height: 950rpx;
	}
	.write-comment-content {
		background: white;
	}
	.write-comment-content .head {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin: 0 25rpx 0 15rpx;
		padding-top: 25rpx;
	}
	.write-comment-content .body .comment-image {
		margin: 15rpx;
	}
	.write-comment-content .body .comment-image>:first-child {
		padding-bottom: 15rpx;
	}
	.comment-image .upload-comment-image {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 180rpx;
		height: 180rpx;
		background: #d7e3ee;	
		border-radius: 10rpx;	
	}
	.comment-image .upload-comment-image >span {
		margin-bottom: 15rpx;
		color: #ffffff;
		font-size: 100rpx;
		font-weight: 900;
	}
	.comment-input {
		margin: 15rpx;
		padding-bottom: 35rpx;
	}
	.comment-input > :first-child {
		padding-bottom: 10rpx;
	}
	.write-reply-content {
		background: white;
	}
	.write-reply-content .head {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin: 0 25rpx 0 15rpx;
		padding-top: 25rpx;
	}
</style>
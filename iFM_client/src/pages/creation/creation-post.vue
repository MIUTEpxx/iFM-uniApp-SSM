<template>
	<view class="creation-post">
		<head-general titleContent="帖子创建"></head-general>
		<view class="creation-post-body">
			<view class="creation-post-input">
				<uv-text text="帖子标题" color="#767f8b" size="20px" align="left"></uv-text>
				<uv-textarea
				class="post-input"
				v-model="postTitle" 
				placeholder="请输入至少五字的帖子标题"
				adjustPosition="true" 
				count="true"
				maxlength=32
				autoHeight="true"
				border="none"
				customStyle="background:#ebeff5;"
				countStyle="background-color:rgba(0, 0, 0, 0.0);"
				textStyle="font-size:32rpx;color:#404753;"
				placeholderStyle="font-size:32rpx;color:#717d93;">
				</uv-textarea>
				
				<uv-text text="帖子详情" color="#767f8b" size="20px" align="left"></uv-text>
				<uv-textarea
				class="post-input"
				v-model="postDetail" 
				placeholder="情输入帖子详情内容"
				adjustPosition="true" 
				count="true"
				maxlength=3000
				height="250rpx"
				border="none"
				customStyle="background:#ebeff5;"
				countStyle="background-color:rgba(0, 0, 0, 0.0);"
				textStyle="font-size:32rpx;color:#404753;"
				placeholderStyle="font-size:32rpx;color:#717d93;">
				</uv-textarea>
			</view>
			<view class="post-image">
				<uv-text :text="'添加图片 '+fileList.length+'/9'" color="#767f8b" size="20px" align="left"></uv-text>
				<uv-upload 
				name="3" multiple 
				:fileList="fileList" 
				:maxCount="9" 
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
				<view class="upload-post-image">
					<span>+</span>
				</view>
				</uv-upload>
			</view>
			<view class="post-section">
				<uv-text text="选择帖子所属板块" color="#767f8b" size="20px" align="left"></uv-text>
				<uv-tabs
				class="section-choose"
				@click="choosePostSection"
				:list="[{key:0,name:'话题讨论'},{key:1,name:'分享推荐'},{key:2,name:'求助问答'}]"
				lineWidth="165rpx" 
				lineColor="#63b1f9" 
				:activeStyle="{
					color: '#63b1f9',
					fontSize:'40rpx',
					transform: 'scale(1.05)',
				}"
				:inactiveStyle="{
					color: '#2d2e30',
					fontSize:'40rpx',
					transform: 'scale(1)'
				}" 
				itemStyle="padding-bottom:15rpx; height: 34px;"
				></uv-tabs>	
			</view>
			<view class="post-association">
				<uv-text text="选择帖子关联帖子或节目" color="#767f8b" size="20px" align="left"></uv-text>
				<view class="choose-association" v-if="associationId==-1" @click="goAssociationChoose">
					<span>+</span>
				</view>
				<channel-item 
				class="choose-association_" 
				v-else-if="associationType==0"  
				:enableComponentClick="false" 
				:showSubscribeButton="false" 
				@click="goAssociationChoose" 
				v-bind="association"></channel-item>
				<broadcast-item 
				class="choose-association_" 
				v-else-if="associationType==1"  
				:enableComponentClick="false"
				:showPlayButton="false" 
				@click="goAssociationChoose" 
				v-bind="association"></broadcast-item>
			</view>
			<view class="post-hashtag">
				<uv-text :text="'帖子主题标签 当前已选择 '+checkboxValue.length+' 个标签'" color="#767f8b" size="20px" align="left"></uv-text>
				<uv-checkbox-group class="post-hashtag-checkbox" v-model="checkboxValue" placement="row" iconPlacement="left">
					<uv-checkbox v-for="(item,i) in hashtagList" :key="i" 
					:name="item.hashtagId" :label="item.hashtagName" 
					size="40rpx" customStyle="minWidth: 148rpx;"
					></uv-checkbox>				
				</uv-checkbox-group>
			</view>
			<view class="create-button">
				<uv-button type="primary" @click="creatPostButton" text="创建" color="#6eabf6" customStyle="width: 600rpx;"></uv-button>
			</view>
			<popup-window
			ref="popupRef"
			popupTitleText="确认创建帖子"
			popupWindowText="请检查填写的帖子信息"
			leftButtonText="取消创建"
			rightButtonText="确定创建"
			@leftButtonClick="concelCreatepost"
			@rightButtonClick="confirmCreatepost">
			</popup-window>
		</view>
	</view>
</template>

<script setup lang="ts">
	import { onLoad, onShow, onUnload } from '@dcloudio/uni-app';
	import { ref } from 'vue';
	import { addImageForPost, createPost, getBroadcastDetail, getChannelDetail, getHashtag } from "@/request/api"
	import useUserStore from '../../stores/user';
	import { logOut } from '../../utils/logOut';
	
	const userStore = useUserStore();
	//弹窗索引
	const popupRef  = ref<any>(null);
	//帖子标题
	let postTitle=ref("");
	//帖子详情内容
	let postDetail=ref("");
	//帖子主题标签
	const hashtagList = ref<any>([])
	//选中的帖子主题标签
	let checkboxValue = ref([])
	//上传的图片
	let fileList = ref<any>([]);
	//帖子板块
	let postSection = ref(0)
	// 关联内容类型(-1无关联内容 0关联频道 1关联节目)
	let associationType = ref(-1);
	//关联内容id
	let associationId = ref(-1);
/* 	//关联频道数据
	let channel = ref()
	//关联节目数据
	let broadcast = ref() */
	//关联内容
	let association = ref<any>()
	
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
		associationType.value = JSON.parse(options.associationType); 
		associationId.value = JSON.parse(options.associationId); // 字符串转对象
		//获取关联内容
		getAssociation();
	})
	
	onShow(() => {
		 // 监听acceptDataFromOpenedPage事件来接收数据
		 uni.$on('acceptDataFromOpenedPage', (data) => {
		   console.log('Received data:', data);
			associationType.value=data.associationType;
			associationId.value=data.associationId;
		 });
		//获取关联内容
		getAssociation();

		//获取主题标签数据
		getHashtag ().then((res:any) => {
			hashtagList.value=res.data.hashtagList
			//console.log(hashtagList.value)
		}).catch((err:any) => { 
		  console.error('主题标签数据请求失败', err); 
		});
	});
	
	onUnload(()=> {
	    // 当页面卸载时，移除事件监听
	    uni.$off('acceptDataFromOpenedPage');
	})
	 
	 const getAssociation =()=>{
		 //判断是否有接收关联内容数据
		 if(associationId.value!=-1){
		 	if(associationType.value==0){
		 		//获取频道详情信息
		 		getChannelDetail(associationId.value).then((res: any) => {
		 		  association.value = res.data.channel;
		 		}).catch((err: any) => {
		 		  console.error('频道数据请求失败', err);
		 		});
		 	}else{
		 		//获取节目详情信息
		 		getBroadcastDetail(associationId.value).then((res: any) => {
		 		  association.value = res.data.broadcast;
		 		}).catch((err: any) => {
		 		  console.error('节目数据请求失败', err);
		 		});
		 	}
		 }
	 } 
	  
	//前往帖子关联内容选择页
	const goAssociationChoose =()=>{
		uni.navigateTo({
		  url: "/pages/creation/post-association-choose",
		});
	}
	
	//选择板块
	const choosePostSection =(item:any)=>{
		postSection.value = item.key;
	}
	//创建帖子按钮
	const creatPostButton =()=>{
		if(postTitle.value==""||postDetail.value==""||checkboxValue.value.length==0){
			uni.showToast({
				title: '节目标题,详情和主题标签信息不能为空',
				icon: 'error',
				duration: 2000
			}) 
			return;
		}
		//打开弹窗
		 if (popupRef.value) {
		    popupRef.value.open();
		}
	}
	
	//取消创建帖子
	const concelCreatepost =()=>{
		if (popupRef.value) {
		    popupRef.value.close();
		}
	}
	
	//确认创建帖子
	const confirmCreatepost =()=>{
		if(userStore.isLogin==false||userStore.userId==-1){
			logOut();
			return;
		}
	createPost(
	userStore.userId, postTitle.value,
	postDetail.value, postSection.value,
	associationType.value, associationId.value,
	checkboxValue.value
	).then(async (res: any) => {
	/* console.log("res", res); */
	concelCreatepost();
		if (res.success == true) {
			const postId = res.data.postId;
			// 帖子基础信息创建成功
			if (fileList.value.length > 0) {
			// 为帖子添加图片数据
				for (const [index, file] of fileList.value.entries()) {
					await addImageForPost(postId, file).then((res_: any) => {
						if (res_.success != true) {
							uni.showToast({
							title: res_.message + '\n' + res_.data.error,
							icon: 'error',
							duration: 3000
							});
						}
					}).catch((err: any) => {
						console.error('帖子添加图片请求失败', err);
					});
					uni.showToast({
					title: '帖子创建中!',
					icon: 'none',
					duration: 1000
					});
					 await new Promise(resolve => setTimeout(resolve, 500)); // 等待0.5秒
				}
			}
			
			// 循环结束后执行
			await uni.navigateBack({
			delta: 1 // 默认返回上一级页面
			});

		} else {
			uni.showToast({
			title: res.message + '\n' + res.data.error,
			icon: 'error',
			duration: 3000
			});
		}
		}).catch((err: any) => {
		console.error('帖子创建请求失败', err);
		});
		
	}
	
</script>

<style scoped>
	.creation-post-body {
		display: flex;
		flex-direction: column;
		align-items: center;
	}
	.creation-post-body .creation-post-input {
		width: 90%;
	}
	.creation-post-body .creation-post-input .post-input {
		margin: 15rpx 0 50rpx 0;
	}
	.creation-post-body .post-image {
		width: 90%;
	}
	.creation-post-body .post-image>:first-child {
		padding-bottom: 15rpx;
	}
	.post-image .upload-post-image {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 180rpx;
		height: 180rpx;
		background: #d7e3ee;	
		border-radius: 10rpx;	
	}
	.post-image .upload-post-image >span {
		margin-bottom: 15rpx;
		color: #ffffff;
		font-size: 100rpx;
		font-weight: 900;
	}
	.creation-post-body .post-section {
		display: flex;
		flex-direction: column;
		align-items: flex-start;
		margin: 35rpx 0;
		width: 95%;
	}
	.creation-post-body .post-section>:first-child {
		padding-left: 15rpx;
	}
	.post-section .section-choose {
		margin-top: 15rpx;
	}
	.creation-post-body .post-association {
		width: 95%;
	}
	.creation-post-body .post-association >:first-child {
		padding-left: 15rpx;
	}
	.creation-post-body .post-association .choose-association {
		display: flex;
		justify-content: center;
		align-items: center;
		margin: 15rpx 15rpx;
		background: #d7e3ee;
		height: 200rpx;
		border-radius: 15rpx;
	}
	.creation-post-body .post-association .choose-association_ {
		display: flex;
		justify-content: center;
		align-items: center;
		margin: 15rpx 15rpx;
		height: 200rpx;
		border-radius: 15rpx;
	}
	.creation-post-body .post-association .choose-association >span {
		margin-bottom: 30rpx;
		color: #ffffff;
		font-size: 130rpx;
		font-weight: 900;
	}
	.creation-post-body .post-hashtag {
		margin-top: 35rpx;
		width: 90%;
	}
	.creation-post-body .post-hashtag>:first-child {
		padding-bottom: 20rpx;
	}
	.creation-post-body .post-hashtag .post-hashtag-checkbox>* {
		margin-right: 20rpx;
		margin-bottom: 15rpx;
	}
	.create-button {
		display: flex;
		justify-content: center;	
		margin-top: 25rpx;
		padding-bottom: 35rpx;
	}
</style>
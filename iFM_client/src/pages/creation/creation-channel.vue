<template>
	<view class="creation-channel">
		<head-general titleContent="频道创建"></head-general>
		<view class="create-channel-body">
			<view class="channel-image">
				<uv-upload
					:fileList="fileList" 
					name="6" 
					multiple 
					:maxCount="1" 
					width="420rpx" 
					height="420rpx"
					:previewFullImage="true"
					:deletable="true"
					accept="image"
					@afterRead="handleAfterRead"
					@delete="deletePic"
					custom-style="
					display: flex;
					justify-content: center;
					align-items: center;
					">
					<view class="upload-channel-image">
						<span>+</span>
					</view>
				</uv-upload>
			</view>
			<uv-text text="频道封面" color="#767f8b" size="22px" align="center"></uv-text>
			
			<view class="creation-channel-input">
				<uv-text text="频道标题" color="#767f8b" size="20px" align="left"></uv-text>
				<uv-textarea
				class="channel-input"
				v-model="channelTitle" 
				placeholder="请输入频道标题"
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
				
				<uv-text text="频道详情" color="#767f8b" size="20px" align="left"></uv-text>
				<uv-textarea
				class="channel-input"
				v-model="channelDetail" 
				placeholder="请输入频道详情内容"
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
			
			<view class="channel-hashtag">
				<uv-text :text="'频道主题标签 当前已选择 '+checkboxValue.length+' 个标签'" color="#767f8b" size="20px" align="left"></uv-text>
				<uv-checkbox-group class="channel-hashtag-checkbox" v-model="checkboxValue" placement="row" iconPlacement="left">
					<uv-checkbox v-for="(item,i) in hashtagList" :key="i" 
					:name="item.hashtagId" :label="item.hashtagName" 
					size="40rpx" customStyle="minWidth: 148rpx;"
					></uv-checkbox>				
				</uv-checkbox-group>
			</view>
			
		</view>
		<view class="create-button">
			<uv-button type="primary" @click="creatChannelButton" text="创建" color="#6eabf6" customStyle="width: 600rpx;"></uv-button>
		</view>
		<popup-window
		ref="popupRef"
		popupTitleText="确认创建频道"
		popupWindowText="请检查填写的频道信息"
		leftButtonText="取消创建"
		rightButtonText="确定创建"
		@leftButtonClick="concelCreateChannel"
		@rightButtonClick="confirmCreateChannel">
		</popup-window>
	</view>
</template>

<script setup lang="ts">
	import { createChannel, getHashtag } from "@/request/api"
	import { onShow } from '@dcloudio/uni-app';
	import { ref } from 'vue';
	import useUserStore from "../../stores/user";
	import { logOut }from "@/utils/logOut";

	
	const userStore = useUserStore();
	//上传的图片
	const fileList = ref([]);
	//频道标题
	let channelTitle = ref("")
	//频道详情内容
	let channelDetail = ref("")
	//频道主题标签
	const hashtagList = ref<any>([])
	//选中的频道主题标签
	let checkboxValue = ref([])
	
	//弹窗索引
	const popupRef  = ref<any>(null);
	
	// 删除图片
	function deletePic(event:any) {
		fileList.value.splice(event.index, 1);
	}

	// 处理文件读取后的逻辑
	function handleAfterRead(event:any) {
		// 直接将文件赋值给fileList
		fileList.value = event.file;
	}
	
	onShow(() => {
		//获取主题标签数据
		getHashtag ().then((res:any) => {
			hashtagList.value=res.data.hashtagList
			//console.log(hashtagList.value)
		}).catch((err:any) => { 
		  console.error('主题标签数据请求失败', err); 
		});
	});
	
	//创建节目按钮
	const creatChannelButton =()=>{
		if(fileList.value.length==0||channelTitle.value==""||
		channelDetail.value==""||checkboxValue.value.length==0){
			uni.showToast({
				title: '频道信息不能留空',
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
	
	//确定创建
	const confirmCreateChannel =()=>{
		if(userStore.isLogin==false||userStore.userId==-1){
			logOut();
			return;
		}
		createChannel(userStore.userId,channelTitle.value,channelDetail.value,fileList.value[0],checkboxValue.value)
		.then((res:any)=>{
			//console.log("res",res);
			if(res.success==true){
				uni.showToast({
					title: '频道创建成功!',
					icon: 'success',
					duration: 2000
				})
				setTimeout(() => {
				   uni.navigateBack({
				   	delta: 1 // 默认返回上一级页面
				   });
				}, 3000); // 这里的 3000 毫秒应该与 showToast 的 duration 相匹配
			}else{
				uni.showToast({
						title: res.message+'\n'+res.data.error,
						icon: 'error',
						duration: 3000
				}) 				
			}
		}).catch((err:any) => { 
			console.error('节目创建请求失败', err); 
		});
		//console.log("fileList",fileList.value)
		//关闭弹窗
		concelCreateChannel();
		
	}
	//关闭弹窗
	const concelCreateChannel =()=>{
		if (popupRef.value) {
		    popupRef.value.close();
		}
	}
	
</script>

<style scoped>
	.create-channel-body {
		display: flex;
		flex-direction: column;
		align-items: center;
	}
	.create-channel-body .channel-image {
		width: 400rpx;
		height: 400rpx;
		overflow: hidden;
		border-radius: 15rpx;
	}
	.upload-channel-image {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 400rpx;
		height: 400rpx;
		background: #d7e3ee;
	}
	.upload-channel-image >span {
		margin-bottom: 25rpx;
		color: white;
		font-size: 150rpx;
		font-weight: 900;
	}
	.create-channel-body .creation-channel-input {
		margin-top:50rpx;
		width: 90%;
	}
	.create-channel-body .creation-channel-input .channel-input {
		margin: 15rpx 0 50rpx 0;
	}
	.create-channel-body .channel-hashtag {
		width: 90%;
	}
	.create-channel-body .channel-hashtag>:first-child {
		padding-bottom: 20rpx;
	}
	.create-channel-body .channel-hashtag .channel-hashtag-checkbox>* {
		margin-right: 20rpx;
		margin-bottom: 15rpx;
	}
	.create-button {
		display: flex;
		justify-content: center;	
		margin-top: 25rpx;
	}
</style>
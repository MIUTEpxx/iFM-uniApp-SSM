<template>
	<view class="creation-broadcast">
		<head-general titleContent="节目创建"></head-general>
		<view class="create-broadcast-body">
			<view class="broadcast-image">
				<uv-upload
					:fileList="imageList" 
					name="6" 
					multiple 
					:maxCount="1" 
					width="420rpx" 
					height="420rpx"
					:previewFullImage="true"
					:deletable="true"
					accept="image"
					@afterRead="handleAfterReadImage"
					@delete="deletePic"
					custom-style="
					display: flex;
					justify-content: center;
					align-items: center;
					">
					<view class="upload-broadcast-image">
						<span>+</span>
					</view>
				</uv-upload>
			</view>
			<uv-text text="节目封面" color="#767f8b" size="22px" align="center"></uv-text>
			
			<view class="creation-broadcast-input">
				<uv-text text="节目标题" color="#767f8b" size="20px" align="left"></uv-text>
				<uv-textarea
				class="broadcast-input"
				v-model="broadcastTitle" 
				placeholder="请输入节目标题"
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
				
				<uv-text text="节目详情" color="#767f8b" size="20px" align="left"></uv-text>
				<uv-textarea
				class="broadcast-input"
				v-model="broadcastDetail" 
				placeholder="请输入节目详情内容"
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
			
			<view class="broadcast-audio">
				<uv-text v-if="audioList.length<=0" text="节目音频" color="#767f8b" size="20px" align="left" ></uv-text>
				<uv-text v-else :text="'已选择音频文件:'+audioList[0].name" color="#767f8b" size="20px" align="left" ></uv-text>
				<view class="audio" v-if="audioList.length<=0">
					<uv-upload
						:fileList="audioList" 
						name="6" 
						multiple 
						:maxCount="1" 
						width="680rpx" 
						height="200rpx"
						:previewFullImage="true"
						:deletable="true"
						accept="media "
						@afterRead="handleAfterReadAudio"
						@delete="deleteAudio"
						custom-style="
						display: flex;
						justify-content: center;
						align-items: center;
						">
						<view class="upload-audio">
							<span>+</span>
						</view>
					</uv-upload>
					<!-- <a v-if="audioList.length>0" :href="audioList[0].url">123123</a> -->
				</view>
				<view class="audio" v-else>
					<web-view :fullscreen="false" :src="audioList[0].url" style="padding-bottom: 100rpx;"></web-view>
					<uv-icon @click="deleteAudio" name="close-circle" color="#767f8b" size="40" style="padding-bottom: 120rpx;padding-left: 25rpx;" ></uv-icon>
				</view>
			</view>

			
		</view>
		<view class="create-button">
			<uv-button type="primary" @click="creatbroadcastButton" text="创建" color="#6eabf6" customStyle="width: 600rpx;"></uv-button>
		</view>
		<popup-window
		ref="popupRef"
		popupTitleText="确认创建节目"
		popupWindowText="请检查填写的节目信息"
		leftButtonText="取消创建"
		rightButtonText="确定创建"
		@leftButtonClick="concelCreatebroadcast"
		@rightButtonClick="confirmCreatebroadcast">
		</popup-window>
	</view>
</template>

<script setup lang="ts">
	import { createBroadcast, addAudioForBroadcast } from "@/request/api"
	import { onLoad, onShow } from '@dcloudio/uni-app';
	import { ref } from 'vue';
	import useUserStore from "../../stores/user";
	import { logOut }from "@/utils/logOut";

	
	const userStore = useUserStore();
	//节目所属频道
	const channelId = ref(-1);
	//上传的图片
	const imageList = ref([]);
	//上传的音频文件
	let audioList = ref<any>([]);
	//节目标题
	let broadcastTitle = ref("")
	//节目详情内容
	let broadcastDetail = ref("")
	//节目Id
	let broadcastId = ref(-1);
	
	//弹窗索引
	const popupRef  = ref<any>(null);
	
	// 删除图片
	function deletePic(event:any) {
		imageList.value.splice(event.index, 1);
	}
	// 删除音频
	function deleteAudio(event:any) {
		audioList.value = [];
	}
	
	// 处理图片文件读取后的逻辑
	function handleAfterReadImage(event:any) {
		// 直接将文件赋值给imageList
		imageList.value = event.file;
	}
	// 处理音频文件读取后的逻辑
	function handleAfterReadAudio(event:any) {
		// 直接将文件赋值给audioList
		audioList.value = event.file;
		console.log(audioList.value);
	}
	
	onLoad((options:any)=>{
		channelId.value = JSON.parse(options.channelId); // 字符串转对象
	})
	
	//创建节目按钮
	const creatbroadcastButton =()=>{
		if(imageList.value.length==0||broadcastTitle.value==""||
		broadcastDetail.value==""){
			uni.showToast({
				title: '节目信息不能留空',
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
	const confirmCreatebroadcast =()=>{
		if(userStore.isLogin==false||userStore.userId==-1){
			logOut();
			return;
		}
		//console.log("audioList",audioList)
		 createBroadcast(userStore.userId,channelId.value,broadcastTitle.value,broadcastDetail.value,imageList.value[0])
		.then((res:any)=>{
			//console.log("res",res);
			if(res.success==true){
				//获取节目id
				broadcastId.value=res.data.broadcastId;
				//为节目添加音频
				addAudioForBroadcast(userStore.userId,broadcastId.value,audioList.value[0]).then((res:any)=>{
					//console.log("res",res);
					if(res.success==true){
						uni.showToast({
							title: '节目创建成功!',
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
		//console.log("imageList",imageList.value)
		//关闭弹窗
		concelCreatebroadcast();
		
	}
	//关闭弹窗
	const concelCreatebroadcast =()=>{
		if (popupRef.value) {
		    popupRef.value.close();
		}
	}
	
</script>

<style scoped>
	.create-broadcast-body {
		display: flex;
		flex-direction: column;
		align-items: center;
	}
	.create-broadcast-body .broadcast-image {
		width: 400rpx;
		height: 400rpx;
		overflow: hidden;
		border-radius: 15rpx;
	}
	.upload-broadcast-image {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 400rpx;
		height: 400rpx;
		background: #d7e3ee;
	}
	.upload-broadcast-image >span {
		margin-bottom: 25rpx;
		color: white;
		font-size: 150rpx;
		font-weight: 900;
	}
	
	.create-broadcast-body .creation-broadcast-input {
		margin-top:50rpx;
		width: 90%;
	}
	.create-broadcast-body .creation-broadcast-input .broadcast-input {
		margin: 15rpx 0 50rpx 0;
	}
	
	.broadcast-audio {
		width: 90%;
	}
	.broadcast-audio>:first-child {
		padding-bottom: 15rpx;
	}
	.broadcast-audio .audio {
		width: 100%;
		height: 200rpx;
		display: flex;
		justify-content: center;
		align-items: flex-end;
		overflow: hidden;
		border-radius: 15rpx;
	}
	.create-broadcast-body .audio .upload-audio {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 1000rpx;
		height: 200rpx;
		background: #d7e3ee;
	}
	.upload-audio >span {
		margin-bottom: 15rpx;
		color: white;
		font-size: 100rpx;
		font-weight: 900;
	}
	.create-button {
		display: flex;
		justify-content: center;	
		margin-top: 25rpx;
	}
</style>
<template>
	<view class="edit-user-avatar">
		<view class="edit-title">
			<uv-text :lines="2" text="修改用户头像" color="#86c7f9" size="26px" bold="true"></uv-text>	
		</view>
		<view class="edit-user-avatar-body">
			<view class="user-avatar">
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
					<view class="upload-user-avatar">
						<span>+</span>
					</view>
				</uv-upload>
			</view>
			<uv-text text="点击上传图片" color="#767f8b" size="22px" align="center"></uv-text>
		</view>
		
		<view class="edit-button">
			<uv-text @click="cancelClick" align="left" text="取消修改" color="#76c7f9" size="20px"></uv-text>
			<uv-text @click="confirmClick" align="right" text="确定修改" color="#76c7f9" size="20px"></uv-text>
		</view>
	</view>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import useUserStore from '../../stores/user';
import { changeUserPicture } from '../../request/api';


//新头像图片文件
const fileList = ref([]);
// 删除图片
function deletePic(event:any) {
	fileList.value.splice(event.index, 1);
}
// 处理文件读取后的逻辑
function handleAfterRead(event:any) {
	// 直接将文件赋值给fileList
	fileList.value = event.file;
}
const userStore= useUserStore()
//父页面传入取消按钮对应的退出编辑方法
const props = defineProps({
  cancelClick: {
    type: Function,
    required: true
  },
});
const confirmClick =()=>{
	
	if(!userStore.isLogin){
		uni.showToast({
			title: '请登录后操作',
			icon: 'error',
			duration: 1000
		}) 
		return;
	}
	if(fileList.value.length <= 0){
		uni.showToast({
			title: '请上传图像文件',
			icon: 'error',
			duration: 3000
		}) 
		return;
	}
	changeUserPicture(userStore.userId,fileList.value[0]).then((res:any)=>{
		if(res.success==true){
			//修改成功
			props.cancelClick()
			userStore.userPicture=res.data.userPicture
			uni.showToast({
				title: "头像修改成功!",
				icon: 'success',
				duration: 2000
			}) 
			// 设置2秒后刷新页面
			setTimeout(() => {
				uni.redirectTo({
					 url: "/pages/my/my-detail",
				});
			}, 2000); // 2000毫秒后执行
		}else{
			uni.showToast({
				title: res.message+'\n'+res.data.error,
				icon: 'error',
				duration: 3000
			}) 
		}
	})
}
</script>

<style>
	/* 将toast的z-index设置为最高 */
	uni-toast {
	    z-index: 9999 !important; /* 根据实际情况进行调整 */
	}
	.edit-user-avatar {
		padding: 2% 5%;
		background: white;
		border-radius:10px;
	}
	.edit-title {
		margin-bottom: 15px;
	}
	.edit-user-avatar-body {
		display:flex;
	    flex-direction: column;
	    align-items: center;	
	}
	.edit-user-avatar-body .user-avatar {
		margin-bottom: 15rpx;
		width: 400rpx;
		height: 400rpx;
		overflow: hidden;
		border-radius: 15rpx;
	}
	.upload-user-avatar {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 400rpx;
		height: 400rpx;
		background: #d7e3ee;
	}
	.upload-user-avatar >span {
		margin-bottom: 25rpx;
		color: white;
		font-size: 150rpx;
		font-weight: 900;
	}
	.edit-button {
		display: flex;
		margin-top: 15px;
	}
	
</style>
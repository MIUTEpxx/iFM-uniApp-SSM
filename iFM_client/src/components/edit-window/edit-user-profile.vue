<template>
	<view class="edit-user-profile">
		<view class="edit-title">
			<uv-text :lines="2" text="修改个性签名" color="#86c7f9" size="26px" bold="true"></uv-text>	
		</view>
		<uv-textarea 
		v-model="newUserProfile" maxlength=50
		adjustPosition count
		placeholder="请输入新个性签名"
		height="110px"
		customStyle="background:#dbe3ee;"
		countStyle="background-color:#dbe3ee;"
		textStyle="font-size:20px;color:#404753;"
		placeholderStyle="font-size:20px;color:#717d93;"></uv-textarea>
		<view class="edit-button">
			<uv-text @click="cancelClick" align="left" text="取消修改" color="#76c7f9" size="20px"></uv-text>
			<uv-text @click="confirmClick" align="right" text="确定修改" color="#76c7f9" size="20px"></uv-text>
		</view>
	</view>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import useUserStore from '../../stores/user';
import { changeUserName, changeUserProfile } from '../../request/api';
import { logOut } from "@/utils/logOut"

//输入框输入的昵称
let newUserProfile =ref('')

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
	if(newUserProfile.value.trim().length <= 0){
		uni.showToast({
			title: '信息不能为空',
			icon: 'error',
			duration: 3000
		}) 
		return;
	}
	changeUserProfile(userStore.userId,newUserProfile.value).then((res:any)=>{
		if(res.success==true){
			//修改成功
			props.cancelClick()
			userStore.userProfile=newUserProfile.value
			uni.showToast({
				title: "昵称修改成功!",
				icon: 'success',
				duration: 2000
			}) 
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
	.edit-user-profile {
		padding: 2% 5%;
		background: white;
		border-radius:10px;
	}
	.edit-title {
		margin-bottom: 15px;
	}
	.edit-button {
		display: flex;
		margin-top: 15px;
	}

</style>
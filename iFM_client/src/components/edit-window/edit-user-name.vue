<template>
	<view class="edit-user-name">
		<view class="edit-title">
			<uv-text :lines="2" text="修改昵称" color="#86c7f9" size="26px" bold="true"></uv-text>	
		</view>
		<uv-textarea 
		v-model="newUserName" maxlength=30
		adjustPosition count
		placeholder="请输入新昵称"
		customStyle="background:#dbe3ee; height:50px"
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
import { changeUserName } from '../../request/api';
import { logOut } from '@/utils/logOut';
//输入框输入的昵称
let newUserName =ref('')

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
	if(newUserName.value.trim().length <= 0){
		uni.showToast({
			title: '信息不能为空',
			icon: 'error',
			duration: 3000
		}) 
		return;
	}
	changeUserName(userStore.userId,newUserName.value).then((res:any)=>{
		if(res.success==true){
			//修改成功
			props.cancelClick()
			userStore.userName=newUserName.value
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
	.edit-user-name {
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
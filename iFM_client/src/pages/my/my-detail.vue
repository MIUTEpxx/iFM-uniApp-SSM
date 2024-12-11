<template>
	<view class="my-detail">
		<head-general titleContent="用户信息"></head-general>
		<view class="my-detail-head">
			<uv-avatar
			:src="base_url+userStore.userPicture" 
			size="180px" :lazy-load="true"
			mode="aspectFill"></uv-avatar>		
			<p>点击更换头像</p>
		</view>
		<view class="my-detail-body">
			<view class="user-info">
				<span>账号:</span>
				<uv-text :text="userStore.userId" color="#5d5d5d"  size="28px"></uv-text>
			</view>
			<view class="user-info">
				<span>昵称:</span>
				<uv-text :text="userStore.userName" color="#5d5d5d"  size="26px"></uv-text>
				<uv-icon @click="editUserNameClick" name="bianji" custom-prefix="custom-icon" color="#a3b2ca" size="30px"></uv-icon>
			</view>
			<view class="user-info">
				<span>邮箱:</span>
				<uv-text :text="userStore.userEmail" color="#5d5d5d"  size="20px"></uv-text>
				<uv-icon @click="editUserEmailClick" name="bianji" custom-prefix="custom-icon" color="#a3b2ca" size="30px"></uv-icon>
			</view>
			<view class="user-info">
				<span>密码:</span>
				<uv-text :lines="2" text="点击修改密码" color="#5d5d5d"  size="26px"></uv-text>
				<uv-icon @click="editUserPasswordClick" name="bianji" custom-prefix="custom-icon" color="#a3b2ca" size="30px"></uv-icon>
			</view>
			<uv-divider :hairline="false"></uv-divider>
			<view class="user-profile">
				<span>个性签名:</span>
				<uv-icon @click="editUserProflieClick" name="bianji" custom-prefix="custom-icon" color="#a3b2ca" size="30px"></uv-icon>
			</view>
			<view class="user-profile-content">
				<uv-text :text="userStore.userProfile" color="#3f444d"  size="18px"></uv-text>
			</view>
		</view>
<!-- 		<uv-popup 
		ref="popupRef" mode="center" 
		:closeable="true" round="10px">
			<view class="content">
				<EditUserName     v-if="editUserName"></EditUserName>
				<EditUserEmail 	  v-if="editUserEmail"></EditUserEmail>
				<EditUserPassword v-if="editUserPassword"></EditUserPassword>
				<EditUserProfile  v-if="editUserProfile"></EditUserProfile>
			</view>
		</uv-popup> -->	 
		<uv-overlay class="overlay" :show="show">
			<EditUserName     :cancelClick="closeEdit"	class="overlay-content" v-if="editUserName"></EditUserName>
			<EditUserEmail 	  :cancelClick="closeEdit"	class="overlay-content" v-if="editUserEmail"></EditUserEmail>
			<EditUserPassword :cancelClick="closeEdit"	class="overlay-content" v-if="editUserPassword"></EditUserPassword>
			<EditUserProfile  :cancelClick="closeEdit"	class="overlay-content" v-if="editUserProfile"></EditUserProfile>
		</uv-overlay>
		
	</view>
</template>

<script setup lang="ts">
import EditUserEmail from '@/components/edit-window/edit-user-email.vue';
import EditUserName from '@/components/edit-window/edit-user-name.vue';
import EditUserPassword from '@/components/edit-window/edit-user-password.vue';
import EditUserProfile from '@/components/edit-window/edit-user-profile.vue';	
import useUserStore from '@/stores/user';
import useBaseStores from '@/stores/base';
import { onShow } from '@dcloudio/uni-app';
import { ref } from 'vue';

const base_url = useBaseStores().baseUrl;
const userStore = useUserStore()
let show=ref(false)
let editUserName =ref(false)
let editUserEmail =ref(false)
let editUserPassword =ref(false)
let editUserProfile =ref(false)
const popupRef = ref(null);

const editUserNameClick =()=>{
	show.value=true
	editUserName.value=true
	if (popupRef.value) {
		popupRef.value.open();
	}
}
const editUserEmailClick =()=>{
	show.value=true
	editUserEmail.value=true
}
const editUserPasswordClick =()=>{
	show.value=true
	editUserPassword.value=true
}
const editUserProflieClick =()=>{
	show.value=true
	editUserProfile.value=true
}
const closeEdit =()=>{
	show.value=false
	editUserName.value=false	
	editUserEmail.value=false
	editUserPassword.value=false
	editUserProfile.value=false
}
onShow(()=>{
	console.log("userEmail",userStore.userEmail)
})
</script>

<style>
	
	.my-detail-head {
		display: flex;
	    flex-direction: column;
	    align-items: center;	
		margin-top: 50px;
	}
	.my-detail-head>p {
		margin-top: 10px;
		color:#909bb9 ;
	}
	.my-detail-body {
		margin: 0 30px;
	}
	
	.my-detail-body .user-info{
		display: flex;
	    align-items: baseline;	
		margin: 30px 0;
	}
	.my-detail-body .user-info>span {
		margin-right: 20px;
		font-size: 26px;
		font-weight: 600;
		color: #000;
	}
	.my-detail-body .user-profile{
		display: flex;
	    align-items: center;
			margin-bottom: 15px;
	}
	.user-profile>span {
		margin-right: 20px;
		padding-bottom: 10pox;
		font-size: 20px;
		font-weight: 600;
		color: #7ab7e6;
	}
	.overlay {
		position: relative;	
	}
	.overlay .overlay-content{
		position: absolute;
		top: 20%;
		left: 50%; /* 设置 left 为 50% */
		transform: translateX(-50%); /* 使用 transform 平移元素使其水平居中 */
		width: 80%; /* 可选，根据需要设置宽度 */
	}
</style>
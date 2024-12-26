<template>
	<view class="my-detail">
		<head-general titleContent="用户信息"></head-general>
		<view class="my-detail-head" @click="editUserAvatarClick">
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
				<uv-text lines="3" :text="userStore.userEmail" color="#5d5d5d"  size="20px"></uv-text>
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
		<uv-overlay class="overlay" :show="show">
			<EditUserAvatar	  :cancelClick="closeEdit"	class="overlay-content" v-if="editUserAvatar"></EditUserAvatar>
			<EditUserName     :cancelClick="closeEdit"	class="overlay-content" v-if="editUserName"></EditUserName>
			<EditUserEmail 	  :cancelClick="closeEdit"	class="overlay-content" v-if="editUserEmail"></EditUserEmail>
			<EditUserPassword :cancelClick="closeEdit"	class="overlay-content" v-if="editUserPassword"></EditUserPassword>
			<EditUserProfile  :cancelClick="closeEdit"	class="overlay-content" v-if="editUserProfile"></EditUserProfile>
		</uv-overlay>
		<player-bar></player-bar>
	</view>
</template>

<script setup lang="ts">
import EditUserAvatar from '@/components/edit-window/edit-user-avatar.vue';
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
let editUserAvatar =ref<any>(false)
let editUserName =ref<any>(false)
let editUserEmail =ref<any>(false)
let editUserPassword =ref<any>(false)
let editUserProfile =ref<any>(false)

const editUserAvatarClick =()=>{
	show.value=true
	editUserAvatar.value=true
}
const editUserNameClick =()=>{
	show.value=true
	editUserName.value=true
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
	editUserAvatar.value=false
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
	/* 将toast的z-index设置为最高 */
	uni-toast {
	    z-index: 19999999 !important; /* 根据实际情况进行调整 */
	}
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
		z-index: 100;
		position: relative;	
	}
	.overlay .overlay-content{
		z-index: 100;
		position: absolute;
		top: 20%;
		left: 50%; /* 设置 left 为 50% */
		transform: translateX(-50%); /* 使用 transform 平移元素使其水平居中 */
		width: 80%; /* 可选，根据需要设置宽度 */
	}
</style>
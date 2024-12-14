<template>
	<view class="my">
		<head-general :showBackButton="false" :showHomeButton="false" titleContent="个人中心"></head-general>
		<my-head 
		v-if="userStore.isLogin"
		:userId="userStore.userId"
		:userName="userStore.userName"
		:userProfile="userStore.userProfile"
		:userPicture="userStore.userPicture"
		></my-head>
		<my-head v-else></my-head>
		<view class="my-body">
			<my-button @click="goCreation" textContent="创作中心" iconContent="jixuchuangzuo" iconSize="40"></my-button>
			<uv-divider :dot="true" :hairline="false"></uv-divider>
			<my-button @click="goMySubscribe" textContent="频道订阅" iconContent="dingyue1" iconSize="50"></my-button>
			<my-button @click='goMyFavorite' textContent="我的收藏" iconContent="shoucang" iconSize="50"></my-button>
			<my-button @click="goMyHistory" textContent="收听历史" iconContent="lishixiangmu_jilu" iconSize="50"></my-button>
			<uv-divider :dot="true" :hairline="false"></uv-divider>
			<my-button v-if="!userStore.isLogin" @click="goLogin" textContent="用户登录" iconContent="tuichudenglu1" iconSize="50"></my-button>
			<my-button v-else @click="logOut" textContent="退出登录" iconContent="tuichudenglu1" iconSize="50"></my-button>
		</view>
		<player-bar :reserveSpace="true"></player-bar>
		<tabbar></tabbar>
	</view>
</template>

<script setup lang="ts">
import {onShow} from "@dcloudio/uni-app";
import useUserStore from '@/stores/user';
import { ref } from "vue";
	
const userStore =useUserStore()

onShow(() => {
  //隐藏pages.json里配置的导航栏，使用封装的tabbar组件
  	uni.hideTabBar({
          animation:false
       })
});

const goLogin =()=>{			
	uni.navigateTo({
		url: "/pages/login-register/login"
	});   
}	
//跳转创作中心页
const goCreation =()=>{
	if(!userStore.isLogin){
		uni.showToast({
			title: '请登录后操作',
			icon: 'error',
			duration: 1000
		}) 
		return;
	}
	uni.navigateTo({
		url: "/pages/creation/creation"
	});  
}
//跳转我的收藏页
const goMyFavorite =()=>{
	if(!userStore.isLogin){
		uni.showToast({
			title: '请登录后操作',
			icon: 'error',
			duration: 1000
		}) 
		return;
	}
	uni.navigateTo({
		url: "/pages/my/my-favorite"
	});  
}
//跳转频道订阅页
const goMySubscribe =()=>{
	if(!userStore.isLogin){
		uni.showToast({
			title: '请登录后操作',
			icon: 'error',
			duration: 1000
		}) 
		return;
	}
	uni.navigateTo({
		url: "/pages/my/my-subscribe"
	});  
}
//跳转收听历史页
const goMyHistory =()=>{
	if(!userStore.isLogin){
		uni.showToast({
			title: '请登录后操作',
			icon: 'error',
			duration: 1000
		}) 
		return;
	}
	uni.navigateTo({
		url: "/pages/my/my-history"
	});  
}

const logOut =()=>{
	uni.showToast({
		title: '成功退出账号',
		icon: 'success',
		duration: 2000
	}) 
	//登出账号 清空用户信息
	userStore.clearUserInfo();
	//关闭所有页面 并重新打开个人中心页
	uni.reLaunch({
	    url: '/pages/my/my'
	})
}
</script>

<style>
	.my-head {
		margin: 0 30px;
	}
	.my-body {
		margin: 80px 20px;
	}
</style>
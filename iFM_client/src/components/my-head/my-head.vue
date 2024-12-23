<template>
	<view class="my-head">
		<view class="user-info" @click="goMyDetail">
			<view class="user-name">
			<uv-text class="name" :lines="3" :text="props.userName" color="#1d2b36" size="24px" bold="true"></uv-text>
			<uv-text v-if="props.isMyUser==true" text=">" color="#91a8c5" size="28px" bold="true" customStyle="margin-left:10px;"></uv-text>	
			</view>
			<uv-text :lines="3" :text="props.userProfile" color="#5782a3" size="18px"></uv-text>
		</view>
		<uv-avatar 
		:src="base_url+props.userPicture" 
		size="120px" :lazy-load="true"
		mode="aspectFill"></uv-avatar>		
	</view>	
</template>

<script setup lang="ts">
	import { defineProps, withDefaults } from 'vue';
	import useBaseStores from '@/stores/base';
	import useUserStore from '@/stores/user';
	
	const base_url = useBaseStores().baseUrl;
	const userStore = useUserStore()
	
	//该组件获取数据的接口
	interface IMyHead{      
		userId:number;
		userName:string;
		userProfile:string;
		userPicture:string;
		isMyUser:boolean;
	}
	//设置默认值
	const props = withDefaults(defineProps<IMyHead>(), {
		userId:-1,
		userName:'游客123',
		userProfile:"当前未登录",
		userPicture:"/images/user/head/D.png",
		isMyUser:true,
	});
	//跳转到个人详情页
	const goMyDetail =()=>{
		if(props.isMyUser==false) {
			return;
		}
		if(!userStore.isLogin){
			uni.showToast({
				title: '请登录后操作',
				icon: 'error',
				duration: 1000
			}) 
			return;
		}
		uni.navigateTo({
		  url: "/pages/my/my-detail",
		});   
	}
	
</script>

<style>
	.my-head{
		display:flex;
	    align-items: center;
	    justify-content: space-between;	

	
		.user-info {
			flex: 0 0 70%;
			display: flex;
	        flex-direction: column;
	        align-items: stretch;
			margin-right: 10px;
			
			.user-name {
				display: flex;
				align-items: baseline;
				margin-bottom: 10px;
				
				.name{
					flex:0 0 60%;
				}
			}
		}
	}
</style>
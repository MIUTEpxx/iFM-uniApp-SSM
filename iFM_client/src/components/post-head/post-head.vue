<template>
		<view class="post-head">
			<view class="user-post-head" @click="goUserDetail">
				<uv-avatar :src="baseStore.baseUrl+props.userPicture" size="45px"></uv-avatar>
				<uv-text class="user-name" :lines="2" :text="props.userName" color="#78889c"  size="14px" align="left" bold="true"></uv-text>
			</view>
			<view class="post-post-head">
				<uv-text class="post-section" :lines="1" :text="sectionName" color="#86c7f9"  size="18px" bold="true" align="right"></uv-text>
				<uv-text class="post-create-time" :lines="2" :text="'发帖日期: '+ changeTimeStamp(props.postCreateTime)" color="#778195"  size="12px" align="right"></uv-text>
			</view>
		</view>
</template>

<script setup lang="ts">
	import useBaseStore from "@/stores/base"
	import { computed, ref } from "vue";
	import { changeTime,changeTimeStamp } from "@/utils/timeChange";
	
	const baseStore=useBaseStore();
	
	const props = defineProps({
		userId:{
			type: Number,
			default: -1
		},
		userName: {
			type: String,
			default: "匿名用户"
		},
		userPicture:{
			type: String,
			default: "/images/user/head/D.png"
		},
		postCreateTime:{
			type: Number,
			default: 1734153004
		},
		postSection:{
			type: Number,
			default: 0
		},
	});
	// 使用计算属性来根据 postSection 的值设置 sectionName
		const sectionName = computed(() => {
			switch (props.postSection) {
				case 0:
					return "话题讨论";
				case 1:
					return "分享推荐";
				case 2:
					return "求助问答";
				default:
					return "未知板块";
			}
		});
	
	const goUserDetail = () =>{
		uni.navigateTo({
		  url: "/pages/user/user?userId="+props.userId,
		});   
	}
</script>

<style>
	
	.post-head {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 10rpx 0;
	}
	.post-head>* {
		flex: 1;
	}
	 .post-head .user-post-head {
		flex: 1.6;
		display: flex;
	}
	.post-head .user-post-head >* {
		margin:15rpx 20rpx 15rpx 5rpx;
	}
	.post-head .post-post-head .post-section{
		padding-bottom: 10rpx ;
	}
</style>
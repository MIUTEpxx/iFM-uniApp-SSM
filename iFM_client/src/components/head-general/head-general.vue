<template>
	<view class="head">
		<view class="back-button"   @click="goBack" v-if="props.showBackButton">
			<uv-icon name="arrow-left" :color="props.color" size="40px"></uv-icon>
		</view>
		<view class="home-button"   @click="goBackHome" v-if="props.showHomeButton">
			<uv-icon name="home" :color="props.color" size="50px"></uv-icon>
		</view>
		<view class="title">
			<uv-text :text="props.titleContent" :color="props.color" size="40px" bold="true" ></uv-text>
		</view>
	</view>
	
</template>

<script setup lang="ts">
import { defineProps, withDefaults } from 'vue';
import useBaseStore from '@/stores/base';
	interface IHead{
		showBackButton:boolean;
		showHomeButton:boolean;
		titleContent:string;
		color:string;
	}
	const props = withDefaults(defineProps<IHead>(), {
		showBackButton:true,
		showHomeButton:true,
		titleContent:"默认",
		color:"#86c7f9",
	});
	
	// 定义返回上一页的方法
	const goBack = () => {
		uni.navigateBack({
			delta: 1 // 默认返回上一级页面
		});
	};
	//返回首页
	const goBackHome = () => {
		//更新底部导航栏选中的索引
		useBaseStore().activeTab=0
		uni.switchTab({
		   url: '/pages/index/index'
		 });
	};
</script>

<style>
	.head{
		display: flex;
		align-items: center;
		padding: 20px;
		/* background-color: #fff; */
		.home-button {
			margin-left: 10px;
			padding-bottom:5px;
		}
		.title {
			padding-bottom: 5px;
			margin-left: auto;
		}
	}
</style>
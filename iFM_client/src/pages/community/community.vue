<template>
	<view class="community">
		<head-general :showBackButton="false" :showHomeButton="false" titleContent="社区中心"></head-general>
		<search-box></search-box>
		<uv-sticky class="community-head" bgColor="white" customStyle="top:0;">
			<view class="community-head-sort">
				<uv-text text="帖子板块" color="#86c7f9"  size="20px"></uv-text>
				<view class="sort-method">
					<uv-icon name="paixu" custom-prefix="custom-icon" color="#647182" size="26px"></uv-icon>
					<uv-drop-down
						ref="dropDown"
						sign="dropDown_1" 
						:defaultValue="[0,'0','all']"  
						customStyle="border-bottom:none;" 
					>
						<uv-drop-down-item 
							name="order" 
							type="2" 
							:label="currentSortMethod.name" 
							value="all">
						</uv-drop-down-item>
					</uv-drop-down>
					<uv-drop-down-popup sign="dropDown_1">
						<view class="drop-content">
							<sort-method-box 
							customStyle="margin:15px 0;height:40px;font-size:20px;" 
							:methodList="sortMethodList" 
							:current="currentSortMethod.key" 
							@change=""></sort-method-box>
						</view>
					</uv-drop-down-popup>		
				</view>
			</view>
			<tags-tabs customStyle="margin:0 10px 10px 10px;" :hashtag="sectionList"></tags-tabs>
		</uv-sticky>
		<view class="community-body">
			<post-item></post-item>
			<post-item></post-item>
			<post-item></post-item>
			<post-item></post-item>
			<post-item></post-item>
			<uv-text text="没有更多帖子了" color="#8e9aa7"  size="16px" align="center"></uv-text>
		</view>
		<player-bar :reserveSpace="true"></player-bar>
		<tabbar></tabbar>
	</view>
</template>

<script setup lang="ts">
	import { ref } from 'vue';
	//排序方法列表
	let sortMethodList =ref([''])
	let currentSortMethod=ref({key:0,name:'综合排序'})
	//帖子板块分类列表
	const sectionList=[{hashtagId:0,hashtagName:"综合热门"},{hashtagId:1,hashtagName:"话题讨论"},{hashtagId:2,hashtagName:"分享推荐"},{hashtagId:3,hashtagName:"求助问答"}]
</script>

<style>
	.community-head {
		border-bottom: 0.5rpx solid #98a0ab;
	}
	.community-head-sort {
		display:flex;
		margin: 0  0 0 25rpx;
	}
	.community-head-sort .sort-method {
		display:flex;
	}
	.community-body {
		background: #dce8f9;
		padding-top: 25rpx;
		padding-bottom: 140rpx;
	}
</style>
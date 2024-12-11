<template>
	<view class="channel-classify">
		
		<uv-sticky bgColor="white" customStyle="top:0;">
			<head-general titleContent="频道分类"></head-general>
			<tags-tabs customStyle="margin:0 10px 10px 10px;" @click="handleTabClick" :hashtag="hashtagList"></tags-tabs>
			<sort-method-box customStyle="margin-bottom:10px;" @change="sortMethodClick"></sort-method-box>	
		</uv-sticky>
		
		<view class="channel-classify-body">
			<scroll-view scroll-y class="channle-scroll">
				<channel-item v-for="(item,i) in channelList" :key="i" v-bind="item"></channel-item>
			</scroll-view>
		</view>
	</view>
	
	
</template>

<script setup lang="ts">
	import { onShow } from "@dcloudio/uni-app";
	import { getHashtag,getChannelByHashtag } from "@/request/api"; 
	import { ref,watch } from 'vue';
	import { sortChannelList } from '@/utils/channelSort';

	
	let hashtagCurrent = ref(10);//当前选择的标签id
	let sortMethodCurrent = ref(0);//当前排序方式
	let hashtagList = ref([]);//用于储存主题标签数据
	let channelList = ref<any>([]);
	onShow(() => {
		//获取主题标签数据
		getHashtag ().then((res:any) => {
			hashtagList.value=res.data.hashtagList
		}).catch((err:any) => { 
		  console.error('主题标签数据请求失败', err); 
		});
		//根据当前选择的主题标签请求频道数据
		getChannelByHashtag (hashtagCurrent.value).then((res:any)=>{
			channelList.value=res.data.channelList;
			/* console.log("channelList",channelList.value); */
			//排序频道
			channelList.value =sortChannelList(channelList.value,sortMethodCurrent.value);
		}).catch((err:any) => { 
		  console.error('频道据请求失败', err); 
		});
		
	});
	//监听主题标签选择器组件返回的数据(即当前选择的标签数据)
	const handleTabClick = (hashtag:any) => {
		hashtagCurrent.value=hashtag.key;
	};	
	//监听排序方式选择组件返回的数据(即当前选择的是第几个排序方式)
	const sortMethodClick = (sortMethod: number) => {
		sortMethodCurrent.value=sortMethod;
	}
	
	// 监听hashtagCurrent的变化
	watch(hashtagCurrent, (newValue, oldValue) => {
		//根据当前选择的主题标签请求频道数据
		getChannelByHashtag (hashtagCurrent.value).then((res:any)=>{
			channelList.value=res.data.channelList;
			//排序频道
			channelList.value =sortChannelList(channelList.value,sortMethodCurrent.value);
		}).catch((err:any) => { 
		  console.error('频道据请求失败', err); 
		});
	});
	
	// 监听sortMethodCurrent的变化
	watch(sortMethodCurrent, (newValue, oldValue) => {
		//排序频道
		channelList.value =sortChannelList(channelList.value,newValue);
	});
	
</script>

<style>
	.channel-classify-body {
		padding: 0 10px 30px 10px;
	}
	.channle-scroll {
		min-height: 100px;
		border: 3px dashed #9dcaf9;
		border-radius: 10px;
	}
</style>
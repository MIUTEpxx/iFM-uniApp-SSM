<template>
	<view class="search-result">
		<head-general titleContent="搜索" ></head-general>
		<uv-back-top :scroll-top="scrollTop"></uv-back-top>
		<uv-sticky bgColor="white" customStyle="top:0px;">
			<view class="search-result-head">
				<search-box :defaultKeyword="keyword"></search-box>
				<view class="sort-click">
					<uv-tabs
					@click="searchContentClick"
					:list="[{key:0,name:'节目'},{key:1,name:'频道'},{key:2,name:'帖子'},{key:3,name:'用户'}]"
					lineWidth="40" 
					lineColor="#63b1f9" 
					:activeStyle="{
						color: '#63b1f9',
						fontSize:'20px',
						transform: 'scale(1.05)',
					}"
					:inactiveStyle="{
						color: '#606266',
						fontSize:'20px',
						transform: 'scale(1)'
					}" 
					itemStyle="padding:5px 10px; height: 34px;"
					></uv-tabs>	
					<uv-icon name="paixu" custom-prefix="custom-icon" color="#5c7083" size="30px"></uv-icon>	
				</view>
			</view>
		</uv-sticky>
		
		<view class="search-result-body">
			<view v-if="searchContent==0" class="search-result-content">
				<broadcast-item  v-for="(item,i) in broadcastList" :key="i" v-bind="item"></broadcast-item>
			</view>
			<view v-if="searchContent==1" class="search-result-content">
				<channel-item v-for="(item,i) in channelList" :key="i" v-bind="item"></channel-item>	
			</view>
			<view v-if="searchContent==2" class="search-result-content">
				
			</view>
			<view v-if="searchContent==3" class="search-result-content">
				
			</view>
		</view>
	</view>
</template>

<script setup lang="ts">
import { onLoad, onPageScroll } from '@dcloudio/uni-app';
import {searchBroadcast,searchChannel} from '@/request/api'
import { ref } from 'vue';

let keyword=ref("")
let searchContent=ref(0)
let scrollTop=ref(0)
let broadcastList=ref([])
let channelList=ref([])

onPageScroll((e:any)=>{
	scrollTop.value=e.scrollTop
})
const searchContentClick =(index:any)=>{
	searchContent.value=index.key
	console.log("index",index);
}
onLoad((options:any) => {
	keyword.value = decodeURIComponent(options.keyword);
	getSearchResult()
	
	searchBroadcast(keyword.value).then((res:any)=>{	
		if(res.success==true){
			broadcastList.value=res.data.broadcastList
		}
	}).catch((err:any) => { 
	  console.error('节目搜索失败', err); 
	});
	
	searchChannel(keyword.value).then((res:any)=>{
		if(res.success==true){
			channelList.value=res.data.channelList
		}
	}).catch((err:any) => { 
	  console.error('频道搜索失败', err); 
	});
})
const getSearchResult =()=>{
	if(searchContent.value===0){
		//搜索节目
	}else if(searchContent.value===1){
		//搜索频道
	}else if(searchContent.value===1){
		//搜索帖子
	}else{
		//搜索用户
	}
}
</script>

<style>
	.search-result-head {
		padding-top: 10px;
	}
	.sort-click {
		display:flex;
	    align-items: center;
	    justify-content: space-between;
		margin: 10px 20px 10px 10px;
	}
	.search-result-body {
		height: 5000px;
		margin: 10px;
	}
</style>
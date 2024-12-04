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
					<uv-drop-down
						v-if="searchContent!=3"
						ref="dropDown"
						sign="dropDown_1" 
						:defaultValue="[0,'0','all']" 
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
							@change="sortMethodClick"></sort-method-box>
						</view>
					</uv-drop-down-popup>		
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
			<uv-load-more status="nomore" customStyle="padding:5px 0 10px 0;" />
		</view>
	</view>
</template>

<script setup lang="ts">
import { onLoad, onPageScroll } from '@dcloudio/uni-app';
import {searchBroadcast,searchChannel} from '@/request/api'
import { ref } from 'vue';
import { sortChannelList } from '@/utils/channelSort';
import { sortBroadcastList } from '@/utils/broadcastSort';

let keyword=ref("")
let searchContent=ref(0)
let scrollTop=ref(0)
let broadcastList=ref<any>([])
let channelList=ref<any>([])
//排序方法列表
let sortMethodList =ref([''])
let currentSortMethod=ref({key:0,name:'综合排序'})

onPageScroll((e:any)=>{
	scrollTop.value=e.scrollTop
})
const searchContentClick =(index:any)=>{
	searchContent.value=index.key
	changeMethodList()
}
onLoad((options:any) => {
	keyword.value = decodeURIComponent(options.keyword);
	changeMethodList()
	
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
//改变当前排序
//监听排序方式选择组件返回的数据(即当前选择的是第几个排序方式)
const sortMethodClick = (sortMethod: number) => {
	currentSortMethod.value.key=sortMethod;
	currentSortMethod.value.name=sortMethodList.value[currentSortMethod.value.key]
	sortSearchResult()
}

//改变排序方式列表
const changeMethodList =()=>{
	if(searchContent.value===0){
		//搜索节目
		sortMethodList.value=["综合排序","最新发布","最多播放","最多收藏"]
	}else if(searchContent.value===1){
		//搜索频道
		sortMethodList.value=["综合排序","最新发布","最近更新","最多订阅"]
	}else if(searchContent.value===2){
		//搜索帖子
		sortMethodList.value=["综合排序","最新发布","最多点赞","最多收藏"]
	}else{
		//搜索用户
	}
	currentSortMethod.value.name=sortMethodList.value[currentSortMethod.value.key]
	sortSearchResult()
}
//对搜索结果进行排序
const sortSearchResult =()=>{
	if(searchContent.value===0){
		broadcastList.value=sortBroadcastList(broadcastList.value,currentSortMethod.value.key)
	}else if(searchContent.value===1){
		channelList.value=sortChannelList(channelList.value,currentSortMethod.value.key)
	}else if(searchContent.value===2){
		
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
		
		margin: 10px;
	}
	.drop-content {
		width: 100%;
		height: 120rpx;
		background-color: #fff;
	}
	.drop-content *{
		font-size: 18px;
	}
</style>
<template>
	<view class="post-association-choose">
		<head-general :showHomeButton="false"  titleContent="关联选择"></head-general>
		<view class="association-choose-head">
			<uv-search
			@search="search"
			@custom="search"
			v-model="keyword"
			:showAction="true" 
			actionText="搜索" 
			:animation="true" 
			:clearabled="true"
			height=40px
			bgColor="#EDF2F5"
			searchIconSize=35
			actionStyle="background:#86c7f9;
			color:white;border:2px solid #86c7f9;
			border-radius: 5px;font-size:20px;margin:0 5px;"
			>
			</uv-search>
			<view class="sort-click">
				<uv-tabs
				@click="searchContentClick"
				:list="[{key:0,name:'频道'},{key:1,name:'节目'}]"
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
						@change="sortMethodClick"></sort-method-box>
					</view>
				</uv-drop-down-popup>		
			</view>
			<view class="search-result-body">
				<view v-if="searchContentType==0" class="search-result-content">
					<channel-item 
					:enableComponentClick="false" :showSubscribeButton="false" 
					v-for="(item,i) in channelList" :key="i" v-bind="item"
					@click="chooseAssociation(item,i)" 
					></channel-item>	
				</view>
				<view v-if="searchContentType==1" class="search-result-content">
					<broadcast-item 
					:enableComponentClick="false" :showPlayButton="false" 
					v-for="(item,i) in broadcastList" :key="i" v-bind="item"
					@click="chooseAssociation(item,i)" 
					></broadcast-item>
				</view>
				<uv-load-more status="nomore" customStyle="padding:5px 0 10px 0;" />
			</view>
		</view>
	</view>
</template>

<script setup lang="ts">
	import { onShow } from '@dcloudio/uni-app';
	import { ref } from 'vue';
	import {searchBroadcast,searchChannel,getPopularBroadcast,getPopularChannel} from '@/request/api'
	import { sortChannelList } from '@/utils/channelSort';
	import { sortBroadcastList } from '@/utils/broadcastSort';
	
	//关键词
	let keyword = ref("");
	//搜索内容类型 0:频道 1:节目
	let searchContentType = ref(0);
	//频道搜索结果
	let channelList=ref<any>([]);
	//节目搜索结果
	let broadcastList=ref<any>([]);
	//排序方法列表
	let sortMethodList =ref([''])
	//当前排序方法
	let currentSortMethod=ref({key:0,name:'综合排序'})
	
	//更改搜索内容类型
	const searchContentClick =(index:any)=>{
		searchContentType.value=index.key
		changeMethodList()
	}
	//搜索
	const search=()=>{
		if(keyword.value.trim().length <= 0){
			uni.showToast({
				title: "搜索框不能为空!",
				icon: 'error',
				duration: 2000
			}) 
			return
		}
		searchBroadcast(keyword.value).then((res:any)=>{
			if(res.success==true){
				broadcastList.value=res.data.broadcastList
			}
		}).catch((err:any) => { 
		  console.error('节目搜索请求失败', err); 
		});
		
		searchChannel(keyword.value).then((res:any)=>{
			if(res.success==true){
				channelList.value=res.data.channelList
			}
		}).catch((err:any) => { 
		  console.error('频道搜索请求失败', err); 
		});
	}
	//默认显示热门节目和频道
	onShow(()=>{
		changeMethodList()
		getPopularBroadcast ().then((res:any) => {
			broadcastList.value=res.data.broadcastList
		}).catch((err:any) => { 
		  console.error('热门频道请求失败', err); 
		});
		
		getPopularChannel ().then((res:any) => {
			channelList.value=res.data.channelList
			//console.log("channel",channelList.value)
		}).catch((err:any) => { 
		  console.error('热门频道请求失败', err); 
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
		if(searchContentType.value===0){
			//搜索节目
			sortMethodList.value=["综合排序","最新发布","最近更新","最多订阅"]
		}else if(searchContentType.value===1){
			//搜索频道
			sortMethodList.value=["综合排序","最新发布","最多播放","最多收藏"]
		}
		currentSortMethod.value.name=sortMethodList.value[currentSortMethod.value.key]
		sortSearchResult()
	}
	//对搜索结果进行排序
	const sortSearchResult =()=>{
		if(searchContentType.value===0){
			channelList.value=sortChannelList(channelList.value,currentSortMethod.value.key)
		}else if(searchContentType.value===1){
			broadcastList.value=sortBroadcastList(broadcastList.value,currentSortMethod.value.key)
		}
	}
	const chooseAssociation =(item:any,i:any)=>{
		 // 使用uni.$emit发送数据到上一页面
		 let id = 0;
		 if(searchContentType.value==0){
			id=item.channelId;
		 }else{
			id=item.broadcastId;
		 }
		 
		  uni.$emit('acceptDataFromOpenedPage', {
		    associationId: id,
		    associationType: searchContentType.value
		  });
		  
		  // 返回上一页面
		  uni.navigateBack({
		    delta: 1 // 返回上一页面
		  });
	}
</script>

<style scoped>
	.association-choose-head {
		margin: 15rpx;
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
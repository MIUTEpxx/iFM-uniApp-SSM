<template>
	<view class="search-box">
		<uv-search
		v-model="keyword"
		:showAction="false" 
		:clearabled="true"
		height=40px
		bgColor="#EDF2F5"
		searchIconSize=35
		@focus="openSearchPopup"
		>
		</uv-search>
		<uv-popup ref="popup" mode="right">
			<view class="search-page" >
				<head-general :showBackButton="false" :showHomeButton="false" titleContent="搜索"></head-general>
				<view class="head">
					<uv-icon name="arrow-left" @click="closeSearchPopup" size="50rpx"></uv-icon>
					<uv-search
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
					@search="search(null)"
					@custom="search(null)"
					></uv-search>
				</view>
				<view class="search-history">
					<view class="head">
						<uv-text text="搜索历史" color="#86c7f9" size="40rpx" bold="true" ></uv-text>
						<view class="delete-button" @click="deleteAllSearchHistory_()">
							<uv-icon name="close" size="28rpx"></uv-icon>
							<uv-text text="删除搜索记录" color="#667a95" size="28rpx" ></uv-text>
						</view>
					</view>
					<view class="search-history-item">
						<uv-tags 
						class="item" 
						v-for="(item , i) in searchHistoryList"
						:key="i"
						:text="item.keyword" 
						shape="circle"
						closable="true"
						plain size="medium  " 
						color="#667a95" 
						borderColor="#c4cfdf"
						@close="deleteSearchHistort_(item,i)"
						@click="search(item)"></uv-tags>
					</view>
				</view>
			</view>
		</uv-popup>
	</view>

</template>

<script setup lang="ts">
import { ref } from 'vue';
import {  deleteAllSearchHistory, deleteSearchHistory, getSearchHistory, saveSearchHistory } from '@/request/api';
import useUserStore from '@/stores/user';
	const userStore=useUserStore()
	const popup = ref<any>(null);
	//记录用户搜索记录
	let searchHistoryList = ref<any>([])
	
	const openSearchPopup = () =>{
		if(popup.value) {
			popup.value.open();
		}
		if(userStore.isLogin==false){
			return;
		}
		getSearchHistory(userStore.userId).then((res:any)=>{
			if(res.success==true){
				searchHistoryList.value=res.data.searchHistoryList
				//console.log(res)
			}else{
				uni.showToast({
					title: res.message+'\n'+res.data.error,
					icon: 'error',
					duration: 6000
				}) 
			}
		}).catch((err:any) => { 
			console.error('用户搜索记录获取请求失败', err); 
		});
	}
	const closeSearchPopup = () =>{
		if(popup.value) {
			popup.value.close();
		}
	}
	
	const props = defineProps({
		defaultKeyword: {
			type: String,
			default: ''
		}
	});
	
	let keyword = ref(props.defaultKeyword);
	//搜索并跳转到搜索结果页
	const search=(item:any)=>{
		if(item!=null) {
			keyword.value=item.keyword
		}
		if(keyword.value.trim().length <= 0){
			uni.showToast({
				title: "搜索框不能为空!",
				icon: 'error',
				duration: 2000
			}) 
			return
		}
		if(userStore.isLogin==true){
			//如果登录,则保存搜索记录
			saveSearchHistory(userStore.userId,keyword.value).then((res:any)=>{
				if(res.success==true){
					searchHistoryList.value=res.data.searchHistoryList
					//console.log(res)
				}else{
					uni.showToast({
						title: res.message+'\n'+res.data.error,
						icon: 'error',
						duration: 6000
					}) 
				}
			}).catch((err:any) => { 
				console.error('用户搜索记录保存请求失败', err); 
			});
		}
		
		uni.navigateTo({
		   url: `/pages/search-pages/search-result?keyword=${encodeURIComponent(keyword.value.toString())}`,
		});   
	}
	
	const deleteAllSearchHistory_ =()=>{
		if(!userStore.isLogin){
			uni.showToast({
				title: '请登录后操作',
				icon: 'error',
				duration: 1000
			}) 
			return;
		}
		deleteAllSearchHistory(userStore.userId).then((res:any)=>{
				if(res.success==true){
					// 删除searchHistoryList中对应index的元素
					searchHistoryList.value=[];
				}else{
					uni.showToast({
						title: res.message+'\n'+res.data.error,
						icon: 'error',
						duration: 6000
					}) 
				}
			}).catch((err:any) => { 
				console.error('用户搜索记录删除请求失败', err); 
			});
	}
	const deleteSearchHistort_ =(item:any,index:number)=>{
			if(!userStore.isLogin){
				uni.showToast({
					title: '请登录后操作',
					icon: 'error',
					duration: 1000
				}) 
				return;
			}
			deleteSearchHistory(userStore.userId,item.searchHistoryId).then((res:any)=>{
				if(res.success==true){
					// 删除searchHistoryList中对应index的元素
					searchHistoryList.value.splice(index, 1);
				}else{
					uni.showToast({
						title: res.message+'\n'+res.data.error,
						icon: 'error',
						duration: 6000
					}) 
				}
			}).catch((err:any) => { 
				console.error('用户搜索记录删除请求失败', err); 
			});
	}
</script>
	
<style>
	.search-box {
		margin: 0 10px 0 10px;
	}
	.search-page {
		width: 748rpx;

	}
	.search-page  .head {
		display: flex;
		align-items: center;
		
	}
	.search-page .search-history {
		margin: 25rpx;
		
	}
	.search-page .search-history .search-history-item {
		display: flex;
		flex-wrap: wrap;
	}
	.delete-button {
		display: flex;
		align-items: baseline;
		/* margin-top: 10rpx; */
	}
</style>
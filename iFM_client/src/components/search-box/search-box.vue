<template>
	<view class="search-box">
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
	</view>

</template>

<script setup lang="ts">
	import { ref } from 'vue';
	
	const props = defineProps({
		defaultKeyword: {
			type: String,
			default: ''
		}
	});
	
	let keyword = ref(props.defaultKeyword);
	//搜索并跳转到搜索结果页
	const search=()=>{
		if(keyword.value.trim().length <= 0){
			uni.showToast({
				title: "搜索框不能为空!",
				icon: 'error',
				duration: 2000
			}) 
			return
		}
		uni.navigateTo({
		   url: `/pages/search-pages/search-result?keyword=${encodeURIComponent(keyword.value.toString())}`,
		});   
	}
	
</script>
	
<style>
	.search-box {
		margin: 0 10px 0 10px;
	}
</style>
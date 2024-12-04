<template>
	<uv-drop-down
		ref="dropDown"
		sign="dropDown_1" 
		:defaultValue="[0,'0','all']" 
	>
		<uv-drop-down-item 
			name="order" 
			type="2" 
			label="综合排序" 
			value="all">
		</uv-drop-down-item>
	</uv-drop-down>
	<uv-drop-down-popup
		sign="dropDown_1"
		@popupChange="change"
	>
		<view class="drop-content">
		
		</view>
	</uv-drop-down-popup>		
</template>

<script setup lang="ts">
	const change =(e:any) =>{
		console.log('弹窗打开状态：',e);
	}
	const sortMethodClick =(index:any) =>{
		
	}
</script>

<style>
	.drop-content {
		width: 100%;
		height: 150rpx;
		background-color: #fff;
	}
</style>
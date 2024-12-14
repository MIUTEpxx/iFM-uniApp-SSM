<template>
	<view class="creation-channel">
		<head-general titleContent="频道创建"></head-general>
		<view class="creat-channel-body">
			<uv-upload
				:fileList="fileList" 
				name="6" 
				multiple 
				:maxCount="1" 
				width="400" 
				height="400"
				:previewFullImage="true"
				:deletable="true"
				custom-style="
				display: flex;
				justify-content: center;
				align-items: center;
				">
				<view class="upload-channel-image">
					<span>+</span>
				</view>
			</uv-upload>
		</view>
			
		
	</view>
</template>

<script setup lang="ts">
	import { ref, watch } from 'vue';

	let fileList = ref([]);
	
	watch(fileList, (newValue, oldValue) => {
		console.log(fileList.value)
	});
	
</script>

<style scoped>
	.creat-channel-body {
		display: flex;
		
	}
	.upload-channel-image {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 400rpx;
		height: 400rpx;
		border-radius: 15rpx;
		background: #c2d4e5;
	}
	.upload-channel-image >span {
		margin-bottom: 25rpx;
		color: white;
		font-size: 150rpx;
		font-weight: 900;
	}
</style>
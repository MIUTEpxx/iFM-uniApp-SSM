<template>
	<view class="post-interaction">
		<uv-line color="#456982"></uv-line>
		<view class="post-button">
			<view class="button">
				<uv-icon name="thumb-up" color="#676c7a" :size="props.iconSize+'rpx'"></uv-icon>
				<uv-text class="data" text="114" color="#676c7a"  :size="props.textSize+'rpx'" align="left"></uv-text>
			</view>
			<view class="button">
				<uv-icon name="chat" color="#858b9d" :size="props.iconSize+'rpx'"></uv-icon>
				<uv-text class="data" text="514" color="#676c7a"  :size="props.textSize+'rpx'" align="left"></uv-text>
			</view>
			<view class="button">
				<uv-icon name="star" color="#858b9d" :size="props.iconSize+'rpx'"></uv-icon>
				<uv-text class="data" text="666" color="#676c7a"  :size="props.textSize+'rpx'" align="left"></uv-text>
			</view>
		</view>
	</view>
</template>

<script setup lang="ts">
	
	const props = defineProps({
		iconSize: {
			type: Number,
			default: 65
		},
		textSize:{
			type: Number,
			default: 30
		},
		broadcastId:{
			type: Number,
			default: -1
		}
	});
	
	
</script>

<style>
	.post-interaction {
		margin-top: 25rpx;
	}
	.post-interaction .post-button {
		display: flex;
	    justify-content: space-between;
	    align-items: center;	
		margin-top: 10rpx;
	}
	.post-interaction .post-button>*{
		flex: 1;
	}
	.post-button .button {
		display: flex;
	    align-items: center;
	    justify-content: center;	
	}
	.post-button .button>* {
		flex:1;
		display: flex;
		justify-content: flex-end;
	}
</style>
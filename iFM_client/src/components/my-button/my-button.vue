<template>
	<view class="my-button">
		<view class="icon-content">
			<uv-icon :name="props.iconContent" custom-prefix="custom-icon" color="#86c7f9" :size="props.iconSize"></uv-icon>
			<p>{{props.textContent}}</p>	
		</view>
		<p>></p>	
	</view>      
</template>

<script setup lang="ts">
	import { ref } from 'vue';
	
	//该组件获取数据的接口
	interface IMyButton{      
		textContent:string;
		iconContent:string;
		iconSize:number;
	}
	//设置默认值
	const props = withDefaults(defineProps<IMyButton>(), {
		textContent:'默认按钮',
		iconContent:'info-circle',
		iconSize:55,
	});
	
</script>

<style>
	.my-button {
			display: flex;
			justify-content: space-between;
			align-items: center;
			margin: 15px 0;
		}
		.icon-content {
			display: flex;
			align-items: center;
			justify-content: flex-start;
			width: 100%; /* Ensure the icon-content takes full width */
		}
		.icon-content > * {
			flex: 1; /* Initially give each child equal flex-grow */
		}
		.icon-content > :first-child {
			flex: 1; /* Icon takes 1 part */
		}
		.icon-content > :last-child {
			flex: 4; /* Text takes 4 parts */
			font-size: 28px;
			font-weight: 500;
			color: #697481;
		}
		
		p {
			margin: 0; /* Remove default margin */
			color: #aec0d4;
			font-size: 45px;
		}
</style>
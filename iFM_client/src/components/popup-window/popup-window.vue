<template>
	<!-- 弹窗 -->
	<uv-popup
	ref="popup" mode="center" class="popup-window" 
	custom-style="width: 520rpx; padding:15rpx; border-radius: 10rpx;">
		<view class="popup-window-content">
			<uv-text class="popup-titel" :text="props.popupTitleText" color="#49a5eb"  size="55rpx" bold="true" align="center"></uv-text>
			<uv-text class="popup-txt" lines="3" :text="props.popupWindowText" color="#576674"  size="38rpx"  align="center"></uv-text>
			<uv-line color="#959faabc"></uv-line>
			<view class="popup-window-button">
				<uv-text :text="props.leftButtonText" @click="handleLeftButtonClick" color="#49a5eb"  size="35rpx" bold="true" align="left"></uv-text>
				<uv-text :text="props.rightButtonText"  @click="handleRightButtonClick" color="#49a5eb"  size="35rpx" bold="true" align="right"></uv-text>
			</view>
		</view>
	</uv-popup>
</template>

<script setup lang="ts">
	
	import { ref } from 'vue';
	const popup = ref<any>(null);
	//打开弹窗
	const open = () => {
	  if (popup.value) {
	    popup.value.open();
	  }
	};
	//关闭弹窗
	const close = () => {
		if (popup.value) {
		  popup.value.close();
		}
	}
	//父页面传递参数
	const props = defineProps({
		popupTitleText: {
			type: String,
			default: "弹窗标题内容"
		},
		popupWindowText: {
			type: String,
			default: "弹窗文本内容"
		},
		leftButtonText:{
			type: String,
			default: "左按键文本内容"
		},
		rightButtonText:{
			type: String,
			default: "右按键文本内容"
		}
	});
	//父页面传递方法
	const emit = defineEmits(['leftButtonClick', 'rightButtonClick']);
	const handleLeftButtonClick = () => {
		emit('leftButtonClick');
	};
	const handleRightButtonClick = () => {
		emit('rightButtonClick');
	};
	
	// 将 open 方法暴露给父组件
	defineExpose({
	  open,
	  close
	});
</script>

<style scoped>
	.popup-titel {
		padding-bottom: 15rpx;
	}
	.popup-txt {
		padding-bottom: 80rpx;
	}
	.popup-window-button {
		display: flex;
		justify-content: space-between;
		margin: 15rpx 30rpx 0 30rpx;
	}
</style>
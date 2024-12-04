<template>
	<uv-subsection 
	:list="props.methodList" 
	:current="current"
	custom-style="height: 60rpx;border-radius: 15rpx;" 
	custom-item-style="border-radius: 15rpx;" 
	@change="change"></uv-subsection>
</template>

<script  setup lang="ts">
	import { defineProps, withDefaults,ref } from 'vue';
	
	//该组件获取数据的接口
	interface ISortMethod{
		methodList:Array<string>;
		current:number;
	}
	//设置默认值
	const props = withDefaults(defineProps<ISortMethod>(), {
		methodList: () => ["综合排序","最新发布","最近更新","最多订阅"],
		current:0,
	});
	let current=ref(props.current);
	
	const change = (index:number) =>{
		current.value=index;
		// 触发自定义事件，并传递当前选中的排序方式
		emit('sort-change', index);
	}
	// 定义事件发射器
	const emit = defineEmits<{
		(e: 'sort-change', sortMethod: number): void;
	}>();	
</script>
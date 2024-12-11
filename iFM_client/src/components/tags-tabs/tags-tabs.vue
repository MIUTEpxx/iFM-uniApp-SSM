<template>
	<uv-tabs 
	@click="click"
	:list="hashtagList"
	lineWidth="60" 
	lineColor="#63b1f9" 
	:activeStyle="{
		color: '#63b1f9',
		fontSize:'24px',
		fontWeight: 'bold',
		transform: 'scale(1.05)',
	}"
	:inactiveStyle="{
		color: '#606266',
		fontSize:'20px',
		transform: 'scale(1)'
	}" 
	itemStyle="padding-bottom: 10px;padding-left: 10px; padding-right: 10px; height: 34px;"
	></uv-tabs>		
</template>

<script setup lang="ts">
	import { defineProps, withDefaults, computed } from 'vue';
	//该组件获取数据的接口
	interface IHashtag{
		hashtag:Array<{ hashtagId: number; hashtagName: string }>;
	}
	//设置默认值
	const props = withDefaults(defineProps<IHashtag>(), {
		hashtag: () => [{ hashtagId: 1, hashtagName: '标签1' },{ hashtagId: 2, hashtagName: '标签2' }],
	});
	// 使用计算属性来映射 hashtag 到一个包含 key 和 name 的对象数组
	const hashtagList = computed(() => props.hashtag.map(item => ({
		key: item.hashtagId.toString(), // 使用 hashtagId 作为 key
		name: item.hashtagName, // 使用 hashtagName 作为标签名称
	})));
	const click = (hashtag:any) =>{
		// 触发自定义事件，并传递当前点击的标签数据
		emit('tab-click', hashtag);
	}
	// 定义事件发射器,将选中的标签数据传递给调用该组件的页面
	const emit = defineEmits<{
		(e: 'tab-click', hashtag: any): void;
	}>();	
</script>

<style>
</style>
<template>
	<view class="edit-user-password">
		<view class="edit-title">
			<uv-text :lines="2" text="修改密码" color="#86c7f9" size="26px" bold="true"></uv-text>	
			<uv-text :text="'当前邮箱: '+userStore.userEmail" color="#94a2b3" size="16px"></uv-text>
		</view>
		<view class="input">
			<uv-input v-model="vCode" placeholder="输入邮箱验证码" fontSize="20px">
				<template v-slot:suffix>
					<uv-button
						@click="getVCodeClick"
						:text="tips"
						type="success"
						size="normal"
						color="#63b1f9"
					></uv-button>
				</template>
			</uv-input>			
		</view>
		<view class="input">
			<p>新的密码:</p>
			<uv-input v-model="newUserPassword1" placeholder="请输入新密码" border="bottom" fontSize="20px" password='true' clearable='true'></uv-input>
		</view>
		<view class="input">
			<p>确认密码:</p>
			<uv-input v-model="newUserPassword2" placeholder="再次输入新密码" border="bottom" fontSize="20px" password='true' clearable='true'></uv-input>
		</view>
		<view class="edit-button">
			<uv-text @click="cancelClick" align="left" text="取消修改" color="#76c7f9" size="20px"></uv-text>
			<uv-text @click="confirmClick" align="right" text="确定修改" color="#76c7f9" size="20px"></uv-text>
		</view>
	</view>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import useUserStore from '../../stores/user';
import { changeUserPassword, getVCode } from '../../request/api';
import { checkPassword } from '@/utils/checkPassword';
import { logOut } from '@/utils/logOut';

const userStroe =useUserStore()
const tips = ref("获取验证码");
const seconds = ref(60);
const canSendVCode = ref(true)
//输入框输入的昵称
let newUserName =ref('')
let newUserPassword1 =ref('')
let newUserPassword2 =ref('')
let vCode =ref('')

const userStore= useUserStore()
//父页面传入取消按钮对应的退出编辑方法
const props = defineProps({
  cancelClick: {
    type: Function,
    required: true
  },
});

// 获取验证码按钮点击事件
	const getVCodeClick =() =>{
		 if (!canSendVCode.value) {
		    // 如果不能发送验证码，直接返回
		    return;
		  }
		  // 开始倒计时
		  startCountdown();
		getVCode (userStore.userEmail).then((res:any) => {
			if(res.code===20000){
				//验证码发送成功
				uni.showToast({
					title: "验证码发送成功!",
					icon: 'success',
					duration: 2000
				}) 
			}else{
				uni.showToast({
					title: res.message+'\n'+res.data.error,
					icon: 'error',
					duration: 3000
				}) 
			}
		}).catch((err:any) => { 
		  console.error('验证码发送请求失败', err); 
		});
	}
	
	// 开始倒计时
	const startCountdown = () => {
	  canSendVCode.value = false; // 设置为不能发送验证码
	  tips.value = `${seconds.value}s后再获取`; // 更新按钮文本
	
	  const timer = setInterval(() => {
	    seconds.value -= 1; // 秒数减一
	    tips.value = `${seconds.value}s后再获取`; // 更新按钮文本
	
	    if (seconds.value <= 0) {
	      clearInterval(timer); // 清除定时器
	      seconds.value = 60; // 重置秒数
	      tips.value = "获取验证码"; // 恢复按钮文本
	      canSendVCode.value = true; // 允许再次发送验证码
	    }
	  }, 1000);
	};
const confirmClick =()=>{
	if(!userStore.isLogin){
		uni.showToast({
			title: '请登录后操作',
			icon: 'error',
			duration: 1000
		}) 
		return;
	}
	if(newUserPassword1.value.trim().length <= 0||
	newUserPassword2.value.trim().length <= 0||
	vCode.value.trim().length <= 0){
		uni.showToast({
			title: '信息不能为空',
			icon: 'error',
			duration: 3000
		}) 
		return;
	}
	//检查密码格式是否正确
	if(!checkPassword(newUserPassword1.value,newUserPassword2.value)){
		uni.showToast({
			title: '密码格式有误',
			icon: 'error',
			duration: 2000
		}) 
		return;
	}

	changeUserPassword(userStore.userId,newUserPassword1.value,userStore.userEmail,vCode.value).then((res:any)=>{
		//清空验证码
		vCode.value=""
		if(res.success==true){
			//修改成功
			uni.showToast({
				title: "密码修改成功!",
				icon: 'success',
				duration: 2000
			}) 
			userStore.userName=newUserName.value
			props.cancelClick()
		}else{
			uni.showToast({
				title: res.message+'\n'+res.data.error,
				icon: 'error',
				duration: 3000
			}) 
		}
	})

}
</script>

<style>
	/* 将toast的z-index设置为最高 */
	uni-toast {
	    z-index: 9999 !important; /* 根据实际情况进行调整 */
	}
	.edit-user-password {
		padding: 2% 5%;
		background: white;
		border-radius:10px;
	}
	.edit-title {
		margin-bottom: 15px;
	}
	.edit-button {
		display: flex;
		margin-top: 15px;
	}

</style>
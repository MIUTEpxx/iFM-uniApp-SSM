<template>
	<view class="register">
		<head-general  titleContent="注册"></head-general>
		<view class="title"	>
			<p>欢迎注册</p><h2>iFM</h2><p>用户 !!!</p>
		</view>
		<view class="register-body">
			<span>请填写用户信息</span>
			<view class="input">
				<p>昵称:</p>
				<uv-input v-model="userName" placeholder="请输入用户昵称" border="bottom" fontSize="20px" clearable='true'></uv-input>
			</view>
			<view class="input">
				<p>邮箱:</p>
				<uv-input v-model="userEmail" placeholder="请输入邮箱" border="bottom" fontSize="20px" clearable='true'></uv-input>
			</view>
			<view class="input">
				<p>验证码:</p>
				<uv-input v-model="vCode" placeholder="输入验证码" fontSize="20px">
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
			<span>注:密码6位以上 30位以下 至少包含两种字符</span>
			<view class="input">
				<p>用户密码:</p>
				<uv-input v-model="userPassword" placeholder="请输入密码" border="bottom" fontSize="20px" password='true' clearable='true'></uv-input>
			</view>
			<view class="input">
				<p>确认密码:</p>
				<uv-input v-model="confirmPassword" placeholder="请再次输入密码" border="bottom" fontSize="20px" password='true' clearable='true'></uv-input>
			</view>
			
			<view class="register-agreement">
				<checkbox @click="agreementChange"></checkbox>
				<p>同意 </p>
				<span>《用户协议》</span>
				<p>和</p>
				<span>《隐私协议书》</span>
			</view>
			<view class="register-button">
				<uv-button
				@click="registerButtonClick"
				customStyle="width:300px;padding:25px;fontSize:28px;" 
				class="btn" 
				color="#86c7f9" 
				text="">
				注册</uv-button>
			</view>
		</view>
	</view>
</template>

<script setup lang="ts">
	import { ref } from 'vue';
	import { createUser, getVCode } from "@/request/api";
	import useUserStore from '@/stores/user';
	import {checkPassword} from '@/utils/checkPassword';
	
	const userStroe =useUserStore()
	const tips = ref("获取验证码");
	const seconds = ref(60);
	const canSendVCode = ref(true)
	let userPassword=''
	let confirmPassword=''
	let userEmail=''
	let userName=''
	let vCode=''
	let agreement = false
	let userInfo =ref<any>([])
	
	//勾选协议
	const agreementChange = (index:any) =>{
		agreement=!agreement
	}
	// 获取验证码按钮点击事件
	const getVCodeClick =() =>{
		 if (!canSendVCode.value) {
		    // 如果不能发送验证码，直接返回
		    return;
		  }
		if(userEmail.trim().length <= 0){
			uni.showToast({
				title: '邮箱不能为空',
				icon: 'error',
				duration: 2000
			}) 
			return;
		}
		// 开始倒计时
		startCountdown();
		getVCode (userEmail).then((res:any) => {
			if(res.code==true){
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
	
	const registerButtonClick = () =>{
		//检查是否勾选协议
		if(!agreement){
			uni.showToast({
				title: '请勾选同意《用户协议》和《隐私协议书》',
				icon: 'error',
				duration: 2000
			}) 
			return;
		}
		
		if(userName.trim().length <= 0||userPassword.trim().length <= 0
		||userEmail.trim().length <= 0||vCode.trim().length <= 0){
			uni.showToast({
				title: '用户信息不能有空',
				icon: 'error',
				duration: 2000
			}) 
			return;
		}
		//检查密码格式是否正确
		if(!checkPassword(userPassword,confirmPassword)){
			uni.showToast({
				title: '密码格式有误',
				icon: 'error',
				duration: 2000
			}) 
			return;
		}
		
		
		createUser (userName,userPassword,userEmail,vCode).then((res:any) => {
			//userInfo.value=res.data.user
			//console.log("userInfo",userInfo.value)
			console.log("res",res)
			if(res.success!=false){
				//注册成功
				userStroe.setUserInfo(res.data.user);
				uni.showToast({
					title: "注册成功!",
					icon: 'success',
					duration: 2000
				}) 
				uni.navigateBack({
					delta: 1 // 默认返回上一级页面
				});
			}else{
				uni.showToast({
					title: res.message+'\n'+res.data.error,
					icon: 'error',
					duration: 6000
				}) 
			}
		}).catch((err:any) => { 
		  console.error('注册失败', err); 
		});
	}
	

	
</script>

<style>
	.register>.title {
		display: flex;
		align-items: center;
		justify-content: flex-start;
		margin: 30px;
	}
	.title p{
		font-size: 30px;
		color: #3e4a5e;
	}
	.title h2{
		margin: 0 10px 5px 10px;
		font-size: 45px;
		color: #66a9ec;
	}
	.input {
		display: flex;
		align-items: center;
		justify-content: flex-start;
		margin-bottom: 40px;
	}
	.register-body {
		margin: 20px 30px 20px 20px;
	}
	.register-body p{
		font-size: 26px;
		margin-right: 10px;
		color: #48566d;
	}
	.register-body>span{
		color: #92a1b3;
	}
	.register-agreement {
		display: flex;
	}
	.register-agreement p{
		font-size: 16px;
	}
	.register-agreement span{
		font-size: 16px;
		color: #00aaff;
	}
	.register-button {
		display: flex;
	    flex-direction: column;
	    align-items: center;	
		margin-top: 50px;
	}
</style>
<template>
	<view class="login">
		<head-general  titleContent="登录"></head-general>
		<view class="title"	>
			<p>欢迎使用</p><h2>iFM</h2><p>!!!</p>
		</view>
		<view class="login-body">
			<uv-tabs
			@click="loginMethodClick"
			:list="[{key:0,name:'账号密码'},{key:1,name:'邮箱验证'}]"
			lineWidth="60" 
			lineColor="#63b1f9" 
			:activeStyle="{
				color: '#63b1f9',
				fontSize:'20px',
				transform: 'scale(1.05)',
			}"
			:inactiveStyle="{
				color: '#606266',
				fontSize:'20px',
				transform: 'scale(1)'
			}" 
			itemStyle="padding:5px 10px; height: 34px;"
			></uv-tabs>		
			
			<view class="input-password" v-if="loginMethod==0">
				<view class="input">
					<p>账号:</p>
					<uv-input v-model="userId" placeholder="请输入账号" border="bottom" fontSize="20px" clearable='true'></uv-input>
				</view>
				 <view class="input">
				 	<p>密码:</p>
				 	<uv-input v-model="userPassword" placeholder="请输入账号" border="bottom" fontSize="20px" password='true' clearable='true'></uv-input>
				 </view>
			</view>
			<view class="input-email" v-else>
				 <view class="input">
				 	<p>邮箱:</p>
				 	<uv-input v-model="userEmail" placeholder="请输入内容邮箱" border="bottom" fontSize="20px" clearable='true'></uv-input>
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
			</view>
			<view class="login-agreement">
				<checkbox @click="agreementChange"></checkbox>			
				<p>同意 </p>
				<span>《用户协议》</span>
				<p>和</p>
				<span>《隐私协议书》</span>
			</view>
			<view class="login-button">
				<uv-button
				@click="loginButtonClick"
				customStyle="width:300px;padding:25px;fontSize:28px;" 
				class="btn" 
				color="#86c7f9" 
				text="">
				登录</uv-button>
				<view class="register" @click="goRegister">
					<uv-text text="没有账号? 点击这里注册!" color="#5c7187" size="20px" bold="true" ></uv-text>
				</view>
			</view>
			
			
		</view>
	</view>
</template>

<script setup lang="ts" >
	import { ref } from 'vue';
	import { loginEmail,loginPassword,getVCode, checkEmail } from "@/request/api";
	import useUserStore from '@/stores/user';
	
	const userStroe =useUserStore()
	const tips = ref("获取验证码");
	const seconds = ref(60);
	const canSendVCode = ref(true)
	
	let loginMethod = ref(0)
	let userId=''
	let userPassword=''
	let userEmail=''
	let vCode=''
	let agreement = false
	let userInfo =ref<any>([])
	
	
	//切换登录方式
	const loginMethodClick = (index:any) =>{
		loginMethod.value=index.key
	}
	//跳转注册页
	const goRegister =()=>{
		uni.navigateTo({
			url: "/pages/login-register/register"
		});   
	}
	//勾选协议
	const agreementChange = () =>{
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
		//检查邮箱是否已被注册,再决定是否要发送验证码
		checkEmail (userEmail).then((res:any) => {
			console.log(res);
			if(res.success == true){
				if(res.data.hasRegistered == false) {
					//邮箱未经注册, 不发送验证码
					uni.showToast({
						title: '该邮箱未注册',
						icon: 'error',
						duration: 2000
					}) 
					return;
				}
			}else{
				uni.showToast({
					title: res.message+'\n'+res.data.error,
					icon: 'error',
					duration: 3000
				}) 
				return;
			}
		}).catch((err:any) => { 
		  console.error('邮箱检查请求失败', err); 
		});
		
		getVCode (userEmail).then((res:any) => {
			if(res.code===20000){
				//验证码发送成功
				uni.showToast({
					title: "验证码发送成功!",
					icon: 'success',
					duration: 2000
				}) 
				// 开始倒计时
				startCountdown();
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
	
	//登录!
	const loginButtonClick = () =>{
		//检查是否勾选协议
		if(!agreement){
			uni.showToast({
				title: '请勾选同意《用户协议》和《隐私协议书》',
				icon: 'error',
				duration: 2000
			}) 
			return;
		}
		
		if(loginMethod.value===0){
			//账号密码登录
			if(userId.trim().length <= 0||userPassword.trim().length <= 0){
				uni.showToast({
					title: '账号密码不能为空',
					icon: 'error',
					duration: 2000
				}) 
				return;
			}
			//账号不能包含数字以外的字符
			console.log(isUserIdNumeric(userId));
			if(isUserIdNumeric(userId)==false){
				uni.showToast({
					title: '账号错误',
					icon: 'error',
					duration: 2000
				}) 
				return;
			}
			loginPassword (userId,userPassword).then((res:any) => {
				userInfo.value=res.data.user
				//console.log("userInfo",userInfo.value)
				if(res.success==true){
					//登录成功
					userStroe.setUserInfo(res.data.user);
					uni.showToast({
						title: "登录成功!",
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
						duration: 3000
					}) 
				}
			}).catch((err:any) => { 
			  console.error('登录失败', err); 
			});
			
		}else{
			//邮箱验证登录
			if(userEmail.trim().length <= 0||vCode.trim().length <= 0){
				uni.showToast({
					title: '邮箱和验证码不能为空',
					icon: 'error',
					duration: 2000
				}) 
				return;
			}
			loginEmail (userEmail,vCode).then((res:any) => {
				//userInfo.value=res.data.user
				/* console.log("userInfo",userInfo.value) */
				if(res.code===20000){
					//登录成功
					userStroe.setUserInfo(res.data.user);
					userStroe.setToken(res.data.token)
					uni.showToast({
						title: "登录成功!",
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
						duration: 3000
					}) 
				}
			}).catch((err:any) => { 
			  console.error('登录失败', err); 
			});
		}
	}
	
	// 检测userId是否只包含数字
	function isUserIdNumeric(id: string): boolean {
	  return /^\d+$/.test(id);
	}
	
	
	
</script>

<style>
	.login>.title {
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
	.login-body {
		margin: 10px 20px;
	}
	.input-password,
	.input-email {
		margin: 30px 20px 0 10px;
	}
	
	.input-password p,
	.input-email p{
		font-size: 26px;
		margin-right: 10px;
		color: #48566d;
	}
	.input {
		display: flex;
		align-items: center;
		justify-content: flex-start;
		margin-bottom: 40px;
	}
	.login-agreement {
		display: flex;
		align-items: center;
		justify-content: flex-start;
	}
	.login-agreement p{
		font-size: 16px;
	}
	.login-agreement span {
		font-size: 16px;
		color: #00aaff;
	}
	.login-agreement input {
		margin-right:15px ;
	}
	.login-button {
		display: flex;
	    flex-direction: column;
	    align-items: center;	
		margin-top: 50px;
	}
	.login-button .register {
		margin-top: 20px;
	}
</style>
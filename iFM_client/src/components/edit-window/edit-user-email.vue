<template>
	<view class="edit-user-email">
		<view class="edit-title">
			<uv-text text="修改邮箱" color="#86c7f9" size="26px" bold="true"></uv-text>	
			<uv-text :text="'当前邮箱: '+userStore.userEmail" color="#94a2b3" size="16px"></uv-text>
		</view>
		<view class="edit-user-email-body">
			<view class="input">
				<uv-input v-model="vCode1" placeholder="输入旧邮箱验证码" fontSize="20px">
					<template v-slot:suffix>
						<uv-button
							@click="getVCodeClick1"
							:text="tips1"
							type="success"
							size="normal"
							color="#63b1f9"
						></uv-button>
					</template>
				</uv-input>			
			</view>
			<view class="input">
				<p>新邮箱:</p>
				<uv-input v-model="newEmail" placeholder="请输入新邮箱" border="bottom" fontSize="20px" clearable='true'></uv-input>
			</view>
			<view class="input">
				<uv-input v-model="vCode2" placeholder="输入新邮箱验证码" fontSize="20px">
					<template v-slot:suffix>
						<uv-button
							@click="getVCodeClick2"
							:text="tips2"
							type="success"
							size="normal"
							color="#63b1f9"
						></uv-button>
					</template>
				</uv-input>			
			</view>
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
import { changeUserEmail, getVCode } from '../../request/api';
import { logOut } from '@/utils/logOut';
//输入框输入的昵称
let newUserName =ref('')

const userStore= useUserStore()
//父页面传入取消按钮对应的退出编辑方法
const props = defineProps({
  cancelClick: {
    type: Function,
    required: true
  },
});

const tips1 = ref("获取验证码");
const seconds1 = ref(60);
const canSendVCode1 = ref(true)

const tips2 = ref("获取验证码");
const seconds2 = ref(60);
const canSendVCode2 = ref(true)


let newEmail=''
let vCode1=''
let vCode2=''

const getVCodeClick1 =()=>{
	if (!canSendVCode1.value) {
	   // 如果不能发送验证码，直接返回
	   return;
	 }
	getVCodeFunc(userStore.userEmail,1);
}
const getVCodeClick2 =()=>{
	if (!canSendVCode2.value) {
	   // 如果不能发送验证码，直接返回
	   return;
	 }
	getVCodeFunc(newEmail,2);
}
// 获取验证码
const getVCodeFunc =(userEmail:string,index:number) =>{

	if(userEmail.trim().length <= 0){
		uni.showToast({
			title: '邮箱不能为空',
			icon: 'error',
			duration: 2000,
		}) 
		return;
	}
	// 开始倒计时
	if(index===1){startCountdown1();}
	else {startCountdown2();}
	getVCode (userEmail).then((res:any) => {
		if(res.success==true){
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

// 开始倒计时1
const startCountdown1 = () => {
  canSendVCode1.value = false; // 设置为不能发送验证码
  tips1.value = `${seconds1.value}s后再获取`; // 更新按钮文本

  const timer = setInterval(() => {
    seconds1.value -= 1; // 秒数减一
    tips1.value = `${seconds1.value}s后再获取`; // 更新按钮文本

    if (seconds1.value <= 0) {
      clearInterval(timer); // 清除定时器
      seconds1.value = 60; // 重置秒数
      tips1.value = "获取验证码"; // 恢复按钮文本
      canSendVCode1.value = true; // 允许再次发送验证码
    }
  }, 1000);
};
// 开始倒计时2
const startCountdown2 = () => {
  canSendVCode2.value = false; // 设置为不能发送验证码
  tips2.value = `${seconds2.value}s后再获取`; // 更新按钮文本

  const timer = setInterval(() => {
    seconds2.value -= 1; // 秒数减一
    tips2.value = `${seconds2.value}s后再获取`; // 更新按钮文本

    if (seconds2.value <= 0) {
      clearInterval(timer); // 清除定时器
      seconds2.value = 60; // 重置秒数
      tips2.value = "获取验证码"; // 恢复按钮文本
      canSendVCode2.value = true; // 允许再次发送验证码
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
	if(vCode1.trim().length <= 0||
	vCode2.trim().length <= 0||
	newEmail.trim().length <= 0){
		uni.showToast({
			title: '信息不能为空',
			icon: 'error',
			duration: 3000
		}) 
		return;
	}
	changeUserEmail(userStore.userId,userStore.userEmail,newEmail,vCode1,vCode2).then((res:any)=>{
		//清空验证码
		vCode1=''
		vCode2=''
		if(res.success==true){
			userStore.userEmail=newEmail
			//修改成功
			uni.showToast({
				title: "邮箱修改成功!",
				icon: 'success',
				duration: 2000
			}) 
			//关闭弹窗
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
	    z-index: 1999999 !important; /* 根据实际情况进行调整 */
	}
	.edit-user-email {
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
	.input {
		display: flex;
		align-items: center;
		justify-content: flex-start;
		margin-bottom: 20px;
	}
	.input>p{
		font-size: 20px;
		color:#515962;
		margin-right: 10px;
	}
</style>
import useUserStore from '@/stores/user';
export function logOut(){
	uni.showToast({
		title: '登录过期!请重新登录!',
		icon: 'error',
		duration: 2000
	})
	const userStore=useUserStore()
	//登出账号 清空用户信息
	userStore.clearUserInfo();
	//关闭所有页面 返回首页
	uni.reLaunch({
	    url: '/pages/index/index'
	})
}
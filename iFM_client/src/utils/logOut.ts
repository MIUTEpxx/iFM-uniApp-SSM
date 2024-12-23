import useUserStore from '@/stores/user';
export function logOut(){
    // 提醒用户登录过期
    uni.showToast({
        title: '登录过期\n请重新登录',
        icon: 'error',
        duration: 2000
    });

    const userStore = useUserStore();
    // 登出账号 清空用户信息
    userStore.clearUserInfo();

    // 设置一个延时，等待toast提示结束
    setTimeout(() => {
        // 关闭所有页面 返回首页
        uni.reLaunch({
            url: '/pages/index/index'
        });
    }, 2000); // 延时时间与toast的duration属性相同

}
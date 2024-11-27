import { defineStore } from 'pinia'

//创建仓库
const useUserStore = defineStore('User', {
  state: () => {
    return {
     isLogin:false,//登录状态
	 userId:0,//用户账号
	 userName:'游客',//用户昵称
	 userEmail:'',//用户邮箱
	 userPicture:'/static/logo.png',//用户头像
	 userProfile:'',//用户个性签名
	 Token:'',//Token安全令牌
    }
  },
  actions: {
	  //设置登录状态
	setLogin(isLogin:boolean){
		this.isLogin=isLogin
	},
    //设置User的各个数据
    setUser(
	userId:number,userName:string,userEmail:string,
	userPicture:string,userProfile:string) {
      this.userId=userId
	  this.userName=userName
	  this.userPicture=userPicture
	  this.userProfile=userProfile
    },
	//设置Token值
	setToken(newToken:string){
		this.Token=newToken
	}
  }
})
 
//暴露仓库
export default useUserStore
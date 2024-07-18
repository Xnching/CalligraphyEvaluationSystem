<template>
    <div class="login_container">
       <div class="login_box">
         <div style="margin:20px 0; text-align:center; font-size:24px"><b>登录</b></div>
         <!-- 用户名-->
         <el-form ref="LoginFormRef" :model="loginForm" :rules="LoginFormRules" >
           <el-form-item prop="loginId">
             <el-input size="medium" style="margin:10px 0px;width: 300px;margin-left:25px" v-model="loginForm.loginId" prefix-icon="el-icon-user"></el-input>
           </el-form-item>
         <!-- 密码-->
            <el-form-item prop="password">
              <el-input size="medium" style="margin:10px 0px;width: 300px;margin-left:25px" show-password v-model="loginForm.password" prefix-icon="el-icon-lock" type="password"></el-input>
           </el-form-item>
           <div style="margin:10px 0; text-align:center">
             <el-button type="primary" size="small" @click="login" >登录</el-button>
             <el-button type="warning" size="small" @click="resetLoginForm">重置</el-button>
           </div> 
         </el-form> 
       </div>
    </div>
</template>
   
<script>
import {setRoutes} from "@/router";
import {resetRouter} from "@/router";
export default {
    name: "Login",
    data() {
      return {
        loginForm: {
            loginId:'',
            password:''
        },
        LoginFormRules:{
            loginId:[
            { required: true, message: '请输入用户名', trigger: 'blur' },
            ],
            password:[
            { required: true, message: '请输入密码', trigger: 'blur' },
            ]
        }
      }
    },
    methods:{
      login(){
        this.$refs['LoginFormRef'].validate(async (valid) =>{
          if(valid){
            this.request.post("http://localhost:8084/api/backend/login",this.loginForm).then(res=>{
              if(res.code=='200'){
                console.log(res.data.token)
                localStorage.setItem("user",JSON.stringify(res.data));//存储用户信息到浏览器
                //动态设置当前用户的路由
                localStorage.setItem("menus",JSON.stringify(res.data.menus));//存储用户权限菜单信息到浏览器
                setRoutes()
                this.$router.push("/backend/user");
                this.$message.success("登录成功");
              }else{
                this.$message.error(res.msg);
              }
            })
          }
        })
      },
      resetLoginForm(){
        this.$refs.LoginFormRef.resetFields()
      }
    },   
}
</script>

<style scoped>
.login_container{
background-color: #2b4b6b;
height: 100%;
}

.login_box{
width: 350px;
height: 300px;
background-color: #fff;
border-radius: 3px;
position: absolute;
left: 50%;
top: 50%;
transform: translate(-50%,-50%)
}
</style>
   
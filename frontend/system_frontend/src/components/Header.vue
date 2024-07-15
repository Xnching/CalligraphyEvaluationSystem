<template>
    <el-dropdown style="width:100px; cursor:pointer">
        <span>{{user.name}}</span><i class="el-icon-arrow-down" style="margin-left:5px"></i>
        <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>个人信息</el-dropdown-item>
            <el-dropdown-item>
                <span style="text-decoration: none" @click="logout">退出</span>
            </el-dropdown-item>
        </el-dropdown-menu>
    </el-dropdown>  
</template>
<script>
export default {
    name: "Header",
    props: {
    //姓名采用传参的方式
    name: String
    },
    data() {
        return{
            user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}
        }
    },
    methods:{
        logout(){
            this.$router.push("/backend/login");
            localStorage.removeItem("user");
            this.$message.success("退出成功");
        }
    } 
}
</script>

<style>

</style>

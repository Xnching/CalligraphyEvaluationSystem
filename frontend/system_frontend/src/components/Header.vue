<template>
    <el-dropdown style="width:100px; cursor:pointer">
        <span>{{name}}</span><i class="el-icon-arrow-down" style="margin-left:5px"></i>
        <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>个人信息</el-dropdown-item>
            <el-dropdown-item>退出</el-dropdown-item>
        </el-dropdown-menu>
    </el-dropdown>  
</template>
<script>
export default {
    name: "Header",
    props: {
    //姓名采用传参的方式
    name: String
  }
}
</script>

<style>

</style>

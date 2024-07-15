<template>
  <!--   此处更改默认打开的导航，1，3对应导航，style为隐藏边线多余部分   -->
  <el-menu :default-openeds="['1', '3']"    
      background-color=rgb(48,65,86)
      text-color=#ccc
      active-text-color=red
      router=""
      style="min-height:100%; overflow-x:hidden"
      >
    <!-- LOGO位置-->
    <div style="height:60px; line-height:60px; text-align:center">
      <img src="../assets/logo.png" style="width:20px;position:relative;top:5px;margin-right:5px"/>
      <b style="color:white">后台管理系统</b>
    </div>
    <div v-for="item in menus" :key="item.id">
      <div v-if="item.path">
        <el-menu-item :index="item.path">
          <i :class="item.icon"></i>
          <span slot="title">{{item.name}}</span>
        </el-menu-item>
      </div>
      <div v-else>
        <el-submenu :index="item.id+''">
          <template slot="title">
            <i :class="item.icon"></i>
            <span slot="title">{{item.name}}</span>
          </template>
          <div v-for="subItem in item.children" :key="subItem.id">
            <div v-if="subItem.path">
              <el-menu-item :index="subItem.path">
                <i :class="subItem.icon"></i>
                <span slot="title">{{subItem.name}}</span>
              </el-menu-item>
            </div>
            <div v-else>
              <el-submenu :index="subItem.id+''">
                <template slot="title">
                  <i :class="subItem.icon"></i>
                  <span slot="title">{{subItem.name}}</span>
                </template>
                <el-menu-item v-for="subSubItem in subItem.children" :key="subSubItem.id" :index="subSubItem.path">
                  <i :class="subSubItem.icon"></i>
                  <span slot="title">{{subSubItem.name}}</span>
                </el-menu-item>
              </el-submenu>
            </div>
          </div>
        </el-submenu>
      </div>
    </div>    
  </el-menu>
</template>


<script>
export default {
    //输出组件
    name: "Aside",
    props:{

    },
    data(){
      return{
        menus:localStorage.getItem("menus")?JSON.parse(localStorage.getItem("menus")):[],  
        opens:localStorage.getItem("menus")?JSON.parse(localStorage.getItem("menus")).map(v=>v.id+''):[],
      }
    },
    methods:{
      showmst(){
        console(this.opens)
      }
    }
}
</script>

<style scoped>
.el-menu {
  overflow-y: auto;
}

.el-menu::-webkit-scrollbar {
  display: none;
}

.el-menu {
  -ms-overflow-style: none;
}
</style>

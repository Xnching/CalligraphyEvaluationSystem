<!-- 此为主页面 -->
<!-- 此为主页面 -->
<!-- 此为主页面 -->
<template>
  <div class="home" style="height:100%;">
   <el-container style="height: 100%; border: 1px solid #eee" >
	  <el-aside width="200px" style="background-color: rgb(238, 241, 246); height: 100%; overflow: auto;">
        <Aside></Aside>
    </el-aside>

	  <el-container>

      <!--  此处为header -->
	    <el-header style="text-align: right; font-size: 12px; border-bottom: 1px solid red; line-height:60px">
        <Header name="王小虎测试"/>
      </el-header>


      <!-- 此处为主体部分 -->
	    <el-main>
        <!--当前页面的子路由将在<router-view/>中展示-->
        <router-view/>   
      </el-main>


	  </el-container>
	</el-container>
  </div>
</template>


<script>
// @ is an alias to /src
import Aside from '@/components/Aside.vue'
import Header from '@/components/Header.vue'


export default {
  //此处添加组件
  components:{
    Aside,
    Header
  },
  data() {
    return {
      tableData: Array(8).fill().map(() => ({      //表格中的数值必须要在内存中的地址不同，即是新创建的实例，否则无法实现多选
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      })),
      multipleSelection: []
    }
  },

  methods: {
    toggleSelection(rows) {     //此处是实现多选的方法
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    }
  }
}

</script>

<style>
.el-aside::-webkit-scrollbar {
  display: none;
}
</style>
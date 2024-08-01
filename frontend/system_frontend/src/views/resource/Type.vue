<template>
  <el-table
      ref="multipleTable"
      :data="factsData"
      style="width: 100%;margin-bottom: 20px;"
      row-key="id"
      border
      default-expand-all
      :tree-props="{children: 'children'}">
      <el-table-column label="名称">
            <template slot-scope="scope">
                <el-input v-model="scope.row.name" placeholder="请输入类型名称" v-if="scope.row.edit"></el-input>
                <span v-else>{{scope.row.name}}</span>
            </template>
        </el-table-column>
    <el-table-column fixed="right" label="操作">
      <template slot-scope="scope">
        <el-button type="success" size="small" icon="el-icon-edit" @click="handleEdit(scope.row)">修改</el-button>
        <el-button type="danger" size="small"  icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
        <el-button type="warning" size="small"  icon="el-icon-delete" @click="handleAdd(scope.row)">添加下级</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<!-- 实现了父节点无法被选中但是全选没了！-->

<script>

export default{
  data(){
    return{
      factsData:[],
        
      
    }

  },
  created(){
    this.load();
  },
  methods:{
    //加载页面数据
    load(){
      this.loading = true; // 开始加载
      this.request.get("/calligraphy-facts/page").then(res=>{
          console.log(res);
          if(res.code=='200'){
              this.factsData=res.data;
              console.log('设置到表格的数据:', this.factsData);
          }else{
              this.$message.error('获取全部地区数据失败，原因：'+res.msg);
          }
      })

    },
    handleAdd(){

    },
    handleDelete(){

    },
    handleEdit(){

    }

  }


}
</script>


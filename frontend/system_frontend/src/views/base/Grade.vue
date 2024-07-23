<template>
    <el-table
    ref="multipleTable"
    :data="gradeData"
    style="width: 100%;margin-bottom: 20px;"
    row-key="id"
    border
    default-expand-all
    :tree-props="{children: 'children'}">
    <el-table-column
        prop="name"
        label="名称">
    </el-table-column>
    <el-table-column fixed="right" label="操作">                         
          <template slot-scope="scope">
              <el-button type="success" size="small" icon="el-icon-edit" @click="handleEdit(scope.row)">修改</el-button>
              <el-button type="danger" size="small"  icon="el-icon-delete">删除</el-button>
              <el-button type="warning" size="small"  icon="el-icon-delete">添加下级</el-button>
          </template>
        </el-table-column> 
    </el-table>
  </template>
  
  <script>
  
  export default{
    name:'gradeTable',
    data(){
        return{
            gradeData:[],
        }

    },
    created(){
        this.load();
    },
    methods:{
      //加载页面数据
      load(){
            this.loading = true; // 开始加载
            this.request.get("/grade/page").then(res=>{
                console.log(res);
                if(res.code=='200'){
                    this.gradeData=res.data;
                }else{
                    this.$message.error('获取全部年级数据失败，原因：'+res.msg);
                }
                this.loading = false; // 加载完成
            })

        },

    }


}
  </script>
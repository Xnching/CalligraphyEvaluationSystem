<template>
    <div>
        <div>
            <el-input style="width:300px " 
                suffix-icon="el-icon-search" 
                placeholder="请输入以搜索地区"
                v-model="inputVal"
                @keyup.enter.native="Search_table()"
                clearable>
            </el-input>
            <el-button style="margin-left:20px ;margin-right:535px" type="primary" @click="Search_table">搜索</el-button>
        </div>
        <el-table
        ref="multipleTable"
        :data="regionData"
        style="width: 100%;margin-bottom: 20px;"
        row-key="id"
        border
        v-loading="loading"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(0, 0, 0, 0.8)"
        default-expand-all
        @data-change="handleDataChange"
        :tree-props="{children: 'children'}">
        <el-table-column label="名称">
            <template slot-scope="scope">
                <el-input v-model="scope.row.name" placeholder="请输入地区名称" v-if="scope.row.edit"></el-input>
                <span v-else>{{scope.row.name}}</span>
            </template>
        </el-table-column>
        <el-table-column
          prop="id"
          label="区域id"
          width="100">
        </el-table-column>
        <el-table-column fixed="right" label="操作">                         
            <template slot-scope="scope">
                <el-button type="success" size="small" icon="el-icon-edit" @click="handleEdit(scope.row)">修改</el-button>
                <el-button type="danger" size="small"  icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
                <el-button type="warning" size="small"  icon="el-icon-delete" @click="handleAdd(scope.row)">添加下级</el-button>
            </template>
            </el-table-column> 
        </el-table>

    
        
    </div>
    
</template>

<script>
  
  export default{
    name:'reigonTable',
    data(){
        return{
            inputVal:"",
            regionData:[],
            loading: false, // 新增
            // reigonData:[],
        }

    },
    created(){
        this.load();
    },
    watch: {
    },
    
    methods:{
        Search_table() {
            this.load();
        },
        //加载页面数据
        load(){
            this.loading = true; // 开始加载
            this.request.get("/region/page",{
                params:{
                    str:this.inputVal,
                }
            }).then(res=>{
                console.log(res);
                if(res.code=='200'){
                    this.regionData=res.data;
                    console.log('设置到表格的数据:', this.regionData);
                }else{
                    this.$message.error('获取全部地区数据失败，原因：'+res.msg);
                }
                this.loading = false; // 加载完成
            })

        },
        handleDataChange(newData) {
            console.log('表格数据发生变化:', newData);
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
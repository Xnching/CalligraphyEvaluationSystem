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
        :data="reigonData"
        style="width: 100%;margin-bottom: 20px;"
        row-key="id"
        border
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
          prop="name"
          label="用户姓名"
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
        <p>{{regionData}}</p>

        <!-- 分页栏-->
        <div style="padding:10px">
            <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[15, 30, 50, 100]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
            </el-pagination>
        </div>
        
    </div>
    
</template>

<script>
  
  export default{
    name:'reigonTable',
    data(){
        return{
            inputVal:"",
            reigonData:[],
            total:0,
            pageNum:1,
            pageSize:15,
            reigonData:[],
        }

    },
    created(){
        this.load();
    },
    watch: {
        regionData(newData) {
            console.log('表格数据发生变化:', newData);
        }
    },
    
    methods:{
        Search_table() {
            this.load();
        },
        //分页用的功能
        handleCurrentChange(val) {
            this.pageNum = val;   //获取当前第几页
            this.load();
        },
        handleSizeChange(val) {
            this.pageSize = val;  //获取当前每页显示条数
            this.load();
        },
        //加载页面数据
        load(){
            this.request.get("/region/page",{
                params:{
                    pageNum:this.pageNum,
                    pageSize:this.pageSize,
                    str:this.inputVal,
                }
            }).then(res=>{
                console.log(res);
                if(res.code=='200'){
                    this.regionData=res.data.records;
                    this.total=res.data.total;
                    console.log('设置到表格的数据:', this.regionData);
                    this.$refs.multipleTable.clearSelection();
                }else{
                    this.$message.error('获取全部地区数据失败，原因：'+res.msg);
                }
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
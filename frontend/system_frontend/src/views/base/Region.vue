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
        handleAdd(row){
            this.$prompt('请输入下级区域名称', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
            }).then(({ value }) => {
                this.request.post('/region/add', {
                    name: value,
                    parentId: row.id,
                    level: row.level + 1,
                }).then(res => {
                if (res.status === 200) {
                    this.$message.success('添加成功');
                    this.load();
                } else {
                    this.$message.error('添加失败');
                }
                });
            }).catch(() => {
                this.$message.info('取消添加');
            });
        },
        handleDelete(row){
            this.$confirm('此操作将永久删除该区域, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(() => {
            this.request({
                url: '/region/delete',
                method: 'delete',
                data: {
                    id: row.id
                }
            }).then(res => {
            if (res.status === 200) {
                this.$message.success('删除成功');
                this.load();
            } else {
                this.$message.error('删除失败');
            }
            });
        }).catch(() => {
            this.$message.info('取消删除');
        });
        },
        handleEdit(row) {
            this.$prompt('请输入新的区域名称', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                inputValue: row.name
            }).then(({ value }) => {
                this.request.put('/region/update', {
                    id: row.id,
                    name: value,
                    parentId: row.parentId,
                    level: row.level,
                }).then(res => {
                if (res.status === 200) {
                    this.$message.success('修改成功');
                    this.load();
                } else {
                    this.$message.error('修改失败');
                }
                });
            }).catch(() => {
                this.$message.info('取消修改');
            });
        }

    }


}
  </script>
<template>
    <div class="management-container" style=" text-align: center;">
        <!--字体定义管理-->
        <div>
            <label>字体类型管理</label>
            <!--搜索框-->
            <div style="padding:10px; margin:10px; margin-bottom: -5px;">
              搜索：<el-input style="width:220px " 
                suffix-icon="el-icon-search" 
                placeholder="请输入"
                v-model="inputVal1"
                @keyup.enter.native="Search_table()"
                clearable>
              </el-input>
              <el-button style="margin-left:20px ;margin-right:235px" type="primary" @clickadd="Search_table">搜索</el-button>
              新增：<el-input style="width:220px " 
                suffix-icon="el-icon-search" 
                placeholder="请输入"
                v-model="inputVal2"
                clearable>
              </el-input>
              <el-button type="primary" style="margin-left:20px " @click="handleAdd">新增<i class="el-icon-circle-plus"></i></el-button>
            </div>      

            <!--表格-->
            <el-table
            ref="fontTable"
            :data="tableData"
            stripe
            tooltip-effect="dark"
            style="width: 100%">
                <el-table-column
                prop="id"
                label="字体编号"
                width="295">
                </el-table-column>
                <el-table-column
                prop="name"
                label="字体名"
                width="300">
                </el-table-column>
                <el-table-column fixed="right" label="操作">                         
                    <template slot-scope="scope">
                        <el-button type="danger" size="small" icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
                    </template>
                </el-table-column> 

            </el-table>
            <!-- 分页栏-->
            <div style="padding:10px">
                <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-sizes="[6, 10, 15, 20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
                </el-pagination>
            </div>
            



        </div>


    </div>
</template>
<script>
export default {
  data(){
      return{
        pageNum:1,
        pageSize:6,
        total:0,
        tableData:[],
        inputVal1:'',
        inputVal2:'',
      }
  },
  created(){
    this.load();      
  },
  methods:{
    Search_table(){
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
    handleAdd(){
      this.request.post("/font/add",{
          name:this.inputVal2
        }).then(res=>{
        if(res.code == '200'){
          this.$message.success('新增字体成功！');
          this.inputVal2='';
          this.load();
        } else {
          this.$message.error('新增字体失败，原因：' + res.msg);
        }
      })
    },
    //请求分页查询数据
    load(){
      this.request.get("/font/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          str:this.inputVal1,
        }
      }).then(res=>{
        //console.log(res);
        if(res.code=='200'){
          this.tableData=res.data.records;
          this.total=res.data.total;
        }else{
          this.$message.error('获取全部字体失败，原因：'+res.msg);
        }
      })
    },
    //编辑用户弹窗确定提交的方法
    handleDelete(row) {
      // 在此处处理删除逻辑
      this.$confirm('确认删除该记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 调用后端删除接口
        this.request.put('/font/delete', {
          id: row.id
        }).then(res => {
          if(res.code == '200'){
            this.$message.success('删除字体成功！');
            this.load();
          }else{
            this.$message.error('删除字体失败，原因：'+res.msg);
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });          
      });
    },
  },
}
</script>

<style scoped>
</style>

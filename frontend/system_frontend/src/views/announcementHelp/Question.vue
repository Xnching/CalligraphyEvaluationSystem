<template>
  <div class="container" >
    <div style=" text-align: right;">
      <el-button type="primary" @click="handleAdd" style="margin-right: 130px">新增</el-button>
    </div>
        <el-table :data="tableData" 
        style="width: 100%"
        stripe>
          <el-table-column prop="q" label="问题"></el-table-column>
          <el-table-column prop="a" label="答案"></el-table-column>
          <el-table-column prop="editor" label="编辑人"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button @click="handleEdit(scope.row)">编辑</el-button>
              <el-button type="danger" @click="handleDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页栏-->
        <div style="padding:10px">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[20, 25, 30, 40]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
          </el-pagination>
        </div>
        
        <el-dialog :visible.sync="dialogVisible1" width="50%">
          <el-form ref="form" :model="editForm" label-width="80px">
            <el-form-item label="问题">
              <el-input v-model="editForm.q"></el-input>
            </el-form-item>
            <el-form-item label="答案">
              <el-input v-model="editForm.a" type="textarea"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible1 = false">取消</el-button>
            <el-button type="primary" @click="handleSave">确定</el-button>
          </span>
        </el-dialog>


        <el-dialog :visible.sync="dialogVisible2" width="50%">
          <el-form ref="form" :model="addForm" label-width="80px">
            <el-form-item label="问题">
              <el-input v-model="addForm.q"></el-input>
            </el-form-item>
            <el-form-item label="答案">
              <el-input v-model="addForm.a" type="textarea"></el-input>
            </el-form-item>
          </el-form>
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible2 = false">取消</el-button>
            <el-button type="primary" @click="handleSubmit">确定</el-button>
          </span>
        </el-dialog>
  </div>
</template>
<script>
export default {
  data() {
    return {
      tableData: [], // 表格数据
      pageNum:1,
      pageSize:20,
      total:0,
      editForm: {},
      addForm:{
        q:'',
        a:''
      },
      tableData:[],
      dialogVisible1: false, // 弹窗显示状态
      dialogVisible2:false,
    };
  },
  created(){
    this.load();
  },
  methods: {
    handleAdd(){
      this.addForm.q='';
      this.addForm.a='';
      this.dialogVisible2=true;
    },
    handleEdit(row){
      this.editForm=row;
      this.dialogVisible1=true;
    },
    handleSubmit(){
      this.request.post("/question/add",this.addForm).then(res=>{
        if(res.code == '200'){
          this.$message.success('添加数据成功！');
          this.dialogVisible1=false;
        } else {
          this.$message.error('添加数据失败，原因：' + res.msg);
        }
      })
      this.dialogVisible2=false;
      this.load();
    },
    handleDelete(row){
      // 在此处处理删除逻辑
			this.$confirm('确认删除该记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 调用后端删除接口
        this.request({
          url: '/question/delete',
          method: 'delete',
          data: {
            id: row.id
          }
        }).then(res => {
          if(res.code == '200'){
            this.$message.success('删除数据成功！');
            this.load();
          }else{
            this.$message.error('删除数据失败，原因：'+res.msg);
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });          
      });
    },
    handleSave(){
      this.request.put("/question/update",this.editForm).then(res=>{
        if(res.code == '200'){
          this.$message.success('修改样本字数据成功！');
          this.dialogVisible1=false;
        } else {
          this.$message.error('修改样本字数据失败，原因：' + res.msg);
        }
      })
      this.dialogVisible1=false;     
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
    //请求分页查询数据
    load(){
      this.request.get("/question/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
        }
      }).then(res=>{
        if(res.code=='200'){
					console.log(res);
          this.tableData=res.data.records;
          this.total=res.data.total;
        }else{
          this.$message.error('获取全部数据失败，原因：'+res.msg);
        }
      })
    },
  },
  // 可以在这里添加生命周期钩子等其他逻辑
};
</script>

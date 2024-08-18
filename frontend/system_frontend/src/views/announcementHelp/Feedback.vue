<template>
  <div>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column 
      prop="providerType" 
      label="用户类型">
      </el-table-column>
      <el-table-column 
      prop="type" 
      label="反馈类型">
      </el-table-column>
      <el-table-column 
      prop="state" 
      label="有效性">
      </el-table-column>
      <el-table-column 
      prop="providerPhone" 
      label="反馈人电话">
      </el-table-column>
      <el-table-column 
      prop="editor" 
      label="编辑人">
      </el-table-column>
      <el-table-column fixed="right" label="操作">                         
          <template slot-scope="scope">
              <el-button type="primary" size="small" icon="el-icon-edit"  @click="handleEdit(scope.row)">回复</el-button>
              <el-button type="danger" size="small" icon="el-icon-edit"  @click="handleDelete1(scope.row)">删除</el-button>
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


    <!--反馈内容弹窗-->
    <el-dialog title="用户反馈内容" :visible.sync="dialogVisible" style=" text-align: center;">
      <div class="dialog-container">
        <el-form ref="editForm" :model="editForm" label-width="80px">
            <el-form-item label="反馈内容:">
              <el-input type="textarea" v-model="message"></el-input>
            </el-form-item>
            <h3>照片区域</h3>
            <el-image
              style="width: 100px; height: 100px"
              :src="file">
            </el-image>
            <el-form-item label="用户类型:">
              <el-input v-model="editForm.providerType"></el-input>
            </el-form-item>
            <el-form-item label="反馈性质:">
              <el-input v-model="editForm.type"></el-input>
            </el-form-item>
            <el-form-item label="电话:">
              <el-input v-model="editForm.providerPhone"></el-input>
            </el-form-item>
            <el-form-item label="回复内容:">
              <el-input type="textarea" v-model="reply"></el-input>
            </el-form-item>

        </el-form>
        
        <el-button type="primary" @click="submitReply">回复</el-button>
        <el-button type="warning" @click="submitState">设为无效反馈</el-button>
        <el-button type="danger" @click="handleDelete2">删除</el-button>
      </div>
    </el-dialog>
  </div>


</template>

<script>
export default {
  data() {
    return {
      total:0,
      pageNum:1,
      pageSize:10,
      dialogVisible:false,
      tableData: [],
      message:'',
      reply:'',
      file:'',
      editForm:{},    
    };
  },
  created(){
    this.load();
  },
  methods: {
    handleDelete1(row){
      this.delete(row.id);
      this.load();
    },
    handleDelete2(){
      this.delete(this.editForm.id);
      this.dialogVisible=false;
      this.load();
    },
    delete(val){
      // 在此处处理删除逻辑
			this.$confirm('确认删除该记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 调用后端删除接口
        this.request({
          url: '/feedback/delete',
          method: 'delete',
          data: {
            id: val
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
    submitState() {
      // 调用后端删除接口
      this.request.put('/feedback/state', {
          id: this.editForm.id,
        }).then(res => {
          if(res.code == '200'){
            this.$message.success('修改数据成功！');
            this.dialogVisible=false;
            this.load();
          }else{
            this.$message.error('修改数据失败，原因：'+res.msg);
          }
        });
    },
    submitReply() {
      // 调用后端删除接口
      this.request.put('/feedback/reply', {
          id: this.editForm.id,
          reply:this.reply
        }).then(res => {
          if(res.code == '200'){
            this.$message.success('修改数据成功！');
            this.load();
            this.dialogVisible=false;
          }else{
            this.$message.error('修改数据失败，原因：'+res.msg);
          }
        });
    },
    handleEdit(row){
      // 将当前行数据复制到 editForm 中，避免直接修改表格数据
      this.editForm = Object.assign({}, row); 
      this.getDetail(this.editForm.id);
      this.dialogVisible=true;
    },
    getDetail(val){
      this.request.get("/feedback/detail",{
        params:{
          id:val
        }
      }).then(res=>{
        if(res.code=='200'){
          this.reply = res.data.reply;
          this.message = res.data.message;
          this.file = res.data.file;
        }else{
          this.$message.error('获取全部用户数据失败，原因：'+res.msg);
        }
      })
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
      this.request.get("/feedback/page",{
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
          this.$message.error('获取全部用户数据失败，原因：'+res.msg);
        }
      })
    },
  }
};
</script>

<style scoped>

</style>

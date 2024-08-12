<template>
  <div>
    <div style="padding:10px; margin:10px; margin-bottom: -5px;">
      <el-input style="width:300px"
                suffix-icon="el-icon-search"
                placeholder="请输入"
                v-model="inputVal"
                @keyup.enter.native="Search_table()"
                clearable>
      </el-input>
      <el-button style="margin-left:20px ;margin-right:535px" type="primary" @click="Search_table()">搜索</el-button>
    </div>

    <el-table
        ref="multipleTable"
        :data="tableData"
        stripe
        tooltip-effect="dark"
        style="width: 100%">
      <el-table-column
          prop="submissionId"
          label="作品编号"
          width="95">
      </el-table-column>
      <el-table-column
          prop="name"
          label="作业名称"
          width="190">
      </el-table-column>
      <el-table-column
          prop="author"
          label="作者"
          width="120">
      </el-table-column>
      <el-table-column
          prop="teacher"
          label="推荐教师"
          width="120">
      </el-table-column>
      <el-table-column
          prop="type"
          label="作业类型"
          width="120">
      </el-table-column>
      <el-table-column
          prop="reviewer"
          label="评选人"
          width="120">
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button type="success" size="small" icon="el-icon-edit" @click="handleEdit(scope.row)">作品详情</el-button>
          <el-button type="danger" size="small" icon="el-icon-delete" @click="handleDelete1(scope.row)">移出优秀作品</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页栏-->
    <div style="padding:10px">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[10, 15, 20, 30]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>

    <!-- 作品详情弹窗 -->
    <el-dialog :visible.sync="dialogVisible" width="60%" :close-on-click-modal="false">
      <el-form label-position="left" label-width="90px" :model="editForm">
        <!-- 作业图片 -->
        <el-form-item label="作业图片:">
          <div v-for="(imageSrc, index) in editForm.imageSrcList" :key="index">
            <img :src="imageSrc" alt="作业图片" style="width: 100%; margin-bottom: 10px;">
          </div>
        </el-form-item>
        <!-- 作者 -->
        <el-form-item label="作者:">
          <el-input v-model="author" placeholder="作者"></el-input>
        </el-form-item>
        <!-- 作业类型 -->
        <el-form-item label="作业类型:">
          <el-input v-model="type" placeholder="作业类型"></el-input>
        </el-form-item>
        <!-- 分数 -->
        <el-form-item label="分数:">
          <el-input v-model="editForm.teacherScore" placeholder="分数"></el-input>
        </el-form-item>
        <!-- 作业名 -->
        <el-form-item label="作业名:">
          <el-input v-model="name" placeholder="作业名"></el-input>
        </el-form-item>
        <!-- 评选人 -->
        <el-form-item label="评选人:">
          <el-input v-model="reviewer" placeholder="评选人"></el-input>
        </el-form-item>
        <!-- 教师评语 -->
        <el-form-item label="教师评语:">
          <el-input v-model="editForm.teacherFeedback" placeholder="教师评语"></el-input>
        </el-form-item>
        <!-- 删除按钮 -->
        <el-form-item style=" text-align: right;">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="danger" @click="handleDelete2">删除</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pageNum:1,
      pageSize:20,
      total:0,
      inputVal: "",
      tableData: [],
      dialogVisible: false,
      editForm: {},
      grade:'',
      teacher:'',
      author:'',
      type:'',
      name:'',
      reviewer:'',
    };
  },
  created(){
    this.load();
  },
  methods: {
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
    //请求分页查询数据
    load(){
      this.request.get("/outstanding-homework/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          str:this.inputVal,
        }
      }).then(res=>{
        if(res.code=='200'){
					console.log(res);
          this.tableData=res.data.records;
          this.total=res.data.total;
        }else{
          this.$message.error('获取全部视频数据失败，原因：'+res.msg);
        }
      })
    },
    handleEdit(row) {
      this.grade = row.grade;
      this.teacher = row.teacher;
      this.author = row.author;
      this.type = row.type;
      this.name = row.name;
      this.reviewer = row.reviewer;
      // 然后显示弹窗
      this.request.get("/outstanding-homework/detail",{
        params:{
          submissionId:row.submissionId
        }
      }).then(res=>{
        if(res.code=='200'){
					console.log(res);
          this.editForm=res.data;
          console.log('让我看下editForm');
          console.log(this.editForm);
          
        }else{
          this.$message.error('获取全部视频数据失败，原因：'+res.msg);
        }
      })
      this.dialogVisible = true;
      // 如果需要，可以根据row设置弹窗中的其他内容
    },
    handleDelete1(row) {
      console.log("有东西吗：");
      console.log(row.submissionId);
      
      this.delete(row.submissionId);
    },
    handleDelete2() {
      this.delete(this.editForm.id);
      this.dialogVisible = false;
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
          url: '/outstanding-homework/delete',
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
  },
};
</script>

<style scoped>
.el-dialog__headerbtn {
  margin-top: -10px;
}
</style>

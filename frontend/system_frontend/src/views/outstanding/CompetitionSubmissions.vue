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
      <el-button style="margin-left:20px ;margin-right:535px" type="primary" @click="Search_table">搜索</el-button>
    </div>

    <el-table
        ref="multipleTable"
        :data="tableData"
        stripe
        tooltip-effect="dark"
        style="width: 100%">
      <el-table-column
          prop="submissionId"
          label="竞赛作品编号"
          width="120">
      </el-table-column>
      <el-table-column
          prop="competition"
          label="竞赛名"
          width="200">
      </el-table-column>
      <el-table-column
          prop="division"
          label="组别"
          width="200">
      </el-table-column>
      <el-table-column
          prop="author"
          label="作者"
          width="80">
      </el-table-column>
      <el-table-column
          prop="averageFinalScore"
          label="最终分数"
          width="80">
      </el-table-column>
      <el-table-column
          prop="rk"
          label="排名"
          width="80">
      </el-table-column>
      <el-table-column
          prop="level"
          label="等级"
          width="100">
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit(scope.row)">作品详情</el-button>
          <el-button type="danger" size="small"  icon="el-icon-delete" @click="handleDelete1(scope.row)">移出优秀作品</el-button>
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

    <!-- 弹窗 -->
    <el-dialog title="作品详情"
        :visible.sync="dialogVisible"
        width="40%"
        :close-on-click-modal="false">
      <el-form label-position="left" label-width="90px" :model="editForm">
        <!-- 作业图片 -->
        <el-form-item label="作业图片:">
          <div v-for="(imageSrc, index) in editForm.imageSrcList" :key="index">
            <img :src="imageSrc" alt="作业图片" style="width: 100%; margin-bottom: 10px;">
          </div>
        </el-form-item>
        <!-- 作者 -->
        <el-form-item label="作者:">
          <el-input v-model="editForm.author" placeholder="作者"></el-input>
        </el-form-item>
        <!-- 竞赛名 -->
        <el-form-item label="竞赛名:">
          <el-input v-model="editForm.competition" placeholder="竞赛名"></el-input>
        </el-form-item>
        <el-form-item label="组别:">
          <el-input v-model="editForm.division" placeholder="组别"></el-input>
        </el-form-item>
        <!-- 分数 -->
        <el-form-item label="最终分数:">
          <el-input v-model="editForm.averageFinalScore" placeholder="分数"></el-input>
        </el-form-item>
        <!-- 等级 -->
        <el-form-item label="等级:">
          <el-input v-model="editForm.level" placeholder="等级"></el-input>
        </el-form-item>
        <!-- 教师评语 -->
        <el-form-item label="初级评价:">
          <el-input v-model="editForm.initialEvaluation" placeholder="教师评语"></el-input>
        </el-form-item>
        <!-- 删除按钮 -->
        <el-form-item style=" text-align: right;">
          <el-button type="danger" @click="handleDelete2">移出优秀作品</el-button>
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
      inputVal:"",
      tableData: [],
      dialogVisible: false,
      editForm:{},
    };
  },
  created(){
    this.load();
  },
  methods: {
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
      this.request.get("/outstanding-competition/page",{
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
    Search_table() {
      this.load();
    },
    handleEdit(row) {
      this.editForm = row;
      this.dialogVisible = true;
    },
    handleDelete1(row) {
      
      this.delete(row.submissionId);
    },
    handleDelete2() {
      this.delete(this.editForm.submissionId);
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
          url: '/outstanding-competition/delete',
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

<template>
  <div>
    <el-button type="primary" @click="goBack">返回</el-button>
    <el-tabs v-model="currentPage">
      <el-tab-pane label="初级作品评阅" name="junior">
        <!-- 初级作品评阅的内容 -->
        <div style="padding:10px; margin:10px; margin-bottom: -5px;">
          <h2 style=" text-align: center;">{{competitionDivision}}---{{ teacherName }}</h2>
        </div>
  
        <el-table
            ref="initialTableData"
            :data="initialTableData"
            stripe
            tooltip-effect="dark"
            style="width: 100%">
          <el-table-column
              prop="id"
              label="作品编号"
              width="125">
          </el-table-column>
          <el-table-column
              prop="systemScore"
              label="系统评分"
              width="120">
          </el-table-column>
          <el-table-column
              prop="initialScore"
              label="该教师初级评分"
              show-overflow-tooltip>
          </el-table-column>
          <el-table-column fixed="right" label="操作">
            <template slot-scope="scope">
              <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit1(scope.row)">详情</el-button>
            </template>
          </el-table-column>
  
        </el-table>
  
        <div style="padding:10px">
          <el-pagination
            @size-change="initialSizeChange"
            @current-change="initialCurrentChange"
            :current-page="initialPageNum"
            :page-sizes="[10, 15, 20, 40]"
            :page-size="initialPageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="initialTotal">
          </el-pagination>
        </div>

      </el-tab-pane>
      <el-tab-pane label="最终作品评阅" name="final">
        <!-- 最终作品评阅的内容 -->
        <div style="padding:10px; margin:10px; margin-bottom: -5px;">
          <h2 style=" text-align: center;">{{competitionDivision}}---{{ teacherName }}</h2>
        </div>
  
        <el-table
            ref="finalTableData"
            :data="finalTableData"
            stripe
            tooltip-effect="dark"
            style="width: 100%">
            <el-table-column
              prop="id"
              label="作品编号"
              width="125">
          </el-table-column>
          <el-table-column
              prop="systemScore"
              label="系统评分"
              width="120">
          </el-table-column>
          <el-table-column
              prop="initialScore"
              label="初级评分"
              width="120">
          </el-table-column>
          <el-table-column
              prop="finalScore"
              label="该教师评阅的最终评分"
              show-overflow-tooltip>
          </el-table-column>
          <el-table-column fixed="right" label="操作">
            <template slot-scope="scope">
              <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit2(scope.row)">详情</el-button>
            </template>
          </el-table-column>
  
        </el-table>
  
        <!--分页器-->
        <div style="padding:10px">
          <el-pagination
            @size-change="finalSizeChange"
            @current-change="finalCurrentChange"
            :current-page="finalPageNum"
            :page-sizes="[10, 15, 20, 40]"
            :page-size="finalPageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="finalTotal">
          </el-pagination>
        </div>

      </el-tab-pane>
    </el-tabs>
    <!-- 参赛人员作品详情的弹窗 -->
    <el-dialog
      title="点击修改信息"
      :visible.sync="dialogVisible"
      width="50%"
      :close-on-click-modal="false">
      
      <el-form ref="studentForm" :model="submissionForm" label-width="100px">
        <el-form-item label="作品图片："> 
          <div v-for="image in submissionForm.imageList" :key="image" class="image-item">
            <img :src="image" :alt="image">
          </div>
        </el-form-item>
          <el-form-item label="系统评分：">
              <el-input v-model="submissionForm.systemScore"></el-input>
          </el-form-item>
          <el-form-item label="系统评语：">
              <el-input type="textarea" v-model="submissionForm.systemEvaluation"></el-input>
          </el-form-item>
          <el-form-item label="初级评分：">
              <el-input v-model="submissionForm.initialScore"></el-input>
          </el-form-item>
          <el-form-item label="初级评语：">
              <el-input type="textarea" v-model="submissionForm.initialEvaluation"></el-input>
          </el-form-item>
          <el-form-item label="最终评分：">
              <el-input v-model="submissionForm.finalScore"></el-input>
          </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
          <el-button @click="dialogVisible = false">确定</el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<script>
export default {
  data() {
    return {
      currentPage: 'junior', // 当前选中的tab页
      competitionDivision: '', // 竞赛组别
      teacherName: '', // 教师姓名
      initialTableData: [], // 初级作品评阅表格数据
      finalTableData: [], // 最终作品评阅表格数据
      initialPageNum: 1, // 初级作品评阅当前页码
      initialPageSize: 10, // 初级作品评阅每页显示条数
      initialTotal: 0, // 初级作品评阅总条数
      finalPageNum: 1, // 最终作品评阅当前页码
      finalPageSize: 10, // 最终作品评阅每页显示条数
      finalTotal: 0, // 最终作品评阅总条数
      dialogVisible: false, // 弹窗是否可见
      submissionForm: {
        name:'',
        imageList: [], // 作品图片列表
        systemScore: '', // 系统评分
        systemEvaluation: '', // 系统评语
        initialScore: '', // 初级评分
        initialEvaluation: '', // 初级评语
        finalScore:'',
        initialRank: '', // 初级排名
      },
      teacherIdAndDivisionId: '', // 教师ID和组别ID
      divisionId: '', // 组别ID
      teacherId: '', // 教师ID
    };
  },

  methods: {
    

    //初级作品评阅的评阅按钮
    handleEdit1(row){
      this.submissionForm = row;
      this.dialogVisible=true;
    },

    //最终作品评阅的按钮
    handleEdit2(row){
      this.submissionForm = row;
      this.dialogVisible=true;
    },
    //分页用的功能
    initialCurrentChange(val) {
      this.initialPageNum = val;   //获取当前第几页
      this.loadInitial();
    },
    initialSizeChange(val) {
      this.initialPageSize = val;  //获取当前每页显示条数
      this.loadInitial();
    },
    //请求分页查询数据
    loadInitial(){
      this.request.get("/detailed-review/initial-page",{
        params:{
          pageNum:this.initialPageNum,
          pageSize:this.initialPageSize,
          teacherId:this.teacherId,
          divisionId:this.divisionId
        }
      }).then(res=>{
        //console.log(res);
        if(res.code=='200'){
          console.log(res);
          
          this.initialTableData=res.data.records;
          this.initialTotal=res.data.total;
        }else{
          this.$message.error('获取全部用户数据失败，原因：'+res.msg);
        }
      })
    },
    //分页用的功能
    finalCurrentChange(val) {
      this.finalPageNum = val;   //获取当前第几页
      this.loadFinal();
    },
    finalSizeChange(val) {
      this.finalPageSize = val;  //获取当前每页显示条数
      this.loadFinal();
    },
    //请求分页查询数据
    loadFinal(){
      this.request.get("detailed-review/final-page",{
        params:{
          pageNum:this.finalPageNum,
          pageSize:this.finalPageSize,
          teacherId:this.teacherId,
          divisionId:this.divisionId
        }
      }).then(res=>{
        //console.log(res);
        if(res.code=='200'){
          this.finalTableData=res.data.records;
          this.finalTotal=res.data.total;
        }else{
          this.$message.error('获取全部用户数据失败，原因：'+res.msg);
        }
      })
    },
    //获得教师名称
    getTeacherName(){
      this.request.get("/teacher/name",{
        params:{
          teacherId:this.teacherId
        }
      }).then(res=>{
        if(res.code=='200'){
          this.teacherName=res.data;
        }else{
          this.$message.error('获取数据失败，原因：'+res.msg);
        }
      })
    },
    //获取竞赛信息
    getDivision(){
      this.request.get("/competition/competition-name",{
        params:{
          divisionId:this.divisionId
        }
      }).then(res=>{
        if(res.code=='200'){
          console.log(res.data);
          this.competitionDivision = res.data;
        }else{
          this.$message.error('获取全部用户数据失败，原因：'+res.msg);
        }
      })
    },

    
    //返回按钮
    goBack() {
      this.$router.go(-1); 
      this.$destroy();
    },


  },
  created() {
    
    // 通过 this.$route.params 获取路由参数
    this.teacherIdAndDivisionId = this.$route.params.teacherName;
    // 拆分 teacherIdAndDivisionId 成 teacherId 和 divisionId
    const parts = this.teacherIdAndDivisionId.split("-");
    this.divisionId = parts[0];
    this.teacherId = parts[1];
    this.getDivision();
    this.getTeacherName();
    this.loadFinal();
    this.loadInitial();
  }
};

</script>

<style>
</style>
  
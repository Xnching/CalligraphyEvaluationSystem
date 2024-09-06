<template>

  <div>


    <!--评审弹窗-->
    <el-dialog
      :visible.sync="dialogVisible"
      width="50%"
      :close-on-click-modal="false">
      <el-form label-position="left" label-width="80px" :model="editForm">
        <!-- 作业图片 -->
        <el-form-item label="作业图片:">
          <div v-for="(imageSrc, index) in editForm.imageSrcList" :key="index">
            <img :src="imageSrc" alt="作业图片" style="width: 100%; margin-bottom: 10px;">
          </div>
        </el-form-item>
        <!-- 作业名称 -->
        <el-form-item label="作业名称:">
          <el-input v-model="name" placeholder="作业名称"></el-input>
        </el-form-item>
        <!-- 作者 -->
        <el-form-item label="作者:">
          <el-input v-model="author" placeholder="作者"></el-input>
        </el-form-item>
        <!-- 作者年级 -->
        <el-form-item label="作者年级:">
          <el-input v-model="grade" placeholder="作者年级"></el-input>
        </el-form-item>
        <!-- 推荐教师 -->
        <el-form-item label="推荐教师:">
          <el-input v-model="teacher" placeholder="推荐教师"></el-input>
        </el-form-item>
        <!-- 作业类型 -->
        <el-form-item label="作业类型:">
          <el-input v-model="type" placeholder="作业类型"></el-input>
        </el-form-item>
        <!-- 智能评分评语 -->
        <el-form-item label="智能评分:">
          <el-input v-model="editForm.systemScore" placeholder="智能评分"></el-input>
        </el-form-item>
        <el-form-item label="智能评语:">
          <el-input type="textarea" v-model="editForm.systemFeedback" placeholder="智能评语"></el-input>
        </el-form-item>
        <!-- 教师的评分评语 -->
        <el-form-item label="教师评分:">
          <el-input v-model="editForm.teacherScore" placeholder="教师评分"></el-input>
        </el-form-item>
        <el-form-item label="教师评语:">
          <el-input type="textarea" v-model="editForm.teacherFeedback" placeholder="教师评语"></el-input>
        </el-form-item>
        <!-- 通过/不通过按钮 -->
        <el-form-item>
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="warning"@click="handleNotPass">不通过</el-button>
          <el-button type="primary" @click="handlePass">通过</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>


  <!--主页面-->
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
          label="作品ID"
          width="115">
      </el-table-column>
      <el-table-column
          prop="name"
          label="作业名称"
          width="220">
      </el-table-column>
      <el-table-column
          prop="author"
          label="作者"
          width="140">
      </el-table-column>
      <el-table-column
          prop="teacher"
          label="推荐教师"
          width="140">
      </el-table-column>
      <el-table-column
          prop="type"
          label="作业类型"
          width="140">
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit(scope.row)">评审</el-button>
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
  </div>
    
  </div>
  
</template>


<script>
export default {
  data() {
    return {
      pageNum:1,
      pageSize:20,
      total:0,
      //搜索栏要用的
      inputVal:"",
      tableData:[],
      dialogVisible: false,
      editForm: {
        imageSrcList: [], // Array to hold image sources
        systemScore: '', // System score
        systemFeedback: '', // System feedback
        teacherScore: '', // Teacher's score
        teacherFeedback: '' // Teacher's feedback
      },
      grade:'',
      teacher:'',
      author:'',
      type:'',
      name:'',
    };
  },
  created(){
    this.load();
  },

  methods: {
    handleEdit(row) {
      this.grade = row.grade;
      this.teacher = row.teacher;
      this.author = row.author;
      this.type = row.type;
      this.name = row.name;
      // 然后显示弹窗
      this.request.get("/review-outstanding/detail",{
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

    // 处理点击通过按钮
    handlePass() {
      // 这里添加处理通过按钮点击的逻辑
      // 比如更新作业状态等
      this.review('通过');
      this.dialogVisible = false; // 关闭弹窗
    },
    // 处理点击通过按钮
    handleNotPass() {
      // 这里添加处理通过按钮点击的逻辑
      // 比如更新作业状态等
      this.review('不通过');
      this.dialogVisible = false; // 关闭弹窗
    },
    review(type){
      console.log("让我看下id");
      
      console.log(this.editForm.id);
      
      this.request.post("/review-outstanding/review", {
        submissionId:this.editForm.id,
        result:type
			}).then(res=>{
        if(res.code == '200'){
          this.$message.success('新增合集成功！');
          this.load();
        } else {
          this.$message.error('新增合集失败，原因：' + res.msg);
        }
      })
    },
    
    
    //实现搜索栏多属性搜索的
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
      this.request.get("/review-outstanding/page",{
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
    
  },

};

</script>
<style scoped>

</style>

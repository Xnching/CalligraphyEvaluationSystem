<template>
  <div>
      <el-tabs v-model="activeName" type="card">
      <!--竞赛信息页面-->
      <el-tab-pane label="竞赛信息" name="first">
          <el-button type="primary" @click="goBack">返回</el-button>
          <div style="display: flex; flex-wrap: wrap; padding: 10px;">
              <div style="flex: 1; margin-right: 20px;">
                <el-form :model="form">
                  <el-form-item label="竞赛编号">
                      <el-input v-model="form.id" :disabled="!editable"></el-input>
                  </el-form-item>
                  <el-form-item label="竞赛名称">
                      <el-input v-model="form.name" :disabled="!editable"></el-input>
                  </el-form-item>
                  <el-form-item label="开始报名时间">
                      <el-date-picker v-model="form.registrationStartTime" type="datetime" :disabled="!editable"></el-date-picker>
                  </el-form-item>
                  <el-form-item label="截止报名时间">
                      <el-date-picker v-model="form.registrationEndTime" type="datetime" :disabled="!editable"></el-date-picker>
                  </el-form-item>
                  <el-form-item label="开始竞赛时间">
                      <el-date-picker v-model="form.competitionStartTime" type="datetime" :disabled="!editable"></el-date-picker>
                  </el-form-item>
                  <el-form-item label="结束竞赛时间">
                      <el-date-picker v-model="form.competitionEndTime" type="datetime" :disabled="!editable"></el-date-picker>
                  </el-form-item>
                  <el-form-item label="开始评阅时间">
                      <el-date-picker v-model="form.reviewStartTime" type="datetime" :disabled="!editable"></el-date-picker>
                  </el-form-item>
                </el-form>
              </div>
              <div style="flex: 1;">
                  <el-form :model="form">
                      <el-form-item label="竞赛要求" :disabled="!editable">
                        <wang-editor ref="myEditor" v-model="valueHtml"></wang-editor>
                      </el-form-item>
                      <el-form-item label="宣传图">
                        <img :src="form.picture" :alt="form.name">
                      </el-form-item>
                  </el-form>
              </div>
          </div>

          <div style="padding: 10px;">
            <div v-for="(group, index) in form.groups" :key="index" style="display: flex; flex-wrap: wrap; margin-bottom: 10px;">
              <div style="flex: 1; margin-right: 20px;">
                <el-form :model="group">
                  <el-form-item label="组名">
                    <el-input v-model="group.name" :disabled="!editable"></el-input>
                  </el-form-item>
                  <el-form-item label="特等奖比例">
                    <el-input v-model="group.specialPrizeRatio" suffix="%" :disabled="!editable"></el-input>
                  </el-form-item>
                  <el-form-item label="一等奖比例">
                    <el-input v-model="group.firstPrizeRatio" suffix="%" :disabled="!editable"></el-input>
                  </el-form-item>
                  <el-form-item label="二等奖比例">
                    <el-input v-model="group.secondPrizeRatio" suffix="%" :disabled="!editable"></el-input>
                  </el-form-item>
                  <el-form-item label="三等奖比例">
                    <el-input v-model="group.thirdPrizeRatio" suffix="%" :disabled="!editable"></el-input>
                  </el-form-item>
                  <el-form-item label="参赛对象">
                    <el-radio-group v-model="group.target" :disabled="!editable">
                      <el-radio label="小学">小学</el-radio>
                      <el-radio label="初中">初中</el-radio>
                      <el-radio label="小学和初中">小学和初中</el-radio>
                    </el-radio-group>
                  </el-form-item>
                </el-form>

              </div>
              <div style="flex: 1;">
                <el-form :model="group">
                  <el-form-item label="该组题目要求">
                    <el-input type="textarea" v-model="group.requirement" :disabled="!editable"></el-input>
                  </el-form-item>
                  <el-form-item>
                    <el-button type="danger" size="small" icon="el-icon-delete" @click="deleteDivision(index)" :disabled="!editable">删除改组别</el-button>
                  </el-form-item>
                </el-form>
              </div>
            </div>

            <div style=" text-align: center;">
                <el-button type="primary" @click="addGroup" :disabled="!editable">添加组别</el-button>
                <el-button type="buttonType" @click="handleEdit1">{{ buttonText }}</el-button>
            </div>
          </div>

      </el-tab-pane>


      <!--参赛人员信息页面-->
      <el-tab-pane label="参赛人员信息" name="second">
          <el-button type="primary" @click="goBack">返回</el-button>
          <div style="display: flex; align-items: center; padding: 10px; margin: 10px; margin-bottom: -5px; margin-top: 5px;">
          <!-- 输入框 -->
          <el-input style="width: 300px;" 
                  suffix-icon="el-icon-search" 
                  placeholder="请输入"
                  v-model="inputVal1"
                  @keyup.enter.native="getParticipant"
                  clearable>
          </el-input>
          <el-button style="margin-left: 20px;margin-right: 30px;" type="primary" @click="getParticipant">搜索</el-button>
          <!-- 显示学校名称 -->
          <h1>{{ competitionName }}</h1> 
          <el-select v-model="selectedGrade1" filterable placeholder="请选择年级" @change="getParticipant" clearable style="width: 150px;margin-right: 20px;margin-left: 30px;">
            <el-option
            v-for="item in gradeOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id">
            </el-option>
          </el-select>
          <el-select v-model="selectedStatus1" filterable placeholder="请选择作品状态" @change="getParticipant" clearable style="width: 150px;margin-right: 20px;">
            <el-option
            v-for="item in statusOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
            </el-option>
          </el-select>
      </div>    

      <el-table
          ref="studentTable"
          :data="studentTableData"
          stripe
          tooltip-effect="dark"
          style="width: 100%">
          <el-table-column
              prop="id"
              label="学生编号"
              width="105">
          </el-table-column>
          <el-table-column
              prop="name"
              label="学生姓名"
              width="150">
          </el-table-column>
          <el-table-column
              prop="number"
              label="学籍号"
              width="190">
          </el-table-column>
          <el-table-column
              prop="grade"
              label="年级"
              width="200">
          </el-table-column>
          <el-table-column
              prop="submissionId"
              label="作品提交状态"
              width="120">
          </el-table-column>
          
          <el-table-column fixed="right" label="操作">                         
          <template slot-scope="scope">
              <el-button type="success" size="small" icon="el-icon-edit"  @click="getDetailedSubmission1(scope.row)">查看作品详情</el-button>
              <el-button type="danger" size="small"  icon="el-icon-delete" @click="deleteStudent(scope.row)">删除</el-button>
          </template>
          </el-table-column> 

      </el-table>

      <!-- 分页栏-->
      <div style="padding:10px">
        <el-pagination
          @size-change="handleSizeChange1"
          @current-change="handleCurrentChange1"
          :current-page="pageNum1"
          :page-sizes="[20, 25, 30, 40]"
          :page-size="pageSize1"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total1">
        </el-pagination>
      </div>


      </el-tab-pane>


      <!--参赛作品信息页面-->
      <el-tab-pane label="参赛作品信息" name="third">
          <el-button type="primary" @click="goBack">返回</el-button>
          <div style="display: flex; align-items: center; padding: 10px; margin: 10px; margin-bottom: -5px; margin-top: 5px;">
          <!-- 输入框 -->
          <el-input style="width: 300px;" 
                  suffix-icon="el-icon-search" 
                  placeholder="请输入"
                  v-model="inputVal2"
                  @keyup.enter.native="getSubmissions"
                  clearable>
          </el-input>
          <el-button style="margin-left: 20px;margin-right: 30px;" type="primary" @click="getSubmissions">搜索</el-button>
          <!-- 显示竞赛名称 -->
          <h1>{{ competitionName}}</h1> 
          <el-select v-model="selectedGrade2" filterable placeholder="请选择年级" @change="getSubmissions" clearable style="width: 150px;margin-right: 20px; ;margin-left: 30px;">
            <el-option
              v-for="item in gradeOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
          <el-select v-model="selectedLevel2" filterable placeholder="请选择等级" @change="getSubmissions" clearable style="width: 150px;margin-left:20px;">
              <el-option
              v-for="item in levelOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
              </el-option>
          </el-select>
      </div>    

      <el-table
          ref="workTable"
          :data="workTableData" 
          stripe
          tooltip-effect="dark"
          style="width: 100%" >
          <el-table-column
              prop="id"
              label="作品编号"
              width="105">
          </el-table-column>
          <el-table-column
              prop="name"
              label="作品名称"
              width="150">
          </el-table-column>
          <el-table-column
              prop="student"
              label="学生姓名"
              width="190">
          </el-table-column>
          <el-table-column
              prop="systemScore"
              label="作品初评分数"
              width="150">
          </el-table-column>
          <el-table-column
              prop="level"
              label="作品等级"
              width="200">
          </el-table-column>
          
          <el-table-column fixed="right" label="操作">                         
          <template slot-scope="scope">
              <el-button type="success" size="small" icon="el-icon-edit"  @click="getDetailedSubmission2(scope.row)">查看作品详情</el-button>
          </template>
          </el-table-column> 

      </el-table>

      <!-- 分页栏-->
      <div style="padding:10px">
        <el-pagination
          @size-change="handleSizeChange2"
          @current-change="handleCurrentChange2"
          :current-page="pageNum2"
          :page-sizes="[20, 25, 30, 40]"
          :page-size="pageSize2"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total2">
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
              <el-form-item label="最终评语：">
                  <el-input type="textarea" v-model="submissionForm.initialEvaluation"></el-input>
              </el-form-item>
              <el-form-item label="作品等级：">
                  <el-input v-model="submissionForm.level"></el-input>
              </el-form-item>
              <el-form-item label="最终均分：">
                  <el-input v-model="submissionForm.averageFinalScore"></el-input>
              </el-form-item>
              <el-form-item label="初级排名：">
                  <el-input v-model="submissionForm.initialRank"></el-input>
              </el-form-item>
              <el-form-item label="最终评分排名：">
                  <el-input v-model="submissionForm.finalRank"></el-input>
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
import WangEditor from '@/components/wangEditor.vue';

export default {
  components:{
    WangEditor,
  },
  data() {
    return {
      activeName: 'first',
      valueHtml:'',
      pageNum1: 1,
      pageSize1: 10,
      total1: 0,
      pageNum2: 1,
      pageSize2: 10,
      total2: 0,
      inputVal1:'',
      inputVal2:'',
      editable: false, // 初始设置为不可编辑,
      competition:{},
      competitionName:'',
      //选择器的内容
      selectedGrade1:'',
      selectedGrade2:'',
      selectedStatus1:'',
      selectedLevel2:'',
      submissionForm:{},
      form: {
        id:'',
        name: '',
        registrationStartTime: '',
        registrationEndTime: '',
        competitionStartTime: '',
        competitionEndTime: '',
        reviewStartTime: '',
        reviewEndTime: '',
        groups: []
      },
      buttonType: 'warning', // 按钮类型
      buttonText: '修改', // 按钮文本
      competitionId:null,
      studentTableData: [],
      workTableData: [],
      studentForm:{},
      dialogVisible: false,
      levelOptions: [{
        value: '特等奖',
        label: '特等奖'
        },{
        value: '一等奖',
        label: '一等奖'
        }, {
        value: '二等奖',
        label: '二等奖'
        }, {
        value: '三等奖',
        label: '三等奖'
        }],
      statusOptions: [{
        value: 1,
        label: '已提交'
        },{
        value: 0,
        label: '未提交'
        },],
      gradeOptions: [],
    };
  },
  methods: {

    //竞赛信息的修改按钮
    handleEdit1(){
        this.editable = !this.editable; // 切换编辑状态
        if (this.editable) {
          this.buttonType = 'success'; // 设置按钮为绿色
          this.buttonText = '确定'; // 设置按钮文本为“确定”
        } else {
          this.buttonType = 'warning'; // 设置按钮为黄色
          this.buttonText = '修改'; // 设置按钮文本为“修改”
          this.updateCompetition();
        }
    },
    updateCompetition(){
      this.form.requirement=this.$refs.myEditor.valueHtml;
      this.request.put("/competition/update",this.form).then(res=>{
        if(res.code=='200'){
          this.$message.success('编辑数据成功！');
        }else{
          this.$message.error('编辑数据数据失败，原因：'+res.msg);
        }
      })
    },





    //返回按钮
    goBack() {
        this.$router.go(-1); 
        this.$destroy();
    },

    getDetailedSubmission1(row){
      this.request.get("/competition/submission-detail-student",{
        params:{
          studentId:row.id,
          competitionId:this.competitionId
        }
      }).then(res=>{
        if(res.code=='200'){
          this.submissionForm=res.data;
          this.dialogVisible = true;
        }else{
          this.$message.error('获取数据失败，原因：'+res.msg);
        }
      })
      
    },
    getDetailedSubmission2(row){
      this.request.get("/competition/submission-detail-submission",{
        params:{
          submissionId:row.id
        }
      }).then(res=>{
        if(res.code=='200'){
          this.submissionForm=res.data;
          this.dialogVisible = true;
        }else{
          this.$message.error('获取数据失败，原因：'+res.msg);
        }
      })
      
    },
    //根据竞赛id获取竞赛信息
    getCompetition(val){
      this.request.get("/competition/detailed-competition",{
        params:{
          competitionId:val
        }
      }).then(res=>{
        if(res.code=='200'){
          this.competition=res.data;
          this.form = res.data;
          this.$refs.myEditor.setHtml(this.form.requirement);
          this.competitionName = this.form.name;
        }else{
          this.$message.error('获取全部用户数据失败，原因：'+res.msg);
        }
      })
    },
    //分页用的功能
    handleCurrentChange1(val) {
      this.pageNum1 = val;   //获取当前第几页
      this.getParticipant();
    },
    handleSizeChange1(val) {
      this.pageSize1 = val;  //获取当前每页显示条数
      this.getParticipant();
    },
    //分页用的功能
    handleCurrentChange2(val) {
      this.pageNum2 = val;   //获取当前第几页
      this.getSubmissions();
    },
    handleSizeChange2(val) {
      this.pageSize2 = val;  //获取当前每页显示条数
      this.getSubmissions();
    },
    //获取参赛人员信息
    getParticipant(){
      this.request.get("/competition/participant",{
        params:{
          pageNum:this.pageNum1,
          pageSize:this.pageSize1,
          str:this.inputVal1,
          competitionId:this.competitionId,
          status:this.selectedStatus1,
          gradeId:this.selectedGrade1
        }
      }).then(res=>{
        if(res.code=='200'){
          console.log(res);
          this.studentTableData = res.data.records.map(record => {
            return {
              ...record,
              submissionId: record.status === 0 ? '未提交' : '已提交'
            };
          });
          this.total1=res.data.total;
        }else{
          this.$message.error('获取全部用户数据失败，原因：'+res.msg);
        }
      })
    },
    //获取竞赛作品信息
    getSubmissions(){
      this.request.get("/competition/submissions",{
        params:{
          pageNum:this.pageNum2,
          pageSize:this.pageSize2,
          str:this.inputVal2,
          competitionId:this.competitionId,
          gradeId:this.selectedGrade2,
          level:this.selectedLevel2
        }
      }).then(res=>{
        if(res.code=='200'){
          console.log(res);
          this.workTableData=res.data.records;
          this.total2=res.data.total;
        }else{
          this.$message.error('获取全部数据失败，原因：'+res.msg);
        }
      })
    },
    //增加组别
    addGroup() {
      this.form.groups.push({
        name: '',
        specialPrizeRatio: '',
        firstPrizeRatio: '',
        secondPrizeRatio: '',
        thirdPrizeRatio: '',
        target: '',
        requirement: ''
      });
    },
    //删除组别
    deleteDivision(index){
      this.form.groups.splice(index, 1);
    },
    getGrades(){
      this.request.get("/grade/grades").then(res=>{
        if(res.code=='200'){
          this.gradeOptions=res.data;
        }else{
          this.$message.error('获取全部年级数据失败，原因：'+res.msg);
        }

      })
    },
  },
  created(){
    // 通过 this.$route.params 获取路由参数
    this.competitionId = this.$route.params.competition;
    this.getCompetition(this.competitionId);
    
    this.getParticipant(this.competitionId);
    this.getSubmissions(this.competitionId);
    this.getGrades();
  },
  
};
</script>





<template>
    <div>
        <h1>规则：</h1>
        <!--规则管理-->
        <div style="display: flex;">
          系统初筛分数：
          <el-input
              placeholder=""
              v-model="scoreInput"
              :disabled="!isEditable"
              style="width: 15%;margin-right: 40px;">
              <template slot="suffix">
                <span style="font-weight: bold; color: black;">分</span>
              </template>
          </el-input>
          进入终评人数比：
          <el-input
              placeholder=""
              v-model="rationInput"
              :disabled="!isEditable"
              style="width: 15%;margin-right: 40px;">
              <template slot="suffix">
                <span style="font-weight: bold; color: black;">%</span>
              </template>
          </el-input>
          优秀作品个数：
          <el-input
              placeholder=""
              v-model="numberInput"
              :disabled="!isEditable"
              style="width: 15%;margin-right: 40px;">
              <template slot="suffix">
                <span style="font-weight: bold; color: black;">个</span>
              </template>
          </el-input>
          <el-button 
            :type="isEditable ? 'success' : 'warning'"
            @click="ruleEdit">
            {{ isEditable ? '确定' : '修改' }}
          </el-button>
        </div>

        <h1 style="margin-right: auto;">进行中的竞赛组别列表：</h1>
        <!--下面是竞赛组别列表-->
        <el-table
          ref="competitionTable"
          :data="ingDivisionTableData" 
          stripe
          tooltip-effect="dark"
          style="width: 100%" >
          <el-table-column
            prop="id"
            label="组别编号"
            width="65">
          </el-table-column>
          <el-table-column
            prop="competitionName"
            label="竞赛名"
            width="175">
          </el-table-column>
          <el-table-column
            prop="name"
            label="组别"
            width="130">
          </el-table-column>
          <el-table-column
            prop="state"
            label="竞赛状态"
            width="85">
          </el-table-column>
          <el-table-column
            prop="submissionNumber"
            label="竞赛作品数"
            width="80">
          </el-table-column>
          <el-table-column
            prop="teacherNumber"
            label="参与评阅的教师数量"
            width="80">
          </el-table-column>
          <el-table-column
            prop="reviewedNumber"
            label="初级评阅已评作品数"
            width="80">
          </el-table-column>
          
          <el-table-column fixed="right" label="操作">                         
            <template slot-scope="scope">
              <el-button type="warning" size="small" icon="el-icon-edit" @click="urgeDivision(scope.row)">催促</el-button>
              <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit1(scope.row)">详情</el-button>
              <el-button type="primary" size="small" icon="el-icon-edit"  @click="becomeFinal(scope.row)">进入最终评阅</el-button>
              <el-button type="danger" size="small" icon="el-icon-edit"  @click="endFinal(scope.row)">结束最终评阅</el-button>
            </template>
          </el-table-column> 

        </el-table>

      

      <h1 style="margin-top:30px">所有参与评阅的教师：</h1>
      <div style="display: flex; align-items: center; padding: 10px; margin: 10px; margin-bottom: -5px; margin-top: 5px;">
        
        <el-input style="width: 300px;" 
          suffix-icon="el-icon-search" 
          placeholder="请输入"
          v-model="inputVal"
          @keyup.enter.native="getIngTeacher"
          clearable>
        </el-input>
        <el-button style="margin-left: 20px;margin-right: 30px;" type="primary" @click="getIngTeacher">搜索</el-button>
        <el-button type="primary" @click="addTeacher" style="margin-left: auto; margin-right: 120px;" >新增教师<i class="el-icon-circle-plus"></i></el-button>
      </div>

      <!--下面是参与评阅教师的表格-->
      <el-table
        ref="teacherTable"
        :data="teacherTableData" 
        stripe
        tooltip-effect="dark"
        style="width: 100%" >
        <el-table-column
          prop="id"
          label="教师id"
          width="105">
        </el-table-column>
        <el-table-column
          prop="name"
          label="教师姓名"
          width="145">
        </el-table-column>
        <el-table-column
          prop="school"
          label="学校"
          width="230">
        </el-table-column>
        <el-table-column
          prop="finishReviewed"
          label="初级评阅完成份数"
          width="150">
        </el-table-column>
        
        <el-table-column fixed="right" label="操作">                         
          <template slot-scope="scope">
            <el-button type="warning" size="small" icon="el-icon-edit" @click="urgeTeacher(scope.row)">催促</el-button>
            <el-button type="danger" size="small"  icon="el-icon-delete" @click="deleteTeacherAll(scope.row)">删除</el-button>
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


      <!-- 新增教师的弹窗 -->
      <el-dialog
        title="选择将要被添加的教师"
        :visible.sync="dialogVisible1"
        width="80%"
        :close-on-click-modal="false">
        <div style="display: flex; align-items: center; padding: 10px; margin: 10px; margin-bottom: -5px; margin-top: 5px;">
            <el-input style="width: 300px;" 
                    suffix-icon="el-icon-search" 
                    placeholder="请输入教师名或学校名"
                    v-model="teacherInputVal"
                    @keyup.enter.native="getAllTeacher"
                    clearable>
            </el-input>
            <el-button style="margin-left: 20px;margin-right: 30px;" type="primary " @click="getAllTeacher">搜索</el-button>
        </div>
        
        <div style="display: flex;">
          <div>
            <h1>将要被添加的竞赛：</h1>
            <el-table
            ref="addTable1"
            :data="divisionAddTableData" 
            stripe
            tooltip-effect="dark"
            style="width: 100%" 
            @selection-change="divisionHandleSelectionChange">
              <el-table-column
                type="selection"
                width="55">
              </el-table-column>
              <el-table-column
                prop="competitionName"
                label="竞赛名"
                width="130">
              </el-table-column>
              <el-table-column
                prop="name"
                label="组别"
                width="130">
              </el-table-column>
              <el-table-column
                prop="state"
                label="竞赛状态"
                width="185">
              </el-table-column>
              
            </el-table>
            
          </div>
          
          <div>
            <h1>将要被添加的教师：</h1>
            <el-table
            ref="addTable2"
            :data="teacherAddTableData" 
            stripe
            tooltip-effect="dark"
            style="width: 100%" 
            @selection-change="teacherHandleSelectionChange">
              <el-table-column
                type="selection"
                width="55">
              </el-table-column>
              <el-table-column
                prop="id"
                label="教师编号"
                width="130">
              </el-table-column>
              <el-table-column
                prop="name"
                label="姓名"
                width="130">
              </el-table-column>
              <el-table-column
                prop="school"
                label="学校"
                width="185">
              </el-table-column>
              
            </el-table>
            <!-- 分页栏-->
            <div style="padding:10px">
              <el-pagination
                @size-change="teacherSizeChange"
                @current-change="teacherCurrentChange"
                :current-page="teacherPageNum"
                :page-sizes="[10, 15, 20, 40]"
                :page-size="teacherPageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="teacherTotal">
              </el-pagination>
            </div>

          </div>
        
        </div>
        

        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible1 = false">取消</el-button>
            <el-button type="primary" @click="submitAdd">确定</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
</template>
<script>
export default {
  data(){
    return {
      scoreInput:'',
      rationInput:'',
      numberInput:'',
      isEditable: false,
      teacherPageSize:6,
      teacherPageNum:1,
      teacherTotal:0,
      total:0,
      pageNum:1,
      pageSize:6,
      ingDivisionTableData:[],
      teacherAddTableData:[],
      divisionAddTableData:[],
      //用来获取选中的id
      selectedDivisionIds: [],
      selectedTeacherIds: [],  
      selectedTeachers: new Set(), 
      teacherInputVal:'',
      inputVal:'',
      teacherTableData:[],
      dialogVisible1:false,
      
    }   

  },
  created(){
    this.getRules();
    this.getIngDivision();
    this.getIngTeacher();
    
  },
  methods:{
    //修改规则
    ruleEdit() {
      if(this.isEditable==true){
        this.request.put("/review-management/update-rules",{
          score: this.scoreInput,
          ration: this.rationInput,
          number:this.numberInput
        }).then(res=>{
          if(res.code=='200'){
            this.$message.success('编辑数据成功！');
          }else{
            this.$message.error('编辑数据失败，原因：'+res.msg);
          }
        })
      }
      this.isEditable = !this.isEditable;
    },
    //获取规则数据，也就是页面最上面一行
    getRules(){
      this.request.get("/review-management/get-rules").then(res=>{
        if(res.code=='200'){
          this.scoreInput=res.data[0].answer;
          this.rationInput=res.data[1].answer;
          this.numberInput=res.data[2].answer;
        }else{
          this.$message.error('获取全部用户数据失败，原因：'+res.msg);
        }
      })
    },
    //获取进行中的竞赛列表
    getIngDivision(){
      this.request.get("/review-management/division-ing").then(res=>{
        if(res.code=='200'){
          this.ingDivisionTableData = res.data;
          this.divisionAddTableData = res.data;
        }else{
          this.$message.error('获取全部数据失败，原因：'+res.msg);
        }
      })
    },

    //组别详情的点击事件
    handleEdit1(row) {
      const division = row.id;

      // 跳转到竞赛组别详情页面
      this.$router.push(`reviewManagement/${division}`);
    },

    //新增教师弹窗打开
    addTeacher(){
      this.getIngDivision();
      this.getAllTeacher();
      this.selectedDivisionIds = [];
      this.selectedTeacherIds = [];
      this.selectedTeachers = new Set();
      this.dialogVisible1 = true;
    },
    //分页用的功能
    teacherCurrentChange(val) {
      this.teacherPageNum = val;   //获取当前第几页
      this.getAllTeacher();
    },
    teacherSizeChange(val) {
      this.teacherPageSize = val;  //获取当前每页显示条数
      this.getAllTeacher();
    },
    //新增评阅教师中的教师列表
    getAllTeacher(){
      this.request.get("/review-management/all-teacher",{
        params:{
          pageNum:this.teacherPageNum,
          pageSize:this.teacherPageSize,
          str:this.teacherInputVal,
        }
      }).then(res=>{
        if(res.code=='200'){
          this.teacherAddTableData = res.data.records;
          this.teacherTotal=res.data.total;
          this.$nextTick(() => {
            this.$refs.addTable2.clearSelection();
            this.teacherAddTableData.forEach(row => {
              if (this.selectedTeachers.has(row.id)) {
                this.$refs.addTable2.toggleRowSelection(row, true);
              }
            });
          });
        }else{
          this.$message.error('获取全部数据失败，原因：'+res.msg);
        }
      })
    },
    //分页用的功能
    handleCurrentChange(val) {
      this.pageNum = val;   //获取当前第几页
      this.getIngTeacher();
    },
    handleSizeChange(val) {
      this.pageSize = val;  //获取当前每页显示条数
      this.getIngTeacher();
    },
    //获取参预评阅的教师列表
    getIngTeacher(){
      this.request.get("/review-management/all-ing-teacher",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          str:this.inputVal,
        }
      }).then(res=>{
        if(res.code=='200'){
          this.teacherTableData = res.data.records;
          this.total=res.data.total;
        }else{
          this.$message.error('获取全部数据失败，原因：'+res.msg);
        }
      })
    },
    //催促教师
    urgeTeacher(row){
      this.request.put("/review-management/urge-teacher",{
        teacherId:row.id
      }).then(res=>{
        if(res.code=='200'){
          this.$message.success('催促成功！');
        }else{
          this.$message.error('催促失败，原因：'+res.msg);
        }
      })
    },

    //催促组别
    urgeDivision(row){
      this.request.put("/review-management/urge-division",{
        divisionId:row.id
      }).then(res=>{
        if(res.code=='200'){
          this.$message.success('催促成功！');
        }else{
          this.$message.error('催促失败，原因：'+res.msg);
        }
      })
    },
    //删除一个教师的所有批改组别记录
    deleteTeacherAll(row){
      // 在此处处理删除逻辑
			this.$confirm('确认删除该记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 调用后端删除接口
        this.request({
          url: '/review-management/delete-teacher-all-division',
          method: 'delete',
          data: {
            id: row.id
          }
        }).then(res => {
          if(res.code == '200'){
            this.$message.success('删除数据成功！');
            this.getIngTeacher();
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

    //教师详情
    handleEdit2(row){
      // 获取竞赛名和组别
      const teacherId = row.id;
      // 跳转到竞赛组别详情页面
      this.$router.push(`reviewManagement/teacher/${teacherId}`);
    },

    //增加教师的确定，即确定添加教师
    submitAdd(){
      const payload = {
        divisionIds: Array.from(this.selectedDivisionIds),
        teacherIds: Array.from(this.selectedTeachers)
      };
      this.request.post("/review-management/add-teacher",payload).then(res=>{
        if(res.code == '200'){
          this.$message.success('新增教师成功！');
          this.dialogVisible1=false;
          this.getIngTeacher();
        } else {
          this.$message.error('新增用户数据失败，原因：' + res.msg);
        }
      })
    },
    //进入最终评阅阶段
    becomeFinal(row){
      this.request.put("/review-management/become-final",{
        divisionId:row.id
      }).then(res=>{
        if(res.code=='200'){
          this.$message.success('此组别已进入评阅阶段！');
        }else{
          this.$message.error('变化失败，原因：'+res.msg);
        }
      })
    },
    //结束最终评阅阶段，进入已结束阶段
    endFinal(row){
      this.request.put("/review-management/end-final",{
        divisionId:row.id
      }).then(res=>{
        if(res.code=='200'){
          this.$message.success('此组别已结束评阅阶段，处于已结束阶段！');
        }else{
          this.$message.error('变化失败，原因：'+res.msg);
        }
      })
    },


    //下面几个是用来实现左右多选组别和教师以达到添加教师到对应的组别里的
    divisionHandleSelectionChange(selectedRows) {
      this.selectedDivisionIds = selectedRows.map(row => row.id);
    },
    teacherHandleSelectionChange(selectedRows) {
      selectedRows.forEach(row => this.selectedTeachers.add(row.id));
    },
    
  },
}
</script>

<style scoped>

</style>

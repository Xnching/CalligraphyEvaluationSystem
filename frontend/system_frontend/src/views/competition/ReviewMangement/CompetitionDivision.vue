<template>
    <div>
      
      <div style="display:flex; align-items: center; padding: 10px; margin: 10px; margin-bottom: -5px; margin-top: 5px;">
        <el-button type="primary" @click="goBack">返回</el-button>
        <h1 style="margin-top:10px;margin-left: 380px;">{{ competitionDivision }}</h1>
      </div>
      
      <div style="display: flex; align-items: center; padding: 10px; margin: 10px; margin-bottom: -5px; margin-top: 5px;">
        <h1 style="margin-top:10px">参与该竞赛评阅的教师：</h1>
        <el-input style="width: 300px;" 
                  suffix-icon="el-icon-search" 
                  placeholder="请输入"
                  v-model="inputVal"
                  @keyup.enter.native="load"
                  clearable>
        </el-input>
        <el-button style="margin-left: 20px;margin-right: 30px;" type="primary" @click="load">搜索</el-button>
        <el-button type="primary"  style="margin-left: auto; margin-right: 120px;" @click="handleEdit1">新增教师<i class="el-icon-circle-plus"></i></el-button>
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
          label="教师编号"
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
              <el-button type="success" size="small" icon="el-icon-share"  @click="handleEdit2(scope.row)">详情</el-button>
              <el-button type="danger" size="small"  icon="el-icon-delete" @click="deleteTeacher(scope.row)">删除</el-button>
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


      <!-- 新增教师的弹窗 -->
      <el-dialog
        title="选择将要被添加的教师"
        :visible.sync="dialogVisible"
        width="40%"
        :close-on-click-modal="false">
        <div style="display: flex; align-items: center; padding: 10px; margin: 10px; margin-bottom: -5px; margin-top: 5px;">
            <el-input style="width: 300px;" 
              suffix-icon="el-icon-search" 
              placeholder="请输入"
              v-model="teacherInputVal"
              @keyup.enter.native="getAllTeacher"
              clearable>
            </el-input>
            <el-button style="margin-left: 20px;margin-right: 30px;" type="primary" @click="getAllTeacher">搜索</el-button>
        </div>
        
        <el-table
        ref="addTable"
        :data="addTableData" 
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

        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
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
      divisionId:'',
      pageNum: 1,
      pageSize: 20,
      total: 0,
      teacherPageSize:6,
      teacherPageNum:1,
      teacherTotal:0,
      teacherTableData: [],
      addTableData: [],
      teacherInputVal:'',
      inputVal:'',
      selectedTeacherIds: [],  
      selectedTeachers: new Set(), 
      competitionDivision:null,
      dialogVisible:false,
    }   

  },
  methods:{
    //新增教师按钮跳出弹窗
    handleEdit1(){
      this.selectedTeacherIds = [];
      this.selectedTeachers = new Set();
      this.getAllTeacher();
      this.dialogVisible = true;
    },


    //点击详情按钮跳转页面
    handleEdit2(row) {
      // 获取竞赛名和组别
      const teacherId = row.id;
      const teacherIdAndDivisionId = this.divisionId+"-"+teacherId;
      // 跳转到竞赛组别详情页面
      this.$router.push(`/backend/reviewManagement/teacher/${teacherIdAndDivisionId}`);
    },

    //返回按钮
    goBack() {
      this.$router.go(-1); 
      this.$destroy();
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
    //分页用的功能
    teacherCurrentChange(val) {
      this.teacherPageNum = val;   //获取当前第几页
      this.getAllTeacher();
    },
    teacherSizeChange(val) {
      this.teacherPageSize = val;  //获取当前每页显示条数
      this.getAllTeacher();
    },
    //获取所有教师用于往组别内添加教师
    getAllTeacher(){
      this.request.get("/review-management/all-teacher-in-division",{
        params:{
          pageNum:this.teacherPageNum,
          pageSize:this.teacherPageSize,
          str:this.teacherInputVal,
          divisionId:this.divisionId
        }
      }).then(res=>{
        if(res.code=='200'){
          this.addTableData = res.data.records;
          this.teacherTotal=res.data.total;
          this.$nextTick(() => {
            if (this.$refs.addTable) {
              this.$refs.addTable.clearSelection();
              this.addTableData.forEach(row => {
                if (this.selectedTeachers.has(row.id)) {
                  this.$refs.addTable.toggleRowSelection(row, true);
                }
              });
            }
          });
        }else{
          this.$message.error('获取全部数据失败，原因：'+res.msg);
        }
      })
    },
    //用于复原多选的
    teacherHandleSelectionChange(selectedRows) {
      selectedRows.forEach(row => this.selectedTeachers.add(row.id));
    },
    //提交新增
    submitAdd(){
      const payload = {
        divisionIds: [this.divisionId], // 将 divisionId 改成数组，并将 this.divisionId 作为第一个元素
        teacherIds: Array.from(this.selectedTeachers)
      };
      this.request.post("/review-management/add-teacher-in-division",payload).then(res=>{
        if(res.code == '200'){
          this.$message.success('新增教师成功！');
          this.dialogVisible=false;
          this.load();
        } else {
          this.$message.error('新增用户数据失败，原因：' + res.msg);
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
      this.request.get("/review-management/teacher-in-division",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          str:this.inputVal,
          divisionId:this.divisionId,
        }
      }).then(res=>{
        if(res.code=='200'){
          this.teacherTableData=res.data.records;
          this.total=res.data.total;
        }else{
          this.$message.error('获取全部用户数据失败，原因：'+res.msg);
        }
      })
    },
    //催促
    urgeTeacher(){
      this.request.put("/review-management/urge-teacher-in-division",{
        teacherId:row.id,
        divisionId:this.divisionId
      }).then(res=>{
        if(res.code=='200'){
          this.$message.success('催促成功！');
        }else{
          this.$message.error('催促失败，原因：'+res.msg);
        }
      })
    },
    //将一个教师从评阅组中删除出去
    deleteTeacher(row){
      // 在此处处理删除逻辑
			this.$confirm('确认删除该记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 调用后端删除接口
        this.request({
          url: '/review-management/delete-teacher-in-division',
          method: 'delete',
          data: {
            divisionId:this.divisionId,
            teacherId: row.id
          }
        }).then(res => {
          if(res.code == '200'){
            this.$message.success('删除数据成功！');
            this.load()
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
    }
  },
  created(){
    // 通过 this.$route.params 获取路由参数
    this.divisionId = this.$route.params.competitionDivision;
    this.getDivision();
    this.load();
  },

}
</script>

<style scoped>

</style>

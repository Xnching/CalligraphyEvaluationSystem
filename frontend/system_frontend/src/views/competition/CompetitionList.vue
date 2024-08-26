<template>
  <div>
    <div v-if="!showForm">
      <div style="padding:10px; margin:10px; margin-bottom: -5px;">
        <el-input style="width:300px "
                  suffix-icon="el-icon-search"
                  placeholder="请输入"
                  v-model="inputVal"
                  @keyup.enter.native="Search_table()"
                  clearable>
        </el-input>
        <el-button style="margin-left:20px ;margin-right:535px" type="primary" @click="Search_table">搜索</el-button>
        <el-button type="primary" @click="showAdd">新增<i class="el-icon-circle-plus"></i></el-button>
      </div>

      <el-table
          ref="multipleTable"
          :data="tableData"
          stripe
          tooltip-effect="dark"
          style="width: 100%">
        <el-table-column
            prop="id"
            label="竞赛编号"
            width="95">
        </el-table-column>
        <el-table-column
            prop="name"
            label="名称"
            width="200">
        </el-table-column>
        <el-table-column
            prop="count"
            label="参赛人数"
            width="120">
        </el-table-column>
        <el-table-column
            prop="state"
            label="当前状态"
            width="120">
        </el-table-column>
        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button type="success" size="small" icon="el-icon-edit" @click="handleEdit1(scope.row)">详情</el-button>
            <el-button type="danger" size="small" icon="el-icon-delete" @click="deleteCompetition(scope.row)">删除</el-button>
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
    </div>

    <div v-else>
      <el-button type="primary" @click="showForm = false">返回</el-button>
      <div style="display: flex; flex-wrap: wrap; padding: 10px;">
        <div style="flex: 1; margin-right: 20px;">
          <el-form :model="form">
            <el-form-item label="竞赛名称">
              <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="开始报名时间">
              <el-date-picker v-model="form.registrationStartTime" type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
            </el-form-item>
            <el-form-item label="截止报名时间">
              <el-date-picker v-model="form.registrationEndTime" type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
            </el-form-item>
            <el-form-item label="开始竞赛时间">
              <el-date-picker v-model="form.competitionStartTime" type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
            </el-form-item>
            <el-form-item label="结束竞赛时间">
              <el-date-picker v-model="form.competitionEndTime" type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
            </el-form-item>
            <el-form-item label="开始评阅时间">
              <el-date-picker v-model="form.reviewStartTime" type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
            </el-form-item>
          </el-form>
        </div>
        <div style="flex: 1;">
          <el-form :model="form">
            <el-form-item label="竞赛要求">
              <el-input type="textarea" v-model="form.requirement"></el-input>
            </el-form-item>
            <el-form-item label="宣传图添加">
              <el-upload
                ref="image"
                class="upload-demo"
                drag
                action="https://jsonplaceho"
                :limit="1"
                accept=".png,.jpeg,.jpg"
                style=" text-align: center;"
                :on-success="handleExcelImportSuccess"
                :on-error="handleExcelImportError"
                :auto-upload="false">
                <i class="el-icon-plus"></i>
              </el-upload>
            </el-form-item>
          </el-form>
        </div>
      </div>

      <div style="padding: 10px;">
        <div v-for="(group, index) in form.groups" :key="index" style="display: flex; flex-wrap: wrap; margin-bottom: 10px;">
          <div style="flex: 1; margin-right: 20px;">
            <el-form :model="group">
              <el-form-item label="组名">
                <el-input v-model="group.name"></el-input>
              </el-form-item>
              <el-form-item label="特等奖比例">
                <el-input v-model="group.specialPrizeRatio" suffix="%"></el-input>
              </el-form-item>
              <el-form-item label="一等奖比例">
                <el-input v-model="group.firstPrizeRatio" suffix="%"></el-input>
              </el-form-item>
              <el-form-item label="二等奖比例">
                <el-input v-model="group.secondPrizeRatio" suffix="%"></el-input>
              </el-form-item>
              <el-form-item label="三等奖比例">
                <el-input v-model="group.thirdPrizeRatio" suffix="%"></el-input>
              </el-form-item>
              <el-form-item label="参赛对象">
                <el-radio-group v-model="group.target">
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
                <el-input type="textarea" v-model="group.requirement"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="danger" size="small" icon="el-icon-delete" @click="deleteDivision(index)">删除改组别</el-button>
              </el-form-item>
            </el-form>
          </div>
          
        </div>
        <div style=" text-align: center;">
          <el-button type="primary" @click="addGroup">添加组别</el-button>
        </div>
      </div>
      <el-button type="primary" size="small" icon="el-icon-delete" @click="handleAdd">确认添加竞赛</el-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showForm: false,
      inputVal: '',
      tableData: [],
      pageNum: 1,
      pageSize: 20,
      total: 0,
      form: {
        name: '',
        registrationStartTime: '',
        registrationEndTime: '',
        competitionStartTime: '',
        competitionEndTime: '',
        reviewStartTime: '',
        groups: []
      }

    };
  },
  created(){
    this.load();
  },
  methods: {
    
    Search_table() {
      this.load();
    },
    deleteDivision(index){
      this.form.groups.splice(index, 1);
    },
    deleteCompetition(row){
      // 在此处处理删除逻辑
			this.$confirm('确认删除该记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 调用后端删除接口
        this.request({
          url: '/competition/delete',
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
    //导入成功
    handleExcelImportSuccess(response, file, fileList){ 
      //console.log("到了导入成功里面去了");
      if(response.code=='200'){
        this.$message.success("导入成功");
        this.load();
      }else{
        this.$message.error("导入失败，原因为："+response.msg);
      }
    },
    //导入失败
    handleExcelImportError(err, file, fileList){ 
      //console.log(err);
      this.$message.error("导入失败，原因为"+err);
    },
    handleAdd(){
      let formData = new FormData();
      formData.append('file', this.$refs.image.uploadFiles[0].raw);
      formData.append('competition', JSON.stringify(this.form));
      this.request.post("/competition/add", formData, {
					headers: {
							'Content-Type': 'multipart/form-data'
					}
			}).then(res=>{
        if(res.code == '200'){
          this.$message.success('新增数据成功！');
          this.resetForm();
          this.showForm=false;
          this.load();
        } else {
          this.$message.error('新增数据失败，原因：' + res.msg);
        }
      })
    },

    handleEdit1(row){
      // 获取竞赛名和组别
      const competitionId = row.id;

      // 跳转到竞赛组别详情页面
      this.$router.push(`competitionList/${competitionId}`);
    },
    showAdd(){
      this.resetForm();
      this.showForm=true;
    },



    
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
    resetForm() {
      this.form = {
        id: '',
        name: '',
        registrationStartTime: '',
        registrationEndTime: '',
        competitionStartTime: '',
        competitionEndTime: '',
        reviewStartTime: '',
        reviewEndTime: '',
        requirement: '',
        groups: []
      };
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
      this.request.get("/competition/page",{
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
          this.$message.error('获取全部用户数据失败，原因：'+res.msg);
        }
      })
    },
  }
};
</script>

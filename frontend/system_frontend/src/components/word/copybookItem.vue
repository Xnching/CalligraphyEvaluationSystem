<script>

export default {
  data () {
    return {
      formModel: {
        name: '',
        difficulty: 0
      },
      pageNum:0,
      pageSize: 6,
      total:0,
      tableData: [],
      copybookItemId: 0,
      visible: false,
      copybookSource:'',
      copybookId:'',
    }
  },
  // mouted(){
  //   this.load();
  // },
  created(){
    this.load();
  },
  methods: {
    handleDetail (row) {
      console.log(row.id)
      this.copybookItemId = row.id
    },
    handleCurrentChange(val){
      this.pageNum = val;
      this.load();
    },
    handleSizeChange(val){
      this.pageSize=val;
      this.load();
    },
    load(){
      this.request.get("/copybook/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
        }
      }).then(res=>{
        if(res.code=='200'){
          this.tableData=res.data.records;
          this.total=res.data.total;
        }else{
          this.$message.error('获取全部部首失败，原因：'+res.msg);
        }
      })
    },
    handleShow(row){
      this.copybookSource = row.content;
      this.copybookId = row.id;
      console.log(this.copybookSource);
      console.log(this.copybookId);
    },
    //提交创建系统模板
    submitAdd(){
      const data = {
        name:this.formModel.name,
        difficulty:this.formModel.difficulty,
        copybookId: this.copybookId,
      };
      this.request.post("/system-template/copybook-add",data).then(res=>{
        if(res.code == '200'){
          this.$message.success('新增系统模板成功！');
          this.copybookSource = '';
          this.copybookId = '';
        } else {
          this.$message.error('新增系统模板失败，原因：' + res.msg);
        }
      })
    },

  }
}
</script>
<template>
  <div class="box">
    <div class="top">
      <el-input
        v-model="formModel.name"
        placeholder="请输入模板名称"
        style="width: 240px"
      ></el-input>
      难度：<el-rate v-model="formModel.difficulty" size="large" />
    </div>
    <div class="main">
      <div class="left">
        <el-table :data="tableData">
          <el-table-column label="字帖名称" prop="name"></el-table-column>
          <el-table-column label="作者" prop="author"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button type="primary"  @click="handleShow(scope.row)">展示该字贴</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div style="padding:10px">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="total">
          </el-pagination>
        </div>
      </div>
      <div class="right">
        <el-scrollbar height="70vh">
          <el-image
            style="width: 80%; height: 80%"
            :src="copybookSource">
          </el-image>
        </el-scrollbar>
      </div>
    </div>
    <el-button type="primary" @click="submitAdd()" style="margin-left:85%; margin-top: 50px;">确定生成</el-button>
  </div>
</template>

<style lang="scss" scoped>
.box {
  .top {
    display: flex;
    justify-content: space-around;
    align-items: center;
    margin-bottom: 20px;
  }
  .main {
    display: flex;
    justify-content: space-around;
    height: 75vh;
    .left {
      width: 45%;
    }
    .right {
      width: 45%;
      border: black 1px solid;
      padding: 5px;
    }
  }
}
</style>

<template>
<div>
  
  <div style="display: flex;">
      <el-container>
        <el-header>
          专项(部首)系统模板占比：
        </el-header> 
        <el-main>
          <el-progress type="circle" :percentage="formModel['专项(部首)']" ></el-progress>
        </el-main>
      </el-container> 
      <el-container>
        <el-header>
          专项(结构)系统模板占比：
        </el-header> 
        <el-main>
          <el-progress type="circle" :percentage="formModel['专项(结构)']" ></el-progress>
        </el-main>
      </el-container> 
      <el-container>
        <el-header>
          综合系统模板占比：
        </el-header> 
        <el-main>
          <el-progress type="circle" :percentage="formModel['综合']" ></el-progress>
        </el-main>
      </el-container> 
      <el-container>
        <el-header>
          字帖系统模板占比：
        </el-header> 
        <el-main>
          <el-progress type="circle" :percentage="formModel['字帖']" ></el-progress>
        </el-main>
      </el-container> 
    </div>

  <div style="display: flex;">
    <div style="margin-right: 100px;">
      <h1>依据年级统计：</h1>
      <el-table
        ref="competitionTable"
        :data="gradeTableData" 
        stripe
        tooltip-effect="dark"
        style="width: 100%" >
        <el-table-column
          prop="id"
          label="年级编号"
          width="105">
        </el-table-column>
        <el-table-column
          prop="name"
          label="年级名"
          width="155">
        </el-table-column>
        <el-table-column
          prop="copybookNumber"
          label="相关字帖数量"
          width="105">
        </el-table-column>

      </el-table>
    </div>
    

    <div>
      <h1>依据字体统计：</h1>
      <el-table
        ref="competitionTable"
        :data="fontTableData" 
        stripe
        tooltip-effect="dark"
        style="width: 100%" >
        <el-table-column
          prop="id"
          label="字体编号"
          width="105">
        </el-table-column>
        <el-table-column
          prop="name"
          label="字体名"
          width="205">
        </el-table-column>
        <el-table-column
          prop="templateNumber"
          label="相关模板数量"
          width="105">
        </el-table-column>
        <el-table-column
          prop="copybookNumber"
          label="相关字帖数量"
          width="105">
        </el-table-column>

      </el-table>
    </div>
    
  </div>
  
  
  
</div>
</template>
<script>
export default {
  data() {
    return {
      fontTableData:[],
      gradeTableData:[],
      formModel:{
        '专项(部首)': 0,
        '专项(结构)': 0,
        '综合': 0,
        '字帖': 0,
      },
    }
  },
  created(){
    this.getRadio();
    this.getFontGradeData();
  },
  methods: { 
    getRadio(){
      this.request.get("/analysis/radio-data").then(res=>{
        if (res.code == '200') {
          // 将字符串转换为数字
          Object.keys(res.data).forEach(key => {
            this.formModel[key] = Number(res.data[key]);
          });
        }else{
          this.$message.error('获取全部占比数据失败，原因：'+res.msg);
        }
      })
    },
    getFontGradeData(){
      this.request.get("/analysis/font-grade-data").then(res=>{
        if(res.code=='200'){
          this.fontTableData=res.data.fontData;
          this.gradeTableData = res.data.gradeData;
        }else{
          this.$message.error('获取全部数据失败，原因：'+res.msg);
        }
      })
    },
    
  },
}
</script>

<style scoped>

</style>

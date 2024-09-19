<template>
    <div>
      <h1>本日新增情况统计：</h1>
      <div style="display: flex;">
        本日学生新增量：
        <el-input
          placeholder=""
          v-model="addData.dayStudent"
          :disabled="!isEditable"
          style="width: 15%;margin-right: 40px;">
          <template slot="suffix">
            <span style="font-weight: bold; color: black;">人</span>
          </template>
        </el-input>
        本日教师新增量：
        <el-input
          placeholder=""
          v-model="addData.dayTeacher"
          :disabled="!isEditable"
          style="width: 15%;margin-right: 40px;">
          <template slot="suffix">
            <span style="font-weight: bold; color: black;">人</span>
          </template>
        </el-input>
        本日学校新增量：
        <el-input
          placeholder=""
          v-model="addData.daySchool"
          :disabled="!isEditable"
          style="width: 15%;margin-right: 40px;">
          <template slot="suffix">
            <span style="font-weight: bold; color: black;">人</span>
          </template>
        </el-input>
      </div>
      <h1>本月新增情况统计：</h1>
      <div style="display: flex;">
        本月学生新增量：
        <el-input
          placeholder=""
          v-model="addData.monthStudent"
          :disabled="!isEditable"
          style="width: 15%;margin-right: 40px;">
          <template slot="suffix">
            <span style="font-weight: bold; color: black;">人</span>
          </template>
        </el-input>
        本月教师新增量：
        <el-input
          placeholder=""
          v-model="addData.monthTeacher"
          :disabled="!isEditable"
          style="width: 15%;margin-right: 40px;">
          <template slot="suffix">
            <span style="font-weight: bold; color: black;">人</span>
          </template>
        </el-input>
        本月学校新增量：
        <el-input
          placeholder=""
          v-model="addData.monthSchool"
          :disabled="!isEditable"
          style="width: 15%;margin-right: 40px;">
          <template slot="suffix">
            <span style="font-weight: bold; color: black;">人</span>
          </template>
        </el-input>
      </div>

      <!--地区统计数据-->
      <el-table
        ref="competitionTable"
        :data="regionTableData" 
        stripe
        tooltip-effect="dark"
        style="width: 80%" >
        <el-table-column
          prop="id"
          label="省份编号"
          width="105">
        </el-table-column>
        <el-table-column
          prop="name"
          label="省份名"
          width="205">
        </el-table-column>
        <el-table-column
          prop="studentNumber"
          label="学生数量"
          width="205">
        </el-table-column>
        <el-table-column
          prop="teacherNumber"
          label="教师数量"
          width="205">
        </el-table-column>
        <el-table-column
          prop="schoolNumber"
          label="学校数量"
          width="205">
        </el-table-column>

      </el-table>
    </div>
</template>
<script>
  export default{
    data(){
      return{
        addData:{
          dayStudent:'',
          dayTeacher:'',
          daySchool:'',
          monthStudent:'',
          monthTeacher:'',
          monthSchool:'',
        },
        isEditable:false,
        regionTableData:[],
      }

    },
    created(){
      this.getAddData();
      this.getRegionTableData();
    },
    methods:{
      getAddData(){
        this.request.get("/analysis/add-data").then(res=>{
          if(res.code=='200'){
            this.addData=res.data;
          }else{
            this.$message.error('获取全部新增数据失败，原因：'+res.msg);
          }

        })
      },
      getRegionTableData(){ 
        this.request.get("/analysis/region-data").then(res=>{
          if(res.code=='200'){
            this.regionTableData=res.data;
          }else{
            this.$message.error('获取全部省份数据失败，原因：'+res.msg);
          }

        })
      },

    }


}
</script>


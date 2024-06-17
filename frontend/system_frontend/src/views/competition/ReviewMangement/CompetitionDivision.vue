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
                  @keyup.enter.native="Search_table()"
                  clearable>
        </el-input>
        <el-button style="margin-left: 20px;margin-right: 30px;" type="primary">搜索</el-button>
        <el-button type="primary" @click="handleEdit1" style="margin-left: auto; margin-right: 120px;">新增教师<i class="el-icon-circle-plus"></i></el-button>
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
          prop="workCount"
          label="分配份数"
          width="140">
        </el-table-column>
        <el-table-column
          prop="finishCount"
          label="完成份数"
          width="150">
        </el-table-column>
        
        <el-table-column fixed="right" label="操作">                         
          <template slot-scope="scope">
            <el-button type="warning" size="small" icon="el-icon-edit" >催促</el-button>
              <el-button type="success" size="small" icon="el-icon-share"  @click="handleEdit2(scope.row)">详情</el-button>
              <el-button type="danger" size="small"  icon="el-icon-delete">删除</el-button>
          </template>
        </el-table-column> 

      </el-table>

      <!-- 分页栏-->
      <div style="padding:10px">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage4"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="10"
          layout="total, sizes, prev, pager, next, jumper"
          :total="20">
        </el-pagination>
      </div>


      <!-- 新增教师的弹窗 -->
      <el-dialog
        title="选择将要被添加的教师"
        :visible.sync="dialogVisible1"
        width="40%"
        :close-on-click-modal="false">
        <div style="display: flex; align-items: center; padding: 10px; margin: 10px; margin-bottom: -5px; margin-top: 5px;">
            <el-input style="width: 300px;" 
                    suffix-icon="el-icon-search" 
                    placeholder="请输入"
                    v-model="inputVal"
                    @keyup.enter.native="Search_table()"
                    clearable>
            </el-input>
            <el-button style="margin-left: 20px;margin-right: 30px;" type="primary">搜索</el-button>
        </div>
        
        <el-table
        ref="addTable"
        :data="addTableData" 
        stripe
        tooltip-effect="dark"
        style="width: 100%" 
        @selection-change="handleSelectionChange">
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
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage4"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="10"
            layout="total, sizes, prev, pager, next, jumper"
            :total="20">
            </el-pagination>
        </div>

        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible1 = false">取消</el-button>
            <el-button type="primary" @click="handleSubmit1">确定</el-button>
          </span>
        </template>
      </el-dialog>

    </div>
</template>
<script>
export default {
    data(){
        return {
            conpetitionTableData: Array(4).fill().map(() => ({
                id: '111111',
                name: '少儿书法竞赛',
                division: 'A组',
                workCount: '560',
                teacherCount: '12',
                evaluatedCount: '340',
            })),
            teacherTableData: Array(8).fill().map(() => ({
                id: '111111',
                name: '王胜国',
                school:'盘城小学',
                finishCount: '1200',
                workCount:'965',
            })),
            addTableData: Array(8).fill().map(() => ({
                id: '111111',
                name: '王胜国',
                school:'盘城小学',
            })),
            competitionDivision:null,
            dialogVisible1:false,
            //多选用的
            multipleSelection: [],
        }   

    },
    methods:{
        toggleEditable() {
            this.isEditable = !this.isEditable;
        },
        //新增教师按钮跳出弹窗
        handleEdit1(){
        
        this.dialogVisible1 = true;
        },

        //新增用户表单提交前判断下数据格式是否正确
        handleSubmit1() {


        this.dialogVisible1 = false;  //关闭弹窗
        },

        //点击详情按钮跳转页面
        handleEdit2(row) {
          // 获取竞赛名和组别
          const teacherName = row.name;
          // 跳转到竞赛组别详情页面
          this.$router.push(`/reviewManagement/teacher/${teacherName}`);

        
        },
        //多选用的
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },

        //返回按钮
        goBack() {
            this.$router.go(-1); 
        }
    },
    mounted() {
    // 通过 this.$route.params 获取路由参数
    this.competitionDivision = this.$route.params.competitionDivision;
   }
}
</script>

<style scoped>

</style>

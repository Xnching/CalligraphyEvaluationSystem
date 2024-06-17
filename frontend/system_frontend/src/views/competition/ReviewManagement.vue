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
            </el-input>
            进入终评人数比：
            <el-input
                placeholder=""
                v-model="rationInput"
                :disabled="!isEditable"
                style="width: 15%;margin-right: 40px;">
            </el-input>
            优秀作品个数：
            <el-input
                placeholder=""
                v-model="numberInput"
                :disabled="!isEditable"
                style="width: 15%;margin-right: 40px;">
            </el-input>
            <el-button 
            :type="isEditable ? 'success' : 'warning'"
            @click="toggleEditable"
            >
            {{ isEditable ? '确定' : '修改' }}
            </el-button>
        </div>

        <h1 style="margin-right: auto;">进行中的竞赛组别列表：</h1>
        <!--下面是竞赛组别列表-->
        <el-table
        ref="competitionTable"
        :data="conpetitionTableData" 
        stripe
        tooltip-effect="dark"
        style="width: 100%" >
          <el-table-column
            prop="id"
            label="竞赛id"
            width="125">
          </el-table-column>
          <el-table-column
            prop="name"
            label="竞赛名"
            width="195">
          </el-table-column>
          <el-table-column
            prop="division"
            label="组别"
            width="210">
          </el-table-column>
          <el-table-column
            prop="workCount"
            label="竞赛作品数"
            width="140">
          </el-table-column>
          <el-table-column
            prop="teacherCount"
            label="参与评阅的教师数量"
            width="120">
          </el-table-column>
          <el-table-column
            prop="evaluatedCount"
            label="已评作品数"
            width="120">
          </el-table-column>
          
          <el-table-column fixed="right" label="操作">                         
            <template slot-scope="scope">
                <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit1(scope.row)">详情</el-button>
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
      

      <h1 style="margin-top:30px">所有参与评阅的教师：</h1>
      <div style="display: flex; align-items: center; padding: 10px; margin: 10px; margin-bottom: -5px; margin-top: 5px;">
        
        <el-input style="width: 300px;" 
                  suffix-icon="el-icon-search" 
                  placeholder="请输入"
                  v-model="inputVal"
                  @keyup.enter.native="Search_table()"
                  clearable>
        </el-input>
        <el-button style="margin-left: 20px;margin-right: 30px;" type="primary">搜索</el-button>
        <el-button type="primary" @click="handleEdit2" style="margin-left: auto; margin-right: 120px;">新增教师<i class="el-icon-circle-plus"></i></el-button>
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
              <el-button type="success" size="small" icon="el-icon-share"  @click="handleEdit3(scope.row)">详情</el-button>
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
        width="80%"
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
        
        <div style="display: flex;">
          <div>
            <h1>将要被添加的竞赛：</h1>
            <el-table
            ref="addTable1"
            :data="addTableData1" 
            stripe
            tooltip-effect="dark"
            style="width: 100%" 
            @selection-change="handleSelectionChange">
              <el-table-column
                type="selection"
                width="55">
              </el-table-column>
              <el-table-column
                prop="name"
                label="竞赛名"
                width="130">
              </el-table-column>
              <el-table-column
                prop="division"
                label="组别"
                width="130">
              </el-table-column>
              <el-table-column
                prop="status"
                label="竞赛状态"
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
          </div>
          
          <div>
            <h1>将要被添加的教师：</h1>
            <el-table
            ref="addTable2"
            :data="addTableData2" 
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

          </div>
        
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
            scoreInput:'60分',
            rationInput:'5%',
            numberInput:'8',
            isEditable: false,
            
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
            dialogVisible1:false,
            addTableData1: Array(8).fill().map(() => ({
                division: 'A组',
                name: '中国书法竞赛',
                status:'竞赛中',
            })),
            addTableData2: Array(8).fill().map(() => ({
                id: '111111',
                name: '王胜国',
                school:'盘城小学',
            })),

        }   

    },
    methods:{
        toggleEditable() {
            this.isEditable = !this.isEditable;
        },

        //组别详情的点击事件
        handleEdit1(row) {
          // 获取竞赛名和组别
          const competitionName = row.name;
          const division = row.division;

          // 生成竞赛组别名
          const competitionDivision = `${competitionName}-${division}`;

          // 跳转到竞赛组别详情页面
          this.$router.push(`reviewManagement/${competitionDivision}`);
        },

        //新增教师
        handleEdit2(){
          this.dialogVisible1 = true;
        },

        //教师详情
        handleEdit3(row){
          // 获取竞赛名和组别
          const teacherName = row.name;
          // 跳转到竞赛组别详情页面
          this.$router.push(`reviewManagement/teacher/${teacherName}`);
        },

        //增加教师的确定
        handleSubmit1(){
          this.dialogVisible1=false;

        },
    },
}
</script>

<style scoped>

</style>

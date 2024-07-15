<template>
    <div>
        <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
        <!--竞赛信息页面-->
        <el-tab-pane label="竞赛信息" name="first">
            <el-button type="primary" @click="goBack">返回</el-button>
            <div style="display: flex; flex-wrap: wrap; padding: 10px;">
                <div style="flex: 1; margin-right: 20px;">
                    <el-form :model="form">
                        <el-form-item label="竞赛编号">
                            <el-input v-model="form.competitionID" :disabled="!editable"></el-input>
                        </el-form-item>
                        <el-form-item label="竞赛名称">
                            <el-input v-model="competition" :disabled="!editable"></el-input>
                        </el-form-item>
                        <el-form-item label="开始报名时间">
                            <el-date-picker v-model="form.startSignupTime" type="datetime" :disabled="!editable"></el-date-picker>
                        </el-form-item>
                        <el-form-item label="截止报名时间">
                            <el-date-picker v-model="form.endSignupTime" type="datetime" :disabled="!editable"></el-date-picker>
                        </el-form-item>
                        <el-form-item label="开始竞赛时间">
                            <el-date-picker v-model="form.startCompetitionTime" type="datetime" :disabled="!editable"></el-date-picker>
                        </el-form-item>
                        <el-form-item label="结束竞赛时间">
                            <el-date-picker v-model="form.endCompetitionTime" type="datetime" :disabled="!editable"></el-date-picker>
                        </el-form-item>
                        <el-form-item label="开始评阅时间">
                            <el-date-picker v-model="form.startReviewTime" type="datetime" :disabled="!editable"></el-date-picker>
                        </el-form-item>
                        <el-form-item label="结束评阅时间">
                            <el-date-picker v-model="form.endReviewTime" type="datetime" :disabled="!editable"></el-date-picker>
                        </el-form-item>
                    </el-form>
                </div>
                <div style="flex: 1;">
                    <el-form :model="form">
                        <el-form-item label="竞赛要求" :disabled="!editable">
                            <el-input type="textarea" v-model="form.competitionRequirements"></el-input>
                        </el-form-item>
                        <el-form-item label="宣传图添加">
                            <el-upload
                                action="#"
                                list-type="picture-card"
                                :on-preview="handlePictureCardPreview"
                                :on-remove="handleRemove"
                                :limit="1"
                                accept=".jpg,.jpeg,.png">
                                <i class="el-icon-plus"></i>
                            </el-upload>
                        </el-form-item>
                    </el-form>
                </div>
            </div>

            <div style="padding: 10px;">
                <div style="display: flex; flex-wrap: wrap; margin-bottom: 10px;">
                    <div style="flex: 1; margin-right: 20px;">
                        <el-form :model="form">
                        <el-form-item label="组名">
                            <el-input v-model="form.groupName" :disabled="!editable"></el-input>
                        </el-form-item>
                        <el-form-item label="特等奖比例">
                            <el-input v-model="form.specialAwardRatio" suffix="%":disabled="!editable"></el-input>
                        </el-form-item>
                        <el-form-item label="一等奖比例">
                            <el-input v-model="form.firstAwardRatio" suffix="%" :disabled="!editable"></el-input>
                        </el-form-item>
                        <el-form-item label="二等奖比例">
                            <el-input v-model="form.secondAwardRatio" suffix="%" :disabled="!editable"></el-input>
                        </el-form-item>
                        <el-form-item label="三等奖比例">
                            <el-input v-model="form.thirdAwardRatio" suffix="%" :disabled="!editable"></el-input>
                        </el-form-item>
                        <el-form-item label="参赛对象">
                            <el-radio-group v-model="form.competitionObject" :disabled="!editable">
                            <el-radio label="小学">小学</el-radio>
                            <el-radio label="初中">初中</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        </el-form>
                    </div>
                    <div style="flex: 1;">
                        <el-form :model="form">
                        <el-form-item label="该组题目要求">
                            <el-input type="textarea" v-model="form.groupRequirements"></el-input>
                        </el-form-item>
                        </el-form>
                    </div>
                </div>
                <div style=" text-align: center;">
                    <el-button type="primary" @click="addGroup">添加组别</el-button>
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
                    v-model="inputVal"
                    @keyup.enter.native="Search_table()"
                    clearable>
            </el-input>
            <el-button style="margin-left: 20px;margin-right: 30px;" type="primary">搜索</el-button>
            <!-- 显示学校名称 -->
            <h1>{{ competition }}</h1> 
            <el-select v-model="selectedGrade" filterable placeholder="请选择年级 " style="width: 150px;margin-left:auto;margin-right: 170px;">
                <el-option
                v-for="item in gradeOptions"
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
                prop="status"
                label="作品提交状态"
                width="100">
            </el-table-column>
            
            <el-table-column fixed="right" label="操作">                         
            <template slot-scope="scope">
                <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit3(scope.row)">查看作品详情</el-button>
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


        </el-tab-pane>


        <!--参赛作品信息页面-->
        <el-tab-pane label="参赛作品信息" name="third">
            <el-button type="primary" @click="goBack">返回</el-button>
            <div style="display: flex; align-items: center; padding: 10px; margin: 10px; margin-bottom: -5px; margin-top: 5px;">
            <!-- 输入框 -->
            <el-input style="width: 300px;" 
                    suffix-icon="el-icon-search" 
                    placeholder="请输入"
                    v-model="inputVal"
                    @keyup.enter.native="Search_table()"
                    clearable>
            </el-input>
            <el-button style="margin-left: 20px;margin-right: 30px;" type="primary">搜索</el-button>
            <!-- 显示竞赛名称 -->
            <h1>{{ competition}}</h1> 
            <el-select v-model="selectedGrade" filterable placeholder="请选择年级 " style="width: 150px;margin-left:auto;">
                <el-option
                v-for="item in gradeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
                </el-option>
            </el-select>
            <el-select v-model="selectedLevel" filterable placeholder="请选择等级" style="width: 150px;margin-left:20px;">
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
                prop="studentName"
                label="学生姓名"
                width="190">
            </el-table-column>
            <el-table-column
                prop="score"
                label="作品分数"
                width="150">
            </el-table-column>
            <el-table-column
                prop="level"
                label="作品等级"
                width="200">
            </el-table-column>
            
            <el-table-column fixed="right" label="操作">                         
            <template slot-scope="scope">
                <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit4(scope.row)">查看作品详情</el-button>
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


        </el-tab-pane>
        </el-tabs>


        <!-- 参赛人员作品详情的弹窗 -->
        <el-dialog
            title="点击修改信息"
            :visible.sync="dialogVisible2"
            width="50%"
            :close-on-click-modal="false">
            <img src="/images/copybook/1.jpg" style="width: 100%;">
            <el-form ref="studentForm" :model="form" label-width="100px">
                <el-form-item label="系统评分：">
                    <el-input v-model="studentForm.systemScore"></el-input>
                </el-form-item>
                <el-form-item label="系统评语：">
                    <el-input type="textarea" v-model="studentForm.systemComment"></el-input>
                </el-form-item>
                <el-form-item label="教师评分：">
                    <el-input v-model="studentForm.teacherScore"></el-input>
                </el-form-item>
                <el-form-item label="教师评语：">
                    <el-input type="textarea" v-model="studentForm.teacherComment"></el-input>
                </el-form-item>
                <el-form-item label="作品等级：">
                    <el-input v-model="studentForm.level"></el-input>
                </el-form-item>
                <el-form-item label="最终均分：">
                    <el-input v-model="studentForm.finalScore"></el-input>
                </el-form-item>
                <el-form-item label="排名：">
                    <el-input v-model="studentForm.rank"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
            <span class="dialog-footer">
                <el-button type="primary" @click="handleSubmit1()">确定</el-button>
            </span>
            </template>
        </el-dialog>
    </div>
  </template>



  <script>
    export default {
      data() {
        return {
          activeName: 'first',
          editable: false, // 初始设置为不可编辑
          form:{
            competitionID: 'C001', // 竞赛编号
            startSignupTime: new Date('2024-07-01T00:00:00'), // 开始报名时间
            endSignupTime: new Date('2024-08-01T00:00:00'), // 截止报名时间
            startCompetitionTime: new Date('2024-09-01T00:00:00'), // 开始竞赛时间
            endCompetitionTime: new Date('2024-10-01T00:00:00'), // 结束竞赛时间
            startReviewTime: new Date('2024-11-01T00:00:00'), // 开始评阅时间
            endReviewTime: new Date('2024-12-01T00:00:00'), // 结束评阅时间
            competitionRequirements: '参赛者需提交一份A4纸大小的书法作品，内容不限。', // 竞赛要求
            groupName: '小学组', // 组名
            specialAwardRatio: '5', // 特等奖比例
            firstAwardRatio: '15', // 一等奖比例
            secondAwardRatio: '20', // 二等奖比例
            thirdAwardRatio: '30', // 三等奖比例
            competitionObject: '小学', // 参赛对象
            groupRequirements: '小学组的参赛者需提交一份关于《论语》的书法作品。' // 该组题目要求
          },
          buttonType: 'warning', // 按钮类型
          buttonText: '修改', // 按钮文本
          competition:null,
          studentTableData: Array(8).fill().map(() => ({
            id:'10016',
            number:'202283290333',
            name:'王超之',
            school:'盘城小学',
            grade:'六三制小学一年级',
            status:'已提交',
          })),
          workTableData: Array(8).fill().map(() => ({
            id:'100016',
            studentName:'王超之',
            name:'最美夏天',
            score:'96',
            level:'一等奖',
          })),
          studentForm:{
            systemComment:'1.作品字形端正，线条流畅，意境深远',
            systemScore:'99分',
            teacherComment:'1.作品字形端正，线条流畅，意境深远',
            teacherScore:'99分',
            level:'一等奖',
            finalScore:'96.4',
            rank:'12',
          },
          dialogVisible2: false,
          levelOptions: [{
            value: '选项1',
            label: '一等奖'
            }, {
            value: '选项2',
            label: '二等奖'
            }, {
            value: '选项3',
            label: '三等奖'
            }],
            gradeOptions: [{
            value: '选项1',
            label: '一年级'
            }, {
            value: '选项2',
            label: '二年级'
            }, {
            value: '选项3',
            label: '三年级'
            }],
            selectedLevel:'',
            selectedGrade:'',
        };
      },
      methods: {

        handleClick(tab, event) {
          
        },

        //竞赛信息的修改按钮
        handleEdit1(){
            this.editable = !this.editable; // 切换编辑状态
            if (this.editable) {
            this.buttonType = 'success'; // 设置按钮为绿色
            this.buttonText = '确定'; // 设置按钮文本为“确定”
            } else {
            this.buttonType = 'warning'; // 设置按钮为黄色
            this.buttonText = '修改'; // 设置按钮文本为“修改”
            }
        

        },


        //参赛人员信息的编辑按钮
        handleEdit3(){
            this.dialogVisible2 = true;
        },

        //参赛作品信息的编辑按钮
        handleEdit4(){
            this.dialogVisible2 = true;
        },

        //返回按钮
        goBack() {
            this.$router.go(-1); 
        },

        handleSubmit1(){
            this.dialogVisible2 = false;
        }
      },
      mounted() {
        // 通过 this.$route.params 获取路由参数
        this.competition = this.$route.params.competition;
        
      }
    };
  </script>




  
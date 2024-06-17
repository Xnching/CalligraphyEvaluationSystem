<template>
    <div>
      <div style="padding:10px; margin:10px; margin-bottom: -5px;">
        <el-input style="width:300px ;margin-left:-80px" 
        suffix-icon="el-icon-search" 
        placeholder="请输入"
        v-model="inputVal"
        @keyup.enter.native="Search_table()"
        clearable>
        </el-input>
        <el-button style="margin-left:20px ;margin-right:535px" type="primary">搜索</el-button>
        <el-button type="primary" @click="handleEdit1">新增模板<i class="el-icon-circle-plus"></i></el-button>
      </div>       

      <el-table
        ref="multipleTable"
        :data="tableData"
        stripe
        tooltip-effect="dark"
        style="width: 100%">
        <el-table-column
          prop="id"
          label="模板id"
          width="125">
        </el-table-column>
        <el-table-column
          prop="name"
          label="模板名称"
          width="215">
        </el-table-column>
        <el-table-column
          prop="type"
          label="模板类型"
          width="120">
        </el-table-column>
        <el-table-column
          prop="detailedType"
          label="详细类型"
          width="150">
        </el-table-column>
        <el-table-column
          prop="releasePerson"
          label="发布人"
          width="150">
        </el-table-column>
        <el-table-column
          prop="releaseTime"
          label="发布时间"
          width="150">
        </el-table-column>
        <el-table-column fixed="right" label="操作">                         
          <template slot-scope="scope">
              <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit2(scope.row)">编辑</el-button>
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

      <!-- 新增模板的弹窗 -->
      <el-dialog
        title="请输入新增模板的信息"
        :visible.sync="dialogVisible1"
        width="80%"
        :close-on-click-modal="false" >

        <el-tabs type="border-card" @tab-click="handleTabClick">
            <el-tab-pane label="专项练习（偏旁部首）">
              <earmarkedItem
                  v-if="templateType[1] === '偏旁'"
                  :templateType="templateType"
              ></earmarkedItem>
            </el-tab-pane>

            <el-tab-pane label="专项练习（结构）">
              <earmarkedItem
                  v-if="templateType[1] === '结构'"
                  :templateType="templateType"
              ></earmarkedItem>
            </el-tab-pane>

            <el-tab-pane label="综合练习">
              <comprehensiveItem
                  v-if="templateType[0] === '综合练习'"
                  :templateType="templateType"
                  :key="new Date().getTime()"
              ></comprehensiveItem>
            </el-tab-pane>

            <el-tab-pane label="字帖练习">
              <copybookItem
                  v-if="templateType[0] === '字帖练习'"
                  :templateType="templateType"
                  :key="new Date().getTime()"
              ></copybookItem>
            </el-tab-pane>

        </el-tabs>

        
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible1 = false">取消</el-button>
            <el-button type="primary" @click="handleSubmit1()">确定</el-button>
          </span>
        </template>

      </el-dialog>

      <!-- 编辑模板的弹窗 -->
      <el-dialog
        title="点击修改模板信息"
        :visible.sync="dialogVisible2"
        width="60%"
        :close-on-click-modal="false">
        <div class="top">
          <el-input
            v-model="formModel.name"
            placeholder="请输入模板名称"
            style="width: 240px"
          ></el-input>
          <el-select
            v-model="formModel.radical"
            size="large"
            style="width: 240px"
            :disabled="true" 
          >
            <el-option
              v-for="item in radicalOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          <el-select v-model="formModel.font" size="large" style="width: 240px">
            <el-option
              v-for="item in fontOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          <el-rate v-model="formModel.difficulty" size="large" />
          <img src="/images/copybook/1.jpg" style="width: 100%;">
        </div>



        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible2 = false">取消</el-button>
            <el-button type="primary" @click="handleSubmit2">确定</el-button>
          </span>
        </template>

      </el-dialog>

    
    </div>
</template>



<script>
import comprehensiveItem from '@/components/word/comprehensiveItem.vue'
import earmarkedItem from '@/components/word/earmarkedItem.vue'
import copybookItem from '@/components/word/copybookItem.vue'

export default {
  components:{
    earmarkedItem,
    comprehensiveItem,
    copybookItem
  },
  data() {
    return {
      //搜索栏要用的
      inputVal:"",
      tableData: Array(8).fill().map(() => ({
        id:"10000",
        name: "第一次测试模板",
        type:"专项",
        detailedType:"偏旁部首",
        releasePerson:"港衷傲",
        releaseTime:"2024-6-11",
      })),
      showTable:[],
      //初始隐藏两个表单
      dialogVisible1: false,
      dialogVisible2: false,
      templateType: [],
      formModel: {
        name: '楷书体模板',
        radical: '专项（结构）',
        font: '楷书',
        difficulty: 3
      },
      radicalOptions: [
        {
          value: '专项练习(偏旁部首)',
          label: '专项练习(偏旁部首)'
        },
        {
          value: '专项练习(结构)',
          label: '专项练习(结构)'
        },
        {
          value: '专项练习(字体)',
          label: '专项练习(字体)'
        }
      ],
      fontOptions: [
        {
          value: '楷书',
          label: '楷书'
        },
        {
          value: '行书',
          label: '行书'
        },
        {
          value: '草书',
          label: '草书'
        },
        {
          value: '隶书',
          label: '隶书'
        }
      ],
    };
  },
  watch: {
    //用于实现搜索栏搜索的
    inputVal(item1) {
      if (item1 == "") {
        this.tableData = this.showTable;
      }
    },
  },

  methods: {
    //实现搜索栏多属性搜索的
    Search_table() {
      const Search_List = [];
      let res1 = this.inputVal;
      const res = res1.replace(/\s/gi, "");
      let searchArr = this.showTable;
      searchArr.forEach((e) => {
        let id = e.id;
        let name= e.name;
        let address= e.address;
        let schoolType= e.schoolType;
        let leader= e.leader;
        let leaderPhone= e.leaderPhone;
        if (id.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (name.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (address.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (schoolType.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (leader.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (leaderPhone.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
      });
      this.tableData = Search_List;
    },

    //新增按钮跳出弹窗
    handleEdit1(){
      
      this.dialogVisible1 = true;
    },

    handleTabClick(tab) {
      // ...更新 templateType 的逻辑...
    },

    //新增用户表单提交前判断下数据格式是否正确
    handleTabClick(tab) {
      const label = tab.label;
      if (label === "专项练习（偏旁部首）") {
        this.templateType = ['专项练习', '偏旁'];
      } else if (label === "专项练习（结构）") {
        this.templateType = ['专项练习', '结构'];
      } else if (label === "综合练习") {
        this.templateType = ['综合练习'];
      } else if (label === "字帖练习") {
        this.templateType = ['字帖练习'];
      }
    },

    //点击编辑按钮跳出弹窗填充数据
    handleEdit2(row) {
      // 将当前行数据复制到 editForm 中，避免直接修改表格数据
      this.editForm = Object.assign({}, row); 
      this.dialogVisible2 = true;
    },

    //编辑用户弹窗确定提交的方法
    handleSubmit2() {
      // 在此处处理表格提交逻辑
      
      this.dialogVisible2 = false; // 关闭弹窗
    },
    
    

    //分页用的功能
    handleCurrentChange() {},
    handleSizeChange() {},
    currentPage4() {}
  },
  //把tableData数值赋给showTable
  created(){
    this.showTable = [...this.tableData];
  }
};
</script>

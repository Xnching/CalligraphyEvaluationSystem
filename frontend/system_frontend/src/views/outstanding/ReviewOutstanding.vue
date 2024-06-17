<template>

  <div>
    <el-dialog
      :visible.sync="dialogVisible"
      width="50%"
      @close="dialogVisible = false"
  >
    <div class="dialog-content">
      <!-- 左上：照片 -->
      <div class="dialog-left-top">
        <img src="/images/copybook/1.jpg" alt="作业图片" style="width: 100%; ">
      </div>
      <!-- 左下：文本框 -->
      <div class="dialog-left-bottom">
        <p>(系统自动评价，含评分和评价与当初教师评价的分数与评语)</p>
      </div>
      <!-- 右上：所属年级 + 优秀作品标准 -->
      <div class="dialog-right-top">
        <div>所属年级：<span>一年级</span></div>
        <div>优秀作品标准：</div>
        <div>
          <p>这里是优秀作品标准的描述...</p>
        </div>
      </div>
      <!-- 右下：通过/不通过按钮 -->
      <div class="dialog-right-bottom">
        <el-button type="primary" @click="handlePass">通过</el-button>
        <el-button @click="dialogVisible = false">不通过</el-button>
      </div>
    </div>
  </el-dialog>
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
    </div>

    <el-table
        ref="multipleTable"
        :data="tableData"
        stripe
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column
          prop="ID"
          label="作品ID"
          width="115">
      </el-table-column>
      <el-table-column
          prop="homeworkName"
          label="作业名称"
          width="120">
      </el-table-column>
      <el-table-column
          prop="name"
          label="作者"
          width="140">
      </el-table-column>
      <el-table-column
          prop="recommendTeacher"
          label="推荐教师"
          width="140">
      </el-table-column>
      <el-table-column
          prop="workType"
          label="作业类型"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit2(scope.row)">评审</el-button>
          <el-button type="danger" size="small"  icon="el-icon-delete">移出优秀作品</el-button>
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
  </div>
    
  </div>
  
</template>


<script>
export default {
  data() {
    return {
      //搜索栏要用的
      inputVal:"",
      tableData: Array(8).fill().map(() => ({
        ID:"3",
        homeworkName: "一年级第一次专项作业",
        name: "张三",
        recommendTeacher: "李四",
        workType: "综合作业",
        
      })),
      dialogVisible: false,
      showTable:[],
      multipleSelection: []
    };
  },
  watch: {
    inputVal(item1) {
      if (item1 == "") {
        this.tableData = this.showTable;
      }
    },
  },

  methods: {
    handleEdit2(row) {
      // 然后显示弹窗
      this.dialogVisible = true;
      // 如果需要，可以根据row设置弹窗中的其他内容
    },

    // 处理点击通过按钮
    handlePass() {
      // 这里添加处理通过按钮点击的逻辑
      // 比如更新作业状态等
      this.dialogVisible = false; // 关闭弹窗
    },
    //实现表格前面的多选框的
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    //
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //实现搜索栏多属性搜索的
    Search_table() {
      const Search_List = [];
      let res1 = this.inputVal;
      const res = res1.replace(/\s/gi, "");
      let searchArr = this.showTable;
      searchArr.forEach((e) => {
        let ID = e.ID;
        let homeworkName= e.homeworkName;
        let name= e.name;
        let recommendTeacher=e.recommendTeacher;
        let workType=e.workType;
        if (ID.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (name.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (homeworkName.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (recommendTeacher.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (workType.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
      });
      this.tableData = Search_List;
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
<style scoped>
.dialog-content {
  display: flex;
  flex-wrap: wrap;
}
.dialog-left-top,
.dialog-left-bottom,
.dialog-right-top,
.dialog-right-bottom {
  flex: 1;
  padding: 10px;
  box-sizing: border-box;
}
.dialog-right-top {
  display: flex;
  flex-direction: column;
}
/* 其他样式 */
</style>

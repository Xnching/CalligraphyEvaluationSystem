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
      <el-button style="margin-left:20px ;margin-right:535px" type="primary" @click="Search_table()">搜索</el-button>
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
          width="95">
      </el-table-column>
      <el-table-column
          prop="homeworkName"
          label="作业名称"
          width="100">
      </el-table-column>
      <el-table-column
          prop="name"
          label="作者"
          width="120">
      </el-table-column>
      <el-table-column
          prop="recommendTeacher"
          label="推荐教师"
          width="120">
      </el-table-column>
      <el-table-column
          prop="workType"
          label="作业类型"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column
          prop="judge"
          label="评选人"
          width="120">
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button type="success" size="small" icon="el-icon-edit" @click="handleEdit2(scope.row)">作品详情</el-button>
          <el-button type="danger" size="small" icon="el-icon-delete">移出优秀作品</el-button>
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

    <!-- 作品详情弹窗 -->
    <el-dialog :visible.sync="dialogVisible" width="60%" :before-close="handleClose">
      <img src="/images/copybook/1.jpg" alt="作品图片" style="width: 100%; height: 75%;">
      <div style="display: flex; justify-content: space-between; padding: 10px;">
        <div style="width: 45%;">
          <p>作者：{{ currentWork.name }}</p>
          <p>作业类型：{{ currentWork.workType }}</p>
          <p>分数：88</p>
          <p>展示状态：<el-button type="text" @click="toggleDisplay">{{ displayStatus }}</el-button></p>
        </div>
        <div style="width: 45%;">
          <p>作业名：{{ currentWork.homeworkName }}</p>
          <p>评选人：{{ currentWork.judge }}</p>
          <p>教师评语：*****</p>
          <el-button type="danger" @click="handleDelete">删除</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      inputVal: "",
      tableData: Array(8).fill().map(() => ({
        ID: "3",
        homeworkName: "一年级第一次专项作业",
        name: "张三",
        recommendTeacher: "李四",
        workType: "综合作业",
        judge: "王五",
        image: "image_url"
      })),
      showTable: [],
      multipleSelection: [],
      dialogVisible: false,
      currentWork: {},
      displayStatus: '允许展示'
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
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    Search_table() {
      const Search_List = [];
      let res1 = this.inputVal;
      const res = res1.replace(/\\s/gi, "");
      let searchArr = this.showTable;
      searchArr.forEach((e) => {
        if (
            e.ID.includes(res) ||
            e.homeworkName.includes(res) ||
            e.name.includes(res) ||
            e.recommendTeacher.includes(res) ||
            e.workType.includes(res) ||
            e.judge.includes(res)
        ) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
      });
      this.tableData = Search_List;
    },
    handleCurrentChange() {},
    handleSizeChange() {},
    currentPage4() {},
    handleEdit2(row) {
      this.currentWork = row;
      this.dialogVisible = true;
    },
    handleClose() {
      this.dialogVisible = false;
    },
    toggleDisplay() {
      this.displayStatus = this.displayStatus === '允许展示' ? '不予展示' : '允许展示';
    },
    handleDelete() {
      this.dialogVisible = false;
    }
  },
  created() {
    this.showTable = [...this.tableData];
  }
};
</script>

<style scoped>
.el-dialog__headerbtn {
  margin-top: -10px;
}
</style>

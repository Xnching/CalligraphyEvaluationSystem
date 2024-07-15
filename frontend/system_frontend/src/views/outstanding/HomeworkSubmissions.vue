<template>
  <div>
    <div style="padding:10px; margin:10px; margin-bottom: -5px;">
      <el-input style="width:300px"
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
          width="190">
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
          width="120">
      </el-table-column>
      <el-table-column
          prop="judge"
          label="评选人"
          width="120">
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button type="success" size="small" icon="el-icon-edit" @click="handleEdit(scope.row)">作品详情</el-button>
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
      <el-form label-position="left" label-width="90px">
        <!-- 作业图片 -->
        <el-form-item label="作业图片:">
          <img :src="imageSrc" style="width: 100%; height: 75%;">
        </el-form-item>
        <!-- 作者 -->
        <el-form-item label="作者:">
          <el-input v-model="currentWork.name" placeholder="作者"></el-input>
        </el-form-item>
        <!-- 作业类型 -->
        <el-form-item label="作业类型:">
          <el-input v-model="currentWork.workType" placeholder="作业类型"></el-input>
        </el-form-item>
        <!-- 分数 -->
        <el-form-item label="分数:">
          <el-input v-model="currentWork.finalScore" placeholder="分数"></el-input>
        </el-form-item>
        <!-- 展示状态 -->
        <el-form-item label="展示状态:">
          <el-button type="text" @click="toggleDisplay">{{ displayStatus }}</el-button>
        </el-form-item>
        <!-- 作业名 -->
        <el-form-item label="作业名:">
          <el-input v-model="currentWork.homeworkName" placeholder="作业名"></el-input>
        </el-form-item>
        <!-- 评选人 -->
        <el-form-item label="评选人:">
          <el-input v-model="currentWork.judge" placeholder="评选人"></el-input>
        </el-form-item>
        <!-- 教师评语 -->
        <el-form-item label="教师评语:">
          <el-input v-model="currentWork.comment" placeholder="教师评语"></el-input>
        </el-form-item>
        <!-- 删除按钮 -->
        <el-form-item style=" text-align: right;">
          <el-button type="danger" @click="handleDelete">删除</el-button>
        </el-form-item>
      </el-form>
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
      displayStatus: '允许展示',
      imageSrc:'/images/copybook/6.jpg',
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
    handleEdit(row) {
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

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
      <el-button style="margin-left:20px ;margin-right:535px" type="primary" @click="Search_table">搜索</el-button>
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
          label="竞赛作品编号"
          width="120">
      </el-table-column>
      <el-table-column
          prop="competitionName"
          label="竞赛名"
          width="200">
      </el-table-column>
      <el-table-column
          prop="name"
          label="作者"
          width="120">
      </el-table-column>
      <el-table-column
          prop="finalScore"
          label="最终分数"
          width="120">
      </el-table-column>
      <el-table-column
          prop="level"
          label="等级"
          width="120">
      </el-table-column>
      <el-table-column
          prop="judge"
          label="评选人"
          width="120">
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit(scope.row)">作品详情</el-button>
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

    <!-- 弹窗 -->
    <el-dialog title="作品详情"
        :visible.sync="dialogVisible"
        width="40%"
        :close-on-click-modal="false">
      <el-form label-position="left" label-width="90px">
        <!-- 作业图片 -->
        <el-form-item label="作业图片:">
          <img :src="imageSrc" style="width: 100%; height: 75%;">
        </el-form-item>
        <!-- 作者 -->
        <el-form-item label="作者:">
          <el-input v-model="currentRow.name" placeholder="作者"></el-input>
        </el-form-item>
        <!-- 竞赛名 -->
        <el-form-item label="竞赛名:">
          <el-input v-model="currentRow.competitionName" placeholder="竞赛名"></el-input>
        </el-form-item>
        <!-- 分数 -->
        <el-form-item label="最终分数:">
          <el-input v-model="currentRow.finalScore" placeholder="分数"></el-input>
        </el-form-item>
        <!-- 展示状态 -->
        <el-form-item label="展示状态:">
          <el-button type="text" @click="toggleDisplayStatus">{{ displayStatus }}</el-button>
        </el-form-item>
        <!-- 等级 -->
        <el-form-item label="等级:">
          <el-input v-model="currentRow.level" placeholder="等级"></el-input>
        </el-form-item>
        <!-- 评选人 -->
        <el-form-item label="评选人:">
          <el-input v-model="currentRow.judge" placeholder="评选人"></el-input>
        </el-form-item>
        <!-- 教师评语 -->
        <el-form-item label="教师评语:">
          <el-input v-model="currentRow.comment" placeholder="教师评语"></el-input>
        </el-form-item>
        <!-- 删除按钮 -->
        <el-form-item style=" text-align: right;">
          <el-button type="danger" @click="handleDelete">移出优秀作品</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      inputVal:"",
      tableData: Array(8).fill().map(() => ({
        ID:"3",
        competitionName: "王羲之杯硬笔书法大赛",
        name: "张三",
        finalScore: "94.567",
        level: "特等奖",
        judge: "李四",
        image: "image_url",
        comment: "优秀作品"
      })),
      showTable:[],
      multipleSelection: [],
      dialogVisible: false,
      currentRow: {},
      displayStatus: '允许展示',
      imageSrc:'/images/copybook/3.jpg',
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
        let ID = e.ID;
        let competitonName= e.competitionName;
        let name= e.name;
        let finalScore=e.finalScore;
        let level=e.level;
        let judge=e.judge;
        if (ID.includes(res) || competitonName.includes(res) || name.includes(res) || finalScore.includes(res) || level.includes(res) || judge.includes(res)) {
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
      this.currentRow = row;
      this.dialogVisible = true;
    },
    toggleDisplayStatus() {
      this.displayStatus = this.displayStatus === '允许展示' ? '不予展示' : '允许展示';
    },
    handleDelete() {
      this.dialogVisible = false;
    }
  },
  created(){
    this.showTable = [...this.tableData];
  }
};
</script>

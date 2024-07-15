<template>
  <div>
    <el-tabs v-model="currentPage" @tab-click="handleClick">
      <el-tab-pane label="初级作品评阅" name="junior">
        <!-- 初级作品评阅的内容 -->
        <div style="padding:10px; margin:10px; margin-bottom: -5px;">
          <el-input style="width:300px "
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
              prop="juniorWorkID"
              label="ID"
              width="95">
          </el-table-column>
          <el-table-column
              prop="juniorCompetitionName"
              label="竞赛名"
              width="100">
          </el-table-column>
          <el-table-column
              prop="juniorGroup"
              label="组别"
              width="120">
          </el-table-column>
          <el-table-column
              prop="systemScore"
              label="系统评分"
              width="120">
          </el-table-column>
          <el-table-column
              prop="mineScore"
              label="我的评分"
              show-overflow-tooltip>
          </el-table-column>
          <el-table-column fixed="right" label="操作">
            <template slot-scope="scope">
              <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit1(scope.row)">修改/评阅</el-button>
              <el-button type="danger" size="small"  icon="el-icon-delete">删除</el-button>
            </template>
          </el-table-column>

        </el-table>

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
      <el-tab-pane label="最终作品评阅" name="final">
        <!-- 最终作品评阅的内容 -->
        <div style="padding:10px; margin:10px; margin-bottom: -5px;">
          <el-input style="width:300px"
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
              prop="finalWorkID"
              label="ID"
              width="95">
          </el-table-column>
          <el-table-column
              prop="finalCompetitionName"
              label="竞赛名"
              width="100">
          </el-table-column>
          <el-table-column
              prop="finalGroup"
              label="组别"
              width="120">
          </el-table-column>
          <el-table-column
              prop="juniorAvgScore"
              label="初级评分"
              width="120">
          </el-table-column>
          <el-table-column
              prop="mineFinalScore"
              label="我的最终评分"
              show-overflow-tooltip>
          </el-table-column>
          <el-table-column fixed="right" label="操作">
            <template slot-scope="scope">
              <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit2(scope.row)">修改/评阅</el-button>
              <el-button type="danger" size="small"  icon="el-icon-delete">删除</el-button>
            </template>
          </el-table-column>

        </el-table>

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

  </div>
</template>

<script>
export default {
  data() {
    return {
      //搜索栏要用的
      inputVal:"",
      currentPage: 'junior',
      tableData: Array(8).fill().map(() => ({
        juniorWorkID:"1000",
        juniorCompetitionName: "兰亭序杯",
        juniorGroup: "小学A组",
        systemScore: "90",
        mineScore: "92",
        finalWorkID:"1000",
        finalCompetitionName: "兰亭序杯",
        finalGroup: "小学A组",
        juniorAvgScore: "90",
        mineFinalScore: "92"
      })),
      showTable:[],
      multipleSelection: [],
      ruleForm: {
        juniorWorkID:'',
        juniorCompetitionName: '',
        juniorGroup: '',
        systemScore: '',
        mineScore: '',
        finalWorkID:'',
        finalCompetitionName: '',
        finalGroup: '',
        juniorAvgScore: '',
        mineFinalScore: ''
      }
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
    showPage(page) {
      this.currentPage = page;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    Search_table() {
      const Search_List = [];
      let res1 = this.inputVal;
      const res = res1.replace(/\s/gi, "");
      let searchArr = this.showTable;
      searchArr.forEach((e) => {
        let ID = e.ID;
        let name= e.name;
        let count= e.count;
        let competitionObject=e.competitionObject;
        let group=e.group;
        let state=e.state;
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
        if (count.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (competitionObject.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (group.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
        if (state.includes(res)) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
      });
      this.tableData = Search_List;
    },

    //初级作品评阅的评阅按钮
    handleEdit1(row){
      const id = row.juniorWorkID;
      // 跳转到竞赛组别详情页面
      this.$router.push(`reviewWorks/junior/${id}`);
    },

    //最终作品评阅的按钮
    handleEdit2(row){
      const id = row.finalWorkID;
      // 跳转到竞赛组别详情页面
      this.$router.push(`reviewWorks/final/${id}`);
    },
    
    handleCurrentChange() {},
    handleSizeChange() {},
    currentPage4() {}

  }
};

</script>

<style>

</style>

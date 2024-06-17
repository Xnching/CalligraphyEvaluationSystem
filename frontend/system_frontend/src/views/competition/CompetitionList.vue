<template>
  <div>
    <div v-if="!showForm">
      <div style="padding:10px; margin:10px; margin-bottom: -5px;">
        <el-input style="width:300px ;margin-left:-80px"
                  suffix-icon="el-icon-search"
                  placeholder="请输入"
                  v-model="inputVal"
                  @keyup.enter.native="Search_table()"
                  clearable>
        </el-input>
        <el-button style="margin-left:20px ;margin-right:535px" type="primary" @click="Search_table">搜索</el-button>
        <el-button type="primary" @click="showForm = true">新增<i class="el-icon-circle-plus"></i></el-button>
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
            label="ID"
            width="95">
        </el-table-column>
        <el-table-column
            prop="name"
            label="名称"
            width="100">
        </el-table-column>
        <el-table-column
            prop="count"
            label="参赛人数"
            width="120">
        </el-table-column>
        <el-table-column
            prop="competitionObject"
            label="参赛对象"
            width="120">
        </el-table-column>
        <el-table-column
            prop="group"
            label="组别"
            show-overflow-tooltip>
        </el-table-column>
        <el-table-column
            prop="state"
            label="当前状态"
            width="120">
        </el-table-column>
        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button type="success" size="small" icon="el-icon-edit" @click="handleEdit2(scope.row)">详情</el-button>
            <el-button type="danger" size="small" icon="el-icon-delete">删除</el-button>
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
    </div>

    <div v-else>
      <el-button type="primary" @click="showForm = false">返回</el-button>
      <div style="display: flex; flex-wrap: wrap; padding: 10px;">
        <div style="flex: 1; margin-right: 20px;">
          <el-form :model="form">
            <el-form-item label="竞赛编号">
              <el-input v-model="form.competitionID"></el-input>
            </el-form-item>
            <el-form-item label="竞赛名称">
              <el-input v-model="form.competitionName"></el-input>
            </el-form-item>
            <el-form-item label="开始报名时间">
              <el-date-picker v-model="form.startSignupTime" type="datetime"></el-date-picker>
            </el-form-item>
            <el-form-item label="截止报名时间">
              <el-date-picker v-model="form.endSignupTime" type="datetime"></el-date-picker>
            </el-form-item>
            <el-form-item label="开始竞赛时间">
              <el-date-picker v-model="form.startCompetitionTime" type="datetime"></el-date-picker>
            </el-form-item>
            <el-form-item label="结束竞赛时间">
              <el-date-picker v-model="form.endCompetitionTime" type="datetime"></el-date-picker>
            </el-form-item>
            <el-form-item label="开始评阅时间">
              <el-date-picker v-model="form.startReviewTime" type="datetime"></el-date-picker>
            </el-form-item>
            <el-form-item label="结束评阅时间">
              <el-date-picker v-model="form.endReviewTime" type="datetime"></el-date-picker>
            </el-form-item>
          </el-form>
        </div>
        <div style="flex: 1;">
          <el-form :model="form">
            <el-form-item label="竞赛要求">
              <el-input type="textarea" v-model="form.competitionRequirements"></el-input>
            </el-form-item>
            <el-form-item label="宣传图添加">
              <el-upload
                  action="#"
                  list-type="picture-card"
                  :on-preview="handlePictureCardPreview"
                  :on-remove="handleRemove">
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
                <el-input v-model="form.groupName"></el-input>
              </el-form-item>
              <el-form-item label="特等奖比例">
                <el-input v-model="form.specialAwardRatio" suffix="%"></el-input>
              </el-form-item>
              <el-form-item label="一等奖比例">
                <el-input v-model="form.firstAwardRatio" suffix="%"></el-input>
              </el-form-item>
              <el-form-item label="二等奖比例">
                <el-input v-model="form.secondAwardRatio" suffix="%"></el-input>
              </el-form-item>
              <el-form-item label="三等奖比例">
                <el-input v-model="form.thirdAwardRatio" suffix="%"></el-input>
              </el-form-item>
              <el-form-item label="参赛对象">
                <el-radio-group v-model="form.competitionObject">
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
        <el-button type="primary" @click="addGroup">添加组别</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showForm: false,
      inputVal: "",
      tableData: Array(8).fill().map(() => ({
        ID: "10000A",
        name: "兰亭序杯",
        count: "50",
        competitionObject: "小学",
        state: "评阅中",
        group: "A组"
      })),
      showTable: [],
      multipleSelection: [],
      form: {
        competitionID: '',
        competitionName: '',
        startSignupTime: '',
        endSignupTime: '',
        startCompetitionTime: '',
        endCompetitionTime: '',
        startReviewTime: '',
        endReviewTime: '',
        competitionRequirements: '',
        groupName: '',
        specialAwardRatio: '',
        firstAwardRatio: '',
        secondAwardRatio: '',
        thirdAwardRatio: '',
        competitionObject: '',
        groupRequirements: ''
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
            e.name.includes(res) ||
            e.count.includes(res) ||
            e.competitionObject.includes(res) ||
            e.state.includes(res) ||
            e.group.includes(res)
        ) {
          if (Search_List.indexOf(e) == "-1") {
            Search_List.push(e);
          }
        }
      });
      this.tableData = Search_List;
    },

    handleEdit2(row){
      // 获取竞赛名和组别
      const competitionName = row.name;
      const division = row.group;

      // 生成竞赛组别名
      const competition = `${competitionName}-${division}`;

      // 跳转到竞赛组别详情页面
      this.$router.push(`competitionList/${competition}`);


    },



    handleCurrentChange() {},
    handleSizeChange() {},
    currentPage4() {},
    addGroup() {
      this.form = {
        competitionID: '',
        competitionName: '',
        startSignupTime: '',
        endSignupTime: '',
        startCompetitionTime: '',
        endCompetitionTime: '',
        startReviewTime: '',
        endReviewTime: '',
        competitionRequirements: '',
        groupName: '',
        specialAwardRatio: '',
        firstAwardRatio: '',
        secondAwardRatio: '',
        thirdAwardRatio: '',
        competitionObject: '',
        groupRequirements: ''
      };
    }
  }
};
</script>

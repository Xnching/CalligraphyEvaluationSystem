<template>
  <div>
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

    <!-- 编辑按钮的弹窗 -->
    <el-dialog
        title="点击修改信息"
        :visible.sync="dialogVisible"
        width="60%"
        :close-on-click-modal="false">
        <el-form :model="editForm" label-width="100px">
          <el-form-item label="公告名："> 
            <el-input v-model="editForm.name"></el-input>
          </el-form-item>
          <el-form-item label="公告类型：">
            <el-select v-model="editForm.type" placeholder="请选择公告类型">
              <el-option label="系统更新公告" value="system"></el-option>
              <el-option label="竞赛公告" value="competition"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发布对象：">
            <el-select v-model="editForm.target" placeholder="请选择发布对象">
              <el-option label="学生" value="student"></el-option>
              <el-option label="教师" value="teacher"></el-option>
              <el-option label="学生和教师" value="both"></el-option>
              <el-option label="管理员" value="manger"></el-option>
              <el-option label="所有人" value="all"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="发布状态：">
            <el-radio-group v-model="editForm.state">
              <el-radio label="已发布" ></el-radio>
              <el-radio label="待发布" ></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="发布时间：">
            <el-input v-model="editForm.time"></el-input>
          </el-form-item>
          <el-form-item label="公告内容：">
            <WangEditor></WangEditor>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="handleSubmit2">确定</el-button>
          </span>
        </template>
      </el-dialog>


    <!--主表格-->
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
          prop="announcementID"
          label="编号"
          width="95">
      </el-table-column>
      <el-table-column
          prop="announcementName"
          label="公告名"
          width="100">
      </el-table-column>
      <el-table-column
          prop="announcementType"
          label="公告类型"
          width="120">
      </el-table-column>
      <el-table-column
          prop="publishObject"
          label="发布对象"
          width="120">
      </el-table-column>
      <el-table-column
          prop="publishState"
          label="发布状态"
          width="120">
      </el-table-column>
      <el-table-column
          prop="publishDate"
          label="发布时间"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column
          prop="publishMan"
          label="发布人"
          show-overflow-tooltip>
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template slot-scope="scope">
          <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit2(scope.row)">查看</el-button>
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
  </div>
</template>

<script>
import WangEditor from '@/components/wangEditor.vue';

export default {
  components:{
    WangEditor
  },
  data() {
    return {
      //搜索栏要用的
      inputVal:"",
      tableData: Array(8).fill().map(() => ({
        announcementID:"10000",
        announcementName: "系统更新2.0",
        publishObject: "全体",
        publishState: "已发布",
        publishDate: "2024-1-10",
        publishMan:'赵云',
        announcementType: "系统公告",
      })),
      showTable:[],
      multipleSelection: [],
      dialogVisible: false,
      editForm:[],
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
    //
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
    handleCurrentChange() {},
    handleSizeChange() {},
    currentPage4() {},
    handleEdit2(row) {
      this.dialogVisible = true;
      this.$set(this.editForm, 'announcementName', row.announcementName);
      
    },
    handleSubmit2(){
      this.dialogVisible=false;
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
};

</script>

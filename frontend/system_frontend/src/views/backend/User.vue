<template>
    <div>
        <div style="padding:10px; margin:10px">
          <el-input style="width:250px" suffix-icon="el-icon-search" placeholder="请输入名称搜索"></el-input>
          <el-input style="width:250px" suffix-icon="el-icon-email" placeholder="请输入邮箱搜索"></el-input>
          <el-input style="width:250px" suffix-icon="el-icon-position" placeholder="请输入地址搜索"></el-input>
          <el-button style="margin-left:5px" type="primary">搜索</el-button>
          <el-button type="primary">新增<i class="el-icon-circle-plus"></i></el-button>
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
            label="日期"
            width="120">
            <template slot-scope="scope">{{ scope.row.date }}</template>
          </el-table-column>
          <el-table-column
            prop="name"
            label="姓名"
            width="120">
          </el-table-column>
          <el-table-column
            prop="address"
            label="地址"
            show-overflow-tooltip>
          </el-table-column>
          <el-table-column fixed="right" label="操作">                         
            <template slot-scope="scope">
               <el-button type="success" size="small" icon="el-icon-edit">编辑</el-button>
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
    </div>
</template>


<script>
export default {
  data() {
    return {
      tableData: Array(8).fill().map(() => ({
        date: '2016-05-02',
        name: '王小虎',
        address: '上海市普陀区金沙江路 1518 弄'
      })),
      multipleSelection: []
    };
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

    //分页用的功能
    handleCurrentChange() {},
    handleSizeChange() {},
    currentPage4() {}
  }
};





</script>
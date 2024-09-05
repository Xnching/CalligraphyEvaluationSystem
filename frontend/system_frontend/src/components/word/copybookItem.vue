<script>

export default {
  data () {
    return {
      formModel: {
        name: '',
        difficulty: 0
      },
      currentPage: 1,
      pageSize: 5,
      tableData: [
        {
          id: 1,
          name: '名家字帖1',
          source: '作家1'
        },
        {
          id: 2,
          name: '名家字帖2',
          source: '作家2'
        },
        {
          id: 3,
          name: '名家字帖3',
          source: '作家3'
        },
        {
          id: 4,
          name: '名家字帖4',
          source: '作家4'
        },
        {
          id: 5,
          name: '名家字帖5',
          source: '作家5'
        },
        {
          id: 6,
          name: '"书法杯"优秀作品1',
          source: '作家6'
        },
        {
          id: 7,
          name: '"书法杯"优秀作品2',
          source: '作家7'
        },
        {
          id: 8,
          name: '"书法杯"优秀作品3',
          source: '作家8'
        },
        {
          id: 9,
          name: '"书法杯"优秀作品4',
          source: '作家9'
        },
        {
          id: 10,
          name: '"书法杯"优秀作品5',
          source: '作家10'
        }
      ],
      copybookItemId: 0,
      visible: false
    }
  },
  methods: {
    handleDetail (row) {
      console.log(row.id)
      this.copybookItemId = row.id
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      // 在这里添加处理当前页改变的逻辑
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      // 在这里添加处理每页条数改变的逻辑
    }

  }
}
</script>
<template>
  <div class="box">
    <div class="top">
      <el-input
        v-model="formModel.name"
        placeholder="请输入模板名称"
        style="width: 240px"
      ></el-input>
      难度：<el-rate v-model="formModel.difficulty" size="large" />
    </div>
    <div class="main">
      <div class="left">
        <el-table :data="tableData">
          <el-table-column label="字帖名称" prop="name"></el-table-column>
          <el-table-column label="字帖来源" prop="source"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button type="primary" @click="handleDetail(scope.row)"
                >详情</el-button
              >
            </template>
          </el-table-column>
        </el-table>
        <div style="display: flex; justify-content: right">
          <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[5, 10]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="tableData.length"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </div>
      </div>
      <div class="right">
        <el-scrollbar height="70vh">
          <img src="/images/copybook/1.jpg" style="width: 100%;height:80%;" />
        </el-scrollbar>
      </div>
    </div>
    <el-button type="primary" @click="submitAdd()" style="margin-left:85%; margin-top: 50px;">确定生成</el-button>
  </div>
</template>

<style lang="scss" scoped>
.box {
  .top {
    display: flex;
    justify-content: space-around;
    align-items: center;
    margin-bottom: 20px;
  }
  .main {
    display: flex;
    justify-content: space-around;
    height: 75vh;
    .left {
      width: 45%;
    }
    .right {
      width: 45%;
      border: black 1px solid;
      padding: 5px;
    }
  }
}
</style>

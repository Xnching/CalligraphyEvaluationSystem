<script setup>
import { ref, defineEmits } from 'vue'
const formModel = ref({
  name: '',
  difficulty: 0
})

const currentPage = ref(1)
const pageSize = ref(5)
const tableData = ref([
  {
    id: 1,
    name: '名家字帖1',
    source: '系统库'
  },
  {
    id: 2,
    name: '名家字帖2',
    source: '系统库'
  },
  {
    id: 3,
    name: '名家字帖3',
    source: '系统库'
  },
  {
    id: 4,
    name: '名家字帖4',
    source: '系统库'
  },
  {
    id: 5,
    name: '名家字帖5',
    source: '系统库'
  },
  {
    id: 6,
    name: '"书法杯"优秀作品1',
    source: '竞赛'
  },
  {
    id: 7,
    name: '"书法杯"优秀作品2',
    source: '竞赛'
  },
  {
    id: 8,
    name: '"书法杯"优秀作品3',
    source: '竞赛'
  },
  {
    id: 9,
    name: '"书法杯"优秀作品4',
    source: '竞赛'
  },
  {
    id: 10,
    name: '"书法杯"优秀作品5',
    source: '竞赛'
  }
])

const copybookItemId = ref(0)
const handleDetail = (row) => {
  console.log(row.id)
  copybookItemId.value = row.id
}

const visible = ref(false)
const emit = defineEmits(['update'])
const handleSubmit = () => {
  emit('update', visible.value)
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
      <el-rate v-model="formModel.difficulty" size="large" />
    </div>
    <div class="main">
      <div class="left">
        <el-table :data="tableData">
          <el-table-column label="字帖名称" prop="name"></el-table-column>
          <el-table-column label="字帖来源" prop="source"></el-table-column>
          <el-table-column label="操作">
            <template #default="{ row }">
              <el-button type="primary" @click="handleDetail(row)"
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
          <img src="/src/assets/template/字帖.jpg" style="width: 100%" />
        </el-scrollbar>
      </div>
    </div>
    <div class="footer" style="text-align: center; margin: 10px">
      <el-button type="primary" @click="handleSubmit">提交</el-button>
    </div>
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

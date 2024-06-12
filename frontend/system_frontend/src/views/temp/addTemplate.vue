<script setup>
import { ref } from 'vue'
import comprehensiveItem from './comprehensiveItem.vue'
import earmarkedItem from './earmarkedItem.vue'
import copybookItem from './copybookItem.vue'
import templateDetail from '../templateDetail.vue'

const total1 = ref(100)
const currentPage1 = ref(1)
const pageSize1 = ref(10)
const sysTableData = ref([
  {
    id: 1,
    createTime: '2021-09-01',
    templateName: '系统模板1',
    templateType: '字帖练习'
  },
  {
    id: 2,
    createTime: '2021-09-02',
    templateName: '系统模板2',
    templateType: '专项练习'
  },
  {
    id: 3,
    createTime: '2021-09-03',
    templateName: '系统模板3',
    templateType: '专项练习'
  },
  {
    id: 4,
    createTime: '2021-09-04',
    templateName: '系统模板4',
    templateType: '专项练习'
  },
  {
    id: 5,
    createTime: '2021-09-05',
    templateName: '系统模板5',
    templateType: '字帖练习'
  },
  {
    id: 6,
    createTime: '2021-09-06',
    templateName: '系统模板6',
    templateType: '专项练习'
  },
  {
    id: 7,
    createTime: '2021-09-07',
    templateName: '系统模板7',
    templateType: '专项练习'
  },
  {
    id: 8,
    createTime: '2021-09-08',
    templateName: '系统模板8',
    templateType: '专项练习'
  },
  {
    id: 9,
    createTime: '2021-09-09',
    templateName: '系统模板9',
    templateType: '专项练习'
  },
  {
    id: 10,
    createTime: '2021-09-10',
    templateName: '系统模板10',
    templateType: '专项练习'
  }
])

const total2 = ref(100)
const currentPage2 = ref(1)
const pageSize2 = ref(10)
const existHomeworkTableData = ref([
  {
    id: 1,
    createTime: '2021-09-01',
    templateName: '一年级简单专项练习1',
    templateType: '专项练习'
  },
  {
    id: 2,
    createTime: '2021-09-02',
    templateName: '一年级简单专项练习2',
    templateType: '专项练习'
  },
  {
    id: 3,
    createTime: '2021-09-03',
    templateName: '一年级简单综合练习3',
    templateType: '综合练习'
  },
  {
    id: 4,
    createTime: '2021-09-04',
    templateName: '一年级简单专项练习4',
    templateType: '专项练习'
  },
  {
    id: 5,
    createTime: '2021-09-05',
    templateName: '一年级简单综合练习2',
    templateType: '综合练习'
  },
  {
    id: 6,
    createTime: '2021-09-06',
    templateName: '一年级简单专项练习5',
    templateType: '专项练习'
  },
  {
    id: 7,
    createTime: '2021-09-07',
    templateName: '一年级简单字帖练习1',
    templateType: '字帖练习'
  },
  {
    id: 8,
    createTime: '2021-09-08',
    templateName: '一年级简单专项练习5',
    templateType: '专项练习'
  },
  {
    id: 9,
    createTime: '2021-09-09',
    templateName: '一年级简单专项练习6',
    templateType: '专项练习'
  },
  {
    id: 10,
    createTime: '2021-09-10',
    templateName: '一年级简单专项练习7',
    templateType: '字帖练习'
  }
])

const template = ref({
  name: '系统模板1',
  wordCount: 10,
  font: '楷书',
  difficulty: 3,
  imgs: []
})
const dialogVisible1 = ref(false)
const handelDetail = (index) => {
  console.log('详情', index)
  dialogVisible1.value = true
}

const dialogVisible2 = ref(false)
const handelAdd = (index) => {
  console.log('添加', index)
  dialogVisible2.value = true
}

const dialogVisible3 = ref(false)
const options = ref([
  {
    value: '专项练习',
    label: '专项练习',
    children: [
      {
        value: '偏旁',
        label: '偏旁'
      },
      {
        value: '结构',
        label: '结构'
      }
    ]
  },
  {
    value: '综合练习',
    label: '综合练习'
  },
  {
    value: '字帖练习',
    label: '字帖练习'
  }
])
const handleTabClick = (tab) => {
  if (tab.props.label === '新建模板') {
    templateType.value = []
    dialogVisible3.value = true
  }
}

const templateType = ref([])
const selectTemplateType = () => {
  console.log('选择模板类型', templateType.value)
  dialogVisible3.value = false
}
</script>
<template>
  <el-tabs tab-position="left" @tab-click="handleTabClick">
    <el-tab-pane label="系统模板">
      <el-table :data="sysTableData" border>
        <el-table-column label="创建时间" prop="createTime"></el-table-column>
        <el-table-column label="模板名称" prop="templateName"></el-table-column>
        <el-table-column label="模板类型" prop="templateType"></el-table-column>
        <el-table-column label="操作">
          <template #default="{ row }">
            <div style="display: flex; justify-content: space-around">
              <el-button
                type="primary"
                size="small"
                @click="handelDetail(row.id)"
                >详情</el-button
              >
              <el-button type="success" size="small" @click="handelAdd(row.id)"
                >添加</el-button
              >
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div style="display: flex; justify-content: flex-end">
        <el-pagination
          v-model:current-page="currentPage1"
          v-model:page-size="pageSize1"
          :page-sizes="[5, 10]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total1"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-tab-pane>
    <el-tab-pane label="已有作业">
      <el-table :data="existHomeworkTableData" border>
        <el-table-column label="创建时间" prop="createTime"></el-table-column>
        <el-table-column label="模板名称" prop="templateName"></el-table-column>
        <el-table-column label="模板类型" prop="templateType"></el-table-column>
        <el-table-column label="操作">
          <template #default="{ row }">
            <div style="display: flex; justify-content: space-around">
              <el-button
                type="primary"
                size="small"
                @click="handelDetail(row.id)"
                >详情</el-button
              >
              <el-button type="success" size="small" @click="handelAdd(row.id)"
                >添加</el-button
              >
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div style="display: flex; justify-content: flex-end">
        <el-pagination
          v-model:current-page="currentPage2"
          v-model:page-size="pageSize2"
          :page-sizes="[5, 10]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total2"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-tab-pane>




    <el-tab-pane label="新建模板">
      <earmarkedItem
        v-if="templateType[1]"
        :templateType="templateType"
        :key="new Date().getTime()"
      ></earmarkedItem>
      <comprehensiveItem
        v-if="templateType[0] === '综合练习'"
        :templateType="templateType"
        :key="new Date().getTime()"
      ></comprehensiveItem>
      <copybookItem
        v-if="templateType[0] === '字帖练习'"
        :templateType="templateType"
        :key="new Date().getTime()"
      ></copybookItem>
    </el-tab-pane>





    
  </el-tabs>

  <el-dialog v-model="dialogVisible1">
    <templateDetail :template="template"></templateDetail>
  </el-dialog>

  <el-dialog v-model="dialogVisible2" title="添加模板" width="500">
    <span>确定添加该模板吗？</span>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible2 = false">取消</el-button>
        <el-button type="success" @click="dialogVisible2 = false">
          确认
        </el-button>
      </div>
    </template>
  </el-dialog>

  <el-dialog v-model="dialogVisible3" title="选择模板类型" width="500">
    <el-cascader-panel
      v-model="templateType"
      style="width: fit-content"
      :options="options"
    />
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible3 = false">取消</el-button>
        <el-button type="primary" @click="selectTemplateType"> 确认 </el-button>
      </div>
    </template>
  </el-dialog>
</template>

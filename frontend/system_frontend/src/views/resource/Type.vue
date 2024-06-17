<template>
  <el-table
      ref="multipleTable"
      :data="reigonData"
      style="width: 100%;margin-bottom: 20px;"
      row-key="id"
      border
      default-expand-all
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
    <el-table-column
        prop="name"
        label="名称">
    </el-table-column>
    <el-table-column fixed="right" label="操作">
      <template slot-scope="scope">
        <el-button type="success" size="small" icon="el-icon-edit" @click="handleEdit(scope.row)">修改</el-button>
        <el-button type="danger" size="small"  icon="el-icon-delete">删除</el-button>
        <el-button type="warning" size="small"  icon="el-icon-delete">添加下级</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<!-- 实现了父节点无法被选中但是全选没了！-->

<script>
import axios from 'axios'; // 或者你使用的其他 HTTP 请求库

export default{
  name:'reigonTable',
  data(){
    return{
      reigonData:[{
        id:'1',
        name:"教学指导",
        hasChildren1: true, // 有此才能判定是否为父节点,实现父节点多选框无法被选中
        children:[
          {
            id:'11',
            name:"教学指导",
          }
        ]
      },
        {
          id:'2',
          name:"书法知识",
          hasChildren1: true, // 有此才能判定是否为父节点
          children:[
            {
              id:'21',
              name:"名家作品",
            },
            {
              id:'22',
              name:"汉字",
            },
            {
              id:'23',
              name:"人物传记",
            }
          ]
        }
      ],
    }

  },
  methods:{


  }


}
</script>


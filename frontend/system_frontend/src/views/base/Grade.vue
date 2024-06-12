<template>
    <el-table
    ref="multipleTable"
    :data="gradeData"
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
<!-- 实现了父节点无法被选中但是全选没了！-->
<!-- 实现了父节点无法被选中但是全选没了！-->
<!-- 实现了父节点无法被选中但是全选没了！-->
  <script>
  import axios from 'axios'; // 或者你使用的其他 HTTP 请求库
  
  export default{
    name:'gradeTable',
    data(){
        return{
            gradeData:[{
                id:'1',
                name:"六三制小学",
                hasChildren1: true, // 有此才能判定是否为父节点,实现父节点多选框无法被选中
                children:[
                    {
                    id:'11',    
                    name:"一年级",
                    },
                    {
                    id:'12',
                    name:"二年级",
                    },
                    {
                    id:'13',
                    name:"三年级",
                    },
                    {
                    id:'14',
                    name:"四年级",
                    },
                    {
                    id:'15',
                    name:"五年级",
                    },
                    {
                    id:'16',
                    name:"六年级",
                    }
                ]
                },
                {
                id:'2',
                name:"六三制初中",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'21',
                    name:"初一",
                    },
                    {
                    id:'22',
                    name:"初二",
                    },
                    {
                    id:'23',
                    name:"初三",
                    }
                ]
                },
                {
                id:'3',
                name:"五四制小学",
                hasChildren1: true, // 有此才能判定是否为父节点,实现父节点多选框无法被选中
                children:[
                    {
                    id:'31',    
                    name:"一年级",
                    },
                    {
                    id:'32',
                    name:"二年级",
                    },
                    {
                    id:'33',
                    name:"三年级",
                    },
                    {
                    id:'34',
                    name:"四年级",
                    },
                    {
                    id:'35',
                    name:"五年级",
                    }
                ]
                },
                {
                id:'4',
                name:"五四制初中",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'41',
                    name:"初一",
                    },
                    {
                    id:'42',
                    name:"初二",
                    },
                    {
                    id:'43',
                    name:"初三",
                    },
                    {
                    id:'44',
                    name:"初四",
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
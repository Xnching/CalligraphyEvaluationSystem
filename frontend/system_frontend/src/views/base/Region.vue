<template>
    <el-table
    ref="multipleTable"
    :data="reigonData"
    style="width: 100%;margin-bottom: 20px;"
    row-key="id"
    border
    default-expand-all
    @row-click="handleRowClick"
    :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
    <el-table-column
        type="selection"
        width="55"
        :selectable="row => !row.hasChildren1" >
    </el-table-column>
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
    name:'reigonTable',
    data(){
        return{
            reigonData:[{
                id:'1',
                name:"系统用户管理",
                hasChildren1: true, // 有此才能判定是否为父节点,实现父节点多选框无法被选中
                children:[
                    {
                    id:'11',    
                    name:"系统用户管理",
                    },
                    {
                    id:'12',
                    name:"用户组管理",
                    }
                ]
                },
                {
                id:'2',
                name:"基础数据管理",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'21',
                    name:"区域管理",
                    },
                    {
                    id:'22',
                    name:"学校管理",
                    },
                    {
                    id:'23',
                    name:"年级管理",
                    },
                    {
                    id:'24',
                    name:"学年管理",
                    },
                ]
                },
                {
                id:'3',
                name:"前端用户管理",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'31',
                    name:"班级管理",
                    },
                    {
                    id:'32',
                    name:"学生管理",
                    },
                    {
                    id:'33',
                    name:"教师管理",
                    },
                ]
                },
                {
                id:'4',
                name:"字库管理",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'41',
                    name:"样本字管理",
                    },
                    {
                    id:'42',
                    name:"模板字管理",
                    },
                    {
                    id:'43',
                    name:"字帖管理",
                    },
                    {
                    id:'44',
                    name:"模板管理",
                    },
                    {
                    id:'45',
                    name:"定义管理",
                    },
                ]
                },
                {
                id:'5',
                name:"公告与帮助",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'51',
                    name:"公告管理",
                    hasChildren1: true, // 有此才能判定是否为父节点
                    children:[
                        {
                        id:'511',
                        name:"发布公告",
                        },
                        {
                        id:'512',
                        name:"查看公告",
                        },
                    ]
                    },
                    {
                    id:'52',
                    name:"帮助管理",
                    hasChildren1: true, // 有此才能判定是否为父节点
                    children:[
                        {
                        id:'521',
                        name:"常见问题与答案设置",
                        },
                        {
                        id:'522',
                        name:"人工客服",
                        },
                        {
                        id:'523',
                        name:"反馈管理",
                        },
                    ]
                    }
                ]
                },
                {
                id:'6',
                name:"竞赛管理",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'61',
                    name:"竞赛列表",
                    },
                    {
                    id:'62',
                    name:"评阅管理",
                    },
                    {
                    id:'63',
                    name:"作品评阅",
                    },
                ]
                },
                {
                id:'7',
                name:"优秀作品管理",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'71',
                    name:"作业评审",
                    },
                    {
                    id:'72',
                    name:"作业作品",
                    },
                    {
                    id:'73',
                    name:"竞赛作品",
                    },
                ]
                },
                {
                id:'8',
                name:"书法知识管理",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'81',
                    name:"类型管理",
                    },
                    {
                    id:'82',
                    name:"添加书法知识",
                    },
                    {
                    id:'83',
                    name:"资源管理",
                    },
                    {
                    id:'84',
                    name:"合集管理",
                    },
                ]
                }
            ],
        }

    },
    methods:{
      //选中行就能选中前面的多选框
      handleRowClick(row) {
        //此处实现父节点不会被选中
        if (!row.hasChildren1) { 
                this.$refs.multipleTable.toggleRowSelection(row);
        }
      },

    }


}
  </script>
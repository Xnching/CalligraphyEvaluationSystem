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
                name:"北京市",
                hasChildren1: true, // 有此才能判定是否为父节点,实现父节点多选框无法被选中
                children:[
                    {
                    id:'11',    
                    name:"东城区",
                    }
                ]
                },
                {
                id:'2',
                name:"江苏省",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'21',
                    name:"南京市",
                    },
                    {
                    id:'22',
                    name:"苏州市",
                    }
                ]
                },
                {
                id:'3',
                name:"浙江省",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'31',
                    name:"杭州市",
                    },
                    {
                    id:'32',
                    name:"宁波市",
                    }
                ]
                },
                {
                id:'4',
                name:"安徽省",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'41',
                    name:"合肥市",
                    },
                    {
                    id:'42',
                    name:"马鞍山市",
                    }
                ]
                },
                {
                id:'5',
                name:"山东省",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'51',
                    name:"青岛市",
                    hasChildren1: true, // 有此才能判定是否为父节点
                    children:[
                        {
                        id:'511',
                        name:"胶州市",
                        },
                        {
                        id:'512',
                        name:"平度市",
                        },
                    ]
                    },
                    {
                    id:'52',
                    name:"烟台市",
                    hasChildren1: true, // 有此才能判定是否为父节点
                    children:[
                        {
                        id:'521',
                        name:"福山区",
                        },
                        {
                        id:'522',
                        name:"莱山区",
                        },
                        {
                        id:'523',
                        name:"蓬莱区",
                        },
                    ]
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
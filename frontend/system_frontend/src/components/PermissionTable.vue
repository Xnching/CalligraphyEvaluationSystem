<template>
    <el-table
    ref="multipleTable"
    :data="permissionData"
    style="width: 100%;margin-bottom: 20px;"
    row-key="id"
    border
    @row-click="handleRowClick"
    default-expand-all
    :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
    <el-table-column
        type="selection"
        width="55"
        :selectable="row => !row.hasChildren1" >
    </el-table-column>
    <el-table-column
        prop="permission"
        label="权限">
    </el-table-column>
    </el-table>
<!-- 实现了父节点无法被选中但是全选没了！-->
<!-- 实现了父节点无法被选中但是全选没了！-->
<!-- 实现了父节点无法被选中但是全选没了！-->
<!-- 实现了父节点无法被选中但是全选没了！-->
</template>
<script>
export default{
    name:'PermissionTable',
    data(){
        return{
            permissionData:[{
                id:'1',
                permission:"系统用户管理",
                hasChildren1: true, // 有此才能判定是否为父节点,实现父节点多选框无法被选中
                children:[
                    {
                    id:'11',    
                    permission:"系统用户管理",
                    },
                    {
                    id:'12',
                    permission:"用户组管理",
                    }
                ]
                },
                {
                id:'2',
                permission:"基础数据管理",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'21',
                    permission:"区域管理",
                    },
                    {
                    id:'22',
                    permission:"学校管理",
                    },
                    {
                    id:'23',
                    permission:"年级管理",
                    },
                    {
                    id:'24',
                    permission:"学年管理",
                    },
                ]
                },
                {
                id:'3',
                permission:"前端用户管理",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'31',
                    permission:"班级管理",
                    },
                    {
                    id:'32',
                    permission:"学生管理",
                    },
                    {
                    id:'33',
                    permission:"教师管理",
                    },
                ]
                },
                {
                id:'4',
                permission:"字库管理",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'41',
                    permission:"样本字管理",
                    },
                    {
                    id:'42',
                    permission:"模板字管理",
                    },
                    {
                    id:'43',
                    permission:"字帖管理",
                    },
                    {
                    id:'44',
                    permission:"模板管理",
                    },
                    {
                    id:'45',
                    permission:"定义管理",
                    },
                ]
                },
                {
                id:'5',
                permission:"公告与帮助",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'51',
                    permission:"公告管理",
                    hasChildren1: true, // 有此才能判定是否为父节点
                    children:[
                        {
                        id:'511',
                        permission:"发布公告",
                        },
                        {
                        id:'512',
                        permission:"查看公告",
                        },
                    ]
                    },
                    {
                    id:'52',
                    permission:"帮助管理",
                    hasChildren1: true, // 有此才能判定是否为父节点
                    children:[
                        {
                        id:'521',
                        permission:"常见问题与答案设置",
                        },
                        {
                        id:'522',
                        permission:"人工客服",
                        },
                        {
                        id:'523',
                        permission:"反馈管理",
                        },
                    ]
                    }
                ]
                },
                {
                id:'6',
                permission:"竞赛管理",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'61',
                    permission:"竞赛列表",
                    },
                    {
                    id:'62',
                    permission:"评阅管理",
                    },
                    {
                    id:'63',
                    permission:"作品评阅",
                    },
                ]
                },
                {
                id:'7',
                permission:"优秀作品管理",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'71',
                    permission:"作业评审",
                    },
                    {
                    id:'72',
                    permission:"作业作品",
                    },
                    {
                    id:'73',
                    permission:"竞赛作品",
                    },
                ]
                },
                {
                id:'8',
                permission:"书法知识管理",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'81',
                    permission:"类型管理",
                    },
                    {
                    id:'82',
                    permission:"添加书法知识",
                    },
                    {
                    id:'83',
                    permission:"资源管理",
                    },
                    {
                    id:'84',
                    permission:"合集管理",
                    },
                ]
                }
            ],
        }

    },
    methods:{
        //选中行就能选中前面的多选框
        handleRowClick(row) {
            if (!row.hasChildren1) { 
                this.$refs.multipleTable.toggleRowSelection(row);
            }
        },

    }


}



</script>


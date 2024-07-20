<template>
    <el-table
    ref="multipleTable"
    :data="permissionData"
    style="width: 100%;margin-bottom: 20px;"
    row-key="id"
    @select="handleSelect"
    @select-all="handleSelectAll"
    @select-change="handleSelectChange"
    default-expand-all
    border
    :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
    <el-table-column
        type="selection"
        width="55"
        :selectable="row => isEditable">
    </el-table-column>
    <el-table-column
        prop="permission"
        label="权限">
    </el-table-column>
    </el-table>
</template>
<script>
export default{
    name:'PermissionTable',
    props: {
        editable: Boolean,
        menus: Array
    },
    watch: {
        //根据传入的值更新数据
        editable(newVal, oldVal) {
            this.isEditable = newVal;
        },
        menus: {
            handler(newVal, oldVal) {
                this.selectRows();
            },
            deep: true
        }
    },
    mounted() {
        this.selectRows();
    },
    data(){
        return{
            isEditable: this.editable,
            permissionData:[{
                id:'1',
                permission:"系统用户管理",
                hasChildren1: true, // 有此才能判定是否为父节点,实现父节点多选框无法被选中
                children:[
                    {
                    id:'2',    
                    permission:"系统用户管理",
                    },
                    {
                    id:'3',
                    permission:"用户组管理",
                    }
                ]
                },
                {
                id:'4',
                permission:"基础数据管理",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'5',
                    permission:"区域管理",
                    },
                    {
                    id:'6',
                    permission:"学校管理",
                    },
                    {
                    id:'7',
                    permission:"年级管理",
                    },
                    {
                    id:'8',
                    permission:"学年管理",
                    },
                ]
                },
                {
                id:'9',
                permission:"前端用户管理",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'10',
                    permission:"班级管理",
                    },
                    {
                    id:'11',
                    permission:"学生管理",
                    },
                    {
                    id:'12',
                    permission:"教师管理",
                    },
                ]
                },
                {
                id:'13',
                permission:"字库管理",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'14',
                    permission:"样本字管理",
                    },
                    {
                    id:'15',
                    permission:"模板字管理",
                    },
                    {
                    id:'16',
                    permission:"字帖管理",
                    },
                    {
                    id:'17',
                    permission:"模板管理",
                    },
                    {
                    id:'18',
                    permission:"分类管理",
                    },
                ]
                },
                {
                id:'19',
                permission:"数据分析",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'20',
                    permission:"用户数据分析",
                    },
                    {
                    id:'21',
                    permission:"字库分析",
                    },
                    {
                    id:'22',
                    permission:"书法知识资源分析",
                    }
                ]
                },
                {
                id:'23',
                permission:"公告与帮助",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'24',
                    permission:"公告管理",
                    hasChildren1: true, // 有此才能判定是否为父节点
                    children:[
                        {
                        id:'25',
                        permission:"发布公告",
                        },
                        {
                        id:'26',
                        permission:"查看公告",
                        },
                    ]
                    },
                    {
                    id:'27',
                    permission:"帮助管理",
                    hasChildren1: true, // 有此才能判定是否为父节点
                    children:[
                        {
                        id:'28',
                        permission:"常见问题与答案设置",
                        },
                        {
                        id:'29',
                        permission:"反馈管理",
                        },
                    ]
                    }
                ]
                },
                {
                id:'30',
                permission:"竞赛管理",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'31',
                    permission:"竞赛列表",
                    },
                    {
                    id:'32',
                    permission:"评阅管理",
                    },
                    {
                    id:'33',
                    permission:"作品评审",
                    },
                ]
                },
                {
                id:'34',
                permission:"优秀作品管理",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'35',
                    permission:"作业评审",
                    },
                    {
                    id:'36',
                    permission:"优秀作业作品",
                    },
                    {
                    id:'37',
                    permission:"优秀竞赛作品",
                    },
                ]
                },
                {
                id:'38',
                permission:"书法知识管理",
                hasChildren1: true, // 有此才能判定是否为父节点
                children:[
                    {
                    id:'39',
                    permission:"类型管理",
                    },
                    {
                    id:'40',
                    permission:"添加书法知识",
                    },
                    {
                    id:'41',
                    permission:"资源管理",
                    },
                    {
                    id:'42',
                    permission:"视频合集管理",
                    },
                ]
                }
            ],
        }

    },
    methods:{
        selectChildren(row, select) {
            if (row.children) {
                row.children.forEach(child => {
                    this.$refs.multipleTable.toggleRowSelection(child, select);
                    this.selectChildren(child, select);
                });
            }
        },
        selectParents(row, select) {
            let parent = this.findParent(this.permissionData, row);
            if (parent) {
                this.$refs.multipleTable.toggleRowSelection(parent, select);
                this.selectParents(parent, select);
            }
        },

        findParent(data, row) {
            for (let i = 0; i < data.length; i++) {
                if (data[i].children && data[i].children.includes(row)) {
                    return data[i];
                } else if (data[i].children) {
                    let parent = this.findParent(data[i].children, row);
                    if (parent) {
                        return parent;
                    }
                }
            }
            return null;
        },
        handleSelect(selection, row) {
            if (this.isEditable) {
                let select = selection.includes(row);
                this.selectChildren(row, select);
                if (select) {
                    this.selectParents(row, select);
                }
            }
        },
        
        handleSelectAll(selection) {
            if (this.isEditable) {
                this.permissionData.forEach(row => {
                    let select = selection.includes(row);
                    this.selectChildren(row, select);
                });
            }
        },
        handleSelectChange(selection) {
            if (this.isEditable) {
                this.permissionData.forEach(row => {
                    if (row.children) {
                        // 检查是否所有子节点都未被选中
                        if (row.children.every(child => !selection.includes(child))) {
                            this.$refs.multipleTable.toggleRowSelection(row, false);
                        } else {
                            // 如果有任何子节点被选中，确保父节点也被选中
                            this.$refs.multipleTable.toggleRowSelection(row, true);
                        }
                    }
                });
                
            }
        },

        //根据传入的menus数据选中多选框
        selectRows() {
            this.permissionData.forEach(row => {
                if (this.menus.includes(Number(row.id))) {
                    this.$refs.multipleTable.toggleRowSelection(row, true);
                } else {
                    this.$refs.multipleTable.toggleRowSelection(row, false);
                }
                if (row.children) {
                    row.children.forEach(child => {
                        if (this.menus.includes(Number(child.id))) {
                            this.$refs.multipleTable.toggleRowSelection(child, true);
                        } else {
                            this.$refs.multipleTable.toggleRowSelection(child, false);
                        }
                    });
                }
            });
        }


    }


}

</script>


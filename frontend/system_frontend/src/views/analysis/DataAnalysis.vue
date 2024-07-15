<template>
    <div>
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
              <el-button type="warning" size="small"  icon="el-icon-delete"@click="handleEdit1">详情</el-button>
          </template>
        </el-table-column> 
    </el-table>
     <el-dialog
        title="请输入新增系统用户的信息"
        :visible.sync="dialogVisible1"
        width="40%"
        :close-on-click-modal="false" >
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="300px" class="demo-ruleForm">
          <el-form-item label="区域名称：北京市" prop="name"> </el-form-item>
          <el-form-item label="学校数量：99所" prop="loginName"> </el-form-item>
          <el-form-item label="学校数量排行：第一名" prop="password">  </el-form-item>
          <el-form-item label="使用学校增长率排行：第一名"prop="phone"> </el-form-item>
          <el-form-item label="用户人数：9999人" prop="userGroup"></el-form-item>
          <el-form-item label="使用学校占比率：98%" prop="userGroup"></el-form-item>
          <el-form-item label="使用学校站比率排行：第一名" prop="userGroup"></el-form-item>
          <el-form-item label="区域内增长最快的下一级地区：东城区" prop="userGroup"></el-form-item>
          <el-form-item label="学生教师比：30" prop="userGroup"></el-form-item>
          <el-form-item label="预测使用人数增长量：1111" prop="userGroup"></el-form-item>
          <el-form-item label="取消合作数：12" prop="userGroup"></el-form-item>
          <el-form-item label="取消合作最多的下一级地区：东城区" prop="userGroup"></el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible1 = false">关闭</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
</template>
<script>
  export default{
    name:'reigonTable',
    data(){
        return{
            dialogVisible1: false,
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
       //第一个表单的跳出方法，就是点击新增
    handleEdit1(){
      if (this.$refs.ruleForm) {
        this.$refs.ruleForm.resetFields(); // 清除表单验证并重置表单
      }
      this.dialogVisible1 = true;
      this.radio = 1;
    }

    }


}
</script>

<style scoped>

</style>

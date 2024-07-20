<template>
    <div>
      <div style="padding:10px; margin:10px; margin-bottom: -5px;">
        <el-input style="width:300px " 
        suffix-icon="el-icon-search" 
        placeholder="请输入"
        v-model="inputVal"
        @keyup.enter.native="Search_table()"
        clearable>
        </el-input>
        <el-button style="margin-left:20px ;margin-right:535px" type="primary" @click="Search_table">搜索</el-button>
        <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus"></i></el-button>
      </div>       

      <el-table
        ref="multipleTable"
        :data="tableData"
        stripe
        tooltip-effect="dark"
        style="width: 100%">
        <el-table-column
          prop="id"
          label="用户组ID"
          width="195">
        </el-table-column>
        <el-table-column
          prop="name"
          label="用户组名"
          width="270">
        </el-table-column>
        <el-table-column
          prop="userCount"
          label="人数"
          width="190">
        </el-table-column>
        <el-table-column
          prop="state"
          label="状态"
          width="220">
        </el-table-column>
        <el-table-column fixed="right" label="操作">                         
          <template slot-scope="scope">
              <el-button type="success" size="small" icon="el-icon-edit" @click="handleEdit1(scope.row)">编辑</el-button>
              <el-button type="danger" size="small"  icon="el-icon-delete" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column> 

      </el-table>

      <!-- 分页栏-->
      <div style="padding:10px">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>

      <!-- 新增用户组的弹窗 -->
      <el-dialog
        title="请输入新增用户组的信息"
        :visible.sync="dialogVisible1"
        destroy-on-close
        width="40%"
        :close-on-click-modal="false"
        overflow-x:auto >
        <!-- 用于提示新增用户时的数据不能为空-->
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户组名:" prop="name"> 
            <el-input v-model="ruleForm.name"></el-input>
           </el-form-item>
          <el-form-item label="用户组描述:" prop="description"> 
            <el-input type="textarea" v-model="ruleForm.description"></el-input> 
          </el-form-item>
          <div style="display:flex">
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
              :tree-props="{children: 'children'}">
              <el-table-column
                  type="selection"
                  width="55">
              </el-table-column>
              <el-table-column
                  prop="permission"
                  label="权限">
              </el-table-column>
            </el-table>
            

          
          </div>
          
          <el-radio v-model="radio" :label="'未激活'" style="margin-left: 180px" value="未激活">未激活</el-radio>
          <el-radio v-model="radio" :label="'已激活'" value="已激活">激活</el-radio>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible1 = false">取消</el-button>
            <el-button type="primary" @click="handleSubmit1()">确定</el-button>
          </span>
        </template>
      </el-dialog>

      <!-- 编辑按钮的弹窗 -->
      <el-dialog
        title="点击修改信息"
        :visible.sync="dialogVisible2"
        destroy-on-close
        width="40%"
        :close-on-click-modal="false">
        <el-form :model="editForm" label-width="100px">
          <el-form-item label="用户组名:"> 
            <el-input v-model="editForm.name" :disabled="!isEditing"></el-input>
          </el-form-item>
          <el-form-item label="人数:">
            <el-input v-model="editForm.userCount" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="用户组描述:">
            <el-input type="textarea" v-model="editForm.description" :disabled="!isEditing"></el-input> 
          </el-form-item>
          <el-table
            ref="multipleTable"
            :data="permissionData"
            style="width: 100%;margin-bottom: 20px;"
            row-key="id"
            :key="editForm.id"
            @select="handleSelect"
            @select-all="handleSelectAll"
            @select-change="handleSelectChange"
            default-expand-all
            border
            :tree-props="{children: 'children'}">
            <el-table-column
                type="selection"
                width="55"
                :selectable="row => isEditing">
            </el-table-column>
            <el-table-column
                prop="permission"
                label="权限">
            </el-table-column>
          </el-table>

          <el-form-item label="状态">
            <el-select v-model="editForm.state" placeholder="请选择状态" :disabled="!isEditing">
              <el-option
                :label="'未激活'"
                :value="'未激活'">
              </el-option>
              <el-option
                :label="'激活'"
                :value="'已激活'">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible2 = false">取消</el-button>
            <el-button type="primary" @click="isEditing ? handleConfirm() : handleEdit2()">
              {{ isEditing ? '确定' : '编辑' }}
            </el-button>
          </span>
        </template>
      </el-dialog>

    </div>
</template>


<script>
export default {
  data() {
    return {
      inputVal:"",
      tableData: [],
      showTable:[],
      multipleTable:[],
      //下面两个是用来控制两个表单的隐藏与出现
      dialogVisible1:false,
      dialogVisible2:false,
      radio: '', // 设置 radio 的初始值为 "已激活"
      total:0,
      pageNum:1,
      pageSize:6,
      isEditing:false,
      editForm:{
        id:'',
        name:'',
        userCount:'',
        state:'',
        menus:[],
        description:'',
      },
      ruleForm:{
        name:'',
        state:'',
        description:'',
        menus:[],
      },
      rules:{
        name: [
          { required: true, message: '请输入用户组名', trigger: 'blur' }
        ],
      },
      permissionData:[{
        id:'1',
        permission:"系统用户管理",
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
        children:[
            {
            id:'24',
            permission:"公告管理",
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
      
    };
  },
  watch: {
    //用于实现搜索栏搜索的
    inputVal(item1) {
      if (item1 == "") {
        this.tableData = this.showTable;
      }
    },
  },
  //把tableData数值赋给showTable
  created(){
    //请求分页查询数据
    this.load();
    this.showTable = [...this.tableData];
  },
  methods: {
    //实现搜索栏多属性搜索的
    Search_table() {
      this.load();
    },

    //第一个表单的跳出方法，就是点击新增
    handleAdd(){
      this.dialogVisible1 = true;
      this.radio = '已激活';
    },

    //删除按钮的删除接口以及逻辑
    handleDelete(row) {
      this.$confirm('确认删除该记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 调用后端删除接口
        this.request.put('/user-group/delete', {
          id: row.id
        }).then(res => {
          if(res.code == '200'){
            this.$message.success('删除用户数据成功！');
            this.load();
          }else{
            this.$message.error('删除用户数据失败，原因：'+res.msg);
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });          
      });
    },
    
    //新增用户组的表单，提交前要判断下数据，就是新增表单里的确定按钮
    handleSubmit1(){
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          // 表单验证通过，提交数据
          this.ruleForm.state = this.radio;
          this.ruleForm.menus = this.$refs.multipleTable.selection.map(row => Number(row.id));
          this.request.post("/user-group/add",this.ruleForm).then(res=>{
            if(res.code == '200'){
              this.$message.success('新增用户组数据成功！');
              this.load();
            } else {
              this.$message.error('新增用户组数据失败，原因：' + res.msg);
            }
          })
          // 重置表单
          this.dialogVisible1 = false;  //关闭弹窗
        } else {
          return false; // 阻止表单提交
        }
      });
    },
    
    //第二个表单的跳出方法，就是点击编辑,跳出编辑弹窗
    handleEdit1(row){
      this.isEditing = false;
      this.editForm = Object.assign({}, row); 
      this.dialogVisible2 = true;
      /*从你的打印结果来看，selectRows函数被正确地调用了，并且editForm.menus包含了正确的数据。然而，尽管toggleRowSelection被调用了，但是它并没有按照预期选中行。
      这可能是因为toggleRowSelection函数在DOM更新之前被调用了。在Vue.js中，DOM的更新是异步的，这意味着当你改变数据时，视图不会立即更新，而是等到下一个事件循环。*/
      this.$nextTick(() => {
        this.selectRows();
      });
    },

    //编辑表单中的编辑方法
    handleEdit2(){
      this.isEditing = true;
    },
    //编辑表单中的确定方法
    handleConfirm(){
      this.editForm.state = this.radio;
      this.editForm.menus = this.$refs.multipleTable.selection.map(row => Number(row.id));
      this.request.put("/user-group/update",this.editForm).then(res=>{
        if(res.code == '200'){
          this.$message.success('新增用户组数据成功！');
          this.load();
        } else {
          this.$message.error('新增用户组数据失败，原因：' + res.msg);
        }
      })
      this.isEditing = false;
    },

    //分页用的功能
    handleCurrentChange(val) {
      this.pageNum = val;   //获取当前第几页
      this.load();
    },
    handleSizeChange(val) {
      this.pageSize = val;  //获取当前每页显示条数
      this.load();
    },
    //请求分页查询数据
    load(){
      this.request.get("/user-group/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          str:this.inputVal,
        }
      }).then(res=>{
        //console.log(res);
        if(res.code=='200'){
          this.tableData=res.data.records;
          this.total=res.data.total;
        }else{
          this.$message.error('获取全部用户组数据失败，原因：'+res.msg);
        }
      })
    },

    //权限表的方法
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
      
      let select = selection.includes(row);
      this.selectChildren(row, select);
      if (select) {
          this.selectParents(row, select);
          if (row.children) {
              row.children.forEach(child => {
                  this.selectChildren(child, select);
              });
          }
      }
    },
    handleSelectAll(selection) {
      this.permissionData.forEach(row => {
          let select = selection.includes(row);
          this.selectChildren(row, select);
          if (row.children) {
              row.children.forEach(child => {
                this.selectChildren(child, select);
              });
          }
      });
    },
    handleSelectChange(selection) {
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
    },

    //根据传入的menus数据选中多选框
    selectRows() {
      const selectChildren = (children) => {
        children.forEach(child => {
          if (this.editForm.menus.includes(Number(child.id))) {
            this.$refs.multipleTable.toggleRowSelection(child, true);
          } else {
            this.$refs.multipleTable.toggleRowSelection(child, false);
          }
          if (child.children) {
            selectChildren(child.children);
          }
        });
      };
      this.permissionData.forEach(row => {
        if (this.editForm.menus.includes(Number(row.id))) {
          this.$refs.multipleTable.toggleRowSelection(row, true);
        } else {
          this.$refs.multipleTable.toggleRowSelection(row, false);
        }
        if (row.children) {
          selectChildren(row.children);
        }
      });
    }

    
  },
  
};
</script>
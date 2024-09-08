<template>
    <div>
      <div style="padding:10px; margin:10px; margin-bottom: -5px;">
        <el-input style="width:300px " 
        suffix-icon="el-icon-search" 
        placeholder="请输入"
        v-model="inputVal"
        @keyup.enter.native="load"
        clearable>
        </el-input>
        <el-button style="margin-left:20px ;margin-right:35px" type="primary" @click="load">搜索</el-button>
        <!--下面是属性选择器 ，可以手动输入搜索-->
        <el-select v-model="selectedType" filterable placeholder="请选择模板类型" @change="load" clearable style="width: 150px;margin-right: 25%;">
          <el-option
            v-for="item in typeOptions"
            :key="item"
            :label="item"
            :value="item">
          </el-option>
        </el-select>
        
        <el-button type="primary" @click="handleAdd" style="margin-left: auto; margin-right: 120px;">新增模板<i class="el-icon-circle-plus"></i></el-button>
      </div>       

      <el-table
        ref="multipleTable"
        :data="tableData"
        stripe
        tooltip-effect="dark"
        style="width: 100%">
        <el-table-column
          prop="id"
          label="模板编号"
          width="125">
        </el-table-column>
        <el-table-column
          prop="name"
          label="模板名称"
          width="215">
        </el-table-column>
        <el-table-column
          prop="type"
          label="模板类型"
          width="120">
        </el-table-column>
        <el-table-column
          prop="detailType"
          label="详细类型"
          width="150">
        </el-table-column>
        <el-table-column
          prop="createdTime"
          label="创建时间"
          width="220">
        </el-table-column>
        <el-table-column fixed="right" label="操作">                         
          <template slot-scope="scope">
              <el-button type="success" size="small" icon="el-icon-edit"  @click="handleEdit(scope.row)">详情</el-button>
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

      <!-- 新增模板的弹窗 -->
      <el-dialog
        title="请输入新增模板的信息"
        :visible.sync="dialogVisible1"
        width="80%"
        :close-on-click-modal="false" >
        <el-tabs type="border-card" @tab-click="handleTabClick">
            <el-tab-pane label="专项练习（部首）">
              <earmarkedItem
                  v-if="templateType[1] === '部首'"
                  :templateType="templateType"
              ></earmarkedItem>
            </el-tab-pane>

            <el-tab-pane label="专项练习（结构）">
              <earmarkedItem
                  v-if="templateType[1] === '结构'"
                  :templateType="templateType"
              ></earmarkedItem>
            </el-tab-pane>

            <el-tab-pane label="综合练习">
              <comprehensiveItem
                  v-if="templateType[0] === '综合练习'"
                  :templateType="templateType"
                  :key="new Date().getTime()"
              ></comprehensiveItem>
            </el-tab-pane>

            <el-tab-pane label="字帖练习">
              <copybookItem
                  v-if="templateType[0] === '字帖练习'"
                  :templateType="templateType"
                  :key="new Date().getTime()"
              ></copybookItem>
            </el-tab-pane>

        </el-tabs>

        
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible1 = false">取消</el-button>
          </span>
        </template>

      </el-dialog>

      <!-- 查看模板的弹窗 -->
      <el-dialog
        title="模板详细信息"
        :visible.sync="dialogVisible2"
        width="60%"
        :close-on-click-modal="false">
        <div class="top">
          <el-input
            v-model="formModel.name"
            style="width: 240px"
            disabled
          ></el-input>
          <el-input
            v-model="formModel.type"
            style="width: 240px"
            disabled
          ></el-input>
          <el-input
            v-model="formModel.detailType"
            style="width: 240px"
            disabled
          ></el-input>
          <el-input
            v-model="formModel.font"
            style="width: 240px"
            disabled
          ></el-input>
          <el-rate v-model="formModel.difficulty" size="large" disabled/>
          <div v-for="image in formModel.imageList" :key="image" class="image-item">
            <img :src="image" :alt="image">
          </div>
        </div>



        <template #footer>
          <span class="dialog-footer">
            <el-button type="primary" @click="dialogVisible2 = false">确定</el-button>
          </span>
        </template>

      </el-dialog>

    
    </div>
</template>



<script>
import comprehensiveItem from '@/components/word/comprehensiveItem.vue'
import earmarkedItem from '@/components/word/earmarkedItem.vue'
import copybookItem from '@/components/word/copybookItem.vue'

export default {
  components:{
    earmarkedItem,
    comprehensiveItem,
    copybookItem
  },
  data() {
    return {
      //搜索栏要用的
      inputVal:"",
      //初始隐藏两个表单
      dialogVisible1: false,
      dialogVisible2: false,
      templateType: ['专项练习', '部首'],
      formModel: {
        name: '楷书体模板',
        radical: '专项（结构）',
        font: '楷书',
        difficulty: 3
      },
      tableData:[],
      pageNum: 1,
      pageSize: 20,
      total: 0,
      selectedType:'',
      typeOptions: [
        '专项',
        '综合',
        '字帖'
      ],
      fontOptions:[],
      radicalOptions:[],
    };
  },
  created(){
    this.load();
    this.getFont();
    this.getRadical();
    this.getStructure();
  },
  methods: {
    handleAdd(){
      this.dialogVisible1=true;
    },
    submitAdd(){

    },
    //新增按钮跳出弹窗
    //点击编辑按钮跳出弹窗填充数据
    handleEdit(row) {
      // 将当前行数据复制到 editForm 中，避免直接修改表格数据
      this.editForm = Object.assign({}, row); 
      this.dialogVisible2 = true;
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
      this.request.get("/system-template/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          str:this.inputVal,
          type:this.selectedType
        }
      }).then(res=>{
        if(res.code=='200'){
					console.log(res);
          this.tableData=res.data.records;
          this.total=res.data.total;
        }else{
          this.$message.error('获取全部用户数据失败，原因：'+res.msg);
        }
      })
    },
    getFont(){
      this.request.get("/font/fonts").then(res=>{
        if(res.code=='200'){
          this.fontOptions=res.data;
        }else{
          this.$message.error('获取全部字体数据失败，原因：'+res.msg);
        }

      })
    },
    getRadical(){
      this.request.get("/radical/radicals").then(res=>{
        if(res.code=='200'){
          this.radicalOptions=res.data;
        }else{
          this.$message.error('获取全部部首数据失败，原因：'+res.msg);
        }

      })
    },
    getStructure(){
      this.request.get("/structure/structures").then(res=>{
        if(res.code=='200'){
          this.structureOptions=res.data;
        }else{
          this.$message.error('获取全部字体数据失败，原因：'+res.msg);
        }

      })
    },
    

    //新增用户表单提交前判断下数据格式是否正确
    handleTabClick(tab) {
      const label = tab.label;
      if (label === "专项练习（部首）") {
        this.templateType = ['专项练习', '部首'];
      } else if (label === "专项练习（结构）") {
        this.templateType = ['专项练习', '结构'];
      } else if (label === "综合练习") {
        this.templateType = ['综合练习'];
      } else if (label === "字帖练习") {
        this.templateType = ['字帖练习'];
      }
    },


    
  
  },
};
</script>

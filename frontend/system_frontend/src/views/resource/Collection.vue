<template>
    <div>
      <!--用来搜索合集的搜索框-->
      <div style="padding:10px; margin:10px; margin-bottom: -5px;">
        <el-input style="width:300px ;margin-left:-20px" 
        suffix-icon="el-icon-search" 
        placeholder="请输入"
        v-model="inputVal"
        @keyup.enter.native="Search_table()"
        clearable>
        </el-input>
        <el-button style="margin-left:20px ;margin-right:75px" type="primary" @click="Search_table">搜索</el-button>
        <el-select v-model="selectedRecommended" filterable placeholder="是否为推荐" @change="selectChange" style="width: 150px;margin-right: 220px;">
          <el-option
              v-for="item in Recommended"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
        <el-button type="primary" @click="handleAdd" style="margin-left: auto; margin-right: 120px;">新增合集<i class="el-icon-circle-plus"></i></el-button>
      
      </div>
    
      
      <!--下面是图片展示区域-->  
      <div class="image-grid"> 
          <div v-for="image in images" :key="image.id" class="image-item">
              <img :src="image.pictureUrl" :alt="image.name" @click="handleEdit1(image)">
              <p style="text-align: center;">{{ image.name }}</p> <!-- 添加这一行来显示图片标题 -->
          </div>
      </div>

      <!-- 分页栏-->
      <div style="padding:10px">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[20, 25, 30, 40]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </div>

    

      <!-- 新增合集的弹窗 -->
      <el-dialog
      title="请输入合集的信息"
      :visible.sync="dialogVisible1"
      width="60%"
      :close-on-click-modal="false" 
      style="text-align: left">
        <el-form ref="videoForm" :model="videoData" label-width="150px">
          <el-form-item label="合集封面添加：">
            <el-upload
              class="upload-demo"
              action="#"
              ref="image"
              :on-success="handleExcelImportSuccess"
              :on-error="handleExcelImportError"
              multiple
              drag
              :limit="1"
              :auto-upload="false"
              accept=".jpg,.jpeg,.png">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
              </el-upload>
          </el-form-item>
          <el-form-item label="合集标题：">
              <el-input v-model="videoData.name" size="small"></el-input>
          </el-form-item>
          <el-form-item label="合集简介：">
              <el-input type="textarea" v-model="videoData.summary" size="small"></el-input>
          </el-form-item>
          <el-form-item label="视频书法知识类型：">
              <el-select v-model="videoData.firstTypeId" @change="onFirstChange">
                <el-option label="书法知识:" value="1"></el-option>
                <el-option label="教学指导:" value="4"></el-option>
              </el-select>
          </el-form-item>
          <el-form-item label="详细类型：">
              <el-select v-model="videoData.secondTypeId" v-if="secondTypes.length">
                  <el-option v-for="type in secondTypes" :key="type.id" :label="type.name" :value="type.id"></el-option>
              </el-select>
          </el-form-item>
          <el-form-item label="是否为推荐的：">
            <el-switch
              style="display: block"
              v-model="videoData.isRecommended"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="推荐"
              inactive-text="不推荐">
            </el-switch>
          </el-form-item>
          <el-form-item label="添加标签：">
              <el-tag
              :key="tag"
              v-for="tag in dynamicTags"
              closable
              :disable-transitions="false"
              @close="handleClose(tag)">
              {{tag}}
              </el-tag>
              <el-input
              class="input-new-tag"
              v-if="inputVisible"
              v-model="inputValue"
              ref="saveTagInput"
              size="small"
              @keyup.enter.native="handleInputConfirm"
              @blur="handleInputConfirm">
              </el-input>
              <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
          </el-form-item>
          <el-form-item label="添加单集视频：">
            <el-transfer
              filterable
              :filter-method="filterMethod"
              filter-placeholder="请输入单集视频标题"
              v-model="chooseVideos"
              :data="simpleData">
            </el-transfer>

          </el-form-item>
        </el-form>
        
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="handleCancel">取消</el-button>
                <el-button type="primary" @click="handleSubmit1()">确定</el-button>
            </span>
        </template>
      </el-dialog>



      <!--合集详情的弹窗-->
      <el-dialog
      title="此处编辑合集"
      :visible.sync="dialogVisible2"
      width="70%"
      :close-on-click-modal="false" >
        <!--合集详细信息表单-->
        <el-form ref="videoForm" :model="editData" label-width="160px">
          <el-form-item label="合集封面：">
            <!-- 显示图片 -->
            <img v-if="editData" :src="editData.pictureUrl" :alt="editData.name" />
          </el-form-item>
          <el-form-item label="合集标题：">
            <el-input v-model="editData.name" size="small"></el-input>
          </el-form-item>
          <el-form-item label="合集简介：">
            <el-input type="textarea" v-model="editData.summary" size="small"></el-input>
          </el-form-item>
          <el-form-item label="标签：">
                <el-input v-model="videoData.tag"></el-input>
              </el-form-item>
          <el-form-item label="视频书法知识类型：">
            <el-select v-model="editData.firstTypeId" @change="onFirstChange2">
              <el-option label="书法知识:" value="1"></el-option>
              <el-option label="教学指导:" value="4"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="详细类型：">
            <el-select v-model="videoData.secondTypeId" v-if="secondTypes.length">
              <el-option v-for="type in secondTypes" :key="type.id" :label="type.name" :value="type.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="是否为推荐的：">
            <el-switch
              style="display: block"
              v-model="editData.isRecommended"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-text="推荐"
              inactive-text="不推荐">
            </el-switch>
          </el-form-item>
          
            
          <el-form-item label="合集中单集视频：">
            <!--合集里面单集表格-->
            <el-table
              ref="multipleTable"
              :data="tableData"
              stripe
              tooltip-effect="dark"
              style="width: 100%">
              <el-table-column
                prop="id"
                label="视频编号"
                width="90">
              </el-table-column>
              <el-table-column
                prop="name"
                label="单集标题"
                width="180">
              </el-table-column>
              <el-table-column fixed="right" label="操作">           
                <template slot-scope="scope">
                    <el-button type="danger" size="small"  icon="el-icon-delete" @click="deleteVideo(scope.row)">删除</el-button>
                    <el-button type="primary" size="small"  icon="el-icon-delete"@click="openAddVideoDialog">增加</el-button>
                </template>
              </el-table-column> 
              <el-table-column fixed="right" label="修改顺序">
                <template slot-scope="scope">
                    <el-button  size="small" icon="el-icon-edit" @click="upVideo(scope.row)">上移</el-button>
                    <el-button  size="small"  icon="el-icon-delete" @click="downVideo(scope.row)">下移</el-button>
                </template>
              </el-table-column> 

            </el-table>

          </el-form-item>
        </el-form>

        
          
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible2 = false">取消</el-button>
                <el-button type="primary" @click="handleSubmit2()">确定</el-button>
                <el-button type="danger" @click="handleDelete()">删除</el-button>
            </span>
        </template>
      </el-dialog>
      
      <!-- 新增视频的弹窗 -->
      <el-dialog
        title="选择视频"
        :visible.sync="addVideoDialogVisible"
        width="50%"
        :close-on-click-modal="false">
        <el-table
          ref="addVideoTable"
          :data="allVideos"
          stripe
          tooltip-effect="dark"
          style="width: 100%">
          <el-table-column
            type="selection"
            width="55">
          </el-table-column>
          <el-table-column
            prop="id"
            label="视频编号"
            width="180">
          </el-table-column>
          <el-table-column
            prop="name"
            label="视频标题"
            width="280">
          </el-table-column>
        </el-table>

        <template #footer>
          <span class="dialog-footer">
            <el-button @click="addVideoDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="addSelectedVideos">确定</el-button>
          </span>
        </template>
      </el-dialog>


    </div>
</template>


<script>
export default {
  data() {
    return {
      Recommended: [ {
        value: true,
        label: '为推荐的'
      }, {
        value: false,
        label: '不为推荐的'
      }],
      secondTypes: [],
      //下面三个是穿梭框的
      simpleData: [],
      selectedRecommended:null,
      editData:{

      },
      addVideoDialogVisible: false,

      //下面三个是自定义标签所需要的
      dynamicTags: [],
      inputVisible: false,
      inputValue: '',
      inputVal:'',
      //初始隐藏两个表单
      dialogVisible1: false,
      dialogVisible2: false,
      videoData:{
        name:'',
        summary:'',
        firstTypeId:'',
        secondTypeId:'',
        tag:'',
        list:[],
        isRecommended:false,
      },
      images: [],
      pageNum:1,
      pageSize:20,
      total:0,
      chooseVideos:[],
      allVideos:[],
      tableData:[],
      editData:{
        id:'',
        name:'',
        firstTypeId:'',
        secondTypeId:'',
        tag:'',
        isRecommended:'',
        summary:'',
        importer:'',
        pictureUrl:'',
        list:[],
      }
    };
  },
  created(){
    this.load();
  },
 

  methods: {
    Search_table(){
			this.load();
		},
    //新增按钮跳出弹窗
    handleAdd(){
      this.getVideos();
      console.log(this.simpleData);
      this.dialogVisible1=true;
    },
		selectChange(){
			this.load();
		},
    onFirstChange(){
      const firstTypeId = parseInt(this.videoData.firstTypeId,10);
      this.getSecond(firstTypeId);
      this.videoData.secondTypeId='';
    },
    onFirstChange2(){
      const firstTypeId = parseInt(this.editData.firstTypeId,10);
      this.getSecond(firstTypeId);
      this.editData.secondTypeId='';
    },
    getSecond(val){
      console.log("打印下值多少"+val);
      this.request.get("/calligraphy-facts/seconds",{
        params: {
          id: val
        }
      }).then(res => {
        if(res.code == '200'){
          this.secondTypes= res.data;
        }else{
          this.$message.error('获取二级类型失败，原因：'+res.msg);
        }
      });
      return null;
    },

    //新增用户表单提交前判断下数据格式是否正确
    handleSubmit1() {
      this.videoData.list = this.chooseVideos.map((item, index) => ({
        id: item,
        name: this.simpleData.find(video => video.key === item).label,
      }));
      this.videoData.tag=this.dynamicTags.join('@');
      let formData = new FormData();
      formData.append('image', this.$refs.image.uploadFiles[0].raw);
      formData.append("collection",JSON.stringify(this.videoData));
      this.request.post("/collection/add",formData, {
					headers: {
							'Content-Type': 'multipart/form-data'
					}
			}).then(res=>{
        if(res.code == '200'){
          this.$message.success('新增合集成功！');
          this.load();
        } else {
          this.$message.error('新增合集失败，原因：' + res.msg);
        }
      })
			
    },
    getVideos(){  
      this.request.get("/video/videos").then(res=>{
        if(res.code=='200'){
          console.log("打印下找到videos没");
          console.log(res.data);
          this.simpleData=res.data.map(video => ({
            key: video.id,
            label: video.name
          }));
        }else{
          this.$message.error('获取全部视频失败，原因：'+res.msg);
        }

      })
    },
    filterMethod(query, item) {
      return item.label.toLowerCase().indexOf(query.toLowerCase()) > -1;
    },
    openAddVideoDialog() {
      this.addVideoDialogVisible = true;
      this.fetchAllVideos();
    },
    fetchAllVideos() {
      this.request.get("/video-collection/other-videos",{
        params:{
          collectionId:this.editData.id
        }
      }).then(res=>{
        if(res.code=='200'){
          this.allVideos=res.data;
        }else{
          this.$message.error('获取全部视频失败，原因：'+res.msg);
        }

      })
    },
    addSelectedVideos() {
      const selected = this.$refs.addVideoTable.selection;
      selected.forEach(video => {
        this.tableData.push(video);
      });
      this.addVideoDialogVisible = false;
    },
    deleteVideo(row) {
      const index = this.tableData.indexOf(row);
      if (index !== -1) {
        this.tableData.splice(index, 1);
      }
    },
    upVideo(row) {
      const index = this.tableData.indexOf(row);
      if (index > 0) {
        const temp = this.tableData[index - 1];
        this.tableData.splice(index - 1, 1, row);
        this.tableData.splice(index, 1, temp);
      }
    },
    downVideo(row) {
      const index = this.tableData.indexOf(row);
      if (index < this.tableData.length - 1) {
        const temp = this.tableData[index + 1];
        this.tableData.splice(index + 1, 1, row);
        this.tableData.splice(index, 1, temp);
      }
    },
    
		

    //点击编辑按钮跳出弹窗填充数据
    handleEdit1(image) {
			this.editData=image;
      this.tableData=this.editData.list;
      console.log("让我们看下格式");
      console.log(this.editData);
      this.dialogVisible2 = true;
    },
    //导入成功
    handleExcelImportSuccess(response, file, fileList){ 
      //console.log("到了导入成功里面去了");
      if(response.code=='200'){
        this.$message.success("导入成功");
        this.load();
      }else{
        this.$message.error("导入失败，原因为："+response.msg);
      }
    },
    //导入失败
    handleExcelImportError(err, file, fileList){ 
      //console.log(err);
      this.$message.error("导入失败，原因为"+err);
    },
    handleDelete() {
      // 在此处处理删除逻辑
			this.$confirm('确认删除该记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 调用后端删除接口
        this.request.put('/collection/delete', {
          id: this.editData.id
        }).then(res => {
          if(res.code == '200'){
            this.$message.success('删除合集成功！');
            this.load();
      			this.dialogVisible2 = false; // 关闭弹窗
          }else{
            this.$message.error('删除合集失败，原因：'+res.msg);
          }
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });          
      });
    },
		//编辑弹窗里的取消
		handleCancel(){
			this.dialogVisible1=false;
		},
		//编辑弹窗里的确认提交
		handleSubmit2(){
      this.editData.list=this.tableData;
			this.request.put("/collection/update",this.editData).then(res=>{
        if(res.code == '200'){
          this.$message.success('修改样本字数据成功！');
          this.load();
        } else {
          this.$message.error('修改样本字数据失败，原因：' + res.msg);
        }
      })
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
      this.request.get("/collection/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          str:this.inputVal,
					isRecommended:this.selectedRecommended === true // 确保传递布尔值
        }
      }).then(res=>{
        if(res.code=='200'){
					console.log(res);
          this.images=res.data.records;
          this.total=res.data.total;
        }else{
          this.$message.error('获取全部用户数据失败，原因：'+res.msg);
        }
      })
    },
		
    //自定义标签的方法
    handleClose(tag) {
        this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
    },
    
    //自定义标签的方法
    showInput() {
      this.inputVisible = true;
      this.$nextTick(_ => {
        this.$refs.saveTagInput.$refs.input.focus();
      });
    },

    //自定义标签的方法
    handleInputConfirm() {
      let inputValue = this.inputValue;
      if (inputValue && this.dynamicTags.indexOf(inputValue) === -1) { // 检查标签是否已存在
        this.dynamicTags.push(inputValue);
      }
      this.inputVisible = false;
      this.inputValue = '';
    },
  
  },
  
};
</script>

<style scoped>
.el-transfer-panel__item {
  padding-left: 30px!important;
}
.image-grid { 
  display: grid;
  grid-template-columns: repeat(5, 1fr); 
  grid-template-rows: repeat(3, auto);   
  margin-top: 20px;
  margin-bottom: 20px;
  grid-gap: 50px;                  
  row-gap: 30px;                 
}
.image-item {
  border: 2px solid #ccc; /* 边框样式 */
  padding: 10px;          /* 可选：内边距 */
  position: relative;     /* 设置为相对定位 */ 
}

.image-item img {
  display: block;           /*  将图片设置为块级元素 */
  margin: 0 auto;          /*  水平居中 */
  max-width: 80%;          /*  设置最大宽度为容器的 80% */
  height: auto;            /*  高度自适应 */
}

/*  添加伪元素来创建正方形背景  */
.image-item::before {
  content: '';             
  display: block;
  padding-top: 100%;      /*  通过 padding-top 来控制正方形的高度  */
  position: absolute;      
  top: 0;
  left: 0;
  z-index: -1;            /*  将伪元素置于图片下方  */
}
.el-dialog img {
  max-width: 200px;  /*  设置图片最大宽度  */
  max-height: 300px; /*  设置图片最大高度  */
}
.el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }
</style>

<template>
    <div>

        <!--用来搜索字的搜索框-->
        <div style="padding:10px; margin:10px; margin-bottom: -5px;">
            <el-input style="width:300px " 
            suffix-icon="el-icon-search" 
            placeholder="请输入要查找的字名"
            v-model="inputVal"
            @keyup.enter.native="Search_table()"
            clearable>
            </el-input>
            <el-button style="margin-left:20px ;margin-right:535px" type="primary" @click="Search_table">搜索</el-button>
        </div>
      <!-- 下面为表格的筛选-->
      <div style="text-align: left;margin-left: 30px;margin-top: 10px;display: flex">
        
        <!--下面是属性选择器 ，可以手动输入搜索-->
        <el-select v-model="selectedStructure" filterable placeholder="请选择结构" @change="selectChange" style="width: 150px;margin-right: 20px;" clearable>
            <el-option
            v-for="item in structureOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id">
            </el-option>
        </el-select>
        <el-select v-model="selectedRadical" filterable placeholder="请选择部首" @change="selectChange" style="width: 150px;margin-right: 20px;" clearable>
            <el-option
            v-for="item in radicalOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id">
            </el-option>
        </el-select>
        <el-select v-model="selectedGrade" filterable placeholder="请选择年级" @change="selectChange" style="width: 150px;margin-right: 20px;" clearable>
            <el-option
            v-for="item in gradeOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id">
            </el-option>
        </el-select>
        <el-select v-model="selectedSource" filterable placeholder="请选择来源" @change="selectChange" style="width: 150px;margin-right: 20px;" clearable>
            <el-option
            v-for="item in sourceOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id">
            </el-option>
        </el-select>
        <el-select v-model="selectedImporter" filterable placeholder="请选择导入人" @change="selectChange" style="width: 150px;margin-right: 20px;" clearable>
            <el-option
            v-for="item in importerOptions"
            :key="item"
            :label="item"
            :value="item">
            </el-option>
        </el-select>
        <el-button type="primary" @click="handleAdd" style="margin-left: auto; margin-right: 120px;">新增样本字<i class="el-icon-circle-plus"></i></el-button>
      </div>
        
        <!--下面是图片展示区域-->  
        <div class="image-grid"> 
            <div v-for="image in images" :key="image.id" class="image-item">
                <img :src="image.content" :alt="image.name" @click="handleEdit1(image)">
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

      

        <!-- 新增样本字的弹窗 -->
        <el-dialog
        title="请导入新增样本字的信息"
        :visible.sync="dialogVisible1"
        width="40%"
        :close-on-click-modal="false" >
            <el-tabs type="border-card" v-model="activeName">
                <!--单个添加-->
                <el-tab-pane label="单个添加" name="single">
                    <el-form ref="addForm" :model="addForm" label-width="100px">
                        <el-form-item label="字名：">
                            <el-input v-model="addForm.name"></el-input>
                        </el-form-item>
                        <el-form-item label="结构：">
                            <el-select v-model="addForm.structureId" filterable placeholder="请选择结构" style="width: 150px;margin-right: 20px;">
                                <el-option
                                v-for="item in structureOptions"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="部首：">
                            <el-select v-model="addForm.radicalId" filterable placeholder="请选择部首" style="width: 150px;margin-right: 20px;">
                                <el-option
                                v-for="item in radicalOptions"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="年级：">
                            <el-select v-model="addForm.gradeId" filterable placeholder="请选择年级"
                            style="width: 150px;margin-right: 20px;">
                                <el-option
                                v-for="item in gradeOptions"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="来源：">
													<el-select v-model="addForm.source" filterable placeholder="请选择来源" 
													style="width: 150px;margin-right: 20px;" clearable>
															<el-option
															v-for="item in sourceOptions"
															:key="item.id"
															:label="item.name"
															:value="item.id">
															</el-option>
													</el-select>
                        </el-form-item>
                    </el-form>

                    <el-upload
										ref="singleImage"
                    class="upload-demo"
                    drag
                    action="https://jsonplaceho"
                    :limit="1"
                    accept=".png,.jpeg,.jpg"
                    style=" text-align: center;"
										:on-success="handleExcelImportSuccess"
										:on-error="handleExcelImportError"
										:auto-upload="false">
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__text">将需要录入的字、部首的图片拖到此处，或<em>点击上传</em></div>
                    </el-upload>

                </el-tab-pane>

                <!--批量添加-->
                <el-tab-pane label="批量添加" style=" text-align: center;" name="batch">
                    <el-upload
										ref="batchImages"
                    class="upload-demo"
                    drag
										:multiple="true"
                    action="https://json/posts/"
                    accept=".png,.jpeg,.jpg"
										:auto-upload="false"
										:on-success="handleExcelImportSuccess"
										:on-error="handleExcelImportError">
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__text">将需要录入的字、部首的图片拖到此处，或<em>点击上传</em></div>
                    </el-upload>

                    <el-upload
										ref="batchExcelFile"
                    class="upload-demo"
                    drag
                    action="https://j/"
                    :multiple="false"
                    :limit="1"
										:auto-upload="false"
                    accept=".xls,.xlsx"
										:on-success="handleExcelImportSuccess"
										:on-error="handleExcelImportError">
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__tip" slot="tip">格式以对应字文件名，字名，结构，部首，年级，来源</div>
                        <div class="el-upload__text">将需要录入的字、部首的对应的属性Excel拖到此处，或<em>点击上传</em></div>
                    </el-upload>

                </el-tab-pane>
            
            </el-tabs>
            

            <template #footer>
            <span class="dialog-footer">
                <el-button @click="dialogVisible1 = false">取消</el-button>
                <el-button type="primary" @click="handleSubmit1()">确定</el-button>
            </span>
            </template>
        </el-dialog>

        <!-- 编辑按钮的弹窗 -->
        <el-dialog
        title="该样本字详细信息"
        :visible.sync="dialogVisible2"
        width="30%"
				destroy-on-close
        :close-on-click-modal="false" 
        style=" text-align: center;">
            <img v-if="selectedImage" :src="selectedImage.content" :alt="selectedImage.name" style="width: 100%;">
            <el-form :model="editForm" label-width="120px">
                <el-form-item label="样本字名：">
                    <el-input v-model="editForm.name" :disabled="!isEditing"></el-input>
                </el-form-item>
                <el-form-item label="结构：">
										<el-select v-model="editForm.structureId" filterable placeholder="请选择结构" 
										:disabled="!isEditing" style="width: 150px;margin-right: 20px;">
												<el-option
												v-for="item in structureOptions"
												:key="item.id"
												:label="item.name"
												:value="item.id">
												</el-option>
										</el-select>
								</el-form-item>
								<el-form-item label="部首：">
										<el-select v-model="editForm.radicalId" filterable placeholder="请选择部首" 
										:disabled="!isEditing" style="width: 150px;margin-right: 20px;">
												<el-option
												v-for="item in radicalOptions"
												:key="item.id"
												:label="item.name"
												:value="item.id">
												</el-option>
										</el-select>
								</el-form-item>
								<el-form-item label="年级：">
										<el-select v-model="editForm.gradeId" filterable placeholder="请选择年级"
										:disabled="!isEditing" style="width: 150px;margin-right: 20px;">
												<el-option
												v-for="item in gradeOptions"
												:key="item.id"
												:label="item.name"
												:value="item.id">
												</el-option>
										</el-select>
								</el-form-item>
                <el-form-item label="来源：">
									<el-select v-model="editForm.source" filterable placeholder="请选择来源" 
									:disabled="!isEditing" style="width: 150px;margin-right: 20px;" clearable>
											<el-option
											v-for="item in sourceOptions"
											:key="item.id"
											:label="item.name"
											:value="item.id">
											</el-option>
									</el-select>
                </el-form-item>
                <el-form-item label="导入人：">
                    <el-input v-model="editForm.importer" :disabled="true"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
							<span class="dialog-footer">
								<el-button @click="handleCancel">取消</el-button>
								<el-button type="primary" @click="isEditing ? handleSubmit2() : handleEdit2()">
									{{ isEditing ? '确定' : '编辑' }}
								</el-button>
									<el-button type="danger" @click="handleDelete()">删除</el-button>
							</span>
            </template>
        </el-dialog>
    </div>
</template>


<script>
export default {
    data() {
        return {
            radicalOptions: [],
            structureOptions: [],
						pageNum:1,
      			pageSize:20,
						total:0,
						gradeOptions: [],
            sourceOptions: [{
							id: '学生作品',
            name: '学生作品'
            }, {
							id: '竞赛作品',
            name: '竞赛作品'
            }, {
            id: '社会取得',
            name: '社会取得'
            }],
            importerOptions: [],
            addForm:{
								name:'',
                structureId:'',
                gradeId:'',
                radicalId:'',
                source:'',
                importer:'',
            },
            selectedRadical:null,
            selectedStructure:null,
            selectedGrade:null,
            selectedSource:'',
            selectedImporter:'',
            //初始隐藏两个表单
            dialogVisible1: false,
            dialogVisible2: false,
            showModal: false,
            selectedImage: null,
            user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
            images: [],
						isEditing:false,
						activeName:'single',
						inputVal:'',
            editForm:{
								id:'',
                name:'',
								content:'',
                structureId:'',
                gradeId:'',
                radicalId:'',
                source:'',
                importer:'',
            },

        };
    },

    
		created(){
			this.getGrades();
			this.getImporter();
			this.getRadical();
			this.getStructure();
			this.load();
		},

    methods: {
    Search_table(){
			this.load();
		},
    //新增按钮跳出弹窗
    handleAdd(){
      // 重置表单
    this.addForm = {
        name: '',
        structureId: '',
        gradeId: '',
        radicalId: '',
        source: '',
        importer: ''
    };

    // 清空上传的文件列表
    if (this.$refs.singleImage) {
        this.$refs.singleImage.clearFiles();
    }
    if (this.$refs.batchImages) {
        this.$refs.batchImages.clearFiles();
    }
    if (this.$refs.batchExcelFile) {
        this.$refs.batchExcelFile.clearFiles();
    }
      this.dialogVisible1 = true;
    },
		selectChange(){
			console.log("看看选了什么年级："+this.selectedGrade);
			console.log("看看选了什么结构："+this.selectedStructure);
			console.log("看看选了什么部首："+this.selectedRadical);
			console.log("看看选了什么来源："+this.selectedSource);
			console.log("看看选了什么导入人："+this.selectedImporter);
			this.load();
		},

    //新增用户表单提交前判断下数据格式是否正确
    handleSubmit1() {
			if (this.activeName === 'single') {
            this.uploadSingle();
        } else if (this.activeName === 'batch') {
            this.uploadBatch();
        }
      this.dialogVisible1 = false;  //关闭弹窗
    },
		//单个添加
		uploadSingle() {
			console.log("发了没");
			let formData = new FormData();
			this.addForm.importer=this.user.name;
			
			formData.append('file', this.$refs.singleImage.uploadFiles[0].raw);
			console.log("有没有文件");
			console.log(this.$refs.singleImage.uploadFiles[0].raw);
			formData.append('sampleWord', JSON.stringify(this.addForm));
			this.request.post('/sample-word/single-add', formData, {
					headers: {
							'Content-Type': 'multipart/form-data'
					}
			}).then(response => {
					this.handleExcelImportSuccess(response);
			}).catch(error => {
					this.handleExcelImportError(error);
			});
    },
		//批量添加
    uploadBatch() {
			let formDatas = new FormData();
			let images = this.$refs.batchImages.uploadFiles;
			for (let i = 0; i < images.length; i++) {
					formDatas.append('images', images[i].raw);
			}
			let importer = this.user.name; // 
			
    	formDatas.append('importer', importer);
			formDatas.append('excelFile', this.$refs.batchExcelFile.uploadFiles[0].raw);
			console.log("看下批量添加有importer吗");
			console.log(importer);
			console.log("看下批量添加有文件吗");
			console.log(this.$refs.batchExcelFile.uploadFiles[0].raw);
			this.request.post('/sample-word/batch-add', formDatas, {
					headers: {
							'Content-Type': 'multipart/form-data'
					}
			}).then(response => {
					this.handleExcelImportSuccess(response);
			}).catch(error => {
					this.handleExcelImportError(error);
			});
    },

    //点击编辑按钮跳出弹窗填充数据
    handleEdit1(image) {
      // 将选中的图片数据赋值给 selectedImage
      this.selectedImage = image;
			this.editForm=image;
			this.isEditing=false;
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

    //编辑用户弹窗确定提交的方法
    handleDelete() {
      // 在此处处理删除逻辑
			this.$confirm('确认删除该记录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 调用后端删除接口
				console.log("看看是否有id"+this.editForm.id);
        this.request.put('/sample-word/delete', {
          id: this.editForm.id
        }).then(res => {
          if(res.code == '200'){
            this.$message.success('删除样本字数据成功！');
            this.load();
						this.isEditing=false;
      			this.dialogVisible2 = false; // 关闭弹窗
          }else{
            this.$message.error('删除样本字数据失败，原因：'+res.msg);
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
			this.dialogVisible2=false;
		},
    //编辑弹窗里的编辑事件
    handleEdit2(){
      this.isEditing = true;
    },
		//编辑弹窗里的确认提交
		handleSubmit2(){
			this.editForm.importer=this.user.name;
			this.request.put("/sample-word/update",this.editForm).then(res=>{
        if(res.code == '200'){
          this.$message.success('修改样本字数据成功！');
          this.load();
        } else {
          this.$message.error('修改样本字数据失败，原因：' + res.msg);
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
      this.request.get("/sample-word/page",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          str:this.inputVal,
					structureId:this.selectedStructure,
					radicalId:this.selectedRadical,
					gradeId:this.selectedGrade,
					source:this.selectedSource,
					importer:this.selectedImporter
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
		getGrades(){
      this.request.get("/grade/grades").then(res=>{
        if(res.code=='200'){
          this.gradeOptions=res.data;
        }else{
          this.$message.error('获取全部年级数据失败，原因：'+res.msg);
        }

      })
    },
		getImporter(){
			this.request.get("/sample-word/importers").then(res=>{
        if(res.code=='200'){
          this.importerOptions=res.data;
        }else{
          this.$message.error('获取全部导入人数据失败，原因：'+res.msg);
        }

      })
		},

    
  },
  
};
</script>

<style scoped>
.image-grid { 
  display: grid;
  grid-template-columns: repeat(5, 1fr); 
  grid-template-rows: repeat(4, auto);   
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
</style>

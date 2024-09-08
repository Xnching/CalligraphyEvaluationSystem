<script>
export default {
  props: {
    templateType: Array,
  },
  data () {
    return {
      formModel: {
        name: '',
        type: '',
        font: '',
        difficulty: 0
      },
      radicalOptions: [
        {
          value: '专项练习(部首)',
          label: '专项练习(部首)'
        },
        {
          value: '专项练习(结构)',
          label: '专项练习(结构)'
        }
      ],
      fontOptions: [],

      wordImgCount: 30, // 使用相同的图片数量
      wordImgs: [],
      tableData:[],
      wordImgBlobs: [],
      candidateWordList: [],
      imgShow: false,
      visible: false,
      tablePageNum: 1,
      tablePageSize: 10,
      tableTotal: 0, 
      wordPageNum: 1,
      wordPageSize: 20,
      wordTotal: 0, 
      tempRow:{},
    }
  },
  mounted() {
    if (this.templateType[1] === '部首') {
      this.formModel.type = '专项练习(部首)'
    } else if (this.templateType[1] === '结构') {
      this.formModel.type = '专项练习(结构)'
    }
    this.$nextTick(() => {
      this.getFont();
      if (this.templateType[1] === '部首') {
          this.getRadical();
        } else if (this.templateType[1] === '结构') {
          this.getStructure();
        }
    });
  },
  watch: {
    templateType: {
      handler(newVal) {
        if (newVal[1] === '部首') {
          this.formModel.type = '专项练习(部首)';
          this.getRadical();
        } else if (newVal[1] === '结构') {
          this.formModel.type = '专项练习(结构)';
          this.getStructure();
        }
      },
      immediate: true // 立即执行一次
    }
  },
  methods: {
    async fetchImages() {
      console.log('进来了没');

      // 清空 wordImgBlobs
      this.wordImgBlobs = [];
      // 清空 candidateWordList
      this.candidateWordList = [];
      const wordImgPromises = this.wordImgs.map(async (item) => {
        return { id: item.id, content: item.content };
      });

      const blobs = await Promise.all(wordImgPromises);
      console.log('这个呢');

      // 使用 this.$set 更新 wordImgBlobs
      blobs.forEach((item, index) => {
        this.$set(this.wordImgBlobs, index, { id: item.id, content: item.content});
      });
      console.log('第二个');
      console.log(this.wordImgBlobs);
    },
    wordHandleClick(imgBlob) {
      console.log(imgBlob);
      this.candidateWordList.push({ id: imgBlob.id, content: imgBlob.content });
      console.log(this.candidateWordList);
    },
    onDel (index) {
      this.candidateWordList.splice(index, 1)
    },
    onEdit () {
      this.imgShow = !this.imgShow
    },
    //获取右边展示的模板字
    handleShow(row) {
      this.tempRow = row;
      const params = {
        pageNum: this.wordPageNum,
        pageSize: this.wordPageSize,
        fontId: this.formModel.font
      };

      if (this.templateType[1] === '部首') {
        params.radicalId = row.id;
      } else if (this.templateType[1] === '结构') {
        params.structureId = row.id;
      }

      this.request.get("/template-word/page2", { params })
        .then(res => {
          if (res.code === '200') {
            console.log(res);
            this.wordImgs = res.data.records.map((record) => ({
              id: record.id,
              content: record.content,
            }));
            this.wordTotal = res.data.total;
            console.log(this.wordImgs);
            this.fetchImages();
          } else {
            this.$message.error('获取模板字失败，原因：' + res.msg);
          }
        });
    },
    //获取字体
    getFont(){
      this.request.get("/font/fonts").then(res=>{
        if(res.code=='200'){
          this.fontOptions=res.data;
        }else{
          this.$message.error('获取全部字体数据失败，原因：'+res.msg);
        }

      })
    },
    //分页用的功能
    tableCurrentChange(val) {
      this.tablePageNum = val;   //获取当前第几页
      if (this.templateType[1] === '部首') {
        this.getRadical();
      } else if (this.templateType[1] === '结构') {
        this.getStructure();
      }
    },
    tableSizeChange(val) {
      this.tablePageSize = val;  //获取当前每页显示条数
      if (this.templateType[1] === '部首') {
        this.getRadical();
      } else if (this.templateType[1] === '结构') {
        this.getStructure();
      }
    },
    //获取部首列表
    getRadical(){
      this.request.get("/radical/page",{
        params:{
          pageNum:this.tablePageNum,
          pageSize:this.tablePageSize,
        }
      }).then(res=>{
        if(res.code=='200'){
          this.tableData=res.data.records;
          this.tableTotal=res.data.total;
        }else{
          this.$message.error('获取全部部首失败，原因：'+res.msg);
        }
      })
    },
    //获取结构列表
    getStructure(){
      this.request.get("/structure/page",{
        params:{
          pageNum:this.tablePageNum,
          pageSize:this.tablePageSize,
        }
      }).then(res=>{
        if(res.code=='200'){
          this.tableData=res.data.records;
          this.tableTotal=res.data.total;
        }else{
          this.$message.error('获取全部部首失败，原因：'+res.msg);
        }
      })
    },
    //分页用的功能
    wordCurrentChange(val) {
      this.wordPageNum = val;   //获取当前第几页
      this.handleShow(this.tempRow);
    },
    wordSizeChange(val) {
      this.wordPageSize = val;  //获取当前每页显示条数
      this.handleShow(this.tempRow);
    },

    //生成模板
    submitAdd(){
      const ids = this.candidateWordList.map(item => item.id);
      const type = this.templateType[1];
      const data = {
        name:this.formModel.name,
        detailType: type,
        wordIds: ids,
        difficulty:this.formModel.difficulty,
        fontId:this.formModel.font,
        wordCount:this.candidateWordList.length,
      };
      this.request.post("/system-template/special-add",data).then(res=>{
        if(res.code == '200'){
          this.$message.success('新增系统模板成功！');
          // 清空 candidateWordList
          this.candidateWordList = [];
        } else {
          this.$message.error('新增用户数据失败，原因：' + res.msg);
        }
      })
    },
    

  }
}

</script>
<template>
  <div class="box">
    <div class="top">
      <el-input
        v-model="formModel.name"
        placeholder="请输入模板名称"
        style="width: 240px"
      ></el-input>
      <el-select
        v-model="formModel.type"
        size="large"
        style="width: 240px"
        :disabled="true" 
      >
        <el-option
          v-for="item in radicalOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      <el-select v-model="formModel.font" size="large" style="width: 240px">
        <el-option
          v-for="item in fontOptions"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        />
      </el-select>
      难度：<el-rate v-model="formModel.difficulty" size="large" />
    </div>
    <div class="main">
      <div class="left">
        <div>部首</div>
        <div style="border: black solid 2px; width: 32vw">
          <el-scrollbar height="400px">
            <el-table
              ref="fontTable"
              :data="tableData"
              stripe
              tooltip-effect="dark"
              style="width: 100%">
              <el-table-column
                prop="id"
                label="编号"
                width="65">
              </el-table-column>
              <el-table-column
                prop="name"
                label="名称"
                width="130">
              </el-table-column>
              <el-table-column fixed="right" label="操作">                         
                <template slot-scope="scope">
                  <el-button type="primary" size="small" icon="el-icon-info" @click="handleShow(scope.row)">展示该内容相关的模板字</el-button>
                </template>
              </el-table-column> 
            </el-table>

            <!-- 分页栏-->
            <div style="padding:10px">
              <el-pagination
                @size-change="tableSizeChange"
                @current-change="tableCurrentChange"
                :current-page="tablePageNum"
                :page-size="tablePageSize"
                layout="total, prev, pager, next"
                :total="tableTotal">
              </el-pagination>
            </div>

          </el-scrollbar>
        </div>
      </div>
      <div class="right">
        <div>汉字列表</div>
        <div style="border: black solid 2px; width: 32vw">
          <el-scrollbar height="400px">
            <div
              style="
                display: flex;
                justify-content: space-around;
                flex-wrap: wrap;
              "
            >
              <span
                v-for="(imgBlob, index) in wordImgBlobs"
                :key="index"
                style="
                  display: flex;
                  flex-direction: column;
                  align-items: center;
                  margin: 5px;
                "
                v-if="wordImgBlobs.length > 0" 
              >
                <img
                  v-if="imgBlob.content"
                  :src="imgBlob.content"
                  @click="wordHandleClick(imgBlob)"
                  style="width: 90px"
                />
              </span>
              <!-- 分页栏-->
              <div style="padding:10px">
                <el-pagination
                  @size-change="wordSizeChange"
                  @current-change="wordCurrentChange"
                  :current-page="wordPageNum"
                  :page-size="wordPageSize"
                  layout="total, prev, pager, next"
                  :total="wordTotal">
                </el-pagination>
              </div>
            </div>
          </el-scrollbar>
        </div>
      </div>
    </div>
    <div class="footer">
      <el-scrollbar height="300px">
        <span
          v-for="(imgBlob, index) in candidateWordList"
          :key="index"
          style="position: relative"
        >
          <img :src="imgBlob.content" style="width: 90px; margin: 5px 0 0 5px" />
          <img
            v-show="imgShow"
            src="/images/叉号.svg"             
            style="position: absolute; bottom: 75px; right: 0; cursor: pointer ; width: 23px; height: 23px;"
            @click="onDel(index)"
          />
        </span>
      </el-scrollbar>
      <div style="text-align: right">
        <span>总字数&nbsp;{{ candidateWordList.length }}</span>
      </div>
      <div style="text-align: center">
        <el-button
          v-if="!imgShow"
          type="primary"
          style="margin: 20px"
          @click="onEdit"
          >编辑</el-button
        >
        <el-button v-else type="success" style="margin: 20px" @click="onEdit"
          >保存</el-button
        >
      </div>
    </div>
    <el-button type="primary" @click="submitAdd()" style="margin-left:85%; margin-top: 50px;">确定生成</el-button>
  </div>
</template>
<style lang="scss" scoped>
.box {
  border:white 1px solid;
  .top {
    display: flex;
    justify-content: space-around;
  }
  .main {
    display: flex;
    justify-content: space-around;
    margin: 20px;
    .left,
    .right {
      display: flex;
      flex-direction: column;
      align-items: center;
    }
  }
  .footer {
    width: 90%;
    border: black 1px solid;
    margin: 0 auto;
    padding: 10px 10px 0 10px;
  }
}
</style>

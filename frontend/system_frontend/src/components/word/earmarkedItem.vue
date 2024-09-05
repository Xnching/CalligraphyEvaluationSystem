<script>
export default {
  props: {
    templateType: Array,
  },
  data () {
    return {
      formModel: {
        name: '',
        radical: '',
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
      imgCount: 30, // 总共有 143 张图片
      imgUrls:[],
      
      wordImgCount: 30, // 使用相同的图片数量
      wordImgUrls: [],
      imgBlobs: [],
      wordImgBlobs: [],
      candidateWordList: [],
      imgShow: false,
      visible: false,
      tablePageNum: 1,
      tablePageSize: 20,
      tableTotal: 0, 
      wordPageNum: 1,
      wordPageSize: 20,
      wordTotal: 0, 
    }
  },
  mounted() {
    this.wordImgUrls= Array.from(
        { length: this.wordImgCount },
        (_, i) => `/images/addTemplateWord/${i + 1}.jpg` // 注意路径的变化
      );
    this.imgUrls= Array.from(
        { length: this.imgCount },
        (_, i) => `/images/addTemplateWord/${i + 1}.jpg` // 注意路径的变化
      );
    if (this.templateType[1] === '偏旁') {
      this.formModel.radical = '专项练习(偏旁部首)'
    } else if (this.templateType[1] === '结构') {
      this.formModel.radical = '专项练习(结构)'
    }
    this.$nextTick(() => {
      this.getFont();
      this.getRadical();
      this.fetchImages(); 
    });
  },
  watch: {
    templateType: {
      handler(newVal) {
        if (newVal[1] === '偏旁') {
          this.formModel.radical = '专项练习(偏旁部首)'
        } else if (newVal[1] === '结构') {
          this.formModel.radical = '专项练习(结构)'
        }
        this.fetchImages();
      },
      immediate: true // 立即执行一次
    }
  },
  methods: {
    fetchImages () {
      const imgPromises = this.imgUrls.map((url) =>
        fetch(url).then((response) => response.blob())
      )
      Promise.all(imgPromises).then((blobs) => {
        // 使用 this.$set 更新 imgBlobs
        blobs.forEach((blob, index) => {
          this.$set(this.imgBlobs, index, blob); 
        });
      })
      const wordImgPromises = this.wordImgUrls.map((url) =>
        fetch(url).then((response) => response.blob())
      )
      Promise.all(wordImgPromises).then((blobs) => {
        // 使用 this.$set 更新 wordImgBlobs
        blobs.forEach((blob, index) => {
          this.$set(this.wordImgBlobs, index, blob);
        });
      })
    },
    createObjectURL_ (imgBlob) {
      console.log(URL.createObjectURL(imgBlob));//确认返回的 URL 是否正确。
      return URL.createObjectURL(imgBlob)
    },
    wordCreateObjectURL_ (imgBlob) {
      console.log(imgBlob) // 打印 Blob 对象
      return URL.createObjectURL(imgBlob)
    },
    wordHandleClick (imgBlob) {
      const src = this.wordCreateObjectURL_(imgBlob)
      this.candidateWordList.push(src)
      console.log(this.candidateWordList)
    },
    onDel (index) {
      this.candidateWordList.splice(index, 1)
    },
    onEdit () {
      this.imgShow = !this.imgShow
    },
    handleClick(){

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
        v-model="formModel.radical"
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
                label="字体编号"
                width="95">
              </el-table-column>
              <el-table-column
                prop="name"
                label="字体名"
                width="100">
              </el-table-column>
              <el-table-column fixed="right" label="操作">                         
                <template slot-scope="scope">
                  <el-button type="primary" size="small" icon="el-icon-info" @click="handleShow(scope.row)">展示该部首的模板字</el-button>
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
                  v-if="wordCreateObjectURL_(imgBlob)"
                  :src="wordCreateObjectURL_(imgBlob)"
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
          <img :src="imgBlob" style="width: 90px; margin: 5px 0 0 5px" />
          <img
            v-show="imgShow"
            src="/images/叉号.svg"             
            style="position: absolute; bottom: 90px; right: 0; cursor: pointer ; width: 23px; height: 23px;"
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

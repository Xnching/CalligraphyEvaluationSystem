<script setup>
import { defineProps, onMounted, ref, defineEmits } from 'vue'
const props = defineProps({
  templateType: Array,
  // eslint-disable-next-line vue/no-reserved-props
  key: String
})

const formModel = ref({
  name: '',
  radical: '',
  font: '楷书',
  difficulty: 0
})

onMounted(() => {
  console.log(props.templateType)
  if (props.templateType[1] === '偏旁') {
    formModel.value.radical = '专项练习(偏旁部首)'
  } else if (props.templateType[1] === '结构') {
    formModel.value.radical = '专项练习(结构)'
  }
})
const radicalOptions = [
  {
    value: '专项练习(偏旁部首)',
    label: '专项练习(偏旁部首)'
  },
  {
    value: '专项练习(结构)',
    label: '专项练习(结构)'
  },
  {
    value: '专项练习(字体)',
    label: '专项练习(字体)'
  }
]

const fontOptions = [
  {
    value: '楷书',
    label: '楷书'
  },
  {
    value: '行书',
    label: '行书'
  },
  {
    value: '草书',
    label: '草书'
  },
  {
    value: '隶书',
    label: '隶书'
  }
]

const imgCount = 60 // 替换为你的图片数量
const imgUrls = Array.from(
  { length: imgCount },
  (_, i) => `/src/assets/template/偏旁部首/(${i + 1}).jpg`
)
const imgPromises = imgUrls.map((url) =>
  fetch(url).then((response) => response.blob())
)
const imgBlobs = ref([])
Promise.all(imgPromises).then((imgBlobs_) => {
  // imgBlobs_ 是一个包含所有图片文件的 Blob 对象的数组
  imgBlobs.value = imgBlobs_
})
const createObjectURL_ = (imgBlob) => {
  // console.log(imgBlob) // 打印 Blob 对象
  return URL.createObjectURL(imgBlob)
}

const wordImgCount = 36 // 替换为你的图片数量
const wordImgUrls = Array.from(
  { length: wordImgCount },
  (_, i) => `/src/assets/template/单人旁的字/(${i + 1}).jpg`
)
const wordImgPromises = wordImgUrls.map((url) =>
  fetch(url).then((response) => response.blob())
)
const wordImgBlobs = ref([])
Promise.all(wordImgPromises).then((imgBlobs_) => {
  // imgBlobs_ 是一个包含所有图片文件的 Blob 对象的数组
  wordImgBlobs.value = imgBlobs_
})
const wordCreateObjectURL_ = (imgBlob) => {
  // console.log(imgBlob) // 打印 Blob 对象
  return URL.createObjectURL(imgBlob)
}

const candidateWordList = ref([])
const wordHandleClick = (imgBlob) => {
  const src = wordCreateObjectURL_(imgBlob)
  candidateWordList.value.push(src)
  console.log(candidateWordList.value)
}

const imgShow = ref(false)
const onDel = (index) => {
  candidateWordList.value.splice(index, 1)
}
const onEdit = () => {
  imgShow.value = !imgShow.value
}

const visible = ref(false)
const emit = defineEmits(['update'])
const handleSubmit = () => {
  console.log(candidateWordList.value)
  emit('update', visible.value)
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
        :disabled="templateType[1]"
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
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      <el-rate v-model="formModel.difficulty" size="large" />
    </div>
    <div class="main">
      <div class="left">
        <div>偏旁部首</div>
        <div style="border: black solid 2px; width: 30vw">
          <el-scrollbar height="400px">
            <div
              style="
                display: flex;
                justify-content: space-around;
                flex-wrap: wrap;
              "
            >
              <span
                v-for="(imgBlob, index) in imgBlobs"
                :key="index"
                style="
                  display: flex;
                  flex-direction: column;
                  align-items: center;
                  margin: 5px;
                "
              >
                <img :src="createObjectURL_(imgBlob)" @click="handleClick" />
              </span>
            </div>
          </el-scrollbar>
        </div>
      </div>
      <div class="right">
        <div>汉字列表</div>
        <div style="border: black solid 2px; width: 30vw">
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
              >
                <img
                  :src="wordCreateObjectURL_(imgBlob)"
                  @click="wordHandleClick(imgBlob)"
                  style="width: 90px"
                />
              </span>
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
            src="/src/assets/public/叉号.svg"
            style="position: absolute; bottom: 55px; right: 0; cursor: pointer"
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
        <el-button type="primary" @click="handleSubmit">提交</el-button>
      </div>
    </div>
  </div>
</template>
<style lang="scss" scoped>
.box {
  border: black 1px solid;
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

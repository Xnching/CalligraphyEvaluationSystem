<template>
  <div>

    <!--下面为面板-->
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <!--下面为视频管理面板-->
      <el-tab-pane label="视频管理" name="video">
        <VideoTab></VideoTab>
      </el-tab-pane>

      <!--下面为文章管理面板-->
      <el-tab-pane label="文章管理" name="text">
        <ArticleTab></ArticleTab>

      </el-tab-pane>

    </el-tabs>
    

  </div>
  
</template>


<script>
import VideoTab from './VideoTab.vue';
import ArticleTab from './ArticleTab.vue';

export default {
  components:{
    VideoTab,
    ArticleTab
  },
  data() {
    return {
      activeName:'video',
    };
  },
  
  methods: {
    handleClick(){

    },
    


  },

};
</script>


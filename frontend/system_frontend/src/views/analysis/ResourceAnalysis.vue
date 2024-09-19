<template>
<div style="display: flex;">
  <div id="articleChart" style="width: 40%;height:400px;"></div>
  <div id="videoChart" style="width: 40%;height:400px;"></div>
</div>
</template>
<script>
import * as echarts from 'echarts';
export default {
  data() {
    return {
      articleData:[],
      videoData:[],
    }
  },
  created(){
    this.getData();
  },
  methods: { 
    getData(){
      //获取文章数据
      this.request.get("/analysis/article-data").then(res=>{
        if(res.code=='200'){
          this.articleData=res.data;
          this.createArticle();
        }else{
          this.$message.error('获取全部文章数据失败，原因：'+res.msg);
        }

      })
      //获取视频数据
      this.request.get("/analysis/video-data").then(res=>{
        if(res.code=='200'){
          this.videoData=res.data;
          this.createVideo();
        }else{
          this.$message.error('获取全部视频数据失败，原因：'+res.msg);
        }
      })
    },
    //文章
    createArticle(){
      const myChart1 = echarts.init(document.getElementById('articleChart'));
      const option1 = {
        title: {
          text: '文章类型分布',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
        },
        series: [
          {
            name: '类型',
            type: 'pie',
            radius: '50%',
            data: this.articleData.map(item => ({
              value: item.count,
              name: item.type
            })),
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      myChart1.setOption(option1);
    },

    //视频
    createVideo(){
      const myChart2 = echarts.init(document.getElementById('videoChart'));
      const option2 = {
        title: {
          text: '视频类型分布',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
        },
        series: [
          {
            name: '类型',
            type: 'pie',
            radius: '50%',
            data: this.videoData.map(item => ({
              value: item.count,
              name: item.type
            })),
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };
      myChart2.setOption(option2);
    },
  },
}
</script>

<style scoped>

</style>

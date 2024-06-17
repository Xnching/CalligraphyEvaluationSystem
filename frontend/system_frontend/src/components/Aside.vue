<!-- 此处为侧边栏组件 -->
<template>
    <!--   此处更改默认打开的导航，1，3对应导航，style为隐藏边线多余部分   -->
    <el-menu :default-openeds="['1', '3']"    
        background-color=rgb(48,65,86)
        text-color=#ccc
        active-text-color=red
        router=""
        style="min-height:100%; overflow-x:hidden"
        >
        <!-- LOGO位置-->
        <div style="height:60px; line-height:60px; text-align:center">
          <img src="../assets/logo.png" style="width:20px;position:relative;top:5px;margin-right:5px"/>
          <b style="color:white">后台管理系统</b>
        </div>

        <el-submenu index="backend">
          <template slot="title"><i class="el-icon-message"></i>系统用户管理</template>
          <el-menu-item index="/user">系统用户管理</el-menu-item>
          <el-menu-item index="/userGroup">用户组管理</el-menu-item>
        </el-submenu>

        <el-submenu index="base">
          <template slot="title"><i class="el-icon-message"></i>基础数据管理</template>
          <el-menu-item index="/region">区域管理</el-menu-item>
          <el-menu-item index="/school">学校管理</el-menu-item>
          <el-menu-item index="/grade">年级管理</el-menu-item>
          <el-menu-item index="/academicYear">学年管理</el-menu-item>
        </el-submenu>
        
        <el-submenu index="frontend">
          <template slot="title"><i class="el-icon-message"></i>前端用户管理</template>
          <el-menu-item index="/klass">班级管理</el-menu-item>
          <el-menu-item index="/student">学生管理</el-menu-item>
          <el-menu-item index="/teacher">教师管理</el-menu-item>
        </el-submenu>
        
        <el-submenu index="word">
          <template slot="title"><i class="el-icon-message"></i>字库管理</template>
          <el-menu-item index="/sampleWord">样本字管理</el-menu-item>
          <el-menu-item index="/templateWord">模板字管理</el-menu-item>
          <el-menu-item index="/copybook">字帖管理</el-menu-item>
          <el-menu-item index="/template">模板管理</el-menu-item>
          <el-menu-item index="/define">定义管理</el-menu-item>
        </el-submenu>

        <el-submenu index="analysis">
          <template slot="title"><i class="el-icon-message"></i>数据分析</template>
          <el-menu-item index="/dataAnalysis">用户数据分析</el-menu-item>
          <el-menu-item index="/behaviorAnalysis">用户行为分析</el-menu-item>
          <el-menu-item index="/fontAnalysis">字库分析</el-menu-item>
          <el-menu-item index="/resourceAnalysis">书法知识资源分析</el-menu-item>
        </el-submenu>

        <el-submenu index="announcementHelp">
          <template slot="title"><i class="el-icon-menu"></i>公告与帮助</template>
          <el-submenu index="announcement">
          <template slot="title">公告管理</template>
            <el-menu-item index="/releaseAnnouncement">发布公告</el-menu-item>
            <el-menu-item index="/viewAnnouncement">查看公告</el-menu-item>
          </el-submenu>
          <el-submenu index="help">
          <template slot="title">帮助管理</template>
            <el-menu-item index="/question">常见问题与答案设置</el-menu-item>
            <el-menu-item index="/feedback">反馈管理</el-menu-item>
          </el-submenu>
        </el-submenu>

        <el-submenu index="competition">
          <template slot="title"><i class="el-icon-message"></i>竞赛管理</template>
          <el-menu-item index="/competitionList">竞赛列表</el-menu-item>
          <el-menu-item index="/reviewManagement">评阅管理</el-menu-item>
          <el-menu-item index="/reviewWorks">作品评阅</el-menu-item>
        </el-submenu>

        <el-submenu index="outstanding">
          <template slot="title"><i class="el-icon-message"></i>优秀作品管理</template>
          <el-menu-item index="/reviewOutstanding">作业评审</el-menu-item>
          <el-menu-item index="/homeworkSubmissions">作业作品</el-menu-item>
          <el-menu-item index="/competitionSubmissions">竞赛作品</el-menu-item>
        </el-submenu>

        <el-submenu index="resource">
          <template slot="title"><i class="el-icon-message"></i>书法知识管理</template>
          <el-menu-item index="/type">类型管理</el-menu-item>
          <el-menu-item index="/addSource">添加书法知识</el-menu-item>
          <el-menu-item index="/resourceManagement">资源管理</el-menu-item>
          <el-menu-item index="/collection">视频合集管理</el-menu-item>
        </el-submenu>

      </el-menu>
</template>

<script>
export default {
    //输出组件
    name: "Aside"
}
</script>

<style scoped>
</style>
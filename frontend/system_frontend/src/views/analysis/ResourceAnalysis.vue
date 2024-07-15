<template>
<div>
 <el-tabs v-model="activeName" @tab-click="handleClick">     

             <el-container>
          <el-main>
           <el-row :gutter="33">
           <el-col :span="50">
             <el-container>
                <el-header>
                 文章点击榜单
                 <el-dropdown>
                     <span class="el-dropdown-link">
                      下拉菜单<i class="el-icon-arrow-down el-icon--right"></i>
                      </span>
                      <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item>总榜</el-dropdown-item>
                      <el-dropdown-item>年榜</el-dropdown-item>
                      <el-dropdown-item>月榜</el-dropdown-item>
                      <el-dropdown-item>日榜</el-dropdown-item>
                      </el-dropdown-menu>
                      </el-dropdown>
                 </el-header>
                 <el-main>
                      <el-table
                      :data="tableData1"
                      style="width: 100%">
                      <el-table-column
                      prop="rank"
                      label="排行"
                      width="60">
                      </el-table-column>
                      <el-table-column
                      prop="id"
                      label="编号"
                      width="60">
                      </el-table-column>
                      <el-table-column
                      prop="name"
                      label="文章标题"
                      width="155">
                      </el-table-column>
                      <el-table-column
                      prop="count"
                      label="点击次数">
                      </el-table-column>
                      </el-table> 
                 </el-main>
             </el-container>
           </el-col>
           <el-col :span="100">
             <el-container>
                <el-header>
                 文章类型点击榜单
                 <el-dropdown>
                     <span class="el-dropdown-link">
                      下拉菜单<i class="el-icon-arrow-down el-icon--right"></i>
                      </span>
                      <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item>总榜</el-dropdown-item>
                      <el-dropdown-item>年榜</el-dropdown-item>
                      <el-dropdown-item>月榜</el-dropdown-item>
                      <el-dropdown-item>日榜</el-dropdown-item>
                      </el-dropdown-menu>
                      </el-dropdown>
                 </el-header>
                 <el-main>
                      <el-table
                      :data="tableData2"
                      style="width: 100%">
                      <el-table-column
                      prop="rank"
                      label="排行"
                      width="60">
                      </el-table-column>
                      <el-table-column
                      prop="id"
                      label="编号"
                      width="60">
                      </el-table-column>
                      <el-table-column
                      prop="name"
                      label="文章类型"
                      width="95">
                      </el-table-column>
                      <el-table-column
                      prop="count"
                      label="点击次数">
                      </el-table-column>
                      </el-table> 
                 </el-main>
             </el-container>
           </el-col>
           <el-col :span="100">
              <div id="chart1" style="width: 600px;height:400px;"></div>
           </el-col>
           </el-row>
           <el-row :gutter="33">
           <el-col :span="50">
             <el-container>
                <el-header>
                 视频点击榜单
                 <el-dropdown>
                     <span class="el-dropdown-link">
                      下拉菜单<i class="el-icon-arrow-down el-icon--right"></i>
                      </span>
                      <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item>总榜</el-dropdown-item>
                      <el-dropdown-item>年榜</el-dropdown-item>
                      <el-dropdown-item>月榜</el-dropdown-item>
                      <el-dropdown-item>日榜</el-dropdown-item>
                      </el-dropdown-menu>
                      </el-dropdown>
                 </el-header>
                 <el-main>
                      <el-table
                      :data="tableData3"
                      style="width: 100%">
                      <el-table-column
                      prop="rank"
                      label="排行"
                      width="60">
                      </el-table-column>
                      <el-table-column
                      prop="id"
                      label="编号"
                      width="60">
                      </el-table-column>
                      <el-table-column
                      prop="name"
                      label="视频标题"
                      width="155">
                      </el-table-column>
                      <el-table-column
                      prop="count"
                      label="点击次数">
                      </el-table-column>
                      </el-table> 
                 </el-main>
             </el-container>
           </el-col>
            <el-col :span="100">
             <el-container>
                <el-header>
                 视频类型点击榜单
                 <el-dropdown>
                     <span class="el-dropdown-link">
                      下拉菜单<i class="el-icon-arrow-down el-icon--right"></i>
                      </span>
                      <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item>总榜</el-dropdown-item>
                      <el-dropdown-item>年榜</el-dropdown-item>
                      <el-dropdown-item>月榜</el-dropdown-item>
                      <el-dropdown-item>日榜</el-dropdown-item>
                      </el-dropdown-menu>
                      </el-dropdown>
                 </el-header>
                 <el-main>
                      <el-table
                      :data="tableData4"
                      style="width: 100%">
                      <el-table-column
                      prop="rank"
                      label="排行"
                      width="60">
                      </el-table-column>
                      <el-table-column
                      prop="id"
                      label="编号"
                      width="60">
                      </el-table-column>
                      <el-table-column
                      prop="name"
                      label="视频类型"
                      width="95">
                      </el-table-column>
                      <el-table-column
                      prop="count"
                      label="点击次数">
                      </el-table-column>
                      </el-table> 
                 </el-main>
             </el-container>
           </el-col>
           <el-col :span="100">
              <div id="chart2" style="width: 600px;height:400px;"></div>
           </el-col>
           </el-row>
          </el-main>
         </el-container>   
        </el-tabs>
        <div style="display: flex;justify-content: space-between;flex-wrap: wrap;">
  </div>

     
</div>
</template>
<script>
import * as echarts from 'echarts';
export default {
      data() {
        return {
          tableData6: [
            { rank1: '1', id1: '1234', name1: '上下', count1: '999' },
            { rank1: '2', id1: '1235', name1: '左右', count1: '998' },
            { rank1: '3', id1: '1236', name1: '品字', count1: '997' },
            { rank1: '4', id1: '1237', name1: '半包围', count1: '996' },
            { rank1: '5', id1: '1238', name1: '独体字', count1: '995' },
            { rank1: '6', id1: '1239', name1: '全包围', count1: '994' },
          ],
          tableData5: [
            { rank1: '1', id1: '1234', name1: '上下', count1: '999' },
            { rank1: '2', id1: '1235', name1: '左右', count1: '998' },
            { rank1: '3', id1: '1236', name1: '品字', count1: '997' },
            { rank1: '4', id1: '1237', name1: '半包围', count1: '996' },
            { rank1: '5', id1: '1238', name1: '独体字', count1: '995' },
            { rank1: '6', id1: '1239', name1: '全包围', count1: '994' },
          ],
          structuredTableData1: [],
          structuredTableData2: [],
          tableData3: [{
            rank:'1',
            id:'1234',
            name:'南',
            count:'999'
          }, {
            rank:'2',
            id:'1235',
            name:'京',
            count:'998'
          },{
            rank:'3',
            id:'1236',
            name:'信',
            count:'997'
          },{
            rank:'4',
            id:'1237',
            name:'息',
            count:'996'
          },{
            rank:'5',
            id:'1238',
            name:'工',
            count:'995'
          },
          ],
          tableData1: [{
            rank:'1',
            id:'1234',
            name:'南',
            count:'999'
          }, {
            rank:'2',
            id:'1235',
            name:'京',
            count:'998'
          },{
            rank:'3',
            id:'1236',
            name:'信',
            count:'997'
          },{
            rank:'4',
            id:'1237',
            name:'息',
            count:'996'
          },{
            rank:'5',
            id:'1238',
            name:'工',
            count:'995'
          },
          ],
          tableData2: [{
            rank:'1',
            id:'1234',
            name:'南',
            count:'999'
          }, {
            rank:'2',
            id:'1235',
            name:'京',
            count:'998'
          },{
            rank:'3',
            id:'1236',
            name:'信',
            count:'997'
          },{
            rank:'4',
            id:'1237',
            name:'息',
            count:'996'
          },{
            rank:'5',
            id:'1238',
            name:'工',
            count:'995'
          },
          ],
          tableData4: [{
            rank:'1',
            id:'1234',
            name:'上下',
            count:'999'
          }, {
            rank:'2',
            id:'1235',
            name:'左右',
            count:'998'
          },{
            rank:'3',
            id:'1236',
            name:'品字',
            count:'997'
          },{
            rank:'4',
            id:'1237',
            name:'半包围',
            count:'996'
          },{
            rank:'5',
            id:'1238',
            name:'独体字',
            count:'995'
          },
          ]
        }
      },
      created(){
        this.structuredTableData1 = this.getStructuredTableData1();
        this.structuredTableData2 = this.getStructuredTableData2();
      },
      mounted(){
        this.createChart();
      },
      methods: { 
        createChart() {
          // 基于准备好的dom，初始化echarts实例
          const myChart1 = echarts.init(document.getElementById('chart1'));
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
                name: '字形结构',
                type: 'pie',
                radius: '50%',
                data: this.structuredTableData1.map(item => ({
                  value: item.count1,
                  name: item.name1
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

          const myChart2 = echarts.init(document.getElementById('chart2'));
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
                name: '字形结构',
                type: 'pie',
                radius: '50%',
                data: this.structuredTableData2.map(item => ({
                  value: item.count1,
                  name: item.name1
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
        getStructuredTableData2() {
          const order = ["上下", "左右", "品字", "半包围", "全包围", "独体字"];
          return order.map(structure => this.tableData6.find(item => item.name1 === structure)).filter(Boolean);
        },
        getStructuredTableData1() {
          const order = ["上下", "左右", "品字", "半包围", "全包围", "独体字"];
          return order.map(structure => this.tableData5.find(item => item.name1 === structure)).filter(Boolean);
        }
      },
      }
</script>

<style scoped>

</style>

<template>
    <div class="management-container">


        <!--字体定义管理-->
        <div>
            <label style="margin-left: -220px;">字体类型管理:</label>
            <!--搜索框-->
            <div style="padding:10px; margin:10px;  display: flex; align-items: center;">
                <el-input style="width:150px ;margin-right: 30px" 
                suffix-icon="el-icon-search" 
                placeholder="请输入"
                v-model="inputVal"
                @keyup.enter.native="Search_table1()"
                clearable>
                </el-input>
                <el-button style="margin-right:70px" type="primary">搜索</el-button>
            </div>

            <!--表格-->
            <el-table
            ref="fontTable"
            :data="fontTableData"
            stripe
            tooltip-effect="dark"
            style="width: 100%">
                <el-table-column
                prop="id"
                label="字体ID"
                width="95">
                </el-table-column>
                <el-table-column
                prop="name"
                label="字体名"
                width="100">
                </el-table-column>
                <el-table-column fixed="right" label="操作">                         
                    <template slot-scope="scope">
                        <el-button type="danger" size="small" icon="el-icon-delete">删除</el-button>
                    </template>
                </el-table-column> 

            </el-table>
            <div class="pagination" style="margin-top: 10px;">
                <button :disabled="currentPage === 1" @click="onPageChange(currentPage - 1)">上一页</button>
                <span>{{ currentPage }} / {{ totalPages }}</span>
                <button :disabled="currentPage === totalPages" @click="onPageChange(currentPage + 1)">下一页</button>
            </div>
            <!--添加操作-->
            <div>
                <el-button type="success" @click="add1" style="margin-top: 20px;">添加</el-button>
            </div>



        </div>


        <!--结构定义管理-->
        <div>
            <label style="margin-left: -220px;">结构类型管理:</label>
            <!--搜索框-->
            <div style="padding:10px; margin:10px;  display: flex; align-items: center;">
                <el-input style="width:150px ;margin-right: 30px" 
                suffix-icon="el-icon-search" 
                placeholder="请输入"
                v-model="inputVal"
                @keyup.enter.native="Search_table2()"
                clearable>
                </el-input>
                <el-button style="margin-right:100px" type="primary">搜索</el-button>
            </div>

            <!--表格-->
            <el-table
            ref="structureTable"
            :data="structureTableData"
            stripe
            tooltip-effect="dark"
            style="width: 100%">
                <el-table-column
                prop="id"
                label="结构ID"
                width="95">
                </el-table-column>
                <el-table-column
                prop="name"
                label="结构名"
                width="100">
                </el-table-column>
                <el-table-column fixed="right" label="操作">                         
                    <template slot-scope="scope">
                        <el-button type="danger" size="small"  icon="el-icon-delete">删除</el-button>
                    </template>
                </el-table-column> 

            </el-table>
            <div class="pagination" style="margin-top: 10px;">
                <button :disabled="currentPage === 1" @click="onPageChange(currentPage - 1)">上一页</button>
                <span>{{ currentPage }} / {{ totalPages }}</span>
                <button :disabled="currentPage === totalPages" @click="onPageChange(currentPage + 1)">下一页</button>
            </div>
            <!--添加操作-->
            <div>
                <el-button type="success" @click="add2" style="margin-top: 20px;">添加</el-button>
            </div>
        </div>
            

        <!--偏旁部首定义管理-->
        <div>
            <label style="margin-left: -220px;">偏旁部首类型管理:</label>
            <!--搜索框-->
            <div style="padding:10px; margin:10px; display: flex; align-items: center;">
                <el-input style="width:150px ;margin-right: 30px" 
                suffix-icon="el-icon-search" 
                placeholder="请输入"
                v-model="inputVal"
                @keyup.enter.native="Search_table3()"
                clearable>
                </el-input>
                <el-button style="margin-right:100px" type="primary" >搜索</el-button>
            </div>

            <!--表格-->
            <el-table
            ref="eccentricityTable"
            :data="eccentricityTableData"
            stripe
            tooltip-effect="dark"
            style="width: 100%">
                <el-table-column
                prop="id"
                label="偏旁部首ID"
                width="95">
                </el-table-column>
                <el-table-column
                prop="name"
                label="偏旁部首名"
                width="100">
                </el-table-column>
                <el-table-column fixed="right" label="操作">                         
                    <template slot-scope="scope">
                        <el-button type="danger" size="small"  icon="el-icon-delete">删除</el-button>
                    </template>
                </el-table-column> 

            </el-table>
            <div class="pagination" style="margin-top: 10px;">
                <button :disabled="currentPage === 1" @click="onPageChange(currentPage - 1)">上一页</button>
                <span>{{ currentPage }} / {{ totalPages }}</span>
                <button :disabled="currentPage === totalPages" @click="onPageChange(currentPage + 1)">下一页</button>
            </div>
            <!--添加操作-->
            <div>
                <el-button type="success" @click="add3" style="margin-top: 20px;">添加</el-button>
            </div>


        </div>
    </div>
</template>
<script>
export default {
    data(){
        return{
            fontTableData: Array(8).fill().map(() => ({
                id:"10000",
                name: "楷体-孙国胜",
            })),

            eccentricityTableData: Array(8).fill().map(() => ({
                id:"10000",
                name: "草字头",
            })),
            structureTableData: Array(8).fill().map(() => ({
                id:"10000",
                name: "上下结构",
            })),
        }

    }
}
</script>

<style scoped>
.management-container {
  display: flex;
  justify-content: space-between; /*  可选：将三个区域均匀分布  */
  gap: 20px;                     /*  可选：设置区域之间的间距  */
}
</style>

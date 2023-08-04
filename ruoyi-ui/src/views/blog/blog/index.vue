<template>
  <div class="app-container">
    <!--查询栏-->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="文章标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入文章标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文章内容" prop="text">
        <el-input
          v-model="queryParams.text"
          placeholder="请输入文章内容"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作者" prop="authorId">
        <el-input
          v-model="queryParams.authorId"
          placeholder="请输入作者"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类" prop="categoryId">
        <el-input
          v-model="queryParams.categoryId"
          placeholder="请输入分类"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文章状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择文章状态" clearable size="small">
          <el-option v-for="dict in statusOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <!--功能栏-->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:blog:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:blog:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:blog:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          :disabled="multiple"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:blog:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!--结果表格-->
    <el-table v-loading="loading" :data="blogList" :default-sort = "{prop: 'createTime', order: 'descending'}" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="45" align="center" />
      <el-table-column label="文章id" align="center" prop="id" v-if="false" />
      <el-table-column label="文章首图" align="center" prop="img" >
        <template slot-scope="scope">
        <el-image
          style="width: 100%; height: 100%"
          :src="scope.row.img"
          :preview-src-list="[scope.row.img]">
        </el-image>
        </template>
      </el-table-column>
      <el-table-column label="文章标题" align="center" prop="title" />
<!--      <el-table-column label="文章内容" align="center" prop="text" />-->
      <el-table-column label="作者" align="center" prop="sysUser.nickName" />
      <el-table-column label="分类" align="center" prop="category.name" />
      <el-table-column label="浏览数量" align="center" prop="views" />
      <el-table-column label="文章状态" align="center" prop="status" :formatter="stateFormat2"/>
      <el-table-column label="文章密码" align="center" prop="password" />
      <el-table-column label="创建时间" align="center" prop="createTime" sortable />
      <el-table-column label="赞赏" align="center" width="50" prop="allowFeed" :formatter="stateFormat"/>
      <el-table-column label="评论" align="center" width="50" prop="allowComment" :formatter="stateFormat"/>
      <el-table-column label="转载" align="center" width="50" prop="allowShare" :formatter="stateFormat"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:blog:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:blog:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

  </div>
</template>

<script>
import { listBlog,delBlog, addBlog, updBlog, exportBlog } from "@/api/blog/blog";

export default {
  name: "Blog",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 文章表格数据
      blogList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        img: null,
        title: null,
        text: null,
        authorId: null,
        categoryId: null,
        views: null,
        status: null,
        password: null,
        allowFeed: null,
        allowComment: null,
        allowShare: null,
        version: null,
        deleted: 0
      },
      statusOptions: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("blog_status").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询文章列表 */
    getList() {
      this.loading = true;
      listBlog(this.queryParams).then(response => {
        this.blogList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    //布尔值转换
    stateFormat(row, column,value) {
      if (value === true) {
        return '是'
      } else  {
        return '否'
      }
    },
    stateFormat2(row, column,value) {
      switch (value) {
        case -1: return "删除";
        case 0: return "暂存";
        case 1: return "模板";
        case 2: return "发布";
        case 3: return "置顶";
      }
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        img: null,
        title: null,
        text: null,
        authorId: null,
        categoryId: null,
        views: null,
        status: "0",
        password: null,
        allowFeed: null,
        allowComment: null,
        allowShare: null,
        createTime: null,
        updateTime: null,
        version: null,
        deleted: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push({path:"/blog/write"})
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const id = row.id || this.ids
      if(typeof id == "number"){
        this.$router.push({path:"/blog/edit",query: [id]})
      }else{
        this.$router.push({path:"/blog/edit",query:id})
      }

    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updBlog(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addBlog(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除文章编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delBlog(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有文章数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportBlog(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>

<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="用户" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文件夹" prop="floderId">
        <el-input
          v-model="queryParams.floderId"
          placeholder="请输入文件夹"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="存储策略" prop="strategy">
        <el-input
          v-model="queryParams.strategy"
          placeholder="请输入存储策略"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="别名" prop="aliasName">
        <el-input
          v-model="queryParams.aliasName"
          placeholder="请输入别名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="文件类型" prop="mime">
        <el-input
          v-model="queryParams.mime"
          placeholder="请输入文件类型"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:images:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:images:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:images:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:images:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-row :gutter="15" class="panel-group">
      <el-col :xs="12" :sm="6" :lg="4" v-for="(image, index) in imagesList" :key="index" class="card-panel-col ">
        <el-card :body-style="{ padding: '0px' }" shadow="hover">
          <el-button type="text" class="delButton" @click="delFile(image.id,image.name)" icon="el-icon-error"></el-button>
          <el-image
            :preview-src-list="['https://pic.ilvyu.cn'+image.path+image.name]"
            :src="'https://pic.ilvyu.cn'+image.path+image.name"
            class="image">
          </el-image>

          <div style="padding: 14px;">
            <span>{{image.name}}</span>
            <div class="bottom clearfix">
              <time class="time">"2021-03-16T14:06:39.337Z"</time>
              <el-button type="text" class="button" icon="el-icon-paperclip"></el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改图片对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="归属用户id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入归属用户id" />
        </el-form-item>
        <el-form-item label="文件夹id" prop="floderId">
          <el-input v-model="form.floderId" placeholder="请输入文件夹id" />
        </el-form-item>
        <el-form-item label="存储策略" prop="strategy">
          <el-input v-model="form.strategy" placeholder="请输入存储策略" />
        </el-form-item>
        <el-form-item label="路径" prop="path">
          <el-input v-model="form.path" placeholder="请输入路径" />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="别名" prop="aliasName">
          <el-input v-model="form.aliasName" placeholder="请输入别名" />
        </el-form-item>
        <el-form-item label="路径名称" prop="pathname">
          <el-input v-model="form.pathname" placeholder="请输入路径名称" />
        </el-form-item>
        <el-form-item label="大小" prop="size">
          <el-input v-model="form.size" placeholder="请输入大小" />
        </el-form-item>
        <el-form-item label="文件类型" prop="mime">
          <el-input v-model="form.mime" placeholder="请输入文件类型" />
        </el-form-item>
        <el-form-item label="sha1" prop="sha1">
          <el-input v-model="form.sha1" placeholder="请输入sha1" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listImages, getImages, delImages, addImages, updateImages, exportImages } from "@/api/blog/images";

export default {
  name: "Images",
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
      showSearch: false,
      // 总条数
      total: 0,
      // 图片表格数据
      imagesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        floderId: null,
        strategy: null,
        path: null,
        name: null,
        aliasName: null,
        pathname: null,
        size: null,
        mime: null,
        sha1: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询图片列表 */
    getList() {
      this.loading = true;
      listImages(this.queryParams).then(response => {
        this.imagesList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        userId: null,
        floderId: null,
        strategy: null,
        path: null,
        name: null,
        aliasName: null,
        pathname: null,
        size: null,
        mime: null,
        sha1: null
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
      this.reset();
      this.open = true;
      this.title = "添加图片";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getImages(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改图片";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateImages(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addImages(this.form).then(response => {
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

    delFile(id,name){
      this.$confirm('是否确认删除图片编号为"' + name + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delImages(id);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(function() {});
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除图片编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delImages(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有图片数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportImages(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
<style scoped>
  .card-panel-col {
    height: 225px;
    margin-bottom: 32px;
  }
  el-card{
    height: 150px;
    align-content: center;
  }
  .delButton{
    position: absolute;
    z-index: 1;
    padding: 0;
  }
  .time {
    font-size: 13px;
    color: #999;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    height: 150px;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }

</style>

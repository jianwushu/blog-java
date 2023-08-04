<template>
  <div class="app-container">
    <el-collapse>
      <el-collapse-item title="基本设置" name="1">
        <!-- 基本设置 -->
        <el-form ref="form" label-width="100px">
          <el-form-item :label="item.description" prop="title" v-for="(item,index) in options1" :key="index">
            <el-input v-model="item.val" :placeholder="'请输入'+item.description" />
          </el-form-item>
        </el-form>
        <div class="dialog-footer right">
          <el-button size="mini" type="primary" @click="submitForm('index')">更新</el-button>
        </div>
      </el-collapse-item>
      <el-collapse-item title="Rss订阅" name="2">
        <!-- 关于我 -->
        <el-form ref="form" label-width="100px">
          <el-form-item :label="item.description" prop="title" v-for="(item,index) in options3" :key="index">
            <el-input v-model="item.val" :placeholder="'请输入'+item.description" />
          </el-form-item>
        </el-form>
        <div class="dialog-footer right">
          <el-button size="mini"  type="primary" @click="submitForm('rss')">更新</el-button>
        </div>
      </el-collapse-item>
      <el-collapse-item title="关于我" name="3">
        <!-- 关于我 -->
        <el-form ref="form" label-width="100px">
          <el-form-item :label="item.description" prop="title" v-for="(item,index) in options2" :key="index">
            <el-input v-model="item.val" :placeholder="'请输入'+item.description" />
          </el-form-item>
        </el-form>
        <div class="dialog-footer right">
          <el-button size="mini"  type="primary" @click="submitForm('about')">更新</el-button>
        </div>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
  import { listOption, updOption} from "@/api/blog/option";

  export default {
    name: "Options",
    data() {
      return {
        // 总条数
        total: 0,
        // 网站设置表格数据
        options1: [],
        options2: [],
        options3: [],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 20,
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
      /** 查询网站设置列表 */
      getList() {
        this.options1 = []
        this.options2 = []
        this.options3 = []
        listOption(this.queryParams).then(res => {
          for(let o of res.rows){
            switch (o.type) {
              case 'index':
                this.options1.push(o);break;
              case 'about':
                this.options2.push(o);break;
              case 'rss':
                this.options3.push(o);break;
            }
          }
        });
      },
      /** 提交按钮 */
      submitForm(type) {
        switch (type) {
          case 'index':
            this.updOptionData(JSON.stringify(this.options1));break;
          case 'about':
            this.updOptionData(JSON.stringify(this.options2));break;
          case 'rss':
            this.updOptionData(JSON.stringify(this.options3));break;
        }
      },
      updOptionData(data){
        updOption(data).then(res => {
          if (res.code === 200) {
            this.msgSuccess("修改成功");
            this.getList();
          }
        });
      }
    }
  };
</script>

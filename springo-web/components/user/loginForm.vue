<template>
  <div style="padding:25px">

    <el-form
      :model="form"
      :rules="rules"
      ref="form"
    >
      <!-- 用户名的输入框 -->
      <el-form-item prop="username">
        <el-input
          placeholder="用户名/手机"
          v-model="form.userName">
        </el-input>
      </el-form-item>

      <!-- 密码的输入框 -->
      <el-form-item prop="password">
        <el-input
          placeholder="密码"
          type="password"
          v-model="form.passWord"
          show-password>
        </el-input>
      </el-form-item>

      <el-button
        @click="handleSubmit"
        style="width:100%;"
        type="primary"
      >登录
      </el-button>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        userName: "",
        passWord: ""
      },

      rules: {
        userName: [
          {required: true, message: "请输入用户名", trigger: "blur"}
        ],
        passWord: [
          {required: true, message: "请输入密码", trigger: "blur"}
        ]
      }
    }
  },

  methods: {
    // 提交登录事件
    handleSubmit() {

      this.$refs.form.validate(valid => {
        if (valid) {

          this.$store.dispatch("user/login", this.form).then(res => {
            console.log(res)
            this.$message.success("登录成功");
            this.$router.push("/")
          });
        }
      })

    }
  }
}
</script>

<style>

</style>

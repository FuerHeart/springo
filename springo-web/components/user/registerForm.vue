<template>
  <el-form
    :model="form"
    :rules="rules"
    class="form"
    ref="form">

    <el-form-item class="form-item" prop="phone">
      <el-input
        placeholder="手机号"
        v-model="form.phone">
      </el-input>
    </el-form-item>

    <!--    <el-form-item class="form-item" prop="captcha">-->
    <!--      <el-input-->
    <!--        placeholder="验证码"-->
    <!--        v-model="form.captcha">-->
    <!--        <template slot="append">-->
    <!--          <el-button @click="handleSendCaptcha">-->
    <!--            发送验证码-->
    <!--          </el-button>-->
    <!--        </template>-->
    <!--      </el-input>-->
    <!--    </el-form-item>-->

    <el-form-item class="form-item" prop="userName">
      <el-input
        placeholder="用户名"
        v-model="form.userName">
      </el-input>
    </el-form-item>

    <el-form-item label="" prop="gender">
      <el-radio-group v-model="form.gender" style="margin-left: 90px">
        <el-radio v-model="form.gender" label="男" border>男</el-radio>
        <el-radio v-model="form.gender" label="女" border>女</el-radio>
      </el-radio-group>
    </el-form-item>


    <el-form-item class="form-item" prop="passWord">
      <el-input
        placeholder="密码"
        type="password"
        v-model="form.passWord"
        show-password
      ></el-input>
    </el-form-item>

    <el-form-item class="form-item" prop="checkPassword">
      <el-input
        placeholder="确认密码"
        type="password"
        v-model="form.checkPassword"
        show-password>
      </el-input>
    </el-form-item>

    <el-button
      @click="handleRegSubmit"
      class="submit"
      type="primary">
      注册
    </el-button>
  </el-form>
</template>

<script>
export default {
  data() {
    // rule 当前的规则
    // value 当前表单字段的值
    // callback 回调是必须要调用，才会触发后面的代码
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.form.passWord) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };

    return {
      // 表单数据
      form: {
        userName: "",
        // captcha: "", // 验证码
        gender: "男",
        phone: "",
        passWord: "",
        checkPassword: ""
      },
      // 表单规则
      rules: {
        userName: [
          {required: true, message: "请输入用户名", trigger: "blur"}
        ],
        // captcha: [
        //   {required: true, message: "请输入验证码", trigger: "blur"}
        // ],
        gender: [
          {required: true, message: '请选择性别', trigger: 'change'}
        ],
        phone: [
          {required: true, message: "请输入手机号", trigger: "blur"}
        ],
        passWord: [
          {required: true, message: "请输入密码", trigger: "blur"}
        ],
        checkPassword: [
          // validator 验证函数
          {validator: validatePass, trigger: "blur"}
        ]
      },
    }
  },
  methods: {
    // 发送验证码
    handleSendCaptcha() {
      // 如果手机号码是空，直接返回
      if (!this.form.phone) {
        this.$message.warning("请输入手机号码");
        return;
      }

      this.$axios({
        url: "/captchas",
        method: "POST",
        data: {
          tel: this.form.phone
        }
      }).then(res => {
        const {code} = res.data;
        alert(code);
      })
    },

    // 注册
    handleRegSubmit() {
      // 表单的验证
      this.$refs.form.validate(valid => {
        if (valid) {
          const {checkPassword, ...props} = this.form;

          // 调用注册的接口
          this.$axios({
            url: "/users/userRegister",
            method: "POST",
            data: props
          }).then(res => {
            // alert(res.data.message);
            if (res.data.code === 200) {
              this.$message({
                message: res.data.message,
                type: 'success'
              });
              this.$store.commit("user/setUserInfo", res.data);
            } else {
              this.$message.error('注册失败了哦，已有用户名或者手机号！');
            }
            this.$router.push("/user/login")
          });
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
.form {
  padding: 25px;
}

.form-item {
  margin-bottom: 20px;
}

.form-text {
  font-size: 12px;
  color: #409EFF;
  text-align: right;
  line-height: 1;
}

.submit {
  width: 100%;
  margin-top: 10px;
}
</style>
